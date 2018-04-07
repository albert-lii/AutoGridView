package com.liyi.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.liyi.grid.adapter.BaseAutoGridAdapter;
import com.liyi.grid.model.AutoGridHelper;
import com.liyi.grid.model.AutoGridParamBean;
import com.liyi.grid.model.AutoGridResultBean;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AutoGridView extends ViewGroup {
    /**
     * 默认值
     */
    // 默认网格图模式
    public static final int DEF_GRID_MODE = AutoGridConfig.GRID_NINE;
    // 默认网格行数
    public static final int DEF_GRID_ROW = 3;
    // 默认网络列数
    public static final int DEF_GRID_COLUMN = 3;
    // 默认网格的高度
    public static final int DEF_GRID_HEIGHT = AutoGridConfig.INVALID_VAL;
    // 默认网格的横向间距
    public static final int DEF_GRID_HSPACE = 10;
    // 默认网格的纵向间距
    public static final int DEF_GRID_VSPACE = 10;
    // 网格图中，当只有一个 itemView 时，itemView 的宽与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    // 此处默认 DEF_GRID_ONE_WPERCENT 为无效值，即 itemView 的宽度自适应
    public static final float DEF_GRID_ONE_WPERCENT = AutoGridConfig.INVALID_VAL;
    // 网格图中，当只有一个 itemView 时，itemView 的高与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    public static final float DEF_GRID_ONE_HPERCENT = AutoGridConfig.INVALID_VAL;

    /**
     * 变量
     */
    // 网格图的模式
    private int mGridMode;
    // 网格图的行数
    private int mGridRow;
    // 网格图的列数
    private int mGridColumn;
    // 单个网格图的高度
    private float mGridHeight;
    // 网格之间的横向间距
    private float mGridHspace;
    // 网格之间的纵向间距
    private float mGridVspqce;
    // 九宫格模式时，当只有一个 itemView 时，itemView 的宽与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    private float mGridOneWidthPercent;
    // 九宫格模式时，当只有一个 itemView 时，itemView 的高与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    private float mGridOneHeightPercent;

    // 网格数据计算辅助类
    private AutoGridHelper mAutoGridHelper;
    private AutoGridParamBean mParamBean;
    // AutoGridHelper 中计算结果后，返回的结果类
    private AutoGridResultBean mResultBean;
    // 缓存器，存储 itemView ，用于复用
    private HashMap<String, List<SoftReference<View>>> mCacheBin;

    private AdapterObserver mDataSetObserver;
    private BaseAutoGridAdapter mAdapter;
    private OnItemClickListener mItemClickListener;

    public AutoGridView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initParams();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoGridView);
            if (a != null) {
                mGridMode = a.getInt(R.styleable.AutoGridView_grid_mode, mGridMode);
                mGridRow = a.getInt(R.styleable.AutoGridView_grid_row, mGridRow);
                mGridColumn = a.getInt(R.styleable.AutoGridView_grid_column, mGridColumn);
                mGridHeight = a.getDimension(R.styleable.AutoGridView_grid_height, mGridHeight);
                mGridHspace = a.getDimension(R.styleable.AutoGridView_grid_hspace, mGridHspace);
                mGridVspqce = a.getDimension(R.styleable.AutoGridView_grid_vspace, mGridVspqce);
                mGridOneWidthPercent = a.getFloat(R.styleable.AutoGridView_grid_onewper, mGridOneWidthPercent);
                mGridOneHeightPercent = a.getFloat(R.styleable.AutoGridView_grid_onehper, mGridOneHeightPercent);
                a.recycle();
            }
        }
    }

    private void initParams() {
        mGridMode = DEF_GRID_MODE;
        mGridRow = DEF_GRID_ROW;
        mGridColumn = DEF_GRID_COLUMN;
        mGridHeight = DEF_GRID_HEIGHT;
        mGridHspace = DEF_GRID_HSPACE;
        mGridVspqce = DEF_GRID_VSPACE;
        mGridOneWidthPercent = DEF_GRID_ONE_WPERCENT;
        mGridOneHeightPercent = DEF_GRID_ONE_HPERCENT;

        mAutoGridHelper = new AutoGridHelper();
        mCacheBin = new HashMap<String, List<SoftReference<View>>>();
    }

    public void setAdapter(BaseAutoGridAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            // 删除已经存在的观察者
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        // 清空缓存器
        mCacheBin.clear();
        removeAllViews();
        // 重置 adapter
        mAdapter = adapter;
        if (mAdapter != null) {
            mDataSetObserver = new AdapterObserver();
            // 注册观察者
            mAdapter.registerDataSetObserver(mDataSetObserver);
            if (mAdapter.getCount() > 0) {
                addItemViews();
            }
        }
    }

    /**
     * 添加 itemView
     */
    private void addItemViews() {
        int itemCount = mAdapter.getCount();
        int maxCount = mGridRow * mGridColumn;
        int childCount = itemCount > maxCount ? maxCount : itemCount;
        for (int i = 0; i < childCount; i++) {
            View itemView = mAdapter.getView(i, null, this);
            /** 一定要有 LayoutParams ，addView 的时候，（ itemView 本身的 xml 的 width 和 height 设置的值，都是没效果的（变成 wrap_content ），
             * 可能会出现控件的宽高显示偏差，所以必须设置此句）*/
            itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            String key = mGridMode + "+" + mAdapter.getItemViewType(i);
            List<SoftReference<View>> views = mCacheBin.get(key);
            views = (views != null ? views : new ArrayList<SoftReference<View>>());
            views.add(new SoftReference<View>(itemView));
            mCacheBin.put(key, views);
            addItemClickListener(itemView, i);
            addView(itemView);
        }
    }

    /**
     * 设置 itemView 的点击监听事件
     *
     * @param view
     * @param position
     */
    private void addItemClickListener(View view, final int position) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position, v);
                }
            }
        });
    }

    /**
     * 通知刷新
     */
    private void notifyChanged() {
        if (mAdapter != null) {
            int itemCount = mAdapter.getCount();
            removeAllViews();
            if (itemCount == 0) {
                mCacheBin.clear();
                return;
            }
            for (int i = 0; i < itemCount; i++) {
                String key = mGridMode + "+" + mAdapter.getItemViewType(i);
                // 从缓存器中取出 key 类型的 view 列表
                List<SoftReference<View>> views = mCacheBin.get(key);
                /** 此处做简单的缓存复用处理 */
                if (views != null && views.size() > 0) {
                    boolean isAdd = false;
                    for (SoftReference<View> softReference : views) {
                        if (softReference != null && softReference.get() != null) {
                            // 如果列表中的 itemView 还没有父容器，则复用该 itemView
                            if (softReference.get().getParent() == null) {
                                addItemClickListener(softReference.get(), i);
                                addView(mAdapter.getView(i, softReference.get(), this));
                                isAdd = true;
                                break;
                            }
                        }
                    }
                    // 如果从 views 中未找到匹配的 itemView ，则新建 itemView
                    if (!isAdd) {
                        View itemView = mAdapter.getView(i, null, this);
                        // 将新建的 itemView 存入列表中
                        views.add(new SoftReference<View>(itemView));
                        mCacheBin.put(key, views);
                        addItemClickListener(itemView, i);
                        addView(itemView);
                    }
                }
                // 如果没有 key 类型的 view 存在，则新建 itemView
                else {
                    View itemView = mAdapter.getView(i, null, this);
                    views = (views != null ? views : new ArrayList<SoftReference<View>>());
                    views.add(new SoftReference<View>(itemView));
                    mCacheBin.put(key, views);
                    addItemClickListener(itemView, i);
                    addView(itemView);
                }
            }
            /** 将多余的没有用到的 itemView 移除 */
            for (String key : mCacheBin.keySet()) {
                List<SoftReference<View>> views = mCacheBin.get(key);
                if (views != null && views.size() > 0) {
                    List<SoftReference<View>> tempViews = new ArrayList<SoftReference<View>>();
                    for (SoftReference<View> softReference : views) {
                        if (softReference != null && softReference.get() != null) {
                            if (softReference.get().getParent() == null) {
                                tempViews.add(softReference);
                            }
                        }
                    }
                    views.remove(tempViews);
                    mCacheBin.put(key, views);
                }
            }
        }
        invalidate();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (mAdapter == null || getChildCount() == 0) {
            setMeasuredDimension(widthSize, heightMode == MeasureSpec.EXACTLY ? heightSize : getPaddingTop() + getPaddingBottom());
            return;
        }
        int width = widthSize - getPaddingLeft() - getPaddingRight();
        int childCount = getChildCount();
        mParamBean = new AutoGridParamBean();
        mParamBean.setGridType(mGridMode)
                .setGridRow(mGridRow)
                .setGridColumn(mGridColumn)
                .setGridHeight(mGridHeight)
                .setGridHspace(mGridHspace)
                .setGridVspqce(mGridVspqce)
                .setGridOneWper(mGridOneWidthPercent)
                .setGridOneHper(mGridOneHeightPercent)
                .setItemCount(childCount)
                .setMaxWidth(width);
        if (mAutoGridHelper == null) {
            mAutoGridHelper = new AutoGridHelper();
        }
        mResultBean = mAutoGridHelper.calculateSize(mParamBean);
        // 如果只有一个 child ，且当前模式为九宫格模式
        if (childCount == 1 && mGridMode == AutoGridConfig.GRID_NINE) {
            View child = getChildAt(0);
            int childWms, childHms;
            // 宽度自适应
            if (mGridOneWidthPercent == AutoGridConfig.INVALID_VAL) {
                childWms = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
            } else {
                childWms = MeasureSpec.makeMeasureSpec((int) mResultBean.getChildWidth(), MeasureSpec.EXACTLY);
            }
            // 高度自适应
            if (mGridOneHeightPercent == AutoGridConfig.INVALID_VAL) {
                childHms = MeasureSpec.makeMeasureSpec((int) mResultBean.getChildHeight(), MeasureSpec.UNSPECIFIED);
            } else {
                childHms = MeasureSpec.makeMeasureSpec((int) mResultBean.getChildHeight(), MeasureSpec.EXACTLY);
            }
            child.measure(childWms, childHms);
//            child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
//                    MeasureSpec.makeMeasureSpec((int) mResultBean.getChildHeight(), MeasureSpec.EXACTLY));
            int cw = child.getMeasuredWidth() == 0 ? width : child.getMeasuredWidth();
            int ch = child.getMeasuredHeight() == 0 ? (int) (0.6 * width) : child.getMeasuredHeight();
            mResultBean.setChildWidth(cw);
            mResultBean.setChildHeight(ch);
            mResultBean.setParentWidth(cw);
            mResultBean.setParentHeight(ch);
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != null) {
                    child.measure(MeasureSpec.makeMeasureSpec((int) mResultBean.getChildWidth(), MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec((int) mResultBean.getChildHeight(), MeasureSpec.EXACTLY));
                }
            }
        }

        /** 一定要测量 child ，否则 child 不显示；另外，测量 child 时，当 viewGroup 设置 padding 时，child 也会被加上 padding */
//        measureChildren(MeasureSpec.makeMeasureSpec((int) mResultBean.getChildWidth(), MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec((int) mResultBean.getChildHeight(), MeasureSpec.EXACTLY));
        if (mResultBean != null) {
            int widthMeasure = (int) (mResultBean.getParentWidth() + getPaddingLeft() + getPaddingRight());
            int heightMeasure = (int) (mResultBean.getParentHeight() + getPaddingTop() + getPaddingBottom());
            setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : widthMeasure, heightMode == MeasureSpec.EXACTLY ? heightSize : heightMeasure);
        } else {
            setMeasuredDimension(widthSize, heightMode == MeasureSpec.EXACTLY ? heightSize : getPaddingTop() + getPaddingBottom());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mAdapter == null || getChildCount() == 0 || mResultBean == null) {
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            int[] position = mAutoGridHelper.findPosition(mResultBean, i);
            int left = (int) (getPaddingLeft() + position[1] * (mResultBean.getChildWidth() + mGridHspace));
            int top = (int) (getPaddingTop() + position[0] * (mResultBean.getChildHeight() + mGridVspqce));
            int right = (int) (left + mResultBean.getChildWidth());
            int bottom = (int) (top + mResultBean.getChildHeight());
            View child = getChildAt(i);
            child.layout(left, top, right, bottom);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setGridMode(int gridType) {
        this.mGridMode = gridType;
        requestLayout();
    }

    public int getGridMode() {
        return mGridMode;
    }

    public void setGridRow(int gridRow) {
        this.mGridRow = gridRow;
        requestLayout();
    }

    public int getGridRow() {
        return mGridRow;
    }

    public void setGridColumn(int gridColumn) {
        this.mGridColumn = gridColumn;
        requestLayout();
    }

    public int getGridColumn() {
        return mGridColumn;
    }

    public void setGridHeight(float gridHeight) {
        this.mGridHeight = gridHeight;
        requestLayout();
    }

    public float getGridHeight() {
        return mGridHeight;
    }

    public void setGridHspace(float gridHspace) {
        this.mGridHspace = gridHspace;
        requestLayout();
    }

    public float getGridHspace() {
        return mGridHspace;
    }

    public void setGridVspqce(float gridVspqce) {
        this.mGridVspqce = gridVspqce;
        requestLayout();
    }

    public float getGridVspace() {
        return mGridVspqce;
    }

    public void setGridOneWpercent(float gridOneWpercent) {
        this.mGridOneWidthPercent = gridOneWpercent;
        requestLayout();
    }

    public float getGridOneWpercent() {
        return mGridOneWidthPercent;
    }

    public void setGridOneHpercent(float gridOneHpercent) {
        this.mGridOneHeightPercent = gridOneHpercent;
        requestLayout();
    }

    public float getGridOneHpercent() {
        return mGridOneHeightPercent;
    }


    private class AdapterObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
        }
    }
}

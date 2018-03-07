package com.liyi.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.liyi.grid.model.GridHelper;
import com.liyi.grid.model.GridParamBean;
import com.liyi.grid.model.GridResultBean;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AutoGridView extends ViewGroup {
    /**
     * 默认值
     */
    // 默认网格图模式
    public static final int DEF_GRID_MODE = GridConfig.GRID_NINE;
    // 默认网格行数
    public static final int DEF_GRID_ROW = 3;
    // 默认网络列数
    public static final int DEF_GRID_COLUMN = 3;
    // 默认网格的高度
    public static final int DEF_GRID_HEIGHT = GridConfig.INVALID_VAL;
    // 默认网格的横向间距
    public static final int DEF_GRID_HSPACE = 10;
    // 默认网格的纵向间距
    public static final int DEF_GRID_VSPACE = 10;
    // 网格图中，当只有一个 itemView 时，itemView 的宽与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    // 此处默认 DEF_GRID_ONE_WPERCENT 为无效值，即 itemView 的宽度自适应
    public static final float DEF_GRID_ONE_WPERCENT = GridConfig.INVALID_VAL;
    // 网格图中，当只有一个 itemView 时，itemView 的高与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    public static final float DEF_GRID_ONE_HPERCENT = GridConfig.INVALID_VAL;

    private int mGridMode;
    private int mGridRow;
    private int mGridColumn;
    private float mGridHeight;
    private float mGridHspace;
    private float mGridVspqce;
    private float mGridOneWidthPercent;
    private float mGridOneHeightPercent;

    private GridHelper mGridHelper;
    private GridParamBean mGPBean;
    // GridHelper 中计算结果后，返回的结果类
    private GridResultBean mGRBean;

    private BaseAdapter mAdapter;
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    // 存储 itemView ，用于复用
    private HashMap<String, List<SoftReference<View>>> mRecycleBin;

    public AutoGridView(Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }

    public AutoGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public AutoGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initParams();
        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.AutoGridView);
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

        mGridHelper = new GridHelper();
        mRecycleBin = new HashMap<String, List<SoftReference<View>>>();
    }

    public void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;
        removeAllViews();
        if (mAdapter != null && mAdapter.getCount() > 0) {
            addItemView();
        }
    }

    private void addItemView() {
        if (mRecycleBin.size() > 0) {
            mRecycleBin.clear();
        }
        int itemCount = mAdapter.getCount();
        int maxCount = mGridRow * mGridColumn;
        int childCount = itemCount > maxCount ? maxCount : itemCount;
        for (int i = 0; i < childCount; i++) {
            View itemView = mAdapter.getView(i, null, this);
            /** 一定要有 LayoutParams ，addView 的时候，（ itemView 本身的 xml 的 width 和 height 设置的值，都是没效果的（变成 wrap_content ），
             * 可能会出现控件的宽高显示偏差，所以必须设置此句）*/
            itemView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            String key = mGridMode + "+" + mAdapter.getItemViewType(i);
            List<SoftReference<View>> views = mRecycleBin.get(key);
            views = (views != null ? views : new ArrayList<SoftReference<View>>());
            views.add(new SoftReference<View>(itemView));
            mRecycleBin.put(key, views);
            addItemClickListener(itemView, i);
            addView(itemView);
        }
    }

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
     * 刷新数据
     */
    public void notifyChanged() {
        if (mAdapter != null) {
            int itemCount = mAdapter.getCount();
            removeAllViews();
            if (itemCount == 0) {
                mRecycleBin.clear();
                return;
            }
            for (int i = 0; i < itemCount; i++) {
                String key = mGridMode + "+" + mAdapter.getItemViewType(i);
                // 从回收站中取出 key 类型的 view 列表
                List<SoftReference<View>> views = mRecycleBin.get(key);
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
                        mRecycleBin.put(key, views);
                        addItemClickListener(itemView, i);
                        addView(itemView);
                    }
                }
                // 如果没有 key 类型的 view 存在，则新建 itemView
                else {
                    View itemView = mAdapter.getView(i, null, this);
                    views = (views != null ? views : new ArrayList<SoftReference<View>>());
                    views.add(new SoftReference<View>(itemView));
                    mRecycleBin.put(key, views);
                    addItemClickListener(itemView, i);
                    addView(itemView);
                }
            }
            /** 将多余的没有用到的 itemView 移除 */
            for (String key : mRecycleBin.keySet()) {
                List<SoftReference<View>> views = mRecycleBin.get(key);
                if (views != null && views.size() > 0) {
                    List<SoftReference<View>> temp = new ArrayList<SoftReference<View>>();
                    for (SoftReference<View> softReference : views) {
                        if (softReference != null && softReference.get() != null) {
                            if (softReference.get().getParent() == null) {
                                temp.add(softReference);
                            }
                        }
                    }
                    views.remove(temp);
                    mRecycleBin.put(key, views);
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
        mGPBean = new GridParamBean();
        mGPBean.setGridType(mGridMode)
                .setGridRow(mGridRow)
                .setGridColumn(mGridColumn)
                .setGridHeight(mGridHeight)
                .setGridHspace(mGridHspace)
                .setGridVspqce(mGridVspqce)
                .setGridOneWper(mGridOneWidthPercent)
                .setGridOneHper(mGridOneHeightPercent)
                .setItemCount(childCount)
                .setMaxWidth(width);
        if (mGridHelper == null) {
            mGridHelper = new GridHelper();
        }
        mGRBean = mGridHelper.calculateSize(mGPBean);
        // 如果只有一个 child ，且当前模式为九宫格模式
        if (childCount == 1 && mGridMode == GridConfig.GRID_NINE) {
            View child = getChildAt(0);
            int childWms, childHms;
            // 宽度自适应
            if (mGridOneWidthPercent == GridConfig.INVALID_VAL) {
                childWms = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
            } else {
                childWms = MeasureSpec.makeMeasureSpec((int) mGRBean.getChildWidth(), MeasureSpec.EXACTLY);
            }
            // 高度自适应
            if (mGridOneHeightPercent == GridConfig.INVALID_VAL) {
                childHms = MeasureSpec.makeMeasureSpec((int) mGRBean.getChildHeight(), MeasureSpec.UNSPECIFIED);
            } else {
                childHms = MeasureSpec.makeMeasureSpec((int) mGRBean.getChildHeight(), MeasureSpec.EXACTLY);
            }
            child.measure(childWms, childHms);
//            child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
//                    MeasureSpec.makeMeasureSpec((int) mGRBean.getChildHeight(), MeasureSpec.EXACTLY));
            int cw = child.getMeasuredWidth() == 0 ? width : child.getMeasuredWidth();
            int ch = child.getMeasuredHeight() == 0 ? (int) (0.6 * width) : child.getMeasuredHeight();
            mGRBean.setChildWidth(cw);
            mGRBean.setChildHeight(ch);
            mGRBean.setParentWidth(cw);
            mGRBean.setParentHeight(ch);
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != null) {
                    child.measure(MeasureSpec.makeMeasureSpec((int) mGRBean.getChildWidth(), MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec((int) mGRBean.getChildHeight(), MeasureSpec.EXACTLY));
                }
            }
        }

        /** 一定要测量 child ，否则 child 不显示；另外，测量 child 时，当 viewGroup 设置 padding 时，child 也会被加上 padding */
//        measureChildren(MeasureSpec.makeMeasureSpec((int) mGRBean.getChildWidth(), MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec((int) mGRBean.getChildHeight(), MeasureSpec.EXACTLY));
        if (mGRBean != null) {
            int widthMeasure = (int) (mGRBean.getParentWidth() + getPaddingLeft() + getPaddingRight());
            int heightMeasure = (int) (mGRBean.getParentHeight() + getPaddingTop() + getPaddingBottom());
            setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : widthMeasure, heightMode == MeasureSpec.EXACTLY ? heightSize : heightMeasure);
        } else {
            setMeasuredDimension(widthSize, heightMode == MeasureSpec.EXACTLY ? heightSize : getPaddingTop() + getPaddingBottom());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mAdapter == null || getChildCount() == 0 || mGRBean == null) {
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            int[] position = mGridHelper.findPosition(mGRBean, i);
            int left = (int) (getPaddingLeft() + position[1] * (mGRBean.getChildWidth() + mGridHspace));
            int top = (int) (getPaddingTop() + position[0] * (mGRBean.getChildHeight() + mGridVspqce));
            int right = (int) (left + mGRBean.getChildWidth());
            int bottom = (int) (top + mGRBean.getChildHeight());
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
}

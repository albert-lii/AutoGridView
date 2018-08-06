package com.liyi.grid;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.liyi.grid.adapter.BaseAutoGridAdapter;
import com.liyi.grid.model.SizeHelper;
import com.liyi.grid.model.SizeInfo;
import com.liyi.grid.model.SizeParam;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AutoGridView extends ViewGroup {
    /**
     * 默认值
     */
    // 无效的值
    private static final int INVALID_VAL = -1;
    // 默认网格图模式
    private static final int DEF_MODE = AutoGridMode.GRID_NINE;
    // 默认的 item 最大行数
    private static final int DEF_ROW = 3;
    // 默认的 item 最大列数
    private static final int DEF_COLUMN = 3;
    // 默认的 item 高度
    public static final int DEF_ITEM_HEIGHT = INVALID_VAL;
    // 默认的 item 横向间距
    private static final int DEF_HORIZONTAL_SPACE = 10;
    //  默认的 item 纵向间距
    private static final int DEF_VERTICAL_SPACE = 10;
    // 九宫格模式时，当只有一个 item 时，item 的宽与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    // 此处默认 DEF_SINGLE_WIDTH_PERCENT 为无效值，即 item 的宽度自适应
    private static final float DEF_SINGLE_WIDTH_PERCENT = INVALID_VAL;
    // 九宫格模式时，当只有一个 item 时，item 的高与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    private static final float DEF_SINGLE_HEIGHT_PERCENT = INVALID_VAL;

    /**
     * 变量
     */
    // 网格图的模式
    private int mMode;
    // item 的行数
    private int mRow;
    // item 的列数
    private int mColumn;
    // item 的高度
    private float mItemHeight;
    // item 之间的横向间距
    private float mHorizontalSpace;
    // item 之间的纵向间距
    private float mVerticalSpace;
    // 九宫格模式时，当只有一个 item 时，item 的宽与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    private float mNineSingleWidthPer;
    // 九宫格模式时，当只有一个 item 时，item 的高与父容器可用总宽度（即去除左右 padding 后的 width ）的比
    private float mNineSingleHeightPer;

    // 网格数据计算辅助类
    private SizeHelper mSizeHelper;
    private SizeParam mSizeParam;
    // SizeHelper 中计算结果后，返回的结果类
    private SizeInfo mSizeInfo;
    // 缓存 item 的 viewType
    private ArrayList<String> mCacheTypeBin;
    // 缓存器，存储 item，用于复用
    private HashMap<String, List<SoftReference<View>>> mCacheBin;
    private AdapterObserver mDataSetObserver;
    private BaseAutoGridAdapter mAdapter;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

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
        initData();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoGridView);
            if (a != null) {
                mMode = a.getInt(R.styleable.AutoGridView_agv_mode, mMode);
                mRow = a.getInt(R.styleable.AutoGridView_agv_row, mRow);
                mColumn = a.getInt(R.styleable.AutoGridView_agv_column, mColumn);
                mItemHeight = a.getDimension(R.styleable.AutoGridView_agv_item_height, mItemHeight);
                mHorizontalSpace = a.getDimension(R.styleable.AutoGridView_agv_horizontal_space, mHorizontalSpace);
                mVerticalSpace = a.getDimension(R.styleable.AutoGridView_agv_vertical_space, mVerticalSpace);
                mNineSingleWidthPer = a.getFloat(R.styleable.AutoGridView_agv_nines_widthper, mNineSingleWidthPer);
                mNineSingleHeightPer = a.getFloat(R.styleable.AutoGridView_agv_nines_heightper, mNineSingleHeightPer);
                a.recycle();
            }
        }
    }

    private void initData() {
        mMode = DEF_MODE;
        mRow = DEF_ROW;
        mColumn = DEF_COLUMN;
        mItemHeight = DEF_ITEM_HEIGHT;
        mHorizontalSpace = DEF_HORIZONTAL_SPACE;
        mVerticalSpace = DEF_VERTICAL_SPACE;
        mNineSingleWidthPer = DEF_SINGLE_WIDTH_PERCENT;
        mNineSingleHeightPer = DEF_SINGLE_HEIGHT_PERCENT;

        mSizeHelper = new SizeHelper();
    }

    public void setAdapter(BaseAutoGridAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            // 删除已经存在的观察者
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        removeAllViews();
        // 清空缓存器
        if (mCacheTypeBin != null) {
            mCacheTypeBin.clear();
        }
        if (mCacheBin != null) {
            mCacheBin.clear();
        }
        // 重置 adapter
        mAdapter = adapter;
        if (mAdapter != null) {
            mDataSetObserver = new AdapterObserver();
            // 注册观察者
            mAdapter.registerDataSetObserver(mDataSetObserver);
            if (mAdapter.getItemCount() > 0) {
                addItemViews();
            }
        }
    }

    /**
     * 通知刷新
     */
    private void notifyChanged() {
        if (mAdapter != null) {
            int newSize = mAdapter.getItemCount();
            if (newSize == 0) {
                if (mCacheTypeBin != null) {
                    mCacheTypeBin.clear();
                }
                if (mCacheBin != null) {
                    mCacheBin.clear();
                }
            } else {
                if (mCacheBin == null) mCacheBin = new HashMap<>();
                for (int i = 0, len = mCacheTypeBin.size(); i < len; i++) {
                    String key = mCacheTypeBin.get(i);
                    List<SoftReference<View>> views = mCacheBin.get(key);
                    views = (views != null ? views : new ArrayList<SoftReference<View>>());
                    views.add(new SoftReference<View>(getChildAt(i)));
                    mCacheBin.put(key, views);
                }
            }
            removeAllViews();
            mCacheTypeBin.clear();
            for (int i = 0; i < newSize; i++) {
                String key = mMode + "_" + mAdapter.getItemViewType(i);
                mCacheTypeBin.add(key);
                // 从缓存器中取出 key 类型的 view 列表
                List<SoftReference<View>> views = mCacheBin.get(key);
                /** 此处做简单的缓存复用处理 */
                if (views != null && views.size() > 0) {
                    boolean isAddItem = false;
                    for (SoftReference<View> softReference : views) {
                        if (softReference != null && softReference.get() != null) {
                            View itemView = softReference.get();
                            // 如果列表中的 item 还没有父容器，则复用该 item
                            if (itemView.getParent() == null) {
                                addItemClickListener(itemView, i);
                                addItemLongClickListener(itemView, i);
                                addView(mAdapter.getView(i, itemView, this), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                                isAddItem = true;
                                views.remove(itemView);
                                mCacheBin.put(key, views);
                                break;
                            }
                        }
                    }
                    // 如果从 views 中未找到匹配的 item ，则新建 item
                    if (!isAddItem) {
                        View itemView = mAdapter.getView(i, null, this);
                        // 将新建的 item 存入列表中
                        views.add(new SoftReference<View>(itemView));
                        addItemClickListener(itemView, i);
                        addItemLongClickListener(itemView, i);
                        addView(itemView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    }
                }
                // 如果没有 key 类型的 view 存在，则新建 item
                else {
                    View itemView = mAdapter.getView(i, null, this);
                    views = (views != null ? views : new ArrayList<SoftReference<View>>());
                    views.add(new SoftReference<View>(itemView));
                    addItemClickListener(itemView, i);
                    addItemLongClickListener(itemView, i);
                    addView(itemView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                }
            }
            if (mCacheBin != null && mCacheBin.size() > 0) {
                mCacheBin.clear();
            }
        }
        requestLayout();
        invalidate();
    }

    /**
     * 添加 item
     */
    private void addItemViews() {
        int totalCount = mAdapter.getItemCount();
        int maxCount = mRow * mColumn;
        int childCount = totalCount > maxCount ? maxCount : totalCount;
        if (mCacheTypeBin == null) mCacheTypeBin = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View itemView = mAdapter.getView(i, null, this);
            String key = mMode + "_" + mAdapter.getItemViewType(i);
            mCacheTypeBin.add(key);
            addItemClickListener(itemView, i);
            addItemLongClickListener(itemView, i);
            /** 一定要有 LayoutParams ，addView 的时候，item 在 xml 中的 width 和 height 设置的值，都是没效果的（变成 wrap_content ），
             * 可能会出现控件的宽高显示偏差，所以必须设置此句）*/
            addView(itemView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 设置 item 的点击监听事件
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
     * 设置 item 的长按监听事件
     *
     * @param view
     * @param position
     */
    private void addItemLongClickListener(View view, final int position) {
        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemLongClickListener != null) {
                    return mItemLongClickListener.onItemLongClick(position, v);
                }
                return false;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (mAdapter == null || getChildCount() == 0) {
            setMeasuredDimension(measureWidth, heightMode == MeasureSpec.EXACTLY ? measureHeight : getPaddingTop() + getPaddingBottom());
            return;
        }
        int maxWidth = measureWidth - getPaddingLeft() - getPaddingRight();
        int childCount = getChildCount();
        mSizeParam = new SizeParam();
        mSizeParam.setMode(mMode)
                .setRow(mRow)
                .setColumn(mColumn)
                .setMaxWidth(maxWidth)
                .setItemCount(childCount)
                .setItemHeight(mItemHeight)
                .setHorizontalSpace(mHorizontalSpace)
                .setVerticalSpace(mVerticalSpace)
                .setSingleWidthPer(mNineSingleWidthPer)
                .setSingleHeightPer(mNineSingleHeightPer);
        if (mSizeHelper == null) {
            mSizeHelper = new SizeHelper();
        }
        mSizeInfo = mSizeHelper.calculateSize(mSizeParam);
        // 如果只有一个 child ，且当前模式为九宫格模式
        if (childCount == 1 && mMode == AutoGridMode.GRID_NINE) {
            View child = getChildAt(0);
            int childWms, childHms;
            // 宽度自适应
            if (mNineSingleWidthPer == INVALID_VAL) {
                childWms = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);
            } else {
                childWms = MeasureSpec.makeMeasureSpec((int) mSizeInfo.getChildWidth(), MeasureSpec.EXACTLY);
            }
            // 高度自适应
            if (mNineSingleHeightPer == INVALID_VAL) {
                childHms = MeasureSpec.makeMeasureSpec((int) mSizeInfo.getChildHeight(), MeasureSpec.UNSPECIFIED);
            } else {
                childHms = MeasureSpec.makeMeasureSpec((int) mSizeInfo.getChildHeight(), MeasureSpec.EXACTLY);
            }
            child.measure(childWms, childHms);
//            child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
//                    MeasureSpec.makeMeasureSpec((int) mSizeInfo.getChildHeight(), MeasureSpec.EXACTLY));
            int cw = child.getMeasuredWidth() == 0 ? maxWidth : child.getMeasuredWidth();
            int ch = child.getMeasuredHeight() == 0 ? (int) (0.6 * maxWidth) : child.getMeasuredHeight();
            mSizeInfo.setChildWidth(cw);
            mSizeInfo.setChildHeight(ch);
            mSizeInfo.setParentWidth(cw);
            mSizeInfo.setParentHeight(ch);
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != null) {
                    child.measure(MeasureSpec.makeMeasureSpec((int) mSizeInfo.getChildWidth(), MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec((int) mSizeInfo.getChildHeight(), MeasureSpec.EXACTLY));
                }
            }
        }

        /** 一定要测量 child ，否则 child 不显示；另外，测量 child 时，当 viewGroup 设置 padding 时，child 也会被加上 padding */
//        measureChildren(MeasureSpec.makeMeasureSpec((int) mSizeInfo.getChildWidth(), MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec((int) mSizeInfo.getChildHeight(), MeasureSpec.EXACTLY));
        if (mSizeInfo != null) {
            int widthMeasure = (int) (mSizeInfo.getParentWidth() + getPaddingLeft() + getPaddingRight());
            int heightMeasure = (int) (mSizeInfo.getParentHeight() + getPaddingTop() + getPaddingBottom());
            setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? measureWidth : widthMeasure, heightMode == MeasureSpec.EXACTLY ? measureHeight : heightMeasure);
        } else {
            setMeasuredDimension(measureWidth, heightMode == MeasureSpec.EXACTLY ? measureHeight : getPaddingTop() + getPaddingBottom());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mAdapter == null || getChildCount() == 0 || mSizeInfo == null) {
            return;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            int[] position = mSizeHelper.findPosition(mSizeInfo, i);
            int left = (int) (getPaddingLeft() + position[1] * (mSizeInfo.getChildWidth() + mHorizontalSpace));
            int top = (int) (getPaddingTop() + position[0] * (mSizeInfo.getChildHeight() + mVerticalSpace));
            int right = (int) (left + mSizeInfo.getChildWidth());
            int bottom = (int) (top + mSizeInfo.getChildHeight());
            View child = getChildAt(i);
            child.layout(left, top, right, bottom);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }

    public void setMode(@AutoGridMode int mode) {
        this.mMode = mode;
    }

    @AutoGridMode
    public int getMode() {
        return mMode;
    }

    public void setRow(int row) {
        this.mRow = row;
    }

    public int getRow() {
        return mRow;
    }

    public void setColumn(int column) {
        this.mColumn = column;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setItemHeight(float itemHeight) {
        this.mItemHeight = itemHeight;
    }

    public float getItemHeight() {
        return mItemHeight;
    }

    public void setHorizontalSpace(float horizontalSpace) {
        this.mHorizontalSpace = horizontalSpace;
    }

    public float getHorizontalSpace() {
        return mHorizontalSpace;
    }

    public void setVerticalSpace(float verticalSpace) {
        this.mVerticalSpace = verticalSpace;
    }

    public float getVerticalSpace() {
        return mVerticalSpace;
    }

    public void setNineSingleWidthPercent(@FloatRange(from = 0f, to = 1f) float nineSingleWidthPercent) {
        this.mNineSingleWidthPer = nineSingleWidthPercent;
    }

    public float getNineSingleWidthPercent() {
        return mNineSingleWidthPer;
    }

    public void setNineSingleHeightPercent(@FloatRange(from = 0f, to = 1f) float nineSingleHeightPercent) {
        this.mNineSingleHeightPer = nineSingleHeightPercent;
    }

    public float getNineSingleHeightPercent() {
        return mNineSingleHeightPer;
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

    @Override
    protected void onDetachedFromWindow() {
        if (mAdapter != null) {
            if (mDataSetObserver != null) {
                mAdapter.unregisterDataSetObserver(mDataSetObserver);
            }
            mAdapter = null;
        }
        if (mCacheTypeBin != null) {
            mCacheTypeBin.clear();
            mCacheTypeBin = null;
        }
        if (mCacheBin != null) {
            mCacheBin.clear();
            mCacheBin = null;
        }
        mItemClickListener = null;
        mItemLongClickListener = null;
        super.onDetachedFromWindow();
    }
}

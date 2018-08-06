package com.liyi.grid.adapter;

import android.widget.ImageView;

import com.liyi.grid.AutoGridMode;
import com.liyi.grid.R;

import java.util.List;

/**
 * 对 QuickAutoGridAdapter 封装后的简用适配器
 */
public class SimpleAutoGridAdapter<T, VH extends BaseAutoGridHolder> extends QuickAutoGridAdapter<T, VH> {
    // item 类型
    private final int ITEM_TYPE_SINGLE = 0;
    private final int ITEM_TYPE_NORMAL = 1;

    private int mGridMode;
    private ImageLoader mImageLoader;

    public SimpleAutoGridAdapter() {
        init();
    }

    public SimpleAutoGridAdapter(List<T> list) {
        setData(list);
        init();
    }

    private void init() {
        addItemType(ITEM_TYPE_SINGLE, R.layout.auto_grid_item_simple_pic_single);
        addItemType(ITEM_TYPE_NORMAL, R.layout.auto_grid_item_simple_pic);
        mGridMode = AutoGridMode.GRID_NINE;
    }

    public void setMode(@AutoGridMode int mode) {
        this.mGridMode = mode;
    }

    public void setImageLoader(ImageLoader loader) {
        this.mImageLoader = loader;
    }

    @Override
    public int onHandleViewType(int position) {
        int viewType;
        if (mGridMode == AutoGridMode.GRID_NINE) {
            if (getItemCount() == 1) {
                viewType = ITEM_TYPE_SINGLE;
            } else {
                viewType = ITEM_TYPE_NORMAL;
            }
        } else {
            viewType = ITEM_TYPE_NORMAL;
        }
        return viewType;
    }

    @Override
    public void onHandleViewHolder(VH holder, int position, T item) {
        if (mImageLoader != null) {
            mImageLoader.onLoadImage(position, item, holder.getImageView(R.id.iv_auto_grid_item_simple_pic));
        }
    }

    public interface ImageLoader<T> {
        /**
         * 加载图片
         */
        void onLoadImage(int position, T item, ImageView imageView);
    }
}

package com.liyi.grid.adapter;

import android.support.annotation.IntRange;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liyi.grid.AutoGridConfig;
import com.liyi.grid.R;

import java.util.List;

/**
 * 简单的 AutoGridView 的适配器
 */
public class SimpleAutoGridAdapter<T> extends BaseAutoGridAdapter {
    private final int NORMAL = 0;
    private final int SINGLE = 1;
    private int mMode;
    private List<T> mImageList;
    private ImageLoader mImageLoader;

    public SimpleAutoGridAdapter() {
        mMode = AutoGridConfig.GRID_NORMAL;
    }

    /**
     * 设置网格图的模式
     */
    public void setMode(@IntRange(from = AutoGridConfig.GRID_NINE, to = AutoGridConfig.GRID_NORMAL) int mode) {
        this.mMode = mode;
    }

    /**
     * 设置数据源
     */
    public void setSource(List<T> list) {
        this.mImageList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (mImageList != null && mImageList.size() > 0) {
            int size = mImageList.size();
            if (mMode == AutoGridConfig.GRID_NINE) {
                if (size == 1) {
                    return SINGLE;
                } else {
                    return NORMAL;
                }
            } else if (mMode == AutoGridConfig.GRID_NORMAL) {
                return NORMAL;
            }
        }
        return NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleHolder normalHolder = null, singleHolder = null;
        int viewType = getItemViewType(position);
        if (convertView == null) {
            if (viewType == NORMAL) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.auto_grid_item_simple_pic, null);
                normalHolder = new SimpleHolder();
                normalHolder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_auto_grid_item_simple_pic);
                convertView.setTag(normalHolder);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.auto_grid_item_simple_pic_single, null);
                singleHolder = new SimpleHolder();
                singleHolder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_auto_grid_item_simple_pic_single);
                convertView.setTag(singleHolder);
            }
        } else {
            if (viewType == NORMAL) {
                normalHolder = (SimpleHolder) convertView.getTag();
            } else {
                singleHolder = (SimpleHolder) convertView.getTag();
            }
        }
        if (viewType == NORMAL) {
            if (mImageLoader != null) {
                mImageLoader.onLoadImage(position, mImageList.get(position), normalHolder.iv_pic, viewType);
            }
        } else {
            if (mImageLoader != null) {
                mImageLoader.onLoadImage(position, mImageList.get(position), singleHolder.iv_pic, viewType);
            }
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mImageList != null ? mImageList.size() : 0;
    }

    private class SimpleHolder {
        private ImageView iv_pic;
    }

    public void setImageLoader(ImageLoader loader) {
        this.mImageLoader = loader;
    }

    public interface ImageLoader {
        /**
         * 加载图片
         *
         * @param position
         * @param source
         * @param view
         * @param viewType
         */
        void onLoadImage(int position, Object source, ImageView view, int viewType);
    }
}

package com.liyi.grid.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * AutoGridView 的适配器基类
 */
public abstract class BaseAutoGridAdapter<VH extends BaseAutoGridHolder>  {
    // 被观察者，用来注册观察者
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    /**
     * 创建 ViewHolder
     */
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定 item 的 ViewHolder
     */
    public abstract void onBindViewHolder(VH holder, int position);

    /**
     * 获取 item 的数量
     */
    public abstract int getItemCount();

    public int getItemViewType(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        VH holder = null;
        if (convertView == null) {
            holder = onCreateViewHolder(parent, viewType);
            convertView = holder.getConvertView();
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }
        holder.setViewType(viewType);
        onBindViewHolder(holder, position);
        return convertView;
    }


    /**
     * 注册观察者
     *
     * @param observer
     */
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    /**
     * 删除已经注册过的观察者
     *
     * @param observer
     */
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * 通知刷新
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }
}

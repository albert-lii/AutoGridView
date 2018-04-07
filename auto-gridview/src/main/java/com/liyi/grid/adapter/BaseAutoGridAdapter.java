package com.liyi.grid.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * AutoGridView 的适配器
 */
public abstract class BaseAutoGridAdapter {
    // 被观察者，用来注册观察者
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

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

    public int getItemViewType(int position) {
        return 0;
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);

    public abstract int getCount();
}

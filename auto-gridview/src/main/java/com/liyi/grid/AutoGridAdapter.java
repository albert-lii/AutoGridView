package com.liyi.grid;

import android.view.View;
import android.view.ViewGroup;

/**
 * AutoGridView 的适配器
 */
public abstract class AutoGridAdapter {
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public abstract int getCount();
}

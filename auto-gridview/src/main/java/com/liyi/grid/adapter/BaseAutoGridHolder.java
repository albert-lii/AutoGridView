package com.liyi.grid.adapter;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class BaseAutoGridHolder {
    private View convertView;

    public BaseAutoGridHolder(final View convertView) {
        this.convertView = convertView;
    }

    public View getConvertView() {
        return convertView;
    }

    public <T extends View> T findViewById(@IdRes int viewId) {
        T view = (T) convertView.findViewById(viewId);
        return view;
    }

    public ImageView getImageView(@IdRes int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(@IdRes int viewId) {
        return findViewById(viewId);
    }
}

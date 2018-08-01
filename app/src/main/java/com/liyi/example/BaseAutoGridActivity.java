package com.liyi.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liyi.grid.AutoGridView;
import com.liyi.grid.adapter.BaseAutoGridAdapter;
import com.liyi.grid.adapter.BaseAutoGridHolder;

import java.util.List;

public class BaseAutoGridActivity extends Activity {
    private AutoGridView autoGridVi;
    private BaseAdapter mAdapter;

    private List<Integer> mImageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_auto_grid);
        initView();
    }

    private void initView() {
        autoGridVi = findViewById(R.id.autoGridVi);

        mImageList = Utils.getImageList(5);
        mAdapter = new BaseAdapter();
        autoGridVi.setAdapter(mAdapter);
    }

    private class BaseAdapter extends BaseAutoGridAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BaseAutoGridHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.auto_grid_item_simple_pic, null);
                holder = new BaseAutoGridHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (BaseAutoGridHolder) convertView.getTag();
            }
            ImageView imageView = holder.getImageView(R.id.iv_auto_grid_item_simple_pic);
            imageView.setImageResource(mImageList.get(position));
            return convertView;
        }

        @Override
        public int getCount() {
            return mImageList != null ? mImageList.size() : 0;
        }
    }
}

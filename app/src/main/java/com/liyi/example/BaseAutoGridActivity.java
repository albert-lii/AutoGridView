package com.liyi.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        public BaseAutoGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auto_grid_item_simple_pic, null);
            return new BaseAutoGridHolder(view);
        }

        @Override
        public void onBindViewHolder(BaseAutoGridHolder holder, int position) {
            holder.getImageView(R.id.iv_auto_grid_item_simple_pic).setImageResource(mImageList.get(position));
        }

        @Override
        public int getItemCount() {
            return mImageList != null ? mImageList.size() : 0;
        }
    }
}

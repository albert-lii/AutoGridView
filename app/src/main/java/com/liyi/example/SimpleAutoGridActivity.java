package com.liyi.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liyi.grid.AutoGridMode;
import com.liyi.grid.AutoGridView;
import com.liyi.grid.adapter.BaseAutoGridHolder;
import com.liyi.grid.adapter.SimpleAutoGridAdapter;

import java.util.List;

public class SimpleAutoGridActivity extends Activity {
    private TextView tv_mode;
    private AutoGridView autoGridVi;
    private SimpleAutoGridAdapter mAdapter;

    private List<Integer> mImageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_auto_grid);
        initView();
    }

    private void initView() {
        autoGridVi = findViewById(R.id.autoGridVi);
        tv_mode = findViewById(R.id.tv_mode);

        tv_mode.setText("当前是九宫格模式");
        mImageList = Utils.getImageList(1);
        mAdapter = new SimpleAutoGridAdapter<Integer, BaseAutoGridHolder>(mImageList);
        mAdapter.setImageLoader(new SimpleAutoGridAdapter.ImageLoader<Integer>() {
            @Override
            public void onLoadImage(int position, Integer item, ImageView imageView) {
                imageView.setImageResource(item);
            }
        });
        autoGridVi.setAdapter(mAdapter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_nine:
                tv_mode.setText("当前是九宫格模式");
                mAdapter.setMode(AutoGridMode.GRID_NINE);
                autoGridVi.setMode(AutoGridMode.GRID_NINE);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_normal:
                tv_mode.setText("当前是普通网格模式");
                mAdapter.setMode(AutoGridMode.GRID_NORMAL);
                autoGridVi.setMode(AutoGridMode.GRID_NORMAL);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_add_pic:
                mImageList.add(R.drawable.img_demo_pic);
                mAdapter.updateData(mImageList);
                break;
            case R.id.btn_clear_pic:
                mImageList.clear();
                mAdapter.updateData(null);
                break;
            case R.id.btn_update_data1:
                mImageList = Utils.getImageList(1);
                mAdapter.updateData(mImageList);
                break;
            case R.id.btn_update_data2:
                mImageList = Utils.getImageList(4);
                mAdapter.updateData(mImageList);
                break;
            case R.id.btn_update_data3:
                mImageList = Utils.getImageList(5);
                mAdapter.updateData(mImageList);
                break;
            case R.id.btn_update_data4:
                mImageList = Utils.getImageList(9);
                mAdapter.updateData(mImageList);
                break;
        }
    }
}

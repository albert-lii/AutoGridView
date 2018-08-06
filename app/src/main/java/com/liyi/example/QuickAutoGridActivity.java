package com.liyi.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.liyi.grid.AutoGridView;
import com.liyi.grid.adapter.BaseAutoGridHolder;
import com.liyi.grid.adapter.QuickAutoGridAdapter;

import java.util.List;

public class QuickAutoGridActivity extends Activity {
    private AutoGridView autoGridVi;
    private QuickAdapter mAdapter;

    private List<Integer> mImageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_auto_grid);
        initView();
    }

    private void initView() {
        autoGridVi = findViewById(R.id.autoGridVi);

        mImageList = Utils.getImageList(9);
        mAdapter = new QuickAdapter(mImageList);
        autoGridVi.setAdapter(mAdapter);
    }

    private class QuickAdapter extends QuickAutoGridAdapter<Integer, BaseAutoGridHolder> {

        public QuickAdapter(List<Integer> list) {
            super();
            addItemType(0, R.layout.auto_grid_item_simple_pic);
            setData(list);
        }

        @Override
        public int onHandleViewType(int position) {
            return 0;
        }

        @Override
        public void onHandleViewHolder(BaseAutoGridHolder holder, int position, Integer item) {
            ImageView imageView = holder.getImageView(R.id.iv_auto_grid_item_simple_pic);
            imageView.setImageResource(item);
        }
    }
}

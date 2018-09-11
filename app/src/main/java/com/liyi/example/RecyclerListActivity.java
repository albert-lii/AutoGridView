package com.liyi.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liyi.grid.AutoGridView;
import com.liyi.grid.adapter.BaseAutoGridHolder;
import com.liyi.grid.adapter.SimpleAutoGridAdapter;


public class RecyclerListActivity extends Activity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SampleAdapter());
    }

    private class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ItemHolder> {

        @Override
        public SampleAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent,false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(SampleAdapter.ItemHolder holder, int position) {
            SimpleAutoGridAdapter adapter;
            if (position == 0) {
                adapter = new SimpleAutoGridAdapter<Integer, BaseAutoGridHolder>(Utils.getImageList(1));
            } else if (position == 1) {
                adapter = new SimpleAutoGridAdapter<Integer, BaseAutoGridHolder>(Utils.getImageList(4));
            } else {
                adapter = new SimpleAutoGridAdapter<Integer, BaseAutoGridHolder>(Utils.getImageList(6));
            }
            adapter.setImageLoader(new SimpleAutoGridAdapter.ImageLoader<Integer>() {
                @Override
                public void onLoadImage(int position, Integer item, ImageView imageView) {
                    imageView.setImageResource(item);
                }
            });
            holder.autoGridView.setAdapter(adapter);
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class ItemHolder extends RecyclerView.ViewHolder {
            private AutoGridView autoGridView;

            public ItemHolder(View itemView) {
                super(itemView);
                autoGridView = itemView.findViewById(R.id.autoGridVi);
            }
        }
    }
}

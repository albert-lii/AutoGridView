package com.liyi.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


public class MainAdapter extends BaseAdapter {
    private ArrayList<Integer> mList;
    private boolean isNineGrid;

    public void setData(ArrayList<Integer> list, boolean isNineGrid) {
        this.mList = list;
        this.isNineGrid = isNineGrid;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 1) {
            if (isNineGrid) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder = null;
        ItemHolder holder_first = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            if (type == 0) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_pic_first, null);
                holder_first = new ItemHolder();
                holder_first.iv_pic = (ImageView) convertView.findViewById(R.id.iv_item_grid_pic_first);
                convertView.setTag(holder_first);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_pic, null);
                holder = new ItemHolder();
                holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_item_grid_pic);
                convertView.setTag(holder);
            }
        } else {
            if (type == 0) {
                holder_first = (ItemHolder) convertView.getTag();
            } else {
                holder = (ItemHolder) convertView.getTag();
            }
        }
        if (type == 0) {
            holder_first.iv_pic.setImageResource(mList.get(position));
        } else {
            holder.iv_pic.setImageResource(mList.get(position));
        }
        return convertView;
    }

    private class ItemHolder {
        private ImageView iv_pic;
    }
}

package com.yyx.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected List<T> mDatas = new ArrayList<T>();
    protected Context mContext;
    protected LayoutInflater mInflater;

    public BaseAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void resetData(List<T> list) {
        if (list != null && list.size() != 0) {
            mDatas.clear();
            mDatas = list;
        }
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
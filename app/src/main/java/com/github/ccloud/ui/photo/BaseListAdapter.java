package com.github.ccloud.ui.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    private final List<T> dataList = new ArrayList<>();

    protected Context context;
    private LayoutInflater inflater;

    public BaseListAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(List<T> list) {
        dataList.addAll(list);
    }

    public void setDataList(List<T> list) {
        if (list != null && list.size() > 0) {
            dataList.clear();
            dataList.addAll(list);
            notifyDataSetChanged();
        }
    }
}

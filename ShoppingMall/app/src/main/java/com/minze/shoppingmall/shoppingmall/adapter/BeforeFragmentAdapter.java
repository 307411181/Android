package com.minze.shoppingmall.shoppingmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.bean.New;

import java.util.ArrayList;

/**
 * @创建者 张京
 * @创建时间 2016/9/7 10:45
 * @描述 ${TODO}
 */
public class BeforeFragmentAdapter extends BaseAdapter {

    private Context        mContext;
    private ArrayList<New> list;
    private boolean        check;

    public BeforeFragmentAdapter(Context context, ArrayList<New> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_before_fragment, null);


        return view;
    }
}

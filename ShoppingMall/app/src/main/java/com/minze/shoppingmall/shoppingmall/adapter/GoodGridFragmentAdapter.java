package com.minze.shoppingmall.shoppingmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minze.shoppingmall.shoppingmall.R;


/**
 * 商品列表GridView适配器
 * Created by Administrator on 2016/9/5.
 */

public class GoodGridFragmentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    ViewHolder holder;

    public GoodGridFragmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {

            convertView = mInflater.inflate(R.layout.fragment_goodsgrid_item, null);

            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.fragment_goodslist_imageview);
            holder.goodsName = (TextView) convertView.findViewById(R.id.fragment_goodslist_goodsname);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.fragment_goodslist_goodsprice);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView mImageView;
        TextView goodsName;
        TextView goodsPrice;
    }
}

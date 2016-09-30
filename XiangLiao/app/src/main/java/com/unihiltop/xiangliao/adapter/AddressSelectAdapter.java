package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.ReceiverAddress;

import java.util.List;

/**
 * 地址管理listview适配器
 * Created by yy on 2015/11/18.
 */
public class AddressSelectAdapter extends BaseAdapter {

    private Context context;

    private List<ReceiverAddress> list;

    public AddressSelectAdapter(Context context, List<ReceiverAddress> list){
        this.context=context;
        this.list=list;
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
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView = View.inflate(context, R.layout.listview_address_select_item, null);

            viewHolder.tvName = (TextView) convertView.findViewById(R.id.textView_name);
            viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.textView_phone);
            viewHolder.tvZipCode= (TextView) convertView.findViewById(R.id.textView_zip_code);
            viewHolder.tvAddress= (TextView) convertView.findViewById(R.id.textView_address);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText("收货人："+list.get(position).getContactPersonName());
        viewHolder.tvPhone.setText(list.get(position).getContactPersonMobile());
        viewHolder.tvZipCode.setText("邮编："+list.get(position).getPostCode());
        viewHolder.tvAddress.setText("收货地址"+list.get(position).getCity()+list.get(position).getCounty()+list.get(position).getDetailAddress());

        return convertView;
    }

    class ViewHolder{
        /**
         * 名字
         */
        TextView tvName;
        /**
         * 电话号码
         */
        TextView tvPhone;
        /**
         * 邮编
         */
        TextView tvZipCode;
        /**
         * 地址
         */
        TextView tvAddress;
    }
}

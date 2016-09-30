package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.TheBill;
import com.unihiltop.xiangliao.util.DateUtils;

import java.util.List;

/**
 * Created by yangyang on 2015/10/14.
 */
public class AdapterSearchResult extends BaseAdapter{
    private  Context context;
    private  List<TheBill> list;
   public AdapterSearchResult(Context context, List<TheBill> list){
        this.context = context;
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
        TheBill bill = list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null){
          viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_search_resulat,null);
            viewHolder.tvResulatPhone = (TextView) convertView.findViewById(R.id.textView_resulat_phone);
            viewHolder.tvResulatTime = (TextView) convertView.findViewById(R.id.textView_resulat_dialTime);
            viewHolder.tvResulatMoney = (TextView) convertView.findViewById(R.id.textView_resulat_hangUpTime);
            viewHolder.tvResulatCalled = (TextView) convertView.findViewById(R.id.textView_resulat_called);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvResulatPhone.setText(bill.callingNumber);
        viewHolder.tvResulatTime.setText(DateUtils.getDate(bill.dialTime));
        viewHolder.tvResulatCalled.setText(bill.calledNumber);
        viewHolder.tvResulatMoney.setText(DateUtils.getDate(bill.hangUpTime));
        return convertView;
    }
    private final class ViewHolder{
        TextView tvResulatPhone;//电话
        TextView tvResulatTime;//开始时间
        TextView tvResulatMoney;//结束时间
        TextView tvResulatCalled;//被打者电话
    }
}

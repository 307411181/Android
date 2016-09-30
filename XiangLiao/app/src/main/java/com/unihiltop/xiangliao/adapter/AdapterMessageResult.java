package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.SystemMessage;
import com.unihiltop.xiangliao.util.DateUtils;

import java.util.List;


/**
 * Created by yangyang on 2015/10/14.
 */
public class AdapterMessageResult extends BaseAdapter{
    private  Context context;
    private List<SystemMessage> list;
   public AdapterMessageResult(Context context, List<SystemMessage> list){
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
        SystemMessage bill = list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null){
          viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_message_resulat,null);
            viewHolder.textView_message = (TextView) convertView.findViewById(R.id.textView_message);
            viewHolder.textView_time = (TextView) convertView.findViewById(R.id.textView_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView_message.setText(bill.content);
        viewHolder.textView_time.setText(DateUtils.getDate(bill.publishTime));

        return convertView;
    }
    private final class ViewHolder{
        TextView textView_message;//信息
        TextView  textView_time;//时间
    }
}

package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.IconDetailArray;


/**
 * Created by yangyang on 2015/11/28.
 */
public class AnswerAdapter extends BaseAdapter {
    private Context context = null;
    private IconDetailArray arrayList = null;

    public AnswerAdapter(Context context, IconDetailArray arrayList){
        this.context = context;
        this.arrayList = arrayList;


    }
    @Override
    public int getCount() {
        return arrayList.count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_answer, null);
            viewHolder.tvAnswer = (TextView)convertView.findViewById(R.id.textView_answer);
        viewHolder.ivAnswer = (ImageView) convertView.findViewById(R.id.imageView_answer);


        Log.i("nameArray", arrayList.nameArray[position] + "");
        if (!TextUtils.isEmpty(arrayList.nameArray[position])){
            viewHolder.tvAnswer.setText(arrayList.nameArray[position]);
            viewHolder.tvAnswer.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvAnswer.setVisibility(View.GONE);
        }

        if (arrayList.iconArray[position] == 0){
            viewHolder.ivAnswer.setVisibility(View.GONE);
        }else{
            viewHolder.ivAnswer.setVisibility(View.VISIBLE);
            viewHolder.ivAnswer.setImageResource(arrayList.iconArray[position]);
        }


        return convertView;
    }
    final  class ViewHolder{

        TextView tvAnswer;
        ImageView ivAnswer;

    }

}

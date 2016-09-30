package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;

import java.util.ArrayList;

/**
 * Created by yangyang on 2015/11/28.
 */
public class ProblemAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<String> arrayList = null;
    public ProblemAdapter(Context context, ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_problem, null);
            viewHolder.tvProblem = (TextView)convertView.findViewById(R.id.textView_problem);
            convertView.setTag(viewHolder);

        viewHolder.tvProblem.setText(arrayList.get(position));
       // viewHolder.tvProblem.setText(arrayList.nameArray[position]);
        return convertView;
    }
    final  class ViewHolder{

        TextView tvProblem;

    }
}

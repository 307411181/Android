package com.unihiltop.xiangliao.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.IconDetailArray;

/**
 * yangyang
 */
public class AgreementAdapter extends BaseAdapter {
	private Context context = null;
	private IconDetailArray arrayList = null;
	
	public AgreementAdapter(Context context, IconDetailArray arrayList){
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
		
		final ViewHolder viewHolder= new ViewHolder();			
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_agreement, null);
		viewHolder.name = (TextView)convertView.findViewById(R.id.textView_question);
		viewHolder.number = (TextView)convertView.findViewById(R.id.textView_answer);
		convertView.setTag(viewHolder);
		viewHolder.name.setText(arrayList.nameArray[position]);
		viewHolder.number.setText(arrayList.noteArray[position]);
		return convertView;
	}
	
	final  class ViewHolder{
		TextView name;
		TextView number;		
	}
}

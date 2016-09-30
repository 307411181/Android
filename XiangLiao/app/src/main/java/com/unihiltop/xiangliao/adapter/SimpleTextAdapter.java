package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.unihiltop.xiangliao.R;

import java.util.Arrays;
import java.util.List;
/**
 * yangyang
 */
public class SimpleTextAdapter<T> extends BaseAdapter {
	
	private Context context = null;
	private List<T> texts = null;
	public SimpleTextAdapter(Context context, T[] texts){
		this.context = context;
		this.texts = Arrays.asList(texts);
	} 
	
	public SimpleTextAdapter(Context context, List<T> texts){
		this.context = context;
		this.texts = texts;
	}
	
	@Override
	public int getCount() {
		return texts.size();
	}

	@Override
	public T getItem(int position) {
		return texts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listview_item_text, null);
			viewHolder.tvText = (TextView) convertView.findViewById(R.id.textView_text);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvText.setText(texts.get(position).toString());
		return convertView;
	}

	private final class ViewHolder{
		public TextView tvText;
	}
}

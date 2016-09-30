package com.unihiltop.xiangliao.dialog;

import java.util.List;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.adapter.SimpleTextAdapter;

public class ListMenuDialog extends Dialog {
	private TextView tvTitle = null;
	private ListView listView = null;
	
	public ListMenuDialog(Context context) {
		super(context, R.style.dialog);
		initView();
	}
	

	private void initView() {
		setContentView(R.layout.dialog_list_menu);
		tvTitle = (TextView)findViewById(R.id.textView_title_dialog_list_menu);
		listView = (ListView)findViewById(R.id.listView_list);
	}

	public void setTitle(String title){
		tvTitle.setText(title);
	}
	public void hideTitle(){
		tvTitle.setVisibility(View.GONE);
		findViewById(R.id.textView_line).setVisibility(View.GONE);
	}
	
	public void setTitle(int resid){
		tvTitle.setText(resid);
	}

	/**
	 * 
	 * @param resid array id
	 */
	public void setMenuItems(int resid){
		listView.setAdapter( new SimpleTextAdapter<String>(getContext(), getContext().getResources().getStringArray(resid)));
	}
	
	public void setMenuItems(List<String> textLists){
		listView.setAdapter( new SimpleTextAdapter<String>(getContext(), textLists));
	}
	
	
	public void setMenuItems(String[] texts){
		listView.setAdapter(new SimpleTextAdapter<String>(getContext(), texts));
	}
	
	public void setMenuItems(ListAdapter adapter){
		listView.setAdapter(adapter);
	}
	
	public void setOnItemClickListener(OnItemClickListener l){
		listView.setOnItemClickListener(l);
	}
	
}

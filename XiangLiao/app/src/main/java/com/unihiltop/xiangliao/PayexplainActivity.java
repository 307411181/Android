package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.unihiltop.xiangliao.adapter.AgreementAdapter;
import com.unihiltop.xiangliao.bean.IconDetailArray;

/**
 *
 * */
public class PayexplainActivity extends Activity {
	private ImageView ivReturn;
	private IconDetailArray agreementlist;
	private ListView lvagreement;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_payexplain);
	initData();
	initView();
	initListenter();
}

	private void initData() {
		Resources resources = getResources();
		agreementlist = new IconDetailArray();
		agreementlist.nameArray = resources.getStringArray(R.array.payexplain_title_string_array);
		agreementlist.noteArray =  resources.getStringArray(R.array.payexplain_intro_string_array);
		agreementlist.count = agreementlist.nameArray.length;
	}

	private void initView() {
		ivReturn = (ImageView) findViewById(R.id.imageView_return);
		lvagreement = (ListView)findViewById(R.id.listView_agreement);
		AgreementAdapter agreementAdapter = new AgreementAdapter(getApplicationContext(), agreementlist);
		lvagreement.setAdapter(agreementAdapter);


	}
	private void initListenter() {
		MyListenter mylistenter = new MyListenter();
		ivReturn.setOnClickListener(mylistenter);

	}

	private class MyListenter implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			if (v == ivReturn){
				finish();
			}
		}
	}
}

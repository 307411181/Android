package com.unihiltop.xiangliao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;


public class AlertDialog extends Dialog {
	
	private TextView tvAlert;
	private Button btnCancel;
	private Button btnSure;
	private View.OnClickListener myClickListener;
	private AlertClickListener alertClickListener;

	public AlertDialog(Context context) {
		super(context, R.style.DialogTheme);
		initData();
		initView();
		initListener();
	}

	private void initData() {
	}




	private void initView() {
		setContentView(R.layout.dialog_alert);
		tvAlert = (TextView) findViewById(R.id.textView_alert);
		btnCancel = (Button) findViewById(R.id.button_cancel);
		btnSure = (Button) findViewById(R.id.button_sure);
	}

	public void setAlert(String alert){
		tvAlert.setText(alert);
	}

	public void setSureButton(String sure){
		btnSure.setText(sure);
	}

	public void setCancelButton(String cancel){
		btnCancel.setText(cancel);
	}

	public interface AlertClickListener{
		public void sure();
	}

	public void setAlertClickListener(AlertClickListener l){
		this.alertClickListener = l;
	}

	private void initListener() {
		myClickListener = new MyClickListener();
		btnCancel.setOnClickListener(myClickListener);
		btnSure.setOnClickListener(myClickListener);
	}

	private final class MyClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			if (v == btnSure) {
				if (alertClickListener != null) {
					alertClickListener.sure();
				}
			}
			dismiss();
		}
		
	}
}
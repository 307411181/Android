package com.unihiltop.xiangliao.call.ui;

import android.content.Context;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.unihiltop.xiangliao.call.utils.GlobalInfo;


public class CallingViewManager {
	private static CallingViewManager ins;
	private CallingView callingView;
	private WindowManager windowManager;
	private boolean show = false;

	private CallingViewManager(Context context){
		windowManager = (WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
	}
	public static CallingViewManager get(Context context){
		if(ins == null){
			ins = new CallingViewManager(context);
		}
		return ins;
	}
	
	public void refrshView() {
		if (callingView != null) {
			Log.i("TTT", "刷新界面发送");
			callingView.setCalling(true);
			callingView.setBaseTime(SystemClock.elapsedRealtime());
			callingView.startChronometer();
		}
	}

	public void closeMinati(){
		if (callingView != null){
			callingView.closeMianti();
		}

	}

	public void setPhoneNum(String num,String name) {
		if (callingView != null) {
			callingView.setPhoneNumber(num);
			callingView.setPhoneName(name);
		}
	}
	
	public void show(Context context, String num,String name) {
		GlobalInfo.times = 0;
		if (callingView == null) {
			callingView = new CallingView(context);
		}
		callingView.reset();
		callingView.setBaseTime(SystemClock.elapsedRealtime());

		setPhoneNum(num,name);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(2006, 32768);
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型,重要
		params.format = 1;
		params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;

		params.alpha = 1.0f;

		params.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
		// 以屏幕左上角为原点，设置x、y初始值
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		params.x = 0;
		params.y = 0;
		// 设置悬浮窗口长宽数据
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.MATCH_PARENT;
		try {
			windowManager.addView(callingView, params);
		}catch (Exception e){
			e.printStackTrace();
		}
		callingView.setShow(true);
		show = true;
	}



	public void dismiss() {
		if (callingView == null) {
			return;
		}
		show = false;
		callingView.setCalling(false);
		callingView.stopChronometer();
		if (callingView.isShow()) {
			callingView.setShow(false);
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			windowManager.removeView(callingView);
		} else {

		}
		
	}

	public boolean isShow() {
		return show;
	}
}

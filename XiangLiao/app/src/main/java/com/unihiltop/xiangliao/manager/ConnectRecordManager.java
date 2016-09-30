package com.unihiltop.xiangliao.manager;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import com.unihiltop.xiangliao.bean.Calllog;
import com.unihiltop.xiangliao.data.ContactData;


public class ConnectRecordManager {
	private Context context;

	public ConnectRecordManager(Context context) {
		this.context = context;
	}

	public void addCalllog(String number, String name) {
		Log.i("222", "插入通话记录：" + number);

		long id = insertCallLog(number, name, "0", CallLog.Calls.OUTGOING_TYPE + "", "0", 0);
		Calllog calllog = new Calllog();
		calllog.setId(id);
		calllog.setName(name);
		calllog.setNumber(number);
		calllog.setDate(System.currentTimeMillis());
		calllog.setType(CallLog.Calls.OUTGOING_TYPE);
		ContactData.get(context).addCalllog(calllog);
	}

	private long insertCallLog(String num, String name, String time, String type,
							   String isSee, long i) {
		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.NUMBER, num);
		values.put(CallLog.Calls.CACHED_NAME, name);
		values.put(CallLog.Calls.DATE, System.currentTimeMillis());
		values.put(CallLog.Calls.DURATION, time);
		values.put(CallLog.Calls.TYPE, type);// 未接
		values.put(CallLog.Calls.NEW, isSee);// 0已看1未看
		if (context.checkCallingOrSelfPermission(Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
			return -1;
		}
		Uri uri = context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
		if (uri != null){
			 return ContentUris.parseId(uri);
		 }else{
			 return -1;
		 }

	}


}
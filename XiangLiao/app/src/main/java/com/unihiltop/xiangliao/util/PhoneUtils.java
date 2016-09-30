package com.unihiltop.xiangliao.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtils {
	public static void call(Context context, String phone){
		Uri callUri = Uri.parse("tel:"+phone);
		Intent intent = new Intent(Intent.ACTION_CALL,callUri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}

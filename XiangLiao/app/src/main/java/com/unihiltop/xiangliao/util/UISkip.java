package com.unihiltop.xiangliao.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.unihiltop.xiangliao.AddressEditActivity;
import com.unihiltop.xiangliao.AddressManagmentActivity;
import com.unihiltop.xiangliao.AddressSelectActivity;
import com.unihiltop.xiangliao.CallingActivity;
import com.unihiltop.xiangliao.ExchangeStoreActivity;
import com.unihiltop.xiangliao.LoginActivity;
import com.unihiltop.xiangliao.MainActivity;
import com.unihiltop.xiangliao.MallActivity;
import com.unihiltop.xiangliao.ProblemDetailActivity;
import com.unihiltop.xiangliao.RechargeCarActivity;
import com.unihiltop.xiangliao.SearchResulatActivity;
import com.unihiltop.xiangliao.bean.IntegralProduct;
import com.unihiltop.xiangliao.bean.ReceiverAddress;
import com.unihiltop.xiangliao.constant.ExtraConstant;
import com.unihiltop.xiangliao.constant.RequestCode;


public class UISkip {
	public static void skip(boolean isClearTop, Context from, Class<?> to) {
		Intent intent = new Intent(from, to);
		if (isClearTop) {
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}
		from.startActivity(intent);
	}
	public static void skipToSearchResulatActivity(Activity activity,Long startime,Long endtime) {
		Intent intent = new Intent(activity, SearchResulatActivity.class);
		intent.putExtra("startime", startime);
		intent.putExtra("endtime", endtime);
		activity.startActivity(intent);
	}
	public static void skipToRechargeCarActivity(Activity activity,String phone) {
		Intent intent = new Intent(activity, RechargeCarActivity.class);
		intent.putExtra("phone", phone);
		activity.startActivity(intent);
	}
	public static void skipToProblemDetailActivity(Activity activity,int position) {
		Intent intent = new Intent(activity, ProblemDetailActivity.class);
		intent.putExtra("position", position);
		activity.startActivity(intent);
	}
	public static void skipToMainActivityActivity(Activity activity) {
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
	}

	public static void skipToLoginActivity(Activity activity) {
		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
	}

	public static void skipToExchangeStoreActivity(Context context, IntegralProduct integralProduct) {
		Intent intent = new Intent(context, ExchangeStoreActivity.class);
		intent.putExtra("integralProduct", integralProduct);
		context.startActivity(intent);
	}
	public static void skipToCallingActivity(Activity activity, String number,String name){
		Intent intent = new Intent(activity, CallingActivity.class);
		intent.putExtra("phonenumber", number);
		intent.putExtra("name",name);
		activity.startActivity(intent);
	}

	public static void skipToMallActivity(Activity activity, String title, String url){
		Intent intent = new Intent(activity, MallActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("url", url);
		activity.startActivity(intent);
	}

	public static void skipToAddressEditActivity(Activity activity) {
		Intent intent = new Intent(activity, AddressEditActivity.class);
		intent.putExtra(ExtraConstant.KEY_ADDRESS_EDIT_STATUS, 0);
		activity.startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_ADDRESS);
	}

	public static void skipToAddressEditActivity(Activity activity, ReceiverAddress receiverAddress) {
		Intent intent = new Intent(activity, AddressEditActivity.class);
		intent.putExtra(ExtraConstant.KEY_ADDRESS_EDIT_STATUS, 1);
		intent.putExtra(ExtraConstant.KEY_ADDRESS_EDIT_DATA, receiverAddress);
		activity.startActivityForResult(intent, RequestCode.REQUEST_CODE_EDIT_ADDRESS);
	}

	public static void skipToAddressManagmentActivity(Context context) {
		Intent intent = new Intent(context, AddressManagmentActivity.class);
		context.startActivity(intent);
	}

	public static void skipToAddressSelectActivity(Activity activity) {
		Intent intent = new Intent(activity, AddressSelectActivity.class);
		activity.startActivityForResult(intent, RequestCode.REQUEST_CODE_SELECT_ADDRESS);
	}
}

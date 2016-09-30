package com.unihiltop.xiangliao.call.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class GlobalInfo {

	/**
	 * 是否自动接听
	 */
	public static boolean IsAutoAnswer = false;
	/**
	 * 是否自动挂断
	 */
	public static boolean IsAutoOff = false;
	/**
	 * 是否接听了电话
	 */
	public static boolean IsCalling = false;
	/**
	 * 拨打的电话号码
	 */
//	public static String CallingNum = "";
	/**
	 * 话单的id(TheBill.id)
	 */
//	public static long theBillId = 0;
	/**
	 * 是否清空拨号键盘的号码
	 */
	public static boolean IsClear = false;
	
	public static int times = 0;

	public static Activity activity ;

	public static boolean isHaveMainActivity = false;
	public static boolean yesnoKey = true;
}

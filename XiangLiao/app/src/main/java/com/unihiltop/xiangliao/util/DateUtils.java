package com.unihiltop.xiangliao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String getShortDate(long date){
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		
		long nowDate = System.currentTimeMillis();
		String nowTime = format.format(nowDate);
		String time = format.format(date);
		if(nowTime.substring(0, 5).equals(time.substring(0, 5))){
			format = new SimpleDateFormat("HH:mm");
			time = format.format(date);
		}
		return time;//10-03 23:41
	} 
	
	public static String getYYYYMMDD(long date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		
		return time;//2015-10-03
	}
	public static String getRecordsDate(long date) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

		String time = format.format(date);
		return time;// 10-03 23:41
	}
	public static String getDate(long date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm");
		
		long nowDate = System.currentTimeMillis();
		String nowTime = format.format(nowDate);
		String time = format.format(date);
		return time;//2015-10-03 23:41
	}

	/**
	 * 返回传入时间的毫秒值
	 * @param dateStr 格式  HH:mm
	 * @return
	 */
	public static long getHHAndMMTime(String dateStr) {
		if (dateStr == null || dateStr.trim().equals("")) {
			return 0;
		}
		long time = 0L;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date date;
		try {
			date = format.parse(dateStr);
			time = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return time;
	}
	
	/**
	 * 返回当前时间天的毫秒值
	 * @return
	 */
	public static long getNowYearAndMonthAndDayTime() {
		long time = 0L;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String timeStr = sdf.format(System.currentTimeMillis());
		Date date;
		try {
			date = sdf.parse(timeStr);
			time = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	/**
	 * 
	 * @param dateStr yyyy-M-d HH:mm格式
	 * @return
	 */
	public static long getLongTime(String dateStr) {
		long time = 0L;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm");
		Date date;
		try {
			date = format.parse(dateStr);
			time = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return time;
	}
	/**
	 *
	 * @param dateStr yyyy-MM-dd 格式
	 * @return
	 */
	public static long getLongTimeDay(String dateStr) {
		long time = 0L;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(dateStr);
			time = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	/**
	 * 
	 * @param dateStr 日期
	 * @param formatStr 日期格式
	 * @return
	 */
	public static long getLongTime(String dateStr, String formatStr) {
		long time = 0L;
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date;
		try {
			date = format.parse(dateStr);
			time = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return time;
	}
	public static String getFullDate(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		return format.format(date);//2015-10-03
	}
}

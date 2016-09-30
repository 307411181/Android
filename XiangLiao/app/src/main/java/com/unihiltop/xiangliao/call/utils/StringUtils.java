package com.unihiltop.xiangliao.call.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static ArrayList<String>  getMatcherList(String resStr, String lStr, String rStr){
		ArrayList<String> Stres = new ArrayList<String>();
		Pattern p = Pattern.compile(lStr+"([\\w/\\.]*)"+rStr);
        Matcher m = p.matcher(resStr);
        while (!m.hitEnd() && m.find()) {
        	Stres.add(m.group(1));
        }
		return Stres;        
	}
	
	/**
	 * 处理首字母
	 * 
	 * @return 字符串的首字母，不是A~Z范围的返回#
	 */
	public static String formatAlpha(String str) {
		if (TextUtils.isEmpty(str)) {
			return "#";
		}
		if (" ".equals(str)){
			return " ";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else {
			return "#";
		}
	}
	
	
	
	public static String getPhoneNumber(String phone){
		if(phone == null){
			return "";
		}
		phone = phone.replace(" ", "");
		if (phone.length() < 11) {
			return phone;
		}
		if (phone.substring(0, 3).equals("+86")) {
			phone = phone.substring(3);
		}else if (phone.substring(0, 5).equals("17951")){
			phone = phone.substring(5);
		}else if (phone.substring(0, 5).equals("17901")){
			phone = phone.substring(5);
		}else if (phone.substring(0, 5).equals("10193")){
			phone = phone.substring(5);
		}
		return phone;
	}

	/**
	 * 得到长度为lenght的String数组
	 * @param lenght
	 */
	public static ArrayList<String> getStrings(String src, int lenght) {
		ArrayList<String>  strings = new ArrayList<String>();
		if (src != null && !src.equals("")) {
			int num = src.length()/lenght;
			for (int i = 0; i < num; i++) {
				int start = i*lenght;
				strings.add(src.substring(start, start + lenght));
			}
			strings.add(src.substring(num*lenght, src.length()));
		}
		return strings;
	}
}

package com.unihiltop.xiangliao.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;

public class PreferencesHelper {
	
	private SharedPreferences sharedPreferences = null;
		
	private static PreferencesHelper preferencesHelper = null;
	private final static String LISTEN_ORDER_STATE = "listen_order_state";

	private PreferencesHelper(Context context) {
		sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}
	
	
	public static PreferencesHelper get(Context context){
		if (preferencesHelper == null) {
			preferencesHelper = new PreferencesHelper(context);
		}
		return preferencesHelper;
	}
	/**
	 * 保存键为key的值为value
	 * @param key 来自 PreferenceKey
	 * @param value
	 */
	public void put(String key,int value){		
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	/**
	 * 保存键为key的值为value
	 * @param key 来自 PreferenceKey
	 * @param value
	 */
	public void put(String key,String value){		
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	
	/**
	 * 保存键为key的值为value
	 * @param key 来自 PreferenceKey
	 * @param value
	 */
	public void put(String key,boolean value){		
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	/**
	 * 保存键为key的值为value
	 * @param
	 */
	public void put(Map<String, String> map){		
		Editor editor = sharedPreferences.edit();
		for (String key:map.keySet()) {
			editor.putString(key, map.get(key));
		}
		editor.commit();
	}
	
	/**
	 * 保存键为key的值为value
	 * @param key 来自 PreferenceKey
	 * @param value
	 */
	public void put(String key,long value){		
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	
	/**
	 * 保存键为key的值为value
	 * @param key 来自 PreferenceKey
	 * @param value
	 */
	public void put(String key,float value){		
		Editor editor = sharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	/**
	 * key对应的整型值叠加1
	 * @param key 来自 PreferenceKey
	 */
	public void superposition(String key){
		int value = sharedPreferences.getInt(key, 0);
		Editor editor = sharedPreferences.edit();
		value ++;
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public int getInt(String key, int defult){
		return sharedPreferences.getInt(key, defult);
	}
	
	public int getInt(String key){
		return sharedPreferences.getInt(key, 0);
	}
	
	public boolean getBoolean(String key){
		return sharedPreferences.getBoolean(key, true);
	}
	
	public boolean getBoolean(String key, boolean isTrue){
		return sharedPreferences.getBoolean(key, isTrue);
	}

	public String getString(String key) {
		return sharedPreferences.getString(key, null);
	}

	public String getString(String key,String defult) {
		return sharedPreferences.getString(key, defult);
	}

	public long getLong(String key, long defult) {
		return sharedPreferences.getLong(key, defult);
	}
	
	public long getLong(String key) {
		return sharedPreferences.getLong(key, 0);
	}
	public void clear() {
		preferencesHelper = null;

		
	}
	public void setListenOrder(boolean isListenOrder){
		preferencesHelper.put(LISTEN_ORDER_STATE, isListenOrder);
	}

	public boolean isListenOrder(){
		return preferencesHelper.getBoolean(LISTEN_ORDER_STATE, true);
	}

	public void putObject(String key,Object obj){
		String json = sharedPreferences.getString(key, null);
		if (obj == null){
			put(key, null);
		}else{
			put(key, new Gson().toJson(obj));
		}
	}

	public <T>T getObject(String key,Class<T> cls){
		String json = sharedPreferences.getString(key, null);
		if (json == null)
			return null;
		return new Gson().fromJson(json, cls);
	}
}

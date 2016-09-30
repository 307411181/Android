package com.unihiltop.xiangliao.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;


/**
 * 网络返回的数据
 * @author yangyang
 *
 */
public class NetMessage {
	private JSONObject jsonObject = null;
	public NetMessage(){
		jsonObject = new JSONObject();
	}
	
	public NetMessage(String json){
		Log.e("NetMessage", "json=" + json);
		if (TextUtils.isEmpty(json)) {
			jsonObject = new JSONObject();
			return;
		}
		try {
			jsonObject = new JSONObject(json);			
		} catch (JSONException e) {
			Log.e("NetMessage", "json=" + json);
			e.printStackTrace();
		}
		
	}

	public void setJson(String json){
		Log.i("NetMessage", "set json=" + json);
		try {			
			jsonObject = new JSONObject(json);			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setCode(int code){
		try {
			if(jsonObject == null){
				return;
			}
			jsonObject.put(CommonConstant.KEY.KEY_STATE, code);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setMsg(String msg){
		try {
			if(jsonObject == null){
				return;
			}
			jsonObject.put(CommonConstant.KEY.ATTACH_TEXT_KEY, msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setData(String data){
		try {
			if(jsonObject == null){
				return;
			}
			jsonObject.put(CommonConstant.KEY.RESPONSE_DATA_KEY, data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getCode() {
		try {
			if(jsonObject == null){
				return -1;
			}
			return jsonObject.getInt(CommonConstant.KEY.KEY_STATE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public String getMsg() {
		String msg = null;
		try {
			if(jsonObject != null){
				msg = jsonObject.getString(CommonConstant.KEY.ATTACH_TEXT_KEY);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (msg == null) {
			return getMsg(getCode());
		}
		return msg;
	}

	public String getData() {
		try {
			if(jsonObject == null){
				return null;
			}
			return jsonObject.getString(CommonConstant.KEY.RESPONSE_DATA_KEY);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getData(String key){
		if(jsonObject == null){
			return null;
		}
		if (jsonObject.has(key)) {
			try {
				return jsonObject.getString(key);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getJson(){
		if (jsonObject == null) {
			return null;
		}
		return jsonObject.toString();
	}
	private String getMsg(int code) {
		switch(code){
//		case TextConstant.SERVER_ERROR:
//			return "服务器异常";
//		case  TextConstant.REQUEST_CAPTCHA_FAILURE:
//			return "请求短信验证码失败";
			default:
				return "未知错误";
		}
	}
}
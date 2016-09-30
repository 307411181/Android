package com.unihiltop.xiangliao.net;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class OkHttpUtils {
	//public static final String BASE_URL = "http://112.74.14.195:7070/Logistics";
	//public static final String BASE_URL = "http://192.168.0.104:7070/KuaiHua";//内网

	/**
	 * 原来的外网,把需要替换的地方都加了   //
	 */
//	public static final String BASE_URL = "http://114.215.85.203:8080/XL";//外网

	/**
	 * 更改后的外网
     */
	public static final String BASE_URL_UPDATE = "http://139.129.217.239:8080/XL";//外网

	//
	  public static void get(String url, Map<String, String> params,  Callback.CommonCallback<String> callback){
		  RequestParams requestParams = new RequestParams(BASE_URL_UPDATE + url);
		  if(params != null){
			  Set<Entry<String, String>> set = params.entrySet();
			  if(set != null && set.size() > 0){
				  for (Entry<String, String> entry : set) {
					  if (entry.getValue() != null){
						  requestParams.addBodyParameter(entry.getKey(), entry.getValue());
					  }
				  }
			  }
		  }
		  x.http().get(requestParams, callback);
	  }

	public static void getByWholeUrl(String url, Map<String, String> params, Callback.CommonCallback<String> callback){
		url = getGetUrl(url, params);
		RequestParams requestParams = new RequestParams(url);
		x.http().get(requestParams, callback);
	}


	//
	public static void get(String url, Callback.CommonCallback<String> callback){
		RequestParams requestParams = new RequestParams(BASE_URL_UPDATE + url);
		x.http().get(requestParams, callback);
	}

	  public static void post(String url, Map<String, String> params, Callback.CommonCallback<String> callback){
		  RequestParams requestParams = new RequestParams(BASE_URL_UPDATE + url);
		  if(params != null){
			  Set<Entry<String, String>> set = params.entrySet();
			  if(set != null && set.size() > 0){
				  for (Entry<String, String> entry : set) {
					  if (entry.getValue() != null){
						  requestParams.addBodyParameter(entry.getKey(), entry.getValue());
					  }
				  }
			  }
		  }
		  x.http().post(requestParams, callback);
	  }


	/**
	 * post请求方式新,用于了登陆页面(验证码)处的逻辑
	 * @param url
	 * @param params
	 * @param callback
     */
	public static void postUpdate(String url, Map<String, String> params, Callback.CommonCallback<String> callback){
		RequestParams requestParams = new RequestParams(BASE_URL_UPDATE + url);
		if(params != null){
			Set<Entry<String, String>> set = params.entrySet();
			if(set != null && set.size() > 0){
				for (Entry<String, String> entry : set) {
					if (entry.getValue() != null){
						requestParams.addBodyParameter(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		x.http().post(requestParams, callback);
	}




	//
	  public static void get(String url, Map<String, String> params){
		  RequestParams requestParams = new RequestParams(BASE_URL_UPDATE + url);
		  if(params != null){
			  Set<Entry<String, String>> set = params.entrySet();
			  if(set != null && set.size() > 0){
				  for (Entry<String, String> entry : set) {
					  if (entry.getValue() != null){
						  requestParams.addBodyParameter(entry.getKey(), entry.getValue());
					  }
				  }
			  }
		  }
		  x.http().get(requestParams, null);
	  }

	//
	  public static void post(String url, Map<String, String> params){
		  RequestParams requestParams = new RequestParams(BASE_URL_UPDATE + url);
		  if(params != null){
			  Set<Entry<String, String>> set = params.entrySet();
			  if(set != null && set.size() > 0){
				  for (Entry<String, String> entry : set) {
					  if (entry.getValue() != null){
						  requestParams.addBodyParameter(entry.getKey(), entry.getValue());
					  }
				  }
			  }
		  }
		  x.http().post(requestParams, null);
	  }

	  
	  private static String getGetUrl(String url, Map<String, String> params){
		  if(params != null){
			  Set<Entry<String, String>> set = params.entrySet();
			  if(set != null && set.size() > 0){
				  url = url +"?";
				  StringBuffer paramsBuffer = new StringBuffer();
				  for (Entry<String, String> entry : set) {
					  paramsBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
				  }
				  url = url + paramsBuffer.substring(0, paramsBuffer.length()-1);
			  }
			 
		  }
		  return url;
	  }

}

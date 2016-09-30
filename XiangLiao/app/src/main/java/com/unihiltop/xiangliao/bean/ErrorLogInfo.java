package com.unihiltop.xiangliao.bean;


import java.io.Serializable;

/**
 * 错误信息
 * @author Administrator
 *
 */

public class ErrorLogInfo implements Serializable{
	public String id;
	/**
	 * 
	 */
	public static final long serialVersionUID = -73625042151019456L;
	/**
	 * 错误内容
	 */
	public String content;
	/**
	 * 错误日期 2015-01-07 12:30:25
	 */
	public String date;
	/**
	 * 版本号
	 */
	public int versionCode;
	/**
	 * 上传者手机号
	 */
	public String mobile;
	/**
	 * 设备信息
	 */
	public String device;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 错误类型
	 */

	public String type;

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}

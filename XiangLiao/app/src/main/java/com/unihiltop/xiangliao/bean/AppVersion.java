package com.unihiltop.xiangliao.bean;

/**
 * Created by Administrator on 2015/2/3 0003.
 */


import java.io.Serializable;

/**
 * 版本管理
 */

public class AppVersion implements Serializable{
    public long id;
    /**
     * app名称
     */
    public String appName;
    /**
     * 版本号
     */
    public int versionCode;
    /**
     * 版本名
     */
    public String versionName;
    /**
     * 下载地址
     */
    public String downloadURL;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }


    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof AppVersion){
            AppVersion version = (AppVersion) obj;
            return version.getId() == id;
        }
        return false;
    }
}

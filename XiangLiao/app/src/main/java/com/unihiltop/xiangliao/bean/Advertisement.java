package com.unihiltop.xiangliao.bean;

/**
 * Created by Administrator on 2015/3/30 0030.
 */


import java.io.Serializable;

/**
 * 广告
 */

public class Advertisement implements Serializable {
    public String id;
    /**
     * 图片网络地址
     */
    public String imageURL;
    /**
     * 图片链接（点击图片要打开的URL）
     */
    public String clickURL;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getClickURL() {
        return clickURL;
    }

    public void setClickURL(String clickURL) {
        this.clickURL = clickURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

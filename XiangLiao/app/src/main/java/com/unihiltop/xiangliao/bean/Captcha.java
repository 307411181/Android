package com.unihiltop.xiangliao.bean;



/**
 * Created by Administrator on 2015/9/10 0010.
 */

public class Captcha {
    private long id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 发送时间
     */
    private long sendTime;

    public Captcha() {
    }

    public Captcha(String mobile, String captcha, long sendTime) {
        this.mobile = mobile;
        this.captcha = captcha;
        this.sendTime = sendTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}

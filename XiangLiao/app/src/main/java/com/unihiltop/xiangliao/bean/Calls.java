package com.unihiltop.xiangliao.bean;

import java.io.Serializable;

/**
 * Created by yy on 2015/10/22.
 */
public class Calls implements Serializable {
    /**
     * 帐号（手机号）
     */
    private String phone;
    /**
     * 呼叫类型
     */
    private String type;
    /**
     * 联系人姓名
     */
    private String name;
    /**
     * 呼叫时间（当时的时间）
     */
    private String time;
    /**
     * 电话持续时间
     */
    private String duration;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

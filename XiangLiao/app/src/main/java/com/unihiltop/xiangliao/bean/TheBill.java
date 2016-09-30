package com.unihiltop.xiangliao.bean;

import java.io.Serializable;

/**
 * 话单
 */
public class TheBill implements Serializable {
    public long id;
    /**
     * 主叫号码
     */
    public String callingNumber;
    /**
     * 被叫号码
     */
    public String calledNumber;
    /**
     * 拨打时间
     */
    public long dialTime;
    /**
     * 结束时间
     */
    public long hangUpTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDialTime() {
        return dialTime;
    }

    public void setDialTime(long dialTime) {
        this.dialTime = dialTime;
    }

    public long getHangUpTime() {
        return hangUpTime;
    }

    public void setHangUpTime(long hangUpTime) {
        this.hangUpTime = hangUpTime;
    }

    public String getCallingNumber() {
        return callingNumber;
    }

    public void setCallingNumber(String callingNumber) {
        this.callingNumber = callingNumber;
    }

    public String getCalledNumber() {
        return calledNumber;
    }

    public void setCalledNumber(String calledNumber) {
        this.calledNumber = calledNumber;
    }

}

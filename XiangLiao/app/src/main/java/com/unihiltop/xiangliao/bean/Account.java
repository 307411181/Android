package com.unihiltop.xiangliao.bean;


import java.io.Serializable;

/**
 * 帐号
 */

public class Account implements Serializable{
    public long id;
    /**
     * 帐号（手机号）
     */
    public String account;
    /**
     * 可使用时间-开始
     */
    public long availableStartDate;
    /**
     * 可使用时间-结束
     */
    public long availableEndDate;
    /**
     * 是否开通vip
     */
    public boolean vip;
    /**
     * 头像
     */
    public String headImg;
    /**
     * 话费余额
     */
    public double bill;
    /**
     * 积分
     */
    public double integral;
    /**
     * 金币
     */
    public double coin;

    public int version;

    /**
     * 注册时间
     */
    public long registerTime;

    /**
     * 是否禁用
     */
    public boolean forbidden;

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public boolean isForbidden() {
        return forbidden;
    }

    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }

    public Account() {
    }

    public Account(String account) {
        this.account = account;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getAvailableStartDate() {
        return availableStartDate;
    }

    public void setAvailableStartDate(long availableStartDate) {
        this.availableStartDate = availableStartDate;
    }

    public long getAvailableEndDate() {
        return availableEndDate;
    }

    public void setAvailableEndDate(long availableEndDate) {
        this.availableEndDate = availableEndDate;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}

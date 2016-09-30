package com.unihiltop.xiangliao.bean;

/**
 * Created by Administrator on 2015/2/1 0001.
 */


import java.io.Serializable;

/**
 * 充值的订单
 */

public class RechargeOrder implements Serializable{
    public long id;
    /**
     * 关联者的账号
     */
    public String account;
    /**
     * 充值月数
     */
    public int rechargeMonth;
    /**
     * 充值金额
     */
    public double rechargeMoney;

    public RechargeOrder() {
    }

    public RechargeOrder(String account, int rechargeMonth, double rechargeMoney) {
        this.account = account;
        this.rechargeMonth = rechargeMonth;
        this.rechargeMoney = rechargeMoney;
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

    public int getRechargeMonth() {
        return rechargeMonth;
    }

    public void setRechargeMonth(int rechargeMonth) {
        this.rechargeMonth = rechargeMonth;
    }

    public double getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(double rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof RechargeOrder) {
            if(( (RechargeOrder) obj).getId() == id) {
                return true;
            }
        }
        return false;
    }
}

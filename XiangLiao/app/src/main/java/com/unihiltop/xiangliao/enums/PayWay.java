package com.unihiltop.xiangliao.enums;

/**
 * Created by Administrator on 2015/1/22 0022.
 */

/**
 * 支付方式
 */
public enum PayWay {
    PAY_WITH_BALANCE("微信支付", 0),
    PAY_WITH_WECHAT("支付宝支付", 1),
    PAY_WITH_WECHAT_AND_COUPON("银联支付", 2);
    private String name;
    private int index;

    PayWay(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

package com.unihiltop.xiangliao.bean;

import java.io.Serializable;


/**
 * class description: 评价
 * creator: yedaibing
 * create date: 2015/11/10  9:52.
 */
public class Evaluation implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 商品Id
     */
    private String productId;

    private Account account;
    /**
     * 评价分数
     */
    private float grade;
    /**
     * 评价描述
     */
    private String detail;
    /**
     * 评价时间
     */
    private long evalTime;


    public String getId() {
        return id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getEvalTime() {
        return evalTime;
    }

    public void setEvalTime(long evalTime) {
        this.evalTime = evalTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

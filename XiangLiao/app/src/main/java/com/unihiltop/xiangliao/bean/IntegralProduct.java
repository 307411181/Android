package com.unihiltop.xiangliao.bean;


import java.io.Serializable;

/**
 * 积分商品
 */

public class IntegralProduct implements Serializable {
    public long id;
    /**
     * 商品名称
     */
    public String productName;
    /**
     * 商品图片URL
     */
    public String imgURL;
    /**
     * 兑换所需金币
     */
    public double needCoin;
    /**
     * 数量
     */
    public int amount;

    /**
     * 乐观锁
     */
    public int version;
    /**
     * 商品描述
     */
    public String productDescription;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public double getNeedCoin() {
        return needCoin;
    }

    public void setNeedCoin(double needCoin) {
        this.needCoin = needCoin;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    public int getproductDescription() {
        return version;
    }

    public void setproductDescription(int version) {
        this.version = version;
    }
}

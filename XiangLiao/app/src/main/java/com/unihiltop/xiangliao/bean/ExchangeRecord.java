package com.unihiltop.xiangliao.bean;



/**
 * 兑换记录
 */

public class ExchangeRecord {
    public long id;
    /**
     * 兑换的商品
     */
    public IntegralProduct integralProduct;
    /**
     * 兑换时间
     */
    public long exchangeTime;
    /**
     * 兑换的数量
     */
    public int exchangeAmount;
    /**
     * 省
     */
    public String province;
    /**
     * 市
     */
    public String city;
    /**
     * 县
     */
    public String county;
    /**
     * 详细地址
     */
    public String detailAddress;
    /**
     * 联系人姓名
     */
    public String contactPersonName;
    /**
     * 联系人电话
     */
    public String contactPersonMobile;
    /**
     * 帐号
     */
    public Account account;
    /**
     * 配送状态
     * 0 表示未发货，1表示送货中，2表示已完成
     */
    public int deliveryStatus;


    public ExchangeRecord() {
    }

    public ExchangeRecord(IntegralProduct integralProduct, long exchangeTime, int exchangeAmount, String province, String city, String county, String detailAddress,
                          String contactPersonName, String contactPersonMobile, Account account,int deliveryStatus) {
        this.integralProduct = integralProduct;
        this.exchangeTime = exchangeTime;
        this.exchangeAmount = exchangeAmount;
        this.province = province;
        this.city = city;
        this.county = county;
        this.detailAddress = detailAddress;
        this.contactPersonName = contactPersonName;
        this.contactPersonMobile = contactPersonMobile;
        this.account = account;
        this.deliveryStatus = deliveryStatus;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public IntegralProduct getIntegralProduct() {
        return integralProduct;
    }

    public void setIntegralProduct(IntegralProduct integralProduct) {
        this.integralProduct = integralProduct;
    }

    public long getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(long exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public int getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(int exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonMobile() {
        return contactPersonMobile;
    }

    public void setContactPersonMobile(String contactPersonMobile) {
        this.contactPersonMobile = contactPersonMobile;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public int getdeliveryStatus() {
        return deliveryStatus;
    }

    public void setdeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

}

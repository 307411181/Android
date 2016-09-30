package com.unihiltop.xiangliao.bean;

/**
 * Created by Administrator on 2015/1/6 0006.
 */

import java.io.Serializable;

/**
 * 收货地址
 */
public class ReceiverAddress implements Serializable {
    private String id;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 县城
     */
    private String county;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 邮政编码
     */
    private String postCode;
    /**
     * 此收货地址属于谁
     */
    private Account account;
    /**
     * 是否默认收货地址
     */
    private boolean defaultAddress = false;
    /**
     * 收货人姓名
     */
    private String contactPersonName;
    /**
     * 收货地址电话
     */
    private String contactPersonMobile;
    /**
     * 是否可见，表示这个地址是否被删除
     */
    private boolean visible = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof ReceiverAddress) {
            ReceiverAddress address = (ReceiverAddress) obj;
            if (address.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}

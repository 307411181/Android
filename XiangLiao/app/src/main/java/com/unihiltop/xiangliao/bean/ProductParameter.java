package com.unihiltop.xiangliao.bean;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class ProductParameter {
    private String key;
    private String value;
    public ProductParameter(){}
    public ProductParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

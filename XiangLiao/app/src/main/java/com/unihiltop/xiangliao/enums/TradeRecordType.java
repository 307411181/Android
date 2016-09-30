package com.unihiltop.xiangliao.enums;

/**
 * Created by Administrator on 2015/3/3 0003.
 */
public enum TradeRecordType {

    INCOMING("入账",0),
    PAY("出账",1);

    private String name;
    private int index;

    TradeRecordType(String name, int index) {
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

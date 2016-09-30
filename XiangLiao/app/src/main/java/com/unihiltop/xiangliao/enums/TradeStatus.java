package com.unihiltop.xiangliao.enums;

/**
 * Created by Administrator on 2015/4/11 0011.
 */
public enum  TradeStatus {
    BEING_PROCESSED("处理中",0),
    PROCESSED_SUCCESS("处理成功",1),
    PROCESSED_FAILURE("处理失败",2);

    TradeStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    private String name;
    private int index;

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

package com.unihiltop.xiangliao.enums;

/**
 * Created by Administrator on 2015/1/4 0004.
 */
public enum OrderStatus {
    //待支付，已支付待发货，已发货，已收货未评价，已收货已评价
    ORDER_WAIT_FOR_PAY("待支付",0),
    ORDER_PAYED_WAIT_FOR_DELIVER("已支付待发货",1),
    ORDER_DELIVERED("已发货",2),
    ORDER_BEEN_TAKEN_NO_EVALUATE("已收货未评价",3),
    ORDER_BEEN_TAKEN_EVALUATED("已收货已评价",4),
    ORDER_BEEN_CANCELED("订单被取消",5);

    private OrderStatus(String name, int index){
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

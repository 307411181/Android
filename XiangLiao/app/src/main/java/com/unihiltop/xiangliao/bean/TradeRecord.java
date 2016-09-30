package com.unihiltop.xiangliao.bean;

/**
 * Created by Administrator on 2015/3/3 0003.
 */
import com.unihiltop.xiangliao.enums.TradeRecordType;
import com.unihiltop.xiangliao.enums.TradeStatus;

import java.io.Serializable;

/**
 * 交易记录(记录成功的)
 */
public class TradeRecord implements Serializable {
    private String id;
    /**
     * 账号
     */
    private Account account;
    /**
     * 日期
     */
    private Long date;
    /**
     * 这次交易记录应该支付的金额
     */
    private Double money;

    /**
     * 实际支付金额
     */
    private Double realMoney;
    /**
     * 该订单使用了多少积分
     */
    private int usePoints;

    /**
     * 邮费
     */
    private double postageFee;
    /**
     * 资金类型
     */
    private TradeRecordType tradeRecordType;
    /**
     * 交易状态
     */
    private TradeStatus tradeStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public TradeRecordType getTradeRecordType() {
        return tradeRecordType;
    }

    public void setTradeRecordType(TradeRecordType tradeRecordType) {
        this.tradeRecordType = tradeRecordType;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Double getRealMoney() {
        return realMoney;
    }

    public double getPostageFee() {
        return postageFee;
    }

    public void setPostageFee(double postageFee) {
        this.postageFee = postageFee;
    }

    public void setRealMoney(Double realMoney) {
        this.realMoney = realMoney;
    }

    public int getUsePoints() {
        return usePoints;
    }

    public void setUsePoints(int usePoints) {
        this.usePoints = usePoints;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof TradeRecord) {
            if (((TradeRecord) obj).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}

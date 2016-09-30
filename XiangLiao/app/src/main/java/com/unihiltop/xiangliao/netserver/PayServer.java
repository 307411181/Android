package com.unihiltop.xiangliao.netserver;

import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.net.OkHttpUtils;

import java.util.HashMap;


/**
 * Created by yangyang on 2015/10/27.
 */
public class PayServer {
    /**
     *余额充值
     * @param account
     * @param responseHandler
     */
    public void rechargeBill(String chargerAccount,String account,int month,int money, NetMessageCallback responseHandler) {
         HashMap<String, String> params = new HashMap<String, String>();
        params.put("chargerAccount", chargerAccount);
        params.put("account", account);
        params.put("month", month+"");
        params.put("money", money+"");
        OkHttpUtils.post("/recharge/rechargeBill",
                params, responseHandler);

    }
    /**
     * 支付宝充值回调接口
     *
     *
     * @param responseHandler
     */
    public void aliNotifyRechargeResult( NetMessageCallback responseHandler) {

        OkHttpUtils.get("/payNotify/aliNotifyRechargeResult",
                responseHandler);
    }
    /**
     * 微信充值回调接口
     *
     *
     * @param responseHandler
     */
    public void weChatNotifyRechargeResult( NetMessageCallback responseHandler) {

        OkHttpUtils.get("/payNotify/weChatNotifyRechargeResult",
                responseHandler);
    }

    /**
     *余额充值
     * @param
     * @param responseHandler
     */
    public void rechargeBillWithCard(String chargerAccount,String mobile,String cardNum,String cardPwd, NetMessageCallback responseHandler) {
         HashMap<String, String> params = new  HashMap<String, String>();
        params.put("chargerAccount", chargerAccount);
        params.put("mobile", mobile);
        params.put("cardNum", cardNum);
        params.put("cardPwd", cardPwd);
        OkHttpUtils.post("/recharge/rechargeBillWithCard",
                params, responseHandler);

    }
//    /**
//     * 手机归属地查询
//     * @param mobile
//     * @param responseHandler
//     */
//    public void mobileTelSegment(String mobile, Callback responseHandler) {
//        HashMap<String, String> params = new  HashMap<String, String>();
//        params.put("mobile", mobile);
//        params.put("amount", 10000+"");
//        OkHttpUtils.getByWholeUrl("http://virtual.paipai.com/extinfo/GetMobileProductInfo",
//                params, responseHandler);
//
//    }
    /**
     *充值流量
     * @param
     * @param responseHandler
     */
    public void rechargeFlow(String chargerAccount,double money, NetMessageCallback responseHandler) {
         HashMap<String, String> params = new  HashMap<String, String>();
        params.put("chargerAccount", chargerAccount);
        params.put("money", money+"");

        OkHttpUtils.post("/recharge/rechargeFlow",
                params, responseHandler);

    }
    /**
     *抽奖
     * @param
     * @param responseHandler
     */
    public void InitPage(String account, NetMessageCallback responseHandler) {
         HashMap<String, String> params = new  HashMap<String, String>();
        params.put("account", account);
        OkHttpUtils.post("/AwardController/InitPage",
                params, responseHandler);

    }
}

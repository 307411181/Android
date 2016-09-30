package com.unihiltop.xiangliao.netserver;

import android.text.TextUtils;


import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.net.OkHttpUtils;

import java.util.HashMap;

public class AccountServer {
    /**
     * 请求验证码
     *
     * @param account
     * @param responseHandler
     */
    public void requestCaptcha(String account, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        OkHttpUtils.post("/captchaController/requestCaptcha",
                params, responseHandler);
    }

    /**
     * 注册帐号,登陆
     *
     * @param account
     * @param recommendedMobile
     * @param account
     * @param responseHandler
     */
    public void registerOrLogin(String adminAccount, String recommendedMobile, String account, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        if (!TextUtils.isEmpty(adminAccount)) {
            params.put("adminAccount", adminAccount);
        }
        if (!TextUtils.isEmpty(recommendedMobile)) {
            params.put("recommendedMobile", recommendedMobile);
        }

        params.put("account", account);
        OkHttpUtils.post("/account/registerOrLogin",
                params, responseHandler);
    }


    /**
     * 注册帐号,登陆(新)
     *
     * @param account
     * @param recommendedMobile
     * @param account
     * @param responseHandler
     */
    public void registerOrLoginUpdate(String adminAccount, String recommendedMobile, String account, String verificationCode, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        if (!TextUtils.isEmpty(adminAccount)) {
            params.put("adminAccount", adminAccount);
        }
        if (!TextUtils.isEmpty(recommendedMobile)) {
            params.put("recommendedMobile", recommendedMobile);
        }

        params.put("account", account);
        params.put("verificationCode", verificationCode);
        OkHttpUtils.post("/account/registerOrLogin",
                params, responseHandler);
    }

    /**
     * 环信注册
     *
     * @param mobile
     * @param responseHandler
     */
    public void registerMob(String mobile, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        OkHttpUtils.post("/account/registerMob",
                params, responseHandler);
    }

    /**
     * 获取积分商品商品列表(分页获取)
     *
     * @param preIntegralProductId
     * @param responseHandler
     */
    public void getIntegralProductList(long preIntegralProductId, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        if (preIntegralProductId > 0) {
            params.put("preIntegralProductId", preIntegralProductId + "");
        }
        params.put("preIntegralProductId", preIntegralProductId + "");
        OkHttpUtils.get("/integralProduct/getIntegralProductList",
                params, responseHandler);
    }

    /**
     * 兑换积分商品
     *
     * @param integralProductId
     * @param responseHandler
     */
    public void exchangeIntegralProduct(long integralProductId, long accountId, int amount, String receivingAddressJson, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("integralProductId", integralProductId + "");
        params.put("accountId", accountId + "");
        params.put("amount", amount + "");
        params.put("receivingAddressJson", receivingAddressJson);
        OkHttpUtils.post("/exchangeRecord/exchangeIntegralProduct",
                params, responseHandler);
    }

    /**
     * 分页获取兑换商品记录
     *
     * @param accountId
     * @param preExchangeRecordId
     * @param responseHandler
     */
    public void getExchangeIntegralProductList(long accountId, long preExchangeRecordId, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId + "");
        if (preExchangeRecordId > 0) {
            params.put("preExchangeRecordId", preExchangeRecordId + "");
        }
        OkHttpUtils.get("/exchangeRecord/getExchangeIntegralProductList",
                params, responseHandler);
    }

    /**
     * 上传通话记录
     *
     * @param accountId
     * @param mobile
     * @param dialTime
     * @param hangUpTime
     * @param responseHandler
     */
    public void uploadTheBill(long accountId, String mobile, long dialTime, long hangUpTime, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId + "");
        params.put("mobile", mobile);
        params.put("dialTime", dialTime + "");
        params.put("hangUpTime", hangUpTime + "");
        OkHttpUtils.post("/theBill/uploadTheBill",
                params, responseHandler);
    }

    /**
     * 上传通话记录二
     *
     * @param theBillId
     * @param talkTime
     * @param responseHandler
     */
    public void uploadTheBillWithTalkTime(long theBillId, long talkTime, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("theBillId", theBillId + "");
        params.put("talkTime", talkTime + "");
        OkHttpUtils.post("/theBill/uploadTheBillWithTalkTime",
                params, responseHandler);
    }

    /**
     * 查询通话记录(分页获取)
     *
     * @param preTheBillId
     * @param preTheBillId
     * @param startTime
     * @param endTime
     * @param responseHandler
     */
    public void searchTheBill(String callingNumber, Long preTheBillId, Long startTime, Long endTime, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("callingNumber", callingNumber);
        if (preTheBillId > 0) {
            params.put("preTheBillId", preTheBillId + "");
        }
        params.put("startTime", startTime + "");
        params.put("endTime", endTime + "");
        OkHttpUtils.get("/theBill/searchTheBill",
                params, responseHandler);
    }

    /**
     * 上传app出错异常数据
     *
     * @param errorLogInfo
     * @param responseHandler
     */
    public void sendErrorLog(String errorLogInfo, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("errorLogInfo", errorLogInfo);
        OkHttpUtils.post("/errorLogController/sendErrorLog",
                params, responseHandler);
    }

    //    /**
    //     *上传头像
    //     * @param accountId
    //     * @param responseHandler
    //     */
    //    public void uploadHeadImg(long accountId,File headImg, NetMessageResponseHandler responseHandler) {
    //        HashMap<String, String> params = new HashMap<String, String>();
    //        params.put("accountId", accountId);
    //        try {
    //            params.put("headImg", headImg);
    //        } catch (FileNotFoundException e) {
    //            e.printStackTrace();
    //        }
    //        OkHttpUtils.post("/account/uploadHeadImg",
    //                params, responseHandler);
    //
    //    }

    /**
     * 提交意见反馈
     *
     * @param content
     * @param responseHandler
     */
    public void uploadFeedBack(String content, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("content", content);
        OkHttpUtils.post("/feedBack/uploadFeedBack",
                params, responseHandler);

    }

    /**
     * 检查手机号是否注册过
     *
     * @param
     * @param
     */
    public void isUserRegistered(String mobile, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        OkHttpUtils.get("/account/isUserRegistered",
                params, responseHandler);

    }

    /**
     * 筛选注册了的用户
     *
     * @param
     * @param
     */
    public void screeningUser(String mobileListJson, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobileListJson", mobileListJson);
        OkHttpUtils.post("/account/screeningUser",
                params, responseHandler);

    }

    /**
     * 刷新用户
     *
     * @param
     * @param
     */
    public void getAccount(String account, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("account", account);
        OkHttpUtils.get("/account/getAccount",
                params, responseHandler);

    }


}
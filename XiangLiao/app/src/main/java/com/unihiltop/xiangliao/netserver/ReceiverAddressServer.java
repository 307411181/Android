package com.unihiltop.xiangliao.netserver;

import com.google.gson.Gson;
import com.unihiltop.xiangliao.bean.ReceiverAddress;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.net.OkHttpUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public class ReceiverAddressServer {

    /**
     * 编辑用户地址
     *
     * @param address
     * @param callback
     */
    public void updateReceiverAddress(ReceiverAddress address, NetMessageCallback callback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("receiverAddressJson", new Gson().toJson(address));
        OkHttpUtils.post("/ReceiverAddressController/updateReceiverAddress",
                params, callback);
    }

    /**
     *
     * @param accountId
     * @param callback
     */
    public void getDefaultReceiverAddress(long accountId, NetMessageCallback callback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId+"");
        OkHttpUtils.get("/ReceiverAddressController/getDefaultReceiverAddress",
                params, callback);
    }

    /**
     *
     * @param accountId
     * @param callback
     */
    public void getReceiverAddressList(long accountId, NetMessageCallback callback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId+"");
        OkHttpUtils.get("/ReceiverAddressController/getReceiverAddressList", params, callback);
    }

    /**
     * 添加用户地址
     *
     * @param accountId
     * @param address
     * @param callback
     */
    public void addReceiverAddress(long accountId, ReceiverAddress address, NetMessageCallback callback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId+"");
        params.put("receiverAddressJson", new Gson().toJson(address));
        OkHttpUtils.post("/ReceiverAddressController/addReceiverAddress",
                params, callback);
    }

    /**
     * 删除某个地址，如果该地址是用户的默认地址，把该用户的其他地址设为默认地址
     * @param receiverAddressId
     * @param callback
     */
    public void deleteReceiverAddress(long accountId, String receiverAddressId, NetMessageCallback callback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId+"");
        params.put("receiverAddressId", receiverAddressId);
        OkHttpUtils.post("/ReceiverAddressController/deleteReceiverAddress",
                params, callback);
    }

    /**
     * 设置用户的某个地址为默认地址，先把该用户的地址全部设置为false，在把该地址设为true
     * @param accountId
     * @param receiverAddressId
     * @param netMessageCallback
     */
    public void setDefaultAddress(long accountId, String receiverAddressId, NetMessageCallback netMessageCallback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId+"");
        params.put("receiverAddressId", receiverAddressId);
        OkHttpUtils.post("/ReceiverAddressController/addReceiverAddress",
                params, netMessageCallback);
    }
}

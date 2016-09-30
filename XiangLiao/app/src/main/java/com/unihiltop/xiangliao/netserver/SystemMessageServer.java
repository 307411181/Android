package com.unihiltop.xiangliao.netserver;

import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.net.OkHttpUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/1/27 0027.
 */
public class SystemMessageServer {
    /**
     *获取系统消息
     * @param
     * @param
     */
    public void getSystemMessageList(long preSystemMessageId, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        if (preSystemMessageId >0){
            params.put("preSystemMessageId", preSystemMessageId+"");
        }
        OkHttpUtils.get("/systemMessage/getSystemMessageList",
                params, responseHandler);

    }

    /**
     * 获取最新的系统消息
     */
    public void getRecentSystemMessage( NetMessageCallback responseHandler) {
        OkHttpUtils.get("/systemMessage/getRecentSystemMessage", responseHandler);

    }

}

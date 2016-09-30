package com.unihiltop.xiangliao.netserver;

import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.net.OkHttpUtils;

import java.util.HashMap;


/**
 * Created by yy on 2015/10/30.
 */
public class CallServer {
    /**
     * 拨打电话接口
     *
     * @param selfPhone       自己的号码
     * @param anotherPhone    对方号码
     * @param responseHandler
     */
    public void call(String selfPhone, String anotherPhone, NetMessageCallback responseHandler) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("selfPhone", selfPhone);
        params.put("anotherPhone", anotherPhone.replace("-", ""));
        OkHttpUtils.post("/CallController/callPerson",
                params, responseHandler);
    }



    /**
     * 上传通话记录(挂断)
     */
    public void uploadTheBill(long theBillId, NetMessageCallback responseHandler){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("theBillId", theBillId+"");
        OkHttpUtils.post("/theBill/uploadTheBill", params, responseHandler);
    }
}
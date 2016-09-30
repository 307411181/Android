package com.unihiltop.xiangliao.netserver;


import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.net.OkHttpUtils;

/**
 * Created by yangyang on 2015/10/26.
 */
public class AppVersionServer {
    /**
     * 检查app版本
     *
     *
     * @param responseHandler
     */
    public void getAppVersion( NetMessageCallback responseHandler) {
          OkHttpUtils.get("/version/getAppVersion",
                  responseHandler);
    }
}

package com.unihiltop.xiangliao.netserver;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.net.OkHttpUtils;

import java.util.HashMap;


/**
 * Created by yangyang on 2015/11/23.
 */
public class AdvertisementServer {
    /**
     * 广告
     * @param handler
     */
    public void getAdvertisementList(NetMessageCallback handler){
           HashMap<String, Object> params = new  HashMap<String, Object>();
//        params.put("client", 0);
          OkHttpUtils.get("/advertisement/getAdvertisementList", handler);
    }

}

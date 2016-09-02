package com.minze.shoppingmall.shoppingmall.app;

import android.app.Application;
import android.content.Context;

/**
 * @创建者 张京
 * @创建时间 2016/8/31 11:31
 * @描述 ${全局Application}
 */
public class MyApplication extends Application {
    /**
     * 全局的上下文.
     */
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }

    /**
     * 获取Context.
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }
}


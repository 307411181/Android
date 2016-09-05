package com.minze.shoppingmall.shoppingmall.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.minze.shoppingmall.shoppingmall.app.MyApplication;

/**
 * @创建者 张京
 * @创建时间 2016/8/31 12:11
 * @描述 ${关于SharedPreferences的操作}
 */
public class SpUtils {

    //配置文件的文件名
    public static final String CONFIG_FILE = "config";
    //    配置文件的key名
    public static final String CONFIG_KEY  = "isFristInto";

    /**
     * 提供的get方法,用于创建sp,指定的是"config"配置文件,根据CONFIG_KEY查找用户是否进入过引导页
     * @return
     */
    public static boolean getIsFirstInto() {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        boolean b = sp.getBoolean(CONFIG_KEY, false);
        return b;
    }

    /**
     * 为配置文件提供的set方法,当用户跳转到mainAcitivt之前,设置为true,确保用户下次不会重新进入引导页
     */
    public static void setIsFirstInto(String key, boolean isFirst) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        sp.edit().putBoolean(CONFIG_KEY, isFirst).commit();
    }

}

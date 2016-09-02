package com.minze.shoppingmall.shoppingmall.util;

import android.util.Log;

/**
 * 打印Log日志工具类
 *
 * @author Administrator
 *         <p/>
 *         开发初期只需要让常量LEVEL = VERBOSE的值即可
 *         这样做可以打印全部内容,
 *         <p/>
 *         比如只想打印INFO 及以上级别,则直接让 LEVEL = INFO即可
 *         <p/>
 *         <p/>
 *         项目上线后,不需要打印任何日志   则让LEVEl = NOTHING值即可.这样可以屏蔽任何日志
 */

public class LogUtil {

    private static final int VERBOSE = 1;
    private static final int DEBUG   = 2;
    private static final int INFO    = 3;
    private static final int WARN    = 4;
    private static final int ERROR   = 5;
    private static final int NOTHING = 6;
    private static final int LEVEL   = VERBOSE;

    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }

}

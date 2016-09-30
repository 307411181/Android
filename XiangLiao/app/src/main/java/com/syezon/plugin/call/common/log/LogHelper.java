//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.common.log;

import android.util.Log;

public class LogHelper {
    public static boolean DEBUG = false;
    public static boolean DEVELOPER_MODE = false;
    public static boolean EXTERNAL_MODE = false;
    public static boolean TO_FILE = false;

    public LogHelper() {
    }

    public static void d(String var0, String var1) {
        if(DEBUG) {
            Log.d(var0, var1);
        }

    }

    public static void d2f(String var0, String var1) {
        d(var0, var1);
    }

    public static void e(String var0, String var1) {
        if(DEBUG) {
            Log.e(var0, var1);
        }

    }

    public static void e2f(String var0, String var1) {
        e(var0, var1);
    }

    public static void i(String var0, String var1) {
        if(DEBUG) {
            Log.i(var0, var1);
        }

    }

    public static void i2f(String var0, String var1) {
        i(var0, var1);
    }

    public static void v(String var0, String var1) {
        if(DEBUG) {
            Log.v(var0, var1);
        }

    }

    public static void w(String var0, String var1) {
        if(DEBUG) {
            Log.w(var0, var1);
        }

    }

    public static void w2f(String var0, String var1) {
        w(var0, var1);
    }
}

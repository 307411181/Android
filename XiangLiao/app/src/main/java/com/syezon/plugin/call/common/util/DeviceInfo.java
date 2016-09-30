//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.common.util;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

public class DeviceInfo {
    public DeviceInfo() {
    }

    private static String CodeCompletion(String var0) {
        String var1 = var0;
        if(var0.length() == 1) {
            var1 = "000" + var0;
        } else {
            if(var0.length() == 2) {
                return "00" + var0;
            }

            if(var0.length() == 3) {
                return "0" + var0;
            }
        }

        return var1;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static int getDeviceHeight(Context var0) {
        return ((WindowManager)var0.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }

    public static String getDeviceSDKVersion() {
        return VERSION.SDK;
    }

    public static String getDeviceType() {
        return Build.MODEL;
    }

    public static int getDeviceWidth(Context var0) {
        return ((WindowManager)var0.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    public static String getIMSI(Context var0) {
        TelephonyManager var1 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
        String var2 = "";
        if(var1 != null) {
            var2 = var1.getSubscriberId();
            if(var2 == null || var2.length() < 15) {
                var2 = "";
            }
        }

        return var2;
    }

    public static String getResolution(Context var0) {
        String var1 = Integer.toHexString(getDeviceWidth(var0));
        String var2 = Integer.toHexString(getDeviceHeight(var0));
        StringBuilder var3 = new StringBuilder();
        var3.append("0x");
        if(var1.length() < 4) {
            var1 = CodeCompletion(var1);
        }

        var3.append(var1);
        if(var2.length() < 4) {
            var2 = CodeCompletion(var2);
        }

        var3.append(var2);
        return var3.toString();
    }

    public static String getSdkVersion(Context var0) {
        return VERSION.RELEASE;
    }

    public static String getWifiMacAddress(Context var0) {
        return ((WifiManager)var0.getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }
}

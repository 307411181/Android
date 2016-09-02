package com.minze.shoppingmall.shoppingmall.util;

import android.widget.Toast;

import com.minze.shoppingmall.shoppingmall.app.MyApplication;

/**
 * @创建者 张京
 * @创建时间 2016/8/31 13:29
 * @描述 ${Toast的工具类,,,实现单例模式}
 */
public class TostUtrils {
    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * Toast对象
     */
    private static Toast toast   = null;
    /**
     * 第一次时间
     */
    private static long  oneTime = 0;
    /**
     * 第二次时间
     */
    private static long  twoTime = 0;

    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (text.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = text;
                toast.setText(text);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
}

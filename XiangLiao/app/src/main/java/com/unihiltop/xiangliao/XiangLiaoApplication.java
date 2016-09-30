package com.unihiltop.xiangliao;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.unihiltop.xiangliao.constant.PreferenceKeyConstants;
import com.unihiltop.xiangliao.util.PreferencesHelper;

import org.xutils.x;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

public class XiangLiaoApplication extends Application implements Thread.UncaughtExceptionHandler {

    private List<Activity> activities;
    public static Context applicationContext;
    private static XiangLiaoApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        activities = new LinkedList<Activity>();
        instance = this;
        applicationContext = this;


        /**
         * 声明
         */
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(320, 320)
                .diskCacheExtraOptions(480, 480, null)
                .threadPoolSize(2)
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        /**
         * 获取错误的信息
         */
        Thread.setDefaultUncaughtExceptionHandler(this);
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志, 开启debug会影响性
    }

    /**
     * 获取错误的信息
     *
     * @param ex
     * @return
     */
    @Override
    public void uncaughtException(Thread arg0, Throwable ex) {
        String error = getErrorInfo(ex);
       // FileUtils.write(FilePathConstants.IMAGE_PATH + "/" + System.currentTimeMillis() + ".txt", error , true);
        PreferencesHelper.get(getApplicationContext()).put(PreferenceKeyConstants.KEY_ERROR, error);
        Toast.makeText(getApplicationContext(), "应用异常，重启应用将上传异常", Toast.LENGTH_LONG).show();
        System.exit(0);
    }

    /**
     * 获取错误的信息
     *
     * @param ex
     * @return
     */
    private String getErrorInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        Log.i("Application", "error=" + error);
        return error;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void exit() {
        for (Activity activity : activities) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static XiangLiaoApplication getInstance() {
        return instance;
    }

}
package com.minze.shoppingmall.shoppingmall.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.util.LogUtil;

import butterknife.ButterKnife;

/**
 * @创建者 张京
 * @创建时间 2016/8/31 13:55
 * @描述 ${TODO}
 */
public abstract class BaseActivity extends FragmentActivity {

    /**
     * 全局上下文对象,与application里定义的为同一个对象
     * 子类可以直接使用
     */
    public static Context mContext;
    private String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //写死横竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        LogUtil.e(TAG, "onCreate");
        setContentView(savedInstanceState);
        ButterKnife.inject(this);
        initData();
        settingViewOrAdapter();
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onResume();
    }

    /**
     * 设置所需要的布局文件即可
     */
    public abstract void setContentView(Bundle bundle);

    /**
     * 初始化数据,设置一些图片或者网络请求逻辑
     */
    public abstract void initData();

    /**
     * 设置控件要执行的方法,如点击,设置文字,设置内容   还有适配器等
     */
    public abstract void settingViewOrAdapter();


    /**
     * 父类提供的方法,用于快速跳转新的Activity,子类只需要传入目标Activity.class即可
     *
     * @param clazz
     */
    public void startAnActivity(Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        startActivity(intent);
    }

    /**
     * 得到v4包下FragmentManager方法
     *
     * @return
     */
    public FragmentManager getV4FragmentManager() {
        return getSupportFragmentManager();
    }

    /**
     * 创建新Fragment方法,不带标签
     *
     * @param containerViewId  :布局文件Id
     * @param fragment  :fragment对象
     */
    public void greateNewFragment(int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }
    /**
     * 创建新Fragment方法,带标签
     *
     * @param containerViewId  :布局文件Id
     * @param fragment  :fragment对象
     */
    public void greateNewFragment(int containerViewId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, tag).commit();
    }
}

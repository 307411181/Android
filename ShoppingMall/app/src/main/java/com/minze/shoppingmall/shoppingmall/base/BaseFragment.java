package com.minze.shoppingmall.shoppingmall.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @创建者 张京
 * @创建时间 2016/9/7 9:45
 * @描述 ${TODO}
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 上下文的Activity
     */
    public Activity       mActivity;
    /**
     * 用于布局填充器
     */
    public LayoutInflater mInflater;
    /**
     * 用于返回的View视图页面
     */
    public View           mRootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mInflater = inflater;
        mRootView = rootView();
        initView();
        initData();
        initAdapter();
        return mRootView;
    }

    /**
     * 重写方法,用于返回Fragment的布局
     *
     * @return
     */
    public abstract View rootView();

    /**
     * 初始化控件,可以做一些findviewbyid()的工作
     */
    protected abstract void initView();

    /**
     * 初始化数据,可以作为请求网络或者设置数据等
     */
    protected abstract void initData();

    /**
     * 设置布局效果,比如listview,gridview适配器等
     */
    protected abstract void initAdapter();
}

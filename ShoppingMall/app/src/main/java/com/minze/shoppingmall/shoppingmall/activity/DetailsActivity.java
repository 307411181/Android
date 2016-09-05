package com.minze.shoppingmall.shoppingmall.activity;

import android.os.Bundle;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.base.BaseActivity;
import com.minze.shoppingmall.shoppingmall.view.TitleBar;

/**
 * @创建者 张京
 * @创建时间 2016/9/5 10:23
 * @描述 ${详情页}
 */
public class DetailsActivity extends BaseActivity {
    private TitleBar mTitleBar;

    @Override
    public void setContentView(Bundle bundle) {
        setContentView(R.layout.activity_details);
        mTitleBar = (TitleBar) findViewById(R.id.details_title_bar);
        //设置标题栏内容
        mTitleBar.setLeftButtonImage(R.mipmap.main_left_button);
        mTitleBar.setRightButtonImage(R.mipmap.details_fenxiang_icon);
        mTitleBar.setTitleText("绿谷超市");
    }

    @Override
    public void initData() {


    }

    @Override
    public void settingViewOrAdapter() {

    }
}

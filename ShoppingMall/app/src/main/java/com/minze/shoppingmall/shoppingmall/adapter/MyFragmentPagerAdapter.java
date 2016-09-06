package com.minze.shoppingmall.shoppingmall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 商品列表ViewPager+Fragment适配器
 * Created by Administrator on 2016/9/2.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    FragmentManager mFragmentManager;
    List<Fragment> fragmentList;
    List<String> titleList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        mFragmentManager = fm;
        this.fragmentList = fragmentList;
        this.titleList = titleList;

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

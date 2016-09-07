package com.minze.shoppingmall.shoppingmall.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.adapter.MyFragmentPagerAdapter;
import com.minze.shoppingmall.shoppingmall.base.BaseActivity;
import com.minze.shoppingmall.shoppingmall.fragment.GoodsGridFragment;
import com.minze.shoppingmall.shoppingmall.fragment.GoodsListFragment;
import com.minze.shoppingmall.shoppingmall.util.TostUtrils;
import com.minze.shoppingmall.shoppingmall.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品列表
 */
public class GoodsListActivity extends BaseActivity {
    private TabLayout            mTabLayout;
    private ViewPager            mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    private List<Fragment> mFragmentList;
    private List<String>   mStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        initListData();
        initView();

    }

    @Override
    public void setContentView(Bundle bundle) {
        TitleBar bar = (TitleBar) findViewById(R.id.title_bar);
        bar.setTitleText(getResources().getString(R.string.goodslist));
        bar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TostUtrils.showToast("BACK");
            }
        });
        bar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TostUtrils.showToast("购物车");
            }
        });
    }

    @Override
    public void initData() {

    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.activity_goods_list_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.activity_goods_list_viewpager);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList, mStringList);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initListData() {
        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new GoodsListFragment());
        mFragmentList.add(new GoodsGridFragment());
        mStringList = new ArrayList<String>();
        mStringList.add("List");
        mStringList.add("GRID");
    }

    @Override
    public void settingViewOrAdapter() {

    }
}

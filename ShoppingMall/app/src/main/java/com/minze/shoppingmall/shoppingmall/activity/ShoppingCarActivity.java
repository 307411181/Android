package com.minze.shoppingmall.shoppingmall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.base.BaseActivity;
import com.minze.shoppingmall.shoppingmall.fragment.BeforeFragment;
import com.minze.shoppingmall.shoppingmall.fragment.NewFragment;
import com.minze.shoppingmall.shoppingmall.view.TitleBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @创建者 张京
 * @创建时间 2016/9/6 14:36
 * @描述 ${购物车Activity}
 */
public class ShoppingCarActivity extends BaseActivity {
    @InjectView(R.id.shop_car_title_bar)
    TitleBar    mShopCarTitleBar;
    @InjectView(R.id.rb_shopcar_new)
    RadioButton mRbShopcarNew;
    @InjectView(R.id.rb_shopcar_before)
    RadioButton mRbShopcarBefore;
    @InjectView(R.id.rg_shopcar)
    RadioGroup  mRgShopcar;
    @InjectView(R.id.view1)
    View        mView1;
    @InjectView(R.id.view_tab1)
    View        mViewTab1;
    @InjectView(R.id.view_tab2)
    View        mViewTab2;
    @InjectView(R.id.fl_shopping_car)
    FrameLayout mFlShoppingCar;
    private TitleBar mTitleBar;

    private NewFragment    newFragment;
    private BeforeFragment beforeFragment;

    @Override
    public void setContentView(Bundle bundle) {
        setContentView(R.layout.activity_shopping_car);
        mTitleBar = (TitleBar) findViewById(R.id.shop_car_title_bar);
        mTitleBar.setLeftButtonImage(R.mipmap.main_right_button);
        mTitleBar.setRightButtonImage(R.mipmap.shopping_car_titlebar_right_icon);
        mTitleBar.setTitleText("购物车");
        newFragment = new NewFragment();
        beforeFragment = new BeforeFragment();


    }

    @Override
    public void initData() {

    }

    /**
     * 设置一上来默认选中第一个按钮,并显示第一个Activity
     */
    @Override
    public void settingViewOrAdapter() {

        mRbShopcarNew.setChecked(true);
        greateNewFragment(R.id.fl_shopping_car, newFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.rb_shopcar_new, R.id.rb_shopcar_before})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_shopcar_new:
                mViewTab1.setVisibility(View.VISIBLE);
                mViewTab2.setVisibility(View.INVISIBLE);
                greateNewFragment(R.id.fl_shopping_car, newFragment);
                break;
            case R.id.rb_shopcar_before:
                mViewTab1.setVisibility(View.INVISIBLE);
                mViewTab2.setVisibility(View.VISIBLE);
                greateNewFragment(R.id.fl_shopping_car, beforeFragment);
                break;
        }
    }


    public void setTitlebarIcon(int image){
        mTitleBar.setRightButtonImage(image);
    }
}

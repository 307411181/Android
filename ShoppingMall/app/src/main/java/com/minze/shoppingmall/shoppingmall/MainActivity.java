package com.minze.shoppingmall.shoppingmall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.minze.shoppingmall.shoppingmall.activity.DetailsActivity;
import com.minze.shoppingmall.shoppingmall.activity.GoodsListActivity;
import com.minze.shoppingmall.shoppingmall.activity.LoginActivity;
import com.minze.shoppingmall.shoppingmall.activity.RegisterActivity;
import com.minze.shoppingmall.shoppingmall.activity.SearchActivity;
import com.minze.shoppingmall.shoppingmall.activity.ShoppingCarActivity;
import com.minze.shoppingmall.shoppingmall.base.BaseActivity;
import com.minze.shoppingmall.shoppingmall.util.TostUtrils;
import com.minze.shoppingmall.shoppingmall.view.TitleBar;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 主页面,包括一些数据额展示,以及登录注册跳转
 */
public class MainActivity extends BaseActivity {

    @InjectView(R.id.iv_main_item1_one)
    ImageView   mIvMainItem1One;
    @InjectView(R.id.iv_main_item1_two)
    ImageView   mIvMainItem1Two;
    @InjectView(R.id.iv_main_item1_three)
    ImageView   mIvMainItem1Three;
    @InjectView(R.id.iv_main_item1_four)
    ImageView   mIvMainItem1Four;
    @InjectView(R.id.iv_main_item2_one)
    ImageView   mIvMainItem2One;
    @InjectView(R.id.iv_main_item2_two)
    ImageView   mIvMainItem2Two;
    @InjectView(R.id.iv_main_item2_three)
    ImageView   mIvMainItem2Three;
    @InjectView(R.id.iv_main_item2_four)
    ImageView   mIvMainItem2Four;
    @InjectView(R.id.iv_main_item3_one)
    ImageView   mIvMainItem3One;
    @InjectView(R.id.iv_main_item3_two)
    ImageView   mIvMainItem3Two;
    @InjectView(R.id.iv_main_item3_three)
    ImageView   mIvMainItem3Three;
    @InjectView(R.id.iv_main_touxiang)
    ImageView   mIvMainTouxiang;
    @InjectView(R.id.btn_main_login)
    Button      mBtnMainLogin;
    @InjectView(R.id.ib_main_bag)
    ImageButton mIbMainBag;
    @InjectView(R.id.btn_main_register)
    Button      mBtnMainRegister;
    /**
     * 盛放资源图片id的数组
     */
    private int[] imageId = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};

    /**
     * 储存viewpager五张图片的集合
     */
    private ArrayList<ImageView> mImageViewList;

    @InjectView(R.id.viewpager_main)
    ViewPager    mViewpagerMain;
    @InjectView(R.id.ll_point_container)
    LinearLayout mLlPointContainer;
    @InjectView(R.id.btn_main_tiantiantuan)
    Button       mBtnMainTiantiantuan;
    @InjectView(R.id.btn_main_tejia)
    Button       mBtnMainTejia;
    @InjectView(R.id.btn_main_coujiangjifen)
    Button       mBtnMainCoujiangjifen;
    @InjectView(R.id.btn_main_saoyisao)
    Button       mBtnMainSaoyisao;
    // 用于记录当前是第几个Pager
    private int previousSelectedPosition = 0;
    // 是否可以自动滚动
    boolean isAutoPlay = false;

    @Override
    public void setContentView(Bundle bundle) {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        TitleBar bar = (TitleBar) findViewById(R.id.title_bar);
        bar.setTitleText("我是更改的主页面哟");
        bar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TostUtrils.showToast("我在主页面点击了Menu");

            }
        });
        bar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TostUtrils.showToast("主页面点击了Find");
                startAnActivity(SearchActivity.class);
            }
        });

        //        bar.setLeftButtonImage(R.mipmap.main_find);

    }


    @Override
    public void initData() {
        // 初始化要展示的5个ImageView集合
        mImageViewList = new ArrayList<ImageView>();
        ImageView imageView;// 每一个Pager是一个ImageView,所以设置imageView的显示图片
        View pointView;// 底部的小圆点,,,需要根据ViewPager的子Pager个数动态添加
        LinearLayout.LayoutParams layoutParams;
        // 根据有图片集合的个数动态添加底部指示器(小圆点)
        for (int i = 0; i < imageId.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageId[i]);
            mImageViewList.add(imageView);// 把imageView放入到集合中

            // 加小白点, 指示器
            pointView = new View(this);
            // 背景颜色为"选择器"控制
            pointView.setBackgroundResource(R.drawable.selecor_bg_point);
            // 设置圆点的 宽, 高为30像素
            layoutParams = new LinearLayout.LayoutParams(20, 40);
            /*
             * 如果不为第一个圆点,则距离左边 20dip
			 */
            if (i != 0)
                layoutParams.leftMargin = 20;

            // 设置默认所有都不可用
            pointView.setEnabled(false);
            // 吧设置好的圆点和其属性添加到LinearLayout中
            mLlPointContainer.addView(pointView, layoutParams);
        }
          /*
         * 拿到放置小圆点的Linearlayout布局中第一个自View,设置为tru
		 */
        mLlPointContainer.getChildAt(0).setEnabled(true);// 设置默认第一个小圆点选中
    }

    @Override
    public void settingViewOrAdapter() {
        // 设置适配器
        mViewpagerMain.setAdapter(new MainPagerAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2
                - (Integer.MAX_VALUE / 2 % mImageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        mViewpagerMain.setCurrentItem(5000000); // 设置到某个位置

        mViewpagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 这样设计不会造成循环播放的时候角标越界永远是 0 - 4
                int newPosition = position % mImageViewList.size();

                // 把之前的禁用, 把最新的启用, 更新指示器
                mLlPointContainer.getChildAt(previousSelectedPosition)
                        .setEnabled(false);
                mLlPointContainer.getChildAt(newPosition).setEnabled(true);

                // 记录之前的位置
                previousSelectedPosition = newPosition;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        isRunning();

    }

    /**
     * 设置轮播图是否循环播放
     */
    private void isRunning() {
        // 开启轮询
        new Thread() {
            public void run() {
                isAutoPlay = true;
                while (isAutoPlay) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 跳到下一个Pager
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mViewpagerMain.setCurrentItem(mViewpagerMain.getCurrentItem() + 1);
                        }
                    });
                }
            }

            ;
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    class MainPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int newPosition = position % mImageViewList.size();
            ImageView imageView = mImageViewList.get(newPosition);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TostUtrils.showToast("点击了第" + newPosition);
                }
            });
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    @OnClick({R.id.btn_main_tiantiantuan, R.id.btn_main_tejia, R.id.btn_main_coujiangjifen, R.id.btn_main_saoyisao, R.id.iv_main_item1_one, R.id.iv_main_item1_two, R.id.iv_main_item1_three, R.id.iv_main_item1_four, R.id.iv_main_item2_one, R.id.iv_main_item2_two, R.id.iv_main_item2_three, R.id.iv_main_item2_four, R.id.iv_main_item3_one, R.id.iv_main_item3_two, R.id.iv_main_item3_three, R.id.iv_main_touxiang, R.id.btn_main_login, R.id.ib_main_bag, R.id.btn_main_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_tiantiantuan:
                TostUtrils.showToast("天天团");
                startAnActivity(GoodsListActivity.class);
                break;
            case R.id.btn_main_tejia:
                TostUtrils.showToast("特价");
                break;
            case R.id.btn_main_coujiangjifen:
                TostUtrils.showToast("积分充值");
                break;
            case R.id.btn_main_saoyisao:
                TostUtrils.showToast("扫一扫");
                break;
            case R.id.iv_main_item1_one:
                TostUtrils.showToast("布局1-item1");
                break;
            case R.id.iv_main_item1_two:
                TostUtrils.showToast("布局1-item2");
                break;
            case R.id.iv_main_item1_three:
                TostUtrils.showToast("布局1-item3");
                break;
            case R.id.iv_main_item1_four:
                TostUtrils.showToast("布局1-item4");
                break;
            case R.id.iv_main_item2_one:
                TostUtrils.showToast("布局2-item1");
                break;
            case R.id.iv_main_item2_two:
                TostUtrils.showToast("布局2-item2");
                break;
            case R.id.iv_main_item2_three:
                TostUtrils.showToast("布局2-item3");
                break;
            case R.id.iv_main_item2_four:
                TostUtrils.showToast("布局2-item4");
                break;
            case R.id.iv_main_item3_one:
                TostUtrils.showToast("布局3-item1");
                break;
            case R.id.iv_main_item3_two:
                TostUtrils.showToast("布局3-item2");
                break;
            case R.id.iv_main_item3_three:
                TostUtrils.showToast("布局3-item3");
                break;
            case R.id.iv_main_touxiang:
                TostUtrils.showToast("头像");
                startAnActivity(DetailsActivity.class);
                break;
            case R.id.btn_main_login:
                //                TostUtrils.showToast("跳转登陆");
                Intent loginIntent = new Intent(mContext, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.btn_main_register:
                TostUtrils.showToast("跳转注册");
                Intent RegisterIntent = new Intent(mContext, RegisterActivity.class);
                startActivity(RegisterIntent);
                break;
            case R.id.ib_main_bag:
                TostUtrils.showToast("点击了袋子");
                startAnActivity(ShoppingCarActivity.class);
                break;
        }
    }

    /**
     * 生命周期方法,页面关闭,让循环广告页关闭
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAutoPlay = false;
    }
}

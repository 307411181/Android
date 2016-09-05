package com.minze.shoppingmall.shoppingmall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.minze.shoppingmall.shoppingmall.app.MyApplication;
import com.minze.shoppingmall.shoppingmall.util.SpUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @创建者 张京
 * @创建时间 2016/8/31 20:39
 * @描述 ${新手引导页,使用viewPger实现4个滑动的页面,最后一个页面中点击进入按钮进入主页,新手引导页只显示一次}
 */
public class GuideActivity extends Activity {

    @InjectView(R.id.viewpager_guide)
    ViewPager mViewPager;
    @InjectView(R.id.btn_main)
    Button    mBtnMain;
    //引导页资源id
    private int[] mImageId = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3, R.mipmap.guide_1};
    //ImageView的集合
    private ArrayList<ImageView> mImageList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);

        //        根据sp文件的值判断用户是否已经进入过引导页
        isFirstIntoGuideActivity();

        //初始化数据
        initData();

        //设置viewpager适配器
        mViewPager.setAdapter(new MainAdapter());
        //设置viewpager页面变化监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //为最后一个页面时,按钮显示,否则为隐藏状态
                if (position == mImageList.size() - 1) {
                    mBtnMain.setVisibility(View.VISIBLE);
                } else {
                    mBtnMain.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    /**
     * 初始化引导页的四张图片
     */
    private void initData() {
        //初始化iamgeView集合
        mImageList = new ArrayList<ImageView>();
        for (int i = 0; i < mImageId.length; i++) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(mImageId[i]);
            mImageList.add(imageView);


        }

    }

    /**
     * 判断是否已经进入过引导页,判断sp文件,
     * true表示已经进入,则跳转MainActivity
     * false表示还没进入过,则执行下面逻辑
     */
    public void isFirstIntoGuideActivity() {
        boolean isFirstInto = SpUtils.getIsFirstInto();
        if (isFirstInto) {
            startActivity(new Intent(MyApplication.getContext(), MainActivity.class));
            finish();
        } else {
            return;
        }
    }

    /**
     * 引导页的适配器
     */
    class MainAdapter extends PagerAdapter {

        //返回页面个数
        @Override
        public int getCount() {
            return mImageList.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //初始化
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImageList.get(position);
            container.addView(imageView);
            return imageView;
        }

        //销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //按钮点击事件
    @OnClick(R.id.btn_main)

    public void onClick() {
        //跳转至主页面
        Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
        startActivity(intent);
        //设置第一次进入的sp文件为true,保证下次不再进入"引导页"
        SpUtils.setIsFirstInto(SpUtils.CONFIG_KEY, true);
        //结束当前页面
        finish();
    }
}

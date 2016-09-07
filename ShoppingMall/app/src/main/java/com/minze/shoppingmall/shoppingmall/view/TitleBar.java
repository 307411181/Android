package com.minze.shoppingmall.shoppingmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minze.shoppingmall.shoppingmall.R;

/**
 * @创建者 张京
 * @创建时间 2016/9/1 10:20
 * @描述 ${抽取的TitleBar自定义布局}
 */
public class TitleBar extends RelativeLayout {

    private ImageButton    leftButton;//左边的图片按钮
    private ImageButton    rightButton;//右边按钮
    private TextView       titleText;//中间TitleBar标题文字
    private RelativeLayout mRootView;//最外层布局

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //添加布局
        LayoutInflater.from(context).inflate(R.layout.layout_main_title_bar, this);

        mRootView = (RelativeLayout) findViewById(R.id.title_bar_rootView);
        leftButton = (ImageButton) findViewById(R.id.title_bar_leftButton);
        rightButton = (ImageButton) findViewById(R.id.title_bar_rightButton);
        titleText = (TextView) findViewById(R.id.title_bar_titleText);

    }


    /**
     * 向外界暴露的更改标题文字的方法
     */
    public void setTitleText(String title) {
        titleText.setText(title);
    }

    /**
     * 向外界提供更改Menu按钮逻辑的方法
     */
    public void setLeftButtonOnClickListener(OnClickListener listener) {
        leftButton.setOnClickListener(listener);
    }

    /**
     * 向外界提供更改find按钮逻辑的方法
     */
    public void setRightButtonOnClickListener(OnClickListener listener) {
        rightButton.setOnClickListener(listener);
    }

    /**
     * 设置左侧按钮是否显示
     */
    public void setLeftButtonVisibility(int isVisible) {
        leftButton.setVisibility(isVisible);
    }

    /**
     * 设置右侧按钮是否显示
     */
    public void setRightButtonVisibility(int isVisible) {
        rightButton.setVisibility(isVisible);
    }

    /**
     * 设置标题栏左侧按钮图标方法
     */
    public void setLeftButtonImage(int image){
        leftButton.setBackgroundResource(image);
    }

    /**
     * 设置标题栏右侧按钮图标方法
     */
    public void setRightButtonImage(int image){
        rightButton.setBackgroundResource(image);
    }

}

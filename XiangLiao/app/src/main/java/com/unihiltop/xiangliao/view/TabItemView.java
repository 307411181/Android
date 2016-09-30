package com.unihiltop.xiangliao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;


public class TabItemView extends RelativeLayout {

    private ImageView ivIcon;
    private TextView tvName;
    private int[] icons = null;
    private boolean isSelected = false;
    private int[] textColorResids;
    public TabItemView(Context context){
        super(context);
        initView(context);
    }
    public TabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_tab_item, this, true);
        ivIcon = (ImageView) view.findViewById(R.id.imageView_icon);
        tvName = (TextView) view.findViewById(R.id.TextView_tab_name);
    }

    public void setTitle(String title){
        tvName.setText(title);
    }

    public void setTextColor(int[] textColorResids){
        this.textColorResids = textColorResids;
        tvName.setTextColor(getResources().getColor(textColorResids[0]));
    }

    public void setTitle(int resId){
        tvName.setText(resId);
    }

    public void setIcon(int[] icons){
        if (!isSelected) {
            ivIcon.setImageResource(icons[0]);
        }else {
            ivIcon.setImageResource(icons[1]);
        }
        this.icons = icons;
    }

    public void isSelected(boolean isSelected){
        this.isSelected = isSelected;
        if (isSelected) {
            if (icons != null) {
                ivIcon.setImageResource(icons[1]);
            }
            tvName.setTextColor(getResources().getColor(textColorResids[1]));
        } else {
            if (icons != null) {
                ivIcon.setImageResource(icons[0]);
            }
            tvName.setTextColor(getResources().getColor(textColorResids[0]));
        }
    }
    public void setTextSize(int textSize) {
        tvName.setTextSize( TypedValue.COMPLEX_UNIT_SP, textSize);
    }
}

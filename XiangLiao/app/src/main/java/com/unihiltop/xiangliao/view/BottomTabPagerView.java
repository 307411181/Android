package com.unihiltop.xiangliao.view;


import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.unihiltop.xiangliao.R;


public class BottomTabPagerView extends RelativeLayout{

	private TabChooseView tabChooseView;
	private ViewPager viewPager;
	private int selectedPosition;
	private OnSelectedClickListener onSelectedClickListener;
	public BottomTabPagerView(Context context) {
		super(context);
		initView();
		initListener();
	}

	public BottomTabPagerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		initListener();
	}

	private void initView() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.view_bottom_tab_pager, this, true);
		tabChooseView = (TabChooseView) view.findViewById(R.id.tabChooseView);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
	}

	private void initListener() {
		tabChooseView.setOnSelectChangeListener(new TabChooseView.OnSelectChangeListener() {
			@Override
			public void selectChange(int selected) {
				if (selectedPosition == selected){
					return;
				}
				viewPager.setCurrentItem(selected);
				if (onSelectedClickListener != null) {
					onSelectedClickListener.onSelected(selectedPosition, selected);
				}
				selectedPosition = selected;

			}
		});
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
			@Override
			public void onPageSelected(int position) {
				if (selectedPosition == position){
					return;
				}
				tabChooseView.setSelected(position);
				if (onSelectedClickListener != null) {
					onSelectedClickListener.onSelected(selectedPosition, position);
				}
				selectedPosition = position;
			}
		});
	}

	public int getSelectedPosition(){
		return selectedPosition;
	}

	/**
	 * 初始�?
	 * @param titleArrayRes 选项的标�?
	 * @param
	 * @param
	 */
	public void init(int[][] icons, int titleArrayRes, FragmentPagerAdapter adapter){
		tabChooseView.setIcon(icons);
		tabChooseView.setTitle(titleArrayRes);
		viewPager.setAdapter(adapter);
	}

	public void setOnSelectedClickListener(OnSelectedClickListener l){
		onSelectedClickListener = l;
	}

	public interface OnSelectedClickListener{
		void onSelected(int oldSelected, int selected);
	}

	public void setTabTextSize(int textSize) {
		tabChooseView.setTabTextSize(textSize);
	}
}

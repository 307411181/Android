package com.unihiltop.xiangliao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.unihiltop.xiangliao.R;


public class TabChooseView extends LinearLayout implements OnClickListener{
	public static final int STYLE_BOTTOM = 0;
	public static final int STYLE_TOP = 1;
	private LinearLayout llTabChoose;
	private TabItemView[] tabItemViews;
	private OnSelectChangeListener selectChangeListener = null;
	private int selected = 0;
	private int[][] icons;
	private int[] textColorResids = {R.color.gray, R.color.title};
	private int textSize = 10;
	public TabChooseView(Context context) {
		super(context);
		initView();
	}
	public TabChooseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	private void initView() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.view_tab_choose, this, true);
		llTabChoose = (LinearLayout)view.findViewById(R.id.LinearLayout_tabChoose);
	}
	private void initListener() {
		for (TabItemView tabItemView: tabItemViews) {
			tabItemView.setOnClickListener(this);
		}

	}

	public void setIcon(int[][] icons){
		this.icons = icons;
	}

	public void setTextColor(int[] textColorResids){

		this.textColorResids = textColorResids;
	}
	public void setSelected(int selected){
		if (this.selected == selected) {
			return;
		} else {
			tabItemViews[this.selected].isSelected(false);
			tabItemViews[selected].isSelected(true);
			this.selected = selected;
		}
	}

	public void setOnSelectChangeListener(OnSelectChangeListener l){

		this.selectChangeListener = l;
	}

	public void setTitle(String[] titles){
		int num = titles.length;
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, 1);
		tabItemViews = new TabItemView[num];
		for (int i = 0; i < num; i++) {
			tabItemViews[i] = new TabItemView(getContext());
			tabItemViews[i].setIcon(icons[i]);
			tabItemViews[i].setTitle(titles[i]);
			tabItemViews[i].setTextColor(textColorResids);
			tabItemViews[i].setTextSize(textSize);
			llTabChoose.addView(tabItemViews[i], params);
		}
		tabItemViews[selected].isSelected(true);
		initListener();
	}

	public void setTitle(int titleArrayRes){
		String[] titles = getResources().getStringArray(titleArrayRes);
		int num = titles.length;
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1);
		tabItemViews = new TabItemView[num];
		for (int i = 0; i < num; i++) {
			tabItemViews[i] = new TabItemView(getContext());
			tabItemViews[i].setIcon(icons[i]);
			tabItemViews[i].setTitle(titles[i]);
			tabItemViews[i].setTextColor(textColorResids);
			tabItemViews[i].setTextSize(textSize);
			llTabChoose.addView(tabItemViews[i], params);
		}
		tabItemViews[selected].isSelected(true);
		initListener();
	}

	public void setTitle(int[] titles){
		for (int i = 0; i < titles.length; i++) {
			tabItemViews[i].setTitle(titles[i]);
		}
	}

	public interface OnSelectChangeListener{
		/**
		 * 当前选中的项
		 * @param selected
		 */
		public void selectChange(int selected);
	}

	@Override
	public void onClick(View v) {
		for (int i = 0; i < tabItemViews.length; i++) {
			if (v == tabItemViews[i]) {
				if (selected == i) {
					return;
				}else {
					setSelected(i);
					if (selectChangeListener != null) {
						selectChangeListener.selectChange(i);
					}
					break;
				}

			}
		}

	}
	public void setTabTextSize(int textSize) {
		if(tabItemViews != null){
			for (TabItemView tabItemView:tabItemViews) {
				tabItemView.setTextSize(textSize);
			}
		}
		this.textSize  =textSize;
	};

}

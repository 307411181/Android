package com.minze.shoppingmall.shoppingmall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.activity.ShoppingCarActivity;
import com.minze.shoppingmall.shoppingmall.adapter.BeforeFragmentAdapter;
import com.minze.shoppingmall.shoppingmall.base.BaseFragment;
import com.minze.shoppingmall.shoppingmall.bean.New;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @创建者 张京
 * @创建时间 2016/9/7 9:45
 * @描述 ${TODO}
 */
public class BeforeFragment extends BaseFragment {
    private    ListView       mListView;
    private ArrayList<New> list;
    @Override
    public View rootView() {
        return mInflater.inflate(R.layout.fragment_shopping_car_before, null);
    }

    @Override
    protected void initView() {
        mListView = (ListView) mRootView.findViewById(R.id.lv_before_fragment);
        ShoppingCarActivity activity = (ShoppingCarActivity) getActivity();
        activity.setTitlebarIcon(R.mipmap.shopping_car_have_icon);
    }

    @Override
    protected void initData() {
        list = new ArrayList<New>();
        for (int i = 0; i < 10; i++) {
            list.add(new New("标题标题" + i, "说明说明" + i));
        }
    }

    @Override
    protected void initAdapter() {
        BeforeFragmentAdapter adapter = new BeforeFragmentAdapter(mActivity, list);
        mListView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

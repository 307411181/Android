package com.minze.shoppingmall.shoppingmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.adapter.GoodListFragmentAdapter;


/**
 * 商品列表list页面
 * Created by Administrator on 2016/9/2.
 */

public class GoodsListFragment extends Fragment {
    private View rootView;
    private ListView mListView;
    private GoodListFragmentAdapter mGoodListFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_goodslist, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        mListView = (ListView) rootView.findViewById(R.id.fragment_goodslist_listview);
        mGoodListFragmentAdapter = new GoodListFragmentAdapter(getActivity());
        mListView.setAdapter(mGoodListFragmentAdapter);
    }
}

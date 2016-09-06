package com.minze.shoppingmall.shoppingmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.adapter.GoodGridFragmentAdapter;

/**
 * 商品列表grid页面
 * Created by Administrator on 2016/9/2.
 */

public class GoodsGridFragment extends Fragment {
    private View rootView;
    private GridView mGridView;
    private GoodGridFragmentAdapter mGoodGridFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_goodsgrid, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        mGridView = (GridView) rootView.findViewById(R.id.fragment_goodsgrid_gridview);
        mGoodGridFragmentAdapter = new GoodGridFragmentAdapter(getActivity());
        mGridView.setAdapter(mGoodGridFragmentAdapter);
    }
}

package com.minze.shoppingmall.shoppingmall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.activity.DetailsActivity;
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

        //点击每一项,暂时先跳转商品详情页
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}

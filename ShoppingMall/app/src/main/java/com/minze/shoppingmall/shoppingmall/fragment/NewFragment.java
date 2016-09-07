package com.minze.shoppingmall.shoppingmall.fragment;

import android.view.View;
import android.widget.ListView;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.adapter.NewFragmentAdapter;
import com.minze.shoppingmall.shoppingmall.base.BaseFragment;
import com.minze.shoppingmall.shoppingmall.bean.New;

import java.util.ArrayList;

/**
 * @创建者 张京
 * @创建时间 2016/9/7 9:45
 * @描述 ${购物车下的首个标签页}
 */
public class NewFragment extends BaseFragment {
    private ListView       mListView;
    private ArrayList<New> list;

    @Override
    public View rootView() {
        return mInflater.inflate(R.layout.fragment_shopping_car_new, null);
    }

    @Override
    protected void initView() {
        mListView = (ListView) mRootView.findViewById(R.id.lv_new_fragment);
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
        NewFragmentAdapter adapter = new NewFragmentAdapter(mActivity, list);
        mListView.setAdapter(adapter);
    }


}

package com.unihiltop.xiangliao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unihiltop.xiangliao.adapter.AdapterSearchResult;
import com.unihiltop.xiangliao.bean.TheBill;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.pulltorefresh.library.PullToRefreshBase;
import com.unihiltop.xiangliao.pulltorefresh.library.PullToRefreshListView;

import java.util.LinkedList;
import java.util.List;

/**
 * 查询结果
 * Created by yangyang on 2015/10/14.
 */
public class SearchResulatActivity extends Activity{
    private ImageView ivReturn;//返回
    private PullToRefreshListView lvSearchResulat;//查询结果
    private Long starttime;
    private Long endtime;
    private List<TheBill> bills;
    private String mobile;
    private TheBill theBill;
    private AdapterSearchResult adapterSearchResult;
    private long theBillId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resulat);
        initData();
        initView();
        initsetView();
        initListenter();
    }
    private void initData() {
         theBill = new TheBill();
         bills = new LinkedList<TheBill>();
        theBillId = theBill.id;
         starttime = getIntent().getLongExtra("startime",0);
         endtime = getIntent().getLongExtra("endtime",0);
         mobile = UserData.getData(SearchResulatActivity.this).getAccountName();
         adapterSearchResult = new AdapterSearchResult(SearchResulatActivity.this,bills);
         initResukat();
    }
    private void initView() {

        ivReturn = (ImageView) findViewById(R.id.imageView_return);

        lvSearchResulat = (PullToRefreshListView) findViewById(R.id.listView_search_resulat);
        lvSearchResulat.setMode(PullToRefreshBase.Mode.BOTH);
        lvSearchResulat.setAdapter(adapterSearchResult);
    }

    private void initsetView() {
        //new AccountServer().searchTheBill();
      //  lvSearchResulat.setAdapter(adapterSearchResult);
    }
    private void initListenter() {
        MyListenter myListenter = new MyListenter();
        ivReturn.setOnClickListener(myListenter);
        //上下拉刷新
        lvSearchResulat.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                initResukat();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
//                lvStore.clearFocus();
//                lvStore.clearChildFocus(refreshView);
//                lvStore.onRefreshComplete();
                getMoreResukat();
            }

        });

    }
    private void initResukat(){
      new AccountServer().searchTheBill(mobile, -1l, starttime, endtime, new NetMessageCallback() {
          @Override
          public void onFailure(String error, String message) {
              adapterSearchResult.notifyDataSetChanged();
              Toast.makeText(getApplicationContext(),
                      message, Toast.LENGTH_LONG).show();
          }

          @Override
          public void onSuccess(NetMessage netMessage) {
              if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                   bills.clear();
                  List<TheBill> list = new Gson().fromJson(netMessage.getData(), new TypeToken<List<TheBill>>() {
                  }.getType());
                  if (list != null && list.size() > 0) {
                      bills.addAll(list);
                      theBillId++;
                  } else {
                      Toast.makeText(getApplicationContext(),
                              "没有更多信息", Toast.LENGTH_LONG).show();
                  }


              } else {
                  Toast.makeText(getApplicationContext(),
                          netMessage.getMsg(), Toast.LENGTH_LONG).show();

              }
              adapterSearchResult.notifyDataSetChanged();
              lvSearchResulat.onRefreshComplete();
          }
      });
    }
    private void getMoreResukat(){
        if (bills.size() == 0){
            theBillId = 0;
        }else{
            theBillId = bills.get(bills.size() - 1).getId();
        }
        new AccountServer().searchTheBill(mobile, theBillId, starttime, endtime, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                adapterSearchResult.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    // bills.clear();
                    List<TheBill> list = new Gson().fromJson(netMessage.getData(), new TypeToken<List<TheBill>>() {
                    }.getType());
                    if (list != null && list.size() > 0) {
                        bills.addAll(list);
                        theBillId++;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "没有更多信息", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(),
                            netMessage.getMsg(), Toast.LENGTH_LONG).show();

                }
                adapterSearchResult.notifyDataSetChanged();
                lvSearchResulat.onRefreshComplete();
            }
        });
    }
    private class MyListenter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == ivReturn){
                finish();
            }else if (v == lvSearchResulat){

            }
        }
    }
}

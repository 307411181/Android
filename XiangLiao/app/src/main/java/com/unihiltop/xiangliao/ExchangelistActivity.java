package com.unihiltop.xiangliao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unihiltop.xiangliao.adapter.AdapterExchangeCard;
import com.unihiltop.xiangliao.bean.ExchangeRecord;
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
 * 同意登陆
 */
public class ExchangelistActivity extends Activity {
    private ImageView ivReturn;
    private PullToRefreshListView lvExchangeList;
    private List<ExchangeRecord> list;
    private ExchangeRecord exchangeRecord;
    private long exchangeRecordid;
    private AdapterExchangeCard adapterExchangeCard;
    private long accountid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_list);
        initData();
        initView();
        initListenter();
    }


    private void initData() {
        list = new LinkedList<ExchangeRecord>();
        exchangeRecord = new ExchangeRecord();
        accountid = UserData.getData(ExchangelistActivity.this).getAccountId();
        exchangeRecordid = exchangeRecord.id;
        adapterExchangeCard = new AdapterExchangeCard(ExchangelistActivity.this, list);
        initMessage();
    }

    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
        lvExchangeList = (PullToRefreshListView) findViewById(R.id.listView_exchange_list);
        /**
         * 下拉刷新1
         */
        lvExchangeList.setMode(PullToRefreshBase.Mode.BOTH);
        lvExchangeList.setAdapter(adapterExchangeCard);

    }

    private void initListenter() {
        MyListenter mylistenter = new MyListenter();
        ivReturn.setOnClickListener(mylistenter);
/**
 * 下拉刷新2
 */
        lvExchangeList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                initMessage();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
               getmoreMessage();

            }

        });

    }

    private void mytoast(String str) {
        Toast.makeText(ExchangelistActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    private class MyListenter implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == ivReturn) {
                finish();
            }
        }
    }


    private void initMessage() {

        new AccountServer().getExchangeIntegralProductList(accountid,-1l, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                adapterExchangeCard.notifyDataSetChanged();
                mytoast(message);
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    list.clear();
                    List<ExchangeRecord> listinter = new Gson().fromJson(netMessage.getData(), new TypeToken<List<ExchangeRecord>>() {

                    }.getType());
                    if (listinter != null && listinter.size() > 0) {
                        list.addAll(listinter);
                        exchangeRecordid++;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "暂时没有信息", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            netMessage.getMsg(), Toast.LENGTH_LONG).show();
                }
                adapterExchangeCard.notifyDataSetChanged();
                lvExchangeList.onRefreshComplete();
            }
        });
    }
    private void getmoreMessage(){
        if(list.size() == 0){
            exchangeRecordid = 0;
        }else {
            exchangeRecordid = list.get(list.size() - 1).getId();
        }
        new AccountServer().getExchangeIntegralProductList(accountid,exchangeRecordid, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                adapterExchangeCard.notifyDataSetChanged();
                mytoast(message);
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    // list.clear();
                    lvExchangeList.onRefreshComplete();
                    List<ExchangeRecord> listinter = new Gson().fromJson(netMessage.getData(), new TypeToken<List<ExchangeRecord>>() {

                    }.getType());
                    if (listinter != null && listinter.size() > 0) {
                        exchangeRecordid++;
                        list.addAll(listinter);
                        adapterExchangeCard.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "暂时更多没有信息", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            netMessage.getMsg(), Toast.LENGTH_LONG).show();
                }
//                adapterMessageResult.notifyDataSetChanged();
//                lvSearchMessage.onRefreshComplete();
            }
        });

    }

}

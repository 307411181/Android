package com.unihiltop.xiangliao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unihiltop.xiangliao.adapter.AdapterMessageResult;
import com.unihiltop.xiangliao.bean.SystemMessage;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.SystemMessageServer;
import com.unihiltop.xiangliao.pulltorefresh.library.PullToRefreshBase;
import com.unihiltop.xiangliao.pulltorefresh.library.PullToRefreshListView;

import java.util.LinkedList;
import java.util.List;

/**
 * 同意登陆
 */
public class MessageActivity extends Activity {
    private ImageView ivReturn;
    private PullToRefreshListView lvSearchMessage;
    private List<SystemMessage> list;
    private SystemMessage systemMessage;
    private long systemMessageid;
    private AdapterMessageResult adapterMessageResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initData();
        initView();
        initListenter();
    }


    private void initData() {
        list = new LinkedList<SystemMessage>();
        systemMessage = new SystemMessage();
        systemMessageid = systemMessage.id;
        adapterMessageResult = new AdapterMessageResult(MessageActivity.this, list);
        initMessage();
    }

    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
        lvSearchMessage = (PullToRefreshListView) findViewById(R.id.listView_search_message);
        /**
         * 下拉刷新1
         */
        lvSearchMessage.setMode(PullToRefreshBase.Mode.BOTH);
        lvSearchMessage.setAdapter(adapterMessageResult);

    }

    private void initListenter() {
        MyListenter mylistenter = new MyListenter();
        ivReturn.setOnClickListener(mylistenter);
/**
 * 下拉刷新2
 */
        lvSearchMessage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

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
        Toast.makeText(MessageActivity.this, str, Toast.LENGTH_SHORT).show();
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

        new SystemMessageServer().getSystemMessageList(-1l, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                adapterMessageResult.notifyDataSetChanged();
                mytoast(message);
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    list.clear();
                    List<SystemMessage> listinter = new Gson().fromJson(netMessage.getData(), new TypeToken<List<SystemMessage>>() {

                    }.getType());
                    if (listinter != null && listinter.size() > 0) {
                        list.addAll(listinter);
                        systemMessageid++;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "暂时没有信息", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            netMessage.getMsg(), Toast.LENGTH_LONG).show();
                }
                adapterMessageResult.notifyDataSetChanged();
                lvSearchMessage.onRefreshComplete();
            }
        });
    }
    private void getmoreMessage(){
        if(list.size() == 0){
            systemMessageid = 0;
        }else {
            systemMessageid = list.get(list.size() - 1).getId();
        }
        new SystemMessageServer().getSystemMessageList(systemMessageid, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                adapterMessageResult.notifyDataSetChanged();
                mytoast(message);
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                   // list.clear();
                    lvSearchMessage.onRefreshComplete();
                    List<SystemMessage> listinter = new Gson().fromJson(netMessage.getData(), new TypeToken<List<SystemMessage>>() {

                    }.getType());
                    if (listinter != null && listinter.size() > 0) {
                        systemMessageid++;
                        list.addAll(listinter);
                        adapterMessageResult.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "暂时没有信息", Toast.LENGTH_LONG).show();
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

package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unihiltop.xiangliao.adapter.AddressSelectAdapter;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.ReceiverAddress;
import com.unihiltop.xiangliao.constant.ResultDataContact;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.ReceiverAddressServer;
import com.unihiltop.xiangliao.util.Tools;

import java.util.ArrayList;
import java.util.List;


/**
 * 地址选择界面
 */
public class AddressSelectActivity extends Activity {
    private ImageView ivBack;
    private ListView lvAddress;
    private List<ReceiverAddress> arrayList;
    private AddressSelectAdapter addressSelectAdapter;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select);
        initData();
        iniView();
        initListener();
    }
    private void initData() {
        account = UserData.getData(AddressSelectActivity.this).getAccount();
        arrayList = new ArrayList<ReceiverAddress>();
        addressSelectAdapter = new AddressSelectAdapter(AddressSelectActivity.this,arrayList);
        initUserAddress();
    }



    private void iniView() {
        ivBack = (ImageView) findViewById(R.id.imageView_back);
        lvAddress = (ListView) findViewById(R.id.listView_address);
        lvAddress.setAdapter(addressSelectAdapter);
    }
    private void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();
                data.putExtra(ResultDataContact.KEY_ADDRESS, arrayList.get(position));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
    private void initUserAddress() {
        new ReceiverAddressServer().getReceiverAddressList(account.getId(), new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                Tools.showNetworkErrorToast(AddressSelectActivity.this);
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    List<ReceiverAddress> list = new Gson().fromJson(netMessage.getData(), new TypeToken<List<ReceiverAddress>>() {
                    }.getType());
                    if (list != null && list.size() > 0) {
                        arrayList.addAll(list);
                        addressSelectAdapter.notifyDataSetChanged();
                    } else {
                        Tools.showToast(AddressSelectActivity.this, "暂无地址请先添加地址");
                    }
                } else {
                    Tools.showToast(AddressSelectActivity.this, netMessage.getMsg());
                }
            }
        });
    }

}

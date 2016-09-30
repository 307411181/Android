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
import com.unihiltop.xiangliao.adapter.AddressAdapter;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.ReceiverAddress;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.ReceiverAddressServer;
import com.unihiltop.xiangliao.util.Tools;
import com.unihiltop.xiangliao.util.UISkip;

import java.util.ArrayList;
import java.util.List;


/**
 * 地址管理
 */
public class AddressManagmentActivity extends Activity {
    private ImageView ivBack;// 返回
    private ImageView ivAddAddress;// 添加地址
    private ListView lvAddress;
    private List<ReceiverAddress> arrayList;
    private AddressAdapter addressAdapter;
    private Account user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_managment);
        initData();
        initView();
        initListener();
    }
    private void initData() {
        user = UserData.getData(AddressManagmentActivity.this).getAccount();
        arrayList = new ArrayList<ReceiverAddress>();
        addressAdapter = new AddressAdapter(AddressManagmentActivity.this,arrayList);
        initAddress();
    }
    private void initView() {
        ivBack = (ImageView)findViewById(R.id.imageView_back);
        ivAddAddress = (ImageView)findViewById(R.id.imageView_add_address);
        lvAddress = (ListView)findViewById(R.id.listView_address);
        lvAddress.setAdapter(addressAdapter);
    }

    private void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UISkip.skipToAddressEditActivity(AddressManagmentActivity.this);
            }
        });
        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UISkip.skipToAddressEditActivity(AddressManagmentActivity.this, arrayList.get(position));
            }
        });
    }

    private void initAddress(){
        new ReceiverAddressServer().getReceiverAddressList(user.getId(), new NetMessageCallback() {

            @Override
            public void onFailure(String error, String message) {
                Tools.showNetworkErrorToast(AddressManagmentActivity.this);
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    List<ReceiverAddress> list = new Gson().fromJson(netMessage.getData(), new TypeToken<List<ReceiverAddress>>() {
                    }.getType());
                    arrayList.clear();
                    if (list != null || list.size() > 0) {
                        arrayList.addAll(list);
                        addressAdapter.notifyDataSetChanged();
                    }
                } else {
                    Tools.showToast(AddressManagmentActivity.this, netMessage.getMsg());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
//            if (requestCode == RequestCode.REQUEST_CODE_ADD_ADDRESS){
//                UserAddress userAddress = (UserAddress) data.getSerializableExtra(ResultDataContact.KEY_ADDRESS);
//                arrayList.add(0, userAddress);
//                addressAdapter.notifyDataSetChanged();
//            }else if (requestCode == RequestCode.REQUEST_CODE_EDIT_ADDRESS){
//
//            }
            initAddress();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

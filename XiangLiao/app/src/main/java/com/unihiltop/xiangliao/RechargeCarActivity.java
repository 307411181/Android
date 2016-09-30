package com.unihiltop.xiangliao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.PayServer;
import com.unihiltop.xiangliao.util.Tools;

/**
 * 充值卡充值
 * Created by yangyang on 2015/10/14.
 */
public class RechargeCarActivity extends Activity{
    private ImageView ivReturn;//返回
    private EditText  etRechargecarNumber;//输入卡号
    private EditText  etRechargePass;//输入密码
    private Button btRegist;//充值
    private String carNumber;
    private String carPass;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_car);
        initData();
        initView();
        initListenter();
    }
    private void initData() {
        phone = getIntent().getStringExtra("phone");
    }
    private void initView() {

        ivReturn = (ImageView) findViewById(R.id.imageView_return);

        etRechargecarNumber = (EditText) findViewById(R.id.editText_rechargecar_number);

        etRechargePass = (EditText) findViewById(R.id.editText_recharge_pass);

        btRegist = (Button) findViewById(R.id.button_regist);
    }

    private void initListenter() {
        MyListenter myListenter = new MyListenter();
        ivReturn.setOnClickListener(myListenter);
        btRegist.setOnClickListener(myListenter);
    }
    private class MyListenter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
        if (v == btRegist){
            carNumber = etRechargecarNumber.getText().toString();
            carPass = etRechargePass.getText().toString();
            if (TextUtils.isEmpty(carNumber)||TextUtils.isEmpty(carPass)||TextUtils.isEmpty(phone)){
                MyToast("卡号密码都不能为空");
                return;
            }
            rechargeBillWithCard(phone, carNumber, carPass);
//            if(!TextUtils.isEmpty(carNumber) && !TextUtils.isEmpty(carPass)  && !TextUtils.isEmpty(phone)){
//                rechargeBillWithCard(phone, carNumber, carPass);
//            }else {
//
//            }
        }else if (v == ivReturn){
            finish();
        }
        }
    }

    public void rechargeBillWithCard(String mobile,String cardNum, String cardPwd){
        final ProgressDialog progressDialog = ProgressDialog.show(
                RechargeCarActivity.this, null, null);
        new PayServer().rechargeBillWithCard(UserData.getData(RechargeCarActivity.this).getAccount().getAccount(),
                mobile,
                cardNum,
                cardPwd, new NetMessageCallback() {
                    @Override
                    public void onFailure(String error, String message) {
                        progressDialog.dismiss();
                        Tools.showToast(RechargeCarActivity.this, message);
                    }

                    @Override
                    public void onSuccess(NetMessage netMessage) {
                        progressDialog.dismiss();
                        if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS){
                            Tools.showToast(RechargeCarActivity.this, "充值成功");
                            etRechargecarNumber.setText("");
                            etRechargePass.setText("");
                            if (phone.equals(UserData.getData(RechargeCarActivity.this).getAccountName())){

                                UserData.getData(RechargeCarActivity.this).setAccount(netMessage.getData());
                            }
                            finish();
                        }else {
                            Tools.showToast(RechargeCarActivity.this, netMessage.getMsg());
                        }
                    }
                });
    }
    private void MyToast(String mytoast){
        Toast.makeText(getApplicationContext(), mytoast, Toast.LENGTH_SHORT).show();
    }
}

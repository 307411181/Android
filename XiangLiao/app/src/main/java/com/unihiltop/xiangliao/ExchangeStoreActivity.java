package com.unihiltop.xiangliao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.IntegralProduct;
import com.unihiltop.xiangliao.bean.ReceiverAddress;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.dialog.DetailAdressDialog;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.dialog.DetailAdressDialog.AddressClickListener;
import com.unihiltop.xiangliao.util.ImageLoadOptions;
import com.unihiltop.xiangliao.util.RegExpUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 兑换商城2
 * Created by yangyang on 2015/10/17.
 */
public class ExchangeStoreActivity extends Activity {
    private ImageView ivReturn;//返回
    private ImageView ivExchangestorePhone;//具体卡片图
    private TextView tvExchangestoreCar;//年卡或月卡
    private TextView tvExchangestoreInfo;//介绍内容
    private TextView tvExchangestoreMoney;//所需积分
    private TextView tvExchangestoreGold;//剩余数量
    private RelativeLayout rlExchangeAdress;//选择地址
    private TextView tvExchangestoreAdress;//选择地址

    private EditText etNumber;//选数量
    private EditText etExchangeDetailaddress;//详细地址
    private EditText etExchangePerson;//联系人
    private EditText etExchangePhone;//联系方式
    private Button btAgree;//确认兑换
    private long accountid;
    private IntegralProduct integralProduct;
    private ReceiverAddress receivingAddress;
    private DetailAdressDialog adressDialog;
    private String imageurl;
    // private String amount ;
    private String address;
    private String name;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_store);
        initData();
        initView();
        initSetView();
        initListenter();
    }

    private void initData() {
        integralProduct = (IntegralProduct) getIntent().getSerializableExtra("integralProduct");
        accountid = UserData.getData(ExchangeStoreActivity.this).getAccountId();
        receivingAddress = new ReceiverAddress();

    }

    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
        ivExchangestorePhone = (ImageView) findViewById(R.id.imageView_exchangestore_phone);
        tvExchangestoreCar = (TextView) findViewById(R.id.textView_exchangestore_car);
        tvExchangestoreInfo = (TextView) findViewById(R.id.textView_exchangestore_info);
        tvExchangestoreMoney = (TextView) findViewById(R.id.textView_exchangestore_money);
        tvExchangestoreGold = (TextView) findViewById(R.id.textView_exchangestore_gold);
        rlExchangeAdress = (RelativeLayout) findViewById(R.id.relativeLayout_exchange_adress);
        tvExchangestoreAdress = (TextView) findViewById(R.id.textView_exchangestore_adress);
        etExchangeDetailaddress = (EditText) findViewById(R.id.editText_exchange_detailaddress);
        etExchangePerson = (EditText) findViewById(R.id.editText_exchange_person);
        etExchangePhone = (EditText) findViewById(R.id.editText_exchange_phone);
        etNumber = (EditText) findViewById(R.id.editText_number);
        btAgree = (Button) findViewById(R.id.button_agree);
        if (integralProduct.productName != null){
            tvExchangestoreCar.setText(integralProduct.productName);
        }

        tvExchangestoreMoney.setText(String.valueOf(integralProduct.needCoin));
        tvExchangestoreGold.setText(String.valueOf(integralProduct.amount));

        tvExchangestoreInfo.setText(integralProduct.productDescription);
        // amount = etNumber.getText().toString();
        //获取图片
        imageurl = integralProduct.getImgURL();
        if (imageurl != null) {
            ImageLoader.getInstance().displayImage(imageurl,
                    ivExchangestorePhone,
                    ImageLoadOptions.getOptions(R.drawable.ic_picture));
        }

    }

    private void initSetView() {

//        try {
//            amount = Integer.parseInt(etNumber.getText().toString());
//            Log.i("yangyang",amount+"");
//        } catch (Exception e) {
//            return;
//        }

    }

    private void initListenter() {
        MyListenter mylistenter = new MyListenter();
        ivReturn.setOnClickListener(mylistenter);
        rlExchangeAdress.setOnClickListener(mylistenter);
        btAgree.setOnClickListener(mylistenter);
    }

    private class MyListenter implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == ivReturn) {
                finish();
            } else if (v == rlExchangeAdress) {
/**
 * 获取省市县
 */
                if (adressDialog == null) {
                    adressDialog = new DetailAdressDialog(ExchangeStoreActivity.this);
                }
                adressDialog.setAddressClickListener(new AddressClickListener() {
                    @Override
                    public void sure(String provinceStr, String cityStr, String countyStr) {

                        receivingAddress.setProvince(provinceStr);
                        receivingAddress.setCity(cityStr);
                        receivingAddress.setCounty(countyStr);
                        if(TextUtils.isEmpty(receivingAddress.getCounty())){
                            tvExchangestoreAdress.setText(provinceStr + "" + cityStr );
                        }else {
                            tvExchangestoreAdress.setText(provinceStr + "" + cityStr + "" + countyStr);
                        }

                    }
                });
                adressDialog.show();


            } else if (v == btAgree) {
                String number = etNumber.getText().toString();
                if (TextUtils.isEmpty(number)) {

                    Toast.makeText(ExchangeStoreActivity.this, "兑换量不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //  yesno();
                 if(TextUtils.isEmpty(receivingAddress.getProvince())){
                     Toast.makeText(ExchangeStoreActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                     return;
            }
                String address = etExchangeDetailaddress.getText().toString();
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(ExchangeStoreActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = etExchangePerson.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ExchangeStoreActivity.this, "联系人不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phone = etExchangePhone.getText().toString();
                if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, phone)) {
                    Toast.makeText(ExchangeStoreActivity.this, "联系电话不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                int amount = Integer.parseInt(number);


                receivingAddress.setDetailAddress(etExchangeDetailaddress.getText().toString());
                receivingAddress.setContactPersonName(etExchangePerson.getText().toString());
                receivingAddress.setContactPersonMobile(etExchangePhone.getText().toString());

                final ProgressDialog progressDialog = ProgressDialog.show(
                        ExchangeStoreActivity.this, null, null);
                Gson gson = new Gson();
                String receivingAddressJson = gson.toJson(receivingAddress);
                new AccountServer().exchangeIntegralProduct(integralProduct.id,
                        accountid, amount, receivingAddressJson,
                        new NetMessageCallback() {
                            @Override
                            public void onFailure(String error, String message) {
                                progressDialog.dismiss();
                                Toast.makeText(ExchangeStoreActivity.this, message, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onSuccess(NetMessage netMessage) {
                                progressDialog.dismiss();
                                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                                  Log.i("yangyang", netMessage.getData()+"");
                                    Account account = UserData.getData(ExchangeStoreActivity.this).getAccount();
                                    account.coin = account.coin - integralProduct.needCoin* Integer.parseInt(etNumber.getText().toString());
                                    UserData.getData(ExchangeStoreActivity.this).setAccount(account);

                                   // this.startActivityForResult(_intent, CONTEXT_RESTRICTED);//CONTEXT_RESTRICTED int型变量，可自定义
                                    Toast.makeText(ExchangeStoreActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                                    finish();

                                }else {
                                    Toast.makeText(ExchangeStoreActivity.this, netMessage.getMsg(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });


            }
        }
    }

    private void yesno() {
        if (address == null) {
            Toast.makeText(ExchangeStoreActivity.this, "地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address == null) {
            //  Toast.makeText(ExchangeStoreActivity.this,"兑换数量不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone == null) {
            Toast.makeText(ExchangeStoreActivity.this, "联系电话不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name == null) {
            Toast.makeText(ExchangeStoreActivity.this, "联系人不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        receivingAddress.setDetailAddress(etExchangeDetailaddress.getText().toString());
        receivingAddress.setContactPersonName(etExchangePerson.getText().toString());
        receivingAddress.setContactPersonMobile(etExchangePhone.getText().toString());

    }
}
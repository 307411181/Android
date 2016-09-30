package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.City;
import com.unihiltop.xiangliao.bean.County;
import com.unihiltop.xiangliao.bean.Province;
import com.unihiltop.xiangliao.bean.ReceiverAddress;
import com.unihiltop.xiangliao.constant.ExtraConstant;
import com.unihiltop.xiangliao.constant.PreferenceKeyConstants;
import com.unihiltop.xiangliao.constant.ResultDataContact;
import com.unihiltop.xiangliao.dao.AreaDao;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.ReceiverAddressServer;
import com.unihiltop.xiangliao.util.PreferencesHelper;
import com.unihiltop.xiangliao.util.RegExpUtil;
import com.unihiltop.xiangliao.util.Tools;


import java.util.ArrayList;
import java.util.List;

/**
 * 地址信息录入（添加或编辑）
 */
public class AddressEditActivity extends Activity {
    private ImageView ivBack;
    private TextView tvAdd;// 添加地址
    private EditText etReceiver;// 收货人
    private EditText etPhone;// 手机号
    private EditText etZipCode;// 邮编
    private TextView tvProvince;// 省
    private TextView tvCity;// 市
    private TextView tvCounty;// 县
    private AreaDao areaDao;

    private List<String> provinceList;
    private List<Province> provinces;


    private List<String> cityList;
    private List<City> cities;

    private List<String> countyList;
    private List<County> counties;

    private Province province;
    private City city;
    private County county;

    private EditText etDetailAddress;// 详细地址
    private View.OnClickListener addressEditClickListener;
    private ReceiverAddress receiverAddress;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        account = UserData.getData(AddressEditActivity.this).getAccount();
        receiverAddress = new ReceiverAddress();
        int addressEditStatus = getIntent().getIntExtra(ExtraConstant.KEY_ADDRESS_EDIT_STATUS, 0);//0表示增加，1表示编辑
        if (addressEditStatus == 1){//获取传过来的地址
            receiverAddress = (ReceiverAddress) getIntent().getSerializableExtra(ExtraConstant.KEY_ADDRESS_EDIT_DATA);
        }

        areaDao = new AreaDao(AddressEditActivity.this);
        provinces = areaDao.getProvinces();
        provinceList = new ArrayList<String>();
        for (Province province:provinces) {
            provinceList.add(province.getProvince());
        }
        cityList = new ArrayList<String>();
        countyList = new ArrayList<String>();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.imageView_back);
        tvAdd = (TextView) findViewById(R.id.textView_add);
        etReceiver = (EditText) findViewById(R.id.editText_receiver);
        etPhone = (EditText) findViewById(R.id.editText_phone);
        etZipCode = (EditText) findViewById(R.id.editText_zip_code);
        tvProvince = (TextView) findViewById(R.id.textView_province);
        tvCity = (TextView) findViewById(R.id.textView_city);
        tvCounty = (TextView) findViewById(R.id.textView_county);
        etDetailAddress = (EditText) findViewById(R.id.editText_detail_address);
    }

    private void initListener() {
        addressEditClickListener = new AddressEditClickListener();
        ivBack.setOnClickListener(addressEditClickListener);
        tvAdd.setOnClickListener(addressEditClickListener);
        tvProvince.setOnClickListener(addressEditClickListener);
        tvCity.setOnClickListener(addressEditClickListener);
        tvCounty.setOnClickListener(addressEditClickListener);
    }

    private final class AddressEditClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == ivBack){
                finish();
            }else if(v == tvAdd){
                addUserAddress();
            }else if(v == tvProvince){
                chooseProvince();
            }else if(v == tvCity){
                chooseCity();
            }else if(v == tvCounty){
                chooseCounty();
            }
        }
    }

    private void addUserAddress(){
        String province = tvProvince.getText().toString();
        String city = tvProvince.getText().toString();
        String county = tvCounty.getText().toString();
        String address =  etDetailAddress.getText().toString();
        String contacts = etReceiver.getText().toString();
        String tel = etPhone.getText().toString();
        String postCode = etZipCode.getText().toString();
        if (TextUtils.isEmpty(province) ||
                TextUtils.isEmpty(city) ||
                TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(postCode) ||
                TextUtils.isEmpty(tel)){
            Tools.showToast(AddressEditActivity.this, "收货地址，信息不全，请完善地址信息");
            return;
        }
        if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, tel)){
            Tools.showToast(AddressEditActivity.this, "请输入有效的电话号码");
            return;
        }
        if (!RegExpUtil.match(RegExpUtil.REGEXP_POST_CODE, postCode)){
            Tools.showToast(AddressEditActivity.this, "请输入有效的邮编");
            return;
        }
        receiverAddress.setProvince(province);
        receiverAddress.setCity(city);
        receiverAddress.setCounty(county);
        receiverAddress.setDetailAddress(address);
        receiverAddress.setPostCode(postCode);
        receiverAddress.setContactPersonName(contacts);
        receiverAddress.setContactPersonMobile(tel);
        if (TextUtils.isEmpty(receiverAddress.getId())){
            new ReceiverAddressServer().addReceiverAddress(account.getId(), receiverAddress, new NetMessageCallback() {

                @Override
                public void onFailure(String error, String message) {
                    Tools.showNetworkErrorToast(AddressEditActivity.this);
                }

                @Override
                public void onSuccess(NetMessage netMessage) {
                    if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                        ReceiverAddress receiverAddress = new Gson().fromJson(netMessage.getData(), ReceiverAddress.class);
                        PreferencesHelper.get(AddressEditActivity.this).putObject(PreferenceKeyConstants.KEY_ACCOUNT_DEFAULT_ADDRESS, receiverAddress);
                        Intent data = new Intent();
                        data.putExtra(ResultDataContact.KEY_ADDRESS, receiverAddress);
                        setResult(RESULT_OK, data);
                        finish();
                    } else {
                        Tools.showToast(AddressEditActivity.this, netMessage.getMsg());
                    }

                }
            });
        }else {
            new ReceiverAddressServer().updateReceiverAddress(receiverAddress, new NetMessageCallback() {

                @Override
                public void onFailure(String error, String message) {
                    Tools.showNetworkErrorToast(AddressEditActivity.this);
                }

                @Override
                public void onSuccess(NetMessage netMessage) {
                    if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Tools.showToast(AddressEditActivity.this, netMessage.getMsg());
                    }
                }
            });

        }
    }

    /**
     * 选择目的省
     */
    private void chooseProvince() {
        String[] Provs = provinceList.toArray(new String[provinceList.size()]);
        new android.app.AlertDialog.Builder( AddressEditActivity.this)
                .setTitle(R.string.title_choice_start_province)
                .setItems(Provs, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        province = provinces.get(which);
                        tvProvince.setText(province.getProvince());

                        chooseCity();
                    }
                }).setNegativeButton(R.string.content_cancel, null).show();
    }

    /**
     * 选择目的城市
     */
    private void chooseCity() {
        if (province == null){
            Tools.showToast(AddressEditActivity.this, "请先选择省");
            return;
        }
        cities = areaDao.getCities(province.getProvinceid());
        city = cities.get(0);
        tvCity.setText(city.getCity());

        cityList.clear();
        for (City city:cities) {
            cityList.add(city.getCity());
        }
        new android.app.AlertDialog.Builder( AddressEditActivity.this)
                .setTitle(R.string.title_choice_start_city)
                .setItems(cityList.toArray(new String[cityList.size()]), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        city = cities.get(which);
                        tvCity.setText(city.getCity());
                        chooseCounty();

                    }
                }).setNegativeButton(R.string.content_cancel, null).show();
    }

    /**
     *选择目的县或区
     */
    private void chooseCounty(){
        if (province == null){
            Tools.showToast(AddressEditActivity.this, "请先选择市");
            return;
        }
        countyList.clear();
        counties = areaDao.getCountes(city.getCityid());
        if(counties == null || counties.size() == 0){
            county = new County();
            tvCounty.setText("");
            return;
        }
        county = counties.get(0);
        tvCounty.setText(county.getCounty());
        for (County county:counties) {
            countyList.add(county.getCounty());
        }
        new android.app.AlertDialog.Builder(AddressEditActivity.this)
                .setTitle(R.string.title_choice_start_county)
                .setItems(countyList.toArray(new String[countyList.size()]), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        county = counties.get(which);                        
                        tvCounty.setText(county.getCounty());
                    }
                }).setNegativeButton(R.string.content_cancel, null).show();
    }
}

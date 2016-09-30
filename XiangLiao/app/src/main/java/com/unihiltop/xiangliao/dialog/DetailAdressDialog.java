package com.unihiltop.xiangliao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.City;
import com.unihiltop.xiangliao.bean.County;
import com.unihiltop.xiangliao.bean.Province;
import com.unihiltop.xiangliao.dao.AreaDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyang on 2015/10/22.
 */
public class DetailAdressDialog extends Dialog {
    private Context context;
    private ImageView ivReturn;
    private RelativeLayout rlProvince;
    private TextView tvProvince;
    private RelativeLayout rlCity;
    private TextView tvCity;
    private RelativeLayout rlCounty;
    private TextView tvCounty;
    private TextView tvAddressChoose;
    private AreaDao areaDao;

    private List<String> provinceList;
    private List<Province> provinces;


    private List<String> startCityList;
    private List<City> startCities;

    private List<String> startCountyList;
    private List<County> startCounties;


    private Province startProvince;
    private City startCity;
    private County startCounty;
    private AddressClickListener addressClickListener;
    private String provinceStr;
    private String cityStr;
    private String countyStr;
    public DetailAdressDialog(Context context) {
        super(context, R.style.FullScreenDialogTheme);
        this.context = context;
        initData();
        initView();
        initListener();
    }

    private void initData() {
        areaDao = new AreaDao(context);
        provinces = areaDao.getProvinces();
        provinceList = new ArrayList<String>();
        for (Province province:provinces) {
            provinceList.add(province.getProvince());
        }
        startCityList = new ArrayList<String>();
        startCountyList = new ArrayList<String>();
    }

    private void initView() {
        setContentView(R.layout.dialog_address);
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
        rlProvince = (RelativeLayout) findViewById(R.id.relativeLayout_province);
        tvProvince = (TextView) findViewById(R.id.textView_province);
        rlCity = (RelativeLayout) findViewById(R.id.relativeLayout_city);
        tvCity = (TextView) findViewById(R.id.textView_city);
        rlCounty = (RelativeLayout) findViewById(R.id.relativeLayout_county);
        tvCounty = (TextView) findViewById(R.id.textView_county);
        tvAddressChoose = (TextView) findViewById(R.id.address_choose);
    }
    private void initListener() {
        MyListenter myListenter = new MyListenter();
        ivReturn.setOnClickListener(myListenter);
        rlProvince.setOnClickListener(myListenter);
        rlCity.setOnClickListener(myListenter);
        rlCounty.setOnClickListener(myListenter);
        tvAddressChoose.setOnClickListener(myListenter);
    }
    private class MyListenter implements View.OnClickListener{
        @Override
        public void onClick(View v) {
              if(v == ivReturn){
                  dismiss();
              }else if (v == rlProvince){
                  chooseStartProvince();
              }else if (v == rlCity){
                  chooseStartCity();
              }else if(v == rlCounty){
                  chooseStartCounty();
              }else if (v == tvAddressChoose){
                  if (addressClickListener != null) {
                      addressClickListener.sure(provinceStr, cityStr,countyStr);
                  }
                  dismiss();
              }
        }
    }
    /**
     * 选择目的省
     */
    private void chooseStartProvince() {
        String[] Provs = provinceList.toArray(new String[provinceList.size()]);
        new android.app.AlertDialog.Builder( context)
                .setTitle(R.string.title_choice_start_province)
                .setItems(Provs, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        startProvince = provinces.get(which);
                        provinceStr = provinces.get(which).getProvince();
                        tvProvince.setText(startProvince.getProvince());
                        chooseStartCity();
                    }
                }).setNegativeButton(R.string.content_cancel, null).show();
    }
    /**
     * 选择目的城市
     */
    private void chooseStartCity() {
        if(startProvince == null){
            Toast.makeText(context, "请选择出发省",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        startCities = areaDao.getCities(startProvince.getProvinceid());
        startCity = startCities.get(0);
        tvCity.setText(startCity.getCity());
        startCityList.clear();
        for (City city:startCities) {
            startCityList.add(city.getCity());
        }
        new android.app.AlertDialog.Builder( context)
                .setTitle(R.string.title_choice_start_city)
                .setItems(startCityList.toArray(new String[startCityList.size()]), new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCity = startCities.get(which);
                        cityStr = startCities.get(which).getCity();
                        tvCity.setText(startCity.getCity());
                        chooseStartCounty();
                    }
                }).setNegativeButton(R.string.content_cancel, null).show();
    }
    /**
     *选择目的县或区
     */
    private void chooseStartCounty(){

        if(startCity  == null) {
            Toast.makeText(context, "请选择出发市",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        startCounties = areaDao.getCountes(startCity.getCityid());
        if(startCounties == null || startCounties.size() == 0){
            startCounty = new County();
            tvCounty.setText("");
            countyStr = null;
            return;
        }
        startCounty = startCounties.get(0);
        tvCounty.setText(startCounty.getCounty());
        startCountyList.clear();
        for (County county:startCounties) {
            startCountyList.add(county.getCounty());
        }
        new android.app.AlertDialog.Builder(context)
                .setTitle(R.string.title_choice_start_county)
                .setItems(startCountyList.toArray(new String[startCountyList.size()]), new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCounty = startCounties.get(which);
                        countyStr = startCounties.get(which).getCounty();
                        tvCounty.setText(startCounty.getCounty());
                    }
                }).setNegativeButton(R.string.content_cancel, null).show();
    }
    public interface AddressClickListener{
        public void sure(String provinceStr, String cityStr, String countyStr);
    }

    public void setAddressClickListener(AddressClickListener l){
        this.addressClickListener = l;
    }
}

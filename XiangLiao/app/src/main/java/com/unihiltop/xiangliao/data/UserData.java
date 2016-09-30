package com.unihiltop.xiangliao.data;

import com.google.gson.Gson;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.util.PreferencesHelper;


import android.content.Context;
import android.text.TextUtils;

public class UserData {

    private final static String USER_DATA = "user_data";

    private static UserData data = null;
  // private Account account = null;
    private PreferencesHelper preferencesHelper = null;

    private Account account;

    private Gson gson = null;

//	private String accessToken;
//	private double latitude;
//	private double longitude;

    private UserData(Context context){
        gson = new Gson();
        preferencesHelper = PreferencesHelper.get(context);
    }
    public static UserData getData(Context context){
        if (data == null) {
            data = new UserData(context);
        }
        return data;
    }
    public void setAccount(String accountJson){
        this.account = gson.fromJson(accountJson, Account.class);
        preferencesHelper.put(USER_DATA, accountJson);

    }

    public void setAccount(Account account){
        this.account = account;
        preferencesHelper.put(USER_DATA, new Gson().toJson(account));

    }

    public Account getAccount(){
        if (account == null) {
            String json = preferencesHelper.getString(USER_DATA);
            if (TextUtils.isEmpty(json)) {
                return null;
            }
            account = gson.fromJson(json, Account.class);
        }
        return account;
    }

    public long getAccountId(){
        if(getAccount() == null){
            return 0;
        }
        return getAccount().getId();
    }
    public String getAccountName(){
        if(getAccount() == null){
            return null;
        }
        return getAccount().getAccount();
    }
    public double getBill(){
        if(getAccount() == null){
            return 0;
        }
        return getAccount().getBill();
    }
    public String getHeadImg(){
        if(getAccount() == null){
            return null;
        }
        return getAccount().getHeadImg();
    }
    public double getIntegral(){
        if(getAccount() == null){
            return 0;
        }
        return getAccount().getIntegral();
    }
    public double getCoin(){
        if(getAccount() == null){
            return 0;
        }
        return getAccount().getCoin();
    }
    public void clear(){
        this.account = null;
        preferencesHelper.put(USER_DATA, null);

    }

}
package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.ErrorLogInfo;
import com.unihiltop.xiangliao.constant.Constants;
import com.unihiltop.xiangliao.constant.PreferenceKeyConstants;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.manager.ContactManager;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.util.DateUtils;
import com.unihiltop.xiangliao.util.PreferencesHelper;
import com.unihiltop.xiangliao.util.UISkip;

/**
 * 开始界面
 *
 * @author yangyang
 */
public class StartActivity extends Activity {
    private Handler handler = new Handler();
    private ImageView firstStartImageView;
    private Account   account;
    private String tag = "TAG";

    //private static final int sleepTime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initData();
        initView();

    }

    private void initData() {
        Log.e(tag, "initData");
        ContactManager contactManager = ContactManager.getInstance(this);
        contactManager.insertKuaiHuas();
        account = UserData.getData(getApplicationContext()).getAccount();

        try {
            sendError(PreferencesHelper.get(StartActivity.this).getString(PreferenceKeyConstants.KEY_ERROR), account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 升级中.改变了逻辑,通过判断account,他是通过下面代码得到的,次此升级时间:2016,9.28
     *
     * account = UserData.getData(getApplicationContext()).getAccount();
     * Accout account账户类为第一次登陆以后会被赋值,而在"个人设置"退出登陆按钮中被 clear()掉了
     *
     * 如果accout账户信息存在说明原来用户登陆过,信息不为空,则直接进入到主页面MainActivity
     * 如果accout账户信息为空,说明用户第一次进入或者是已经点了"个人设置"页面中的退出登陆按钮,导致accout被clear()
     *
     * 其代码思想为::
     * if(account == null){
     * 进入登陆注册
     * }else{
     * 进入主页面
     * }
     */
    private void initView() {
        firstStartImageView = (ImageView) findViewById(R.id.imageView_first_start);
        if (this.account == null) {
            Log.e(tag, "accout  kong ");
            handler.postDelayed(runnable, 1000);
        } else {
            Log.e(tag, "accout  not kong ");
            //            registerOrLogin();
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            UISkip.skip(true, StartActivity.this, LoginActivityUpdate.class);
            finish();
        }
    };


    private void registerOrLogin() {
        new AccountServer().registerOrLogin(Constants.ADMIN_ACCOUNT, null, UserData.getData(StartActivity.this).getAccountName(), new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                Log.e(tag, "resiter failure");
                showToast("网络异常，请联网再试");
                UISkip.skipToLoginActivity(StartActivity.this);
                finish();
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                Log.e(tag, "resiter success  if");
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    UserData.getData(StartActivity.this).setAccount(netMessage.getData());
                    //                    UISkip.skipToMainActivityActivity(StartActivity.this);


                    //跳转MainActivity(主页)
                    startActivity(new Intent(StartActivity.this, MainActivity.class));

                    finish();
                } else {
                    Log.e(tag, "resiter success  else");
                    showToast(netMessage.getMsg());
                    //                    UISkip.skipToLoginActivity(StartActivity.this);

                    //跳转登陆页面
                    startActivity(new Intent(StartActivity.this, LoginActivityUpdate.class));
                    finish();
                }
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void sendError(String error, Account account) throws Exception {
        Log.e(tag, "sendError sendError");
        if (TextUtils.isEmpty(error)) {
            Log.e(tag, "sendError if");
            return;
        }
        Log.e(tag, "sendError if 外边");

        ErrorLogInfo errorLogInfo = new ErrorLogInfo();
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        errorLogInfo.versionCode = packInfo.versionCode;
        errorLogInfo.device = Build.BRAND + " " + Build.MODEL;
        errorLogInfo.content = error;
        errorLogInfo.date = DateUtils.getDate(System.currentTimeMillis());
        if (account != null) {
            errorLogInfo.mobile = account.account;
            Log.e(tag, "sendError account !null");
        }
        Log.e(tag, "sendError account !nullz外面");
        Gson gson = new Gson();
        String errorLogInfoJson = gson.toJson(errorLogInfo, ErrorLogInfo.class);
        new AccountServer().sendErrorLog(errorLogInfoJson, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                Log.e(tag, "sendError   onFailure");
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                PreferencesHelper.get(StartActivity.this).put(PreferenceKeyConstants.KEY_ERROR, null);
                Log.e(tag, "sendError   onSuccess");
            }
        });
    }

}

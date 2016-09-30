package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.syezon.plugin.call.module.generator.call.CallControlGenerator;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.AppVersion;
import com.unihiltop.xiangliao.bean.Calllog;
import com.unihiltop.xiangliao.call.ui.CallingViewManager;
import com.unihiltop.xiangliao.call.utils.CallPhone;
import com.unihiltop.xiangliao.call.utils.GlobalInfo;
import com.unihiltop.xiangliao.constant.PreferenceKeyConstants;
import com.unihiltop.xiangliao.data.ContactData;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.dialog.AlertDialog;
import com.unihiltop.xiangliao.fragment.CallFragment;
import com.unihiltop.xiangliao.fragment.CallFragmentUpdate;
import com.unihiltop.xiangliao.fragment.CallLogFragment;
import com.unihiltop.xiangliao.fragment.CalllogRecordFragment;
import com.unihiltop.xiangliao.fragment.ContactFragment;
import com.unihiltop.xiangliao.fragment.MineFragment;
import com.unihiltop.xiangliao.fragment.MyFragment;
import com.unihiltop.xiangliao.manager.ContactManager;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.netserver.AppVersionServer;
import com.unihiltop.xiangliao.netserver.CallServer;
import com.unihiltop.xiangliao.util.PreferencesHelper;
import com.unihiltop.xiangliao.util.UISkip;

/**
 * 主界面控制整个程序的主入口
 *
 * @author yangyang
 */
public class MainActivity extends FragmentActivity {

    /**
     * 打印日志的TAG标签
     */
    protected static final String TAG = "MainActivity";

    /**
     * 需要用到的图片资源
     */
    private int[][] tabIcons = {
            {R.drawable.ic_tab_phone_normal, R.drawable.ic_tab_phone_selected},
            {R.drawable.ic_tab_quick_chat_normal, R.drawable.ic_tab_quick_chat_selected},
            {R.drawable.ic_tab_mall_normal, R.drawable.ic_tab_mall_selected},
            {R.drawable.ic_tab_me_normal, R.drawable.ic_tab_me_selected}
    };
    /**
     * Application对象
     */
    private XiangLiaoApplication kuaiHuaApplication;

    /**
     * 之前用到的Fragment对象
     */
    private MyFragment            myFragment;//我的详聊(旧)
    private CallFragment          callFragment;//拨号键盘页面(旧)
    private CallLogFragment       callLogFragment;//通话记录(旧)
    private ContactFragment       contactFragment;//通讯录页面(依然延续)
    /**
     * 更新后的Fragment对象,至于通讯录,依然沿用原来有的布局
     */
    private MineFragment          mineFragment;//我的详聊(更新后)
    private CallFragmentUpdate    callFragmentUpdate;//拨号键盘页面(更新后)
    private CalllogRecordFragment calllogRecordFragment;//最近通话(新)


    /**
     * 控件
     */
    //        private BottomTabPagerView    tab;
    private FrameLayout fl_main;//针布局视图
    private RadioGroup  rg_main;
    private RadioButton rb_bohao;//拨号键盘按钮
    private RadioButton rb_tonghua;//最近通话按钮
    private RadioButton rb_tongxunlu;//通讯录按钮
    private RadioButton rb_mine;//我的详聊按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_update);
        initReceiver();
        initData();
        initView();


        TelephonyManager tm = (TelephonyManager) MainActivity.this
                .getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(new TradetimePhoneStateListener(MainActivity.this),
                PhoneStateListener.LISTEN_CALL_STATE);

    }


    /**
     * 初始化控件
     */
    private void initView() {
        //初始化Fragment对象
        mineFragment = new MineFragment();//我的详聊(更新后)
        callFragmentUpdate = new CallFragmentUpdate();//拨号键盘页面(更新后)
        calllogRecordFragment = new CalllogRecordFragment();//最近通话(新)
        contactFragment = new ContactFragment();


        //findViewById()操作
        fl_main = (FrameLayout) findViewById(R.id.rl_main);//针布局视图
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        rb_bohao = (RadioButton) findViewById(R.id.rb_bohao);//拨号键盘按钮
        rb_tonghua = (RadioButton) findViewById(R.id.rb_tonghua);//最近通话按钮
        rb_tongxunlu = (RadioButton) findViewById(R.id.rb_tongxunlu);//通讯录按钮
        rb_mine = (RadioButton) findViewById(R.id.rb_mine);//我的详聊按钮

        //页面初始化时,默认选中第一个按钮,默认选中第一个页面
        rb_bohao.setChecked(true);
        createFragment(callFragmentUpdate);

        //设置点击按钮切换不同页面
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {//点击第一个按钮,显示"拨号键盘"fragment页面
                    case R.id.rb_bohao:
                        createFragment(callFragmentUpdate);
                        break;
                    case R.id.rb_tonghua://点击第二个按钮,显示"最近通话"fragment页面
                        createFragment(calllogRecordFragment);
                        break;
                    case R.id.rb_tongxunlu://点击第三个按钮,显示"通讯录"fragment页面
                        createFragment(contactFragment);
                        break;
                    case R.id.rb_mine://点击第四个按钮,显示"我的详聊"fragment页面
                        createFragment(mineFragment);
                        break;
                }
            }
        });

    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        filter.addAction("CALL_STATE_IDLE");
    }


    private void initData() {
        GlobalInfo.isHaveMainActivity = true;
        getConsumerAppVersion();
        kuaiHuaApplication = (XiangLiaoApplication) getApplication();
        kuaiHuaApplication.addActivity(MainActivity.this);

        //PreferencesHelper.get(MainActivity.this).put(PreferenceKeyConstants.KEY_THE_IS_ON_KEY, Killactivity);
    }

    //    /**
    //     * 跳转到具体界面
    //     */
    //    private void initView() {
    //        tab = (BottomTabPagerView) findViewById(R.id.bottomTabPagerView_main);
    //        tab.init(tabIcons, R.array.main_tab_name_string_array,
    //                new FragmentPagerAdapter(getSupportFragmentManager()) {
    //
    //                    @Override
    //                    public int getCount() {
    //                        return 4;
    //                    }
    //
    //                    @Override
    //                    public Fragment getItem(int arg0) {
    //                        if (arg0 == 0) {
    //                            //                            callFragment = new CallFragment();
    //                            callFragmentUpdate = new CallFragmentUpdate();
    //                            return callFragmentUpdate;
    //                        } else if (arg0 == 1) {
    //                            calllogRecordFragment = new CalllogRecordFragment();
    //                            //                            callLogFragment = new CallLogFragment();
    //                            return calllogRecordFragment;
    //                        } else if (arg0 == 2) {
    //                            contactFragment = new ContactFragment();
    //                            return contactFragment;
    //                        } else if (arg0 == 3) {
    ////                                                        myFragment = new MyFragment();
    //                            mineFragment = new MineFragment();
    //                            return mineFragment;
    //
    //                        }
    //                        return null;
    //                    }
    //                });
    //        tab.setTabTextSize(12);
    //        tab.setOnSelectedClickListener(new BottomTabPagerView.OnSelectedClickListener() {
    //            @Override
    //            public void onSelected(int oldSelected, int selected) {
    //                if (selected == 0) {
    //                    if (callFragment != null) {
    //                        callFragment.onResume();
    //                    }
    //                }
    //            }
    //        });
    //
    //    }

    //	@Override
    //	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //		if (resultCode == RESULT_OK) {
    //
    //			switch (requestCode) {
    //				case PHOTO_SHOP_REQUEST_GALLERY:
    //					String imgPath = AndroidImageUtils.getPath(MainActivity.this, data.getData());
    //					myFragment.setImg(imgPath);
    //					break;
    //				case PHOTO_SHOP_REQUEST_TAKEPHOTO:
    //					myFragment.setImg();
    //					break;
    //				case 101:
    //					finish();
    //					break;
    //			}
    //			}
    //
    //		super.onActivityResult(requestCode, resultCode, data);
    //	}


    public void createFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.rl_main, fragment);
        transaction.commit();
    }


    /**
     * 按两次退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出功能
     */
    private void exit() {
        AlertDialog alertDialog = new AlertDialog(MainActivity.this);
        alertDialog.setAlertClickListener(new AlertDialog.AlertClickListener() {

            @Override
            public void sure() {
                UISkip.skip(false, MainActivity.this, LoginActivity.class);
                MainActivity.this.finish();

            }
        });
        alertDialog.setAlert("是否确定退出？");
        alertDialog.show();
    }


    private void getConsumerAppVersion() {
        new AppVersionServer().getAppVersion(new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    final AppVersion appVersion = new Gson().fromJson(netMessage.getData(), AppVersion.class);
                    try {
                        if (appVersion.getVersionCode() > getPackageInfo().versionCode) {
                            AlertDialog dialog = new AlertDialog(MainActivity.this);
                            dialog.setAlert("检测到新版本，是否更新？");
                            dialog.setSureButton("更新");
                            dialog.setCancelButton("放弃");
                            dialog.setAlertClickListener(new AlertDialog.AlertClickListener() {

                                @Override
                                public void sure() {
                                    Uri uri = Uri.parse(appVersion.getDownloadURL());

                                    String url = uri.toString();
                                    if (!url.startsWith("http://")) {
                                        Toast.makeText(MainActivity.this, "下载地址错误", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(downloadIntent);
                                }
                            });
                            dialog.show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "获取最新版本失败，请稍后再试", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    /**
     * 获取版本信息
     *
     * @return 版本信息
     * @throws Exception
     */
    private PackageInfo getPackageInfo() throws Exception {
        PackageManager packageManager = MainActivity.this.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(MainActivity.this.getPackageName(), 0);
        return packInfo;
    }


    public class TradetimePhoneStateListener extends PhoneStateListener {
        private Activity context;

        public TradetimePhoneStateListener(Activity cont) {
            this.context = cont;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i("TAG", "电话状态改变了");
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // 判断是否在软件中拨打
                    if (GlobalInfo.IsAutoAnswer) {
                        GlobalInfo.IsAutoAnswer = false;
                        GlobalInfo.IsAutoOff = true;
                    }
                    if ((TextUtils.isEmpty(incomingNumber)
                            || incomingNumber.startsWith("076")
                            || incomingNumber.startsWith("075")
                            || incomingNumber.startsWith("066")
                            || incomingNumber.length() < 3)) {
                        ContactManager.getInstance(context).insertKuaiHua(context, incomingNumber);
                    } else {
                        Calllog calllog = new Calllog();
                        calllog.setDate(System.currentTimeMillis());
                        calllog.setName(ContactData.get(MainActivity.this).getName(incomingNumber));
                        calllog.setNumber(incomingNumber);
                        calllog.setType(CallLog.Calls.INCOMING_TYPE);
                        ContactData.get(MainActivity.this).addCalllog(calllog);
                        if (calllogRecordFragment != null) {
                            calllogRecordFragment.addCallLog(calllog);
                        }
                    }
                    //获取拨号到接到来电的时间
                    long callings = System.currentTimeMillis();
                    long called = PreferencesHelper.get(context).getLong(PreferenceKeyConstants.KEY_CALL_TIME, 0L);
                    long calledRow = callings - called;
                    if (
                        //!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, incomingNumber)&&
                            CallPhone.Called == true && calledRow < 30 * 1000) {
                        if (Build.BRAND.toLowerCase().contains("samsung")
                                && PreferencesHelper.get(context).isListenOrder()) {
                            CallControlGenerator.headsetControl(context);
                            CallPhone.Called = false;
                        } else if (PreferencesHelper.get(context).isListenOrder()) {
                            CallControlGenerator.answer(context);
                            CallPhone.Called = false;
                        }

                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("接听");
                    // 接听
                    GlobalInfo.IsCalling = true;
                    if (Build.VERSION.SDK_INT < 21
                            && !Build.BRAND.toLowerCase().contains("meizu")
                            && PreferencesHelper.get(context).isListenOrder()
                        //&& !Build.BRAND.toLowerCase().contains("oppo")
                            ) {
                        CallingViewManager.get(context).refrshView();
                        return;
                    } else {
                        //进入Activity接听界面的计时处理广播
                        Intent intent = new Intent("CALL_ACTIVITY_CHANGE");
                        context.sendBroadcast(intent);
                        Log.i("TTT", "进入Activity接听界面的计时处理广播");
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE: // 挂断
                    // 挂断
                    Log.i("TAGG", "自动挂断广播已发送");
                    if (Build.VERSION.SDK_INT < 21
                            && !Build.BRAND.toLowerCase().contains("meizu")
                            && PreferencesHelper.get(context).isListenOrder()
                        //&& !Build.BRAND.toLowerCase().contains("oppo")
                            ) {
                        if (GlobalInfo.IsCalling) {
                            CallingViewManager.get(context).closeMinati();
                            CallingViewManager.get(context).dismiss();
                            GlobalInfo.IsCalling = false;
                            // 把通话记录上传到服务器
                            uploadTheBill();
                        }
                    } else {
                        Intent intent = new Intent("CALL_STATE_IDLE");
                        context.sendBroadcast(intent);
                        uploadTheBill();
                    }

                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }

    }

    private void uploadTheBill() {
        long theBillId = PreferencesHelper.get(MainActivity.this).getLong(PreferenceKeyConstants.KEY_THEBILL_ID);
        Log.i("MainActivitysss", "theBillId2=" + theBillId);
        if (theBillId != 0) {
            long endDate = System.currentTimeMillis();
            long startDate = PreferencesHelper.get(MainActivity.this).getLong(PreferenceKeyConstants.KEY_CALL_TIME, 0L);
            PreferencesHelper.get(MainActivity.this).put(PreferenceKeyConstants.KEY_TALK_TIME, endDate - startDate);
            new CallServer().uploadTheBill(theBillId, new NetMessageCallback() {
                @Override
                public void onFailure(String error, String message) {
                    PreferencesHelper.get(MainActivity.this).put(PreferenceKeyConstants.KEY_THEBILL_ID, 0L);
                }

                @Override
                public void onSuccess(NetMessage netMessage) {
                    if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                        PreferencesHelper.get(MainActivity.this).put(PreferenceKeyConstants.KEY_THEBILL_ID, 0L);
                        PreferencesHelper.get(MainActivity.this).put(PreferenceKeyConstants.KEY_THE_OLD_BILL_ID, 0L);
                        PreferencesHelper.get(MainActivity.this).put(PreferenceKeyConstants.KEY_TALK_TIME, 0L);
                    } else {
                        PreferencesHelper.get(MainActivity.this).put(PreferenceKeyConstants.KEY_THEBILL_ID, 0L);
                    }

                }
            });
            ContactManager.getInstance(MainActivity.this).deleteKuaihuaCallog(MainActivity.this);
        }

    }

    @Override
    protected void onDestroy() {
        GlobalInfo.isHaveMainActivity = false;
        kuaiHuaApplication.removeActivity(MainActivity.this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (callFragment != null) {
            callFragment.onResume();
        }
        //获取手机信息
        Account account1 = UserData.getData(MainActivity.this).getAccount();
        if (account1 != null) {
            Log.i("hyy", account1.account);
            new AccountServer().getAccount(account1.account, new NetMessageCallback() {
                @Override
                public void onFailure(String error, String message) {
                    Toast.makeText(MainActivity.this, "网络异常，请重试", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(NetMessage netMessage) {
                    if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                        UserData.getData(getApplicationContext()).setAccount(netMessage.getData());
                    } else {
                        Toast.makeText(MainActivity.this, "网络异常，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        super.onResume();
    }
}
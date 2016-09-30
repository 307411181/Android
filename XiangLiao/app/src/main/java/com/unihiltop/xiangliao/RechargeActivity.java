package com.unihiltop.xiangliao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.RechargeOrder;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.dialog.ListMenuDialog;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.PayServer;
import com.unihiltop.xiangliao.util.RegExpUtil;
import com.unihiltop.xiangliao.util.UISkip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 快捷充值
 * Created by yangyang on 2015/10/14.
 */
public class RechargeActivity extends FragmentActivity implements PaymentHandler {
    private ImageView ivReturn;//返回
    private EditText etRechargePhone;//输入手机号
    private RelativeLayout rlMonth;//充值月份
    private TextView tvMonth;//充值月份
    private TextView etRechargeMoney;//充值金额
    private RelativeLayout llRecharageCard;//充值卡充值
    private RelativeLayout llRechargeZhifubao;//支付宝充值
    private RelativeLayout llRechargeUnionPay;//银联充值
    private RelativeLayout llRechargeWeChat;//微信充值
    private RelativeLayout rlRechargeOne;//一键支付

    private int money = 0;
    private String monthStr;
    private int monthall;
    private ListMenuDialog listMenuDialog;
    private static String YOUR_URL = "http://114.215.85.203:8080/XL/recharge/pingPlusPlusCallRechargeBill";
    public static final String URL = YOUR_URL;
    private Account account;
    private long accountId;
    private RechargeOrder rechrageOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initData();
        initView();
        initsetView();
        initListenter();
        // 设置要使用的支付方式
        // PingPP日志开关
        PingppLog.DEBUG = true;

    }

    private void initData() {
        account = UserData.getData(RechargeActivity.this).getAccount();
        accountId = UserData.getData(RechargeActivity.this).getAccountId();
        rechrageOrder = new RechargeOrder();
    }

    private void initsetView() {
        String phone1 = etRechargePhone.getText().toString();
        // money = etRechargeMoney.getText().toString();
    }

    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);

        etRechargePhone = (EditText) findViewById(R.id.editText_recharge_phone);

        rlMonth = (RelativeLayout) findViewById(R.id.relativeLayout_month);

        tvMonth = (TextView) findViewById(R.id.textView_month);

        etRechargeMoney = (TextView) findViewById(R.id.textView_recharge_money);

        llRecharageCard = (RelativeLayout) findViewById(R.id.linearLayout_recharage_card);

        llRechargeZhifubao = (RelativeLayout) findViewById(R.id.linearLayout_recharge_zhifubao);

        llRechargeUnionPay = (RelativeLayout) findViewById(R.id.linearLayout_recharge_unionPay);

        llRechargeWeChat = (RelativeLayout) findViewById(R.id.linearLayout_recharge_weChat);
        rlRechargeOne = (RelativeLayout) findViewById(R.id.linearLayout_recharge_one);
    }

    private void initListenter() {
        MyListenter myListenter = new MyListenter();
        ivReturn.setOnClickListener(myListenter);
        rlMonth.setOnClickListener(myListenter);
        llRecharageCard.setOnClickListener(myListenter);
        llRechargeZhifubao.setOnClickListener(myListenter);
        llRechargeUnionPay.setOnClickListener(myListenter);
        llRechargeWeChat.setOnClickListener(myListenter);
        rlRechargeOne.setOnClickListener(myListenter);
    }



    private class MyListenter implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == ivReturn) {
                finish();
            } else if (v == rlMonth) {
                /**
                 * 选择月份与判断充值金额
                 */
                if (listMenuDialog == null) {
                    listMenuDialog = new ListMenuDialog(RechargeActivity.this);
                }
                listMenuDialog.setTitle("选择月份");
                final String[] month = getResources().getStringArray(R.array.choose_month);
                listMenuDialog.setMenuItems(month);
                listMenuDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        monthStr = month[position];
                        monthall = position + 1;
                        rechrageOrder.rechargeMonth = monthall;
                        if (monthall == 12) {
                            tvMonth.setText("充值" + 1 + "年");
                            etRechargeMoney.setText("365" + "元");
                            money = 365;
                        } else if (monthall != 12) {
                            tvMonth.setText(month[position]);
                            etRechargeMoney.setText(monthall * 39 + "元");
                            money = monthall * 39;

                        }
                        listMenuDialog.dismiss();
                    }
                });
                listMenuDialog.show();
            } else if (v == llRecharageCard) {
               phone = etRechargePhone.getText().toString();
                if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, phone)) {
                    MyToast("请输入正确的手机号！");
                    return;
                } else {
                    UISkip.skipToRechargeCarActivity(RechargeActivity.this, phone);
                }

            } else if (v == llRechargeZhifubao) {
                zhifubaopay();
            } else if (v == llRechargeUnionPay) {

            } else if (v == llRechargeWeChat) {
                weixinpay();
            } else if (v == rlRechargeOne) {
                onepay();
            }
        }
    }

    private void MyToast(String mytoast) {
        Toast.makeText(RechargeActivity.this, mytoast, Toast.LENGTH_SHORT).show();
    }

    /**
     * 支付宝充值
     */
    private void zhifubaopay() {
        new PayServer().aliNotifyRechargeResult(new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {

            }

            @Override
            public void onSuccess(NetMessage netMessage) {

            }
        });
    }

    /**
     * 微信支付
     */
    private void weixinpay() {
        new PayServer().weChatNotifyRechargeResult(new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {

            }

            @Override
            public void onSuccess(NetMessage netMessage) {

            }
        });
    }
    private void getNumber(){

    }

    /**
     * 一键支付
     */
   //
    private String orderNo;//订单号
    private String phone;//充值金额
    private void onepay() {

//        // 产生个订单号
//      String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
//               .format(new Date());
        phone = etRechargePhone.getText().toString();
        if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, phone)) {
            Toast.makeText(getApplicationContext(),
                    R.string.reg_exp_error_phone, Toast.LENGTH_LONG).show();
            return;
        }
        if(monthall==0){
            Toast.makeText(getApplicationContext(),
                  "请选择充值月数", Toast.LENGTH_LONG).show();
        }
        final ProgressDialog progressDialog = ProgressDialog.show(
                RechargeActivity.this, null, null);
        new PayServer().rechargeBill(account.account,phone, monthall, money * 100, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                progressDialog.dismiss();
               Toast.makeText(RechargeActivity.this,message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                progressDialog.dismiss();
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {

                    PingppOne.enableChannels(new String[]{"alipay"});

                    orderNo = netMessage.getData();

                    Log.i("orderNo", orderNo + "1=");
                    Log.i("orderNo", orderNo + "");
                    double amount = 0;

                    // JSONArray billLists = new JSONArray();
                    //billLists.put("充值"+":"+phone);
                    JSONArray billList = new JSONArray();
                    billList.put("充值电话" + "x" + phone);

                    billList.put("充值时间" + "x" + monthall + "月");

                  amount = money*100;
                  //  amount = money;
                    // 构建账单json对象
                    JSONObject bill = new JSONObject();
                    JSONObject displayItem = new JSONObject();
                    // JSONObject displayItems = new JSONObject();
                    try {
                        displayItem.put("name", "商品");
                        displayItem.put("contents", billList);
                        // displayItems.put("contents", billList);
                        JSONArray display = new JSONArray();
                        display.put(displayItem);
                        bill.put("order_no", orderNo);
                        bill.put("amount", amount);
                        bill.put("display", display);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // 发起支付
                    Log.i("bill", bill.toString() + "");
                    PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, RechargeActivity.this);

                } else {
                    //Toast.makeText(RechargeActivity.this, "456 = " + netMessage.getCode(), Toast.LENGTH_LONG).show();
                }
            }
        });




    }

//    /**
//     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
//     * 最终支付成功根据异步通知为准
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PayActivity.PAYACTIVITY_REQUEST_CODE) {
//            if (resultCode == PayActivity.PAYACTIVITY_RESULT_CODE) {
//               String result =  data.getExtras().getString("pay_result");
//                String result2 =  data.getExtras().getString("result");
//               int code =  data.getExtras().getInt("code");
//                Log.i("result", result + " " + code);
//                  if (phone.equals(account.account) &&
//                          ( !TextUtils.isEmpty(result) && result.contains("success")) ||
//                          ( !TextUtils.isEmpty(result2) && result2.contains("success"))
//                          ){
//                      account.bill = account.bill + money;
//                      account.integral = account.integral + money;
//                      UserData.getData(RechargeActivity.this).setAccount(account);
//                      Toast.makeText(
//                              this,
//                              "支付成功",
//                              Toast.LENGTH_LONG).show();
//                  }else{
//                      Toast.makeText(
//                              this,
//                              "支付失败",
//                              Toast.LENGTH_LONG).show();
//                  }
//
//            }
//        }
//    }

    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null){
            String result =  data.getExtras().getString("pay_result");
            String result2 =  data.getExtras().getString("result");
            int code =  data.getExtras().getInt("code");

            Log.i("result", result + " " + code);
            if (phone.equals(account.account) &&
                    ( !TextUtils.isEmpty(result) && result.contains("success")) ||
                    ( !TextUtils.isEmpty(result2) && result2.contains("success"))
                    ){
                account.bill = account.bill + money;
                account.integral = account.integral + money;
                UserData.getData(RechargeActivity.this).setAccount(account);
                Toast.makeText(
                        this,
                        "支付成功",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(
                        this,
                        "支付失败",
                        Toast.LENGTH_LONG).show();
            }
        }else{

        }

    }
}

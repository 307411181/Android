package com.unihiltop.xiangliao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * @创建者 张京
 * @创建时间 2016/9/18 16:20
 * @描述 ${快捷充值页面(更新后)}
 */
public class RechargeActivityUpdate extends FragmentActivity implements View.OnClickListener , PaymentHandler{
    private Context   mContext;
    /**
     * 图片
     */
    private ImageView ivRechargePhone;//手机号图片
    private ImageView ivMonth;//选择充值时长的图片
    private ImageView ivRechargeMoney;//充值金额的图片
    private ImageView ivRecharageCard;//充值卡图片
    private ImageView ivRechargeZhifubao;//支付宝图片

    /**
     * 控件
     */
    private EditText  etRechargePhone;//输入手机号的editText
    private TextView  tvMonth;//选择充值时长
    private TextView  tvRechargeMoney;//充值金额
    private TextView  tvRecharageCard;//充值卡
    private TextView  tvRechargeZhifubao;//支付宝充值
    private ImageView ivReturn;//头布局左上方返回按钮
    //    private Button    btnPayment;//一键支付按钮
    //输入法软键盘
    InputMethodManager imm;

    //时间选择器的ListView
    private ListMenuDialog listMenuDialog;
    private String         monthStr;
    private int            monthall;
    private RechargeOrder  rechrageOrder;
    private int money = 0;
    private Account account;
    private long    accountId;
    private static String URL = "http://114.215.85.203:8080/XL/recharge/pingPlusPlusCallRechargeBill";
    /**
     * 一键支付
     */
    //
    private String orderNo;//订单号
    private String phone;//充值金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_recharge_update);
        //获取软键盘管理器
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //        imm.hideSoftInputFromWindow(etRechargePhone.getWindowToken(), 0); //强制隐藏键盘
        //        setFocusImage(ivMonth);

        initView();

        String accountPhoneNumber = UserData.getData(mContext).getAccount().getAccount();
        etRechargePhone.setText(accountPhoneNumber);


        initData();

        initListener();
    }

    private void initData() {
        account = UserData.getData(mContext).getAccount();
        accountId = UserData.getData(mContext).getAccountId();
        rechrageOrder = new RechargeOrder();
    }

    /**
     * 控件点击事件
     */
    private void initListener() {
        etRechargePhone.setOnClickListener(this);
        tvMonth.setOnClickListener(this);
        tvRechargeMoney.setOnClickListener(this);
        tvRecharageCard.setOnClickListener(this);
        tvRechargeZhifubao.setOnClickListener(this);
        ivReturn.setOnClickListener(this);
        //        btnPayment.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        ivRechargePhone = (ImageView) findViewById(R.id.iv_recharge_phone);//手机号图片
        ivMonth = (ImageView) findViewById(R.id.iv_month);//选择充值时长的图片
        ivRechargeMoney = (ImageView) findViewById(R.id.iv_recharge_money);//充值金额的图片
        ivRecharageCard = (ImageView) findViewById(R.id.iv_recharage_card);//充值卡图片
        ivRechargeZhifubao = (ImageView) findViewById(R.id.iv_recharge_zhifubao);//支付宝图片


        etRechargePhone = (EditText) findViewById(R.id.editText_recharge_phone);//输入手机号的editText
        tvMonth = (TextView) findViewById(R.id.textView_month);//选择充值时长
        tvRechargeMoney = (TextView) findViewById(R.id.textView_recharge_money);//充值金额
        tvRecharageCard = (TextView) findViewById(R.id.textview_recharage_card);//充值卡
        tvRechargeZhifubao = (TextView) findViewById(R.id.textView_recharge_zhifubao);//支付宝充值
        ivReturn = (ImageView) findViewById(R.id.imageView_return);//头布局左上方返回按钮
        //        btnPayment = (Button) findViewById(R.id.button_payment);//一键支付按钮
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editText_recharge_phone://点击了手机号输入框
                setFocusImage(ivRechargePhone);
                break;
            case R.id.textView_month://点击了充值时长
                imm.hideSoftInputFromWindow(etRechargePhone.getWindowToken(), 0); //强制隐藏键盘
                setFocusImage(ivMonth);
                /**
                 * 选择月份与判断充值金额,弹出listview选择器,然用户选择时长
                 */
                if (listMenuDialog == null) {
                    listMenuDialog = new ListMenuDialog(mContext);
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
                            tvRechargeMoney.setText("365" + "元");
                            money = 365;
                        } else if (monthall != 12) {
                            tvMonth.setText(month[position]);
                            tvRechargeMoney.setText(monthall * 39 + "元");
                            money = monthall * 39;
                        }
                        listMenuDialog.dismiss();
                    }
                });
                listMenuDialog.show();
                break;
            case R.id.textView_recharge_money://点击了充值金额
                imm.hideSoftInputFromWindow(etRechargePhone.getWindowToken(), 0); //强制隐藏键盘
                setFocusImage(ivRechargeMoney);
                break;
            case R.id.textview_recharage_card://点击了充值卡充值
                setFocusImage(ivRecharageCard);
                imm.hideSoftInputFromWindow(etRechargePhone.getWindowToken(), 0); //强制隐藏键盘
                phone = etRechargePhone.getText().toString();
                if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, phone)) {
                    showToast("请输入正确的手机号！");
                    return;
                } else {
                    UISkip.skipToRechargeCarActivity(RechargeActivityUpdate.this, phone);
                }
                break;
            case R.id.textView_recharge_zhifubao://点击了支付宝充值
                setFocusImage(ivRechargeZhifubao);
                imm.hideSoftInputFromWindow(etRechargePhone.getWindowToken(), 0); //强制隐藏键盘
                onepay();
                break;
            case R.id.imageView_return:
                finish();
                break;

            //            case R.id.button_payment:
            //
            //                phone = etRechargePhone.getText().toString();
            //                if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, phone)) {
            //                    showToast("请输入正确的手机号！");
            //                    return;
            //                } else {
            //                    UISkip.skipToRechargeCarActivity(RechargeActivityUpdate.this, phone);
            //                }
            //
            //                break;
        }
    }

    /**
     * 一键支付
     */
    //
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
        if (monthall == 0) {
            Toast.makeText(getApplicationContext(),
                    "请选择充值月数", Toast.LENGTH_LONG).show();
        }
        final ProgressDialog progressDialog = ProgressDialog.show(
                RechargeActivityUpdate.this, null, null);
        new PayServer().rechargeBill(account.account, phone, monthall, money * 100, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                progressDialog.dismiss();
                Toast.makeText(RechargeActivityUpdate.this, message, Toast.LENGTH_LONG).show();
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

                    amount = money * 100;
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
                    // 发起支付
                    Log.i("bill", bill.toString() + "");
                    PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), URL, RechargeActivityUpdate.this);

                } else {
                    //Toast.makeText(RechargeActivity.this, "456 = " + netMessage.getCode(), Toast.LENGTH_LONG).show();
                }
            }
        });


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
     * 根据传入的ImageView设置哪些图片应该设置为焦点图片
     */
    public void setFocusImage(ImageView iv) {
        ivRechargePhone.setBackgroundResource(R.drawable.ic_recharge_phone_normal);
        ivMonth.setBackgroundResource(R.drawable.ic__recharge_duration_normal);
        ivRechargeMoney.setBackgroundResource(R.drawable.ic__recharge_amount_normal);
        ivRecharageCard.setBackgroundResource(R.drawable.ic__recharge_card_normal);
        ivRechargeZhifubao.setBackgroundResource(R.drawable.ic__recharge_zhifubao_normal);

        if (iv == ivRechargePhone) {
            ivRechargePhone.setBackgroundResource(R.drawable.ic_recharge_phone_focus);
            return;
        }
        if (iv == ivMonth) {
            ivMonth.setBackgroundResource(R.drawable.ic__recharge_duration_focus);
            return;
        }
        if (iv == ivRechargeMoney) {
            ivRechargeMoney.setBackgroundResource(R.drawable.ic__recharge_amount_focus);
            return;
        }
        if (iv == ivRecharageCard) {
            ivRecharageCard.setBackgroundResource(R.drawable.ic__recharge_card_focus);
            return;
        }
        if (iv == ivRechargeZhifubao) {
            ivRechargeZhifubao.setBackgroundResource(R.drawable.ic__recharge_zhifubao_focus);
            return;
        }
    }


    /**
     * 弹出Toast的方法
     *
     * @param text
     */
    private void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {
            String result = data.getExtras().getString("pay_result");
            String result2 = data.getExtras().getString("result");
            int code = data.getExtras().getInt("code");

            Log.i("result", result + " " + code);
            if (phone.equals(account.account) &&
                    (!TextUtils.isEmpty(result) && result.contains("success")) ||
                    (!TextUtils.isEmpty(result2) && result2.contains("success"))
                    ) {
                account.bill = account.bill + money;
                account.integral = account.integral + money;
                UserData.getData(RechargeActivityUpdate.this).setAccount(account);
                Toast.makeText(
                        this,
                        "支付成功",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(
                        this,
                        "支付失败",
                        Toast.LENGTH_LONG).show();
            }
        } else {

        }

    }
}

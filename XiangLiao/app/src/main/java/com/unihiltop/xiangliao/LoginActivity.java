package com.unihiltop.xiangliao;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unihiltop.xiangliao.constant.Constants;
import com.unihiltop.xiangliao.constant.ToastMsgConstants;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.util.RegExpUtil;
import com.unihiltop.xiangliao.util.UISkip;

/**
 * 登录
 */
public class LoginActivity extends Activity {
    private EditText        etPhone;// 手机号
    private EditText        etRecommend;//推荐人手机号
        private EditText etCaptcha;// 验证码
    private Button          btCaptcha;// 验证码
    private CheckBox        cbAgreement;
    private TextView        tvAgreement;// 协议
    private OnClickListener clickListener;
    private Button          btRegist;// 登陆

    private String mobile;
    private String verificationCode;
    private String recommendMobile;
    private final boolean        yesnoKey       = true;
    private       ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
        initListerten();
    }

    private void initData() {


    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.editText_phone);
        etRecommend = (EditText) findViewById(R.id.editText_recommend);
        //        etCaptcha = (EditText) findViewById(R.id.editText_captcha);
        btCaptcha = (Button) findViewById(R.id.button_captcha);
        cbAgreement = (CheckBox) findViewById(R.id.checkbox_register_agreement);
        tvAgreement = (TextView) findViewById(R.id.textView_register_agreement);
        btRegist = (Button) findViewById(R.id.button_regist);
    }

    private void initListerten() {
        clickListener = new MyClickListener();
        tvAgreement.setOnClickListener(clickListener);
        btCaptcha.setOnClickListener(clickListener);
        btRegist.setOnClickListener(clickListener);
    }

    private class MyClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == tvAgreement) {
                UISkip.skip(false, LoginActivity.this, AgreementActivity.class);
            } else if (v == btRegist) {
                register();
            }

        }

    }

    /**
     * 登陆
     */
    private void register() {

        mobile = etPhone.getText().toString().trim();
        if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, mobile)) {
            Toast.makeText(getApplicationContext(),
                    R.string.reg_exp_error_phone, Toast.LENGTH_LONG).show();
            return;
        }
        recommendMobile = etRecommend.getText().toString().trim();

        if (!cbAgreement.isChecked()) {
            showToast("请同意用户服务条款");
            return;
        }
        /**
         * 判断验证码是否为空
         */
        verificationCode = etCaptcha.getText().toString();
        if (TextUtils.isEmpty(verificationCode)) {
            showToast("请输入验证码");
            return;
        }

        progressDialog = ProgressDialog.show(
                LoginActivity.this, "登录中", "登录中，请稍等…");
        new AccountServer().registerOrLoginUpdate(Constants.ADMIN_ACCOUNT, recommendMobile, mobile,verificationCode, new NetMessageCallback() {

            @Override
            public void onFailure(String error, String message) {
                Log.i("", "error=" + error + ";\n message=" + message);
                progressDialog.dismiss();
                showToast(ToastMsgConstants.FailureMessage);
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    UserData.getData(getApplicationContext()).setAccount(netMessage.getData());
                    progressDialog.dismiss();
                    UISkip.skipToMainActivityActivity(LoginActivity.this);
                } else {
                    progressDialog.dismiss();
                    showToast(netMessage.getMsg());
                }
            }
        });
    }

    /**
     * 弹出Toast方法
     *
     * @param msg
     */
    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
            // super.onBackPressed();
            XiangLiaoApplication application = (XiangLiaoApplication) getApplication();
            application.exit();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

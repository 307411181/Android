package com.minze.shoppingmall.shoppingmall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.base.BaseActivity;
import com.minze.shoppingmall.shoppingmall.util.TostUtrils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @创建者 张京
 * @创建时间 2016/9/5 9:16
 * @描述 ${TODO}
 */
public class RegisterActivity extends BaseActivity {


    @InjectView(R.id.iv_register_head)
    ImageView    mIvRegisterHead;
    @InjectView(R.id.btn_register_get_verification_code)
    Button       mBtnRegisterGetVerificationCode;
    @InjectView(R.id.btn_register_register)
    Button       mBtnRegisterRegister;
    @InjectView(R.id.rl_register_login)
    LinearLayout mRlRegisterLogin;

    @Override
    public void setContentView(Bundle bundle) {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initData() {

    }

    @Override
    public void settingViewOrAdapter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick({R.id.iv_register_head, R.id.btn_register_get_verification_code, R.id.btn_register_register, R.id.rl_register_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_head:
                TostUtrils.showToast("注册头像");
                break;
            case R.id.btn_register_get_verification_code:
                TostUtrils.showToast("获取验证码");
                break;
            case R.id.btn_register_register:
                TostUtrils.showToast("提交注册吧");
                break;
            case R.id.rl_register_login:
                startAnActivity(LoginActivity.class);
                break;
        }
    }
}

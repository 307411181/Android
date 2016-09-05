package com.minze.shoppingmall.shoppingmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.minze.shoppingmall.shoppingmall.R;
import com.minze.shoppingmall.shoppingmall.base.BaseActivity;
import com.minze.shoppingmall.shoppingmall.util.TostUtrils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @创建者 张京
 * @创建时间 2016/9/2 15:46
 * @描述 ${登陆页面,用户输入用户名,密码,提交并登陆
 * 页面还包括"立即注册","忘记密码",以及"第三方登陆"按钮功能}
 */
public class LoginActivity extends BaseActivity {
    @InjectView(R.id.iv_login_head)
    ImageView   mIvLoginHead;
    @InjectView(R.id.et_login_username)
    EditText    mEtLoginUsername;
    @InjectView(R.id.et_login_password)
    EditText    mEtLoginPassword;
    @InjectView(R.id.btn_login_register)
    Button      mBtnLoginRegister;
    @InjectView(R.id.btn_login_forget_password)
    Button      mBtnLoginForgetPassword;
    @InjectView(R.id.btn_login_login)
    Button      mBtnLoginLogin;
    @InjectView(R.id.disanfangdenglu)
    TextView    mDisanfangdenglu;
    @InjectView(R.id.btn_login_weixin_login)
    ImageButton mBtnLoginWeixinLogin;
    @InjectView(R.id.btn_login_weibo_login)
    ImageButton mBtnLoginWeiboLogin;
    @InjectView(R.id.btn_login_qq_login)
    ImageButton mBtnLoginQqLogin;

    @Override
    public void setContentView(Bundle bundle) {
        setContentView(R.layout.activity_login);
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

    @OnClick({R.id.iv_login_head, R.id.btn_login_register, R.id.btn_login_forget_password, R.id.btn_login_login, R.id.btn_login_weixin_login, R.id.btn_login_weibo_login, R.id.btn_login_qq_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_head:
                TostUtrils.showToast("头像");
                break;
            case R.id.btn_login_register:
                Intent registerIntent = new Intent(mContext, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.btn_login_forget_password:
                startAnActivity(ForgetPasswordActivity.class);
                break;
            case R.id.btn_login_login:
                TostUtrils.showToast("登陆");
                break;
            case R.id.btn_login_weixin_login:
                TostUtrils.showToast("微信登陆");
                break;
            case R.id.btn_login_weibo_login:
                TostUtrils.showToast("微博登陆");
                break;
            case R.id.btn_login_qq_login:
                TostUtrils.showToast("qq登陆");
                break;
        }
    }
}

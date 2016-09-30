package com.unihiltop.xiangliao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.constant.Constants;
import com.unihiltop.xiangliao.constant.ToastMsgConstants;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.util.RegExpUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @创建者 张京
 * @创建时间 2016/9/19 10:31
 * @描述 ${登陆页面(更新)}
 */
public class LoginActivityUpdate extends Activity implements View.OnFocusChangeListener, View.OnClickListener {

    private Context mContext;

    String codeUrl = "http://139.129.217.239:8080/XL/captchaController/requestCaptcha";
    private static final String loginUrl = "";

    private final OkHttpClient client = new OkHttpClient();


    /**
     * EditText,协议按钮,登陆按钮
     */
    private EditText             etPhone;// 手机号
    private EditText             etRecommend;//推荐人手机号
    private EditText             etCaptcha;// 验证码
    private LinearLayout         btCaptcha;// 验证码按钮
    private CheckBox             cbAgreement;//多选按钮
    private TextView             tvAgreement;// 协议
    private View.OnClickListener clickListener;
    private Button               btRegist;// 登陆

    private TextView mTvSecond;
    private TextView mTvSend;

    private String mobile;
    private String recommendMobile;
    private String verificationCode;

    private ProgressDialog progressDialog = null;

    private TimeCount time;//按钮倒计时

    /**
     * 三个Editext前方的图片
     */
    private ImageView iv_phone;//用户名图片
    private ImageView iv_recommend;//推荐人图片
    private ImageView iv_captcha;//验证码图片

    /**
     * 记录阅读协议的按钮是否选中的状态
     */
    private boolean isCheck = true;//因为xml文件中选择按钮是默认选中,所以默认=true


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://请求验证码"失败"失败时返回的信息
                    //                    showToast("失败" + (String) msg.obj);
                    break;
                case 2://请求验证码"成功"时返回的信息
                    //                    showToast("成功" + (String) msg.obj);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_login_update);
        initView();

    }


    /**
     * 初始化控件
     */
    private void initView() {
        etPhone = (EditText) findViewById(R.id.editText_phone);
        etRecommend = (EditText) findViewById(R.id.editText_recommend);//推荐人手机号
        etCaptcha = (EditText) findViewById(R.id.editText_captcha);// 验证码EditText
        btCaptcha = (LinearLayout) findViewById(R.id.btn_captcha);// 验证码按钮
        cbAgreement = (CheckBox) findViewById(R.id.checkbox_register_agreement);//checkbox协议按钮
        tvAgreement = (TextView) findViewById(R.id.textView_register_agreement);// 绿色协议字体
        btRegist = (Button) findViewById(R.id.button_regist);// 登陆

        mTvSecond = (TextView) findViewById(R.id.tv_second);
        mTvSend = (TextView) findViewById(R.id.tv_send);

        /**
         * 初始化图片控件
         */
        iv_phone = (ImageView) findViewById(R.id.iv_phone);//用户名图片
        iv_recommend = (ImageView) findViewById(R.id.iv_recommend);//推荐人图片
        iv_captcha = (ImageView) findViewById(R.id.iv_captcha);//验证码图片

        //设置焦点更换图片
        etPhone.setOnFocusChangeListener(this);
        etRecommend.setOnFocusChangeListener(this);
        etCaptcha.setOnFocusChangeListener(this);

        /**
         * 验证码按钮,协议按钮,协议字体,登陆按钮的点击监听
         */
        btCaptcha.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        btRegist.setOnClickListener(this);
        //协议按钮选择状态监听:::
        //        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        //            @Override
        //
        //            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //                isCheck = isChecked;
        //                if (isChecked) {
        //                    checkbox_selector
        //                    cbAgreement.setBackgroundResource(R.drawable.che);
        //                } else {
        //                    cbAgreement.setBackgroundResource(R.drawable.bg_tile_blue_selector_no_checked);
        //                }
        //            }
        //        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_captcha://验证码按钮
                //拿到用户输入的数据
                mobile = etPhone.getText().toString().trim();
                //判断 手机号 格式是否正确
                if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, mobile)) {
                    Toast.makeText(getApplicationContext(),
                            R.string.reg_exp_error_phone, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //请求网络,获取验证码,使用okhttp请求网络,需要在子线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            requestGetVerificationCode();
                        }
                    }).start();
                    //启动按钮倒计时,设置时长为120秒,,,1秒刷新一次
                    time = new TimeCount(120000, 1000);//参数依次为总时长，计时时间间隔
                    time.start();
                }
                break;
            case R.id.textView_register_agreement:
                startActivity(new Intent(LoginActivityUpdate.this, AgreementActivity.class));
                break;
            case R.id.button_regist:
                register();
                break;
        }
    }


    /**
     * 自定义倒计时类,用于显示发送验证码逻辑
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            //由于构造方法只会执行一次,请求网络代码放到构造方法中
            requestVerificationCode(mobile);
        }


        //刷新时调用,由于指定的是1秒刷新一次,所以此方法每一秒回回调一次
        @Override
        public void onTick(long millisUntilFinished) {
            //显示秒数的TextView显示
            mTvSecond.setVisibility(View.VISIBLE);
            //设置textview的显示内容为120秒递减
            mTvSecond.setText(millisUntilFinished / 1000 + "秒");
            mTvSend.setText("后重新发送");
            //设置"发送验证码"按钮的北京颜色为灰色
            btCaptcha.setBackgroundResource(R.color.darkgray);
            //让次按钮120秒内不可以被点击
            btCaptcha.setClickable(false);


        }

        //当计时方法走完时回调
        @Override
        public void onFinish() {
            //让显示秒数的Textview隐藏
            mTvSecond.setVisibility(View.GONE);
            mTvSend.setText("点击获取验证码");
            //使按钮重新回到可以点击的状态
            btCaptcha.setBackgroundResource(R.drawable.shap_button_backgroud);
            btCaptcha.setClickable(true);
        }
    }

    /**
     * 请求服务器,获取验证码
     */
    private void requestGetVerificationCode() {
        RequestBody formBody = new FormBody.Builder().add("account", mobile).build();
        Request request = new Request.Builder().url(codeUrl).post(formBody).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String message = e.getMessage();
                Message msg = new Message();
                msg.obj = message;
                msg.what = 1;//类型定义为1,方便在HandleMessage方法中查找
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseMsg = response.body().string();
                Message msg = new Message();
                msg.obj = responseMsg;
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
        });

    }

    /**
     * 请求服务器获取验证码
     */
    private void requestVerificationCode(String mobile) {
        RequestParams params = new RequestParams(codeUrl);
        params.addHeader("account", mobile);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("登陆result-------------" + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("登陆onError-------------" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 请求服务器   登陆
     */
    private void register() {

        /**
         * 判断手机号是否符合规定格式
         */
        mobile = etPhone.getText().toString().trim();
        if (!RegExpUtil.match(RegExpUtil.REGEXP_PHONE, mobile)) {
            Toast.makeText(getApplicationContext(),
                    R.string.reg_exp_error_phone, Toast.LENGTH_LONG).show();
            return;
        }
        //推荐人手机号
        recommendMobile = etRecommend.getText().toString().trim();

        /**
         * 判断验证码是否为空
         */
        verificationCode = etCaptcha.getText().toString();
        if (TextUtils.isEmpty(verificationCode)) {
            showToast("请输入验证码");
            return;
        }


        /**
         * 判断用户是否选中了同意协议按钮
         */
        if (!cbAgreement.isChecked()) {
            showToast("请同意用户服务条款");
            return;
        }

        progressDialog = ProgressDialog.show(
                LoginActivityUpdate.this, "登录中", "登录中，请稍等…");

        new AccountServer().registerOrLoginUpdate(Constants.ADMIN_ACCOUNT, recommendMobile, mobile, verificationCode, new NetMessageCallback() {

            @Override
            public void onFailure(String error, String message) {
                Log.i("", "error=" + error + ";\n message=" + message);
                progressDialog.dismiss();
                showToast(ToastMsgConstants.FailureMessage);
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    Log.e("登陆成功", "netMessage" + netMessage.getData());

                    UserData.getData(getApplicationContext()).setAccount(netMessage.getData());
                    Log.e("赋值以后", "" + UserData.getData(getApplicationContext()).getAccount().getAccount().toString());
                    progressDialog.dismiss();
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);

                } else {
                    progressDialog.dismiss();
                    showToast(netMessage.getMsg());
                    Account data = UserData.getData(getApplicationContext()).getAccount();
                }
            }
        });
    }

    /**
     * 通过监听 用户名,推荐人,验证码 输入框获取焦点的状态设置前方图片
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.editText_phone://当"用户输入框"焦点改变时改变前面的图片
                if (hasFocus) {
                    iv_phone.setBackgroundResource(R.drawable.ic_phone_focus);
                } else {
                    iv_phone.setBackgroundResource(R.drawable.ic_phone_normal);
                }
                break;
            case R.id.editText_recommend://当"推荐人输入框"焦点改变时改变前面的图片
                if (hasFocus) {
                    iv_recommend.setBackgroundResource(R.drawable.ic_user_focus);
                } else {
                    iv_recommend.setBackgroundResource(R.drawable.ic_user_normal);
                }
                break;
            case R.id.editText_captcha://当"验证码输入框"焦点改变时改变前面的图片
                if (hasFocus) {
                    iv_captcha.setBackgroundResource(R.drawable.ic_captcha_focus);
                } else {
                    iv_captcha.setBackgroundResource(R.drawable.ic_captcha_normal);
                }
                break;
        }
    }

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

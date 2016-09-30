package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.unihiltop.xiangliao.call.utils.CallPhone;
import com.unihiltop.xiangliao.dialog.AlertDialog;

/**
 * @创建者 张京
 * @创建时间 2016/9/18 16:09
 * @描述 ${帮助与反馈}
 */
public class HelpActivity extends Activity implements View.OnClickListener {
    private Activity    mActivity;
    private Context     mContext;
    /**
     * 页面布局上部四个单选按钮
     */
    private RadioGroup  mRadioGroup;
    private RadioButton rbZhanghu;//账户按钮
    private RadioButton rbTonghua;//通话按钮
    private RadioButton rbZifei;//资费按钮
    private RadioButton rbFankui;//反馈按钮

    /**
     * 没个按钮底下的页签选择器
     */
    private ImageView ivTabZhanghu;
    private ImageView ivTabTonghua;
    private ImageView ivTabZifei;
    private ImageView ivTabFankui;

    /**
     * 头布局 返回按钮,   服务热线按钮
     */
    private Button    btnHotLine;
    private ImageView ivReturn;

    private CallPhone callPhone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        setContentView(R.layout.activity_help);

        initView();
        initData();
        initListener();

        // TODO: 2016/9/18 逻辑
    }

    /**
     * 点击事件
     */
    private void initListener() {

        ivReturn.setOnClickListener(this);
        btnHotLine.setOnClickListener(this);


        /**
         * 单选按钮切换选中状态的事件处理
         */
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_my_zhanghu:
                        setImageViewVisibility(ivTabZhanghu);
                        break;
                    case R.id.rb_my_tonghua:
                        setImageViewVisibility(ivTabTonghua);
                        break;
                    case R.id.rb_my_zifei:
                        setImageViewVisibility(ivTabZifei);
                        break;
                    case R.id.rb_my_fankui:
                        setImageViewVisibility(ivTabFankui);
                        break;
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 初始化控件
     */
    private void initView() {

        mRadioGroup = (RadioGroup) findViewById(R.id.rg_my_button);
        rbZhanghu = (RadioButton) findViewById(R.id.rb_my_zhanghu);
        rbTonghua = (RadioButton) findViewById(R.id.rb_my_tonghua);
        rbZifei = (RadioButton) findViewById(R.id.rb_my_zifei);
        rbFankui = (RadioButton) findViewById(R.id.rb_my_fankui);

        ivTabZhanghu = (ImageView) findViewById(R.id.iv_tab_zhanghu);
        ivTabTonghua = (ImageView) findViewById(R.id.iv_tab_tonghua);
        ivTabZifei = (ImageView) findViewById(R.id.iv_tab_zifei);
        ivTabFankui = (ImageView) findViewById(R.id.iv_tab_fankui);

        btnHotLine = (Button) findViewById(R.id.btn_hot_line);
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
    }

    /**
     * 设置页签显示与隐藏的方法,先让所有四个页签隐藏,再根据传入的imageview指定设置显示
     *
     * @param imageview
     */
    private void setImageViewVisibility(ImageView imageview) {
        ivTabZhanghu.setVisibility(View.INVISIBLE);
        ivTabTonghua.setVisibility(View.INVISIBLE);
        ivTabZifei.setVisibility(View.INVISIBLE);
        ivTabFankui.setVisibility(View.INVISIBLE);

        imageview.setVisibility(View.VISIBLE);
    }

    /**
     * 按钮点击事件处理
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageView_return://用户点击了头布局的返回按钮,结束掉当前Activity
                finish();
                break;
            case R.id.btn_hot_line://点击了服务热线
                CallOrNot();
                break;
        }
    }

    public void CallOrNot() {
        AlertDialog alertDialog = new AlertDialog(mActivity);
        alertDialog.setAlertClickListener(new AlertDialog.AlertClickListener() {
            @Override
            public void sure() {
                //                callPhone.call(mActivity, "详聊客服", "400-6977-553", null);

                String phoneNumber = "400-6977-553";

                //意图：启动电话程序
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                //设置意图指定打电话
                intent.setData(Uri.parse("tel:" + phoneNumber));
                //开启系统拨号器
                startActivity(intent);
            }
        });
        alertDialog.setAlert("是否给" + "详聊客服" + ":" + "400-6977-553" + "拨打电话");
        alertDialog.show();
    }
}

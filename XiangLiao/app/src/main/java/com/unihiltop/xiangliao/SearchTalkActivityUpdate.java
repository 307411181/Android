package com.unihiltop.xiangliao;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.unihiltop.xiangliao.adapter.AdapterSearchResult;
import com.unihiltop.xiangliao.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @创建者 张京
 * @创建时间 2016/9/18 17:04
 * @描述 ${话单查询}
 */
public class SearchTalkActivityUpdate extends Activity implements View.OnClickListener {

    private Context mContext;

    /**
     * 布局按钮
     */
    private ImageView ivReturn;//返回
    private TextView  tvStartTime;//开始时间
    private TextView  tvEndTime;//结束时间
    private Button    btSearchTalk;//查询
    /**
     * 记录数据的变量
     */
    private Long      startime;//记录开始时间的变量
    private Long      endtime;//记录结束时间的变量
    /**
     * 开始时间,结束时间前面的图片
     */
    private ImageView ivStartTime;
    private ImageView ivEndTime;

    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_search_talk_update);

        initData();
        initView();
        initListenter();
    }

    private void initListenter() {
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        btSearchTalk.setOnClickListener(this);
        ivReturn.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);//返回
        tvStartTime = (TextView) findViewById(R.id.textView_startTime);//开始时间
        tvEndTime = (TextView) findViewById(R.id.textView_endTime);//结束时间
        btSearchTalk = (Button) findViewById(R.id.button_search_talk);//查询

        ivStartTime = (ImageView) findViewById(R.id.iv_startTime);
        ivEndTime = (ImageView) findViewById(R.id.iv_endTime);
    }

    private void initData() {
        list = new ArrayList();
        AdapterSearchResult adapterSearchResult = new AdapterSearchResult(mContext, list);
    }

    /**
     * 控件点击监听....
     * 点击开始时间,结束时间Textview 更换前面的图片
     * 并且要弹出时间选择器
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textView_startTime://点击"开始时间",设置前面图片更改为获得焦点的图片
                ivStartTime.setBackgroundResource(R.drawable.ic_huafeichaxun_focus);
                ivEndTime.setBackgroundResource(R.drawable.ic_huafeichaxun_normal);
                showDateDlg();//弹出日期选择器
                break;
            case R.id.textView_endTime://点击"结束时间",设置前面图片更改为获得焦点的图片
                ivStartTime.setBackgroundResource(R.drawable.ic_huafeichaxun_normal);
                ivEndTime.setBackgroundResource(R.drawable.ic_huafeichaxun_focus);
                showDateDlgend();//弹出日期选择器
                break;
            case R.id.button_search_talk://跳转查询话单页面
                Intent intent = new Intent(mContext, SearchResulatActivity.class);
                intent.putExtra("startime", startime);
                intent.putExtra("endtime", endtime);
                startActivity(intent);
                break;
            case R.id.imageView_return:
                finish();
                break;
        }
    }

    private void showDateDlgend() {
        /**
         * 获取时间
         */
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                tvEndTime.setText(year + "-" + (monthOfYear + 1) + "-"
                        + dayOfMonth);

                endtime = DateUtils.getLongTimeDay(year + "-" + (monthOfYear + 1) + "-"
                        + (dayOfMonth + 1));

            }
        };
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                mContext, dateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();

    }

    private void showDateDlg() {
        /**
         * 获取时间
         */
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                tvStartTime.setText(year + "-" + (monthOfYear + 1) + "-"
                        + dayOfMonth);
                startime = DateUtils.getLongTimeDay(year + "-" + (monthOfYear + 1) + "-"
                        + dayOfMonth);
            }
        };
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                mContext, dateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();

    }
}

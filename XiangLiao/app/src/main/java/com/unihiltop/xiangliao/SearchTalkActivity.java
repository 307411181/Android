package com.unihiltop.xiangliao;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unihiltop.xiangliao.adapter.AdapterSearchResult;
import com.unihiltop.xiangliao.util.DateUtils;
import com.unihiltop.xiangliao.util.UISkip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 话单查询
 * Created by yangyang on 2015/10/14.
 */
public class SearchTalkActivity extends Activity{
    private ImageView ivReturn;//返回
    private LinearLayout  llStartTime;//开始时间
    private TextView tvStartTime;//开始时间
    private LinearLayout  llEndTime;//结束时间
    private TextView tvEndTime;//结束时间
    private  Button btSearchTalk;//查询
    private Long starttime;
    private Long endtime;
    private List list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_talk);
        initData();
        initView();
        initListenter();
    }

    private void initData() {
        list = new ArrayList();
        AdapterSearchResult  adapterSearchResult = new AdapterSearchResult(SearchTalkActivity.this,list);
    }
    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);

        llStartTime = (LinearLayout) findViewById(R.id.linearLayout_startTime);

        tvStartTime = (TextView) findViewById(R.id.textView_startTime);

        tvEndTime = (TextView) findViewById(R.id.textView_endTime);

        btSearchTalk = (Button) findViewById(R.id.button_search_talk);
    }
    private void initListenter() {
        MyListenter myListenter = new MyListenter();
        ivReturn.setOnClickListener(myListenter);
        tvStartTime.setOnClickListener(myListenter);
        tvEndTime.setOnClickListener(myListenter);
        btSearchTalk.setOnClickListener(myListenter);
    }
    private class MyListenter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v == ivReturn){
                finish();
            }else if (v == tvStartTime){
                showDateDlg();
            }else if (v ==tvEndTime){
                showDateDlgend();
            }else if(v == btSearchTalk){
                if (starttime != null||endtime != null){
                    UISkip.skipToSearchResulatActivity(SearchTalkActivity.this,starttime,endtime);
                }else {
                    Toast.makeText(getApplication(), "请输入你要查询的具体时间！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 开始时间
     */
    private void showDateDlg(){
        /**
         * 获取时间
         */
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
               tvStartTime.setText(year + "-" + (monthOfYear + 1) + "-"
                        + dayOfMonth);
                        starttime = DateUtils.getLongTimeDay(year + "-" + (monthOfYear + 1) + "-"
                                + dayOfMonth);

            }
        };
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SearchTalkActivity.this, dateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();

    }

    /**
     * 结束时间
     */
    private void showDateDlgend(){
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
                        + (dayOfMonth+1));

            }
        };
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SearchTalkActivity.this, dateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        //datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();

    }
}

package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CallingActivity extends Activity {
    private TextView textview_phone_number;
    private TextView textView_phone_name;
    private LinearLayout linearLayout_Prompt;
    private LinearLayout linearLayout_wait,linearLayout_time;
    private Button button_wait,botton_cancel,botton_down;
    private String phonenumber;
    private String name;
    private Chronometer chronometer;
    private UpReceiver upReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        phonenumber= getIntent().getStringExtra("phonenumber");
        name=getIntent().getStringExtra("name");
        initView();
        initListener();
        IntentFilter filter=new IntentFilter();
        filter.addAction("CALL_STATE_IDLE");
        filter.addAction("CALL_ACTIVITY_CHANGE");
        upReceiver=new UpReceiver();
        registerReceiver(upReceiver, filter);
    }

    private void initListener() {
        button_wait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout_Prompt.setVisibility(View.GONE);
                linearLayout_wait.setVisibility(View.VISIBLE);
                linearLayout_time.setVisibility(View.GONE);
                botton_cancel.setVisibility(View.VISIBLE);
                button_wait.setVisibility(View.GONE);
                botton_down.setVisibility(View.GONE);
            }
        });
        botton_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        botton_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textview_phone_number.setText(phonenumber);
        textView_phone_name.setText(name);

    }

    private void initView() {
        textview_phone_number= (TextView) findViewById(R.id.textview_phone_number);
        linearLayout_Prompt= (LinearLayout) findViewById(R.id.linearLayout_Prompt);
        linearLayout_wait= (LinearLayout) findViewById(R.id.linearLayout_wait);
        linearLayout_time= (LinearLayout) findViewById(R.id.linearLayout_time);
        button_wait= (Button) findViewById(R.id.button_wait);
        botton_cancel= (Button) findViewById(R.id.botton_cancel);
        botton_down= (Button) findViewById(R.id.botton_down);
        textView_phone_name= (TextView) findViewById(R.id.textView_phone_name);
        chronometer=(Chronometer)findViewById(R.id.chronometer);

    }


    class UpReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            Log.i("TAG", "Activity的广播action"+action);
            if(action.equals("CALL_ACTIVITY_CHANGE")){
                Log.i("TAG", "Activity的广播已收到");
                linearLayout_time.setVisibility(View.VISIBLE);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                linearLayout_Prompt.setVisibility(View.GONE);
                linearLayout_wait.setVisibility(View.GONE);
                botton_cancel.setVisibility(View.GONE);
                button_wait.setVisibility(View.GONE);
                botton_down.setVisibility(View.VISIBLE);
            }else if(action.equals("CALL_STATE_IDLE")){
                Log.i("TAG","销毁Activity的广播已收到");
                CallingActivity.this.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(upReceiver);
    }
}

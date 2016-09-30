package com.unihiltop.xiangliao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.call.utils.CallPhone;
import com.unihiltop.xiangliao.data.ContactData;
import com.unihiltop.xiangliao.util.Tools;

/**
 * 通讯
 */
public class CallFragment extends Fragment{
    private View view;
    private TextView keyboard_phone_number;
    private LinearLayout keyboard_one;
    private LinearLayout keyboard_two;
    private LinearLayout keyboard_three;
    private LinearLayout keyboard_four;
    private LinearLayout keyboard_five;
    private LinearLayout keyboard_six;
    private LinearLayout keyboard_seven;
    private LinearLayout keyboard_eight;
    private LinearLayout keyboard_night;
    private LinearLayout keyboard_asterisk;//*号
    private LinearLayout keyboard_zero;//0
    private LinearLayout keyboard_pound;//#号
    private ImageView imageview_call;
    private ImageView imageview_call_delete;
    private StringBuffer number = new StringBuffer();

    private CallPhone callPhone = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_call, container,
                false);
        initData();
        initView();
        initListenter();
        return view;
    }




    private void initData() {
        callPhone = new CallPhone();
    }



    public void initView() {

        //软键盘最下面的三个按钮
        imageview_call = (ImageView) view.findViewById(R.id.imageview_call);
        imageview_call_delete = (ImageView) view.findViewById(R.id.imageview_call_delete);
        keyboard_phone_number = (TextView) view.findViewById(R.id.keyboard_phone_number);
        //软键盘上的数字键及符号键
        keyboard_one = (LinearLayout) view.findViewById(R.id.keyboard_one);
        keyboard_two = (LinearLayout) view.findViewById(R.id.keyboard_two);
        keyboard_three = (LinearLayout) view.findViewById(R.id.keyboard_three);
        keyboard_four = (LinearLayout) view.findViewById(R.id.keyboard_four);
        keyboard_five = (LinearLayout) view.findViewById(R.id.keyboard_five);
        keyboard_six = (LinearLayout) view.findViewById(R.id.keyboard_six);
        keyboard_seven = (LinearLayout) view.findViewById(R.id.keyboard_seven);
        keyboard_eight = (LinearLayout) view.findViewById(R.id.keyboard_eight);
        keyboard_night = (LinearLayout) view.findViewById(R.id.keyboard_ninght);
        keyboard_asterisk = (LinearLayout) view.findViewById(R.id.keyboard_asterisk);
        keyboard_zero = (LinearLayout) view.findViewById(R.id.keyboard_zero);
        keyboard_pound = (LinearLayout) view.findViewById(R.id.keyboard_pound);


    }

    public void initListenter() {

        //点击拨打电话
        imageview_call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard_phone_number.setText(number);
                String phoneNumber = keyboard_phone_number.getText().toString();
                Log.i("TAG","拨打的电话号码是"+phoneNumber);

                //如果没有输入号码就点击拨打则拨打最近一个通话记录号码
                if (TextUtils.isEmpty(phoneNumber)) {
                    Tools.showToast(getActivity(), "请输入号码后在拨打");
                } else {
                    keyboard_phone_number.setText("");
                    number = new StringBuffer("");
                    callPhone.call(getActivity(), ContactData.get(getActivity()).getName(phoneNumber), phoneNumber, null);
                }
            }
        });

        //点击回退删除号码
        imageview_call_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    number = new StringBuffer(number.substring(0, (number.length() - 1)));
                    keyboard_phone_number.setText(number);
                    String code = keyboard_phone_number.getText().toString();
                    if (code.equals("")) {
                        keyboard_phone_number.setText("");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        //软键盘上的数字键及符号键
        keyboard_one.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("1");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("2");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_three.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("3");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_four.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("4");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_five.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("5");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_six.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("6");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_seven.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("7");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_eight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("8");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_asterisk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("*");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_night.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("9");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_zero.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("0");
                keyboard_phone_number.setText(number);
            }
        });
        keyboard_pound.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("#");
                keyboard_phone_number.setText(number);
            }
        });
    }

    @Override
    public void onResume() {
        if (keyboard_phone_number != null){
            number = new StringBuffer();
            keyboard_phone_number.setText("");
        }
        super.onResume();
    }
}

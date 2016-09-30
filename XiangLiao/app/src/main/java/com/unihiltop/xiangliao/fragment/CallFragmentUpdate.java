package com.unihiltop.xiangliao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.call.utils.CallPhone;
import com.unihiltop.xiangliao.data.ContactData;
import com.unihiltop.xiangliao.util.Tools;

/**
 * @创建者 张京
 * @创建时间 2016/9/19 9:19
 * @描述 ${拨号页面(更新)}
 */
public class CallFragmentUpdate extends Fragment {
    private View         view;
    private TextView     keyboard_phone_number;
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
    private ImageButton  imageview_call;
    private ImageView    imageview_call_delete;
    private StringBuffer number = new StringBuffer();

    private CallPhone callPhone = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_call_update, container,
                false);
        initData();
        initView();
        imageview_call_delete.setVisibility(View.INVISIBLE);
        initListenter();

        return view;
    }

    private void initListenter() {

        if (number.length() == 0) {
            imageview_call_delete.setVisibility(View.INVISIBLE);
        }
        //点击拨打电话按钮的监听
        imageview_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboard_phone_number.setText(number);
                String phoneNumber = keyboard_phone_number.getText().toString();
                Log.i("TAG", "拨打的电话号码是" + phoneNumber);
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


        //删除按钮监听,点击监听,,,,长按监听
        imageview_call_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (number.length() == 0) {
                    System.out.println("0000000000000000");
                    return;
                } else {
                    System.out.println(number.length());
                    number = new StringBuffer(number.substring(0, (number.length() - 1)));
                    keyboard_phone_number.setText(number);
                    System.out.println("删除后" + number.length());
                    if(number.length() == 0){
                        //数字为空时,,隐藏删除按钮
                        imageview_call_delete.setVisibility(View.INVISIBLE);
                    }
                }


                //                number = new StringBuffer(number.substring(0, (number.length() - 1)));
                //                keyboard_phone_number.setText(number);
                //                String code = keyboard_phone_number.getText().toString();
                //                if (code.equals("") || code.length() == 0) {
                //                    keyboard_phone_number.setText("");
                //
                //                    return;
                //                }
                //                Toast.makeText(getActivity(), "点击", Toast.LENGTH_SHORT).show();
            }
        });


        imageview_call_delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                keyboard_phone_number.setText("");
                number.delete(0, number.length());
                imageview_call_delete.setVisibility(View.INVISIBLE);
                System.out.println("长按");
                return true;
            }
        });


        //软键盘上的数字键及符号键
        keyboard_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("1");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("2");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("3");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("4");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("5");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("6");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("7");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("8");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_asterisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("*");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("9");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("0");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
        keyboard_pound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.append("#");
                keyboard_phone_number.setText(number);
                //输入数字就让删除按钮显示
                imageview_call_delete.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onResume() {
        if (keyboard_phone_number != null) {
            number = new StringBuffer();
            keyboard_phone_number.setText("");
        }
        super.onResume();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //打电话
        imageview_call = (ImageButton) view.findViewById(R.id.imageview_call);
        //删除
        imageview_call_delete = (ImageView) view.findViewById(R.id.imageview_call_delete);
        //显示电话的textview
        keyboard_phone_number = (TextView) view.findViewById(R.id.keyboard_phone_number);
/*
软键盘拨号键
 */
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

    /**
     * 初始化数据
     */
    private void initData() {
        callPhone = new CallPhone();
    }
}

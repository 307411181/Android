package com.unihiltop.xiangliao.call.ui;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.syezon.plugin.call.module.generator.call.CallControlGenerator;
import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.call.utils.GlobalInfo;
import com.unihiltop.xiangliao.call.utils.SpeakerManager;
import com.unihiltop.xiangliao.util.Tools;

/**
 * 
 * @ClassName: CallingView
 * @Description: TODO(打电话时的遮挡界面)
 * @author: 石浩
 * @date: 2014-12-2 下午12:04:08
 *
 *
 * 点击最近通话,会跳转到此页面
 */
public class CallingView extends LinearLayout{


	private LinearLayout linearLayout_option;
	private Context context;
	private Chronometer chronometer = null;
	private TextView textView_phone_name;
	private TextView textview_phone_number;
	private Button    botton_down;
	private TextView textView_mianti;
	private TextView textViewPromt;
	private LinearLayout linearLayout_wait=null;
	private LinearLayout linearLayout_time;
	private LinearLayout linearLayout_softKeyboard;
	private RelativeLayout relativeLayout_button_down;
	private LinearLayout linearLayout_first_keyboard;
	private Button button_wait;
	private LinearLayout openSpeaker;
	private LinearLayout closeSpeaker;
	private Button botton_cancel;
	private boolean isCalling = false;
	private boolean isShow = false;

	//拨号界面
	private TextView keyboard_phone_number;
	private LinearLayout keyboard_oneline_one, keyboard_oneline_two, keyboard_oneline_three;
	private LinearLayout keyboard_twoline_one, keyboard_twoline_two, keyboard_twoline_three;
	private LinearLayout keyboard_threeline_one, keyboard_threeline_two, keyboard_threeline_three;
	private LinearLayout keyboard_fourline_one, keyboard_fourline_two, keyboard_fourline_three;
	private Button button_callDwon;
	private ImageView imageView_softkeyBoard_hide;
	private ImageView imageView_phonedelete;
	private StringBuffer number = new StringBuffer();

	private SpeakerManager speakerManager=new SpeakerManager();
	public CallingView(Context c) {
		super(c);
		this.context = c;
		View view = LayoutInflater.from(getContext()).inflate(R.layout.calling, this);

		//拨号界面

		//软键盘上的数字键及符号键
		keyboard_oneline_one = (LinearLayout) view.findViewById(R.id.keyboard_oneline_one);
		keyboard_oneline_two = (LinearLayout) view.findViewById(R.id.keyboard_oneline_two);
		keyboard_oneline_three = (LinearLayout) view.findViewById(R.id.keyboard_oneline_three);
		keyboard_twoline_one = (LinearLayout) view.findViewById(R.id.keyboard_twoline_one);
		keyboard_twoline_two = (LinearLayout) view.findViewById(R.id.keyboard_twoline_two);
		keyboard_twoline_three = (LinearLayout) view.findViewById(R.id.keyboard_twoline_three);
		keyboard_threeline_one = (LinearLayout) view.findViewById(R.id.keyboard_threeline_one);
		keyboard_threeline_two = (LinearLayout) view.findViewById(R.id.keyboard_threeline_two);
		keyboard_threeline_three = (LinearLayout) view.findViewById(R.id.keyboard_threeline_three);
		keyboard_fourline_one = (LinearLayout) view.findViewById(R.id.keyboard_fourline_one);
		keyboard_fourline_two = (LinearLayout) view.findViewById(R.id.keyboard_fourline_two);
		keyboard_fourline_three = (LinearLayout) view.findViewById(R.id.keyboard_fourline_three);
		keyboard_phone_number= (TextView) view.findViewById(R.id.keyboard_phone_number);


		textview_phone_number = (TextView) view.findViewById(R.id.textview_phone_number);
		chronometer = (Chronometer) view.findViewById(R.id.chronometer);
		botton_down = (Button) view.findViewById(R.id.botton_down);
		textViewPromt = (TextView) view.findViewById(R.id.textView_prompt);
		linearLayout_wait= (LinearLayout) view.findViewById(R.id.linearLayout_wait);
		linearLayout_time= (LinearLayout) view.findViewById(R.id.linearLayout_time);
		button_wait= (Button) view.findViewById(R.id.button_wait);
		botton_cancel= (Button) view.findViewById(R.id.botton_cancel);
		textView_phone_name= (TextView) view.findViewById(R.id.textView_phone_name);
		textView_mianti= (TextView) view.findViewById(R.id.textView_mianti);
		openSpeaker= (LinearLayout) view.findViewById(R.id.openSpeaker);
		closeSpeaker= (LinearLayout) view.findViewById(R.id.closeSpeaker);
		linearLayout_first_keyboard= (LinearLayout) view.findViewById(R.id.linearLayout_first_keyboard);

		relativeLayout_button_down = (RelativeLayout) view.findViewById(R.id.relativeLayout_button_down);
		linearLayout_option= (LinearLayout) view.findViewById(R.id.linearLayout_option);
		button_callDwon= (Button) view.findViewById(R.id.button_callDwon);
		imageView_softkeyBoard_hide= (ImageView) view.findViewById(R.id.imageView_softkeyBoard_hide);
		linearLayout_softKeyboard= (LinearLayout) view.findViewById(R.id.linearLayout_softKeyboard);
		imageView_phonedelete= (ImageView) view.findViewById(R.id.imageView_phonedelete);
		//点击回退删除号码
		imageView_phonedelete.setOnClickListener(new OnClickListener() {
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
		//点击隐藏软键盘恢复正常界面
		imageView_softkeyBoard_hide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				relativeLayout_button_down.setVisibility(VISIBLE);
				linearLayout_option.setVisibility(VISIBLE);
				linearLayout_first_keyboard.setVisibility(GONE);
			}
		});
		//点击挂断电话
		button_callDwon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (GlobalInfo.IsAutoOff) {
					endCall();
					GlobalInfo.IsAutoOff = false;
					GlobalInfo.IsAutoAnswer = false;
				}
				isCalling = false;
				CallingViewManager.get(context).dismiss();
				keyboard_phone_number.setText("");
				number = new StringBuffer("");
				linearLayout_first_keyboard.setVisibility(GONE);
				relativeLayout_button_down.setVisibility(VISIBLE);
				linearLayout_option.setVisibility(VISIBLE);
				button_wait.setVisibility(VISIBLE);
				botton_cancel.setVisibility(GONE);
				botton_down.setVisibility(GONE);
				textViewPromt.setVisibility(VISIBLE);
				linearLayout_time.setVisibility(GONE);
				linearLayout_wait.setVisibility(GONE);
				openSpeaker.setVisibility(VISIBLE);
				closeSpeaker.setVisibility(GONE);
				Log.i("TAG", "电话挂断");
			}
		});
		//点击显示软键盘
		linearLayout_softKeyboard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				relativeLayout_button_down.setVisibility(GONE);
				linearLayout_option.setVisibility(GONE);
				linearLayout_first_keyboard.setVisibility(VISIBLE);
			}
		});
		closeSpeaker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				speakerManager.closeSpeaker(context);
				openSpeaker.setVisibility(VISIBLE);
				closeSpeaker.setVisibility(GONE);
			}
		});
		openSpeaker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				speakerManager.openSpeaker(context);
				openSpeaker.setVisibility(GONE);
				closeSpeaker.setVisibility(VISIBLE);

			}
		});
		button_wait.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				button_wait.setVisibility(GONE);
				botton_cancel.setVisibility(VISIBLE);
				botton_down.setVisibility(GONE);
				textViewPromt.setVisibility(GONE);
				linearLayout_time.setVisibility(GONE);
				linearLayout_wait.setVisibility(VISIBLE);
			}
		});

		botton_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("dismiss");
				if (GlobalInfo.IsAutoOff) {
					endCall();
					GlobalInfo.IsAutoOff = false;
					GlobalInfo.IsAutoAnswer = false;
				}
				isCalling = false;
				CallingViewManager.get(context).dismiss();
				keyboard_phone_number.setText("");
				number = new StringBuffer("");
				linearLayout_first_keyboard.setVisibility(GONE);
				relativeLayout_button_down.setVisibility(VISIBLE);
				linearLayout_option.setVisibility(VISIBLE);
				button_wait.setVisibility(VISIBLE);
				botton_cancel.setVisibility(GONE);
				botton_down.setVisibility(GONE);
				textViewPromt.setVisibility(VISIBLE);
				linearLayout_time.setVisibility(GONE);
				linearLayout_wait.setVisibility(GONE);
				openSpeaker.setVisibility(VISIBLE);
				closeSpeaker.setVisibility(GONE);
				Tools.closeProgressDialog();
				Intent intent=new Intent("CALL_PHONE_VIEW_CHAGE_DOWN");
				context.sendBroadcast(intent);

			}
		});
		botton_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (GlobalInfo.IsAutoOff) {
					endCall();
					GlobalInfo.IsAutoOff = false;
					GlobalInfo.IsAutoAnswer = false;
				}
				isCalling = false;
				CallingViewManager.get(context).dismiss();
				keyboard_phone_number.setText("");
				number = new StringBuffer("");
				linearLayout_first_keyboard.setVisibility(GONE);
				relativeLayout_button_down.setVisibility(VISIBLE);
				linearLayout_option.setVisibility(VISIBLE);
				button_wait.setVisibility(VISIBLE);
				botton_cancel.setVisibility(GONE);
				botton_down.setVisibility(GONE);
				textViewPromt.setVisibility(VISIBLE);
				linearLayout_time.setVisibility(GONE);
				linearLayout_wait.setVisibility(GONE);
				openSpeaker.setVisibility(VISIBLE);
				closeSpeaker.setVisibility(GONE);
				Log.i("TAG", "电话挂断");


			}
		});

		//拨号界面的点击事件
//软键盘上的数字键及符号键
		keyboard_oneline_one.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("TAG","点击了1");
				number.append("1");
				keyboard_phone_number.setText(number);
			}
		});
		keyboard_oneline_two.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("2");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_oneline_three.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("3");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_twoline_one.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("4");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_twoline_two.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("5");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_twoline_three.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("6");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_threeline_one.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("7");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_threeline_two.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("8");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_fourline_one.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("*");

				keyboard_phone_number.setText(number+"");
			}
		});
		keyboard_threeline_three.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("9");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_fourline_two.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				number.append("0");

				keyboard_phone_number.setText(number);
			}
		});
		keyboard_fourline_three.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				number.append("#");
				keyboard_phone_number.setText(number);
			}
		});












	}
	/**
	 *
	 * @Title: endCall
	 * @Description: TODO(挂断电话)
	 * @author：石浩
	 * @date：2014-12-2 下午3:52:42
	 */
	private void endCall() {
		CallControlGenerator.endCall(context);
		//System.exit(0);
//		TelephonyManager telMag = (TelephonyManager) context
//				.getSystemService(Context.TELEPHONY_SERVICE);
//		Class<TelephonyManager> c = TelephonyManager.class;
//		Method mthEndCall = null;
//		try {
//			mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
//			mthEndCall.setAccessible(true);
//			ITelephony iTel = (ITelephony) mthEndCall.invoke(telMag,
//					(Object[]) null);
//			iTel.endCall();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}



	public void setBaseTime(long baseTime) {
		chronometer.setBase(SystemClock.elapsedRealtime());
	}

	public void setCalling(boolean isCalling) {
		this.isCalling = isCalling;
		textViewPromt.setVisibility(View.GONE);
		linearLayout_time.setVisibility(View.VISIBLE);
		linearLayout_wait.setVisibility(View.GONE);
		button_wait.setVisibility(GONE);
		botton_cancel.setVisibility(GONE);
		botton_down.setVisibility(VISIBLE);
	}

	public boolean isCalling(){
		return this.isCalling;
	}

	public void setPhoneNumber(String phoneNumber) {

		textview_phone_number.setText(phoneNumber);
	}
	public void setPhoneName(String phoneName) {

		textView_phone_name.setText(phoneName);
	}
	public void setShow(boolean show) {
		this.isShow = show;
	}

	public boolean isShow(){
		return this.isShow;
	}

	public void startChronometer(){
		chronometer.start();
	}

	public void stopChronometer(){
		chronometer.stop();
	}

	public void closeMianti(){
		openSpeaker.setVisibility(VISIBLE);
		closeSpeaker.setVisibility(GONE);
		keyboard_phone_number.setText("");
		number = new StringBuffer("");
		linearLayout_first_keyboard.setVisibility(GONE);
		relativeLayout_button_down.setVisibility(VISIBLE);
		linearLayout_option.setVisibility(VISIBLE);
	}

	public void reset() {
		this.isCalling = false;
		textViewPromt.setVisibility(View.VISIBLE);
		linearLayout_time.setVisibility(View.GONE);
		linearLayout_wait.setVisibility(View.GONE);
		button_wait.setVisibility(View.VISIBLE);
		botton_cancel.setVisibility(GONE);
		botton_down.setVisibility(GONE);
	}
}

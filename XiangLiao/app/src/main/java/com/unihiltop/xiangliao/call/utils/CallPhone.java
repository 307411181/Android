package com.unihiltop.xiangliao.call.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.call.ui.CallingViewManager;
import com.unihiltop.xiangliao.constant.PreferenceKeyConstants;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.manager.ConnectRecordManager;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.netserver.CallServer;
import com.unihiltop.xiangliao.util.PreferencesHelper;
import com.unihiltop.xiangliao.util.Tools;
import com.unihiltop.xiangliao.util.UISkip;


/**
 * Created by yy on 2015/10/30.
 */
public class CallPhone {

    private MediaPlayer player;
    private AudioManager audioManager;
    public static boolean Called = false;
    private	 boolean Killactivity ;

    /**
     * 播放音乐
     *
     * @param player
     */
    public void playMusic(MediaPlayer player) {
        if (player != null) {
            if (player.isPlaying()) {
                player.reset();
                player.start();
            } else {
                player.start();
            }
        }
        return;
    }


    /**
     * 一定要释放资源
     *
     * @param player
     */
    public void onMediaPlayerDestroy(MediaPlayer player) {
        try {
            audioManager.setMode(AudioManager.MODE_NORMAL);
            if (player != null) {
                player.stop();
                player.release();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void call(final Activity context, final String name, final String tel, final OnCallListener listener) {
        Log.i("TAG", "tel是多少" + tel);
        Killactivity = PreferencesHelper.get(context).isListenOrder();
        if (!TextUtils.isEmpty(tel)) {
            uploadTheBillWithTalkTime(context);
            Tools.showProgres(context, "连接中", "服务器连接中");
            new CallServer().call(UserData.getData(context).getAccount().getAccount(), tel.trim(), new NetMessageCallback() {
                @Override
                public void onFailure(String error, String message) {
                    Tools.showToast(context, message);
                    Tools.closeProgressDialog();
                }

                @Override
                public void onSuccess(NetMessage netMessage) {
                    Tools.closeProgressDialog();
                    if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
//                        player = MediaPlayer.create(context, R.raw.voice);

                        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setSpeakerphoneOn(false);
                        context.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                        audioManager.setMode(AudioManager.MODE_IN_CALL);
//                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                            @Override
//                            public void onCompletion(MediaPlayer mp) {
//                                onMediaPlayerDestroy(player);
//                            }
//                        });
//                        playMusic(player);
                        PreferencesHelper.get(context).put(PreferenceKeyConstants.KEY_CALL_TIME, System.currentTimeMillis());
                        PreferencesHelper.get(context).put(PreferenceKeyConstants.KEY_THEBILL_ID, Long.parseLong(netMessage.getData()));
                        PreferencesHelper.get(context).put(PreferenceKeyConstants.KEY_THE_OLD_BILL_ID, Long.parseLong(netMessage.getData()));
                        Called = true;
                         if (PreferencesHelper.get(context).isListenOrder()==false
                                || Build.VERSION.SDK_INT > 20 ||
                                Build.BRAND.toLowerCase().contains("meizu")
                                ) {
                            Log.i("TAG", "进入了Activity中");
                            UISkip.skipToCallingActivity((Activity) context, tel, name);
                        } else {
                            Log.i("TAG", "进入到界面" + tel);
                            CallingViewManager.get(context).show(context, tel, name);
                        }
                        GlobalInfo.IsAutoAnswer = true;
                        //插入通话记录
                        ConnectRecordManager crm = new ConnectRecordManager(context);
                        crm.addCalllog(tel, name);
                        if (listener != null) {
                            listener.onCallSuccess(name, tel, System.currentTimeMillis());
                        }
                        return;
                    } else {
                        Tools.showToast(context, netMessage.getMsg());
                    }
                }
            });


        }else {
            Toast.makeText(context,"请输入手机号码",Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadTheBillWithTalkTime(final Context context){
        long theOldBillId = PreferencesHelper.get(context).getLong(PreferenceKeyConstants.KEY_THE_OLD_BILL_ID);
        long talktime = PreferencesHelper.get(context).getLong(PreferenceKeyConstants.KEY_TALK_TIME);
        if (theOldBillId != 0L&&talktime!= 0){

            new AccountServer().uploadTheBillWithTalkTime(theOldBillId, talktime, new NetMessageCallback() {
                @Override
                public void onFailure(String error, String message) {

                }

                @Override
                public void onSuccess(NetMessage netMessage) {

                }
            });
        }

    }

    public interface OnCallListener {
        void onCallSuccess(String name, String tel, long time);

    }

}
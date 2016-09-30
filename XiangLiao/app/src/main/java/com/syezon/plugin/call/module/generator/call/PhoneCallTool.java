//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.ITelephony.Stub;
import com.syezon.plugin.call.common.config.CallConfig;
import com.syezon.plugin.call.common.util.StringUtil;
import com.syezon.plugin.call.module.generator.call.answer.BaseAnswer;

import java.io.IOException;
import java.lang.reflect.Method;

public class PhoneCallTool {
    private static PhoneCallTool intance = null;
    private ITelephony aidPhone = null;
    private AudioManager audioManager = null;
    private Context mContext = null;
    private ITelephony mainPhone = null;

    private PhoneCallTool(Context var1) {
        this.mContext = var1;
        this.mainPhone = Stub.asInterface(ServiceManager.getService(StringUtil.phone));
        this.aidPhone = Stub.asInterface(ServiceManager.getService(StringUtil.phone2));
        this.audioManager = (AudioManager)this.mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    private ITelephony getITelephony() throws Exception {
        TelephonyManager var1 = (TelephonyManager)this.mContext.getSystemService(Context.TELEPHONY_SERVICE);
        Class var2 = var1.getClass();
        Class[] var3 = new Class[0];
        Method var4 = var2.getDeclaredMethod(StringUtil.getITelephony, var3);
        var4.setAccessible(true);
        return (ITelephony)var4.invoke(var1, new Object[0]);
    }

    public static PhoneCallTool getIntance(Context var0) {
        synchronized(PhoneCallTool.class){}

        PhoneCallTool var2;
        try {
            if(intance == null) {
                intance = new PhoneCallTool(var0);
            }

            var2 = intance;
        } finally {
            ;
        }

        return var2;
    }

    private void plugHeadset() {
        Intent var1 = new Intent(StringUtil.HEADSET_PLUG);
        var1.putExtra(StringUtil.state, 1);
        var1.putExtra(StringUtil.name, StringUtil.headset_mic);
        var1.putExtra(StringUtil.microphone, 1);
        this.mContext.sendOrderedBroadcast(var1, (String)null);
    }

    private void pullHeadset() {
        Intent var1 = new Intent(StringUtil.HEADSET_PLUG);
        var1.putExtra(StringUtil.status, 0);
        var1.putExtra("app", "sz");
        var1.putExtra("TAG", "szService");
        var1.putExtra(StringUtil.state, 0);
        var1.putExtra(StringUtil.name, StringUtil.Headset);
        var1.putExtra(StringUtil.microphone, 1);
        this.mContext.sendOrderedBroadcast(var1, (String)null);
    }

    private void simulateAnswerKeyDown() {
        int var1 = StringUtil.action0;
        int var2 = StringUtil.code;
        Intent var3 = new Intent(StringUtil.MEDIA_BUTTON);
        KeyEvent var4 = new KeyEvent(0L, System.currentTimeMillis(), var1, var2, 0, 0, 0, 0, 0);
        var3.putExtra(StringUtil.KEY_EVENT, var4);
        this.mContext.sendOrderedBroadcast(var3, "android.permission.CALL_PRIVILEGED");
    }

    private void simulateAnswerKeyUp() {
        int var1 = StringUtil.action1;
        int var2 = StringUtil.code;
        Intent var3 = new Intent(StringUtil.MEDIA_BUTTON);
        KeyEvent var4 = new KeyEvent(0L, System.currentTimeMillis(), var1, var2, 0, 0, 0, 0, 0);
        var3.putExtra(StringUtil.KEY_EVENT, var4);
        this.mContext.sendOrderedBroadcast(var3, "android.permission.CALL_PRIVILEGED");
    }

    private void toolsEndCall() {
        try {
            this.getITelephony().endCall();
        } catch (Exception var2) {
            this.endCallByP970();
            var2.printStackTrace();
        }
    }

    public void answer() throws RemoteException {
        boolean var2;
        label16: {
            boolean var3;
            try {
                var3 = this.mainPhone.isRinging();
            } catch (RemoteException var4) {
                var4.printStackTrace();
                var2 = false;
                break label16;
            }

            var2 = var3;
        }
        if(var2) {
            this.mainPhone.answerRingingCall();
        }

    }

    public void answerRingCallByHeadset() {
        if(this.audioManager.isWiredHeadsetOn()) {
            try {
                SystemClock.sleep(10L);
                this.simulateAnswerKeyDown();
                SystemClock.sleep(10L);
                this.simulateAnswerKeyUp();
            } catch (Exception var3) {
                var3.printStackTrace();
                this.answerRingCallByKey();
            }
        } else {
            try {
                SystemClock.sleep(5L);
                this.plugHeadset();
                SystemClock.sleep(10L);
                this.simulateAnswerKeyDown();
                SystemClock.sleep(10L);
                this.simulateAnswerKeyUp();
                SystemClock.sleep(10L);
                this.pullHeadset();
                SystemClock.sleep(5L);
            } catch (Exception var4) {
                var4.printStackTrace();
                this.answerRingCallByKey();
            }
        }
    }

    //    public void answerRingCallByKey() {
//        try {
//            int var2 = StringUtil.action0;
//            int var3 = StringUtil.code;
//            int var4 = StringUtil.action1;
//            int var5 = StringUtil.code;
//            Intent var6 = new Intent(StringUtil.MEDIA_BUTTON);
//            KeyEvent var7 = new KeyEvent(var2, var3);
//            var6.putExtra(StringUtil.KEY_EVENT, var7);
//            this.mContext.sendOrderedBroadcast(var6, StringUtil.CALL_PRIVILEGED);
//            Intent var9 = new Intent(StringUtil.MEDIA_BUTTON);
//            KeyEvent var10 = new KeyEvent(var4, var5);
//            var9.putExtra(StringUtil.KEY_EVENT, var10);
//            this.mContext.sendOrderedBroadcast(var9, StringUtil.CALL_PRIVILEGED);
////            if(StringUtil.n>1){System.out.println("aaa");
////            	Intent var6 = new Intent(StringUtil.MEDIA_BUTTON);
////                KeyEvent var7 = new KeyEvent(var2, var3);
////                var6.putExtra(StringUtil.KEY_EVENT, var7);
////                this.mContext.sendOrderedBroadcast(var6, StringUtil.CALL_PRIVILEGED);
////            }else{System.out.println("bbb");
////            	Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
////                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
////                        KeyEvent.KEYCODE_HEADSETHOOK);
////                meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
////                this.mContext.sendOrderedBroadcast(meidaButtonIntent,
////                        "android.permission.CALL_PRIVILEGED");
////            }
////            StringUtil.n++;
//            
//            Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//            KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
//                    KeyEvent.KEYCODE_HEADSETHOOK);
//            meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
//            this.mContext.sendOrderedBroadcast(meidaButtonIntent,
//                    "android.permission.CALL_PRIVILEGED");
//        } catch (Exception var11) {
//            var11.printStackTrace();
//            answerRingingCall(mContext);
//        }
//    }
    public void answerRingCallByKey()
    {
        try
        {
            int i = StringUtil.action0;
            int j = StringUtil.code;
            int k = StringUtil.action1;
            int m = StringUtil.code;
            Intent localIntent1 = new Intent(StringUtil.MEDIA_BUTTON);
            KeyEvent localKeyEvent1 = new KeyEvent(i, j);
            localIntent1.putExtra(StringUtil.KEY_EVENT, localKeyEvent1);
            this.mContext.sendOrderedBroadcast(localIntent1, StringUtil.CALL_PRIVILEGED);
            Intent localIntent2 = new Intent(StringUtil.MEDIA_BUTTON);
            KeyEvent localKeyEvent2 = new KeyEvent(k, m);
            localIntent2.putExtra(StringUtil.KEY_EVENT, localKeyEvent2);
            this.mContext.sendOrderedBroadcast(localIntent2, StringUtil.CALL_PRIVILEGED);
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }

    // 模拟耳机插拔接听电话
    public synchronized void answerRingingCall(Context context) {
        // 此方法只能用于Android2.3及2.3以上的版本上
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
            KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_HEADSETHOOK);
            meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
            context.sendOrderedBroadcast(meidaButtonIntent,
                    "android.permission.CALL_PRIVILEGED");
        } else {
            try {
                Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
                localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                localIntent1.putExtra("state", 1);
                localIntent1.putExtra("microphone", 1);
                localIntent1.putExtra("name", "Headset");
                context.sendOrderedBroadcast(localIntent1,
                        "android.permission.CALL_PRIVILEGED");

                Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                localIntent2.putExtra("android.intent.extra.KEY_EVENT",
                        localKeyEvent1);
                context.sendOrderedBroadcast(localIntent2,
                        "android.permission.CALL_PRIVILEGED");

                Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                localIntent3.putExtra("android.intent.extra.KEY_EVENT",
                        localKeyEvent2);
                context.sendOrderedBroadcast(localIntent3,
                        "android.permission.CALL_PRIVILEGED");

                Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
                localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                localIntent4.putExtra("state", 0);
                localIntent4.putExtra("microphone", 1);
                localIntent4.putExtra("name", "Headset");
                context.sendOrderedBroadcast(localIntent4,
                        "android.permission.CALL_PRIVILEGED");
            } catch (Exception e5) {
                e5.printStackTrace();

                try {
                    Intent intent = new Intent(
                            "android.intent.action.MEDIA_BUTTON");
                    KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_HEADSETHOOK);
                    intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                    context.sendOrderedBroadcast(intent,
                            "android.permission.CALL_PRIVILEGED");

                } catch (Exception e2) {
                    Intent meidaButtonIntent = new Intent(
                            Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_HEADSETHOOK);
                    meidaButtonIntent
                            .putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                    context.sendOrderedBroadcast(meidaButtonIntent, null);

                }

            }
        }

    }
    public void endCall() {
        switch(CallConfig.getInstance(this.mContext).getPhoneType(-1)) {
            case 0:
                this.endCallAid();
                return;
            default:
                this.endCallMain();
        }
    }

    public void endCallAid() {
        boolean var2;
        label16: {
            boolean var3;
            try {
                var3 = this.aidPhone.endCall();
            } catch (Exception var4) {
                var4.printStackTrace();
                var2 = false;
                break label16;
            }

            var2 = var3;
        }

        if(!var2) {
            this.toolsEndCall();
//        } else {
//            StyleManager.getIntance(this.mContext).removeView(false);
        }
    }

    public void endCallByP970() {
        boolean var2;
        label30: {
            boolean var9;
            try {
                var9 = this.mainPhone.isRinging();
            } catch (RemoteException var12) {
                var12.printStackTrace();
                var2 = false;
                break label30;
            }

            var2 = var9;
        }

        if(var2 && this.audioManager.getStreamVolume(2) > 0) {
            this.audioManager.setStreamMute(2, true);
            CallConfig.getInstance(this.mContext).setIsRingingMute(true);
        }

        if(this.audioManager.isWiredHeadsetOn()) {
            try {
                Intent var6 = new Intent(StringUtil.MEDIA_BUTTON);
                var6.putExtra(StringUtil.KEY_EVENT, new KeyEvent(0, 79));
                this.mContext.sendOrderedBroadcast(var6, (String)null);
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        } else {
            try {
                this.plugHeadset();
                Intent var4 = new Intent(StringUtil.MEDIA_BUTTON);
                var4.putExtra(StringUtil.KEY_EVENT, new KeyEvent(0, 79));
                this.mContext.sendOrderedBroadcast(var4, (String)null);
                this.pullHeadset();
            } catch (Exception var11) {
                var11.printStackTrace();
            }
        }
    }

    public void endCallMain() {
        boolean var2;
        label16: {
            boolean var3;
            try {
                var3 = this.mainPhone.endCall();
            } catch (Exception var4) {
                var4.printStackTrace();
                var2 = false;
                break label16;
            }

            var2 = var3;
        }

        if(!var2) {
            this.toolsEndCall();
        }
//        else {
//            StyleManager.getIntance(this.mContext).removeView(false);
//        }
    }

    public void headsetLongPress(String var1) {
        String[] var2 = new String[]{StringUtil.c8500, StringUtil.c8600, StringUtil.c8500s};
        if(!TextUtils.isEmpty(var1)) {
            for(int var3 = 0; var3 < var2.length; ++var3) {
                if(var1.toLowerCase().contains(var2[var3].toLowerCase()) || var1.toLowerCase().contains(var2[var3].toLowerCase())) {
                    this.endCallMain();
                }
            }
        }

    }

    public void headsetPress(BaseAnswer var1) {
        switch(CallConfig.getInstance(this.mContext).getPhoneType(-1)) {
            case 0:
                try {
                    if(this.aidPhone.isRinging()) {
                        var1.answer(this.mContext);
                        return;
                    }

                    if(this.aidPhone.isOffhook()) {
                        this.endCallAid();
                        return;
                    }
                    break;
                } catch (Exception var4) {
                    var4.printStackTrace();
                    return;
                }
            default:
                try {
                    if(this.mainPhone.isRinging()) {
                        var1.answer(this.mContext);
                    } else if(this.mainPhone.isOffhook()) {
                        this.endCallMain();
                        return;
                    }
                } catch (Exception var5) {
                    var5.printStackTrace();
                    return;
                }
        }

    }

    public void ringingMute() {
        try {
            if(this.audioManager.getStreamVolume(2) > 0) {
                this.audioManager.setStreamMute(2, true);
                CallConfig.getInstance(this.mContext).setIsRingingMute(true);
            }

        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    public void ringingOpen() {
        try {
            this.audioManager.setStreamMute(2, false);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }
}

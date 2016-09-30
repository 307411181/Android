package com.unihiltop.xiangliao.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.unihiltop.xiangliao.constant.PreferenceKeyConstants;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.util.PreferencesHelper;

/**
 * Created by yangyang on 2015/11/25.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    String packnameString = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        packnameString = context.getPackageName();

        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobNetInfo.isConnected() || wifiNetInfo.isConnected()) {
            // connect network
            uploadTheBillWithTalkTime(context);
          //  Toast.makeText(context,"有网",Toast.LENGTH_LONG).show();
        } else {
          //  Toast.makeText(context,"没有网",Toast.LENGTH_LONG).show();
            // unconnect network
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
                    if ( netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS){
                        PreferencesHelper.get(context).put(PreferenceKeyConstants.KEY_THE_OLD_BILL_ID, 0L);
                        PreferencesHelper.get(context).put(PreferenceKeyConstants.KEY_TALK_TIME, 0L);
                    }

                }
            });
        }

    }
}
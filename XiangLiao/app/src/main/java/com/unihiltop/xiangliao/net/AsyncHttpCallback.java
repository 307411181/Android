package com.unihiltop.xiangliao.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import org.xutils.common.Callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Created by yangyang on 2015/12/3.
 */
public abstract class AsyncHttpCallback implements Callback.CommonCallback<String> {
    private final static int STATE_FAILURE = 0X1002;
    private final static int STATE_SUCESS = 0X1001;
    public abstract void onFailure(String error, String message);
    public abstract void onSuccess(byte[] bytes);
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == STATE_SUCESS) {
                onSuccess((byte[])msg.obj);
            }else if(msg.what == STATE_FAILURE){
//				Log.i("handler", msg.getData().getString("message"));
                onFailure(msg.getData().getString("error"), "网络异常，请稍后再试");
            }
            super.handleMessage(msg);
        }
    };
    @Override
    public void onSuccess(String result) {
        if (result == null) {
            Message msg = new Message();
            Bundle data = new Bundle();
            msg.what = STATE_FAILURE;
            data.putString("error", "network error");
            data.putString("message", "network error");
            msg.setData(data);
            handler.sendMessage(msg);
        }else{
            handler.sendMessage(handler.obtainMessage(STATE_SUCESS, result));
        }
    }

    @Override
    public void onError(Throwable e, boolean isOnCallback) {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.close();
        Message msg = new Message();
        Bundle data = new Bundle();
        msg.what = STATE_FAILURE;
        data.putString("error", writer.toString());
        data.putString("message", writer.toString());
        msg.setData(data);
        handler.sendMessage(msg);
        Log.i("onFailure", "error=" + writer.toString());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        cex.printStackTrace(pw);
        pw.close();
        Message msg = new Message();
        Bundle data = new Bundle();
        msg.what = STATE_FAILURE;
        data.putString("error", writer.toString());
        data.putString("message", writer.toString());
        msg.setData(data);
        handler.sendMessage(msg);
        Log.i("onFailure", "error=" + writer.toString());
    }

    @Override
    public void onFinished() {

    }
}

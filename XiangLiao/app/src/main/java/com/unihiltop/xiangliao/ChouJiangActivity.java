package com.unihiltop.xiangliao;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

import com.unihiltop.xiangliao.data.UserData;

/**
 * Created by yangyang on 2015/11/10.
 */
public class ChouJiangActivity extends Activity {

    private ImageView ivReturn;
     private  WebView webView;
    private String url;
    private ProgressDialog   progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choujiang);
        String phone = UserData.getData(ChouJiangActivity.this).getAccountName();
        url = "http://121.42.33.183:9999/KuaiHua/AwardController/InitPage?account="+phone;
        webView = (WebView) findViewById(R.id.webView_choujing);

        //匹配手机
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onExceededDatabaseQuota(String url,
                                                String databaseIdentifier, long quota,
                                                long estimatedDatabaseSize, long totalQuota,
                                                WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }

            @Override
            // 弹出警告框操作
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                Dialog dialog = new android.app.AlertDialog.Builder(ChouJiangActivity.this)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        result.cancel();
                                        // 这里我们通过Webview.loadUrl()方法去调用js中的函数
                                    }
                                }).create();
                dialog.setCancelable(false);// 避免点Back取消，那样js接收不到任何返回信息
                dialog.show();
                return true;
            }

            //
            @Override
            // 弹出确认框操作
            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                Dialog dialog = new android.app.AlertDialog.Builder(ChouJiangActivity.this)
                        .setIcon(android.R.drawable.stat_sys_warning)
                        .setMessage(message)
                        .setPositiveButton("是",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        result.confirm();
                                    }
                                })
                        .setNegativeButton("否",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        result.cancel();
                                    }
                                }).create();
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }

            // 带输入框的提示框
            @Override
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, final JsPromptResult result) {
                final EditText text = new EditText(ChouJiangActivity.this);
                text.setHint(defaultValue);
                android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(ChouJiangActivity.this)
                        .setTitle(message)
                        .setView(text)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        result.confirm(text.getText()
                                                .toString());
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        result.cancel();
                                    }
                                }).create();
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }

            @Override
            public void onConsoleMessage(String message, int lineNumber,
                                         String sourceID) {
//                Log("sourceID:" + sourceID + ";message:" + message
//                        + ";lineNumber:" + lineNumber);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                Log("messageLevel:" + consoleMessage.messageLevel().toString()
//                        + ";sourceID:" + consoleMessage.sourceId()
//                        + ";message:" + consoleMessage.message()
//                        + ";lineNumber:" + consoleMessage.lineNumber());
                return super.onConsoleMessage(consoleMessage);
            }
        });
        //链接还是在当前的webview里跳转，不跳到浏览器那边
        webView.setWebViewClient(new MyWebViewClient());
        //链接网络
        webView.loadUrl(url);
         ivReturn = (ImageView) findViewById(R.id.imageView_return);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    /**
     * 链接还是在当前的webview里跳转，不跳到浏览器那边
     */
    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url)
        {

            //结束
            if (progressDialog != null){
                progressDialog.dismiss();
            }

            super.onPageFinished(view, url);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            progressDialog = ProgressDialog.show(ChouJiangActivity.this,"加载中","幸运大转盘加载中，请稍等");
            //开始
            super.onPageStarted(view, url, favicon);

        }
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url){
//
//            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//
//            view.loadUrl(url);
//
//            return true;
//
//        }

    }

    @Override
    protected void onDestroy() {
        webView.clearCache(true);
        //webView.destroy();
        super.onDestroy();
    }
}

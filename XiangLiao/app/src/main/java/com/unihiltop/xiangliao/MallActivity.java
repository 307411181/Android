package com.unihiltop.xiangliao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 商场web界面
 * Created by yangyang on 2015/10/29.
 */
public class MallActivity extends Activity{
    private ImageView ivReturn;
    private TextView tvTitle;
    private  WebView mall;
    private String title;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        initData();
        initView();
        initListenter();
    }

    private void initData() {
        title = getIntent().getStringExtra("title");
        url  = getIntent().getStringExtra("url");
    }

    private void initView() {
       ivReturn = (ImageView) findViewById(R.id.imageView_return);
        mall = (WebView) findViewById(R.id.webView_mall);
        tvTitle = (TextView) findViewById(R.id.textView_mall_title);
        tvTitle.setText(title);
        //匹配手机
        mall.getSettings().setJavaScriptEnabled(true);
        //链接还是在当前的webview里跳转，不跳到浏览器那边
        mall.setWebViewClient(new MyWebViewClient());
        //链接网络
        mall.loadUrl(url);
    }
    private void initListenter() {
        Mylistenter mylistenter = new Mylistenter();
        ivReturn.setOnClickListener(mylistenter);
        mall.setOnClickListener(mylistenter);
    }
    private class Mylistenter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == ivReturn){
                finish();
            }
        }
    }

    /**
     * 链接还是在当前的webview里跳转，不跳到浏览器那边
     */
    class MyWebViewClient extends WebViewClient {

        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url){

            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边

            view.loadUrl(url);

            return true;

        }

    }
}

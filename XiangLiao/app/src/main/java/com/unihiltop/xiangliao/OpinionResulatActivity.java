package com.unihiltop.xiangliao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;

/**
 * 意见反馈
 * Created by yangyang on 2015/10/14.
 */
public class OpinionResulatActivity extends Activity{
    private ImageView ivReturn;//返回
   private  EditText etOpinion;//意见内容
    private Button btOpinionResulat;//提交
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion_resulat);
        initData();
        initView();
        initSetView();
        initListerten();
    }



    private void initData() {
    }

    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
          etOpinion = (EditText) findViewById(R.id.editText_opinion);

        btOpinionResulat = (Button) findViewById(R.id.button_opinion_resulat);
    }

    private void initSetView() {
    }
    private void initListerten() {
        MyClickListener mylistenter = new MyClickListener();
        ivReturn.setOnClickListener(mylistenter);
        btOpinionResulat.setOnClickListener(mylistenter);
    }
    private class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v == ivReturn){
           finish();
             //   qq();
            }else if(v == btOpinionResulat){

              String opinion =  etOpinion.getText().toString();
                if (TextUtils.isEmpty(opinion)){
                    Toast.makeText(OpinionResulatActivity.this,"请填写内容",Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog progressDialog = ProgressDialog.show(
                        OpinionResulatActivity.this, null, null);
               new AccountServer().uploadFeedBack(opinion, new NetMessageCallback() {
                   @Override
                   public void onFailure(String error, String message) {
                       progressDialog.dismiss();
                       Toast.makeText(OpinionResulatActivity.this,message,Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onSuccess(NetMessage netMessage) {
                       progressDialog.dismiss();
                           if(netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS){
                               finish();
                               Toast.makeText(OpinionResulatActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                           }
                   }
               });
            }
        }
    }

    /**
     * 跳转到qq具体位置
     */
    private void qq(){
        String url="mqqwpa://im/chat?chat_type=wpa&uin=501863587";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

    }
}

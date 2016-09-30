package com.unihiltop.xiangliao;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unihiltop.xiangliao.adapter.AdapterGoodsCard;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.IntegralProduct;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AccountServer;
import com.unihiltop.xiangliao.pulltorefresh.library.PullToRefreshBase;
import com.unihiltop.xiangliao.pulltorefresh.library.PullToRefreshListView;
import com.unihiltop.xiangliao.util.ImageLoadOptions;
import com.unihiltop.xiangliao.util.UISkip;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * 兑换商城
 * Created by yangyang on 2015/10/14.
 */
public class StoreActivity extends Activity{
    private ImageView ivReturn;//返回
    private   ImageView ivStorePhone;//头像
    private TextView tvStorePhone;//电话
    private TextView tvStoreMoney;//余额
    private TextView tvStoreGold;//金币
    private ImageView ivOrders;//兑换记录
    private PullToRefreshListView  lvStore;
   // private TextView tvStoreMonthnumber;//已兑换
    //private   Button btStoreMonth;//立即兑换
   // private  TextView tvStoreYearnumber;//已兑换
   // private Button btStoreYear;//立即兑换
    private static final int PHOTO_SHOP_REQUEST_TAKEPHOTO = 1001;// 拍照
    private static final int PHOTO_SHOP_REQUEST_GALLERY = 1002;// 从相册中选择
    private String idCardImgPath;
    private String imgPath;
    private String phone;
    private int type;
    private AdapterGoodsCard adapterGoodsCard;

    private IntegralProduct integralProduct;
    private List<IntegralProduct> list;
     private Account account;
    private double bill;
    private double gold;
    private long intergralproductid ;
    private String myheadimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        initData();
        initView();
        initSetView();
        initListenter();
    }
    private void initData() {

        integralProduct = new IntegralProduct();
        intergralproductid = integralProduct.id;
        list = new LinkedList<IntegralProduct>();
        adapterGoodsCard = new AdapterGoodsCard(StoreActivity.this,list);
      //  idCardImgPath = AndroidImageUtils.saveToSDCard(AndroidImageUtils.getBitmap(idCardImgPath, 1000, 1000), idCardImgPath);
        account = UserData.getData(StoreActivity.this).getAccount();
        myheadimg = UserData.getData(StoreActivity.this).getHeadImg();
        bill = UserData.getData(StoreActivity.this).getBill();
        gold = UserData.getData(StoreActivity.this).getCoin();
        phone = UserData.getData(StoreActivity.this).getAccountName();
        initStore();
//        uploadHeadImg();
    }
    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
        ivStorePhone = (ImageView) findViewById(R.id.imageView_store_phone);
        ivOrders = (ImageView) findViewById(R.id.imageView_orders);
        tvStorePhone = (TextView) findViewById(R.id.textView_store_phone);
        tvStoreMoney = (TextView) findViewById(R.id.textView_store_money);
        tvStoreGold = (TextView) findViewById(R.id.textView_store_gold);
        lvStore = (PullToRefreshListView) findViewById(R.id.listView_store);
      //  tvStoreMonthnumber = (TextView) findViewById(R.id.textView_store_monthnumber);
       // btStoreMonth = (Button) findViewById(R.id.button_store_month);
       // tvStoreYearnumber = (TextView) findViewById(R.id.textView_store_yearnumber);
       // btStoreYear = (Button) findViewById(R.id.button_store_year);
        lvStore.setMode(PullToRefreshBase.Mode.BOTH);
        lvStore.setAdapter(adapterGoodsCard);

    }

    private void initSetView() {
       // ivStorePhone.setImageBitmap(AndroidImageUtils.getBitmap(idCardImgPath, 400, 400));
        tvStoreMoney.setText(String.valueOf(bill));
        tvStoreGold.setText(String.valueOf(gold));
        tvStorePhone.setText(phone);
        if(!TextUtils.isEmpty(myheadimg)){
            ImageLoader.getInstance().displayImage(
                    myheadimg,
                    ivStorePhone,
                    ImageLoadOptions.getOptions(R.drawable.ic_avatar,
                            ImageLoadOptions.headCornerRadius));
        }else {
          //  Toast.makeText(StoreActivity.this,"account.headImg"+"=null",Toast.LENGTH_LONG).show();
        }
    }
    private void initListenter() {
        MyListenter mylistenter = new MyListenter();

        ivReturn.setOnClickListener(mylistenter);
        ivStorePhone.setOnClickListener(mylistenter);
        ivOrders.setOnClickListener(mylistenter);
        //上下拉刷新
        lvStore.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                initStore();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
//                lvStore.clearFocus();
//                lvStore.clearChildFocus(refreshView);
//                lvStore.onRefreshComplete();
                getMoreStore();
            }

        });

    }

    /**
     * 获取商品信息
     */
    private void initStore(){

       new AccountServer().getIntegralProductList(-1l, new NetMessageCallback() {
           @Override
           public void onFailure(String error, String message) {
               adapterGoodsCard.notifyDataSetChanged();
               Toast.makeText(getApplicationContext(),
                       message, Toast.LENGTH_LONG).show();
           }

           @Override
           public void onSuccess(NetMessage netMessage) {

                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS){
                   list.clear();
                    List<IntegralProduct> listinter = new Gson().fromJson(netMessage.getData(),new TypeToken< List<IntegralProduct>>(){
                    }.getType());
                    if (listinter != null && listinter.size() > 0) {
                        list.addAll(listinter);
                        intergralproductid++;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "暂时还没有更多商品", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            netMessage.getMsg(), Toast.LENGTH_LONG).show();
                }
               adapterGoodsCard.notifyDataSetChanged();
               lvStore.onRefreshComplete();
                }
       });
    }
    /*

      */
    private void getMoreStore(){
        if (list.size() == 0){
            intergralproductid = 0;
        }else{
            intergralproductid = list.get(list.size() - 1).getId();
        }
        new AccountServer().getIntegralProductList(intergralproductid, new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                adapterGoodsCard.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(NetMessage netMessage) {

                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS){
                  // list.clear();
                    lvStore.onRefreshComplete();
                    List<IntegralProduct> listinter = new Gson().fromJson(netMessage.getData(),new TypeToken< List<IntegralProduct>>(){
                    }.getType());
                    if (listinter != null && listinter.size() > 0) {
                        intergralproductid++;
                        list.addAll(listinter);
                        adapterGoodsCard.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "暂时还没有更多商品", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            netMessage.getMsg(), Toast.LENGTH_LONG).show();
                }

               // lvStore.onRefreshComplete();
            }
        });
    }
    private class MyListenter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == ivReturn){
                finish();
            }else if (v == ivOrders){
                UISkip.skip(false,StoreActivity.this,ExchangelistActivity.class);
            }
        }
    }

    @Override
    protected void onResume() {
        integralProduct = new IntegralProduct();
        account = UserData.getData(StoreActivity.this).getAccount();
        myheadimg = UserData.getData(StoreActivity.this).getHeadImg();
        bill = UserData.getData(StoreActivity.this).getBill();
        gold = UserData.getData(StoreActivity.this).getCoin();
        phone = UserData.getData(StoreActivity.this).getAccountName();
        tvStoreMoney.setText(String.valueOf(bill));
        tvStoreGold.setText(String.valueOf(gold));
        tvStorePhone.setText(phone);
        integralProduct = new IntegralProduct();
        intergralproductid = integralProduct.id;
        initStore();
        super.onResume();
    }
//    //第一个页面接收返回值，下面方法直接在页面加上即可
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bundle bundle = data.getExtras();
//       // String str = bundle.getString("参数名");
//    }
}

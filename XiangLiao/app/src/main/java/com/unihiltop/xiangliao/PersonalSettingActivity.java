package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.popupwindow.CameraPopupwindow;
import com.unihiltop.xiangliao.popupwindow.SurePopupwindow;
import com.unihiltop.xiangliao.util.UISkip;
import com.unihiltop.xiangliao.view.CircleImageView;
import com.unihiltop.xiangliao.view.GlideCircleTransform;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;

/**
 * @创建者 张京
 * @创建时间 2016/9/18 15:52
 * @描述 ${个人设置页面}
 */
public class PersonalSettingActivity extends Activity implements View.OnClickListener {

    //自定义变量
    public static final int TAG_TAKE_PHOTO              = 1;//相机请求码
    public static final int TAG_GALLERY                 = 2;//图库(相册)请求码
    public static final int TAG_CROP_PHOTO_FROM_PHOTO   = 3;//相机启动剪裁程序请求码
    public static final int TAG_CROP_PHOTO_FROM_GALLERY = 4;//图库启动剪裁程序请求码
    private Uri    imageUri; //图片路径
    private String filename; //图片名称
    private File   outputImage;//用于保存相机拍摄的照片
    private Uri    galleryUri;//用于记录图库图片的Uri
    private String galleryImgPath;//图库图片的路径
    private File   galleryFile;//图库图片的文件

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    private static long id;//临时ID,测试用
    private Context  mContext;//上下文Context

    //        private static final String    url    = "http://114.215.85.203:8080/XL/account/uploadHeadImg";
    private static final String url = "http://139.129.217.239:8080/XL/account/uploadHeadImg";

    private Activity mActivity;//上下文Activity

    private static final String TAG = "PersonalSettingActivity";//打印Log日志的tag

    //头像地址
    private String headImage;

    /**
     * 控件
     */
    private Button          btnExit;
    private LinearLayout    llModificationAddress;
    private LinearLayout    llModificationPassword;
    private LinearLayout    llModificationNickName;
    private LinearLayout    ll_rootview;
    private TextView        tvImage;
    private CircleImageView imageViewMyphoto;
    private ImageView       ivReturn;
    private TextView        tv_fragment_phone;//页面上显示电话号码的Textview
    private String          phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        mActivity = this;
        mContext = this;

        /**
         * 测试,,,,,,打印用户的Id和头像地址
         */
        id = UserData.getData(mContext).getAccountId();
        phone = UserData.getData(mContext).getAccount().getAccount();
        Log.e(TAG, "该用户的Id:::::::::::::::::::::::::::::::" + id);
        Log.e(TAG, "该用户的电话:::::::::::::::::::::::::::::::" + phone);
        Log.e(TAG, "该用户的头像:::::::::::::::::::::::::::::::" + UserData.getData(mContext).getHeadImg());
        headImage = UserData.getData(mContext).getHeadImg();


        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tv_fragment_phone.setText(phone);

        /**
         * 根据保存的用户资料显示圆形图
         * 用Glide显示
         */
        RequestManager circleTransform = Glide.with(mContext);
        circleTransform.load(headImage).transform(new GlideCircleTransform(mContext))
                .placeholder(R.drawable.ic_avatar).error(R.drawable.ic_avatar).into(imageViewMyphoto);

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        btnExit.setOnClickListener(this);
        llModificationNickName.setOnClickListener(this);
        llModificationPassword.setOnClickListener(this);
        llModificationAddress.setOnClickListener(this);
        tvImage.setOnClickListener(this);
        ivReturn.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        btnExit = (Button) findViewById(R.id.btn_exit);
        llModificationNickName = (LinearLayout) findViewById(R.id.ll_modification_nick_name);
        llModificationPassword = (LinearLayout) findViewById(R.id.ll_modification_password);
        llModificationAddress = (LinearLayout) findViewById(R.id.ll_modification_address);
        ll_rootview = (LinearLayout) findViewById(R.id.ll_rootview);
        tvImage = (TextView) findViewById(R.id.tv_headimage);
        imageViewMyphoto = (CircleImageView) findViewById(R.id.imageView_fragment_myphoto);
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
        tv_fragment_phone = (TextView) findViewById(R.id.tv_fragment_phone);
    }

    /**
     * 点击按钮的监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_exit:
                exit();
                break;
            case R.id.ll_modification_nick_name:
                showToast("更改昵称");
                break;
            case R.id.ll_modification_password:
                showToast("密码");
                break;
            case R.id.ll_modification_address:
                showToast("更改地址");
                break;
            case R.id.tv_headimage:
                startCameraOrPhoto();
                break;
            case R.id.imageView_return:
                finish();
                break;

        }
    }

    /**
     * 弹出popWindow,让用户选择是相机还是相册
     */
    private void startCameraOrPhoto() {
        final CameraPopupwindow popupwindow = new CameraPopupwindow(mActivity);
        popupwindow.showAtLocation(ll_rootview, Gravity.BOTTOM, 0, 0);
        popupwindow.setOnClickSurePopupwindowListener(new CameraPopupwindow.OnClickSurePopupwindowListener() {
            @Override
            public void onSure() {
                popupwindow.dismiss();
                //图片名称 时间命名
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                filename = format.format(date);

                //存储至DCIM文件夹
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);//指明文件路径为SD卡根目录下DCIM文件夹
                outputImage = new File(path, filename + ".jpg");//保存图片的文件制定路径为  DCIM文件下 "系统时间"+".jpg"
                //如果该文件存在(同名)则删除替换新的
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.e(TAG, "filename::" + filename);
                Log.e(TAG, "返回前---outputImage.length():::" + outputImage.length());
                Log.e(TAG, "outputImagePath()::" + outputImage.getAbsolutePath());

                //将File对象转换为Uri并启动照相程序
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //照相
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
                startActivityForResult(intent, TAG_TAKE_PHOTO); //启动照相
            }


            @Override
            public void onCancel() {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
                startActivityForResult(i, TAG_GALLERY);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//拍照后,从相册选取照片后会走此模块

            switch (requestCode) {
                case TAG_TAKE_PHOTO://拍摄照片点击确定,在activity回调中data一直是null
                    Log.e(TAG, "返回后---outputImage.length():::" + outputImage.length());
                    startToCROP(imageUri, TAG_CROP_PHOTO_FROM_PHOTO);
                    break;

                case TAG_GALLERY:
                    if (data != null) {//选择了图库图片,从data中过去uri
                        galleryUri = data.getData();
                        Log.e("图库返回的Uri", "" + galleryUri.getPath());
                        startToCROP(galleryUri, TAG_CROP_PHOTO_FROM_GALLERY);
                    } else {//如果用户未选择图片,为了严谨,指定头像显示默认图片
                        imageViewMyphoto.setImageResource(R.drawable.ic_avatar);
                    }
                    break;
                case TAG_CROP_PHOTO_FROM_PHOTO:
                    getBitmap(imageUri);
                    //上传图片
                    request(outputImage);

                    break;
                case TAG_CROP_PHOTO_FROM_GALLERY:
                    getBitmap(galleryUri);
                    uriToFile(galleryUri);
                    //上传图片
                    request(galleryFile);


                    break;
            }
        } else {//如果用户中途点击了返回按钮,则走此模块,需要把imageView设置为默认图片
            imageViewMyphoto.setImageResource(R.drawable.ic_avatar);
        }
    }

    /**
     * 上传头像到服务器
     */
    private void request(File file) {
        RequestParams params = new RequestParams(url);
        showToast(url);
        params.addParameter("accountId", id);
        params.addBodyParameter("headImg", file);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("上传图片", "result:::::" + result);

                JSONObject jsonObject = null;
                String str = null;
                try {
                    jsonObject = new JSONObject(result);
                    str = jsonObject.getString(CommonConstant.KEY.RESPONSE_DATA_KEY);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("解析后", "netMessage" + str);
                UserData.getData(getApplicationContext()).setAccount(str);

                //                UserData.getData(mContext).getAccount().setHeadImg(result);
                Log.e("上传图片", "上传图片成功" + UserData.getData(getApplicationContext()).getHeadImg());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("ex:::" + ex.getMessage());
                showToast(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 根据uri获取File或者是绝对路径
     *
     * @param uri
     */
    private void uriToFile(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        galleryImgPath = actualimagecursor.getString(actual_image_column_index);
        galleryFile = new File(galleryImgPath);
        Log.e(TAG, "图库文件大小:" + galleryFile.length());
        Log.e(TAG, "图库文件路径:" + galleryImgPath);
    }


    /**
     * 通过Uri转换为Bitmap对象
     *
     * @param uri
     */
    private void getBitmap(Uri uri) {
        try {
            //图片解析成Bitmap对象
            Bitmap bitmap = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(uri));
            Log.e("通过Uri转换bitmap方法:", "" + uri.toString());
            Glide.with(mContext).load(uri).into(imageViewMyphoto);
            //            imageViewMyphoto.setImageBitmap(bitmap); //将剪裁后照片显示出来
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动系统剪裁照片程序
     */
    private void startToCROP(Uri uri, int tag) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //剪裁
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("scale", true);
        //设置宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设置裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        Log.e("启动剪裁图片", "启动剪裁图片");
        startActivityForResult(intent, tag); //设置裁剪参数显示图片至ImageView

        //广播刷新相册
        sendBc(uri);
    }

    /**
     * 发送系统广播,更新相册
     */
    private void sendBc(Uri uri) {
        //广播刷新相册
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(uri);
        this.sendBroadcast(intentBc);
    }


    /**
     * 退出登录逻辑
     */

    private void exit() {
        Log.i("TAG", "退出了");
        //弹出popWindow选择是或否
        final SurePopupwindow surePopupwindow = new SurePopupwindow(mActivity);
        surePopupwindow.showAtLocation(ll_rootview, Gravity.BOTTOM, 0, 0);
        surePopupwindow.setOnClickSurePopupwindowListener(new SurePopupwindow.OnClickSurePopupwindowListener() {
            @Override
            public void onSure() {
                UserData.getData(mContext).clear();
                Account data = UserData.getData(getApplicationContext()).getAccount();
                if (data == null) {
                    Log.e(TAG, "清除数据data null");
                } else {
                    Log.e(TAG, "清除数据data not null");
                }

                UISkip.skip(false, mContext, LoginActivityUpdate.class);
                surePopupwindow.dismiss();
                finish();
            }

            @Override
            public void onCancel() {

            }
        });
        //        AlertDialog alertDialog = new AlertDialog(getActivity());
        //        alertDialog.setAlertClickListener(new AlertDialog.AlertClickListener() {
        //
        //            @Override
        //            public void sure() {
        //                UserData.getData(getActivity()).clear();
        //                UISkip.skip(false, getActivity(), LoginActivity.class);
        //
        //            }
        //        });
        //        alertDialog.setAlert("是否确定退出？");
        //        alertDialog.show();
    }

    private void showToast(String text) {
        Toast.makeText(PersonalSettingActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}

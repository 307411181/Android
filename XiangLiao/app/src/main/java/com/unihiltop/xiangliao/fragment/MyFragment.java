package com.unihiltop.xiangliao.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.unihiltop.xiangliao.LoginActivity;
import com.unihiltop.xiangliao.MessageActivity;
import com.unihiltop.xiangliao.OpinionResulatActivity;
import com.unihiltop.xiangliao.PayexplainActivity;
import com.unihiltop.xiangliao.ProblemActivity;
import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.RechargeActivity;
import com.unihiltop.xiangliao.SearchTalkActivity;
import com.unihiltop.xiangliao.StoreActivity;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.AppVersion;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.dialog.AlertDialog;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AppVersionServer;
import com.unihiltop.xiangliao.popupwindow.SurePopupwindow;
import com.unihiltop.xiangliao.util.DateUtils;
import com.unihiltop.xiangliao.util.ImageLoadOptions;
import com.unihiltop.xiangliao.util.PreferencesHelper;
import com.unihiltop.xiangliao.util.Tools;
import com.unihiltop.xiangliao.util.UISkip;
import com.unihiltop.xiangliao.view.WiperSwitch;

/**
 * 我的个人中心
 */
public class MyFragment extends Fragment {
    View view;
    private ImageView      ivFragmentMyphoto;//头像
    private TextView       tvFagmentmyPhone;//电话
    private TextView       tvFagmentmyPay;//余额
    private RelativeLayout rlFragmentQuery;//话单查询
    private RelativeLayout rlFragmentPay;//话费充值
    private RelativeLayout rlFragmentRecommend;//推荐好友
    private RelativeLayout rlFragmentExplain;//资费说明
    private RelativeLayout rlFragmentHelp;//常见问题

    private RelativeLayout rlFragmentStore;//兑换商城
    private RelativeLayout rlFragmentMessage;//系统信息
    private RelativeLayout rlFragmentOpinion;//意见反馈
    private RelativeLayout rlFragmentUpgrade;//软件升级
    private TextView       tvFagmentmyScore;//积分
    private Button         btExit;//退出登陆
    //    private static final int PHOTO_SHOP_REQUEST_TAKEPHOTO = 1001;// 拍照
    //    private static final int PHOTO_SHOP_REQUEST_GArlERY = 1002;// 从相册中选择

    private String         idCardImgPath;
    private String         imgPath;
    private String         phone;
    private double         integral;
    private double         birl;
    private int            type;
    private Account        account;
    private String         myheadimg;
    private boolean        isListenOrder;
    private long           accountid;
    private String         imgurl;
    private TextView       tvMyendtime;
    private RelativeLayout rlFragmentIsOn;
    private WiperSwitch    wsAutoPlayVoice;
    private View           scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container,
                false);
        initData();
        initView();
        initListenter();
        initSetView();
        return view;
    }

    private void initData() {
        isListenOrder = PreferencesHelper.get(getActivity()).isListenOrder();
        account = UserData.getData(getActivity()).getAccount();

        phone = UserData.getData(getActivity()).getAccountName();
        birl = UserData.getData(getActivity()).getBill();
        integral = UserData.getData(getActivity()).getIntegral();
        myheadimg = UserData.getData(getActivity()).getHeadImg();
        accountid = UserData.getData(getActivity()).getAccountId();
    }

    private void initView() {
        ivFragmentMyphoto = (ImageView) view.findViewById(R.id.imageView_fragment_myphoto);
        tvFagmentmyPhone = (TextView) view.findViewById(R.id.textView_fagmentmy_phone);
        tvFagmentmyScore = (TextView) view.findViewById(R.id.textView_fagmentmy_score);
        tvFagmentmyPay = (TextView) view.findViewById(R.id.textView_fagmentmy_pay);
        rlFragmentQuery = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_query);
        rlFragmentPay = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_pay);
        rlFragmentRecommend = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_recommend);
        rlFragmentExplain = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_explain);
        rlFragmentHelp = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_problem);
        rlFragmentStore = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_store);
        rlFragmentMessage = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_message);
        rlFragmentOpinion = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_opinion);
        rlFragmentUpgrade = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_upgrade);
        tvMyendtime = (TextView) view.findViewById(R.id.textView_myendtime);
        btExit = (Button) view.findViewById(R.id.button_exit);

        rlFragmentIsOn = (RelativeLayout) view.findViewById(R.id.linearLayout_fragment_is_on);
        wsAutoPlayVoice = (WiperSwitch) view.findViewById(R.id.wiperSwitch_voice);
        if (Build.VERSION.SDK_INT > 20
                || Build.BRAND.toLowerCase().contains("meizu")
            //&& !Build.BRAND.toLowerCase().contains("oppo")
                ) {
            rlFragmentIsOn.setVisibility(View.GONE);
        } else {
            rlFragmentIsOn.setVisibility(View.VISIBLE);
        }
        wsAutoPlayVoice.setChecked(PreferencesHelper.get(getActivity()).isListenOrder());

        scrollView = view.findViewById(R.id.scrollView_my);

    }

    private void initListenter() {
        MyClickListener mylistenter = new MyClickListener();
        ivFragmentMyphoto.setOnClickListener(mylistenter);
        rlFragmentQuery.setOnClickListener(mylistenter);
        rlFragmentPay.setOnClickListener(mylistenter);
        rlFragmentRecommend.setOnClickListener(mylistenter);
        rlFragmentExplain.setOnClickListener(mylistenter);
        rlFragmentHelp.setOnClickListener(mylistenter);
        rlFragmentStore.setOnClickListener(mylistenter);
        rlFragmentMessage.setOnClickListener(mylistenter);
        rlFragmentOpinion.setOnClickListener(mylistenter);
        rlFragmentUpgrade.setOnClickListener(mylistenter);
        btExit.setOnClickListener(mylistenter);
        wsAutoPlayVoice.setOnChangedListener(new WiperSwitch.OnChangedListener() {

            @Override
            public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
                //  ConfigData.getConfigData(getApplicationContext()).setListenOrder(checkState);
                //   GlobalInfo.yesnoKey = checkState;
                PreferencesHelper.get(getActivity()).setListenOrder(checkState);
            }
        });
    }

    private void initSetView() {

    }

    public void setImg(String imgPath) {
        if (!TextUtils.isEmpty(imgPath)) {
            //addimage(imgPath);
        }

    }


    private class MyClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == ivFragmentMyphoto) {
                //                private RelativeLayout rlFragmentQuery;//话单查询
                //                private RelativeLayout rlFragmentPay;//话费充值
                //                private RelativeLayout rlFragmentRecommend;//推荐好友
                //                private RelativeLayout rlFragmentExplain;//资费说明
                //                private RelativeLayout rlFragmentHelp;//常见问题
                //
                //                private RelativeLayout rlFragmentStore;//兑换商城
                //                private RelativeLayout rlFragmentMessage;//系统信息
                //                private RelativeLayout rlFragmentOpinion;//意见反馈
                //                private RelativeLayout rlFragmentUpgrade;//软件升级
            } else if (v == rlFragmentQuery) {
                UISkip.skip(false, getActivity(), SearchTalkActivity.class);
            } else if (v == rlFragmentPay) {
                UISkip.skip(false, getActivity(), RechargeActivity.class);
            } else if (v == rlFragmentRecommend) {
                showShare();
            } else if (v == rlFragmentExplain) {
                UISkip.skip(false, getActivity(), PayexplainActivity.class);
            } else if (v == rlFragmentHelp) {
                UISkip.skip(false, getActivity(), ProblemActivity.class);
            } else if (v == rlFragmentStore) {
                UISkip.skip(false, getActivity(), StoreActivity.class);
            } else if (v == rlFragmentMessage) {
                UISkip.skip(false, getActivity(), MessageActivity.class);
            } else if (v == rlFragmentOpinion) {
                UISkip.skip(false, getActivity(), OpinionResulatActivity.class);
            } else if (v == rlFragmentUpgrade) {
                getConsumerAppVersion();
            } else if (v == btExit) {
                exit();
            }

        }

    }

    private void showShare() {
        //        ShareSDK.initSDK(getActivity());
        //        OnekeyShare oks = new OnekeyShare();
        //        // 关闭sso授权
        //        oks.disableSSOWhenAuthorize();
        //        // 分享时Notification的图标和文字
        //        // oks.setNotification(R.drawable.ic_launcher,
        //        // getString(R.string.app_name));
        //        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //        oks.setTitle(getString(R.string.ssdk_oks_share));
        //        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        //        oks.setTitleUrl("http://114.215.85.203:8080/XL/download/initDownloadPage");
        //        // text是分享文本，所有平台都需要这个字段
        //        oks.setText("祥聊，想聊就聊，爱聊不聊，下载请点击链接，http://114.215.85.203:8080/XL/download/initDownloadPage");
        //        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //        //oks.setImagePath(imgFlie);//确保SDcard下面存在此张图片
        //        // url仅在微信（包括好友和朋友圈）中使用
        //        oks.setUrl("http://114.215.85.203:8080/XL/download/initDownloadPage");
        //        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //        // oks.setComment("我是测试评论文本");
        //        // site是分享此内容的网站名称，仅在QQ空间使用
        //        oks.setSite(getString(R.string.app_name));
        //        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //        oks.setSiteUrl("http://114.215.85.203:8080/XL/download/initDownloadPage");
        //        // 隐藏编辑页面
        //        oks.setSilent(true);
        //        // 启动分享GUI
        //        oks.show(getActivity());
        Tools.showToast(getActivity(), "暂时不能分享");
    }

    private void exit() {

        Log.i("TAG", "退出了");

        final SurePopupwindow surePopupwindow = new SurePopupwindow(getActivity());
        surePopupwindow.showAtLocation(scrollView, Gravity.BOTTOM, 0, 0);
        surePopupwindow.setOnClickSurePopupwindowListener(new SurePopupwindow.OnClickSurePopupwindowListener() {
            @Override
            public void onSure() {
                UserData.getData(getActivity()).clear();
                UISkip.skip(false, getActivity(), LoginActivity.class);
                surePopupwindow.dismiss();
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

    private void getConsumerAppVersion() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, null);
        new AppVersionServer().getAppVersion(new NetMessageCallback() {
            @Override
            public void onFailure(String error, String message) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(NetMessage netMessage) {
                progressDialog.dismiss();
                if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                    final AppVersion appVersion = new Gson().fromJson(netMessage.getData(), AppVersion.class);
                    try {
                        if (appVersion.getVersionCode() > getPackageInfo().versionCode) {
                            AlertDialog dialog = new AlertDialog(getActivity());
                            dialog.setAlert("检测到新版本，是否更新？");
                            dialog.setSureButton("更新");
                            dialog.setCancelButton("放弃");
                            dialog.setAlertClickListener(new AlertDialog.AlertClickListener() {

                                @Override
                                public void sure() {
                                    Uri uri = Uri.parse(appVersion.getDownloadURL());

                                    String url = uri.toString();
                                    if (!url.startsWith("http://")) {
                                        Toast.makeText(getActivity(), "下载地址错误", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(downloadIntent);
                                }
                            });
                            dialog.show();
                        } else {
                            Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_LONG).show();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "获取最新版本失败，请稍后再试", Toast.LENGTH_LONG).show();
                    }
                } else {
                    progressDialog.dismiss();
                    //Toast.makeText(getActivity(), netMessage.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 获取版本信息
     *
     * @return 版本信息
     * @throws Exception
     */
    private PackageInfo getPackageInfo() throws Exception {
        PackageManager packageManager = getActivity().getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
        return packInfo;
    }

    @Override
    public void onResume() {

        initData();
        setData();
        super.onResume();
    }

    private void setData() {
        tvFagmentmyPhone.setText(phone);
        tvFagmentmyPay.setText(String.valueOf(birl));
        tvFagmentmyScore.setText(String.valueOf(integral));
        if (account != null) {
            tvMyendtime.setText(DateUtils.getYYYYMMDD(account.availableEndDate));
        } else {
            tvMyendtime.setText("2015");
        }


        imgurl = UserData.getData(getActivity()).getHeadImg();
    }

    public void uploadHeadImg() {
        //Log.i("headImg",account.headImg+"");
        if (!TextUtils.isEmpty(myheadimg)) {
            ImageLoader.getInstance().displayImage(
                    myheadimg,
                    ivFragmentMyphoto,
                    ImageLoadOptions.getOptions(R.drawable.ic_avatar,
                            ImageLoadOptions.headCornerRadius));
        } else {
            //			Toast.makeText(getActivity(), "headImg=" + account.headImg, Toast.LENGTH_LONG).show();
        }

    }

}
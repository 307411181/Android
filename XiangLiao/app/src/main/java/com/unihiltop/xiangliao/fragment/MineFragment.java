package com.unihiltop.xiangliao.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.unihiltop.xiangliao.HelpActivity;
import com.unihiltop.xiangliao.MessageActivity;
import com.unihiltop.xiangliao.PersonalSettingActivity;
import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.RechargeActivityUpdate;
import com.unihiltop.xiangliao.SearchTalkActivityUpdate;
import com.unihiltop.xiangliao.StoreActivity;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.AppVersion;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.dialog.AlertDialog;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.AppVersionServer;
import com.unihiltop.xiangliao.util.DateUtils;
import com.unihiltop.xiangliao.util.PreferencesHelper;
import com.unihiltop.xiangliao.view.GlideCircleTransform;
import com.unihiltop.xiangliao.view.WiperSwitch;

import java.util.ArrayList;

/**
 * @创建者 张京
 * @创建时间 2016/9/18 15:04
 * @描述 ${我的详聊页面}
 */
public class MineFragment extends Fragment {
    private View               view;
    /**
     * 控件
     */
    private Activity           mActivity;
    private GridView           mGridView;
    private ArrayList<String>  mTexts;//存放显示item上的文字集合
    private ArrayList<Integer> mResIdList;//item图片资源
    private TextView           tvFagmentmyScore;//积分
    private TextView           tvFagmentmyPay;//详聊币
    private TextView           tvMyendtime;//到期时间
    private TextView           tvFagmentmyPhone;//电话
    private ImageView          ivFragmentMyphoto;


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
    private RelativeLayout rlFragmentIsOn;
    private WiperSwitch    wsAutoPlayVoice;
    private View           scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);

        initView();//初始化控件
        initData();//初始化数据,以及从网络上获取头像写在了initData()方法中
        initAdapter();//初始化适配器
        return view;
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mGridView.setAdapter(new MyAdapter());
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://点击话单查询
                        mActivity.startActivity(new Intent(mActivity, SearchTalkActivityUpdate.class));
                        break;
                    case 1://详聊充值
                        //                        mActivity.startActivity(new Intent(mActivity, RechargeActivity.class));
                        mActivity.startActivity(new Intent(mActivity, RechargeActivityUpdate.class));
                        break;
                    case 2://好友推荐
                        Toast.makeText(mActivity, "暂时不能分享", Toast.LENGTH_SHORT).show();
                        break;
                    case 3://帮助与反馈
                        mActivity.startActivity(new Intent(mActivity, HelpActivity.class));
                        break;
                    case 4://商品展示
                        mActivity.startActivity(new Intent(mActivity, StoreActivity.class));
                        break;
                    case 5://系统消息
                        mActivity.startActivity(new Intent(mActivity, MessageActivity.class));
                        break;
                    case 6://软件升级
                        getConsumerAppVersion();
                        break;
                    case 7://个人设置
                        mActivity.startActivity(new Intent(mActivity, PersonalSettingActivity.class));
                        break;
                }

            }
        });
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
                        int versionCode = getPackageInfo().versionCode;
                        System.out.println(versionCode);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

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

        long availableStartDate = account.getAvailableStartDate();
        long availableEndDate = account.getAvailableEndDate();
        Log.e("tag", "" + availableStartDate);
        Log.e("tag", "" + availableEndDate);
        //一天代表
        long oneDay = 1000 * 60 * 60 * 24;
        long s = availableEndDate - availableStartDate;
        Log.e("tag_MinFragment", "" + s / oneDay);
        Log.e("tag_MinFragment", "" + DateUtils.getYYYYMMDD(account.availableStartDate));

        //取出头像地址
        imgurl = UserData.getData(getActivity()).getHeadImg();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        isListenOrder = PreferencesHelper.get(getActivity()).isListenOrder();
        account = UserData.getData(getActivity()).getAccount();

        phone = UserData.getData(getActivity()).getAccountName();
        birl = UserData.getData(getActivity()).getBill();
        integral = UserData.getData(getActivity()).getIntegral();
        myheadimg = UserData.getData(getActivity()).getHeadImg();
        accountid = UserData.getData(getActivity()).getAccountId();

        mTexts = new ArrayList<>();
        mResIdList = new ArrayList<>();
        //添加文字
        mTexts.add("话单查询");
        mTexts.add("祥聊充值");
        mTexts.add("好友推荐");
        mTexts.add("帮助与反馈");
        mTexts.add("袖品展示");
        mTexts.add("系统消息");
        mTexts.add("软件升级");
        mTexts.add("个人设置");
        //添加图片资源
        mResIdList.add(R.drawable.ic_my_huadanchaxun);
        mResIdList.add(R.drawable.ic_my_xiangliaochongzhi);
        mResIdList.add(R.drawable.ic_my_tuijianhaoyou);
        mResIdList.add(R.drawable.ic_my_tuijianhaoyou);
        mResIdList.add(R.drawable.ic_my_tuijianhaoyou);
        mResIdList.add(R.drawable.ic_my_xitongxiaoxi);
        mResIdList.add(R.drawable.ic_my_ruanjianshengji);
        mResIdList.add(R.drawable.ic_my_gerenshezhi);

        /**
         * 根据保存的用户资料显示圆形图
         * 用Glide显示
         */
        Log.e("头像地址", "头像地址:" + myheadimg);
        Log.e("account", "头像地址:" + account);
        Log.e("phone", "头像地址:" + phone);
        RequestManager circleTransform = Glide.with(getActivity());
        circleTransform.load(myheadimg).transform(new GlideCircleTransform(getActivity()))
                .placeholder(R.drawable.ic_avatar).error(R.drawable.ic_avatar).into(ivFragmentMyphoto);


    }

    /**
     * 初始化控件
     */
    private void initView() {
        mGridView = (GridView) view.findViewById(R.id.mine_gridview);
        mActivity = getActivity();

        tvFagmentmyScore = (TextView) view.findViewById(R.id.textView_fagmentmy_score);//积分
        tvFagmentmyPay = (TextView) view.findViewById(R.id.textView_fagmentmy_pay);//详聊币
        tvMyendtime = (TextView) view.findViewById(R.id.textView_myendtime);//到期时间
        tvFagmentmyPhone = (TextView) view.findViewById(R.id.textView_fagmentmy_phone);//电话
        ivFragmentMyphoto = (ImageView) view.findViewById(R.id.imageView_fragment_myphoto);//头像
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTexts.size();
        }

        @Override
        public Object getItem(int position) {
            return mTexts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(), R.layout.item_mine_gridview, null);

            TextView tvText = (TextView) view.findViewById(R.id.tv_item_my_gridview);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_item_my_gridview);

            tvText.setText(mTexts.get(position));
            iv.setBackgroundResource(mResIdList.get(position));
            return view;
        }
    }
}

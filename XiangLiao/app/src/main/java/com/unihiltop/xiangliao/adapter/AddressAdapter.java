package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.Account;
import com.unihiltop.xiangliao.bean.ReceiverAddress;
import com.unihiltop.xiangliao.constant.PreferenceKeyConstants;
import com.unihiltop.xiangliao.data.UserData;
import com.unihiltop.xiangliao.net.CommonConstant;
import com.unihiltop.xiangliao.net.NetMessage;
import com.unihiltop.xiangliao.net.NetMessageCallback;
import com.unihiltop.xiangliao.netserver.ReceiverAddressServer;
import com.unihiltop.xiangliao.util.PreferencesHelper;
import com.unihiltop.xiangliao.util.Tools;

import java.util.List;


/**
 * 地址管理listview适配器
 * Created by yy on 2015/11/18.
 */
public class AddressAdapter extends BaseAdapter {

    private final PreferencesHelper preferencesHelper;
    private Context context;

    private List<ReceiverAddress> list;

    private int defaultPosition = -1;
    private Account account;
    public AddressAdapter(Context context, List<ReceiverAddress> list){
        this.context=context;
        this.list=list;
        preferencesHelper = PreferencesHelper.get(context);
        account = UserData.getData(context).getAccount();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView = View.inflate(context, R.layout.listview_address_item, null);

            viewHolder.tvName = (TextView) convertView.findViewById(R.id.textView_name);
            viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.textView_phone);
            viewHolder.tvZipCode= (TextView) convertView.findViewById(R.id.textView_zip_code);
            viewHolder.tvAddress= (TextView) convertView.findViewById(R.id.textView_address);
            viewHolder.rbAutoAddress = (RadioButton) convertView.findViewById(R.id.radioButton_auto_address);
            viewHolder.llDelete= (LinearLayout) convertView.findViewById(R.id.linearLayout_delete);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(list.get(position).getContactPersonName());
        viewHolder.tvPhone.setText(list.get(position).getContactPersonMobile());
        viewHolder.tvZipCode.setText(list.get(position).getPostCode());
        viewHolder.tvAddress.setText(list.get(position).getCity()+list.get(position).getCounty()+list.get(position).getDetailAddress());

        if(!list.get(position).isDefaultAddress()){
            viewHolder.rbAutoAddress.setChecked(false);
        }{
            viewHolder.rbAutoAddress.setChecked(true);
            defaultPosition = position;

        }

        viewHolder.llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReceiverAddressServer().deleteReceiverAddress(account.getId(), list.get(position).getId(), new NetMessageCallback() {

                    @Override
                    public void onFailure(String error, String message) {
                        Tools.showNetworkErrorToast(context);
                    }

                    @Override
                    public void onSuccess(NetMessage netMessage) {
                        if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                            list.remove(position);
                            preferencesHelper.put(PreferenceKeyConstants.KEY_ACCOUNT_ADDRESS_LIST, new Gson().toJson(list));
                            notifyDataSetChanged();
                        } else {
                            Tools.showToast(context, netMessage.getMsg());
                        }
                    }
                });
            }
        });
        viewHolder.rbAutoAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (list.get(position).isDefaultAddress()){
                    RadioButton radioButton = (RadioButton)v;
                    radioButton.setChecked(true);
                    return;
                }
                Tools.showProgress(context, "切换中，请稍等");
                new ReceiverAddressServer().setDefaultAddress(account.getId(), list.get(position).getId(), new NetMessageCallback() {

                    @Override
                    public void onFailure(String error, String message) {
                        Tools.closeProgressDialog();
                        Tools.showNetworkErrorToast(context);
                        CheckBox checkBox = (CheckBox)v;
                        checkBox.setChecked(false);
                    }

                    @Override
                    public void onSuccess(NetMessage netMessage) {
                        Tools.closeProgressDialog();
                        if (netMessage.getCode() == CommonConstant.VALUE.RESULT_SUCCESS) {
                            list.get(position).setDefaultAddress(true);
                            if (defaultPosition != -1){
                                list.get(defaultPosition).setDefaultAddress(false);
                            }
                            preferencesHelper.putObject(PreferenceKeyConstants.KEY_ACCOUNT_DEFAULT_ADDRESS, list.get(position));
                            preferencesHelper.put(PreferenceKeyConstants.KEY_ACCOUNT_ADDRESS_LIST, new Gson().toJson(list));
                            notifyDataSetChanged();
                        } else {
                            Tools.showToast(context, netMessage.getMsg());
                        }
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder{
        /**
         * 名字
         */
        TextView tvName;
        /**
         * 电话号码
         */
        TextView tvPhone;
        /**
         * 邮编
         */
        TextView tvZipCode;
        /**
         * 地址
         */
        TextView tvAddress;
        /**
         * 默认地址
         */
        RadioButton rbAutoAddress;
        /**
         * 删除
         */
        LinearLayout llDelete;
    }
}

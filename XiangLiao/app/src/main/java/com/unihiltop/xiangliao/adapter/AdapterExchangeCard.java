package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.ExchangeRecord;
import com.unihiltop.xiangliao.util.ImageLoadOptions;

import java.util.List;

/**
 * Created by yangyang on 2015/10/21.
 */
public class AdapterExchangeCard extends BaseAdapter {
    private Context context;
    private List<ExchangeRecord> list;
   public AdapterExchangeCard(Context context, List<ExchangeRecord> list){
         this.context = context;
         this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ExchangeRecord item = list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_exchange_card,null);
            viewHolder.ivStoreMonthe = (ImageView) convertView.findViewById(R.id.imageView_store_monthe);
            viewHolder.tvStoreText2 = (TextView) convertView.findViewById(R.id.textView_store_text2);
            viewHolder.tvStoreMonthnumber = (TextView) convertView.findViewById(R.id.textView_store_monthnumber);
            viewHolder.tvState = (TextView) convertView.findViewById(R.id.textView_state);

            viewHolder.tvStoreAmount = (TextView) convertView.findViewById(R.id.textView_store_amount);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       // viewHolder.ivStoreMonthe.setBackground(item.productName);

        /*ImageLoader.getInstance().displayImage(order.receiptImg,
                viewHolder.ivStoreMonthe,
                ImageLoadOptions.getOptions(R.drawable.ic_picture));*/
        viewHolder.tvStoreText2.setText(item.integralProduct.productName);
        viewHolder.tvStoreMonthnumber.setText(String.valueOf(item.integralProduct.needCoin));
        viewHolder.tvStoreAmount.setText(String.valueOf(item.exchangeAmount));
        int i = item.deliveryStatus;
        switch (i){
            case 0:
                viewHolder.tvState.setText("未发货");
                break;
            case 1:
                viewHolder.tvState.setText("送货中");
                break;
            case 2:
                viewHolder.tvState.setText("已完成");
                break;
        }

        String imageUrl = item.integralProduct.imgURL;
        if(imageUrl != null){
           ImageLoader.getInstance().displayImage(imageUrl,
                     viewHolder.ivStoreMonthe,
                    ImageLoadOptions.getOptions(R.drawable.ic_picture));
        }

        return convertView;
    }
    private final class ViewHolder{
        ImageView ivStoreMonthe;//图片
        TextView tvStoreText2;//介绍
        TextView tvStoreMonthnumber;//金币
        TextView tvState;//状态
        TextView tvStoreAmount;//数量
    }
}

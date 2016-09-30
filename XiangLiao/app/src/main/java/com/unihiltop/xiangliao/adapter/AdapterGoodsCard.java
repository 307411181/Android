package com.unihiltop.xiangliao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.IntegralProduct;
import com.unihiltop.xiangliao.util.ImageLoadOptions;
import com.unihiltop.xiangliao.util.UISkip;

import java.util.List;

/**
 * Created by yangyang on 2015/10/21.
 */
public class AdapterGoodsCard extends BaseAdapter {
    private Context context;
    private List<IntegralProduct> list;
   public AdapterGoodsCard(Context context ,List<IntegralProduct> list){
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
        final IntegralProduct item = list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_goods_card,null);
            viewHolder.ivStoreMonthe = (ImageView) convertView.findViewById(R.id.imageView_store_monthe);
            viewHolder.tvStoreText2 = (TextView) convertView.findViewById(R.id.textView_store_text2);
            viewHolder.tvStoreMonthnumber = (TextView) convertView.findViewById(R.id.textView_store_monthnumber);
            viewHolder.btStoreMonth = (Button) convertView.findViewById(R.id.button_store_month);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       // viewHolder.ivStoreMonthe.setBackground(item.productName);

        /*ImageLoader.getInstance().displayImage(order.receiptImg,
                viewHolder.ivStoreMonthe,
                ImageLoadOptions.getOptions(R.drawable.ic_picture));*/
        viewHolder.tvStoreText2.setText(item.productName);
        viewHolder.tvStoreMonthnumber.setText(String.valueOf(item.amount));
        String imageUrl = item.imgURL;
        if(imageUrl != null){
           ImageLoader.getInstance().displayImage(imageUrl,
                     viewHolder.ivStoreMonthe,
                    ImageLoadOptions.getOptions(R.drawable.ic_picture));
        }
        viewHolder.btStoreMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   UISkip.skip(false, context,ExchangeStoreActivity.class);
                UISkip.skipToExchangeStoreActivity(context,item);
            }
        });
        return convertView;
    }
    private final class ViewHolder{
        ImageView ivStoreMonthe;//图片
        TextView tvStoreText2;//介绍
        TextView tvStoreMonthnumber;//余量
        Button btStoreMonth;//跳转
    }
}

package com.unihiltop.xiangliao.adapter;


import android.content.Context;
import android.graphics.Color;
import android.provider.CallLog.Calls;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.Calllog;
import com.unihiltop.xiangliao.util.DateUtils;

import java.util.List;


public class CalllogAdapter extends BaseAdapter {
    private Context        mContext;
    private List<Calllog>  list;
    private LayoutInflater inflater;
    public  String         type;
    public  boolean        isVisible;//表示当前item是否可见的临时变量
    public boolean isIconVisible = true;//表示当前item是否可见的临时变量

    /**
     * 控件
     */
    ImageView imageview_outgoing;
    CheckBox  checkbox;
    ImageView imageview_time_icon;


    public CalllogAdapter(Context context, List<Calllog> logs, String type) {
        this.mContext = context;
        this.list = logs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.type = type;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Calllog getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.listview_item_calllog, null);
        TextView textView_name = (TextView) view.findViewById(R.id.textView_name);
        TextView textView_number = (TextView) view.findViewById(R.id.textView_number);
        TextView textview_date = (TextView) view.findViewById(R.id.textview_date);
        imageview_outgoing = (ImageView) view.findViewById(R.id.imageview_outgoing);
        TextView textview_time = (TextView) view.findViewById(R.id.textview_time);
        checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        imageview_time_icon = (ImageView) view.findViewById(R.id.imageview_time_icon);


        /**
         * 判断每个Item中时间图标和checbox图标是否想显示,以及checkBox点击事件
         */
        //根据判断传入的值判断是否显示item中选择按钮
        setVisible(isVisible);
        //根据传入的值判断图片是否显示Item中的时间图片
        setIconVisible(isIconVisible);
        //根据传入对象的值确定按钮是否为选中状态
        checkbox.setChecked(list.get(position).isChecked());
        //多选按钮点击监听
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setChecked(isChecked);//修改
                Log.e("选中了", "" + position);
            }
        });

        final Calllog log = list.get(position);


        //设置名字的颜色
        if (log.getType() == Calls.MISSED_TYPE) {
            textView_name.setTextColor(Color.RED);
        } else {
            textView_name.setTextColor(Color.BLACK);
        }
        //设置是否显示呼出图标
        if (log.getType() == Calls.OUTGOING_TYPE) {
            imageview_outgoing.setVisibility(View.VISIBLE);
        } else {
            imageview_outgoing.setVisibility(View.INVISIBLE);
        }
        //是否显示删除选择按钮
        if (type.equals("show")) {
            checkbox.setVisibility(View.VISIBLE);
        } else if (type.equals("hide")) {
            checkbox.setVisibility(View.GONE);
        }

        textView_name.setText(log.getName());
        //设置电话号码
        textView_number.setText(log.getNumber());
        //设置日期
        textview_date.setText(DateUtils.getDate(log.getDate()));
        textview_time.setText(DateUtils.getRecordsDate(log.getDate()));
        return view;
    }

    /**
     * 设置没个Item按钮是否显示
     */
    private void setVisible(boolean isVisible) {
        if (isVisible) {
            checkbox.setVisibility(View.VISIBLE);
        } else {
            checkbox.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置没个Item图片是否显示
     */
    private void setIconVisible(boolean isVisible) {
        if (isVisible) {
            imageview_time_icon.setVisibility(View.VISIBLE);
        } else {
            imageview_time_icon.setVisibility(View.INVISIBLE);
        }
    }



}

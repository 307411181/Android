package com.unihiltop.xiangliao.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.unihiltop.xiangliao.R;

/**
 * Created by duanchuan on 2015/11/5.
 */
public class CameraPopupwindow extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {

    private Activity activity;
    // 布局文件
    private View popView;
    // 确定
    private Button sureBtn;
    // 取消
    private Button cancelBtn;
    private OnClickSurePopupwindowListener onClickSurePopupwindowListener;

    public CameraPopupwindow(Activity activity) {
        super(activity);

        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.popupwindow_camera, null);

        initView();
        initListener();
    }

    private void initView() {
        // 让泡泡窗口获取焦点
        super.setFocusable(true);
        // 点击其它地方收起泡泡窗口
        super.setBackgroundDrawable(new BitmapDrawable());
        // 设置弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow的View
        this.setContentView(popView);

        sureBtn = (Button) popView.findViewById(R.id.button_sure);
        cancelBtn = (Button) popView.findViewById(R.id.button_cancel);
    }

    private void initListener() {
        sureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        this.setOnDismissListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == sureBtn) {
            if (onClickSurePopupwindowListener != null){
                onClickSurePopupwindowListener.onSure();
            }
        } else if (v == cancelBtn) {
            this.dismiss();
            if (onClickSurePopupwindowListener != null){
                onClickSurePopupwindowListener.onCancel();
            }
        }
    }

    /**
     * 显示popupwindow
     *
     * @param parent
     * @param gravity
     * @param x
     * @param y
     */
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        backgroundAlpha(0.5f);
    }

    /**
     * 设置屏幕的背景透明度
     *
     * @param bgAlpha 透明度0.0-1.0
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * popupwindow被取消时还原背景透明度
     */
    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }


    public void setOnClickSurePopupwindowListener(OnClickSurePopupwindowListener l){
        this.onClickSurePopupwindowListener = l;
    }

    public interface OnClickSurePopupwindowListener{
        void onSure();
        void onCancel();
    }




}
package com.unihiltop.xiangliao.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.Toast;

import com.unihiltop.xiangliao.dialog.AlertDialog;
import com.unihiltop.xiangliao.manager.ContactManager;

import java.util.List;

public class Tools {
//    public static Map<String ,String> map=new HashMap();
    public static String getCurrentVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo inPackageInfo = packageManager.getPackageInfo(packageName, 0);
            String Version = inPackageInfo.versionName;
            return Version;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    private static ProgressDialog prDialog;

    public static void showProgress(Context context, String msg) {
        if (prDialog == null) {
            prDialog = new ProgressDialog(context);
            prDialog.setMessage(msg);
            prDialog.setCanceledOnTouchOutside(false);
            prDialog.show();

        }
    }
    public static void showProgres(Context context, String title,String msg) {
        if (prDialog == null) {
            prDialog = new ProgressDialog(context);
            prDialog.setMessage(msg);
            prDialog.setTitle(title);
            prDialog.setCanceledOnTouchOutside(false);
            prDialog.show();

        }
    }

    public static void closeProgressDialog() {
        if (prDialog != null) {
            prDialog.cancel();
            prDialog = null;
        }
    }

    public static void deleteOrNot(final Context context, final List numbers) {

        AlertDialog alertDialog = new AlertDialog(context);
        alertDialog.setAlertClickListener(new AlertDialog.AlertClickListener() {
            @Override
            public void sure() {
                if (numbers.size() == 0) {
                    Tools.showToast(context, "请勾选通话记录");
                    return;
                }
                Log.i("TAG","选中了"+numbers.size()+"条通话记录");
                for(int i=0;i<numbers.size();i++){
                    Log.i("TAG","号码为"+numbers.get(i));
                }

                ContactManager.DeleteCall(context,numbers);
            }
        });
        alertDialog.setAlert("是否确定删除通话记录？");
        alertDialog.show();
    }

    public static void showNetworkErrorToast(Context context) {
        Toast.makeText(context, "网络异常，请稍后再试！", Toast.LENGTH_SHORT).show();
    }
}

	


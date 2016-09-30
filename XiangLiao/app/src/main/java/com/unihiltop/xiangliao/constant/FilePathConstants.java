package com.unihiltop.xiangliao.constant;

import android.content.Context;
import android.os.Environment;

/**
 * Created by yangyang on 2015/10/19.
 */
public class FilePathConstants {
    public static String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String IMAGE_PATH = SD_CARD_PATH+"/kuaihua/user/image";
    public static String getFilePath(Context context){
        return context.getFilesDir().getPath().toString();
    }
}

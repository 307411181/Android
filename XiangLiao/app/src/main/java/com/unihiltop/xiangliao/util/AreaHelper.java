package com.unihiltop.xiangliao.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class AreaHelper{
public final String DB_NAME = "area.db";
/**
 * 数据库路径
 */
public static final String DATABASE_PATH = "/data"+Environment.getDataDirectory().getAbsolutePath() +"/com.guotion.kuaihua/databases/";
	private final int BUFFER_SIZE = 400000;

	private static AreaHelper instance = null;
	private AndroidConnectionSource connectionSource;
	public static AreaHelper getInstance(Context context){
		if (instance == null) {
			instance = new AreaHelper(context);
		}
		return instance;
	}
	
	private AreaHelper(Context context) {
		try {
			File filePath = new File(DATABASE_PATH);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!(new File(DATABASE_PATH + DB_NAME).exists())) {//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
				InputStream is = context.getAssets().open(DB_NAME); //欲导入的数据库
				FileOutputStream fos = new FileOutputStream(DATABASE_PATH + DB_NAME);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + DB_NAME,
					null);
			connectionSource = new AndroidConnectionSource(db);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy(){
		instance = null;
	}
	
	public <D extends Dao<T, ?>, T> D  getDao(Class<T> clazz) throws Exception {
		return DaoManager.createDao(connectionSource, clazz);
	}
}

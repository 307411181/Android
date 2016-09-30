package com.unihiltop.xiangliao.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
	/**
	 * 写入数据到指定文件
	 * @param filePath 文件路径
	 * @param text 写入内容
	 * @param append 是否追加内容
	 */
	public static boolean write(String filePath, String text, boolean append){
		
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			FileOutputStream fw = new FileOutputStream(filePath, append);
			byte[] byt = text.getBytes();
			fw.write(byt);
			fw.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			return false;
		} 
	}
}

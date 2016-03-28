package org.liuyk.konghao.app.utils;

import java.io.File;

import org.liuyk.konghao.app.AppContext;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utils {

	/**
	 * 获取文件存储的文件夹
	 * 
	 * @return
	 */
	public static File getFileDir() {
		File file = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			file = new File(Environment.getExternalStorageDirectory(), "konghao");
		} else {
			file = new File(AppContext.getAppContext().getFilesDir(), "konghao");
		}
		if (!file.exists()) file.mkdirs();
		return file;
	}
	
	private static DisplayMetrics getScreenMetrics(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics;
	}
	
	public static int getScreenWidthPx(Context context) {
		DisplayMetrics outMetrics = getScreenMetrics(context);
		return outMetrics.widthPixels;
	}
	
	public static int getScreenHeightPx(Context context) {
		DisplayMetrics outMetrics = getScreenMetrics(context);
		return outMetrics.heightPixels;
	}

}

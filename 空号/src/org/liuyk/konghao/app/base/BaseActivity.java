package org.liuyk.konghao.app.base;

import java.lang.reflect.Field;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;


public class BaseActivity extends Activity {

	private static int statusBarHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("BaseActivity", "--------" + this.getClass().getSimpleName() + " oncreate! " + "--------");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (canSetTranslucentStatusBar()) {
			setTranslucentStatus();
		}
	}
	
	/**检测是否可以设置透明状态栏*/
	protected boolean canSetTranslucentStatusBar() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void setTranslucentStatus() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getStatusBarHeight();
	}
	
	/**
	 * 为TitleView更新高度
	 * @param titleView
	 */
	protected void layoutTitleBar(RelativeLayout titleView) {
		if(canSetTranslucentStatusBar() && statusBarHeight > 0) {
			ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
			layoutParams.height = layoutParams.height + statusBarHeight;
			titleView.setLayoutParams(layoutParams);
		}
	}
	
	private void getStatusBarHeight() {
		if(statusBarHeight > 0) return;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			statusBarHeight = getResources().getDimensionPixelSize(resourceId);
			if(statusBarHeight > 0) {
				return;
			}
		}
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = getResources().getDimensionPixelSize(x);
			if (statusBarHeight > 0) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Rect frame = new Rect();
			getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
			statusBarHeight = frame.top;
			if (statusBarHeight > 0) {
				return;
			}
		}
	}
}

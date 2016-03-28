package org.liuyk.konghao.app.activity;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.app.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		// 设置当前窗体为全屏显示
		getWindow().setFlags(flag, flag);
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.splash);
		imageView.setScaleType(ScaleType.FIT_XY);
		setContentView(imageView);
		handler.sendEmptyMessageDelayed(1, 3000);
	}

	private final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
			finish();
		};
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	};
	
}

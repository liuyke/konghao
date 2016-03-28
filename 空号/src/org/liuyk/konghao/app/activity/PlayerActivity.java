package org.liuyk.konghao.app.activity;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.app.base.BaseActivity;
import org.liuyk.konghao.app.widget.MyMediaController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class PlayerActivity extends BaseActivity {
	private static final String TAG = "PlayerActivity";
	protected static final int BATTERY = 1;
	protected static final int TIME = 2;
	private VideoView mVideoView;
	private MyMediaController mMediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerBoradcastReceiver();
		 //定义全屏参数
	    int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
	    //设置当前窗体为全屏显示
   		getWindow().setFlags(flag, flag);
   		toggleHideyBar();
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
			return;
		}
		setContentView(R.layout.activity_player);
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		
		String playPath = getIntent().getStringExtra("playPath");
		mVideoView.setVideoPath(playPath);
		mMediaController = new MyMediaController(this, mVideoView, this);
		mMediaController.show(15000);// 控制器显示15s后自动隐藏
		mVideoView.setMediaController(mMediaController);// 绑定控制器
		mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);// 设置播放画质 高画质
		mVideoView.requestFocus();// 取得焦点
		updateTime();
	}
	
	public void toggleHideyBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(batteryBroadcastReceiver);
		mHandler.removeCallbacksAndMessages(null);
	}

	private void registerBoradcastReceiver() {
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	    registerReceiver(batteryBroadcastReceiver, intentFilter);
	}
	
	public void updateTime() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mHandler.obtainMessage(TIME, getNow()).sendToTarget();
			}
		}, 1000);
	}
	
	private final static SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
	
	private final static String getNow() {
		return df.format(new Date());
	}
	
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TIME:
				mMediaController.setTime(msg.obj.toString());
				updateTime();
				break;
			case BATTERY:
				mMediaController.setBattery(msg.obj.toString());
				break;
			}
		};
	};
	
	private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
	            //获取当前电量
	            int level = intent.getIntExtra("level", 0);
	            //电量的总刻度
	            int scale = intent.getIntExtra("scale", 100);
	            Message msg = new Message();
	            msg.obj = (level*100)/scale+"";
	            msg.what = BATTERY;
	            mHandler.sendMessage(msg);
	        }
	    }
	};

	public static void actionStart(Context context, String playPath) {
		Intent intent = new Intent(context, PlayerActivity.class);
		intent.putExtra("playPath", playPath);
		context.startActivity(intent);
	}
	
}

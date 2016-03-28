package org.liuyk.konghao.app.widget;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.app.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MyMediaController extends MediaController implements View.OnClickListener {
	private final static String TAG = "MyMediaController";
	private VideoView videoView;
	private Activity activity;
	private int controllerWidth;
	private LayoutInflater layoutInflater;
	private ImageButton img_back;
	private ImageView img_Battery;
	private TextView textViewTime;
	private GestureDetector mGestureDetector;
	private int mVolume;
	private AudioManager mAudioManager;
	private int mMaxVolume;
	private TextView tvTip;
	
	public MyMediaController(Context context, VideoView videoView , Activity activity) {
		super(context);
		this.videoView = videoView;
	    this.activity = activity;
	    controllerWidth = Utils.getScreenWidthPx(activity);
	    layoutInflater = activity.getLayoutInflater();
	    mGestureDetector = new GestureDetector(activity, new MyGestureListener());
	    mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	    mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}
	
	@Override
	protected View makeControllerView() {
		View v = layoutInflater.inflate(R.layout.media_controller, null, false);
		v.setMinimumHeight(controllerWidth);
		String packageName = activity.getPackageName();
		img_back = (ImageButton) v.findViewById(getResources().getIdentifier("mediacontroller_top_back", "id", packageName));
	    img_Battery = (ImageView) v.findViewById(getResources().getIdentifier("mediacontroller_imgBattery", "id", packageName));
	    tvTip = (TextView) v.findViewById(R.id.tip);
	    img_back.setOnClickListener(this);
	    textViewTime = (TextView)v.findViewById(getResources().getIdentifier("mediacontroller_time", "id", packageName));
	    v.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mGestureDetector.onTouchEvent(event)) {
					return true;
				}
				 // 处理手势结束
			    switch (event.getAction() & MotionEvent.ACTION_MASK) {
			        case MotionEvent.ACTION_UP:
			            break;
			    }
				return false;
			}
		});
		return v;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    System.out.println("MYApp-MyMediaController-dispatchKeyEvent");
	    return true;
	}
	
	@Override
	public void onClick(View v) {
		if(activity != null) {
			activity.finish();
		}
	}
	
	/**
	 * 显示电量
	 * @param stringBattery
	 */
	public void setBattery(String stringBattery){
		Log.e(TAG, "---setBattery---" +stringBattery);
	    if(textViewTime != null && img_Battery != null){
	        int battery = Integer.valueOf(stringBattery);
	        String packageName = activity.getPackageName();
	        String batteryId = "battery";
	        if(battery < 10) {
	        	batteryId += 0;
	        } else if(battery < 30 && battery >= 10) {
	        	batteryId += 1;
	        } else if(battery < 50 && battery >= 20) {
	        	batteryId += 2;
	        } else if(battery < 70 && battery >= 50) {
	        	batteryId += 3;
	        } else if(battery <= 100 && battery >= 70) {
	        	batteryId += 4;
	        } else {
	        	batteryId += 0;
	        }
	        Log.e(TAG, "---batteryStr---" +batteryId);
	        int resId = getResources().getIdentifier(batteryId, "drawable", packageName);
	        Log.e(TAG, "---resId---" +resId);
	        img_Battery.setImageResource(resId);
	    }
	}
	
	public void setTime(String time) {
		if(textViewTime != null) {
			textViewTime.setText(time);
		}
	}

	// 隐藏/显示
	private void toggleMediaControlsVisiblity() {
		if (isShowing()) {
			hide();
		} else {
			show();
		}
	}
	
	// 播放与暂停
	private void playOrPause() {
		if (videoView != null) {
			if (videoView.isPlaying()) {
				videoView.pause();
			} else {
				videoView.start();
			}
		}
	}
	
	private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
	    public boolean onSingleTapUp(MotionEvent e) {
	        return false;
	    }

	    @Override
	    public boolean onSingleTapConfirmed(MotionEvent e) {
	        //当收拾结束，并且是单击结束时，控制器隐藏/显示
	    	Log.e(TAG, "---onSingleTapConfirmed---");
	    	toggleMediaControlsVisiblity();
	        return super.onSingleTapConfirmed(e);
	    }

	    @Override
	    public boolean onDown(MotionEvent e) {
	        return true;
	    }

	    @Override
	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	    	 float mOldX = e1.getX(), mOldY = e1.getY();
	         int y = (int) e2.getRawY();
	         int x = (int) e2.getRawX();
	         int windowWidth = Utils.getScreenWidthPx(activity);
	         int windowHeight = Utils.getScreenHeightPx(activity);
	         if (mOldX > windowWidth * 3.0 / 4.0) {// 右边滑动 屏幕 3/4
//	             onVolumeSlide((mOldY - y) / windowHeight);
	         } else if (mOldX < windowWidth * 1.0 / 4.0) {// 左边滑动 屏幕 1/4
//	             onBrightnessSlide((mOldY - y) / windowHeight);
	         }
	        return super.onScroll(e1, e2, distanceX, distanceY);
	    }
	    
	    //双击暂停或开始
	    @Override
	    public boolean onDoubleTap(MotionEvent e) {
	    	Log.e(TAG, "---onDoubleTap---");
	    	playOrPause();
	    	return true;
	    }
	    
	}
	
	private void onVolumeSlide(float percent) {
	    if (mVolume == -1) {
	        mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	        if (mVolume < 0)
	            mVolume = 0;

	        // 显示
//	        mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
//	        mOperationTv.setVisibility(VISIBLE);
	    }

	    int index = (int) (percent * mMaxVolume) + mVolume;
	    if (index > mMaxVolume)
	        index = mMaxVolume;
	    else if (index < 0)
	        index = 0;
	    /*if (index >= 10) {
	        mOperationBg.setImageResource(R.drawable.volmn_100);
	    } else if (index >= 5 && index < 10) {
	        mOperationBg.setImageResource(R.drawable.volmn_60);
	    } else if (index > 0 && index < 5) {
	        mOperationBg.setImageResource(R.drawable.volmn_30);
	    } else {
	        mOperationBg.setImageResource(R.drawable.volmn_no);
	    }
	    //DecimalFormat    df   = new DecimalFormat("######0.00");
	    mOperationTv.setText((int) (((double) index / mMaxVolume)*100)+"%");*/
	    // 变更声音
	    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

	}
}

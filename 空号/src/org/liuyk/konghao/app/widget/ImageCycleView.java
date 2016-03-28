package org.liuyk.konghao.app.widget;

import java.util.ArrayList;
import java.util.List;

import org.liuyk.konghao.app.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ClickableViewAccessibility")
public class ImageCycleView extends LinearLayout {
	private Context mContext;
	private ViewPager mAdvPager = null;
	private ImageCycleAdapter mAdvAdapter;
	private ViewGroup mGroup;
	private ImageView mImageView = null;
	private ImageView[] mImageViews = null;
	private TextView advertising_title;

	public ImageCycleView(Context context) {
		super(context);
	}

	private ArrayList<String> index_title;

	@SuppressLint("NewApi")
	public ImageCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.image_cycle_view, this);
		mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
		advertising_title = (TextView) findViewById(R.id.advertising_title);
		mAdvPager.addOnPageChangeListener(new GuidePageChangeListener());
		mAdvPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					startImageTimerTask();
					break;
				default:
					stopImageTimerTask();
					break;
				}
				return false;
			}
		});
		index_title = new ArrayList<String>();
		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
	}

	/**
	 * @Title: setImageResources
	 * @author: anddevelopment@sina.com
	 * @throws
	 */
	public void setImageResources(List<String> list, ImageCycleViewListener imageCycleViewListener, List<String> title) {
		mGroup.removeAllViews();
		final int imageCount = list.size();
		mImageViews = new ImageView[imageCount];
		index_title.clear();
		index_title.addAll(title);
		for (int i = 0; i < imageCount; i++) {
			mImageView = new ImageView(mContext);
			int imageParame = (int) getResources().getDimension(R.dimen.DIMEN_30PX) / 2;
			LayoutParams layoutParams = new LayoutParams(imageParame, imageParame);
			layoutParams.setMargins(imageParame / 3, imageParame / 2, imageParame / 4, imageParame / 2);
			mImageView.setLayoutParams(layoutParams);
			mImageView.setPadding(imageParame, imageParame, imageParame, imageParame);
			mImageViews[i] = mImageView;
			mImageViews[i].setBackgroundResource(R.drawable.point_normal);
			mGroup.addView(mImageViews[i]);
		}
		mAdvAdapter = new ImageCycleAdapter(mContext, list,	imageCycleViewListener);
		mAdvPager.setAdapter(mAdvAdapter);
		
		//初始化默认选中的item
        int intiCount=10000/2;
        mAdvPager.setCurrentItem(intiCount);
        advertising_title.setText(index_title.get(intiCount % imageCount));
        mImageViews[intiCount % imageCount].setBackgroundResource(R.drawable.point_focused);
		startImageTimerTask();
	}

	public void startImageCycle() {
		startImageTimerTask();
	}

	public void pushImageCycle() {
		stopImageTimerTask();
	}

	private void startImageTimerTask() {
		stopImageTimerTask();
		mHandler.postDelayed(mImageTimerTask, 3000);
	}

	private void stopImageTimerTask() {
		mHandler.removeCallbacks(mImageTimerTask);
	}

	private Handler mHandler = new Handler();
	private Runnable mImageTimerTask = new Runnable() {
		@Override
		public void run() {
			//新增代码
			if (mImageViews != null) {
				mAdvPager.setCurrentItem(mAdvPager.getCurrentItem()+1);
			}
		}
	};

	private boolean isFirst = true;
	/**
     * 上一次被高亮显示的点
     */
    private int lastPointIndex;
	
	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE)
				startImageTimerTask();
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
			int newIndex = index % mImageViews.length;
            String string = index_title.get(newIndex);
            advertising_title.setText(string);
			// 当前下标点高亮
			mGroup.getChildAt(newIndex).setBackgroundResource(R.drawable.point_focused);
			mGroup.getChildAt(lastPointIndex).setBackgroundResource(R.drawable.point_normal);
			if (isFirst) {
				mGroup.getChildAt(newIndex).setBackgroundResource(R.drawable.point_focused);
			}
			isFirst = false;
			lastPointIndex = newIndex;
		}
	}
	

	private class ImageCycleAdapter extends PagerAdapter {
		private List<ImageView> mImageViewCacheList;
		private List<String> mAdList = new ArrayList<String>();
		private ImageCycleViewListener mImageCycleViewListener;
		private Context mContext;

		public ImageCycleAdapter(Context context, List<String> list, ImageCycleViewListener imageCycleViewListener) {
			mContext = context;
			mAdList = list;
			mImageCycleViewListener = imageCycleViewListener;
			mImageViewCacheList = new ArrayList<ImageView>();
		}

		@Override
		public int getCount() {
//			return mAdList.size();
			return 10000;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			String imageUrl = mAdList.get(position%mImageViews.length);
			ImageView imageView = null;
			if (mImageViewCacheList.isEmpty()) {
				imageView = new ImageView(mContext);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			} else {
				imageView = mImageViewCacheList.remove(0);
			}
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mImageCycleViewListener != null)
						mImageCycleViewListener.onImageClick(position % mImageViews.length, v);
				}
			});
			imageView.setTag(imageUrl);
			container.addView(imageView);
			if (mImageCycleViewListener != null)
				mImageCycleViewListener.displayImage(imageUrl, imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = (ImageView) object;
			container.removeView(view);
			mImageViewCacheList.add(view);
		}
	}

	public static interface ImageCycleViewListener {
		public void displayImage(String imageURL, ImageView imageView);

		public void onImageClick(int position, View imageView);

		public void onItemChanged(int position);

	}

}

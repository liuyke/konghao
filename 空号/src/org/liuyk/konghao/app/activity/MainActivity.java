package org.liuyk.konghao.app.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.app.adapter.NewestCategoriesAdpater;
import org.liuyk.konghao.app.base.AsyncNetWorkTask;
import org.liuyk.konghao.app.base.BaseActivity;
import org.liuyk.konghao.app.widget.ImageCycleView;
import org.liuyk.konghao.engine.DataAnalysizerFactory;
import org.liuyk.konghao.model.IndexPage;
import org.liuyk.konghao.model.VideoCategory;
import org.liuyk.konghao.model.VideoProcess;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	private Toast TOAST;
	private static final String TAG = "MainActivity";
	private TextView title;
	private ListView listView;
//	private ScrollView scrollView;
	private RelativeLayout titleView;
	private AsyncNetWorkTask<List<VideoProcess>> taskVideoProcess;
	private AsyncNetWorkTask<IndexPage> taskIndexPage;
	private ImageCycleView mImageCycleView;
	/**轮播图列表信息*/
	private List<VideoCategory> recycleImages;
//	private HorizontalListView horizontalListView;
//	private MainVideoCategoriesAdapter videoCategoriesAdapter;
	private NewestCategoriesAdpater newestCategoriesAdpater;
	/**最近更新专辑列表*/
	private List<VideoCategory> videoCategories;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initEvent();
		initDatas();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (taskVideoProcess != null) {
			taskVideoProcess.cancel();
		}
		if (taskIndexPage != null) {
			taskIndexPage.cancel();
		}
		if(TOAST != null) {
			TOAST.cancel();
			TOAST = null;
		}
	}
	
	private void initViews() {
		title = (TextView) findViewById(R.id.id_tv_title);
		listView = (ListView) findViewById(R.id.id_main_list_view);
//		scrollView = (ScrollView) findViewById(R.id.id_scroll_view);
		titleView = (RelativeLayout) findViewById(R.id.id_main_title_view);
//		mImageCycleView = (ImageCycleView) findViewById(R.id.id_main_imagecycleview);
		View headView = getLayoutInflater().inflate(R.layout.cycle_images, null);
		mImageCycleView = (ImageCycleView) headView.findViewById(R.id.id_main_imagecycleview);
		listView.addHeaderView(headView);
//		horizontalListView = (HorizontalListView) findViewById(R.id.id_main_horizontallistview);
		layoutTitleBar(titleView);
	}

	private void initEvent() {
		listView.setOnItemClickListener(this);
	}

	private void initDatas() {
		title.setText("孔浩学习分享空间");
		TextView righTextview = (TextView)findViewById(R.id.id_tv_title_right);
		righTextview.setText("缓存");
		righTextview.setBackgroundColor(Color.TRANSPARENT);
		righTextview.setOnClickListener(this);
//		scrollView.smoothScrollTo(0, 0);
		/*
		taskVideoProcess = new AsyncNetWorkTask<List<VideoProcess>>() {
			@Override
			protected List<VideoProcess> doInBackground() {
				return DataAnalysizerFactory.createAnalysizer().analysisVideoProcesses();
			}
			
			@Override
			protected void post(List<VideoProcess> t) {
				if (t != null && !t.isEmpty()) {
					MainVideoProcessAdapter adapter = new MainVideoProcessAdapter(getApplicationContext(), t);
					listView.setAdapter(adapter);
				}
			}
		}.excute();
		*/
		taskIndexPage = new AsyncNetWorkTask<IndexPage>() {
			@Override
			protected void post(IndexPage t) {
				if (t != null) {
					recycleImages = t.getRecycleImages();
					Log.d(TAG, Arrays.toString(recycleImages.toArray()));
					videoCategories = t.getVideoCategories();
					Log.d(TAG, Arrays.toString(videoCategories.toArray()));
					initCycleViewImages();
					initVideoCategoriesDatas();
				}
			}

			@Override
			protected IndexPage doInBackground() {
				return DataAnalysizerFactory.createAnalysizer().analysisIndex();
			}
		}.excute();
		
	}
	
	private void initVideoCategoriesDatas() {
		if(videoCategories == null || videoCategories.isEmpty()) {
			return;
		}
		/*
		if(videoCategoriesAdapter == null) {
//			videoCategoriesAdapter = new MainVideoCategoriesAdapter(this, videoCategories);
			videoCategoriesAdapter = new MainVideoCategoriesAdapter(getApplicationContext(), videoCategories);
			listView.setAdapter(videoCategoriesAdapter);
		} else {
			videoCategoriesAdapter.setDatas(videoCategories);
			videoCategoriesAdapter.notifyDataSetChanged();
		}
		*/
		if(newestCategoriesAdpater == null) {
			newestCategoriesAdpater = new NewestCategoriesAdpater(this, videoCategories);
			listView.setAdapter(newestCategoriesAdpater);
		} else {
			newestCategoriesAdpater.setDatas(videoCategories);
			newestCategoriesAdpater.notifyDataSetChanged();
		}
		
	}
	
	private void initCycleViewImages() {
		if (recycleImages == null || recycleImages.isEmpty()) {
			return;
		}
		List<String> imgUrls = new ArrayList<String>();
		List<String> titles = new ArrayList<String>();
		for (VideoCategory vi : recycleImages) {
			imgUrls.add(vi.getImg());
			titles.add(vi.getName());
		}
//			onItemChanged(0);
		mImageCycleView.setImageResources(imgUrls, imageCycleViewListener, titles);
	}
	
	private ImageCycleView.ImageCycleViewListener imageCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
		@Override
		public void onItemChanged(int position) {
			if (recycleImages == null || recycleImages.isEmpty() || position >= recycleImages.size()) {
				return;
			}
			VideoCategory videoCategory = recycleImages.get(position);
			Log.d("TAG-----", videoCategory.toString());
		}
		
		@Override
		public void onImageClick(int position, View imageView) {
			if (recycleImages == null || recycleImages.isEmpty() || position >= recycleImages.size()) {
				return;
			}
			VideoCategory videoCategory = recycleImages.get(position);
			Log.d("TAG", videoCategory.toString());
			VideoCategoryActivity.actionStart(MainActivity.this, videoCategory.getLink(), videoCategory.getName(), videoCategory.getImg());
		}
		
		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_tv_title_right://缓存
			CacheCategroyActivity.actionStart(this);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		VideoCategory videoCategory = newestCategoriesAdpater.getItem(position - 1);
		VideoCategoryActivity.actionStart(this, videoCategory.getLink(), videoCategory.getName(), videoCategory.getImg());
	}
	private long lastback;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(KeyEvent.KEYCODE_BACK == keyCode) {
//			new AlertDialog.Builder(this).setTitle(null).setCancelable(true).setMessage("确定退出孔浩视频APP？")
//			.setNeutralButton("退出", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					finish();
//					dialog.dismiss();
//				}
//			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//				}
//			}).show(); 
			if(System.currentTimeMillis() - lastback > 3000) {
				lastback = System.currentTimeMillis();
				TOAST = Toast.makeText(MainActivity.this, "再按一次返回退出应用", Toast.LENGTH_SHORT);
				TOAST.show();
				return true;
			}
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}

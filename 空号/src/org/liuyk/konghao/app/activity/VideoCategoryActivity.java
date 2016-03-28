package org.liuyk.konghao.app.activity;

import java.util.Date;
import java.util.List;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.app.adapter.VideoCategoryVideosAdapter;
import org.liuyk.konghao.app.base.AsyncNetWorkTask;
import org.liuyk.konghao.app.base.BaseActivity;
import org.liuyk.konghao.app.service.DownloadService;
import org.liuyk.konghao.db.Category;
import org.liuyk.konghao.db.Video;
import org.liuyk.konghao.engine.DataAnalysizerFactory;
import org.liuyk.konghao.model.VideoInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VideoCategoryActivity extends BaseActivity implements OnItemClickListener, OnClickListener {

	private RelativeLayout titleView;
	private ListView listView;
	private VideoCategoryVideosAdapter adapter;
	private String vcUrl, categoryName; 
	/**专辑图片*/
	private String categoryUrl;
	private AsyncNetWorkTask<List<VideoInfo>> task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_category);
		Intent intent = getIntent();
		vcUrl = intent.getStringExtra("url");
		categoryName = intent.getStringExtra("categoryName");
		categoryUrl = intent.getStringExtra("categoryUrl");
		initViews();
		initDatas();
	}

	public static void actionStart(Context context, String url, String categoryName, String categoryUrl) {
		Intent intent = new Intent(context, VideoCategoryActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("categoryName", categoryName);
		intent.putExtra("categoryUrl", categoryUrl);
		context.startActivity(intent);
	}
	
	private void initDatas() {
		task = new  AsyncNetWorkTask<List<VideoInfo>>() {
			@Override
			protected void post(List<VideoInfo> datas) {
				if(adapter == null) {
					adapter = new VideoCategoryVideosAdapter(VideoCategoryActivity.this, datas);
					listView.setAdapter(adapter);
				} else {
					adapter.setDatas(datas);
					adapter.notifyDataSetChanged();
				}
			}
			
			@Override
			protected List<VideoInfo> doInBackground() {
				return DataAnalysizerFactory.createAnalysizer().analysisVideos(vcUrl);
			}
		}.excute();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(task != null) {
			task.cancel();
		}
		handler.removeCallbacksAndMessages(null);
	}

	private void initViews() {
		listView = (ListView) findViewById(R.id.id_list_view);
		titleView = (RelativeLayout) findViewById(R.id.id_title_view);
		((TextView)titleView.findViewById(R.id.id_tv_title)).setText(categoryName);
		layoutTitleBar(titleView);
		TextView right = (TextView) titleView.findViewById(R.id.id_tv_title_right);
		right.setText(null);
		TextView left = (TextView) titleView.findViewById(R.id.id_tv_title_left);
		left.setText("返回");
		left.setOnClickListener(this);
		listView.setOnItemClickListener(this);
		
		listView.setEmptyView(getLayoutInflater().inflate(R.layout.empty, null));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		VideoInfo videoInfo = adapter.getItem(position);
		Log.d("liuyk", videoInfo.toString());
		final Video video = new Video(null, videoInfo.getLink(), videoInfo.getName(), 0L, 0L, 0, 0);
		Category category = new Category();
		category.setName(categoryName);
		category.setIntime(new Date());
		category.setUrl(categoryUrl);
//		DownloadService.addDownload(this, video, category);
		Toast.makeText(this, "正在解析播放链接，请稍候…", Toast.LENGTH_SHORT).show();
		new Thread(){
			@Override
			public void run() {
				String playUrl = DataAnalysizerFactory.createAnalysizer().analysisPlayUrl(video.getUrl());
				handler.obtainMessage(1, playUrl).sendToTarget();
			};
		}.start();
	}
	
	private final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			PlayerActivity.actionStart(VideoCategoryActivity.this, (String)msg.obj);
		};
	};
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_tv_title_left:
			finish();
			break;
		}
	}
	
}

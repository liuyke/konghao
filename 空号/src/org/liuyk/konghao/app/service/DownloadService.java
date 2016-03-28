package org.liuyk.konghao.app.service;

import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;
import org.liuyk.konghao.db.Category;
import org.liuyk.konghao.db.Video;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DownloadService extends Service {

	private static final String TAG = "DownloadService";
	private boolean downloading;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int command = -1;
		if(intent != null) {
			command = intent.getIntExtra("command", -1);
		}
		Log.d(TAG, "-----------command-----------" + command);
		switch (command) {
		case 0://添加
			//如果没有正在下载的视频，取出最新添加的一条video记录开始下载
			if(!downloading) {
				final Video video = Video.selectNext();
				if(video == null) {
					Log.e(TAG, "selectNext is null");
					return START_STICKY;
				}
				downloading = true;
				video.setState(1);//将状态更新为正在下载
				video.update(video.getId());

				DownloadTask task = new DownloadTask(video);
				task.setOnDownloadListener(new DownloadTask.DownloadListener() {
					@Override
					public void onProgressUpdate(long total, long finishLength) {
						video.setSize(finishLength);
						video.update(video.getId());
					}
					
					@Override
					public void onError(Exception e) {
						video.setState(0);
						video.update(video.getId());
					}

					@Override
					public void onFinish() {
						video.setState(3);
						video.update(video.getId());
						Log.e(TAG, "---------------onFinish---------------------" + video);
					}
				});
				Log.d(TAG, "-----------start downloading-----------" + video.getName());
				new Thread(task).start();
			}
			break;
		}
		return START_STICKY;
	}
	
	public static void addDownload(Context context, Video video, Category category) {
		List<Category> categories = (List<Category>) DataSupport.where("name=?", category.getName()).find(Category.class);
		if(categories == null || categories.isEmpty()) {
			category.setIntime(new Date());
			category.save();
			video.setCategory(category);
		} else {
			video.setCategory(categories.get(0));
		}
		video.setIntime(new Date());
		List<Video> find = Video.where("name=?", video.getName()).find(Video.class);
		if(find != null && !find.isEmpty()) {
			video = find.get(0);
			Log.d(TAG, "----------state:" + video);
			if(video.getState() == 1) {
				video.setState(0);
				video.update(video.getId());
			}
			Toast.makeText(context, "已经缓存过了！", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, video.save() ? "添加缓存成功" : "添加缓存失败", Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent(context, DownloadService.class);
		intent.putExtra("command", 0);
		context.startService(intent);
	}

}

package org.liuyk.konghao.app.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.liuyk.konghao.app.utils.Utils;
import org.liuyk.konghao.db.Video;
import org.liuyk.konghao.engine.DataAnalysizerFactory;

import android.text.TextUtils;
import android.util.Log;

public class DownloadTask implements Runnable {

	private Video video;
	private boolean isStop;
	private DownloadListener listener;
	
	public void setOnDownloadListener(DownloadListener listener) {
		this.listener = listener;
	}

	public DownloadTask(Video video) {
		super();
		this.video = video;
	}

	@Override
	public void run() {
		HttpURLConnection conn = null;
		InputStream is = null;
		RandomAccessFile accessFile = null;
		FileOutputStream os = null;
		try {
			String playUrl = DataAnalysizerFactory.createAnalysizer().analysisPlayUrl(video.getUrl());
			File videoFile = null;
			if(TextUtils.isEmpty(video.getPath())) {//首次下载
				long totalLen = getLength(playUrl);//视频总大小
				listener.onError(new RuntimeException("获取视频总大小错误"));
				if(totalLen == 0) return;
				video.setTotal(totalLen);
				videoFile = new File(Utils.getFileDir(), video.getName() + ".mp4");
				video.setPath(videoFile.getAbsolutePath());
				video.update(video.getId());
			}
			URL url = new URL(playUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept-Encoding", "identity"); //禁止gzip压缩
			File file = new File(video.getPath());
			long startPos = file.exists() ? file.length() : 0;
			long endPos = video.getTotal();
			if(startPos != 0) {
				conn.setRequestProperty("Range", "bytes=" + startPos + "-"+ endPos);//设置获取实体数据的范围
			}
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			is = conn.getInputStream();
			if(startPos != 0) {
				accessFile = new RandomAccessFile(video.getPath(), "rwd");
				accessFile.seek(startPos);
			} else {
				os = new FileOutputStream(video.getPath());
			}
			byte[] bytes = new byte[1024 * 10];
			int len = 0;
			long finish = startPos;
			if(startPos > 0) {
				while (!isStop && (len = is.read(bytes)) != -1) {
					accessFile.write(bytes, 0, len);
					finish += len;
					if(listener != null) {
						listener.onProgressUpdate(video.getTotal(), finish);
					}
				}
			} else {
				while (!isStop && (len = is.read(bytes)) != -1) {
					os.write(bytes, 0, len);
					finish += len;
					if(listener != null) {
						listener.onProgressUpdate(video.getTotal(), finish);
					}
				}
			}
			if(listener != null) {
				listener.onFinish();
			}
		} catch (MalformedURLException e) {
			Log.e("liuyk", "下载视频异常MalformedURLException", e);
			if(listener != null) {
				listener.onError(e);
			}
		} catch (IOException e) {
			Log.e("liuyk", "下载视频异常IOException", e);
			if(listener != null) {
				listener.onError(e);
			}
		} finally {
			if(conn != null) conn.disconnect();
			try {
				if(accessFile != null) accessFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(os != null) os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取文件大小
	 */
	private long getLength(String urlStr) {
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept-Encoding", "identity"); //禁止gzip压缩
            conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			return conn.getContentLength();
		} catch (MalformedURLException e) {
			Log.e("liuyk", "获取视频大小异常MalformedURLException", e);
			if(listener != null) {
				listener.onError(e);
			}
		} catch (IOException e) {
			Log.e("liuyk", "获取视频大小异常IOException", e);
			if(listener != null) {
				listener.onError(e);
			}
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) conn.disconnect();
		}
		return 0;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public interface DownloadListener {
		void onProgressUpdate(long total, long finishLength);
		void onError(Exception e);
		void onFinish();
	}
	
}

package org.liuyk.konghao.engine;

import java.util.List;

import org.jsoup.select.Elements;
import org.liuyk.konghao.model.IndexPage;
import org.liuyk.konghao.model.VideoCategory;
import org.liuyk.konghao.model.VideoInfo;
import org.liuyk.konghao.model.VideoProcess;

/**
 * 
 * 数据解析器的接口
 * @author liu_yunke@sina.cn
 *
 */
public interface DataAnalysizer {

	/**
	 * 解析首页
	 */
	public IndexPage analysisIndex();

	/**
	 * 解析轮播图
	 * 
	 * @param element
	 * @return
	 */
	public List<VideoCategory> analysisVideoCategorys(Elements element);

	/**
	 * 解析首页最近更新的视频专辑
	 * 
	 * @param element
	 * @return
	 */
	public List<VideoCategory> analysisLatestCategries(Elements element);

	/**
	 * 根据视频专辑获取视频信息列表
	 * 
	 * @param url
	 * @return
	 */
	public List<VideoInfo> analysisVideos(String url);

	/**
	 * 抓取视频播放地址
	 * 
	 * @param url
	 * @return
	 */
	public String analysisPlayUrl(String url);

	/**
	 * 抓取顺序教学视频列表
	 * @return
	 */
	public List<VideoProcess> analysisVideoProcesses();
	
}

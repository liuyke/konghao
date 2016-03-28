package org.liuyk.konghao.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * 下载需要的专辑数据库存储对象
 */
public class Category extends DataSupport {

	private Integer id;
	/**
	 * 专辑名称
	 */
	@Column(unique = true)
	private String name;
	/**
	 * 专辑图片地址
	 */
	private String url;
	private Date intime;
	
	/**
	 * 专辑中的视频数量
	 */
	@Column(ignore=true)
	private Integer total;
	
	/**
	 * 专辑中的已下载完成的视频数量
	 */
	@Column(ignore=true)
	private Integer finish;
	
	private List<Video> videos = new ArrayList<Video>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}
	
	public Integer getTotal() {
		return videos.size();
	}

	public Integer getFinish() {
		int count = 0;
		for (Video video : videos) {
			if(video.getState() == 3) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 获取专辑中所有视频的总大小
	 * @return
	 */
	public long getTotalSize() {
		long size = 0;
		for (Video video : videos) {
			size += video.getTotal();
		}
		return size;
	}
	
	/**
	 * 获取专辑中所有视频的总大小
	 * @return
	 */
	public long getFinishSize() {
		long size = 0;
		for (Video video : videos) {
			size += video.getSize();
		}
		return size;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", url=" + url
				+ ", intime=" + intime + ", total=" + total + ", finish="
				+ finish + ", videos=" + videos + "]";
	}
	
}
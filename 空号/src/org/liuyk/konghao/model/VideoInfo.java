package org.liuyk.konghao.model;

import java.io.Serializable;

public class VideoInfo implements Serializable, Comparable<VideoInfo> {

	private static final long serialVersionUID = -8893436950441228411L;

	private int seq;
	private String link;
	private String name;
	private Integer accessCount;
	private String size;
	private String updateTime;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Integer accessCount) {
		this.accessCount = accessCount;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "VideoInfo [seq=" + seq + ", link=" + link + ", name=" + name
				+ ", accessCount=" + accessCount + ", size=" + size
				+ ", updateTime=" + updateTime + "]";
	}

	@Override
	public int compareTo(VideoInfo another) {
		return seq - another.seq;
	}

}

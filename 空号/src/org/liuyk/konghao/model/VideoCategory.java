package org.liuyk.konghao.model;

import java.io.Serializable;

public class VideoCategory implements Serializable {

	private static final long serialVersionUID = -2579457571749051276L;

	private String link;
	private String img;
	private String name;
	private String updateTime;
	
	public VideoCategory() {
	}

	public VideoCategory(String link, String img, String name, String updateTime) {
		super();
		this.link = link;
		this.img = img;
		this.name = name;
		this.updateTime = updateTime;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "VideoCategory [link=" + link + ", img=" + img + ", name="
				+ name + ", updateTime=" + updateTime + "]";
	}

}

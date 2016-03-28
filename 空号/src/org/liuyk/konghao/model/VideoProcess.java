package org.liuyk.konghao.model;

import java.io.Serializable;

public class VideoProcess implements Serializable {

	private static final long serialVersionUID = 1910242999819793027L;

	private String link;
	private String name;
	private String image;
	private String intro;
	private String size;
	private String videoNum;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getVideoNum() {
		return videoNum;
	}

	public void setVideoNum(String videoNum) {
		this.videoNum = videoNum;
	}

	@Override
	public String toString() {
		return "VideoProcess [link=" + link + ", name=" + name + ", image="
				+ image + ", intro=" + intro + ", size=" + size + ", videoNum="
				+ videoNum + "]";
	}

}

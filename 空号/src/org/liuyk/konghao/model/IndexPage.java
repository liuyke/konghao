package org.liuyk.konghao.model;

import java.io.Serializable;
import java.util.List;

public class IndexPage implements Serializable {

	private static final long serialVersionUID = -3072851790248844004L;

	private List<VideoCategory> recycleImages;
	private List<VideoCategory> videoCategories;

	public List<VideoCategory> getRecycleImages() {
		return recycleImages;
	}

	public void setRecycleImages(List<VideoCategory> recycleImages) {
		this.recycleImages = recycleImages;
	}

	public List<VideoCategory> getVideoCategories() {
		return videoCategories;
	}

	public void setVideoCategories(List<VideoCategory> videoCategories) {
		this.videoCategories = videoCategories;
	}

	@Override
	public String toString() {
		return "IndexPage [recycleImages=" + recycleImages
				+ ", videoCategories=" + videoCategories + "]";
	}

}

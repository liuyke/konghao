package org.liuyk.konghao.db;

import java.io.Serializable;
import java.util.Date;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import android.database.Cursor;

/**
 * 视频下载的数据库存储对象
 */
public class Video extends DataSupport implements Serializable {

	private static final long serialVersionUID = -8316396491705920316L;

	private Integer id;
	/** 下载视频地址 */
	@Column(nullable = false)
	private String url;
	@Column(nullable = false)
	private String name;
	private Long total;
	/**
	 * 已经下载的大小
	 */
	private Long size;
	/**
	 * 是否完成，0未完成，1完成
	 */
	private int finish;
	/**
	 * 下载状态，0表示等待，1表示正在下载，2表示暂停下载，3表示下载完成
	 */
	@Column(defaultValue="0")
	private Integer state;
	/**
	 * 存储路径
	 */
	private String path;
	private Date intime;
	private Category category;

	public Video() {
	}
	
	public Video(Integer id, String url, String name, Long total, Long size, int finish, Integer state) {
		super();
		this.id = id;
		this.url = url;
		this.name = name;
		this.total = total;
		this.size = size;
		this.finish = finish;
		this.state = state;
	}

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

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}
	
	public Integer getState() {
		return state;
	}

	/**
	 * 下载状态，0表示等待，1表示正在下载，2表示暂停下载，3表示下载完成
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 查询下一个需要下载的视频对象
	 * @return
	 */
	public static Video selectNext() {
		Cursor cursor = null;
		try {
			cursor = DataSupport.findBySQL("select * from video where id=(select max(id) from video where state=0)");
			return convertByCursor(cursor);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}
	
	private static Video convertByCursor(Cursor cursor) {
		if(cursor.moveToFirst()) {
			Integer id = cursor.getInt(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String url = cursor.getString(cursor.getColumnIndex("url"));
			Long total = cursor.getLong(cursor.getColumnIndex("total"));
			Long size = cursor.getLong(cursor.getColumnIndex("size"));
			Integer finish = cursor.getInt(cursor.getColumnIndex("finish"));
			Integer state = cursor.getInt(cursor.getColumnIndex("state"));
			return new Video(id, url, name, total, size, finish, state);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", url=" + url + ", name=" + name
				+ ", total=" + total + ", size=" + size + ", finish=" + finish
				+ ", state=" + state + ", path=" + path + ", intime=" + intime
				+ "]";
	}

}

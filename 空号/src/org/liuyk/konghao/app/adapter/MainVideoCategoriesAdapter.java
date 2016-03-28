package org.liuyk.konghao.app.adapter;

import java.util.List;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.model.VideoCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MainVideoCategoriesAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<VideoCategory> datas;
	
	public MainVideoCategoriesAdapter(Context context, List<VideoCategory> videoCategories) {
		inflater = LayoutInflater.from(context);
		datas = videoCategories;
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public VideoCategory getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.item_categroy, null);
		}
		return convertView;
	}

	public void setDatas(List<VideoCategory> datas) {
		this.datas = datas;
	}

}

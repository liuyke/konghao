package org.liuyk.konghao.app.adapter;

import java.util.List;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.model.VideoCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class NewestCategoriesAdpater extends BaseAdapter {

	private List<VideoCategory> mDatas;
	private LayoutInflater inflater;
	
	public NewestCategoriesAdpater(Context context, List<VideoCategory> datas) {
		inflater = LayoutInflater.from(context);
		mDatas = datas;
	}
	
	public void setDatas(List<VideoCategory> mDatas) {
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public VideoCategory getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_newest_category, null);
			holder.img = (ImageView) convertView.findViewById(R.id.id_iv_category_img);
			holder.categoryName = (TextView) convertView.findViewById(R.id.id_tv_category_name);
			holder.updateTime = (TextView) convertView.findViewById(R.id.id_tv_updatetime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		VideoCategory videoCategory = getItem(position);
		ImageLoader.getInstance().displayImage(videoCategory.getImg(), holder.img);
		holder.updateTime.setText(videoCategory.getUpdateTime());
		holder.categoryName.setText(videoCategory.getName());
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
		TextView categoryName, updateTime;
	}
	
}

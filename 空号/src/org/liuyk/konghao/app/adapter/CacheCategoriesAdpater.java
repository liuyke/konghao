package org.liuyk.konghao.app.adapter;

import java.util.List;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.db.Category;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CacheCategoriesAdpater extends BaseAdapter {

	private List<Category> mDatas;
	private LayoutInflater inflater;
	
	public CacheCategoriesAdpater(Context context, List<Category> datas) {
		inflater = LayoutInflater.from(context);
		mDatas = datas;
	}
	
	public void setDatas(List<Category> mDatas) {
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public Category getItem(int position) {
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
			convertView = inflater.inflate(R.layout.item_cache_category, null);
			holder.img = (ImageView) convertView.findViewById(R.id.id_iv_category_img);
			holder.categoryName = (TextView) convertView.findViewById(R.id.id_tv_category_name);
			holder.videoCount = (TextView) convertView.findViewById(R.id.id_tv_video_count);
			holder.categroySize = (TextView) convertView.findViewById(R.id.id_tv_category_size);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Category category = getItem(position);
		ImageLoader.getInstance().displayImage(category.getUrl(), holder.img);
		holder.videoCount.setText("视频 " + category.getVideos().size());
		holder.categoryName.setText(category.getName());
		holder.categroySize.setText(category.getFinishSize() / 1024 / 1024 + "MB");
		return convertView;
	}

	static class ViewHolder {
		ImageView img;
		TextView categoryName, videoCount, categroySize;
	}
	
}

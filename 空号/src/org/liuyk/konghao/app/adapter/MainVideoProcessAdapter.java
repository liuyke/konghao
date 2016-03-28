package org.liuyk.konghao.app.adapter;

import java.util.List;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.model.VideoProcess;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainVideoProcessAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<VideoProcess> mDatas;
	
	public MainVideoProcessAdapter(Context context, List<VideoProcess> videoCategories) {
		inflater = LayoutInflater.from(context);
		mDatas = videoCategories;
	}
	
	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public VideoProcess getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_main, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.id_mian_item_image);
			holder.videoNum = (TextView) convertView.findViewById(R.id.id_main_item_videonum);
			holder.size = (TextView) convertView.findViewById(R.id.id_main_item_size);
			holder.name = (TextView) convertView.findViewById(R.id.id_main_item_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		layoutData2ItemView(holder, getItem(position));
		return convertView;
	}
	
	private void layoutData2ItemView(ViewHolder holder, VideoProcess videoProcess) {
		holder.image.setImageResource(R.drawable.ic_launcher);
		ImageLoader.getInstance().displayImage(videoProcess.getImage(), holder.image);
		holder.videoNum.setText("共" + videoProcess.getVideoNum() + "个");
		holder.size.setText("约" + videoProcess.getSize());
		holder.name.setText(videoProcess.getName());
	}

	class ViewHolder {
		ImageView image;
		TextView videoNum;
		TextView size;
		TextView name;
	}
}

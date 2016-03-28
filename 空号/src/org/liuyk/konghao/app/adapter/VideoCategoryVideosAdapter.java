package org.liuyk.konghao.app.adapter;

import java.util.List;

import org.liuyk.konghao.app.R;
import org.liuyk.konghao.model.VideoInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VideoCategoryVideosAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<VideoInfo> mDatas;
	
	public VideoCategoryVideosAdapter(Context context, List<VideoInfo> datas) {
		inflater = LayoutInflater.from(context);
		mDatas = datas;
	}
	
	public void setDatas(List<VideoInfo> mDatas) {
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public VideoInfo getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		VideoInfo videoInfo = getItem(position);
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_videoinfo, null);
			holder.videoName = (TextView) convertView.findViewById(R.id.id_video_name);
			holder.accessNum = (TextView) convertView.findViewById(R.id.id_access_num);
			holder.size = (TextView) convertView.findViewById(R.id.id_size);
			holder.updateTime = (TextView) convertView.findViewById(R.id.id_update_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int seq = videoInfo.getSeq();
		holder.videoName.setText((seq < 10 ? "0" + seq : seq ) + " " + videoInfo.getName());
		holder.accessNum.setText("访问次数：" + videoInfo.getAccessCount());
		holder.size.setText("大小：" + videoInfo.getSize());
		holder.updateTime.setText("更新时间：" + videoInfo.getUpdateTime());
		return convertView;
	}
	
	static class ViewHolder {
		TextView videoName, accessNum, size, updateTime;
	}

}

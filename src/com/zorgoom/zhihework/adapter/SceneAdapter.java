package com.zorgoom.zhihework.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.smart.SenceInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 场景适配器
 */

public class SceneAdapter extends BaseAdapter {
	Context context;
	List<SenceInfo> deviceInfos;

	public SceneAdapter(Context context, List<SenceInfo> deviceInfos) {
		super();
		this.context = context;
		this.deviceInfos = deviceInfos;
	}

	public void setDeviceInfos(ArrayList<SenceInfo> deviceInfos) {
		this.deviceInfos = deviceInfos;
	}

	@Override
	public int getCount() { 
		return deviceInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return deviceInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.smart_home_scene_item, null);
			holder = new ViewHolder();
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SenceInfo deviceInfo = deviceInfos.get(position);

		if (deviceInfo != null) {
			holder.tv_title.setText(deviceInfo.getSenceName());
			switch (deviceInfo.getSenceId()) {
			case 0:// 全关
				holder.image.setBackgroundResource(R.drawable.home_jj_02);
				break;
			case 1:// 全开
				holder.image.setBackgroundResource(R.drawable.home_jj_01);

				break;
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv_title;
		ImageView image;
	}
}

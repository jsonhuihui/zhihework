package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.RsOpenDoorRecord.RsOpenDoorRecordData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OpenDoorRecordAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<RsOpenDoorRecordData> list;

	public OpenDoorRecordAdapter(Context context, List<RsOpenDoorRecordData> list2) {
		this.list = list2;
		// 布局填充图
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 显示的数据
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// VO对象用于设置项的值
		RsOpenDoorRecordData infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.open_door_record_item, null);
			blurb.title = (TextView) convertView.findViewById(R.id.title);
			blurb.time = (TextView) convertView.findViewById(R.id.time);

			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		switch (infoVO.getTYPE()) {
		case "S":
			blurb.title.setText(infoVO.getUSERID() + "——" + "刷卡开门");

			break;
		case "A":
			blurb.title.setText(infoVO.getUSERID() + "——" + "手机开门");
			break;
		case "M":
			blurb.title.setText(infoVO.getUSERID() + "——" + "访客密码开门");
			break;
		case "N":
			blurb.title.setText(infoVO.getUSERID() + "——" + "业主密码");
			break;
		case "D":
			blurb.title.setText(infoVO.getUSERID() + "——" + "室内机开门");
			break;

		default:
			break;
		}
		return convertView;
	}

	class Blurb {
		TextView title;
		TextView time;
	}

	public void setDate(List<RsOpenDoorRecordData> data) {
		this.list = data;
		notifyDataSetChanged();
	}
}

package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.RsAttenceDetail.RsAttenceDetailData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AttenceDetailAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<RsAttenceDetailData> list;

	public AttenceDetailAdapter(Context context, List<RsAttenceDetailData> list2) {
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
		RsAttenceDetailData infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.attencedetail_item, null);
			blurb.date = (TextView) convertView.findViewById(R.id.date);
			blurb.time1 = (TextView) convertView.findViewById(R.id.time1);
			blurb.time2 = (TextView) convertView.findViewById(R.id.time2);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.date.setText(infoVO.getDATE());
		blurb.time1.setText(infoVO.getWORKTIME());
		blurb.time2.setText(infoVO.getENDTIME());
		return convertView;
	}

	class Blurb {
		TextView date;
		TextView time1;
		TextView time2;
	}

	public void setDate(List<RsAttenceDetailData> data) {
		this.list = data;
		notifyDataSetChanged();
	}
}

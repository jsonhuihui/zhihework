package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.MaintainListment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MaintainListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<MaintainListment> list;
	private Context mContext;

	public MaintainListAdapter(Context context, List<MaintainListment> list2) {
		this.mContext = context;
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
		MaintainListment infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.message_item, null);
			blurb.title = (TextView) convertView.findViewById(R.id.title);
			blurb.date = (TextView) convertView.findViewById(R.id.date);
			blurb.content = (TextView) convertView.findViewById(R.id.content);
			blurb.state = (TextView) convertView.findViewById(R.id.state);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.title.setText(infoVO.getTROUBLETITLE());// 内容
		blurb.date.setText(infoVO.getCREDATE());// 日期
		blurb.content.setText(infoVO.getREMARK());// 日期
		String state = "";
		switch (infoVO.getSTATE()) {
		case "N":
			state = "新故障";
			break;
		case "S":
			state = "受理";
			break;
		case "W":
			state = "安排人员";
			break;
		case "D":
			state = "解决";
			break;
		}
		blurb.state.setText(state);
		return convertView;
	}

	class Blurb {
		TextView title;
		TextView date;
		TextView content;
		TextView state;
	}

}

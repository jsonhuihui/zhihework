package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.RsStallApplyVO.StallApplyVO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StallApplyAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<StallApplyVO> list;

	public StallApplyAdapter(Context context, List<StallApplyVO> list2) {
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
		StallApplyVO infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.stall_apply_item, null);
			blurb.title = (TextView) convertView.findViewById(R.id.title);
			blurb.date = (TextView) convertView.findViewById(R.id.date);
			blurb.content = (TextView) convertView.findViewById(R.id.content);
			blurb.state = (TextView) convertView.findViewById(R.id.state);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.title.setText("申请车牌：" + infoVO.getCARNO());// 内容
		blurb.date.setText(infoVO.getCREDATE());
		if (null != infoVO.getMEMO()) {
			blurb.content.setText(infoVO.getMEMO());
		}
		blurb.state.setText(infoVO.getSTATUS_DSC());
		return convertView;
	}

	class Blurb {
		TextView title;
		TextView date;
		TextView content;
		TextView state;
	}

}

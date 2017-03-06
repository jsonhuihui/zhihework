package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.RsFeedBackVO.FeedBackVO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FeedBackAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<FeedBackVO> list;
	private Context mContext;

	public FeedBackAdapter(Context context, List<FeedBackVO> list2) {
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
		FeedBackVO infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.feed_back_item, null);
			blurb.title = (TextView) convertView.findViewById(R.id.title);
			blurb.date = (TextView) convertView.findViewById(R.id.date);
			blurb.content = (TextView) convertView.findViewById(R.id.content);
			blurb.state = (TextView) convertView.findViewById(R.id.state);
			blurb.type = (TextView) convertView.findViewById(R.id.type);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.title.setText(infoVO.getMEMO());// 内容
		blurb.date.setText(infoVO.getCREDATE());// 日期
		blurb.type.setText("（" + infoVO.getTYPE_DSC() + "）");
		blurb.state.setText(infoVO.getSTATUS_DSC());
		return convertView;
	}

	class Blurb {
		TextView title;
		TextView date;
		TextView content;
		TextView type;
		TextView state;
	}

}

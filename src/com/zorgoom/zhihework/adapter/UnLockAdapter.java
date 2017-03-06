package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.RsUsersKeysListResultVO.UsersKeys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UnLockAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<UsersKeys> list;

	public UnLockAdapter(Context context, List<UsersKeys> list2) {
		this.list = list2;

		// 布局填充图
		inflater = LayoutInflater.from(context);
	}

	public void setList(List<UsersKeys> list) {
		this.list = list;
		notifyDataSetChanged();
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
		UsersKeys infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.unlockgrid_item, null);
			blurb.iView = (ImageView) convertView.findViewById(R.id.unlock_image);
			blurb.state = (TextView) convertView.findViewById(R.id.state);
			blurb.textKey = (TextView) convertView.findViewById(R.id.unlock_key);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}

		blurb.textKey.setText(infoVO.getLOCKNAME());// 内容
		if (infoVO.getState() == 0) {
			blurb.state.setVisibility(View.GONE);
			blurb.iView.setBackgroundResource(R.drawable.key4);
		} else {
			blurb.state.setVisibility(View.VISIBLE);
			blurb.iView.setBackgroundResource(R.drawable.key7);
		}
		return convertView;
	}

	class Blurb {
		ImageView iView;
		TextView textKey;// 日期
		TextView state;
	}

}

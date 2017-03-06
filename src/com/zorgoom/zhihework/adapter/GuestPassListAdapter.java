package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.GuestPassActivity;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.RsGuestPass.GuestPass;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GuestPassListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<GuestPass> list;
	private GuestPassActivity mContext;
	private Intent intent;

	public GuestPassListAdapter(GuestPassActivity context, Intent intent, List<GuestPass> list2) {
		this.mContext = context;
		this.intent = intent;
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
		final GuestPass infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.guestpass_item, null);
			blurb.title = (TextView) convertView.findViewById(R.id.title);
			blurb.date = (TextView) convertView.findViewById(R.id.date);
			blurb.name = (TextView) convertView.findViewById(R.id.name);
			blurb.time = (TextView) convertView.findViewById(R.id.time);
			blurb.share = (TextView) convertView.findViewById(R.id.share);
			blurb.tv_img = (ImageView) convertView.findViewById(R.id.tv_img);
			blurb.cancel = (TextView) convertView.findViewById(R.id.cancel);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.title.setText("访客密码：" + infoVO.getPASSWORD());// 内容
		blurb.name.setText("我的访客：" + infoVO.getPHONE());// 日期
		blurb.date.setText("有效时间：" + infoVO.getENDDATE());// 日期
		blurb.time.setText("" + infoVO.getCREDATE());// 日期
		blurb.cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mContext.delete(infoVO.getRID());
			}
		});
		blurb.share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("image/*");
				intent.putExtra(Intent.EXTRA_SUBJECT, "访客通行");
				intent.putExtra(Intent.EXTRA_TEXT, "访客密码：" + infoVO.getPASSWORD());
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(Intent.createChooser(intent, "分享"));

			}
		});
		return convertView;
	}

	class Blurb {
		TextView title;
		TextView date;
		TextView name;
		ImageView tv_img;
		TextView time;
		TextView share;
		TextView cancel;
	}

}

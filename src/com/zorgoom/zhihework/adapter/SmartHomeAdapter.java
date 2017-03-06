package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsPayment.Payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SmartHomeAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Payment> list;
	private Context testFragment;
	private String[] strings = new String[8];
	private int[] icons = new int[8];

	public SmartHomeAdapter(Context testFragment) {
		// 布局填充图
		inflater = LayoutInflater.from(testFragment);
		this.testFragment = testFragment;

		strings[0] = "烟雾";
		strings[1] = "红外";
		strings[2] = "门磁";
		strings[3] = "烟雾";
		strings[4] = "红外";
		strings[5] = "热感";
		strings[6] = "门磁";
		strings[7] = "紧急按钮";

		icons[0] = R.drawable.defence_42x;
		icons[1] = R.drawable.defence_12x;
		icons[2] = R.drawable.defence_22x;
		icons[3] = R.drawable.defence_52x;
		icons[4] = R.drawable.defence_42x;
		icons[5] = R.drawable.defence_12x;
		icons[6] = R.drawable.defence_22x;
		icons[7] = R.drawable.defence_62x;
	}

	@Override
	public int getCount() {
		return 8;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// VO对象用于设置项的值
		// final Payment infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.smart_home_list_item, null);
			blurb.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
			blurb.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.iv_img.setBackgroundResource(icons[position]);
		blurb.tv_title.setText(strings[position]);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(testFragment, BillDetails.class);
				// intent.putExtra("uuid", infoVO.getUUID());
				// intent.putExtra("price", infoVO.getBILLTOTAL() + "");
				// testFragment.startActivity(intent);
				ToastUtil.showMessage(testFragment, strings[position]);
			}
		});
		return convertView;
	}

	class Blurb {
		ImageView iv_img;
		TextView tv_title;
	}

}

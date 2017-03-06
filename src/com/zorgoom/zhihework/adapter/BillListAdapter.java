package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.BillDetails;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.RsPayment.Payment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BillListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Payment> list;
	private Context testFragment;
	private int index;

	public BillListAdapter(Context testFragment, List<Payment> nlist, int i) {
		this.list = nlist;
		// 布局填充图
		inflater = LayoutInflater.from(testFragment);
		this.testFragment = testFragment;
		this.index = i;
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
		final Payment infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.bill_list_item, null);
			blurb.title = (TextView) convertView.findViewById(R.id.title);
			blurb.price = (TextView) convertView.findViewById(R.id.price);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.title.setText(infoVO.getBILLDATE());// 内容
		blurb.price.setText("￥" + infoVO.getBILLTOTAL());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(testFragment, BillDetails.class);
				intent.putExtra("uuid", infoVO.getUUID());
				intent.putExtra("index", index);
				intent.putExtra("price", infoVO.getBILLTOTAL() + "");
				testFragment.startActivity(intent);
			}
		});
		return convertView;
	}

	class Blurb {
		TextView title;
		TextView price;
	}

}

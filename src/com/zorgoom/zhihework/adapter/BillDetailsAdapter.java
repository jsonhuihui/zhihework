package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.RsPaymentDetail.PaymentDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BillDetailsAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<PaymentDetail> list;
	private Context mContext;

	public BillDetailsAdapter(Context context, List<PaymentDetail> list2) {

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
		PaymentDetail infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = inflater.inflate(R.layout.bill_details_item, null);
			blurb.name = (TextView) convertView.findViewById(R.id.name);
			blurb.feilv = (TextView) convertView.findViewById(R.id.feilv);
			blurb.count = (TextView) convertView.findViewById(R.id.count);
			blurb.price = (TextView) convertView.findViewById(R.id.price);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}

		blurb.name.setText(infoVO.getFEENAME());// 内容
		blurb.feilv.setText(infoVO.getFEERATE() + "");// 日期
		blurb.count.setText((int) infoVO.getQUANTITY() + "");// 日期
		blurb.price.setText(infoVO.getFEETOTAL() + "");// 日期

		return convertView;
	}

	class Blurb {
		TextView name;// 内容
		TextView feilv;// 日期
		TextView count;// 日期
		TextView price;// 日期
	}

}

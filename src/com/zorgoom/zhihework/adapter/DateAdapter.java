package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DateAdapter extends BaseAdapter {

	Context context;
	private LayoutInflater layoutInflater;
	private List<String> carts;

	public DateAdapter(Context context, List<String> dates) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.carts = dates;
	}

	@Override
	public int getCount() {
		return carts.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = layoutInflater.inflate(R.layout.sale_date_item, null);
			blurb.date = (TextView) convertView.findViewById(R.id.date);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.date.setText(carts.get(position));
		return convertView;
	}

	class Blurb {
		TextView date;
	}

}

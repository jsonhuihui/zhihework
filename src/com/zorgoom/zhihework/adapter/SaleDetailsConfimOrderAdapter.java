package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.base.db.entity.ShopsCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SaleDetailsConfimOrderAdapter extends BaseAdapter {

	Context context;
	View.OnClickListener cliker;
	private LayoutInflater layoutInflater;
	private List<ShopsCart> carts;

	public SaleDetailsConfimOrderAdapter(Context context, View.OnClickListener clicker, List<ShopsCart> carts) {
		this.context = context;
		this.carts = carts;
		layoutInflater = LayoutInflater.from(context);
		this.cliker = clicker;
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
			convertView = layoutInflater.inflate(R.layout.sale_details_confirm_order_item, null);
			blurb.name = (TextView) convertView.findViewById(R.id.name);
			blurb.num = (TextView) convertView.findViewById(R.id.num);
			blurb.price = (TextView) convertView.findViewById(R.id.price);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		ShopsCart shopsCart = carts.get(position);
		blurb.name.setText(shopsCart.getName());
		blurb.num.setText("x" + shopsCart.getNum());
		blurb.price.setText("￥" + shopsCart.getPrice());
		return convertView;
	}

	class Blurb {
		TextView name, num, price;
	}
}

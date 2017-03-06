package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.base.db.entity.ShopsCart;
import com.zorgoom.zhihework.popuwindow.CartPopuWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SaleCartAdapter extends BaseAdapter {

	Context context;
	CartPopuWindow cartPopuWindow;
	private LayoutInflater layoutInflater;
	private List<ShopsCart> carts;

	public SaleCartAdapter(Context context, CartPopuWindow cartPopuWindow, List<ShopsCart> carts) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.cartPopuWindow = cartPopuWindow;
		this.carts = carts;
	}

	public void setDATE(List<ShopsCart> carts) {
		this.carts = carts;
		notifyDataSetChanged();

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
		ShopsCart shopsCart = carts.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = layoutInflater.inflate(R.layout.sale_cart_item, null);
			blurb.name = (TextView) convertView.findViewById(R.id.name);
			blurb.price = (TextView) convertView.findViewById(R.id.price);
			blurb.jian = (ImageView) convertView.findViewById(R.id.jian);
			blurb.number = (TextView) convertView.findViewById(R.id.number);
			blurb.jia = (ImageView) convertView.findViewById(R.id.jia);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.name.setText(shopsCart.getName());
		blurb.price.setText("￥" + shopsCart.getPrice());
		blurb.number.setText(shopsCart.getNum() + "");
		blurb.jia.setOnClickListener(new MyOnClickListener(shopsCart, blurb.number));
		blurb.jian.setOnClickListener(new MyOnClickListener(shopsCart, blurb.number));
		return convertView;
	}

	class MyOnClickListener implements OnClickListener {
		private TextView number;
		private ShopsCart shopsCart;

		public MyOnClickListener(ShopsCart shopsCart, TextView number) {
			this.shopsCart = shopsCart;
			this.number = number;
		}

		@Override
		public void onClick(View v) {
			int num = Integer.parseInt(number.getText().toString());
			switch (v.getId()) {
			case R.id.jia:
				num++;
				cartPopuWindow.requestCart(num, 1, shopsCart.getGid(), shopsCart.getName(), shopsCart.getPrice());
				break;
			case R.id.jian:
				num--;
				cartPopuWindow.requestCart(num, 0, shopsCart.getGid(), shopsCart.getName(), shopsCart.getPrice());
				break;
			}
		}
	}

	class Blurb {
		TextView name;
		TextView price;
		ImageView jian;
		TextView number;
		ImageView jia;
	}

}

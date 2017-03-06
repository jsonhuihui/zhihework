package com.zorgoom.zhihework.popuwindow;

import java.util.List;


import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.SaleDetailsActivity;
import com.zorgoom.zhihework.SaleDetailsConfirmOrderActivity;
import com.zorgoom.zhihework.SaleDetailsGoodActivity;
import com.zorgoom.zhihework.adapter.SaleCartAdapter;
import com.zorgoom.zhihework.base.db.dao.ShopsCartDao;
import com.zorgoom.zhihework.base.db.entity.ShopsCart;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * 购物车popu
 * 
 * @author hx
 * @param <MusicPlayer>
 * 
 */
public class CartPopuWindow extends PopupWindow implements OnClickListener, AdapterView.OnItemClickListener {

	private SaleDetailsActivity mContext;
	private SaleDetailsGoodActivity m;
	private View view;
	private LinearLayout linearLayout01;
	private ListView list_cart;
	private int sHOPID;
	private ShopsCartDao shopsCartDao;
	private TextView distribution_price;
	private List<ShopsCart> carts;
	private String nAME;
	private TextView name;

	public CartPopuWindow(SaleDetailsGoodActivity mContext, View parent, int sHOPID, String nAME) {
		this.m = mContext;
		view = View.inflate(mContext, R.layout.cart_popwindow, null);
		setContentView(view);
		setHeight(LayoutParams.FILL_PARENT);
		setWidth(LayoutParams.FILL_PARENT);
		setFocusable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		this.sHOPID = sHOPID;
		this.nAME = nAME;
		shopsCartDao = new ShopsCartDao(mContext);
		initView();
	}

	public CartPopuWindow(SaleDetailsActivity mContext, View parent, int sHOPID, String nAME) {
		this.mContext = mContext;
		view = View.inflate(mContext, R.layout.cart_popwindow, null);
		setContentView(view);
		setHeight(LayoutParams.FILL_PARENT);
		setWidth(LayoutParams.FILL_PARENT);
		setFocusable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		this.sHOPID = sHOPID;
		this.nAME = nAME;
		shopsCartDao = new ShopsCartDao(mContext);
		initView();
	}

	private SaleCartAdapter adapter;

	private void initView() {
		name = (TextView) view.findViewById(R.id.name);
		view.findViewById(R.id.delete).setOnClickListener(this);
		name.setText(nAME);
		distribution_price = (TextView) view.findViewById(R.id.distribution_price);
		linearLayout01 = (LinearLayout) view.findViewById(R.id.linearLayout01);
		linearLayout01.setOnClickListener(this);
		view.findViewById(R.id.distribution_iv).setOnClickListener(this);
		list_cart = (ListView) view.findViewById(R.id.list_cart);
		list_cart.setOnItemClickListener(this);
		carts = shopsCartDao.queryBySidGoodAll(sHOPID);
		double price = 0;
		for (ShopsCart s : carts) {
			price += (Double.parseDouble(s.getPrice()) * s.getNum());
		}
		distribution_price.setText("￥" + price);
		if (mContext != null) {
			adapter = new SaleCartAdapter(mContext, CartPopuWindow.this, carts);
		} else {
			adapter = new SaleCartAdapter(m, CartPopuWindow.this, carts);
		}

		list_cart.setAdapter(adapter);
	}

	public void requestCart(int num, int type, int gid, String name, String price) {
		shopsCartDao.update(sHOPID, gid, name, price, num);
		carts.clear();
		carts = shopsCartDao.queryBySidGoodAll(sHOPID);
		if (null != mContext) {
			mContext.refreshData();
		}else{
			m.refreshData();
		}
		if (carts.size() == 0) {
			dismiss();
			return;
		}
		adapter.setDATE(carts);
		double price1 = 0;
		for (ShopsCart s : carts) {
			price1 += (Double.parseDouble(s.getPrice()) * s.getNum());
		}
		distribution_price.setText("￥" + price1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayout01:
			dismiss();
			break;
		case R.id.delete:
			shopsCartDao.deleteShops(sHOPID);
			mContext.refreshData();
			dismiss();
			break;
		case R.id.distribution_iv:
			Context context = null;
			if (mContext == null) {
				context = m;
			} else {
				context = mContext;
			}
			Intent intent = new Intent(context, SaleDetailsConfirmOrderActivity.class);
			intent.putExtra("shopid", sHOPID);
			intent.putExtra("NAME", nAME);
			context.startActivity(intent);
			break;
		}
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	}

}
package com.zorgoom.zhihework.popuwindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.SaleDetailsConfirmOrderActivity;
import com.zorgoom.zhihework.adapter.DateAdapter;

import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * 
 * 购物车popu
 * 
 * @author hx
 * @param <MusicPlayer>
 * 
 */
public class DatePopuWindow extends PopupWindow implements OnClickListener, AdapterView.OnItemClickListener {

	private SaleDetailsConfirmOrderActivity mContext;
	private View view;
	private LinearLayout linearLayout01;
	private ListView list_cart;
	private List<String> dates = new ArrayList<String>();

	public DatePopuWindow(SaleDetailsConfirmOrderActivity mContext, View parent) {
		this.mContext = mContext;
		view = View.inflate(mContext, R.layout.date_popwindow, null);
		setContentView(view);
		setHeight(LayoutParams.FILL_PARENT);
		setWidth(LayoutParams.FILL_PARENT);
		setFocusable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		initView();
	}

	private DateAdapter adapter;

	private void initView() {
		linearLayout01 = (LinearLayout) view.findViewById(R.id.linearLayout01);
		linearLayout01.setOnClickListener(this);
		list_cart = (ListView) view.findViewById(R.id.list_cart);
		list_cart.setOnItemClickListener(this);

		view.findViewById(R.id.canenl).setOnClickListener(this);

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String date = df.format(new Date());
		int hh = Integer.parseInt(date.split(":")[0]);
		for (int i = 0; i < 5; i++) {
			hh++;
			dates.add(hh + "：00");
		}
		if (mContext != null) {
			adapter = new DateAdapter(mContext, dates);
		}
		list_cart.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayout01:
		case R.id.canenl:
			dismiss();
			break;

		}
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		mContext.refreshData(dates.get(position));
		dismiss();
	}

}
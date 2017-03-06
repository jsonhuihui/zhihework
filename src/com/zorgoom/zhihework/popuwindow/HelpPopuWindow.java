package com.zorgoom.zhihework.popuwindow;


import com.zorgoom.zhihework.R;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * 
 * 使用指南popu
 * 
 * @author hx
 * @param <MusicPlayer>
 * 
 */
public class HelpPopuWindow extends PopupWindow implements OnClickListener, AdapterView.OnItemClickListener {

	private Context m;
	private View view;
	private LinearLayout linearLayout01;

	public HelpPopuWindow(Context mContext, View parent) {
		this.m = mContext;
		view = View.inflate(mContext, R.layout.help_popwindow, null);
		setContentView(view);
		setHeight(LayoutParams.FILL_PARENT);
		setWidth(LayoutParams.FILL_PARENT);
		setFocusable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		showAtLocation(parent, Gravity.CENTER, 0, 0);
		initView();
	}



	private void initView() {
		linearLayout01 = (LinearLayout) view.findViewById(R.id.linearLayout01);
		linearLayout01.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayout01:
			dismiss();
			break;
		}
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
	}

}
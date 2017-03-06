package com.zorgoom.zhihework.popuwindow;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.adapter.ImageLoadUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.zhihework.vo.ReAdVO;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 
 */
public class Main_AdPopuWindow extends PopupWindow implements OnClickListener, HttpListener, OnDismissListener {

	private Context mContext;
	private View view;
	private ImageView linearLayout01;
	private C2BHttpRequest c2BHttpRequest;

	public Main_AdPopuWindow(Activity mContext, View parent, int hei) {
		this.mContext = mContext;
		view = View.inflate(mContext, R.layout.ad_popwindow, null);
		setContentView(view);
		setHeight(hei);
		setWidth(LayoutParams.FILL_PARENT);
		setFocusable(true);
		setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		setAnimationStyle(R.style.mypopwindow_anim_style1);
		showAtLocation(parent, Gravity.TOP, 0, 0);
		initView();
		c2BHttpRequest = new C2BHttpRequest(mContext, this);

		String OPERID = PrefrenceUtils.getStringUser("OPERID", mContext);
		// 获取广告
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(OPERID + "", timestamp);

		c2BHttpRequest.getHttpResponse(
				Http.GETADBYPOSITION + "OPERID=" + OPERID + "&COVERS=C" + "&FKEY=" + key + "&TIMESTAMP=" + timestamp,
				4);
		setOnDismissListener(this);
	}

	private void initView() {

		linearLayout01 = (ImageView) view.findViewById(R.id.linearLayout01);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayout01:
			dismiss();
			if (null != adPopuWindow) {
				adPopuWindow.dismiss();
			}
			break;
		}
	}

	private Object[] imageLoadObj;// 图片加载对象

	@Override
	public void OnResponse(String result, int reqId) {
		BaseModel baseModel1 = DataPaser.json2Bean(result, BaseModel.class);
		if (null != baseModel1) {
			if (baseModel1.getCode().equals("101")) {
				try {
					ReAdVO adVO = DataPaser.json2Bean(result, ReAdVO.class);
					if (adVO.getData().size() > 0) {
						imageLoadObj = ImageLoadUtils.initImageLoad(mContext);// 初始化ImageLoad
						ImageLoadUtils.loadImage(imageLoadObj, Http.SERVLET_URL + adVO.getData().get(0).getPICURL(),
								linearLayout01);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Main_AdPopuWindow adPopuWindow;

	public void setMain_AdPopu(Main_AdPopuWindow adPopuWindow) {
		this.adPopuWindow = adPopuWindow;

	}

	@Override
	public void onDismiss() {
		if (null != adPopuWindow) {
			adPopuWindow.dismiss();
		}
	}

}
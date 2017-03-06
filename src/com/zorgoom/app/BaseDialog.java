package com.zorgoom.app;

import com.zorgoom.zhihework.adapter.ImageLoadUtils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import jni.util.Utils;

/**
 * 
 * @ClassName: BaseDialog
 * @Description: dialog的基类
 * @author Carter
 * @date 2015年8月13日
 * @modifyDate 2015年8月13日
 *
 */
public abstract class BaseDialog extends Dialog {

	protected Context mContext;
	public Object[] imageLoadObj;

	public BaseDialog(Context context) {
		super(context);
		this.mContext = context;
	}
	public BaseDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageLoadObj = ImageLoadUtils.initImageLoad(mContext);
		initData();
		initView();
	}

	protected abstract void initView();

	protected abstract void initData();

	/**
	 * 点击其他地方隐藏键盘
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			// if (Utils.isShouldHideInput(v, ev)) {
			//
			// InputMethodManager imm = (InputMethodManager)
			// mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			// if (imm != null) {
			// imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			// }
			// }
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	/**
	 * @Title: setWidAndHei
	 * @Description:设置dialog的宽高
	 */
	protected void setWidAndHei(int width, int height) {
		getWindow().setLayout(width, height);
	}

	/**
	 * @Title: setPosition
	 * @Description: 设置dialog的位置
	 * @param position
	 *            取值为Gravity中的常量
	 */
	protected void setPosition(int position) {
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(position);
	}
}

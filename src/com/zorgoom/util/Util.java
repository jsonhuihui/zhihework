package com.zorgoom.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.widget.NetworkRequestDialog;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Util {
	// 1、 开锁2、验证门口机是否在线3、户户通呼叫4\呼叫界面开锁 5、智能家居发送信息
	public static int sendIndex = 0;

	@SuppressLint("SimpleDateFormat")
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public static void setGridViewHeightBasedOnChildren(Context mContext, GridView gridView) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		int size = listAdapter.getCount() / 3;
		if (size % 3 != 0 || size == 0) {
			size += 1;
		}
		params.height = totalHeight / size + dip2px(mContext, 88);
		gridView.setLayoutParams(params);
	}

	/**
	 * 
	 * @Title: setListViewHeightBasedOnChildren @Description:
	 *         TODO(根据ListView算出List的高度) @param @param mContext @param @param
	 *         listView 参数 @return void 返回类型 @throws
	 */
	public static void setListViewHeightBasedOnChildren(Context mContext, ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem
					.getMeasuredHeight()/* + dip2px(mContext, 10) */;
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static String getFormatedDateTime(String pattern, long dateTime) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
		return sDateFormat.format(new Date(dateTime + 0));
	}

	public static NetworkRequestDialog progressDialog;

	public static void showLoadDialog(Context mContext, String msg) {
		if (null != mContext) {
			if (progressDialog == null) {
				progressDialog = new NetworkRequestDialog(mContext, R.style.dialog);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setMsg(msg);
				progressDialog.show();
			} else {
				if (!progressDialog.isShowing()) {
					progressDialog.show();
				}
			}
		}
	}

	public static void dismissLoadDialog() {
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		}
	}

	/**
	 * 根据手机分辨率从dp转成px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 按钮选择状态 适合单个文本和背景
	 * 
	 * @param index
	 * @param mContext
	 * @param list
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void switchStatus(int index, Context mContext, List<TextView> list) {
		for (int i = 0; i < list.size(); i++) {
			TextView tx;
			if (index == i) {// 选中
				tx = list.get(i);
				tx.setBackground(mContext.getResources().getDrawable(R.color.qianlan));
				tx.setTextColor(mContext.getResources().getColor(R.color.white));

			} else {// 未选中
				tx = list.get(i);
				tx.setBackgroundColor(mContext.getResources().getColor(R.color.qianhui));// 背景才有正常
				tx.setTextColor(mContext.getResources().getColor(R.color.q_black));
			}
		}
	}

}

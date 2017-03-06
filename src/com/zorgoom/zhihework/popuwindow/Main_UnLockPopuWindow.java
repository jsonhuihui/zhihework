package com.zorgoom.zhihework.popuwindow;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.adapter.UnLockAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.service.MainService;
import com.zorgoom.zhihework.vo.RsUsersKeysListResultVO;
import com.zorgoom.zhihework.vo.RsUsersKeysListResultVO.UsersKeys;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import rtc.sdk.common.RtcConst;
import rtc.sdk.core.RtcRules;

/**
 * 
 * 开锁popu
 * 
 * @author hx
 * @param <MusicPlayer>
 * 
 */
@SuppressLint("NewApi")
public class Main_UnLockPopuWindow extends PopupWindow
		implements OnClickListener, HttpListener, AdapterView.OnItemClickListener, OnDismissListener {

	private Context mContext;
	private View view;
	private ImageView linearLayout01;
	private String onResponseResult;
	private C2BHttpRequest c2BHttpRequest;
	private Main_AdPopuWindow adPopuWindow;
	private GridView unlock_gridview;

	public Main_UnLockPopuWindow(Activity mContext, View parent, int hei1, Main_AdPopuWindow adPopuWindow) {
		this.mContext = mContext;
		this.adPopuWindow = adPopuWindow;
		view = View.inflate(mContext, R.layout.unlock_popwindow, null);
		setContentView(view);
		setHeight(hei1);
		setWidth(LayoutParams.FILL_PARENT);
		setFocusable(true);
		setOutsideTouchable(true);
		setAnimationStyle(R.style.mypopwindow_anim_style);
		setBackgroundDrawable(new BitmapDrawable());
		setOnDismissListener(this);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		initView();
		c2BHttpRequest = new C2BHttpRequest(mContext, this);

		String EMPID = PrefrenceUtils.getStringUser("EMPID", mContext);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(EMPID, timestamp);

		c2BHttpRequest.getHttpResponse(Http.GETLOCK + "&EMPID=" + EMPID + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);

		IntentFilter filter = new IntentFilter();
		filter.addAction("checklock");
		filter.addAction("lock");
		mContext.registerReceiver(receiver, filter);
	}

	private void initView() {
		linearLayout01 = (ImageView) view.findViewById(R.id.linearLayout01);
		linearLayout01.setOnClickListener(this);
		unlock_gridview = (GridView) view.findViewById(R.id.unlock_gridview);
		unlock_gridview.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.linearLayout01:
			adPopuWindow.dismiss();
			dismiss();
			break;
		}
	}

	/**
	 * 钥匙列表数据
	 */
	private RsUsersKeysListResultVO rsPropertypaymentListResultVO;
	private List<UsersKeys> usersKeys;
	private UnLockAdapter myadapter;

	protected void keyList() {
		if (onResponseResult != null) {
			rsPropertypaymentListResultVO = DataPaser.json2Bean(onResponseResult, RsUsersKeysListResultVO.class);
			if (null != rsPropertypaymentListResultVO) {
				if ("101".equals(rsPropertypaymentListResultVO.getCode())) {
					if (rsPropertypaymentListResultVO.getData().size() == 0) {
						ToastUtil.showMessage1(mContext, "当前没有钥匙！", 300);
						return;
					}
					usersKeys = rsPropertypaymentListResultVO.getData();

					myadapter = new UnLockAdapter(mContext, usersKeys);
					unlock_gridview.setAdapter(myadapter);
					setGrid(unlock_gridview, 100, usersKeys);
					Util.sendIndex = 2;
					String remoteuri = RtcRules.UserToRemoteUri_new(
							usersKeys.get(index).getLOCKMAC().replaceAll(":", ""), RtcConst.UEType_Any);
					if (null != MainService.device) {
						int returnMsg = MainService.device.sendIm(remoteuri, "text/plain", " ");
						if (returnMsg != 0) {
							usersKeys.get(index).setState(1);
							myadapter.setList(usersKeys);
						}
					} else {
						ToastUtil.showMessage(mContext, "钥匙初始化异常，请重新打开钥匙包");
					}
				}
			}
		}
	}

	/** 设置GridView的item的大小 */
	private <T> void setGrid(GridView gridView, int itemWidth, List<T> list) {
		int size = list.size();
		int columnWidth = Util.dip2px(mContext, itemWidth);// 设置girdview的每个item的宽度
		int horizontalSpacing = Util.dip2px(mContext, 12);// 设置gridview的item之间的间隔

		gridView.setColumnWidth(columnWidth);
		gridView.setNumColumns(size);
		gridView.setHorizontalSpacing(horizontalSpacing);
		gridView.setStretchMode(GridView.NO_STRETCH);

		int width = size * columnWidth + (size - 1) * horizontalSpacing;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT);
		gridView.setLayoutParams(params);
	}

	// private boolean isLock = true;
	private int index = 0;

	@Override
	public void OnResponse(String result, int reqId) {
		onResponseResult = result;
		switch (reqId) {
		case 1:// 钥匙列表数据
			keyList();
			break;
		}
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			int returnMsg = intent.getIntExtra("returnMsg", -1);
			switch (action) {
			case "lock":
				switch (returnMsg) {
				case 200:
					adPopuWindow.dismiss();
					dismiss();
					ToastUtil.showMessage(mContext, "开门成功...");
					break;
				case 404:
					ToastUtil.showMessage(mContext, "开门失败，门口不存在");
					break;
				case 480:
				default:
					ToastUtil.showMessage(mContext, "开门失败，门口机不在线");
					break;
				}
				break;
			case "checklock":
				if (index < usersKeys.size()) {
					switch (returnMsg) {
					case 200:
						usersKeys.get(index).setState(0);
						break;
					default:
						usersKeys.get(index).setState(1);
						break;
					}
					index += 1;
					if (index <= usersKeys.size()) {
						myadapter.setList(usersKeys);

						if (index < usersKeys.size()) {
							String remoteuri = RtcRules.UserToRemoteUri_new(
									usersKeys.get(index).getLOCKMAC().replaceAll(":", ""), RtcConst.UEType_Any);
							int msg = MainService.device.sendIm(remoteuri, "text/plain", " ");
							if (msg != 0) {
								usersKeys.get(index).setState(1);
								myadapter.setList(usersKeys);
							}
						}
					}
				}
				break;
			}

		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		if (null != MainService.device) {
			Util.sendIndex = 1;
			int returnMsg = MainService.device.sendIm(
					RtcRules.UserToRemoteUri_new(usersKeys.get(position).getLOCKMAC().replaceAll(":", ""),
							RtcConst.UEType_Any),
					"text/plain", PrefrenceUtils.getStringUser("userId", mContext) + "/openDoor");
			if (returnMsg == 0) {
				// ToastUtil.showMessage(mContext, "请稍候,正在开门...");
			} else {
				ToastUtil.showMessage(mContext, "开门失败，请重新开门");
			}
		} else {
			ToastUtil.showMessage(mContext, "开门失败，请重新开门");
		}
	}

	@Override
	public void onDismiss() {
		adPopuWindow.dismiss();
		if (null != receiver) {
			mContext.unregisterReceiver(receiver);
		}
	}

}
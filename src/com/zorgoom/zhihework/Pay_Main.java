package com.zorgoom.zhihework;

import com.google.gson.Gson;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsCheckCode;
import com.zorgoom.zhihework.vo.RsCode;
import com.zorgoom.zhihework.zxing.encoding.EncodingUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Pay_Main extends Fragment implements View.OnClickListener, HttpListener {

	private SwipeRefreshLayout main_srl_view;
	private ImageView pay_erweima;
	private View mView;
	private C2BHttpRequest c2BHttpRequest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.pay_main, container, false);
			pay_erweima = (ImageView) mView.findViewById(R.id.pay_erweima);
			c2BHttpRequest = new C2BHttpRequest(getActivity(), this);
			initView();
			initData();
			return mView;
		}
		return mView;

	}

	private void initData() {
		String userId = PrefrenceUtils.getStringUser("userId", getActivity());
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId + "", timestamp);
		c2BHttpRequest.getHttpResponse(Http.QRCODE + "USERID=" + userId + "&FKEY=" + key + "&TIMESTAMP=" + timestamp,
				1);
	}

	private Bitmap bimap;

	private void initCode() {
		if (bimap != null && !bimap.isRecycled()) {
			bimap.recycle();
			bimap = null;
		}
		if (null != code) {
			String s = code.getData().getUUID();
			bimap = EncodingUtils.createQRCode(s, 1000, 1000,
					BitmapFactory.decodeResource(getResources(), R.drawable.logo));
			pay_erweima.setImageBitmap(bimap);
		}
	}

	private void initView() {
		main_srl_view = (SwipeRefreshLayout) mView.findViewById(R.id.main_srl_view);
		// 下拉刷新
		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
					@SuppressLint("NewApi")
					public void run() {
						if (!getActivity().isDestroyed()) {
							main_srl_view.setRefreshing(false); // 是否显示刷新进度;false:不显示
							initData();
//							initCode();
						}
					}
				}, 2000);
			}
		}); // 设置刷新监听
		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_erweima:
			initData();
		}
	}

	/*************************** 一分钟刷新二维码start ***************************/
	private final int ADTIME = 60000;// 刷新时间一分钟

	public Runnable runnable;

	private void initRefreshCodeTime() {// 初始化定时器
		runnable = new Runnable() {
			@Override
			public void run() {

				handler1.sendEmptyMessage(1);
			}
		};
		handler1.postDelayed(runnable, ADTIME);
	}

	public void stopTimer() {// 停止
		if (null != runnable) {
			handler1.removeCallbacks(runnable);
		}
	}

	private void refreshTimer() {// 刷新
		handler1.removeCallbacks(runnable);
		handler1.postDelayed(runnable, ADTIME);
		handler1.sendEmptyMessageDelayed(0, ADTIME);
	}

	Handler handler1 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:// 呼叫时间到
				initCode();
				// 倒计时再次开启
				refreshTimer();
				break;
			}
		}
	};

	/*************************** 一分钟刷新二维码end ***************************/

	/*************************** 2秒钟请求二维码是否过期start **********************/

	private final int ADTIME2 = 3000;// 两秒

	public Runnable runnable2;

	private void initCheckTime() {// 初始化定时器
		runnable2 = new Runnable() {
			@Override
			public void run() {
				handler2.sendEmptyMessage(1);
			}
		};
		handler2.postDelayed(runnable2, ADTIME2);
	}

	public void stopTimer2() {// 停止
		if (null != runnable2) {
			handler2.removeCallbacks(runnable2);
		}
	}

	private void refreshTimer2() {// 刷新
		handler2.removeCallbacks(runnable2);
		handler2.postDelayed(runnable2, ADTIME2);
		handler2.sendEmptyMessageDelayed(0, ADTIME2);
	}

	Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:// 呼叫时间到，进行注册
				initData2();
				break;
			}
		}
	};

	/*************************** 2秒钟请求二维码是否过期end **********************/
	private void initData2() {
		String CODE = code.getData().getUUID();
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(CODE + "", timestamp);
		c2BHttpRequest.setShow(false);
		c2BHttpRequest.getHttpResponse(Http.CODEISCONSUME + "CODE=" + CODE + "&FKEY=" + key + "&TIMESTAMP=" + timestamp,
				2);
	}

	RsCode code;
	Gson gson = new Gson();

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			switch (reqId) {
			case 1:
				code = gson.fromJson(result, RsCode.class);
				if (code != null) {
					if ("101".equals(code.getCode())) {
						initCode();
						initRefreshCodeTime();
						initCheckTime();
					}
				}
				break;
			case 2:
				RsCheckCode code = gson.fromJson(result, RsCheckCode.class);
				if (code != null) {
					if ("101".equals(code.getCode())) {
						refreshTimer2();
					} else if ("202".equals(code.getCode())) {
						Intent intent = new Intent(getContext(), ConsumeDetail_detail.class);
						intent.putExtra("checkCode", code.getData());
						startActivity(intent);
					} else {
						ToastUtil.showMessage(getContext(), code.getMsg());
					}
				}
				break;
			}

		}
	}

}

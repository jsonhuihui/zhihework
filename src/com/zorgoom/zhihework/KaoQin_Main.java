package com.zorgoom.zhihework;

import com.google.gson.Gson;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsKqCode;
import com.zorgoom.zhihework.zxing.encoding.EncodingUtils;

import android.annotation.SuppressLint;
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

public class KaoQin_Main extends Fragment implements View.OnClickListener, HttpListener {

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
			initHandler();
			initData();
			return mView;

		}
		return mView;

	}

	private void initData() {
		String userId = PrefrenceUtils.getStringUser("userId", getActivity());
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId + "", timestamp);
		c2BHttpRequest.getHttpResponse(Http.KQQRCODE + "USERID=" + userId + "&FKEY=" + key + "&TIMESTAMP=" + timestamp,
				1);
	}

	private Bitmap bimap;

	private void initCode() {
		if (bimap != null && !bimap.isRecycled()) {
			bimap.recycle();
			bimap = null;
		}
		String s = code.getData().getATTENCEUUID();
		bimap = EncodingUtils.createQRCode(s, 1000, 1000,
				BitmapFactory.decodeResource(getResources(), R.drawable.logo));
		pay_erweima.setImageBitmap(bimap);
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
							initCode();
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

	}

	private final int ADTIME = 60000;// 刷新时间一分钟
	// private final int ADTIME = 500;// 刷新时间一分钟
	public Runnable runnable;
	private Handler handler1 = null;

	private void initRtcTime() {
		runnable = new Runnable() {
			@Override
			public void run() {

				handler1.sendEmptyMessage(1);
			}
		};
		handler1.postDelayed(runnable, ADTIME);
	}

	public void stopTimer() {
		if (null != runnable) {
			handler1.removeCallbacks(runnable);
		}
	}

	private void refreshTimer() {
		handler1.removeCallbacks(runnable);
		handler1.postDelayed(runnable, ADTIME);
		handler1.sendEmptyMessageDelayed(0, ADTIME);
	}

	protected void initHandler() {
		handler1 = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:// 呼叫时间到，进行注册
						// if (rtcClient != null && token != null) {
						// rtcRegister();
						// } else {
						// initRtcTime();
						// }
					initCode();
					// 倒计时再次开启
					refreshTimer();
					break;
				}
			}
		};
	}

	RsKqCode code;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			Gson gson = new Gson();
			code = gson.fromJson(result, RsKqCode.class);
			if (code != null) {
				if ("101".equals(code.getCode())) {
					initCode();
					initRtcTime();
				}
			}

		} else {
			ToastUtil.showMessage1(getActivity(), "当前没有数据", 300);
		}
	}

}
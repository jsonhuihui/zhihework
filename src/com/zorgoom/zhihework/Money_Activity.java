package com.zorgoom.zhihework;

import com.google.gson.Gson;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsEmp;
import com.zorgoom.zhihework.vo.RsSUserVO;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.TextView;

public class Money_Activity extends BaseActivity implements View.OnClickListener, HttpListener {

	private Money_Activity mContext;
	private TextView money, bzmoney;
	private SwipeRefreshLayout main_srl_view;
	private C2BHttpRequest c2BHttpRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.money_layout);
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initView();
		initData();
	}

	private void initData() {
		String empId = PrefrenceUtils.getStringUser("EMPID", this);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(empId + "", timestamp);
		c2BHttpRequest.getHttpResponse(Http.EMP + "EMPID=" + empId + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	private void initView() {
		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.money_mingxi).setOnClickListener(this);
		findViewById(R.id.money_bt).setOnClickListener(this);
		money = (TextView) findViewById(R.id.money);
		bzmoney = (TextView) findViewById(R.id.bzmoney);
		main_srl_view = (SwipeRefreshLayout) findViewById(R.id.main_srl_view);
		// 下拉刷新
		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
					@SuppressLint("NewApi")
					@Override
					public void run() {
						if (!isDestroyed()) {
							main_srl_view.setRefreshing(false); // 是否显示刷新进度;false:不显示
							initData();
						}
					}
				}, 2000);
			}
		}); // 设置刷新监听
		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDestroy() {
		super.onDestroy();

	};

	RsEmp code;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			Gson gson = new Gson();
			code = gson.fromJson(result, RsEmp.class);
			if (code != null) {
				if ("101".equals(code.getCode())) {
					money.setText((code.getData().getMONEY()) + "");
					bzmoney.setText((code.getData().getALLOWANCE()) + "");
				}
			}
		} else {
			ToastUtil.showMessage1(this, "当前没有数据！", 300);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		case R.id.money_mingxi:
			setClass(mContext, ConsumeDetail_Main.class);
			break;
		case R.id.money_bt:
			setClass(mContext, PayActivity.class);
			break;

		}
	}
}

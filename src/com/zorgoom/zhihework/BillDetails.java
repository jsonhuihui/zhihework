package com.zorgoom.zhihework;


import com.zorgoom.zhihework.adapter.BillDetailsAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsMsg;
import com.zorgoom.zhihework.vo.RsPaymentDetail;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 物业账单详情
 * 
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class BillDetails extends MBaseActivity implements View.OnClickListener, HttpListener {

	private ListView message_listView1;
	private C2BHttpRequest c2BHttpRequest;
	private BillDetails mContext;
	private TextView tv_price, title;
	private TextView tv_pay;
	private int index;
	private String price;
	private String uuid;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bill_details_laout);
		index = getIntent().getIntExtra("index", -1);
		mContext = this;
		uuid = getIntent().getStringExtra("uuid");
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initData() {
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(uuid + "", timestamp);
		c2BHttpRequest.getHttpResponse(
				Http.PAYMENT_DETAIL + "BILLUUID=" + uuid + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	private void initView() {
		price = getIntent().getStringExtra("price");
		title = (TextView) findViewById(R.id.title);
		tv_price = (TextView) findViewById(R.id.price);
		tv_pay = (TextView) findViewById(R.id.tv_pay);
		tv_price.setText("￥" + price);
		title.setText(PrefrenceUtils.getStringUser("HOUSING", this));
		message_listView1 = (ListView) findViewById(R.id.message_listView1);
		findViewById(R.id.regis_back).setOnClickListener(this);
		main_srl_view = (SwipeRefreshLayout) findViewById(R.id.main_srl_view);
		// 下拉刷新
		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.regis_back:// 返回
			finish();
			break;
		case R.id.tv_pay:
			Intent intent = new Intent(this, PayActivity.class);
			intent.putExtra("index", index);
			intent.putExtra("price", price);
			intent.putExtra("uuid", uuid);
			startActivity(intent);
			break;
		}
	}

	RsMsg rsPropertypaymentListResultVO;

	@Override
	public void OnResponse(String result, int reqId) {
		RsPaymentDetail paymentDetail = DataPaser.json2Bean(result, RsPaymentDetail.class);
		if (null != paymentDetail) {
			if ("101".equals(paymentDetail.getCode())) {
				if (paymentDetail.getData().size() == 0) {
					ToastUtil.showMessage1(this, "当前没有消息数据！", 300);
					return;
				}
				if (paymentDetail.getData().get(0).getBILLSTATE().equals("P")) {// 已付款
					tv_pay.setVisibility(View.GONE);
				} else {// 未付款
					tv_pay.setVisibility(View.VISIBLE);
					tv_pay.setOnClickListener(this);
				}
				message_listView1.setAdapter(new BillDetailsAdapter(this, paymentDetail.getData()));
			}
		}
	}

	

}

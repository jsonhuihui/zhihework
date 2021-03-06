package com.zorgoom.zhihework;

import com.google.gson.Gson;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.adapter.ChargeDetailAdapter;
import com.zorgoom.zhihework.adapter.ConsumeDetailAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsChargeDetail;
import com.zorgoom.zhihework.vo.RsConsumeDetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 消费记录
 * 
 * @author Administrator
 *
 */

public class ConsumeDetail_Main extends MBaseActivity implements View.OnClickListener, HttpListener {


	private ListView message_listView1;
	private String onResponseResult;
	private C2BHttpRequest c2BHttpRequest;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consumedetail_laout);
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initData();
	}

	private void initData() {
		String userId = PrefrenceUtils.getStringUser("userId", this);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId, timestamp);

		c2BHttpRequest
				.getHttpResponse(Http.CONSUMEDETAIL + "USERID=" + userId + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	private void initView() {
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
		message_listView1 = (ListView) findViewById(R.id.message_listView1);
		message_listView1.setOnItemClickListener(new myOnitemClick());
		findViewById(R.id.regis_back).setOnClickListener(this);
	}

	public class myOnitemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
			Intent intent = new Intent(ConsumeDetail_Main.this, ConsumeDetail_detail.class);
			intent.putExtra("checkCode", RsConsumeDetailData.getData().get(position));
			startActivity(intent);
		}
	}

		
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		}
	}

	RsConsumeDetail RsConsumeDetailData;
	private ConsumeDetailAdapter myadapter;

	@Override
	public void OnResponse(String result, int reqId) {
		onResponseResult = result;
		if (onResponseResult != null) {
			switch (reqId) {
			case 1:
				RsConsumeDetailData = gson.fromJson(onResponseResult, RsConsumeDetail.class);
				if ("101".equals(RsConsumeDetailData.getCode())) {
					if (RsConsumeDetailData.getData().size() == 0) {
						ToastUtil.showMessage1(this, "当前没有消息数据！", 300);
						return;
					}
					myadapter = new ConsumeDetailAdapter(this, RsConsumeDetailData.getData());
					message_listView1.setAdapter(myadapter);
				}
				break;
			}

		}
	}

	Gson gson = new Gson();

}

package com.zorgoom.zhihework;

import com.google.gson.Gson;
import com.zorgoom.zhihework.adapter.OpenDoorRecordAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsOpenDoorRecord;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

/**
 * 开门记录
 * 
 * @author Administrator
 *
 */
public class OpenDoorRecordActivity extends MBaseActivity implements View.OnClickListener, HttpListener {

	private ListView message_listView1;
	private String onResponseResult;
	private C2BHttpRequest c2BHttpRequest;
	private int index;
	private SwipeRefreshLayout main_srl_view;
	private OpenDoorRecordActivity mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.open_door_record_laout);
		index = getIntent().getIntExtra("index", 0);
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initData();
	}

	private void initData() {
		//String userId = PrefrenceUtils.getStringUser("userId", this);
		String userId = "169";
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId, timestamp);

		c2BHttpRequest
				.getHttpResponse(Http.FINDACCESS + "USERID=" + userId + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
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

		findViewById(R.id.regis_back).setOnClickListener(this);
	}

	

		
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		}
	}

	RsOpenDoorRecord rsPropertypaymentListResultVO;
	private OpenDoorRecordAdapter myadapter;

	@Override
	public void OnResponse(String result, int reqId) {
		onResponseResult = result;
		if (onResponseResult != null) {
			switch (reqId) {
			case 1:
				rsPropertypaymentListResultVO = gson.fromJson(onResponseResult, RsOpenDoorRecord.class);
				if ("101".equals(rsPropertypaymentListResultVO.getCode())) {
					if (rsPropertypaymentListResultVO.getData().size() == 0) {
						ToastUtil.showMessage1(this, "当前没有消息数据！", 300);
						return;
					}
					myadapter = new OpenDoorRecordAdapter(this, rsPropertypaymentListResultVO.getData());
					message_listView1.setAdapter(myadapter);
				}
				break;
			}

		}
	}

	Gson gson = new Gson();

}

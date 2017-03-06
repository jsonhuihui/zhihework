package com.zorgoom.zhihework;

import com.zorgoom.zhihework.adapter.MaintainListAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsMaintainListment;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 维修列表
 * 
 * @author Administrator
 *
 */
public class MaintainList extends MBaseActivity implements View.OnClickListener, HttpListener {

	private ListView message_listView1;
	private C2BHttpRequest c2BHttpRequest;
	private MaintainList mContext;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.maintain_laout);
		mContext = this;
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initData();
	}

	private void initData() {
		String userId = PrefrenceUtils.getStringUser("userId", mContext);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId, timestamp);

		c2BHttpRequest.getHttpResponse(Http.GETMYTROUBLE + "USERID=" + userId + "&UNITID="
				+ PrefrenceUtils.getStringUser("UNITID", this) + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	@SuppressLint("NewApi")
	private void initView() {
		message_listView1 = (ListView) findViewById(R.id.message_listView1);
		findViewById(R.id.add_maintain).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		message_listView1.setOnItemClickListener(new myOnitemClick());

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

	public class myOnitemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
			Intent intent = new Intent(mContext, MaintainInfo.class);
			intent.putExtra("maintainListment", rsPropertypaymentListResultVO.getData().get(position));
			startActivity(intent);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_maintain:// 新建
			openActivity(MaintainListAdd.class);
			break;
		case R.id.regis_back:// 返回
			finish();
			break;
		}
	}

	RsMaintainListment rsPropertypaymentListResultVO;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			rsPropertypaymentListResultVO = DataPaser.json2Bean(result, RsMaintainListment.class);
			if (null != rsPropertypaymentListResultVO) {
				if ("101".equals(rsPropertypaymentListResultVO.getCode())) {
					if (rsPropertypaymentListResultVO.getData().size() == 0) {
						ToastUtil.showMessage1(this, "当前没有消息数据！", 300);
						return;
					}
					MaintainListAdapter myadapter = new MaintainListAdapter(this,
							rsPropertypaymentListResultVO.getData());
					message_listView1.setAdapter(myadapter);
				}
			}
		}
	}

}
package com.zorgoom.zhihework;

import com.zorgoom.zhihework.adapter.NoticeListAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsMsg;
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
 * 通知公告
 * 
 * @author Administrator
 *
 */
public class NoticeList extends MBaseActivity implements View.OnClickListener, HttpListener {

	private ListView message_listView1;
	private C2BHttpRequest c2BHttpRequest;
	private NoticeList mContext;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notice_laout);
		mContext = this;
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initData();
	}

	private void initData() {
		String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", mContext);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(COMMUNITYID, timestamp);

		c2BHttpRequest.getHttpResponse(
				Http.GETNOTICE + "COMMUNITYID=" + COMMUNITYID + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	@SuppressLint("NewApi")
	private void initView() {
		message_listView1 = (ListView) findViewById(R.id.message_listView1);
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

			Intent intent = new Intent(mContext, Message_detail.class);
			intent.putExtra("msg", rsPropertypaymentListResultVO.getData().get(position));
			startActivity(intent);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.regis_back:// 返回
			finish();
			break;

		}
	}

	RsMsg rsPropertypaymentListResultVO;

	@Override
	public void OnResponse(String result, int reqId) {
		if (result != null) {
			rsPropertypaymentListResultVO = DataPaser.json2Bean(result, RsMsg.class);
			if (null != rsPropertypaymentListResultVO) {
				if ("101".equals(rsPropertypaymentListResultVO.getCode())) {
					if (rsPropertypaymentListResultVO.getData().size() == 0) {
						ToastUtil.showMessage1(this, "当前没有消息数据！", 300);
						return;
					}
					NoticeListAdapter myadapter = new NoticeListAdapter(this, rsPropertypaymentListResultVO.getData());
					message_listView1.setAdapter(myadapter);
				}
			}
		}
	}

}

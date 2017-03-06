package com.zorgoom.zhihework;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zorgoom.zhihework.adapter.OrderListAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.vo.OrderList;
import com.zorgoom.zhihework.vo.OrderListVO;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @ClassName: OrderUnpayActivity
 * @Description: TODO(我的订单)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年8月21日
 * @修改时间 2015年8月21日
 */
public class OrderActivity extends BaseActivity implements OnClickListener, HttpListener {

	private ListView lv_unpay;
	private LinearLayout lin_no_order;
	private OrderListAdapter orderUnpayAdapter;
	// private int state;
	private TextView tv_title;
	private int pageIndex = 1;
	// public List<Data> Orders = new ArrayList<Data>();
	// private PullToRefreshView mPullToRefreshView;
	// private XListViewFooter footer;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_activity_layout);
		initView();
		initData();
	}

	private void initData() {
		String userId = PrefrenceUtils.getStringUser("userId", this);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId + "", timestamp);

		c2BHttpRequest.getHttpResponse(
				Http.GETORDERBYUSERID + "USERID=" + userId + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// if (null != orderUnpayAdapter) {
		// for (int i = 0; i < orderUnpayAdapter.getCount(); i++) {
		// orderUnpayAdapter.getView(i, null, null).destroyDrawingCache();
		// }
		// orderUnpayAdapter.destroy();
		// }
		ImageLoader.getInstance().clearMemoryCache();
		System.gc();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@SuppressLint("NewApi")
	protected void initView() {
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

		lin_no_order = (LinearLayout) findViewById(R.id.lin_no_order);
		findViewById(R.id.regis_back).setOnClickListener(this);
		lv_unpay = (ListView) findViewById(R.id.lv_unpay);
	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:// 确认收获
				break;
			case 2:// 取消订单
				break;
			}
		}
	};
	String orderSN;

	List<OrderList> data;

	@Override
	public void OnResponse(String result, int connectType) {
		if (null != result) {
			switch (connectType) {
			case 1:
				try {
					OrderListVO listVO = DataPaser.json2Bean(result, OrderListVO.class);
					if (null != listVO) {
						if (listVO.getCode().equals("101")) {
							lin_no_order.setVisibility(View.GONE);
							lv_unpay.setVisibility(View.VISIBLE);

							data = listVO.getData();
							orderUnpayAdapter = new OrderListAdapter(this, listVO.getData());
							orderUnpayAdapter.setHandler(handler);
							lv_unpay.setAdapter(orderUnpayAdapter);
						} else {
							lin_no_order.setVisibility(View.VISIBLE);
							lv_unpay.setVisibility(View.GONE);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;

		default:
			break;
		}
	}

}

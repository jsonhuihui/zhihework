package com.zorgoom.zhihework;

import java.util.ArrayList;
import java.util.List;


import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.fragment.BillListFragment;
import com.zorgoom.zhihework.fragment.BillListFragment2;
import com.zorgoom.zhihework.vo.RsPayment;
import com.zorgoom.zhihework.vo.RsPayment.Payment;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.widget.h.TabPageIndicator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * 物业账单列表
 * 
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class BillList extends BaseActivity implements View.OnClickListener, HttpListener {

	private C2BHttpRequest c2BHttpRequest;

	private TextView tv_count, tv_price, title;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bill_laout);
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initData();
	}

	private void initData() {
		String unitId = PrefrenceUtils.getStringUser("UNITID", this);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(unitId + "", timestamp);

		c2BHttpRequest.getHttpResponse(
				Http.PAYMENT_LIST + "UNITID=" + unitId + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	private static final String[] CONTENT = new String[] { "未缴账单", "已缴账单" };

	private void initView() {
		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_price = (TextView) findViewById(R.id.tv_price);
		title = (TextView) findViewById(R.id.title);
		title.setText(PrefrenceUtils.getStringUser("HOUSING", this));
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

	class GoogleMusicAdapter extends FragmentPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return BillListFragment.newInstance(nlist);
			case 1:
				return BillListFragment2.newInstance(plist);
			}
			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return CONTENT.length;
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

	RsPayment rsPropertypaymentListResultVO;
	List<Payment> nlist = null;
	List<Payment> plist = null;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			rsPropertypaymentListResultVO = DataPaser.json2Bean(result, RsPayment.class);
			if (null != rsPropertypaymentListResultVO) {
				if ("101".equals(rsPropertypaymentListResultVO.getCode())) {
					if (rsPropertypaymentListResultVO.getData().size() == 0) {
						ToastUtil.showMessage1(this, "当前没有消息数据！", 300);
						return;
					}
					nlist = new ArrayList<Payment>();
					plist = new ArrayList<Payment>();
					double price = 0;
					for (Payment payment : rsPropertypaymentListResultVO.getData()) {
						price += payment.getBILLTOTAL();
						switch (payment.getBILLSTATE()) {
						case "N":
							nlist.add(payment);
							break;
						case "P":
							plist.add(payment);
							break;
						}
					}
					FragmentPagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());
					ViewPager pager = (ViewPager) findViewById(R.id.pager);
					pager.setAdapter(adapter);
					TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
					indicator.setViewPager(pager);

					tv_count.setText(rsPropertypaymentListResultVO.getData().size() + "");
					tv_price.setText("￥" + price);
				}
			}
		}
	}

}

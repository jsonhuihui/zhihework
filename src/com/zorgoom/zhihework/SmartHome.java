package com.zorgoom.zhihework;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.fragment.SmartHomeFragment;
import com.zorgoom.zhihework.fragment.SmartHomeSceneFragment;
import com.zorgoom.zhihework.vo.RsPayment;
import com.zorgoom.zhihework.vo.RsPayment.Payment;
import com.zorgoom.util.DataPaser;
import com.zorgoom.widget.h.IconPagerAdapter;
import com.zorgoom.widget.h.TabPageIndicator;
import com.zorgoom.widget.h.TabPageIndicator2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 我的慧屋
 * 
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class SmartHome extends Fragment implements View.OnClickListener, HttpListener {

	private C2BHttpRequest c2BHttpRequest;
	private TextView tv_count, tv_price, title;
	private SwipeRefreshLayout main_srl_view;

	private FragmentActivity mContext;
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mContext = getActivity();
			// c2BHttpRequest = new C2BHttpRequest(getActivity(), this);
			// shopsDao = new ShopsDao(mContext);
			mView = inflater.inflate(R.layout.smart_home_laout, container, false);
			initView();
			return mView;
		}
		return mView;

	}

	private static final String[] CONTENT = new String[] { "情景", "控制", "视频", "安防" };

	private static final int[] ICONS = new int[] { R.drawable.perm_group_calendar1, R.drawable.perm_group_calendar2,
			R.drawable.perm_group_calendar3, R.drawable.perm_group_calendar4, };

	private void initView() {
		FragmentPagerAdapter adapter = new GoogleMusicAdapter(mContext.getSupportFragmentManager());
		ViewPager pager = (ViewPager) mView.findViewById(R.id.pager);
		pager.setAdapter(adapter);
		TabPageIndicator2 indicator = (TabPageIndicator2) mView.findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		main_srl_view = (SwipeRefreshLayout) mView.findViewById(R.id.main_srl_view);
		// 下拉刷新
		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
					@Override
					public void run() {
						if (!getActivity().isDestroyed()) {
							main_srl_view.setRefreshing(false); // 是否显示刷新进度;false:不显示
							// initData();
						}
					}
				}, 2000);
			}
		}); // 设置刷新监听
		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色

	}

	class GoogleMusicAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return SmartHomeSceneFragment.newInstance();
			default:
				return SmartHomeFragment.newInstance();
			}
			// return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}

		@Override
		public int getIconResId(int index) {
			return ICONS[index];
		}
	}

	// RsPayment rsPropertypaymentListResultVO;
	// List<Payment> nlist = null;
	// List<Payment> plist = null;

	@Override
	public void OnResponse(String result, int reqId) {
		// rsPropertypaymentListResultVO = DataPaser.json2Bean(result,
		// RsPayment.class);
		// if (null != rsPropertypaymentListResultVO) {
		// if ("101".equals(rsPropertypaymentListResultVO.getCode())) {
		// if (rsPropertypaymentListResultVO.getData().size() == 0) {
		// ToastUtil.showMessage1(mContext, "当前没有消息数据！", 300);
		// return;
		// }
		// nlist = new ArrayList<Payment>();
		// plist = new ArrayList<Payment>();
		// double price = 0;
		// for (Payment payment : rsPropertypaymentListResultVO.getData()) {
		// price += payment.getBILLTOTAL();
		// switch (payment.getBILLSTATE()) {
		// case "N":
		// nlist.add(payment);
		// break;
		// case "P":
		// plist.add(payment);
		// break;
		// }
		// }
		// FragmentPagerAdapter adapter = new
		// GoogleMusicAdapter(mContext.getSupportFragmentManager());
		// ViewPager pager = (ViewPager) mView.findViewById(R.id.pager);
		// pager.setAdapter(adapter);
		// TabPageIndicator indicator = (TabPageIndicator)
		// mView.findViewById(R.id.indicator);
		// indicator.setViewPager(pager);
		//
		// tv_count.setText(rsPropertypaymentListResultVO.getData().size() +
		// "");
		// tv_price.setText("￥" + price);
		// }
		// }
	}

	@Override
	public void onClick(View v) {

	}

}

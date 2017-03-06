package com.zorgoom.zhihework;


import com.zorgoom.zhihework.adapter.ImageLoadUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.db.dao.ShopsCartDao;
import com.zorgoom.zhihework.popuwindow.CartPopuWindow;
import com.zorgoom.zhihework.view.ObservableScrollView;
import com.zorgoom.zhihework.view.ObservableScrollView.ScrollViewListener;
import com.zorgoom.zhihework.vo.DetailsListVo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 社区商圈商品详情
 * 
 * @author Administrator
 *
 */
public class SaleDetailsGoodActivity extends BaseActivity
		implements View.OnClickListener, HttpListener, ScrollViewListener {

	private ObservableScrollView sv_sale;
	public int stype = 1;
	private C2BHttpRequest c2BHttpRequest;
	private RelativeLayout top_layout;
	private RelativeLayout title_layout;

	private int imageHeight;
	private DetailsListVo data;
	private ImageView logo;
	private TextView title;
	private TextView yueshou;
	private TextView price;
	private Object[] imageLoadObj;

	private RelativeLayout cart_layout;
	private TextView tv_num;
	private int numNO;
	private ShopsCartDao shopsCartDao;
	private String NAME, IMG;
	private TextView distribution_iv;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sale_details_good_layout);
		data = (DetailsListVo) getIntent().getSerializableExtra("data");
		NAME = getIntent().getStringExtra("NAME");
		IMG = getIntent().getStringExtra("IMG");
		imageLoadObj = ImageLoadUtils.initImageLoad(this);
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initView();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (null != sv_sale) {
			sv_sale.smoothScrollTo(0, 0);
		}
	}

	@SuppressLint("NewApi")
	public void refreshData() {
		numNO = shopsCartDao.queryBySidAll(data.getSHOPID());
		refreshNum();
	}

	@SuppressLint("NewApi")
	private void refreshNum() {
		if (numNO == 0) {
			tv_num.setVisibility(View.GONE);
			cart_layout.setTag(0);
			cart_layout.setBackground(getResources().getDrawable(R.drawable.car_yuan));
			distribution_iv.setBackgroundColor(getResources().getColor(R.color.sale_pay));
			distribution_iv.setEnabled(false);
		} else {
			cart_layout.setTag(1);
			cart_layout.setBackground(getResources().getDrawable(R.drawable.car_yuan2));
			distribution_iv.setBackgroundColor(getResources().getColor(R.color.sale_select_pay));
			distribution_iv.setEnabled(true);
			tv_num.setVisibility(View.VISIBLE);
			tv_num.setText(numNO + "");
		}
	}

	@SuppressLint("NewApi")
	private void initView() {
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
							// initData();
						}
					}
				}, 2000);
			}
		}); // 设置刷新监听
		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色

		distribution_iv = (TextView) findViewById(R.id.distribution_iv);
		distribution_iv.setOnClickListener(this);
		shopsCartDao = new ShopsCartDao(this);
		cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
		cart_layout.setOnClickListener(this);
		tv_num = (TextView) findViewById(R.id.num);

		numNO = shopsCartDao.queryBySidAll(data.getSHOPID());
		refreshNum();
		title = (TextView) findViewById(R.id.title);
		yueshou = (TextView) findViewById(R.id.yueshou);
		price = (TextView) findViewById(R.id.price);
		logo = (ImageView) findViewById(R.id.logo);

		ImageLoadUtils.loadImage(imageLoadObj, Http.SERVLET_URL + data.getGOODSIMAGE(), logo);
		title.setText(data.getPRODNAME());
		yueshou.setText("库存 " + data.getSTOCK());
		price.setText("￥" + data.getPRICE());

		sv_sale = (ObservableScrollView) findViewById(R.id.sv_sale);
		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.cart_layout).setOnClickListener(this);
		top_layout = (RelativeLayout) findViewById(R.id.top_layout);
		title_layout = (RelativeLayout) findViewById(R.id.title_layout);

		sv_sale.smoothScrollTo(0, 0);

		initListeners();
	}

	private void initListeners() {
		// 获取顶部图片高度后，设置滚动监听
		ViewTreeObserver vto = top_layout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				top_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				imageHeight = top_layout.getHeight();
				sv_sale.setScrollViewListener(SaleDetailsGoodActivity.this);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cart_layout:// 购物车
			int tag = Integer.parseInt(v.getTag() + "");
			if (tag == 1) {
				new CartPopuWindow(this, sv_sale, data.getSHOPID(), NAME);
			}
			break;
		case R.id.regis_back:
			finish();
			break;
		case R.id.distribution_iv://
			Bundle bundle = new Bundle();
			bundle.putInt("shopid", data.getSHOPID());
			bundle.putString("NAME", NAME);
			bundle.putString("IMG", IMG);
			openActivity(SaleDetailsConfirmOrderActivity.class, bundle);
			break;

		}
	}

	

	public static void setListViewHeightBasedOnChildren1(Context mContext, ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	public void OnResponse(String result, int reqId) {
		switch (reqId) {
		case 1:
			break;
		}

	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
		// TODO Auto-generated method stub
		// Log.i("TAG", "y--->" + y + " height-->" + height);
		if (y <= 0) {
			title_layout.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));// AGB由相关工具获得，或者美工提供
		} else if (y > 0 && y <= imageHeight) {
			float scale = (float) y / imageHeight;
			float alpha = (255 * scale);
			// 只是layout背景透明
			title_layout.setBackgroundColor(Color.argb((int) alpha, 227, 29, 26));
		} else {
			title_layout.setBackgroundColor(Color.argb((int) 255, 227, 29, 26));
		}
	}

}

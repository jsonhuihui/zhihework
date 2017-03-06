package com.zorgoom.zhihework;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.zhihework.adapter.ImageLoadUtils;
import com.zorgoom.zhihework.adapter.Sale_Details_Adapter;
import com.zorgoom.zhihework.adapter.Sale_Details_Adapter2;
import com.zorgoom.zhihework.adapter.Sale_Details_Adapter2.onSaleDetailsAdapterListener;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.db.dao.ShopsCartDao;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.popuwindow.CartPopuWindow;
import com.zorgoom.zhihework.view.CircleImageView;
import com.zorgoom.zhihework.view.ObservableScrollView;
import com.zorgoom.zhihework.view.ObservableScrollView.ScrollViewListener;
import com.zorgoom.zhihework.vo.DetailsListVo;
import com.zorgoom.zhihework.vo.SaleCategoryVo;
import com.zorgoom.zhihework.vo.SaleCategoryVo.CategoryVo;
import com.zorgoom.zhihework.vo.SaleDetailsListVo;
import com.zorgoom.util.DataPaser;

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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 社区商圈详情
 * 
 * @author Administrator
 *
 */
public class SaleDetailsActivity extends BaseActivity
		implements View.OnClickListener, HttpListener, ScrollViewListener, onSaleDetailsAdapterListener {

	private ObservableScrollView sv_sale;
	public int stype = 1;
	private ListView sale_listView1, sale_listView2;
	private C2BHttpRequest c2BHttpRequest;
	private RelativeLayout top_layout;
	private RelativeLayout title_layout;
	private Sale_Details_Adapter details_Adapter;
	private Sale_Details_Adapter2 details_Adapter2;
	private int imageHeight;

	private Animation mAnimation;
	private CircleImageView logo;
	private TextView name;
	private ImageView mAnimImageView;
	private int SHOPID;
	private String IMG, NAME, REMARK;
	private Object[] imageLoadObj;
	private ShopsCartDao shopsCartDao;
	private RelativeLayout cart_layout;
	private TextView tv_num, announcement;
	private int numNO;
	private TextView distribution_iv;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sale_details_layout);
		c2BHttpRequest = new C2BHttpRequest(this, this);
		SHOPID = getIntent().getIntExtra("SHOPID", 0);
		IMG = getIntent().getStringExtra("IMG");
		NAME = getIntent().getStringExtra("NAME");
		REMARK = getIntent().getStringExtra("REMARK");
		imageLoadObj = ImageLoadUtils.initImageLoad(this);
		shopsCartDao = new ShopsCartDao(this);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (null != sv_sale) {
			sv_sale.smoothScrollTo(0, 0);
		}
		initData();
	}

	private void initData() {
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(SHOPID + "", timestamp);
		c2BHttpRequest.getHttpResponse(
				Http.GETSHOPPORDCATEGORY + "SHOPID=" + SHOPID + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
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
							initData();
						}
					}
				}, 2000);
			}
		}); // 设置刷新监听
		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色

		announcement = (TextView) findViewById(R.id.announcement);
		announcement.setText("公告：欢迎光临" + NAME);
		distribution_iv = (TextView) findViewById(R.id.distribution_iv);
		distribution_iv.setOnClickListener(this);
		name = (TextView) findViewById(R.id.name);
		logo = (CircleImageView) findViewById(R.id.logo);
		cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
		cart_layout.setOnClickListener(this);
		tv_num = (TextView) findViewById(R.id.num);

		numNO = shopsCartDao.queryBySidAll(SHOPID);
		refreshNum();
		name.setText(NAME);
		ImageLoadUtils.loadImage(imageLoadObj, Http.SERVLET_URL + IMG, logo);
		mAnimImageView = (ImageView) findViewById(R.id.cart_anim_icon);
		sv_sale = (ObservableScrollView) findViewById(R.id.sv_sale);
		findViewById(R.id.regis_back).setOnClickListener(this);
		top_layout = (RelativeLayout) findViewById(R.id.top_layout);
		title_layout = (RelativeLayout) findViewById(R.id.title_layout);
		sale_listView1 = (ListView) findViewById(R.id.sale_listView1);
		sale_listView2 = (ListView) findViewById(R.id.sale_listView2);
		sale_listView1.setOnItemClickListener(new ItemClickListener());

		initListeners();
		sale_listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", data1.get(position));
				bundle.putString("NAME", NAME);
				bundle.putString("IMG", IMG);
				openActivity(SaleDetailsGoodActivity.class, bundle);
			}
		});
		initCarAnimation();
	}

	private void initCarAnimation() {
		mAnimation = AnimationUtils.loadAnimation(this, R.anim.cart_anim);
		mAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mAnimImageView.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void initListeners() {
		// 获取顶部图片高度后，设置滚动监听
		ViewTreeObserver vto = top_layout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				top_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				imageHeight = top_layout.getHeight();
				sv_sale.setScrollViewListener(SaleDetailsActivity.this);
			}
		});
	}

	private final class ItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			data1.clear();
			details_Adapter.setPostion(position);
			details_Adapter.notifyDataSetChanged();
			c2BHttpRequest.setShow(true);
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(SHOPID + "", timestamp);

			c2BHttpRequest.getHttpResponse(Http.GETPROD + "SHOPID=" + SHOPID + "&PRODCATEGORYID="
					+ data.get(position).getRID() + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 2);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cart_layout:// 购物车
			int tag = Integer.parseInt(v.getTag() + "");
			if (tag == 1) {
				new CartPopuWindow(SaleDetailsActivity.this, sv_sale, SHOPID, NAME);
			}
			break;
		case R.id.regis_back:
			finish();
			break;
		case R.id.distribution_iv://
			Bundle bundle = new Bundle();
			bundle.putInt("shopid", SHOPID);
			bundle.putString("IMG", IMG);
			bundle.putString("NAME", NAME);
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

	List<CategoryVo> data = new ArrayList<CategoryVo>();
	List<DetailsListVo> data1 = new ArrayList<DetailsListVo>();

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			switch (reqId) {
			case 1:
				SaleCategoryVo listVo = DataPaser.json2Bean(result, SaleCategoryVo.class);
				if (null != listVo) {
					if (listVo.getCode().equals("101")) {
						data = listVo.getData();
						details_Adapter = new Sale_Details_Adapter(getApplicationContext(), this, data);
						sale_listView1.setAdapter(details_Adapter);
						if (data.size() > 0) {
							c2BHttpRequest.setShow(false);

							String timestamp = System.currentTimeMillis() + "";
							String key = c2BHttpRequest.getKey(SHOPID + "", timestamp);

							c2BHttpRequest.getHttpResponse(Http.GETPROD + "SHOPID=" + SHOPID + "&PRODCATEGORYID="
									+ data.get(0).getRID() + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 2);
						}
					} else {
						ToastUtil.showMessage(getApplicationContext(), listVo.getMsg());
					}
				}
				break;
			case 2:
				SaleDetailsListVo listVo1 = DataPaser.json2Bean(result, SaleDetailsListVo.class);
				if (null != listVo1) {
					if (listVo1.getCode().equals("101")) {
						data1 = listVo1.getData();
						if (null != details_Adapter2) {
							details_Adapter2.setList(data1);
						} else {
							details_Adapter2 = new Sale_Details_Adapter2(getApplicationContext(), this, data1);
							details_Adapter2.setOnCheckedChanged(this);
							sale_listView2.setAdapter(details_Adapter2);
						}
						setListViewHeightBasedOnChildren1(getApplicationContext(), sale_listView2);
					} else {
						ToastUtil.showMessage(getApplicationContext(), listVo1.getMsg());
					}
				}
				break;
			}
		}
	}

	@SuppressLint("NewApi")
	public void refreshData() {
		numNO = shopsCartDao.queryBySidAll(SHOPID);
		refreshNum();
		details_Adapter2.notifyDataSetChanged();
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

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
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

	@SuppressLint("NewApi")
	@Override
	public void getChoiceData(int position, int num, int type) {
		DetailsListVo detailsListVo = data1.get(position);
		shopsCartDao.update(SHOPID, detailsListVo.getRID(), detailsListVo.getPRODNAME(), detailsListVo.getPRICE() + "",
				num);

		if (type == 1) {// 增加才有动画
			mAnimImageView.setVisibility(View.VISIBLE);
			mAnimImageView.startAnimation(mAnimation);
		}

		numNO = shopsCartDao.queryBySidAll(SHOPID);
		refreshNum();
	}

}

package com.zorgoom.zhihework;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zorgoom.zhihework.adapter.SaleDetailsConfimOrderAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.db.dao.ShopsCartDao;
import com.zorgoom.zhihework.base.db.entity.ShopsCart;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.popuwindow.DatePopuWindow;
import com.zorgoom.zhihework.vo.AddressList;
import com.zorgoom.zhihework.vo.AddressListVo;
import com.zorgoom.zhihework.vo.Detail;
import com.zorgoom.zhihework.vo.Master;
import com.zorgoom.zhihework.vo.OrderRequest;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 社区商圈确认订单
 * 
 * @author Administrator
 *
 */
public class SaleDetailsConfirmOrderActivity extends BaseActivity implements View.OnClickListener, HttpListener {

	public int stype = 1;
	private C2BHttpRequest c2BHttpRequest;
	private ListView list_order;
	private SaleDetailsConfimOrderAdapter saleDetailsConfimOrderAdapter;
	private LinearLayout lin_not_address;// 收货地址没有数据
	private RelativeLayout rel_is_address;// 收货地址有数据
	private TextView tv_address_name, tv_address_phone, tv_address, tv_date, shop_name, distribution_price;
	private int shopid;
	private String NAME, IMG;
	private ShopsCartDao shopsCartDao;
	private EditText remarks;
	private List<ShopsCart> carts;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sale_details_confirm_order_layout);
		c2BHttpRequest = new C2BHttpRequest(this, this);
		shopid = getIntent().getIntExtra("shopid", 0);
		NAME = getIntent().getStringExtra("NAME");
		IMG = getIntent().getStringExtra("IMG");

		shopsCartDao = new ShopsCartDao(this);
		carts = shopsCartDao.queryBySidGoodAll(shopid);

		String userId = PrefrenceUtils.getStringUser("userId", this);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId + "", timestamp);

		c2BHttpRequest.getHttpResponse(Http.GETADRRBYUSERID + "USERID=" + userId + "&OPERID="
				+ PrefrenceUtils.getStringUser("OPERID", this) + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);

		initView();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private int tolNum;
	private double price = 0;

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
							// initData();
						}
					}
				}, 2000);
			}
		}); // 设置刷新监听
		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色
		remarks = (EditText) findViewById(R.id.remarks);
		distribution_price = (TextView) findViewById(R.id.distribution_price);

		for (ShopsCart s : carts) {
			tolNum += s.getNum();
			price += (Double.parseDouble(s.getPrice()) * s.getNum());
		}
		distribution_price.setText("￥" + price);

		shop_name = (TextView) findViewById(R.id.shop_name);
		shop_name.setText(NAME);

		tv_date = (TextView) findViewById(R.id.tv_date);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String date = df.format(new Date());
		tv_date.setText(date);

		tv_address_name = (TextView) findViewById(R.id.tv_address_name);
		tv_address_phone = (TextView) findViewById(R.id.tv_address_pnone);
		tv_address = (TextView) findViewById(R.id.tv_address);
		lin_not_address = (LinearLayout) findViewById(R.id.lin_not_address);
		lin_not_address.setOnClickListener(this);
		rel_is_address = (RelativeLayout) findViewById(R.id.rel_is_address);
		rel_is_address.setOnClickListener(this);
		list_order = (ListView) findViewById(R.id.list_order);
		findViewById(R.id.lin_address).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.diyongquan_layout).setOnClickListener(this);
		findViewById(R.id.daijinquan_layout).setOnClickListener(this);
		findViewById(R.id.date_layout).setOnClickListener(this);
		findViewById(R.id.distribution_iv).setOnClickListener(this);
		saleDetailsConfimOrderAdapter = new SaleDetailsConfimOrderAdapter(this, this, carts);
		list_order.setAdapter(saleDetailsConfimOrderAdapter);
		setListViewHeightBasedOnChildren1(this, list_order);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		case R.id.diyongquan_layout:
			ToastUtil.showMessage(this, "暂没有可用抵用券");
			break;
		case R.id.daijinquan_layout:
			ToastUtil.showMessage(this, "暂没有可用代金券");
			break;
		case R.id.date_layout:
			new DatePopuWindow(this, v);
			break;
		case R.id.lin_address:
		case R.id.lin_not_address:
		case R.id.rel_is_address:
			intent = new Intent(this, AddressManageActivity.class);
			intent.putExtra("indexSelect", indexSelect);
			startActivityForResult(intent, 2);
			break;
		case R.id.distribution_iv:
			if (null == address) {
				ToastUtil.showMessage(getApplicationContext(), "请完善订单信息");
				return;
			}
			OrderRequest data = new OrderRequest();
			Master master = new Master();
			master.setAddress(address.getPROVINCE() + address.getCITY() + address.getDISTRICT() + address.getADDRESS());
			master.setCustomId(Integer.parseInt(PrefrenceUtils.getStringUser("userId", this)));
			master.setCustomName(address.getNAME());
			master.setCustomPhone(address.getMOBILE());
			master.setRemarks(remarks.getText().toString());
			master.setShopId(shopid);
			master.setTotalNum(tolNum);
			master.setTotalVal(price);
			master.setOPERTYPE(1);
			master.setPlatForm(1);

			data.setMaster(master);

			List<Detail> detail = new ArrayList<Detail>();
			for (ShopsCart cart : carts) {
				Detail detail2 = new Detail();
				detail2.setCount(cart.getNum());
				detail2.setProdId(cart.getGid());
				detail2.setVal(Double.parseDouble(cart.getPrice()));
				detail.add(detail2);
			}
			data.setDetail(detail);

			// RequestParams params = new RequestParams();
			// params.addBodyParameter("data", gson.toJson(data));
			// params.addBodyParameter("SHOPID", shopid + "");
			// params.addBodyParameter("USERID",
			// PrefrenceUtils.getStringUser("userId", this));

			intent = new Intent(this, PayActivity.class);
			// intent.putExtra("params", (Serializable) params);
			intent.putExtra("data", DataPaser.bean2Json(data));
			intent.putExtra("SHOPID", shopid + "");
			intent.putExtra("NAME", NAME);
			intent.putExtra("IMG", IMG);
			intent.putExtra("price", price + "");
			startActivity(intent);
			break;
		}
	}

	private int indexSelect;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null != data) {
			switch (requestCode) {
			case 2:// 收货地址
				rel_is_address.setVisibility(View.VISIBLE);
				lin_not_address.setVisibility(View.GONE);
				address = (AddressList) data.getSerializableExtra("address");
				indexSelect = data.getIntExtra("indexSelect", 0);
				tv_address_name.setText(address.getNAME());
				tv_address_phone.setText(address.getMOBILE());
				tv_address.setText(
						address.getPROVINCE() + address.getCITY() + address.getDISTRICT() + address.getADDRESS());
				break;
			}
		}
	};

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

	private AddressList address;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			switch (reqId) {
			case 1:
				AddressListVo listVo = DataPaser.json2Bean(result, AddressListVo.class);
				if (null != listVo) {
					if (listVo.getCode().equals("101")) {
						if (listVo.getData().size() > 0) {
							lin_not_address.setVisibility(View.GONE);
							rel_is_address.setVisibility(View.VISIBLE);
							for (AddressList a : listVo.getData()) {
								if (a.getISDEFAULT()) {
									address = a;
								}
							}
							if (address == null) {
								address = listVo.getData().get(0);
							}
							tv_address_phone.setText(address.getMOBILE());
							tv_address_name.setText(address.getNAME());
							tv_address.setText(address.getPROVINCE() + address.getCITY() + address.getDISTRICT()
									+ address.getADDRESS());
						} else {
							lin_not_address.setVisibility(View.VISIBLE);
							rel_is_address.setVisibility(View.GONE);
						}
					} else {
						// ToastUtil.showMessage(getApplicationContext(),
						// listVo.getMsg());
					}
				}
				break;
			case 2:
				break;
			}
		}

	}

	public void refreshData(String date) {
		tv_date.setText(date);
	}

}

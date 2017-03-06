package com.zorgoom.zhihework;

import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.view.CommonHintDialog.onCheckedChanged;
import com.zorgoom.zhihework.vo.OrderList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: OrderWaitRecActivity
 * @Description: 订单的等待收货界面
 * @author carter
 * @date 2015年9月7日
 * @modifyDate 2015年9月7日
 *
 */
public class OrderDetailsActivity extends BaseActivity implements OnClickListener, HttpListener, onCheckedChanged {

	private ListView gv_order_confirm;
	// private LinearLayout lin_wait_received;
	// private List photoPath;
	// private RelativeLayout rlWaitPay;// 等待付款

	private TextView
	// ctv_time_sendout, // 订单时间
	// ctv_order_num, // 订单号
	// ctv_logist_message, // 物流信息
	// ctv_logist_time, // 物流时间
	ctv_is_delivery, // 系统已确认，已出库
			ctv_member_name, // 收货人
			ctv_member_tel, // 联系方式
			ctv_member_add, // 收货地址
			tv_count_payed, // 价格
			tv_state, // 订单状态
			// tv_voucher, // 发票类型
			ctv_voucher_header; // 发票抬头
	// private LinearLayout ll_trans_info;// 物流信息正常 显示布局
	private LinearLayout lin_pay_state;
	// private TextView ctv_confirm_receive;
	private TextView tv_pay_state, // 支付方式
			// tv_zhihuika_pay, // 页面智慧卡支付金额
			tv_saoma_pay, // 页面扫码支付金额
			ctv_voucher_content, // 发票内容
			tv_liuyan, // 买家留言
			tv_goods_price, // 图书总额
			tv_yunfei, // 运费
			tv_date, //
			tv_pay_shouxufei, // 手续费
			tv_youhuiquan_code, // 优惠券抵扣
			tv_jiajian1, // 加减标题
			tv_jiajian, // 加减价
			tv_jifen_code, // 积分
			tv_jifen_msg, // 积分规则
			tv_order;// 优惠券
	private LinearLayout lin_pay;
	private RelativeLayout rel_jiajian, lin_pay_shouxufei, rel_jifen;
	private OrderList orderList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orderList = (OrderList) getIntent().getSerializableExtra("model");
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	// TextView ctvName;
	// RelativeLayout rlWaitReceive;
	// LinearLayout llCompleted;
	private TextView tv_cancel;
	private TextView tv_pay;

	protected void initView() {
		setContentView(R.layout.order_details_activity_layout);

		tv_jifen_code = (TextView) findViewById(R.id.tv_jifen_code);
		tv_jifen_msg = (TextView) findViewById(R.id.tv_jifen_msg);
		rel_jifen = (RelativeLayout) findViewById(R.id.rel_jifen);
		tv_pay_shouxufei = (TextView) findViewById(R.id.tv_pay_shouxufei);
		tv_youhuiquan_code = (TextView) findViewById(R.id.tv_youhuiquan_code);
		tv_jiajian = (TextView) findViewById(R.id.tv_jiajian);
		tv_jiajian1 = (TextView) findViewById(R.id.tv_jiajian1);
		lin_pay = (LinearLayout) findViewById(R.id.lin_pay);
		rel_jiajian = (RelativeLayout) findViewById(R.id.rel_jiajian);
		lin_pay_shouxufei = (RelativeLayout) findViewById(R.id.lin_pay_shouxufei);

		// ll_trans_info = (LinearLayout) findViewById(R.id.ll_trans_info);
		tv_pay = (TextView) findViewById(R.id.tv_pay);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_order = (TextView) findViewById(R.id.tv_order);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_pay.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		tv_pay_state = (TextView) findViewById(R.id.tv_pay_state);
		// tv_zhihuika_pay = (TextView) findViewById(R.id.tv_zhihuika_pay);
		tv_saoma_pay = (TextView) findViewById(R.id.tv_saoma_pay);
		ctv_voucher_content = (TextView) findViewById(R.id.ctv_voucher_content);
		tv_liuyan = (TextView) findViewById(R.id.tv_liuyan);
		tv_goods_price = (TextView) findViewById(R.id.tv_goods_price);
		tv_yunfei = (TextView) findViewById(R.id.tv_yunfei);
		lin_pay_state = (LinearLayout) findViewById(R.id.lin_pay_state);

		// ctvName = (TextView) findViewById(R.id.tv_name);// 页面标题
		// rlWaitReceive = (RelativeLayout)
		// findViewById(R.id.rl_wait_receive);// 等待收获状态
		// rlWaitPay = (RelativeLayout) findViewById(R.id.rl_wait_pay);//
		// llCompleted = (LinearLayout) findViewById(R.id.ll_completed);

		// ctv_time_sendout = (TextView) findViewById(R.id.ctv_time_sendout);
		// ctv_order_num = (TextView) findViewById(R.id.ctv_order_num);
		ctv_member_name = (TextView) findViewById(R.id.ctv_member_name);
		tv_state = (TextView) findViewById(R.id.tv_state);
		// ctv_logist_message = (TextView)
		// findViewById(R.id.ctv_logist_message);
		// ctv_logist_time = (TextView) findViewById(R.id.ctv_logist_time);
		ctv_member_tel = (TextView) findViewById(R.id.ctv_member_tel);
		ctv_member_add = (TextView) findViewById(R.id.ctv_member_add);
		tv_count_payed = (TextView) findViewById(R.id.tv_count_payed);
		tv_goods_price = (TextView) findViewById(R.id.tv_goods_price);

		// tv_voucher = (TextView) findViewById(R.id.tv_voucher);
		ctv_voucher_header = (TextView) findViewById(R.id.ctv_voucher_header);

		// findViewById(R.id.ctv_order_delelte).setOnClickListener(this);
		// findViewById(R.id.ctv_buy_again).setOnClickListener(this);
		// findViewById(R.id.ctv_pay).setOnClickListener(this);
		// findViewById(R.id.ctv_cancel).setOnClickListener(this);
		// findViewById(R.id.ctv_goods_matain).setOnClickListener(this);
		// findViewById(R.id.ctv_order_appraise).setOnClickListener(this);
		// ctv_confirm_receive = (TextView)
		// findViewById(R.id.ctv_confirm_receive);
		// ctv_confirm_receive.setOnClickListener(this);

		// lin_wait_received = (LinearLayout)
		// findViewById(R.id.lin_wait_received);
		gv_order_confirm = (ListView) findViewById(R.id.gv_order_confirm);

		// findViewById(R.id.back).setOnClickListener(this);
		// initState();
	}

	@SuppressLint("NewApi")
	private void initState() {
//		switch (state) {
//		case 1:
//			// ctvName.setText("等待付款");
//			// tv_state.setText("待付款");
//			// rlTransInfo.setVisibility(View.GONE);
//			// rlWaitPay.setVisibility(View.VISIBLE);
//			tv_pay.setText("立即支付");
//			tv_pay.setTextColor(getResources().getColor(R.color.white));
//			tv_pay.setBackground(getResources().getDrawable(R.drawable.order_pay_bg2));
//			tv_pay.setTag(1);
//			tv_pay.setVisibility(View.VISIBLE);
//			tv_cancel.setVisibility(View.VISIBLE);
//			tv_cancel.setText("取消订单");
//			tv_cancel.setTag(1);// 取消
//			break;
//		case 2:
//			// tv_state.setText("待发货");
//			// rlTransInfo.setVisibility(View.VISIBLE);
//			tv_pay.setVisibility(View.GONE);
//			tv_cancel.setVisibility(View.GONE);
//			tv_cancel.setText("取消订单");
//			tv_cancel.setTag(1);// 取消
//			break;
//		case 3:
//			// tv_state.setText("待收货");
//			// rlTransInfo.setVisibility(View.VISIBLE);
//			tv_pay.setVisibility(View.GONE);
//			tv_cancel.setVisibility(View.VISIBLE);
//			tv_cancel.setText("确认收货");
//			tv_cancel.setTag(2);// 确认收货
//			break;
//		case 4:
//		case 5:
//			// tv_state.setText("已完成");
//			// rlTransInfo.setVisibility(View.VISIBLE);
//			tv_cancel.setVisibility(View.GONE);
//			tv_cancel.setText("晒单评价");
//			tv_pay.setVisibility(View.GONE);
//			tv_cancel.setTag(3);// 晒单评价
//			break;
//		case 12:
//			// tv_state.setText("待退款");
//			tv_cancel.setText("申请退款");
//			tv_cancel.setTag(4);
//			tv_pay.setVisibility(View.GONE);
//			tv_cancel.setVisibility(View.GONE);
//			break;
//		}
		// tv_state.setText(Data.getState_value());
	}

	private void initState(int state) {
		String str = "订单已提交，等待系统确认";
		switch (state) {
		case 1:
			str = "订单已取消";
			break;
		case 5:
			str = "订单已提交，等待系统确认";
			break;
		case 7:
			str = "系统已确认，正在准备出库";
			break;
		case 9:
			str = "图书已出库，正在准备发货";
			break;
		case 11:
			str = "快递员已收件，请注意查收";
			break;
		case 99:
			str = "已收货，订单已完成";
			break;
		}
		ctv_is_delivery.setText(str);
	}

	protected void initData() {
		// // ApplicationVar.requestType.ORDERDETAIL);
		// XmlUtils xmlUtils = new XmlUtils(mContext);
		// areaModels = xmlUtils.initAreaDatas();
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("user_id", ApplicationVar.getUserId(this));
		// // params.put("user_id", 43916 + "");
		// params.put("order_id", orderSN + "");
		// c2BHttpRequest.postHttpResponse1(UriFactory.getUri(ApplicationVar.ORDERDETAIL),
		// params,
		// ApplicationVar.requestType.ORDERDETAIL);
	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;
		listView.setLayoutParams(params);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// if (isBack) {
		// draglayout.currenItem(500);
		// } else {
		finish();
		// }

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.back:
		// finish();
		// break;
		// case R.id.ctv_confirm_receive:// 确认收货
		// CommonHintDialog dialog = new CommonHintDialog(mContext,
		// R.style.dialog, 3);
		// dialog.setPosition(1);
		// dialog.setOnCheckedChanged(this);
		// dialog.show();
		// break;
		case R.id.tv_pay:// 立即付款
			int tag1 = (Integer) v.getTag();
			switch (tag1) {
			case 1:// 立即付款
//				Bundle bundle = new Bundle();
//				bundle.putString("orderPrice", Data.getPaymentAmount() + "");
//				bundle.putString("orderSN", Data.getOrderId());
//				openActivity(PayActivity.class, bundle);
//				finish();
				break;
			}
			break;
		}

	}

	@Override
	public void OnResponse(String OrderDetailResult, int connectType) {
		if (null != OrderDetailResult) {
			switch (connectType) {
			case 1:// 订单详情
				refreshUI(OrderDetailResult);
				break;
			}
		}
	}

	private void refreshUI(String OrderDetailResult) {/*
		OrderDetailResponseModel msg = DataPaser.json2Bean(OrderDetailResult, OrderDetailResponseModel.class);
		if (msg.getReturn_code().equals("SUCCESS")) {
			Data = msg.result;
			if (Data.getPayCharge() > 0) {
				lin_pay_shouxufei.setVisibility(View.VISIBLE);
				tv_pay_shouxufei.setText(Utils.setdoublePlaces(Data.getPayCharge()));
			} else {
				lin_pay_shouxufei.setVisibility(View.GONE);
			}
			if (Data.getPayCharge() == 0) {
				lin_pay_shouxufei.setVisibility(View.GONE);
			} else {
				lin_pay_shouxufei.setVisibility(View.VISIBLE);
				tv_pay_shouxufei.setText("¥" + Utils.setdoublePlaces(Data.getPayCharge()));
			}
			tv_yunfei.setText("¥" + Utils.setdoublePlaces(Data.getAdjustedFreight()));
			tv_youhuiquan_code.setText("¥" + Utils.setdoublePlaces(Data.getCouponValue()));

			tv_jiajian.setText("¥" + Utils.setdoublePlaces(Data.getAdjustedDiscount()));
			if (Data.getAdjustedDiscount() > 0) {
				tv_jiajian1.setText("+加价");
				rel_jiajian.setVisibility(View.VISIBLE);
			} else if (Data.getAdjustedDiscount() < 0) {
				tv_jiajian1.setText("-减价");
				rel_jiajian.setVisibility(View.VISIBLE);
			} else {
				rel_jiajian.setVisibility(View.GONE);
			}
			if (Data.isIs_waybillcode()) {
				tv_look_wuliu.setVisibility(View.VISIBLE);
			} else {
				tv_look_wuliu.setVisibility(View.GONE);
			}
			if (null != Data.getPoint()) {
				if (Data.getPoint().getIs_pointpay() == 0) {// 不包含积分支付
					rel_jifen.setVisibility(View.GONE);
				} else {// 否则包含
					rel_jifen.setVisibility(View.VISIBLE);
					tv_jifen_msg.setText(Data.getPoint().getPoint_msg());
					tv_jifen_code.setText("¥" + Utils.setdoublePlaces(Data.getPoint().getPoint_fee()));
				}
			}
			OrderDetailsAdapter OWAdapter = new OrderDetailsAdapter(mContext, msg.result.getCartItem());
			gv_order_confirm.setAdapter(OWAdapter);
			setListViewHeightBasedOnChildren(gv_order_confirm);
			// ctv_time_sendout.setText(Data.getOrderDate());
			tv_order.setText(Data.getOrderId());
			tv_date.setText(Data.getOrderDate().replace("T", " "));

			ctv_member_name.setText(Data.getShipTo());
			ctv_member_tel.setText(Data.getCellPhone());
			// ctv_member_add.setText(Data.Basic.Address);
			if (state == 1) {
				lin_pay_state.setVisibility(View.GONE);
			} else {
				tv_pay_state.setText(Data.getPaymentType());
				lin_pay_state.setVisibility(View.VISIBLE);
				switch (Data.getPaymentTypeId()) {
				case 2:// 网银支付
					tv_saoma_pay.setText("银联支付：¥" + Utils.setdoublePlaces(Data.getThirdPartyPayment()));
					// tv_zhihuika_pay.setVisibility(View.GONE);
					break;
				case 99:// 微信支付
					tv_saoma_pay.setText("微信支付：¥" + Utils.setdoublePlaces(Data.getThirdPartyPayment()));
					// tv_zhihuika_pay.setVisibility(View.GONE);
					break;
				case 88:// 智慧卡支付
					// tv_zhihuika_pay.setText("智慧卡支付：¥" +
					// Utils.setdoublePlaces(Data.getScarPay()));
					// tv_zhihuika_pay.setVisibility(View.VISIBLE);
					tv_saoma_pay.setText("智慧卡支付：¥" + Utils.setdoublePlaces(Data.getScarPay()));
					break;
				case 86:// 智慧卡银联支付
					// tv_zhihuika_pay.setVisibility(View.VISIBLE);
					tv_saoma_pay.setText("智慧卡+银联支付：¥" + Utils.setdoublePlaces(Data.getThirdPartyPayment()));
					break;
				case 87:// 智慧卡扫码支付
					// tv_zhihuika_pay.setVisibility(View.VISIBLE);
					tv_saoma_pay.setText("智慧卡+扫码支付：¥" + Utils.setdoublePlaces(Data.getThirdPartyPayment()));
					break;
				}
			}
			if (null != Data.getInvoiceTitle()) {
				if (!Data.getInvoiceTitle().replace(" ", "").equals("")) {
					ctv_voucher_header.setText("发票抬头：" + Data.getInvoiceTitle());
					ctv_voucher_content.setText("发票内容：图书");
				} else {
					ctv_voucher_header.setText("发票抬头：无");
					ctv_voucher_content.setText("发票内容：图书");
				}
			} else {
				ctv_voucher_header.setText("发票抬头：无");
				ctv_voucher_content.setText("发票内容：图书");
			}
			tv_liuyan.setText(Data.getRemark());
			// tv_goods_price.setText("¥" +
			// Utils.setdoublePlaces(Data.getGoods_amount()));

			String area1 = "";
			String area = "";
			String province = "";
			String CityName = "";
			String TownName = "";
			for (AreaModel a : areaModels) {
				for (ProvinceModel p : a.getProvinceList()) {
					for (CityModel c : p.getCityList()) {
						if (c.getId() == Data.getRegionId()) {
							area = a.getName();
							province = p.getName();
							CityName = c.getName();
						} else {
							for (DistrictModel d : c.getDistrictList()) {
								if (d.getId() == Data.getRegionId()) {
									area = a.getName();
									province = p.getName();
									CityName = c.getName();
									TownName = d.getName();
								}
							}
						}
					}
				}
			}
			area1 = area + province + CityName + TownName;
			ctv_member_add.setText(area1 + Data.getAddress());
			tv_count_payed.setText("¥" + Utils.setdoublePlaces(Data.getPaymentAmount()));
			tv_goods_price.setText("¥" + Utils.setdoublePlaces(Data.getGoods_amount()));
			String str = "";
			// switch (Data.Basic.InvoiceType) {
			// case 1:
			// str = "不需要发票";
			// break;
			// case 3:
			// str = "普通发票";
			// break;
			// case 5:
			// str = "增值税发票";
			// break;
			// }
			// tv_voucher.setText(str);
			// if (null != Data.Basic.InvoiceTitle) {
			// ctv_voucher_header.setText("（发票抬头：" + Data.Basic.InvoiceTitle +
			// "）");
			// }
			mPullToRefreshView.onFooterRefreshComplete();
			mPullToRefreshView.onHeaderRefreshComplete();
			initState(Data.getOrderStatus());
			initState();
		} else {
			ToastUtil.showMessage(getApplicationContext(), msg.getReturn_msg());
		}
	*/}


	@Override
	public void getChoiceData(int position) {
		switch (position) {
		case 1:// 确认收货
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("user_id", ApplicationVar.getUserId(this));
//			// params.put("user_id", 43916 + "");
//			params.put("order_id", orderSN + "");
//			c2BHttpRequest.setSize(false);
//			c2BHttpRequest.postHttpResponse1(UriFactory.getUri(ApplicationVar.PRODUCTARRIVE), params,
//					ApplicationVar.requestType.PRODUCTARRIVE);
			break;
		case 2:// 取消订单
//			Map<String, String> params1 = new HashMap<String, String>();
//			params1.put("user_id", ApplicationVar.getUserId(this));
//			// params.put("user_id", 43916 + "");
//			params1.put("order_id", orderSN + "");
//			// params1.put("payment_typeid", Data.getPaymentTypeId() + "");
//			c2BHttpRequest.setSize(false);
//			c2BHttpRequest.postHttpResponse1(UriFactory.getUri(ApplicationVar.ORDERDELETE), params1,
//					ApplicationVar.requestType.ORDERDELETE);
			break;
		case 4:

			break;
		}
	}

}

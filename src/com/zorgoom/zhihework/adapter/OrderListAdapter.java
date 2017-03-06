package com.zorgoom.zhihework.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.app.JwyBaseAdapter;
import com.zorgoom.zhihework.OrderDetailsActivity;
import com.zorgoom.zhihework.PayActivity;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.base.db.entity.ShopsCart;
import com.zorgoom.zhihework.vo.Detail;
import com.zorgoom.zhihework.vo.Master;
import com.zorgoom.zhihework.vo.OrderList;
import com.zorgoom.zhihework.vo.OrderRequest;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @ClassName: OrderUnpayAdapter
 * @Description: TODO(订单列表适配器)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年8月31日
 * @修改时间 2015年8月31日
 */
public class OrderListAdapter extends JwyBaseAdapter<OrderList> {
	private LayoutInflater inflater; // 布局填充器
	private Handler handler;
	private List<OrderUnPayGoodAdapter> adapters;

	public OrderListAdapter(Context mContext, List<OrderList> list) {
		super(mContext, list);
		inflater = LayoutInflater.from(mContext);
		adapters = new ArrayList<OrderUnPayGoodAdapter>();
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	// public void destroy() {
	// if (null != adapters) {
	// for (int i = 0; i < adapters.size(); i++) {
	// OrderUnPayGoodAdapter adapter = adapters.get(i);
	// for (int j = 0; j < adapter.getCount(); j++) {
	// View view = adapter.getView(j, null, null);
	// ImageView imageView = (ImageView) view.findViewById(R.id.iv_img);
	// if (null != imageView) {
	// imageView.setImageDrawable(null);
	// imageView.destroyDrawingCache();
	// view.destroyDrawingCache();
	// }
	// }
	// }
	// }
	// }

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.order_list_adapter_layout, null);
			holder = new ViewHolder();
			holder.lv_order_item = (ListView) convertView.findViewById(R.id.lv_order_item);
			holder.tv_should_pay = (TextView) convertView.findViewById(R.id.tv_should_pay);
			holder.tv_order_state = (TextView) convertView.findViewById(R.id.tv_order_state);
			holder.tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);
			holder.ill_order = (LinearLayout) convertView.findViewById(R.id.ill_order);
			holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
			holder.tv_cancel = (TextView) convertView.findViewById(R.id.tv_cancel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final OrderList model = list.get(position);
		holder.shop_name.setText(model.getMaster().getSHOPNAME());
		holder.tv_should_pay.setText("￥" + model.getMaster().getTOTALVAL());

		OrderUnPayGoodAdapter adapter = new OrderUnPayGoodAdapter(mContext, model.getDetail());
		adapters.add(adapter);
		holder.lv_order_item.setAdapter(adapter);
		Util.setListViewHeightBasedOnChildren(mContext, holder.lv_order_item);
		//
		switch (model.getMaster().getSTATE()) {
		case "WZF":// 未支付
			holder.tv_order_state.setText("待付款");

			holder.tv_pay.setText("立即支付");
			holder.tv_pay.setTextColor(mContext.getResources().getColor(R.color.white));
			holder.tv_pay.setBackground(mContext.getResources().getDrawable(R.drawable.order_pay_bg2));
			holder.tv_pay.setVisibility(View.VISIBLE);
			holder.tv_pay.setTag(1);

			holder.tv_cancel.setVisibility(View.VISIBLE);
			holder.tv_cancel.setText("取消订单");
			holder.tv_cancel.setTag(1);
			break;
		case "CFN":// 已支付
			holder.tv_order_state.setText("待发货");
			holder.tv_pay.setVisibility(View.GONE);
			holder.tv_cancel.setVisibility(View.GONE);
			break;
		case "PSZ":// 配送中
			holder.tv_order_state.setText("配送中");
			holder.tv_pay.setText("确认收货");
			holder.tv_pay.setTextColor(mContext.getResources().getColor(R.color.white));
			holder.tv_pay.setBackground(mContext.getResources().getDrawable(R.drawable.order_pay_bg2));
			holder.tv_pay.setTag(2);

			holder.tv_cancel.setVisibility(View.GONE);
			break;
		case "RVD":// 已收货
			holder.tv_order_state.setText("已收货");
			holder.tv_pay.setVisibility(View.GONE);
			holder.tv_cancel.setVisibility(View.GONE);
			break;
		case "YPS":// 已配送
			holder.tv_order_state.setText("订单完成");
			holder.tv_pay.setVisibility(View.GONE);
			holder.tv_cancel.setVisibility(View.GONE);
			break;
		}
		holder.tv_pay.setOnClickListener(new MyOnClickListener(position));
		holder.tv_cancel.setOnClickListener(new MyOnClickListener(position));

		holder.lv_order_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
				Intent intent = new Intent(mContext, OrderDetailsActivity.class);
				intent.putExtra("model", model);
				mContext.startActivity(intent);
			}

		});
		return convertView;
	}

	class MyOnClickListener implements OnClickListener {
		private int position;

		public MyOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Intent intent;
			switch (v.getId()) {/*
			case R.id.tv_cancel:
				int tag = (int) v.getTag();
				switch (tag) {
				case 1:// 取消订单
					handler.obtainMessage(2, 0, 0, list.get(position)).sendToTarget();
					break;
				}
				break;
			case R.id.tv_pay:
				int t = (int) v.getTag();
				switch (1) {
				case 1:// 支付
					// intent = new Intent(mContext, PayActivity.class);
					// intent.putExtra("orderSN",
					// list.get(position).getOrderId());
					// intent.putExtra("orderPrice",
					// list.get(position).getPaymentAmount() + "");
					// mContext.startActivity(intent);
					OrderRequest data = new OrderRequest();
					Master master = new Master();
					master.setAddress(
							address.getPROVINCE() + address.getCITY() + address.getDISTRICT() + address.getADDRESS());
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
					intent.putExtra("data", gson.toJson(data));
					intent.putExtra("SHOPID", shopid + "");
					intent.putExtra("NAME", NAME);
					intent.putExtra("IMG", IMG);
					intent.putExtra("price", price + "");
					startActivity(intent);
					((Activity) mContext).finish();
					break;
				case 3:// 确认收货
					handler.obtainMessage(1, 0, 0, list.get(position)).sendToTarget();
					break;
				}
				break;
			*/}
		}

	}

	class ViewHolder {
		ListView lv_order_item;
		TextView tv_order_state;
		TextView tv_pay, tv_cancel;
		// TextView tv_date;// 订单时间
		TextView tv_should_pay;// 应付款
		// InterceptLinearlayout ill_order;

		TextView shop_name;
		LinearLayout ill_order;
		// TextView tv_order_num;// 图书数量
	}

}

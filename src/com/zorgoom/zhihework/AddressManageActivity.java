package com.zorgoom.zhihework;

import java.io.Serializable;


import com.zorgoom.zhihework.adapter.AddressManageAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.AddressListVo;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @ClassName: AddressManageActivity
 * @Description: TODO(收货地址管理)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年8月18日
 * @修改时间 2015年8月18日
 */
@SuppressLint("NewApi")
public class AddressManageActivity extends BaseActivity implements OnClickListener, HttpListener {
	private AddressManageAdapter addressManageAdapter;
	private ListView lv_address_manage;
	private int indexSelect;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_manage_activity_layout);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

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
		indexSelect = getIntent().getIntExtra("indexSelect", 0);
		findViewById(R.id.tv_add_address).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		lv_address_manage = (ListView) findViewById(R.id.lv_address_manage);
		findViewById(R.id.tv_add_address).setOnClickListener(this);
		lv_address_manage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				addressManageAdapter.setIndexSelect(position);
				addressManageAdapter.notifyDataSetChanged();
				Intent intent = new Intent();
				intent.putExtra("address", (Serializable) listVo.getData().get(position));
				intent.putExtra("indexSelect", position);
				setResult(2, intent);
				finish();
			}
		});
	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	protected void initData() {
		String userId = PrefrenceUtils.getStringUser("userId", this);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId + "", timestamp);

		c2BHttpRequest.getHttpResponse(Http.GETADRRBYUSERID + "USERID=" + userId + "&OPERID="
				+ PrefrenceUtils.getStringUser("OPERID", this) + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		case R.id.tv_add_address:// 新增收货地址
			Intent intent = new Intent(this, AddressEditActivity.class);
			startActivityForResult(intent, 1);
			break;

		}
	}

	

	private AddressListVo listVo;

	@Override
	public void OnResponse(String result, int connectType) {
		if (null != result) {
			switch (connectType) {
			case 1:
				listVo = DataPaser.json2Bean(result, AddressListVo.class);
				if (null != listVo) {
					if (listVo.getCode().equals("101")) {
						addressManageAdapter = new AddressManageAdapter(this, listVo.getData());
						// addressManageAdapter.setOnCheckedChanged(this);
						addressManageAdapter.setIndexSelect(indexSelect);
						lv_address_manage.setAdapter(addressManageAdapter);
					} else {
						ToastUtil.showMessage(getApplicationContext(), listVo.getMsg());
					}
				}
				break;
			}
		}

	}

	int position;

}

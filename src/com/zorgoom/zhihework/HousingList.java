package com.zorgoom.zhihework;

import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;
import com.zorgoom.zhihework.adapter.HousingListAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.service.MainService;
import com.zorgoom.zhihework.view.CommonHintDialog;
import com.zorgoom.zhihework.view.CommonHintDialog.onCheckedChanged;
import com.zorgoom.zhihework.vo.RsHousing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * 部门列表
 * 
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class HousingList extends MBaseActivity implements View.OnClickListener, HttpListener, onCheckedChanged {

	private ListView message_listView1;
	private String onResponseResult;
	private C2BHttpRequest c2BHttpRequest;
	private int index;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.housing_laout);
		index = getIntent().getIntExtra("index", 0);
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		c2BHttpRequest.setShow(true);
		initData();
	}

	private void initData() {
		String MOBILE = PrefrenceUtils.getStringUser("MOBILE", this);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(MOBILE, timestamp);

		c2BHttpRequest.getHttpResponse(Http.GETMYUNIT + "MOBILE=" + MOBILE + "&FKEY=" + key + "&TIMESTAMP=" + timestamp,
				1);
	}

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

		message_listView1 = (ListView) findViewById(R.id.message_listView1);
		message_listView1.setOnItemClickListener(new myOnitemClick());
		message_listView1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				CommonHintDialog dialog = new CommonHintDialog(HousingList.this, R.style.dialog, 3);
				dialog.setPosition(position);
				dialog.setOnCheckedChanged(HousingList.this);
				dialog.show();
				return true;
			}
		});

		findViewById(R.id.add_housing).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
	}

	public class myOnitemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, final int position, long id) {
			Util.showLoadDialog(HousingList.this, "房屋切换中,请稍候...");
			new Thread() {
				public void run() {
					// COMPANY infoVO =
					// rsPropertypaymentListResultVO.getData().get(position);
					// PrefrenceUtils.saveUser("COMPANY",
					// infoVO.getCOMPANYNAME() + infoVO.getDEPTNAME() + "部门",
					// getApplicationContext());
					// PrefrenceUtils.saveUser("DEPTID", infoVO.getDEPTID() +
					// "", getApplicationContext());
					// PrefrenceUtils.saveUser("COMPANYID",
					// infoVO.getCOMPANYID() + "", getApplicationContext());
					// PrefrenceUtils.saveUser("COMPANYNAME",
					// infoVO.getCOMPANYNAME() + "", getApplicationContext());
					// PrefrenceUtils.saveUser("DEPTNAME", infoVO.getDEPTNAME()
					// + "", getApplicationContext());
					// PrefrenceUtils.saveUser("OPERID", infoVO.getOPERID() +
					// "", getApplicationContext());
					// PrefrenceUtils.saveUser("state", "2",
					// getApplicationContext());
					// PrefrenceUtils.saveUser("CALLINFO",
					// infoVO.getCOMPANYID() + "" + infoVO.getDEPTID() ,
					// getApplicationContext());
					// PrefrenceUtils.saveUser("CALLINFO", "" +
					// infoVO.getUNITNO(),
					// getApplicationContext());
					// sendBroadcast(new Intent(MainService.CALL));
					// handler.sendEmptyMessage(1);
				};
			}.start();
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 1:
				ToastUtil.showMessage(getApplicationContext(), "切换成功");
				Util.dismissLoadDialog();
				finish();
				break;
			case 2:
				ToastUtil.showMessage(getApplicationContext(), "认证成功");
				Util.dismissLoadDialog();
				break;
			}
		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		case R.id.add_housing:
			openActivity(HousingAuthorityActivity.class);
			break;
		}
	}

	RsHousing rsPropertypaymentListResultVO;
	private HousingListAdapter myadapter;

	@Override
	public void OnResponse(String result, int reqId) {
		onResponseResult = result;
		if (onResponseResult != null) {
			switch (reqId) {
			case 1:
				rsPropertypaymentListResultVO = DataPaser.json2Bean(onResponseResult, RsHousing.class);
				if (rsPropertypaymentListResultVO != null) {
					if ("101".equals(rsPropertypaymentListResultVO.getCode())) {
						// if (rsPropertypaymentListResultVO.getData().size() ==
						// 0) {
						ToastUtil.showMessage1(this, "当前没有消息数据！", 300);
						return;
					}
					// myadapter = new HousingListAdapter(this,
					// rsPropertypaymentListResultVO.getData());
					message_listView1.setAdapter(myadapter);

					if (index == 1) {// 推送认证
						Util.showLoadDialog(this, "认证中,请稍候...");
						new Thread() {
							public void run() {
								// COMPANY infoVO =
								// rsPropertypaymentListResultVO.getData().get(0);
								// PrefrenceUtils.saveUser("COMPANY",
								// infoVO.getCOMPANYNAME() +
								// infoVO.getDEPTNAME() + "部门",
								// getApplicationContext());
								// PrefrenceUtils.saveUser("UNITID",
								// infoVO.getUNITID() + "",
								// getApplicationContext());
								// PrefrenceUtils.saveUser("BLOCKID",
								// infoVO.getBLOCKID() + "",
								// getApplicationContext());
								// PrefrenceUtils.saveUser("BLOCKNO",
								// infoVO.getBLOCKNO() + "",
								// getApplicationContext());
								// PrefrenceUtils.saveUser("UNITAREA",
								// infoVO.getUNITAREA() + "",
								// getApplicationContext());
								// PrefrenceUtils.saveUser("COMMUNITYID",
								// infoVO.getCOMMUNITYID() + "",
								// getApplicationContext());
								// PrefrenceUtils.saveUser("state", "2",
								// getApplicationContext());
								// PrefrenceUtils.saveUser("CALLINFO",
								// infoVO.getCOMMUNITYID() + ""
								// + infoVO.getBLOCKNO() + "" +
								// infoVO.getUNITNO(), getApplicationContext());
								// PrefrenceUtils.saveUser("CALLINFO", "" +
								// infoVO.getUNITNO(),
								// getApplicationContext());
								sendBroadcast(new Intent(MainService.CALL));
								// runOnUiThread(new Runnable() {
								// @Override
								// public void run() {
								// ToastUtil.showMessage(getApplicationContext(),
								// "认证成功");
								// Util.dismissLoadDialog();
								// }
								// });
								handler.sendEmptyMessage(2);
							};
						}.start();
					}
				}
			}
			// break;

			// case 2:// 删除房屋
			// BaseModel guestPass = DataPaser.json2Bean(result,
			// BaseModel.class);
			// if (null != guestPass) {
			// if ("101".equals(guestPass.getCode())) {
			// ToastUtil.showMessage(this, guestPass.getMsg());
			// rsPropertypaymentListResultVO.getData().remove(position);
			// myadapter.setDate(rsPropertypaymentListResultVO.getData());
			// } else {
			// ToastUtil.showMessage(this, guestPass.getMsg());
			// }
			// }
			// break;
		}

	}
	// }

	private int position;

	@Override
	public void getChoiceData(int position) {
		this.position = position;

		// int rid =
		// rsPropertypaymentListResultVO.getData().get(position).getRID();
		// String timestamp = System.currentTimeMillis() + "";
		// String key = c2BHttpRequest.getKey(rid + "", timestamp);
		//
		// c2BHttpRequest.getHttpResponse(Http.DELETEMYUNIT + "RID=" + rid +
		// "&FKEY=" + key + "&TIMESTAMP=" + timestamp,
		// 2);
	}
}

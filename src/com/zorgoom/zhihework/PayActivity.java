package com.zorgoom.zhihework;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.adapter.ImageLoadUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.MyActivityManager;
import com.zorgoom.zhihework.base.db.dao.ShopsCartDao;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.pay.weixin.WxPay;
import com.zorgoom.zhihework.view.downview.CountdownView;
import com.zorgoom.zhihework.view.downview.CountdownView.OnCountdownEndListener;
import com.zorgoom.zhihework.vo.Detail;
import com.zorgoom.zhihework.vo.OrderRequest;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: NewsActivity
 * @Description: TODO(支付Activiyt)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年9月16日
 * @修改时间 2015年9月16日
 */
@SuppressLint("NewApi")
public class PayActivity extends BaseActivity implements OnClickListener, HttpListener {
	private TextView login_textView04;
	private int index;
	private ImageView wx_iv,zfb_iv;
	private RequestParams params;
	private EditText money;
	private RelativeLayout wx_pay,zfb_pay;

//	private BroadcastReceiver receiver = new BroadcastReceiver() {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if (action.equals(PAYWINXIN)) {
//				int errCode = intent.getIntExtra("errCode", 2);
//				switch (errCode) {
//				case 0:
//					ToastUtil.showMessage(getApplicationContext(), "支付成功");
//					onFinish();
//					break;
//				case -1:
//					break;
//				case -2:
//					ToastUtil.showMessage(getApplicationContext(), "取消支付");
//					break;
//				}
//			}
//		}
//	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_activity_layout);
		initData();
		initView();
	}

	C2BHttpRequest c2BHttpRequest = null;

	protected void initView() {

//		cb_setdefault = (ImageView) findViewById(R.id.cb_setdefault);
//		cb_setdefault.setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		login_textView04 = (TextView) findViewById(R.id.login_textView04);
		login_textView04.setOnClickListener(this);
		money=(EditText) findViewById(R.id.money);
		wx_pay=(RelativeLayout) findViewById(R.id.wx_pay);
				wx_pay.setOnClickListener(this);
		zfb_pay=(RelativeLayout) findViewById(R.id.zfb_pay);
				zfb_pay.setOnClickListener(this);
		wx_iv = (ImageView) findViewById(R.id.wx_iv);
		zfb_iv= (ImageView) findViewById(R.id.zfb_iv);

	}

	public final static String PAYWINXIN = "BROADCASTVAR_PAYWINXIN";

	protected void initData() {
//		imageLoadObj = ImageLoadUtils.initImageLoad(this);
		c2BHttpRequest = new C2BHttpRequest(this, this);
		// params = (RequestParams) getIntent().getSerializableExtra("params");
		// index = getIntent().getIntExtra("index", -1);
		// orderPrice = getIntent().getStringExtra("price");
		// NAME = getIntent().getStringExtra("NAME");
		// IMG = getIntent().getStringExtra("IMG");
		// data = getIntent().getStringExtra("data");
		// SHOPID = getIntent().getStringExtra("SHOPID");
		// IntentFilter intentFilter = new IntentFilter();
		// intentFilter.addAction(PAYWINXIN);
		// registerReceiver(receiver, intentFilter);

	}

	OrderRequest orderRequest;

//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		if (receiver != null) {
//			unregisterReceiver(receiver);
//		}
//	}

	@SuppressLint("NewApi")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			onExit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	//
	@SuppressLint("NewApi")
	private void onExit() {
		// super.onBackPressed();
		// 创建对话框的构造器，可以帮我们构造对话框的模版
		AlertDialog.Builder builder = new Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		// 设置对话框的 标题
		builder.setTitle("支付：");
		// 设置对话框的提示信息
		builder.setMessage("确定退出支付吗");
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		// 通过构造器创建一个对话框对象
		AlertDialog ad = builder.create();
		// 把对话的界面显示出来
		ad.show();
	}

	private int selectIndex = 1;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
//			onExit();
			break;
		case R.id.wx_pay:
			wx_iv.setImageResource(R.drawable.checkbox_true);
			zfb_iv.setImageResource(R.drawable.checkbox_false);
			break;
		case R.id.zfb_pay:
			wx_iv.setImageResource(R.drawable.checkbox_false);
			zfb_iv.setImageResource(R.drawable.checkbox_true);
			ToastUtil.showMessage(this, "暂未开放支付宝充值接口！");
			break;
//		case R.id.cb_setdefault:
//			if (selectIndex == 1) {
//				selectIndex = 0;
//				cb_setdefault.setBackground(getResources().getDrawable(R.drawable.checkbox_false));
//			} else {
//				selectIndex = 1;
//				cb_setdefault.setBackground(getResources().getDrawable(R.drawable.checkbox_true));
//			}
//			break;
		case R.id.login_textView04:
			params = new RequestParams();
			params.addBodyParameter("USERID", PrefrenceUtils.getStringUser("userId", this));
			if (selectIndex == 0) {
				ToastUtil.showMessage(getApplicationContext(), "请选择支付方式");
				return;
			}
			params.addBodyParameter("COMPANYID", PrefrenceUtils.getStringUser("COMPANYID", this));
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(PrefrenceUtils.getStringUser("userId", this) + "", timestamp);
			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);
			params.addBodyParameter("DEPTID", PrefrenceUtils.getStringUser("DEPTID", this));
			// if (index == 0) {// 物业账单支付
			// String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID",
			// this);
			// String timestamp = System.currentTimeMillis() + "";
			// String key = c2BHttpRequest.getKey(COMMUNITYID + "", timestamp);
			//
			// params.addBodyParameter("FKEY", key);
			// params.addBodyParameter("TIMESTAMP", timestamp);
			//
			// params.addBodyParameter("OPERID",
			// PrefrenceUtils.getStringUser("OPERID", this));
			// params.addBodyParameter("COMMUNITYID", COMMUNITYID);
			// params.addBodyParameter("ORDERNUM", uuid);
			// params.addBodyParameter("VAL", orderPrice);
			params.addBodyParameter("VAL", money.getText().toString());
			params.addBodyParameter("TYPE", "CZ");
			// params.addBodyParameter("PAYTYPE", "W");
			 params.addBodyParameter("PLATFORM", "1");
			// } else {// o20支付
			// orderRequest = DataPaser.json2Bean(data, OrderRequest.class);
			// if (null != orderRequest) {
			// orderRequest.getMaster().setPayType("W");
			//
			// String timestamp = System.currentTimeMillis() + "";
			// String key = c2BHttpRequest.getKey(SHOPID + "", timestamp);
			//
			// params.addBodyParameter("FKEY", key);
			// params.addBodyParameter("TIMESTAMP", timestamp);
			//
			// params.addBodyParameter("DATA",
			// DataPaser.bean2Json(orderRequest));
			// params.addBodyParameter("SHOPID", SHOPID);
			// }
			// }
			new WxPay(this, params, index);
			break;
		}
	}

	public void destroy() {
		finish();
	}

	@Override
	public void OnResponse(String result, int connectType) {
		if (null != result) {

		}
	}

	private ShopsCartDao shopsCartDao;

	public void onFinish() {
		shopsCartDao = new ShopsCartDao(this);
		if (null != orderRequest) {
			for (Detail detail : orderRequest.getDetail()) {
				shopsCartDao.deleteGood(detail.getProdId());
			}
		}
		MyActivityManager.getInstance().killActivity(SaleDetailsConfirmOrderActivity.class);
		finish();
	}
}

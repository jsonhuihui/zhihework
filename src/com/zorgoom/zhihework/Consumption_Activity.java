package com.zorgoom.zhihework;

import com.google.gson.Gson;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsSUserVO;
import com.zorgoom.zhihework.zxing.activity.CaptureActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Consumption_Activity extends BaseActivity implements View.OnClickListener, HttpListener {

	private Consumption_Activity mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.consumption_layout);
		initView();
	}

	private void initView() {
		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.consumption).setOnClickListener(this);
		findViewById(R.id.consumption_ib1).setOnClickListener(this);
		findViewById(R.id.consumption_tv1).setOnClickListener(this);
		findViewById(R.id.consumption2).setOnClickListener(this);
		findViewById(R.id.consumption2_ib2).setOnClickListener(this);
		findViewById(R.id.consumption2_tv2).setOnClickListener(this);
		findViewById(R.id.consumption3).setOnClickListener(this);
		findViewById(R.id.consumption3_ib3).setOnClickListener(this);
		findViewById(R.id.consumption3_tv3).setOnClickListener(this);
		findViewById(R.id.consumption4).setOnClickListener(this);
		findViewById(R.id.consumption4_ib4).setOnClickListener(this);
		findViewById(R.id.consumption4_tv4).setOnClickListener(this);
		
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDestroy() {
		super.onDestroy();

	};

	RsSUserVO rsSUserInfoResultVO;
	Gson gson = new Gson();

	@Override
	public void OnResponse(String result, int reqId) {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		case R.id.consumption://扫一扫
		case R.id.consumption_ib1:
		case R.id.consumption_tv1:
			setClass(mContext, CaptureActivity.class);
			break;
		case R.id.consumption2://付款
		case R.id.consumption2_ib2:
		case R.id.consumption2_tv2:
			setClass(mContext, FuKuan_Main.class);
//			ToastUtil.showMessage(mContext, "程序员玩命赶制中!");
			break;
		case R.id.consumption3://账户查询
		case R.id.consumption3_ib3:
		case R.id.consumption3_tv3:
			setClass(mContext, Select_Account_Activity.class);
			break;
		case R.id.consumption4://余额
		case R.id.consumption4_ib4:
		case R.id.consumption4_tv4:
//			ToastUtil.showMessage(mContext, "程序员玩命赶制中!");
			setClass(mContext, Money_Activity.class);
			break;
		
		}
	}
}

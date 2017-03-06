package com.zorgoom.zhihework;

import com.google.gson.Gson;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsSUserVO;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Equipment_Activity extends BaseActivity implements View.OnClickListener, HttpListener {

	private Equipment_Activity mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.equipment_activity);
		initView();
	}

	private void initView() {
		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.bt1).setOnClickListener(this);
		findViewById(R.id.bt1_ib1).setOnClickListener(this);
		findViewById(R.id.bt1_tv1).setOnClickListener(this);
		findViewById(R.id.bt2).setOnClickListener(this);
		findViewById(R.id.bt2_ib2).setOnClickListener(this);
		findViewById(R.id.bt2_tv2).setOnClickListener(this);
		findViewById(R.id.bt3).setOnClickListener(this);
		findViewById(R.id.bt3_ib3).setOnClickListener(this);
		findViewById(R.id.bt3_tv3).setOnClickListener(this);
		findViewById(R.id.bt4).setOnClickListener(this);
		findViewById(R.id.bt4_ib4).setOnClickListener(this);
		findViewById(R.id.bt4_tv4).setOnClickListener(this);
		
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
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		case R.id.bt1://访客通行
		case R.id.bt1_ib1:
		case R.id.bt1_tv1:
			setClass(mContext, GuestPassActivity.class);
			break;
		case R.id.bt2://工作对讲
		case R.id.bt2_ib2:
		case R.id.bt2_tv2:
			setClass(mContext, HuhutongActivity.class);
			break;
		case R.id.bt3://环境指数
		case R.id.bt3_ib3:
		case R.id.bt3_tv3:
			setClass(mContext, HuangJing_index_Activity.class);
			break;
		case R.id.bt4://巡更
		case R.id.bt4_ib4:
		case R.id.bt4_tv4:
			setClass(mContext, XunGengActivity.class);
			break;
		
		}
	}
}

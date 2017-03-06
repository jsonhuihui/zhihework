package com.zorgoom.zhihework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.jpush.android.data.r;

import com.zorgoom.zhihework.vo.CheckCode;

public class ConsumeDetail_detail extends BaseActivity implements View.OnClickListener {
	private TextView money;
	private CheckCode msg;
	private TextView time;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.consumedetail_detail);
		this.msg = ((CheckCode) getIntent().getSerializableExtra("checkCode"));
		initView();
	}
	
	private void initView() {
		findViewById(R.id.regis_back).setOnClickListener(this);
		this.money = ((TextView) findViewById(R.id.money));
		this.time = ((TextView) findViewById(R.id.time));
		this.money.setText(this.msg.getMONEY()+"");
		this.time.setText(this.msg.getTIME());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
	}

	}
}
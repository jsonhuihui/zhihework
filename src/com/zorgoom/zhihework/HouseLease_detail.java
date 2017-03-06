package com.zorgoom.zhihework;

import com.zorgoom.zhihework.vo.HouseLease;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class HouseLease_detail extends BaseActivity implements View.OnClickListener {

	private TextView message_detail_textView03, // 小区名
			message_detail_textView04, // 发布时间
			message_detail_textView08, // 房屋面积
			message_detail_textView05, // 标题
			message_detail_textView07, // 描述
			message_detail_textView06;// 房屋户型
	private HouseLease msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.house_lease_detail);
		msg = (HouseLease) getIntent().getSerializableExtra("msg");
		initView();

	}

	private void initView() {
		message_detail_textView03 = (TextView) findViewById(R.id.message_detail_textView03);
		message_detail_textView04 = (TextView) findViewById(R.id.message_detail_textView04);
		message_detail_textView05 = (TextView) findViewById(R.id.message_detail_textView05);
		message_detail_textView06 = (TextView) findViewById(R.id.message_detail_textView06);
		message_detail_textView07 = (TextView) findViewById(R.id.message_detail_textView07);
		message_detail_textView08 = (TextView) findViewById(R.id.message_detail_textView08);
		findViewById(R.id.regis_back).setOnClickListener(this);
		message_detail_textView03.setText(msg.getBLOCKNAME() + " " + msg.getUNITNO() + "室");
		message_detail_textView04.setText("发布于" + msg.getCREDATE());
		message_detail_textView05.setText(msg.getTITLE());
		message_detail_textView06.setText("房屋户型：" + msg.getROOM());
		message_detail_textView07.setText(msg.getCONTENT());

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

package com.zorgoom.zhihework;

import com.zorgoom.zhihework.vo.Msg;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Message_detail extends BaseActivity implements View.OnClickListener {

	private TextView message_detail_textView03, // 标题
			message_detail_textView04, // 作者
			message_detail_textView05, // 内容
			message_detail_textView06;// 时间
	private Msg msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message_detail);
		msg = (Msg) getIntent().getSerializableExtra("msg");
		initView();

	}

	private void initView() {
		message_detail_textView03 = (TextView) findViewById(R.id.message_detail_textView03);
		message_detail_textView04 = (TextView) findViewById(R.id.message_detail_textView04);
		message_detail_textView05 = (TextView) findViewById(R.id.message_detail_textView05);
		message_detail_textView06 = (TextView) findViewById(R.id.message_detail_textView06);
		findViewById(R.id.regis_back).setOnClickListener(this);
		message_detail_textView03.setText(msg.getNOTICETITLE());
		message_detail_textView04.setText(msg.getCREBYNAME());
		message_detail_textView05.setText(msg.getREMARK());
		message_detail_textView06.setText(msg.getCREDATE());

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

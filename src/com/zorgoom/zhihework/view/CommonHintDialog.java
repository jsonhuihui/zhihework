package com.zorgoom.zhihework.view;

import com.zorgoom.app.BaseDialog;
import com.zorgoom.zhihework.R;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * 
 * @ClassName: DeteleDialog
 * @Description: TODO(通用提示对话框)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年8月18日
 * @修改时间 2015年8月18日
 */
public class CommonHintDialog extends BaseDialog implements android.view.View.OnClickListener {
	private onCheckedChanged listener;
	private int position;
	private TextView title, cancel;
	private int index;
	private TextView sure;

	public CommonHintDialog(Context context, int dialog, int index) {
		super(context, dialog);
		this.index = index;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	protected void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_detele_layout);
		title = (TextView) findViewById(R.id.title);
		cancel = (TextView) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		sure = (TextView) findViewById(R.id.sure);
		sure.setOnClickListener(this);

		switch (index) {
		case 2:
			title.setText("确定删除这个收货地址吗");
			break;
		case 3:
			title.setText("确定删除这个房屋信息吗？");
			break;
		case 4:
			title.setText("确定删除这个访客密码吗？");
			break;
		}
	}

	@Override
	protected void initData() {

	}

	public interface onCheckedChanged {

		public void getChoiceData(int position);
	}

	public void setOnCheckedChanged(onCheckedChanged listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.cancel:
			this.dismiss();
			break;
		case R.id.sure:
			if (null != listener) {
				listener.getChoiceData(position);
				this.dismiss();
			}
			break;
		}
	}

	private double remaining;

	public void setPrice(double remaining) {
		this.remaining = remaining;

	}

}

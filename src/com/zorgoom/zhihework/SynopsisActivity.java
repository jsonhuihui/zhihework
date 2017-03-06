package com.zorgoom.zhihework;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.zorgoom.zhihework.R;

/**
 * @ClassName: SynopsisActivity
 * @Description: TODO(简介Activity)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年9月14日
 * @修改时间 2015年9月14日
 */
public class SynopsisActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_synopsis_activity_layout);
		initView();
	}

	protected void initView() {
//		findViewById(R.id.rel_phone).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				showDialog("拨打电话", "客服热线:4001001373", 1);
//			}
//		});
		findViewById(R.id.regis_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@SuppressLint("NewApi")
	public void showDialog(String title, String message, final int index) {
		// 创建对话框的构造器，可以帮我们构造对话框的模版
		AlertDialog.Builder builder = new Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		// 设置对话框的 标题
		builder.setTitle(title);
		// 设置对话框的提示信息
		builder.setMessage(message);
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// // 用intent启动拨打电话
				// Intent intent = new Intent(Intent.ACTION_CALL,
				// Uri.parse("tel:" + "400-966-1089"));
				// startActivity(intent);
				// dialogDisspose(index);
				// 用intent启动拨打电话
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4001001373"));
				startActivity(intent);
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
}

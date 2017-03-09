package com.zorgoom.zhihework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * 欢迎界面
 * 
 * @author Carter 2015-7-6
 *
 */
public class WelcomeActivity extends BaseActivity {
	protected static final String tag = "MainActivity";
	private SharedPreferences sharedPreferences;
	// 本地的版本号
	private String mClientVersionName;
	private PackageManager packageManager;
	// private TextView tvVersionName;
	private RelativeLayout rel_layout;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome_activity_layout);
		loadMain();
	}
	private void loadMain() {
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		// 发送延时消息,睡眠
		new Handler() {
			public void handleMessage(android.os.Message msg) {
				// 红色界面的跳转过程
				boolean b = sharedPreferences.getBoolean("is_first", true);
				// (第一次进入应用就跳转到引导页)
				// if (b) {
				// // 1,跳转到引导界面,并且将is_first设置成false
				// // sharedPreferences.edit().putBoolean("is_first",
				// // false).commit();
				// Intent intent = new Intent(getApplicationContext(),
				// GuideActivity.class);
				// startActivity(intent);
				// } else {
				// 2,跳转到应用程序主界面
				Intent intent = new Intent(getBaseContext(), Start_Main.class);
				startActivity(intent);
				// }
				finish();

			};
		}.sendEmptyMessageDelayed(0, 2000);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

}

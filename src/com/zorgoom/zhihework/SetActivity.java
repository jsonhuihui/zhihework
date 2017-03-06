package com.zorgoom.zhihework;

import com.zorgoom.zhihework.service.MainService;
import com.zorgoom.zhihework.update.CacheThread;
import com.zorgoom.zhihework.update.DataCleanManager;
import com.zorgoom.zhihework.view.SwitchButton;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: SynopsisActivity
 * @Description: TODO(设置Activity)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年9月14日
 * @修改时间 2015年9月14日
 */
@SuppressLint("NewApi")
public class SetActivity extends BaseActivity implements OnClickListener {
	private TextView tv_clean_cache;
	private SwitchButton sb_msg_voice, // 声音提醒
			sb_msg_shake, // 震动提醒
			sb_msg_remind;// 消息提醒
	private TextView tv_current_versions, tv_new_versions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_activity_layout);
		initView();
		initData();
	}

	protected void initView() {
		sb_msg_remind = (SwitchButton) findViewById(R.id.sb_msg_remind);
		sb_msg_voice = (SwitchButton) findViewById(R.id.sb_msg_voice);
		sb_msg_shake = (SwitchButton) findViewById(R.id.sb_msg_shake);
		tv_clean_cache = (TextView) findViewById(R.id.tv_clean_cache);
		tv_current_versions = (TextView) findViewById(R.id.tv_current_versions);
		//tv_new_versions = (TextView) findViewById(R.id.tv_new_versions);
		// findViewById(R.id.rel_recommendedfriend).setOnClickListener(this);
		findViewById(R.id.rel_synopsis).setOnClickListener(this);
		// findViewById(R.id.rel_wechatid).setOnClickListener(this);
		findViewById(R.id.rel_phone).setOnClickListener(this);
		findViewById(R.id.rel_feedback).setOnClickListener(this);
		findViewById(R.id.rel_clean_cache).setOnClickListener(this);
		findViewById(R.id.rel_exit).setOnClickListener(this);
		findViewById(R.id.rel_versions).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		sb_msg_remind.setOn(true);
		sb_msg_voice.setOn(true);
		sb_msg_shake.setOn(true);

		PackageManager packageManager = getPackageManager();
		PackageInfo packageInfo;
		String mClientVersionName = null;
		try {
			packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			mClientVersionName = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if (ApplicationVar.VersionVar.isNewstVersion) {
		// tv_new_versions.setVisibility(View.VISIBLE);
		// tv_new_versions.setText("最新V" +
		// ApplicationVar.VersionVar.VersionName);
		 tv_current_versions.setText("版本升级(当前V" + mClientVersionName + ")");
		// } else {
		// tv_new_versions.setVisibility(View.GONE);
		// // tv_current_versions.setText("版本升级");
		// tv_current_versions.setText("版本升级(当前V" + mClientVersionName + ")");
		//
		// }

	}

	protected void initData() {
		getCacheSize();
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rel_versions:// 版本信息
			// if (!ApplicationVar.VersionVar.isNewstVersion) {
			// ToastUtil.showMessage(mContext, "当前是最新版本了");
			// } else {
			// openActivity(UpgradeActivity.class);
			// }
			break;
		case R.id.rel_clean_cache:// 清除缓存
			showDialog("清楚缓存", "确定清除缓存吗？", 2);
			break;
		case R.id.rel_exit:// 退出
			if (PrefrenceUtils.getStringUser("userId", this).equals("0")) {
				showDialog("提示", "您还未登陆", 3);
			} else {
				showDialog("退出", "确定退出吗？", 3);
			}
			break;
		// case R.id.rel_recommendedfriend:// 推荐给好友
		// openActivity(RecommendedFriendActivity.class);
		// break;
		case R.id.rel_synopsis:// 简介
			openActivity(SynopsisActivity.class);
			break;
		case R.id.rel_feedback:// 投诉建议
			openActivity(FeedbackActivity.class);
			break;
		case R.id.rel_phone:
			showDialog("拨打电话", "客服热线:4001001373", 1);
			break;
		}
	}

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
				dialogDisspose(index);
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

	protected void dialogDisspose(int index) {
		switch (index) {
		case 1:// 拨打电话
				// 用intent启动拨打电话
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "075566689535"));
			startActivity(intent);
			break;
		case 2:// 清楚缓存
			clearCache();
			break;
		case 3:// 退出登录
			PrefrenceUtils.saveUser("HOUSING", null, this);
			PrefrenceUtils.saveUser("UNITID", null, this);
			PrefrenceUtils.saveUser("UNITAREA", null, this);
			PrefrenceUtils.saveUser("BLOCKID", null, this);
			PrefrenceUtils.saveUser("BLOCKNO", null, this);
			PrefrenceUtils.saveUser("COMMUNITYID", null, this);
			PrefrenceUtils.saveUser("state", "0", this);
			PrefrenceUtils.saveUser("CALLINFO", null, this);
			PrefrenceUtils.saveUser("MOBILE", null, this);
			PrefrenceUtils.saveUser("userId", null, this);
			PrefrenceUtils.saveUser("photo", null, this);
			PrefrenceUtils.saveUser("OPERID", null, this);
			openActivity(Login_Activity.class);

			if (MainService.callConnection != null) {
				MainService.callConnection.disconnect();
				MainService.callConnection = null;
			}
			if (MainService.device != null) {
				MainService.device.release();
				MainService.device = null;
			}
			if (MainService.rtcClient != null) {
				MainService.rtcClient.release();
				MainService.rtcClient = null;
			}
			finish();
			break;
		}
	}

	/**
	 * 清除缓存
	 */
	public void clearCache() {
		SharedPreferences preferences = getSharedPreferences("cache", Context.MODE_PRIVATE);
		long time = preferences.getLong("lastcleartime", 0);
		if (time != 0) {
			if ((System.currentTimeMillis() - time) <= 1000 * 60 * 60) {
				Toast.makeText(this, "成功清除缓存", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		Editor editor = preferences.edit();
		editor.putLong("lastcleartime", System.currentTimeMillis());
		editor.commit();
		new CacheThread(this, cachehandler, true).start();
	}

	Handler cachehandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			long size = 0;
			if (msg.what == 101) {
				size = (Long) msg.obj;
			} else if (msg.what == 102) {
				// 清除缓存
			}
			SharedPreferences preferences = getSharedPreferences("cache", Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putLong("lastgetsize", size);
			editor.commit();
			tv_clean_cache.setText("清除缓存(" + DataCleanManager.getFormatSize(size) + ")");
		}
	};

	/**
	 * 获取缓存大小
	 */
	public void getCacheSize() {
		SharedPreferences preferences = getSharedPreferences("cache", Context.MODE_PRIVATE);
		long time = preferences.getLong("lastgettime", 0);
		long size = preferences.getLong("lastgetsize", 0);
		if (time != 0) {
			if ((System.currentTimeMillis() - time) <= 1000 * 60 * 60) {
				tv_clean_cache.setText("清除缓存(" + DataCleanManager.getFormatSize(size) + ")");
				return;
			}
		}
		Editor editor = preferences.edit();
		editor.putLong("lastgettime", System.currentTimeMillis());
		editor.commit();
		new CacheThread(this, cachehandler, false).start();
	}
}

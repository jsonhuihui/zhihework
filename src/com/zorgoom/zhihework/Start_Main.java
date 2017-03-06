package com.zorgoom.zhihework;

import java.io.File;

import com.google.gson.Gson;
import com.zorgoom.app.App;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.MyActivityManager;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.service.MainService;
import com.zorgoom.zhihework.update.UpdateService;
import com.zorgoom.zhihework.vo.ReUpdateVO;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class Start_Main extends BaseActivity implements View.OnClickListener, HttpListener {

	private Start_Main mContext;
	private ImageView start_main_image01, start_main_image02, start_main_image03, start_main_image04;
	private TextView start_main_text01, start_main_text02, start_main_text03, start_main_text04;
	// private TabHost mTabHost;
	private LinearLayout start_main_linearLayout1, start_main_linearLayout2, start_main_linearLayout3,
			start_main_linearLayout4;
	private C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	// 底部标签切换的Fragment
	private Fragment mainActivity, pay_Main, me_Activity, currentFragment, kaoQin_Main;

	public static String DOWNLOADFILE = "DOWNLOADFILE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.getInstance().addActivity(this);
		// StatusBarUtil.setColor(this,
		// getResources().getColor(R.color.theme_color), 1);
		setContentView(R.layout.start_main);
		mContext = this;
		initView();
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		App.screenHeight = display.getHeight();
		App.screenWidth = display.getWidth();

		c2BHttpRequest.setShow(false);

		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey("3", timestamp);
		c2BHttpRequest.getHttpResponse(Http.GETUPGRADE + "TYPE=3" + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 4);
		registerBoradcastReceiver();
		// 开启服务
		startService(new Intent(this, MainService.class));

		//new MsgDao(mContext);
	}

	private void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(DOWNLOADFILE);
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(DOWNLOADFILE)) {// 下载apk
				String updateFile = intent.getStringExtra("updateFile");
				intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setDataAndType(Uri.fromFile(new File(updateFile)), "application/vnd.android.package-archive");
				// startActivity(intent);
				startActivityForResult(intent, 0);
			}
		}
	};
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出智核社区", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				MyActivityManager.getInstance().AppExit(this);
				System.exit(0);
			}
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			// if (((CfMainActivity) cfMainActivity).getmEditTextBody() != null
			// && ((CfMainActivity)
			// cfMainActivity).getmEditTextBody().getVisibility() ==
			// View.VISIBLE) {
			// ((CfMainActivity)
			// cfMainActivity).getmEditTextBody().setVisibility(View.GONE);
			// return true;
			// }
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initView() {
		start_main_image01 = (ImageView) findViewById(R.id.start_main_image01);
		start_main_image01.setOnClickListener(this);
		start_main_image02 = (ImageView) findViewById(R.id.start_main_image02);
		start_main_image02.setOnClickListener(this);
		start_main_image03 = (ImageView) findViewById(R.id.start_main_image03);
		start_main_image03.setOnClickListener(this);
		start_main_image04 = (ImageView) findViewById(R.id.start_main_image04);
		start_main_image04.setOnClickListener(this);
		start_main_linearLayout1 = (LinearLayout) findViewById(R.id.start_main_linearLayout1);
		start_main_linearLayout1.setOnClickListener(this);
		start_main_linearLayout2 = (LinearLayout) findViewById(R.id.start_main_linearLayout2);
		start_main_linearLayout2.setOnClickListener(this);
		start_main_linearLayout3 = (LinearLayout) findViewById(R.id.start_main_linearLayout3);
		start_main_linearLayout3.setOnClickListener(this);
		start_main_linearLayout4 = (LinearLayout) findViewById(R.id.start_main_linearLayout4);
		start_main_linearLayout4.setOnClickListener(this);

		start_main_text01 = (TextView) findViewById(R.id.start_main_text01);
		start_main_text02 = (TextView) findViewById(R.id.start_main_text02);
		start_main_text03 = (TextView) findViewById(R.id.start_main_text03);
		start_main_text04 = (TextView) findViewById(R.id.start_main_text04);
		mainActivity = new MainActivity();
		pay_Main = new Pay_Main();
		// sale_Activity = new Sale_Activity();
		kaoQin_Main = new KaoQin_Main();
		me_Activity = new Me_Activity();
		initTab();
	}

	/**
	 * 初始化底部标签
	 */
	private void initTab() {
		if (mainActivity == null) {
			mainActivity = new MainActivity();
		}

		if (!mainActivity.isAdded()) {
			// 提交事务
			getSupportFragmentManager().beginTransaction().add(R.id.fragment, mainActivity).commit();

			// 记录当前Fragment
			currentFragment = mainActivity;
		}
	}

	private void clickLayout(Fragment fragment) {
		// addOrShowFragment(getSupportFragmentManager().beginTransaction(),
		// fragment);
		addOrShowFragment(getSupportFragmentManager().beginTransaction(), fragment);
	}

	/**
	 * 添加或者显示碎片
	 * 
	 * @param transaction
	 * @param fragment
	 */
	private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
		if (currentFragment == fragment)
			return;

		if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
			transaction.hide(currentFragment).add(R.id.fragment, fragment).commit();
		} else {
			transaction.hide(currentFragment).show(fragment).commit();
		}

		currentFragment = fragment;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mBroadcastReceiver != null) {
			unregisterReceiver(mBroadcastReceiver);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (index == 1) {
			if (mainActivity != null) {
				((MainActivity) mainActivity).refresh();
			}
		}
		// if (index == 2) {
		// if (sale_Activity != null) {
		// ((Sale_Activity) sale_Activity).refresh();
		// }
		// }
		if (index == 4) {
			if (me_Activity != null) {
				((Me_Activity) me_Activity).refresh();
			}
		}
	}

	private int index = 1;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_main_image01:
		case R.id.start_main_text01:
		case R.id.start_main_linearLayout1:
			start_main_image01.setImageResource(R.drawable.fragment_home_choose);
			start_main_image02.setImageResource(R.drawable.fragment_money);
			start_main_image03.setImageResource(R.drawable.fragment_time);
			start_main_image04.setImageResource(R.drawable.fragment_set);
			start_main_text01.setTextColor(0xff1d213b);
			start_main_text02.setTextColor(0xff888888);
			start_main_text03.setTextColor(0xff888888);
			start_main_text04.setTextColor(0xff888888);
			clickLayout(mainActivity);
			((Pay_Main) pay_Main).stopTimer();
			((KaoQin_Main) kaoQin_Main).stopTimer();
			// ((Sale_Activity) sale_Activity).refresh();
			index = 1;
			break;
		case R.id.start_main_image02:
		case R.id.start_main_text02:
		case R.id.start_main_linearLayout2:
			String state = PrefrenceUtils.getStringUser("state", this);
			if (state.equals("0")) {
				ToastUtil.showMessage(this, "请先登录");
				startActivity(new Intent(mContext, Login_Activity.class));
				return;
			}
			// if (state.equals("1")) {
			// ToastUtil.showMessage(this, "请先验证房屋");
			// startActivity(new Intent(mContext, HousingList.class));
			// return;
			// }
			start_main_image01.setImageResource(R.drawable.fragment_home);
			start_main_image02.setImageResource(R.drawable.fragment_money_choose);
			start_main_image03.setImageResource(R.drawable.fragment_time);
			start_main_image04.setImageResource(R.drawable.fragment_set);
			start_main_text01.setTextColor(0xff888888);
			start_main_text02.setTextColor(0xff1d213b);
			start_main_text03.setTextColor(0xff888888);
			start_main_text04.setTextColor(0xff888888);
			clickLayout(pay_Main);
			((KaoQin_Main) kaoQin_Main).stopTimer();
			index = 2;
			break;
		case R.id.start_main_image03:
		case R.id.start_main_text03:
		case R.id.start_main_linearLayout3:
			String state2 = PrefrenceUtils.getStringUser("state", this);
			if (state2.equals("0")) {
				ToastUtil.showMessage(this, "请先登录");
				startActivity(new Intent(mContext, Login_Activity.class));
				return;
			}
			// if (state2.equals("1")) {
			// ToastUtil.showMessage(this, "请先验证房屋");
			// startActivity(new Intent(mContext, HousingList.class));
			// return;
			// }
			start_main_image01.setImageResource(R.drawable.fragment_home);
			start_main_image02.setImageResource(R.drawable.fragment_money);
			start_main_image03.setImageResource(R.drawable.fragment_time_choose);
			start_main_image04.setImageResource(R.drawable.fragment_set);
			start_main_text01.setTextColor(0xff888888);
			start_main_text02.setTextColor(0xff888888);
			start_main_text03.setTextColor(0xff1d213b);
			start_main_text04.setTextColor(0xff888888);
			clickLayout(kaoQin_Main);
			((Pay_Main) pay_Main).stopTimer();
			index = 3;
			break;
		case R.id.start_main_image04:
		case R.id.start_main_text04:
		case R.id.start_main_linearLayout4:
			start_main_image01.setImageResource(R.drawable.fragment_home);
			start_main_image02.setImageResource(R.drawable.fragment_money);
			start_main_image03.setImageResource(R.drawable.fragment_time);
			start_main_image04.setImageResource(R.drawable.fragment_set_choose);
			start_main_text01.setTextColor(0xff888888);
			start_main_text02.setTextColor(0xff888888);
			start_main_text03.setTextColor(0xff888888);
			start_main_text04.setTextColor(0xff1d213b);
			clickLayout(me_Activity);
			((KaoQin_Main) kaoQin_Main).stopTimer();
			((Pay_Main) pay_Main).stopTimer();
			((Me_Activity) me_Activity).refresh();
			index = 4;
			break;
		// case R.id.lin_lock:
		// case R.id.iv_lock:
		// String state1 = PrefrenceUtils.getStringUser("state", this);
		// if (state1.equals("0")) {
		// ToastUtil.showMessage(this, "请先登录");
		// startActivity(new Intent(this, Login_Activity.class));
		// return;
		// }
		// if (state1.equals("1")) {
		// ToastUtil.showMessage(this, "请先验证房屋");
		// startActivity(new Intent(this, HousingList.class));
		// return;
		// }
		// // 方法1 Android获得屏幕的宽和高
		// WindowManager windowManager = getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		// int screenHeight = screenHeight = display.getHeight();
		// int hei = screenHeight / 5 * 3;
		// int hei1 = screenHeight / 5 * 2;
		// Main_AdPopuWindow adPopuWindow = new Main_AdPopuWindow(mContext,
		// (RelativeLayout) findViewById(R.id.start_main_pop), hei);
		// new Main_UnLockPopuWindow(mContext, (RelativeLayout)
		// findViewById(R.id.start_main_pop), hei1, adPopuWindow);
		// adPopuWindow.setMain_AdPopu(adPopuWindow);
		// break;
		}
	}

	private Gson gson = new Gson();

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			switch (reqId) {
			case 1:
				ReUpdateVO rsAppUpdateListResultVO = gson.fromJson(result, ReUpdateVO.class);
				if (rsAppUpdateListResultVO.getCode().equals("101")) {
					String VERSIONNUMBER = rsAppUpdateListResultVO.getData().getVERSIONNUMBER();
					double number = Double.parseDouble(VERSIONNUMBER);
					if (number > getVersion(getApplicationContext())) {
						final String url = rsAppUpdateListResultVO.getData().getURL();
						AlertDialog.Builder addPhoneBuilder = new AlertDialog.Builder(Start_Main.this);
						addPhoneBuilder.setTitle("系统提示").setMessage("有最新版本是否更新？").setNegativeButton("取消", null)
								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										downLoadApk(url);

									}
								}).show();
					}
				}
				break;
			}
		}
	}

	/**
	 * 下载apk
	 */
	private void downLoadApk(String downloadurl) {
		// 判断当前用户是否有sd卡
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			ToastUtil.showMessage1(this, "通知栏下载中", 300);
			Intent updateIntent = new Intent(this, UpdateService.class);
			updateIntent.putExtra("downloadurl", downloadurl);
			updateIntent.putExtra("titleId", R.string.app_name);
			startService(updateIntent);
		}
	}

	/**
	 * 获取软件版本
	 * 
	 * @param context
	 * @return
	 */
	public static double getVersion(Context context)// 获取版本号
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}

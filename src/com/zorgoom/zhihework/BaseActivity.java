package com.zorgoom.zhihework;

import com.zorgoom.zhihework.base.MyActivityManager;
import com.zorgoom.util.statusbar.StatusBarUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by hx on 2015/7/4.
 */
@SuppressLint({ "ResourceAsColor", "NewApi" })
public class BaseActivity extends FragmentActivity {
	private SharedPreferences sp;

	@SuppressLint({ "InlinedApi", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyActivityManager.getInstance().addActivity(this);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		setStatusBar();
	}

	protected void setStatusBar() {
		StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color), 1);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	// /** * 设置状态栏颜色 * * @param activity 需要设置的activity * @param color 状态栏颜色值 */
	// @SuppressLint("NewApi")
	// public static void setColor(Activity activity, int color) {
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	// // 设置状态栏透明
	// activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	// // 生成一个状态栏大小的矩形
	// View statusView = createStatusView(activity, color);
	// // 添加 statusView 到布局中
	// ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
	// decorView.addView(statusView);
	// // 设置根布局的参数
	// ViewGroup rootView = (ViewGroup) ((ViewGroup)
	// activity.findViewById(android.R.id.content)).getChildAt(0);
	// rootView.setFitsSystemWindows(true);
	// rootView.setClipToPadding(true);
	// }
	// }
	//
	// private static View createStatusView(Activity activity, int color) {
	// // 获得状态栏高度
	// int resourceId =
	// activity.getResources().getIdentifier("status_bar_height", "dimen",
	// "android");
	//// int statusBarHeight =
	// activity.getResources().getDimensionPixelSize(resourceId);
	// int statusBarHeight = 20;
	//
	// // 绘制一个和状态栏一样高的矩形
	// View statusView = new View(activity);
	// LinearLayout.LayoutParams params = new
	// LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
	// statusBarHeight);
	// statusView.setLayoutParams(params);
	// statusView.setBackgroundColor(color);
	// return statusView;
	// }

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (conflictBuilder != null) {
			conflictBuilder.create().dismiss();
			conflictBuilder = null;
		}
	}

	/**
	 * 显式启动activity
	 * 
	 * Carter2015-07-15
	 * 
	 * @param clazz
	 *            所要启动的的activity
	 */
	public void openActivity(Class<?> clazz) {
		openActivity(clazz, null);
	}

	/**
	 * 显式启动activity ，绑定Bundle数据
	 * 
	 * Carter2015-07-15
	 * 
	 * @param clazz
	 * 
	 * @param bundle
	 * 
	 */
	public void openActivity(Class<?> clazz, Bundle pBundle) {
		Intent intent = new Intent(this, clazz);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	/**
	 * 通过Action启动Activity
	 * 
	 * Carter2015-07-15
	 * 
	 * @param pAction
	 */
	public void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * Carter2015-07-15
	 * 
	 * @param pAction
	 * 
	 * @param pBundle
	 */
	protected void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	private android.app.AlertDialog.Builder conflictBuilder;
	private boolean isConflictDialogShow;
	// ProgressDialog progressDialog = null;

	public interface ShowDialog {

		public void DisplayToast(String str);

	}

	/**
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		Looper.prepare();
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		Looper.loop();
	}

	/**
	 * 提供子类跳转的类
	 * 
	 * @param ctx
	 * @param cls
	 */
	protected void setClass(Context ctx, Class<?> cls) {

		Intent intent = new Intent();
		intent.setClass(ctx, cls);
		startActivity(intent);

	}

	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void showProgress(String msg) {
		/*
		 * progressDialog = new ProgressDialog(this);
		 * progressDialog.setMessage(msg);
		 * progressDialog.setIndeterminate(true);
		 * progressDialog.setCancelable(false); progressDialog.show();
		 */}

	/** 加载进度条 */
	public void showProgressDialog(Context context, String msg) {
		/*
		 * 
		 * if (progressDialog != null) { progressDialog.cancel(); }
		 * progressDialog = new ProgressDialog(context); Drawable drawable =
		 * getResources().getDrawable(R.drawable.loading_animation);
		 * progressDialog.setIndeterminateDrawable(drawable);
		 * progressDialog.setIndeterminate(true);
		 * progressDialog.setCancelable(true); progressDialog.setMessage(msg);
		 * progressDialog.show();
		 */}

}

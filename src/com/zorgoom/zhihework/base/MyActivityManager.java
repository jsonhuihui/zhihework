package com.zorgoom.zhihework.base;

import java.util.ArrayList;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 本类用来对activity对象进行管理，是一个单例类
 * 
 * @author Carter
 * 
 */
public class MyActivityManager {
	private static Stack<Activity> mActivityStack;
	private static MyActivityManager mAppManager;
	private ArrayList<String> sbannermImageUrl = new ArrayList<String>();

	public MyActivityManager() {
		// TODO Auto-generated constructor stub
	}

	public void saveSbannerUrl(ArrayList<String> sbannerUrl) {
		sbannermImageUrl=sbannerUrl;
	}
	
	
	public ArrayList<String> getSbannerUrl() {
		return sbannermImageUrl;
	}
	/**
	 * Carter 2015-7-15
	 * 
	 * 单一实例
	 */
	public static MyActivityManager getInstance() {
		if (mAppManager == null) {
			mAppManager = new MyActivityManager();
		}
		return mAppManager;
	}

	/**
	 * Carter 2015-7-15
	 * 
	 * 添加Activity到堆栈，在Activity的oncreate方法中调用
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}

	/**
	 * Carter 2015-7-15
	 * 
	 * 获取栈顶Activity（堆栈中最后一个压入的）
	 */
	public Activity getTopActivity() {
		Activity activity = mActivityStack.lastElement();
		return activity;
	}

	/**
	 * Carter 2015-7-15
	 * 
	 * 结束栈顶Activity（堆栈中最后一个压入的）
	 */
	public void killTopActivity() {
		Activity activity = mActivityStack.lastElement();
		killActivity(activity);
	}

	/**
	 * Carter 2015-7-15
	 * 
	 * 结束指定类名的Activity
	 */
	public void killActivity(Class<?> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				// killActivity(activity);
				if (activity != null) {
					mActivityStack.remove(activity);
					activity.finish();
					activity = null;
					return;
				}
			}
		}
	}

	/**
	 * Carter 2015-7-15
	 * 
	 * 结束指定的Activity,在Activity的onDestory方法中调用
	 */
	public void killActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * Carter 2015-7-15
	 * 
	 * 结束所有Activity
	 */
	public void killAllActivity() {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}

	/**
	 * Carter 2015-7-15
	 * 
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			killAllActivity();
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * Carter 2015-7-15
	 * 
	 * @return 获得栈中数量
	 */
	public int getStackSize() {
		return mActivityStack.size();
	}
}

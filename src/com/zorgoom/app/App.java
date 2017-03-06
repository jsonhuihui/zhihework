package com.zorgoom.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.lidroid.xutils.http.RequestParams;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.vo.BaseModel;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import cn.smssdk.SMSSDK;

public class App extends Application implements HttpListener {
	public static Context applicationContext;
	public static int screenHeight = 0;
	public static int screenWidth = 0;
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPKEY = "154de90e257f8";
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "6db036ccd95eb000ed995387845e28fc";
	C2BHttpRequest c2BHttpRequest;
	private static final String TAG = "App";

	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	@Override
	public void onCreate() {
		super.onCreate();
		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		applicationContext = this;
		c2BHttpRequest = new C2BHttpRequest(this);
		c2BHttpRequest.setShow(false);
		// 设置未捕获异常的处理器
		Thread.setDefaultUncaughtExceptionHandler(new GlobarCatchException());

		initImageLoader();
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		collectDeviceInfo(applicationContext);
		if (ex == null) {
			return false;
		}
		String message = ex.getMessage();

		String operId = PrefrenceUtils.getStringUser("OPERID", applicationContext);
		String timestamp = System.currentTimeMillis() + "";
		RequestParams params = new RequestParams();
		params.addBodyParameter("CONTENT", infos + message);
		params.addBodyParameter("PLATFORM", "0");
		params.addBodyParameter("TYPE", "MKJ");
		params.addBodyParameter("APPTYPE", "0");
		params.addBodyParameter("INTFNAME", "MKJ");
		params.addBodyParameter("CRETIME", Util.getDate());
		params.addBodyParameter("OPERID", operId);

		String key = c2BHttpRequest.getKey(operId + "", timestamp);
		params.addBodyParameter("FKEY", key);
		params.addBodyParameter("TIMESTAMP", timestamp);
		// params.addBodyParameter("USERID",
		// PrefrenceUtils.getStringUser("userId", applicationContext));
		Log.i("日志添加接口开始", "appLog/addLog.do");
		c2BHttpRequest.postHttpResponse(Http.APPLOGUPLOAD, params, 1);
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(applicationContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		// 保存日志文件
		// saveCrashInfo2File(ex);
		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	class GlobarCatchException implements UncaughtExceptionHandler {

		// 出现未被捕获的异常就会走入该方法中
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			if (!handleException(ex)) {
				uncaughtException(thread, ex);
			}
			File errLog = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/elinker_error.log");
			PrintWriter writer;
			try {
				writer = new PrintWriter(errLog);
				ex.printStackTrace(writer);
				writer.close();

			} catch (FileNotFoundException e) {
				Log.e("Application", "error : ", e);
				e.printStackTrace();
			}

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e("Application", "error : ", e);
			}
			// 根据当前进程id,杀死进程
			android.os.Process.killProcess(android.os.Process.myPid());
		}

	}

	@Override
	public void OnResponse(String result, int reqId) {
		Log.i("日志添加接口返回", result);
		if (null != result) {
			try {
				if ("101".equals(DataPaser.json2Bean(result, BaseModel.class).getCode())) {

					// ToastUtil.showMessage(this, "");
				} else {
					// ToastUtil.showMessage(this, guestPass.getMsg());
				}
			} catch (Exception e) {

			}
		}
	}

	// 默认存放图片的路径
	public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator
			+ "CircleDemo" + File.separator + "Images" + File.separator;

	public static int mKeyBoardH = 0;

	/** 初始化imageLoader */
	private void initImageLoader() {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.im_skin_icon_imageload_default)
				.showImageOnFail(R.drawable.im_skin_icon_imageload_failed).showImageOnLoading(R.drawable.im_skin_icon_imageload_default).cacheInMemory(true)
				.cacheOnDisc(true).build();

		File cacheDir = new File(DEFAULT_SAVE_IMAGE_PATH);
		ImageLoaderConfiguration imageconfig = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().discCacheSize(50 * 1024 * 1024).discCacheFileCount(200).discCache(new UnlimitedDiscCache(cacheDir))
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).defaultDisplayImageOptions(options).build();

		ImageLoader.getInstance().init(imageconfig);
	}

	/* *//***************************************
			 * ===========================退出应用=================================
			 **************/
	/*
	 * private List<Activity> mList = new LinkedList<Activity>(); private static
	 * App instance;
	 * 
	 * private App() { }
	 * 
	 * public synchronized static App getInstance() { if (null == instance) {
	 * instance = new App(); } return instance; }
	 * 
	 * // add Activity public void addActivity(Activity activity) {
	 * mList.add(activity); }
	 * 
	 * public void exit() { try { for (Activity activity : mList) { if (activity
	 * != null) activity.finish(); } } catch (Exception e) {
	 * e.printStackTrace(); } finally { System.exit(0); } }
	 * 
	 * @Override public void onLowMemory() { super.onLowMemory(); System.gc(); }
	 * 
	 */}

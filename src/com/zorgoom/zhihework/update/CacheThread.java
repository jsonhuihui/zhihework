package com.zorgoom.zhihework.update;

import java.io.File;

import android.content.Context;
import android.os.Handler;

public class CacheThread extends Thread {

	private Handler handler;
	// true表示执行清除任务，false表示获取任务
	private boolean clear;
	private Context context;

	public CacheThread(Context context, Handler handler, boolean clear) {
		this.handler = handler;
		this.clear = clear;
		this.context = context;
	}

	@Override
	public void run() {
		String exterfilepath = context.getExternalCacheDir().getAbsolutePath() + "/data/" + context.getPackageName()
				+ "/";
		File[] exterfiles = new File[] { new File(exterfilepath + "files/"), new File(exterfilepath + "cache/") };
		File intercache = context.getCacheDir();
		File interfile = context.getFilesDir();
		if (clear) {
			DataCleanManager.cleanCustomCache(exterfiles[0].getAbsolutePath());
			DataCleanManager.cleanExternalCache(context);
			DataCleanManager.cleanFiles(context);
			DataCleanManager.cleanInternalCache(context);
			handler.obtainMessage(102).sendToTarget();
		} else {
			long size = 0;
			try {
				size += DataCleanManager.getFolderSize(intercache);
				size += DataCleanManager.getFolderSize(interfile);
				for (int i = 0; i < exterfiles.length; i++) {
					size += DataCleanManager.getFolderSize(exterfiles[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.obtainMessage(101, size).sendToTarget();
		}
	}
}

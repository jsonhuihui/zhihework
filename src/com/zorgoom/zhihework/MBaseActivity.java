package com.zorgoom.zhihework;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by hx on 2015/7/29.
 */
public class MBaseActivity extends BaseActivity {

	protected void setClass(Context ctx, Class<?> cls) {

		Intent intent = new Intent();
		intent.setClass(ctx, cls);
		startActivity(intent);
	}


	/**
	 * 只需要加上那两句就能在非UI线程中显示Toast
	 * 
	 * @param msg
	 */
	public void showToast(String msg) {
		Looper.prepare();
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		Looper.loop();
	}

	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

}

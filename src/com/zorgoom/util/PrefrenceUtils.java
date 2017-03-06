package com.zorgoom.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 共享数据操作
 * 
 * @author win7 32
 * 
 */
public class PrefrenceUtils {
	private static final String PREFRECE_USERS = "user";

	/**
	 * 保存当前是否验证房屋
	 */
	public static void saveUser(String name, String value, Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(PREFRECE_USERS, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(name, value);
		editor.commit();
	}

	/**
	 * 获取用户表数据
	 */
	public static String getStringUser(String string, Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(PREFRECE_USERS, Context.MODE_PRIVATE);
		return preferences.getString(string, "0");
	}
	
	/**
	 * 清除用户信息
	 */
	public static void removeUser(Context mContext) {
		PrefrenceUtils.saveUser("HOUSING", null, mContext);
		PrefrenceUtils.saveUser("COMMUNITYNAME", null, mContext);
		PrefrenceUtils.saveUser("UNITID", null, mContext);
		PrefrenceUtils.saveUser("UNITAREA", null, mContext);
		PrefrenceUtils.saveUser("BLOCKID", null, mContext);
		PrefrenceUtils.saveUser("BLOCKNO", null, mContext);
		PrefrenceUtils.saveUser("COMMUNITYID", null, mContext);
		PrefrenceUtils.saveUser("state", "0", mContext);
		PrefrenceUtils.saveUser("CALLINFO", null, mContext);
		PrefrenceUtils.saveUser("MOBILE", null, mContext);
		PrefrenceUtils.saveUser("userId", null, mContext);
		PrefrenceUtils.saveUser("photo", null, mContext);
		PrefrenceUtils.saveUser("OPERID", null, mContext);
	}

}

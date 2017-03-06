package com.zorgoom.zhihework.base;

import java.io.File;
import java.lang.reflect.Field;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class RequestUtil {

	public static String PHPSESSID;

	public static void request(HttpMethod httpMethod, String url, RequestParams params,
			RequestCallBack<String> callBack) {
		// 发请求
		HttpUtils httpUtils = new HttpUtils(10000);
		httpUtils.send(httpMethod, url, params, callBack);
	}

	public static void request1(HttpMethod httpMethod, String url, RequestParams params,
			RequestCallBack<String> callBack) {
		// 发请求
		HttpUtils httpUtils = new HttpUtils(10000);
		httpUtils.send(httpMethod, url, params, callBack);
	}

	public static void requestOnSession(HttpMethod httpMethod, String url, RequestParams params,
			RequestCallBack<String> callBack) {
		// 发请求
		HttpUtils httpUtils = new HttpUtils(50000);
		// if (null != MyCookieStore.cookieStore) {
		// }
		httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
		httpUtils.send(httpMethod, url, params, callBack);
		
		// DefaultHttpClient dh = (DefaultHttpClient) httpUtils.getHttpClient();
		// MyCookieStore.cookieStore = dh.getCookieStore();
		// CookieStore cs = dh.getCookieStore();
		// List<Cookie> cookies = cs.getCookies();
		// for (int i = 0; i < cookies.size(); i++) {
		// if ("ASP.NET_Sessionid".equals(cookies.get(i).getName())) {
		// PHPSESSID = cookies.get(i).getValue();
		// break;
		// }
		// }
	}
	public static void requestOnSession1(HttpMethod httpMethod, String url, RequestParams params,
			RequestCallBack<File> callBack) {
		// 发请求
		HttpUtils httpUtils = new HttpUtils(10000);
		// if (null != MyCookieStore.cookieStore) {
		// }
		httpUtils.configCurrentHttpCacheExpiry(1000); // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
		httpUtils.send(httpMethod, url, params, callBack);
		
		// DefaultHttpClient dh = (DefaultHttpClient) httpUtils.getHttpClient();
		// MyCookieStore.cookieStore = dh.getCookieStore();
		// CookieStore cs = dh.getCookieStore();
		// List<Cookie> cookies = cs.getCookies();
		// for (int i = 0; i < cookies.size(); i++) {
		// if ("ASP.NET_Sessionid".equals(cookies.get(i).getName())) {
		// PHPSESSID = cookies.get(i).getValue();
		// break;
		// }
		// }
	}
	public static <T> RequestParams addPostPara(T t) {
		RequestParams params = new RequestParams();
		try {
			Field[] fiedlString = t.getClass().getFields();
			for (Field field : fiedlString) {
				if (field.getType() == String.class) {
					String fieldName = field.getName();
					String value = (String) field.get(t);
					params.addBodyParameter(fieldName, value);
				} else if (field.getType() == Integer.TYPE) {
					String fieldName = field.getName();
					Integer value = field.getInt(t);
					params.addBodyParameter(fieldName, value + "");
				} else if (field.getType() == Float.TYPE) {
					String fieldName = field.getName();
					Float value = field.getFloat(t);
					params.addBodyParameter(fieldName, value + "");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return params;
	}
}

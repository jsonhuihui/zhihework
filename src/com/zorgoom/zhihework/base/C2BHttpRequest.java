package com.zorgoom.zhihework.base;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zorgoom.util.MD5Util;
import com.zorgoom.util.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;

@SuppressLint("NewApi")
public class C2BHttpRequest {

	private static final String TAG = "MusicRead.C2BHttpRequest";
	// private HttpClient httpClient;
	// private HttpParams httpParams;
	// private HttpPost httpPost;
	// private HttpResponse httpResponse;
	private HttpListener listener;
	public static int RES_OK = 1;
	public static int RES_ERROR = 2;
	public static int RES_OUT = 3;
	private Activity context;
	/**
	 * 超时时间
	 */
	public final static int NETWORK_SUCCESS = 101;
	public final static int NETWORK_FAIL = 102;
	private boolean isShow = true;

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public C2BHttpRequest(HttpListener listener) {
		this.listener = listener;
	}

	public C2BHttpRequest(Activity context, HttpListener listener) {
		this.context = context;
		this.listener = listener;
	}

	/**
	 * 检测KEY是否正确
	 * 
	 * @param paraname
	 *            传入参数
	 * @param paraname
	 *            传入参数时间搓
	 * @param FKEY
	 *            接收的 KEY
	 * @return 为空则返回true，不否则返回false
	 */
	public String getKey(String paraname, String timestamp) {
		paraname = (null == paraname) ? "" : paraname;
		return MD5Util.MD5Encode(paraname + timestamp + ",hxOoOO000xOooO,");
	}                                                     

	private int type;

	// FKEY TIMESTAMP
	public void postHttpResponse(String url, RequestParams Params, int connectType) {
		this.type = connectType;
		if (isShow) {
			Util.showLoadDialog(context, "请求中,请稍候...");
		}
		RequestUtil.requestOnSession(HttpMethod.POST, url, Params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				disposeResponse(NETWORK_SUCCESS, arg0.result, type);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				disposeResponse(NETWORK_FAIL, null, type);
			}
		});
	}

	private String url = "";

	public void getHttpResponse(String url, int connectType) {
		this.url = url;
		this.type = connectType;
		if (isShow) {
			if (!context.isDestroyed()) {
				try {

					Util.showLoadDialog(context, "请求中,请稍候...");
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}
		RequestUtil.requestOnSession(HttpMethod.GET, url, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				disposeResponse(NETWORK_SUCCESS, arg0.result, type);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				disposeResponse(NETWORK_FAIL, null, type);
			}
		});
	}

	private void disposeResponse(int networkResult, String result, int type) {
		if (!context.isDestroyed()) {
			Util.dismissLoadDialog();
		}
		switch (networkResult) {
		case NETWORK_SUCCESS:
			// 访问结果
			if (null == result) {// 网络异常
				listener.OnResponse(null, type);
				return;
			}
			/**
			 * 返回正确数据
			 */
			listener.OnResponse(result, type);
			break;
		case NETWORK_FAIL:
			// 访问出错
			listener.OnResponse(null, type);
			break;
		case 103:
			// 访问出错
			listener.OnResponse(null, type);
			break;
		}
	}

	public void request(String servletInerface, String strCmd, final int reqId) {
		if (TextUtils.isEmpty(strCmd)) {
			httpUrl = servletInerface;
		} else {
			httpUrl = servletInerface + "?" + strCmd;
		}
		RequestUtil.requestOnSession(HttpMethod.POST, httpUrl, null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				listener.OnResponse(arg0.result, reqId);
				// disposeResponse(NETWORK_SUCCESS, arg0.result, type);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// disposeResponse(NETWORK_FAIL, null, type);
				listener.OnResponse(null, reqId);
			}
		});
	}

	/***
	 * @Title: Request
	 * @Description: http请求
	 * @param 服务器定义的servletInerface
	 * @param 打包好的json数据jsonEncoding
	 * @return String 结果
	 * @author pengliying
	 * @Date 2015-4-20下午11:08:58
	 */
	String httpUrl;
	//
	// public void request(String servletInerface, String strCmd, final int
	// reqId) {
	// if (TextUtils.isEmpty(strCmd)) {
	// httpUrl = servletInerface;
	// } else {
	// httpUrl = servletInerface + "?" + strCmd;
	// }
	//
	// Log.i(TAG, "请求url：" + httpUrl);
	// try {
	// httpPost = new HttpPost(httpUrl);
	// } catch (Exception e) {
	// Log.e(TAG, "e：" + e.getMessage());
	// listener.OnResponse(null, reqId);
	// return;
	// }
	// Thread thread = new Thread() {
	// @Override
	// public void run() {
	// super.run();
	// String result = null;
	// try {
	// httpResponse = httpClient.execute(httpPost);
	// HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
	// HttpConnectionParams.setSoTimeout(httpParams, 20000);
	// if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	// result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
	// listener.OnResponse(result, reqId);
	// Log.i(TAG, "http返回：" + result);
	// } else {
	// listener.OnResponse(null, reqId);
	// Log.i("error", "http请求失败");
	// }
	// // 服务器请求失败
	// } catch (Exception e) {
	// Log.e(TAG, "e:" + e.getMessage());
	// e.printStackTrace();
	// listener.OnResponse(null, reqId);
	// }
	// }
	// };
	// thread.start();
	//
	// }

	/*
	 * public void requestUptale(String servletInerface, String filepath, String
	 * filename) {
	 * 
	 * String end = "\r\n"; String twoHyphens = "--"; String boundary = "*****";
	 * try { URL url = new URL(servletInerface); HttpURLConnection con =
	 * (HttpURLConnection) url.openConnection();
	 *//* 允许Input、Output，不使用Cache *//*
									 * con.setDoInput(true);
									 * con.setDoOutput(true);
									 * con.setUseCaches(false); // 设置http连接属性
									 * con.setRequestMethod("POST");
									 * con.setRequestProperty("Connection",
									 * "Keep-Alive");
									 * con.setRequestProperty("Charset",
									 * "UTF-8");
									 * con.setRequestProperty("Content-Type",
									 * "multipart/form-data;boundary=" +
									 * boundary); DataOutputStream ds = new
									 * DataOutputStream(con.getOutputStream());
									 * ds.writeBytes(twoHyphens + boundary +
									 * end); ds.writeBytes(
									 * "Content-Disposition: form-data; " +
									 * "name=\"file1\";filename=\"" + filename +
									 * "\"" + end); ds.writeBytes(end); //
									 * 取得文件的FileInputStream FileInputStream
									 * fStream = new FileInputStream(filepath);
									 *//* 设置每次写入1024bytes *//*
															 * int bufferSize =
															 * 1024; byte[]
															 * buffer = new
															 * byte[bufferSize];
															 * int length = -1;
															 *//* 从文件读取数据至缓冲区 *//*
																				 * while
																				 * (
																				 * (
																				 * length
																				 * =
																				 * fStream
																				 * .
																				 * read
																				 * (
																				 * buffer
																				 * )
																				 * )
																				 * !=
																				 * -1)
																				 * {
																				 *//* 将资料写入DataOutputStream中 *//*
																											 * ds
																											 * .
																											 * write
																											 * (
																											 * buffer,
																											 * 0,
																											 * length
																											 * )
																											 * ;
																											 * }
																											 * ds
																											 * .
																											 * writeBytes
																											 * (
																											 * end
																											 * )
																											 * ;
																											 * ds
																											 * .
																											 * writeBytes
																											 * (
																											 * twoHyphens
																											 * +
																											 * boundary
																											 * +
																											 * twoHyphens
																											 * +
																											 * end
																											 * )
																											 * ;
																											 * fStream
																											 * .
																											 * close
																											 * (
																											 * )
																											 * ;
																											 * ds
																											 * .
																											 * flush
																											 * (
																											 * )
																											 * ;
																											 *//* 取得Response内容 *//*
																																 * InputStream
																																 * is
																																 * =
																																 * con
																																 * .
																																 * getInputStream
																																 * (
																																 * )
																																 * ;
																																 * int
																																 * ch;
																																 * StringBuffer
																																 * b
																																 * =
																																 * new
																																 * StringBuffer
																																 * (
																																 * )
																																 * ;
																																 * while
																																 * (
																																 * (
																																 * ch
																																 * =
																																 * is
																																 * .
																																 * read
																																 * (
																																 * )
																																 * )
																																 * !=
																																 * -1)
																																 * {
																																 * b
																																 * .
																																 * append
																																 * (
																																 * (
																																 * char)
																																 * ch
																																 * )
																																 * ;
																																 * }
																																 *//* 将Response显示于Dialog *//*
																																							 * Looper
																																							 * .
																																							 * prepare
																																							 * (
																																							 * )
																																							 * ;
																																							 * /
																																							 * /
																																							 * ds
																																							 * .
																																							 * close
																																							 * (
																																							 * )
																																							 * ;
																																							 *//* 关闭DataOutputStream *//*
																																													 * listener
																																													 * .
																																													 * OnResponse
																																													 * (
																																													 * b
																																													 * .
																																													 * toString
																																													 * (
																																													 * )
																																													 * ,
																																													 * reqId
																																													 * )
																																													 * ;
																																													 * }
																																													 * catch
																																													 * (Exception
																																													 * e)
																																													 * {
																																													 * Looper
																																													 * .
																																													 * prepare
																																													 * (
																																													 * )
																																													 * ;
																																													 * /
																																													 * /
																																													 * listener
																																													 * .
																																													 * OnResponse
																																													 * (
																																													 * null,
																																													 * reqId
																																													 * )
																																													 * ;
																																													 * }
																																													 * 
																																													 * }
																																													 */
}

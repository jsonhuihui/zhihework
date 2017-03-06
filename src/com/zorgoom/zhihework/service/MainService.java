package com.zorgoom.zhihework.service;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.zorgoom.chad4.CallActivity;
import com.zorgoom.util.ExampleUtil;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;
import com.zorgoom.zhihework.Login_Activity;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.dialog.ToastUtil;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import jni.http.HttpManager;
import jni.http.HttpResult;
import jni.http.RtcHttpClient;
import jni.util.Utils;
import rtc.sdk.clt.RtcClientImpl;
import rtc.sdk.common.RtcConst;
import rtc.sdk.common.SdkSettings;
import rtc.sdk.core.RtcRules;
import rtc.sdk.iface.ClientListener;
import rtc.sdk.iface.Connection;
import rtc.sdk.iface.ConnectionListener;
import rtc.sdk.iface.Device;
import rtc.sdk.iface.DeviceListener;
import rtc.sdk.iface.RtcClient;

/**
 * 程序的主要后台服务
 */
public class MainService extends Service {

	public static final String APP_ID = "71460";
	public static final String APP_KEY = "dc34b104-2694-429f-a69e-e9a0389ea7f4";
	public static final String LOGTAG = "Intercom";

	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";

	// 智能家居 控制
	public static final String SMART_CONTROL = "smart.control";

	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	public static final String CALL = "CLL_REGISTER";

	public static RtcClient rtcClient = null;
	public static Device device = null;
	public static Connection callConnection;

	public static String fromNo = null;

	protected Handler handler = null;

	AlarmManager mAlarmManager = null;
	PendingIntent mPendingIntent = null;

	@Override
	public void onCreate() {
		initHandler();
		// 初始化天翼
		initRtcClient();
		// 初始化极光
		initJPush();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Toast.makeText(getApplicationContext(), startId + "进程常驻中" + flags,
		// Toast.LENGTH_LONG).show();
		return START_STICKY;
	}

	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	private void initJPush() {
		JPushInterface.getRegistrationID(getApplicationContext());
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		filter.addAction(SMART_CONTROL);
		filter.addAction(CALL);
		filter.addAction("JPush");
		filter.addAction("call");
		registerReceiver(mMessageReceiver, filter);

		String name = PrefrenceUtils.getStringUser("userId", getApplicationContext());
		if (name.equals("0")) {
			return;
		}
		JPushInterface.setAliasAndTags(getApplicationContext(), name, null, mAliasCallback);
	}

	private TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i("", logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i("", logs);
				if (ExampleUtil.isConnected(getApplicationContext())) {
					handler.sendEmptyMessage(101);
				} else {
					Log.i("", "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e("", logs);
			}
			// ExampleUtil.showToast(logs, getApplicationContext());
		}

	};

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			switch (action) {
			case MESSAGE_RECEIVED_ACTION:
				String messge = intent.getStringExtra(KEY_MESSAGE);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				ToastUtil.showMessage(getApplicationContext(), showMsg.toString());
				break;
			case CALL:// 当重新登录，或者切换房屋后，重新初始化天翼
				if (null != device) {
					device.release();
					device = null;
				}
				if (rtcClient != null) {
					rtcClient.release();
					rtcClient = null;
				}
				// 初始化天翼
				initRtcClient();
				break;
			case "JPush":// 推送设置tags值
				JPushInterface.setAliasAndTags(getApplicationContext(),
						PrefrenceUtils.getStringUser("userId", getApplicationContext()), null, mAliasCallback);
				break;
			case "call":// 户户通呼叫
				String name = intent.getStringExtra("name");
				try {
					String remoteuri = "";
					remoteuri = RtcRules.UserToRemoteUri_new(name, RtcConst.UEType_Any);
					JSONObject jinfo = new JSONObject();
					jinfo.put(RtcConst.kCallRemoteUri, remoteuri);
					jinfo.put(RtcConst.kCallInfo, "123"); // opt
					jinfo.put(RtcConst.kCallType, mCT);
					MainService.callConnection = MainService.device.connect(jinfo.toString(), connectionListener);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case SMART_CONTROL:// 智能家居控制
				int type = intent.getIntExtra("type", 0);
				int senceId = intent.getIntExtra("senceId", 0);
				int uid = intent.getIntExtra("uid", 0);
				int status = intent.getIntExtra("status", 0);

				if (null != MainService.device) {
					Util.sendIndex = 5;
					int returnMsg = MainService.device.sendIm(
							RtcRules.UserToRemoteUri_new("30020203", RtcConst.UEType_Any), "text/plain",
							type + "/" + senceId + "/" + uid + "/" + status);
					if (returnMsg == 0) {
						// ToastUtil.showMessage(mContext, "请稍候,正在开门...");
					} else {
						ToastUtil.showMessage(getApplicationContext(), "操作失败");
					}
				} else {
					ToastUtil.showMessage(getApplicationContext(), "操作失败");
				}
				break;
			}
		}
	}

	int mCT = RtcConst.CallType_A_V;
	// /** The m ct. */
	// int mCT = RtcConst.CallType_A_V;

	@SuppressLint("HandlerLeak")
	protected void initHandler() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 4:
					Toast.makeText(getApplicationContext(), "您的帐号在异地登录，请及时更改密码！", Toast.LENGTH_LONG).show();
					// showDialog("提示", "您的帐号在异地登录，请及时更改密码！");
					PrefrenceUtils.removeUser(getApplicationContext());
					// MyActivityManager.getInstance().killAllActivity();
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
					Intent intent2 = new Intent(getApplicationContext(), Login_Activity.class);
					intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent2);
					break;
				case 107:
					onResponseGetToken(msg);
					break;
				case 2:
					Intent intent = new Intent(getBaseContext(), CallActivity.class);
					intent.putExtra("cInfo", cInfo);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					break;
				case 3:// 当前是互户通的拨号 ，提示呼叫界面 更新界面
					sendBroadcast(new Intent("huhutongCall"));
					break;
				case 106://
					Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_LONG).show();
					break;
				case 1://
					int m = msg.arg1;
					switch (m) {
					case 200:
						ToastUtil.showMessage(getApplicationContext(), "操作成功...");
						break;
					case 404:
						ToastUtil.showMessage(getApplicationContext(), "操作失败，室内主机不存在");
						break;
					case 480:
					default:
						ToastUtil.showMessage(getApplicationContext(), "操作失败，室内主机不在线，请检查网络或重启室内主机");
						break;
					}
					break;
				}
			}
		};
	}

	private void initRtcClient() {
		rtcClient = new RtcClientImpl();
		rtcClient.initialize(this.getApplicationContext(), new ClientListener() {
			@Override // 初始化结果回调
			public void onInit(int result) {
				Utils.PrintLog(5, "ClientListener", "onInit,result=" + result);// 常见错误9003:网络异常或系统时间差的太多
				if (result == 0) {
					rtcClient.setAudioCodec(RtcConst.ACodec_OPUS);
					rtcClient.setVideoCodec(RtcConst.VCodec_VP8);
					rtcClient.setVideoAttr(RtcConst.Video_SD);
					startGetToken();
				}
			}
		});
	}

	C2BHttpRequest c2BHttpRequest = null;
	private String token;

	private void onResponseGetToken(Message msg) {
		HttpResult ret = (HttpResult) msg.obj;
		Utils.PrintLog(5, LOGTAG, "handleMessage getCapabilityToken status:" + ret.getStatus());
		JSONObject jsonrsp = (JSONObject) ret.getObject();
		if (jsonrsp != null && jsonrsp.isNull("code") == false) {
			try {
				String code = jsonrsp.getString(RtcConst.kcode);
				String reason = jsonrsp.getString(RtcConst.kreason);
				Utils.PrintLog(5, LOGTAG, "Response getCapabilityToken code:" + code + " reason:" + reason);
				if (code.equals("0")) {
					token = jsonrsp.getString(RtcConst.kcapabilityToken);
					Utils.PrintLog(5, LOGTAG, "handleMessage getCapabilityToken:" + token);
					rtcRegister();
				} else {
					// onGetTokenError();
					Utils.PrintLog(5, LOGTAG, "获取token失败 [status:" + ret.getStatus() + "]" + ret.getErrorMsg());
				}
			} catch (JSONException e) {
				e.printStackTrace();
				// onGetTokenError();
				Utils.PrintLog(5, LOGTAG, "获取token失败 [status:" + e.getMessage() + "]");
			}
		} else {
			handler.sendEmptyMessage(106);

		}
	}

	/**
	 * 终端直接从rtc平台获取token，应用产品需要通过自己的服务器来获取，rtc平台接口请参考开发文档<2.5>章节.
	 */
	private void getTokenFromServer() {
		String name = PrefrenceUtils.getStringUser("CALLINFO", getApplicationContext());
		if (name.equals("0")) {
			return;
		}
		RtcConst.UEAPPID_Current = RtcConst.UEAPPID_Self;// 账号体系，包括私有、微博、QQ等，必须在获取token之前确定。
		JSONObject jsonobj = HttpManager.getInstance().CreateTokenJson(0, name, RtcHttpClient.grantedCapabiltyID, "");
		HttpResult ret = HttpManager.getInstance().getCapabilityToken(jsonobj, APP_ID, APP_KEY);
		Message msg = new Message();
		msg.what = 107;
		msg.obj = ret;
		handler.sendMessage(msg);
	}

	private void startGetToken() {
		new Thread() {
			public void run() {
				getTokenFromServer();
			}
		}.start();
	}

	private void rtcRegister() {
		try {
			JSONObject jargs = SdkSettings.defaultDeviceSetting();
			jargs.put(RtcConst.kAccPwd, token);
			// 账号格式形如“账号体系-号码~应用id~终端类型”，以下主要设置账号内各部分内容，其中账号体系的值要在获取token之前确定，默认为私有账号
			jargs.put(RtcConst.kAccAppID, APP_ID);// 应用id
			// jargs.put(RtcConst.kAccUser, key); // 号码
			jargs.put(RtcConst.kAccUser, PrefrenceUtils.getStringUser("CALLINFO", getApplicationContext())); // 号码
			jargs.put(RtcConst.kAccType, RtcConst.UEType_Current);// 终端类型
			jargs.put(RtcConst.kAccSrtp, 2);
			device = rtcClient.createDevice(jargs.toString(), deviceListener); // 注册

			AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			rtcClient.enableSpeaker(audioManager, true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String cInfo = "";
	/** The m a listener. */
	DeviceListener deviceListener = new DeviceListener() {
		@Override
		public void onDeviceStateChanged(int result) {
			Utils.PrintLog(5, "DeviceListener", "onDeviceStateChanged,result=" + result);
			if (result == RtcConst.CallCode_Success) { // 注销也存在此处
				// onConnectSuccess();
			} else if (result == RtcConst.NoNetwork) {
			} else if (result == RtcConst.ChangeNetwork) {
			} else if (result == RtcConst.PoorNetwork) {
				onPoorNetWork();
			} else if (result == RtcConst.ReLoginNetwork) {
				// 网络原因导致多次登陆不成功，由用户选择是否继续，如想继续尝试，可以重建device
				Utils.PrintLog(5, "DeviceListener", "onDeviceStateChanged,ReLoginNetwork");
			} else if (result == RtcConst.DeviceEvt_KickedOff) {
				// 被另外一个终端踢下线，由用户选择是否继续，如果再次登录，需要重新获取token，重建device
				// Utils.PrintLog(5, "DeviceListener",
				// "onDeviceStateChanged,DeviceEvt_KickedOff");
				handler.sendEmptyMessage(4);
			} else if (result == RtcConst.DeviceEvt_MultiLogin) {
				Utils.PrintLog(5, "DeviceListener", "onDeviceStateChanged,DeviceEvt_MultiLogin");
			} else {
			}
		}

		private void onPoorNetWork() {
			Utils.PrintLog(5, LOGTAG, "onPoorNetWork");
		}

		@Override
		public void onNewCall(Connection call) {
			JSONObject callInfo = null;
//			String acceptMember = null;
			cInfo = null;
			try {
				callInfo = new JSONObject(call.info());
//				acceptMember = callInfo.getString("uri");
				cInfo = callInfo.getString(RtcConst.kCallInfo);
			} catch (JSONException e) {
			}
			Utils.PrintLog(5, "DeviceListener", "onNewCall,call=" + call.info());
			if (callConnection != null) {
				call.reject();
				call = null;
				Utils.PrintLog(5, "DeviceListener", "onNewCall,reject call");
				return;
			}
			try {
				fromNo = callInfo.getString("uri");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			callConnection = call;
			call.setIncomingListener(connectionListener);
			handler.sendEmptyMessage(2);
		}

		@Override
		public void onQueryStatus(int status, String paramers) {
			Utils.PrintLog(5, LOGTAG, paramers);
		}

		@Override
		public void onSendIm(int status) {
			Intent intent = new Intent();
			switch (Util.sendIndex) {
			case 1:// 开锁
				intent.setAction("lock");
				intent.putExtra("returnMsg", status);
				break;
			case 2:// 验证门口机是否在线
				intent.setAction("checklock");
				intent.putExtra("returnMsg", status);
				break;
			case 3:// 户户通呼叫是否在线
				intent.setAction("huhutong");
				intent.putExtra("returnMsg", status);
				break;
			case 4:// 呼叫界面开锁
				break;
			case 5:// 智能主机发送信息
				handler.obtainMessage(1, status, 0).sendToTarget();
				break;
			}
			sendBroadcast(intent);
		}

		@Override
		public void onReceiveIm(String from, String mime, String content) {
		}

	};

	ConnectionListener connectionListener = new ConnectionListener() {
		@Override
		public void onConnecting() {
			Log.i("", "");

		}

		@Override
		public void onConnected() {
			Log.i("", "");
		}

		@Override
		public void onDisconnected(int code) {
			Utils.PrintLog(5, LOGTAG, "onDisconnected timerDur" + callConnection.getCallDuration());
			callConnection = null;
			sendBroadcast(new Intent("finish"));
		}

		@Override
		public void onVideo() {
			Log.i("", "");
			sendBroadcast(new Intent("huhutongCall"));

		}

		@Override
		public void onNetStatus(int msg, String info) {
			Log.i("", "");
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (callConnection != null) {
			callConnection.disconnect();
			callConnection = null;
		}
		if (device != null) {
			device.release();
			device = null;
		}
		if (rtcClient != null) {
			rtcClient.release();
			rtcClient = null;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}

package com.zorgoom.zhihework;

import org.json.JSONObject;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.util.DataPaser;
import com.zorgoom.zhihework.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * 忘记密码
 */

public class WangJiMiMa_Activity extends BaseActivity implements HttpListener, View.OnClickListener, Callback {

	private EditText regis_putphone, // 手机号码
//			login_editText1, // 用户名
//			login_username, // 真实姓名
			login_editText3, // 确认密码
			et_verification_code, // 验证码
			login_editText2;// 密码
	private TextView regis_next;
	private TextView ctv_verify;// 获取验证码
	private C2BHttpRequest c2BHttpRequest;
	private String user_phone, uName, userName, pwd1, pwd2, verification_code;
	private ImageView regis_back;

	private TimeCount time;// 倒计时

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wangjimima);
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initView();
		initSDK();

	}

	private void initSDK() {
		try {

			final Handler handler = new Handler(this);
			EventHandler eventHandler = new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					Message msg = new Message();
					msg.arg1 = event;
					msg.arg2 = result;
					msg.obj = data;
					handler.sendMessage(msg);
				}
			};

			SMSSDK.registerEventHandler(eventHandler); // 注册短信回调

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initView() {
//		login_editText1 = (EditText) findViewById(R.id.login_editText1);
//		login_username = (EditText) findViewById(R.id.login_username);
		login_editText3 = (EditText) findViewById(R.id.login_editText3);
		login_editText2 = (EditText) findViewById(R.id.login_editText2);
		et_verification_code = (EditText) findViewById(R.id.et_verification_code);

		regis_putphone = (EditText) findViewById(R.id.regis_putphone);// 输入手机号
		regis_next = (TextView) findViewById(R.id.regis_next);// 提交
		ctv_verify = (TextView) findViewById(R.id.ctv_verify);// 获取验证码
		regis_back = (ImageView) findViewById(R.id.regis_back);// 返回

		regis_next.setOnClickListener(this);
		regis_back.setOnClickListener(this);
		ctv_verify.setOnClickListener(this);
	}

	/**
	 * @ClassName: TimeCount
	 * @Description: 倒计时内部类
	 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@SuppressLint({ "ResourceAsColor", "NewApi" })
		@Override
		public void onFinish() {// 计时完毕时触发
			ctv_verify.setClickable(true);
			ctv_verify.setText("重新获取");
			ctv_verify.setBackground(getResources().getDrawable(R.drawable.bt_click_bg));

		}

		@SuppressLint("ResourceAsColor")
		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示

			ctv_verify.setText(millisUntilFinished / 1000 + "秒");
		}
	}

	int index = 0;

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ctv_verify:// 获取验证码
			user_phone = regis_putphone.getText().toString().trim();
			if (!TextUtils.isEmpty(user_phone)) {
				SMSSDK.getVerificationCode("86", user_phone);// 获取短信
			} else {
				Toast.makeText(this, "电话号码不能为空", 0).show();
				return;
			}
			index = 1;
			time = new TimeCount(60000, 1000);// 构造CountDownTimer对象
			time.start();
			ctv_verify.setBackground(getResources().getDrawable(R.drawable.bt_bg6));
			ctv_verify.setClickable(false);

			break;
		case R.id.regis_next://
			// user_phone = regis_putphone.getText().toString();
//			uName = login_editText1.getText().toString();
//			userName = login_username.getText().toString();
			pwd1 = login_editText2.getText().toString();
			pwd2 = login_editText3.getText().toString();
			verification_code = et_verification_code.getText().toString();

			if (index == 0) {
				Toast.makeText(this, "请获取验证码", Toast.LENGTH_LONG).show();
				return;
			}
			if (code == null) {
				if (!TextUtils.isEmpty(verification_code)) {
					SMSSDK.submitVerificationCode("86", user_phone, verification_code);// 验证短信
				} else {
					Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
					return;
				}
			} else {
				if(verification_code.equals(code)){
					register();
				}else{
					Toast.makeText(this, "验证码错误", Toast.LENGTH_LONG).show();
				}
			}
			break;
		case R.id.regis_back:
			finish();
			break;

		default:
			break;

		}

	}

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			BaseModel baseModel = DataPaser.json2Bean(result, BaseModel.class);
			if (null != baseModel) {
				if (baseModel.getCode().equals("101")) {
					ToastUtil.showMessage(getApplicationContext(), baseModel.getMsg());
					finish();
				} else {
					ToastUtil.showMessage(getApplicationContext(), baseModel.getMsg());
				}
			}
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			register();
		}
	};

	private void register() {
		if ("".equals(user_phone) || user_phone == null) {
			ToastUtil.showMessage1(WangJiMiMa_Activity.this, "手机号码不能为空", 300);
			return;
		}
//		if ("".equals(uName) || uName == null) {
//			ToastUtil.showMessage1(WangJiMiMa_Activity.this, "用户名不能为空", 300);
//			return;
//		}
//		if ("".equals(userName) || userName == null) {
//			ToastUtil.showMessage1(WangJiMiMa_Activity.this, "真实姓名不能为空", 300);
//			return;
//		}
		if ("".equals(pwd1) || pwd1 == null) {
			ToastUtil.showMessage1(WangJiMiMa_Activity.this, "密码不能为空", 300);
			return;
		}
		if ("".equals(pwd2) || pwd2 == null) {
			ToastUtil.showMessage1(WangJiMiMa_Activity.this, "确认密码不能为空", 300);
			return;
		}
		if (!pwd2.equals(pwd1)) {
			ToastUtil.showMessage1(WangJiMiMa_Activity.this, "两次密码输入不一样", 300);
			return;
		}
		RequestParams params = new RequestParams();

		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(uName + "", timestamp);

		params.addBodyParameter("FKEY", key);
		params.addBodyParameter("TIMESTAMP", timestamp);

		params.addBodyParameter("USERNAME", uName);
		params.addBodyParameter("PASSWORD", pwd1);
		params.addBodyParameter("MOBILE", user_phone);
		params.addBodyParameter("REALNAME", userName);
		c2BHttpRequest.postHttpResponse(Http.REGISTER, params, 1);
	}

	private String code = null;

	@Override
	public boolean handleMessage(Message msg) {

		int event = msg.arg1;
		int result = msg.arg2;
		Object data = msg.obj;
		if (result == SMSSDK.RESULT_COMPLETE) {
			System.out.println("--------result" + event);
			// 回调完成
			if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
				// 验证码成功验证
				// Toast.makeText(this, "验证码成功验证", Toast.LENGTH_SHORT).show();
				code = verification_code;
				handler.sendEmptyMessage(1);
			} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
				// 获取验证码成功
				Toast.makeText(this, "发送验证码成功", Toast.LENGTH_SHORT).show();

			} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
				// 返回支持发送验证码的国家列表
			}

		} else {
			int status = 0;
			try {
				((Throwable) data).printStackTrace();
				Throwable throwable = (Throwable) data;

				JSONObject object = new JSONObject(throwable.getMessage());
				String des = object.optString("detail");
				status = object.optInt("status");
				// if(status == 468){
				// Toast.makeText(this, "请先获取验证码", Toast.LENGTH_SHORT).show();
				// return false;
				// }
				if (!TextUtils.isEmpty(des)) {
					Toast.makeText(this, des, Toast.LENGTH_SHORT).show();
					return false;
				}
			} catch (Exception e) {
				SMSLog.getInstance().w(e);
			}

		}
		return false;
	}

}

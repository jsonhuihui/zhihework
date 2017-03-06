package com.zorgoom.zhihework;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zorgoom.chad4.CallActivity;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.popuwindow.HelpPopuWindow;
import com.zorgoom.zhihework.service.MainService;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import rtc.sdk.common.RtcConst;
import rtc.sdk.core.RtcRules;

public class HuhutongActivity extends MBaseActivity implements OnClickListener, HttpListener {
	private ImageView btnCancel;
	// private String[] numbers;
	private EditText edtNumber;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huhutong_layout);
		// numbers = getResources().getStringArray(R.array.numbers);
		edtNumber = (EditText) findViewById(R.id.edtNumber);
		edtNumber.setInputType(InputType.TYPE_NULL);
		btnCancel = (ImageView) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		findViewById(R.id.btn_0).setOnClickListener(this);
		findViewById(R.id.btn_1).setOnClickListener(this);
		findViewById(R.id.btn_2).setOnClickListener(this);
		findViewById(R.id.btn_3).setOnClickListener(this);
		findViewById(R.id.btn_4).setOnClickListener(this);
		findViewById(R.id.btn_5).setOnClickListener(this);
		findViewById(R.id.btn_6).setOnClickListener(this);
		findViewById(R.id.btn_7).setOnClickListener(this);
		findViewById(R.id.btn_8).setOnClickListener(this);
		findViewById(R.id.btn_9).setOnClickListener(this);
		findViewById(R.id.btn_xin).setOnClickListener(this);
		findViewById(R.id.huhubohao).setOnClickListener(this);
		findViewById(R.id.btn_jin).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.shiyongzhinan).setOnClickListener(this);

		IntentFilter filter = new IntentFilter();
		filter.addAction("huhutong");
		registerReceiver(receiver, filter);
	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);
	private String callInfo = "";

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.shiyongzhinan:
			new HelpPopuWindow(this, v);
			break;
		case R.id.regis_back:
			finish();
			break;
		case R.id.btnCancel:
			setEditValue();
			break;
		case R.id.huhubohao:
			String str = edtNumber.getText().toString();
			if (str.equals("")) {
				ToastUtil.showMessage(getApplicationContext(), "呼叫号码不能为空");
				return;
			}
			Pattern p = Pattern.compile("[0-9]*");
			Matcher m = p.matcher(str);
			if (!m.matches()) {
				ToastUtil.showMessage(getApplicationContext(), "呼叫格式有误");
				return;
			}
			if (str.length() != 6) {
				ToastUtil.showMessage(getApplicationContext(), "呼叫格式有误");
				return;
			}

			String BLOCKNO = str.substring(0, 2);
			String UNITNO = str.substring(2, 6);
			callInfo = PrefrenceUtils.getStringUser("COMMUNITYID", this) + BLOCKNO + UNITNO;

			String localInfo = PrefrenceUtils.getStringUser("CALLINFO", this);
			if (callInfo.equals(localInfo)) {
				ToastUtil.showMessage(getApplicationContext(), "不能呼叫自己");
				return;
			}

			String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", this);
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(COMMUNITYID, timestamp);

			c2BHttpRequest.getHttpResponse(Http.FINDUNIT + "COMMUNITYID=" + COMMUNITYID + "&BLOCKNO=" + BLOCKNO
					+ "&UNITNO=" + UNITNO + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
			break;
		default:
			String text = v.getTag().toString();
			if (null == text || text.equals(""))
				return;
			Editable edit = edtNumber.getText();
			if (edit.length() > 0) {
				edit.insert(edit.length(), text);
			} else {
				edit.insert(0, text);
			}
			break;
		}
	}

	private void setEditValue() {
		int start = edtNumber.getSelectionStart();
		if (start > 0) {
			edtNumber.getText().delete(start - 1, start);
		}
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			int returnMsg = intent.getIntExtra("returnMsg", -1);
			switch (action) {
			case "huhutong":
				switch (returnMsg) {
				case 200:
					Intent intent2 = new Intent(getApplicationContext(), CallActivity.class);
					intent2.putExtra("index", 1);
					intent2.putExtra("name", callInfo);
					intent2.putExtra("cInfo", "123");
					startActivity(intent2);
					break;
				case 404:
					ToastUtil.showMessage(context, "用户不在线");
					break;
				case 480:
				default:
					ToastUtil.showMessage(context, "用户不在线");
					break;
				}
				break;
			}
		}
	};

	@Override
	public void OnResponse(String result, int connectType) {
		if (null != result) {
			switch (connectType) {
			case 1:
				BaseModel listVO = DataPaser.json2Bean(result, BaseModel.class);
				if (null != listVO) {
					if (listVO.getCode().equals("101")) {

						if (null != MainService.device) {
							Util.sendIndex = 3;
							int returnMsg = MainService.device.sendIm(
									RtcRules.UserToRemoteUri_new(callInfo, RtcConst.UEType_Any), "text/plain", "111");
							if (returnMsg == 0) {
								// ToastUtil.showMessage(mContext,
								// "请稍候,正在开门...");
							} else {
								ToastUtil.showMessage(this, "呼叫失败，请重新呼叫");
							}
						} else {
							ToastUtil.showMessage(this, "呼叫失败，请重新呼叫");
						}
					} else {
						ToastUtil.showMessage(getApplicationContext(), listVO.getMsg());
					}
				}
				break;

			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != receiver) {
			unregisterReceiver(receiver);
		}
	}
}
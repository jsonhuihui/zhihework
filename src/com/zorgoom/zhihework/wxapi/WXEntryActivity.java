package com.zorgoom.zhihework.wxapi;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.os.Bundle;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		api = WXAPIFactory.createWXAPI(this, "wx26877c6742b5b2c5", true);
		api.registerApp("wx26877c6742b5b2c5");

		api.handleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq arg0) {

	}

	@Override
	public void onResp(BaseResp resp) {
		String result = "";
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "errcode_success";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "errcode_cancel";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "errcode_deny";
			break;
		default:
			result = "errcode_unknown";
			break;
		}
	}

}

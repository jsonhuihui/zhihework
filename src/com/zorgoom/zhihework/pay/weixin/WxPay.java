package com.zorgoom.zhihework.pay.weixin;

import java.util.Map;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.PayActivity;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.SAppWinXinPayVO;
import com.zorgoom.zhihework.vo.SAppWinXinPayVO.SAppWinXinPayResVO;
import com.zorgoom.util.DataPaser;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.widget.Toast;

public class WxPay implements HttpListener {
	private PayActivity context;
	// private String price;
	private RequestParams params;
	private int index;

	public WxPay(PayActivity context, RequestParams params, int index) {
		super();
		this.context = context;
		// this.price = orderPrice;
		this.params = params;
		this.index = index;
		pay();
	}

	private IWXAPI msgApi = null;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;

	public void pay() {
		msgApi = WXAPIFactory.createWXAPI(context, "wx26877c6742b5b2c5");
		C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(context, this);
		c2BHttpRequest.postHttpResponse(Http.WEIXINPAY, params, 1);
//		if (index == 0) {// 物业缴费
//			c2BHttpRequest.postHttpResponse(Http.EAPPWINXINPAY, params, 1);
//		} else {// o2o支付
//			c2BHttpRequest.postHttpResponse(Http.ADDORDER, params, 1);
//		}

	}

	@Override
	public void OnResponse(String result, int connectType) {
		if (null != result) {
			switch (connectType) {
			case 1:// 微信支付
				try {
					SAppWinXinPayVO guestPass = DataPaser.json2Bean(result, SAppWinXinPayVO.class);
					if (null != guestPass) {
						if ("101".equals(guestPass.getCode())) {
							SAppWinXinPayResVO msg = guestPass.getsAppWinXinPayResVO();
							PayReq req = new PayReq();
							req.appId = msg.getAppId();
							req.partnerId = msg.getPartnerId();
							req.prepayId = msg.getPrepayId();
							req.nonceStr = msg.getNonceStr();
							req.timeStamp = msg.getTimeStamp();
							req.packageValue = msg.getPackageValue();
							req.sign = msg.getSign();
							req.extData = "app data"; // optional
							Toast.makeText(context, "正常调起支付", Toast.LENGTH_SHORT).show();
							// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
							// 将该app注册到微信
							msgApi.registerApp(Constants.APP_ID);
							msgApi.sendReq(req);
							// ToastUtil.showMessage(context,
							// guestPass.getMsg());
						} else {
							ToastUtil.showMessage(context, guestPass.getMsg());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		} else {

		}
	}

}

package com.zorgoom.zhihework.share;

import com.zorgoom.zhihework.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class Share {
	private IWXAPI api;

	/**
	 * AppId：wx26877c6742b5b2c5 AppSecret：5bcfbb67e0f7d90522f90976e8db8074
	 * 
	 * @param context
	 */
	private Context context;
	/**
	 * 
	 * 单一实例
	 */
	public static Share share;

	public static Share getInstance(Context context, Intent intent) {
		if (share == null) {
			share = new Share(context, intent);
		}
		return share;
	}

	private Share(Context context, Intent intent) {
		this.context = context;
		// 微信注册初始化
		api = WXAPIFactory.createWXAPI(context, "wx26877c6742b5b2c5", true);
		api.registerApp("wx26877c6742b5b2c5");

		// api.handleIntent(intent, context);
	}

	public void share2weixin(int flag, String title, String content) {
		// Bitmap bmp = BitmapFactory.decodeResource(getResources(),
		// R.drawable.weixin_share);

		if (!api.isWXAppInstalled()) {
			Toast.makeText(context, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
			return;
		}

		WXWebpageObject webpage = new WXWebpageObject();
		// webpage.webpageUrl = "http://baidu.com";
		WXMediaMessage msg = new WXMediaMessage(webpage);

		msg.title = title;
		msg.description = content;
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
		msg.setThumbImage(thumb);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag;
		api.sendReq(req);
	}
}

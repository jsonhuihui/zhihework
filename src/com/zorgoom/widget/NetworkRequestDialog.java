
/**************************************************************************************
* [Project]
*       MyProgressDialog
* [Package]
*       com.lxd.widgets
* [FileName]
*       CustomProgressDialog.java
* [Copyright]
*       Copyright 2012 LXD All Rights Reserved.
* [History]
*       Version          Date              Author                        Record
*--------------------------------------------------------------------------------------
*       1.0.0           2012-4-27         lxd (rohsuton@gmail.com)        Create
**************************************************************************************/

package com.zorgoom.widget;

import com.zorgoom.zhihework.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

/**
 */
public class NetworkRequestDialog extends Dialog {

	public NetworkRequestDialog(Context context, int dialog) {
		super(context, dialog);
	}

	private TextView id_tv_loadingmsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.network_request_dialog);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		id_tv_loadingmsg = (TextView) findViewById(R.id.id_tv_loadingmsg);
		id_tv_loadingmsg.setText(msg);
	}

	private String msg;

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

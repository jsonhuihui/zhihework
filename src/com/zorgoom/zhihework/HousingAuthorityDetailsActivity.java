package com.zorgoom.zhihework;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.MyActivityManager;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HousingAuthorityDetailsActivity extends MBaseActivity implements HttpListener, OnClickListener {

	private EditText jiequText,numText;
	private TextView nameText;
	private TextView phoneText;

	private String type;
	private Button postBtn;
	private TextView my_set_buyaddress_sheng;
	private String citys, communitys, blocks, units, UNITID, BLOCKID, COMPANYID, UNITNO, BLOCKNO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.housing_authority_details);

		Intent i = getIntent();
		citys = i.getStringExtra("citys");
		communitys = i.getStringExtra("communitys");
		blocks = i.getStringExtra("blocks");
		units = i.getStringExtra("units");
		UNITID = i.getStringExtra("UNITID");
		BLOCKID = i.getStringExtra("BLOCKID");
		COMPANYID = i.getStringExtra("COMPANYID");
		UNITNO = i.getStringExtra("UNITNO");
		BLOCKNO = i.getStringExtra("BLOCKNO");

		jiequText = (EditText) this.findViewById(R.id.my_set_buyaddress_jiequ);
		nameText = (TextView) this.findViewById(R.id.my_set_buyaddress_name);
		numText = (EditText) this.findViewById(R.id.my_set_buyaddress_phone);
		phoneText = (TextView) this.findViewById(R.id.my_set_buyaddress_phone);
		postBtn = (Button) this.findViewById(R.id.my_set_buyaddress_address_btn);
		postBtn.setOnClickListener(this);
		phoneText.setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.my_set_buyaddress_sheng_linear).setOnClickListener(this);

		my_set_buyaddress_sheng = (TextView) findViewById(R.id.my_set_buyaddress_sheng);
		my_set_buyaddress_sheng.setText(citys + " " + communitys + " " );//+ blocks + " " + units + ""
		nameText.setText(PrefrenceUtils.getStringUser("MOBILE", this));
	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			switch (reqId) {
			case 1:
				BaseModel baseModel = DataPaser.json2Bean(result, BaseModel.class);
				if (null != baseModel) {
					if (baseModel.getCode().equals("101")) {
						ToastUtil.showMessage(getApplicationContext(), baseModel.getMsg());
						MyActivityManager.getInstance().killActivity(HousingAuthorityActivity.class);
						// PrefrenceUtils.saveUser("HOUSING", communitys +
						// blocks +
						// units, this);
						// PrefrenceUtils.saveUser("UNITID", UNITID + "", this);
						// PrefrenceUtils.saveUser("BLOCKID", BLOCKID + "",
						// this);
						// PrefrenceUtils.saveUser("BLOCKNO", BLOCKNO + "",
						// this);
						// PrefrenceUtils.saveUser("COMMUNITYID", COMMUNITYID +
						// "",
						// this);
						// PrefrenceUtils.saveUser("CALLINFO", COMMUNITYID + ""
						// +
						// BLOCKNO + "" + UNITNO, this);
						// // PrefrenceUtils.saveUser("CALLINFO",
						// infoVO.getUNITNO(),
						// // mContext);
						// sendBroadcast(new Intent(MainService.CALL));
						// // 已经有房屋认证了
						// PrefrenceUtils.saveUser("state", "2", this);
						ToastUtil.showMessage(getApplicationContext(), baseModel.getMsg());
						finish();
					} else {
						ToastUtil.showMessage(getApplicationContext(), baseModel.getMsg());
					}
				}
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		case R.id.my_set_buyaddress_sheng_linear:
			finish();
			break;
//		case R.id.my_set_buyaddress_phone:
//			final String[] items = new String[] { "业主", "家属", "租客" };
//			AlertDialog dialog = new AlertDialog.Builder(HousingAuthorityDetailsActivity.this)
//					.setItems(items, new Dialog.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							phoneText.setText(items[which]);
//							switch (which) {
//							case 0:
//								type = "O";
//								break;
//							case 1:
//								type = "F";
//								break;
//							case 2:
//								type = "R";
//								break;
//							}
//						}
//					}).create();
//			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.show();
//			break;
		case R.id.my_set_buyaddress_address_btn:// 提交
			String phone = nameText.getText().toString();
			String name = jiequText.getText().toString();
			String num = numText.getText().toString();
			if (phone.equals("") || name.equals("")) {
				ToastUtil.showMessage(getApplicationContext(), "请输入完整信息");
				return;
			}

			RequestParams params = new RequestParams();

			// String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", this);
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(COMPANYID + "", timestamp);

			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);
			params.addBodyParameter("COMPANYID", COMPANYID);
			params.addBodyParameter("DEPTID", PrefrenceUtils.getStringUser("DEPTID", getApplicationContext()));
			params.addBodyParameter("USERNAME", name);
			params.addBodyParameter("TEL", phone);
			params.addBodyParameter("USERID", PrefrenceUtils.getStringUser("userId", getApplicationContext()));
			params.addBodyParameter("EMPNO", num);
			c2BHttpRequest.postHttpResponse(Http.AUTHENTICATION, params, 1);
			break;

		}

	}
}

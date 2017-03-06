package com.zorgoom.zhihework;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 房屋租赁发布列表
 * 
 * @author Administrator
 *
 */
public class HouseLeaseAddActivity extends MBaseActivity implements View.OnClickListener, HttpListener {

	private C2BHttpRequest c2BHttpRequest;
	private HouseLeaseAddActivity mContext;
	private TextView tv_house, tv_mianji;// 房屋名字
	private EditText et_shi;// 室
	private EditText et_ting, et_biaoti, et_miaoshu;// 室

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.house_lease_add_layout);
		mContext = this;
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
	}

	private void initView() {
		et_shi = (EditText) findViewById(R.id.et_shi);
		et_ting = (EditText) findViewById(R.id.et_ting);
		et_biaoti = (EditText) findViewById(R.id.et_biaoti);
		et_miaoshu = (EditText) findViewById(R.id.et_miaoshu);

		tv_house = (TextView) findViewById(R.id.tv_house);
		tv_mianji = (TextView) findViewById(R.id.tv_mianji);
		tv_house.setText(PrefrenceUtils.getStringUser("HOUSING", this));
		tv_mianji.setText(PrefrenceUtils.getStringUser("UNITAREA", this) + " m²");

		findViewById(R.id.tv_sumbut).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_sumbut:// 提交
			String shi = et_shi.getText().toString();
			String ting = et_ting.getText().toString();
			String biaoti = et_biaoti.getText().toString();
			String miaoshu = et_miaoshu.getText().toString();
			if (shi.equals("") || ting.equals("") || biaoti.equals("") || miaoshu.equals("")) {
				ToastUtil.showMessage(mContext, "请输入完整信息");
				return;
			}
			RequestParams params = new RequestParams();

			String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", this);
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(COMMUNITYID + "", timestamp);

			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);

			params.addBodyParameter("ROOM", shi + "室" + ting + "厅");
			params.addBodyParameter("CONTENT", miaoshu);
			params.addBodyParameter("TITLE", biaoti);
			params.addBodyParameter("UNITID", PrefrenceUtils.getStringUser("UNITID", mContext));
			params.addBodyParameter("BLOCKID", PrefrenceUtils.getStringUser("BLOCKID", mContext));
			params.addBodyParameter("USERID", PrefrenceUtils.getStringUser("userId", mContext));
			params.addBodyParameter("COMMUNITYID", COMMUNITYID);
			c2BHttpRequest.postHttpResponse(Http.ADDLEASEHOUSE, params, 1);
			break;
		case R.id.regis_back:// 返回
			finish();
			break;

		}
	}

	BaseModel guestPass;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			guestPass = DataPaser.json2Bean(result, BaseModel.class);
			if (null != guestPass) {
				if ("101".equals(guestPass.getCode())) {
					finish();
					ToastUtil.showMessage(mContext, guestPass.getMsg());
				} else {
					ToastUtil.showMessage(mContext, guestPass.getMsg());
				}
			}
		}
	}

}

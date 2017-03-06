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
 * 添加车位申请
 * 
 * @author Administrator
 *
 */
public class StallApplyAddActivity extends MBaseActivity implements View.OnClickListener, HttpListener {

	private C2BHttpRequest c2BHttpRequest;
	private StallApplyAddActivity mContext;
	private EditText et_chepai, et_remark;
	private TextView et_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stall_apply_add_layout);
		mContext = this;
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
	}

	private void initView() {
		et_chepai = (EditText) findViewById(R.id.et_number);
		et_time = (TextView) findViewById(R.id.et_time);
		et_remark = (EditText) findViewById(R.id.et_remark);
		findViewById(R.id.tv_sumbut).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);

		et_time.setText(PrefrenceUtils.getStringUser("HOUSING", this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_sumbut:// 提交
			String number = et_chepai.getText().toString();
			String remark = et_remark.getText().toString();
			if (number.equals("")) {
				ToastUtil.showMessage(mContext, "请输入完整信息");
				return;
			}
			RequestParams params = new RequestParams();

			String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", this);
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(COMMUNITYID + "", timestamp);

			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);

			params.addBodyParameter("CARNO", number);
			params.addBodyParameter("MEMO", remark);
			params.addBodyParameter("UNITID", PrefrenceUtils.getStringUser("UNITID", mContext));
			params.addBodyParameter("USERID", PrefrenceUtils.getStringUser("userId", mContext));
			params.addBodyParameter("COMMUNITYID", COMMUNITYID);
			c2BHttpRequest.postHttpResponse(Http.ADDCARAPPLY, params, 1);
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

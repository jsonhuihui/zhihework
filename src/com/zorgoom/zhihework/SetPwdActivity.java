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
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * @ClassName: FeedbackActivity
 * @Description: TODO(密码管理Activity)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年9月14日
 * @修改时间 2015年9月14日
 */
public class SetPwdActivity extends BaseActivity implements OnClickListener, HttpListener {
	private EditText et_old_pwd, et_new_pwd, et_new_pwd2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_pwd_activity_layout);
		initView();
	}

	protected void initView() {
		et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
		et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
		et_new_pwd2 = (EditText) findViewById(R.id.et_new_pwd2);

		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.tv_submit).setOnClickListener(this);
	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_submit:
			String old = et_old_pwd.getText().toString();
			String new1 = et_new_pwd.getText().toString();
			String new2 = et_new_pwd2.getText().toString();
			if (old.equals("") || new1.equals("") || new2.equals("")) {
				ToastUtil.showMessage(this, "提交信息不能为空");
				return;
			}
			if (!new1.equals(new2)) {
				ToastUtil.showMessage(this, "两次输入新密码不一致");
				return;
			}
			if (new2.length() < 6 || new1.length() < 6) {
				ToastUtil.showMessage(this, "新密码长度不能低于6位");
				return;
			}
			RequestParams params = new RequestParams();

			String RID = PrefrenceUtils.getStringUser("userId", this);
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(RID + "", timestamp);

			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);

			params.addBodyParameter("PASSWORD", old);
			params.addBodyParameter("NEWPASSWORD", new2);
			params.addBodyParameter("RID", RID);
			c2BHttpRequest.postHttpResponse(Http.EDITPASSWORD, params, 1);
			break;
		case R.id.regis_back:
			finish();
			break;
		}
	}

	@Override
	public void OnResponse(String result, int connectType) {
		if (null != result) {
			BaseModel guestPass = DataPaser.json2Bean(result, BaseModel.class);
			if (null != guestPass) {
				if ("101".equals(guestPass.getCode())) {
					ToastUtil.showMessage(this, guestPass.getMsg());
					finish();
				} else {
					ToastUtil.showMessage(this, guestPass.getMsg());
				}
			}
		}

	}
}

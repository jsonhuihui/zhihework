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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * @ClassName: FeedbackActivity
 * @Description: TODO(意见反馈Activity)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年9月14日
 * @修改时间 2015年9月14日
 */
public class FeedbackActivity extends BaseActivity implements OnClickListener, HttpListener {
	private EditText et_content, et_contact, et_title;
	private int pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_feedback_activity_layout);
		initView();
	}

	protected void initView() {
		et_content = (EditText) findViewById(R.id.et_content);
		et_contact = (EditText) findViewById(R.id.et_contact);
		et_title = (EditText) findViewById(R.id.et_title);

		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.tv_submit).setOnClickListener(this);

		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
				pos = p;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_submit:
			/// 投诉内容
			String ComplainNote = et_content.getText().toString();
			/// 手机
			String Mobile = et_contact.getText().toString();
			if (ComplainNote.equals("")) {
				ToastUtil.showMessage(this, "内容不能为空");
				return;
			}
			// if (!Mobile.equals("")) {
			// ToastUtil.showMessage(this, "联系方式不能为空");
			// }
			// ToastUtil.showMessage(this, "反馈成功");
			// finish();
			RequestParams params = new RequestParams();

			String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", this);
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(COMMUNITYID + "", timestamp);

			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);

			params.addBodyParameter("MEMO", ComplainNote);
			params.addBodyParameter("TYPE", pos + "");
			params.addBodyParameter("USERID", PrefrenceUtils.getStringUser("userId", this));
			params.addBodyParameter("COMMUNITYID", COMMUNITYID);
			c2BHttpRequest.postHttpResponse(Http.ADDSUGGESTION, params, 1);
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
					finish();
					ToastUtil.showMessage(this, guestPass.getMsg());
				} else {
					ToastUtil.showMessage(this, guestPass.getMsg());
				}
			}
		}
	}

}

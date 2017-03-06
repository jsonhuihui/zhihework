package com.zorgoom.zhihework;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.CommMeth;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.service.MainService;
import com.zorgoom.zhihework.vo.RsHousing;
import com.zorgoom.zhihework.vo.RsHousing.COMPANY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class Login_Activity extends BaseActivity implements View.OnClickListener, HttpListener {

	private TextView login_textView03, login_textView04;//login_textView02, 
	private EditText login_editText1, login_editText2;
	private String UserName = "", Password = "";
	private SharedPreferences sp1;
	private Login_Activity mContext;
	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);
	private String onResponseResult = "";
	String u = "";
	String p = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		setContentView(R.layout.login);
		initView();
		sp1 = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
	}

	private void putlogin() {
		if (onResponseResult != null) {
			rsSUserInfoResultVO = DataPaser.json2Bean(onResponseResult, RsHousing.class);
			if (null != rsSUserInfoResultVO) {
				if (rsSUserInfoResultVO.getCode().equals("101")) {
					PrefrenceUtils.saveUser("state", "1", mContext);
					if (rsSUserInfoResultVO.getData() != null) {// 部门没有验证
						COMPANY infoVO = rsSUserInfoResultVO.getData();
						PrefrenceUtils.saveUser("COMPANY",
								infoVO.getCOMPANYNAME() + infoVO.getDEPTNAME() + infoVO.getDEPTID() + infoVO.getEMPNO(), mContext);
					    PrefrenceUtils.saveUser("ALLOWANCE", infoVO.getALLOWANCE()+"", mContext);
						PrefrenceUtils.saveUser("EMPID", infoVO.getEMPID() + "", mContext);
						PrefrenceUtils.saveUser("EMPNAME", infoVO.getEMPNAME() + "", mContext);
						PrefrenceUtils.saveUser("TEL", infoVO.getTEL() + "", mContext);
						PrefrenceUtils.saveUser("EMPNO", infoVO.getEMPNO() + "", mContext);
						PrefrenceUtils.saveUser("DEPTID", infoVO.getDEPTID() + "", mContext);
						PrefrenceUtils.saveUser("DEPTNAME", infoVO.getDEPTNAME() + "", mContext);
						PrefrenceUtils.saveUser("COMPANYID", infoVO.getCOMPANYID() + "", mContext);
						PrefrenceUtils.saveUser("CREDATE", infoVO.getCREDATE() + "", mContext);
						PrefrenceUtils.saveUser("STATE", infoVO.getSTATE() + "", mContext);
						PrefrenceUtils.saveUser("state", "2", mContext);
						PrefrenceUtils.saveUser("MONEY", infoVO.getMONEY() + "", mContext);
						PrefrenceUtils.saveUser("CALLINFO",
								infoVO.getCOMPANYID() + "" +infoVO.getEMPNO()  , mContext);
						sendBroadcast(new Intent(MainService.CALL));
					}
					PrefrenceUtils.saveUser("MOBILE", UserName, mContext);
					PrefrenceUtils.saveUser("CNAME", login_editText1.getText().toString(), mContext);
					PrefrenceUtils.saveUser("userId", rsSUserInfoResultVO.getRid() + "", mContext);
					sendBroadcast(new Intent("JPush"));
					ToastUtil.showMessage(mContext, rsSUserInfoResultVO.getMsg());
					finish();
				} else {
					ToastUtil.showMessage(mContext, rsSUserInfoResultVO.getMsg());
				}
			}
		}
	}

	private void initView() {
//		login_textView02 = (TextView) findViewById(R.id.login_textView02);
//		login_textView02.setOnClickListener(this);
		login_textView03 = (TextView) findViewById(R.id.login_textView03);
		login_textView03.setOnClickListener(this);
		login_textView04 = (TextView) findViewById(R.id.login_textView04);
		login_textView04.setOnClickListener(this);
		login_editText1 = (EditText) findViewById(R.id.login_editText1);
		login_editText2 = (EditText) findViewById(R.id.login_editText2);
		String loginName = PrefrenceUtils.getStringUser("LOGINUSERNAME", mContext);
		if (null != loginName && !"0".equals(loginName)) {
			login_editText1.setText(loginName);
		}
		findViewById(R.id.regis_back).setOnClickListener(this);

	}

	RsHousing rsSUserInfoResultVO;

	@Override
	public void OnResponse(String result, int reqId) {
		onResponseResult = result;
		if (null != onResponseResult) {
			switch (reqId) {
			// 登录成功返回数据处理
			case 1:
				putlogin();
				break;
			case 2:
				// BaseModel baseModel = DataPaser.json2Bean(onResponseResult,
				// BaseModel.class);
				// if (baseModel.getCode().equals("101")) {
				// ToastUtil.showMessage(mContext, baseModel.getMsg());
				// } else {
				// ToastUtil.showMessage(mContext, baseModel.getMsg());
				// }
				// finish();
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
//		case R.id.login_textView02:// 注册
//			setClass(mContext, MyRegister_Activity.class);
//			break;
		case R.id.login_textView03:
			setClass(mContext, WangJiMiMa_Activity.class);
			break;

		case R.id.login_textView04:
			UserName = login_editText1.getText().toString();
			Password = login_editText2.getText().toString();

			if (UserName.equals("")) {
				ToastUtil.showMessage1(this, "用户名不能为空！", 300);
				// showProgressDialog();
				return;
			}
			if (Password.equals("")) {
				ToastUtil.showMessage1(this, "密码不能为空！", 300);
				// DisplayToast("密码不能为空!");
				return;
			}

			sp1.edit().putString("UserName", UserName).commit();

			if (CommMeth.checkNetworkState(mContext)) {
				showProgressDialog(mContext, "请稍候,正在登录.....");// 显示进度条
				RequestParams params = new RequestParams();

				String timestamp = System.currentTimeMillis() + "";
				String key = c2BHttpRequest.getKey(UserName + "", timestamp);

				params.addBodyParameter("FKEY", key);
				params.addBodyParameter("TIMESTAMP", timestamp);

				params.addBodyParameter("TEL", UserName);
				params.addBodyParameter("PASSWORD", Password);
				c2BHttpRequest.postHttpResponse(Http.LOGIN, params, 1);
			}
			break;
		default:
			break;
		}
	}
}

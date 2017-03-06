package com.zorgoom.zhihework;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.zhihework.vo.IndoorInitVO;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * @ClassName: FeedbackActivity
 * @Description: TODO(我的室内机Activity)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年9月14日
 * @修改时间 2015年9月14日
 */
public class IndoorUnitActivity extends BaseActivity implements OnClickListener, HttpListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indoor_unit_activity_layout);
		initView();
	}

	protected void initView() {

		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.chushihua).setOnClickListener(this);
		findViewById(R.id.chongzhi).setOnClickListener(this);

	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chushihua:
			RequestParams params = new RequestParams();

			String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", this);
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(COMMUNITYID + "", timestamp);

			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);

			params.addBodyParameter("COMMUNITYID", COMMUNITYID);
			params.addBodyParameter("BLOCKID", PrefrenceUtils.getStringUser("BLOCKID", this));
			params.addBodyParameter("UNITID", PrefrenceUtils.getStringUser("UNITID", this));
			params.addBodyParameter("USERID", PrefrenceUtils.getStringUser("userId", this));
			c2BHttpRequest.postHttpResponse(Http.SAVEINDOORUNIT, params, 1);
			break;
		case R.id.chongzhi:
			final EditText inputServer = new EditText(this);
			inputServer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("输入重置的室内机密码").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
					.setNegativeButton("取消", null);
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					String pwd = inputServer.getText().toString();
					if (pwd.length() < 6) {
						ToastUtil.showMessage(IndoorUnitActivity.this, "密码长度不能低于六位");
						return;
					}
					RequestParams params1 = new RequestParams();

					String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", IndoorUnitActivity.this);
					String timestamp = System.currentTimeMillis() + "";
					String key = c2BHttpRequest.getKey(COMMUNITYID + "", timestamp);

					params1.addBodyParameter("FKEY", key);
					params1.addBodyParameter("TIMESTAMP", timestamp);

					params1.addBodyParameter("COMMUNITYID", COMMUNITYID);
					params1.addBodyParameter("BLOCKID",
							PrefrenceUtils.getStringUser("BLOCKID", IndoorUnitActivity.this));
					params1.addBodyParameter("UNITID", PrefrenceUtils.getStringUser("UNITID", IndoorUnitActivity.this));
					params1.addBodyParameter("PASSWORD", pwd);
					c2BHttpRequest.postHttpResponse(Http.UPDATEINDOORUNITPASSWORD, params1, 2);
				}
			});
			builder.show();

			break;
		case R.id.regis_back:
			finish();
			break;
		}
	}

	@Override
	public void OnResponse(String result, int connectType) {
		if (null != result) {
			switch (connectType) {
			case 1:
				IndoorInitVO guestPass = DataPaser.json2Bean(result, IndoorInitVO.class);
				if (null != guestPass) {
					switch (guestPass.getCode()) {
					case "101":
						showDialog("室内机初始化", "用户名：" + guestPass.getData().getLOGINNAME() + "\n" + "密    码："
								+ guestPass.getData().getLOGINNAME());
						break;
					case "303":
						showDialog("室内机初始化", "用户名：" + guestPass.getData().getLOGINNAME());
						ToastUtil.showMessage(this, guestPass.getMsg());
						break;
					default:
						ToastUtil.showMessage(this, guestPass.getMsg());
						break;
					}
				}
				break;
			case 2:
				BaseModel model = DataPaser.json2Bean(result, BaseModel.class);
				if (null != model) {
					switch (model.getCode()) {
					case "101":
						ToastUtil.showMessage(this, model.getMsg());
						break;
					default:
						ToastUtil.showMessage(this, model.getMsg());
						break;
					}
					finish();
				}
				break;
			}

		}
	}

	@SuppressLint("NewApi")
	public void showDialog(String title, String message) {
		// 创建对话框的构造器，可以帮我们构造对话框的模版
		AlertDialog.Builder builder = new Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		// 设置对话框的 标题
		builder.setTitle(title);
		// 设置对话框的提示信息
		builder.setMessage(message);
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		// 通过构造器创建一个对话框对象
		AlertDialog ad = builder.create();
		// 把对话的界面显示出来
		ad.show();
	}
}

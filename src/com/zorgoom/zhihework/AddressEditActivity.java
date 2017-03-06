package com.zorgoom.zhihework;


import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.MyActivityManager;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.popuwindow.CitySelectWindow;
import com.zorgoom.zhihework.view.SwitchButton;
import com.zorgoom.zhihework.view.SwitchButton.OnSwitchStateChangeListener;
import com.zorgoom.zhihework.vo.AddressList;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @ClassName: AddressManageActivity
 * @Description: TODO(收货地址编辑)
 * @创建人 peter
 * @修改人 peter
 * @创建时间 2015年8月18日
 * @修改时间 2015年8月18日
 */
@SuppressLint("InlinedApi")
public class AddressEditActivity extends BaseActivity implements OnClickListener, HttpListener {
	private EditText et_consignee, // 收货人
			et_details_address, // 收货地址
			// et_zip_code, // 邮政编码
			et_contact;// 联系方式
	// private List<Area> areas = null;
	private TextView et_region; // 地区
	private AddressList address;
	private SwitchButton sb_set_default;
	// private boolean IsDefault;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_edit_activity_layout);
		initView();
		initData();
	}

	protected void initView() {
		address = (AddressList) getIntent().getSerializableExtra("address");
		sb_set_default = (SwitchButton) findViewById(R.id.sb_set_default);
		findViewById(R.id.lin_select_contact).setOnClickListener(this);
		findViewById(R.id.rel_region).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		findViewById(R.id.tv_save).setOnClickListener(this);
		et_consignee = (EditText) findViewById(R.id.et_consignee);
		et_contact = (EditText) findViewById(R.id.et_contact);
		et_region = (TextView) findViewById(R.id.et_region);
		et_details_address = (EditText) findViewById(R.id.et_details_address);
		// et_zip_code = (EditText) findViewById(R.id.et_zip_code);
		sb_set_default.setOn(false);
		sb_set_default.setOnSwitchStateChangeListener(new OnSwitchStateChangeListener() {

			@Override
			public void onSwitchStateChange(boolean isOn) {
				if (isOn) {
					ISDEFAULT = "T";
				} else {
					ISDEFAULT = "F";
				}
			}
		});
		if (null != address) {
			et_contact.setText(address.getMOBILE());
			et_consignee.setText(address.getNAME());
			et_details_address.setText(address.getADDRESS());
			et_region.setText(address.getPROVINCE() + " " + address.getCITY() + " " + address.getDISTRICT());
			if (address.getISDEFAULT()) {
				sb_set_default.setOn(true);
			} else {
				sb_set_default.setOn(false);
			}

		}
	}

	private String ISDEFAULT = "F";

	protected void initData() {
		et_details_address.setText(PrefrenceUtils.getStringUser("HOUSING", this));
	}

	private void save() {
		String address1 = et_details_address.getText().toString();
		String contact = et_contact.getText().toString();
		String consignee = et_consignee.getText().toString();
		address1 = address1.replace(" ", "");
		contact = contact.replace(" ", "");
		consignee = consignee.replace(" ", "");
		if (address1.equals("")) {
			ToastUtil.showMessage(this, "详细地址不能为空");
			return;
		}
		if (consignee.equals("")) {
			ToastUtil.showMessage(this, "收货人姓名不能为空");
			return;
		}
		if (contact.equals("")) {
			ToastUtil.showMessage(this, "联系电话不能为空");
			return;
		}

		String operId = PrefrenceUtils.getStringUser("OPERID", this);
		String timestamp = System.currentTimeMillis() + "";

		RequestParams params = new RequestParams();
		params.addBodyParameter("NAME", consignee);
		params.addBodyParameter("MOBILE", contact);
		params.addBodyParameter("PROVINCE", PROVINCE);
		params.addBodyParameter("CITY", CITY);
		params.addBodyParameter("DISTRICT", DISTRICT);
		params.addBodyParameter("ISDEFAULT", ISDEFAULT + "");
		params.addBodyParameter("ADDRESS", address1);
		params.addBodyParameter("OPERID", operId);
		params.addBodyParameter("USERID", PrefrenceUtils.getStringUser("userId", this));
		if (address != null) {// 修改
			String key = c2BHttpRequest.getKey(operId + "", timestamp);
			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);

			params.addBodyParameter("RID", address.getRID() + "");
			c2BHttpRequest.postHttpResponse(Http.UPDATERECEVIEADRR, params, 1);
		} else {// 新增
			String key = c2BHttpRequest.getKey(consignee + "", timestamp);
			params.addBodyParameter("FKEY", key);
			params.addBodyParameter("TIMESTAMP", timestamp);

			c2BHttpRequest.postHttpResponse(Http.ADDRECEVIEADRR, params, 1);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (requestCode) {
		case 1:// 选择联系人
			if (null != intent) {
				Uri uri = intent.getData();
				// 得到ContentResolver对象
				ContentResolver cr = getContentResolver();
				// 取得电话本中开始一项的光标
				Cursor cursor = cr.query(uri, null, null, null, null);
				// 向下移动光标
				String consignee = "";
				String phone1 = "";
				// 向下移动光标
				while (cursor.moveToNext()) {
					// 取得联系人名字
					int nameFieldColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
					consignee = cursor.getString(nameFieldColumnIndex);
					// 取得电话号码
					String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
					Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);

					while (phone.moveToNext()) {
						String PhoneNumber = phone
								.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						phone1 = PhoneNumber;
					}
				}
				cursor.close();
				if (!"".equals(consignee)) {
					et_consignee.setText(consignee);
				} else {
					et_consignee.setText("");
				}
				if (!"".equals(phone1)) {
					et_contact.setText(phone1.replace(" ", ""));
				} else {
					et_contact.setText("");
				}
			}
			break;
		}

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.lin_select_contact:// 选择联系人
			intent = new Intent(Intent.ACTION_PICK, android.provider.ContactsContract.Contacts.CONTENT_URI);
			startActivityForResult(intent, 1);
			break;
		case R.id.rel_region:// 所在地区
			new CitySelectWindow(this, v);
			break;
		case R.id.regis_back:
			finish();
			break;
		case R.id.tv_save:
			save();
			break;
		}
	}

	private String PROVINCE = "", CITY = "", DISTRICT = "";

	public void refreshCity(String PROVINCE, String CITY, String DISTRICT) {
		this.PROVINCE = PROVINCE;
		this.CITY = CITY;
		this.DISTRICT = DISTRICT;
		et_region.setText(PROVINCE + " " + CITY + " " + DISTRICT);

	}

	C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	BaseModel guestPass;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			guestPass = DataPaser.json2Bean(result, BaseModel.class);
			if (null != guestPass) {
				if ("101".equals(guestPass.getCode())) {
					MyActivityManager.getInstance().killActivity(SaleDetailsConfirmOrderActivity.class);

					finish();
					ToastUtil.showMessage(this, guestPass.getMsg());
				} else {
					ToastUtil.showMessage(this, guestPass.getMsg());
				}
			}
		}
	}

	

}

package com.zorgoom.zhihework;

import java.util.Calendar;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.R;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 申请休假
 * 
 * @author Administrator
 *
 */
public class QingJiaAddActivity extends MBaseActivity implements View.OnClickListener, HttpListener {

	private C2BHttpRequest c2BHttpRequest;
	private QingJiaAddActivity mContext;
	private EditText et_chepai, et_remark;
	private TextView et_time;
	RadioGroup radiogroup;
	RadioButton radio1, radio2;
	private Calendar calendar;
	private TextView start_date, start_time, end_date, end_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qingjiaadd);
		mContext = this;
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		// 获取当前的年月日时分信息
		calendar = Calendar.getInstance();
	}

	private void initView() {
		// et_chepai = (EditText) findViewById(R.id.et_number);
		// et_time = (TextView) findViewById(R.id.et_time);
		// et_remark = (EditText) findViewById(R.id.et_remark);
		// findViewById(R.id.tv_sumbut).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radio1 = (RadioButton) findViewById(R.id.radiobutton1);
		radio2 = (RadioButton) findViewById(R.id.radiobutton2);
		start_date = (TextView) findViewById(R.id.start_date);
		start_date.setOnClickListener(this);
		start_time = (TextView) findViewById(R.id.start_time);
		start_time.setOnClickListener(this);
		end_date = (TextView) findViewById(R.id.end_date);
		end_date.setOnClickListener(this);
		end_time = (TextView) findViewById(R.id.end_time);
		end_time.setOnClickListener(this);
		// et_time.setText(PrefrenceUtils.getStringUser("HOUSING", this));

		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == radio1.getId()) {
					ToastUtil.showMessage(QingJiaAddActivity.this, "事假");
				} else {
					ToastUtil.showMessage(QingJiaAddActivity.this, "病假");
				}
			}
		});
	}

	private void showDateDialog() {
		DatePickerDialog date_dialog = new DatePickerDialog(QingJiaAddActivity.this, new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				if ((monthOfYear + 1) > 10 && dayOfMonth >= 10){
					start_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
				}else if ((monthOfYear + 1) > 10 && dayOfMonth < 10) {
					start_date.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
				}else if ((monthOfYear + 1) <= 10 && dayOfMonth < 10) {
					start_date.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
				}else if ((monthOfYear + 1) <= 10 && dayOfMonth >= 10) {
					start_date.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
				}
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

		date_dialog.show();
	}

	private void showDateDialog2() {
		DatePickerDialog date_dialog = new DatePickerDialog(QingJiaAddActivity.this, new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				if ((monthOfYear + 1) > 10 && dayOfMonth >= 10){
					end_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
				}else if ((monthOfYear + 1) > 10 && dayOfMonth < 10) {
					end_date.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
				}else if ((monthOfYear + 1) <= 10 && dayOfMonth < 10) {
					end_date.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
				}else if ((monthOfYear + 1) <= 10 && dayOfMonth >= 10) {
					end_date.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
				}
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

		date_dialog.show();
	}

	private void showTimeDialog() {
		TimePickerDialog time_dialog = new TimePickerDialog(QingJiaAddActivity.this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if (hourOfDay < 10 && minute >= 10) {
					start_time.setText("0" + hourOfDay + ":" + minute);
				} else if (hourOfDay >= 10 && minute < 10) {
					start_time.setText(hourOfDay + ":0" + minute);
				} else if (hourOfDay < 10 && minute < 10) {
					start_time.setText("0" + hourOfDay + ":0" + minute);
				} else {
					start_time.setText(hourOfDay + ":" + minute);
				}
			}
		}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
		time_dialog.show();
	}

	private void showTimeDialog2() {
		TimePickerDialog time_dialog = new TimePickerDialog(QingJiaAddActivity.this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if (hourOfDay < 10 && minute >= 10) {
					end_time.setText("0" + hourOfDay + ":" + minute);
				} else if (hourOfDay >= 10 && minute < 10) {
					end_time.setText(hourOfDay + ":0" + minute);
				} else if (hourOfDay < 10 && minute < 10) {
					end_time.setText("0" + hourOfDay + ":0" + minute);
				} else {
					end_time.setText(hourOfDay + ":" + minute);
				}

			}
		}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
		time_dialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_sumbut:// 提交
			ToastUtil.showMessage(mContext, "不准请假！");
			// String number = et_chepai.getText().toString();
			// String remark = et_remark.getText().toString();
			// if (number.equals("")) {
			// ToastUtil.showMessage(mContext, "请输入完整信息");
			// return;
			// }
			// RequestParams params = new RequestParams();
			//
			// String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID",
			// this);
			// String timestamp = System.currentTimeMillis() + "";
			// String key = c2BHttpRequest.getKey(COMMUNITYID + "", timestamp);
			//
			// params.addBodyParameter("FKEY", key);
			// params.addBodyParameter("TIMESTAMP", timestamp);
			//
			// params.addBodyParameter("CARNO", number);
			// params.addBodyParameter("MEMO", remark);
			// params.addBodyParameter("UNITID",
			// PrefrenceUtils.getStringUser("UNITID", mContext));
			// params.addBodyParameter("USERID",
			// PrefrenceUtils.getStringUser("userId", mContext));
			// params.addBodyParameter("COMMUNITYID", COMMUNITYID);
			// c2BHttpRequest.postHttpResponse(Http.ADDCARAPPLY, params, 1);
			break;
		case R.id.regis_back:// 返回
			finish();
			break;
		case R.id.start_date:// 开始日期
			showDateDialog();
			break;
		case R.id.start_time:// 开始时间
			showTimeDialog();
			break;
		case R.id.end_date:// 结束日期
			showDateDialog2();
			break;
		case R.id.end_time:// 结束时间
			showTimeDialog2();
			break;

		}
	}

	// BaseModel guestPass;

	@Override
	public void OnResponse(String result, int reqId) {
		// if (null != result) {
		// guestPass = DataPaser.json2Bean(result, BaseModel.class);
		// if (null != guestPass) {
		// if ("101".equals(guestPass.getCode())) {
		// finish();
		// ToastUtil.showMessage(mContext, guestPass.getMsg());
		// } else {
		// ToastUtil.showMessage(mContext, guestPass.getMsg());
		// }
		// }
		// }
	}

}

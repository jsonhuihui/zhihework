package com.zorgoom.zhihework;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.management.MBeanServerInvocationHandler;

import com.google.gson.Gson;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsSUserVO;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

public class JangChen_Activity extends BaseActivity implements View.OnClickListener, HttpListener {

	private JangChen_Activity mContext;
	private TextView time, time2, date, date2;
	private static final int msgKey1 = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.jiangchen_layout);
//		new TimThread().start();
		initView();
	}

	private void initView() {
		findViewById(R.id.regis_back).setOnClickListener(this);
//		findViewById(R.id.bt1).setOnClickListener(this);
//		findViewById(R.id.bt2).setOnClickListener(this);
//		findViewById(R.id.bt3).setOnClickListener(this);
//		findViewById(R.id.bt4).setOnClickListener(this);
//		findViewById(R.id.bt5).setOnClickListener(this);
//		findViewById(R.id.bt6).setOnClickListener(this);
//		findViewById(R.id.ib).setOnClickListener(this);
//		findViewById(R.id.ib2).setOnClickListener(this);
//		time = (TextView) findViewById(R.id.time);
//		time2 = (TextView) findViewById(R.id.time2);
//		date = (TextView) findViewById(R.id.date);
//		date2 = (TextView) findViewById(R.id.date2);
//		time.setOnClickListener(this);
//		time2.setOnClickListener(this);
//		date.setOnClickListener(this);
//		date2.setOnClickListener(this);

	}

	@SuppressLint("NewApi")
	@Override
	protected void onDestroy() {
		super.onDestroy();

	};

	RsSUserVO rsSUserInfoResultVO;
	Gson gson = new Gson();

	@Override
	public void OnResponse(String result, int reqId) {
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
//		case R.id.bt1:// 请假条
//			ToastUtil.showMessage(mContext, "程序员玩命赶制中!");
//			break;
//		case R.id.bt2:// 二维码
//			ToastUtil.showMessage(mContext, "程序员玩命赶制中!");
//			break;
//		case R.id.bt3:// 打卡时间查询
//			setClass(mContext, DaKaJiLuActivity.class);
//			break;
//		case R.id.bt4:// 出勤统计
//			ToastUtil.showMessage(mContext, "程序员玩命赶制中!");
//			break;
//		case R.id.bt5:// 奖惩信息
//			ToastUtil.showMessage(mContext, "程序员玩命赶制中!");
//			break;
//		case R.id.bt6:// 反馈
//			setClass(mContext, FeedbackList.class);
//			break;
//		case R.id.ib:// 反馈
//			ToastUtil.showMessage(mContext, "打卡成功！");
//			break;
//		case R.id.ib2:// 反馈
//			ToastUtil.showMessage(mContext, "打卡成功！");
//			break;
//
		}
	}

//	public class TimThread extends Thread {
//		public void run() {
//			do {
//				try {
//					Thread.sleep(1000);
//					Message msg = new Message();
//					msg.what = msgKey1;
//					mHandler.sendMessage(msg);
//
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//
//			} while (true);
//		}
//
//		private Handler mHandler = new Handler() {
//			public void handleMessage(Message msg) {
//				super.handleMessage(msg);
//				switch (msg.what) {
//				case msgKey1:
//					long sysTime = System.currentTimeMillis();
//					Date mdate = new Date(sysTime);
//					SimpleDateFormat mDate = new SimpleDateFormat("yyyy年MM月dd日");
//					SimpleDateFormat mTime = new SimpleDateFormat("hh时mm分ss秒");
//					time.setText(mTime.format(mdate));
//					time2.setText(mTime.format(mdate));
//					date.setText(mDate.format(mdate));
//					date2.setText(mDate.format(mdate));
//					break;
//
//				default:
//					break;
//				}
//			}
//		};
//
//	}
}

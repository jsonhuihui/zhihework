package com.zorgoom.zhihework;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.zhihework.adapter.ContactPropertyAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsContact;
import com.zorgoom.zhihework.vo.RsContact.Contact;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 联系物业
 * 
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class ContactPropertyList extends MBaseActivity implements View.OnClickListener, HttpListener {

	private ListView message_listView1;
	private String onResponseResult;
	private C2BHttpRequest c2BHttpRequest;
	private ContactPropertyList mContext;
	private SwipeRefreshLayout main_srl_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contact_property_laout);
		mContext = this;
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		initData();
	}

	private void initData() {
		String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", mContext);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(COMMUNITYID, timestamp);

		c2BHttpRequest.getHttpResponse(
				Http.GETCONTACT + "COMMUNITYID=" + COMMUNITYID + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	private void initView() {
		message_listView1 = (ListView) findViewById(R.id.message_listView1);
		findViewById(R.id.regis_back).setOnClickListener(this);
		message_listView1.setOnItemClickListener(new myOnitemClick());

		main_srl_view = (SwipeRefreshLayout) findViewById(R.id.main_srl_view);
		// 下拉刷新
		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
					@Override
					public void run() {
						if (!isDestroyed()) {
							main_srl_view.setRefreshing(false); // 是否显示刷新进度;false:不显示
							initData();
						}
					}
				}, 2000);
			}
		}); // 设置刷新监听
		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色
	}

	public class myOnitemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
			showDialog("拨打电话",
					paymentDetail.getData().get(position).getDEPARTMENT() + ":"
							+ paymentDetail.getData().get(position).getTEL(),
					paymentDetail.getData().get(position).getTEL());
		}

	}

	public void showDialog(String title, String message, final String string) {
		// 创建对话框的构造器，可以帮我们构造对话框的模版
		AlertDialog.Builder builder = new Builder(this, AlertDialog.THEME_HOLO_LIGHT);
		// 设置对话框的 标题
		builder.setTitle(title);
		// 设置对话框的提示信息
		builder.setMessage(message);
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + string));
				startActivity(intent);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:// 返回
			finish();
			break;

		}
	}

	RsContact paymentDetail;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			paymentDetail = DataPaser.json2Bean(result, RsContact.class);
			if (null != paymentDetail) {
				if ("101".equals(paymentDetail.getCode())) {
					if (paymentDetail.getData().size() == 0) {
						ToastUtil.showMessage1(this, "当前没有物业联系方式！", 300);
						return;
					} else {
						List<Contact> list = new ArrayList<Contact>();
						message_listView1.setAdapter(new ContactPropertyAdapter(mContext, paymentDetail.getData()));
					}
				}
			}
		}

	}

}

package com.zorgoom.zhihework;

import com.google.gson.Gson;
import com.zorgoom.zhihework.adapter.OpenDoorRecordAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

/**
 *环境指数
 * 
 * @author Administrator
 *
 */
public class HuangJing_index_Activity extends MBaseActivity implements View.OnClickListener, HttpListener {

//	private SwipeRefreshLayout main_srl_view;
	private HuangJing_index_Activity mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.huanjing_index_layout);
		initView();
	}


	private void initView() {
//		main_srl_view = (SwipeRefreshLayout) findViewById(R.id.main_srl_view);
//		// 下拉刷新
//		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
//			@Override
//			public void onRefresh() {
//				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
//					@SuppressLint("NewApi")
//					@Override
//					public void run() {
//						if (!isDestroyed()) {
//							main_srl_view.setRefreshing(false); // 是否显示刷新进度;false:不显示
//						}
//					}
//				}, 2000);
//			}
//		}); // 设置刷新监听
//		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
//		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色

		findViewById(R.id.regis_back).setOnClickListener(this);
	}

	

		
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;
		}
	}


	@Override
	public void OnResponse(String result, int reqId) {
		
	}


}

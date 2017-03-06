package com.zorgoom.zhihework;

import com.zorgoom.zhihework.adapter.ImageLoadUtils;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.view.CircleImageView;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.zhihework.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Me_Activity extends Fragment implements View.OnClickListener {

	private Context mContext;
	private CircleImageView me_image;
	private Object[] imageLoadObj;
	private TextView textView12;
	private SwipeRefreshLayout main_srl_view;

	private void inResume() {
		if (null != mContext) {
			int state = Integer.parseInt(PrefrenceUtils.getStringUser("state", mContext));
			switch (state) {
			case 0:
				textView12.setTag(0);
				textView12.setText("登录/注册");
				break;
//			case 1:
//				textView12.setTag(1);
//				textView12.setText("您尚未认证（点击认证房屋）");
//				break;
			case 2:
				textView12.setTag(2);
				textView12.setText(PrefrenceUtils.getStringUser("COMPANY", mContext) + "");
				break;
			}
			String url = PrefrenceUtils.getStringUser("photo", mContext);
			if (!url.equals("0")) {
				ImageLoadUtils.loadImage(imageLoadObj, Http.SERVLET_URL + url, me_image);
			}
		}
	}

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mContext = getContext();
			// c2BHttpRequest = new C2BHttpRequest(getActivity(), this);
			// shopsDao = new ShopsDao(mContext);
			mView = inflater.inflate(R.layout.me_layout, container, false);
			imageLoadObj = ImageLoadUtils.initImageLoad(mContext);
			initView();
			inResume();
			return mView;
		}
		return mView;

	}

	public void refresh() {
		inResume();
	}

	private void initView() {
		main_srl_view = (SwipeRefreshLayout) mView.findViewById(R.id.main_srl_view);
		// 下拉刷新
		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
					@SuppressLint("NewApi")
					@Override
					public void run() {
						if (!getActivity().isDestroyed()) {
							main_srl_view.setRefreshing(false); // 是否显示刷新进度;false:不显示
							// initData();
						}
					}
				}, 2000);
			}
		}); // 设置刷新监听
		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色
		textView12 = (TextView) mView.findViewById(R.id.textView12);
		textView12.setOnClickListener(this);
		me_image = (CircleImageView) mView.findViewById(R.id.me_image);
		me_image.setOnClickListener(this);
		LinearLayout layout = (LinearLayout) mView.findViewById(R.id.layout);

		for (int i = 0; i < layout.getChildCount(); i++) {
			layout.getChildAt(i).setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView12:
			int tag = (int) v.getTag();
			switch (tag) {
			case 0:
				openActivity(Login_Activity.class);
				break;
//			case 1:
//				openActivity(HousingAuthorityActivity.class);
//				break;
//			case 2:
//				openActivity(HousingList.class);
//				break;
			}
			break;
		case R.id.me_image:
			checkLogin();
			openActivity(MemberInfoActivity.class);
			break;
//		case R.id.my_hous:// 我的房屋
//			checkLogin();
//			openActivity(HousingList.class);
//			break;
//		case R.id.rel_layout2:// 我的房屋租赁
//			checkLogin();
//			openActivity(MyHouseLeaseList.class);
//			break;
		case R.id.rel_layout10:// 密码管理
//			checkLogin();
			String state = PrefrenceUtils.getStringUser("state", mContext);
			if (state.equals("0")) {
				ToastUtil.showMessage(mContext, "请先登录");
				startActivity(new Intent(mContext, Login_Activity.class));
				return;
			}
//			if (state.equals("1")) {
//				ToastUtil.showMessage(mContext, "请先验证房屋");
//				startActivity(new Intent(mContext, HousingList.class));
//				return;
//			}
			openActivity(SetPwdActivity.class);
			break;
		case R.id.rel_layout8:// 开门记录
//			checkHousing();
			String state2 = PrefrenceUtils.getStringUser("state", mContext);
			if (state2.equals("0")) {
				ToastUtil.showMessage(mContext, "请先登录");
				startActivity(new Intent(mContext, Login_Activity.class));
				return;
			}
//			if (state2.equals("1")) {
//				ToastUtil.showMessage(mContext, "请先验证房屋");
//				startActivity(new Intent(mContext, HousingList.class));
//				return;
//			}
			openActivity(OpenDoorRecordActivity.class);
			break;
		case R.id.rel_layout4:// 我的菜谱
//			checkHousing();
			String state3 = PrefrenceUtils.getStringUser("state", mContext);
			if (state3.equals("0")) {
				ToastUtil.showMessage(mContext, "请先登录");
				startActivity(new Intent(mContext, Login_Activity.class));
				return;
			}
//			if (state3.equals("1")) {
//				ToastUtil.showMessage(mContext, "请先验证房屋");
//				startActivity(new Intent(mContext, HousingList.class));
//				return;
//			}
			openActivity(OrderActivity.class);//我的订单
//			openActivity(SaleDetailsActivity.class);//食堂菜谱
			break;
//		case R.id.rel_layout5:// 收货地址
//			checkHousing();
//			openActivity(AddressManageActivity1.class);
//			break;
//		case R.id.rel_layout11:// 室内机
//			checkHousing();
//			openActivity(IndoorUnitActivity.class);
//			break;
		case R.id.rel_layout1:// 关于我们
			openActivity(SynopsisActivity.class);
			break;
		case R.id.rel_layout3:// 设置
			openActivity(SetActivity.class);
			break;
		}
	}

	private void checkLogin() {
		String state = PrefrenceUtils.getStringUser("state", mContext);
		if (state.equals("0")) {
			ToastUtil.showMessage(mContext, "请先登录");
			openActivity(Login_Activity.class);
			return;
		}
	}

	private void checkHousing() {
		String state = PrefrenceUtils.getStringUser("state", mContext);
		if (state.equals("0")) {
			ToastUtil.showMessage(mContext, "请先登录");
			openActivity(Login_Activity.class);
			return;
		}
//		if (state.equals("1")) {
//			ToastUtil.showMessage(mContext, "请先验证房屋");
//			openActivity(HousingList.class);
//			return;
//		}
	}

	public void openActivity(Class<?> clazz) {
		Intent intent = new Intent(mContext, clazz);
		startActivity(intent);
	}

}

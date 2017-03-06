package com.zorgoom.zhihework;

/**
 *首页
 */

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.zorgoom.zhihework.adapter.ImageLoadUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.ImageCycleView;
import com.zorgoom.zhihework.base.ImageCycleView.ImageCycleViewListener;
import com.zorgoom.zhihework.base.db.dao.MsgDao;
import com.zorgoom.zhihework.base.db.entity.MsgPo;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.popuwindow.Main_AdPopuWindow;
import com.zorgoom.zhihework.popuwindow.Main_UnLockPopuWindow;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.zhihework.vo.Msg;
import com.zorgoom.zhihework.vo.ReAdVO;
import com.zorgoom.zhihework.vo.ReAdVO.AdVO;
import com.zorgoom.zhihework.vo.RsMsg;
import com.zorgoom.util.PrefrenceUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

//import com.zorgoom.wuye.WuyeMeOnClick;

@SuppressLint("NewApi")
public class MainActivity extends Fragment implements HttpListener, View.OnClickListener {

	private ImageCycleView main_mAdView;
	public int stype = 1;
	private TextView main_textView01;// 判断登录提示
	private ImageView imageView1;
	private Context mContext;
	private C2BHttpRequest c2BHttpRequest = null;
	private Gson gson = new Gson();
	private ArrayList<String> mImageUrl = new ArrayList<String>();
	private Object[] imageLoadObj;
	// private UpMarqueeTextView tv_kuaibao;
	private List<String> list = new ArrayList<String>();
//	private SwipeRefreshLayout main_srl_view;
	private View mView;
	private MsgDao msgDao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/** 防止重复加载UI **/
		mContext = getActivity();
		imageLoadObj = ImageLoadUtils.initImageLoad(mContext);
		c2BHttpRequest = new C2BHttpRequest(getActivity(), this);
		if (mView == null) {
			msgDao = new MsgDao(mContext);
			mView = inflater.inflate(R.layout.main_activity, container, false);
			initView();
			inResume();
			return mView;
		}
		return mView;
	}

	public void refresh() {
		inResume();
	}

	private void inResume() {
		if (null != mContext) {
			int state = Integer.parseInt(PrefrenceUtils.getStringUser("state", mContext));
			switch (state) {
			case 0:
				main_textView01.setTag(0);
				main_textView01.setText("登录/注册");
				imageView1.setVisibility(View.GONE);
				break;
//			case 1:
//				main_textView01.setTag(1);
//				main_textView01.setText("认证房屋");
//				imageView1.setVisibility(View.GONE);
//				break;
			case 2:
				main_textView01.setTag(2);
				main_textView01.setText(PrefrenceUtils.getStringUser("COMPANY", mContext));
				imageView1.setVisibility(View.VISIBLE);
				break;
			}
			c2BHttpRequest.setShow(false);
			initDate();
		}
	}

	private void initDate() {
		String OPERID = PrefrenceUtils.getStringUser("OPERID", mContext);
		int index = 0;
		if (!OPERID.equals("0")) {
			index = 1;
			// 获取广告
			String timestamp = System.currentTimeMillis() + "";
			String key = c2BHttpRequest.getKey(OPERID + "", timestamp);

			c2BHttpRequest.getHttpResponse(Http.GETADBYPOSITION + "OPERID=" + OPERID + "&COVERS=A" + "&FKEY=" + key
					+ "&TIMESTAMP=" + timestamp, 4);
		}
		if (index == 0) {
			if (PrefrenceUtils.getStringUser("JIGUANG", mContext).equals("0")) {
				// 注册极光
				String userId = PrefrenceUtils.getStringUser("userId", mContext);
				if (!userId.equals("0")) {
					registerJPush(userId);
				}
			}
			

		}
	}

	private void registerJPush(String userId) {
		c2BHttpRequest.setShow(false);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId, timestamp);

		c2BHttpRequest.getHttpResponse(Http.REGISTERJPUSH + "ALIAS=" + userId + "&PLATFORM=0&TYPE=2" + "&FKEY=" + key
				+ "&TIMESTAMP=" + timestamp, 3);
	}

	@SuppressLint("NewApi")
	private void initView() {
		// function_grid = (NoScrollGridView)
		// mView.findViewById(R.id.function_grid);
//		main_srl_view = (SwipeRefreshLayout) mView.findViewById(R.id.main_srl_view);
//		// 下拉刷新
//		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
//
//			@Override
//			public void onRefresh() {
//				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
//					@Override
//					public void run() {
//						if (!getActivity().isDestroyed()) {
//							main_srl_view.setRefreshing(false); // 是否显示刷新进度;false:不显示
//							// c2BHttpRequest.setShow(true);
//							initDate();
//						}
//					}
//				}, 2000);
//			}
//		}); // 设置刷新监听
//		main_srl_view.setColorSchemeResources(R.color.black, R.color.black, R.color.black); // 进度动画颜色
//		main_srl_view.setProgressBackgroundColorSchemeResource(R.color.white); // 进度背景颜色
//

		imageView1 = (ImageView) mView.findViewById(R.id.imageView1);

		main_textView01 = (TextView) mView.findViewById(R.id.main1);
		main_textView01.setOnClickListener(this);
		// main_textView01.setText("登录/注册");

		mView.findViewById(R.id.bt1).setOnClickListener(this);
		mView.findViewById(R.id.id1).setOnClickListener(this);
		mView.findViewById(R.id.id2).setOnClickListener(this);
		mView.findViewById(R.id.id3).setOnClickListener(this);
		mView.findViewById(R.id.id4).setOnClickListener(this);

		mView.findViewById(R.id.iv1).setOnClickListener(this);
		mView.findViewById(R.id.iv2).setOnClickListener(this);
		mView.findViewById(R.id.iv3).setOnClickListener(this);
		mView.findViewById(R.id.iv4).setOnClickListener(this);

		main_mAdView = (ImageCycleView) mView.findViewById(R.id.main_imageView03);
		Bitmap bit = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/temp.jpg"); // 自定义//路径

		mImageUrl = new ArrayList<String>();

		for (MsgPo msgPo : msgDao.queryAll()) {
			list.add(msgPo.getTitle());
		}

		// list.add("明天下暴雨，请大家注意防范");
		// list.add("403水管漏水，请楼下住户注意");
		// list.add("本周六物业进行蚊虫清理，请关好门窗");
		// handler1.sendEmptyMessage(1);

		// function_grid.setAdapter(new FunctionAdapter(mContext));
		// function_grid.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// if (position == 7) {// 我的房屋
		// String state = PrefrenceUtils.getStringUser("state", mContext);
		// if (state.equals("0")) {
		// ToastUtil.showMessage(mContext, "请先登录");
		// openActivity(Login_Activity.class);
		// return;
		// }
		// openActivity(HousingList.class);
		// }
		//
		// String state = PrefrenceUtils.getStringUser("state", mContext);
		// if (state.equals("0")) {
		// ToastUtil.showMessage(mContext, "请先登录");
		// openActivity(Login_Activity.class);
		// return;
		// }
		// if (state.equals("1")) {
		// ToastUtil.showMessage(mContext, "请先验证房屋");
		// openActivity(HousingList.class);
		// return;
		// }
		// switch (position) {
		// case 0:// 一键开门
		// WindowManager windowManager = getActivity().getWindowManager();
		// Display display = windowManager.getDefaultDisplay();
		// int screenHeight = screenHeight = display.getHeight();
		// int hei = screenHeight / 5 * 3;
		// int hei1 = screenHeight / 5 * 2;
		// Main_AdPopuWindow adPopuWindow = new Main_AdPopuWindow(getActivity(),
		// (ScrollView) mView.findViewById(R.id.scrollView_showMessages01),
		// hei);
		// new Main_UnLockPopuWindow(getActivity(),
		// (ScrollView) mView.findViewById(R.id.scrollView_showMessages01),
		// hei1, adPopuWindow);
		// break;
		// case 1:// 物业账单
		// openActivity(BillList.class);
		// break;
		// case 2:// 访客通行
		// openActivity(GuestPassActivity.class);
		// break;
		// case 3:// 维修申报
		// openActivity(MaintainList.class);
		// break;
		// case 4:// 户户通
		// openActivity(HuhutongActivity.class);
		// break;
		// case 5:// 联系物业
		// openActivity(ContactPropertyList.class);
		// break;
		// case 6:// 通知公告
		// openActivity(NoticeList.class);
		// break;
		// case 8:// wifi
		// ToastUtil.showMessage(mContext, "该小区未开通...");
		// break;
		// case 9:// 房屋租赁
		// openActivity(HouseLeaseList.class);
		// break;
		// case 10:// 投诉建议
		// openActivity(FeedbackList.class);
		// break;
		// case 11:// 车位申请
		// openActivity(StallApplyActivity.class);
		// break;
		// }
		// }
		// });
	}

	private int index = 0;
	Handler handler1 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String text = "";
			if (index < list.size()) {
				text = list.get(index);
			}
			index++;
			if (index > list.size() - 1) {
				index = 0;
			}
			// tv_kuaibao.setText(text);
			handler1.sendEmptyMessageDelayed(1, 4000);
		}
	};

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
		@Override
		public void onImageClick(int position, View imageView) {

		}
	};

	// public void sendSale(int SHOPID, String IMG, String NAME, String REMARK)
	// {
	// Intent intent = new Intent();
	// intent.setClass(mContext, SaleDetailsActivity.class);
	// intent.putExtra("SHOPID", SHOPID);
	// intent.putExtra("IMG", IMG);
	// intent.putExtra("NAME", NAME);
	// intent.putExtra("REMARK", REMARK);
	// startActivity(intent);
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id1:
		case R.id.iv1:// 我的考勤
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
			openActivity(Kaoqin_Activity.class);
			break;
		case R.id.id2:// 消费
		case R.id.iv2:
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
			openActivity(Consumption_Activity.class);
//			ToastUtil.showMessage(mContext, "程序员玩命赶制中!");
			break;
		/*
		 * case R.id.id2:// 我的设备 case R.id.iv2: openActivity(SmartHome.class);
		 * break;
		 */
		case R.id.id3:
		case R.id.iv3:// wifi
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
			//openActivity(Sale_Activity.class);
			startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
			break;
		case R.id.id4:
		case R.id.iv4:// 我的设备
			String state4 = PrefrenceUtils.getStringUser("state", mContext);
			if (state4.equals("0")) {
				ToastUtil.showMessage(mContext, "请先登录");
				startActivity(new Intent(mContext, Login_Activity.class));
				return;
			}
//			if (state4.equals("1")) {
//				ToastUtil.showMessage(mContext, "请先验证房屋");
//				startActivity(new Intent(mContext, HousingList.class));
//				return;
//			}
			openActivity(Equipment_Activity.class);
			break;
		case R.id.bt1:// 钥匙
			String state5 = PrefrenceUtils.getStringUser("state", mContext);
			if (state5.equals("0")) {
				ToastUtil.showMessage(mContext, "请先登录");
				startActivity(new Intent(mContext, Login_Activity.class));
				return;
			}
//			if (state5.equals("1")) {
//				ToastUtil.showMessage(mContext, "请先验证房屋");
//				startActivity(new Intent(mContext, HousingList.class));
//				return;
//			}
			WindowManager windowManager = getActivity().getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			int screenHeight = screenHeight = display.getHeight();
			int hei = screenHeight / 5 * 3;
			int hei1 = screenHeight / 5 * 2;
			Main_AdPopuWindow adPopuWindow = new Main_AdPopuWindow(getActivity(), v, hei);
			new Main_UnLockPopuWindow(getActivity(), v, hei1, adPopuWindow);
			break;
		// case R.id.main_linearLayout09:
		// if (null != home) {
		// if (home.getData().size() > 0) {
		// PriorityVo priorityVo = home.getData().get(0);
		// sendSale(priorityVo.getRID(), priorityVo.getSHOPIMAGE(),
		// priorityVo.getSHOPNAME(),
		// priorityVo.getREMARK());
		// }
		// }
		// break;
		// case R.id.main_linearLayout10:
		// if (null != home) {
		// if (home.getData().size() > 1) {
		// PriorityVo priorityVo = home.getData().get(1);
		// sendSale(priorityVo.getRID(), priorityVo.getSHOPIMAGE(),
		// priorityVo.getSHOPNAME(),
		// priorityVo.getREMARK());
		// }
		// }
		// break;
		// case R.id.main_linearLayout11:
		// if (null != home) {
		// if (home.getData().size() > 2) {
		// PriorityVo priorityVo = home.getData().get(2);
		// sendSale(priorityVo.getRID(), priorityVo.getSHOPIMAGE(),
		// priorityVo.getSHOPNAME(),
		// priorityVo.getREMARK());
		// }
		// }
		// break;
		case R.id.main1:// 判断是否登录提示
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
		// case R.id.main_imageView01:
		// Intent intent = new Intent(mContext,
		// MaintainListAdd.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intent);
		// break;
		// case R.id.tv_kuaibao:
		// case R.id.tv_all_news:
		// case R.id.main_imageView02:
		// String state = PrefrenceUtils.getStringUser("state", mContext);
		// if (state.equals("0")) {
		// ToastUtil.showMessage(mContext, "请先登录");
		// openActivity(Login_Activity.class);
		// return;
		// }
		// if (state.equals("1")) {
		// ToastUtil.showMessage(mContext, "请先验证房屋");
		// openActivity(HousingList.class);
		// return;
		// }
		// if (v.getId() == R.id.main_imageView02) {
		// openActivity(HuhutongActivity.class);
		// } else {
		// openActivity(NoticeList.class);
		// }
		// break;
		}
	}

	// private HomePriorityVo home;

	@SuppressLint("NewApi")
	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			switch (reqId) {
			case 2:// 通知
				RsMsg rsMsg = gson.fromJson(result, RsMsg.class);
				if ("101".equals(rsMsg.getCode())) {
					if (rsMsg.getData().size() == 0) {
						return;
					}
					msgDao.deleteAll();
					for (Msg msg : rsMsg.getData()) {
						msgDao.insert(msg);
						list.add(msg.getNOTICETITLE());
					}
					handler1.sendEmptyMessage(1);
				}
				break;
			case 3:// 注册极光
				BaseModel baseModel = gson.fromJson(result, BaseModel.class);
				if (baseModel.getCode().equals("101")) {
					PrefrenceUtils.saveUser("JIGUANG", 1 + "", mContext);
					// ToastUtil.showMessage(mContext, baseModel.getMsg());
				} else {
					// ToastUtil.showMessage(mContext, baseModel.getMsg());
				}

				String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", mContext);
				String timestamp = System.currentTimeMillis() + "";
				String key = c2BHttpRequest.getKey(COMMUNITYID, timestamp);

				c2BHttpRequest.getHttpResponse(
						Http.GETNOTICE + "COMMUNITYID=" + COMMUNITYID + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 2);
				break;
			case 4:// 获取广告
				mImageUrl.clear();
				BaseModel baseModel1 = gson.fromJson(result, BaseModel.class);
				if (baseModel1.getCode().equals("101")) {
					ReAdVO adVO = gson.fromJson(result, ReAdVO.class);
					// mImageUrl.clear();
					for (AdVO vo : adVO.getData()) {
						mImageUrl.add(Http.SERVLET_URL + vo.getPICURL());
					}
					if (mImageUrl.size() > 0) {// 判断当前g
						main_mAdView.setIndex(0);
						main_mAdView.setBackground(null);
						main_mAdView.setImageResources(mImageUrl, mAdCycleViewListener, stype);
						// setIndex 解决首页图片删除完毕 mAdList.size() 为零 代码报错
					} else {
						main_mAdView.setIndex(1);
					}
				} else {
					// 如果服务器没有图片，那么关闭定时器，设置本地默认图片
					main_mAdView.setIndex(1);

				}
				if (PrefrenceUtils.getStringUser("JIGUANG", mContext).equals("0")) {
					// 注册极光
					String userId = PrefrenceUtils.getStringUser("userId", mContext);
					if (!userId.equals("0")) {
						registerJPush(userId);
					}
				} else {
					String COMMUNITYID11 = PrefrenceUtils.getStringUser("COMMUNITYID", mContext);
					String timestamp11 = System.currentTimeMillis() + "";
					String key11 = c2BHttpRequest.getKey(COMMUNITYID11, timestamp11);

					// c2BHttpRequest.getHttpResponse(Http.PRIORITYSHOPLIST +
					// "COMMUNITYID=" + COMMUNITYID11 + "&FKEY="
					// + key11 + "&TIMESTAMP=" + timestamp11, 1);
					c2BHttpRequest.getHttpResponse(Http.GETNOTICE + "COMMUNITYID=" + COMMUNITYID11 + "&FKEY=" + key11
							+ "&TIMESTAMP=" + timestamp11, 2);
				}
				break;
			}
		}
	}

	public void openActivity(Class<?> clazz) {
		openActivity(clazz, null);
	}

	public void openActivity(Class<?> clazz, Bundle pBundle) {
		Intent intent = new Intent(mContext, clazz);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

}

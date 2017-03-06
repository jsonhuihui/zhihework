package com.zorgoom.zhihework;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.zhihework.adapter.Sale_List_Adapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.base.ImageCycleView;
import com.zorgoom.zhihework.base.ImageCycleView.ImageCycleViewListener;
import com.zorgoom.zhihework.base.db.dao.ShopsDao;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.zhihework.vo.ReAdVO;
import com.zorgoom.zhihework.vo.ReAdVO.AdVO;
import com.zorgoom.zhihework.vo.SaleListVo;
import com.zorgoom.zhihework.vo.SaleListVo.SaleVo;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 社区商圈
 * 
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class Sale_Activity extends Fragment implements View.OnClickListener, HttpListener {

	private ScrollView sv_sale;
	public int stype = 1;
	private ListView sale_listView1;
	private C2BHttpRequest c2BHttpRequest;
	private ImageCycleView main_mAdView;
	private ShopsDao shopsDao;
	private SwipeRefreshLayout main_srl_view;
	private Context mContext;
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mContext = getContext();
			c2BHttpRequest = new C2BHttpRequest(getActivity(), this);
			shopsDao = new ShopsDao(mContext);

			mView = inflater.inflate(R.layout.sale_layout, container, false);
			initView();
			inResume();
			return mView;
		}
		return mView;
	}

	public void refresh() {
		if (null != sv_sale) {
			sv_sale.smoothScrollTo(0, 0);
		}
	}

	private void inResume() {
		if (null != sv_sale) {
			sv_sale.smoothScrollTo(0, 0);
		}
		initData();
	}

	private void initData() {
		String COMMUNITYID = PrefrenceUtils.getStringUser("COMMUNITYID", mContext);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(COMMUNITYID, timestamp);
		c2BHttpRequest.getHttpResponse(
				Http.SHOPLIST + "COMMUNITYID=" + COMMUNITYID + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);
	}

	@SuppressLint("NewApi")
	private void initView() {
		mView.findViewById(R.id.sale_2).setOnClickListener(this);
		mView.findViewById(R.id.sale_1).setOnClickListener(this);
		sv_sale = (ScrollView) mView.findViewById(R.id.sv_sale);
		sv_sale.setOnTouchListener(new TouchListenerImpl());
		sv_sale.smoothScrollTo(0, 0);
		sale_listView1 = (ListView) mView.findViewById(R.id.sale_listView1);
		sale_listView1.setOnItemClickListener(new ItemClickListener());
		main_mAdView = (ImageCycleView) mView.findViewById(R.id.main_imageView03);
		main_srl_view = (SwipeRefreshLayout) mView.findViewById(R.id.main_srl_view);
		// 下拉刷新
		main_srl_view.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				main_srl_view.postDelayed(new Runnable() { // 发送延迟消息到消息队列
					@Override
					public void run() {
						if (!getActivity().isDestroyed()) {
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

	private class TouchListenerImpl implements OnTouchListener {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:

				break;
			case MotionEvent.ACTION_MOVE:
				int scrollY = view.getScrollY();
				int height = view.getHeight();
				int scrollViewMeasuredHeight = sv_sale.getChildAt(0).getMeasuredHeight();
				if (scrollY == 0) {

				}
				if ((scrollY + height) == scrollViewMeasuredHeight) {

					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}).start();

				}
				break;

			default:
				break;
			}
			return false;
		}

	};

	private final class ItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent();
			intent.setClass(mContext, SaleDetailsActivity.class);
			intent.putExtra("SHOPID", data.get(position).getRID());
			intent.putExtra("IMG", data.get(position).getSHOPIMAGE());
			intent.putExtra("NAME", data.get(position).getSHOPNAME());
			intent.putExtra("REMARK", data.get(position).getREMARK());
			startActivity(intent);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;

		}
	}

	public static void setListViewHeightBasedOnChildren1(Context mContext, ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	List<SaleVo> data = new ArrayList<SaleVo>();

	@SuppressLint("NewApi")
	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			switch (reqId) {
			case 1:
				SaleListVo listVo = DataPaser.json2Bean(result, SaleListVo.class);
				if (null != listVo) {
					if (listVo.getCode().equals("101")) {
						data = listVo.getData();
						sale_listView1.setAdapter(new Sale_List_Adapter(mContext, this, data));
						setListViewHeightBasedOnChildren1(mContext, sale_listView1);

						new Thread() {
							public void run() {
								for (SaleVo shops : data) {
									shopsDao.insert(shops);
								}
							};
						}.start();
					} else {
						ToastUtil.showMessage(mContext, listVo.getMsg());
					}
					String OPERID = PrefrenceUtils.getStringUser("OPERID", mContext);
					if (!OPERID.equals("0")) {
						// 获取广告
						c2BHttpRequest.setShow(false);

						String operId = PrefrenceUtils.getStringUser("OPERID", mContext);
						String timestamp = System.currentTimeMillis() + "";
						String key = c2BHttpRequest.getKey(operId + "", timestamp);

						c2BHttpRequest.getHttpResponse(Http.GETADBYPOSITION + "OPERID=" + operId + "&COVERS=B"
								+ "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 2);
					}
				}
				break;
			case 2:
				BaseModel baseModel1 = DataPaser.json2Bean(result, BaseModel.class);
				if (null != baseModel1) {
					if (baseModel1.getCode().equals("101")) {
						ReAdVO adVO = DataPaser.json2Bean(result, ReAdVO.class);
						mImageUrl.clear();
						for (AdVO vo : adVO.getData()) {
							mImageUrl.add(Http.SERVLET_URL + vo.getPICURL());
						}
						if (mImageUrl.size() > 0) {
							main_mAdView.setBackground(null);
							main_mAdView.setImageResources(mImageUrl, mAdCycleViewListener, stype);
						}
					}
				}
				break;
			}
		}
	}

	private ArrayList<String> mImageUrl = new ArrayList<String>();
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
		@Override
		public void onImageClick(int position, View imageView) {

		}
	};

}

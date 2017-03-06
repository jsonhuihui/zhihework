package com.zorgoom.circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zorgoom.app.App;
import com.zorgoom.circle.adapter.CircleAdapter;
import com.zorgoom.circle.bean.CircleItem;
import com.zorgoom.circle.bean.CircleItem2;
import com.zorgoom.circle.bean.CircleMaster;
import com.zorgoom.circle.bean.CommentDetail;
import com.zorgoom.circle.bean.CommentItem;
import com.zorgoom.circle.bean.FavortItem;
import com.zorgoom.circle.bean.LikeDetail;
import com.zorgoom.circle.bean.RsCircleItem;
import com.zorgoom.circle.bean.User;
import com.zorgoom.circle.contral.CirclePublicCommentContral;
import com.zorgoom.circle.utils.CommonUtils;
import com.zorgoom.circle.utils.DatasUtil;
import com.zorgoom.zhihework.BaseActivity;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.adapter.HouseLeaseAdapter;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.RsHouseLease;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @ClassName: MainActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yiw
 * @date 2015-12-28 下午4:21:18
 *
 */
public class CfMainActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, HttpListener {

	protected static final String TAG = CfMainActivity.class.getSimpleName();
	private ListView mCircleLv;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private CircleAdapter mAdapter;
	private LinearLayout mEditTextBody;
	private EditText mEditText;
	private TextView sendTv;

	private int mScreenHeight;
	private int mEditTextBodyHeight;
	private CirclePublicCommentContral mCirclePublicCommentContral;

	private C2BHttpRequest c2BHttpRequest;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:// 返回
			finish();
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cf_activity_main);
		initView();
		c2BHttpRequest = new C2BHttpRequest(this, this);
		c2BHttpRequest.setShow(true);
		loadData();
	}

	@SuppressLint({ "ClickableViewAccessibility", "InlinedApi" })
	private void initView() {
		findViewById(R.id.regis_back).setOnClickListener(this);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mRefreshLayout);
		mCircleLv = (ListView) findViewById(R.id.circleLv);
		mCircleLv.setOnScrollListener(new SwpipeListViewOnScrollListener(mSwipeRefreshLayout));
		mCircleLv.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mEditTextBody.getVisibility() == View.VISIBLE) {
					mEditTextBody.setVisibility(View.GONE);
					CommonUtils.hideSoftInput(CfMainActivity.this, mEditText);
					return true;
				}
				return false;
			}
		});
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mAdapter = new CircleAdapter(this);
		mCircleLv.setAdapter(mAdapter);

		mEditTextBody = (LinearLayout) findViewById(R.id.editTextBodyLl);
		mEditText = (EditText) findViewById(R.id.circleEt);
		sendTv = (TextView) findViewById(R.id.sendTv);

		mCirclePublicCommentContral = new CirclePublicCommentContral(this, mEditTextBody, mEditText, sendTv);
		mCirclePublicCommentContral.setmListView(mCircleLv);
		mAdapter.setmCirclePublicCommentContral(mCirclePublicCommentContral);

		setViewTreeObserver();
	}

	private void setViewTreeObserver() {

		final ViewTreeObserver swipeRefreshLayoutVTO = mSwipeRefreshLayout.getViewTreeObserver();
		swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				System.out.println("==========================setViewTreeObserver=========================");
				Rect r = new Rect();
				mSwipeRefreshLayout.getWindowVisibleDisplayFrame(r);
				int screenH = mSwipeRefreshLayout.getRootView().getHeight();
				int keyH = screenH - (r.bottom - r.top);
				if (keyH == App.mKeyBoardH) {// 有变化时才处理，否则会陷入死循环
					return;
				}
				Log.d(TAG, "keyH = " + keyH + " &r.bottom=" + r.bottom + " &top=" + r.top);
				App.mKeyBoardH = keyH;
				mScreenHeight = screenH;// 应用屏幕的高度
				mEditTextBodyHeight = mEditTextBody.getHeight();
				if (mCirclePublicCommentContral != null) {
					mCirclePublicCommentContral.handleListViewScroll();
				}
			}
		});
	}

	@Override
	public void onRefresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				loadData();
				mSwipeRefreshLayout.setRefreshing(false);
			}
		}, 2000);
	}

	private void loadData() {
		System.out.println("==========================加载数据=========================");
		List<CircleItem> datas = DatasUtil.createCircleDatas();
		System.out.println("demo数据格式:"+datas.toString());

		// 远程获取数据,格式:List<CircleItem>

		String userId = PrefrenceUtils.getStringUser("userId", this);
		String communityId = PrefrenceUtils.getStringUser("COMMUNITYID", this);
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey(userId, timestamp);
		c2BHttpRequest.getHttpResponse(Http.GETUFRIENDDATAS + "USERSID=" + userId + "&COMMUNITYID=" + communityId
				+ "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);

		// mAdapter.setDatas(datas);
		// mAdapter.notifyDataSetChanged();
	}

	public int getScreenHeight() {
		return mScreenHeight;
	}

	public int getEditTextBodyHeight() {
		return mEditTextBodyHeight;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (mEditTextBody != null && mEditTextBody.getVisibility() == View.VISIBLE) {
				mEditTextBody.setVisibility(View.GONE);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void OnResponse(String result, int reqId) {
		System.out.println("==========================OnResponse=========================");
		try {
			if (result != null) {
				RsCircleItem rsCirclsItemVO = DataPaser.json2Bean(result, RsCircleItem.class);
				System.out.println(result);
				if (null != rsCirclsItemVO) {
					if ("101".equals(rsCirclsItemVO.getCode())) {
						if (rsCirclsItemVO.getData().size() == 0) {
							ToastUtil.showMessage1(this, "当前没有消息数据！", 300);
							return;
						}

						List<CircleItem> datas = new ArrayList<CircleItem>();
						CircleItem cItem;
						CircleItem2 jItem;
						CircleMaster master;
						List<LikeDetail> likeDs;
						List<CommentDetail> commentDs;

						User user;// 用户信息
						FavortItem favort;// 点赞信息
						List<FavortItem> favortLst=new ArrayList<FavortItem>();
						
						//评论信息
						List<CommentItem> commentLst=new ArrayList<CommentItem>();
						CommentItem comment;
						for (int i = 0; i < rsCirclsItemVO.getData().size(); i++) {
							cItem = new CircleItem();
							jItem = rsCirclsItemVO.getData().get(i);
							master = jItem.getMaster();
							cItem.setId(master.getID());
							cItem.setCreateTime(master.getADDDATE());
							cItem.setType(master.getTYPE());
							
							likeDs=rsCirclsItemVO.getData().get(i).getLikeDetail();
							for (int j = 0; j < likeDs.size(); j++) {
								favort=new FavortItem();
								favort.setId(likeDs.get(i).getFRIENDDATASID());
								favort.setUser(new User(likeDs.get(i).getUSERSID(),"添加姓名",""));
								favortLst.add(favort);
							}
							cItem.setFavorters(favortLst);
							
							commentDs=rsCirclsItemVO.getData().get(i).getDetail();
							for (int j = 0; j < commentDs.size(); j++) {
								comment=new CommentItem();
								comment.setId(commentDs.get(i).getFRIENDDATASID());
								comment.setContent(commentDs.get(i).getCONTENTINFO());
								comment.setToReplyUser(new User(commentDs.get(i).getUSERSID(),"被评论姓名",""));
								comment.setUser(new User(commentDs.get(i).getUSERSID(),"添加姓名",""));
								commentLst.add(comment);
							}
							
							cItem.setComments(commentLst);
							cItem.setContent(master.getCONTENTINFO());
							//cItem.setPhotos(Arrays.asList(master.getRESPATH().split(";")));
							user = new User(master.getUSERSID(), master.getREALNAME(), "");
							cItem.setUser(user);
							
							datas.add(cItem);
						}

						System.out.println("===============获取到的数据================");
						for (int i = 0; i < datas.size(); i++) {
							System.out.println(datas.get(i).toString());
						}
						mAdapter.setDatas(datas);
						mAdapter.notifyDataSetChanged();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

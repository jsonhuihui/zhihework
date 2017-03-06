package com.zorgoom.chad4;

import com.zorgoom.zhihework.MBaseActivity;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.service.MainService;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import rtc.sdk.common.RtcConst;
import rtc.sdk.core.RtcRules;

/**
 * The Class DemoApp.
 */
public class CallActivity extends MBaseActivity implements OnClickListener {

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("finish")) {
				if (null != mPlayer) {
					mPlayer.stop();
					mPlayer = null;
				}
				isPlay = 0;
				finish();
			} else if (action.equals("huhutongCall")) {
				onBtnCall();
			}
		}
	};
	/** The layoutlocal. */
//	LinearLayout layoutlocal;

	private int index;
	private String cInfo;
	private String name;

	/** The mv local. */
	SurfaceView mvLocal = null;

	private ImageView bt_opendoor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
//		layoutlocal = (LinearLayout) findViewById(R.id.ll_local);

		layoutremote = (LinearLayout) findViewById(R.id.ll_remote);
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish");
		filter.addAction("huhutongCall");
		registerReceiver(receiver, filter);

		findViewById(R.id.bt_Call).setOnClickListener(this);
		findViewById(R.id.bt_hangup).setOnClickListener(this);
		bt_opendoor = (ImageView) findViewById(R.id.bt_opendoor);
		bt_opendoor.setOnClickListener(this);
		index = getIntent().getIntExtra("index", 0);
		if (index == 0) {
			startMedia();
		} else {
			name = getIntent().getStringExtra("name");
			// 发广播
			Intent intent = new Intent("call");
			intent.putExtra("name", name);
			sendBroadcast(intent);
		}
		cInfo = getIntent().getStringExtra("cInfo");
		if (cInfo.equals("123")) {
			bt_opendoor.setVisibility(View.GONE);
		} else {
			bt_opendoor.setVisibility(View.VISIBLE);
		}
	}

	MediaPlayer mPlayer;
	private int isPlay;

	private void startMedia() {
		isPlay = 1;
		mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ling);
		mPlayer.start();
		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (isPlay == 1) {
					mp.start();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (layoutremote != null) {
			if (MainService.callConnection != null)
				MainService.callConnection.resetVideoViews();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
		if (null != mPlayer) {
			mPlayer.stop();
			mPlayer = null;
		}
//		if (layoutlocal != null)
//			layoutlocal.removeAllViews();
		isPlay = 0;
		if (MainService.callConnection != null) {
			MainService.callConnection.disconnect();
			MainService.callConnection = null;
		}
		if (layoutremote != null)
			layoutremote.removeAllViews();
		mvRemote = null;
	}

	/** The mv remote. */
	SurfaceView mvRemote = null;

	/** The layoutlocal. */
	// LinearLayout layoutlocal;

	/** The layoutremote. */
	LinearLayout layoutremote;

	/**
	 * Inits the video views.
	 */

	void initVideoViews() {
		if (mvLocal != null)
			return;
		// if (MainService.callConnection != null)
		// mvLocal = (SurfaceView)
		// MainService.callConnection.createVideoView(true, this, true);
		// mvLocal.setVisibility(View.VISIBLE);
		// layoutlocal.addView(mvLocal);
		// mvLocal.setKeepScreenOn(true);
		// mvLocal.setZOrderMediaOverlay(true);
		// mvLocal.setZOrderOnTop(true);

		if (mvRemote != null)
			return;
		if (MainService.callConnection != null)
			mvRemote = (SurfaceView) MainService.callConnection.createVideoView(false, this, true);
		mvRemote.setVisibility(View.VISIBLE);
		mvRemote.setKeepScreenOn(true);
		mvRemote.setZOrderMediaOverlay(true);
		// mvRemote.setZOrderOnTop(true);
		layoutremote.addView(mvRemote);
	}

	/**
	 * Sets the video surface visibility.
	 *
	 * @param visible
	 *            the new video surface visibility
	 */
	void setVideoSurfaceVisibility(int visible) {
		if (mvRemote != null)
			mvRemote.setVisibility(visible);
	}

	/** The m init. */
	boolean mInit = false; // init

	public void onBtnHangup() {
		if (MainService.callConnection != null) {
			MainService.callConnection.disconnect();
			MainService.callConnection = null;
			setVideoSurfaceVisibility(View.INVISIBLE);
			finish();
		}
	}

	int callType = RtcConst.CallType_A_V;

	public void onBtnCall() {
		if (null != mPlayer) {
			mPlayer.stop();
			mPlayer = null;
		}
		isPlay = 0;
		MainService.callConnection.accept(callType); // 视频来电可以选择仅音频接听
		initVideoViews();
		MainService.callConnection.buildVideo(mvRemote);
		setVideoSurfaceVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_Call:
			onBtnCall();
			break;
		case R.id.bt_hangup:
			onBtnHangup();
			break;
		case R.id.bt_opendoor:
			Util.sendIndex = 4;
			if (null == MainService.fromNo) {
				Toast.makeText(this, "开门失败", Toast.LENGTH_LONG).show();
				return;
			}
			int returnMsg = MainService.device.sendIm(
					RtcRules.UserToRemoteUri_new(MainService.fromNo, RtcConst.UEType_Any), "text/plain",
					PrefrenceUtils.getStringUser("userId", this) + "/openDoor");
			if (returnMsg == 0) {
				Toast.makeText(this, "开门成功", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "开门失败，请再次开门", Toast.LENGTH_LONG).show();
			}
			break;
		}

	}

}

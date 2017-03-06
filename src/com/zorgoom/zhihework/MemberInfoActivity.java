package com.zorgoom.zhihework;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.adapter.ImageLoadUtils;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.view.CircleImageView;
import com.zorgoom.zhihework.vo.MemberInfoModel;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;
import com.zorgoom.zhihework.R;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @ClassName: MemberInfoActivity
 * @Description: 我的信息界面
 * @author Carter
 * @date 2015年9月23日
 * @modifyDate 2015年9月23日
 *
 */
@SuppressLint("NewApi")
public class MemberInfoActivity extends BaseActivity implements OnClickListener, HttpListener {

	private CircleImageView civHeadPort;
	private TextView ctvNumber , ctv_goods_rec_address;
	private Dialog portraitlDialog;
	private RelativeLayout illPortreait;
	private C2BHttpRequest c2bHttpRequest;
	public Object[] imageLoadObj;// 图片加载对象

	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c2bHttpRequest = new C2BHttpRequest(this, this);
		initData();
		initView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		civHeadPort.setImageBitmap(null);
		civHeadPort.setBackground(null);
		civHeadPort.destroyDrawingCache();
		civHeadPort = null;
		if (!(bitmap == null)) {
			bitmap.isRecycled();
			bitmap = null;
		}
	}

	protected void initView() {
		setContentView(R.layout.member_activity_infomation);
		illPortreait = (RelativeLayout) findViewById(R.id.ill_head_portrait);
		ctvNumber = (TextView) findViewById(R.id.ctv_number);
//		ctv_goods_rec_address = (TextView) findViewById(R.id.ctv_goods_rec_address);
		civHeadPort = (CircleImageView) findViewById(R.id.civ_head_portrait);
		String url = PrefrenceUtils.getStringUser("photo", this);
		if (!url.equals("0")) {
			ImageLoadUtils.loadImage(imageLoadObj, Http.SERVLET_URL + url, civHeadPort);
		}
		illPortreait.setOnClickListener(this);
		civHeadPort.setOnClickListener(this);
		ctvNumber.setText(PrefrenceUtils.getStringUser("MOBILE", this));
//		ctv_goods_rec_address.setText(PrefrenceUtils.getStringUser("RID" , this));
		//findViewById(R.id.rl_goods_rec_address).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	protected void initData() {
		imageLoadObj = ImageLoadUtils.initImageLoad(this);// 初始化ImageLoad
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.rl_goods_rec_address:
//			Intent intent = new Intent(this, AddressManageActivity1.class);
//			startActivityForResult(intent, 4);
//			break;
		case R.id.ill_head_portrait:
			showPickDialog();
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 用户自定义头像
	 * 
	 * @author Carter
	 */
	private void showPickDialog() {
		portraitlDialog = new Dialog(this, R.style.DialogStyle);
		portraitlDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		portraitlDialog.setContentView(R.layout.image_way_select);

		Button btn_takePhoto = (Button) portraitlDialog.findViewById(R.id.btn_take_photo);
		btn_takePhoto.setOnClickListener(new OnClickListener() {// 拍照选取

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp_repair.jpg")));
				System.out.println("=============" + Environment.getExternalStorageDirectory());
				startActivityForResult(intent, PHOTOHRAPH);
				portraitlDialog.dismiss();
			}
		});

		Button btn_pick_photo = (Button) portraitlDialog.findViewById(R.id.btn_pick_photo);
		btn_pick_photo.setOnClickListener(new OnClickListener() {// 相册选取

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
				startActivityForResult(intent, PHOTOZOOM);
				portraitlDialog.dismiss();
			}
		});

		Button bt_cancel = (Button) portraitlDialog.findViewById(R.id.btn_cancel);
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				portraitlDialog.dismiss();
			}
		});

		portraitlDialog.setCanceledOnTouchOutside(false);
		portraitlDialog.show();
	}

	@Override
	public void OnResponse(String result, int connectType) {
		Util.dismissLoadDialog();
		if (null != result) {
			MemberInfoModel guestPass = DataPaser.json2Bean(result, MemberInfoModel.class);
			if (null != guestPass) {
				if ("101".equals(guestPass.getCode())) {
					// finish();
					civHeadPort.setImageBitmap(bitmap);
					PrefrenceUtils.saveUser("photo", guestPass.getRid(), this);
					ToastUtil.showMessage(this, guestPass.getMsg());
				} else {
					ToastUtil.showMessage(this, guestPass.getMsg());
				}
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		File picture = null;
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			picture = new File(Environment.getExternalStorageDirectory() + "/temp_repair.jpg");
			System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}
		if (data == null)
			return;
		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
				bitmap = photo;
				sendProt();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 上传头像
	 * 
	 * @param itemId
	 * @param lastpage
	 */
	@SuppressLint("NewApi")
	private void sendProt() {
		Util.showLoadDialog(this, "请求中,请稍候...");
		new Thread() {
			public void run() {
				RequestParams params = new RequestParams();

				String userId = PrefrenceUtils.getStringUser("userId", MemberInfoActivity.this);
				String timestamp = System.currentTimeMillis() + "";
				String key = c2bHttpRequest.getKey(userId + "", timestamp);

				params.addBodyParameter("FKEY", key);
				params.addBodyParameter("TIMESTAMP", timestamp);

				params.addBodyParameter("file", saveBitmaps(bitmap), "image/jpg");
				params.addBodyParameter("USERID", userId);
				c2bHttpRequest.setShow(false);
				c2bHttpRequest.postHttpResponse(Http.ADDUSERHEADER, params, 1);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
					}

				});
			}
		}.start();
	}

	/** 保存方法 */
	public File saveBitmaps(Bitmap bitmap) {
		File file = new File(Environment.getExternalStorageDirectory().getPath(),
				"/" + System.currentTimeMillis() + ".jpg");
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1.77777777778);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}
}

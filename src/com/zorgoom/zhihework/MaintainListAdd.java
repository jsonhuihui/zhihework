package com.zorgoom.zhihework;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lidroid.xutils.http.RequestParams;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.util.DataPaser;
import com.zorgoom.util.PrefrenceUtils;
import com.zorgoom.util.Util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

/**
 * 物业报修
 * 
 * 
 */
public class MaintainListAdd extends BaseActivity implements View.OnClickListener, HttpListener {

	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private C2BHttpRequest c2BHttpRequest;
	private MaintainListAdd mContext;
	private List<HashMap<String, Bitmap>> ImageList = new ArrayList<HashMap<String, Bitmap>>();
	private int ImageNu = 0;
	private Bitmap bmp; // 导入临时图片
	private Bitmap bitmap;
	private GridView gridView1; // 网格显示缩略图
	private EditText et_title, et_content;

	// 适配器
	private SimpleAdapter simpleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// App.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.maintain_add_layout);
		mContext = this;
		c2BHttpRequest = new C2BHttpRequest(this);
		initView();
	}

	private void initView() {
		et_title = (EditText) findViewById(R.id.et_title);
		et_content = (EditText) findViewById(R.id.et_content);

		gridView1 = (GridView) findViewById(R.id.gridView1);
		findViewById(R.id.lv_sumbut).setOnClickListener(this);
		findViewById(R.id.regis_back).setOnClickListener(this);

		/*
		 * 载入默认图片添加图片加号 通过适配器实现 SimpleAdapter参数imageItem为数据源
		 * R.layout.griditem_addpic为布局
		 */
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.gridview_addpic); // 加号
		HashMap<String, Bitmap> map = new HashMap<String, Bitmap>();
		map.put("itemImage", bmp);
		ImageList.add(map);
		simpleAdapter = new SimpleAdapter(this, ImageList, R.layout.griditem_addpic, new String[] { "itemImage" },
				new int[] { R.id.imageView1 });
		simpleAdapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data, String textRepresentation) {
				// TODO Auto-generated method stub
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView i = (ImageView) view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		gridView1.setAdapter(simpleAdapter);

		/*
		 * 监听GridView点击事件 报错:该函数必须抽象方法 故需要手动导入import android.view.View;
		 */
		gridView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (ImageList.size() > 3 && position == 0) { // 第一张为默认图片
					ToastUtil.showMessage1(mContext, "图片数3张已满", 300);
				} else if (position == 0) { // 点击图片位置为+ 0对应0张图片
					Edit_touxiang();
				} else {
					DeleteDialog(position);
				}

			}
		});
	}

	/*
	 * Dialog对话框提示用户删除操作 position为删除图片位置
	 */
	protected void DeleteDialog(final int position) {
		AlertDialog.Builder builder = new Builder(MaintainListAdd.this);
		builder.setMessage("确认移除已添加图片吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				ImageList.remove(position);
				simpleAdapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public void Edit_touxiang() {

		AlertDialog.Builder addPhoneBuilder = new AlertDialog.Builder(MaintainListAdd.this);
		addPhoneBuilder.setTitle("加入图片").setNegativeButton("照相", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp_repair.jpg")));
				System.out.println("=============" + Environment.getExternalStorageDirectory());
				startActivityForResult(intent, PHOTOHRAPH);
			}
		}).setPositiveButton("去相册选择", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
				startActivityForResult(intent, PHOTOZOOM);

			}
		}).show();

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
				HashMap<String, Bitmap> map = new HashMap<String, Bitmap>();
				map.put("itemImage", bitmap);
				ImageList.add(map);
				simpleAdapter.notifyDataSetChanged();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.regis_back:
			finish();
			break;
		case R.id.lv_sumbut:
			ImageNu = 0;
			sendProt();
			break;

		}

	}

	/**
	 * 发送申报
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
				ImageList.remove(0);
				int i = 0;
				for (HashMap<String, Bitmap> hashMap : ImageList) {
					i++;
					Bitmap bitmap = hashMap.get("itemImage");
					// params.addBodyParameter("file" + i, saveBitmaps(bitmap),
					// "image/jpg");
					params.addBodyParameter("file", saveBitmaps(bitmap), "image/jpg");
				}
				String title = et_title.getText().toString();
				String content = et_content.getText().toString();
				if (content.equals("") || title.equals("")) {
					ToastUtil.showMessage1(mContext, "请填写相关内容！", 300);
					return;
				}

				String userId = PrefrenceUtils.getStringUser("userId", mContext);
				String timestamp = System.currentTimeMillis() + "";
				String key = c2BHttpRequest.getKey(userId + "", timestamp);

				params.addBodyParameter("FKEY", key);
				params.addBodyParameter("TIMESTAMP", timestamp);

				params.addBodyParameter("TROUBLETITLE", title);
				params.addBodyParameter("REMARK", content);
				params.addBodyParameter("UNITID", PrefrenceUtils.getStringUser("UNITID", mContext));
				params.addBodyParameter("BLOCKID", PrefrenceUtils.getStringUser("BLOCKID", mContext));
				params.addBodyParameter("USERID", userId);
				params.addBodyParameter("COMMUNITYID", PrefrenceUtils.getStringUser("COMMUNITYID", mContext));
				c2BHttpRequest.setShow(false);
				c2BHttpRequest.postHttpResponse(Http.ADDTROUBLE, params, 1);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
					}

				});
			}
		}.start();
	}

	@Override
	protected void onDestroy() {
		if (!(ImageList == null)) {
			ImageList.clear();
			ImageList = null;
		}
		if (!(bmp == null)) {
			bmp.isRecycled();
			bmp = null;
		}
		if (!(bitmap == null)) {
			bitmap.isRecycled();
			bitmap = null;
		}

		System.gc();
		super.onDestroy();
	}

	@Override
	public void OnResponse(String result, int reqId) {
		Util.dismissLoadDialog();
		if (null != result) {
			BaseModel guestPass = DataPaser.json2Bean(result, BaseModel.class);
			if (null != guestPass) {
				if ("101".equals(guestPass.getCode())) {
					finish();
					ToastUtil.showMessage(mContext, guestPass.getMsg());
				} else {
					ToastUtil.showMessage(mContext, guestPass.getMsg());
				}
			}
		}

	}

}

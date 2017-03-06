package com.zorgoom.zhihework;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.zorgoom.zhihework.adapter.Image_Adapter2;
import com.zorgoom.zhihework.vo.MaintainListment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

public class MaintainInfo extends BaseActivity implements View.OnClickListener {

	private TextView repInfo_textView03, repInfo_textView04, repInfo_textView05;
	private GridView gridView1;
	private ArrayList<String> ImageList = new ArrayList<String>();
	private MaintainListment maintainListment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.repairs_info);
		initView();
	}

	private void initView() {
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		maintainListment = (MaintainListment) bundle.getSerializable("maintainListment");

		gridView1 = (GridView) findViewById(R.id.gridView1);

		repInfo_textView03 = (TextView) findViewById(R.id.repInfo_textView03);
		repInfo_textView04 = (TextView) findViewById(R.id.repInfo_textView04);
		repInfo_textView05 = (TextView) findViewById(R.id.repInfo_textView05);

		repInfo_textView03.setText(maintainListment.getTROUBLETITLE());
		repInfo_textView04.setText(maintainListment.getCREDATE());
		repInfo_textView05.setText(maintainListment.getREMARK());

		findViewById(R.id.regis_back).setOnClickListener(this);
		String images = maintainListment.getIMAGES();
		if (null != images && !images.equals("")) {
			for (int i = 0; i < images.split(",").length; i++) {
				ImageList.add(images.split(",")[i]);
			}
		}
		if (!(ImageList == null) && !(ImageList.size() == 0)) {
			gridView1.setVisibility(View.VISIBLE);
			Image_Adapter2 myListViewAdapter = new Image_Adapter2(ImageList, this, this);
			gridView1.setAdapter(myListViewAdapter);
			myListViewAdapter.notifyDataSetChanged();
		} else {
			gridView1.setVisibility(View.GONE);
		}

		gridView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ImagePagerActivity.imageSize = new ImageSize(arg1.getMeasuredWidth(), arg1.getMeasuredHeight());
				ImagePagerActivity.startImagePagerActivity(getApplicationContext(), ImageList, arg2);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regis_back:
			finish();
			break;

		}
	}
}

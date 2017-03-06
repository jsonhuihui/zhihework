package com.zorgoom.zhihework.adapter;

import java.util.ArrayList;

import com.zorgoom.zhihework.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class Image_Adapter2 extends BaseAdapter {

	ArrayList<String> list;
	Context context;
	View.OnClickListener cliker;
	private Object[] imageLoadObj;

	public Image_Adapter2(ArrayList<String> listmaps, Context context,
			View.OnClickListener clicker) {
		this.list = listmaps;
		this.context = context;
		this.cliker = clicker;
		imageLoadObj = ImageLoadUtils.initImageLoad(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView != null) {

		} else {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			convertView = layoutInflater.inflate(R.layout.griditem_addpic, null);
		}
		ImageView imageView01 = (ImageView) convertView.findViewById(R.id.imageView1);
		
		ImageLoadUtils.loadImage(imageLoadObj, list.get(position), imageView01);

		return convertView;
	}

}

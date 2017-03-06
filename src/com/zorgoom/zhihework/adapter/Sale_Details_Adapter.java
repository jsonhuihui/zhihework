package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.SaleCategoryVo.CategoryVo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Sale_Details_Adapter extends BaseAdapter {

	Context context;
	View.OnClickListener cliker;
	private Object[] imageLoadObj;
	private LayoutInflater layoutInflater;
	private int postion = 0;
	private List<CategoryVo> data;

	public Sale_Details_Adapter(Context context, View.OnClickListener clicker, List<CategoryVo> data) {
		this.context = context;
		this.cliker = clicker;
		imageLoadObj = ImageLoadUtils.initImageLoad(context);
		this.data = data;
		layoutInflater = LayoutInflater.from(context);
	}

	public void setPostion(int postion) {
		this.postion = postion;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CategoryVo infoVO = data.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = layoutInflater.inflate(R.layout.sale_details_item, null);
			blurb.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		if (postion == position) {
			blurb.name.setTextColor(R.color.sale_left_select_text_color);
			blurb.name.setBackground(context.getResources().getDrawable(R.color.white));
		} else {
			blurb.name.setTextColor(R.color.sale_left_text_color);
			blurb.name.setBackground(context.getResources().getDrawable(R.color.sale_left_bg));
		}
		blurb.name.setText(infoVO.getPROD_CATEGORY());
		return convertView;
	}

	class Blurb {
		TextView name;
	}
}

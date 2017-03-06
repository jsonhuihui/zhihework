package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.db.dao.ShopsCartDao;
import com.zorgoom.zhihework.vo.SaleListVo.SaleVo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Sale_List_Adapter extends BaseAdapter {

	Context context;
	View.OnClickListener cliker;
	private Object[] imageLoadObj;
	private List<SaleVo> list;
	private ShopsCartDao shopsCartDao;

	public Sale_List_Adapter(Context context, View.OnClickListener clicker, List<SaleVo> list) {
		this.context = context;
		this.cliker = clicker;
		imageLoadObj = ImageLoadUtils.initImageLoad(context);
		this.list = list;
		shopsCartDao = new ShopsCartDao(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SaleVo infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = LayoutInflater.from(context).inflate(R.layout.sale_item, null);
			blurb.sale_item_textView01 = (TextView) convertView.findViewById(R.id.sale_item_textView01);
			blurb.sale_item_imageView01 = (ImageView) convertView.findViewById(R.id.sale_item_imageView01);
			blurb.sale_item_textView02 = (TextView) convertView.findViewById(R.id.sale_item_textView02);
			blurb.num = (TextView) convertView.findViewById(R.id.num);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		int numNO = shopsCartDao.queryBySidAll(infoVO.getRID());
		if (numNO > 0) {
			blurb.num.setText(numNO + "");
			blurb.num.setVisibility(View.VISIBLE);
		} else {
			blurb.num.setVisibility(View.GONE);
		}
		blurb.sale_item_textView01.setText(infoVO.getSHOPNAME());
		blurb.sale_item_textView02.setText(infoVO.getREMARK());
		ImageLoadUtils.loadImage(imageLoadObj, Http.SERVLET_URL + infoVO.getSHOPIMAGE(), blurb.sale_item_imageView01);
		return convertView;
	}

	class Blurb {
		ImageView sale_item_imageView01;
		TextView sale_item_textView01;// 商家名
		TextView sale_item_textView02;// 介绍
		TextView num;
	}
}

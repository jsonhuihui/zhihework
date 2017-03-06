package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.db.dao.ShopsCartDao;
import com.zorgoom.zhihework.dialog.ToastUtil;
import com.zorgoom.zhihework.vo.DetailsListVo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Sale_Details_Adapter2 extends BaseAdapter {

	Context context;
	View.OnClickListener cliker;
	private Object[] imageLoadObj;
	private LayoutInflater layoutInflater;
	private int postion = 0;
	private onSaleDetailsAdapterListener listener;
	List<DetailsListVo> list;
	private ShopsCartDao shopsCartDao;

	public Sale_Details_Adapter2(Context context, View.OnClickListener clicker, List<DetailsListVo> list) {
		this.context = context;
		this.cliker = clicker;
		imageLoadObj = ImageLoadUtils.initImageLoad(context);
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
		shopsCartDao = new ShopsCartDao(context);
	}

	public void setList(List<DetailsListVo> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void setPostion(int postion) {
		this.postion = postion;
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

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DetailsListVo infoVO = list.get(position);
		Blurb blurb = null;
		if (convertView == null) {
			blurb = new Blurb();
			convertView = layoutInflater.inflate(R.layout.sale_details_item2, null);
			blurb.add_minus = (ImageView) convertView.findViewById(R.id.add_minus);
			blurb.add_car = (ImageView) convertView.findViewById(R.id.add_car);
			blurb.sale_item_imageView01 = (ImageView) convertView.findViewById(R.id.sale_item_imageView01);
			blurb.sale_item_textView01 = (TextView) convertView.findViewById(R.id.sale_item_textView01);
			blurb.sale_item_textView02 = (TextView) convertView.findViewById(R.id.sale_item_textView02);
			blurb.sale_item_textView03 = (TextView) convertView.findViewById(R.id.sale_item_textView03);
			blurb.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			convertView.setTag(blurb);
		} else {
			blurb = (Blurb) convertView.getTag();
		}
		blurb.add_car.setOnClickListener(new MyOnClickListener(position, blurb, infoVO));
		blurb.add_minus.setOnClickListener(new MyOnClickListener(position, blurb, infoVO));
		blurb.sale_item_textView01.setText(infoVO.getPRODNAME());
		blurb.sale_item_textView02.setText("库存 " + infoVO.getSTOCK());
		blurb.sale_item_textView03.setText("￥" + infoVO.getPRICE());
		ImageLoadUtils.loadImage(imageLoadObj, Http.SERVLET_URL + infoVO.getGOODSIMAGE(), blurb.sale_item_imageView01);

		int num = shopsCartDao.queryByGidAll(infoVO.getRID());
		if (num == 0) {
			blurb.add_minus.setVisibility(View.GONE);
			blurb.tv_num.setVisibility(View.GONE);
			blurb.tv_num.setText(0 + "");
		} else {
			blurb.add_minus.setVisibility(View.VISIBLE);
			blurb.tv_num.setVisibility(View.VISIBLE);
			blurb.tv_num.setText(num + "");
		}
		return convertView;
	}

	class MyOnClickListener implements OnClickListener {
		private int position;
		private Blurb blurb;
		private DetailsListVo infoVO;

		public MyOnClickListener(int position, Blurb blurb, DetailsListVo infoVO) {
			this.position = position;
			this.blurb = blurb;
			this.infoVO = infoVO;
		}

		@Override
		public void onClick(View v) {
			int num = Integer.parseInt(blurb.tv_num.getText().toString());
			switch (v.getId()) {
			case R.id.add_car:
				// 库存
				int stock = infoVO.getSTOCK();
				num++;
				if (num > stock) {
					ToastUtil.showMessage(context, "库存不够");
					return;
				}
				blurb.add_minus.setVisibility(View.VISIBLE);
				blurb.tv_num.setVisibility(View.VISIBLE);
				blurb.tv_num.setText(num + "");
				listener.getChoiceData(position, num, 1);
				break;
			case R.id.add_minus:
				num--;
				blurb.tv_num.setText(num + "");
				if (num == 0) {
					blurb.add_minus.setVisibility(View.GONE);
					blurb.tv_num.setVisibility(View.GONE);
					// return;
				}
				listener.getChoiceData(position, num, 0);
				break;

			}
		}
	}

	public interface onSaleDetailsAdapterListener {

		public void getChoiceData(int position, int num, int i);
	}

	public void setOnCheckedChanged(onSaleDetailsAdapterListener listener) {
		this.listener = listener;
	}

	class Blurb {
		ImageView add_car, add_minus;
		ImageView sale_item_imageView01;
		TextView sale_item_textView01, sale_item_textView02, sale_item_textView03, tv_num;
	}
}

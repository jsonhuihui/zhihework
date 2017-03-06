package com.zorgoom.zhihework.adapter;

import java.util.List;

import com.zorgoom.app.JwyBaseAdapter;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.OrderDetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OrderUnPayGoodAdapter extends JwyBaseAdapter<OrderDetail> {

	private ViewHolder holder;
	private Object[] imageLoadObj;

	public OrderUnPayGoodAdapter(Context mContext, List<OrderDetail> list) {
		super(mContext, list);
		imageLoadObj = ImageLoadUtils.initImageLoad(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.order_create_listitem_goodsinfo, null);
			holder = new ViewHolder();
			// holder.ivGoodsPhoto = (ImageView)
			// convertView.findViewById(R.id.iv_goods_photo);
			holder.tvGoodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
			holder.tvGoodsCount = (TextView) convertView.findViewById(R.id.tv_goods_count);
			holder.tvGoodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
			holder.tv_goods_specifications = (TextView) convertView.findViewById(R.id.tv_goods_specifications);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		OrderDetail bizModel = list.get(position);
		// ImageLoadUtils.loadImage(imageLoadObj, bizModel.getPic_path(),
		// holder.ivGoodsPhoto);
		holder.tvGoodsName.setText(bizModel.getPRODNAME());
		holder.tvGoodsCount.setText("x" + bizModel.getCOUNT());
		holder.tvGoodsPrice.setText("¥" + bizModel.getVAL() + "");
		// holder.tv_goods_specifications.setText(bizModel.getSkuContent());
		// holder.tvGoodsSize.setText(bizModel.getSKU() + "");
		return convertView;
	}

	class ViewHolder {
		// ImageView ivGoodsPhoto;
		TextView tvGoodsName;
		TextView tvGoodsCount;
		TextView tv_goods_specifications;
		TextView tvGoodsPrice;
	}

}

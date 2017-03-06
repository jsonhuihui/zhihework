package com.zorgoom.zhihework.adapter;

import java.io.Serializable;
import java.util.List;

import com.zorgoom.app.JwyBaseAdapter;
import com.zorgoom.zhihework.AddressEditActivity;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.vo.AddressList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @ClassName: AddressManageAdapter
 * @Description: TODO(收货地址列表适配器)
 * @author peter
 * @date 2015年7月30日
 * @modifyDate 2015年7月30日
 *
 */
public class AddressManageAdapter extends JwyBaseAdapter<AddressList> {
	private int indexSelect;

	public AddressManageAdapter(Context mContext, List<AddressList> list) {
		super(mContext, list);
		indexSelect = 0;
	}

	public void setIndexSelect(int indexSelect) {
		this.indexSelect = indexSelect;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.address_manage_adapter_layout, null);
			holder = new Holder();
			holder.iv_right = (ImageView) convertView.findViewById(R.id.iv_right);
			holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			holder.iv_edit = (ImageView) convertView.findViewById(R.id.iv_edit);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (indexSelect == position) {
			holder.iv_right.setVisibility(View.VISIBLE);
		} else {
			holder.iv_right.setVisibility(View.GONE);
		}
		AddressList address = list.get(position);
		holder.tv_phone.setText(address.getMOBILE());
		holder.tv_name.setText(address.getNAME());
		holder.tv_address
				.setText(address.getPROVINCE() + address.getCITY() + address.getDISTRICT() + address.getADDRESS());
		// if (address.getISDEFAULT()) {
		// holder.cb_setdefault.setBackground(mContext.getResources().getDrawable(R.drawable.checkbox_true));
		// } else {
		// holder.cb_setdefault.setBackground(mContext.getResources().getDrawable(R.drawable.checkbox_false));
		// }
		holder.iv_edit.setOnClickListener(new MyOnClickListener(address));
		return convertView;
	}

	class MyOnClickListener implements OnClickListener {
		private AddressList address;

		public MyOnClickListener(AddressList address) {
			this.address = address;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mContext, AddressEditActivity.class);
			intent.putExtra("address", (Serializable) address);
			((Activity) mContext).startActivityForResult(intent, 1);
		}
	}

	static class Holder {
		ImageView iv_right;
		ImageView iv_edit;
		TextView tv_name;
		TextView tv_phone;
		TextView tv_address;
	}
}

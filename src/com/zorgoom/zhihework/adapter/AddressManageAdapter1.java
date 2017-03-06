package com.zorgoom.zhihework.adapter;

import java.io.Serializable;
import java.util.List;

import com.zorgoom.app.JwyBaseAdapter;
import com.zorgoom.zhihework.AddressEditActivity;
import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.view.CommonHintDialog;
import com.zorgoom.zhihework.view.CommonHintDialog.onCheckedChanged;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
@SuppressLint("NewApi")
public class AddressManageAdapter1 extends JwyBaseAdapter<AddressList> implements onCheckedChanged {
	private onCheckedChanged listener;

	public AddressManageAdapter1(Context mContext, List<AddressList> list) {
		super(mContext, list);
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.address_manage1_adapter_layout, null);
			holder = new Holder();
			holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			holder.iv_edit = (LinearLayout) convertView.findViewById(R.id.lin_edit);
			holder.lin_delete = (LinearLayout) convertView.findViewById(R.id.lin_delete);
			holder.cb_setdefault = (ImageView) convertView.findViewById(R.id.cb_setdefault);
			holder.rel_setdefault = (RelativeLayout) convertView.findViewById(R.id.rel_setdefault);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		AddressList address = list.get(position);
		holder.tv_phone.setText(address.getMOBILE());
		holder.tv_name.setText(address.getNAME());
		holder.tv_address
				.setText(address.getPROVINCE() + address.getCITY() + address.getDISTRICT() + address.getADDRESS());
		if (address.getISDEFAULT()) {
			holder.cb_setdefault.setBackground(mContext.getResources().getDrawable(R.drawable.checkbox_true));
		} else {
			holder.cb_setdefault.setBackground(mContext.getResources().getDrawable(R.drawable.checkbox_false));
		}

		holder.iv_edit.setOnClickListener(new MyOnClickListener(position));
		holder.lin_delete.setOnClickListener(new MyOnClickListener(position));
		holder.rel_setdefault.setOnClickListener(new MyOnClickListener(position));
		return convertView;
	}

	// class MyOnCheckedChangeListener implements OnCheckedChangeListener {
	// private int position;
	//
	// public MyOnCheckedChangeListener(int position) {
	// this.position = position;
	// }
	//
	// @Override
	// public void onCheckedChanged(CompoundButton buttonView, boolean
	// isChecked) {
	// if (isChecked) {
	// listener.getChoiceData(position, 2);
	// }
	// }
	// }

	class MyOnClickListener implements OnClickListener {
		private int position;

		public MyOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.lin_delete:// 删除
				CommonHintDialog dialog = new CommonHintDialog(mContext, R.style.dialog, 2);
				dialog.setPosition(position);
				dialog.setOnCheckedChanged(AddressManageAdapter1.this);
				dialog.show();
				break;
			case R.id.lin_edit:// 编辑
				Intent intent = new Intent(mContext, AddressEditActivity.class);
				intent.putExtra("address", (Serializable) list.get(position));
				((Activity) mContext).startActivityForResult(intent, 1);
				break;
			case R.id.rel_setdefault:
				// if (Integer.parseInt(list.get(position).getIsDefault()) != 1)
				// {
				// listener.getChoiceData(position, 2);
				// }
				break;
			}
		}
	}

	public interface onCheckedChanged {

		public void getChoiceData(int position, int state);
	}

	public void setOnCheckedChanged(onCheckedChanged listener) {
		this.listener = listener;
	}

	static class Holder {
		LinearLayout iv_edit;
		LinearLayout lin_delete;
		TextView tv_name;
		TextView tv_phone;
		TextView tv_address;
		ImageView cb_setdefault;
		RelativeLayout rel_setdefault;
	}

	@Override
	public void getChoiceData(int position) {
		listener.getChoiceData(position, 1);
	}
}

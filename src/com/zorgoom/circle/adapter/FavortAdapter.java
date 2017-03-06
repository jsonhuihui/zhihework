package com.zorgoom.circle.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.circle.bean.FavortItem;
import com.zorgoom.circle.widgets.custom.CustomAdapter;
import com.zorgoom.zhihework.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @ClassName: FavortAdapter
 * @Description: 点赞adapter
 * @author yiw
 * @date 2015-12-28 下午3:42:21
 *
 */

public class FavortAdapter extends CustomAdapter {
	private Context mContext;
	private List<FavortItem> datas = new ArrayList<FavortItem>();

	public FavortAdapter(Context context) {
		mContext = context;
	}

	public void setDatas(List<FavortItem> datas) {
		if (datas == null)
			this.datas.clear();
		else
			this.datas = datas;
	}

	public List<FavortItem> getDatas() {
		return datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.cf_im_item_dig, null);
				holder.name = (TextView) convertView.findViewById(R.id.digName);
				holder.douhao = (TextView) convertView.findViewById(R.id.douhao);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final FavortItem item = datas.get(position);
			final String name = item.getUser().getName();
			holder.name.setText(name);

			holder.name.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
				}
			});

			if (datas.size() > 1 && position != datas.size() - 1) {
				holder.douhao.setVisibility(View.VISIBLE);
			} else {
				holder.douhao.setVisibility(View.GONE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		public TextView name;
		public TextView douhao;
	}

}

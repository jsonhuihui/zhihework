package com.zorgoom.app;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @ClassName: JwyBaseAdapter
 * @Description: TODO(适配器父类)
 * @author peter
 * @date 2015年7月24日
 * @modifyDate 2015年7月24日
 *
 * @param <T>
 */
public abstract class JwyBaseAdapter<T> extends BaseAdapter {
	public List<T> list;
	public Context mContext;

	public JwyBaseAdapter(List<T> list) {
		this.list = list;
	}

	public JwyBaseAdapter(Context mContext, List<T> list) {
		this.list = list;
		this.mContext = mContext;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public List<T> getList() {
		return list;
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

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}

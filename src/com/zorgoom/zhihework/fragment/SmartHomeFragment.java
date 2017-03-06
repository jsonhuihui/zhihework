package com.zorgoom.zhihework.fragment;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.adapter.SmartHomeAdapter;
import com.zorgoom.util.Util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class SmartHomeFragment extends Fragment {

	public static SmartHomeFragment newInstance() {
		SmartHomeFragment fragment = new SmartHomeFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.smart_home_security_item, null);
		GridView listView = (GridView) mView.findViewById(R.id.message_listView1);
		SmartHomeAdapter myadapter = new SmartHomeAdapter(getContext());
		listView.setAdapter(myadapter);
		Util.setGridViewHeightBasedOnChildren(getContext(), listView);
		return mView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}

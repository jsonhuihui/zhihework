package com.zorgoom.zhihework.fragment;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.adapter.SceneAdapter;
import com.zorgoom.zhihework.adapter.SmartHomeAdapter;
import com.zorgoom.zhihework.service.MainService;
import com.zorgoom.zhihework.view.NoScrollGridView;
import com.zorgoom.zhihework.vo.smart.SenceInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SmartHomeSceneFragment extends Fragment {
	private List<SenceInfo> deviceInfos;
	private Context context;

	public static SmartHomeSceneFragment newInstance() {
		SmartHomeSceneFragment fragment = new SmartHomeSceneFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context = getContext();
		View mView = inflater.inflate(R.layout.smart_home_scene_fragment, null);
		NoScrollGridView listView = (NoScrollGridView) mView.findViewById(R.id.message_listView1);
		SmartHomeAdapter myadapter = new SmartHomeAdapter(getContext());
		listView.setAdapter(myadapter);

		deviceInfos = new ArrayList<SenceInfo>();

		SenceInfo info = new SenceInfo();
		info.setSenceName("全开");
		info.setSenceId((short) 1);
		deviceInfos.add(info);

		info = new SenceInfo();
		info.setSenceName("全关");
		info.setSenceId((short) 0);
		deviceInfos.add(info);

		SceneAdapter sceneAdapter = new SceneAdapter(getActivity(), deviceInfos);
		listView.setAdapter(sceneAdapter);
		// Util.setGridViewHeightBasedOnChildren(getContext(), listView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SenceInfo info = deviceInfos.get(position);
				Intent intent = new Intent(MainService.SMART_CONTROL);
				intent.putExtra("type", 1);
				intent.putExtra("senceId", (int) info.getSenceId());
				intent.putExtra("uid", 0);
				intent.putExtra("status", 0);
				context.sendBroadcast(intent);

			}
		});
		return mView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}

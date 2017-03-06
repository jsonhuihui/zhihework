package com.zorgoom.zhihework.fragment;

import java.util.List;

import com.zorgoom.zhihework.R;
import com.zorgoom.zhihework.adapter.BillListAdapter;
import com.zorgoom.zhihework.vo.RsPayment.Payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BillListFragment2 extends Fragment {

	public static BillListFragment2 newInstance(List<Payment> plist) {
		BillListFragment2 fragment = new BillListFragment2();
		fragment.setList(plist);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private List<Payment> plist;

	private void setList(List<Payment> plist) {
		this.plist = plist;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.bill_laout_item, null);
		ListView listView = (ListView) mView.findViewById(R.id.message_listView1);
		BillListAdapter myadapter = new BillListAdapter(getContext(), plist, 1);
		listView.setAdapter(myadapter);
		return mView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}

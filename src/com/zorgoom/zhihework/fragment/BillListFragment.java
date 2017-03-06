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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BillListFragment extends Fragment {

	public static BillListFragment newInstance(List<Payment> nlist) {
		BillListFragment fragment = new BillListFragment();
		fragment.setList(nlist);
		return fragment;
	}

	private List<Payment> nlist;

	private void setList(List<Payment> nlist) {
		this.nlist = nlist;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.bill_laout_item, null);
		ListView listView = (ListView) mView.findViewById(R.id.message_listView1);

		BillListAdapter myadapter = new BillListAdapter(getContext(), nlist, 0);
		listView.setAdapter(myadapter);
		return mView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}

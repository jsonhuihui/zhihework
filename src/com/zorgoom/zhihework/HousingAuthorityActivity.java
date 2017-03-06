package com.zorgoom.zhihework;

import java.util.ArrayList;
import java.util.List;

import com.zorgoom.util.DataPaser;
import com.zorgoom.zhihework.base.C2BHttpRequest;
import com.zorgoom.zhihework.base.Http;
import com.zorgoom.zhihework.base.HttpListener;
import com.zorgoom.zhihework.vo.ReBlockVO;
import com.zorgoom.zhihework.vo.ReCOMPANYVO;
import com.zorgoom.zhihework.vo.ReCOMPANYVO.COMPANYVO;
import com.zorgoom.zhihework.vo.ReDEPTNAMEVO;
import com.zorgoom.zhihework.vo.ReDEPTNAMEVO.DEPTNAMEVO;
import com.zorgoom.zhihework.vo.ReUnitVO;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class HousingAuthorityActivity extends BaseActivity implements HttpListener {

	private ListView mListView1;// 城市
	private ListView mListView2;// 小区
//	private ListView mListView3;// 楼栋
//	private ListView mListView4;// 房间

	private LinearLayout mLinearLayout1;
	private LinearLayout mLinearLayout2;
//	private LinearLayout mLinearLayout3;
//	private LinearLayout mLinearLayout4;

	private List<String> COMPANYs = new ArrayList<String>();
	private List<String> DEPTNAMEs = new ArrayList<String>();
//	private List<String> block = new ArrayList<String>();
//	private List<String> unit = new ArrayList<String>();

	private ArrayAdapter<String> cityAdapter;
	private ArrayAdapter<String> communityAdapter;
//	private ArrayAdapter<String> blockAdapter;
//	private ArrayAdapter<String> unitAdapter;

	private TextView shengTxt2;
	private TextView shengTxt3, shiTxt3, topView3, my_set_adresschoose_shi_4, my_set_adresschoose_shi_5;

	private String shengStr, commentName, quStr,COMPANYID,DEPTID;

	private String citys, communitys, blocks, units;

	private int BLOCKID, COMMUNITYID;

	private String BLOCKNO;

	private C2BHttpRequest c2BHttpRequest = new C2BHttpRequest(this, this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.housing_authority);

		mLinearLayout1 = (LinearLayout) this.findViewById(R.id.my_set_adresschoose_1);
		mLinearLayout2 = (LinearLayout) this.findViewById(R.id.my_set_adresschoose_2);
//		mLinearLayout3 = (LinearLayout) this.findViewById(R.id.my_set_adresschoose_3);
//		mLinearLayout4 = (LinearLayout) this.findViewById(R.id.my_set_adresschoose_4);

		mLinearLayout1.setVisibility(View.VISIBLE);
		mLinearLayout2.setVisibility(View.GONE);
//		mLinearLayout3.setVisibility(View.GONE);
//		mLinearLayout4.setVisibility(View.GONE);

		shengTxt2 = (TextView) this.findViewById(R.id.my_set_adresschoose_sheng_2);
//		shengTxt3 = (TextView) this.findViewById(R.id.my_set_adresschoose_sheng_3);
//		my_set_adresschoose_shi_4 = (TextView) this.findViewById(R.id.my_set_adresschoose_shi_4);
//		my_set_adresschoose_shi_5 = (TextView) this.findViewById(R.id.my_set_adresschoose_shi_5);
		// shiTxt3 = (TextView) this.findViewById(R.id.my_set_adresschoose_shi_3);
//		topView3 = (TextView) this.findViewById(R.id.my_set_adresschoose_textview_3);

		// 初始化选择城市
		// 获取广告
		String timestamp = System.currentTimeMillis() + "";
		String key = c2BHttpRequest.getKey("", timestamp);
		c2BHttpRequest.getHttpResponse(Http.GETCOMPANY + "?FKEY=" + key + "&TIMESTAMP=" + timestamp, 1);

		cityAdapter = new ArrayAdapter(this, R.layout.housing_authority_listview_item,
				R.id.my_set_adresschoose_textview, COMPANYs);

		communityAdapter = new ArrayAdapter(this, R.layout.housing_authority_listview_item,
				R.id.my_set_adresschoose_textview, DEPTNAMEs);

//		blockAdapter = new ArrayAdapter(this, R.layout.housing_authority_listview_item,
//				R.id.my_set_adresschoose_textview, block);
//
//		unitAdapter = new ArrayAdapter(this, R.layout.housing_authority_listview_item,
//				R.id.my_set_adresschoose_textview, unit);

		mListView1 = (ListView) this.findViewById(R.id.my_set_adresschoose_listview_1);
		mListView1.setAdapter(cityAdapter);
		mListView2 = (ListView) this.findViewById(R.id.my_set_adresschoose_listview_2);
		mListView2.setAdapter(communityAdapter);
//		mListView3 = (ListView) this.findViewById(R.id.my_set_adresschoose_listview_3);
//		mListView3.setAdapter(blockAdapter);
//		mListView4 = (ListView) this.findViewById(R.id.my_set_adresschoose_listview_4);
//		mListView4.setAdapter(unitAdapter);

		shengTxt2.setOnClickListener(click);
//		shengTxt3.setOnClickListener(click);
//		shiTxt3.setOnClickListener(click);
		findViewById(R.id.regis_back).setOnClickListener(click);
//		findViewById(R.id.my_set_adresschoose_sheng_4).setOnClickListener(click);
//		findViewById(R.id.my_set_adresschoose_shi_4).setOnClickListener(click);
//		findViewById(R.id.my_set_adresschoose_shi_5).setOnClickListener(click);
		// 城市点击
		mListView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				DEPTNAMEs.clear();
				mLinearLayout1.setVisibility(View.GONE);
				mLinearLayout2.setVisibility(View.VISIBLE);
//				mLinearLayout3.setVisibility(View.GONE);
//				mLinearLayout4.setVisibility(View.GONE);
				String name = cityVO.getData().get(position).getCOMPANYNAME();
				shengStr = name;
				citys = name;
				shengTxt2.setText(name);
				String id = cityVO.getData().get(position).getCOMPANYID()+"";
				String timestamp = System.currentTimeMillis() + "";
				String key = c2BHttpRequest.getKey(id + "", timestamp);
				COMPANYID = id;
				// 点击城市请求小区
				c2BHttpRequest.getHttpResponse(
						Http.GETCOMMUNITY + "COMPANYID=" + id + "&FKEY=" + key + "&TIMESTAMP=" + timestamp, 2);
			}
		});
		// 小区
		mListView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//				block.clear();
				mLinearLayout1.setVisibility(View.GONE);
				mLinearLayout2.setVisibility(View.GONE);
//				mLinearLayout3.setVisibility(View.VISIBLE);
//				mLinearLayout4.setVisibility(View.GONE);
//				COMMUNITYID = ReDEPTNAMEVO.getRID();
//				shengTxt3.setText(shengStr);
//				commentName = communityVO.getCOMMUNITYNAME();
//				shiTxt3.setText(commentName);
				String id = reCommunityVO.getData().get(position).getDEPTID()+"";
				String name = reCommunityVO.getData().get(position).getDEPTNAME();
				shengStr = name;
				citys = name;
				shengTxt2.setText(name);
				communitys=name;
				DEPTID = id;
				String timestamp = System.currentTimeMillis() + "";
				String key = c2BHttpRequest.getKey(id + "", timestamp);
				// 点击小区请求楼栋
				c2BHttpRequest.getHttpResponse(Http.GETBLOCK + "COMMUNITYID=" + id + "&FKEY=" + key
						+ "&TIMESTAMP=" + timestamp, 3);
				
				Bundle bundle = new Bundle();
				bundle.putString("citys", citys);
				bundle.putString("communitys", communitys);
//				bundle.putString("blocks", blocks);
//				bundle.putString("units", unit.get(position));
//				bundle.putString("UNITID", reUnitVO.getData().get(position).getRID() + "");
				bundle.putString("BLOCKID", BLOCKID + "");
//				bundle.putString("UNITNO", reUnitVO.getData().get(position).getUNITNO() + "");
				bundle.putString("BLOCKNO", BLOCKNO + "");
				bundle.putString("COMMUNITYID", COMMUNITYID + "");
				bundle.putString("COMPANYID", COMPANYID);
				bundle.putString("DEPTID", DEPTID);
				openActivity(HousingAuthorityDetailsActivity.class, bundle);

			}
		});
//		// 楼栋
//		mListView3.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//				unit.clear();
//				mLinearLayout1.setVisibility(View.GONE);
//				mLinearLayout2.setVisibility(View.GONE);
//				mLinearLayout3.setVisibility(View.GONE);
//				mLinearLayout4.setVisibility(View.VISIBLE);
//				BlockVO communityVO = reBlockVO.getData().get(position);
//				shengTxt3.setText(shengStr);
//				my_set_adresschoose_shi_4.setText(commentName);
//				blocks = communityVO.getBLOCKNAME();
//				shiTxt3.setText(communityVO.getBLOCKNAME());
//				BLOCKID = communityVO.getRID();
//				BLOCKNO = communityVO.getBLOCKNO();
//				my_set_adresschoose_shi_5.setText(blocks);
//
//				String timestamp = System.currentTimeMillis() + "";
//				String key = c2BHttpRequest.getKey(communityVO.getRID() + "", timestamp);
//				// 点击楼栋请求房间
//				c2BHttpRequest.getHttpResponse(
//						Http.GETUNIT + "BLOCKID=" + communityVO.getRID() + "&FKEY=" + key + "&TIMESTAMP=" + timestamp,
//						4);
//			}
//		});

//		// 房间号
//		mListView4.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//				Bundle bundle = new Bundle();
//				bundle.putString("citys", citys);
//				bundle.putString("communitys", communitys);
//				bundle.putString("blocks", blocks);
//				bundle.putString("units", unit.get(position));
//				bundle.putString("UNITID", reUnitVO.getData().get(position).getRID() + "");
//				bundle.putString("BLOCKID", BLOCKID + "");
//
//				bundle.putString("UNITNO", reUnitVO.getData().get(position).getUNITNO() + "");
//				bundle.putString("BLOCKNO", BLOCKNO + "");
//				bundle.putString("COMMUNITYID", COMMUNITYID + "");
//				openActivity(HousingAuthorityDetailsActivity.class, bundle);
//			}
//		});
	}

	OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.regis_back:
				finish();
				break;
			case R.id.my_set_adresschoose_sheng_2:
				mLinearLayout1.setVisibility(View.VISIBLE);
				mLinearLayout2.setVisibility(View.GONE);
//				mLinearLayout3.setVisibility(View.GONE);
//				mLinearLayout4.setVisibility(View.GONE);
				break;
//			case R.id.my_set_adresschoose_sheng_3:
//				mLinearLayout1.setVisibility(View.VISIBLE);
//				mLinearLayout2.setVisibility(View.GONE);
//				mLinearLayout3.setVisibility(View.GONE);
//				mLinearLayout4.setVisibility(View.GONE);
//				break;
//			case R.id.my_set_adresschoose_shi_3:
//				mLinearLayout1.setVisibility(View.GONE);
//				mLinearLayout2.setVisibility(View.VISIBLE);
//				mLinearLayout3.setVisibility(View.GONE);
//				mLinearLayout4.setVisibility(View.GONE);
//				break;
			//
//			case R.id.my_set_adresschoose_sheng_4:
//				mLinearLayout1.setVisibility(View.VISIBLE);
//				mLinearLayout2.setVisibility(View.GONE);
//				mLinearLayout3.setVisibility(View.GONE);
//				mLinearLayout4.setVisibility(View.GONE);
//				break;
//			case R.id.my_set_adresschoose_shi_4:
//				mLinearLayout1.setVisibility(View.GONE);
//				mLinearLayout2.setVisibility(View.VISIBLE);
//				mLinearLayout3.setVisibility(View.GONE);
//				mLinearLayout4.setVisibility(View.GONE);
//				break;
//			case R.id.my_set_adresschoose_shi_5:
//				mLinearLayout1.setVisibility(View.GONE);
//				mLinearLayout2.setVisibility(View.GONE);
//				mLinearLayout3.setVisibility(View.VISIBLE);
//				mLinearLayout4.setVisibility(View.GONE);
//				break;
			default:
				break;
			}
		}
	};
	private ReCOMPANYVO cityVO;
	private ReDEPTNAMEVO reCommunityVO;
//	private ReBlockVO reBlockVO;
//	private ReUnitVO reUnitVO;

	@Override
	public void OnResponse(String result, int reqId) {
		if (null != result) {
			switch (reqId) {
			case 1:// 获取城市
				cityVO = DataPaser.json2Bean(result, ReCOMPANYVO.class);
				if (null != cityVO) {
					COMPANYs.clear();
					for (COMPANYVO vo : cityVO.getData()) {
						if (null != vo) {
							COMPANYs.add(vo.getCOMPANYNAME());
						}
					}
					cityAdapter.notifyDataSetChanged();
				}
				break;
			case 2:// 获取小区
				reCommunityVO = DataPaser.json2Bean(result, ReDEPTNAMEVO.class);
				if (null != reCommunityVO) {
					if (null == reCommunityVO.getData()) {
						return;
					}
					DEPTNAMEs.clear();
					for (DEPTNAMEVO vo : reCommunityVO.getData()) {
						DEPTNAMEs.add(vo.getDEPTNAME());
					}
					communityAdapter.notifyDataSetChanged();
				}
				break;
//			case 3:// 获取楼栋
//				reBlockVO = DataPaser.json2Bean(result, ReBlockVO.class);
//				if (null != reBlockVO) {
//					if (null == reBlockVO.getData()) {
//						return;
//					}
//					block.clear();
//					for (BlockVO vo : reBlockVO.getData()) {
//						block.add(vo.getBLOCKNAME() + "");
//					}
//					blockAdapter.notifyDataSetChanged();
//				}
//				break;
//			case 4:// 获取房间
//				reUnitVO = DataPaser.json2Bean(result, ReUnitVO.class);
//				if (null != reBlockVO) {
//					if (null == reUnitVO.getData()) {
//						return;
//					}
//					unit.clear();
//					for (UnitVO vo : reUnitVO.getData()) {
//						unit.add(vo.getUNITNO() + "室");
//					}
//					unitAdapter.notifyDataSetChanged();
//				}
//				break;
			}
		}
	}

}

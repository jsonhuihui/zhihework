package com.zorgoom.zhihework.vo;

import java.util.List;

public class ReCommunityVO extends BaseModel {

	List<CommunityVO> data;

	public class CommunityVO {
		private String COMMUNITYNAME;

		private int RID;

		public String getCOMMUNITYNAME() {
			return COMMUNITYNAME;
		}

		public void setCOMMUNITYNAME(String cOMMUNITYNAME) {
			COMMUNITYNAME = cOMMUNITYNAME;
		}

		public int getRID() {
			return RID;
		}

		public void setRID(int rID) {
			RID = rID;
		}

	}

	public List<CommunityVO> getData() {
		return data;
	}

	public void setData(List<CommunityVO> data) {
		this.data = data;
	}

}

package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 反馈列表
 * 
 * @author Administrator
 *
 */
public class RsFeedBackVO extends BaseModel {

	private List<FeedBackVO> data;

	public List<FeedBackVO> getData() {
		return data;
	}

	public void setData(List<FeedBackVO> data) {
		this.data = data;
	}

	public class FeedBackVO {
		private String STATUS;

		private String STATUS_DSC;

		private String TYPE_DSC;

		private int COMMUNITYID;

		private int USERID;

		private int RID;

		private String TYPE;

		private String MEMO;

		private String CREDATE;

		public void setSTATUS(String STATUS) {
			this.STATUS = STATUS;
		}

		public String getSTATUS() {
			return this.STATUS;
		}

		public void setSTATUS_DSC(String STATUS_DSC) {
			this.STATUS_DSC = STATUS_DSC;
		}

		public String getSTATUS_DSC() {
			return this.STATUS_DSC;
		}

		public void setTYPE_DSC(String TYPE_DSC) {
			this.TYPE_DSC = TYPE_DSC;
		}

		public String getTYPE_DSC() {
			return this.TYPE_DSC;
		}

		public void setCOMMUNITYID(int COMMUNITYID) {
			this.COMMUNITYID = COMMUNITYID;
		}

		public int getCOMMUNITYID() {
			return this.COMMUNITYID;
		}

		public void setUSERID(int USERID) {
			this.USERID = USERID;
		}

		public int getUSERID() {
			return this.USERID;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setTYPE(String TYPE) {
			this.TYPE = TYPE;
		}

		public String getTYPE() {
			return this.TYPE;
		}

		public void setMEMO(String MEMO) {
			this.MEMO = MEMO;
		}

		public String getMEMO() {
			return this.MEMO;
		}

		public String getCREDATE() {
			return CREDATE;
		}

		public void setCREDATE(String cREDATE) {
			CREDATE = cREDATE;
		}

	

	}
}

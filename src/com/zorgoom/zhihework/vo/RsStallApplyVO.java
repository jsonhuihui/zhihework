package com.zorgoom.zhihework.vo;

import java.io.Serializable;
import java.util.List;

public class RsStallApplyVO extends BaseModel implements Serializable {

	private List<StallApplyVO> data;

	public List<StallApplyVO> getData() {
		return data;
	}

	public void setData(List<StallApplyVO> data) {
		this.data = data;
	}

	public class StallApplyVO {
		private String STATUS_DSC;

		private String REALNAME;

		private int UNITID;

		private int COMMUNITYID;

		private String BLOCKNO;

		private int RID;

		private String STATUS;

		private int USERID;

		private String CARNO;

		private String BLOCKNAME;

		private String UNITNO;

		private String MOBILE;

		private String CREDATE;
		private String MEMO;

		public String getMEMO() {
			return MEMO;
		}

		public void setMEMO(String mEMO) {
			MEMO = mEMO;
		}

		public void setSTATUS_DSC(String STATUS_DSC) {
			this.STATUS_DSC = STATUS_DSC;
		}

		public String getSTATUS_DSC() {
			return this.STATUS_DSC;
		}

		public void setREALNAME(String REALNAME) {
			this.REALNAME = REALNAME;
		}

		public String getREALNAME() {
			return this.REALNAME;
		}

		public void setUNITID(int UNITID) {
			this.UNITID = UNITID;
		}

		public int getUNITID() {
			return this.UNITID;
		}

		public void setCOMMUNITYID(int COMMUNITYID) {
			this.COMMUNITYID = COMMUNITYID;
		}

		public int getCOMMUNITYID() {
			return this.COMMUNITYID;
		}

		public void setBLOCKNO(String BLOCKNO) {
			this.BLOCKNO = BLOCKNO;
		}

		public String getBLOCKNO() {
			return this.BLOCKNO;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setSTATUS(String STATUS) {
			this.STATUS = STATUS;
		}

		public String getSTATUS() {
			return this.STATUS;
		}

		public void setUSERID(int USERID) {
			this.USERID = USERID;
		}

		public int getUSERID() {
			return this.USERID;
		}

		public void setCARNO(String CARNO) {
			this.CARNO = CARNO;
		}

		public String getCARNO() {
			return this.CARNO;
		}

		public void setBLOCKNAME(String BLOCKNAME) {
			this.BLOCKNAME = BLOCKNAME;
		}

		public String getBLOCKNAME() {
			return this.BLOCKNAME;
		}

		public void setUNITNO(String UNITNO) {
			this.UNITNO = UNITNO;
		}

		public String getUNITNO() {
			return this.UNITNO;
		}

		public void setMOBILE(String MOBILE) {
			this.MOBILE = MOBILE;
		}

		public String getMOBILE() {
			return this.MOBILE;
		}

		public void setCREDATE(String CREDATE) {
			this.CREDATE = CREDATE;
		}

		public String getCREDATE() {
			return this.CREDATE;
		}

	}
}

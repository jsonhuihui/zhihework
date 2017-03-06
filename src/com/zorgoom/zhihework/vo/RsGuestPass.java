package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 访客通行
 * 
 * @author Administrator
 *
 */
public class RsGuestPass extends BaseModel {
	private List<GuestPass> data;

	public List<GuestPass> getData() {
		return data;
	}

	public void setData(List<GuestPass> data) {
		this.data = data;
	}

	public class GuestPass {
		private int COMMUNITYID;

		private String CREDATE;

		private String ENDDATE;

		private String PASSWORD;

		private String PHONE;

		private int RID;

		private String STATUE;

		private int UNITID;

		private int USERID;

		public void setCOMMUNITYID(int COMMUNITYID) {
			this.COMMUNITYID = COMMUNITYID;
		}

		public long getCOMMUNITYID() {
			return this.COMMUNITYID;
		}

		public void setCREDATE(String CREDATE) {
			this.CREDATE = CREDATE;
		}

		public String getCREDATE() {
			return this.CREDATE;
		}

		public void setENDDATE(String ENDDATE) {
			this.ENDDATE = ENDDATE;
		}

		public String getENDDATE() {
			return this.ENDDATE;
		}

		public void setPASSWORD(String PASSWORD) {
			this.PASSWORD = PASSWORD;
		}

		public String getPASSWORD() {
			return this.PASSWORD;
		}

		public void setPHONE(String PHONE) {
			this.PHONE = PHONE;
		}

		public String getPHONE() {
			return this.PHONE;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setSTATUE(String STATUE) {
			this.STATUE = STATUE;
		}

		public String getSTATUE() {
			return this.STATUE;
		}

		public void setUNITID(int UNITID) {
			this.UNITID = UNITID;
		}

		public int getUNITID() {
			return this.UNITID;
		}

		public void setUSERID(int USERID) {
			this.USERID = USERID;
		}

		public int getUSERID() {
			return this.USERID;
		}
	}
}

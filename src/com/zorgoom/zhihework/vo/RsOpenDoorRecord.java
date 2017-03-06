package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 开门记录
 * 
 * @author Administrator
 *
 */
public class RsOpenDoorRecord extends BaseModel {
	private List<RsOpenDoorRecordData> data;

	public List<RsOpenDoorRecordData> getData() {
		return data;
	}

	public void setData(List<RsOpenDoorRecordData> data) {
		this.data = data;
	}

	public class RsOpenDoorRecordData {
		private String CARDNO;

		private int RID;

		private String CREDATE;

		private int TEMPKEYID;

		private int LOCKID;

		private int UNITID;

		private int COMMUNITYID;

		private String TYPE;

		private int USERID;

		public void setCARDNO(String CARDNO) {
			this.CARDNO = CARDNO;
		}

		public String getCARDNO() {
			return this.CARDNO;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setCREDATE(String CREDATE) {
			this.CREDATE = CREDATE;
		}

		public String getCREDATE() {
			return this.CREDATE;
		}

		public void setTEMPKEYID(int TEMPKEYID) {
			this.TEMPKEYID = TEMPKEYID;
		}

		public int getTEMPKEYID() {
			return this.TEMPKEYID;
		}

		public void setLOCKID(int LOCKID) {
			this.LOCKID = LOCKID;
		}

		public int getLOCKID() {
			return this.LOCKID;
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

		public void setTYPE(String TYPE) {
			this.TYPE = TYPE;
		}

		public String getTYPE() {
			return this.TYPE;
		}

		public void setUSERID(int USERID) {
			this.USERID = USERID;
		}

		public int getUSERID() {
			return this.USERID;
		}

	}
}

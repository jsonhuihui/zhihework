package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 缴费信息
 * 
 * @author Administrator
 *
 */
public class RsPayment extends BaseModel {
	private List<Payment> data;

	public List<Payment> getData() {
		return data;
	}

	public void setData(List<Payment> data) {
		this.data = data;
	}

	public class Payment {
		private String BILLDATE;

		private String BILLSTATE;

		private double BILLTOTAL;

		private int COMMUNITYID;

		private int RID;

		private int UNITID;

		private String UUID;

		public void setBILLDATE(String BILLDATE) {
			this.BILLDATE = BILLDATE;
		}

		public String getBILLDATE() {
			return this.BILLDATE;
		}

		public void setBILLSTATE(String BILLSTATE) {
			this.BILLSTATE = BILLSTATE;
		}

		public String getBILLSTATE() {
			return this.BILLSTATE;
		}

		public void setBILLTOTAL(double BILLTOTAL) {
			this.BILLTOTAL = BILLTOTAL;
		}

		public double getBILLTOTAL() {
			return this.BILLTOTAL;
		}

		public void setCOMMUNITYID(int COMMUNITYID) {
			this.COMMUNITYID = COMMUNITYID;
		}

		public int getCOMMUNITYID() {
			return this.COMMUNITYID;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setUNITID(int UNITID) {
			this.UNITID = UNITID;
		}

		public int getUNITID() {
			return this.UNITID;
		}

		public void setUUID(String UUID) {
			this.UUID = UUID;
		}

		public String getUUID() {
			return this.UUID;
		}

	}
}

package com.zorgoom.zhihework.vo;

import java.util.List;

public class RsPaymentDetail extends BaseModel {
	private List<PaymentDetail> data;

	public List<PaymentDetail> getData() {
		return data;
	}

	public void setData(List<PaymentDetail> data) {
		this.data = data;
	}

	public class PaymentDetail {
		private double FEETOTAL;
		private double FEERATE;
		private int RID;
		private String FEENAME;
		private String BILLSTATE;
		private double QUANTITY;

		public void setFEETOTAL(double FEETOTAL) {
			this.FEETOTAL = FEETOTAL;
		}

		public String getBILLSTATE() {
			return BILLSTATE;
		}

		public void setBILLSTATE(String bILLSTATE) {
			BILLSTATE = bILLSTATE;
		}

		public double getFEETOTAL() {
			return this.FEETOTAL;
		}

		public String getFEENAME() {
			return FEENAME;
		}

		public void setFEENAME(String fEENAME) {
			FEENAME = fEENAME;
		}

		public void setFEERATE(double FEERATE) {
			this.FEERATE = FEERATE;
		}

		public double getFEERATE() {
			return this.FEERATE;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setQUANTITY(double QUANTITY) {
			this.QUANTITY = QUANTITY;
		}

		public double getQUANTITY() {
			return this.QUANTITY;
		}
	}
}
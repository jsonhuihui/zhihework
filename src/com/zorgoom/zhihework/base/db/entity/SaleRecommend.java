package com.zorgoom.zhihework.base.db.entity;

public class SaleRecommend {

	private int id;

	private int RID;

	private String SHOPNAME;

	private String SHOPIMAGE;

	private String REMARK;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRID() {
		return RID;
	}

	public void setRID(int rID) {
		RID = rID;
	}

	public String getSHOPNAME() {
		return SHOPNAME;
	}

	public void setSHOPNAME(String sHOPNAME) {
		SHOPNAME = sHOPNAME;
	}

	public String getSHOPIMAGE() {
		return SHOPIMAGE;
	}

	public void setSHOPIMAGE(String sHOPIMAGE) {
		SHOPIMAGE = sHOPIMAGE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

}

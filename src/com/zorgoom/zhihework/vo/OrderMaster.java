package com.zorgoom.zhihework.vo;

import java.io.Serializable;

public class OrderMaster implements Serializable {
	private String ADDRESS;

	private String CRETIME;

	private int CUSTOMID;

	private String CUSTOMNAME;

	private String CUSTOMPHONE;

	private String ORDERNUM;

	private String PAYTYPE;

	private String REMARKS;

	private int SHOPID;

	private String SHOPIMAGE;

	private String SHOPNAME;

	private String STATE;

	private int TOTALNUM;

	private double TOTALVAL;

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}

	public String getSHOPNAME() {
		return SHOPNAME;
	}

	public void setSHOPNAME(String sHOPNAME) {
		SHOPNAME = sHOPNAME;
	}

	public String getADDRESS() {
		return this.ADDRESS;
	}

	public String getCRETIME() {
		return CRETIME;
	}

	public String getSHOPIMAGE() {
		return SHOPIMAGE;
	}

	public void setSHOPIMAGE(String sHOPIMAGE) {
		SHOPIMAGE = sHOPIMAGE;
	}

	public void setCRETIME(String cRETIME) {
		CRETIME = cRETIME;
	}

	public void setCUSTOMID(int CUSTOMID) {
		this.CUSTOMID = CUSTOMID;
	}

	public int getCUSTOMID() {
		return this.CUSTOMID;
	}

	public void setCUSTOMNAME(String CUSTOMNAME) {
		this.CUSTOMNAME = CUSTOMNAME;
	}

	public String getCUSTOMNAME() {
		return this.CUSTOMNAME;
	}

	public void setCUSTOMPHONE(String CUSTOMPHONE) {
		this.CUSTOMPHONE = CUSTOMPHONE;
	}

	public String getCUSTOMPHONE() {
		return this.CUSTOMPHONE;
	}

	public void setORDERNUM(String ORDERNUM) {
		this.ORDERNUM = ORDERNUM;
	}

	public String getORDERNUM() {
		return this.ORDERNUM;
	}

	public void setPAYTYPE(String PAYTYPE) {
		this.PAYTYPE = PAYTYPE;
	}

	public String getPAYTYPE() {
		return this.PAYTYPE;
	}

	public void setREMARKS(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	public String getREMARKS() {
		return this.REMARKS;
	}

	public void setSHOPID(int SHOPID) {
		this.SHOPID = SHOPID;
	}

	public int getSHOPID() {
		return this.SHOPID;
	}

	public void setTOTALNUM(int TOTALNUM) {
		this.TOTALNUM = TOTALNUM;
	}

	public int getTOTALNUM() {
		return this.TOTALNUM;
	}

	public void setTOTALVAL(double TOTALVAL) {
		this.TOTALVAL = TOTALVAL;
	}

	public double getTOTALVAL() {
		return this.TOTALVAL;
	}
}

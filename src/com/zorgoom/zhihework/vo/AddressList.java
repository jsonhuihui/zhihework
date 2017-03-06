package com.zorgoom.zhihework.vo;

import java.io.Serializable;

public class AddressList implements Serializable{

	private String ADDRESS;

	private String CITY;

	private String DISTRICT;

	private boolean ISDEFAULT;

	private String MOBILE;

	private String NAME;

	private String PROVINCE;

	private int RID;

	private int USERID;

	public void setADDRESS(String ADDRESS) {
		this.ADDRESS = ADDRESS;
	}

	public String getADDRESS() {
		return this.ADDRESS;
	}

	public void setCITY(String CITY) {
		this.CITY = CITY;
	}

	public String getCITY() {
		return this.CITY;
	}

	public void setDISTRICT(String DISTRICT) {
		this.DISTRICT = DISTRICT;
	}

	public String getDISTRICT() {
		return this.DISTRICT;
	}

	public void setISDEFAULT(boolean ISDEFAULT) {
		this.ISDEFAULT = ISDEFAULT;
	}

	public boolean getISDEFAULT() {
		return this.ISDEFAULT;
	}

	public void setMOBILE(String MOBILE) {
		this.MOBILE = MOBILE;
	}

	public String getMOBILE() {
		return this.MOBILE;
	}

	public void setNAME(String NAME) {
		this.NAME = NAME;
	}

	public String getNAME() {
		return this.NAME;
	}

	public void setPROVINCE(String PROVINCE) {
		this.PROVINCE = PROVINCE;
	}

	public String getPROVINCE() {
		return this.PROVINCE;
	}

	public void setRID(int RID) {
		this.RID = RID;
	}

	public int getRID() {
		return this.RID;
	}

	public void setUSERID(int USERID) {
		this.USERID = USERID;
	}

	public int getUSERID() {
		return this.USERID;
	}


}

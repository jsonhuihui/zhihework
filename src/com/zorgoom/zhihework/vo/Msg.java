package com.zorgoom.zhihework.vo;

import java.io.Serializable;

public class Msg implements Serializable {

	private String CREBY;
	private String CREBYNAME;
	private String NOTICETITLE;
	private String CREDATE;
	private String REMARK;

	public String getCREBY() {
		return CREBY;
	}

	public void setCREBY(String cREBY) {
		CREBY = cREBY;
	}

	public String getCREBYNAME() {
		return CREBYNAME;
	}

	public void setCREBYNAME(String cREBYNAME) {
		CREBYNAME = cREBYNAME;
	}

	public String getNOTICETITLE() {
		return NOTICETITLE;
	}

	public void setNOTICETITLE(String nOTICETITLE) {
		NOTICETITLE = nOTICETITLE;
	}

	public String getCREDATE() {
		return CREDATE;
	}

	public void setCREDATE(String cREDATE) {
		CREDATE = cREDATE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

}

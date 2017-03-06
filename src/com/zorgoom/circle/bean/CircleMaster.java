package com.zorgoom.circle.bean;

import java.io.Serializable;

public class CircleMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ID;
	private String TYPE;
	private String USERSID;
	private String CONTENTINFO;
	private String ADDDATE;
	private String LIKECOUNT;
	private String COMMENTCOUNT;
	private String STATAUS;
	private String OPERAUTH;
	private String REALNAME;
	private String RESPATH;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public String getUSERSID() {
		return USERSID;
	}

	public void setUSERSID(String uSERSID) {
		USERSID = uSERSID;
	}

	public String getCONTENTINFO() {
		return CONTENTINFO;
	}

	public void setCONTENTINFO(String cONTENTINFO) {
		CONTENTINFO = cONTENTINFO;
	}

	public String getADDDATE() {
		return ADDDATE;
	}

	public void setADDDATE(String aDDDATE) {
		ADDDATE = aDDDATE;
	}

	public String getLIKECOUNT() {
		return LIKECOUNT;
	}

	public void setLIKECOUNT(String lIKECOUNT) {
		LIKECOUNT = lIKECOUNT;
	}

	public String getCOMMENTCOUNT() {
		return COMMENTCOUNT;
	}

	public void setCOMMENTCOUNT(String cOMMENTCOUNT) {
		COMMENTCOUNT = cOMMENTCOUNT;
	}

	public String getSTATAUS() {
		return STATAUS;
	}

	public void setSTATAUS(String sTATAUS) {
		STATAUS = sTATAUS;
	}

	public String getOPERAUTH() {
		return OPERAUTH;
	}

	public void setOPERAUTH(String oPERAUTH) {
		OPERAUTH = oPERAUTH;
	}

	public String getREALNAME() {
		return REALNAME;
	}

	public void setREALNAME(String rEALNAME) {
		REALNAME = rEALNAME;
	}

	public String getRESPATH() {
		return RESPATH;
	}

	public void setRESPATH(String rESPATH) {
		RESPATH = rESPATH;
	}
}

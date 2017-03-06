package com.zorgoom.circle.bean;

import java.io.Serializable;

public class CommentDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ADDDATE;
	private String USERSID;
	private String TAG;
	private String FRIENDDATASID;
	private String CONTENTINFO;
	private String PARENTTAG;
	public String getADDDATE() {
		return ADDDATE;
	}
	public void setADDDATE(String aDDDATE) {
		ADDDATE = aDDDATE;
	}
	public String getUSERSID() {
		return USERSID;
	}
	public void setUSERSID(String uSERSID) {
		USERSID = uSERSID;
	}
	public String getTAG() {
		return TAG;
	}
	public void setTAG(String tAG) {
		TAG = tAG;
	}
	public String getFRIENDDATASID() {
		return FRIENDDATASID;
	}
	public void setFRIENDDATASID(String fRIENDDATASID) {
		FRIENDDATASID = fRIENDDATASID;
	}
	public String getCONTENTINFO() {
		return CONTENTINFO;
	}
	public void setCONTENTINFO(String cONTENTINFO) {
		CONTENTINFO = cONTENTINFO;
	}
	public String getPARENTTAG() {
		return PARENTTAG;
	}
	public void setPARENTTAG(String pARENTTAG) {
		PARENTTAG = pARENTTAG;
	}
	
}

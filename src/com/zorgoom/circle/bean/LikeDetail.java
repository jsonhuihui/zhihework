package com.zorgoom.circle.bean;

import java.io.Serializable;

public class LikeDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ADDDATE;
	private String USERSID;
	private String ID;
	private String FRIENDDATASID;
	
	
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
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getFRIENDDATASID() {
		return FRIENDDATASID;
	}
	public void setFRIENDDATASID(String fRIENDDATASID) {
		FRIENDDATASID = fRIENDDATASID;
	}
	
}

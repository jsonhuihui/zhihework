package com.zorgoom.zhihework.vo;

import java.io.Serializable;

public class MaintainListment implements Serializable{

	private int COMMUNITYID;

	private String CREDATE;

	private String IMAGES;

	private String REMARK;

	private int RID;

	private String STATE;

	private String TROUBLETITLE;

	private int UNITID;

	private int USERID;

	public void setCOMMUNITYID(int COMMUNITYID) {
		this.COMMUNITYID = COMMUNITYID;
	}

	public int getCOMMUNITYID() {
		return this.COMMUNITYID;
	}

	public void setCREDATE(String CREDATE) {
		this.CREDATE = CREDATE;
	}

	public String getCREDATE() {
		return this.CREDATE;
	}

	public void setIMAGES(String IMAGES) {
		this.IMAGES = IMAGES;
	}

	public String getIMAGES() {
		return this.IMAGES;
	}

	public void setREMARK(String REMARK) {
		this.REMARK = REMARK;
	}

	public String getREMARK() {
		return this.REMARK;
	}

	public void setRID(int RID) {
		this.RID = RID;
	}

	public int getRID() {
		return this.RID;
	}

	public void setSTATE(String STATE) {
		this.STATE = STATE;
	}

	public String getSTATE() {
		return this.STATE;
	}

	public void setTROUBLETITLE(String TROUBLETITLE) {
		this.TROUBLETITLE = TROUBLETITLE;
	}

	public String getTROUBLETITLE() {
		return this.TROUBLETITLE;
	}

	public void setUNITID(int UNITID) {
		this.UNITID = UNITID;
	}

	public int getUNITID() {
		return this.UNITID;
	}

	public void setUSERID(int USERID) {
		this.USERID = USERID;
	}

	public int getUSERID() {
		return this.USERID;
	}

}

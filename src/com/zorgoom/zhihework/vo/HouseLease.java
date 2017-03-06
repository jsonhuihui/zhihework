package com.zorgoom.zhihework.vo;

import java.io.Serializable;

public class HouseLease implements Serializable {

	private String BLOCKNAME;

	private String BLOCKNO;

	private int COMMUNITYID;

	private String CONTENT;

	private String CREDATE;

	private String IMAGES;

	private int RID;

	private String ROOM;

	private String TITLE;

	private int UNITID;

	private String UNITNO;

	private int USERID;

	public String getBLOCKNAME() {
		return BLOCKNAME;
	}

	public void setBLOCKNAME(String bLOCKNAME) {
		BLOCKNAME = bLOCKNAME;
	}

	public String getBLOCKNO() {
		return BLOCKNO;
	}

	public void setBLOCKNO(String bLOCKNO) {
		BLOCKNO = bLOCKNO;
	}

	public int getCOMMUNITYID() {
		return COMMUNITYID;
	}

	public void setCOMMUNITYID(int cOMMUNITYID) {
		COMMUNITYID = cOMMUNITYID;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getCREDATE() {
		return CREDATE;
	}

	public void setCREDATE(String cREDATE) {
		CREDATE = cREDATE;
	}

	public String getIMAGES() {
		return IMAGES;
	}

	public void setIMAGES(String iMAGES) {
		IMAGES = iMAGES;
	}

	public int getRID() {
		return RID;
	}

	public void setRID(int rID) {
		RID = rID;
	}

	public String getROOM() {
		return ROOM;
	}

	public void setROOM(String rOOM) {
		ROOM = rOOM;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	public int getUNITID() {
		return UNITID;
	}

	public void setUNITID(int uNITID) {
		UNITID = uNITID;
	}

	public String getUNITNO() {
		return UNITNO;
	}

	public void setUNITNO(String uNITNO) {
		UNITNO = uNITNO;
	}

	public int getUSERID() {
		return USERID;
	}

	public void setUSERID(int uSERID) {
		USERID = uSERID;
	}

}

package com.zorgoom.zhihework.vo;

import java.io.Serializable;

public class OrderDetail implements Serializable {
	private int COUNT;

	private String ORDERNUM;

	private String PRODID;

	private String PRODNAME;

	private double VAL;

	private String GOODSIMAGE;

	public String getGOODSIMAGE() {
		return GOODSIMAGE;
	}

	public void setGOODSIMAGE(String gOODSIMAGE) {
		GOODSIMAGE = gOODSIMAGE;
	}

	public void setCOUNT(int COUNT) {
		this.COUNT = COUNT;
	}

	public int getCOUNT() {
		return this.COUNT;
	}

	public void setORDERNUM(String ORDERNUM) {
		this.ORDERNUM = ORDERNUM;
	}

	public String getORDERNUM() {
		return this.ORDERNUM;
	}

	public void setPRODID(String PRODID) {
		this.PRODID = PRODID;
	}

	public String getPRODID() {
		return this.PRODID;
	}

	public void setPRODNAME(String PRODNAME) {
		this.PRODNAME = PRODNAME;
	}

	public String getPRODNAME() {
		return this.PRODNAME;
	}

	public void setVAL(double VAL) {
		this.VAL = VAL;
	}

	public double getVAL() {
		return this.VAL;
	}

}

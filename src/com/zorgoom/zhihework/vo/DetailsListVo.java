package com.zorgoom.zhihework.vo;

import java.io.Serializable;

public class DetailsListVo implements Serializable {

	private double PRICE;

	private String PRODNUM;

	private String GOODSIMAGES;

	private String GOODSIMAGE;

	private String STATE;

	private int PRODCATEGORYID;

	private String REMARKS;

	private String PRODNAME;

	private int RID;

	private int SHOPID;

	private int STOCK;

	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setPRICE(double PRICE) {
		this.PRICE = PRICE;
	}

	public double getPRICE() {
		return this.PRICE;
	}

	public void setPRODNUM(String PRODNUM) {
		this.PRODNUM = PRODNUM;
	}

	public String getPRODNUM() {
		return this.PRODNUM;
	}

	public void setGOODSIMAGES(String GOODSIMAGES) {
		this.GOODSIMAGES = GOODSIMAGES;
	}

	public String getGOODSIMAGES() {
		return this.GOODSIMAGES;
	}

	public void setGOODSIMAGE(String GOODSIMAGE) {
		this.GOODSIMAGE = GOODSIMAGE;
	}

	public String getGOODSIMAGE() {
		return this.GOODSIMAGE;
	}

	public void setSTATE(String STATE) {
		this.STATE = STATE;
	}

	public String getSTATE() {
		return this.STATE;
	}

	public void setPRODCATEGORYID(int PRODCATEGORYID) {
		this.PRODCATEGORYID = PRODCATEGORYID;
	}

	public int getPRODCATEGORYID() {
		return this.PRODCATEGORYID;
	}

	public void setREMARKS(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	public String getREMARKS() {
		return this.REMARKS;
	}

	public void setPRODNAME(String PRODNAME) {
		this.PRODNAME = PRODNAME;
	}

	public String getPRODNAME() {
		return this.PRODNAME;
	}

	public void setRID(int RID) {
		this.RID = RID;
	}

	public int getRID() {
		return this.RID;
	}

	public void setSHOPID(int SHOPID) {
		this.SHOPID = SHOPID;
	}

	public int getSHOPID() {
		return this.SHOPID;
	}

	public void setSTOCK(int STOCK) {
		this.STOCK = STOCK;
	}

	public int getSTOCK() {
		return this.STOCK;
	}

}

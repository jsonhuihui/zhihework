package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 商家列表
 * 
 * @author Administrator
 *
 */
public class SaleListVo extends BaseModel {
	private List<SaleVo> data;

	public List<SaleVo> getData() {
		return data;
	}

	public void setData(List<SaleVo> data) {
		this.data = data;
	}

	public class SaleVo {
		private String ADDRESS;

		private int CATEGORYID;

		private int COMMUNITYID;

		private String IMAGES;

		private String LAT;

		private int LISTORDER;

		private String LNG;

		private String REMARK;

		private int RID;

		private String SHOPIMAGE;

		private String SHOPNAME;

		private String TEL;

		public void setADDRESS(String ADDRESS) {
			this.ADDRESS = ADDRESS;
		}

		public String getADDRESS() {
			return this.ADDRESS;
		}

		public void setCATEGORYID(int CATEGORYID) {
			this.CATEGORYID = CATEGORYID;
		}

		public int getCATEGORYID() {
			return this.CATEGORYID;
		}

		public void setCOMMUNITYID(int COMMUNITYID) {
			this.COMMUNITYID = COMMUNITYID;
		}

		public int getCOMMUNITYID() {
			return this.COMMUNITYID;
		}

		public void setIMAGES(String IMAGES) {
			this.IMAGES = IMAGES;
		}

		public String getIMAGES() {
			return this.IMAGES;
		}

		public void setLAT(String LAT) {
			this.LAT = LAT;
		}

		public String getLAT() {
			return this.LAT;
		}

		public void setLISTORDER(int LISTORDER) {
			this.LISTORDER = LISTORDER;
		}

		public int getLISTORDER() {
			return this.LISTORDER;
		}

		public void setLNG(String LNG) {
			this.LNG = LNG;
		}

		public String getLNG() {
			return this.LNG;
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

		public void setSHOPIMAGE(String SHOPIMAGE) {
			this.SHOPIMAGE = SHOPIMAGE;
		}

		public String getSHOPIMAGE() {
			return this.SHOPIMAGE;
		}

		public void setSHOPNAME(String SHOPNAME) {
			this.SHOPNAME = SHOPNAME;
		}

		public String getSHOPNAME() {
			return this.SHOPNAME;
		}

		public void setTEL(String TEL) {
			this.TEL = TEL;
		}

		public String getTEL() {
			return this.TEL;
		}

	}
}

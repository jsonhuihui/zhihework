package com.zorgoom.zhihework.vo;

import java.util.List;

public class HomePriorityVo extends BaseModel {
	private List<PriorityVo> data;

	public List<PriorityVo> getData() {
		return data;
	}

	public void setData(List<PriorityVo> data) {
		this.data = data;
	}

	public class PriorityVo {
		private String ADDRESS;

		private int CATEGORYID;

		private String IMAGES;

		private String LAT;

		private int LISTORDER;

		private String LNG;

		private String PRIORITY;

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

		public void setPRIORITY(String PRIORITY) {
			this.PRIORITY = PRIORITY;
		}

		public String getPRIORITY() {
			return this.PRIORITY;
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

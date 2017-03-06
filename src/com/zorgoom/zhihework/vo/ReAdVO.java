package com.zorgoom.zhihework.vo;

import java.util.List;

public class ReAdVO extends BaseModel {

	private List<AdVO> data;

	public List<AdVO> getData() {
		return data;
	}

	public void setData(List<AdVO> data) {
		this.data = data;
	}

	public class AdVO {
		private int ADID;

		private String COVERS;

		private String CREDATE;

		private String PICURL;

		private int RID;

		private String STATE;

		private String TITLE;

		public int getADID() {
			return ADID;
		}

		public void setADID(int aDID) {
			ADID = aDID;
		}

		public String getCOVERS() {
			return COVERS;
		}

		public void setCOVERS(String cOVERS) {
			COVERS = cOVERS;
		}

		public String getCREDATE() {
			return CREDATE;
		}

		public void setCREDATE(String cREDATE) {
			CREDATE = cREDATE;
		}

		public String getPICURL() {
			return PICURL;
		}

		public void setPICURL(String pICURL) {
			PICURL = pICURL;
		}

		public int getRID() {
			return RID;
		}

		public void setRID(int rID) {
			RID = rID;
		}

		public String getSTATE() {
			return STATE;
		}

		public void setSTATE(String sTATE) {
			STATE = sTATE;
		}

		public String getTITLE() {
			return TITLE;
		}

		public void setTITLE(String tITLE) {
			TITLE = tITLE;
		}
		
	}

}

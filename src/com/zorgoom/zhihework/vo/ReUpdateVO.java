package com.zorgoom.zhihework.vo;

public class ReUpdateVO extends BaseModel {

	private UpdateVO data;

	public UpdateVO getData() {
		return data;
	}

	public void setData(UpdateVO data) {
		this.data = data;
	}

	public class UpdateVO {
		private String VERSIONNUMBER;

		private String CREBYNAME;

		private String CREBY;

		private int RID;

		private String TYPE;

		private String URL;

		private String MEMO;

		private int CREDATE;

		public void setVERSIONNUMBER(String VERSIONNUMBER) {
			this.VERSIONNUMBER = VERSIONNUMBER;
		}

		public String getVERSIONNUMBER() {
			return this.VERSIONNUMBER;
		}

		public void setCREBYNAME(String CREBYNAME) {
			this.CREBYNAME = CREBYNAME;
		}

		public String getCREBYNAME() {
			return this.CREBYNAME;
		}

		public void setCREBY(String CREBY) {
			this.CREBY = CREBY;
		}

		public String getCREBY() {
			return this.CREBY;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setTYPE(String TYPE) {
			this.TYPE = TYPE;
		}

		public String getTYPE() {
			return this.TYPE;
		}

		public void setURL(String URL) {
			this.URL = URL;
		}

		public String getURL() {
			return this.URL;
		}

		public void setMEMO(String MEMO) {
			this.MEMO = MEMO;
		}

		public String getMEMO() {
			return this.MEMO;
		}

		public void setCREDATE(int CREDATE) {
			this.CREDATE = CREDATE;
		}

		public int getCREDATE() {
			return this.CREDATE;
		}
	}

}

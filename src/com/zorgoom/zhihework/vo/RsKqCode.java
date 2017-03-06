package com.zorgoom.zhihework.vo;


public class RsKqCode extends BaseModel {

	private KqCode data;

	public KqCode getData() {
		return data;
	}

	public void setData(KqCode data) {
		this.data = data;
	}

	public class KqCode {
		private String ATTENCEUUID;

		public void setATTENCEUUID(String ATTENCEUUID){
		this.ATTENCEUUID = ATTENCEUUID;
		}
		public String getATTENCEUUID(){
		return this.ATTENCEUUID;
		}

		}
}

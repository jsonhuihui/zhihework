package com.zorgoom.zhihework.vo;

import java.util.List;

public class ReCOMPANYVO extends BaseModel {
	
	List<COMPANYVO> data;

	public class COMPANYVO {
		private String TEL;

		private String COMPANYNAME;

		private String ADDRESS;

		private String CONTACT;

		private int COMPANYID;

		public void setTEL(String TEL){
		this.TEL = TEL;
		}
		public String getTEL(){
		return this.TEL;
		}
		public void setCOMPANYNAME(String COMPANYNAME){
		this.COMPANYNAME = COMPANYNAME;
		}
		public String getCOMPANYNAME(){
		return this.COMPANYNAME;
		}
		public void setADDRESS(String ADDRESS){
		this.ADDRESS = ADDRESS;
		}
		public String getADDRESS(){
		return this.ADDRESS;
		}
		public void setCONTACT(String CONTACT){
		this.CONTACT = CONTACT;
		}
		public String getCONTACT(){
		return this.CONTACT;
		}
		public void setCOMPANYID(int COMPANYID){
		this.COMPANYID = COMPANYID;
		}
		public int getCOMPANYID(){
		return this.COMPANYID;
		}
	}

	public List<COMPANYVO> getData() {
		return data;
	}

	public void setData(List<COMPANYVO> data) {
		this.data = data;
	}

}

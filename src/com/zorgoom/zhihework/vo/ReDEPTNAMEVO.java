package com.zorgoom.zhihework.vo;

import java.util.List;

public class ReDEPTNAMEVO extends BaseModel {

	List<DEPTNAMEVO> data;

	public class DEPTNAMEVO {
		private String DEPTNAME;

		private String SYSTEMCODE;

		private int DEPTID;

		private String DEPTDESC;

		public void setDEPTNAME(String DEPTNAME){
		this.DEPTNAME = DEPTNAME;
		}
		public String getDEPTNAME(){
		return this.DEPTNAME;
		}
		public void setSYSTEMCODE(String SYSTEMCODE){
		this.SYSTEMCODE = SYSTEMCODE;
		}
		public String getSYSTEMCODE(){
		return this.SYSTEMCODE;
		}
		public void setDEPTID(int DEPTID){
		this.DEPTID = DEPTID;
		}
		public int getDEPTID(){
		return this.DEPTID;
		}
		public void setDEPTDESC(String DEPTDESC){
		this.DEPTDESC = DEPTDESC;
		}
		public String getDEPTDESC(){
		return this.DEPTDESC;
		}

		}

	public List<DEPTNAMEVO> getData() {
		return data;
	}

	public void setData(List<DEPTNAMEVO> data) {
		this.data = data;
	}

}

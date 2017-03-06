package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 充值记录记录
 * 
 * @author Administrator
 *
 */
public class RsChargeDetail extends BaseModel {
	private List<RsChargeDetailDate> data;

	public List<RsChargeDetailDate> getData() {
		return data;
	}

	public void setData(List<RsChargeDetailDate> data) {
		this.data = data;
	}

	public class RsChargeDetailDate {
		private String TIME;

		private String EMPID;

		private double MONEY;

		private String EMPNAME;

		private String OPERATORID;

		private int RID;

		private String OPERATORNAME;

		private String DEPTNAME;

		private String DEPTID;

		private String EMPNO;

		private String TYPE;

		private String COMPANYID;

		public void setTIME(String TIME){
		this.TIME = TIME;
		}
		public String getTIME(){
		return this.TIME;
		}
		public void setEMPID(String EMPID){
		this.EMPID = EMPID;
		}
		public String getEMPID(){
		return this.EMPID;
		}
		public void setMONEY(double MONEY){
		this.MONEY = MONEY;
		}
		public double getMONEY(){
		return this.MONEY;
		}
		public void setEMPNAME(String EMPNAME){
		this.EMPNAME = EMPNAME;
		}
		public String getEMPNAME(){
		return this.EMPNAME;
		}
		public void setOPERATORID(String OPERATORID){
		this.OPERATORID = OPERATORID;
		}
		public String getOPERATORID(){
		return this.OPERATORID;
		}
		public void setRID(int RID){
		this.RID = RID;
		}
		public int getRID(){
		return this.RID;
		}
		public void setOPERATORNAME(String OPERATORNAME){
		this.OPERATORNAME = OPERATORNAME;
		}
		public String getOPERATORNAME(){
		return this.OPERATORNAME;
		}
		public void setDEPTNAME(String DEPTNAME){
		this.DEPTNAME = DEPTNAME;
		}
		public String getDEPTNAME(){
		return this.DEPTNAME;
		}
		public void setDEPTID(String DEPTID){
		this.DEPTID = DEPTID;
		}
		public String getDEPTID(){
		return this.DEPTID;
		}
		public void setEMPNO(String EMPNO){
		this.EMPNO = EMPNO;
		}
		public String getEMPNO(){
		return this.EMPNO;
		}
		public void setTYPE(String TYPE){
		this.TYPE = TYPE;
		}
		public String getTYPE(){
		return this.TYPE;
		}
		public void setCOMPANYID(String COMPANYID){
		this.COMPANYID = COMPANYID;
		}
		public String getCOMPANYID(){
		return this.COMPANYID;
		}

		}
}

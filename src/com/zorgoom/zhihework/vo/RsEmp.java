package com.zorgoom.zhihework.vo;

import java.util.List;

public class RsEmp extends BaseModel {

	private RsEmpDate data;

	public RsEmpDate getData() {
		return data;
	}

	public void setData(RsEmpDate data) {
		this.data = data;
	}

	public class RsEmpDate {
		private String STATE;

		private String COMPANYNAME;

		private String GROUPID;

		private String CREDATE;

		private String DEPTNAME;

		private String EMPNO;

		private int EMPID;

		private double MONEY;

		private String AUTHDATE;

		public double getALLOWANCE() {
			return ALLOWANCE;
		}
		public void setALLOWANCE(double aLLOWANCE) {
			ALLOWANCE = aLLOWANCE;
		}
		private String EMPNAME;

		private String TEL;

		private String DEPTID;

		private String COMPANYID;
		
		private double ALLOWANCE;

		public void setSTATE(String STATE){
		this.STATE = STATE;
		}
		public String getSTATE(){
		return this.STATE;
		}
		public void setCOMPANYNAME(String COMPANYNAME){
		this.COMPANYNAME = COMPANYNAME;
		}
		public String getCOMPANYNAME(){
		return this.COMPANYNAME;
		}
		public void setGROUPID(String GROUPID){
		this.GROUPID = GROUPID;
		}
		public String getGROUPID(){
		return this.GROUPID;
		}
		public void setCREDATE(String CREDATE){
		this.CREDATE = CREDATE;
		}
		public String getCREDATE(){
		return this.CREDATE;
		}
		public void setDEPTNAME(String DEPTNAME){
		this.DEPTNAME = DEPTNAME;
		}
		public String getDEPTNAME(){
		return this.DEPTNAME;
		}
		public void setEMPNO(String EMPNO){
		this.EMPNO = EMPNO;
		}
		public String getEMPNO(){
		return this.EMPNO;
		}
		public void setEMPID(int EMPID){
		this.EMPID = EMPID;
		}
		public int getEMPID(){
		return this.EMPID;
		}
		public void setMONEY(double MONEY){
		this.MONEY = MONEY;
		}
		public double getMONEY(){
		return this.MONEY;
		}
		public void setAUTHDATE(String AUTHDATE){
		this.AUTHDATE = AUTHDATE;
		}
		public String getAUTHDATE(){
		return this.AUTHDATE;
		}
		public void setEMPNAME(String EMPNAME){
		this.EMPNAME = EMPNAME;
		}
		public String getEMPNAME(){
		return this.EMPNAME;
		}
		public void setTEL(String TEL){
		this.TEL = TEL;
		}
		public String getTEL(){
		return this.TEL;
		}
		public void setDEPTID(String DEPTID){
		this.DEPTID = DEPTID;
		}
		public String getDEPTID(){
		return this.DEPTID;
		}
		public void setCOMPANYID(String COMPANYID){
		this.COMPANYID = COMPANYID;
		}
		public String getCOMPANYID(){
		return this.COMPANYID;
		}

		}
}
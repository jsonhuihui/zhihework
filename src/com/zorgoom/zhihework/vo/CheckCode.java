package com.zorgoom.zhihework.vo;

import java.io.Serializable;

public class CheckCode implements Serializable{
		private String TIME;//消费时间

		private String EMPID;//员工ID

		private double MONEY;//金额

		private String EMPNAME;//员工姓名

		private String OPERATORID;//操作人ID

		private int RID;//消费ID

		private String DEPTNAME;//部门名称

		private String DEPTID;//部门ID

		private String EMPNO;//员工编号

		private String COMPANYID;//公司ID

		public void setTIME(String TIME) {
			this.TIME = TIME;
		}

		public String getTIME() {
			return this.TIME;
		}

		public void setEMPID(String EMPID) {
			this.EMPID = EMPID;
		}

		public String getEMPID() {
			return this.EMPID;
		}

		public void setMONEY(double MONEY) {
			this.MONEY = MONEY;
		}

		public double getMONEY() {
			return this.MONEY;
		}

		public void setEMPNAME(String EMPNAME) {
			this.EMPNAME = EMPNAME;
		}

		public String getEMPNAME() {
			return this.EMPNAME;
		}

		public void setOPERATORID(String OPERATORID) {
			this.OPERATORID = OPERATORID;
		}

		public String getOPERATORID() {
			return this.OPERATORID;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setDEPTNAME(String DEPTNAME) {
			this.DEPTNAME = DEPTNAME;
		}

		public String getDEPTNAME() {
			return this.DEPTNAME;
		}

		public void setDEPTID(String DEPTID) {
			this.DEPTID = DEPTID;
		}

		public String getDEPTID() {
			return this.DEPTID;
		}

		public void setEMPNO(String EMPNO) {
			this.EMPNO = EMPNO;
		}

		public String getEMPNO() {
			return this.EMPNO;
		}

		public void setCOMPANYID(String COMPANYID) {
			this.COMPANYID = COMPANYID;
		}

		public String getCOMPANYID() {
			return this.COMPANYID;
		}
	}

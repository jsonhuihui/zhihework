package com.zorgoom.zhihework.vo;

/**
 * 部门列表
 * 
 * @author Administrator
 *
 */
public class RsHousing extends BaseModel {
	private COMPANY data;
	private String rid;

	public COMPANY getData() {
		return data;
	}



	public String getRid() {
		return rid;
	}



	public void setRid(String rid) {
		this.rid = rid;
	}



	public void setData(COMPANY data) {
		this.data = data;
	}



	public class COMPANY {
		private double MONEY;

		private int EMPID;

		private String EMPNO;

		private String STATE;

		private String COMPANYNAME;

		private String COMPANYID;

		private String TEL;

		private String DEPTID;

		private String EMPNAME;

		private String AUTHDATE;

		private String CREDATE;
		
		private String DEPTNAME;

		private double ALLOWANCE;
		public double getALLOWANCE() {
			return ALLOWANCE;
		}

		public void setALLOWANCE(double aLLOWANCE) {
			ALLOWANCE = aLLOWANCE;
		}

		public void setMONEY(double MONEY) {
			this.MONEY = MONEY;
		}

		public double getMONEY() {
			return this.MONEY;
		}

		public void setEMPID(int EMPID) {
			this.EMPID = EMPID;
		}

		public int getEMPID() {
			return this.EMPID;
		}

		public void setEMPNO(String EMPNO) {
			this.EMPNO = EMPNO;
		}

		public String getEMPNO() {
			return this.EMPNO;
		}

		public void setSTATE(String STATE) {
			this.STATE = STATE;
		}

		public String getSTATE() {
			return this.STATE;
		}

		public void setCOMPANYNAME(String COMPANYNAME) {
			this.COMPANYNAME = COMPANYNAME;
		}

		public String getCOMPANYNAME() {
			return this.COMPANYNAME;
		}

		public void setCOMPANYID(String COMPANYID) {
			this.COMPANYID = COMPANYID;
		}

		public String getCOMPANYID() {
			return this.COMPANYID;
		}

		public void setTEL(String TEL) {
			this.TEL = TEL;
		}

		public String getTEL() {
			return this.TEL;
		}

		public void setDEPTID(String DEPTID) {
			this.DEPTID = DEPTID;
		}

		public String getDEPTID() {
			return this.DEPTID;
		}

		public void setEMPNAME(String EMPNAME) {
			this.EMPNAME = EMPNAME;
		}

		public String getEMPNAME() {
			return this.EMPNAME;
		}

		public void setAUTHDATE(String AUTHDATE) {
			this.AUTHDATE = AUTHDATE;
		}

		public String getAUTHDATE() {
			return this.AUTHDATE;
		}

		public void setCREDATE(String CREDATE) {
			this.CREDATE = CREDATE;
		}

		public String getCREDATE() {
			return this.CREDATE;
		}
		
		public void setDEPTNAME(String DEPTNAME) {
			this.DEPTNAME = DEPTNAME;
		}

		public String getDEPTNAME() {
			return this.DEPTNAME;
		}
	}
}

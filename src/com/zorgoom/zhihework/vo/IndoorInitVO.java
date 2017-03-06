package com.zorgoom.zhihework.vo;

/**
 * 系统信息反馈VO对象
 * 
 * @author 酷米意
 * 
 */
public class IndoorInitVO extends BaseModel {
	private IndoorInit data;

	public IndoorInit getData() {
		return data;
	}

	public void setData(IndoorInit data) {
		this.data = data;
	}

	public class IndoorInit {
		private String PASSWORD;

		private int UNITID;

		private String IP;

		private int COMMUNITYID;

		private String LAST_LOGIN;

		private int RID;

		private int BLOCKID;

		private String LOGINNAME;


		public void setPASSWORD(String PASSWORD) {
			this.PASSWORD = PASSWORD;
		}

		public String getPASSWORD() {
			return this.PASSWORD;
		}

		public void setUNITID(int UNITID) {
			this.UNITID = UNITID;
		}

		public int getUNITID() {
			return this.UNITID;
		}

		public void setIP(String IP) {
			this.IP = IP;
		}

		public String getIP() {
			return this.IP;
		}

		public void setCOMMUNITYID(int COMMUNITYID) {
			this.COMMUNITYID = COMMUNITYID;
		}

		public int getCOMMUNITYID() {
			return this.COMMUNITYID;
		}

		public void setLAST_LOGIN(String LAST_LOGIN) {
			this.LAST_LOGIN = LAST_LOGIN;
		}

		public String getLAST_LOGIN() {
			return this.LAST_LOGIN;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setBLOCKID(int BLOCKID) {
			this.BLOCKID = BLOCKID;
		}

		public int getBLOCKID() {
			return this.BLOCKID;
		}

		public void setLOGINNAME(String LOGINNAME) {
			this.LOGINNAME = LOGINNAME;
		}

		public String getLOGINNAME() {
			return this.LOGINNAME;
		}


	}
}

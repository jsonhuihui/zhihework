package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 打卡记录
 * 
 * @author Administrator
 *
 */
public class RsAttenceDetail extends BaseModel {
	private List<RsAttenceDetailData> data;

	public List<RsAttenceDetailData> getData() {
		return data;
	}

	public void setData(List<RsAttenceDetailData> data) {
		this.data = data;
	}

	public class RsAttenceDetailData {
		private String EMPID;

		private String WORKTIME;

		private String DATE;

		private int RID;

		private String ENDTIME;

		public void setEMPID(String EMPID) {
			this.EMPID = EMPID;
		}

		public String getEMPID() {
			return this.EMPID;
		}

		public void setWORKTIME(String WORKTIME) {
			this.WORKTIME = WORKTIME;
		}

		public String getWORKTIME() {
			return this.WORKTIME;
		}

		public void setDATE(String DATE) {
			this.DATE = DATE;
		}

		public String getDATE() {
			return this.DATE;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

		public void setENDTIME(String ENDTIME) {
			this.ENDTIME = ENDTIME;
		}

		public String getENDTIME() {
			return this.ENDTIME;
		}
	}
}
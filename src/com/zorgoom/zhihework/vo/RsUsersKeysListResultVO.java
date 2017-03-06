package com.zorgoom.zhihework.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 用户钥匙列表数据返回VO对象
 * 
 * @author pengliying
 * 
 */
public class RsUsersKeysListResultVO extends BaseModel implements Serializable {

	private List<UsersKeys> data;

	public List<UsersKeys> getData() {
		return data;
	}

	public void setData(List<UsersKeys> data) {
		this.data = data;
	}

	public class UsersKeys {
		private String LOCKMAC;
		private String LOCKNAME;
		private int RID;
		private int state;
		

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public String getLOCKMAC() {
			return LOCKMAC;
		}

		public void setLOCKMAC(String lOCKMAC) {
			LOCKMAC = lOCKMAC;
		}

		public String getLOCKNAME() {
			return LOCKNAME;
		}

		public void setLOCKNAME(String lOCKNAME) {
			LOCKNAME = lOCKNAME;
		}

		public int getRID() {
			return RID;
		}

		public void setRID(int rID) {
			RID = rID;
		}

	}
}

package com.zorgoom.zhihework.vo;

import java.util.List;

public class ReUnitVO extends BaseModel {

	private List<UnitVO> data;

	public List<UnitVO> getData() {
		return data;
	}

	public void setData(List<UnitVO> data) {
		this.data = data;
	}

	public class UnitVO {
		private String STATE;
		private double UNITAREA;
		private String UNITNO;
		private String UNITTYPE;
		private int RID;

		public String getSTATE() {
			return STATE;
		}

		public void setSTATE(String sTATE) {
			STATE = sTATE;
		}

		public double getUNITAREA() {
			return UNITAREA;
		}

		public void setUNITAREA(double uNITAREA) {
			UNITAREA = uNITAREA;
		}

		public String getUNITNO() {
			return UNITNO;
		}

		public void setUNITNO(String uNITNO) {
			UNITNO = uNITNO;
		}

		public String getUNITTYPE() {
			return UNITTYPE;
		}

		public void setUNITTYPE(String uNITTYPE) {
			UNITTYPE = uNITTYPE;
		}

		public int getRID() {
			return RID;
		}

		public void setRID(int rID) {
			RID = rID;
		}

	}

}

package com.zorgoom.zhihework.vo;

import java.util.List;

public class ReCityVO extends BaseModel {
	
	List<CityVO> data;

	public class CityVO {
		private String CITY;

		private int RID;

		public void setCITY(String CITY) {
			this.CITY = CITY;
		}

		public String getCITY() {
			return this.CITY;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}
	}

	public List<CityVO> getData() {
		return data;
	}

	public void setData(List<CityVO> data) {
		this.data = data;
	}

}

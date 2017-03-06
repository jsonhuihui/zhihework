package com.zorgoom.zhihework.vo;

import java.io.Serializable;
import java.util.List;

public class RsHouseLease extends BaseModel implements Serializable {

	private List<HouseLease> data;

	public List<HouseLease> getData() {
		return data;
	}

	public void setData(List<HouseLease> data) {
		this.data = data;
	}

}

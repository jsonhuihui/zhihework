package com.zorgoom.zhihework.vo;

import java.io.Serializable;
import java.util.List;

public class RsMsg extends BaseModel implements Serializable{

	private List<Msg> data;

	public List<Msg> getData() {
		return data;
	}

	public void setData(List<Msg> data) {
		this.data = data;
	}

}

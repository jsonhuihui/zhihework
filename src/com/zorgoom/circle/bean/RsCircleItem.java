package com.zorgoom.circle.bean;

import java.io.Serializable;
import java.util.List;

import com.zorgoom.zhihework.vo.BaseModel;

public class RsCircleItem extends BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CircleItem2> data;

	public List<CircleItem2> getData() {
		return data;
	}

	public void setData(List<CircleItem2> data) {
		this.data = data;
	}

}

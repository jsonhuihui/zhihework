package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 维修列表
 * 
 * @author Administrator
 *
 */
public class RsMaintainListment extends BaseModel {

	private List<MaintainListment> data;

	public List<MaintainListment> getData() {
		return data;
	}

	public void setData(List<MaintainListment> data) {
		this.data = data;
	}

}

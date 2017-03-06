package com.zorgoom.zhihework.vo;

import java.io.Serializable;

/**
 * 用户基本信息返回VO对象
 * 
 * @author Administrator
 * 
 */
public class RsSUserVO extends BaseModel implements Serializable {
	private int rid;
	private RsHousing data;
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public RsHousing getData() {
		return data;
	}
	public void setData(RsHousing data) {
		this.data = data;
	}
	

}

package com.zorgoom.zhihework.vo;

import java.util.List;

public class OrderRequest {
	private Master master;
	private List<Detail> detail;

	public Master getMaster() {
		return master;
	}

	public void setMaster(Master master) {
		this.master = master;
	}

	public List<Detail> getDetail() {
		return detail;
	}

	public void setDetail(List<Detail> detail) {
		this.detail = detail;
	}

}

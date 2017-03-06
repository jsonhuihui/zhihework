package com.zorgoom.zhihework.vo;

import java.io.Serializable;
import java.util.List;

public class OrderList implements Serializable {

	private List<OrderDetail> detail;

	private OrderMaster master;

	public List<OrderDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<OrderDetail> detail) {
		this.detail = detail;
	}

	public OrderMaster getMaster() {
		return master;
	}

	public void setMaster(OrderMaster master) {
		this.master = master;
	}

}

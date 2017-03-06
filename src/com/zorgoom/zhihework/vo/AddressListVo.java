package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 收货地址列表
 * 
 * @author Administrator
 *
 */
public class AddressListVo extends BaseModel {
	private List<AddressList> data;

	public List<AddressList> getData() {
		return data;
	}

	public void setData(List<AddressList> data) {
		this.data = data;
	}

}

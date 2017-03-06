package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 商家商品列表
 * 
 * @author Administrator
 *
 */
public class SaleDetailsListVo extends BaseModel {
	private List<DetailsListVo> data;

	public List<DetailsListVo> getData() {
		return data;
	}

	public void setData(List<DetailsListVo> data) {
		this.data = data;
	}

}

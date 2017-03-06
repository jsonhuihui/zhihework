package com.zorgoom.zhihework.vo;

import java.util.List;

import com.zorgoom.zhihework.vo.BaseModel;
import com.zorgoom.zhihework.vo.CheckCode;

/**
 * 消费记录
 * 
 * @author Administrator
 *
 */
public class RsConsumeDetail extends BaseModel {
	private List<CheckCode> data;

	public List<CheckCode> getData() {
		return data;
	}

	public void setData(List<CheckCode> data) {
		this.data = data;
	}
}
package com.zorgoom.zhihework.vo;

import java.util.List;

/**
 * 商家分类列表
 * 
 * @author Administrator
 *
 */
public class SaleCategoryVo extends BaseModel {
	private List<CategoryVo> data;

	public List<CategoryVo> getData() {
		return data;
	}

	public void setData(List<CategoryVo> data) {
		this.data = data;
	}

	public class CategoryVo {
		private int ORDERNO;

		private String PROD_CATEGORY;

		private int RID;

		public void setORDERNO(int ORDERNO) {
			this.ORDERNO = ORDERNO;
		}

		public int getORDERNO() {
			return this.ORDERNO;
		}

		public void setPROD_CATEGORY(String PROD_CATEGORY) {
			this.PROD_CATEGORY = PROD_CATEGORY;
		}

		public String getPROD_CATEGORY() {
			return this.PROD_CATEGORY;
		}

		public void setRID(int RID) {
			this.RID = RID;
		}

		public int getRID() {
			return this.RID;
		}

	}
}

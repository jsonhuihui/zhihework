package com.zorgoom.zhihework.vo;

import java.util.List;

public class RsCode extends BaseModel {

	private Code data;

	public Code getData() {
		return data;
	}

	public void setData(Code data) {
		this.data = data;
	}

	public class Code {
		private String UUID;

		public void setUUID(String UUID){
		this.UUID = UUID;
		}
		public String getUUID(){
		return this.UUID;
		}

		}
}

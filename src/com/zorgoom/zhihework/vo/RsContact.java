package com.zorgoom.zhihework.vo;

import java.util.List;

public class RsContact extends BaseModel {

	private List<Contact> data;

	public List<Contact> getData() {
		return data;
	}

	public void setData(List<Contact> data) {
		this.data = data;
	}

	public class Contact {
		private String TEL;
		private String DEPARTMENT;

		public String getTEL() {
			return TEL;
		}

		public void setTEL(String tEL) {
			TEL = tEL;
		}

		public String getDEPARTMENT() {
			return DEPARTMENT;
		}

		public void setDEPARTMENT(String dEPARTMENT) {
			DEPARTMENT = dEPARTMENT;
		}

	}
}

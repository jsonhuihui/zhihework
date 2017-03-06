package com.zorgoom.zhihework.base.db.entity;

public class MsgPo {

	private int id;
	private int rid;
	private String title;

	public MsgPo(int rid, String title) {
		super();
		this.rid = rid;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

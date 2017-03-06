package com.zorgoom.zhihework.base.db.entity;

/**
 * 商家
 * 
 * @author Administrator
 *
 */
public class Shops {

	private int id;// name
	private int rid;// integer
	private int num;// 数量
	private String name;

	public Shops(int rid, String name) {
		super();
		this.rid = rid;
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

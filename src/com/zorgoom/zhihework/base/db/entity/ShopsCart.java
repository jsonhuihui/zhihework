package com.zorgoom.zhihework.base.db.entity;

public class ShopsCart {
	private int id;// name
	private int sid;// 商家ID
	private int gid;// 商品id
	private String name;
	private int num;
	private String price;// 价格

	public ShopsCart(int gid, String name, int num, String price) {
		super();
		this.name = name;
		this.num = num;
		this.price = price;
		this.gid = gid;
	}

	public int getId() {
		return id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}

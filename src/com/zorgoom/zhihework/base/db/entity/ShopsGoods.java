package com.zorgoom.zhihework.base.db.entity;

/**
 * 商家商品表
 * 
 * @author Administrator
 *
 */
public class ShopsGoods {
	private int id;// name
	private int rid;// integer
	private int sid;// 商家ID
	private String name;
	private String price;// 价格

	public ShopsGoods(int rid, int sid, String name, String price) {
		super();
		this.rid = rid;
		this.sid = sid;
		this.name = name;
		this.price = price;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}

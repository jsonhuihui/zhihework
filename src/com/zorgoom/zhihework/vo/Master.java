package com.zorgoom.zhihework.vo;

public class Master {

	private int shopId;// 商铺ID
	private double totalVal;// 总金额
	private int totalNum;// 总数量
	private int customId;// 顾客ID
	private String customName;// 顾客名称
	private String customPhone;// 顾客电话
	private String address;// 配送地址
	private String remarks;// 备注
	private int OPERTYPE;// 1支付订单费用，2，预存， 3，广告位租费，4，运营月租费
	private int platForm;// 1安卓 2苹果
	private String payType;// W微信支付 C现金 Y银联 Z支付宝

	public Master() {

	}

	public int getOPERTYPE() {
		return OPERTYPE;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getPlatForm() {
		return platForm;
	}

	public void setOPERTYPE(int oPERTYPE) {
		OPERTYPE = oPERTYPE;
	}

	public int set() {
		return platForm;
	}

	public void setPlatForm(int platForm) {
		this.platForm = platForm;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public double getTotalVal() {
		return totalVal;
	}

	public void setTotalVal(double totalVal) {
		this.totalVal = totalVal;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getCustomId() {
		return customId;
	}

	public void setCustomId(int customId) {
		this.customId = customId;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getCustomPhone() {
		return customPhone;
	}

	public void setCustomPhone(String customPhone) {
		this.customPhone = customPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}

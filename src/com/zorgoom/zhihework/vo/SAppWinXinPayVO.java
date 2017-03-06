package com.zorgoom.zhihework.vo;

public class SAppWinXinPayVO extends BaseModel {
	private SAppWinXinPayResVO sAppWinXinPayResVO;

	public SAppWinXinPayResVO getsAppWinXinPayResVO() {
		return sAppWinXinPayResVO;
	}

	public void setsAppWinXinPayResVO(SAppWinXinPayResVO sAppWinXinPayResVO) {
		this.sAppWinXinPayResVO = sAppWinXinPayResVO;
	}

	public class SAppWinXinPayResVO {
		private String appId;

		private String partnerId;

		private String prepayId;

		private String nonceStr;

		private String timeStamp;

		private String packageValue;

		private String sign;

		private int status;

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getAppId() {
			return this.appId;
		}

		public void setPartnerId(String partnerId) {
			this.partnerId = partnerId;
		}

		public String getPartnerId() {
			return this.partnerId;
		}

		public void setPrepayId(String prepayId) {
			this.prepayId = prepayId;
		}

		public String getPrepayId() {
			return this.prepayId;
		}

		public void setNonceStr(String nonceStr) {
			this.nonceStr = nonceStr;
		}

		public String getNonceStr() {
			return this.nonceStr;
		}

		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}

		public String getTimeStamp() {
			return this.timeStamp;
		}

		public void setPackageValue(String packageValue) {
			this.packageValue = packageValue;
		}

		public String getPackageValue() {
			return this.packageValue;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public String getSign() {
			return this.sign;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public int getStatus() {
			return this.status;
		}

	}
}

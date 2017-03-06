package com.zorgoom.zhihework.vo;

import java.util.List;

public class ReBlockVO extends BaseModel {

	private List<BlockVO> data;

	public List<BlockVO> getData() {
		return data;
	}

	public void setData(List<BlockVO> data) {
		this.data = data;
	}

	public class BlockVO {
		private String BLOCKNAME;
		private String BLOCKNO;
		private int RID;

		public String getBLOCKNAME() {
			return BLOCKNAME;
		}

		public void setBLOCKNAME(String bLOCKNAME) {
			BLOCKNAME = bLOCKNAME;
		}

		public String getBLOCKNO() {
			return BLOCKNO;
		}

		public void setBLOCKNO(String bLOCKNO) {
			BLOCKNO = bLOCKNO;
		}

		public int getRID() {
			return RID;
		}

		public void setRID(int rID) {
			RID = rID;
		}

	}

}

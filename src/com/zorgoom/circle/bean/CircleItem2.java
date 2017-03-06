package com.zorgoom.circle.bean;

import java.util.List;

public class CircleItem2 extends BaseBean {
	private static final long serialVersionUID = 1L;
	private List<CommentDetail> detail;
	private List<LikeDetail> likeDetail;

	private CircleMaster master;

	public List<CommentDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<CommentDetail> detail) {
		this.detail = detail;
	}

	public List<LikeDetail> getLikeDetail() {
		return likeDetail;
	}

	public void setLikeDetail(List<LikeDetail> likeDetail) {
		this.likeDetail = likeDetail;
	}

	public CircleMaster getMaster() {
		return master;
	}

	public void setMaster(CircleMaster master) {
		this.master = master;
	}

}

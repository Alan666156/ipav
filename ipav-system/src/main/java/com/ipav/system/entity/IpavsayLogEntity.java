package com.ipav.system.entity;

import java.io.Serializable;

public class IpavsayLogEntity implements Serializable {

	private static final long serialVersionUID = -4934885357587484960L;

	private Long saylogid;// 说说日志ID
	private Long sayid;// 说说ID
	private Long commentid;// 评论ID
	private String userid = "";// 操作用户ID
	private String username = "";// 操作用户名
	private Integer actiontype;// 操作类型
	private String actiondate = "";// 操作时间

	public Long getSaylogid() {
		return saylogid;
	}

	public void setSaylogid(Long saylogid) {
		this.saylogid = saylogid;
	}

	public Long getSayid() {
		return sayid;
	}

	public void setSayid(Long sayid) {
		this.sayid = sayid;
	}

	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getActiontype() {
		return actiontype;
	}

	public void setActiontype(Integer actiontype) {
		this.actiontype = actiontype;
	}

	public String getActiondate() {
		return actiondate;
	}

	public void setActiondate(String actiondate) {
		this.actiondate = actiondate;
	}

}

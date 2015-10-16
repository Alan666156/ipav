package com.ipav.system.entity;

import java.io.Serializable;

public class IpavnoticeSendedEntity implements Serializable {

	private static final long serialVersionUID = -4928323121701664241L;

	private Long id;// ID
	private String userid;// 接收用户ID
	private String username;// 接收用户名
	private Long noticeid;// 公告ID
	private Integer isread;// 是否已读
	private Integer isdelete;// 是否删除

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(Long noticeid) {
		this.noticeid = noticeid;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

}

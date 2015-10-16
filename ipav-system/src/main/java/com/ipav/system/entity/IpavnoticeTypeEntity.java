package com.ipav.system.entity;

import java.io.Serializable;

public class IpavnoticeTypeEntity implements Serializable {
	private static final long serialVersionUID = -6073584358073990404L;

	private Long id;// 类型ID
	private String typename;// 类型名
	private String description;// 类型描述
	private String userid;// 创建用户ID
	private String username;// 创建用户名
	private String createdate;// 创建时间
	private Integer typeindex;// 类型编号
	private Integer isdefault;// 是否为公告默认类型(0:代表非默认类型,1:代表为默认类型)
	private Integer istrue;// 是否启用(0:代表启用,1:代表禁用)
	private Integer isdelete;// 数据是否删除(0:代表未删除,1:代表删除)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	public Integer getTypeindex() {
		return typeindex;
	}

	public void setTypeindex(Integer typeindex) {
		this.typeindex = typeindex;
	}

}

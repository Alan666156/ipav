package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry All right reserved. Created on 2014年11月21日 下午2:52:49
 * 上海天道启科电子有限公司
 */
public class IpavsayImageEntity implements Serializable {
	private static final long serialVersionUID = -5002098730621021623L;

	private Long imageid;
	private Long sayid;
	private String imagename = "";
	private String imagepath = "";
	private String createdate = "";

	public Long getImageid() {
		return imageid;
	}

	public void setImageid(Long imageid) {
		this.imageid = imageid;
	}

	public Long getSayid() {
		return sayid;
	}

	public void setSayid(Long sayid) {
		this.sayid = sayid;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

}

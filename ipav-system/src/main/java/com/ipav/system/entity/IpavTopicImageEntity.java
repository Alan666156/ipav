package com.ipav.system.entity;

import java.io.Serializable;

/**
 * @author Aliens
 */
public class IpavTopicImageEntity implements Serializable {
	private static final long serialVersionUID = -5002098730621021623L;

	private Long imageid;
	private Long topicid;
	private String imagename = "";
	private String imagepath = "";
	private String createdate = "";

	public Long getImageid() {
		return imageid;
	}

	public void setImageid(Long imageid) {
		this.imageid = imageid;
	}
 

	public Long getTopicid() {
		return topicid;
	}

	public void setTopicid(Long topicid) {
		this.topicid = topicid;
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

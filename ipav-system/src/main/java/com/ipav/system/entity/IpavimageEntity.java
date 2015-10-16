package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月21日 下午2:52:49	
 * 上海天道启科电子有限公司
 */
public class IpavimageEntity  implements Serializable{
	private static final long serialVersionUID = 4014284208451991654L;
	
	private Long imageid;
	private Long companyid;
	private String userid;
	private Long menuid;
	private String sorcepath;
	private String subpath;
	private String creatdate;
	private String creater;
	private String bustype;
	
	public Long getImageid() {
		return imageid;
	}
	public void setImageid(Long imageid) {
		this.imageid = imageid;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Long getMenuid() {
		return menuid;
	}
	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}
	public String getSorcepath() {
		return sorcepath;
	}
	public void setSorcepath(String sorcepath) {
		this.sorcepath = sorcepath;
	}
	public String getSubpath() {
		return subpath;
	}
	public void setSubpath(String subpath) {
		this.subpath = subpath;
	}
	public String getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(String creatdate) {
		this.creatdate = creatdate;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getBustype() {
		return bustype;
	}
	public void setBustype(String bustype) {
		this.bustype = bustype;
	}
	
	
}

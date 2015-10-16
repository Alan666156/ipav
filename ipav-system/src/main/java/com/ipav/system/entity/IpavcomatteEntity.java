package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月11日 上午9:49:13	
 * 上海天道启科电子有限公司
 */
public class IpavcomatteEntity implements Serializable{
	private static final long serialVersionUID = 4014284208451991654L;
	private Integer id ;
	private String reason="";
	private Long companyid ;
	private String attedate="";
	private String atteuser="";
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getAttedate() {
		return attedate;
	}
	public void setAttedate(String attedate) {
		this.attedate = attedate;
	}
	public String getAtteuser() {
		return atteuser;
	}
	public void setAtteuser(String atteuser) {
		this.atteuser = atteuser;
	}
	
	
}

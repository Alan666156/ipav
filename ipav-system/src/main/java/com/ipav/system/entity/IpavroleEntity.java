package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月1日 上午10:09:48	
 * 上海天道启科电子有限公司
 */
public class IpavroleEntity implements Serializable{
	private static final long serialVersionUID = 4014284208451991654L;
	private Integer roleno ;//角色ID
	private String rolename="";//角色名称
	private Long companyid;//公司ID
	private String valflg="";//是否有效
	private String createdate="";
	private String creater="";
	private String mark;
	private String sysroleflg = "";
	private String delflg ="";//是否删除
	
	
	public String getSysroleflg() {
		return sysroleflg;
	}
	public void setSysroleflg(String sysroleflg) {
		this.sysroleflg = sysroleflg;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getRoleno() {
		return roleno;
	}
	public void setRoleno(Integer roleno) {
		this.roleno = roleno;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getValflg() {
		return valflg;
	}
	public void setValflg(String valflg) {
		this.valflg = valflg;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getDelflg() {
		return delflg;
	}
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}
	
	
}

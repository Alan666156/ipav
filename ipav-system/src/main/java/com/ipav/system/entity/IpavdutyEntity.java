package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月8日 上午11:05:06	
 * 上海天道启科电子有限公司
 */
public class IpavdutyEntity implements Serializable{
	private static final long serialVersionUID = 4014284208451991654L;
	
	private Integer dutyid;//id
	private String dutyname="";//职务名称
	private String valflg="";//生效标识
	private String delflg="";//是否删除
	private Long companyid;//公司id
	private String createdate="";//创建时间
	private String creater="";//创建人
	public Integer getDutyid() {
		return dutyid;
	}
	public void setDutyid(Integer dutyid) {
		this.dutyid = dutyid;
	}
	public String getDutyname() {
		return dutyname;
	}
	public void setDutyname(String dutyname) {
		this.dutyname = dutyname;
	}
	public String getValflg() {
		return valflg;
	}
	public void setValflg(String valflg) {
		this.valflg = valflg;
	}
	public String getDelflg() {
		return delflg;
	}
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
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
	
	
}

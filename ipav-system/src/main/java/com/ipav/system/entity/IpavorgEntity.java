package com.ipav.system.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月24日 上午11:42:08	
 * 上海天道启科电子有限公司
 */
public class IpavorgEntity implements Serializable{
	private static final long serialVersionUID = 4014284208451991654L;
	
	public Long orgid;//机构ID
	public Long companyid;//公司ID
	public String orgno;//机构编号
	public String orgname;//机构名称
	public String orgfullname;
	public int levl;//机构层级
	public String parentno;//父级机构号
	public String rootno;//顶层机构号
	public String valflg;//是否生效
	public String createdate;//创建时间
	public String creater;//创建人
	public String remark;//备注
	public String orgchef;//机构负责人
	public String orgchefName;//机构负责人
	public String seqno  ;//序号
	
	
	public String getOrgfullname() {
		return orgfullname;
	}
	public void setOrgfullname(String orgfullname) {
		this.orgfullname = orgfullname;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public int getLevl() {
		return levl;
	}
	public void setLevl(int levl) {
		this.levl = levl;
	}
	public String getParentno() {
		return parentno;
	}
	public void setParentno(String parentno) {
		this.parentno = parentno;
	}
	public String getRootno() {
		return rootno;
	}
	public void setRootno(String rootno) {
		this.rootno = rootno;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrgchef() {
		return orgchef;
	}
	public void setOrgchef(String orgchef) {
		this.orgchef = orgchef;
	}
	public String getOrgchefName() {
		return orgchefName;
	}
	public void setOrgchefName(String orgchefName) {
		this.orgchefName = orgchefName;
	}
	
	
}

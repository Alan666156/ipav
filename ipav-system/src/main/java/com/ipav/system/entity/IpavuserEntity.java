package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月15日 下午1:57:11	
 * 上海天道启科电子有限公司
 */
public class IpavuserEntity implements Serializable{
	private static final long serialVersionUID = 4014284208451991654L;
	
	private String userid="" ;//用户ID
	private Long companyid;//公司ID
	private String userno="";//员工编号
	private String username="";//员工姓名
	private String sex="";//性别 1-男  2-女
	private Long orgid;//部门
	private String mobile="";//手机号
	private String email="";//电子邮箱
	private String valflg="";//用工状态
	private String password="";//密码
	private String regtype="";//注册方式 
	private String createdate="";//创建时间
	private String creater="";//创建人
	private Integer duty ;//职务
	private String exorgno="";//'兼职部门',
	private String exduty="" ; //'兼职职务',
	private String picpath="" ; //'照片',
	private String sysid="";// '所属工作平台',
	private String address="" ;//'办公地址',
	private String phone="" ; // '办公电话',
	private String chefflg ="";//是否部门最高职务
	private String delflg ="";//是否删除
	private int labourBelong =0;//劳动合同单位
	private int platform =0;//平台
	private String director ="";//直接主管id
	private String directorName ="";//直接主管名称
	private int state;//状态 0-第一次  1-不是第一次
	
	public String getChefflg() {
		return chefflg;
	}
	public void setChefflg(String chefflg) {
		this.chefflg = chefflg;
	}
 
	public Integer getDuty() {
		return duty;
	}
	public void setDuty(Integer duty) {
		this.duty = duty;
	}
	public String getDelflg() {
		return delflg;
	}
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}
	public String getExorgno() {
		return exorgno;
	}
	public void setExorgno(String exorgno) {
		this.exorgno = exorgno;
	}
	public String getExduty() {
		return exduty;
	}
	public void setExduty(String exduty) {
		this.exduty = exduty;
	}
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getValflg() {
		return valflg;
	}
	public void setValflg(String valflg) {
		this.valflg = valflg;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegtype() {
		return regtype;
	}
	public void setRegtype(String regtype) {
		this.regtype = regtype;
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
	public int getLabourBelong() {
		return labourBelong;
	}
	public void setLabourBelong(int labourBelong) {
		this.labourBelong = labourBelong;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getDirectorName() {
		return directorName;
	}
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
	
}

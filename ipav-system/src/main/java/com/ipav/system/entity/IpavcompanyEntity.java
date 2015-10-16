package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月15日 下午3:48:22	
 * 上海天道启科电子有限公司
 */
public class IpavcompanyEntity implements Serializable{
	 private static final long serialVersionUID = 4014284208451991654L;
	
	 private Long companyid;//公司ID
	 private String companyname="";//公司名称
	 private String abbreviation="";//公司简称 
	 private String contacts=""; //公司联系人
	 private String duty ;//职务
	 private String  sex="";//性别
	 private String  telephone=""; //公司电话
	 private String  mobile=""; //手机号
	 private String  email=""; //邮箱
	 private String  fax=""; //传真
	 private String  companytype=""; //企业类型
	 private String  bullerdate=""; //成立时间
//	 private String  licenseno=""; //营业执照号
//	 private Integer  dimensions=0;//公司规模
//	 private String  logopath=""; //公司logo
	 private String  province;//所在地区：省
	 private String  city;//所在地区：市
	 private String address;//详细地址
	 private String postcode;//邮政编码
	 private String webaddress;//网址
	 private String  creater=""; //创建人
	 private String	 createdate=""; //创建时间
	 
	 private String attetime="";//认证时间
	 private String attstuts ="";//认证状态
	 
	public String getAttetime() {
		return attetime;
	}
	public void setAttetime(String attetime) {
		this.attetime = attetime;
	}
	public String getAttstuts() {
		return attstuts;
	}
	public void setAttstuts(String attstuts) {
		this.attstuts = attstuts;
	}
	public long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(long companyid) {
		this.companyid = companyid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCompanytype() {
		return companytype;
	}
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
	public String getBullerdate() {
		return bullerdate;
	}
	public void setBullerdate(String bullerdate) {
		this.bullerdate = bullerdate;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getWebaddress() {
		return webaddress;
	}
	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
 
	 
	 
}

package com.ipav.system.entity;

import java.io.Serializable;
import java.util.List;

public class IpavnoticeEntity implements Serializable {
	private static final long serialVersionUID = -6103121930885433122L;

	private Long id;// 公告ID
	private String nostr;// 公告编号字符,'如【2014】第{0}号'
	private String noindex;// 公告编号数字,'001'
	private String userid;// 发表公告的用户ID
	private String username;// 发表公告的用户名
	private String issuedid;// 发表公告签发人id
	private String issuedname;// 发表公告签发人名
	private String orgid;// 发表公告用户所在的部门
	private String orgname;// 发表公告用户所在的组织名
	private Long typeid;// 公告类型
	private String title;// 公告标题
	private String content;// 公告全部html内容
	private String contentText;// 公告文本内容
	private String oldfilename;// 附件原文件名
	private String newfilename;// 附件新文件名
	private String filepath;// 附件实际存储地址
	private String createdate;// 公告创建时间
	private Integer iscomment;// 是否允许评论
	private Integer istop;// 是否允许置顶
	private String istime;// 是否允许定时发布
	private String remindtype;// 提醒方式
	private Integer istrue;// 是否发布
	private Integer isdelete;// 数据是否删除
	private List<IpavnoticeSendedEntity> sendeds;//公告接收人集合

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNostr() {
		return nostr;
	}

	public void setNostr(String nostr) {
		this.nostr = nostr;
	}

	public String getNoindex() {
		return noindex;
	}

	public void setNoindex(String noindex) {
		this.noindex = noindex;
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

	public String getIssuedid() {
		return issuedid;
	}

	public void setIssuedid(String issuedid) {
		this.issuedid = issuedid;
	}
	
	public String getIssuedname() {
		return issuedname;
	}

	public void setIssuedname(String issuedname) {
		this.issuedname = issuedname;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public Long getTypeid() {
		return typeid;
	}

	public void setTypeid(Long typeid) {
		this.typeid = typeid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getOldfilename() {
		return oldfilename;
	}

	public void setOldfilename(String oldfilename) {
		this.oldfilename = oldfilename;
	}

	public String getNewfilename() {
		return newfilename;
	}

	public void setNewfilename(String newfilename) {
		this.newfilename = newfilename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public Integer getIscomment() {
		return iscomment;
	}

	public void setIscomment(Integer iscomment) {
		this.iscomment = iscomment;
	}

	public Integer getIstop() {
		return istop;
	}

	public void setIstop(Integer istop) {
		this.istop = istop;
	}

	public String getIstime() {
		return istime;
	}

	public void setIstime(String istime) {
		this.istime = istime;
	}

	public String getRemindtype() {
		return remindtype;
	}

	public void setRemindtype(String remindtype) {
		this.remindtype = remindtype;
	}

	public Integer getIstrue() {
		return istrue;
	}

	public void setIstrue(Integer istrue) {
		this.istrue = istrue;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public List<IpavnoticeSendedEntity> getSendeds() {
		return sendeds;
	}

	public void setSendeds(List<IpavnoticeSendedEntity> sendeds) {
		this.sendeds = sendeds;
	}


}

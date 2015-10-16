package com.ipav.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * creater Jerry All right reserved. Created on 2014年12月5日 下午14:51:11
 * 上海天道启科电子有限公司
 */
public class IpavsayEntity implements Serializable {
	private static final long serialVersionUID = 5459256261275501069L;

	private Long sayid;// 说说ID
	private String sayuserid;// 发表说说用户编号
	private String sayuserimg = "";// 发表说说用户头像
	private String sayusername = "";// 发表说说用户姓名
	private String saycontent = "";// 说说内容
	private String saydate = "";// 说说创建时间
	private Integer permission;// 说说可见性范围
	private List<IpavcommentEntity> comments=new ArrayList<IpavcommentEntity>();// 评论集合
	private List<String> images =new ArrayList<String>();// 图片集合
	private String sarfile;// 说说附件

	public Long getSayid() {
		return sayid;
	}

	public void setSayid(Long sayid) {
		this.sayid = sayid;
	}

	public String getSayuserid() {
		return sayuserid;
	}

	public void setSayuserid(String sayuserid) {
		this.sayuserid = sayuserid;
	}

	public String getSayusername() {
		return sayusername;
	}

	public void setSayusername(String sayusername) {
		this.sayusername = sayusername;
	}

	public String getSaycontent() {
		return saycontent;
	}

	public void setSaycontent(String saycontent) {
		this.saycontent = saycontent;
	}

	public String getSaydate() {
		return saydate;
	}

	public void setSaydate(String saydate) {
		this.saydate = saydate;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public List<IpavcommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<IpavcommentEntity> comments) {
		this.comments = comments;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public static void main(String[] args) {
		IpavsayEntity s = new IpavsayEntity();
		// s.setSayusername(null);
		System.out.println(s.getSayusername());
	}

	public String getSayuserimg() {
		return sayuserimg;
	}

	public void setSayuserimg(String sayuserimg) {
		this.sayuserimg = sayuserimg;
	}

	public String getSarfile() {
		return sarfile;
	}

	public void setSarfile(String sarfile) {
		this.sarfile = sarfile;
	}
	
	
	
}

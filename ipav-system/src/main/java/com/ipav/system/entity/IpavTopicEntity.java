package com.ipav.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IpavTopicEntity implements Serializable{

    /**
	 * @author Aliens
	 */
	private static final long serialVersionUID = 1L;
	private Integer tid;  // 话题帖子 id 主键	
    private String userid; // 用户 id
    private String username;//帖子用户名
    private String uimageUrl;//帖子用户头像路径
	private Integer topictype; // 话题类别 1 表示 我的需求 2 表示 改进意见  3 表示 其他反馈
	private String topictitle = ""; // 话题标题
	private String topiccontent; // 话题内容
	private String contacttype; // 联系方式
	private String createtime; // 创建时间
	private String lastposttime; // 最后一次评论的时间		
	private Integer isshare = 0; // 是否公开 0 表示不公开  1表示公开  默认为 0
	private Integer isdelete = 0; // 数据库状态  默认为 0   为1 则表示删除  
    private List<IpavTopicImageEntity> images 
             =new ArrayList<IpavTopicImageEntity>();//  图片集合
 
	 
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	 
	public Integer getTopictype() {
		return topictype;
	}
	public void setTopictype(Integer topictype) {
		this.topictype = topictype;
	}
	public String getTopictitle() {
		return topictitle;
	}
	public void setTopictitle(String topictitle) {
		this.topictitle = topictitle;
	}
	public String getTopiccontent() {
		return topiccontent;
	}
	public void setTopiccontent(String topiccontent) {
		this.topiccontent = topiccontent;
	}
	public String getContacttype() {
		return contacttype;
	}
	public void setContacttype(String contacttype) {
		this.contacttype = contacttype;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLastposttime() {
		return lastposttime;
	}
	public void setLastposttime(String lastposttime) {
		this.lastposttime = lastposttime;
	}
	public Integer getIsshare() {
		return isshare;
	}
	public void setIsshare(Integer isshare) {
		this.isshare = isshare;
	}
 
	public Integer getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	public List<IpavTopicImageEntity> getImages() {
		return images;
	}
	public void setImages(List<IpavTopicImageEntity> images) {
		this.images = images;
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
	public String getUimageUrl() {
		return uimageUrl;
	}
	public void setUimageUrl(String uimageUrl) {
		this.uimageUrl = uimageUrl;
	}
	 
 

}

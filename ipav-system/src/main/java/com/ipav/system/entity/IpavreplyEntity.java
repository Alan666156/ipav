package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry All right reserved. Created on 2014年12月5日 下午14:51:11
 * 上海天道启科电子有限公司
 */
public class IpavreplyEntity implements Serializable {
	private static final long serialVersionUID = -8639619568365670168L;

	private Long replyid;// 回复ID
	private Long commentid;// 评论ID
	private String replyuserimg;// 回复用户头像
	private String replyuserid;// 回复用户ID
	private String replyusername = "";// 回复用户姓名
	private String bereplyid;// 被回复用户ID
	private String bereplyname;// 被回复用户ID
	private String replycontent = "";// 回复内容
	private String replydate = "";// 回复时间

	public Long getReplyid() {
		return replyid;
	}

	public void setReplyid(Long replyid) {
		this.replyid = replyid;
	}

	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public String getReplyuserid() {
		return replyuserid;
	}

	public void setReplyuserid(String replyuserid) {
		this.replyuserid = replyuserid;
	}

	public String getReplyusername() {
		return replyusername;
	}

	public void setReplyusername(String replyusername) {
		this.replyusername = replyusername;
	}

	public String getBereplyid() {
		return bereplyid;
	}

	public void setBereplyid(String bereplyid) {
		this.bereplyid = bereplyid;
	}

	public String getBereplyname() {
		return bereplyname;
	}

	public void setBereplyname(String bereplyname) {
		this.bereplyname = bereplyname;
	}

	public String getReplycontent() {
		return replycontent;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}

	public String getReplydate() {
		return replydate;
	}

	public void setReplydate(String replydate) {
		this.replydate = replydate;
	}

	public String getReplyuserimg() {
		return replyuserimg;
	}

	public void setReplyuserimg(String replyuserimg) {
		this.replyuserimg = replyuserimg;
	}
	

}

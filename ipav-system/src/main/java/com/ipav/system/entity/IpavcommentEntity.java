package com.ipav.system.entity;

import java.io.Serializable;
import java.util.List;

/**
 * creater Jerry All right reserved. Created on 2014年12月5日 下午14:51:11
 * 上海天道启科电子有限公司
 */
public class IpavcommentEntity implements Serializable {
	private static final long serialVersionUID = 5459256261275501069L;

	private Long commentid;// 评论ID
	private Integer actiontype;// 活动类型1:说说 2公告 3帖子
	private Long actionid;// 活动ID
	private String commuserid = "";// 评论用户ID
	private String commuserimg = "";// 评论用户头像
	private String commusername = "";// 评论用户名
	private Integer commtype;// 评论类型(0:代表赞;1:代表对说说的评论)
	private String commcontent = "";// 评论内容
	private String commdate = "";// 评论时间
	private Integer isdelete;// 数据有效性(业务逻辑上),0:存在;1:删除不存在
	private List<IpavreplyEntity> replys;

	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public Integer getActiontype() {
		return actiontype;
	}

	public void setActiontype(Integer actiontype) {
		this.actiontype = actiontype;
	}

	public Long getActionid() {
		return actionid;
	}

	public void setActionid(Long actionid) {
		this.actionid = actionid;
	}

	public String getCommuserid() {
		return commuserid;
	}

	public void setCommuserid(String commuserid) {
		this.commuserid = commuserid;
	}

	public String getCommusername() {
		return commusername;
	}

	public void setCommusername(String commusername) {
		this.commusername = commusername;
	}

	public Integer getCommtype() {
		return commtype;
	}

	public void setCommtype(Integer commtype) {
		this.commtype = commtype;
	}

	public String getCommcontent() {
		return commcontent;
	}

	public void setCommcontent(String commcontent) {
		this.commcontent = commcontent;
	}

	public String getCommdate() {
		return commdate;
	}

	public void setCommdate(String commdate) {
		this.commdate = commdate;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public List<IpavreplyEntity> getReplys() {
		return replys;
	}

	public void setReplys(List<IpavreplyEntity> replys) {
		this.replys = replys;
	}

	public String getCommuserimg() {
		return commuserimg;
	}

	public void setCommuserimg(String commuserimg) {
		this.commuserimg = commuserimg;
	}

}

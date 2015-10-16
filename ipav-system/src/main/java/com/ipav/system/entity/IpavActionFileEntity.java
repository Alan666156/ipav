package com.ipav.system.entity;

import java.io.Serializable;

/**
 * creater Jerry All right reserved. Created on 2014年11月21日 下午2:52:49
 * 上海天道启科电子有限公司
 */
public class IpavActionFileEntity implements Serializable {
	private static final long serialVersionUID = -5002098730621021623L;

	private Long fileid;//文件id
	private String actionid;//活动id 说说/公告/帖子id
	private int actiontype;//1说说 2公告 3帖子4主题图片(app端)5说说附件
	private String filename = "";//文件名
	private String filepath = "";//文件路径
	private String createdate = "";//创建时间
	public Long getFileid() {
		return fileid;
	}
	public void setFileid(Long fileid) {
		this.fileid = fileid;
	}
	public String getActionid() {
		return actionid;
	}
	public void setActionid(String actionid) {
		this.actionid = actionid;
	}
 
	public int getActiontype() {
		return actiontype;
	}
	public void setActiontype(int actiontype) {
		this.actiontype = actiontype;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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

	 

}

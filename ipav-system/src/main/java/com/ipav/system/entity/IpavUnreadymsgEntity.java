package com.ipav.system.entity;
/**
 * 未读消息表
 * @author gongyh
 *
 */
public class IpavUnreadymsgEntity {
	private int id;
	private String actionid;//活动id
	private String userid;//用户id
    private int type;//类型：1：全部动态（同事圈） 2：与我相关（同事圈）
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	 
	public String getActionid() {
		return actionid;
	}
	public void setActionid(String actionid) {
		this.actionid = actionid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}	


}

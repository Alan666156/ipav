package com.ipav.process.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 固定流程类型
 * @author Alan
 *
 * @date 2015年4月7日 上午9:59:37 
 * @version V1.0
 */
public class ProcessType implements Serializable{
	private Integer typeid;		//固定流程类型ID
	private String typeName;	//固定流程类型名称
	private String createUser;  //创建人
	private Date createTime;    //创建时间
	private String typeState;	//状态：启用/禁用/删除(1表示启用，2表示禁用，0表示删除)
	
	
	public ProcessType() {
		super();
	}
	public ProcessType(String typeName, String createUser, Date createTime, String typeState) {
		super();
		this.typeName = typeName;
		this.createUser = createUser;
		this.createTime = createTime;
		this.typeState = typeState;
	}
	
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTypeState() {
		return typeState;
	}
	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}
	
	
}

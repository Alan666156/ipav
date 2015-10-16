package com.ipav.system.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月14日 下午1:48:48	
 * 上海天道启科电子有限公司
 */
public class IpavForm implements Serializable{
	
	private static final long serialVersionUID = 4014284208451991654L;
	public static final String FORM_UNIQ_ID_FIELD_NAME = "_form_uniq_id";
	private String token;
	private Date createTime;

	public IpavForm(String token) {
		this.token = token;
		this.createTime = new Date();
	}
	public IpavForm() {
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getToken() {
		return token;
	}

	public Date getCreateTime() {
		return createTime;
	}
	
	
}

package com.ipav.mobile.param;

import java.util.Calendar;


public class RequestHeader {

	//请求服务码
	private int service;
	
	//请求时间
	private Calendar requestTime;
	
	//设备描述
	private String device;
	
	//app名称
	private String software;
	
	//系统版本
	private String sysVersion;
	
	//软件版本
	private String softVersion;

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}

	public Calendar getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Calendar requestTime) {
		this.requestTime = requestTime;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public String getSoftVersion() {
		return softVersion;
	}

	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
}

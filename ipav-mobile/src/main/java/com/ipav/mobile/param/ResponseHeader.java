package com.ipav.mobile.param;

import java.util.Calendar;

public class ResponseHeader {

	//请求服务码
	private int service;

	//响应时间
	private Calendar responseTime;

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}

	public Calendar getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Calendar responseTime) {
		this.responseTime = responseTime;
	}
}

package com.ipav.mobile.param;

import java.util.Calendar;

public class ResponseParameter<T> {

	//请求头部
	private ResponseHeader header;
	
	//请求正文
	private T body;
	
	public ResponseParameter(int service){
		header=new ResponseHeader();
		header.setService(service);
		header.setResponseTime(Calendar.getInstance());
	}

	public ResponseHeader getHeader() {
		return header;
	}

	public void setHeader(ResponseHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
}

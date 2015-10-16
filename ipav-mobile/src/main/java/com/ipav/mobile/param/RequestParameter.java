package com.ipav.mobile.param;

public class RequestParameter<T> {

	//请求头部
	private RequestHeader header;
	
	//请求正文
	private T body;

	public RequestHeader getHeader() {
		return header;
	}

	public void setHeader(RequestHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
}

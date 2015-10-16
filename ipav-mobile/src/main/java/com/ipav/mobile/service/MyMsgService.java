package com.ipav.mobile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ipav.infoshare.service.IpavMyMsgService;
import com.ipav.mobile.param.RequestParameter;
import com.ipav.mobile.param.ResponseParameter;

@Service
public class MyMsgService {
	@Autowired
	IpavMyMsgService msgservice;
	/**
	 * 查询类型所有未读消息
	 */
	public ResponseParameter searchMsg(RequestParameter param, int service){
		ResponseParameter response=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject();
		String userid=obj.getString("userid");
		body.put("msgList", msgservice.alwaySearchMsg(userid));
		response.setBody(body);
		return response;
	}
	
	/** 
	 *查询单个类型未读信息列表
	 */
	public ResponseParameter searchMsgs(RequestParameter param, int service){
		ResponseParameter response=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject();
		String userid=obj.getString("userid");
		Integer type=obj.getInteger("type");
		body.put("msgList", msgservice.searchMsgs(userid,type));
		response.setBody(body);
		return response;
	 
	}
}

package com.ipav.infoshare.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.ipav.infoshare.service.IpavMyMsgService;
import com.ipav.system.entity.IpavuserEntity;

@Controller
public class IpavmyMsgController {
    @Autowired
	private IpavMyMsgService msgService;
	
    IpavuserEntity curuser=null;	
	private void userSssion(HttpSession session) {
		curuser = (IpavuserEntity) session.getAttribute("curuser"); 
	}
	/**
	 *  轮询查询应用消息
	 * @param userid
	 * @return
	 */
	@RequestMapping(value="/alwaySearchMsg")
	@ResponseBody
	public JSONArray alwaySearchMsg(HttpSession session){
		userSssion(session);
		List list=msgService.alwaySearchMsg(curuser.getUserid());
		JSONArray rtn =(JSONArray) JSONArray.toJSON(list);
		return rtn;
		
	}
	/**
	 *  查询得到未读公告列表
	 * @param userid
	 * @return
	 */
	@RequestMapping(value="/searchMsgs")
	@ResponseBody
	public JSONArray searchNotices(HttpSession session,Integer type){
		userSssion(session);
		List list=msgService.searchMsgs(curuser.getUserid(),type);
		JSONArray rtn =(JSONArray) JSONArray.toJSON(list);
		return rtn;
		
	}
	 
}

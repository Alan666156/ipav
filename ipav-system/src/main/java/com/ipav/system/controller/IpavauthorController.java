package com.ipav.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

/**
 * 权限控制
 * @author 燕龙
 *
 */
@Controller
public class IpavauthorController {

	@RequestMapping(value="/author")
	public String author(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		map.put("warninginfo", request.getAttribute("warninginfo"));
		return "warning";
	}
	
	@RequestMapping(value="/Ajaxauthor")
	public JSONObject Ajaxauthor(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		//map.put("warninginfo", request.getAttribute("warninginfo"));
		JSONObject rtn=new JSONObject();
		rtn.put("warninginfo",request.getAttribute("warninginfo"));
		return rtn;
	}
	
	@RequestMapping(value="/warningInfo")
	public String warning(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		map.put("url", request.getRequestURL().toString().replace("/warningInfo", "/ipav"));
		map.put("warninginfo", request.getParameter("warninginfo").equals("0")?"不能直接访问页面":"登陆超时,请重新登陆");
		return "warninginfo";
	}
}

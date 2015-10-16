package com.ipav.infoshare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavmenuService;
import com.ipav.system.service.IpavroleService;


@Controller(value="infoshare")
public class BaseController {
	@Autowired
	private IpavmenuService menuService;
	@Autowired
	private IpavroleService roleService;
	
	private final String NOTICE_MANAGER_URL="infoshare/notice";//公告管理页
	
	@RequestMapping(value="ipav/notice/noticeManager")
	public String noticeManagerForward(HttpServletRequest request,ModelMap model,HttpSession session){
		String requestPath=request.getServletPath();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("pathStr", requestPath);
		List<Map<String, Object>>  menulist=roleService.queryMenuInfoByPath(param);
		List menuids=new ArrayList<String>();
		if(menulist!=null&&menulist.size()!=0){
			IpavuserEntity user = (IpavuserEntity)session.getAttribute("curuser");
			param.clear();
			param.put("userid", user.getUserid());
			param.put("parentid", menulist.get(0).get("menuid"));
			menuids = menuService.getUserMenuLists(param);
		}
		model.put("menulist",menuids);
		model.put("notice_id", request.getParameter("nid")==null?"":request.getParameter("nid"));
		return NOTICE_MANAGER_URL;
	}

}
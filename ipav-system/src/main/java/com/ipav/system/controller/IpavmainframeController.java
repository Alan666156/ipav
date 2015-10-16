package com.ipav.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavmenuService;
import com.ipav.system.service.IpavroleService;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月19日 下午1:42:39	
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavmainframeController {
	
	@Autowired
	IpavmenuService menuService;
	@Autowired
	private IpavroleService roleService;
	
	/**
	 * 平台页面结构上边页面
	 * @param session
	 * @param menuid
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/system/top")
	public String topFrame(HttpSession session,String menuid,ModelMap map){
		Map parm = new HashMap();
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		parm.put("parentid", menuid);
		parm.put("userid", user.getUserid());
		List topmenu= menuService.getTopMenuList(parm);
		map.put("topmenu", topmenu);
		return "system/top";
	}
	
	/**
	 * 平台页面结构左边页面
	 * @param session
	 * @param op
	 * @param menuid
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/system/left")
	public String leftFrame(HttpSession session,String op,String menuid,ModelMap map){
		Map parm = new HashMap();
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		parm.put("parentid", menuid);
		parm.put("userid", user.getUserid());
		List leftmenu = null;
		String path = "system/left";
		if(StringUtils.isNotEmpty(menuid)){
			 leftmenu= menuService.getTopMenuList(parm);
			 map.put("leftmenu", leftmenu);
		}
		if("ROLE".equals(op)){
			Map mp =new HashMap();
			mp.put("companyid", user.getCompanyid());
			List roles= roleService.getRoleList(mp, null);
			map.put("roles", roles);
			path = "system/leftother";
		}
		return path;
	}
	
	/**
	 * 平台页面结构下边页面
	 * @return
	 */
	@RequestMapping(value="/system/floor")
	public String floorFrame(){
		return "system/floor";
	}
	
	/**
	 * 跳转到index.jsp页面
	 * @return
	 */
	@RequestMapping(value="/system/index")
	public String mianFrame(){
		return "system/index";
	}
}

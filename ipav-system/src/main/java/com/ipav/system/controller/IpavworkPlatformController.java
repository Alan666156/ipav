package com.ipav.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavworkPlatformService;
import com.ipav.system.util.RequestParamToMap;

@Controller
public class IpavworkPlatformController {
	
	@Autowired
	private IpavworkPlatformService workPlatformService;

	@RequestMapping(value="/gotoplatform")
	public String gotoPlatform(){
		return "system/workplatform/platform";
	}
	
	/**
	 * 获取平台列表
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryPlatformList")
	public JSONObject queryPlatformList(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("companyId", curuser.getCompanyid());
		if(param.containsKey("id"))param.put("tip", "tip");
		List<Map<String,Object>> list=null;
		int count=workPlatformService.queryPlatformInfosCount(param);
		if(count>0)
			list=workPlatformService.queryPlatformInfos(param);
		result.put("count", count);
		result.put("list", list);
		return result;
	}
	/**
	 * 查看平台名是否存在
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/platNameIsExist")
	public int platNameIsExist(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("companyId", curuser.getCompanyid());
		param.put("id", param.get("id"));
		param.put("platname", param.get("platname"));	 
		int count=workPlatformService.queryPlatformInfosCount(param);
		return count;
	}
	
	/**
	 * 添加平台
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addPlatform")
	public JSONObject addPlatform(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("companyId", curuser.getCompanyid());
		param.put("updateUser",curuser.getUserid());
		if(param.containsKey("id"))
			result.put("result", this.workPlatformService.updatePlatform(param));
		else
			result.put("result", this.workPlatformService.addPlatform(param));
		return result;
	}
	
	/**
	 * 删除平台
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deletePlatform")
	public JSONObject updateDelStatus(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("updateUser", curuser.getUserid());
		if(param.get("ids").getClass().getSimpleName().equals("String")){
			String[] ids=new String[]{param.get("ids").toString()};
			param.put("ids", ids);
		}
		this.workPlatformService.updateDelStatus(param);
		result.put("result", 1);
		return result;
	}
}

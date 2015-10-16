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
import com.ipav.system.service.IpavlabourCompanyService;
import com.ipav.system.util.RequestParamToMap;

@Controller
public class IpavlabourController {
	@Autowired
	private IpavlabourCompanyService labourCompanyService;
	
	/**
	 * 跳转到劳动合同单位信息页
	 * @return
	 */
	@RequestMapping(value="/gotolabourcompany")
	public String gotoLabourCompany(){
		return "system/labour/labourcompanyinfos";
	}
	
	/**
	 * 获取劳动合同单位列表
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryLabourCompanyList")
	public JSONObject queryLabourCompanyList(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("companyId", curuser.getCompanyid());
		if(param.containsKey("id"))param.put("tip", "tip");
		List<Map<String,Object>> list=null;
		int count=labourCompanyService.queryLabourCompanyInfosCount(param);
		if(count>0)
			list=labourCompanyService.queryLabourCompanyInfos(param);
		result.put("count", count);
		result.put("list", list);
		return result;
	}
	
	/***
	 * 合同单位名是否重名
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/LabouNameIsExist")
	public int LabouNameIsExist(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("companyId", curuser.getCompanyid());
		param.put("belong_name",param.get("belong_name"));
		param.put("id",param.get("id"));
		int count=labourCompanyService.queryLabourCompanyInfosCount(param);		 
		return count;
	}
	
	/**
	 * 添加劳动合同单位
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addLabourCompany")
	public JSONObject addLabourCompany(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("companyId", curuser.getCompanyid());
		param.put("updateUser",curuser.getUserid());
		if(param.containsKey("id"))
			result.put("result", this.labourCompanyService.updateLabourCompany(param));
		else
			result.put("result", this.labourCompanyService.addLabourCompany(param));
		return result;
	}
	
	/**
	 * 删除劳动合同单位
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteLabourCompany")
	public JSONObject updateDelStatus(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("updateUser", curuser.getUserid());
		if(param.get("ids").getClass().getSimpleName().equals("String")){
			String[] ids=new String[]{param.get("ids").toString()};
			param.put("ids", ids);
		}
		this.labourCompanyService.updateDelStatus(param);
		result.put("result", 1);
		return result;
	}
	
	
}

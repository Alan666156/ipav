package com.ipav.system.controller;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavorgEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavdutyService;
import com.ipav.system.service.IpavlabourCompanyService;
import com.ipav.system.service.IpavmsgService;
import com.ipav.system.service.IpavorgService;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.service.IpavworkPlatformService;
import com.ipav.system.thread.UserImportSuccessTask;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.ExcelTools;
import com.ipav.system.util.ExcelView;
import com.ipav.system.util.MessageUtil;
import com.ipav.system.util.PwdUtil;
import com.ipav.system.util.RequestParamToMap;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月24日 下午1:29:20	
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavuserController {
	@Autowired
	private IpavuserService userService;
	@Autowired
	private IpavdutyService dutyService;
	
	@Autowired
	private IpavworkPlatformService workPlatformService;
	
	@Autowired
	private IpavlabourCompanyService labourCompanyService;
	
	@Autowired
	private IpavorgService orgService;
	
	@Autowired
	private MessageUtil messageSevice;
	
	@Autowired
	private IpavmsgService msgService;
	
	
	/*@RequestMapping(value="/userlist")
	public String queryUserList(HttpSession session,IpavuserEntity user,ModelMap map){
		Map parm = new HashMap();
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		parm.put("companyid", curuser.getCompanyid());
		parm.put("orgid", user.getOrgid());
		parm.put("username", user.getUsername());
		parm.put("valflg", user.getValflg());
		List users= userService.getUserList(parm);
		map.put("userlist", users);
		map.put("queryuser", user);
		return "system/user/userlist";
	}*/
	/**
	 * 查询用户列表
	 * @param session
	 * @param user
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/userlist")
	public String queryUserList(HttpSession session,IpavuserEntity user,ModelMap map){
		return "system/user/userlist";
	}
	/**
	 * 查询用户信息集合
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userinfos")
	public JSONObject queryUserList(HttpSession session,HttpServletRequest request){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		result.put("userid", curuser.getUserid());
		param.put("companyid", curuser.getCompanyid());
		int count=this.userService.queryUserCount(param);
		result.put("counts", count);
		if(count>0)
			result.put("userlist", this.userService.queryUserList(param));
		result.put("valflg", param.get("valflg"));
		return result;
	}
	/**
	 * 添加员工准备参数
	 * @param session
	 * @param mdel
	 * @return
	 */
	@RequestMapping(value="/gotoadduser")
	public String gotoAddUser(HttpSession session,ModelMap mdel){
		Map<String, Object> param = new HashMap();
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("companyid", curuser.getCompanyid());
//		param.put("companyid", companyid);
		String userno = userService.getMaxUserNo(param);
		mdel.put("userno", userno);
		param.put("status", ContentUtil.DEFUAL_VALDLF_YES);
		List<Map<String,Object>> dutys=this.dutyService.queryDutyList(param);
		mdel.put("dutys",dutys);
		return "system/user/adduser";
	}
	
//	/**
//	 * 添加员工
//	 * @param user
//	 * @param oper
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/addUser")
//	public String addUser(IpavuserEntity user,String oper,ModelMap mdel)throws Exception{
//		String path="";
//		userService.addUser(user);
//		if(StringUtils.isNotEmpty(oper)&&ContentUtil.DEFUAL_AGAIN.equals(oper)){
//			Map<String, Object> param = new HashMap();
//			param.put("companyid", user.getCompanyid());
//			String userno = userService.getMaxUserNo(param);
//			mdel.put("userno", userno);
//			param.put("status", ContentUtil.DEFUAL_VALDLF_YES);
//			List<Map<String,Object>> dutys=this.dutyService.queryDutyList(param);
//			mdel.put("dutys",dutys);
//			path = "system/user/adduser";
//		}else{
//			path = "forward:/userlist" ;
//		}
//		return path;
//	}
	
	/**
	 * 添加员工
	 * @param user
	 * @param oper
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addUser")
	public String addUser(IpavuserEntity user,String oper,ModelMap mdel,HttpServletRequest request,HttpSession session)throws Exception{
		String path="";
		Map<String,Object> userDutyParam=new HashMap<String, Object>();
		IpavuserEntity u=(IpavuserEntity) session.getAttribute("curuser");
		userDutyParam.put("updateUser", u.getUserid());
		userDutyParam.put("status", 1);
		Map<String, Object> param = RequestParamToMap.convert(request);
		List<Map<String,Object>> userDutyList=new ArrayList<Map<String,Object>>();
		Map<String,Object> tmp=new HashMap<String, Object>();
		 IpavorgEntity org= orgService.queryOrgnoTo0Bycompanyid(user.getCompanyid()+"");
		  if(param.get("orgid").equals("")){
		    //添加默认组织
		   
			tmp.put("orgId", org.getOrgid());
		  }else{
			  tmp.put("orgId", param.get("orgid"));
		  }
			tmp.put("dutyId", param.get("duty").equals("")?"-1":param.get("duty"));
			tmp.put("isLeader", param.get("chefflg"));
			tmp.put("type", 0);
			userDutyList.add(tmp);
			if(user.getOrgid()==null){
				user.setOrgid(org.getOrgid());
			}
		if(param.containsKey("partTimeOrg")&&!param.get("partTimeOrg").toString().equals("")){
			String[] partTimeOrg=param.get("partTimeOrg").toString().split(",");
			String[] partTimeDuty=param.get("partTimeDuty").toString().split(",");
			String[] partTimeCock=param.get("partTimeCock").toString().split(",");
			for(int i=0;i<partTimeOrg.length;i++){
				tmp=new HashMap<String, Object>();
				tmp.put("orgId", partTimeOrg[i]);
				tmp.put("dutyId", partTimeDuty[i]);
				tmp.put("isLeader",partTimeCock[i]);
				tmp.put("type", 1);
				userDutyList.add(tmp);
			}
		}
		user.setValflg("1");
		if(userDutyList.size()>0)
			userDutyParam.put("list", userDutyList);
		//userService.addUser(user);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file=multipartRequest.getFile("picfile");
		userService.addUser(user, userDutyParam,file,u.getUserid());
		
		if(StringUtils.isNotEmpty(oper)&&ContentUtil.DEFUAL_AGAIN.equals(oper)){
			Map<String, Object> tmpParam = new HashMap();
			tmpParam.put("companyid", user.getCompanyid());
			String userno = userService.getMaxUserNo(tmpParam);
			mdel.put("userno", userno);
//			param.put("status", ContentUtil.DEFUAL_VALDLF_YES);
//			List<Map<String,Object>> dutys=this.dutyService.queryDutyList(param);
//			mdel.put("dutys",dutys);
			path = "system/user/adduser";
		}else{
			Map<String,Object> parm=new HashMap<String, Object>();
			parm.put("companyid", u.getCompanyid());
			parm.put("orgid", user.getOrgid());
			parm.put("username", user.getUsername());
			parm.put("valflg", user.getValflg());
			List users= userService.getUserList(parm);
			mdel.put("userlist", users);
//			mdel.put("queryuser", user);
			path = "forward:/userlist" ;
		}
		return path;
	}
	
	/*@RequestMapping(value="/gotomodifyuser")
	public String gotomodifyUser(String userid,@RequestParam(value="oper",required=true)String oper,ModelMap map){
		IpavuserEntity user= userService.queryUserId(userid);
		map.put("user", user);
		Map sqlMap  =new HashMap<String,Object>();
		if(user.getDuty()!=null){
		sqlMap.put("id", user.getDuty());
		List<Map<String,Object>> dutyList= this.dutyService.queryDutyList(sqlMap);
		if(dutyList.size()>0)map.put("dutyName",dutyList.get(0).get("duty_name"));
		else  map.put("dutyName","暂无");
		}else{
			map.put("dutyName","暂无");
		}
		String path = "";
		if(ContentUtil.DEFUAL_UPDATE.equals(oper)){
			Map<String, Object> param = new HashMap();
			param.put("companyid", user.getCompanyid());
			param.put("status", ContentUtil.DEFUAL_VALDLF_YES);
			List<Map<String,Object>> dutys=this.dutyService.queryDutyList(param);
			map.put("dutys",dutys);
			
			path = "system/user/modifyuser";
		}
		if(ContentUtil.DEFUAL_VIEW.equals(oper)){
			path = "system/user/userview";
		}
		return path;
	}*/
	
	/***
	 * 修改员工准备参数
	 * @param userid
	 * @param oper
	 * @param map
	 * @return
	 */
	
	@RequestMapping(value="/gotomodifyuser")
	public String gotomodifyUser(String userid,@RequestParam(value="oper",required=true)String oper,ModelMap map){
		IpavuserEntity user= userService.queryUserId(userid);
		user.setPicpath(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+user.getPicpath());
		map.put("user", user);
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("userId", userid);
		List<Map<String,Object>> postInfos=userService.queryUserPostInfo(param);
		String partTimeOrg="";
		String partTimeOrgName="";
		String partTimeDuty="";
		String partTimeCock="";
		String partTimeDesc="";
		 IpavorgEntity org= orgService.queryOrgnoTo0Bycompanyid(user.getCompanyid()+"");
		if(postInfos!=null&&postInfos.size()>0){
			for(Map<String,Object> tmp:postInfos){
				if(tmp.get("type").toString().equals("0")){
					if(org.getOrgid().longValue()==((Long)tmp.get("org_id"))){ 
						map.put("orgId", "");
						map.put("orgName","");
					}else{
						map.put("orgId", tmp.get("org_id"));
						map.put("orgName", tmp.get("orgname"));
					}
					map.put("isLeader", tmp.get("is_leader"));
					map.put("dutyId", tmp.get("duty_id"));
					map.put("dutyName", tmp.get("duty_name"));
				}else if(tmp.get("type").toString().equals("1")){
					partTimeOrg+=tmp.get("org_id").toString()+",";
					partTimeOrgName+=tmp.get("orgname").toString()+",";
					partTimeDuty+=tmp.get("duty_id").toString()+",";
					partTimeCock+=tmp.get("is_leader").toString()+",";
					partTimeDesc+="<p><span>"+tmp.get("orgname")+"</span>—<span>"+tmp.get("duty_name")+"</span>—<span>"+(tmp.get("is_leader").toString().equals("")?"":(tmp.get("is_leader").toString().equals("1")?"(是)":"(否)"))+"</span></p>";
				}
			}
			if(!partTimeOrg.equals("")){
				partTimeOrg=partTimeOrg.substring(0,partTimeOrg.length()-1);
				partTimeOrgName=partTimeOrgName.substring(0,partTimeOrgName.length()-1);
				partTimeDuty=partTimeDuty.substring(0,partTimeDuty.length()-1);
				partTimeCock=partTimeCock.substring(0,partTimeCock.length()-1);
			}
		}
		map.put("partTimeOrg", partTimeOrg);
		map.put("partTimeOrgName", partTimeOrgName);
		map.put("partTimeDuty", partTimeDuty);
		map.put("partTimeCock", partTimeCock);
		map.put("partTimeDesc", partTimeDesc);
		String path = "";
		param.clear();
		param.put("companyId", user.getCompanyid());
		List<Map<String,Object>> platformList=workPlatformService.queryPlatformInfos(param);
		List<Map<String,Object>> labourList=labourCompanyService.queryLabourCompanyInfos(param);
		if(ContentUtil.DEFUAL_UPDATE.equals(oper)){
			param.clear();
			param.put("companyid", user.getCompanyid());
			param.put("status", ContentUtil.DEFUAL_VALDLF_YES);
			/*List<Map<String,Object>> dutys=this.dutyService.queryDutyList(param);
			map.put("dutys",dutys);*/
			param.clear();
			map.put("platformlist", platformList);
			map.put("labourList", labourList);
			if(user!=null && !user.getDirector().equals("")){
				IpavuserEntity user1= userService.queryUserId(user.getDirector());
				if(user1!=null)
				map.put("directorName",user1==null?"":user1.getUsername());
			}
			path = "system/user/modifyuser";
		}
		if(ContentUtil.DEFUAL_VIEW.equals(oper)){
			String platform="";
			String labour="";
			if(user.getPlatform()>0&&platformList!=null){
				for(Map<String,Object> m:platformList){
					if(user.getPlatform()==Integer.parseInt(m.get("id").toString())){
						platform=m.get("platname").toString();
						break;
					}
				}
			}
			if(user.getLabourBelong()>0&&labourList!=null){
				for(Map<String,Object> m:labourList){
					if(user.getLabourBelong()==Integer.parseInt(m.get("id").toString())){
						labour=m.get("belong_name").toString();
						break;
					}
				}
			}
			String partTimeDep="";
			String partTimeJob="";
			if(!partTimeDesc.equals("")){
				String[] arr=partTimeDesc.split(",");
				String[] tmpArr=null;
				for(String tmp:arr){
					tmpArr=tmp.split("-");
					partTimeDep+=tmpArr[0]+",";
					partTimeJob+=tmpArr[1]+",";
				}
				if(!partTimeDep.equals("")){
					partTimeDep=partTimeDep.substring(0, partTimeDep.length()-1);
					partTimeJob=partTimeJob.substring(0, partTimeJob.length()-1);
				}
			}
			map.put("partTimeDep", partTimeDep);
			map.put("partTimeJob", partTimeJob);
			map.put("platform", platform);
			map.put("labour", labour);
			if(user!=null && !user.getDirector().equals("")){
				IpavuserEntity user1= userService.queryUserId(user.getDirector());
				if(user1!=null)
				map.put("directorName",user1==null?"":user1.getUsername());
				 
			}
			path = "system/user/userview";
		}
		return path;
	}
	
//	@RequestMapping(value="/modifyuser")
//	public String modifyUser(IpavuserEntity user,ModelMap map)throws Exception{
//		userService.updateUser(user);
//		return "forward:/userlist" ;
//	}
	/***
	 * 修改员工
	 * @param user
	 * @param map
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/modifyuser")
	public String modifyUser(IpavuserEntity user,ModelMap map,HttpServletRequest request,HttpSession session)throws Exception{
		Map<String,Object> userDutyParam=new HashMap<String, Object>();
		IpavuserEntity u=(IpavuserEntity) session.getAttribute("curuser");
		userDutyParam.put("updateUser", u.getUserid());
		userDutyParam.put("status", 1);
		Map<String, Object> param = RequestParamToMap.convert(request);
		List<Map<String,Object>> userDutyList=new ArrayList<Map<String,Object>>();
		Map<String,Object> tmp=new HashMap<String, Object>();
		/*if(param.containsKey("orgid")&&!param.get("orgid").toString().equals("")
				&&param.containsKey("duty")&&!param.get("duty").toString().equals("")
				&&param.containsKey("chefflg")&&!param.get("chefflg").toString().equals("")){*/
		  if(("").equals(param.get("orgid"))){
			    //添加默认组织
			    IpavorgEntity org= orgService.queryOrgnoTo0Bycompanyid(user.getCompanyid()+"");
				tmp.put("orgId", org.getOrgid());
			  }else{
				  tmp.put("orgId", param.get("orgid"));
			  }
		   // tmp.put("orgId", param.get("orgid"));
			tmp.put("dutyId", ("").equals(param.get("duty"))?"-1":param.get("duty"));
			tmp.put("isLeader", param.get("chefflg"));
			tmp.put("type", 0);
			userDutyList.add(tmp);
		//}
		if(param.containsKey("partTimeOrg")&&!param.get("partTimeOrg").toString().equals("")){
			String[] partTimeOrg=param.get("partTimeOrg").toString().split(",");
			String[] partTimeDuty=param.get("partTimeDuty").toString().split(",");
			String[] partTimeCock=param.get("partTimeCock").toString().split(",");
			for(int i=0;i<partTimeOrg.length;i++){
				tmp=new HashMap<String, Object>();
				tmp.put("orgId", partTimeOrg[i]);
				tmp.put("dutyId", partTimeDuty[i]);
				tmp.put("isLeader",partTimeCock[i]);
				tmp.put("type", 1);
				userDutyList.add(tmp);
			}
		}
		user.setValflg("1");
		userDutyParam.put("list", userDutyList);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file=multipartRequest.getFile("picfile");
		IpavuserEntity uu=userService.queryUserId(user.getUserid());
		user.setPicpath(uu.getPicpath());
		userService.updateUser(user, userDutyParam,file);
		return "forward:/userlist" ;
	}
	/***
	 *  禁用启用员工
	 * @param myvalflg
	 * @param req
	 * @param rsp
	 * @return
	 */
	@RequestMapping(value="/setuserVal")
	public String setuserVal(@RequestParam(value="myvalflg",required=true)String myvalflg,HttpServletRequest req,HttpServletResponse rsp){
		String[] userids = req.getParameterValues("userids");
		/*Map map = new HashMap();
		map.put("userids", userids);
		map.put("valflg", myvalflg);*/
		userService.setuserVal(myvalflg,userids);
		return "forward:/userlist";
	} 
	/**
	 * 删除员工
	 * @param req
	 * @param rsp
	 * @return
	 */
	@RequestMapping(value="/delUsers")
	public String delUsers(HttpServletRequest req,HttpServletResponse rsp){
		String[] userids = req.getParameterValues("userids");
		userService.delUser(userids);
		return "forward:/userlist";
	}
	
//	@RequestMapping(value="/userExport")
//	public ModelAndView exprotUserExcel(HttpSession session,String oper,String orgid,ModelMap model){
//		List<String> title = new ArrayList<String>();
//		if("user".equals(oper)){
//			title.add("员工编号");
//			title.add("员工姓名");
//			title.add("性别");
//			title.add("机构名称");
//			title.add("手机号");
//			title.add("电子邮箱");
//			title.add("员工状态");
//			title.add("所属工作平台");
//			title.add("办公电话");
//			title.add("办公地址");
//			title.add("是否部门最高职务");
//			IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
//			Map map = new HashMap();
//			map.put("orgid", orgid);
//			map.put("companyid",curuser.getCompanyid());
//			List users= userService.getUserListForExport(map);
//			
//			model.put("content", users);
//		}else{
//			title.add("姓名");
//			title.add("用户注册信息(邮箱或手机号)");
//			title.add("部门编号");
//			title.add("职务编号");
//			title.add("是否部门最高职务");
//			title.add("手机号码");
//			title.add("电子邮箱");
//			title.add("所属工作平台(编号)");
//			title.add("办公电话");
//			title.add("办公地址");
//		}
//		model.put("title", title);
//		return new ModelAndView(new UserExcelView(), model);
//	} 
	/**
	 * 员工导出
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/userExport")
	public ModelAndView exprotUserExcel(HttpServletRequest request,HttpSession session,ModelMap model){
		List<String> title = new ArrayList<String>();
		Map<String,Object> param=RequestParamToMap.convert(request);
		String oper=param.containsKey("oper")?param.get("oper").toString():"";
		if("user".equals(oper)){
			title.add("员工编号");
			title.add("员工姓名");
			title.add("用户注册信息");
			title.add("部门");
			title.add("职务");
			title.add("是否部门最高职务");
			title.add("兼职部门及职务");
			title.add("手机号码");
			title.add("电子邮箱");
			title.add("所属工作平台");
			title.add("工作电话");
			title.add("办公地址");
			title.add("劳动合同签订单位");
			IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
			param.put("companyId",curuser.getCompanyid());
			List<Map<String,Object>> infos=userService.queryExportUserList(param);
			List<List<String>> contentList=null;
			if(infos!=null&&infos.size()>0){
				int length=infos.size();
				List<String> contents=null;
				String prevno="";
				Map<String,Object> tmp=null;
				contentList=new ArrayList<List<String>>();
				for(int i=0;i<length;i++){
					if(!infos.get(i).get("userno").toString().equals(prevno)){
						if(!prevno.equals(""))
							contentList.add(contents);	
						prevno=infos.get(i).get("userno").toString();
						contents=new ArrayList<String>();
						contents.add(infos.get(i).get("userno").toString());
						contents.add(infos.get(i).get("username").toString());
						contents.add(infos.get(i).get("reginfo").toString());
						contents.add(infos.get(i).get("orgname").toString());
						contents.add(infos.get(i).get("duty_name").toString());
						contents.add(infos.get(i).get("is_leader").toString());
						contents.add(infos.get(i).get("part_time_info").toString());
						contents.add(infos.get(i).get("mobile").toString());
						contents.add(infos.get(i).get("email").toString());
						contents.add(infos.get(i).get("platname").toString());
						contents.add(infos.get(i).get("phone").toString());
						contents.add(infos.get(i).get("address").toString());
						contents.add(infos.get(i).get("belong_name").toString());
						continue;
					}
					contents.set(6, infos.get(i).get("part_time_info").toString());
//					tmp=list.get(i-1);
//					tmp.put("part_time_info", list.get(i).get("part_time_info"));
//					list.set(i-1, tmp);
//					list.remove(i);
//					--i;
				}
				contentList.add(contents);	
				model.put("content", contentList);
			}
//			Map map = new HashMap();
//			map.put("orgid", orgid);
//			map.put("companyid",curuser.getCompanyid());
//			List users= userService.getUserListForExport(map);
//			model.put("content", users);
		}else{
			title.add("员工姓名");
			title.add("用户注册信息");
//			title.add("部门");
//			title.add("职务");
//			title.add("是否部门最高职务");
//			title.add("兼职部门及职务");
			title.add("手机号码");
			title.add("电子邮箱");
//			title.add("所属工作平台");
//			title.add("工作电话");
//			title.add("办公地址");
//			title.add("劳动合同签订单位");
		}
		model.put("title", title);
//		return new ModelAndView(new UserExcelView(), model);
		return new ModelAndView(new ExcelView("员工信息","员工信息"), model);
	}
	
	/**
	 * 导入的excel进行表内校验
	 * @param contents
	 * @return
	 */
	public JSONObject validate(List<ArrayList<String>> contents){
		JSONObject validateInfo=new JSONObject();
		if(contents.size()==0){
			validateInfo.put("err","导入的文件内容为空");
			return validateInfo;
		}
		List<String> content=contents.get(0);
		if(content.size()!=4){
			validateInfo.put("err","导入文件内容的标题不正确");
			return validateInfo;
		}else{
			String[] title=new String[]{"员工姓名","用户注册信息","手机号码","电子邮箱"};
			for(int i=0;i<4;i++){
				if(!content.get(i).equals(title[i])){
					validateInfo.put("err","导入文件内容的标题不正确");
					return validateInfo;
				}
			}
		}
		if(contents.size()==1){
			validateInfo.put("err","导入的文件内容没有用户信息");
			return validateInfo;
		}
		contents.remove(0);
		List<List<String>> warningList=new ArrayList<List<String>>();
		String email="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		String mobile="^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
		String telephone="\\d{3}-\\d{8}|\\d{4}-\\d{7}";
		String[] partTimeArr=null;
		String[] partTimeInfo=null;
		StringBuffer partTimeContent=null;
		List<String> phoneList=new ArrayList<String>();
		List<String> emailList=new ArrayList<String>();
		boolean flag=false;
		for(int i=0;i<contents.size();i++){
			partTimeContent=new StringBuffer();
			flag=false;
			if(contents.get(i).get(0).equals("")
				||contents.get(i).get(1).equals("")
				||(!contents.get(i).get(1).matches(mobile)&&!contents.get(i).get(1).matches(email))
				||(!contents.get(i).get(2).equals("")&&!contents.get(i).get(2).matches(mobile))
				||(!contents.get(i).get(3).equals("")&&!contents.get(i).get(3).matches(email))
				||(!contents.get(i).get(2).equals(contents.get(i).get(1))&&!contents.get(i).get(3).equals(contents.get(i).get(1)))
				){
				warningList.add(contents.get(i));
				contents.remove(i);
				i--;
				continue;
			}
			if(flag){
				warningList.add(contents.get(i));
				contents.remove(i);
				i--;
				continue;
			}
			if(i<contents.size()-1){
				for(int j=i+1;j<contents.size();j++){
					if(contents.get(i).get(1).equals(contents.get(j).get(1))||contents.get(i).get(1).equals(contents.get(j).get(2))||contents.get(i).get(1).equals(contents.get(j).get(3))){
						warningList.add(contents.get(i));
						warningList.add(contents.get(j));
						contents.remove(i);
						--i;
						break;
					}
					if(!contents.get(i).get(2).equals("")&&(contents.get(i).get(2).equals(contents.get(j).get(1))||contents.get(i).get(2).equals(contents.get(j).get(2)))){
						warningList.add(contents.get(i));
						warningList.add(contents.get(j));
						contents.remove(i);
						--i;
						break;
					}
					if(!contents.get(i).get(3).equals("")&&(contents.get(i).get(3).equals(contents.get(j).get(1))||contents.get(i).get(3).equals(contents.get(j).get(3)))){
						warningList.add(contents.get(i));
						warningList.add(contents.get(j));
						contents.remove(i);
						--i;
						break;
					}
				}
			}
			if(!contents.get(i).get(2).equals(""))
				phoneList.add(contents.get(i).get(2));
			if(!contents.get(i).get(3).equals(""))
				emailList.add(contents.get(i).get(3));
		}
		validateInfo.put("warningList", warningList);
		Map<String,Object> param=new HashMap<String,Object>();
		if(phoneList.size()>0)
			param.put("phoneList", phoneList);
		if(emailList.size()>0)
			param.put("emailList", emailList);
		List<List<String>> existsList=new ArrayList<List<String>>();
		List<Map<String,Object>> list=this.userService.queryExistRegistedUser(param);
		if(list!=null&&list.size()>0){
			for(Map<String,Object> map:list){
				for(int i=contents.size()-1;i>=0;i--){
					if((!contents.get(i).get(2).equals("")&&contents.get(i).get(2).equals(map.get("mobile")))
							||(!contents.get(i).get(3).equals("")&&contents.get(i).get(3).equals(map.get("email")))){
						existsList.add(contents.get(i));
						contents.remove(i);
					}
				}
			}
		}
		validateInfo.put("existsList", existsList);
		return validateInfo;
	}
	
	/**
	 * 导入的excel进行表内校验
	 * @param contents
	 * @param departmentMap
	 * @param dutyMap
	 * @param platformMap
	 * @param labourMap
	 * @return
	 */
	public JSONObject validate(List<ArrayList<String>> contents,Map<String,String> departmentMap,Map<String,String> dutyMap,Map<String,String> platformMap,Map<String,String> labourMap){
		JSONObject validateInfo=new JSONObject();
		if(contents.size()==0){
			validateInfo.put("err","导入的文件内容为空");
			return validateInfo;
		}
		List<String> content=contents.get(0);
		if(content.size()!=12){
			validateInfo.put("err","导入文件内容的标题不正确");
			return validateInfo;
		}else{
			String[] title=new String[]{"员工姓名","用户注册信息","部门","职务","是否部门最高职务","兼职部门及职务","手机号码","电子邮箱","所属工作平台","工作电话","办公地址","劳动合同签订单位"};
			for(int i=0;i<12;i++){
				if(!content.get(i).equals(title[i])){
					validateInfo.put("err","导入文件内容的标题不正确");
					return validateInfo;
				}
			}
		}
		if(contents.size()==1){
			validateInfo.put("err","导入的文件内容没有用户信息");
			return validateInfo;
		}
		contents.remove(0);
//		int length=contents.size();
		List<List<String>> warningList=new ArrayList<List<String>>();
		String email="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		String mobile="^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
		String telephone="\\d{3}-\\d{8}|\\d{4}-\\d{7}";
		String[] partTimeArr=null;
		String[] partTimeInfo=null;
		StringBuffer partTimeContent=null;
		List<String> phoneList=new ArrayList<String>();
		List<String> emailList=new ArrayList<String>();
		boolean flag=false;
		for(int i=0;i<contents.size();i++){
			partTimeContent=new StringBuffer();
			flag=false;
			if((!contents.get(i).get(1).equals(contents.get(i).get(6))&&!contents.get(i).get(1).equals(contents.get(i).get(7)))
				||contents.get(i).get(1).equals("")
				||(!contents.get(i).get(6).equals("")&&!contents.get(i).get(6).matches(mobile))
				||(!contents.get(i).get(7).equals("")&&!contents.get(i).get(7).matches(email))
				||(!contents.get(i).get(9).trim().equals("")&&!contents.get(i).get(9).matches(mobile)&&!contents.get(i).get(9).matches(telephone))
				||(!contents.get(i).get(1).matches(mobile)&&!contents.get(i).get(1).matches(email))
//				||!(contents.get(i).get(2).toString().equals("")&&contents.get(i).get(3).toString().equals("")&&contents.get(i).get(4).toString().equals(""))
//				||(!contents.get(i).get(2).toString().equals("")&&!contents.get(i).get(3).toString().equals("")&&(!contents.get(i).get(4).toString().equals("是")&&!contents.get(i).get(4).toString().equals("否")))
//				||(!contents.get(i).get(2).toString().equals("")&&!departmentMap.containsKey(contents.get(i).get(2)))
//				||(!contents.get(i).get(3).toString().equals("")&&!dutyMap.containsKey(contents.get(i).get(3)))
				||(!contents.get(i).get(2).toString().equals("")&&!departmentMap.containsKey(contents.get(i).get(2)))
				||(!contents.get(i).get(3).toString().equals("")&&!dutyMap.containsKey(contents.get(i).get(3)))
				||(!contents.get(i).get(4).toString().equals("")&&!contents.get(i).get(4).equals("是")&&!contents.get(i).get(4).equals("否"))
				||(!contents.get(i).get(8).toString().equals("")&&!platformMap.containsKey(contents.get(i).get(8)))
				||(!contents.get(i).get(11).toString().equals("")&&!labourMap.containsKey(contents.get(i).get(11)))){
				warningList.add(contents.get(i));
				contents.remove(i);
				i--;
				continue;
			}
			partTimeArr=contents.get(i).get(5).split(",");
			for(int j=0;j<partTimeArr.length;j++){
				if(partTimeArr[j].indexOf("-")==-1)
					continue;
				partTimeInfo=partTimeArr[j].split("-");
				if(partTimeInfo.length!=3
						||(!partTimeInfo[2].equals("是")&&!partTimeInfo[2].equals("否"))
						||!departmentMap.containsKey(partTimeInfo[0])
						||!dutyMap.containsKey(partTimeInfo[1])){
					flag=true;
					break;
				}
				partTimeContent.append(departmentMap.get(partTimeInfo[0])+"-"+dutyMap.get(partTimeInfo[1])+"-"+(partTimeInfo[2].equals("是")?1:0)+",");
			}
			if(flag){
				warningList.add(contents.get(i));
				contents.remove(i);
				i--;
				continue;
			}
			if(i<contents.size()-1){
				for(int j=i+1;j<contents.size();j++){
					if(contents.get(i).get(1).equals(contents.get(j).get(1))||contents.get(i).get(1).equals(contents.get(j).get(6))||contents.get(i).get(1).equals(contents.get(j).get(7))){
						warningList.add(contents.get(i));
						warningList.add(contents.get(j));
						contents.remove(i);
						--i;
						break;
					}
					if(!contents.get(i).get(6).equals("")&&(contents.get(i).get(6).equals(contents.get(j).get(1))||contents.get(i).get(6).equals(contents.get(j).get(6)))){
						warningList.add(contents.get(i));
						warningList.add(contents.get(j));
						contents.remove(i);
						--i;
						break;
					}
					if(!contents.get(i).get(7).equals("")&&(contents.get(i).get(7).equals(contents.get(j).get(1))||contents.get(i).get(7).equals(contents.get(j).get(7)))){
						warningList.add(contents.get(i));
						warningList.add(contents.get(j));
						contents.remove(i);
						--i;
						break;
					}
				}
			}
			if(partTimeContent.length()>0)
				partTimeContent.setLength(partTimeContent.length()-1);
			contents.get(i).add(partTimeContent.toString());
			contents.get(i).add(contents.get(i).get(2).equals("")?"":departmentMap.get(contents.get(i).get(2)));
			contents.get(i).add(contents.get(i).get(3).equals("")?"":dutyMap.get(contents.get(i).get(3)));
			contents.get(i).add(contents.get(i).get(4).equals("是")?"1":"0");
			contents.get(i).add(contents.get(i).get(8).equals("")?"":platformMap.get(contents.get(i).get(8)));
			contents.get(i).add(contents.get(i).get(11).equals("")?"":labourMap.get(contents.get(i).get(11)));
			if(!contents.get(i).get(6).equals(""))
				phoneList.add(contents.get(i).get(6));
			if(!contents.get(i).get(7).equals(""))
				emailList.add(contents.get(i).get(7));
		}
		validateInfo.put("warningList", warningList);
		Map<String,Object> param=new HashMap<String,Object>();
		if(phoneList.size()>0)
			param.put("phoneList", phoneList);
		if(emailList.size()>0)
			param.put("emailList", emailList);
		List<List<String>> existsList=new ArrayList<List<String>>();
		List<Map<String,Object>> list=this.userService.queryExistRegistedUser(param);
		if(list!=null&&list.size()>0){
			for(Map<String,Object> map:list){
				for(int i=contents.size()-1;i>=0;i--){
					if((!contents.get(i).get(6).equals("")&&contents.get(i).get(6).equals(map.get("mobile")))
							||(!contents.get(i).get(7).equals("")&&contents.get(i).get(7).equals(map.get("email")))){
						existsList.add(contents.get(i));
						contents.remove(i);
					}
				}
			}
		}
		validateInfo.put("existsList", existsList);
		return validateInfo;
	}
	/**
	 * 员工导入
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userImport")
	public JSONObject importUserExcel(HttpSession session,
			HttpServletRequest request){
		long start=Calendar.getInstance().getTimeInMillis();
		JSONObject result=new JSONObject();
		List<ArrayList<String>> content=null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile userInfoFile = multipartRequest.getFile("userInfoFile");
		try{
			if (userInfoFile.getOriginalFilename().endsWith(".xls"))
				content = new ExcelTools().readExcel03(userInfoFile
						.getInputStream());
			else
				content = new ExcelTools().readExcel07(userInfoFile
						.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(content==null){
			result.put("err", "导入的文件为空");
		}else{
			IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
			if(content!=null){
				Map<String,Object> param=new HashMap<String, Object>();
				//excel过滤
				JSONObject validateInfo=this.validate(content);
				result.put("validateInfo", validateInfo);
				start=Calendar.getInstance().getTimeInMillis();
				if(content.size()>0){
					List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
					Map<String,Object> userDutyParam=null;
					Map<String,Object> tmp=null;
					IpavuserEntity user=null;
					String userno="";
					IpavorgEntity org=userService.getOrg(curuser);
					for(int i=0;i<content.size();i++){
						user=new IpavuserEntity();
						param.clear();
						param.put("companyid", curuser.getCompanyid());
						userno = userService.getMaxUserNo(param);
						user.setUserno(userno);
						param.clear();
						user.setUsername(content.get(i).get(0));
						user.setRegtype(content.get(i).get(1).equals(content.get(i).get(2))?"M":"E");
						user.setMobile(content.get(i).get(2).equals("")?"":content.get(i).get(2));
						user.setEmail(content.get(i).get(3).equals("")?"":content.get(i).get(3));
						user.setCompanyid(curuser.getCompanyid());
						user.setPhone("");
						user.setAddress("");
						user.setSex("1");
						user.setDuty(0);
						user.setOrgid(org.getOrgid());
						user.setPicpath("man.png");
						userDutyParam=new HashMap<String, Object>();
						userDutyParam.put("updateUser", curuser.getUserid());
						userDutyParam.put("status", 1);
						List<Map<String,Object>> userDutyList=new ArrayList<Map<String,Object>>();
						tmp=new HashMap<String, Object>();
						tmp.put("orgId", org.getOrgid());
						tmp.put("dutyId",0);
						tmp.put("isLeader", 0);
						tmp.put("type", 0);
						userDutyList.add(tmp);
						if(userDutyList.size()>0)
							userDutyParam.put("list", userDutyList);
						try {
							userService.addUser(user, userDutyParam,null,curuser.getUserid(),list);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					UserImportSuccessTask task=new UserImportSuccessTask();
					task.setList(list);
					task.setMessageUtil(messageSevice);
					new Thread(task).start();
				}
				
			}
		}
		return result;
	}
	/**
	 *得到公司的平台维护和合同单位的数据
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/initSelectors")
	public JSONObject initSelectors(HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("companyId", curuser.getCompanyid());
		List<Map<String,Object>> platformList=workPlatformService.queryPlatformInfos(param);
		result.put("platformlist", platformList);
		List<Map<String,Object>> labourList=labourCompanyService.queryLabourCompanyInfos(param);
		result.put("labourList", labourList);
		return result;
	}
	/***
	 * 查询用户信息
	 * @param session
	 * @return
	 */	
	@ResponseBody
	@RequestMapping(value="/queryUserInfo")
	public JSONObject queryUserInfoOfCenter(HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity user=(IpavuserEntity)session.getAttribute("curuser");
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("userId", user.getUserid());
		List<Map<String,Object>> list=this.userService.queryUserInfoOfCenter(param);
		if(list!=null&list.size()>0){
			Map<String,Object> tmp=list.get(0);
			result.put("account", tmp.get("userid"));
			result.put("userName", tmp.containsKey("username")?tmp.get("username"):"");
//			result.put("sex", tmp.containsKey("sex")?(tmp.get("sex").toString().trim().equals("1")?"男":"女"):"");
			result.put("sex", tmp.get("sex"));
			result.put("mobile", tmp.containsKey("mobile")?tmp.get("mobile"):"");
			result.put("email", tmp.containsKey("email")?tmp.get("email"):"");
			result.put("regtype", tmp.get("regtype"));
			result.put("phone", tmp.containsKey("phone")?tmp.get("phone"):"");
			result.put("address", tmp.containsKey("address")?tmp.get("address"):"");
			result.put("picpath", tmp.containsKey("picpath")?(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+tmp.get("picpath")):"");
			result.put("platname", tmp.containsKey("platname")?tmp.get("platname"):"");
			if(tmp.containsKey("type")&&tmp.get("type").toString().trim().equals("0")){
				result.put("org", tmp.get("orgname"));
				result.put("duty", tmp.get("duty_name"));
				result.put("is_leader", tmp.get("is_leader").toString().trim().equals("1")?"是":"否");
				list.remove(0);
			}
			String partTimeDesc="";
			for(Map<String,Object> map:list){
				if(map.containsKey("orgname"))
					partTimeDesc+=map.get("orgname").toString()+"-"+map.get("duty_name").toString()+",";
			}
			if(!partTimeDesc.equals(""))
				partTimeDesc=partTimeDesc.substring(0,partTimeDesc.length()-1);
			result.put("partTimeDesc", partTimeDesc);
		}
		return result;
	}
	
	/**
	 * 修改个人中心信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateUserInfos")
	public JSONObject updateUserInfos(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity user=(IpavuserEntity)session.getAttribute("curuser");
		String rsmessage="更新失败";
		if(user!=null){
			Map<String,Object> param=RequestParamToMap.convert(request);
//			if(param.containsKey("validateCode")){
//				Map<String,Object> validParam=new HashMap<String, Object>();
//				validParam.put("receive", param.get("mobile"));
//				
//				msgService.getValidateContent()
//			}
			param.put("userId", user.getUserid());
			Map<String,Object> map = new HashMap<String,Object>();
			boolean flag=true;
			//校验电话验证码
			if(param.get("mobileCode")!=null){
				if(!user.getMobile().equals(param.get("mobile").toString())){
					map.put("receive",param.get("mobile").toString());
					map.put("sendType", ContentUtil.MSG_SMS);
					map.put("sendStatus", ContentUtil.SEND_SUCCESS);
					map.put("type", ContentUtil.UPDATE_REGTYPE);
					map.put("code", param.get("mobileCode"));
					rsmessage=this.msgService.getValidateContent(map);
					if(!"1".equals(rsmessage)){
						flag=false;
					}else{
						user.setRegtype("M");
						user.setMobile(param.get("mobile").toString());
						if(this.userService.isExist(user)){
							flag=false;
							rsmessage="更新失败,该手机号已被使用!";
						}
					}
					map.clear();
				}else{
					flag=false;				
				}
			}
			//校验电子邮件验证码
			if(param.get("emailCode")!=null){
				if(!user.getEmail().equals(param.get("email").toString())){
					map.put("receive",param.get("email").toString());
					map.put("sendType", ContentUtil.MSG_EMAIL);
					map.put("sendStatus", ContentUtil.SEND_SUCCESS);
					map.put("type", ContentUtil.UPDATE_REGTYPE);
					map.put("code", param.get("emailCode"));
					try {
						rsmessage=this.msgService.vidateEmailContent(map);
					} catch (ParseException e) {
						rsmessage="验证码错误";
						e.printStackTrace();
					}
					if(!"2".equals(rsmessage)){
						flag=false;
					}else{
						user.setRegtype("E");
						user.setEmail(param.get("email").toString());
						if(this.userService.isExist(user)){
							flag=false;
							rsmessage+="<br/>该邮箱已被使用!";
						}
					}
				}else{
					flag=false;	
				}
			}
			if(flag){
				MultipartHttpServletRequest fileReq=(MultipartHttpServletRequest)request;
				MultipartFile file=fileReq.getFile("headpic");
				if(this.userService.updateUserInfo(param, file)!=0) rsmessage="更新成功";
			}
		}
		result.put("msginfo", rsmessage);
		return result;
	}
	/**
	 * 注册方式修改发送验证码
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getValidateCode")
	public JSONObject getValidateCode(HttpServletRequest request){
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		List<String> paramList=new ArrayList<String>();
		paramList.add(param.get("receive").toString());
		int type=Integer.parseInt(param.get("type").toString());
		if(type==1){
			param.clear();
			param.put("phoneList", paramList);
		}else{
			param.clear();
			param.put("emailList", paramList);
		}
		List<Map<String,Object>> list=this.userService.queryExistRegistedUser(param);	
		if(list!=null&&list.size()>0)
			result.put("err", "该"+(type==1?"手机":"邮箱")+"已被占用");
		else{
			if(type==1)
				messageSevice.sendSms(ContentUtil.getValidateCodePhoneModel(PwdUtil.createPwd()), paramList.get(0), ContentUtil.UPDATE_REGTYPE);
			else
				messageSevice.sendEmail("注册方式修改", ContentUtil.getEmailValidateCode(ContentUtil.getValidateCode()), paramList.get(0), ContentUtil.UPDATE_REGTYPE);
		}
		return result;
	}
	/**
	 * 修改用户密码
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updatePwd")
	public JSONObject updatePwd(HttpSession session,HttpServletRequest request){
		JSONObject result=new JSONObject();
		result.put("success", 0);
		IpavuserEntity user=(IpavuserEntity)session.getAttribute("curuser");
		Map<String,Object> param=RequestParamToMap.convert(request);
		param.put("userId", user.getUserid());
		param.put("newPwd", PwdUtil.MD5(param.get("newPwd").toString().toUpperCase()));
		param.put("oldPwd", PwdUtil.MD5(param.get("oldPwd").toString().toUpperCase()));
		int success=userService.updatePwd(param);
		if(success>0)
			result.put("success", 1);
		return result;
	}
	/**
	 * 注册时候发送验证码
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCenterValidateCode")
	public JSONObject getCenterValidateCode(HttpServletRequest request){
		Map<String,Object> param=RequestParamToMap.convert(request);
		JSONObject result=new JSONObject();
		if(this.userService.hasRegisted(param)){
			result.put("err", param.containsKey("mobile")?"该手机号已注册":"该邮箱已注册");
		}else{
			if(param.containsKey("mobile")){
				messageSevice.sendSms(ContentUtil.getValidateCodePhoneModel(ContentUtil.getValidateCode()), param.get("mobile").toString(), ContentUtil.MSG_SMS);
			}
		}
		return result;
	}
}

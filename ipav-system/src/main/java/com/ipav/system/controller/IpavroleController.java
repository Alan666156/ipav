package com.ipav.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavroleEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavmenuService;
import com.ipav.system.service.IpavroleService;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.RequestParamToMap;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月1日 下午2:29:57	
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavroleController {
	@Autowired
	private IpavroleService roleService;
	@Autowired
	private IpavuserService userService;
	@Autowired
	private IpavmenuService menuService;
	
	/**
	 * 跳转到添加角色页面
	 * @param roleno
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/gotoaddRole")
	public String gotoaddRole(String roleno,ModelMap map){
		map.put("fromroleno", roleno);
		return "system/role/addrole";
	}
	/**
	 * 新增角色
	 * @param role
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/addRole")
	public String addRole(IpavroleEntity role,HttpServletRequest req,HttpServletResponse resp,ModelMap map){
		String[] menus= req.getParameterValues("menuid");
		roleService.addRole(role,menus);
		map.put("fromroleno", role.getRoleno().toString());
		map.put("flag", 1);
		return "system/role/addrole";
	}
	
	/**
	 * 跳转到修改角色页面
	 * @param companyid
	 * @param roleno
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/gotoupdateRole")
	public String gotoupdateRole(@RequestParam(value="companyid", required=true)String companyid,@RequestParam(value="roleno", required=true)String roleno,ModelMap map){
		Map parm = new HashMap();
		parm.put("companyid", companyid);
		parm.put("roleno", roleno);
		IpavroleEntity role= roleService.queryRole(parm);
		map.put("role", role);
		return "system/role/updaterole";
	}
	
	/***
	 * 修改角色
	 * @param role
	 * @param req
	 * @param resp
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/updateRole")
	public String updateRole(IpavroleEntity role,HttpServletRequest req,HttpServletResponse resp,ModelMap map){
		String[] menus= req.getParameterValues("menuid");
		roleService.updateRole(role,menus);
		map.put("role", role);
		//return "system/role/updaterole";
		map.put("flag", 1);
		return "forward:/mianRole";
	}
	/***
	 * 角色主入口页面
	 * @param companyid
	 * @param roleno
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/mianRole")
	public String mianRole(HttpSession session,@RequestParam(value="roleno", required=true)String roleno,ModelMap map){
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map parm = new HashMap();
		parm.put("companyid", user.getCompanyid());
		parm.put("roleno", roleno);
		IpavroleEntity role= roleService.queryRole(parm);
		map.put("role", role);
//		List userlist= roleService.queryRoleUser(parm);
//		map.put("userlist", userlist);
		return "system/role/mainrole";
	}

	/**
	 * 查询角色下的用户列表
	 * @author GaoYang
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRoleUsers")
	@ResponseBody
	public JSONObject queryRoleUsers(HttpSession session,HttpServletRequest request){
		JSONObject rsobj=new JSONObject();
		Map<String,Object> param = RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		if(curuser!=null) param.put("companyid", curuser.getCompanyid());
		rsobj.put("size", this.roleService.queryRoleUserCount(param));
		rsobj.put("list", this.roleService.queryRoleUser(param));
		return rsobj;
	}
	
	/***
	 * 员工授权列表页面
	 * @param companyid
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/roleuserlist")
	public String userRoleList(HttpSession session,String isatte ,ModelMap map){
//		Map parm = new HashMap();
//		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
//		parm.put("companyid", user.getCompanyid());
//		parm.put("isatte",isatte);
//		List userlist= roleService.queryRoleUserDelMy(parm,user.getUserid());
//		map.put("userlist", userlist);
//		map.put("isatte", isatte);
		return "system/role/roleuserlist";
	}
	
	/**
	 * 用户角色列表信息
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/roleuserinfos")
	public JSONObject userRoleList(HttpSession session,HttpServletRequest request){
//		System.out.println("==============用户角色0000=================");
		JSONObject result=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		param.put("userid", curuser.getUserid());
		param.put("companyid", curuser.getCompanyid());
		int count=this.roleService.queryRoleUserCount(param);
		result.put("counts", count);
		if(count>0)
			result.put("userlist", this.roleService.queryRoleUser(param));
		
		result.put("companyid", curuser.getCompanyid());
		
		return result;
		
	}
	
	/***
	 * 跳转到员工授权页面
	 * @param companyid
	 * @param userids
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/gotouseraddrole")
	public String gotoUserAddRole(String companyid,String userids,ModelMap map){
		Map parm = new HashMap();
		parm.put("companyid", companyid);
		List roles= roleService.getRoleList(parm,userids);
		map.put("userids", userids);
		map.put("roles", roles);
		map.put("companyid", companyid);
		return "system/role/useraddrole";
	}
	/**
	 * 员工授权
	 * @param req
	 * @param rsp
	 * @return
	 */
	@RequestMapping(value="/useraddrole")
	public String userAddRole(HttpServletRequest req,HttpServletResponse rsp){
		String[] roles= req.getParameterValues("roleid");
		String[] users= req.getParameterValues("userid");
		roleService.addUserRole(users, roles);
		return "forward:/roleuserlist";
	}
	
	/**
	 * 删除角色
	 * @param roleno
	 */
	@RequestMapping(value="/delRole")
	@ResponseBody
	public void delRole(String roleno){
		roleService.delRole(roleno);
	}
	
	/**
	 * 获取人员组织结构信息
	 * @param companyid
	 * @return
	 */
	@RequestMapping(value="/getOrgUserTree")
	@ResponseBody
	public JSONArray queryOrgUserTree(String companyid){
		List orguserlist =userService.queryOrgUserTree(companyid);
		JSONArray arr=(JSONArray)JSONArray.toJSON(orguserlist);
		return arr;
	}
	
	/**
	 * 获取角色对应的菜单信息
	 * @param roleno
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getMenutreeByRole")
	@ResponseBody
	public JSONArray getMenutreeByRole(String roleno,HttpSession session){
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map map = new HashMap();
		map.put("roleno", roleno);
		map.put("username", user.getUserid());
		List menulist= menuService.getMenuTreeByRole(map);
		JSONArray arr=(JSONArray)JSONArray.toJSON(menulist);
		System.out.println(arr.toJSONString());
		return arr;
	}
	
	/**
	 * 获取角色对应的菜单信息
	 * @param roleno
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getMenuTreeByRoleForUpdate")
	@ResponseBody
	public JSONArray getMenuTreeByRoleForUpdate(String roleno,HttpSession session){
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map map = new HashMap();
		map.put("roleno", roleno);
		map.put("username", user.getUserid());
		 
		List menulist= menuService.getMenuTreeByRoleForUpdate(map);
		JSONArray arr=(JSONArray)JSONArray.toJSON(menulist);
		System.out.println(arr.toJSONString());
		return arr;
	}
}

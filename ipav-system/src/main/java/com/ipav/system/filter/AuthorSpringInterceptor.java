package com.ipav.system.filter;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavroleService;

public class AuthorSpringInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private IpavroleService roleService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out=null;
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		String requestPath=httpServletRequest.getServletPath();
		if(requestPath.equals("/author")||requestPath.equals("/ipav")||requestPath.equals("/ipavMain")||requestPath.equals("/ipavlogin")||requestPath.equals("/system/top")||requestPath.equals("/system/left")||requestPath.equals("/system/index")||requestPath.equals("/system/floor"))
			return true;
		if(requestPath.indexOf(".")>0)
			return true;
		IpavuserEntity user = (IpavuserEntity) httpServletRequest.getSession().getAttribute("curuser");
		JSONObject obj=null;
//		if(user==null){//用户不存在
//			obj=new JSONObject();
//			obj.put("warningtype",0);
//			obj.put("warninginfo", "登录超时,请重新登录!");
//			out=response.getWriter();
//			out.write(obj.toJSONString());
//			out.flush();
//			out.close();
//			return false;
//		}
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("pathStr", requestPath);
		List<Map<String, Object>>  menuList=roleService.queryMenuInfoByPath(param);
		if(menuList==null||menuList.size()==0)
			return true;
		param.clear();
		param.put("userId", user.getUserid());
		param.put("menuId", menuList.get(0).get("menuid"));
		
		List<Map<String,Object>> list=roleService.getOptRole(param);
		if(list==null||list.size()==0){
			obj=new JSONObject();
			obj.put("warningtype",1);
			obj.put("warninginfo", "您没有该操作权限,请联系管理员!");
			out=response.getWriter();
			out.write(obj.toJSONString());
			out.flush();
			out.close();
			return false;
		}
		return true;
	}
	
}

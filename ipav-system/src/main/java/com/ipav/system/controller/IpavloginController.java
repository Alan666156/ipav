package com.ipav.system.controller;



import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavcompanyEntity;
import com.ipav.system.entity.IpavimageEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavcompanyService;
import com.ipav.system.service.IpavmsgService;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.ImageDrawUtil;
import com.ipav.system.util.MessageUtil;
import com.ipav.system.util.PwdUtil;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月13日 下午5:20:56	
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavloginController {
	
	@Autowired
	private IpavuserService userService;
	@Autowired
	private MessageUtil messageUtil;
	@Autowired
	private IpavmsgService msgService;
	@Autowired
	private IpavcompanyService companyService;
	/***
	 * 跳转到登录页面
	 * @return
	 */
	@RequestMapping(value="/ipav")
	public String gotoLogin(String userno,ModelMap model){
 		model.put("userno", userno);
		return "system/login/login";
	}
	/***
	 * 用户登录方法 		
	 * @return
	 */
	@RequestMapping(value="/ipavlogin")
	public String ipavLogin(IpavuserEntity user,HttpServletRequest request,HttpServletResponse response,ModelMap map)throws Exception{
		
		String saveflg = request.getParameter("saveflg");
		if(ContentUtil.SAVE_FLG.equals(saveflg)){
			//创建Cookie对象记录用户名和密码
			String  registno= request.getParameter("registno");
			Cookie ck1=new Cookie("registno",registno);
			Cookie ck2=new Cookie(registno+"password",user.getPassword());
			//设置记录时间60秒
			ck1.setMaxAge(7*24*60*60);
			ck2.setMaxAge(7*24*60*60);
			//添加到response对象
			response.addCookie(ck1);
			response.addCookie(ck2);
		}
		if(userService.loginUser(user,request.getSession())){
			IpavuserEntity u=(IpavuserEntity)request.getSession().getAttribute("curuser");
			if(u.getState()==0){
				Map stateMap=new HashMap<String, Object>();
				stateMap.put("state",1);
				stateMap.put("userid",u.getUserid());
				 userService.updateUserState(stateMap);
				 u.setState(1);
				 map.put("state", "Y");
			}else{
				map.put("state", "N");				
			}
			IpavcompanyEntity company =companyService.queryCompanyByid(String.valueOf(u.getCompanyid()));
			map.put("topCompanyId", company.getCompanyid());
			map.put("topCompanyName", company.getCompanyname());
			IpavimageEntity image=companyService.getCompanyImage(u.getCompanyid(),"LOGO");
			map.put("topCompanyPic", image==null?"":(image.getSubpath()==null?"":ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("company")+image.getSubpath()));
			return "system/homepage";
		}else{
			map.put("loginfalse", "Y");
			return "system/login/login";
		}
	}
	
	/***
	 * 跳转到系统管理页面
	 * @return
	 */
	@RequestMapping(value="/ipavsystem")
	public String goSystem(){
		return "system/main";
	}
	
	/***
	 * 跳转到系统管理页面
	 * @return
	 */
	@RequestMapping(value="/ipavMain")
	public String goMain(){
		return "system/homemain";
	}
	
	/**
	 * 用户注册start
	 * @return
	 */
	@RequestMapping(value="/gotoRegister")
	public String gotoRegisterView(){
		return "system/login/register";
	}
	/**
	 * 用户注册
	 * @param user
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/registerUser")
	public String registerUser(IpavuserEntity user,ModelMap map)throws Exception{
//		userService.registerUser(user);
//		map.put("user", user);
//		return "system/login/regsuccess";
		boolean flag=userService.isExist(user);
		String path="system/login/register";
		if(flag){
			map.put("message", "exist");
		}else{
			userService.regist(user);
			path="system/login/regsuccess";
		}
		map.put("user", user);
		return path;
	}
	
	/**
	 * 跳转到注册成功页面
	 * @param userid
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/gotoRegSuccess")
	public String gotoRegSuccess(String userid,ModelMap map){
		map.put("user", userService.queryUserId(userid));
		return "system/login/regsuccess";
	}
	/***
	 * 跳转到找回密码页面
	 * @return
	 */
	@RequestMapping(value="/findPassword")
	public String gotoFindPassword(){
		return "system/login/findPassword";
	} 
	
	/**
	 * 修改密码
	 * @param user
	 * @param map
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/passwordValdt")
	public String findPassword(IpavuserEntity user,ModelMap map,HttpSession session)throws Exception{
		IpavuserEntity curuser= userService.queryUser(user);
		String path = "";
		if(ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())){
			String code= PwdUtil.createPwd();
			messageUtil.sendValiCodeMessage(curuser,code);
			path = "/system/login/findforphone";
		}else if(ContentUtil.REGIST_TYPE_EMIAL.equals(user.getRegtype())){
//			String session_id = PwdUtil.MD5Pwd();
//			messageUtil.sendPwdResetMail(curuser,session_id);
			messageUtil.sendPwdResetMail(curuser.getUserid(),PwdUtil.getBase64(curuser.getEmail()),curuser.getEmail());
			path = "/system/login/findformail";	
		}else{
			map.put("userid", curuser.getUserid());
			path= "/system/login/pwdreset";
		}
		map.put("user", curuser);
		return path;
	}
	/**
	 * 短信验证手机验证码
	 * @param user
	 * @param phonecode
	 * @param session
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/checkPhonecode")
	public String checkPhoneCode(IpavuserEntity user,String phonecode,HttpSession session,ModelMap map)throws Exception{
		String path ="";
		String userid = (String)user.getUserid();
		Map parm = new HashMap();
		parm.put("receive", user.getMobile());
		parm.put("sendType", ContentUtil.MSG_SMS);
		parm.put("sendStatus", ContentUtil.SEND_SUCCESS);
		parm.put("type", ContentUtil.SMS_VALIDATE_CODE);
		parm.put("code", phonecode);
		String validateResult=msgService.getValidateContent(parm);
		if("1".equals(validateResult)){
			map.put("userid", userid);
			path =  "/system/login/pwdreset";
		}else{
			String code= PwdUtil.createPwd();
			messageUtil.sendValiCodeMessage(user,code);
			map.put("user", user);
			path = "/system/login/findforphone";
		}
		return path;
	}
	/**
	 * 发送手机验证码
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/createPhonecdoe")
	@ResponseBody
	public void creatPhoneCode(HttpServletRequest request)throws Exception{
		String mobile= request.getParameter("mobile");
		String userid= request.getParameter("userid");
		String code= PwdUtil.createPwd();
		IpavuserEntity user=new IpavuserEntity();
		user.setUserid(userid);
		IpavuserEntity curuser= userService.queryUser(user);
		messageUtil.sendValiCodeMessage(curuser,code);
	}
	/**
	 * 验证用户是否存在
	 * @return
	 */
	@RequestMapping(value="/checkhavUser")
	@ResponseBody
	public boolean checkHavUser(HttpServletRequest request,HttpServletResponse response){
		IpavuserEntity user = new IpavuserEntity();
		user.setUserid(request.getParameter("userid"));
		user.setEmail(request.getParameter("email"));
		user.setMobile(request.getParameter("mobile"));
		user.setRegtype(request.getParameter("regtype"));
		return userService.checkExistUser(user);
	}
	/**
	 * 链接到重置密码页面
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/gotoPwdreset")
	public String gotoResetPwd(HttpServletRequest request,ModelMap map) throws Exception{
		map.put("userid", request.getParameter("userid"));
//		map.put("password", request.getParameter("password"));
//		map.put("session_id", request.getParameter("session_id"));
		String resetpwd=request.getParameter("resetpwd");
		String path="";
		if(StringUtils.isNotEmpty(resetpwd)){
			String resetpwd_str=PwdUtil.getFromBase64(resetpwd);
			Map<String,Object> parm =new HashMap<String,Object>();
			parm.put("receive", resetpwd_str);
			parm.put("sendType", ContentUtil.MSG_EMAIL);
			parm.put("sendStatus", ContentUtil.SEND_SUCCESS);
			parm.put("type", ContentUtil.EMAIL_PWD_RESET);
			boolean flg=msgService.validateTimeOut(parm);
			if(flg)	path="/system/login/pwdreset";
			else path="/system/urlerror";
		}
		return path;
	}
	/**
	 * 重置密码
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/pwdReset")
//	public String resetPwd(IpavuserEntity user,String session_id,HttpSession session)throws Exception{
	public String resetPwd(IpavuserEntity user)throws Exception{
//		String code = user.getUserid()+"pwd_session_id";
//		String pwd_session_id= (String)session.getAttribute(code);
//		if(StringUtils.isNotEmpty(pwd_session_id)){
//			if(!pwd_session_id.equals(session_id)){
//				session.removeAttribute(code);
//				throw new Exception("操作失败，页面已失效！");
//			}
//		}else{
//			session.removeAttribute(code);
//			throw new Exception("操作失败，链接已失效！");
//		}
		
		String pwd = user.getPassword();
		userService.updateUserPwd(user);
		user.setPassword(pwd);
//		session.removeAttribute(code);
		return "forward:/ipavlogin" ;
	}
	
	/**
	 * 生成验证码图片
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value="/createCode")
	public void createVerifyCode(HttpServletRequest req,HttpServletResponse resp){
		ImageDrawUtil.drawPicture(req, resp);
	}
	/**
	 * 校验验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/checkCode")
	@ResponseBody
	public boolean checkVerifyCode(HttpServletRequest request,HttpServletResponse response)throws Exception {
		response.setContentType("text/html;charset=utf-8"); 
		String validateC = (String) request.getSession().getAttribute("validateCode"); 
		String veryCode = request.getParameter("veryCode"); 
		if(veryCode==null||"".equals(veryCode)){ 
			return false;
		}else{ 
			if(validateC.equals(veryCode.toUpperCase())){ 
				return true;
			}else{ 
				return false;
			} 
		} 
	}
	
	/**
	 * 安全退出
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/quit")
	public JSONObject quit(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		
		Cookie[] cookies  =request.getCookies();
		if(cookies!=null){
			for (int i = 0; i < cookies.length; i++) {
				Cookie cok =cookies[i];
				String keyname= cok.getName();
				if((curuser.getUserid()+"password").equals(keyname)||(curuser.getEmail()+"password").equals(keyname)||(curuser.getMobile()+"password").equals(keyname)){
					cok.setMaxAge(0);
					response.addCookie(cok);
				}
			}
		}
		JSONObject result=new JSONObject();
		session.removeAttribute("curuser");
		result.put("result", request.getRequestURL().toString().replace(request.getRequestURI().toString(), "/ipav"));
		return result;
	}
}

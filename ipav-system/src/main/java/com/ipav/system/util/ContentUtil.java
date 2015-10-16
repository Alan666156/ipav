package com.ipav.system.util;

import java.util.HashMap;
import java.util.Map;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月15日 下午4:33:19	
 * 上海天道启科电子有限公司
 */
public class ContentUtil {
	
	public static final String  DEFUAL_RGSNAME_SUFFIX = "admin";//默认注册用户的后缀名
	public static final String  SYSTEM_NAME = "system";//默认系统名称
	public static final String  DEFUAL_VALDLF_NO = "0";//默认启用状态为否
	public static final String  DEFUAL_USERNO = "001";//默认注册员工编号
	public static final String  REGIST_TYPE_MOBILE = "M";//手机号注册方式
	public static final String  REGIST_TYPE_EMIAL ="E";//邮箱注册方式
	public static final String  DEFUAL_VALDLF_YES = "1";//启用状态为是
	public static final String  DEFUAL_AGAIN ="again";//再次标识
	public static final String  DEFUAL_ADD ="add";//新增
	public static final String  DEFUAL_UPDATE ="update";//修改
	public static final String  DEFUAL_VIEW ="sel";//查看
	public static final String  SYS_ROLENAME ="系统管理员";//查看
	public static final String  SYS_ROLEFLG ="Y";//是否系统默认角色标识
	public static final String  DEFAULT_USER_NAME = "系统管理员";//默认用户名称

	public static final String  INVITATION_TYPE_MOBILE ="IM";//邀请同事 手机邀请
	public static final String  INVITATION_TYPE_EMAIL ="IE";//邀请同事 邮箱邀请
	
	
	public static final String  DEFUAL_COMPANYNAME ="**公司";//查看
	public static final String  DEFUAL_COMPAYORGNAME ="**部";//查看
	public static final String  DEFUAL_LOGO_PIC ="logo.jpg";//默认公司logo图片
	public static final String  DEFUAL_SUB_LOGO_PIC ="sublogo.jpg";//默认公司logo图片
	
	
	public static final String  DEFUAL_ORGNO ="0";//默认组织机构
	public static final String  DEFUAL_ORGNAME ="组织机构";//默认组织机构
	public static final String  DEFUAL_ORGNOCOMPANY ="10";//默认公司机构
	
	public static final String  SYS_MENU_LEVL ="0";//系统应用级别
	public static final String  SON_MENU_LEVL ="1";//子应用级别
	public static final String  NODE_MENU_LEVL ="2";//模块功能应用级别
	
	public static  String  EMAILHOST = "MAIL.TC-KM.COM";//邮箱服务地址
	public static  String  EMAILFROM = "kjgj@kjgj.net.cn";//发件人邮箱
	public static  String  EMAILUSERNAME = "fstc\\kjgj";//发件人用户名
	public static  String  EMAILPASSWORD = "81157693";//发件人密码
	
	public static  String EMAIL_PATH ="http://192.168.3.228:8080/gotoPwdreset";//密码重置链接  邮箱方式
	public static  String LOGIN_PATH ="http://192.168.3.228:8080/ipav";//登录网址
	
	public static final String  SAVE_FLG = "save";//是否保存
	
	public static  String  SEND_MESSAGE_URL ="http://222.185.228.25:8000/msm/sdk/http/sendsms.jsp?username=JSMB260654&scode=ipav2015";//方式二
	public static final String  SEND_MESSAGE_OK ="0";//短信发送成功状态
	
	public static final int SMS_VALIDATE_CODE=0;//发送验证码
	
	public static final int SMS_REGISTE_SUCCESS=1;//注册成功短信
	
	public static final int EMAIL_PWD_RESET=0;//密码重置
	
	public static final int EMAIL_REGISTE_SUCCESS=1;//注册成功短信
	
	public static final int EMAIL_INVITATION_SUCCESS=2;//发送邀请成功邮件
	
	public static final int UPDATE_REGTYPE=3;//注册方式修改
	
	public static final int MSG_EMAIL=0;//邮箱发送
	
	public static final int MSG_SMS=1;//短信发送
	
	public static final int SEND_SUCCESS=1;//发送成功
	
	public static final int SEND_FAILD=0;//发送失败
	
	public static final String  IMAGE_SERVER_PATH ="C:/images/";
	
	public static final int EXCEL_TYPE_TEXT=0;//自定义工资项文本类型
	
	public static final int EXCEL_TYPE_INT=1;//自定义工资项整数类型
	
	public static final int EXCEL_TYPE_FLOAT=2;//自定义工资项浮点类型
	
	public static String IMAGE_ROOT="";//FTP根路径地址

	public static Map<String,String> IMAGEPATHS= new HashMap<String, String>();//FTP各子目录路径地址集合
	
	public static String FILEPATH="";

	/**
	 * 获取密码重置邮件模板
	 * @param curuser
	 * @return
	 */
	public static String getPwdResetMailModel(String userid,String resetpwd){
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body>亲爱的快捷管家用户:"+userid+ ", 您好！");
		sb.append("	请点击这里，重置您的密码：<br>");
		sb.append(ContentUtil.EMAIL_PATH);
		sb.append("?userid="+userid+"");
		sb.append("&resetpwd="+resetpwd+"<br>");
		sb.append("(如果链接无法点击，请将它拷贝到浏览器的地址栏中)<br>");
		sb.append("	密码规则修改为：请输入密码（6至16位数字或英文字符）<br>");
		sb.append("	该链接将在您修改密码或24小时后失效。<br>");
		sb.append("	快捷管家宗旨：竭尽全力为您的帐号安全保驾护航！<br>");
		sb.append("	现在就登录吧!登陆网址:"+ContentUtil.LOGIN_PATH+"<br><br>");
		sb.append("	快捷管家敬启<br><br>");
		sb.append("	此为自动发送邮件，请勿直接回复！如您有任何疑问，请点击联系我们<br>");
		sb.append("	</body></html>");  
		return sb.toString();
	}
	/***
	 * 获取注册成功邮件模板
	 * @param userid
	 * @param pwd
	 * @return
	 */
	public static String getRegisetMailModel(String userid,String pwd){
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body>");
		sb.append("	亲爱的快捷管家用户，您好！<br>");
		sb.append("	您已成功注册，【快捷管家】管理员帐号:<font color='red'>"+userid+"</font>，密码:"+pwd+"<br>");
		sb.append("	初次登录请考虑重置密码。该邮箱号可作为管理员帐号使用。<br>");
		sb.append("	手机下载客户端（网址）及PC端在网址（"+ContentUtil.LOGIN_PATH+"）登陆进行企业信息互动,客服中心（8008207816）。<br><br>");
		sb.append("	快捷管家信息互动办公软件，免费为企业个性开发，免费给企业快捷使用。<br>");
		sb.append("	现在就登录吧!登陆网址"+ContentUtil.LOGIN_PATH+"<br>");
		sb.append("	快捷管家敬启<br><br>");
		sb.append("	此为自动发送邮件，请勿直接回复！如您有任何疑问，请点击联系我们");
		sb.append("</body></html>"); 
		return sb.toString();
	}
	/***
	 * 公司内部添加员工信息邮件模板
	 * @param userid
	 * @param pwd
	 * @return
	 */
	public static String getRegisetByCompanyMailModel(String userid,String pwd,Long companyid){
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body>");
		sb.append("	亲爱的快捷管家用户，您好！<br>");
		sb.append("	您公司企业号为"+companyid+"邀请您使用快捷管家，您的帐号:<font color='red'>"+userid+"</font>，密码:"+pwd+"<br>");
		sb.append("	初次登录请在首页中的个人中心修改密码，该邮箱号可作为用户帐号使用。<br>");
		sb.append("	手机下载客户端（网址）及PC端在网址（"+ContentUtil.LOGIN_PATH+"）登陆进行企业信息互动,客服中心（8008207816）。<br><br>");
		sb.append("	快捷管家信息互动办公软件，免费为企业个性开发，免费给企业快捷使用。<br>");
		sb.append("	现在就登录吧!登陆网址"+ContentUtil.LOGIN_PATH+"<br>");
		sb.append("	快捷管家敬启<br><br>");
		sb.append("	此为自动发送邮件，请勿直接回复！如您有任何疑问，请点击联系我们");
		sb.append("</body></html>"); 
		return sb.toString();
	}
	
	/**
	 * 获取注册成功手机短信模板
	 * @param userid
	 * @param pwd
	 * @return
	 */
	public static String getRegisePhoneModel(String userid,String pwd){
		StringBuffer sb = new StringBuffer();
		sb.append("&content=@1@="+userid+",@2@="+pwd+",@3@="+ContentUtil.LOGIN_PATH);
		sb.append("&tempid=MB-2014120805");
		
		return sb.toString();
	}
	
	/**
	 * 公司内部注册成功手机短信模板
	 * @param userid
	 * @param pwd
	 * @return
	 */
	public static String getRegiseByCompanyPhoneModel(String userid,String pwd,Long companyid){
		StringBuffer sb = new StringBuffer();
		sb.append("&content=@1@="+userid+",@2@="+pwd+",@3@="+ContentUtil.LOGIN_PATH+",@4@="+companyid);
		sb.append("&tempid=MB-2015031659");
		return sb.toString();
	}
	
	/***
	 * 修改密码，手机验证码短信模板
	 * @param code
	 * @return
	 */
	public static String getValidateCodePhoneModel(String code){
		StringBuffer sb = new StringBuffer();
		sb.append("&content=@1@="+code);
		sb.append("&tempid=MB-2013102300");
		return sb.toString();
	}
	/***
	 * 邀请同事短信模板
	 * @param code
	 * @return
	 */
	public static String invitationColleagueMobleModel(String fname,String sname,String pwd,String companyName,String em){
		StringBuffer sb = new StringBuffer();
		sb.append("&content=@1@="+fname+",@2@="+sname+",@3@="+pwd+",@4@="+em+",@5@="+ContentUtil.LOGIN_PATH+",@6@="+companyName);
		sb.append("&tempid=MB-2015031659");
		return sb.toString();
	}
	
	/**
	 * 同事圈邀请同事邮箱模板
	 * @param fname
	 * @param sname
	 * @param pwd
	 * @return
	 */
	public static String  invitationColleagueEmailModel(String fname,String sname,String pwd,String  em,String CompanyName){
		StringBuffer sb = new StringBuffer(); 
		sb.append("<html><body>");
		sb.append("亲爱的用户，您好！<br>");
		sb.append("您同事"+fname+"邀请您加入使用"+CompanyName+"公司“快捷管家”信息互动平台，<font color='red'>您的账号："+sname+".密码："+pwd+".<font><br>");
		sb.append("初次登录请考虑重置密码或在首页中的个人信息修改密码，您的邮箱号"+em+"也可作为管理员帐号使用。<br>"); 
		sb.append("手机下载客户端（网址）及PC端在网址（"+ContentUtil.LOGIN_PATH+"）登陆进行企业信息互动,客服中心（8008207816）。<br>");
		sb.append("快捷管家信息互动办公软件，免费为企业个性开发，免费给企业快捷使用。<br>");
		sb.append("现在就登录吧!登陆网址"+ContentUtil.LOGIN_PATH+"<br>");
		sb.append("快捷管家敬启<br><br>");
		sb.append("此为自动发送邮件，请勿直接回复！如您有任何疑问，请点击联系我们"); 
		sb.append("</body></html>"); 
		return sb.toString();
	}
	
	
	/********推荐分享*********/
	/***
	 * 邀请同事短信模板
	 * @param code
	 * @return
	 */
	public static String recommendModel(String iname,String ly){
		StringBuffer sb = new StringBuffer();
		sb.append("&content=@1@="+iname+",@2@="+iname+",@3@="+ly+",@4@="+ContentUtil.LOGIN_PATH);
		sb.append("&tempid=MB-2015032744");
		return sb.toString();
	}
	
	/**
	 * 同事圈邀请同事邮箱模板
	 * @param fname
	 * @param sname
	 * @param pwd
	 * @return
	 */
	public static String  recommendEmailModel(String iname,String ly){
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body>");
		sb.append("	亲爱的用户，您好！<br>");
		sb.append("您朋友"+iname+"邀请您使用“快捷管家”信息互动平台，</br>");
		sb.append(iname+"留言（"+ly+"）</br>"); 
		sb.append("快捷管家免费给企业快捷使用。</br>");
		sb.append("	现在就注册吧!注册、登陆网址（"+ContentUtil.LOGIN_PATH+"）；客服中心（8008207816）。<br><br>");
		/*sb.append("	快捷管家信息互动办公软件，免费为企业个性开发，免费给企业快捷使用。<br>"); */
		sb.append("	快捷管家敬启<br><br>");
		sb.append("	此为自动发送邮件，请勿直接回复！如您有任何疑问，请点击联系我们");
		sb.append("</body></html>"); 
		return sb.toString();
	}
	
	
	
	
	
	
	/**
	 * 邮件验证码
	 * @param code
	 * @return
	 */
	public static String getEmailValidateCode(String code){
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body>");
		sb.append("	亲爱的快捷管家用户，您好！<br>");
		sb.append("	您的验证码为:<font color='red'>"+code+"</font></br>");
		sb.append("	手机下载客户端（网址）及PC端在网址（"+ContentUtil.LOGIN_PATH+"）登陆进行企业信息互动,客服中心（8008207816）。</br></br>");
		sb.append("	快捷管家信息互动办公软件，免费为企业个性开发，免费给企业快捷使用。</br>");
		sb.append("	现在就登录吧!登陆网址"+ContentUtil.LOGIN_PATH+"</br>");
		sb.append("	快捷管家敬启</br></br>");
		sb.append("	此为自动发送邮件，请勿直接回复！如您有任何疑问，请点击联系我们");
		sb.append("</body></html>"); 
		return sb.toString();
	}
	
	public static String getValidateCode(){
		String[] codeArr=new String[]{"0","1","2","3","4","5","6","7","8","9"};
		String code="";
		for(int i=0;i<6;i++)
			code+=codeArr[(int)(Math.random()*6)];
		return code;
	}
}

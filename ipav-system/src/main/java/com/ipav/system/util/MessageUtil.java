package com.ipav.system.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavmsgService;

/**
 * 信息发送帮助类
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月19日 上午11:40:16	
 * 上海天道启科电子有限公司
 */
@Service
public class MessageUtil {
	
	@Autowired
	private IpavmsgService msgService;
	
	/**
	 * 邮件发送
	 * @param subject
	 * @param content
	 * @param toEmail
	 * @throws Exception
	 */
	public void sendEmail(String subject,String content,String toEmail,int type){
		try {
			MailUtil mail = new MailUtil();
			mail.setToEmails(toEmail);  
			mail.setSubject(subject);  
			mail.setContent(content);  
			mail.sendEmail();
			addMsgRecord(content, toEmail, ContentUtil.SEND_SUCCESS, type, ContentUtil.MSG_EMAIL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送密码重置邮件
	 */
//	public void sendPwdResetMail(IpavuserEntity curuser,String sessionid)throws Exception{
//		this.sendEmail("找回密码", ContentUtil.getPwdResetMailModel(curuser,sessionid), curuser.getEmail(),ContentUtil.EMAIL_PWD_RESET);
//	}
	public void sendPwdResetMail(String userid, String resetpwd,String email)throws Exception{
		this.sendEmail("找回密码", ContentUtil.getPwdResetMailModel(userid,resetpwd), email,ContentUtil.EMAIL_PWD_RESET);
	}
	/**
	 * 发送注册成功邮件
	 * @param curuser
	 * @param pwd 加密前密码
	 * @throws Exception
	 */
	public void sendRegistOkMail(IpavuserEntity curuser,String pwd)throws Exception{
		sendEmail("注册成功", ContentUtil.getRegisetMailModel(curuser.getUserid(), pwd), curuser.getEmail(),ContentUtil.EMAIL_REGISTE_SUCCESS);
	}
	
	/***
	 * 公司内部添加员工邮件发送方法
	 * @param curuser
	 * @param pwd
	 * @throws Exception
	 */
	public void sendRegistOkByCompanyMail(IpavuserEntity curuser,String pwd)throws Exception{
		sendEmail("注册成功", ContentUtil.getRegisetByCompanyMailModel(curuser.getUserid(), pwd,curuser.getCompanyid()), curuser.getEmail(),ContentUtil.EMAIL_REGISTE_SUCCESS);
	}
	
	
	/**
	 * 发送邀请成功邮件
	 * @param curuser
	 * @param pwd 加密前密码
	 * @throws Exception
	 */
	public void sendInvitationOkMail(String iname,IpavuserEntity curuser,String pwd,String companyName)throws Exception{
		sendEmail("同事邀请", ContentUtil.invitationColleagueEmailModel(iname,curuser.getUserid(), pwd,curuser.getEmail(),companyName), curuser.getEmail(),ContentUtil.EMAIL_INVITATION_SUCCESS);
	}
	
	public void sendNoticeRemindMail(String send_name,String receive_name){}
	/**
	 * 发送邀请成功短信
	 * @param user
	 * @param pwd 加密前密码
	 * @throws Exception
	 */
	public void sendInvitationOkMessage(String iname,IpavuserEntity curuser,String pwd,String companyName)throws Exception{
	    String content=ContentUtil.invitationColleagueMobleModel(iname,curuser.getUserid(), pwd,companyName,curuser.getMobile());
		boolean flag=sendSms(content,curuser.getMobile(),ContentUtil.SMS_REGISTE_SUCCESS);
	    if(!flag)
	    	throw new Exception("短信服务出错，请稍后注册！");
	}
	
	
	/*****推荐分享*****/
	/**
	 * 推荐分享邮件
	 * @param curuser
	 * @param pwd 加密前密码
	 * @throws Exception
	 */
	public void sendrecommendMail(String iname,String ep,String ly)throws Exception{
		sendEmail("好友推荐", ContentUtil.recommendEmailModel(iname, ly), ep,ContentUtil.EMAIL_INVITATION_SUCCESS);
	}
	/**
	 * 推荐分享短信
	 * @param user
	 * @param pwd 加密前密码
	 * @throws Exception
	 */
	public void sendrecommendPhone(String iname,String ep,String ly)throws Exception{
	    String content=ContentUtil.recommendModel(iname,ly);
		boolean flag=sendSms(content,ep,ContentUtil.SMS_REGISTE_SUCCESS);
	    if(!flag)
	    	throw new Exception("短信服务出错，请稍后注册！");
	}	
	
	
	
	
	
	
	/**
	 * 发送信息
	 * @param phone
	 * @param content
	 * @return
	 */
	public boolean sendSms(String phone,String content) {
		boolean result=false;
		StringBuffer POST_URL = new StringBuffer();
		POST_URL.append(ContentUtil.SEND_MESSAGE_URL);
		//方式二
		POST_URL.append("&mobile="+phone);
		POST_URL.append(content);
		
		URL postUrl;
		HttpURLConnection connection=null;
		DataOutputStream out=null;
		try {
			postUrl = new URL(POST_URL.toString());
			connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			//connection.setRequestProperty("Content-Encoding", value);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			//connection.setRequestProperty("Content-Type","text/xml;charset=utf-8"); 
			connection.setRequestProperty("Accept-Charset","utf-8");
			connection.connect();
			out = new DataOutputStream(connection.getOutputStream());
			out.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
			String line = null;  
		    StringBuffer sb = new StringBuffer();  
		    while ((line = br.readLine()) != null) {  
		            sb.append(line);  
		    }
		    String[] rs=sb.toString().split("#");
		    
		    if(rs.length>1){
		    	if(ContentUtil.SEND_MESSAGE_OK.equals(rs[0].trim())){
		    		result=true;
		    	}
		    }
//		    if(ContentUtil.SEND_MESSAGE_OK.equals((sb.toString()).trim()))
		    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!=null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(connection!=null)
				connection.disconnect();
		}
	    return result;
	}
	
	/**
	 * 添加短信发送记录
	 * @param sendId
	 * @param content
	 * @param phone
	 * @param status
	 * @param type
	 */
	public void addMsgRecord(String content,String receive,int sendStatus,int type,int sendType){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("content", content);
		map.put("receive", receive);
		map.put("sendStatus", sendStatus);
		map.put("type", type);
		map.put("sendType", sendType);
		msgService.insertMsg(map);
	}
	
	public boolean sendSms(String content,String mobile,int smsType){
		boolean flag=sendSms(mobile, content);
		addMsgRecord(content, mobile, flag?ContentUtil.SEND_SUCCESS:ContentUtil.SEND_FAILD, smsType,ContentUtil.MSG_SMS);
		return flag;
	}
	
	/**
	 * 发送注册成功短信
	 * @param user
	 * @param pwd
	 * @throws Exception
	 */
	public void sendReistOkMessage(IpavuserEntity user,String pwd)throws Exception{
		String content=ContentUtil.getRegisePhoneModel(user.getUserid(), pwd);
		boolean flag=sendSms(content,user.getMobile(),ContentUtil.SMS_REGISTE_SUCCESS);
	    if(!flag)
	    	throw new Exception("短信服务出错，请稍后注册！");
	}
	/**
	 * 公司内部注册成功短信
	 * @param user
	 * @param pwd
	 * @throws Exception
	 */
	public void sendReistOkByCompanyMessage(IpavuserEntity user,String pwd)throws Exception{
		String content=ContentUtil.getRegiseByCompanyPhoneModel(user.getUserid(), pwd,user.getCompanyid());
		boolean flag=sendSms(content,user.getMobile(),ContentUtil.SMS_REGISTE_SUCCESS);
	    if(!flag)
	    	throw new Exception("短信服务出错，请稍后注册！");
	}
	
	/***
	 * 发送校验码短信
	 * @param user
	 * @param code
	 * @throws Exception
	 */
	public void sendValiCodeMessage(IpavuserEntity user,String code)throws Exception{
		String content=ContentUtil.getValidateCodePhoneModel(code);
		boolean flag=sendSms(content,user.getMobile(),ContentUtil.SMS_VALIDATE_CODE);
		if(!flag)
	    	throw new Exception("验证码发送失败！");
	}
}

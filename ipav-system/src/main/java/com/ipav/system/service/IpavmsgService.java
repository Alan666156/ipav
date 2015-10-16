package com.ipav.system.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipav.system.dao.MsgMapper;

@Service
public class IpavmsgService {

	@Autowired
	private MsgMapper msgMapper;
	
	/**
	 * 插入短信发送记录
	 * @param map
	 * @return
	 */
	public long insertMsg(Map<String,Object> map){
		return this.msgMapper.insertMsg(map);
	}
	
	/**
	 * 更改信息可用状态
	 * @param id
	 */
	public void updateStatus(long id){
		this.msgMapper.updateStatus(id);
	}
	
	/**
	 * 获取验证信息
	 * @param map
	 * @return
	 */
	public String getValidateContent(Map<String,Object> map){
		String result="";
		Map<String,Object> validateInfo = this.msgMapper.getValidateContent(map);
		String content="";
		if(validateInfo==null)
			result="验证码错误";
		else{
			if("1".equals(validateInfo.get("send_type").toString())){
				Calendar now=Calendar.getInstance();
				Calendar sendTime=Calendar.getInstance();
				sendTime.setTime((Date)validateInfo.get("create_time"));
				long interval=(now.getTimeInMillis()-sendTime.getTimeInMillis())/1000;
				if(interval>120)
					result="验证码超时";
				else if(validateInfo.get("status").toString().equals("0"))
					result="验证码已失效";
				else{
					content=validateInfo.get("content").toString();
					content=content.substring(13, 19);
					if(content.equals(map.get("code")))
						result="1";
					else
						result="验证码错误";
				}
			}
			if("1".equals(result))
				this.updateStatus(Long.parseLong(validateInfo.get("id").toString()));
		}
		return result;	
	}
	/**
	 * 重置密码,验证邮箱链接是否失效
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	public boolean validateTimeOut(Map<String,Object> map) throws ParseException{
		boolean time_flg=false;
		Map<String,Object> validateInfo=this.msgMapper.getValidateContent(map);
		int state=Integer.parseInt(validateInfo.get("status").toString());
		if(state==1){//表示数据可用
			String time_str=validateInfo.get("create_time").toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time_stamp=new Date().getTime()-sdf.parse(time_str).getTime();
			long day=time_stamp/(24*60*60*1000);
			if(day==0){
				time_flg=true;
				//更新数据为不可用,使用一次后使重置密码链接失效
				this.updateStatus(Long.parseLong(validateInfo.get("id").toString()));
			}
		}
		return time_flg;
	}
	/**
	 * 电子邮箱校验验证码
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	public String vidateEmailContent(Map<String, Object> map) throws ParseException {
		String result="";
		Map<String,Object> validateInfo = this.msgMapper.getValidateContent(map);
		if(validateInfo==null){
			result="验证码错误";
		}else{			
			if("0".equals(validateInfo.get("send_type").toString())){
				if(validateInfo.get("status").toString().equals("0")){
					result="验证码已失效";
				}else{//表示数据可用
					String time_str=validateInfo.get("create_time").toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					long time_stamp=new Date().getTime()-sdf.parse(time_str).getTime();
					long hours=time_stamp/(60*60*1000);
					if(hours>30){
						result="验证码超时";
					}else{
						String content=validateInfo.get("content").toString();
						content=content.substring(content.indexOf("<font"),content.indexOf("</font>"));
						content=content.substring(content.indexOf(">")+1);
						if(content.equals(map.get("code"))) result="2";
						else result="验证码错误";
					}
				}
			}
			if("2".equals(result))	this.updateStatus(Long.parseLong(validateInfo.get("id").toString()));
		}
		return result;	
	}
	
	
}

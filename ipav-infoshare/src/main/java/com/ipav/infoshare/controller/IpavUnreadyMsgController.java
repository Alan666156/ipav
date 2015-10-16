package com.ipav.infoshare.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.infoshare.service.IpavUnreadyMsgService;
import com.ipav.system.dao.MyMsgMapper;
import com.ipav.system.entity.IpavUnreadymsgEntity;
import com.ipav.system.entity.IpavuserEntity;

@Controller
public class IpavUnreadyMsgController {
  @Autowired
  private IpavUnreadyMsgService umService;
  
  @Autowired
  private MyMsgMapper msgMapper;
  
  IpavuserEntity curuser=null;	
  private void userSssion(HttpSession session) {
		curuser = (IpavuserEntity) session.getAttribute("curuser"); 
  }
  /**
   * 查询未读信息
   * state:状态 1-有 0无
   * type:1:我的消息 2：全部动态 3：与我有关
   */
 	@RequestMapping("/queryUnreadyMsg") 
 	@ResponseBody
  public JSONArray queryUnreadyMsg(HttpSession session){
	  JSONArray rtn =new JSONArray();
	  userSssion(session);
	  
	  /****我的消息***/
	  //未读公告长度
	  Integer noticeConut= msgMapper.queryUnreadNoticeCount(curuser.getUserid());
	  //未读工资长度
	  Integer wageConut= msgMapper.quertUnreadWageCount(curuser.getUserid());
	  JSONObject myMsg=new JSONObject();
	  myMsg.put("type",1);
	  if((noticeConut!=null && noticeConut!=0) || (wageConut!=null && wageConut!=0)){
		  myMsg.put("state",1);
	  }else{
		  myMsg.put("state",0);
	  }
	  rtn.add(myMsg);
	  
	  
	  
	  /*****全部动态******/	 
	  JSONObject allMsg=new JSONObject();
	  IpavUnreadymsgEntity um=new IpavUnreadymsgEntity();
	  um.setUserid(curuser.getUserid());
	  um.setType(1);
	  Integer  allisExist= umService.queryThisExist(um);
	  if(allisExist==0){
		  um.setUserid(curuser.getUserid()); 
		  um.setActionid(0+"");
		  umService.insertReadyMsg(um);
	  }
	  um=umService.queryUnreadyMsg(um);
	  Map map =new HashMap<String,Object>();
	  map.put("companyid",curuser.getCompanyid());
	  map.put("userid",curuser.getUserid());
	  map.put("actionid",um.getActionid());	  
	  Integer maxSid=umService.sayAllUnreadyMaxSid(map);
	  if(maxSid!=null && maxSid>Integer.parseInt(um.getActionid())){
		  allMsg.put("state",1);
	  }else{
		  allMsg.put("state",0);
	  }
	  allMsg.put("type",2);	   
	  rtn.add(allMsg);
	  
	  /*****与我相关******/
	  JSONObject mySayMsg=new JSONObject();
	  um=new IpavUnreadymsgEntity();
	  um.setUserid(curuser.getUserid());
	  um.setType(2);
	  Integer  myisExist= umService.queryThisExist(um);
	  if(myisExist==0){
		  um.setUserid(curuser.getUserid());  
		  um.setActionid(0+"");
		  umService.insertReadyMsg(um);
	  }
	  um=umService.queryUnreadyMsg(um); 
	  String saydates=umService.sayMyUnreadyNewDate(curuser.getUserid());
	  if(saydates!=null && !(um.getActionid()).equals(saydates)){
		  mySayMsg.put("state",1);
	  }else{
		  mySayMsg.put("state",0);
	  }
	  mySayMsg.put("type",3);	   
	  rtn.add(mySayMsg);
	  return rtn;
  }
}

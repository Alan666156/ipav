package com.ipav.mobile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.mobile.param.RequestParameter;
import com.ipav.mobile.param.ResponseParameter;
import com.ipav.system.dao.ActionFileMapper;
import com.ipav.system.entity.IpavActionFileEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavmsgService;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.MessageUtil;
import com.ipav.system.util.PwdUtil;

@Service
public class UserService {
	
	@Autowired
	private IpavuserService userService;
	
	@Autowired
	private IpavmsgService msgService;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private ActionFileMapper sayImageMapper;
	
	
	/**
	 * 移动端查询组织机构及人员
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter queryOrgUserTree(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		List<Map<String,Object>> orguserlist= userService.queryOrgUserTree(obj.getString("companyid"));
		for(Map<String,Object> m :orguserlist){
			 Set<String> set=m.keySet();
			 for(String s:set){
				 if(s!=null&&"picpath".equals(s)){
					 m.put("picpath", ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+m.get(s));
					 break;
				 }
			 }
		}
		JSONArray arr=(JSONArray)JSONArray.toJSON(orguserlist);
		JSONObject body=new JSONObject();
		body.put("result",orguserlist==null?0:1);
		body.put("content",arr);
		result.setBody(body);
		return result;
	}
	

	/**
	 * 移动端登陆
	 * @param param
	 * @return
	 */
	public ResponseParameter login(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		IpavuserEntity user=new IpavuserEntity();
		user.setPassword(obj.getString("pwd"));
		user.setRegtype(obj.getString("type"));
		if(obj.getString("type").equals(ContentUtil.REGIST_TYPE_MOBILE))
			user.setMobile(obj.getString("loginName"));
		else if(obj.getString("type").equals(ContentUtil.REGIST_TYPE_EMIAL))
			user.setEmail(obj.getString("loginName"));
		else 
			user.setUserid(obj.getString("loginName"));
		
		IpavuserEntity userResult=userService.userLogin(user);
		JSONObject body=new JSONObject();
		body.put("result",userResult==null?0:1);
		body.put("userId",userResult==null?"":userResult.getUserid());
		body.put("userName",userResult==null?"":userResult.getUsername());
		body.put("companyid",userResult==null?"":userResult.getCompanyid());
		body.put("content",userResult==null?"用户名或密码错误":"");
		body.put("pic",userResult==null?"":ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+userResult.getPicpath());
		body.put("picpath",ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user"));
		
		/**获取主题图片START**/
		 Map sqlmap=new HashMap<String,Object>();
		    sqlmap.put("actionid",userResult==null?"":userResult.getUserid());
		    sqlmap.put("actiontype",4);
		    IpavActionFileEntity sayimage = sayImageMapper.queryActionfileByAction(sqlmap);
			System.out.println(sayimage);
		    body.put("themeImg",sayimage!=null?ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("theme")+sayimage.getFilepath():"");
		/**获取主题图片END**/
		result.setBody(body);
		return result;
	}
	
	/**
	 * 移动端注册
	 * @param param
	 * @return
	 */
	public ResponseParameter regist(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject();
		IpavuserEntity user=new IpavuserEntity();
		
		
		user.setRegtype(obj.getString("type"));
		if(obj.getString("type").equals(ContentUtil.REGIST_TYPE_MOBILE))
			user.setMobile(obj.getString("registerUser"));
		else 
			user.setEmail(obj.getString("registerUser"));
		boolean flag=userService.isExist(user);
		if(flag){
			body.put("result",0);
			body.put("content", obj.getString("type").equals(ContentUtil.REGIST_TYPE_MOBILE)?"该手机号已被注册":"该邮箱已被注册");
		}else{
			try {
				userService.regist(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(user.getUserid().equals("")){
				body.put("result",0);
				body.put("content", obj.getString("type").equals(ContentUtil.REGIST_TYPE_MOBILE)?"该手机号已被注册":"该邮箱已被注册");
			}else{
				body.put("result",1);
				body.put("content","");
			}
		}
		result.setBody(body);
		return result;
	}
	
	/**
	 * 获取短信验证码或修改密码地址
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter getValidateInfo(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject();
		IpavuserEntity user=new IpavuserEntity();
//		user.setPassword(PwdUtil.MD5(obj.getString("pwd")));
		user.setRegtype(obj.getString("type"));
		if(obj.getString("type").equals(ContentUtil.REGIST_TYPE_MOBILE))
			user.setMobile(obj.getString("sendTo"));
		else if(obj.getString("type").equals(ContentUtil.REGIST_TYPE_EMIAL))
			user.setEmail(obj.getString("sendTo"));
		IpavuserEntity userResult=userService.queryUser(user);
		if(userResult==null&&obj.getString("optType").equals("0")){
			body.put("result", 0);
			body.put("content", obj.getString("type").equals(ContentUtil.REGIST_TYPE_MOBILE)?"该注册手机不存在":"该注册邮箱不存在");
		}else if(userResult!=null&&obj.getString("optType").equals("1")){
			body.put("result", 0);
			body.put("content", obj.getString("type").equals(ContentUtil.REGIST_TYPE_MOBILE)?"该注册手机已存在":"该注册邮箱已存在");
		}else{
			boolean flag=false;
//			if(obj.getString("type").equals(ContentUtil.REGIST_TYPE_MOBILE)){
				flag=messageUtil.sendSms(ContentUtil.getValidateCodePhoneModel(PwdUtil.createPwd()), obj.getString("sendTo"), ContentUtil.SMS_VALIDATE_CODE);
				if(flag){
					body.put("result", 1);
					body.put("content", "");
				}else{
					body.put("result", 0);
					body.put("content", "验证码发送失败！");
				}
//			}else {
//				try {
//					messageUtil.sendPwdResetMail(userResult, PwdUtil.MD5Pwd());
//					body.put("result", 1);
//					body.put("content", "");
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					body.put("result", 0);
//					body.put("content", "密码重置邮件发送失败");
//				}
//			}
				
			
		}
		result.setBody(body);
		return result;
	}
	
	/**
	 * 
	 * 修改密码
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter updatePwd(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject();
		Map<String,Object> map=new HashMap<String,Object>();
		if(obj.containsKey("userId")){
			map.put("userId", obj.getString("userId"));
			map.put("newPwd", obj.getString("newPwd"));
			map.put("oldPwd", obj.getString("newPwd"));
			int count=userService.updatePwd(map);
			if(count>0){
				body.put("result",1);
				body.put("content", "");
			}else {
				body.put("result",0);
				body.put("content", "修改密码失败!");
			}
		}else{
			map.put("phone", obj.getString("loginName"));
			map.put("newPwd", obj.getString("newPwd"));
			int updateCount=userService.updatePwd(map);
			if(updateCount>0){
				body.put("result",1);
				body.put("content", "");
			}else{
				body.put("result",0);
				body.put("content", "密码重置失败");
			}
		}
		result.setBody(body);
		return result;
	}
	
	/**
	 * 校验验证码
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter validate(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("receive", obj.getString("phone"));
		map.put("sendType", ContentUtil.MSG_SMS);
		map.put("sendStatus", ContentUtil.SEND_SUCCESS);
		map.put("type", ContentUtil.SMS_VALIDATE_CODE);
		map.put("code", obj.getString("validCode"));
		String validateResult=msgService.getValidateContent(map);
		if("1".equals(validateResult)){
			body.put("result",1);
			body.put("content", "");
		}else{
			body.put("result",0);
			body.put("content", validateResult);
		}
		result.setBody(body);
		return result;
	}
	
	/**
	 * openfire获取房间信息
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter queryRoomsInfos(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("jid", obj.getString("jid"));
		List<Map<String,Object>> list=this.userService.getRoomsInfosOfOF(map);
		List<JSONObject> roomList=new ArrayList<JSONObject>();
		List<Map<String,Object>> memberList=null;
		JSONObject room=null;
		String roomId="";
		if(list!=null){
			for(Map<String,Object> tmp:list){
				if(!roomId.equals(tmp.get("roomID").toString())){
					if(room!=null){
						room.put("members", memberList);
						roomList.add(room);
					}
					roomId=tmp.get("roomID").toString();
					room=new JSONObject();
					room.put("membersOnly", tmp.get("membersOnly"));
					room.put("name", tmp.get("name"));
					room.put("naturalName", tmp.get("naturalName"));
					room.put("description", tmp.get("description"));
					memberList=new ArrayList<Map<String,Object>>();
					memberList.add(tmp);
				}else
					memberList.add(tmp);
			}
			room.put("members", memberList);
			roomList.add(room);
		}
		body.put("rooms", roomList);
		result.setBody(body);
		return result;
	}
}

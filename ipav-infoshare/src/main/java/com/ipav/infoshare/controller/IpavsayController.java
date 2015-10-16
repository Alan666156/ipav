package com.ipav.infoshare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.infoshare.service.IpavUnreadyMsgService;
import com.ipav.infoshare.service.IpavsayService;
import com.ipav.system.entity.IpavUnreadymsgEntity;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavreplyEntity;
import com.ipav.system.entity.IpavsayEntity;
import com.ipav.system.entity.IpavuserEntity;

/**
 * creater Jerry All right reserved. Created on 2014年11月24日 下午1:29:20
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavsayController {

	@Autowired
	private IpavsayService sayService;
	@Autowired
	private IpavUnreadyMsgService umService;
	 
	IpavuserEntity curuser=null;
	
	private void userSssion(HttpSession session) {
		curuser = (IpavuserEntity) session.getAttribute("curuser"); 
	}
	
	@RequestMapping(value = "/saylist")
	@ResponseBody
	public JSONArray querySayList(Integer pageNo,Integer pageSize,HttpSession session) {
		userSssion(session);
		List<IpavsayEntity> saylist = sayService.getSayList(curuser.getUserid(),pageNo,pageSize);
		  IpavUnreadymsgEntity um=new IpavUnreadymsgEntity();
		  um.setUserid(curuser.getUserid());
		  um.setType(1);		  
		  um=umService.queryUnreadyMsg(um);
		  Map map =new HashMap<String,Object>();
		  map.put("companyid",curuser.getCompanyid());
		  map.put("userid",curuser.getUserid());
		  map.put("actionid",um.getActionid());	  
		  Integer maxSid=umService.sayAllUnreadyMaxSid(map);
		  if(maxSid!=null && maxSid!=Integer.parseInt(um.getActionid())){
		  um.setActionid(maxSid+"");
		  umService.updateActionid(um);
		  }
		JSONArray arr = (JSONArray) JSONArray.toJSON(saylist);
		return arr;
	}
	/**
	 *  查询我发布的说说
	 */	
	@RequestMapping(value = "/myReleaseSays")
	@ResponseBody
	public JSONArray myReleaseSays(Integer pageNo,Integer pageSize,HttpSession session) {
		userSssion(session);
		List<IpavsayEntity> saylist = sayService.myReleaseSays(curuser.getUserid(),pageNo,pageSize);
		JSONArray arr = (JSONArray) JSONArray.toJSON(saylist);
		return arr;
	}


	@RequestMapping(value = "/userSayList")
	@ResponseBody
	public JSONArray queryUserSayList(Integer pageNo,Integer pageSize,HttpSession session) {
		userSssion(session);
		List usersaylist = sayService.getUserSayList(curuser.getUserid(),pageNo,pageSize);
		 IpavUnreadymsgEntity um=new IpavUnreadymsgEntity();
		  um=new IpavUnreadymsgEntity();
		  um.setUserid(curuser.getUserid());
		  um.setType(2); 
		  um=umService.queryUnreadyMsg(um); 
		  String saydates=umService.sayMyUnreadyNewDate(curuser.getUserid());
		  if(saydates!=null && !(um.getActionid()).equals(saydates)){
			  um.setActionid(saydates);
			  umService.updateActionid(um);
		  }
		JSONArray arr = (JSONArray) JSONArray.toJSON(usersaylist);
		return arr;
	}

	@RequestMapping(value = "/getImage")
	public void querySayImage(String imagename, HttpServletResponse res)
			throws Exception {
		sayService.getSayImageByName(imagename, res);
	}

	@RequestMapping(value = "/addSay")
	@ResponseBody
	public JSONObject addSay(HttpSession session,
			@RequestParam(value = "filedata", required = false) MultipartFile[] filedata,
			IpavsayEntity say,
			@RequestParam(value = "sayusers[]", required = false) String[] sayusers) throws Exception {
		JSONObject rsobj = new JSONObject();
		String msgstr="";
		if(say!=null){
			msgstr="success";
			userSssion(session);
			say.setSayuserid(curuser.getUserid());		 
			sayService.addSay(say, filedata, sayusers,0);
		}else{
			msgstr="error";
		}
		rsobj.put("msg", msgstr);
		return rsobj;
	}
	
	@RequestMapping(value = "/addSayTwo")
	@ResponseBody
	public JSONObject addSayTwo(HttpSession session,
			@RequestParam(value = "filedata", required = false) MultipartFile[] filedata,
			IpavsayEntity say,Integer fileSize,
			@RequestParam(value = "sayusers", required = false) String[] sayusers) throws Exception {
		JSONObject rsobj = new JSONObject();
		String msgstr="";
		if(say!=null){
			msgstr="success";
			userSssion(session);
			say.setSayuserid(curuser.getUserid());		 
			sayService.addSay(say, filedata, sayusers,fileSize);
		}else{
			msgstr="error";
		}
		rsobj.put("msg", msgstr);
		return rsobj;
	}
	/**
	 * 删除说说
	 */
	@RequestMapping(value = "/delSay")
	@ResponseBody
	public String delSay(Long sid,HttpSession session){
		userSssion(session);
		String msg =sayService.delSay(sid,curuser.getUserid());		 
		return JSON.toJSONString(msg);
	}
    /**
     * 添加说说评论
     * @return
     */
	@RequestMapping(value = "/addSayComment")
	@ResponseBody
	public JSON addSayComment(String commentStr, String isPersonal,
			HttpSession session,Integer pageSize) {
		userSssion(session);
		IpavcommentEntity comment =  JSON.toJavaObject(JSON.parseObject(commentStr),
				IpavcommentEntity.class);
		comment.setCommuserid(curuser.getUserid());
		sayService.addSayComment(comment); 
		return queryCommentBySid(comment.getActionid(),pageSize); 
	} 
	/**
	 * 点赞/取消赞
	 * @param commentStr 
	 * @return
	 */
	@RequestMapping(value = "/praiseOrCancel")
	@ResponseBody
	public JSON praiseOrCancel(String commentStr,HttpSession session) {
		IpavcommentEntity comment =  JSON.toJavaObject(JSON.parseObject(commentStr),
				IpavcommentEntity.class);
		userSssion(session);
		comment.setCommuserid(curuser.getUserid());
		Map map =sayService.praiseOrCancel(comment);
		return (JSON) JSON.toJSON(map);		 
	}
	
	/**
	 * 根据说说id得到评论
	 * @param commentStr 
	 * @return
	 */
	@RequestMapping(value = "/queryCommentBySid")
	@ResponseBody
	public JSON queryCommentBySid(Long sid,Integer pageSize) { 
		Map map =sayService.queryCommentBySid(sid,pageSize);
		return (JSON) JSON.toJSON(map);		 
	}
	/**
	 * 根据评论id得到回复
	 * @param commentStr 
	 * @return
	 */
	@RequestMapping(value = "/queryReapyByCid")
	@ResponseBody
	public JSON queryReapyByCid(Integer cid,Integer pageSize) { 
		Map map =sayService.queryCommentByCommentid(cid,pageSize);
		return (JSON) JSON.toJSON(map);		 
	}
	/**
	 * 添加说说回复	
	 * @param replyStr
	 * @return
	 */
	@RequestMapping(value = "/addSayReply")
	@ResponseBody
	public JSON addSayReply(String replyStr,HttpSession session,Integer pageSize) {
		IpavreplyEntity reply = JSON.toJavaObject(JSON.parseObject(replyStr),
				IpavreplyEntity.class);
		userSssion(session);
		reply.setReplyuserid(curuser.getUserid());
		sayService.addSayReply(reply);
		Map map =sayService.queryCommentByCommentid(reply.getCommentid().intValue(),pageSize);
		return (JSON) JSON.toJSON(map);	
	}
 
	/**
	 * 得到组织机构下的用户
	 * @param orgid
	 * @return
	 */
	@RequestMapping(value = "/querygetUserListForOrgid")
	@ResponseBody
	public JSONArray querygetUserListForOrgid(Integer orgid,
			@RequestParam(value = "orgnoids[]", required = false) String[]  orgnoids,HttpSession session) {
		userSssion(session);
		List usersaylist = sayService.getUserListForOrgid(orgid,orgnoids,curuser.getCompanyid());
		JSONArray arr = (JSONArray) JSONArray.toJSON(usersaylist);
		return arr;
	}
	
	/**
	 * 模糊查询用户
	 * @param username
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/searchUserForName")
	@ResponseBody
	public JSONArray getUserForName(String username,HttpSession session) {
		userSssion(session);
		List usersaylist = sayService.getUserForName(username,curuser.getCompanyid().intValue());
		JSONArray arr = (JSONArray) JSONArray.toJSON(usersaylist);
		return arr;
	}
	
	/**
	 * 邀请同事	
	 * @param name
	 * @param mailOrphone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/invitationFriend")
	@ResponseBody
    public JSON invitationFriend(String name,String mailOrphone,HttpSession session) throws Exception{
		userSssion(session);
		Map  map=sayService.invitationFriend(curuser.getUsername(),name,mailOrphone);
		return (JSON) JSON.toJSON(map);
	}
	
	 /***
	  * 得到组织树状结构
	  */ 
	@RequestMapping(value="/getAllLevelInfos")
	@ResponseBody
	public JSONArray getAllLevelInfos(HttpSession session) throws Exception{
		userSssion(session);
		List  list=sayService.getAllLevelInfos(curuser.getCompanyid());
		JSONArray arr = (JSONArray) JSONArray.toJSON(list);
		return arr;
	}
	
	/**
	 * 获取赞的人详情
	 */	
	@RequestMapping(value="/SearchPraiseUserMsg")
	@ResponseBody
	public JSONArray SearchPraiseUserMsg(HttpSession session,Long actionid,Integer pageNo,Integer pageSize) throws Exception{
		userSssion(session);
		List  list=sayService.SearchPraiseUserMsg(actionid,1,pageNo,pageSize);
		JSONArray arr = (JSONArray) JSONArray.toJSON(list);
		return arr;
	}
	
	/**
	 * 邀请同事（变更）	
	 * @param name
	 * @param mailOrphone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/invitationColleague")
	@ResponseBody
    public JSON invitationColleague(String name,String mailOrphone,HttpSession session) throws Exception{
		userSssion(session);
		Map  map=sayService.invitationColleague(curuser,name,mailOrphone);
		return (JSON) JSON.toJSON(map);
	}
	
	
	/**
	 * 推荐分享	
	 * @param ly
	 * @param mailOrphone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/recommendSharing")
	@ResponseBody
    public JSONObject recommendSharing(String ly,String mailOrphone,HttpSession session) throws Exception{
		userSssion(session);
		JSONObject json=sayService.recommendSharing(curuser,ly,mailOrphone);
		return json;
	}
	
	
}


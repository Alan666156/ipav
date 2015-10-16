package com.ipav.infoshare.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ipav.infoshare.service.IpavTopicService;
import com.ipav.infoshare.service.IpavcommReplyService;
import com.ipav.system.entity.IpavTopicEntity;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.util.JsonToObjectUtil;

@Controller 
@RequestMapping("/topicController")
public class IpavTopicController {
  @Autowired
  IpavTopicService ipavTopicService;
  @Autowired
  IpavcommReplyService tpavcommReplyService;
  String userid = "1000119002";
    /**
     * 查询所有公开帖子
     * @param userid 
     * @return
     */
	@RequestMapping(value = "/queryTopics")
	@ResponseBody
	public JSONArray queryTopics() {
		List<Map> mapList = ipavTopicService.queryTopics(userid); 
		JSONArray rtn = (JSONArray) JSONArray.toJSON(mapList);
		return rtn;
	}
	
	 /**
     * 查询我发起的帖子
     * @param userid 
     * @return
     */
	@RequestMapping(value = "/queryTopicsByUserId")
	@ResponseBody
	public JSONArray queryTopicsByUserId() {
		List<Map> mapList = ipavTopicService.queryTopicsByUserId(userid);
		JSONArray rtn = (JSONArray) JSONArray.toJSON(mapList);
		return rtn;
	}
	
	 /**
     * 查询我评论或者赞的帖子
     * @param userid 
     * @return
     */
	@RequestMapping(value = "/queryTopicsByComment")
	@ResponseBody
	public JSONArray queryTopicsByComment() {
		List<Map> mapList = ipavTopicService.queryTopicsByComment(userid);
		JSONArray rtn = (JSONArray) JSONArray.toJSON(mapList);
		return rtn;
	}
	
	 /**
     * 查询我的帖子(我评论或者赞的帖子和我发起的)
     * @param userid 
     * @return
     */
	@RequestMapping(value = "/queryTopicsByUserIdAndComment")
	@ResponseBody
	public JSONArray queryTopicsByUserIdAndComment() {
		List<Map> mapList = ipavTopicService.queryTopicsByUserIdAndComment(userid);
		JSONArray rtn = (JSONArray) JSONArray.toJSON(mapList);
		return rtn;
	}
	
	 /**
	  * 添加帖子
	  * @param topicStr
	  * @param session
	  * @return
	  */
	@RequestMapping(value = "/addTopic")
	@ResponseBody
	public String addTopic(String topicStr, HttpSession session ) {
		IpavTopicEntity topic = JsonToObjectUtil.jsonToBean(topicStr,
										IpavTopicEntity.class);
		//IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		//String userid = user.getUserid();
		topic.setUserid(userid); 
		String rtn= ipavTopicService.InsertTopic(topic); 
		return  JSONArray.toJSONString(rtn);
	}
	 
	/**
	 * 修改帖子
	 * @param topicStr
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updateTopic")
	@ResponseBody
	public String updateTopic(String topicStr, HttpSession session ) {
		IpavTopicEntity topic = JsonToObjectUtil.jsonToBean(topicStr,
										IpavTopicEntity.class);
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		String userid = user.getUserid();
		topic.setUserid(userid); 
		ipavTopicService.updateTopic(topic);
		return ""; 
	}
	
	 /**
	  * 根据id得到单个帖子
	  * @param topicStr
	  * @param session
	  * @return
	  */
	@RequestMapping(value = "/queryTopicById")
	@ResponseBody
	public JSON queryTopicById(Integer tid, HttpSession session ) {	 	 
		Map map  = ipavTopicService.queryTopicById(tid,userid);
		JSON rtn = (JSON) JSON.toJSON(map);
		return rtn; 
	}
	
	/**
	 * 删除帖子
	 */
	@RequestMapping(value = "/delTopic")
	@ResponseBody
	public String delTopic(Integer tid) {  
		ipavTopicService.delTopic(tid);
		return ""; 
	}
	
	/**
	 * 添加或者删除赞
	 */
	@RequestMapping(value = "/addOrDelpraise")
	@ResponseBody
	public JSON addOrDelpraise(String commentStr,
			HttpSession session) {
		IpavcommentEntity comment = JsonToObjectUtil.jsonToBean(commentStr,
												IpavcommentEntity.class);
		comment.setCommuserid(userid);
		tpavcommReplyService.addComment(comment);
		Map map= ipavTopicService.queryPariseByAid(comment.getActionid());
		JSON rtn = (JSON) JSON.toJSON(map);
		return rtn; 
	}
	/**
	 * 添加评论
	 */
	@RequestMapping(value = "/addComment")
	@ResponseBody
	public JSON addComment(String commentStr,
			HttpSession session) {
		IpavcommentEntity comment = JsonToObjectUtil.jsonToBean(commentStr,
				IpavcommentEntity.class);
		comment.setCommuserid(userid);
		tpavcommReplyService.addComment(comment);		 
		return queryCommentByAid(comment.getActionid().intValue()); 
	}
	
	
	/**
	 * 根据帖子id得到评论
	 */ 
	@RequestMapping(value = "/queryCommentByAid")
	@ResponseBody
	public JSON queryCommentByAid(int tid) {
		Map map  = ipavTopicService.queryCommentByAid(tid);
		JSON rtn = (JSON) JSON.toJSON(map);
		return rtn; 
		 
	}
 
	
}

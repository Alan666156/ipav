package com.ipav.infoshare.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipav.system.dao.CommentReplyMapper;
import com.ipav.system.dao.TopicImageMapper;
import com.ipav.system.dao.TopicMapper;
import com.ipav.system.dao.UserMapper;
import com.ipav.system.entity.IpavTopicEntity;
import com.ipav.system.entity.IpavTopicImageEntity;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavreplyEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.util.FormatUtil;

@Service
public class IpavTopicService {

	@Autowired
	TopicMapper topicMapper;
	@Autowired
	TopicImageMapper imageMapper;
	@Autowired
	CommentReplyMapper commentReplyMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	IpavcommReplyService commReplyService;

	/**
	 * 查询全部帖子
	 * 
	 * @return
	 */
	public List queryTopics(String userid) {
		List<IpavTopicEntity> topics = topicMapper.queryTopics();
		List<Map> rtnList = TopicsToMap(topics,userid);		
		return rtnList;
	}
	
	/**
	 * 查询我发起的帖子
	 */
	public List queryTopicsByUserId(String userid) {
		List<IpavTopicEntity> topics = topicMapper.queryTopicsByUserId(userid);
		List<Map> rtnList = TopicsToMap(topics,userid);
		return rtnList;
	}
   
	/**
	 * 查询我评论的帖子
	 */
	public List queryTopicsByComment(String userid) {
		List<IpavTopicEntity> topics =  new ArrayList<IpavTopicEntity>();
		topics=queryTopicsByComment(userid,topics);			
		List<Map> rtnList = TopicsToMap(topics, userid);		
		return rtnList;
	}
	
	
	/**
	 * 查询我的帖子
	 */	
	public List queryTopicsByUserIdAndComment(String userid){
		//查询我发起的帖子
		List<IpavTopicEntity> topics=topicMapper.queryTopicsByUserId(userid);
		//添加评论过的帖子 	 
		topics=queryTopicsByComment(userid,topics);	
		//排序(根据创建时间)
		Collections.sort(topics, new Comparator<IpavTopicEntity>() { 
		 public int compare(IpavTopicEntity a, IpavTopicEntity b) { 			 
          return a.getCreatetime().compareTo(b.getCreatetime());	
		 }});
		List<Map> rtnList=TopicsToMap(topics, userid);		 		
	    return rtnList;
	 }

	/**
	 * 根据帖子id查询帖子信息
	 * 
	 * @param topicid
	 * @return
	 */
	public Map queryTopicById(Integer topicid,String userid) {
		IpavTopicEntity topic = topicMapper.queryTopicById(topicid);
		Map map = new HashMap<String, Object>();
		if(topic!=null){
			map.put("msg", "success");
			map.put("topic", topic);
		}else{
			map.put("msg", "success");
		}		 
		return map;
	}

	/**
	 * 插入一张帖子(包括帖子信息和图片信息)
	 * 
	 * @param topic
	 */
	@Transactional
	public String InsertTopic(IpavTopicEntity topic) {
		 
		if(topic.getTopiccontent().trim() =="")return   "topiccontent";
	 if(topic.getTopictitle().trim() =="")return "topictitle";
		 
		topic.setCreatetime(FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss")); 
		for (IpavTopicImageEntity image : topic.getImages()) {
			image.setCreatedate(FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			imageMapper.insertTopicImage(image);
		}
		topicMapper.insertTopic(topic);
		return  "success";
		} 
	

	/** 
	 * 更新帖子信息
	 * @param topic
	 */
	@Transactional
	public void updateTopic(IpavTopicEntity topic) {
		topic.setCreatetime(FormatUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		IpavTopicEntity oldTopic = topicMapper.queryTopicById(topic.getTid());
		if (oldTopic != null && oldTopic.getImages().size() != 0) {
			for (IpavTopicImageEntity image : oldTopic.getImages()) {
				imageMapper.deleteImageById(image.getImageid());
			}
		}
		topicMapper.updateTopic(topic);
		for (IpavTopicImageEntity image : topic.getImages()) {
			image.setCreatedate(FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			imageMapper.insertTopicImage(image);
		}
	}

	/**
	 * 删除帖子
	 */ 
	@Transactional
	public void delTopic(Integer tid) { 
		 topicMapper.delTopic(tid);	 	 
	}
	
	/**
	 * 根据帖子id查询评论
	 * @param tid
	 * @return
	 */
	public Map queryCommentByAid(Integer tid){ 
	Map rtn =new HashMap<String,Object>();
	Map<String,Object> pmap=new HashMap<String, Object>();
	pmap.put("tid",tid);
	pmap.put("actiontype",4); 
	pmap.put("commtype",1);  
	List<IpavcommentEntity> commList=topicMapper.queryCommentByAid(pmap); 
	if(commList!=null && commList.size()> 0){
		for(int i =0;i<commList.size();i++){
		IpavcommentEntity comment= commList.get(i);
		if(comment.getCommtype()==1 && comment.getReplys().size() > 0){
		for(IpavreplyEntity reply : comment.getReplys()){				
			reply.setBereplyname(seachNameByUid(reply.getBereplyid()));			 
			reply.setReplyusername(seachNameByUid(reply.getReplyuserid()));			
		} 
		}  
	}	
		} 
	  rtn.put("commList",commList);
	  rtn.put("tid",tid);
	  rtn.put("size",queryPraiseOrCommentCount(tid.longValue(),1));
	return rtn;
	}
	
	/**
	 * 根据帖子id查询赞人名
	 * @param tid
	 * @return
	 */
	public Map queryPariseByAid(Long tid){   
	Map rtn =new HashMap<String,Object>();
	List<Map> listMap=new ArrayList<Map>();
	Map<String,Object> pmap=new HashMap<String, Object>();
	pmap.put("tid",tid);
	pmap.put("actiontype",4); 
	pmap.put("commtype",0);  
	List<IpavcommentEntity> commList=topicMapper.queryCommentByAid(pmap); 
	if(commList!=null && commList.size()> 0){
		for(int i =0;i<commList.size();i++){
			IpavcommentEntity comment= commList.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userid",comment.getCommuserid());
			map.put("username",comment.getCommusername());
			listMap.add(map);			
		}  
	}
	rtn.put("userList",listMap);
	rtn.put("size",queryPraiseOrCommentCount(tid,0));
	return rtn;
	
} 
	  
	
	/**
	 * 得到赞或者评论的数量
	 */
	public Integer queryPraiseOrCommentCount(Long tid,
			                                 Integer commtype){	 
		int size = commReplyService.queryPraiseOrCommentCount(4,tid,commtype);
		return size;
	}
	
	/**
	 * 将帖子信息集合转换成map
	 * 
	 * @param topics
	 * @return
	 */
	private List<Map> TopicsToMap(List<IpavTopicEntity> topics,String userid) {
		List<Map> rtnList = new ArrayList<Map>();
		for (IpavTopicEntity topic : topics) {
			Map<String, Object> map = TopicToMap(topic, userid);
			rtnList.add(map);
		}
		return rtnList;
	}

	/**
	 * 将帖子信息转换成map ,并添加赞数量和评论数量
	 * 
	 * @param topic
	 * @return
	 */
	private Map TopicToMap(IpavTopicEntity topic,String userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		IpavcommentEntity comment =new IpavcommentEntity();
		comment.setActiontype(4);
		comment.setActionid(topic.getTid().longValue()); 
		comment.setCommtype(0);
		comment.setCommuserid(userid.toString());  
		int praiseSize = commReplyService.queryPraiseOrCommentCount(4,topic.getTid().longValue(),0);
		int commentSize = commReplyService.queryPraiseOrCommentCount(4,topic.getTid().longValue(),1);
		int isoPraise = commReplyService.queryIsPraiseOrComment(4,topic.getTid().longValue(),1,userid,0);
		comment.setCommtype(1);		 
		 
		if(topic.getUserid().equals(userid)){
		map.put("isMyTopic", "Y");
		}else{
		map.put("isMyTopic", "N");			
		}
		//赞人数
		map.put("praiseSize", praiseSize);
		//评论人数
		map.put("commentSize", commentSize);
		//是否赞0否 1 是
		map.put("isoPraise", isoPraise);		
		map.put("topic", topic);
		return map;
	}
	
	/**
	 * 查询用户赞或者评论过的帖子
	 */
	private List<IpavTopicEntity> queryTopicsByComment(String userid,List<IpavTopicEntity> topics){
		IpavcommentEntity comment=new IpavcommentEntity();  		
		comment.setActiontype(4);
		comment.setCommuserid(userid.toString()); 
		List<String> list=commentReplyMapper.queryActionIdList(comment);
		if(list!=null && list.size()>0){
			for(String id:list){
		     boolean flag=true;
			 IpavTopicEntity topic
			        =topicMapper.queryTopicById(Integer.parseInt(id));
			   for(IpavTopicEntity top: topics){
				   if(top.getTid()== topic.getTid()){
					   flag=false;
					   break;
				   }
			   }
			   if(flag == true){
				   topics.add(topic);			   
			   }
			}
			}	
		return topics;
	}
	
	/**
	 * 根据用户id查询用户名
	 */
	public String seachNameByUid(String uid){
		if(uid==null) return "";
		Map map=new HashMap<String,String>();		
		map.put("userid", uid);
		IpavuserEntity user=userMapper.getUserByUniKey(map);
		if(user==null) return "";
		return user.getUsername();	 
	}
	}

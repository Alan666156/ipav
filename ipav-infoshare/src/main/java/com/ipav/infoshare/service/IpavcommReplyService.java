package com.ipav.infoshare.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipav.system.dao.CommentReplyMapper;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavreplyEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;

@Service
public class IpavcommReplyService {

	@Autowired
	private CommentReplyMapper commReplyMapper;
	@Autowired
	private IpavuserService userService;
	/***
	 * 添加赞/评论
	 * 
	 * @param comment
	 */
	public int addComment(IpavcommentEntity comment) {
		int resultint=0;
		comment.setCommdate(FormatUtil.formatDate(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		//comment.setActiontype(0);
		//赞的时候
		if (comment.getCommtype() == 0) {
			int count = queryIsPraiseOrComment(comment.getActiontype(),
					comment.getActionid(),comment.getCommtype(),comment.getCommuserid(),null);
			if (count == 0) {// 该用户对该说说没有点过赞
				resultint=commReplyMapper.insertComment(comment);
			} else if (count >0) {// 该用户对该说说已经点过赞了,此时应执行取消赞功能
				resultint=commReplyMapper.updateLike(comment);
			}
		} else {
			resultint=commReplyMapper.insertComment(comment);
		}
		return resultint;
	}

	/***
	 * 添加回复
	 * 
	 * @param reply
	 */
	public int addReply(IpavreplyEntity reply) {
		reply.setReplydate(FormatUtil.formatDate(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		return commReplyMapper.insertReply(reply);
	}
	
	/**
	 * 得到赞或者评论的数量
	 * @param actiontype:1为说说 2为公告  3为帖子
	 * @param actionid :说说/公告/帖子 id
	 * @param commtype:0为赞  1为评论
	 * @return
	 */


	public int queryPraiseOrCommentCount(Integer actiontype,Long actionid,
			                                 Integer commtype){
		Map<String,Object> map=new HashMap<String, Object>();	 
		map.put("actiontype",actiontype);
		map.put("actionid",actionid);
		map.put("commtype",commtype); 
		Integer size = commReplyMapper.queryCommentCount(map);
		return size==null?0:size;
	}
	/**
	 * 查询用户是否已赞或者已经评论
	 * @param actiontype:1为说说 2为公告  3为帖子
	 * @param tid :说说/公告/帖子 id
	 * @param commtype:0为赞  1为评论
	 * @param userid 用户id
	 * @return 0未赞  1已赞
	 */

	public Integer queryIsPraiseOrComment(Integer actiontype,Long actionid,
            Integer commtype,String userid,Integer isdelete){
	Map<String,Object> map=new HashMap<String, Object>();	 
	map.put("actiontype",actiontype);
	map.put("actionid",actionid);
	map.put("commtype",commtype);
	map.put("commuserid",userid);  
	map.put("isdelete",isdelete);  
	Integer isoPraise = commReplyMapper.queryLikeCount(map);
	return isoPraise==null?0:isoPraise;
	}
	/**
	 * 根据活动id得到评论及回复
 	 * @param actiontype:1为说说 2为公告  3为帖子
	 * @param actionid :说说/公告/帖子 id
	 * @param commtype:0为赞  1为评论
	 */
	 public List queryCommentById(Integer actiontype,Long actionid,
	            Integer commtype,Integer pageNo,Integer pageSize){
		 Map<String,Object> map=new HashMap<String, Object>();	 
			map.put("actiontype",actiontype);
			map.put("actionid",actionid);
			map.put("commtype",commtype); 
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize); 
		List<IpavcommentEntity> list = commReplyMapper.queryCommentByAid(map);
		for(IpavcommentEntity comment:list){
			comment =CommentByComment(comment);
		}
		 return list;
	 }
	 
/**
 * 给评论用户 和回复用户取名
 */
public IpavcommentEntity CommentByComment(IpavcommentEntity comment){
	if(comment!=null){
	IpavuserEntity u=userService.queryUserId(comment.getCommuserid());
	if(u!=null){
		comment.setCommusername(u.getUsername());
		comment.setCommuserimg(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+u.getPicpath());
	 }
	  u=null;
	for(IpavreplyEntity reply:comment.getReplys()){
		u=userService.queryUserId(reply.getBereplyid());
		if(u!=null){
		reply.setBereplyname(u.getUsername());
	    }
		u=null;
	    u=userService.queryUserId(reply.getReplyuserid());
		if(u!=null){
			reply.setReplyusername(u.getUsername());
			 reply.setReplyuserimg(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+u.getPicpath());
		}
	}
	return comment;
	}return null;
}
 /**
  * 查询出赞过的用户名id  
  * @param actionid
  * @param actiontype
  * @return
  */
public List<String> queryUserListByActionId(Long actionid,Integer actiontype,Integer pageNo,Integer pageSize,Integer tip){
	if(tip==0){
	    //需要分页	
		if(pageNo==null)pageNo=1;		 
		if(pageSize==null)pageSize=20;
		pageNo=(pageNo-1)*pageSize;
		
	}
	Map<String,Object> map=new HashMap<String, Object>();
		map.put("actionid", actionid);	
		map.put("actiontype", actiontype);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("commtype", 0);
	  List<String> list=commReplyMapper.queryUserListByActionId(map);
	  return list;
}
 /**
  * 根据评论id得到评论
  * @param Commentid
  * @return
  */
public IpavcommentEntity queryCommentByCommentid(Long Commentid,Integer pageNo,Integer pageSize){
	Map map=new HashMap<String,Object>();
	map.put("commentid",Commentid);
	map.put("pageNo",pageNo);
	map.put("pageSize",pageSize);
	IpavcommentEntity comment= commReplyMapper.queryCommentByCommentid(map);
	return CommentByComment(comment);
}
/**
 * 查询回复数
 * @param commentid
 * @return
 */
public int queryReplyCount(Long commentid){
     Integer count= commReplyMapper.queryReplyCount(commentid);
	return count==null?0:count;
}
 
/**
 * 转换时间
 * @param date
 * @return
 * @throws ParseException
 */
public static String DateToString(String date) {
	 SimpleDateFormat  df=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    Date d;
    try {
		d = df.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(new Date());
	   int day=newDate.get(Calendar.DAY_OF_YEAR)-calendar.get(Calendar.DAY_OF_YEAR);
	   int year =newDate.get(Calendar.YEAR )-calendar.get(Calendar.YEAR);
	   String head="";
	   if(day==0 && year==0){
		   head="今天";
	   }else if(day==1&&year==0){
		   head="昨天";
	   }else if(day==2&&year==0){
    	head="前天";
       }else{
    	   head=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"
 			   +calendar.get(Calendar.DAY_OF_MONTH); 
       }
		 
		
		String body="";
		if(calendar.get(Calendar.HOUR_OF_DAY)<5){
			body="凌晨";
		}else if(calendar.get(Calendar.HOUR_OF_DAY)<11)
		{
			body="上午";
		}else if(calendar.get(Calendar.HOUR_OF_DAY)<13)
		{
			body="中午";
		}else if(calendar.get(Calendar.HOUR_OF_DAY)<19)
		{
			body="下午";
		}else  
		{
			body="晚上";
		}
		String MINUTE= calendar.get(Calendar.MINUTE)>9?(calendar.get(Calendar.MINUTE)+""):("0"+calendar.get(Calendar.MINUTE));
		String HOUR= calendar.get(Calendar.HOUR_OF_DAY)>9?(calendar.get(Calendar.HOUR_OF_DAY)+""):("0"+calendar.get(Calendar.HOUR_OF_DAY));
		return head+" "+body+" "+HOUR+":"+MINUTE; 
	} catch (ParseException e) {
		return date;
	}  
}	
}

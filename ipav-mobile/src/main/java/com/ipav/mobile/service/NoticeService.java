package com.ipav.mobile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ipav.infoshare.service.IpavcommReplyService;
import com.ipav.infoshare.service.IpavnoticeService;
import com.ipav.mobile.param.RequestParameter;
import com.ipav.mobile.param.ResponseParameter;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavnoticeEntity;
import com.ipav.system.entity.IpavnoticeSendedEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.util.FormatUtil;

@Service
public class NoticeService {
	@Autowired
	private IpavnoticeService noticeService;
	@Autowired
	private IpavcommReplyService commReplyService;
	
	/**
	 * 查询全部公告列表
	 */
	public ResponseParameter queryNotices(RequestParameter param, int service){
		ResponseParameter response=new ResponseParameter(service);
		List<Map<String,Object>> rsmap=new ArrayList<Map<String,Object>>();
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		Map<String,Object> parm = new HashMap<String,Object>();
		String userid=obj.getString("userid");
		parm.put("userid", userid);
		parm.put("beginRow", obj.getString("beginRow"));
		parm.put("pageSize", obj.getString("pageSize"));
		List<IpavnoticeEntity>  noticelist = noticeService.getNoticeList(parm);
		for (IpavnoticeEntity notice : noticelist) {
			parm.clear();
			parm.put("noticeid",notice.getId());		
			parm.put("isread",1);
			parm.put("isdelete",0);
			parm.put("userid",userid);
			Map<String,Object> map = new HashMap<String,Object>();
			//当前用户对此公告是否已读
			map.put("isRead", noticeService.getSendedCnt(parm));
			map.put("id", notice.getId());
			map.put("title", notice.getTitle());
			map.put("text",notice.getContentText());
			map.put("createtime",FormatUtil.formatDate(notice.getCreatedate()));
			rsmap.add(map);
		}
		response.setBody(rsmap);
		return response;
	}
	
	/**
	 * 查看公告详情
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter getNoticeById(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject body=new JSONObject();
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		Map<String,Object> parm = new HashMap<String,Object>();
		String userid=obj.getString("userid");
		String noticeid=obj.getString("noticeid");
		parm.put("noticeid", noticeid);
		IpavnoticeEntity notice = noticeService.getNoticeByParm(parm);
		noticeService.modifyName(notice);
		parm.put("isread",1);
		parm.put("isdelete",0);
		body.put("readecnt", noticeService.getSendedCnt(parm));
		
		body.put("isPraise",commReplyService.queryIsPraiseOrComment(2, notice.getId(), 0,userid, 1));
		body.put("likecnt", commReplyService.queryPraiseOrCommentCount(2, notice.getId(), 0));
		body.put("commcnt",  commReplyService.queryPraiseOrCommentCount(2, notice.getId(), 1));
		
		body.put("id", notice.getId());
		body.put("title",notice.getTitle());
		
		parm.clear();
		parm.put("nid", notice.getId());
		parm.put("userid", notice.getUserid());
		parm.put("typeid", notice.getTypeid());
		Map<String,String> nomap=noticeService.getNoticeNo(parm);
		String noticenostr=nomap.get("companyname")+nomap.get("typename")+nomap.get("nostr").replace("{0}", nomap.get("noindex"));					
		body.put("number",noticenostr);
		
		body.put("createtime",FormatUtil.formatDate(notice.getCreatedate()));
		body.put("content",notice.getContent());
		body.put("issuedname",notice.getIssuedname());
		body.put("orgname",notice.getOrgname());
		body.put("createuser",notice.getUsername());
		response.setBody(body);
		return response;
	}
	
	/**
	 * 查询公告所有评论集合
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter queryComments(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject body=new JSONObject();
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		String noticeid=obj.getString("noticeid");
		Integer beginRow=obj.getInteger("beginRow");
		Integer pageSize=obj.getInteger("pageSize");
		createComms(body,noticeid,beginRow,pageSize);
		response.setBody(body);
		return response;
	}

	/**
	 * 生成评论数据集合
	 * @param body
	 * @param noticeid
	 * @param beginRow
	 * @param pageSize
	 * @return
	 */
	private JSONObject createComms(JSONObject body,String noticeid,Integer beginRow,Integer pageSize){
		List<IpavcommentEntity> list=commReplyService.queryCommentById(2, Long.parseLong(noticeid),1,beginRow,pageSize);
		List<Map<String,Object>> rsmap=new ArrayList<Map<String,Object>>();
		for(IpavcommentEntity comment :list){  
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userid",comment.getCommuserid());
			map.put("username",comment.getCommusername());
			map.put("userimg",comment.getCommuserimg());
			map.put("content",comment.getCommcontent());
			map.put("datetime",FormatUtil.formatDate(comment.getCommdate()));
			rsmap.add(map);
		}
		body.put("commlist", rsmap);
		body.put("commcnt",commReplyService.queryPraiseOrCommentCount(2,Long.parseLong(noticeid), 1));
		return body;
	}
	/**
	 * 查询公告所有已读人数集合
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter queryReades(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject body=new JSONObject();
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		String noticeid=obj.getString("noticeid");
		Integer beginRow=obj.getInteger("beginRow");
		Integer pageSize=obj.getInteger("pageSize");
		Map<String,Object> parm = new HashMap<String,Object>();
		parm.put("noticeid",noticeid);
		parm.put("isread", 1);
		parm.put("beginRow", beginRow);
		parm.put("pageSize", pageSize);
		List<IpavnoticeSendedEntity> sendslist = noticeService.getNoticeSended(parm);
		List<Map<String,Object>> rsmap=new ArrayList<Map<String,Object>>();
		for(IpavnoticeSendedEntity ns:sendslist){
			IpavuserEntity user=noticeService.getUserById(ns.getUserid());
			if(user!=null){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userid", user.getUserid());
				map.put("username", user.getUsername());
				map.put("userimg", user.getPicpath());
				rsmap.add(map);
			}
		}
		body.put("readelist", rsmap);
		parm.clear();
		parm.put("noticeid",noticeid);		
		parm.put("isread",1);
		parm.put("isdelete",0);
		body.put("readecnt", noticeService.getSendedCnt(parm));
		response.setBody(body);
		return response;
	}

	/**
	 * 查询公告所有已点赞人数集合
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter queryLikes(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject body=new JSONObject();
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		String noticeid=obj.getString("noticeid");
		Integer beginRow=obj.getInteger("beginRow");
		Integer pageSize=obj.getInteger("pageSize");
		createLikes(body,noticeid,beginRow,pageSize);
		response.setBody(body);
		return response;
	}

	/**
	 * 生成点赞数据集合
	 * @param body
	 * @param noticeid
	 * @param beginRow
	 * @param pageSize
	 * @return
	 */
	private JSONObject createLikes(JSONObject body,String noticeid,Integer beginRow,Integer pageSize){
		List<String> list=commReplyService.queryUserListByActionId(Long.getLong(noticeid),2,null,null,0);
		List<Map<String,Object>> rsmap=new ArrayList<Map<String,Object>>();
		for(String i: list){
			IpavuserEntity user =noticeService.getUserById(i);
			if(user!=null){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userid", user.getUserid());
				map.put("username",user.getUsername());
				map.put("userimg", user.getPicpath());
				rsmap.add(map);
			}
		}	
		body.put("likelist", rsmap);
		body.put("likecnt", commReplyService.queryPraiseOrCommentCount(2, Long.parseLong(noticeid), 0));
		return body;
	}
	/**
	 * 添加评论或点赞
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter addCommOrLike(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject body=new JSONObject();
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		String noticeid=obj.getString("noticeid");
		String commuserid=obj.getString("commuserid");
		String commcontent=obj.getString("commcontent");
		int commtype=obj.getInteger("commtype");
		IpavcommentEntity comment= new IpavcommentEntity();
		comment.setActionid(Long.parseLong(noticeid));
		comment.setActiontype(2);//活动类型2:公告
		comment.setCommuserid(commuserid);
		comment.setCommcontent(commcontent);
		comment.setCommtype(commtype);
		comment.setIsdelete(0);
		body.put("result",commReplyService.addComment(comment));
		if(commtype==0){//0:代表点赞操作;
			createLikes(body,noticeid,0,10);
		}else if(commtype==1){//1:代表评论操作;
			createComms(body, noticeid, 0, 10);
		}
		response.setBody(body);
		return response;
	}
	
}

package com.ipav.vote.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.ipav.vote.service.IpavvoteService;
import com.ipav.vote.util.ContentUtil;

public class VoteAction {

	/**
	 * 发布投票活动
	 * @param param
	 * @param voteItems
	 * @param userAuthorList
	 * @param voteService
	 * @param userId
	 * @param oldId
	 * @return
	 */
	@Transactional
	public int publishVote(Map<String,Object> param,List<Map<String,Object>> voteItems,List<Map<String,Object>> userAuthorList,IpavvoteService voteService,String userId,int oldId,Set<String> oldPicSet){
		int result=0;
		if(!param.containsKey("isAnonymout"))
			param.put("isAnonymout", 0);
		if(!param.containsKey("ext"))
			param.put("ext", "");
		if(!param.containsKey("startTime")){
			param.put("startTime", "");
			param.put("status", ContentUtil.VOTE_STATUS_STARTING);
		}else 
			param.put("status", ContentUtil.VOTE_STATUS_TOBESTART);
		if(param.get("type").toString().equals("3")){
			param.put("criteria", param.get("minNum"));
		}
		voteService.insertVote(param, voteItems,userAuthorList,oldId,oldPicSet);
		result=1;
		return result;
	}
	
	/**
	 * 获取投票活动信息
	 * @param voteService
	 * @param param
	 * @param type 0-列表,1-详情,2-结果
	 * @return
	 */
	public List<Map<String,Object>> getVoteInfos(IpavvoteService voteService,Map<String,Object> param,int type){
		List<Map<String,Object>> result=null;
		if(type==0)
			result=voteService.getVoteInfoList(param);
		else{
			result=new ArrayList<Map<String,Object>>();
			result.add(voteService.getVoteDetails(Integer.parseInt(param.get("id").toString()),type,param.get("userId").toString()));
		}
		return result;
	}
	
	/**
	 * 获取投票报表
	 * @param voteService
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getVoteStatistic(IpavvoteService voteService,Map<String,Object> param){
		return voteService.getVoteStatistic(param);
	}
	
	/**
	 * 获取投票报表记录数
	 * @param voteService
	 * @param param
	 * @return
	 */
	public int getVoteStatisticCounts(IpavvoteService voteService,Map<String,Object> param){
		return voteService.getVoteStatisticCounts(param);
	}
}

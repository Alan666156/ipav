package com.ipav.mobile.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.mobile.param.RequestParameter;
import com.ipav.mobile.param.ResponseParameter;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavuserService;
import com.ipav.vote.action.VoteAction;
import com.ipav.vote.service.IpavvoteService;

@Service
public class VoteService {
	
	@Autowired
	private IpavvoteService voteService;
	
	@Autowired
	private IpavuserService userService;

	/**
	 * 查看工资
	 * @param requestParameter
	 * @param service
	 * @return
	 * 
	 */
	public ResponseParameter getVoteList(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject body=new JSONObject();
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		Map<String,Object> voteParam=new HashMap<String, Object>();
		IpavuserEntity user=userService.queryUserId(obj.getString("userId"));
		if(user==null){
			body.put("result", 0);
			body.put("content","用户不存在");
		}else
			obj.put("companyId", user.getCompanyid());
		List<Map<String,Object>> infos=null;
		if(!obj.containsKey("id")){
			int count=voteService.getVoteInfoCounts(JSON.parseObject(obj.toJSONString(), Map.class));
			if(count>0)
				infos=new VoteAction().getVoteInfos(voteService, JSON.parseObject(obj.toJSONString(), Map.class), 0);
			body.put("count", count);
			body.put("infos", infos);
		}else{
			int type=obj.getIntValue("type");
			obj.remove("type");
			Map<String,Object> details=voteService.getVoteDetails(obj.getIntValue("id"),type,obj.getString("userId"));
			body.put("detail", details);
		}
		result.setBody(body);
		return result;
	}
	
	/**
	 * 投票
	 * @param param(voteId-投票活动id,userId-投票人id,idScoreArr-选项id及投票内容)
	 * @param service
	 * @return
	 */
	public ResponseParameter cast(RequestParameter param,int service){
		ResponseParameter result=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject();
		Map<String,Object> castParam=new HashMap<String, Object>();
		castParam.put("voteId", obj.get("voteId"));
		castParam.put("userId", obj.get("userId"));
		
		List<Map<String,Object>> authorList=voteService.getAuthorList(castParam);
		int success=0;
		if(authorList!=null&&authorList.size()==1){
			int author=Integer.parseInt(authorList.get(0).get("author").toString());
			if(author==1||author==3)
				success=1;
		}
		if(success==1){
			JSONArray arr=JSONArray.parseArray(obj.get("idScoreArr").toString());
			List<Map> list=JSON.parseArray(obj.get("idScoreArr").toString(), Map.class);
			castParam.put("counts", list.size());
			castParam.put("list", list);
			if(voteService.insertVoteRecord(castParam)==0){
				body.put("result", 0);
				body.put("content","投票失败");
			}
		}else{
			body.put("result", 0);
			body.put("content","你没有投票权限");
		}
		return result;
	}
}

package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavsayEntity;

public interface SayMapper {
    //根据公司id查询同事圈所有动态
	public List<IpavsayEntity> querySayList(Map map);
	//查询我发布的说说
	public List<IpavsayEntity> querymyReleaseSays(Map map);
	//app请求同事圈所有动态
	public List<IpavsayEntity> appQuerySayList(Map map);
    //查询用户说说(包括用户发布的和被回复的)
	public List<Map<String,Object>> queryUserSayList(Map map);
	//查询回复用户的说说动态
	//public List<IpavsayEntity> queryUserSayListByReply(String userid); 
	//根据说说id查询说说
	public IpavsayEntity querySayById(Long sayid);
	
	
    //插入一条说说
	public void insertSay(IpavsayEntity say);
	//根据说说id查询评论
	public List<Map<String, Object>> queryUserSayList(Long sayid);
	//删除说说	
	public int deleteSay(Long sayid); 
    //添加说说指定用户
	public void insertSayUsers(Map parm);
    
	// public void insertComment(IpavcommentEntity comment);
	//
	// public void insertReply(IpavreplyEntity reply);
	//
	// public int queryLikeCount(IpavcommentEntity comment);
	//
	// public void updateLike(IpavcommentEntity comment);

}

package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavreplyEntity;

public interface CommentReplyMapper {

	public int insertComment(IpavcommentEntity comment);

	public int insertReply(IpavreplyEntity reply);

	public Integer queryLikeCount(Map map);

	public int updateLike(IpavcommentEntity comment);

	// 查询赞或者评论的人数
	public Integer queryCommentCount(Map map);
	
	//查询回复条数
	public Integer queryReplyCount(Long commentid);

	// 根据活动id得到评论
	public List<IpavcommentEntity> queryCommentByAid(Map map);
	
	//查询赞的人id
	public List<String> queryUserListByActionId(Map map);
	
	//
	public IpavcommentEntity queryCommentByCommentid(Map map);
	
	// 查询actionid集合
	public List<String> queryActionIdList(IpavcommentEntity comment);
}

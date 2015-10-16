package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavTopicEntity;
import com.ipav.system.entity.IpavcommentEntity;

public interface TopicMapper {
	//查询全部帖子
	public List<IpavTopicEntity> queryTopics();
    //查询用户贴
	public List<IpavTopicEntity> queryTopicsByUserId(String userid);
    //根据帖子id查询帖子
	public IpavTopicEntity queryTopicById(Integer topicId);
	//插入帖子
    public void insertTopic(IpavTopicEntity topic);
    //更新帖子
	public void updateTopic(IpavTopicEntity topic);
	//删除帖子
	public void delTopic(Integer tid);
	
	//根据帖子id查询评论
	public List<IpavcommentEntity> queryCommentByAid(Map map);
	
}

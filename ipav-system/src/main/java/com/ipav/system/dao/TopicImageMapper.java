package com.ipav.system.dao;

import java.util.List;

import com.ipav.system.entity.IpavTopicImageEntity;

public interface TopicImageMapper {
    //插入帖子图片
	public void insertTopicImage(IpavTopicImageEntity topicImage);
    //根据帖子id查询图片
	public List<IpavTopicImageEntity> queryTopicImageList(Integer topicid);
	//删除帖子图片信息
	public void deleteImageById(Long imageid);
     
}

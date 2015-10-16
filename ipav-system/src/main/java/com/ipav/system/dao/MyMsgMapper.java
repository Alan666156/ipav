package com.ipav.system.dao;

import java.util.List;
import java.util.Map;


public interface MyMsgMapper { 
	//查询未读公告的列表
	public List<Map<String,Object>> queryUnreadNotices(Map map);
	//未读公告数量
	public Integer queryUnreadNoticeCount(String userid);
	//查询未读工资的列表
	public List<Map<String,Object>> quertUnreadWages(Map map);
	//未读工资数量
	public Integer quertUnreadWageCount(String userid);
}

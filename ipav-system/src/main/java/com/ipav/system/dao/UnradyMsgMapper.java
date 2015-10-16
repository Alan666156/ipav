package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ipav.system.entity.IpavUnreadymsgEntity;


public interface UnradyMsgMapper {
   //添加未读信息
  public void insertReadyMsg(IpavUnreadymsgEntity um);  
  //修改
  public void updateActionid(IpavUnreadymsgEntity um);  
  //查询是否存在
  public Integer queryThisExist(IpavUnreadymsgEntity um);  
  //查询未读信息
  public IpavUnreadymsgEntity queryUnreadyMsg(IpavUnreadymsgEntity um);  
  //查询全部动态最新的sid
  public Integer sayAllUnreadyMaxSid(Map map);  
  //查询我的动态最新的时间
  public String sayMyUnreadyNewDate(@Param("userid") String userid);  
}

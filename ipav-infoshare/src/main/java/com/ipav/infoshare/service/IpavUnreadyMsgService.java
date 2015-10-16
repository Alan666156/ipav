package com.ipav.infoshare.service; 

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipav.system.dao.UnradyMsgMapper;
import com.ipav.system.entity.IpavUnreadymsgEntity;

@Service
public class IpavUnreadyMsgService {
   @Autowired
   private UnradyMsgMapper umMapper;
   //添加未读信息
   public void insertReadyMsg(IpavUnreadymsgEntity um){
	   umMapper.insertReadyMsg(um);
   } 
   public void updateActionid(IpavUnreadymsgEntity um) {
	   umMapper.updateActionid(um);
   }
   //查询是否存在
   public Integer queryThisExist(IpavUnreadymsgEntity um){
	   return umMapper.queryThisExist(um);
   }
   
   //查询是否存在
   public IpavUnreadymsgEntity queryUnreadyMsg(IpavUnreadymsgEntity um){
	   return umMapper.queryUnreadyMsg(um);
   }
   
   public Integer sayAllUnreadyMaxSid(Map map){
	   return umMapper.sayAllUnreadyMaxSid(map);
   }
   
   //查询我的动态最新的时间
   public String sayMyUnreadyNewDate(String parm){
	   return umMapper.sayMyUnreadyNewDate(parm);   
   }
   
}

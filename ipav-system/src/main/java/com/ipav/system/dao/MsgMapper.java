package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavcompanyEntity;

public interface MsgMapper {
	
	public long insertMsg(Map<String,Object> map);
	
	public Map<String,Object> getValidateContent(Map<String,Object> map);
	
	public void updateStatus(long id);
}

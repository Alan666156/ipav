package com.ipav.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipav.system.dao.WorkPlatformMapper;

@Service
public class IpavworkPlatformService {
	
	@Autowired
	private WorkPlatformMapper workPlatformMapper;

	public int addPlatform(Map<String,Object> param){
		return this.workPlatformMapper.addPlatform(param);
	}
	
	public int updatePlatform(Map<String,Object> param){
		return this.workPlatformMapper.updatePlatform(param);
	}
	
	public List<Map<String,Object>> queryPlatformInfos(Map<String,Object> param){
		return this.workPlatformMapper.queryPlatformInfos(param);
	}
	
	public int queryPlatformInfosCount(Map<String,Object> param){
		return this.workPlatformMapper.queryPlatformInfosCount(param);
	}
	
	public int updateDelStatus(Map<String,Object> param){
		return this.workPlatformMapper.updateDelStatus(param);
	}
}

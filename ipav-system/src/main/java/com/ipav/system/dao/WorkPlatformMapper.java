package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

public interface WorkPlatformMapper {

	int addPlatform(Map<String,Object> param);
	
	int updatePlatform(Map<String,Object> param);
	
	List<Map<String,Object>> queryPlatformInfos(Map<String,Object> param);
	
	int queryPlatformInfosCount(Map<String,Object> param);
	
	int updateDelStatus(Map<String,Object> param);
}

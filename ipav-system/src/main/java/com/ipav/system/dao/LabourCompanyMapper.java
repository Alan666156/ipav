package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

public interface LabourCompanyMapper {
	
	int addLabourCompany(Map<String,Object> param);
	
	int updateLabourCompany(Map<String,Object> param);
	
	List<Map<String,Object>> queryLabourCompanyInfos(Map<String,Object> param);
	
	int queryLabourCompanyInfosCount(Map<String,Object> param);
	
	int updateDelStatus(Map<String,Object> param);
}

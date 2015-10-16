package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

public interface WageRecordMapper {
	
	public int insertBatch(List<Map<String,Object>> param);
	
	public int updateWageRecord(Map<String,Object> param);
	
	public int updateWageRecordDel(Map<String,Object> param);
	
	public int deleteWageRecord(Map<String,Object> param);
	
	public int updateWageRecordStatus(Map<String,Object> param);
	
	public List<Map<String, Object>> getWageRecords(Map<String,Object> param);
	
	public Map<String, Object> getWageRecordsSum(Map<String,Object> param);
	
	public Map<String, Object> getWageRecordsCount(Map<String,Object> param);
	
	public List<Map<String,Object>> getWageRecordsExtItems(Map<String,Object> param);
	
	int updateWageRecordReaded(Map<String,Object> param);
}

package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

public interface WageTempletMapper {
	
	public int insertBasicWageTemplet(Map<String,Object> param);
	
	public int updateBasicWageTemplet(Map<String,Object> param);
	
	public List<Map<String,Object>> getBasicWageTemplet(Map<String,Object> param);
	
	public int insertExtWageItem(Map<String,Object> param);
	
	public int updateExtWageItem(Map<String,Object> param);
	
	public int updateExtWageItemDel(Map<String,Object> param);
	
	public int updateExtWageItemValid(Map<String,Object> param);
	
	public int updateExtWageItemInvalid(Map<String,Object> param);
	
	public int updateExtWageSequence(Map<String,Object> param);
	
	public int deleteExtWageItemById(int id);
	
	public List<Map<String,Object>> getExtWageItems(Map<String,Object> param);
}

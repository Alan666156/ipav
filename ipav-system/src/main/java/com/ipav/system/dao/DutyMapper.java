package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavdutyEntity;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月8日 上午11:10:05	
 * 上海天道启科电子有限公司
 */
public interface DutyMapper {
//	/**
//	 * 新增职位
//	 * @param duty
//	 */
//	public void dutyAdd(IpavdutyEntity duty);
//	/**
//	 * 修改职位
//	 * @param duty
//	 */
//	public void dutyUpdate(IpavdutyEntity duty);
//	/**
//	 * 删除职位
//	 * @param dutyid
//	 */
//	public void dutyDelete(String dutyid);
//	/***
//	 * 启用禁用职位
//	 * @param map
//	 */
//	public void dutyValset(Map map);
//	
//	public List dutyList(Map map);
	
	/*****************************WYL*************************************/
	/**
	 * 获取职务列表
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> queryDutyList(Map<String,Object> param);
	
	/**
	 * 获取职务数量
	 * @param param
	 * @return
	 */
	int queryDutyCounts(Map<String,Object> param);
	
	/**
	 * 职务插入-批量
	 * @param param
	 * @return
	 */
	int insertDuty(Map<String,Object> param);
	
	/**
	 * 职务状态修改
	 * @param param
	 * @return
	 */
	int updateStatus(Map<String,Object> param);
	
	/**
	 * 职务修改
	 * @param param
	 * @return
	 */
	int updateDuty(Map<String,Object> param);
	
	/**
	 * 获取已存在的职务名
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> getExistDutyName(Map<String,Object> param);
}

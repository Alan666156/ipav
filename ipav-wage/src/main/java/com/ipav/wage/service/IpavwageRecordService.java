
package com.ipav.wage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipav.system.dao.WageRecordMapper;

@Service
@Transactional
public class IpavwageRecordService {
	
	@Autowired
	private WageRecordMapper wageRecordMapper;
	
	/**
	 * 批量插入工资记录
	 * @param param
	 * @return
	 */
	@Transactional
	public int insertBatch(List<Map<String,Object>> param){
		return this.wageRecordMapper.insertBatch(param);
	}
	
	/**
	 * 修改工资记录
	 * @param param
	 * @return
	 */
	public int updateWageRecord(Map<String,Object> param){
		return this.wageRecordMapper.updateWageRecord(param);
	}
	
	/**
	 * 删除工资记录
	 * @param param
	 * @return
	 */
	public int deleteWageRecord(Map<String,Object> param){
		return this.wageRecordMapper.deleteWageRecord(param);
	}
	
	/**
	 * 用户删除工资记录
	 * @param ids
	 * @return
	 */
	public int updateWageRecordDel(Map<String,Object> param){
		return this.wageRecordMapper.updateWageRecordDel(param);
	}
	
	/**
	 * 获取工资记录
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getWageRecords(Map<String,Object> param){
		return this.wageRecordMapper.getWageRecords(param);
	}
	
	/**
	 * 获取工资记录实发应发总额
	 * @param param
	 * @return
	 */
	public Map<String,Object> getWageRecordsSum(Map<String,Object> param){
		return this.wageRecordMapper.getWageRecordsSum(param);
	}
	
	/**
	 * 获取记录总数
	 * @param param
	 * @return
	 */
	public Map<String, Object> getWageRecordsCount(Map<String,Object> param){
		return this.wageRecordMapper.getWageRecordsCount(param);
	}
	
	/**
	 * 工资单更改为发送状态
	 * @param param
	 * @return
	 */
	public int updateWageRecordStatus(Map<String,Object> param){
		return this.wageRecordMapper.updateWageRecordStatus(param);
	}
	
	/**
	 * 获取自定义项工资列表
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getWageRecordsExtItems(Map<String,Object> param){
		return this.wageRecordMapper.getWageRecordsExtItems(param);
	}
	
	/**
	 * 未读工资信息改为已读
	 * @param param
	 * @return
	 */
	public int updateWageRecordReaded(Map<String,Object> param){
		return this.wageRecordMapper.updateWageRecordReaded(param);
	}
}

package com.ipav.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipav.system.dao.DutyMapper;
import com.ipav.system.entity.IpavdutyEntity;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月8日 上午11:26:30	
 * 上海天道启科电子有限公司
 */
@Service
@Transactional
public class IpavdutyService {
	
	@Autowired
	private DutyMapper dutyMapper;
//	/**
//	 * 新增职位
//	 * @param duty
//	 */
//	public void dutyAdd(IpavdutyEntity duty){
//		String tm = FormatUtil.formatDate(new Date(), null);
//		duty.setCreatedate(tm);
//		duty.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
//		dutyMapper.dutyAdd(duty);
//	}
//	/**
//	 * 修改职位
//	 * @param duty
//	 */
//	public void dutyUpdate(IpavdutyEntity duty){
//		dutyMapper.dutyUpdate(duty);
//	}
//	/**
//	 * 删除职位
//	 * @param dutyid
//	 */
//	public void dutyDelete(String dutyid){
//		dutyMapper.dutyDelete(dutyid);
//		
//	}
//	/**
//	 * 批量删除职位
//	 * @param dutyids
//	 */
//	@Transactional
//	public void dutyDelall(String[] dutyids){
//		if(dutyids!=null&&dutyids.length>0){
//			int len =dutyids.length ;
//			for (int i = 0; i <len ; i++) {
//				String dutyid = dutyids[i];
//				dutyMapper.dutyDelete(dutyid);
//			}
//		}
//	}
//	
//	/***
//	 * 启用禁用职位
//	 * @param map
//	 */
//	public void dutyValset(Map map){
//		dutyMapper.dutyValset(map);
//	}
//	
//	public List dutyList(Map map){
//		return dutyMapper.dutyList(map);
//	}
	
	/**********************************WYL***********************************/
	/**
	 * 获取职务列表
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryDutyList(Map<String,Object> param){
		return this.dutyMapper.queryDutyList(param);
	}
	
	/**
	 * 获取职务总数
	 * @param param
	 * @return
	 */
	public int queryDutyCounts(Map<String,Object> param){
		return this.dutyMapper.queryDutyCounts(param);
	}
	
	/**
	 * 职务插入-批量
	 * @param param
	 * @return
	 */
	@Transactional
	public int insertDuty(Map<String,Object> param){
		return this.dutyMapper.insertDuty(param);
	}
	
	/**
	 * 职务状态修改
	 * @param param
	 * @return
	 */
	public int updateStatus(Map<String,Object> param){
		return this.dutyMapper.updateStatus(param);
	}
	
	/**
	 * 职务修改
	 * @param param
	 * @return
	 */
	public int updateDuty(Map<String,Object> param){
		return this.dutyMapper.updateDuty(param);
	}
	
	/**
	 * 获取已存在的职务名
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getExistDutyName(Map<String,Object> param){
		return this.dutyMapper.getExistDutyName(param);
	}
}

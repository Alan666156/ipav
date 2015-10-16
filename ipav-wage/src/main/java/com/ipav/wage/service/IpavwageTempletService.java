package com.ipav.wage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipav.system.dao.WageTempletMapper;

@Service
@Transactional
public class IpavwageTempletService {

	@Autowired
	private WageTempletMapper wageTempletMapper;
	
	/**
	 * 添加工资基本项模板
	 * @param param
	 * @return
	 */
	public int addBasicWageTemplet(Map<String,Object> param){
		return this.wageTempletMapper.insertBasicWageTemplet(param);
	}
	
	/**
	 * 添加工资自定义项
	 * @param param
	 * @return
	 */
	public int addExtWageItem(Map<String,Object> param){
		return this.wageTempletMapper.insertExtWageItem(param);
	}
	
	/**
	 * 更新工资基本项模板
	 * @param param
	 * @return
	 */
	public int updateBasicWageTemplet(Map<String,Object> param){
		return this.wageTempletMapper.updateBasicWageTemplet(param);
	}
	
	/**
	 * 更新工资自定义项
	 * @param param
	 * @return
	 */
	public int updateExtWageItem(Map<String,Object> param){
		return this.wageTempletMapper.updateExtWageItem(param);
	}
	
	/**
	 * 删除工资基本项
	 * @param id
	 * @return
	 */
	public int deleteExtWageItemById(int id){
		return this.wageTempletMapper.deleteExtWageItemById(id);
	}
	
	/**
	 * 获取基本项
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getBasicWage(Map<String,Object> param){
		return this.wageTempletMapper.getBasicWageTemplet(param);
	}
	
	/**
	 * 获取自定义项集合
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getExtWageItems(Map<String,Object> param){
		return this.wageTempletMapper.getExtWageItems(param);
	}
	
	/**
	 * 自定义模板更改为删除
	 * @param param
	 * @return
	 */
	public int updateExtWageItemDel(Map<String,Object> param){
		return this.wageTempletMapper.updateExtWageItemDel(param);
	}
	
	/**
	 * 自定义模板更改为启用
	 * @param param
	 * @return
	 */
	public int updateExtWageItemValid(Map<String,Object> param){
		return this.wageTempletMapper.updateExtWageItemValid(param);
	}
	
	/**
	 * 自定义模板更改为禁用
	 * @param param
	 * @return
	 */
	public int updateExtWageItemInvalid(Map<String,Object> param){
		return this.wageTempletMapper.updateExtWageItemInvalid(param);
	}
	
	/**
	 * 工资项顺序修改
	 * @param param
	 * @return
	 */
	public int updateExtWageSequence(Map<String,Object> param){
		return this.wageTempletMapper.updateExtWageSequence(param);
	}
}

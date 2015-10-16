package com.ipav.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipav.system.dao.LabourCompanyMapper;

@Service
public class IpavlabourCompanyService {
	
	@Autowired
	private LabourCompanyMapper labourCompanyMapper;

	public int addLabourCompany(Map<String,Object> param){
		return this.labourCompanyMapper.addLabourCompany(param);
	}
	
	public int updateLabourCompany(Map<String,Object> param){
		return this.labourCompanyMapper.updateLabourCompany(param);
	}
	
	public List<Map<String,Object>> queryLabourCompanyInfos(Map<String,Object> param){
		return this.labourCompanyMapper.queryLabourCompanyInfos(param);
	}
	
	public int queryLabourCompanyInfosCount(Map<String,Object> param){
		return this.labourCompanyMapper.queryLabourCompanyInfosCount(param);
	}
	
	public int updateDelStatus(Map<String,Object> param){
		return this.labourCompanyMapper.updateDelStatus(param);
	}
}

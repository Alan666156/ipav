package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavcomatteEntity;
import com.ipav.system.entity.IpavcompanyEntity;

public interface CompanyMapper {
	
	public List<IpavcompanyEntity> queryCompanyList(Map map);
	
	public IpavcompanyEntity queryCompanyByid(String companyid);

	public void updateCompany(IpavcompanyEntity company);
	
	public void insertCompany(IpavcompanyEntity company);
	
	public void insertCompanyAtte(IpavcomatteEntity comatte);
	
	public void updateCompanyAtte(Map map);

	public List<Map<String, Object>> queryProvince();

	public List<Map<String, Object>> queryCitys(String pid);

	public Integer getCompanySize(Map parm);
	
	public Map<String, Object> queryCompanyAtte(Map map);
		
	public List<Map> queryPparameterByType(String type);
	
}

package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.deppon.dpap.framework.data.mybatis.iBatis3DaoImpl;
import com.ipav.system.entity.IpavroleEntity;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月1日 下午2:06:22	
 * 上海天道启科电子有限公司
 */
public interface RoleMapper{
	
	public List getRoleList(Map map);
	
	public List getRoleListByUserid(Map map);
	
	public IpavroleEntity queryRole(Map map);
	
	public void addRole(IpavroleEntity role);
	
	public void updateRole(IpavroleEntity role);
	
	public void addRoleMenu(Map map);
	
	public void delRoleMenu(Integer roleno);
	
	public void delRole(String roleno); 
	
	public List<Map<String,Object>> getOptRole(Map<String,Object> param);
	
	public List<Map<String,Object>> queryMenuInfoByPath(Map<String,Object> param);
}

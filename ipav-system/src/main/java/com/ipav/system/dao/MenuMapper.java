package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavmenuEntity;

public interface MenuMapper {
	
	public List<IpavmenuEntity> getMenuList(Map map);

	public int addMenu(IpavmenuEntity entity);
	
	public List getMenuExcistList(Map map);
	
	public IpavmenuEntity getMenuById(String menuid);
	
	public void updateMenu(IpavmenuEntity entity);
	
	public List getMenuTrees(Map map);
	
	public List getMenuTreeByRole(Map map);
	
	public List getMenuTreeByRoleForUpdate(Map map);
	
	
	public List getTopMenuList(Map map);
	
	public void updateMenuValflg(Map map);
	
	public Integer checkValflg(String menuid);
	
	public void delMenu(String menuid);
	
	public List getMenulistByUser(Map map);

	public Integer getMenuSize(Map parm);
}

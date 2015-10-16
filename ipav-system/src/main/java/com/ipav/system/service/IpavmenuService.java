package com.ipav.system.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipav.system.dao.MenuMapper;
import com.ipav.system.entity.IpavmenuEntity;
import com.ipav.system.util.FormatUtil;

/**
 * creater Jerry All right reserved. Created on 2014年11月12日 上午11:20:27
 * 上海天道启科电子有限公司
 */
@Service
@Transactional
public class IpavmenuService {
	@Autowired
	private MenuMapper menuMapper;
	/***
	 * 新增菜单
	 * @param entity
	 * @return
	 */
	public int addMenu(IpavmenuEntity entity) {
		entity.setCreatedate(FormatUtil.formatDate(new Date(), null));
		entity.setCreater("system");
		return menuMapper.addMenu(entity);
	}
	/***
	 * 按条件获取菜单集合
	 * @param map
	 * @return
	 */
	public List<IpavmenuEntity> getMenuList(Map map) {
		return menuMapper.getMenuList(map);
	}
	/**
	 * 获取除本菜单及下级菜单外的其它菜单集合 
	 * @param map
	 * @return
	 */
	public List getMenuExcistList(Map map){
		return menuMapper.getMenuExcistList(map);
	}
	/***
	 * 根据菜单ID查询菜单信息
	 * @param menuid
	 * @return
	 */
	public IpavmenuEntity getMenuById(String menuid){
		return menuMapper.getMenuById(menuid);
	}
	/****
	 * 修改菜单信息
	 * @param entity
	 */
	public void updateMenu(IpavmenuEntity entity){
		menuMapper.updateMenu(entity);
	}
	/****
	 * 获取菜单树
	 * @param map
	 * @return
	 */
	public List getMenuTrees(Map map){
		return menuMapper.getMenuTrees(map);
	}
	/***
	 * 根据角色查询该角色 关联的菜单树
	 * @param map
	 * @return
	 */
	public List getMenuTreeByRole(Map map){
		return menuMapper.getMenuTreeByRole(map);
	}
	/***
	 * 根据角色查询该角色 可修改的菜单树
	 * @param map
	 * @return
	 */
	public List getMenuTreeByRoleForUpdate(Map  map){
		return menuMapper.getMenuTreeByRoleForUpdate(map);
	}
	
	/***
	 * 查询应用级菜单的方法
	 * @param map
	 * @return
	 */
	public List getTopMenuList(Map map){
		return menuMapper.getTopMenuList(map);
	}
	/***
	 * 修改菜单启用状态
	 * 
	 * @param map
	 */
	public void updateMenuValflg(Map map){
		menuMapper.updateMenuValflg(map);
	}
	
	/***
	 * 检查菜单是否启用
	 * @param menuid
	 * @return
	 */
	public Integer checkValflg(String menuid){
		return  menuMapper.checkValflg(menuid);
	}
	/***
	 * 删除菜单
	 * @param menuid
	 */
	public void delMenu(String menuid){
		menuMapper.delMenu(menuid);
	}
	/***
	 * 查询用户所关联的所有菜单
	 * @param param
	 * @return
	 */
	public List getUserMenuLists(Map param) {
		return menuMapper.getMenulistByUser(param);
	}
	/***
	 * 查询菜单条数
	 * @param parm
	 * @return
	 */
	public int getMenuListSize(Map parm) {
		return menuMapper.getMenuSize(parm)==null?0:menuMapper.getMenuSize(parm);
	}
	
	
}

package com.ipav.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipav.system.dao.RoleMapper;
import com.ipav.system.dao.UserMapper;
import com.ipav.system.entity.IpavroleEntity;
import com.ipav.system.util.ContentUtil;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月1日 下午2:23:46	
 * 上海天道启科电子有限公司
 */
@Service
@Transactional
public class IpavroleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserMapper userMapper;
	
	public void delRole(String roleno){
		roleMapper.delRole(roleno);
	}
	/***
	 * 获取角色列表信息
	 * @param map
	 * @param userids
	 * @return
	 */
	public List getRoleList(Map map,String userids){
		if(StringUtils.isNotEmpty(userids)){
			String[] ids= userids.split(",");
			if(ids.length==1){
				map.put("userid", userids);
				return roleMapper.getRoleListByUserid(map);
			}else{
				return roleMapper.getRoleList(map);
			}
		}else{
			return roleMapper.getRoleList(map);
		}
	}
	/***
	 * 新增角色
	 * @param role
	 * @param menus
	 */
	@Transactional
	public void addRole(IpavroleEntity role,String[] menus){
		role.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
		roleMapper.addRole(role);
		if(menus != null){
			for (int i = 0; i < menus.length; i++) {
				String menuid= menus[i];
				Map parm = new HashMap();
				parm.put("menuid", menuid);
				parm.put("roleno", role.getRoleno());
				roleMapper.addRoleMenu(parm);
			}
		}
	}
	/***
	 * 修改角色信息 
	 * @param role
	 * @param menus
	 */
	@Transactional
	public void updateRole(IpavroleEntity role,String[] menus){
			roleMapper.updateRole(role);
			roleMapper.delRoleMenu(role.getRoleno());
			if(menus != null&&menus.length>0){
				for (int i = 0; i < menus.length; i++) {
					String menuid= menus[i];
					Map parm = new HashMap();
					parm.put("menuid", menuid);
					parm.put("roleno", role.getRoleno());
					roleMapper.addRoleMenu(parm);
			}
		}
	}
	/**
	 * 单一查询角色信息
	 * @param map
	 * @return
	 */
	@Transactional
	public IpavroleEntity queryRole(Map map){
		return roleMapper.queryRole(map);
	}
	/**
	 * 查询当前角色的用户集合
	 * @param map
	 * @return
	 */
	public List queryRoleUser(Map map){
		return userMapper.queryRoleUser(map);
	}
	/***
	 * 查询当前角色的用户数
	 * @param map
	 * @return
	 */
	//add by zhuyx 20150310
	public int queryRoleUserCount(Map map){
		return userMapper.queryRoleUserCount(map);
	}
	/**
	 * TODO 龚严华
	 * 员工授权页面去掉自己的信息
	 * @param userids
	 * @param rolenos
	 */
	public List queryRoleUserDelMy(Map map,String userid){
		List<Map> list = userMapper.queryRoleUser(map);
		for (int i=0;i<list.size();i++) {
			Map mapp=list.get(i);
			if(mapp.get("userid").equals(userid)){
				list.remove(i);
			}
		}
		return list;
	}
	/**
	 * 添加用户与角色的映射关系 
	 * @param userids
	 * @param rolenos
	 */
	@Transactional
	public void addUserRole(String[] userids,String[] rolenos){
		if(userids!=null && rolenos !=null ){
			for (int i = 0; i < userids.length; i++) {
				String userid = userids[i];
				Map parm = new HashMap();
				parm.put("userid", userid);
				userMapper.delUserRole(userid);
				for (int j = 0; j < rolenos.length; j++) {
					String roleno = rolenos[j];
					parm.put("roleno", roleno);
					userMapper.addUserRole(parm);
				}
			}
		}
		
	}
	
	public List<Map<String,Object>> getOptRole(Map<String,Object> param){
		return this.roleMapper.getOptRole(param);
	}
	
	public List<Map<String,Object>> queryMenuInfoByPath(Map<String,Object> param){
		return this.roleMapper.queryMenuInfoByPath(param);
	}
}

package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavuserEntity;

public interface UserMapper {
	//查询员工列表	
	public List getUserList(Map map);
	//得到公司员工数量
	public Integer getUserListCount(Map map);
	//得到员工最大编号
	public String getMaxUserNo(Map map);
	//根据条件查询用户信息
	public IpavuserEntity getUserByUniKey(Map map);
	//添加用户
	public int insertUser(IpavuserEntity user);
	//修改用户密码根据用户id
	public void updateUserPwd(IpavuserEntity user);
	//修改密码根据邮箱或手机号码
	public int updatePwd(Map<String,Object> map);
	//修改用户
	public int updateUser(IpavuserEntity user);
	//修改用户状态（启用禁用）
	public void setuserVal(Map map);
	//查询用户角色菜单
	public List queryRoleUser(Map map);
	//查询角色数量
	public int queryRoleUserCount(Map map);
	//查询带用户的组织机构树
	public List queryOrgUserTree(String companyid);
	//添加用户角色
	public void addUserRole(Map map);
	//删除用户角色
	public void delUserRole(String userid);
	//
	public List<String> autoCompleteUser(Map<String,Object> param);
	//删除用户
	public void delUser(String userid);
	
	public List<Map<String,Object>> getAllLevelInfos(long companyId);
	//查询用户列表（导出）
	public List getUserListForExport(Map map);
	//添加员工职务
	public int addUserDutyInfos(Map<String,Object> param); 
	//删除员工职务
	public int deleteUserDutyInfos(Map<String,Object> param); 
	//查询指定机构下面的员工
	public List<Map<String,Object>> getUserListForOrgid(Map map);
	//根据名字模糊查询员工
	public List<Map<String,Object>> getUserForName(Map map);
    //根据条查询获取用户列表(查询属于同一机构(公司)的全体员工)
	public List queryUsers(Map parm);
	//查询员工导出
	List<Map<String,Object>> queryExportUserList(Map<String,Object> param);
	//员工任职信息
	List<Map<String,Object>> queryUserPostInfo(Map<String,Object> param);
	// 获取已注册的手机及邮箱
	List<Map<String,Object>> queryExistRegistedUser(Map<String,Object> param);
	// 查询用户列表
	List<Map<String,Object>> queryUserList(Map<String,Object> param);
	//查询员工数量
	int queryUserCount(Map<String,Object> param);
	//获取个人中心信息
	List<Map<String,Object>> queryUserInfoOfCenter(Map<String,Object> param);
	//修改用户部分信息
	int updateUserInfos(Map<String,Object> param);
	//修改用户部分信息
	int updateUserCenterInfo(Map<String,Object> param);
	//查询所有菜单
	public List<String> queryAllMenuList();
	//获取openfire群组信息
	public List<Map<String,Object>> getRoomsInfosOfOF(Map<String,Object> param);
	// 判断邮箱或手机号是否已注册
	public List<Map<String,Object>> hasRegisted(Map<String,Object> param);
    //修改用户登录状态0为未登录，1为登录过
    public void updateUserState(Map map);
}

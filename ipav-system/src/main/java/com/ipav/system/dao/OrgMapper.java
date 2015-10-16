package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.deppon.dpap.framework.data.mybatis.iBatis3DaoImpl;
import com.ipav.system.entity.IpavorgEntity;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月24日 下午1:08:26	
 * 上海天道启科电子有限公司
 */
public  interface OrgMapper{
	//查询组织机构列表
	public List<IpavorgEntity> getOrgList(Map map);
	//根据条件查询组织机构列表
	public List<Map<String,Object>> getOrgExsitTreeList(Map map);
	//获得公司组织机构
	public List getOrgTreeList(String companyid);
	//获取公司组织机构
	public List getOrgTrees(String companyid);
	//添加组织机构
	public void addOrg(IpavorgEntity org);
	//根据组织机构id得到组织信息
	public IpavorgEntity queryOrgById(String orgid);
	//修改组织机构
	public void modifyOrg(IpavorgEntity org);
	//删除组织机构
	public void delOrg(String orgid);
	//检查该组织是否存在
	public Integer checkOrgValflg(String orgid);
	//修改组织机构的排序no
	public void changeSeqno(Map map);
	//根据公司id和组织机构号码查询机构信息
	public IpavorgEntity queryOrgByOrgno(Map map);
	//根据组织机构号码模糊查询
	public List<IpavorgEntity> queryOrgByLikeSeqno(Map map);
	//获得公司组织机构
	List<Map<String,Object>> queryOrgLevels(long companyId);
	//检查组织机构名称是否存在
	public Integer checkIsexistOrgName(Map map);
	//查询公司下的编号为0的机构信息
	public IpavorgEntity queryOrgnoTo0Bycompanyid(String commpany);
	//add by zhuyx 
	//得到机构下面子机构的最大编号
	public String queryMaxSeqno(Map map);
}

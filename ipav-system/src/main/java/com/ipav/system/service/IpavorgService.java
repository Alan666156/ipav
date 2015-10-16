package com.ipav.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ipav.system.dao.OrgMapper;
import com.ipav.system.dao.UserMapper;
import com.ipav.system.entity.IpavorgEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月24日 下午7:25:05	
 * 上海天道启科电子有限公司
 */
@Service
@Transactional
public class IpavorgService {
	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private UserMapper userMapper;
	/**
	 * 得到组织机构列表
	 * @param map
	 * @return
	 */
	public List getOrgList(Map map){
		
		return orgMapper.getOrgList(map);
	}
	/**
	 * 添加组织机构
	 * @param org
	 */
	public void addOrg(IpavorgEntity org){
		org.setCreatedate(FormatUtil.formatDate(new Date(), null));
		org.setCreater("system");
		org.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
		orgMapper.addOrg(org);
	}
	/**
	 * 得到组织机构树
	 * @param companyid
	 * @return
	 */
	public List getOrgTreeList(String companyid){
		return orgMapper.getOrgTreeList(companyid);
	}
	
	
	/**
	 * 根据组织机构id得到组织机构信息
	 * @param orgid
	 * @return
	 */
	public IpavorgEntity queryOrgById(String orgid){
		return orgMapper.queryOrgById(orgid);
	}
	/**
	 * 根据公司id和组织机构号码得到该组织机构信息
	 * @param map
	 * @return
	 */
	public IpavorgEntity queryOrgByOrgno(Map map){
		return orgMapper.queryOrgByOrgno(map);
	}
	/**
	 * 根据部分user信息查询user
	 * @param map
	 * @return
	 */
	public IpavuserEntity getUserByUniKey(Map map){
		return userMapper.getUserByUniKey(map);
	}
   /**
    * 修改组织机构
    * @param org
    */
	public void modifyOrg(IpavorgEntity org){
		orgMapper.modifyOrg(org);
	}
	/**
	 * 得到组织机构树
	 * @param companyid
	 * @return
	 */
	public List getOrgTrees(String companyid){
		return orgMapper.getOrgTrees(companyid);
	}
	/**
	 * 删除组织机构
	 * @param orgid
	 */
	public void delOrg(String orgid){
		orgMapper.delOrg(orgid);
	}
	/**
	 * 根据组织机构id检查该组织是否存在或有效
	 * @param orgid
	 * @return
	 */
	public Integer checkOrgValflg(String orgid){
		return orgMapper.checkOrgValflg(orgid);
	}
	/**
	 * 上移下移
	 * @param map
	 */
	@Transactional
	public void changeSeqno(Map map){
	String orgno = (String)map.get("orgno");
		String otherno = (String)map.get("otherno");
		String seqno = (String)map.get("seqno");
		String othersqno = (String)map.get("othersqno");
		String companyid = (String)map.get("companyid");
		
		if(orgno.length()==othersqno.length()){
		/*Map sqlMap = new HashMap();
		sqlMap.put("companyid",companyid);
		sqlMap.put("orgno",orgno);
        IpavorgEntity	org1= orgMapper.queryOrgByOrgno(sqlMap);
        sqlMap.put("orgno",otherno);        
        IpavorgEntity	org2= orgMapper.queryOrgByOrgno(sqlMap);*/
		
		Map parm = new HashMap();
		parm.put("companyid",companyid);
		parm.put("orgno", orgno); 
		parm.put("seqno", othersqno);
		List<IpavorgEntity> list1=orgMapper.queryOrgByLikeSeqno(parm);
		orgMapper.changeSeqno(parm); 		 
		parm.put("orgno", otherno); 
		parm.put("seqno",seqno);
		orgMapper.changeSeqno(parm);  
		Map smap2=new HashMap<String, Object>();
		List<IpavorgEntity> list2=orgMapper.queryOrgByLikeSeqno(parm);
		if(list1.size()>0){
		for(IpavorgEntity org:list1){             
			Map sql = new HashMap();
			sql.put("companyid",companyid);
			sql.put("orgno", org.getOrgno()); 
			sql.put("seqno", seqno+org.getSeqno().substring(othersqno.length()));
			orgMapper.changeSeqno(sql); 
		}
		}
		if(list2.size()>0){
		for(IpavorgEntity org:list2){             
			Map sql = new HashMap();
			sql.put("companyid",companyid);
			sql.put("orgno", org.getOrgno()); 
			sql.put("seqno", othersqno+org.getSeqno().substring(seqno.length()));
			orgMapper.changeSeqno(sql); 
		}
		}
		}
		/*
		parm.put("orgno", otherno);
		parm.put("seqno", othersqno);
		parm.put("othersqno", seqno);
		orgMapper.changeSeqno(parm);//替换otherno机构的序号
		*/
	}
	
	/**
	 * 获取公司下所有组织机构
	 * @param companyId
	 * @return
	 */
	public List<Map<String,Object>> queryOrgList(long companyId){
		return this.orgMapper.queryOrgLevels(companyId);
	}
	/***
	 * 获取部门树状结构(排除本机构及下辖机构)
	 * @param map
	 * @return
	 */
	public List<JSONObject> getOrgExsitTreeList(Map parm){
		List<Map<String,Object>> list = orgMapper.getOrgExsitTreeList(parm);
		
		List<JSONObject> result=new ArrayList<JSONObject>();
		JSONObject map=null;
		Map<String, Object> tmp=null;
		if(list!=null)
			for(int i=0;i<list.size();i++){
				if(!list.get(i).containsKey("pno")){
					tmp=list.get(i);
					map=new JSONObject();
					map.put("id", tmp.get("id"));
					map.put("no", tmp.get("no"));
					map.put("name", tmp.get("name"));
					map.put("fullname", tmp.containsKey("fullname")?tmp.get("fullname"):"");
					map.put("type", 1);
					result.add(map);
					list.remove(i);
					break;
				}
			}	
		this.recurring(result.get(0),new ArrayList<JSONObject>(), list, tmp.get("no").toString(),tmp.get("id").toString());
		return result;
	}
	/**
	 * 获取部门树状结构
	 * @param companyId
	 * @return
	 */
	public List<JSONObject> getOrgLevels(long companyId){
		List<Map<String,Object>> list=this.orgMapper.queryOrgLevels(companyId);
		List<JSONObject> result=new ArrayList<JSONObject>();
		JSONObject map=null;
		Map<String, Object> tmp=null;
		if(list!=null)
			for(int i=0;i<list.size();i++){
				if(!list.get(i).containsKey("pno")){
					tmp=list.get(i);
					map=new JSONObject();
					map.put("id", tmp.get("id"));
					map.put("no", tmp.get("no"));
					map.put("name", tmp.get("name"));
					map.put("fullname", tmp.containsKey("fullname")?tmp.get("fullname"):"");
					map.put("type", 1);
					result.add(map);
					list.remove(i);
					break;
				}
			}	
		this.recurring(result.get(0),new ArrayList<JSONObject>(), list, tmp.get("no").toString(),tmp.get("id").toString());
		return result;
	}
	
	/**
	 * 递归
	 * @param param
	 * @param list
	 * @param parentNo
	 */
	private void recurring(JSONObject param,List<JSONObject> mapList,List<Map<String,Object>> list,String parentNo,String parentId){
		JSONObject map=null;
		Map<String,Object> tmp=null;
		Iterator<Map<String, Object>> iter=list.iterator();
		while(iter.hasNext()){
			tmp=iter.next();
			map=new JSONObject();
			if("1".equals(tmp.get("type").toString())&&parentNo.equals(tmp.get("pno").toString())){
				iter.remove();
				map.put("id", tmp.get("id"));
				map.put("no", tmp.get("no"));
				map.put("name", tmp.get("name"));
				map.put("fullname", tmp.containsKey("fullname")?tmp.get("fullname"):"");
				map.put("type", 1);
				mapList.add(map);
			}
			/*else if("0".equals(tmp.get("type").toString())&&parentId.equals(tmp.get("pno").toString())){
				iter.remove();
				map.put("id", tmp.get("id"));
				map.put("no", tmp.get("no"));
				map.put("name", tmp.get("name"));
				map.put("type", 0);
				mapList.add(map);
			}*/
		}
		for(int i=0;i<mapList.size();i++){
				this.recurring(mapList.get(i), new ArrayList<JSONObject>(),list, mapList.get(i).get("no").toString(),mapList.get(i).get("id").toString());
		}	
		param.put("nodes", mapList);
	}
	/**
	 * 检查该公司下该组织名称是否存在
	 * @param map
	 * @return
	 */
	public Integer checkIsexistOrgName(Map map){
		return orgMapper.checkIsexistOrgName(map);
	}
	/**
	 * 根据公司id查询组织机构号码为0的组织机构信息
	 * @param commpany
	 * @return
	 */
	public IpavorgEntity queryOrgnoTo0Bycompanyid(String commpany){
		return orgMapper.queryOrgnoTo0Bycompanyid(commpany);
	}
	/**
	 * 得到该公司下面最大的seqno
	 * @param map
	 * @return
	 */
	public String queryMaxSeqno(Map map){
		return orgMapper.queryMaxSeqno(map);
	}
}

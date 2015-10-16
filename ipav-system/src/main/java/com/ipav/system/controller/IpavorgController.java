package com.ipav.system.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavorgEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavorgService;
import com.ipav.system.util.ContentUtil;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月24日 下午7:32:30	
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavorgController {
	@Autowired
	private IpavorgService orgService;
	/**
	 * 查询组织机构列表
	 * @param session
	 * @param orgno
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryorglist")
	public String queryOrgList(HttpSession session,String orgno ,ModelMap map){
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map parm = new HashMap();
		parm.put("companyid", user.getCompanyid());
		parm.put("orgno", orgno);
		parm.put("valflg", "1");
		
		List orgs= orgService.getOrgList(parm);
		//map.put("orgideq", orgid);
		map.put("orgno", orgno);
		map.put("list", orgs);
		return "system/org/orglist";
	}	
	/**
	 * 为添加组织机构获取准备参数
	 * @param companyid
	 * @param orgid
	 * @param oper
	 * @param faNo
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/gotoaddorg")
	public String gotoaddOrg(String companyid,String orgid,String oper,String faNo,ModelMap map){
		
		String path ="";
		if(ContentUtil.DEFUAL_ADD.equals(oper)){
			if(StringUtils.isNotEmpty(orgid)){
				IpavorgEntity org= orgService.queryOrgById(orgid);
				map.put("porgno", org.getOrgno());//父级机构编号
				map.put("porgname", org.getOrgname());//父级机构编号
				map.put("porgfullname", org.getOrgfullname());//父级机构全称
				
			}else{
				map.put("porgno", "0");//父级机构编号
				map.put("porgname","组织机构");//父级机构编号
				map.put("porgfullname", "");//父级机构全称
			}
			map.put("porgid", orgid);
			path= "system/org/addorg";
		}
		if(ContentUtil.DEFUAL_UPDATE.equals(oper)){
			IpavorgEntity org= orgService.queryOrgById(orgid);
			map.put("org", org);
			
			Map pram = new HashMap();
			pram.put("companyid", companyid);
			pram.put("orgno", org.getParentno());
			
			IpavorgEntity porg= orgService.queryOrgByOrgno(pram);
			map.put("porgname", porg.getOrgname());
			
			Map pram2 = new HashMap();
			String orgchefname ="";
			if(StringUtils.isNotEmpty(org.orgchef)){
				pram2.put("userid", org.orgchef);
				IpavuserEntity chef= orgService.getUserByUniKey(pram2);
				orgchefname = chef.getUsername();
			}
			map.put("orgchefname",orgchefname);
//			Map pram = new HashMap();
//			pram.put("companyid", companyid);
//			pram.put("orgno", org.getOrgno());
			
			//List orglist =orgService.getOrgExsitTreeList(pram);
			//bug---start 修改机构是记住页面之前点击的机构no
			map.put("faNo", org.getParentno());
			//bug---end
			 
			//map.put("orglist", orglist);
			path= "system/org/updateorg";
		}
		
		return path;
	}
	/**
	 * 获取组织机构树（无人员）
	 * @param companyid
	 * @return
	 */
	@RequestMapping(value="/getOrgTree")
	@ResponseBody
	public JSONArray getOrgTree(String companyid){
		List orglist =orgService.getOrgTreeList(companyid);
		JSONArray arr=(JSONArray)JSONArray.toJSON(orglist);
		return arr;
	}
	/**
	 * 获取组织机构树（无人员）
	 * @param companyid
	 * @return
	 */
	@RequestMapping(value="/showOrgTrees")
	@ResponseBody
	public JSONArray showOrgTrees(String companyid){
		List orglist =orgService.getOrgTrees(companyid);
		JSONArray arr=(JSONArray)JSONArray.toJSON(orglist);
		return arr;
	}
	/**
	 *添加组织机构
	 * @param org
	 * @param map
	 * @param isagin
	 * @param session
	 * @param orgno
	 * @param porgid
	 * @return
	 */
	@RequestMapping(value="/addorg")
	public String addOrg(IpavorgEntity org,ModelMap map,String isagin,HttpSession session,String orgno ,String porgid){
		orgService.addOrg(org);
		if("agin".equals(isagin))
			return "forward:/gotoaddorg?companyid="+org.getCompanyid()+"&oper=add&orgid="+porgid;
		else{
         orgno=null;
         //return "forward:/queryorglist";
         return  queryOrgList(session,orgno ,map);
		}
		
	}
	/**
	 * 修改组织机构
	 * @param org
	 * @param faNo
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/orgmodify")
	public String modifyOrg(IpavorgEntity org,String faNo,ModelMap map,HttpSession session){
		orgService.modifyOrg(org);
		faNo = null;
		return queryOrgList(session, faNo, map);
		//return "forward:/queryorglist";
	}
	/**
	 * 删除组织机构
	 * @param orgid
	 * @param faNo
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/delorg")
	public String delOrg(String orgid,String faNo,ModelMap map,HttpSession session){
		orgService.delOrg(orgid);
		//return "forward:/queryorglist";
		return queryOrgList(session, faNo, map);
	}
	/**
	 * 判断组织机构能否上下移动
	 */
	@RequestMapping(value="/checkOrgvalflg")
	@ResponseBody
	public boolean checkOrgvalflg(String orgid){
		int count=orgService.checkOrgValflg(orgid);
		if(count>0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 改变组织机构排序的no
	 * @param orgno
	 * @param otherno
	 * @param companyid
	 * @param seqno
	 * @param othersqno
	 */
	@RequestMapping(value="/changeSeqno")
	@ResponseBody
	public void changeSeqno(String orgno,String otherno,String companyid,String seqno,String othersqno){
		Map map = new HashMap();
		map.put("orgno", orgno);
		map.put("otherno", otherno);
		map.put("companyid", companyid);
		map.put("seqno", seqno);
		map.put("othersqno", othersqno);
		orgService.changeSeqno(map);
	}
	/**
	 * 获取部门树状结构(排除本机构及下辖机构)	
	 * @param session
	 * @param orgno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/initOrgExsitTreeList")
	public JSONObject getOrgExsitTreeList(HttpSession session,String orgno){
		JSONObject result=new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		Map map = new HashMap();
		map.put("companyid", user.getCompanyid());
		map.put("orgno",orgno);
		
		result.put("orglevels", orgService.getOrgExsitTreeList(map));
		return result;
	}
	/**
	 * 获取部门树状结构
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/initOrgLevels")
	public JSONObject getOrgLevels(HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		result.put("orglevels", orgService.getOrgLevels(user.getCompanyid()));
		return result;
	}
	/**
	 * 检查部门名称是否存在
	 * @param companyid
	 * @param orgno
	 * @param orgname
	 * @param oldorgno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/existSameName")
	public JSONObject checkIsexistOrgName(String companyid,String orgno,String orgname,String oldorgno){
		JSONObject result=new JSONObject();
		Map map = new HashMap();
		map.put("companyid", companyid);
		map.put("orgno", orgno);
		map.put("orgname", orgname.trim());
		map.put("oldorgno", oldorgno);
		int count= orgService.checkIsexistOrgName(map);
		if(count>0){
			result.put("res", "N");
		}else{
			result.put("res", "Y");
		}
		return result;
	}
	/**
	 * 根据父级组织机构号码得到该组织机构下面最新的子机构号码
	 * @param session
	 * @param newparentno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getnewseqno")
	public JSONObject getNewSqno(HttpSession session,String  newparentno){
		JSONObject result=new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		String res ="01";
		Map map = new HashMap();
		map.put("parentno", newparentno);
		map.put("companyid", user.getCompanyid());
		
		String maxseqno= orgService.queryMaxSeqno(map);
		if(StringUtils.isNotEmpty(maxseqno)){
			BigDecimal bg = new BigDecimal(maxseqno);
			bg = bg.add(new BigDecimal(1));
			res = bg.toString();
		}else{
			res = newparentno +res;
		}
		result.put("res", res);
		return result;
	}
	
}

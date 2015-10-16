package com.ipav.system.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavcomatteEntity;
import com.ipav.system.entity.IpavcompanyEntity;
import com.ipav.system.entity.IpavimageEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavcompanyService;
import com.ipav.system.service.IpavdutyService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月19日 下午2:26:17	
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavcompanyController {
	@Autowired
	private IpavcompanyService companyService;
	
	@Autowired
	private IpavdutyService dutyService;
	
//	@RequestMapping(value="/companylist")
//	public String queryCompanyList(String attstuts,ModelMap map){
//		Map parm = new HashMap();
//		parm.put("attstuts", attstuts);
//		List companylist= companyService.queryCompanyList(parm);
//		map.put("companylist", companylist);
//		map.put("attstuts", attstuts);
//		return "system/company/companylist";
//	}
	/**
	 * 进入企业用户审核页面入口
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/companylist")
	public String queryCompanyList(ModelMap map){
		return "system/company/companylist";
	}
	
	/**
	 * 查询所有企业列表
	 * @param attstuts 企业认证状态
	 * @param beginRow 分页起始页
	 * @param pageSize 每页行数
	 * @return
	 */
	@RequestMapping(value="/queryCompanys")
	@ResponseBody
	public JSONObject queryCompanys(String attstuts,Integer beginRow,Integer pageSize){
		JSONObject resultbody=new JSONObject();
		Map parm = new HashMap();
		parm.put("attstuts", attstuts);
		resultbody.put("size",companyService.getCompanySize(parm));
		parm.put("beginRow",beginRow==null?0:beginRow);
		parm.put("pageSize",pageSize==null?10:pageSize);
		List companylist= companyService.queryCompanyList(parm);
		resultbody.put("list",companylist);
		return resultbody;
	}
	/**
	 * 更新公司认证状态
	 * @param comatte 认证结果
	 * @param passflg 0:取消认证;1:认证成功
	 * @return
	 */
	@RequestMapping(value="/sysattecompany")
	public String sysAtteCompany(IpavcomatteEntity comatte,String passflg){
		companyService.modifyCompanyAtte(comatte,passflg);
		return "forward:/companylist";
	}
	
//	@RequestMapping(value="/restcompanyatte")
//	public String resetCompanyAtte(IpavcomatteEntity comatte){
//		Map parm = new HashMap();
//		parm.put("companyid", companyid);
//		parm.put("reason", reason);
//		companyService.
//		parm.put("attstuts", "0");
//		parm.put("attetime","");
//		companyService.modifyCompanyAtte(comatte,"0");
//		return "forward:/companylist";
//	}
	
	/**
	 * 进入企业用户审核页面入口
	 * @param companyid 企业id
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/companyattveiw")
	public String gotoCompanyAttVeiw(String companyid,ModelMap map){
		IpavcompanyEntity company= companyService.queryCompanyByid(companyid) ;
		map.put("company",company);
		IpavimageEntity image=companyService.getCompanyImage(Long.parseLong(companyid),"ATTE");
		if(image!=null)
			map.put("picPath",ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("company")+image.getSorcepath());
		return "system/company/sysattecompany";
	}
	
	/**
	 * 进入公司信息页面入口
	 * @param session
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/gotoCompany")
	public String gotoCompanyVeiw(HttpSession session,ModelMap map) throws Exception{
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		if(user!=null){
			IpavcompanyEntity company= companyService.queryCompanyByid(user.getCompanyid()+"") ;
			//格式化日期公司成立时间
			String new_date=company.getBullerdate();
			if(StringUtils.isNotEmpty(new_date))
				new_date=FormatUtil.formatDate(new SimpleDateFormat("yyyyMMdd").parse(new_date), "yyyy-MM-dd");
			company.setBullerdate(new_date);
			Map<String, Object> param = new HashMap();
			param.put("companyid", user.getCompanyid());
			param.put("status", ContentUtil.DEFUAL_VALDLF_YES);
			List<Map<String,Object>> dutys=this.dutyService.queryDutyList(param);
			map.put("company",company);
			map.put("dutys",dutys);
			List<Map<String,Object>> plist=companyService.getProvince();
			map.put("provinces", plist);
			List<Map<String,Object>> clist=companyService.getCitys(company.getProvince());
			map.put("citys", clist);
			List<Map> Parames=companyService.queryPparameterByType("companytype");
			map.put("Parames", Parames);
		}
		
		return "system/company/companyinfo";
	}
	
	/**
	 * 更新企业信息
	 * @param company
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/updateCompany")
	public String updateCompanyInfo(IpavcompanyEntity company,ModelMap map){
		if(company!=null) company.setBullerdate(company.getBullerdate().replaceAll("-", ""));
		try {
			companyService.updateCompany(company);
			map.put("company",company);
			Map<String, Object> param = new HashMap();
			param.put("companyid", company.getCompanyid());
			param.put("status", ContentUtil.DEFUAL_VALDLF_YES);
			String new_date=company.getBullerdate();
			if(StringUtils.isNotEmpty(new_date))
			new_date=FormatUtil.formatDate(new SimpleDateFormat("yyyyMMdd").parse(new_date), "yyyy-MM-dd");
			company.setBullerdate(new_date);
			map.put("action", "modify");//添加操作状态,区分浏览信息操作
			List<Map<String,Object>> plist=companyService.getProvince();
			map.put("provinces", plist);
			List<Map<String,Object>> clist=companyService.getCitys(company.getProvince());
			map.put("citys", clist);
			List<Map> Parames=companyService.queryPparameterByType("companytype");
			map.put("Parames", Parames);
			map.put("message", "success");
		} catch (ParseException e) {
			map.put("message", "error");
			e.printStackTrace();
		}
		return "system/company/companyinfo";
	}
	
	/**
	 * 进入企业LOGO设置页面入口
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/companylogo")
	public String companyLogoVeiw(HttpSession session,ModelMap map){
		IpavuserEntity curuser= (IpavuserEntity)session.getAttribute("curuser");
		if(curuser!=null){
			IpavimageEntity image=companyService.getCompanyImage(curuser.getCompanyid(),"LOGO");
			String HttpURL=ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("company");
			if(image!=null){
				map.put("sourceimage",StringUtils.isNotEmpty(image.getSorcepath())?HttpURL+image.getSorcepath():"");
				map.put("subimage",StringUtils.isNotEmpty(image.getSubpath())?HttpURL+image.getSubpath():"");
			}
		}
		return "system/company/companylogo";
	}
//	@RequestMapping(value="/getcompanylogo")
//	public void getCompanyLogo(String companyid,String pty,HttpS  ervletResponse resp)throws Exception{
//		companyService.companyLogo(companyid,pty,resp);
//	}
	
	
	
	/**
	 * 上传Logo
	 */
	@RequestMapping(value="/saveLogo")
	public String saveLogo(MultipartFile filefield, HttpServletRequest request,ModelMap model){
		Map map= new HashMap();
		map.put("x", request.getParameter("x"));
		map.put("y", request.getParameter("y"));
		map.put("width", request.getParameter("width"));
		map.put("height", request.getParameter("height"));
		map.put("companyid", request.getParameter("companyid"));
		try {
			companyService.saveCompanyLogo(filefield,map);
			model.put("message", "success");
		} catch (Exception e) {
			model.put("message", "error");
			e.printStackTrace();
		}
		return "forward:/companylogo";	
	}
	
	/**
	 * 转到企业认证页面
	 * @return
	 */
	@RequestMapping(value="/companyAtte")
	public String companyAtteVeiw(HttpSession session,ModelMap model){
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		if(user!=null){
			IpavimageEntity image=companyService.getCompanyImage(user.getCompanyid(),"ATTE");
			String HttpURL=ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("company");
			model.put("imagepath",image!=null?HttpURL+image.getSorcepath():"");
			IpavcompanyEntity company= companyService.queryCompanyByid(user.getCompanyid()+"") ;
			model.put("company", company);
			String state=company==null?"":company.getAttstuts();
			if("0".equals(state)){
				Map map = new HashMap();
				map.put("companyid", user.getCompanyid()+"");
				Map<String,Object> rsmap=companyService.queryCompanyAtte(map);
				if(rsmap!=null)	model.put("backInfo", rsmap.get("reason"));
			}
		}
		return "system/company/companyatte";
	}
	/**
	 * 保存认证图片
	 * @param filefield
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveAtte")
	public String saveAtte(MultipartFile filefield, HttpServletRequest request,ModelMap model){
		Map map= new HashMap();
		map.put("companyid", request.getParameter("companyid"));
		try {
			companyService.saveCompanyAtte(filefield,map);
			model.put("message", "success");
		} catch (Exception e) {
			model.put("message", "error");
			e.printStackTrace();
		}
		return "forward:/companyAtte";
	}
	

	//此方法废除
	@RequestMapping(value="/getcompanyatte")
	public void getCompanyAtte(String companyid,HttpServletResponse resp)throws Exception{
		companyService.getcompanyAtte(companyid,resp);
	}
	
	/**
	 * 根据省份id获取城市列表集合
	 * @param province_id
	 * @return
	 */
	@RequestMapping(value="/getCitys")
	@ResponseBody
	public JSONArray getCitys(String province_id){
		List<Map<String,Object>> citys = companyService.getCitys(province_id);
		JSONArray arr = (JSONArray) JSONArray.toJSON(citys);
		return arr;
	}
	//此方法废除
	@RequestMapping(value="/getCompanyLOGO")
	@ResponseBody
	public String getCompanyLOGO(String companyid){
		JSONObject obj  = new JSONObject();
		IpavimageEntity image=companyService.getCompanyImage(Long.parseLong(companyid),"LOGO");
		obj.put("logo", image==null?"":(image.getSubpath()==null?"":ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("company")+image.getSubpath()));
		return JSON.toJSONString(obj);
	}
}

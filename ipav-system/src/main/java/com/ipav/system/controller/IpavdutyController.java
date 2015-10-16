package com.ipav.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavdutyService;
import com.ipav.system.util.ExcelTools;
import com.ipav.system.util.ExcelView;
import com.ipav.system.util.RequestParamToMap;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年12月8日 上午11:30:20	
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavdutyController {
	@Autowired
	private IpavdutyService dutyService;
	
//	@RequestMapping(value="/dutylist")
//	public String gotoDutyList(HttpSession session,String queryvalflg,ModelMap map){
//		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
//		Map parm = new HashMap();
//		parm.put("companyid", user.getCompanyid());
//		parm.put("valflg", queryvalflg);
//		List dutys= dutyService.dutyList(parm);
//		map.put("dutylist", dutys);
//		map.put("queryvalflg", queryvalflg);
//		map.put("companyid",  user.getCompanyid());
//		return "system/role/dutyadd";
//	}
//	
//	@RequestMapping(value="/dutyadd")
//	public String dutyAdd(HttpSession session,IpavdutyEntity duty){
//		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
//		duty.setCreater(user.getUsername());
//		dutyService.dutyAdd(duty);
//		return "forward:/dutylist";
//	}
//	/**
//	 * 单个删除职位
//	 * @param dutyid
//	 * @return
//	 */
//	@RequestMapping(value="/dutydel")
//	public String dutyDel(String dutyid){
//		dutyService.dutyDelete(dutyid);
//		return "forward:/dutylist";
//	}
//	
//	@RequestMapping(value="/dutydelall")
//	public String dutyDelall(HttpServletRequest req,HttpServletResponse rsp){
//		String[] dutyids= req.getParameterValues("dutyids");
//		dutyService.dutyDelall(dutyids);
//		return "forward:/dutylist";
//	}
//	@RequestMapping(value="/dutyval")
//	public String dutySetval(IpavdutyEntity duty){
//		Map map = new HashMap();
//		map.put("dutyid", duty.getDutyid());
//		map.put("valflg", duty.getValflg());
//		dutyService.dutyValset(map);
//		return "forward:/dutylist";
//	}
//	
//	@RequestMapping(value="/dutyupdate")
//	public String dutyUpdate(IpavdutyEntity duty){
//		dutyService.dutyUpdate(duty);
//		return "forward:/dutylist";
//	}
	
	
	/******************************author:WYL********************************/
	/**
	 * 职务查询
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryDutyList",method=RequestMethod.POST)
	public JSONObject getDutyList(HttpServletRequest request,
			HttpSession session) {
		JSONObject result=new JSONObject();
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map<String, Object> param = RequestParamToMap.convert(request);
		param.put("companyid", user.getCompanyid());
		result.put("counts", dutyService.queryDutyCounts(param));
		List<Map<String,Object>> dutys= this.dutyService.queryDutyList(param);
		result.put("list",dutys);
		return result;
	}
	
	/**
	 * 职务添加
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="insertDuty",method=RequestMethod.POST)
	public JSONObject addDuty(HttpServletRequest request,
			HttpSession session) {
		JSONObject result=new JSONObject();
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map<String, Object> param = RequestParamToMap.convert(request);
		
		List nameList=new ArrayList<String>();
		Map<String, Object> nameCheckedParam=new HashMap<String, Object>();
		nameList.add(param.get("dutyname"));
		nameCheckedParam.put("companyId", user.getCompanyid());
		nameCheckedParam.put("list", nameList);
		List<Map<String,Object>> existNameList=this.dutyService.getExistDutyName(nameCheckedParam);
		if(existNameList==null||existNameList.size()==0){
			if(!param.containsKey("id")){
				List<Map<String,Object>> listParam=new ArrayList<Map<String,Object>>();
				Map<String,Object> subMap=new HashMap<String, Object>();
				subMap.put("dutyname", param.get("dutyname"));
				subMap.put("remark", param.get("remark"));
				listParam.add(subMap);
				param.clear();
				param.put("list", listParam);
				param.put("companyid", user.getCompanyid());
				param.put("updateUser", user.getUserid());
				result.put("result", this.dutyService.insertDuty(param));
			}else{
				param.put("updateUser", user.getUserid());
				result.put("result", this.dutyService.updateDuty(param));
			}
		}else{
			if(!param.containsKey("id")){
				result.put("result",0);
				result.put("err", "该职务名称已存在");
			}else{
				for(Map<String,Object> m:existNameList){
					if(!m.get("id").toString().equals(param.get("id").toString())){
						result.put("result",0);
						result.put("err", "该职务名称已存在");
						break;
					}
				}
				if(!result.containsKey("err")){
					param.put("updateUser", user.getUserid());
					result.put("result", this.dutyService.updateDuty(param));
				}
			}
		}
		return result;
	}
	
	/**
	 * 职务状态修改
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="updateDutyStatus",method=RequestMethod.POST)
	public JSONObject updateDutyStatus(HttpServletRequest request,
			HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map<String, Object> param = RequestParamToMap.convert(request);
		param.put("updateUser", user.getUserid());
		if(param.get("ids").getClass().getSimpleName().equals("String")){
			String[] ids=new String[]{param.get("ids").toString()};
			param.put("ids", ids);
		}
		result.put("result", this.dutyService.updateStatus(param));
		return result;
	}
	
	/**
	 * 职务维护页面跳转
	 * @return
	 */
	@RequestMapping(value="/dutylist")
	public String gotoDutyList(){
		return "system/role/dutyadd";
	}
	
	/**
	 * 模板下载
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/exportTemplet")
	public ModelAndView exportDutyTemplet(ModelMap model) {
		List<String> title = new ArrayList<String>();
		title.add("职务名称");
		title.add("职务描述");
		model.put("title", title);
		return new ModelAndView(new ExcelView("职务模板", "职务模板"), model);
	}
	
	/**
	 * 职务导出
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/exportDutyList")
	public ModelAndView exportDutyList(ModelMap model,HttpServletRequest request,HttpSession session) {
		List<String> title = new ArrayList<String>();
		title.add("职务名称");
		title.add("职务描述");
		title.add("状态");
		title.add("创建人");
		title.add("创建时间");
		model.put("title", title);
		JSONObject result=new JSONObject();
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map<String, Object> param = RequestParamToMap.convert(request);
		param.put("companyid", user.getCompanyid());
		List<Map<String,Object>> dutyList= this.dutyService.queryDutyList(param);
		if(dutyList!=null&&dutyList.size()>0){
			List<List> contents=new ArrayList<List>();
			List<String> row=null;
			for(Map<String,Object> map:dutyList){
				row=new ArrayList<String>();
				row.add(map.get("duty_name").toString());
				row.add(map.get("remark").toString());
				row.add(map.get("status").toString().equals("1")?"启用":"禁用");
				row.add(map.get("username").toString());
				row.add(map.get("update_time").toString().replace("-0", "年").replace("-0", "月").replace("-", "年").replace("-", "月")+"日");
				contents.add(row);
			}
			model.put("content", contents);
		}
		return new ModelAndView(new ExcelView("职务信息", "职务信息"), model);
	}
	
	/**
	 * 职务信息导入
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importDutyInfos")
	public JSONObject importDutyInfos(HttpSession session,
			HttpServletRequest request){
		String fileName=request.getParameter("fileName");
		MultipartHttpServletRequest req=(MultipartHttpServletRequest)request;
		MultipartFile file=req.getFile("dutyFile");
		List<ArrayList<String>> content=null;
		JSONObject result=new JSONObject();
		try {
			if(fileName.lastIndexOf(".xls")>=0)
				content=new ExcelTools().readExcel03(file.getInputStream());
			else if(fileName.lastIndexOf(".xlsx")>=0)
				content=new ExcelTools().readExcel07(file.getInputStream());
			else {
				result.put("err", "请导入正确的excel文件");
				return result;
			}
			if(content!=null&&content.size()>1){
				List<Map<String,Object>> listParam=null;
				Map<String,Object> subMap=null;
				IpavuserEntity user=null;
				Map<String,Object> param=null;
				Map<String,Object> nameCheckedParam=null;
				List<String> nameList=null;
				if(content.get(0).get(0).equals("职务名称")&&content.get(0).get(1).equals("职务描述")){
					user = (IpavuserEntity) session.getAttribute("curuser");
					content.remove(0);
					listParam=new ArrayList<Map<String,Object>>();
					nameList=new ArrayList<String>();
					nameCheckedParam=new HashMap<String, Object>();
					for(List<String> row:content){
						subMap=new HashMap<String, Object>();
						subMap.put("dutyname", row.get(0));
						subMap.put("remark", row.get(1));
						nameList.add(row.get(0));
						listParam.add(subMap);
					}
					nameCheckedParam.put("list", nameList);
					nameCheckedParam.put("companyId", user.getCompanyid());
					List<Map<String,Object>> existNames=this.dutyService.getExistDutyName(nameCheckedParam);
					if(existNames!=null&&existNames.size()==nameList.size())
						result.put("result",0);
					else{
						param=new HashMap<String,Object>();
						param.put("list", listParam);
						param.put("companyid", user.getCompanyid());
						param.put("updateUser", user.getUserid());
						result.put("result", this.dutyService.insertDuty(param));
					}
					if(existNames!=null)
						result.put("exitsNames", JSONArray.toJSON(existNames));
				}else if(content.get(0).get(1).equals("职务名称")&&content.get(0).get(0).equals("职务描述")){
					user = (IpavuserEntity) session.getAttribute("curuser");
					content.remove(0);
					listParam=new ArrayList<Map<String,Object>>();
					nameList=new ArrayList<String>();
					nameCheckedParam=new HashMap<String, Object>();
					for(List<String> row:content){
						subMap=new HashMap<String, Object>();
						subMap.put("dutyname", row.get(1));
						subMap.put("remark", row.get(0));
						nameList.add(row.get(1));
						listParam.add(subMap);
					}
					nameCheckedParam.put("list", nameList);
					nameCheckedParam.put("companyId", user.getCompanyid());
					List<Map<String,Object>> existNames=this.dutyService.getExistDutyName(nameCheckedParam);
					if(existNames!=null&&existNames.size()==nameList.size())
						result.put("result",0);
					else{
						param=new HashMap<String,Object>();
						param.put("list", listParam);
						param.put("companyid", user.getCompanyid());
						param.put("updateUser", user.getUserid());
						result.put("result", this.dutyService.insertDuty(param));
					}
					if(existNames!=null)
						result.put("exitsNames", JSONArray.toJSON(existNames));
				}else{
					result.put("err", "模板错误");
				}
			}else 
				result.put("err", "模板错误");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}

package com.ipav.wage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavuserService;


public class WageInfoService {

	/**
	 * 工资记录数
	 * @param wageRecordService
	 * @param param
	 * @return
	 */
	public int getWageListSize(IpavwageRecordService wageRecordService,Map<String,Object> param){
		int result=0;
		Map<String, Object> countMap = wageRecordService
				.getWageRecordsCount(param);
		if(countMap!=null&&countMap.size()==1)
			result=Integer.parseInt(countMap.get("counts").toString());
		return result;
	}
	
	/**
	 * 实发工资应发工资释疑人等信息
	 * @param wageTempletService
	 * @param param
	 * @param should_pay_resolver
	 * @param net_income_resolver
	 */
	public void initBasicResolver(IpavwageTempletService wageTempletService,Map<String,Object> param,Map<String,Object> should_pay_resolver,Map<String,Object> net_income_resolver){
		List<Map<String, Object>> basic=wageTempletService.getBasicWage(param);
		if(basic==null||basic.size()==0){
//			should_pay_resolver=new HashMap<String, Object>();
			should_pay_resolver.put("id", "");
			should_pay_resolver.put("item_name", "应发工资");
			should_pay_resolver.put("item_type", 2);
			should_pay_resolver.put("resolve_user", "");
			should_pay_resolver.put("username", "");
			should_pay_resolver.put("remark", "");
			should_pay_resolver.put("update_user", "");
			should_pay_resolver.put("status", 1);
			should_pay_resolver.put("sequence", "");
//			net_income_resolver=new HashMap<String, Object>();
			net_income_resolver.put("id", "");
			net_income_resolver.put("item_name", "实发工资");
			net_income_resolver.put("item_type", 2);
			net_income_resolver.put("resolve_user", "");
			net_income_resolver.put("username", "");
			net_income_resolver.put("remark", "");
			net_income_resolver.put("update_user", "");
			net_income_resolver.put("status", 1);
			net_income_resolver.put("sequence", "");
		}else{
			for(Map<String,Object> m:basic){
				m.put("item_type", 2);
				m.put("remark", "");
				m.put("status", 1);
				m.put("sequence", "");
				if(m.get("item_name").toString().equals("应发工资"))
					should_pay_resolver.putAll(m);
				else
					should_pay_resolver.putAll(m);
			}
		}
	}
	
	/**
	 * 工资合计信息
	 * @param wageRecordService
	 * @param wageTempletService
	 * @param param
	 * @return
	 */
	public Map<String,Object> getWageSumInfos(IpavwageRecordService wageRecordService,IpavwageTempletService wageTempletService,Map<String,Object> param){
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, Object> sum = wageRecordService
				.getWageRecordsSum(param);
		result.put("should_pay_sum", sum.get("should_pay_sum"));
		result.put("net_income_sum", sum.get("net_income_sum"));
		List<Map<String,Object>> extItemRecords=wageRecordService.getWageRecordsExtItems(param);
		Map<String,Object> extItemParam=new HashMap<String, Object>();
		extItemParam.put("companyId", param.get("companyId"));
		List<Map<String,Object>> extItems=wageTempletService.getExtWageItems(extItemParam);
		Map<String,String> extIdName=null;
		if(extItems!=null&&extItems.size()>0){
			extIdName=new HashMap<String, String>();
			for(Map<String,Object> map:extItems){
				extIdName.put(map.get("id").toString(), map.get("item_name").toString());
				result.put(map.get("item_name").toString(), 0);
			}
		}
		if(extItemRecords!=null){
			JSONArray arr=null;
			JSONObject obj=null;
			for(Map<String,Object> map:extItemRecords){
				arr=JSON.parseArray(map.get("ext_wage").toString());
				for(int i=0;i<arr.size();i++){
					obj=arr.getJSONObject(i);
					result.put(extIdName.get(obj.getString("title")), Float.parseFloat(result.get(extIdName.get(obj.getString("title"))).toString())+Float.parseFloat(obj.getString("value")));
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取工资了表
	 * @param wageRecordService
	 * @param param
	 * @param source 请求来源:0-web,1-移动端
	 * @return
	 */
	public List<Map<String,Object>> getWageList(IpavwageRecordService wageRecordService,
			IpavwageTempletService wageTempletService,IpavuserService userService,
			Map<String,Object> param,
			Map<String,Object> should_pay_resolver,Map<String,Object> net_income_resolver,
			List<String> titleList,int source){
		List<Map<String, Object>> list = wageRecordService.getWageRecords(param);
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> extItems=null;
		if(list!=null){
			int length=list.size();
			if(length==0)
				return result;
			JSONObject obj=null;//工资记录
			JSONArray arr=null;//工资扩展项记录
			JSONObject extObj=null;//工资扩展记录单项
			JSONObject extItemJsons=null;
			int recordId=0;
			if(source==0){
					obj = (JSONObject) obj.toJSON(list.get(0));
					//获取表头扩展项信息
					if(obj.containsKey("ext_wage")&&!obj.getString("ext_wage").trim().equals("")){
						arr=JSON.parseArray(obj.getString("ext_wage"));
						length=arr.size();
						if(param.containsKey("id")){
							recordId=Integer.parseInt(param.get("id").toString());
							param.remove("id");
						}
						extItems=wageTempletService.getExtWageItems(param);
						if(extItems!=null&&extItems.size()>0){
							extItemJsons=new JSONObject();
							for(Map<String,Object> map:extItems)
								extItemJsons.put(map.get("id").toString(), JSON.toJSON(map));
							for(int i=0;i<length;i++){
								obj=arr.getJSONObject(i);
								if(extItemJsons.containsKey(obj.getString("title")))
									titleList.add(extItemJsons.getJSONObject(obj.getString("title")).get("item_name").toString());
							}
						}
					}
					if(titleList.size()==0)//无扩展项
						for (Map<String, Object> map : list) {
							obj = (JSONObject) obj.toJSON(map);
							obj.put("should_pay_user", should_pay_resolver);
							obj.put("net_income_user", net_income_resolver);
							result.add(obj);
						}
					else{ //有扩展项
						if(recordId==0)//列表页
							for (Map<String, Object> map : list) {
								obj = (JSONObject) obj.toJSON(map);
								obj.put("should_pay_user", should_pay_resolver);
								obj.put("net_income_user", net_income_resolver);
								if(arr!=null){
									arr=JSON.parseArray(obj.getString("ext_wage"));
									length=arr.size();
									for(int i=0;i<length;i++){
										extObj=arr.getJSONObject(i);
										if(!extItemJsons.containsKey(extObj.getString("title")))
											continue;
										extObj.put("resolve_user", extItemJsons.getJSONObject(extObj.getString("title")).getString("username"));
										extObj.put("picpath", extItemJsons.getJSONObject(extObj.getString("title")).getString("picpath"));
										extObj.put("orgname", extItemJsons.getJSONObject(extObj.getString("title")).getString("orgname"));
										extObj.put("mobile", extItemJsons.getJSONObject(extObj.getString("title")).getString("mobile"));
										extObj.put("duty", extItemJsons.getJSONObject(extObj.getString("title")).getString("duty"));
										extObj.put("title", extItemJsons.getJSONObject(extObj.getString("title")).getString("item_name"));
										arr.set(i, extObj);
									}
									obj.put("ext_wage", arr);
								}
								result.add(obj);
							}
						else{//详情页
							for (Map<String, Object> map : list) {
								obj = (JSONObject) obj.toJSON(map);
								obj.put("should_pay_user", should_pay_resolver);
								obj.put("net_income_user", net_income_resolver);
								arr=JSON.parseArray(obj.getString("ext_wage"));
								if(arr!=null){
									//length=arr.size();
									for(int i=0;i<arr.size();i++){
										extObj=arr.getJSONObject(i);
										if(!extItemJsons.containsKey(extObj.getString("title"))){
											arr.remove(i);
											i--;
											continue;
										}
										extObj.put("username", extItemJsons.getJSONObject(extObj.getString("title")).getString("username"));
										extObj.put("id",extObj.get("title"));
										extObj.put("title", extItemJsons.getJSONObject(extObj.getString("title")).getString("item_name"));
										arr.set(i, extObj);
									}
									obj.put("ext_wage", arr);
								}
								result.add(obj);
							}
						}
					}
			}else{
				if(param.containsKey("id")){//详情
					IpavuserEntity user=userService.queryUserId(param.get("userId").toString());
					param.clear();
					param.put("companyId", user.getCompanyid());
					extItems=wageTempletService.getExtWageItems(param);
					if(extItems!=null&&extItems.size()>0){
						extItemJsons=new JSONObject();
						for(Map<String,Object> map:extItems)
							extItemJsons.put(map.get("id").toString(), JSON.toJSON(map));
					}
					
					for (Map<String, Object> map : list) {
						obj=new JSONObject();
						obj.put("id", map.get("id"));
						obj.put("wage_month", map.get("wage_month"));
						obj.put("should_pay", map.get("should_pay"));
						obj.put("net_income", map.get("net_income"));
						arr=JSON.parseArray(map.get("ext_wage").toString());
						if(arr!=null){
							length=arr.size();
							for(int i=0;i<length;i++){
								extObj=arr.getJSONObject(i);
								extObj.put("resolve_user", extItemJsons.getJSONObject(extObj.getString("title")).getString("username"));
								extObj.put("id",extObj.get("title"));
								extObj.put("title", extItemJsons.getJSONObject(extObj.getString("title")).getString("item_name"));
								extObj.put("value", extObj.getString("value"));
								arr.set(i, extObj);
							}
						}else 
							arr=new JSONArray();
						obj.put("ext_wage", arr);
						result.add(obj);
					}
				}else{//列表
					for (Map<String, Object> map : list) {
						obj=new JSONObject();
						obj.put("id", map.get("id"));
						obj.put("wage_month", map.get("wage_month"));
						obj.put("should_pay", map.get("should_pay"));
						obj.put("net_income", map.get("net_income"));
						result.add(obj);
					}
				}
			}
			if(param.containsKey("id")){
				Map<String,Object> updateParam=new HashMap<String, Object>();
				updateParam.put("id", param.get("id"));
				wageRecordService.updateWageRecordReaded(updateParam);
			}
		}
		return result;
	}
}

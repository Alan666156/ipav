package com.ipav.mobile.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.mobile.param.RequestParameter;
import com.ipav.mobile.param.ResponseParameter;
import com.ipav.system.service.IpavuserService;
import com.ipav.wage.service.IpavwageRecordService;
import com.ipav.wage.service.IpavwageTempletService;
import com.ipav.wage.service.WageInfoService;

@Service
public class WageService {

	@Autowired
	private IpavuserService userService;

	@Autowired
	private IpavwageTempletService wageTempletService;

	@Autowired
	private IpavwageRecordService wageRecordService;

	/**
	 * 查看工资
	 * 
	 * @param requestParameter
	 * @param service
	 * @return
	 */
	public ResponseParameter getWageInfosBak(RequestParameter requestParameter,
			int service) {
		ResponseParameter response = new ResponseParameter(service);
		JSONObject body = (JSONObject) JSONObject.toJSON(requestParameter
				.getBody());
		JSONObject resultBody = new JSONObject();
		JSONObject result = new JSONObject();
		Map<String, Object> param = new HashMap<String, Object>();
		param = JSON.toJavaObject(body, Map.class);
		Map<String, Object> userParam = new HashMap<String, Object>();
		userParam.put("userid", param.get("userId"));
		userParam.put("valflg", 1);
		List<Map<String, Object>> userList = userService.getUserList(userParam);
		if (userList != null && userList.size() == 1) {
			param.put("userCode", userList.get(0).get("userno"));
			param.put("companyId", userList.get(0).get("companyid"));
		} else {
			param.put("userCode", -1);
			param.put("companyId", -1);
		}
		int count = 0;
		JSONObject obj = null;
		List<Map<String, Object>> templet = null;
		Map<String, Map<String, Object>> extWageIdMap = null;
		List<Map<String, Object>> basic = null;
		Map<String, Object> should_pay = null;
		Map<String, Object> net_income = null;
		String[] titleArr = null;
		float[] extSumArr = null;
		JSONArray arr = null;
		Map<String, Object> countMap = this.wageRecordService
				.getWageRecordsCount(param);
		if (countMap != null
				&& countMap.containsKey("counts")
				&& (count = Integer.parseInt(countMap.get("counts").toString())) > 0) {
			List<Map<String, Object>> list = this.wageRecordService
					.getWageRecords(param);
			if ("0".equals(param.get("type"))) {
				Map<String, Object> sum = this.wageRecordService
						.getWageRecordsSum(param);
				result.put("should_pay_sum", sum.get("should_pay_sum"));
				result.put("net_income_sum", sum.get("net_income_sum"));
			}
			basic = this.wageTempletService.getBasicWage(param);
			if (basic == null || basic.size() == 0) {
				should_pay = new HashMap<String, Object>();
				should_pay.put("id", "");
				should_pay.put("item_name", "应发工资");
				// should_pay.put("item_type", 2);
				should_pay.put("resolve_user", "");
				should_pay.put("username", "");
				// should_pay.put("remark", "");
				// should_pay.put("update_user", "");
				// should_pay.put("is_send", 1);
				// should_pay.put("status", 1);
				// should_pay.put("sequence", "");
				net_income = new HashMap<String, Object>();
				net_income.put("id", "");
				net_income.put("item_name", "实发工资");
				// net_income.put("item_type", 2);
				net_income.put("resolve_user", "");
				net_income.put("username", "");
				// net_income.put("remark", "");
				// net_income.put("update_user", "");
				// net_income.put("is_send", 1);
				// net_income.put("status", 1);
				// net_income.put("sequence", "");
			} else {
				for (Map<String, Object> m : basic) {
					// m.put("item_type", 2);
					// m.put("remark", "");
					// m.put("is_send", 1);
					// m.put("status", 1);
					// m.put("sequence", "");
					if (m.get("item_name").toString().equals("应发工资"))
						should_pay = m;
					else
						net_income = m;
				}
			}
			arr = new JSONArray();
			obj = new JSONObject();
			JSONArray tmp = null;
			for (Map<String, Object> map : list) {
				obj = (JSONObject) obj.toJSON(map);
				obj.put("should_pay_user", should_pay);
				obj.put("net_income_user", net_income);
				if ("0".equals(param.get("type"))) {
					if (titleArr == null && obj.containsKey("ext_wage")
							&& !obj.getString("ext_wage").trim().equals("")) {
						if (templet == null) {
							templet = this.wageTempletService
									.getExtWageItems(param);
							extWageIdMap = new HashMap<String, Map<String, Object>>();
							for (Map<String, Object> m : templet)
								extWageIdMap.put(m.get("id").toString()
										.toString(), m);
						}
						tmp = JSON.parseArray(obj.get("ext_wage").toString());
						int tempLen = tmp.size();
						titleArr = new String[tempLen];
						extSumArr = new float[tempLen];
						for (int i = 0; i < tempLen; i++) {
							titleArr[i] = extWageIdMap
									.get(tmp.getJSONObject(i)
											.getString("title"))
									.get("item_name").toString();

							tmp.getJSONObject(i)
									.put("resolve_user",
											extWageIdMap
													.get(tmp.getJSONObject(i)
															.getString("title"))
													.get("username") == null ? ""
													: extWageIdMap
															.get(tmp.getJSONObject(
																	i)
																	.getString(
																			"title"))
															.get("username"));
							// tmp.getJSONObject(i).put("picpath",extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("picpath")==null?"":extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("picpath"));
							// tmp.getJSONObject(i).put("orgname",extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("orgname"));
							// tmp.getJSONObject(i).put("mobile",extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("mobile")==null?"":extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("mobile"));
							// tmp.getJSONObject(i).put("duty",extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("duty")==null?"":extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("duty"));
							tmp.getJSONObject(i).put("title", titleArr[i]);

							extSumArr[i] = Float.parseFloat(tmp
									.getJSONObject(i).getString("value"));
						}
						obj.put("ext_wage", tmp);
					} else if (obj.containsKey("ext_wage")
							&& !obj.getString("ext_wage").trim().equals("")) {
						tmp = (JSONArray) JSONArray.toJSON(obj.get("ext_wage"));
						tmp = JSON.parseArray(obj.get("ext_wage").toString());
						int tempLen = tmp.size();
						titleArr = new String[tempLen];
						extSumArr = new float[tempLen];
						for (int i = 0; i < tempLen; i++) {
							tmp.getJSONObject(i)
									.put("resolve_user",
											extWageIdMap
													.get(tmp.getJSONObject(i)
															.getString("title"))
													.get("username") == null ? ""
													: extWageIdMap
															.get(tmp.getJSONObject(
																	i)
																	.getString(
																			"title"))
															.get("username"));
							// tmp.getJSONObject(i).put("picpath",extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("picpath")==null?"":extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("picpath"));
							// tmp.getJSONObject(i).put("orgname",extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("orgname"));
							// tmp.getJSONObject(i).put("mobile",extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("mobile")==null?"":extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("mobile"));
							// tmp.getJSONObject(i).put("duty",extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("duty")==null?"":extWageIdMap.get(tmp.getJSONObject(i).getString(
							// "title")).get("duty"));
							tmp.getJSONObject(i).put("title", titleArr[i]);
							extSumArr[i] += Float.parseFloat(tmp.getJSONObject(
									i).getString("value"));
						}
						obj.put("ext_wage", tmp);
					}
				}
				arr.add(obj);
			}
			result.put("ext_wage", arr);
		}

		resultBody.put("wageInfo", result);
		response.setBody(resultBody);
		return response;
	}

	/**
	 * 查看工资
	 * 
	 * @param requestParameter
	 * @param service
	 * @return
	 */
	public ResponseParameter getWageInfos(RequestParameter requestParameter,
			int service) {
		ResponseParameter response = new ResponseParameter(service);
		JSONObject body = (JSONObject) JSONObject.toJSON(requestParameter
				.getBody());
		JSONObject result = new JSONObject();
		Map<String, Object> param = new HashMap<String, Object>();
		param = JSON.toJavaObject(body, Map.class);
		List<Map<String, Object>> wageList = null;
		int count = 0;
		WageInfoService wageInfoService = new WageInfoService();
		if (!param.containsKey("id")) {
			count = wageInfoService.getWageListSize(wageRecordService, param);
			float shouldPaySum = 0;
			float netIncomeSum = 0;
			JSONArray arr = new JSONArray();
			if (count > 0) {
				wageList = wageInfoService.getWageList(wageRecordService,
						wageTempletService, userService, param, null, null,
						null, 1);
				Map<String, Object> sumInfos = wageInfoService.getWageSumInfos(
						wageRecordService, wageTempletService, param);
				if (sumInfos != null) {
					shouldPaySum = Float.parseFloat(sumInfos.get(
							"should_pay_sum").toString());
					netIncomeSum = Float.parseFloat(sumInfos.get(
							"net_income_sum").toString());
					sumInfos.remove("should_pay_sum");
					sumInfos.remove("net_income_sum");
					if (!sumInfos.isEmpty()) {
						Entry<String, Object> entry = null;
						Iterator<Entry<String, Object>> iter = sumInfos
								.entrySet().iterator();
						JSONObject obj = null;
						while (iter.hasNext()) {
							entry = iter.next();
							obj = new JSONObject();
							obj.put("title", entry.getKey());
							obj.put("value", entry.getValue().toString());
							arr.add(obj);
						}
					}
				}
			}
			result.put("counts", count);
			result.put("wageInfo", wageList);
			result.put("shouldPaySum", shouldPaySum);
			result.put("netIncomeSum", netIncomeSum);
			result.put("extSumInfos", arr);
		} else {
			wageList = wageInfoService
					.getWageList(wageRecordService, wageTempletService,
							userService, param, null, null, null, 1);
			Map<String, Object> should_pay_resolver = new HashMap<String, Object>();
			Map<String, Object> net_income_resolver = new HashMap<String, Object>();
			wageInfoService.initBasicResolver(wageTempletService, param,
					should_pay_resolver, net_income_resolver);
//			result.put("should_pay_resolver", should_pay_resolver.containsKey("username")?should_pay_resolver.get("username"):"");
//			result.put("net_income_resolver", net_income_resolver.containsKey("username")?net_income_resolver.get("username"):"");
			if(wageList!=null&&wageList.size()==1){
				wageList.get(0).put("should_pay_resolver", should_pay_resolver.containsKey("username")?should_pay_resolver.get("username"):"");
				wageList.get(0).put("net_income_resolver", net_income_resolver.containsKey("username")?net_income_resolver.get("username"):"");
			}
			result.put("wageInfo", wageList);
		}
		response.setBody(result);
		return response;
	}
}

package com.ipav.wage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavorgService;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.ExcelTools;
import com.ipav.system.util.ExcelView;
import com.ipav.system.util.RequestParamToMap;
import com.ipav.wage.service.IpavwageRecordService;
import com.ipav.wage.service.IpavwageTempletService;
import com.ipav.wage.service.WageInfoService;

@Controller
@RequestMapping(value = "ipav/wage")
public class IpavwageController {

	@Autowired
	private IpavwageTempletService wageTempletService;

	@Autowired
	private IpavwageRecordService wageRecordService;

	@Autowired
	private IpavorgService orgService;

	@Autowired
	private IpavuserService userService;

	/**
	 * 添加修改工资基本项
	 * 
	 * @param json
	 * @param session
	 * @param out
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/updateBWT", method = RequestMethod.POST) public
	 * JSONObject updateBasicWageTemplet(HttpServletRequest request, HttpSession
	 * session) { Map<String, Object> param =
	 * RequestParamToMap.convert(request); IpavuserEntity user =
	 * (IpavuserEntity) session.getAttribute("curuser"); JSONObject result = new
	 * JSONObject(); boolean flag = false; int id = 0; if (user == null)
	 * result.put("error", "用户已失效,请重新登陆"); else { if (param.containsKey("id"))
	 * {// 修改基本项 if (this.wageTempletService.updateBasicWageTemplet(param) > 0)
	 * flag = true; else result.put("error", "工资基本项修改失败!"); } else {// 新增基本项 id
	 * = this.wageTempletService.addBasicWageTemplet(param); if (id == 0)
	 * result.put("error", "工资基本项新增失败!"); else flag = true; } if (flag) {//
	 * 获取增加或修改的信息 if (id > 0) param.put("id", id); List<Map<String,Object>>
	 * basicItem = this.wageTempletService .getBasicWage(param); if (basicItem
	 * != null) { result.put("id", basicItem.get(0).get("id"));
	 * result.put("realWageUser", basicItem.get("real_wage_user"));
	 * result.put("shouldPayUser", basicItem.get("should_pay_user"));
	 * result.put("updateUser", basicItem.get("update_user")); } } } return
	 * result; }
	 */

	/**
	 * 获取工资基本项信息
	 * 
	 * @param json
	 * @param session
	 * @param out
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/getBWT", method = RequestMethod.POST) public
	 * JSONObject getBasicWageTempletInfo(HttpServletRequest request,
	 * HttpSession session) { Map<String, Object> param =
	 * RequestParamToMap.convert(request); IpavuserEntity user =
	 * (IpavuserEntity) session.getAttribute("curuser"); JSONObject result = new
	 * JSONObject(); boolean flag = false; if (user == null) result.put("error",
	 * "用户已失效,请重新登陆"); else { Map<String, Object> basicItem =
	 * this.wageTempletService .getBasicWage(param); if (basicItem != null) {
	 * result.put("id", basicItem.get("id")); result.put("realWageUser",
	 * basicItem.get("real_wage_user")); result.put("shouldPayUser",
	 * basicItem.get("should_pay_user")); result.put("updateUser",
	 * basicItem.get("update_user")); } } return result; }
	 */

	/**
	 * 添加修改工资自定义项
	 * 
	 * @param json
	 * @param session
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEWI", method = RequestMethod.POST)
	public JSONObject updateExtWageItem(HttpServletRequest request,
			HttpSession session) {
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		Map<String, Object> param = new HashMap<String, Object>();
		if (user == null)
			result.put("error", "用户已失效,请重新登陆");
		else if ("0".equals(request.getParameter("optType"))
				|| "1".equals(request.getParameter("optType"))
				|| "2".equals(request.getParameter("optType"))) {// 删除,启用,禁用自定义项
			param.put("updateUser", user.getUserid());
			String[] ids = request.getParameterValues("id");
			param.put("ids", ids);
			if ("0".equals(request.getParameter("optType")))
				this.wageTempletService.updateExtWageItemDel(param);
			else if ("1".equals(request.getParameter("optType")))
				this.wageTempletService.updateExtWageItemValid(param);
			else
				this.wageTempletService.updateExtWageItemInvalid(param);
		} else if ("3".equals(request.getParameter("optType"))
				|| "4".equals(request.getParameter("optType"))) {
			param = RequestParamToMap.convert(request);
			int count = this.wageTempletService.updateExtWageSequence(param);
		} else {
			param = RequestParamToMap.convert(request);
			if (param.containsKey("id")) {// 修改工资自定义项
				if (param.get("itemName").equals("实发工资")
						|| param.get("itemName").equals("应发工资")) {
					String resolveUser = param.get("resolveUser").toString();
					if (param.get("itemName").equals("实发工资"))
						param.put("realWageUser", resolveUser);
					else
						param.put("shouldPayUser", resolveUser);
					if (this.wageTempletService.updateBasicWageTemplet(param) <= 0)
						result.put("error", "工资基本项更改失败!");
				} else {
					if (this.wageTempletService.updateExtWageItem(param) == 0)
						result.put("error", "工资自定义项修改失败!");
				}
			} else {// 新增工资自定义项
				if (param.get("itemName").equals("实发工资")
						|| param.get("itemName").equals("应发工资")) {

					String resolveUser = param.get("resolveUser").toString();
					param.put("companyId", user.getCompanyid());
					if (param.get("itemName").equals("实发工资"))
						param.put("realWageUser", resolveUser);
					else
						param.put("shouldPayUser", resolveUser);
					param.put("userId", user.getUserid());
					if (this.wageTempletService.addBasicWageTemplet(param) <= 0)
						result.put("error", "工资基本项更改失败!");
				} else {
					param.put("companyId", user.getCompanyid());
					param.put("userId", user.getUserid());
					param.put("remark", param.containsKey("remark") ? param
							.get("remark").toString() : "");
					if (this.wageTempletService.addExtWageItem(param) == 0)
						result.put("error", "工资自定义项新增失败!");
				}
			}
		}
		return result;
	}

	/**
	 * 获取工资自定义项信息
	 * 
	 * @param json
	 * @param session
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/getEWI", method = RequestMethod.POST)
	public JSONObject getExtWageItems(HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> param = RequestParamToMap.convert(request);
		param.put("isDel", 0);
		if (param.containsKey("status") && "-1".equals(param.get("status")))
			param.remove("status");
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject item = null;
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> extList = null;
		List<Map<String, Object>> basic = null;
		Map<String, Object> basicInfo = null;
		if (user == null)
			result.put("error", "用户已失效,请重新登陆");
		else {
			if (!param.containsKey("optType")) {
				param.put("companyId", user.getCompanyid());
				list = new ArrayList<Map<String, Object>>();
				if (!param.containsKey("status")
						|| "1".equals(param.get("status").toString())) {
					basic = this.wageTempletService.getBasicWage(param);
					if (basic == null || basic.size() == 0) {
						basicInfo = new HashMap<String, Object>();
						basicInfo.put("id", "");
						basicInfo.put("item_name", "应发工资");
						basicInfo.put("item_type", 2);
						basicInfo.put("resolve_user", "");
						basicInfo.put("username", "");
						basicInfo.put("remark", "");
						basicInfo.put("update_user", "");
						basicInfo.put("is_send", 1);
						basicInfo.put("status", 1);
						basicInfo.put("sequence", "");
						list.add(basicInfo);
						basicInfo = new HashMap<String, Object>();
						basicInfo.put("id", "");
						basicInfo.put("item_name", "实发工资");
						basicInfo.put("item_type", 2);
						basicInfo.put("resolve_user", "");
						basicInfo.put("username", "");
						basicInfo.put("remark", "");
						basicInfo.put("update_user", "");
						basicInfo.put("is_send", 1);
						basicInfo.put("status", 1);
						basicInfo.put("sequence", "");
						list.add(basicInfo);
					} else {
						if (basic.size() == 1) {
							basicInfo = new HashMap<String, Object>();
							basicInfo.put("id", basic.get(0).get("id"));
							basicInfo.put("item_type", 2);
							basicInfo.put("resolve_user", "");
							basicInfo.put("username", "");
							basicInfo.put("remark", "");
							basicInfo.put("update_user", "");
							basicInfo.put("is_send", 1);
							basicInfo.put("status", 1);
							basicInfo.put("sequence", "");
							if (basic.get(0).get("item_name").equals("实发工资")) {
								basicInfo.put("item_name", "应发工资");
								list.add(basicInfo);
								for (int i = 0; i < basic.size(); i++) {
									basic.get(i).put("item_type", 2);
									basic.get(i).put("remark", "");
									basic.get(i).put("is_send", 1);
									basic.get(i).put("status", 1);
									basic.get(i).put("sequence", "");
								}
								list.addAll(basic);
							} else {
								for (int i = 0; i < basic.size(); i++) {
									basic.get(i).put("item_type", 2);
									basic.get(i).put("remark", "");
									basic.get(i).put("is_send", 1);
									basic.get(i).put("status", 1);
									basic.get(i).put("sequence", "");
								}
								list.addAll(basic);
								basicInfo.put("item_name", "实发工资");
								list.add(basicInfo);
							}
						} else
							list.addAll(basic);
					}
				}
				extList = this.wageTempletService.getExtWageItems(param);
				list.addAll(extList);
			} else {
				if ("0".equals(param.get("optType"))) {
					if (!param.containsKey("templetType"))
						list = this.wageTempletService.getExtWageItems(param);
					else if ("1".equals(param.get("templetType").toString())
							|| "2".equals(param.get("templetType").toString())) {
						String tmp = "1".equals(param.get("templetType")
								.toString()) ? "应发工资" : "实发工资";
						list = this.wageTempletService.getBasicWage(param);
						String tmpId = "";
						for (int i = 0; i < list.size(); i++) {
							tmpId = list.get(i).get("id").toString();
							if (!tmp.equals(list.get(i).get("item_name"))) {
								list.remove(i);
								break;
							}
						}
						if (list.size() == 0) {
							basicInfo = new HashMap<String, Object>();
							basicInfo.put("id", tmpId);
							basicInfo.put("item_name", tmp);
							basicInfo.put("item_type", 2);
							basicInfo.put("resolve_user", "");
							basicInfo.put("username", "");
							basicInfo.put("remark", "");
							basicInfo.put("update_user", "");
							basicInfo.put("is_send", 1);
							basicInfo.put("status", 1);
							basicInfo.put("sequence", "");
							list.add(basicInfo);
						}

					}
				}
			}
			if (!param.containsKey("optType")
					|| "0".equals(param.get("optType"))) {
				if (list != null) {
					for (Map<String, Object> map : list) {
						item = new JSONObject();
						item = (JSONObject) item.toJSON(map);
						array.add(item);
					}
				}
				result.put("info", array);
			}
		}
		return result;
	}

	/**
	 * 导出薪资模板
	 * 
	 * @param json
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/exportWT")
	public ModelAndView exportWageTemplet(ModelMap model,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> param = new HashMap<String, Object>();
		JSONObject result = new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		if (user == null) {
			result.put("error", "用户已失效,请重新登陆");
			return null;
		} else {
			param.put("companyId", user.getCompanyid());
			param.put("status", 1);
			param.put("isDel", 0);
			List<Map<String, Object>> extWages = this.wageTempletService
					.getExtWageItems(param);// 获取自定义工资项
			List<String> title = new ArrayList<String>();
			title.add("员工编号");
			title.add("员工姓名");
			title.add("应发工资");
			title.add("实发工资");
			if (extWages != null)
				for (Map<String, Object> map : extWages)
					title.add(map.get("item_name").toString());
			model.put("title", title);
			return new ModelAndView(new ExcelView("工资模板", "工资模板"), model);
		}
	}

	/**
	 * 工资导出
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/exportWR")
	public ModelAndView exportWageRecords(ModelMap model,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> param = RequestParamToMap.convert(request);
		JSONObject result = new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		List<Map<String, Object>> templet = null;
		Map<String, String> titleMap = null;
		if (user == null) {
			result.put("error", "用户已失效,请重新登陆");
			return null;
		} else {
			param.put("companyId", user.getCompanyid());
			List<Map<String, Object>> records = this.wageRecordService
					.getWageRecords(param);
			List<String> title = new ArrayList<String>();
			List<List<String>> content = new ArrayList<List<String>>();
			List<String> row = null;
			title.add("工资期间");
			title.add("员工部门");
			title.add("员工编号");
			title.add("员工姓名");
			title.add("应发工资");
			title.add("实发工资");
			boolean flag = false;
			int recordLen = 0;
			if (records != null && records.size() > 0) {
				recordLen = records.size();
				row = new ArrayList<String>();
				row.add(param.get("wageMonthCon").toString());
				row.add(records.get(0).containsKey("orgname") ? records.get(0)
						.get("orgname").toString() : "");
				row.add(records.get(0).containsKey("userno") ? records.get(0)
						.get("userno").toString() : "");
				row.add(records.get(0).get("username").toString());
				row.add(records.get(0).get("should_pay").toString());
				row.add(records.get(0).get("net_income").toString());
				if (records.get(0).containsKey("ext_wage")
						&& !records.get(0).get("ext_wage").equals("")) {
					flag = true;
					templet = this.wageTempletService.getExtWageItems(param);
					titleMap = new HashMap<String, String>();
					for (Map<String, Object> map : templet) {
						titleMap.put(map.get("id").toString(),
								map.get("item_name").toString());
					}
					JSONArray arr = JSON.parseArray(records.get(0)
							.get("ext_wage").toString());
					if (arr != null) {
						int arrLen = arr.size();
						JSONObject obj = null;
						for (int i = 0; i < arrLen; i++) {
							obj = arr.getJSONObject(i);
							title.add(titleMap.get(obj.getString("title")));
							row.add(obj.getString("value"));
						}

					}
				}
				content.add(row);
				for (int i = 1; i < recordLen; i++) {
					row = new ArrayList<String>();
					row.add(param.get("wageMonthCon").toString());
					row.add(records.get(i).containsKey("orgname") ? records
							.get(i).get("orgname").toString() : "");
					row.add(records.get(i).containsKey("userno") ? records
							.get(i).get("userno").toString() : "");
					row.add(records.get(i).get("username").toString());
					row.add(records.get(i).get("should_pay").toString());
					row.add(records.get(i).get("net_income").toString());
					if (flag) {
						JSONArray arr = JSON.parseArray(records.get(i)
								.get("ext_wage").toString());
						if (arr != null) {
							int arrLen = arr.size();
							JSONObject obj = null;
							for (int j = 0; j < arrLen; j++) {
								obj = arr.getJSONObject(j);
								row.add(obj.getString("value"));
							}

						}
					}
					content.add(row);
				}
			}
			model.put("title", title);
			model.put("content", content);
			return new ModelAndView(new ExcelView("工资模板", "工资模板"), model);
		}
	}

	/**
	 * 工资数据导入
	 * 
	 * @param session
	 * @param file
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/importWages")
	public JSONObject importWageInfos(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		List<ArrayList<String>> content = null;
		int width = 0;// 自定义工资项数
		int[] extTypeArr = null;// 自定义工资项类型
		String[] titleArr = null;// 自定义工资项title
		int[] extIdArr = null;// 自定义项id
		int contentLength = 0;// 表格内容长度
		List<String> row = null;// 每行内容
		Map<String, Object> validateMap = null;// 待校验数据项
		JSONArray arr = null;
		JSONObject json = null;
		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> contentMap = null;
		Map<String, String> extWageTypeMap = null;
		Map<String, String> extWageIdMap = null;
		Set<String> set = null;
		boolean flag = false;
		Set<String> errInfos = null;
		Map<String, String> employees = null;
		if (user == null)
			result.put("error", "用户已失效,请重新登陆");
		else {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile wageFile = multipartRequest.getFile("wageFile");
			if (!(wageFile.getOriginalFilename().endsWith(".xls") || wageFile
					.getOriginalFilename().endsWith(".xlsx"))) {
				result.put("error", "文件格式错误");
			} else {
				Map<String, Object> param = new HashMap<String, Object>();

				param.put("companyId", user.getCompanyid());
				param.put("status", 1);
				param.put("isDel", 0);
				List<Map<String, Object>> templet = this.wageTempletService
						.getExtWageItems(param);
				if (templet != null) {
					width = templet.size();
					extWageTypeMap = new HashMap<String, String>();
					extWageIdMap = new HashMap<String, String>();
					for (Map<String, Object> tmp : templet) {
						extWageIdMap.put(tmp.get("item_name").toString(), tmp
								.get("id").toString());
						extWageTypeMap.put(tmp.get("item_name").toString(), tmp
								.get("item_type").toString());
					}
				}
				try {
					if (wageFile.getOriginalFilename().endsWith(".xls"))
						content = new ExcelTools().readExcel03(wageFile
								.getInputStream());
					else
						content = new ExcelTools().readExcel07(wageFile
								.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (content != null && content.size() > 1) {
					set = new HashSet<String>();
					contentLength = content.size();
					if (content.get(0).size() != width + 4)
						result.put("error", "表单中数据与模板不符!");
					else {
						String wageMonth = request.getParameter("wageMonth");
						String wageTime = request.getParameter("wageTime");
						if (wageMonth.length() == 8)
							wageMonth = wageMonth.replace("年", "").replace("月",
									"");
						else
							wageMonth = wageMonth.replace("年", "0").replace(
									"月", "");
						if ((wageTime.length() - wageTime.indexOf("月")) == 1)
							wageTime = wageTime.replace("月", "-0").replace("日",
									"");
						else
							wageTime = wageTime.replace("月", "-").replace("日",
									"");
						if ((wageTime.length() - wageTime.indexOf("年")) == 3)
							wageTime = wageTime.replace("年", "-0");
						else
							wageTime = wageTime.replace("年", "-");
						if (width > 0) {
							extTypeArr = new int[width];
							titleArr = new String[width];
							extIdArr = new int[width];
							for (int i = 0; i < width; i++) {
								titleArr[i] = templet.get(i).get("item_name")
										.toString();
								if (!extWageTypeMap.containsKey(content.get(0)
										.get(i + 4))) {
									result.put("error", "模板不存在列:"
											+ content.get(0).get(i + 4));
									return result;
								}
								extTypeArr[i] = this.getType(Integer
										.parseInt(extWageTypeMap.get(
												content.get(0).get(i + 4))
												.toString()));
								extIdArr[i] = Integer.parseInt(extWageIdMap
										.get(content.get(0).get(i + 4))
										.toString());
							}
							errInfos = new HashSet<String>();
							Map<String, Object> userQueryParam = new HashMap<String, Object>();
							userQueryParam
									.put("companyid", user.getCompanyid());
							userQueryParam.put("valflg", 1);
							List<Map<String,Object>> userList = this.userService
									.getUserList(userQueryParam);
							if (userList != null && userList.size() > 0) {
								employees = new HashMap<String, String>();
								for (Map<String,Object> u : userList) {
									employees.put(u.get("userno").toString(),
											u.get("username").toString());
								}
							} else {
								result.put("error", "没有员工信息");
								return result;
							}
							for (int i = 1; i < contentLength; i++) {
								row = content.get(i);
								if (!employees.containsKey(row.get(0))) {
									errInfos.add("员工编号" + row.get(0) + "不存在");
									flag = true;
								} else {
									if (!employees.get(row.get(0)).equals(
											row.get(1))) {
										errInfos.add("员工编号" + row.get(0)
												+ "姓名不正确");
										flag = true;
									}
									if (!this.validateType(row.get(2),
											ContentUtil.EXCEL_TYPE_FLOAT)) {
										// result.put("error", "第" + i +
										// "存在异常格式数据!");
										errInfos.add("员工编号" + row.get(0)
												+ "应付工资格式不正确");
										flag = true;
									}
									if (!this.validateType(row.get(3),
											ContentUtil.EXCEL_TYPE_FLOAT)) {
										// result.put("error", "第" + i +
										// "存在异常格式数据!");
										errInfos.add("员工编号" + row.get(0)
												+ "实付工资格式不正确");
										flag = true;
									}
								}

								arr = new JSONArray();
								for (int j = 4; j < width + 4; j++) {
									if (!this.validateType(row.get(j).equals("")?"0":row.get(j),
											extTypeArr[j - 4])) {
										// result.put("error", "第" + i
										// + "存在异常格式数据!");
										errInfos.add("员工编号" + row.get(0) + ""
												+ titleArr[j - 4] + "格式不正确");
										flag = true;
									} else {
										json = new JSONObject();
										json.put("title", extIdArr[j - 4]);
										json.put("value", row.get(j));
										arr.add(json);
									}
								}
								contentMap = new HashMap<String, Object>();
								contentMap
										.put("companyId", user.getCompanyid());
								if (set.contains(row.get(0).toString())) {
									errInfos.add("员工编号" + row.get(0) + "重复导入");
									flag = true;
								} else
									set.add(row.get(0).toString());
								contentMap.put("userCode", row.get(0));
								contentMap.put("shouldPay", row.get(2));
								contentMap.put("netIncome", row.get(3));
								contentMap.put("wageMonth", wageMonth);
								contentMap.put("wageTime", wageTime);
								contentMap.put("extWage", arr.toString());
								contentMap.put("userId", user.getUserid());
								contentList.add(contentMap);
							}
						} else {
							for (int i = 1; i < contentLength; i++) {
								row = content.get(i);
								if (!employees.containsKey(row.get(0))) {
									errInfos.add("员工编号" + row.get(0) + "不存在");
									flag = true;
								} else {
									if (employees.get(row.get(0)).equals(
											row.get(1))) {
										errInfos.add("员工编号" + row.get(0)
												+ "姓名不正确");
										flag = true;
									}
									if (!this.validateType(row.get(2),
											ContentUtil.EXCEL_TYPE_FLOAT)) {
										// result.put("error", "第" + i +
										// "存在异常格式数据!");
										errInfos.add("员工编号" + row.get(0)
												+ "应付工资格式不正确");
										flag = true;
									}
									if (!this.validateType(row.get(3),
											ContentUtil.EXCEL_TYPE_FLOAT)) {
										// result.put("error", "第" + i +
										// "存在异常格式数据!");
										errInfos.add("员工编号" + row.get(0)
												+ "实付工资格式不正确");
										flag = true;
									}
								}
								contentMap = new HashMap<String, Object>();
								contentMap
										.put("companyId", user.getCompanyid());
								contentMap.put("userCode", row.get(0));
								contentMap.put("shouldPay", row.get(2));
								contentMap.put("netIncome", row.get(3));
								contentMap.put("wageMonth", wageMonth);
								contentMap.put("wageTime", wageTime);
								contentMap.put("extWage", "");
								contentMap.put("userId", user.getUserid());
								contentList.add(contentMap);
							}
						}
						if (flag) {
							result.put("error", "错误清单");
							result.put("errlist", errInfos);
						}
						if (!result.containsKey("error")) {
							param.clear();
							param.put("wageMonth", wageMonth);
							param.put("companyId", user.getCompanyid());
							this.wageRecordService.deleteWageRecord(param);
							this.wageRecordService.insertBatch(contentList);
						}
					}
				} else {
					result.put("error", "表单中没有内容!");
				}
			}
		}
		return result;
	}


	/**
	 * 工资查询
	 * 
	 * @param json
	 * @param session
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/getWages", method = RequestMethod.POST)
	public JSONObject getWagesInfo(HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> param = RequestParamToMap.convert(request);
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		boolean flag=false;
		if (user == null)
			result.put("error", "用户已失效,请重新登陆");
		else {
			if (!"0".equals(param.get("type"))) { // 员工查看自己工资
				param.put("userId", user.getUserid());
				param.put("status", -1);
				param.put("isSend", 1);
				flag=true;
			}
			param.put("companyId", user.getCompanyid());
			if (param.containsKey("first")) {
				int page = Integer.parseInt(param.get("first").toString()) - 1;
				param.put("page", page);
			}
			List<String> title = null;
			WageInfoService wageInfoService = new WageInfoService();
			int count = wageInfoService.getWageListSize(wageRecordService,
					param);
			result.put("counts", count);
			if (count > 0) {
				Map<String, Object> should_pay_resolver = new HashMap<String, Object>();
				Map<String, Object> net_income_resolver = new HashMap<String, Object>();
				int tmpId=0;
				if(param.containsKey("id")){
					tmpId=Integer.parseInt(param.get("id").toString());
					param.remove("id");
				}
				wageInfoService.initBasicResolver(wageTempletService, param,
						should_pay_resolver, net_income_resolver);
				if(tmpId>0)
					param.put("id", tmpId);
				result.put("should_pay_resolver", should_pay_resolver);
				result.put("net_income_resolver", net_income_resolver);
				title = new ArrayList<String>();
				List<Map<String, Object>> list = wageInfoService.getWageList(
						wageRecordService, wageTempletService, userService,
						param, should_pay_resolver, net_income_resolver, title,
						0);
				result.put("info", list);
				Map<String, Object> wageSumInfos = wageInfoService
						.getWageSumInfos(wageRecordService, wageTempletService,
								param);
				result.put("should_pay_sum", wageSumInfos.get("should_pay_sum"));
				result.put("net_income_sum", wageSumInfos.get("net_income_sum"));
				float[] extSumArr = null;
				if (title != null) {
					extSumArr = new float[title.size()];
					for (int i = 0; i < title.size(); i++)
						extSumArr[i] = Float.parseFloat(wageSumInfos.get(
								title.get(i)).toString());
				} else
					extSumArr = new float[0];
				result.put("titleArr", title);
				result.put("extSumArr", extSumArr);
				if(flag){
					param.clear();
					param.put("id", list.get(0).get("id"));
					wageRecordService.updateWageRecordReaded(param);
				}
			}
		}
		return result;
	}


	/**
	 * 用户修改删除工资
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateWI", method = RequestMethod.POST)
	public JSONObject updateWagesInfo(HttpServletRequest request,
			HttpSession session) {
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		int[] extTypeArr = null;// 自定义工资项类型
		String[] titleArr = null;// 自定义工资项title
		Map<String, Object> param = null;
		Map<String, String> extWageIdMap = null;
		Map<String, String> extWageTypeMap = null;
		JSONObject extWage = null;
		int length = 0;
		if (user == null)
			result.put("error", "用户已失效,请重新登陆");
		else {
			param = new HashMap<String, Object>();
			if ("0".equals(request.getParameter("optType"))) { // 删除工资记录
				param.put("ids", request.getParameterValues("id"));
				param.put("updateUser", user.getUserid());
				this.wageRecordService.updateWageRecordDel(param);
			} else if ("5".equals(request.getParameter("optType"))) {// 发送系统工资单
				param = RequestParamToMap.convert(request);
				param.put("companyId", user.getCompanyid());
				// param.put("status", 1);//发送状态
				this.wageRecordService.updateWageRecordStatus(param);
			} else {// 修改工资记录
				param = RequestParamToMap.convert(request);
				if (!this.validateType(param.get("shouldPay").toString(),
						ContentUtil.EXCEL_TYPE_FLOAT))
					result.put("error", "应付工资格式不正确");
				else if (!this.validateType(param.get("netIncome").toString(),
						ContentUtil.EXCEL_TYPE_FLOAT))
					result.put("error", "实付工资格式不正确");
				else if (param.containsKey("extWage")) {
					param.put("companyId", user.getCompanyid());
					String tmpId=param.get("id").toString();
					param.remove("id");
					List<Map<String, Object>> templet = this.wageTempletService
							.getExtWageItems(param);
					param.put("id", tmpId);
					extWageIdMap=new HashMap<String, String>();
					extWageTypeMap=new HashMap<String, String>();
					for (Map<String, Object> m : templet) {
						extWageIdMap.put(m.get("id").toString(),
								m.get("item_name").toString());
						extWageTypeMap.put(m.get("id").toString(),
								m.get("item_type").toString());
					}
					String extWageTmp = param.get("extWage").toString();
					param.put("extWage", extWageTmp);
					JSONArray extWages = JSON.parseArray(extWageTmp);
					length = extWages.size();
					extTypeArr = new int[length];
					titleArr = new String[length];

					for (int i = 0; i < length; i++) {
						extWage = extWages.getJSONObject(i);
						titleArr[i] = extWageIdMap.get(extWage.getString("title"));
//						extWage.put("id",
//								extWageIdMap.get(extWage.getString("id")));
						extTypeArr[i] = Integer.parseInt(extWageTypeMap.get(extWage.get("title").toString()));
						if (!this.validateType(extWage.getString("value"),
								extTypeArr[i])) {
							result.put("error", titleArr[i] + "格式不正确");
							break;
						}

					}
				}
				if (!result.containsKey("error")) {
					param.put("updateUser", user.getUserid());
					//param.put("status", 0);
					if (this.wageRecordService.updateWageRecord(param) != 1)
						result.put("error", "工资修改失败");
				}
			}
		}
		return result;
	}

	/**
	 * 获取组织机构
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepartments", method = RequestMethod.POST)
	public JSONObject getDepartments(HttpServletRequest request,
			HttpSession session) {
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		int count = 0;
		if (user == null)
			result.put("error", "用户已失效,请重新登陆");
		else {
			List orglist = orgService.getOrgTrees(String.valueOf(user
					.getCompanyid()));
			if (orglist != null) {
				JSONArray arr = (JSONArray) JSONArray.toJSON(orglist);
				result.put("info", arr);
			}
		}
		return result;
	}

	/**
	 * 用户自动补全
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/autoCompleteUser", method = RequestMethod.POST)
	public JSONObject autoCompleteUser(HttpServletRequest request,
			HttpSession session) {
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		if (user != null) {
			Map<String, Object> param = RequestParamToMap.convert(request);
			// param.put("companyId", 1000018);
			param.put("companyId", user.getCompanyid());
			param.put("counts", 5);
			List<String> userList = this.userService.autoCompleteUser(param);
			if (userList != null) {
				JSONArray arr = (JSONArray) JSONArray.toJSON(userList);
				;
				result.put("info", arr);
			}
		}
		return result;
	}

	/**
	 * 组织结构
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initLevelInfos", method = RequestMethod.POST)
	public JSONObject initAllLevelInfos(HttpServletRequest request,
			HttpSession session) {
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		JSONArray arr = null;
		if (user == null)
			result.put("error", "用户已失效,请重新登陆");
		else {
			List<JSONObject> list = this.userService.getAllLevelInfos(user
					.getCompanyid());
			if (list != null) {
				arr = (JSONArray) JSON.toJSON(list);
				result.put("info", arr);
			}
		}
		return result;
	}

	/**
	 * 验证数据
	 * 
	 * @param value
	 * @param type
	 * @param precision
	 * @return
	 */
	private boolean validateType(String value, int type) {
		boolean result = false;
		if (type == ContentUtil.EXCEL_TYPE_TEXT)
			result = true;
		else if (this.validateNum(value)) {
			if (value.indexOf(".") == -1)
				result = true;
			else if (value.length() - value.indexOf(".") - 1 <= 2)
				result = true;
		}
		return result;
	}

	/**
	 * 验证是否是数值
	 * 
	 * @param value
	 * @return
	 */
	public boolean validateNum(String value) {
		return value.matches("^\\d+\\.\\d+|^[1-9]\\d*|0");

	}

	/**
	 * 根据自定义工资项类型及精度确定数据类型
	 * 
	 * @param type
	 * @param precision
	 * @return
	 */
	private int getType(int type) {
		int result = 0;
		switch (type) {
		case ContentUtil.EXCEL_TYPE_TEXT:
			result = ContentUtil.EXCEL_TYPE_TEXT;
			break;
		default:
			result = ContentUtil.EXCEL_TYPE_FLOAT;
			break;
		}
		return result;
	}
	
	/**
	 * excel内容读取
	 * @param file
	 */
	public void readExcel(File file) {
		JSONObject result = new JSONObject();
		List<ArrayList<String>> content = null;
		int width = 0;// 自定义工资项数
		int contentLength = 0;// 表格内容长度
		List<String> row = null;// 每行内容
		List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> contentMap = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			if (file.getName().endsWith(".xls"))
				content = new ExcelTools().readExcel03(fis);
			else
				content = new ExcelTools().readExcel07(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (content != null && content.size() > 1) {
			contentLength = content.size();
			if (content.get(0).size() != width + 3)
				result.put("error", "表单中数据与模板不符!");
			else {
				for (int i = 1; i < contentLength; i++) {
					row = content.get(i);
					if (!(this.validateType(row.get(1).equals("")?"0":row.get(1),
							ContentUtil.EXCEL_TYPE_FLOAT) && this.validateType(
							row.get(2).equals("")?"0":row.get(2), ContentUtil.EXCEL_TYPE_FLOAT))) {
						result.put("error", "第" + i + "存在异常格式数据!");
						break;
					}
					contentMap = new HashMap<String, Object>();
					contentMap.put("companyId", 111);
					contentMap.put("userCode", row.get(0));
					contentMap.put("shouldPay", row.get(1));
					contentMap.put("netIncome", row.get(2));
					contentList.add(contentMap);
				}
				if (!result.containsKey("error"))
					this.wageRecordService.insertBatch(contentList);
			}
		} else {
			result.put("error", "表单中没有内容!");

		}
	}
}
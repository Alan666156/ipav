package com.ipav.wage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "ipav/wage")
public class BaseController {
	
	private final String HRM_WAGE_URL="wage/wageManager";//人事工资列表页
	
	private final String EMPLOYEE_WAGE_URL="wage/mySalary";

	@RequestMapping(value="/wageManager")
	public String hrmWageForward(){
		return HRM_WAGE_URL;
	}
	
	@RequestMapping(value="/myWage")
	public String employeeWageForward(HttpServletRequest request,ModelMap model){
		if(request.getParameter("wid")!=null)
			model.put(("wid"),request.getParameter("wid"));	
		return EMPLOYEE_WAGE_URL;
		 	
	}
}

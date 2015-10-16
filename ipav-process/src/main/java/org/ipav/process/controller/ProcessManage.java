package org.ipav.process.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipav.process.entity.ProcessType;

@Controller
public class ProcessManage {
	
	/***
	 * 跳转到流程主界面
	 * @return
	 */
	@RequestMapping(value="/processType")
	public String gotoLogin(HttpServletRequest request,ModelMap model){
		ProcessType p=new ProcessType("工作单","Jack",new Date(),"0");
		model.addObject("p",p);
		return "process/processMain";
	}
}

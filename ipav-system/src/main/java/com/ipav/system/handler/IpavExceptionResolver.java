package com.ipav.system.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月14日 下午6:58:54	
 * 上海天道启科电子有限公司
 */
public class IpavExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("excpMessage", ex.getMessage());
		ModelAndView mav = new ModelAndView("system/error",map);
		return mav;
	}

 
	
	
}

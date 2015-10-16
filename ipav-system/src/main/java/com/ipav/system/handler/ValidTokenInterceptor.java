package com.ipav.system.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ipav.system.entity.IpavForm;
import com.ipav.system.service.IpavFormManager;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月14日 上午11:42:21	
 * 上海天道启科电子有限公司
 */
public class ValidTokenInterceptor implements HandlerInterceptor{
	private String mappingURL;//利用正则映射到需要拦截的路径    
   
	public void setMappingURL(String mappingURL) {    
           this.mappingURL = mappingURL;    
   } 
	@Autowired
	private IpavFormManager formManager;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = true;
		String token = request.getParameter(IpavForm.FORM_UNIQ_ID_FIELD_NAME);
		if (token != null) {
			if (formManager.hasForm(request, token)) {
				formManager.destroyToken(request, token);
			} else {
				flag = false;
				throw new Exception("表单重复提交或过期，令牌[" + token + "]");
			}
		}
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}

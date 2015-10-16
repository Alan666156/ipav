package com.ipav.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipav.system.entity.IpavuserEntity;

public class AuthorFilter implements Filter {
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		String requestPath=httpServletRequest.getServletPath();
		HttpServletResponse resp=(HttpServletResponse)response;
		if(!requestPath.equals("/pages/top.jsp")&&requestPath.endsWith(".jsp")){//不能直接访问jsp页面
//			request.setAttribute("warninginfo", "0");
//			request.getRequestDispatcher("/author").forward(request, response);
			if (httpServletRequest.getHeader("x-requested-with") != null
					&& httpServletRequest.getHeader("x-requested-with").equals(
					"XMLHttpRequest"))
				resp.setHeader("errmsg", "0");
			else{
				//resp.sendRedirect("/author");
				request.setAttribute("warninginfo", "1");
				request.getRequestDispatcher("/author").forward(request, response);
			}
			//((HttpServletResponse)response).sendRedirect("/author");
			return;
		}
		if(requestPath.equals("/findPassword")
				||requestPath.equals("/gotoRegister")
				||requestPath.equals("/registerUser")
				||requestPath.equals("/gotoRegSuccess")
				||requestPath.equals("/createCode")
				||requestPath.equals("/checkhavUser")
				||requestPath.equals("/checkPhonecode")
				||requestPath.equals("/checkCode")
				||requestPath.equals("/passwordValdt")
				||requestPath.equals("/pwdReset")
				||requestPath.equals("/author")
				||requestPath.equals("/ipav")
				||requestPath.equals("/ipavlogin")
				||requestPath.endsWith(".css")
				||requestPath.endsWith(".jpg")
				||requestPath.endsWith(".png")
				||requestPath.endsWith(".js")){//不过滤js,css,首頁,登陆action
			chain.doFilter(request, response);
			return;
		}
		IpavuserEntity user = (IpavuserEntity) httpServletRequest.getSession().getAttribute("curuser");
		if(user==null&&!requestPath.equals("/warningInfo")){//用户不存在
//			request.setAttribute("warninginfo", "1");
//			request.getRequestDispatcher("/author").forward(request, response);
			if (httpServletRequest.getHeader("x-requested-with") != null
					&& httpServletRequest.getHeader("x-requested-with").equals(
					"XMLHttpRequest"))
				resp.setHeader("errmsg", "1");
			else{
//				resp.sendRedirect("/author");
				request.setAttribute("warninginfo", "1");
				request.getRequestDispatcher("/author").forward(request, response);
			}
//			((HttpServletResponse)response).sendRedirect("/author");
			return;
		}
		
		if(requestPath.equals("/system/top")||requestPath.equals("/system/left")||requestPath.equals("/system/index")||requestPath.equals("/system/floor")){
			chain.doFilter(request, response);
			return;
		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}

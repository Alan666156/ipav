package com.ipav.system.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestParamToMap {
	public static Map<String,Object> convert(HttpServletRequest request){
		Map<String,Object> param=new HashMap<String, Object>();
		Enumeration<String> e=request.getParameterNames();
		String name="";
		String str[]=null;
		while(e.hasMoreElements()){
			name=e.nextElement();
//			param.put(name, request.getParameter(name));
			str=request.getParameterValues(name);
			if(str.length==1)
				param.put(name, str[0].replaceAll("\"", "\\\"").replaceAll("\'", "\\\'").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
			else{
				for(int i=0;i<str.length;i++)
					str[i]=str[i].replaceAll("\"", "\\\"").replaceAll("\'", "\\\'").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
				param.put(name, str);
			}
		}
		return param;
	}
}

package com.ipav.system.tag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;



/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月26日 下午1:48:21	
 * 上海天道启科电子有限公司
 */
public class ConvertTag extends SimpleTagSupport{
	private String type;
	private String value;
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		String result ="";
		if(StringUtils.isNotEmpty(value)&&StringUtils.isNotEmpty(type)){
			result= getPrameterValue(type,value);
		}
		JspWriter out= getJspContext().getOut();
		out.print(result);
		//super.doTag();
	}
	
	 private String getPrameterValue(String type ,String code) {  
	        String restr = "";
	        WebApplicationContext webApplicationContext =ContextLoader.getCurrentWebApplicationContext();
	        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory )webApplicationContext.getBean("sqlSessionFactory");
	        SqlSession sqlSession = sqlSessionFactory.openSession();
	        Connection con = sqlSession.getConnection();
	        String mysql = "select  IPAVNAME  from ipav_parameter  where  IPAVTYPE = ? and IPAVCODE =?";  
	        PreparedStatement statement = null;  
	        ResultSet set = null;
	        try { 
	            statement = (PreparedStatement) con.prepareStatement(mysql);
	            statement.setString(1, type);
	            statement.setString(2, code);
	            set= statement.executeQuery();  
	            if(set.next()){
	            	restr = set.getString(1);
	            }
	             
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	            return null;  
	        } finally {  
	  
	            try {  
	                set.close();  
	                statement.close();  
	                con.close();  
	            } catch (SQLException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return restr;  
	    }  
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

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



/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月26日 下午1:48:21	
 * 上海天道启科电子有限公司
 */
public class MenunameTag extends SimpleTagSupport{
	private String menuid;
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		String result ="";
		if(StringUtils.isNotEmpty(menuid)){
			result= getPrameterValue(menuid);
		}
		JspWriter out= getJspContext().getOut();
		out.print(result);
		//super.doTag();
	}
	
	 private String getPrameterValue(String menuid) {  
	        String restr = "";
		 	Connection con= ConnectionUtil.getConnection();
	        String mysql = "select  menuname  from ipav_menu  where  menuid = ?";  
	        PreparedStatement statement = null;  
	        ResultSet set = null;
	        try { 
	            statement = (PreparedStatement) con.prepareStatement(mysql);
	            statement.setString(1, menuid);
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

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}  
	
	
}

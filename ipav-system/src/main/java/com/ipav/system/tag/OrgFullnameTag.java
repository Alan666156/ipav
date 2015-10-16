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
 * Created on 2014年12月3日 上午11:17:37	
 * 上海天道启科电子有限公司
 */
public class OrgFullnameTag extends SimpleTagSupport{
	private String orgid;
	@Override
	public void doTag() throws JspException, IOException {
		String result ="";
		if(StringUtils.isNotEmpty(orgid)){
			result= getPrameterValue(orgid);
		}
		JspWriter out= getJspContext().getOut();
		out.print(result);
		//super.doTag();
	}
	
	 private String getPrameterValue(String orgid) {  
	        String restr = "";
		 	Connection con= ConnectionUtil.getConnection();
	        String mysql = "select  orgfullname  from ipav_org  where  orgid = ?";  
	        PreparedStatement statement = null;  
	        ResultSet set = null;
	        try { 
	            statement = (PreparedStatement) con.prepareStatement(mysql);
	            statement.setString(1, orgid);
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

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	 
	 
}

package com.ipav.system.tag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月27日 下午1:03:15	
 * 上海天道启科电子有限公司
 */
public class OptionTag extends BodyTagSupport{
	
	private String curvalue;//当前值
	private String parmtype;//字典表类型
	private List<Map> items;//传list
	private String use_name;
	private String use_value;
	
	public OptionTag(){
		use_name ="key";
		use_value ="value";
	}
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		if(items==null){
			items = new ArrayList<Map>();
		}
		if(StringUtils.isNotEmpty(parmtype)){
			items= getPrameterValue(parmtype);
		}
		if(StringUtils.isEmpty(use_name)){
			use_name = "key";
		}if(StringUtils.isEmpty(use_value)){
			use_value = "value";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<option value = \"\"></option>");
		if(items.size()>0){
			for (Map obj : items) {
				 sb.append("<option value = \""+obj.get(use_value)+"\"");
				 if (curvalue != null && (curvalue.equals(obj.get(use_value)))){
                    // selected = "selected";
					 sb.append("  selected ");
                 }
				 sb.append(">"+obj.get(use_name)+"</option>");
			}
		} 
		try {
			JspWriter out =pageContext.getOut();
			out.print(sb.toString());
			return EVAL_PAGE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	 private List getPrameterValue(String type) {  
	        String restr = "";
//		 	Connection con= ConnectionUtil.getConnection();
		 	
		 	WebApplicationContext webApplicationContext =ContextLoader.getCurrentWebApplicationContext();
	        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory )webApplicationContext.getBean("sqlSessionFactory");
	        SqlSession sqlSession = sqlSessionFactory.openSession();
	        Connection con = sqlSession.getConnection();
	        
	        String mysql = "select  IPAVNAME  ,IPAVCODE  from ipav_parameter  where  IPAVTYPE = ?";  
	        PreparedStatement statement = null;  
	        ResultSet set = null;
	        List<Map> list = new ArrayList<Map>();
	        try { 
	            statement = (PreparedStatement) con.prepareStatement(mysql);
	            statement.setString(1, type);
	            set= statement.executeQuery();  
	            while(set.next()){
	            	Map map = new HashMap();
	            	map.put("key", set.getString(1));
	            	map.put("value", set.getString(2));
	            	list.add(map);
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
	        return list;  
	    }  

	public String getCurvalue() {
		return curvalue;
	}

	public void setCurvalue(String curvalue) {
		this.curvalue = curvalue;
	}

	public String getParmtype() {
		return parmtype;
	}

	public void setParmtype(String parmtype) {
		this.parmtype = parmtype;
	}

	public List<Map> getItems() {
		return items;
	}

	public void setItems(List<Map> items) {
		this.items = items;
	}

	public String getUse_name() {
		return use_name;
	}

	public void setUse_name(String use_name) {
		this.use_name = use_name;
	}

	public String getUse_value() {
		return use_value;
	}

	public void setUse_value(String use_value) {
		this.use_value = use_value;
	}
	
	
	
}

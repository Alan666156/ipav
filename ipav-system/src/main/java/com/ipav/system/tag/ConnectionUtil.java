package com.ipav.system.tag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月26日 下午4:49:37	
 * 上海天道启科电子有限公司
 */
public class ConnectionUtil {
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			 conn = DriverManager.getConnection("jdbc:mysql://172.16.2.148:3306/ipavdb?useUnicode=true&characterEncoding=utf-8","root","root123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}

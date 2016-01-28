package com.ipav;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplication {
	private static Logger logger = LoggerFactory.getLogger(WebApplication.class);

	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		tomcat.getConnector().setURIEncoding("UTF-8");
		String path = WebApplication.class.getResource("/").getPath();
		tomcat.addWebapp("", path.substring(0, path.indexOf("target")) + "src/main/webapp");
		tomcat.start();
		logger.info("ipav Started tomcat");
		tomcat.getServer().await();
		
	}
}

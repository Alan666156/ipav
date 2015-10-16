package com.ipav.system.util;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;



/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月18日 下午2:27:23	
 * 上海天道启科电子有限公司
 */
public class IpavAuthenticator extends Authenticator{
	    private String username;  
	    private String password;  
	  
	    /** 
	     *  
	     * @author geloin 
	     * @date 2012-5-8 下午2:48:53 
	     * @param username 
	     * @param password 
	     */  
	    public IpavAuthenticator(String username, String password) {  
	        super();  
	        this.username = username;  
	        this.password = password;  
	    }  
	  
	    protected PasswordAuthentication getPasswordAuthentication() {  
	        return new PasswordAuthentication(username, password);  
	    }  
}

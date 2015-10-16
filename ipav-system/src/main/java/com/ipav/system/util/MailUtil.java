package com.ipav.system.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月18日 下午2:04:07	
 * 上海天道启科电子有限公司
 */
public class MailUtil {
	 /** 
     * 发件人邮箱服务器 
     */  
    private  String emailHost;  
    /** 
     * 发件人邮箱 
     */  
    private String emailFrom;  
  
    /** 
     * 发件人用户名 
     */  
    private String emailUserName;  
  
    /** 
     * 发件人密码 
     */  
    private String emailPassword;  
  
    /** 
     * 收件人邮箱，多个邮箱以“;”分隔 
     */  
    private String toEmails;  
    /** 
     * 邮件主题 
     */  
    private String subject;  
    /** 
     * 邮件内容 
     */  
    private String content;  
    /** 
     * 邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址 
     */  
    private Map<String, String> pictures;  
    /** 
     * 邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址 
     */  
    private Map<String, String> attachments;  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the emailHost 
     */  
    public String getEmailHost() {  
          
        if (StringUtils.isEmpty(emailHost)||"".equals(emailHost.trim())) {  
            emailHost = ContentUtil.EMAILHOST;  
        }  
        return emailHost;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param emailHost 
     *            the emailHost to set 
     */  
    public void setEmailHost(String emailHost) {  
        this.emailHost = emailHost;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the emailFrom 
     */  
    public String getEmailFrom() {  
        if (StringUtils.isEmpty(emailFrom)||"".equals(emailFrom.trim())) {  
            emailFrom = ContentUtil.EMAILFROM;  
        }  
        return emailFrom;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param emailFrom 
     *            the emailFrom to set 
     */  
    public void setEmailFrom(String emailFrom) {  
        this.emailFrom = emailFrom;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the emailUserName 
     */  
    public String getEmailUserName() {  
        if (StringUtils.isEmpty(emailUserName)||"".equals(emailUserName.trim())) {  
            emailUserName = ContentUtil.EMAILUSERNAME;  
        }  
        return emailUserName;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param emailUserName 
     *            the emailUserName to set 
     */  
    public void setEmailUserName(String emailUserName) {  
        this.emailUserName = emailUserName;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the emailPassword 
     */  
    public String getEmailPassword() {  
        if (StringUtils.isEmpty(emailPassword)||"".equals(emailPassword.trim())) {  
            emailPassword = ContentUtil.EMAILPASSWORD;  
        }  
        return emailPassword;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param emailPassword 
     *            the emailPassword to set 
     */  
    public void setEmailPassword(String emailPassword) {  
        this.emailPassword = emailPassword;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the toEmails 
     */  
    public String getToEmails() {  
    	if (StringUtils.isEmpty(toEmails)||"".equals(toEmails.trim())) {
    		return "";
    	}else{
    		return toEmails;
    	}
       
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param toEmails 
     *            the toEmails to set 
     */  
    public void setToEmails(String toEmails) {  
        this.toEmails = toEmails;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the subject 
     */  
    public String getSubject() {  
        if (StringUtils.isEmpty(subject)||"".equals(subject.trim())) {
            subject = "无主题";  
        }  
        return subject;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param subject 
     *            the subject to set 
     */  
    public void setSubject(String subject) {  
        this.subject = subject;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the content 
     */  
    public String getContent() {  
        if (StringUtils.isEmpty(content)||"".equals(content.trim())) {
    		return "";
    	}else{
    		return content;
    	}
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param content 
     *            the content to set 
     */  
    public void setContent(String content) {  
        this.content = content;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the pictures 
     */  
    public Map<String, String> getPictures() {  
        return pictures;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param pictures 
     *            the pictures to set 
     */  
    public void setPictures(Map<String, String> pictures) {  
        this.pictures = pictures;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @return the attachments 
     */  
    public Map<String, String> getAttachments() {  
        return attachments;  
    }  
  
    /** 
     *  
     * @author geloin 
     * @date 2012-5-9 上午10:49:01 
     * @param attachments 
     *            the attachments to set 
     */  
    public void setAttachments(Map<String, String> attachments) {  
        this.attachments = attachments;  
    }  
  
    /** 
     * 发送邮件 
     *  
     * @author geloin 
     * @date 2012-5-9 上午11:18:21 
     * @throws Exception 
     */  
    public void sendEmail() throws Exception {  
  
        if (this.getEmailHost().equals("") || this.getEmailFrom().equals("")  
                || this.getEmailUserName().equals("")  
                || this.getEmailPassword().equals("")) {  
            throw new RuntimeException("发件人信息不完全，请确认发件人信息！");  
        }  
  
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
  
        // 设定mail server  
        senderImpl.setHost(emailHost);  
  
        // 建立邮件消息  
        MimeMessage mailMessage = senderImpl.createMimeMessage();  
  
        MimeMessageHelper messageHelper = null;  
        messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");  
        // 设置发件人邮箱  
        messageHelper.setFrom(emailFrom);  
  
        // 设置收件人邮箱  
        String[] toEmailArray = toEmails.split(";");  
        List<String> toEmailList = new ArrayList<String>();  
        if (null == toEmailArray || toEmailArray.length <= 0) {  
            throw new RuntimeException("收件人邮箱不得为空！");  
        } else {  
            for (String s : toEmailArray) {  
            	if (StringUtils.isEmpty(s)||"".equals(s.trim())){
            		s = "";
            	}
                if (!"".equals(s)) {  
                    toEmailList.add(s);  
                }  
            }  
            if (null == toEmailList || toEmailList.size() <= 0) {  
                throw new RuntimeException("收件人邮箱不得为空！");  
            } else {  
                toEmailArray = new String[toEmailList.size()];  
                for (int i = 0; i < toEmailList.size(); i++) {  
                    toEmailArray[i] = toEmailList.get(i);  
                }  
            }  
        }  
        messageHelper.setTo(toEmailArray);  
  
        // 邮件主题  
        messageHelper.setSubject(subject);  
  
        // true 表示启动HTML格式的邮件  
        messageHelper.setText(content, true);  
  
        // 添加图片  
//        if (null != pictures) {  
//            for (Iterator<Map.Entry<String, String>> it = pictures.entrySet()  
//                    .iterator(); it.hasNext();) {  
//                Map.Entry<String, String> entry = it.next();  
//                String cid = entry.getKey();  
//                String filePath = entry.getValue();  
//                if (null == cid || null == filePath) {  
//                    throw new RuntimeException("请确认每张图片的ID和图片地址是否齐全！");  
//                }  
//  
//                File file = new File(filePath);  
//                if (!file.exists()) {  
//                    throw new RuntimeException("图片" + filePath + "不存在！");  
//                }  
//  
//                FileSystemResource img = new FileSystemResource(file);  
//                messageHelper.addInline(cid, img);  
//            }  
//        }  
  
        // 添加附件  
//        if (null != attachments) {  
//            for (Iterator<Map.Entry<String, String>> it = attachments  
//                    .entrySet().iterator(); it.hasNext();) {  
//                Map.Entry<String, String> entry = it.next();  
//                String cid = entry.getKey();  
//                String filePath = entry.getValue();  
//                if (null == cid || null == filePath) {  
//                    throw new RuntimeException("请确认每个附件的ID和地址是否齐全！");  
//                }  
//  
//                File file = new File(filePath);  
//                if (!file.exists()) {  
//                    throw new RuntimeException("附件" + filePath + "不存在！");  
//                }  
//  
//                FileSystemResource fileResource = new FileSystemResource(file);  
//                messageHelper.addAttachment(cid, fileResource);  
//            }  
//        }  
  
        Properties prop = new Properties();  
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
        prop.put("mail.smtp.timeout", "25000");  
        // 添加验证  
        IpavAuthenticator auth = new IpavAuthenticator(emailUserName, emailPassword);  
  
        Session session = Session.getDefaultInstance(prop, auth);  
        senderImpl.setSession(session);  
  
        // 发送邮件  
        senderImpl.send(mailMessage);  
    }  
}

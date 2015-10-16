package com.ipav.system.thread;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.MessageUtil;

public class UserImportSuccessTask implements Runnable {

	private List<Map<String,Object>> list;
	
	private MessageUtil messageUtil;
	
	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public MessageUtil getMessageUtil() {
		return messageUtil;
	}

	public void setMessageUtil(MessageUtil messageUtil) {
		this.messageUtil = messageUtil;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(list!=null){
			for(int i=0;i<list.size();i++){
				IpavuserEntity user=(IpavuserEntity)list.get(i).get("user");
				String pwd=list.get(i).get("pwd").toString();
				if (ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())) {
					 try {
						messageUtil.sendReistOkByCompanyMessage(user, pwd);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (ContentUtil.REGIST_TYPE_EMIAL.equals(user.getRegtype())) {
					try {
						messageUtil.sendRegistOkByCompanyMail(user, pwd);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}

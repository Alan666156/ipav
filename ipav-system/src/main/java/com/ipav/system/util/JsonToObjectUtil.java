package com.ipav.system.util;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavreplyEntity;

public class JsonToObjectUtil {
	/** 从一个JSON 对象字符格式中得到一个java对象 */
	public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {
		T bean = null;
		try {
			bean = new ObjectMapper().readValue(jsonString, beanCalss);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public static Object jsonToBean2(String jsonString, Class beanCalss){
		return JSON.toJavaObject(JSON.parseObject(jsonString), beanCalss);
	}

	/** test */
	public static void main(String[] args) {
		String json = "{\"commcontent\":\"hhwhsh&*^()whhwhsh苏打粉红色东方\",\"commentid\":\"123456\"}";
		IpavcommentEntity p = JSON.toJavaObject(JSON.parseObject(""),IpavcommentEntity.class);
		System.out.println(p.getCommcontent());
	}
}

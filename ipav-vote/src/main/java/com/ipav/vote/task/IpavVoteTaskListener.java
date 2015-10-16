package com.ipav.vote.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.ipav.vote.service.IpavvoteService;
/**
 * 
 * 投票Job定时更新任务
 * 
 * ApplicationListener 应用监听器
 * 只提供了onApplicationEvent方法，我们需要在该方法实现内部判断事件类型来处理，也没有提供按顺序触发监听器的语义，所以Spring提供了另一个接口，SmartApplicationListener
 * @author: fuhongxing
 * @date:   2015年10月16日
 * @version 1.0.0
 */
@Service
public class IpavVoteTaskListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private IpavvoteService service;
	
	private static IpavVoteTask task;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent  event) {
		if(task==null){
			/*task=new IpavVoteTask();
			task.setService(service);
			new Thread(task).start();*/
        }
	}
}

package com.ipav.vote.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ipav.vote.service.IpavvoteService;
/**
 * vote job Thread
 * @author: fuhongxing
 * @date:   2015年10月16日
 * @version 1.0.0
 */
@Component
public class IpavVoteTask implements Runnable {
	
	@Autowired
	private IpavvoteService service;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * Cron表达式的格式：秒 分 时 日 月 周 年(可选)
	 *      *：匹配该域的任意值；如*用在分所在的域，表示每分钟都会触发事件。
    		?：匹配该域的任意值。
    		-：匹配一个特定的范围值；如时所在的域的值是10-12，表示10、11、12点的时候会触发事件。
    		,：匹配多个指定的值；如周所在的域的值是2,4,6，表示在周一、周三、周五就会触发事件(1表示周日，2表示周一，3表示周二，以此类推，7表示周六)。
    		/：左边是开始触发时间，右边是每隔固定时间触发一次事件，如秒所在的域的值是5/15，表示5秒、20秒、35秒、50秒的时候都触发一次事件。
    		L：last，最后的意思，如果是用在天这个域，表示月的最后一天，如果是用在周所在的域，如6L，表示某个月最后一个周五。（外国周日是星耀日，周一是月耀日，一周的开始是周日，所以1L=周日，6L=周五。）
    		W：weekday，工作日的意思。如天所在的域的值是15W，表示本月15日最近的工作日，如果15日是周六，触发器将触发上14日周五。如果15日是周日，触发器将触发16日周一。如果15日不是周六或周日，而是周一至周五的某一个，那么它就在15日当天触发事件。
    		#：用来指定每个月的第几个星期几，如6#3表示某个月的第三个星期五。
	 * 
	 */
	@Scheduled(cron="0 0/5 * * * ?")
	public void updateVoteStatus(){
		try {
			service.voteStatusTask();
		} catch (Exception e) {
			logger.error("定时任务更新投票状态异常!"+e.getMessage());
		}
	}
	
	
	@Override
	public void run() {
		while(true){
			try {
				service.voteStatusTask();
				//Thread.sleep(1000);
				
				//1小时更新1次
				Thread.sleep(60*60*1000);
			} catch (InterruptedException e) {
				logger.error("投票定时任务更新状态异常!"+e.getMessage());
			}
		}
	}

}

package com.ipav.system.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import org.apache.commons.lang.StringUtils;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月15日 下午4:54:27	
 * 上海天道启科电子有限公司
 */
public class FormatUtil {
	
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = ONE_MINUTE*ONE_MINUTE;
    private static final long ONE_DAY = 24*ONE_HOUR;
    private static final long ONE_MONTH = 30*ONE_DAY;
    private static final long ONE_YEAR = 12*ONE_MONTH;
    public static Calendar calendar = Calendar.getInstance();
    
	/***
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date,String pattern){
		if(StringUtils.isEmpty(pattern)){
			pattern = "yyyyMMdd";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	/***
	 * 格式化数字成字符串
	 * @return
	 */
	public static String formatNumber(Object  obj,String pattern){
		if(StringUtils.isEmpty(pattern)){
			pattern = "%03d";
		}
		return String.format(pattern, obj);
		//DecimalFormat df = new DecimalFormat(pattern);
		//return  df.format(obj);
	}
	
	/**
	 * 格式化日期显示
	 * @param data (yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String formatDate(String date){
		if(StringUtils.isEmpty(date))
			return "";
		SimpleDateFormat  df=new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
	    try {
	    		Calendar currDate = Calendar.getInstance();
			    currDate.setTime(df.parse(date));
			    Calendar newDate = Calendar.getInstance();
			    newDate.setTime(new Date());
			    int newYear=newDate.get(Calendar.YEAR);
			    int newMonth=newDate.get(Calendar.MONTH);
			    int newDay=newDate.get(Calendar.DATE);
			    int newHour=newDate.get(Calendar.HOUR_OF_DAY);
			    int newMinute=newDate.get(Calendar.MINUTE);
			    
			    int curYear=currDate.get(Calendar.YEAR);
			    int curMonth=currDate.get(Calendar.MONTH);
			    int curDay=currDate.get(Calendar.DATE);
			    int curHour=currDate.get(Calendar.HOUR_OF_DAY);
			    int curMinute=currDate.get(Calendar.MINUTE);
			    String Hour_Minute=formatNumber(curHour,"%02d")+":" + formatNumber(curMinute,"%02d");
				if((newYear-curYear)==0) {//一年之内
					 if((newMonth-curMonth)==0){//一月之内
						 if((newDay-curDay)==0){//一日之内,今天
							 if((newHour-curHour)==0){//一个小时之内
								 if((newMinute-curMinute)==0){
									 return "刚刚";
								 }else{
									 return (newMinute-curMinute)+"分钟前";
								 }
							 }
							 return Hour_Minute;
						 }else if((newDay-curDay)==1){//两日之内,昨天
							 return "昨天 "+Hour_Minute;
						 }else if((newDay-curDay)==2){//三日之内,前天
							 return "前天 "+Hour_Minute;
						 }					 
					 }
					 return (curMonth+1) + "月" + curDay+ "日 "+Hour_Minute;
				}else{
					 return curYear+"年"+ (curMonth+1) + "月" + curDay+ "日 "+Hour_Minute;
				}
		} catch (ParseException e) {
			return date;
		}  
	}

}

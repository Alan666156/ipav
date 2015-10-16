package com.ipav.infoshare.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ipav.system.dao.CommentReplyMapper;
import com.ipav.system.dao.MyMsgMapper;
import com.ipav.system.dao.VoteMapper;
import com.ipav.system.entity.IpavmenuEntity;
import com.ipav.system.service.IpavmenuService;
import com.ipav.system.util.ContentUtil;

@Service
public class IpavMyMsgService {
	@Autowired
	private MyMsgMapper msgMapper;
	@Autowired
	private IpavcommReplyService commReplyService;
	@Autowired
	private IpavmenuService menuService;
	@Autowired
	private VoteMapper voteMapper;
	/**
	 *  轮询查询应用消息
	 *  type[0:公告消息,1:我的工资.2:投票]
	 * @param userid
	 * @return
	 */
	public List alwaySearchMsg(String userid){
		List<Map<String,Object>> rtnList=new ArrayList<Map<String,Object>>();
		/***查询未读公告START***/
		Integer noticeConut= msgMapper.queryUnreadNoticeCount(userid);
		if(noticeConut==null)noticeConut=0;
		//n.contentText ,n.createdate,ipav_user.picpath
		Map sqlMap =new HashMap<String,Object>();
		sqlMap.put("userid", userid);
		sqlMap.put("pageNo", 0);
		sqlMap.put("pageSize", 1);
		List<Map<String, Object>> list=null;
		if(noticeConut >0){
		Map<String, Object> noticeMap = new HashMap<String, Object>();
			list= msgMapper.queryUnreadNotices(sqlMap);
			if(list.size()==1)noticeMap=list.get(0); 
		if(noticeConut==1){
			String contentText=(String)noticeMap.get("contentText");
			if(contentText.length()>32){
				contentText=contentText.substring(0,32)+"...";
			} 
			noticeMap.put("contentText",contentText); 
		}else{ 
			noticeMap.put("contentText", "您有"+noticeConut+"条公告未查看,请点击查看公告详情"); 
		}
		    IpavmenuEntity menu= menuService.getMenuById(143+"");
			noticeMap.put("picpath", ContentUtil.IMAGE_ROOT+ ContentUtil.IMAGEPATHS.get("system")+menu.getImgsrc());
			noticeMap.put("title", menu.getMenuname()); 
			noticeMap.put("createdate",
					commReplyService.DateToString((String)noticeMap.get("createdate")));
			noticeMap.put("size", noticeConut);
			noticeMap.put("type", 0);
			 
			rtnList.add(noticeMap);
		}
	    /***查询未读公告END***/
		
		/***查询未读工资START***/
		Integer wageConut= msgMapper.quertUnreadWageCount(userid);
		if(wageConut!=null){
		if(wageConut>0){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			list=null;
			list= msgMapper.quertUnreadWages(sqlMap);
			Map<String, Object> wageMap = new HashMap<String, Object>();
			if(list.size()==1)wageMap=list.get(0); 
			Date time = null;
			if(wageConut==1){
				try {
					time = sdf.parse((String)wageMap.get("contentText"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				Date time=(Date)wageMap.get("contentText");
				String contentText="";
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(time);
			if(time!=null){
				contentText=calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日";
			}else{
				contentText="最近";
			}
			wageMap.put("contentText",contentText+"发工资了，请点击查看工资详情！");
			}else{
			wageMap.put("contentText","您有"+wageConut+"次工资未查看，请点击查看工资详情！");	
			}
			wageMap.put("createdate",
					commReplyService.DateToString((String)wageMap.get("createdate")));
			wageMap.put("type", 1); 
			IpavmenuEntity menu= menuService.getMenuById(140+"");
			wageMap.put("picpath", ContentUtil.IMAGE_ROOT+ ContentUtil.IMAGEPATHS.get("system")+menu.getImgsrc());
			wageMap.put("title", menu.getMenuname()); 
			wageMap.put("size", wageConut);
			rtnList.add(wageMap);
		}
		}
		/***查询未读工资END***/
		
		/******投票start********/
		List<Map<String,Object>> votelist = voteMapper.getUnCompletedVote(sqlMap);
		Map<String, Object> voteMap = new HashMap<String, Object>();
	    if(votelist.size()>0){
	    	     voteMap.put("type","2");
	    	     voteMap.put("size",votelist.size());
	    	     IpavmenuEntity menu= menuService.getMenuById(142+"");
	    	     voteMap.put("picpath", ContentUtil.IMAGE_ROOT+ ContentUtil.IMAGEPATHS.get("system")+menu.getImgsrc());
	    	     voteMap.put("title",menu.getMenuname());
	    	     voteMap.put("createdate", commReplyService.DateToString((String)votelist.get(0).get("start_time")));
	    	if(votelist.size()==1){
	    		voteMap.put("contentText",votelist.get(0).get("title")); 
	    	}else{
	    		voteMap.put("contentText","您有"+votelist.size()+"份投票未投，请点击查看工资详情！"); 
	    	}
	    	rtnList.add(voteMap);
	    }
		/******投票 **end********/
		
		
		return rtnList;			
	}
	
	/** 
	 *查询未读信息 
	 */
	public List searchMsgs(String userid,Integer type){
	 if(type==0){
		 return searchNotices(userid);
	 }else if(type==1){
			return searchWages( userid);
	 }else if(type==2){
			return searchVotes( userid);
	 }else{
	 return null;
	 }
	}
	/**
	 * 查询得到未读公告列表
	 * @param userid
	 * @return
	 */
	public List searchNotices(String userid){
		List<Map<String,Object>> rtnList=new ArrayList<Map<String,Object>>();
		Map sqlMap =new HashMap<String,Object>();
		sqlMap.put("userid", userid);
		rtnList=msgMapper.queryUnreadNotices(sqlMap);
		for(int i=0;i<rtnList.size();i++){
			Map map=rtnList.get(i);
			String contentText=map.get("contentText").toString();
			if(contentText.length()>32){
				contentText=contentText.substring(0,32)+"...";
			}
			map.put("contentText",contentText);
			/*map.put("picpath","/pages/images/platform/tool/y4.png");*/
			IpavmenuEntity menu= menuService.getMenuById(143+"");
			map.put("picpath", ContentUtil.IMAGE_ROOT+ ContentUtil.IMAGEPATHS.get("system")+menu.getImgsrc());
			map.put("title", menu.getMenuname()); 
			map.put("createdate",
					commReplyService.DateToString((String)map.get("createdate")));
			map.put("type", 0);
		}
		return rtnList;
	}

	
	/**
	 * 查询未读工资列表
	 */
	public List searchWages(String userid){
		List<Map<String,Object>> rtnList=new ArrayList<Map<String,Object>>();
		Map sqlMap =new HashMap<String,Object>();
		sqlMap.put("userid", userid);
		rtnList=msgMapper.quertUnreadWages(sqlMap);
		for(int i=0;i<rtnList.size();i++){
			Map map=rtnList.get(i);
			String time=(String)map.get("contentText");
			String contentText="";
			Calendar calendar = Calendar.getInstance();
			 SimpleDateFormat  df=new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			 Date d = null;
			try {
				d = df.parse(time);
			} catch (ParseException e) {
				 
				e.printStackTrace();
			}
			calendar.setTime(d);
				if(time!=null){
					contentText=calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日";
				}else{
					contentText="最近";
				}
			map.put("contentText",contentText+"发工资了，请点击查看工资详情！");
			map.put("createdate",
					commReplyService.DateToString((String)map.get("createdate")));
			map.put("type", 1);
			/*map.put("picpath", "/pages/images/platform/tool/y1.png");
			map.put("title","我的工资");*/
			IpavmenuEntity menu= menuService.getMenuById(140+"");
			map.put("picpath", ContentUtil.IMAGE_ROOT+ ContentUtil.IMAGEPATHS.get("system")+menu.getImgsrc());
			map.put("title", menu.getMenuname()); 
		}
		return rtnList;
	}
	/**
	 * 查询未读工资列表
	 */
	public List searchVotes(String userid){
		List<Map<String,Object>> rtnList=new ArrayList<Map<String,Object>>();
		Map sqlMap =new HashMap<String,Object>();
		sqlMap.put("userid", userid);
		List<Map<String,Object>> votelist = voteMapper.getUnCompletedVote(sqlMap); 
		for(int i=0;i<votelist.size();i++){
			Map map=votelist.get(i);
			String contentText=map.get("title").toString();
			if(contentText.length()>32){
				contentText=contentText.substring(0,32)+"...";
			}
			map.put("contentText",contentText); 
			IpavmenuEntity menu= menuService.getMenuById(142+"");
			map.put("picpath", ContentUtil.IMAGE_ROOT+ ContentUtil.IMAGEPATHS.get("system")+menu.getImgsrc());
			map.put("title", menu.getMenuname()); 
			map.put("createdate",
					commReplyService.DateToString((String)map.get("start_time")));
			map.put("type", 2); 
			rtnList.add(map);
		}
		return rtnList;
	}
}

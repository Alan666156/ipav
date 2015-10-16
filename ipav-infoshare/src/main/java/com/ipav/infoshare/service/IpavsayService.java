package com.ipav.infoshare.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ipav.system.dao.ActionFileMapper;
import com.ipav.system.dao.SayMapper;
import com.ipav.system.dao.UserMapper;
import com.ipav.system.entity.IpavActionFileEntity;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavreplyEntity;
import com.ipav.system.entity.IpavsayEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavorgService;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;
import com.ipav.system.util.ImageUtil;
import com.ipav.system.util.MessageUtil;

/**
 * creater Jerry All right reserved. Created on 2014年11月15日 下午4:24:03
 * 上海天道启科电子有限公司
 */
@Service
public class IpavsayService {
	@Autowired
	private SayMapper sayMapper;

	@Autowired
	private ActionFileMapper sayImageMapper;

	@Autowired
	private IpavcommReplyService commReplyService;

	@Autowired
	private IpavuserService userService;
 
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private IpavorgService orgService;
	
	@Autowired
	private MessageUtil messageUtil;
	/***
	 * WEB查询用户同事说说列表
	 * 
	 * @param companyid
	 */
	public List getSayList(String userid,Integer pageNo,Integer pageSize) {
		
		return SayListToMap(publicSayList(userid,pageNo,pageSize,0,0), userid);
	}
	
	/***
	 * WEB我发布的说说
	 *  
	 */
	public List myReleaseSays(String userid,Integer pageNo,Integer pageSize) {
		if(pageNo==null)pageNo=1;
		if(pageSize==null)pageSize=10;
		List<IpavsayEntity> saylist = new ArrayList<IpavsayEntity>();
		IpavuserEntity user = userService.queryUserId(userid);
		Map<String,Object> sqlMap=new HashMap<String, Object>();	 
		sqlMap.put("companyid", user.getCompanyid());
		sqlMap.put("orgid", user.getOrgid());
		sqlMap.put("userid", userid);
		sqlMap.put("pageNo",(pageNo-1)*pageSize);
		sqlMap.put("pageSize", pageSize);
		saylist = sayMapper.querymyReleaseSays(sqlMap); 
		for (IpavsayEntity say : saylist) { 
			IpavuserEntity userr = userService.queryUserId(say.getSayuserid());
			if(userr!=null){
				say.setSayuserimg(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+userr.getPicpath());
			}
			Map map =new HashMap<String,Object>();
			map.put("actiontype",1);
			map.put("actionid", say.getSayid());
			List<String> sayimages = sayImageMapper.queryActionfileUrlList(map);
			List<String> newsayimages=new ArrayList<String>();
			for(String simg:sayimages){
				newsayimages.add(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("say")+simg);
			}
			say.setImages(newsayimages);
		} 
		return SayListToMap(saylist,userid);
	}
	
	/**
	 * 公用查询同事说说
	 */
	public List publicSayList(String userid,Integer pageNo,Integer pageSize,int tip,long sid){
		if(pageNo==null)pageNo=1;
		if(pageSize==null)pageSize=10;
		List<IpavsayEntity> saylist = new ArrayList<IpavsayEntity>();
		IpavuserEntity user = userService.queryUserId(userid);
		Map<String,Object> sqlMap=new HashMap<String, Object>();	
		if(tip==0){
		sqlMap.put("companyid", user.getCompanyid());
		sqlMap.put("orgid", user.getOrgid());
		sqlMap.put("userid", userid);
		sqlMap.put("pageNo",(pageNo-1)*pageSize);
		sqlMap.put("pageSize", pageSize);
		saylist = sayMapper.querySayList(sqlMap);
		}else{
		sqlMap.put("companyid", user.getCompanyid());
		sqlMap.put("orgid", user.getOrgid());
		sqlMap.put("userid", userid);
		sqlMap.put("sid",sid);
		sqlMap.put("pageSize", pageSize);
		saylist = sayMapper.appQuerySayList(sqlMap);
		}
		for (IpavsayEntity say : saylist) { 
			IpavuserEntity userr = userService.queryUserId(say.getSayuserid());
			if(userr!=null){
				say.setSayuserimg(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+userr.getPicpath());
			}
			Map map =new HashMap<String,Object>();
			map.put("actiontype",1);
			map.put("actionid", say.getSayid());
			List<String> sayimages = sayImageMapper.queryActionfileUrlList(map);
			List<String> newsayimages=new ArrayList<String>();
			for(String simg:sayimages){
				newsayimages.add(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("say")+simg);
			}
			say.setImages(newsayimages);
		} 
		return saylist;
	}
   /**
    * 将说说集合转换成前台需要的map
    */
	private List SayListToMap(List<IpavsayEntity> saylist,String userid){
		List<Map> rtnList=new ArrayList<Map>();
		for (IpavsayEntity say : saylist) {
			say.setSaydate(IpavcommReplyService.DateToString(say.getSaydate()));
			Map map=new HashMap<String,Object>();
			if(say.getSayuserid().equals(userid)){
			map.put("isMyTopic","Y");
			}else{
			map.put("isMyTopic","N");
			} 
			Map filemap=new HashMap<String, Object>();
			filemap.put("actionid",say.getSayid());
			filemap.put("actiontype",5);
	       IpavActionFileEntity file=sayImageMapper.queryActionfileByAction(filemap);
	       if(file!=null){
	    	   Map fmap=new HashMap<String, Object>();
	    	   fmap.put("name", file.getFilename());
	    	   fmap.put("path", file.getFilepath());
	    	   map.put("fmap",fmap);
	       }
	       Map commentMap= queryCommentBySid(say.getSayid(),10);
	        map.put("commentMap",commentMap);
			map.put("say",say);
			map.put("praisSize",commReplyService.queryPraiseOrCommentCount(1, say.getSayid(), 0));
			map.put("isPrais",commReplyService.queryIsPraiseOrComment(1, say.getSayid(), 0,userid,0));
			map.put("CommentSize",commReplyService.queryPraiseOrCommentCount(1, say.getSayid(), 1));
			List<Map> userPraises =searchPraiseUser(say.getSayid(),1,null,null,1);
			map.put("userPraises",userPraises);
			rtnList.add(map);
		}
		return rtnList;
	}
	 
	/***
	 * 查询所有与当前用户发表的说说相关的动态列表
	 * @param userid
	 */
	public List getUserSayList(String userid,Integer pageNo,Integer pageSize) {
		List<Map> rtnList = new ArrayList<Map>();
		if(pageNo==null)pageNo=1;
		if(pageSize==null)pageSize=10;
		List<Map<String, Object>> saylist = new ArrayList<Map<String, Object>>();
		Map sqlmap =new HashMap<String, Object>();
		sqlmap.put("userid", userid);
		sqlmap.put("pageNo", (pageNo-1)*pageSize);
		sqlmap.put("pageSize", pageSize);
		saylist = sayMapper.queryUserSayList(sqlmap);
		for(int s=0;s<saylist.size();s++){
			 Map say=saylist.get(s); 
			 say.put("sayuserimg",ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+say.get("sayuserimg"));
			 Long sayid=(Long)say.get("sayid");
			
			 pageSize=5;
			 Long commentid=(Long)say.get("commentid"); 
			 int replySize=commReplyService.queryReplyCount(commentid);
			 if(replySize>pageSize){
				pageNo=replySize-pageSize;
			 }else{
				 pageNo=0; 
			 }
			 Map map=new HashMap<String, Object>();
			 Map sqlmap1 =new HashMap<String,Object>();
			 sqlmap1.put("actiontype",1);
			 sqlmap1.put("actionid",sayid);
			List<String> sayimages = sayImageMapper.queryActionfileUrlList(sqlmap1);
			List<String> newsayimages=new ArrayList<String>();
			for(String simg:sayimages){
				newsayimages.add(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("say")+simg);
			}
			say.put("images",newsayimages);
		    IpavcommentEntity  comment
		           =commReplyService.queryCommentByCommentid(commentid,pageNo, pageSize);
			List<IpavcommentEntity > comments=new ArrayList<IpavcommentEntity>();
				comments.add(comment);	
		    say.put("comments", comments);
		    if(comment.getReplys()!=null && comment.getReplys().size()>0){
		    	IpavcommentEntity  comment1
		    			=commReplyService.queryCommentByCommentid(commentid,null, null);
		    	//String date=comment1.getReplys().get(comment1.getReplys().size()-1).getReplydate();
		    	//say.put("saydate",(IpavcommReplyService.DateToString(date)));
		    	for(int i=comment1.getReplys().size()-1;i>=0;i--){
				if(comment1.getReplys().get(i).getBereplyid().equals(userid) &&
					!comment1.getReplys().get(i).getReplyuserid().equals(userid) ){
				map.put("uname",comment1.getReplys().get(i).getReplyusername());
				map.put("img",comment1.getReplys().get(i).getReplyuserimg());
				map.put("uid",comment1.getReplys().get(i).getReplyuserid());
				break ;
				} 
				}
				map.put("type","回复了");
			
			}else if(comment.getCommtype()==0){
				//map.put("uname",comment.getCommusername());
				map.put("type","赞了");
			}else{
				//map.put("uname",comment.getCommusername());
				map.put("type","评论了");
			}
			
		    if(!map.containsKey("img") || comment.getCommtype()==0){
				map.put("uname",comment.getCommusername());
				map.put("img",comment.getCommuserimg());
				map.put("uid",comment.getCommuserid());
			}
		    say.put("saydate",(IpavcommReplyService.DateToString((String)say.get("saydate"))));
	   if(say.get("sayuserid").equals(userid)){
		map.put("isMyTopic","Y");
		}else{
		map.put("isMyTopic","N");
		} 
		Map filemap=new HashMap<String, Object>();
		filemap.put("actionid",say.get("sayid"));
		filemap.put("actiontype",5);
       IpavActionFileEntity file=sayImageMapper.queryActionfileByAction(filemap);
       if(file!=null){
    	   Map fmap=new HashMap<String, Object>();
    	   fmap.put("name", file.getFilename());
    	   fmap.put("path",file.getFilepath());
    	   map.put("fmap",fmap);
       }
		map.put("say",say);
		map.put("praisSize",commReplyService.queryPraiseOrCommentCount(1, sayid, 0));
		map.put("isPrais",commReplyService.queryIsPraiseOrComment(1, sayid, 0,userid,0));
		map.put("CommentSize",commReplyService.queryPraiseOrCommentCount(1, sayid, 1));
		Integer surplusSize=null;
		if((pageNo==null || pageNo!=0)&&comment.getReplys()!=null ){
			surplusSize=replySize-comment.getReplys().size();
		}
		map.put("surplusSize",surplusSize);
		List<Map> userPraises =searchPraiseUser(sayid,1,null,null,1);
		map.put("userPraises",userPraises);
		rtnList.add(map);
 
	}
	return rtnList;
	}

	/***
	 * 添加说说
	 * 
	 * @param say
	 * @param filedata
	 */
	public void addSay(IpavsayEntity say, MultipartFile[] filedatas,
			String[] sayusers,Integer fileSize) throws Exception {
		String currentdate = FormatUtil.formatDate(new Date(),
				"yyyy-MM-dd HH:mm:ss");
		say.setSaydate(currentdate);
		sayMapper.insertSay(say);	
		Long sayid = say.getSayid();
		if(filedatas!=null && filedatas.length>0){
		int i =-1;
		for (MultipartFile filedata : filedatas) {
			i++;
			if (filedata != null && !filedata.isEmpty()) {
				String imagepath="";
				IpavActionFileEntity sayimage = new IpavActionFileEntity();
				if(fileSize!=0 && filedatas.length-i==fileSize){
					sayimage.setActiontype(5);
					sayimage.setFilename(filedata.getOriginalFilename());
					imagepath =  ImageUtil.saveImage(filedata, ContentUtil.IMAGEPATHS.get("files"));
				}
				else{
					imagepath =  ImageUtil.saveImage(filedata, ContentUtil.IMAGEPATHS.get("say"));
					sayimage.setActiontype(1);
					sayimage.setFilename(filedata.getOriginalFilename());
				}
				sayimage.setFilepath(imagepath);
				sayimage.setActionid(sayid.toString());
				sayimage.setCreatedate(currentdate);
				sayImageMapper.insertActionfile(sayimage);
			}
		}
		}
		// 按指定发表说说,添加指定人
		if (say.getPermission() == 2) {
			if(sayusers!=null){
				for (String userid : sayusers) {
					Map parm = new HashMap();
					parm.put("sayid", sayid);
					parm.put("userid", userid);
					sayMapper.insertSayUsers(parm);
				}
			}
		}

	}

	/**
	 * 删除说说
	 */
	public String delSay(Long sid,String userid){	
		IpavsayEntity say=sayMapper.querySayById(sid);
		if(userid.equals(say.getSayuserid())){
		int count=sayMapper.deleteSay(sid);
		if(count==1){
			return "success";
		}else{
			return "error"; 		
		}
		}else{
			return "您没有此权限";
		}
	}
	
	/**
	 * 根据说说id得到它的评论
	 */
	public Map queryCommentBySid(Long sid,Integer pageSize){
		Map<String,Object> rtnMap=new HashMap<String, Object>();
		Integer pageNo=null;
		int size= commReplyService.queryPraiseOrCommentCount(1,sid, 1);
		if(pageSize==null){
			pageSize=10;
		}
		if(pageSize==-1){
		   pageSize=null;
		}else{      
			if(size>pageSize)
			pageNo=size-pageSize+1;
			else pageNo=0;
		}
		List<IpavcommentEntity> list=
				commReplyService.queryCommentById(1, sid,1,pageNo,pageSize);
		List<Map> mapList=new ArrayList<Map>();
		for(IpavcommentEntity comment :list){  
			Map map =new HashMap<String , Object>();
			map.put("commuserimg", comment.getCommuserimg());
			map.put("commcontent", comment.getCommcontent());
			map.put("commusername", comment.getCommusername());
			map.put("commuserid", comment.getCommuserid());
			map.put("commentid", comment.getCommentid());
			map.put("replys", comment.getReplys());
			mapList.add(map); 
		}
		rtnMap.put("commList",mapList);
		rtnMap.put("sid",sid);
		Integer surplusSize=null;
		if((pageNo==null || pageNo!=0)&&list!=null ){
			surplusSize=size-list.size();
		}
		rtnMap.put("surplusSize",surplusSize);
		rtnMap.put("size",size);
		return rtnMap;
	}
	/***
	 * 添加说说评论
	 * 
	 * @param comment
	 */
	public void addSayComment(IpavcommentEntity comment) {
		commReplyService.addComment(comment);
	}
   /**
    *说说点赞/取消赞 
    */
	public Map praiseOrCancel(IpavcommentEntity comment){
		addSayComment(comment);
		Map rtn =new HashMap<String, Object>();
		List<Map> listMap=searchPraiseUser(comment.getActionid(),comment.getActiontype(),null,null,1);
		rtn.put("userList",listMap);
		rtn.put("size",commReplyService.queryPraiseOrCommentCount(1,comment.getActionid(), 0));
		return rtn;
	}
	
	
	/**
	 * 得到所有说说赞的人的信息 
	 * @param actionid
	 * @param actiontype
	 * @return
	 */
	public List searchPraiseUser(Long actionid,Integer actiontype,Integer pageNo,Integer pageSize,Integer tip){
		List<Map> listMap=new ArrayList<Map>();
		//根据说说id得到所有赞过的人id
		List<IpavuserEntity> list =publicSearchPraiseUser(actionid, actiontype,pageNo,pageSize,tip);
		for(IpavuserEntity user: list){ 
			if(user!=null){
				Map map=new HashMap<String,Object>();
				map.put("userid",user.getUserid());
				map.put("username",user.getUsername());
				map.put("userImageUrl",ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+user.getPicpath());
				listMap.add(map);
			}
		}
		return listMap;
	}
	/**
	 * 得到赞的用户信息
	 */
	public List publicSearchPraiseUser(Long actionid,Integer actiontype,Integer pageNo,Integer pageSize,Integer tip){
		List<String> list=
				commReplyService.queryUserListByActionId(actionid, actiontype,pageNo,pageSize,tip);
		List<IpavuserEntity> rtnList=new ArrayList<IpavuserEntity>();
			for(String i: list){
				Map map =new HashMap<String, Object>();
				map.put("userid",i);
				map.put("delflg","delflg");
				IpavuserEntity user =  userMapper.getUserByUniKey(map);
				rtnList.add(user);
			}
		return rtnList;
	}
	
	/***
	 * 添加说说回复
	 * 
	 * @param comment
	 */
	public void addSayReply(IpavreplyEntity reply) {
		commReplyService.addReply(reply);
	}
	/***
	 * 查询回复根据评论id
	 * @param comment
	 */
	public Map queryCommentByCommentid(Integer commentid,Integer pageSize) {
		Integer pageNo=0;
		if(pageSize==null){
			pageSize=5;
		}
		int replySize=commReplyService.queryReplyCount(commentid.longValue());;
	    if(pageSize==-1){
			pageNo=null;
			pageSize=null;
		}else if(replySize>pageSize){
				pageNo=replySize-pageSize;
			}
		Map map=new HashMap<String,Object>();
		IpavcommentEntity comment=
				commReplyService.queryCommentByCommentid(commentid.longValue(),pageNo,pageSize);
		Integer surplusSize=null;
		if((pageNo==null ||pageNo!=0)&& comment.getReplys()!=null){
			 surplusSize= replySize-comment.getReplys().size();
		}
		map.put("surplusSize",surplusSize);
		List<IpavcommentEntity> comments=new  ArrayList<IpavcommentEntity>();
		comments.add(comment);
		map.put("comment",comments);
		return map;
	}
 

	/**
	 * 根据图片名从服务器端获取单张图片
	 * @param imagename
	 */
	public void getSayImageByName(String imagename, HttpServletResponse response){
		IpavActionFileEntity imageTemp = sayImageMapper.queryActionfileByPath(imagename);
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream; charset=utf-8");  
			response.setHeader("pragma", "no-cache");	
		    response.addHeader("Content-Disposition", "attachment;filename="+new String(imageTemp.getFilename().getBytes("utf-8"), "ISO-8859-1")); 
		   
	        URL url = new URL(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("files")+imageTemp.getFilepath());
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();		   
	        OutputStream out = response.getOutputStream();
	    //写文件  
	    int b;  
	    while((b=in.read())!= -1)  
	    {  
	        out.write(b);  
	    }  
	      
	     in.close();  
	     out.close();  
		} catch (IOException e) {
		 
			e.printStackTrace();
		}  
	}
	/**
	 * 根据组织id查询出组织id下面所有用户信息
	 */
	 public List getUserListForOrgid(Integer orgid,String[] orgnoids,Long companyid){
		return  userService.getUserListForOrgid(orgid,orgnoids,companyid);
	 }

	 /**
	  * 模糊查询用户
	  */
	 public List getUserForName(String username,Integer companyid){
		 if(username.equals("")){
			 return null;
		 }
		 Map map=new HashMap<String ,Object>();
		 map.put("username", username);
		 map.put("companyid", companyid);
		 return  userService.getUserForName(map);
	 }
	 /**
	  * 邀请同事发送邮件或者短信
	 * @throws Exception 
	  */
	 public Map invitationFriend(String username,String name,String mailOrphone) throws Exception{
		 Map map =new HashMap<String, String>();
		 IpavuserEntity user= new IpavuserEntity();
		 user.setUsername(name);
		 Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
		 Matcher m = p.matcher(mailOrphone);
		 boolean b = m.matches();
		  if(b==true){
			  user.setEmail(mailOrphone);
			  user.setRegtype(ContentUtil.REGIST_TYPE_EMIAL);
			  Map usersqlmap=new HashMap<String,Object>();
			  usersqlmap.put("email",mailOrphone);
			  usersqlmap.put("regtype","E");
			  IpavuserEntity  sqluser =userMapper.getUserByUniKey(usersqlmap);
			  if(sqluser!=null){
			    	 map.put("msg", "您邀请的好友已经存在快捷管家系统中"); 
			    	 return map;
			     } 
			  Map remap =new HashMap<String,Object>();
			  remap.put("user", user);
			  remap.put("iname", username);
			  userService.regist(remap);
			  map.put("msg", "success");
			  
		  }else{
		 p = Pattern.compile("^((170)|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		 m = p.matcher(mailOrphone);
		 b = m.matches();
		 if(b==true){
		 Map usersqlmap=new HashMap<String,Object>();
		 usersqlmap.put("mobile",mailOrphone);
	     usersqlmap.put("regtype","M");
	     IpavuserEntity  sqluser =userMapper.getUserByUniKey(usersqlmap);
	     if(sqluser!=null){
	    	 map.put("msg", "您邀请的好友已经存在贵公司"); 
	    	 return map;
	     }
		 user.setMobile(mailOrphone);
		 user.setRegtype(ContentUtil.REGIST_TYPE_MOBILE);
		 Map remap =new HashMap<String,Object>();
		  remap.put("user", user);
		  remap.put("iname", username);
		  userService.regist(remap);
		  map.put("msg", "success");
		 }else{
			 map.put("msg", "您的邮箱或者号码有误."); 
		 }
	   }
		  return map;
	 }
	 /**
	  * 邀请同事发送邮件或者短信（变更）
	  * @throws Exception 
	  */
	 public Map invitationColleague(IpavuserEntity ctuser,String name,String mailOrphone){
		 Map map =new HashMap<String, String>();
		 IpavuserEntity user= new IpavuserEntity();
		 user.setUsername(name);
		 user.setSex(1+"");
		 user.setCompanyid(ctuser.getCompanyid());
		 
		 Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
		 Matcher m = p.matcher(mailOrphone);
		 boolean b = m.matches();
		 if(b==true){
			 user.setEmail(mailOrphone);
			 user.setRegtype(ContentUtil.REGIST_TYPE_EMIAL);
			 Map usersqlmap=new HashMap<String,Object>();
			 usersqlmap.put("email",mailOrphone);
			 usersqlmap.put("regtype","E");
			 IpavuserEntity  sqluser =userMapper.getUserByUniKey(usersqlmap);
			 if(sqluser!=null){
				 map.put("msg", "您邀请的好友已经存在快捷管家系统中"); 
				 return map;
			 } 
			 Map remap =new HashMap<String,Object>();
			 remap.put("user", user);
			 remap.put("iname", ctuser.getUsername());
			 try {
				userService.addUser(remap);
			} catch (Exception e) {
				map.put("msg", e.getMessage());
				String [] users={user.getUserid()};
				userService.delUser(users);
				return map;
			}
			 map.put("msg", "success");
			 
		 }else{
			 p = Pattern.compile("^((170)|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			 m = p.matcher(mailOrphone);
			 b = m.matches();
			 if(b==true){
				 Map usersqlmap=new HashMap<String,Object>();
				 usersqlmap.put("mobile",mailOrphone);
				 usersqlmap.put("regtype","M");
				 IpavuserEntity  sqluser =userMapper.getUserByUniKey(usersqlmap);
				 if(sqluser!=null){
					 map.put("msg", "您邀请的好友已经存在贵公司"); 
					 return map;
				 }
				 user.setMobile(mailOrphone);
				 user.setRegtype(ContentUtil.REGIST_TYPE_MOBILE);
				 Map remap =new HashMap<String,Object>();
				 remap.put("user", user);
				 remap.put("iname", ctuser.getUsername());
				 try {
					userService.addUser(remap);
				} catch (Exception e) {
					map.put("msg", e.getMessage());
					String [] users={user.getUserid()};
					userService.delUser(users);
					return map;
				}
				 map.put("msg", "success");
			 }else{
				 map.put("msg", "您的邮箱或者号码有误."); 
			 }
		 }
		 return map;
	 }
	 
 
	/**
	 * 获取组织结构树状结构
	 * @param companyId
	 * @return
	 */
	public List<JSONObject> getAllLevelInfos(long companyId){
		 return orgService.getOrgTrees(""+companyId);
	}
	
 
/**
 * 得到赞的用户信息
 */
public List SearchPraiseUserMsg(Long actionid,Integer actiontype,Integer pageNo,Integer pageSize){
	List<IpavuserEntity> list =searchPraiseUser(actionid, actiontype,pageNo,pageSize,0);
	return list;
}
/**
 * 好友分享
 * @param ctuser
 * @param ly
 * @param ep
 * @return
 */
public JSONObject recommendSharing(IpavuserEntity ctuser,String ly,String ep){
	JSONObject rtn =new JSONObject();
	 Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
	 Matcher m = p.matcher(ep);
	 boolean b = m.matches();
	 if(b){
		try { 
			messageUtil.sendrecommendMail(ctuser.getUsername(),ep,ly);
		} catch (Exception e) {
			 rtn.put("msg",e.getMessage()); 
			 return rtn;
		}
		 rtn.put("msg","分享成功"); 
	 }else{ 
	   p = Pattern.compile("^((170)|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	   m = p.matcher(ep);
	   b = m.matches();
	 if(b==true){
		try { 
			messageUtil.sendrecommendPhone(ctuser.getUsername(),ep,ly);
		} catch (Exception e) {
			rtn.put("msg",e.getMessage()); 
			 return rtn;
		}
		 rtn.put("msg","分享成功"); 
	 }else{
		 rtn.put("msg","邮箱或手机号码有误"); 
	 }
	 }
	return rtn;
}
 
}

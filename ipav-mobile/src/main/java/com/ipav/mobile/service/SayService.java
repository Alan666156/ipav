package com.ipav.mobile.service;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ipav.infoshare.service.IpavcommReplyService;
import com.ipav.infoshare.service.IpavsayService;
import com.ipav.mobile.param.RequestParameter;
import com.ipav.mobile.param.ResponseParameter;
import com.ipav.system.dao.ActionFileMapper;
import com.ipav.system.dao.SayMapper;
import com.ipav.system.dao.UserMapper;
import com.ipav.system.entity.IpavActionFileEntity;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavreplyEntity;
import com.ipav.system.entity.IpavsayEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;
import com.ipav.system.util.ImageUtil;
import com.ipav.system.util.ftp.FTPUtil;

@Service
public class SayService {
	
	@Autowired
	private IpavsayService sayService;
	@Autowired
	private IpavcommReplyService commReplyService;
	@Autowired
	private SayMapper sayMapper;
	@Autowired
	private ActionFileMapper sayImageMapper;
	@Autowired
	private UserMapper userMapper;

	/**
	 * 获取同事圈所有同事发表的说说集合
	 * @param param
	 * @param service
	 * @return
	 */
	public ResponseParameter getSayList(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		String userid=obj.getString("userId");
		//Integer pageNo=obj.getInteger("pageNo");
		Integer pageSize=obj.getInteger("pageSize");
		long sid=0;
		if(obj.containsKey("sid") && !obj.get("sid").equals("")){
			 sid=obj.getLong("sid");
		}
		if(pageSize==null)pageSize=10;
		//if(sid==null)sid=(long) 1;
	 
		List<IpavsayEntity> saylist = sayService.publicSayList(userid,0,pageSize,1,sid);
		List<JSONObject> saylistMap=new ArrayList<JSONObject>();
		for(IpavsayEntity say:saylist){ 
			say.setSaydate(DateToString(say.getSaydate()));
			JSONObject sayMap= SayToMap(say,userid);
			   saylistMap.add(sayMap);
		  }
			JSONObject body=new JSONObject();
			body.put("saylist", saylistMap);
			System.out.println(saylistMap);
			response.setBody(body);
			return response;
	}
	
	/**
	 * 查询单个说说详情
	 * @param param
	 * @param service
	 * @return
	 */
	/*public ResponseParameter  getSay(RequestParameter param, int service) {	
		ResponseParameter response=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		String userid=obj.getString("userId");
		long sayid=obj.getLong("sayid");
		IpavsayEntity say = sayMapper.querySayById(sayid);
		JSONObject sayMap =SayToMap(say,userid);
		JSONObject body=new JSONObject(); 
		body.put("say", sayMap);
		response.setBody(body);
		return response;
	}*/
	
	/**
	 * 说说转换
	 * @param say
	 * @param userid
	 * @return
	 */
	private JSONObject SayToMap(IpavsayEntity say,String userid){
		JSONObject sayMap=new JSONObject();
		sayMap.put("sayid",say.getSayid());
		sayMap.put("sayuserid",say.getSayuserid());
		sayMap.put("sayuserimg",say.getSayuserimg());
		sayMap.put("sayusername",say.getSayusername());
		sayMap.put("saycontent",say.getSaycontent());
		sayMap.put("saydate",DateToString(say.getSaydate()));
		Map map =new HashMap<String,Object>();
		map.put("actiontype",1);
		map.put("actionid", say.getSayid());
		map.put("actionid", say.getSayid());
		//List<String> sayimages = sayImageMapper.queryActionfileUrlList(map); 
		
		sayMap.put("sayimages",say.getImages());
		List<IpavcommentEntity> comments= 
				commReplyService.queryCommentById(1, say.getSayid(), 1, null, null);
	    List<JSONObject> commentsMap=new ArrayList<JSONObject>();
		if(comments!=null && comments.size()>0){
	    	for(IpavcommentEntity  comment :comments){
	    		JSONObject commentMap=CommentToMap(comment);
	    		commentsMap.add(commentMap);
	    	}
		}
		sayMap.put("comments",commentsMap);
			if(say.getSayuserid().equals(userid)){
			sayMap.put("isMy","Y");
			}else{
			sayMap.put("isMy","N");
			}  
		   sayMap.put("praisSize",commReplyService.queryPraiseOrCommentCount(1, say.getSayid(), 0));
		   sayMap.put("isPrais",commReplyService.queryIsPraiseOrComment(1, say.getSayid(), 0,userid,0));
		   sayMap.put("CommentSize",commReplyService.queryPraiseOrCommentCount(1, say.getSayid(), 1));
			List<IpavuserEntity> userList =sayService.publicSearchPraiseUser(say.getSayid(),1,null,null,1);
		     List<Map> userPraises=new ArrayList<Map>();
			for(IpavuserEntity user: userList){ 
				if(user!=null){
					Map userMap=new HashMap<String, Object>();
					userMap.put("userid",user.getUserid());
					userMap.put("username",user.getUsername());
					userPraises.add(userMap);
				}
			}
			sayMap.put("userPraises",userPraises);
		   return sayMap;
	}
	
	/**
	 * 转换评论
	 */
	public JSONObject  CommentToMap(IpavcommentEntity  comment){
		JSONObject commentMap=new JSONObject();
    	commentMap.put("commentid", comment.getCommentid());
    	commentMap.put("actionid", comment.getActionid());
    	commentMap.put("commuserid", comment.getCommuserid());
    	commentMap.put("commusername", comment.getCommusername());
    	commentMap.put("commcontent", comment.getCommcontent());
    	 List<JSONObject> replysMap=new ArrayList<JSONObject>();
    	 if(comment.getReplys()!=null &&comment.getReplys().size()>0 ){
    		 for(IpavreplyEntity reply : comment.getReplys()){
    		 JSONObject replyMap=new JSONObject(); 
    		  replyMap.put("commentid",reply.getCommentid()); 
    		  replyMap.put("replyuserid",reply.getReplyuserid());
    		  replyMap.put("replyusername",reply.getReplyusername());
    		   replyMap.put("bereplyid",reply.getBereplyid());
    		  replyMap.put("bereplyname",reply.getBereplyname());
    		  replyMap.put("replycontent",reply.getReplycontent());
    		  replysMap.add(replyMap); 
    		 }
    	 }
    	 commentMap.put("replys",replysMap);
    	 return commentMap;
	}
	
	/**
	 * 添加说说
	 * @param param
	 * @param service
	 * @return
	 * @throws Exception 
	 */
	
//	public ResponseParameter addSay(RequestParameter param, int service) {
//		ResponseParameter response=new ResponseParameter(service);
//		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
//		JSONObject body=new JSONObject(); 
//		IpavsayEntity say=new IpavsayEntity();
//	    say.setSaycontent(obj.getString("saycontent"));
//	    say.setSayuserid(obj.getString("sayuserid")); 
//	    int permission=obj.getIntValue("permission");
//	    say.setPermission(permission); 
//	    MultipartFile[] filedatas=(MultipartFile[]) obj.get("MultipartFile");
//	    String users=obj.getString("sayusers");
//		try {
//			String[] sayusers=null;
//			if(StringUtils.isNotEmpty(users))
//				sayusers =users.split(";");
//			sayService.addSay(say,filedatas,sayusers);
//		} catch (Exception e) {
//			body.put("msg","error");
//			e.printStackTrace();
//		} 
//		body.put("msg","success");
//		response.setBody(body);
//		return response;
//	}
	
//	public JSONObject addSay(Map<String,Object> param,MultipartFile[] filedatas) {
//		JSONObject body=new JSONObject(); 
//		IpavsayEntity say=new IpavsayEntity();
//		say.setSayuserid(param.get("sayuserid")==null?"":param.get("sayuserid").toString()); 
//	    say.setSaycontent(param.get("saycontent")==null?"":param.get("saycontent").toString());
//	    int permission=param.get("permission")==null?1:Integer.parseInt(param.get("permission").toString());
//	    say.setPermission(permission); 
//	    String users=param.get("sayusers")==null?"":param.get("sayusers").toString();
//		try {
//			String[] sayusers=null;
//			if(StringUtils.isNotEmpty(users))
//				sayusers =users.split(";");
//			sayService.addSay(say,filedatas,sayusers);
//		} catch (Exception e) {
//			body.put("msg","error");
//			e.printStackTrace();
//		} 
//		body.put("msg","success");
//		return body;
//	}
	public JSONObject addSay(Map<String,Object> param,File file) {
		JSONObject body=new JSONObject();
		IpavsayEntity say=new IpavsayEntity();
		say.setSayuserid(param.get("sayuserid")==null?"":param.get("sayuserid").toString());
	    say.setSaycontent(param.get("saycontent")==null?"":param.get("saycontent").toString());
	    int permission=param.get("permission")==null?1:Integer.parseInt(param.get("permission").toString());
	    say.setPermission(permission); 
	    String users=param.get("sayusers")==null?"":param.get("sayusers").toString();
		String[] sayusers=null;
		try {
			if(StringUtils.isNotEmpty(users))
				sayusers =users.split(";");
			sayService.addSay(say,null,sayusers,0);
			readZipFile(file,say.getSayid().toString());
		} catch (Exception e) {
			body.put("msg","error");
			e.printStackTrace();
		}
		body.put("msg","success");
		return body;
	}
	//解析zip,保存说说图片
	public  void readZipFile(File file,String sayid) throws Exception {  
        ZipFile zf = new ZipFile(file);  
        InputStream in = new BufferedInputStream(new FileInputStream(file));  
        ZipInputStream zin = new ZipInputStream(in);  
        ZipEntry ze;  
        FTPUtil util=new FTPUtil();
        while ((ze = zin.getNextEntry()) != null) {  
            if (ze.isDirectory()) {
         	   continue;
            } else {  
//              System.err.println("file -- " + ze.getName() + " : " + ze.getSize() + " bytes"+"\t");  
                long size = ze.getSize();  
                if (size > 0) {
	             	String extensionName = ze.getName().substring(ze.getName().lastIndexOf(".")+1, ze.getName().length());
	      			String imagepath = System.currentTimeMillis() + "." +  extensionName;
	   				util.upload(zf.getInputStream(ze),ContentUtil.IMAGEPATHS.get("say"), imagepath);
	   				IpavActionFileEntity sayimage = new IpavActionFileEntity();
	   				sayimage.setFilename(imagepath);
	   				sayimage.setFilepath(imagepath);
	   				sayimage.setActionid(sayid);
	   				sayimage.setCreatedate(FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
	   				sayimage.setActiontype(1);
	   				sayImageMapper.insertActionfile(sayimage);
                }  
            }  
        }  
        zin.closeEntry();  
    }  
	
	/***
	 * 添加说说评论
	 * 
	 * @param comment
	 */
	public ResponseParameter addSayComment(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject(); 
		IpavcommentEntity comment=new IpavcommentEntity(); 
		comment.setActiontype(1);
		comment.setActionid(obj.getLong("actionid"));
		comment.setCommuserid(obj.getString("commuserid"));
		if(obj.containsKey("commcontent"))
		comment.setCommcontent(obj.getString("commcontent"));
		else 
			comment.setCommcontent("");
		comment.setCommtype(obj.getInteger("commtype"));
		if(obj.containsKey("isdelete"))
		comment.setIsdelete(obj.getInteger("isdelete"));
		else
		comment.setIsdelete(0);
		int result=commReplyService.addComment(comment)>0?1:0;
		body.put("result", result);
		if(comment.getCommtype()==0){
		//点赞时候
			List<IpavuserEntity> userList =
					sayService.publicSearchPraiseUser(comment.getActionid(),1,null,null,1);
		     List<Map<String,Object>> userPraises=new ArrayList<Map<String,Object>>();
			for(IpavuserEntity user: userList){ 
				if(user!=null){
					Map<String,Object> map =new HashMap<String,Object>();
					map.put("userid", user.getUserid());
					map.put("username", user.getUsername());
					userPraises.add(map);
				}
			}
			body.put("userPraises", userPraises);
			body.put("praisSize",commReplyService.queryPraiseOrCommentCount(1, comment.getActionid(), 0));
		}else{
		//添加评论时候
			List<JSONObject> commentsMap=new ArrayList<JSONObject>();
			List<IpavcommentEntity> commentList=
					commReplyService.queryCommentById(1, comment.getActionid(),1,null,null);
			for(IpavcommentEntity  c :commentList){
	    		JSONObject commentMap=CommentToMap(c);
	    		commentsMap.add(commentMap);
	    	}
			body.put("comments", commentsMap);
			body.put("commentSize",commReplyService.queryPraiseOrCommentCount(1, comment.getActionid(), 1));
		}
		response.setBody(body);
		return response;
	} 
	
	/**
	 * 添加回复
	 * @param param
	 * @param service
	 * @return
	 */

	public ResponseParameter addReply(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		JSONObject body=new JSONObject(); 
		IpavreplyEntity reply=new IpavreplyEntity();
		reply.setBereplyid(obj.getString("bereplyid"));
		reply.setCommentid(obj.getLong("commentid"));
		reply.setReplycontent(obj.getString("replycontent"));
		reply.setReplyuserid(obj.getString("replyuserid"));
		int result=commReplyService.addReply(reply)>0?1:0;
		body.put("result", result);
//		IpavcommentEntity comment=
//				commReplyService.queryCommentByCommentid(reply.getCommentid().longValue(),null,null);
//		 
//		JSONObject commentMap=CommentToMap(comment);
//		body.put("comment", commentMap);
		List<JSONObject> commentsMap=new ArrayList<JSONObject>();
		long sayid = Long.parseLong(obj.getString("actionid"));
		List<IpavcommentEntity> commentList=
				commReplyService.queryCommentById(1,sayid,1,null,null);
		for(IpavcommentEntity  c :commentList){
    		JSONObject commentMap=CommentToMap(c);
    		commentsMap.add(commentMap);
    	}
		body.put("comments", commentsMap);
		body.put("commentSize",commReplyService.queryPraiseOrCommentCount(1, sayid, 1));
		response.setBody(body);
		return response;
	}
	
	/**
	 * 删除说说
	 * @param param
	 * @param service
	 * @return
	 */
	
	public ResponseParameter delSay(RequestParameter param, int service) {
		ResponseParameter response=new ResponseParameter(service);
		JSONObject obj=(JSONObject)JSONObject.toJSON(param.getBody());
		Long sid=obj.getLong("sayid");
		String userid=obj.getString("userid");
		JSONObject body=new JSONObject(); 
		String msg =sayService.delSay(sid,userid);
		body.put("msg",msg);
		response.setBody(body);
		return response;
	}
	/**
	 * 转换时间
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String DateToString(String date) {
		 SimpleDateFormat  df=new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
	    Date d;
	    try {
			d = df.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			Calendar newDate = Calendar.getInstance();
			newDate.setTime(new Date());
		   int day=newDate.get(Calendar.DAY_OF_YEAR)-calendar.get(Calendar.DAY_OF_YEAR);
		   int year =newDate.get(Calendar.YEAR )-calendar.get(Calendar.YEAR);
		   String head="";
		   if(day==0 && year==0){
			   head="今天";
		   }else if(day==1&&year==0){
			   head="昨天";
		   }else if(day==2&&year==0){
	    	head="前天";
	       }else{
	    	   head=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"
	 			   +calendar.get(Calendar.DAY_OF_MONTH); 
	       }
			 
			
			String body="";
			if(calendar.get(Calendar.HOUR_OF_DAY)<5){
				body="凌晨";
			}else if(calendar.get(Calendar.HOUR_OF_DAY)<11)
			{
				body="上午";
			}else if(calendar.get(Calendar.HOUR_OF_DAY)<13)
			{
				body="中午";
			}else if(calendar.get(Calendar.HOUR_OF_DAY)<19)
			{
				body="下午";
			}else  
			{
				body="晚上";
			}
			String MINUTE= calendar.get(Calendar.MINUTE)>10?(calendar.get(Calendar.MINUTE)+""):("0"+calendar.get(Calendar.MINUTE));
			return head+" "+body+" "+calendar.get(Calendar.HOUR)+":"+MINUTE; 
		} catch (ParseException e) {
			return date;
		}  
	}	
	
	/**
	 * 添加主题图片
	 * @param file
	 * @param userid
	 * @return
	 */
  public JSONObject addSayTheme(File file,String userid){
	  JSONObject body=new JSONObject();
	  ZipFile zf;
	try {
	  zf = new ZipFile(file);
      InputStream in = new BufferedInputStream(new FileInputStream(file));  
      ZipInputStream zin = new ZipInputStream(in);  
      ZipEntry ze;  
      String newFileName="";
      while ((ze = zin.getNextEntry()) != null) {  
          if (ze.isDirectory()) {
       	   continue;
          } else {   
        	  String extensionName = ze.getName().substring(ze.getName().lastIndexOf(".") + 1, ze.getName().length());
        	  newFileName = System.currentTimeMillis() + "." + extensionName;
              long size = ze.getSize();  
              if (size > 0) {
        	    FTPUtil util=new FTPUtil();
        	    util.upload(zf.getInputStream(ze),ContentUtil.IMAGEPATHS.get("theme"), newFileName);
            	//查询主题图片是否存在
          	    Map sqlmap=new HashMap<String,Object>();
          	    sqlmap.put("actionid",userid);
          	    sqlmap.put("actiontype",4);
          	    IpavActionFileEntity  sayimage = sayImageMapper.queryActionfileByAction(sqlmap);
          	    if(sayimage!=null){//存在
          	    	//删除旧图片
          	    	 util.delete(sayimage.getFilepath(), ContentUtil.IMAGEPATHS.get("theme"));
          	    	 Map smap=new HashMap<String, Object>();
          	    	 smap.put("fileid", sayimage.getFileid());
          	    	 smap.put("filename", newFileName);
          	    	 smap.put("filepath", newFileName);
          	    	 smap.put("createdate",FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
          	    	 sayImageMapper.updateFilePath(smap);
          	    }else{//不存在
          	    	sayimage = new IpavActionFileEntity();
            	    sayimage.setFilepath(newFileName);
            	    sayimage.setFilename(newFileName);
    				sayimage.setActionid(userid);
    				sayimage.setActiontype(4);
    				sayimage.setCreatedate(FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
    				sayImageMapper.insertActionfile(sayimage);
          	    }
              }  
          }  
      }  
      zin.closeEntry();  
      body.put("msg","success");
      body.put("themeImg",ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("theme")+newFileName);
	  return body;
	}  catch (Exception e) {
	  body.put("msg","error");
 	  return body;
	} 
  }
	
  
}

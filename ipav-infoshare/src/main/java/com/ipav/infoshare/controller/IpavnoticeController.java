package com.ipav.infoshare.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.infoshare.service.IpavcommReplyService;
import com.ipav.infoshare.service.IpavnoticeService;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavnoticeEntity;
import com.ipav.system.entity.IpavnoticeSendedEntity;
import com.ipav.system.entity.IpavnoticeTypeEntity;
import com.ipav.system.entity.IpavorgEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavorgService;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;
import com.ipav.system.util.MultipartFileValidator;

@Controller
@RequestMapping(value="ipav/notice")
public class IpavnoticeController {
	//文件校验类型集合
	private   final String[] filetype=new String[]{".jpg",".png",".rar",".txt",".zip",".doc",".ppt",".xls",".pdf",".docx",".xlsx"};
	//定义script的正则表达式  
	private   final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
	//定义style的正则表达式
    private   final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";  
    //定义HTML标签的正则表达式
    private   final String regEx_html = "<[.[^>]]*>";
    //定义空格回车换行符
    private   final String regEx_space = "\\s*|\t|\r|\n"; 
    //跳转到公告页面,进行初始化
    private   final String NOTICE_URL = "/ipav/notice/noticeManager";
    
	@Autowired
	private IpavnoticeService noticeService;
	@Autowired
	private IpavuserService userService;
	@Autowired
	private IpavorgService orgService;
	@Autowired
	private IpavcommReplyService commReplyService;
	
	private MultipartFileValidator validator;
	
	/**
	 * 初始化文件校验信息
	 */
	@PostConstruct
	public void init(){
		validator = new MultipartFileValidator();
		validator.setMaxSize(20);//单位M
		validator.setAllowedContentTypes(filetype);
	}
	/**
	 * 公告附件下载
	 * @param path
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/getFile")
	public void getNoticeFile(String path,HttpServletResponse response) throws Exception{
		Map<String,Object> parm = new HashMap<String,Object>();
		parm.put("filename", path);
		IpavnoticeEntity notice=noticeService.getNoticeByParm(parm);
		if(notice!=null){
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/octet-stream; charset=utf-8");  
			response.setHeader("pragma", "no-cache");	
		    response.addHeader("Content-Disposition", "attachment;filename="+new String(notice.getOldfilename().getBytes("utf-8"), "ISO-8859-1")); 
	        URL url = new URL(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("files")+notice.getFilepath());
	        URLConnection conn = url.openConnection();
	        InputStream in = conn.getInputStream();	
	        byte[] buffer = new byte[1024];    
	        int len = 0;    
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
	        while((len = in.read(buffer)) != -1) {    
	            bos.write(buffer, 0, len);    
	        }    
	        bos.close();    
	        byte[] getData=bos.toByteArray();
	        OutputStream out = response.getOutputStream();
	        out.write(getData);
	        if(out!=null) out.close();  
	        if(in!=null) in.close();  	
		}
	}
	/**
	 * 获取组织用户树数据
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/showOrgTree")
	@ResponseBody
	public JSONArray showOrgTree(HttpSession session){
		System.out.println("IpavnoticeController.showOrgTree()");
		IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
		List orguserlist =new ArrayList();
		if(curuser!=null){
			orguserlist =userService.queryOrgUserTree(curuser.getCompanyid().toString());
		}
		JSONArray arr=(JSONArray)JSONArray.toJSON(orguserlist);
		return arr;
	}
	
	/**********************公告*********************/
	/**
	 * 根据ID查询单个公告
	 * @param noticeid
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/queryNoticeById")
	@ResponseBody
	public String queryNoticeById(String noticeid,HttpSession session){
		IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
		JSONObject obj= new JSONObject();
		if(curuser!=null){
			Map<String,Object> parm = new HashMap<String,Object>();
			parm.put("noticeid", noticeid);
			IpavnoticeEntity notice = noticeService.getNoticeByParm(parm);
			if(notice!=null){
				noticeService.modifyName(notice);
				Map<String,Object> map=noticeInfo(notice,curuser.getUserid());
				obj.put("list", map);			
			}
		}
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 公告列表全查询
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/queryNotices")
	@ResponseBody
	public String queryNotices(Integer beginRow,Integer pageSize,HttpSession session){
		JSONObject rsobj=new JSONObject();
		List<Map<String,Object>> rsmap=new ArrayList<Map<String,Object>>();
		IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
		if(curuser!=null){
			String userid=curuser.getUserid();
			Map<String,Object> parm = new HashMap<String,Object>();
			parm.put("userid", userid);
			parm.put("beginRow", beginRow);
			parm.put("pageSize", pageSize);
			rsobj.put("listcnt", noticeService.getListCnt(parm));
			List<IpavnoticeEntity>  noticelist = noticeService.getNoticeList(parm);
			for (IpavnoticeEntity notice : noticelist) {
				Map<String,Object> map =noticeInfo(notice,userid);
				rsmap.add(map);
			}
		}
		rsobj.put("list", rsmap);
		return 	JSON.toJSONString(rsobj);
	}
	/**
	 * 根据条件查询公告列表
	 * @param noticetype 公告类型
	 * @param flag : 0代表我未发布,1代表已发布的公告, 2代表我接受的公告,空代表全查询
	 * @param contentstr 根据标题个内容模糊查询
	 * @param dateBegin 公告发布时间:开始时间
	 * @param dateEnd   公告发布时间:结束时间
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/searchNotices")
	@ResponseBody
	public String searchNotices(String noticetype,
			Integer flag, String contentstr,String dateBegin,String dateEnd,HttpSession session,Integer beginRow,Integer pageSize) {
		JSONObject rsobj=new JSONObject();
		List<Map<String,Object>> rsmap=new ArrayList<Map<String,Object>>();
		IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
		if(curuser!=null){
			String userid=curuser.getUserid();
			Map<String,Object> parm = new HashMap<String,Object>();
			parm.put("userid", userid);
			if(compare_date(dateBegin, dateEnd)){
				parm.put("dateBegin", dateBegin);
				parm.put("dateEnd", dateEnd);
			}
			if (StringUtils.isNotEmpty(noticetype)) {
				parm.put("typeid", noticetype);
			}
			if (flag != null) {
				if (flag == 0 || flag == 1) {
					parm.put("istrue", flag);
				} else if (flag == 2) {
					parm.put("sendeduserid", userid);
				}
			}
			if (StringUtils.isNotEmpty(contentstr)) {
				parm.put("searchstr", contentstr);
			}
			parm.put("beginRow", beginRow);
			parm.put("pageSize", pageSize);
			rsobj.put("listcnt", noticeService.getListCnt(parm));
			List<IpavnoticeEntity>  noticelist = noticeService.getNoticeList(parm);
			for (IpavnoticeEntity notice : noticelist) {
				Map<String,Object> map =noticeInfo(notice,userid);
				rsmap.add(map);
			}
		}
		rsobj.put("list", rsmap);
		return 	JSON.toJSONString(rsobj);
	}
	
	/**
	 * 构造公告具体信息
	 * @param notice
	 * @return
	 */
	private Map<String,Object> noticeInfo(IpavnoticeEntity notice,String userid){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> parm = new HashMap<String,Object>();
		Long nid=notice.getId();
		parm.put("nid",nid);
		parm.put("userid", notice.getUserid());
		parm.put("typeid", notice.getTypeid());
		Map<String,String> nomap=noticeService.getNoticeNo(parm);
		String noticenostr=nomap.get("companyname")+" "+nomap.get("typename")+nomap.get("nostr").replace("{0}", nomap.get("noindex"));					
		map.put("noticeno", noticenostr);
		notice.setCreatedate(FormatUtil.formatDate(notice.getCreatedate()));
		map.put("notice", notice);
		//当前用户对此公告是否已赞
		map.put("isPraise",commReplyService.queryIsPraiseOrComment(2, nid, 0,userid, 0));
		parm.clear();
		parm.put("noticeid",nid);		
		parm.put("isread",1);
		parm.put("isdelete",0);
		parm.put("userid",userid);
		//当前用户对此公告是否已读
		map.put("isReade",noticeService.getSendedCnt(parm));
		map.put("likecnt", commReplyService.queryPraiseOrCommentCount(2, nid, 0));
		map.put("commcnt",  commReplyService.queryPraiseOrCommentCount(2, nid, 1));
		parm.remove("userid");
		map.put("readecnt", noticeService.getSendedCnt(parm));
		return map;
	}
	
	/**
	 * 获取公告所有已读人数集合
	 * @param noticeid
	 * @return
	 */
	@RequestMapping(value = "/queryNoticeReades")
	@ResponseBody
	public String queryNoticeReades(String noticeid,Integer curPage,Integer pageSize){
		JSONObject rsobj=new JSONObject();
		if(StringUtils.isNotEmpty(noticeid)){
			Map<String,Object> parm = new HashMap<String,Object>();
			parm.put("noticeid",noticeid);
			parm.put("isread", 1);
			rsobj.put("readecnt",noticeService.getSendedCnt(parm));
			pageSize=pageSize==null?10:pageSize;
			Integer beginRow=curPage==null?0:(curPage-1)*pageSize;
			parm.put("beginRow", beginRow);
			parm.put("pageSize", pageSize);
			List<IpavnoticeSendedEntity> sendslist = noticeService.getNoticeSended(parm);
			List<String> list=new ArrayList<String>();
			for(IpavnoticeSendedEntity ns:sendslist){
				IpavuserEntity user=noticeService.getUserById(ns.getUserid());
				if(user!=null)	list.add(user.getUsername());
			}
			rsobj.put("readelist",list);
			rsobj.put("curPage",curPage);
		}
		return JSON.toJSONString(rsobj);
	}
	
	/**
	 * 更新公告接收人浏览状态(未读-->已读)
	 * @param noticeid
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/modifyReadeState")
	@ResponseBody
	public String modifyReadeState(String noticeid,HttpSession session){
		JSONObject obj=new JSONObject();
		IpavuserEntity curuser = (IpavuserEntity)session.getAttribute("curuser");
		if(curuser!=null){
			Map<String,Object> parm = new HashMap<String,Object>();
			parm.put("noticeid", noticeid);
			parm.put("userid", curuser.getUserid());
			parm.put("isread", 1);
			noticeService.modifySendedState(parm);
			parm.remove("userid");
			obj.put("readecnt", noticeService.getSendedCnt(parm));
			obj.put("msg", "success");
		}else{
			obj.put("msg", "error");
		}
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 根据获取公告在某一公告类型下的最大编号
	 * @param type_id 公告类型id
	 * @return
	 */
	@RequestMapping(value = "/getMaxNoticeIndex")
	@ResponseBody
	public String queryMaxIndex(String type_id){
		JSONObject obj=new JSONObject();
		Map<String,Object> parm = new HashMap<String,Object>();
		parm.put("typeid", type_id);
		String maxIndexStr=noticeService.queryMaxIndex(parm);
		obj.put("maxIndex",maxIndexStr!=null?Integer.parseInt(maxIndexStr)+1:"1");
		return JSON.toJSONString(obj);
	}
	/**
	 * 获取添加公告的默认信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/gotoAddNotice")
	@ResponseBody
	public String gotoAddNotice(HttpSession session) {
		JSONObject obj=new JSONObject();
		IpavuserEntity curuser = (IpavuserEntity) session.getAttribute("curuser");
		if (curuser != null) {
			//默认接收人集合
			Map<String,Object> parm = new HashMap<String,Object>();
			parm.put("companyid", curuser.getCompanyid());
			obj.put("sendeds", userService.queryUsers(parm));
			IpavorgEntity org = orgService.queryOrgById(String.valueOf(curuser.getOrgid()));
			if(org!=null){
				//默认签发人id(当前用户所在的组织负责人)
				obj.put("issuedid", org.getOrgchef());
				obj.put("issuedname", noticeService.getUserById(org.getOrgchef())==null?"":noticeService.getUserById(org.getOrgchef()).getUsername());
				//默认发文部门(当前用户所在的组织)
				obj.put("orgid", org.getOrgid());
				obj.put("orgname", org.getOrgname());
			}
			//默认公告编号Map
			parm.clear();
			parm.put("userid", curuser.getUserid());
			addNoticeNoStr(parm,obj,true);
			obj.put("actionmodel", "addNotice");
		}
		return JSON.toJSONString(obj);
	}
  
	/**
	 * 添加公告
	 * @param notice
	 * @param model
	 * @param filedata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNotice")
	public String addNotice(IpavnoticeEntity notice,HttpSession session,
			@RequestParam(value = "filedata", required = false) MultipartFile filedata) throws Exception {
		validator.validate(filedata);
		if(notice!=null){
			IpavuserEntity curuser = (IpavuserEntity) session.getAttribute("curuser");
			if(notice.getIscomment()==null)	notice.setIscomment(0);
			if(notice.getIstop()==null) notice.setIstop(0);
			if(notice.getOrgid()==null) notice.setOrgid(curuser==null?"":curuser.getOrgid().toString());
			notice.setContentText(getTextFromHtml(notice.getContent()));
			noticeService.addNotice(notice, filedata);
		}
		return "forward:"+NOTICE_URL;
	}
	
	/**
	 * 获取待更新公告信息
	 * @param noticeid
	 * @return
	 */
	@RequestMapping(value = "/gotoUpdateNotice")
	@ResponseBody
	public String gotoUpdateNotice(String noticeid) {
		JSONObject obj=new JSONObject();
		Map<String,Object> parm = new HashMap<String,Object>();
		parm.put("noticeid", noticeid);
		IpavnoticeEntity notice = noticeService.getNoticeByParm(parm);
		if(notice!=null){
			parm.clear();
			parm.put("noticeid", notice.getId());
			notice.setSendeds(noticeService.getNoticeSended(parm));
			noticeService.modifyName(notice);
			parm.clear();
			parm.put("nid", notice.getId());
			parm.put("userid", notice.getUserid());
			parm.put("typeid", notice.getTypeid());
			addNoticeNoStr(parm,obj,false);
			obj.put("notice", notice);
			obj.put("actionmodel", "updateNotice");
		}
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 更新公告
	 * @param updateFile :1代表附件有改动,需重置　　 
	 * @param notice
	 * @param model
	 * @param filedata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateNotice")
	public String updateNotice(Integer updateFile,IpavnoticeEntity notice,ModelMap model,
			@RequestParam(value = "filedata", required = false) MultipartFile filedata)
			throws Exception {
		validator.validate(filedata);
		if(notice!=null){
			if(notice.getIscomment()==null)	notice.setIscomment(0);
			if(notice.getIstop()==null) notice.setIstop(0);
			if(updateFile!=null){if(updateFile==1) noticeService.movefile(notice);}//删除原文件
			notice.setContentText(getTextFromHtml(notice.getContent()));
			noticeService.modifyNotice(notice, filedata);
		}
		return "forward:"+NOTICE_URL;
	}

	/**
	 * 更新公告状态
	 * @param noticeid
	 * @param actiontype:0代表将待发布的公告进行发布;1代表进行删除公告
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateNoticeState")
	@ResponseBody
	public String updateNoticeState(String noticeid,Integer actiontype,HttpSession session) {
		if(actiontype!=null){
			Map<String,Object> parm = new HashMap<String,Object>();
			parm.put("id", Long.parseLong(noticeid));
			if(actiontype==0){
				parm.put("istrue", 1);
				parm.put("createdate", FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			}else if(actiontype==1){
				parm.put("isdelete", 1);
			}
			noticeService.updateNoticeState(parm);
		}
		return queryNotices(0,10,session);
	}

	/**********************公告类型*********************/
	/**
	 * 查询获取所有公告类型列表(无权限控制)
	 * @param istrue
	 * @param beginRow
	 * @param pageSize
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/queryTypes")
	@ResponseBody
	public String queryTypes(Integer istrue,Integer beginRow,Integer pageSize,HttpSession session) {
		JSONObject rsobj = new JSONObject();
		IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
		if(curuser!=null){
			rsobj=getNoticeTypes(istrue,beginRow,pageSize,curuser.getCompanyid().toString());
		}
		return JSON.toJSONString(rsobj);
	}
	
	/**
	 * 查询公告类型集合(带有权限控制)
	 * @param istrue
	 * @param beginRow
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/showTypes")
	@ResponseBody
	public String showTypes(Integer istrue,Integer beginRow,Integer pageSize,HttpSession session) {
		IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
		JSONObject rsobj = new JSONObject();
		if(curuser!=null){
			rsobj=getNoticeTypes(istrue,beginRow,pageSize,curuser.getCompanyid().toString());
		}
		return JSON.toJSONString(rsobj);
	}
	
	/**
	 * 构造公告类型具体信息
	 * @param istrue
	 * @param beginRow
	 * @param pageSize
	 * @param companyid
	 * @return
	 */
	public JSONObject getNoticeTypes(Integer istrue,Integer beginRow,Integer pageSize,String companyid){
		JSONObject rsobj=new JSONObject();
		Map<String,Object> parm = new HashMap<String,Object>();
		parm.put("companyid", companyid);
		parm.put("istrue", istrue);
		Integer tylistcnt=noticeService.getNoticeTypeCnt(parm);
		parm.put("beginRow", beginRow);
		parm.put("pageSize", pageSize);
		List<IpavnoticeTypeEntity> typelist = noticeService.getNoticeTypeList(parm);
		rsobj.put("typelist", typelist);
		rsobj.put("resultcnt", tylistcnt);
		return rsobj;
	}
	
	/**
	 * 添加公告类型
	 * @param noticetype
	 * @param istrue
	 * @return
	 */
	@RequestMapping(value = "/addNoticeType")
	@ResponseBody
	public String addNoticeType(IpavnoticeTypeEntity noticetype,HttpSession session) {
		JSONObject rsobj=new JSONObject();
		IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
		String messageStr="error";
		if(curuser!=null){
			Map<String,Object> parm= new HashMap<String, Object>();
			parm.put("companyid", curuser.getCompanyid());
			parm.put("typename", noticetype.getTypename());
			int namecnt=noticeService.queryNameCnt(parm);
			if(namecnt==0){
				noticeService.addNoticeType(noticetype);
				messageStr="success";
			}
		}
		rsobj.put("message",messageStr);
		return JSON.toJSONString(rsobj);
	}
	
	/**
	 * 更新公告类型
	 * @param noticetype
	 * @param ischk
	 * @return
	 */
	@RequestMapping(value = "/modifyNoticeType")
	@ResponseBody
	public String modifyNoticeType(IpavnoticeTypeEntity noticetype,Integer ischk,HttpSession session) {
		JSONObject rsobj=new JSONObject();
		IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
		String messageStr="error";
		if(curuser!=null){
				if(noticetype!=null&&ischk==1){
					Map<String,Object> parm= new HashMap<String, Object>();
					parm.put("companyid", curuser.getCompanyid());
					parm.put("typename", noticetype.getTypename());
					int namecnt=noticeService.queryNameCnt(parm);
					if(namecnt==0){
						noticeService.modifyNoticeType(noticetype);
						messageStr="success";
					}
				}
		}
		rsobj.put("message",messageStr);
		return JSON.toJSONString(rsobj);
	}
	
	/**
	 * 更新公告类型状态
	 * @param actionty : 0.更新公告类型启用状态;1.更新公告类型禁用状态;2.更新公告类型默认类型;3.删除公告
	 * @param ids
	 * @param values
	 * @return
	 */
	@RequestMapping(value = "/modifyNoticeTypeState")
	@ResponseBody
	public String modifyNoticeTypeState(Integer actiontype,
			@RequestParam(value = "ids[]", required = false) String[] ids,
			@RequestParam(value = "values[]", required = false) String[] values,
			HttpSession session) {
	   JSONObject rsobj=new JSONObject();
	   IpavuserEntity curuser=(IpavuserEntity)session.getAttribute("curuser");
	   String messageStr="error";
	   if(curuser!=null){
		   if (actiontype!=null&&ids!=null) {
				for(int i=0;i<ids.length;i++){
					Map<String,Object> parm = new HashMap<String,Object>();
					parm.put("id", Long.parseLong(ids[i]));
					String keyStr = "";
					int keyValue=1;
					if (actiontype == 0||actiontype == 1) {
						keyStr = "istrue";
						keyValue=Integer.parseInt(values[i]);
					} else if (actiontype == 2) {
						keyStr = "isdefault";
						Map<String,Object> parm1 = new HashMap<String,Object>();
						parm1.put("companyid", curuser.getCompanyid());
						noticeService.updateDefaultType(parm1);
					} else if (actiontype == 3) {
						keyStr = "isdelete";
					}
					parm.put(keyStr, keyValue);
					noticeService.modifyNoticeTypeState(parm);
			   }
			  messageStr="success";
		   }
	   }
	   rsobj.put("message",messageStr);
	   return JSON.toJSONString(rsobj);
	}
	
	/**
	 * 更新公告类型序号
	 * @param ids
	 * @param indexs
	 * @return
	 */
	@RequestMapping(value = "/changeNoticeTypeIndex")
	@ResponseBody
	public String changeNoticeTypeIndex(
			@RequestParam(value = "ids[]", required = false) Integer[] ids,
			@RequestParam(value = "indexs[]", required = false) Integer[] indexs) {
		JSONObject rsobj=new JSONObject();
		String messageStr="error";
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {				
				Map<String,Object> parm = new HashMap<String,Object>();
					parm.put("id", ids[i]);
					parm.put("typeindex", indexs[i]);
					noticeService.changeNoticeTypeIndex(parm);
			}
			messageStr="success";
		}
		rsobj.put("message", messageStr);
		return JSON.toJSONString(rsobj);
	}
	
	/*****************************公告评论,点赞与begin**************************************/
	
	/**
	 * 根据公告id查询评论集合
	 * @param noticeid 
	 * @param isAll 是否加载全部评论
	 * @return
	 */
	@RequestMapping(value = "/queryNoticeComments")
	@ResponseBody
	public String queryComments(String noticeid,String isAll) { 
		JSONObject rsobj=new JSONObject();
		if(StringUtils.isNotEmpty(noticeid)){
			Long nid=Long.parseLong(noticeid);
			Integer beginRow=null;
			Integer pageSize=null;
			if("0".equals(isAll)){	beginRow=0;	pageSize=5;	}
			List<IpavcommentEntity> list=commReplyService.queryCommentById(2, nid,1,beginRow,pageSize);
			List<Map<String,Object>> commlist=new ArrayList<Map<String,Object>>();
			for(IpavcommentEntity comment :list){  
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("commuserid", comment.getCommuserid());
				map.put("commusername", comment.getCommusername());
				map.put("commuserimg", comment.getCommuserimg());
				map.put("commcontent", comment.getCommcontent());
				map.put("commdate", FormatUtil.formatDate(comment.getCommdate()));
				commlist.add(map); 
			}
			rsobj.put("commlist",commlist);
			rsobj.put("commcount",commReplyService.queryPraiseOrCommentCount(2,nid, 1));
		}
		return  JSON.toJSONString(rsobj);		 
	}
	
	/**
	 * 查询公告已赞用户列表
	 * @param noticeid
	 * @return
	 */
	@RequestMapping(value = "/queryNoticeLikes")
	@ResponseBody
	public String queryNoticeLikes(String noticeid,Integer curPage,Integer pageSize){
		JSONObject rsobj=new JSONObject();
		List<Map<String,Object>> rslist = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotEmpty(noticeid)){
			Long nid=Long.parseLong(noticeid);
			pageSize=pageSize==null?10:pageSize;
			Integer pageNo=curPage==null?0:(curPage-1)*pageSize;
			List<String> list=commReplyService.queryUserListByActionId(new Long(noticeid),2,pageNo,pageSize,1);
			for(String i: list){
				IpavuserEntity user =noticeService.getUserById(i);
				if(user!=null){
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("userid", user.getUserid());
					map.put("username",user.getUsername());
					map.put("userimg", ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+user.getPicpath());
					rslist.add(map);
				}
			}	
			rsobj.put("curPage", curPage);
			rsobj.put("likelist",rslist);
			rsobj.put("likecount",commReplyService.queryPraiseOrCommentCount(2,nid, 0));
		}
		return JSON.toJSONString(rsobj);
	}
	
	/**
	 * 添加公告点赞或公告评论
	 * @param commentStr
	 * @return
	 */
	@RequestMapping(value = "/addLikeOrComment")
	@ResponseBody
	public String addLikeOrComment(String commentStr) {
		JSONObject rsobj=new JSONObject();
		String message="error";
		if(StringUtils.isNotEmpty(commentStr)){
			IpavcommentEntity comment = JSON.toJavaObject(JSON.parseObject(commentStr),IpavcommentEntity.class);
			comment.setActiontype(2);//活动类型2:公告
			comment.setIsdelete(0);
			commReplyService.addComment(comment);
			message="success";
	    }
		rsobj.put("msg", message);
		return  JSON.toJSONString(rsobj);	
	}

	/*******************************公告评论,点赞与回复end*******************************************/
	//判断时间有效性
	 private  boolean compare_date(String dateBegin, String dateEnd) {
	    boolean flg=false;
	    if(StringUtils.isNotEmpty(dateBegin)&&StringUtils.isNotEmpty(dateEnd)){
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            Date begin = df.parse(dateBegin);
	            Date end = df.parse(dateEnd);
	            if (begin.getTime() <= end.getTime())  flg=true;
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	    }  
        return flg;
	  }
	 
	 //添加公告编号信息
	  private void addNoticeNoStr(Map parm,Map map,boolean flg){
			Map<String,String> nomap=noticeService.getNoticeNo(parm);
			//默认公告编号Map
			for(String key : nomap.keySet()){
				if("noindex".equals(key)&&flg==true){
					int maxIndex=1;
					String index=nomap.get("noindex");
					if(StringUtils.isNotEmpty(index)){
						maxIndex=Integer.parseInt(index)+1;      
					}
					map.put(key,maxIndex);
				}else{
					map.put(key,nomap.get(key));
				}
			}
	    }
	  
	  //删除前台标签
	   private String delTag(String htmlStr) {  
	        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
	        Matcher m_script = p_script.matcher(htmlStr);  
	        htmlStr = m_script.replaceAll(""); // 过滤script标签  
	  
	        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
	        Matcher m_style = p_style.matcher(htmlStr);  
	        htmlStr = m_style.replaceAll(""); // 过滤style标签  
	  
	        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);  
	        Matcher m_html = p_html.matcher(htmlStr);  
	        htmlStr = m_html.replaceAll(""); // 过滤html标签  
	  
	        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
	        Matcher m_space = p_space.matcher(htmlStr);  
	        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签  
	        return htmlStr.trim(); // 返回文本字符串  
	    }  
	   
	  //提取公告内容html中的文本
	  private String getTextFromHtml(String htmlStr){
		  if(StringUtils.isNotEmpty(htmlStr)){
			  // 删除html标签  
			  htmlStr = delTag(htmlStr);  
	          htmlStr = htmlStr.replaceAll("&nbsp;", "");  
			  return  htmlStr;
		  }else{
			  return "";
		  }
	  }

}

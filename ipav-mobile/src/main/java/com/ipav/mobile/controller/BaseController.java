package com.ipav.mobile.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ipav.mobile.param.RequestHeader;
import com.ipav.mobile.param.RequestParameter;
import com.ipav.mobile.param.ResponseParameter;
import com.ipav.mobile.service.MyMsgService;
import com.ipav.mobile.service.NoticeService;
import com.ipav.mobile.service.SayService;
import com.ipav.mobile.service.UserService;
import com.ipav.mobile.service.VoteService;
import com.ipav.mobile.service.WageService;
import com.ipav.system.util.RequestParamToMap;

@Controller
@RequestMapping(value="ipav/mobile")
public class BaseController {

	@Autowired
	private UserService userSerivce;
	@Autowired
	private WageService wageService;
	@Autowired
	private SayService saySerivce;
	@Autowired
	private VoteService voteService;
	@Autowired
	private MyMsgService msgService;
	@Autowired
	private NoticeService noticeService;
	

	@RequestMapping(value = "/service", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseParameter<T> service(
			@RequestBody(required = true) RequestParameter param,HttpServletRequest request) {
		RequestHeader header = param.getHeader();
		ResponseParameter result = null;
		int service = header.getService();
		switch (service) {
		case 10001:
			result = userSerivce.login(param, service);
			break;
		case 10002:
			result = userSerivce.regist(param, service);
			break;
		case 10003:
			result = userSerivce.updatePwd(param, service);
			break;
		case 10004:
			result = userSerivce.updatePwd(param, service);
			break;
		case 10005:
			result = userSerivce.getValidateInfo(param, service);
			break;
		case 10006:
			result = userSerivce.queryOrgUserTree(param, service);
			break;
		case 10007:
			result = wageService.getWageInfos(param, service);
			break;
		case 10008:
			result = saySerivce.getSayList(param, service);
			break;
		case 10009:
			result=voteService.getVoteList(param, service);
			break;
		case 10010:
			result=voteService.getVoteList(param, service);
			break;
		case 10011:
			result=voteService.cast(param, service);
			break;
		case 10012:
			result=userSerivce.validate(param, service);
			break;
		case 10013:
			result=saySerivce.addSayComment(param, service);
			break;
		case 10014:
			result=saySerivce.addReply(param, service);
			break;
		case 10015:
			result=saySerivce.delSay(param, service);
			break;
//		case 10016:
//			result=saySerivce.addSay(param, service);
//			break;
//		case 10017:
//			result=msgService.searchMsg(param, service);
//			break;
		case 10017:
			result=noticeService.queryNotices(param, service);
			break;
		case 10018:
			result=noticeService.getNoticeById(param, service);
			break;
		case 10019:
			result=noticeService.queryComments(param, service);
			break;
		case 10020:
			result=noticeService.queryReades(param, service);
			break;
		case 10021:
			result=noticeService.queryLikes(param, service);
			break;
		case 10022:
			result=noticeService.addCommOrLike(param, service);
			break; 
		case 10023:
			result=userSerivce.queryRoomsInfos(param, service);
			break;
		}
		return result;
	}
	@RequestMapping(value = "/addSay", method = RequestMethod.POST, produces = "multipart/form-data")
	@ResponseBody
	public String addSay(HttpServletRequest request ,@RequestParam(value = "file", required = false) MultipartFile filedata) {
			Map<String,Object> parm = RequestParamToMap.convert(request);
			File file = null;
			if(filedata!=null){
				CommonsMultipartFile cf= (CommonsMultipartFile)filedata;
				DiskFileItem fi = (DiskFileItem)cf.getFileItem();
				file = fi.getStoreLocation(); 
			}
			JSONObject obj = saySerivce.addSay(parm,file);
			return JSON.toJSONString(obj);
	}
	@RequestMapping(value = "/addSayTheme", method = RequestMethod.POST, produces = "multipart/form-data")
	@ResponseBody
	public String addSayTheme(HttpServletRequest request ,@RequestParam(value = "file", required = false) MultipartFile filedata) {
			File file = null;
			if(filedata!=null){
				CommonsMultipartFile cf= (CommonsMultipartFile)filedata;
				DiskFileItem fi = (DiskFileItem)cf.getFileItem();
				file = fi.getStoreLocation(); 
			}
			Map<String,Object> parm = RequestParamToMap.convert(request);
			String userid = parm.get("sayuserid")==null?"":parm.get("sayuserid").toString();
			JSONObject obj = saySerivce.addSayTheme(file,userid);
			return JSON.toJSONString(obj);
	}
}

package com.ipav.vote.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.ExcelView;
import com.ipav.system.util.RequestParamToMap;
import com.ipav.system.util.ftp.FTPUtil;
import com.ipav.vote.action.VoteAction;
import com.ipav.vote.service.IpavvoteService;

@Controller
@RequestMapping(value="ipav/vote")
public class IpavvoteController {
	
	@Autowired
	private IpavuserService userService;
	
	@Autowired
	private IpavvoteService voteService;
	
	/**
	 * 组织结构
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initLevelInfos", method = RequestMethod.POST,produces="application/json")
	public JSONObject initAllLevelInfos(HttpServletRequest request,
			HttpSession session){
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		JSONObject result = new JSONObject();
		JSONArray arr=null;
		if(user == null)
			result.put("error", "用户已失效,请重新登陆");
		else{
			List<JSONObject> list=this.userService.getAllLevelInfos(user.getCompanyid());
			if(list!=null){
				arr=(JSONArray)JSON.toJSON(list);
				result.put("info", arr);
			}
		}
		return result;
	}
	
	/**
	 * 删除投票
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteVote", method = RequestMethod.POST)
	public JSONObject updateVoteDel(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		if(user==null){
			result.put("error", "用户已失效,请重新登陆");
			return result;
		}
		Map<String, Object> param = RequestParamToMap.convert(request);
//		param.put("companyId", user.getCompanyid());
		param.put("userId", user.getUserid());
		int count=0;
		if(param.get("ids").getClass().getSimpleName().equals("String")){
			String[] ids=new String[]{param.get("ids").toString()};
			param.put("ids", ids);
			count=1;
		}else{
			count=((String[])param.get("ids")).length;
		}
		if(voteService.updateVoteDel(param)!=count)
			result.put("error", "您只能删除自己发起的且没被投票的活动");
		return result;
	}
	
	/**
	 * 发布投票
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/publishVote", method = RequestMethod.POST)
	public JSONObject publishVote(HttpServletRequest request,HttpSession session){
		String[] picName=null;
		Entry<String,Integer> entry=null;
		Map<String,Object> tmp=null;
		JSONObject result=new JSONObject();
		result.put("result", 0);
		List<Map<String,Object>> userAuthorList=new ArrayList<Map<String,Object>>();
		Map<String,Integer> authorTmp=new HashMap<String, Integer>();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		if(user==null){
			result.put("error", "用户已失效,请重新登陆");
			return result;
		}
		String userId=user.getUserid();
		Map<String, Object> param = RequestParamToMap.convert(request);
		
		if(param.containsKey("id")){
			if(this.voteService.voteHasCasted(Integer.parseInt(param.get("id").toString()))){
				result.put("error", "该投票活动已有人投票,不能修改");
				return result;
			}
		}
		param.put("companyId", user.getCompanyid());
		//投票以及报表查看权限
		String[] joinPeople=param.get("joinPeople").toString().split(";");
		for(String str:joinPeople)
			authorTmp.put(str, 1);//参与投票权限,2^0
		param.remove("joinPeople");
		String[] viewPeople=null;
		if(param.containsKey("viewPeople")){
			viewPeople=param.get("viewPeople").toString().split(";");
			param.remove("viewPeople");
		}
		param.put("initiator", userId);
		if(viewPeople!=null)
			for(String str:viewPeople){
				if(authorTmp.containsKey(str))
					authorTmp.put(str, 3);//既能参与投票也能看投票报表,2^0+2^1
				else
					authorTmp.put(str, 2);//查看投票报表权限,2^1
			}
		Iterator<Entry<String, Integer>> iter=authorTmp.entrySet().iterator();
		while(iter.hasNext()){
			entry=iter.next();
			tmp=new HashMap<String, Object>();
			tmp.put("userId", entry.getKey());
			tmp.put("author", entry.getValue());
			userAuthorList.add(tmp);
		}
		//图片保存
		String[] itemName=null;
		if(param.get("itemName") instanceof String[]){
			itemName=(String[])param.get("itemName");
		}else{
			itemName=param.get("itemName").toString().split(";");
		}
		param.remove("itemName");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> fileList = multipartRequest.getFiles("pic");
		picName=new String[itemName.length];
		Set<String> oldPicSet=new HashSet<String>();
		if(!param.get("type").toString().equals("3")){
			String picarr=param.get("picarr").toString();
			String[] oldPicArr=null;
			if(param.containsKey("oldPicArr"))
				oldPicArr=param.get("oldPicArr").toString().split(";");
			param.remove("picarr");
			param.remove("oldPicArr");
			long dirPicName=0;
			int j=0;
			String type="";
			FTPUtil ftpUtil=new FTPUtil();
			for(int i=0;i<itemName.length;i++){
				if('0'==picarr.charAt(i)){
					if(oldPicArr!=null&&oldPicArr.length>1){
						if("0".equals(oldPicArr[i]))
							picName[i]="";
						else {
							picName[i]=oldPicArr[i];
							oldPicSet.add(oldPicArr[i]);
						}
					}else
						picName[i]="";
				}else if('1'==picarr.charAt(i)){
					dirPicName=Calendar.getInstance().getTimeInMillis();
					try {
						type=fileList.get(j).getOriginalFilename().substring(fileList.get(j).getOriginalFilename().lastIndexOf("."));
						ftpUtil.upload(fileList.get(j).getInputStream(),ContentUtil.IMAGEPATHS.get("vote"), dirPicName+type);
						picName[i]=dirPicName+type;
						++j;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						picName[i]="";
						e.printStackTrace();
					}
				}
			}
		}
		//选项初始化
		List<Map<String,Object>> voteItems=new ArrayList<Map<String,Object>>();
		Map<String,Object> item=null;
		for(int i=0;i<itemName.length;i++){
			item=new HashMap<String, Object>();
			item.put("itemName", itemName[i]);
			item.put("pic", "".equals(picName[i])?null: picName[i]);
			voteItems.add(item);
		}
		int oldId=0;
		if(param.containsKey("id")){
			oldId=Integer.parseInt(param.get("id").toString());
			param.remove("id");
		}
		int resultValue=new VoteAction().publishVote(param, voteItems, userAuthorList, voteService, userId,oldId,oldPicSet);
		result.put("result", resultValue);
		return result;
	}
	
	/**
	 * 获取投票信息
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getVoteInfo", method = RequestMethod.POST,produces="application/json")
	public JSONObject getVoteInfo(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		if(user==null){
			result.put("error", "用户已失效,请重新登陆");
			return result;
		}
		Map<String, Object> param = RequestParamToMap.convert(request);
		param.put("userId", user.getUserid());
		param.put("companyId", user.getCompanyid());
		int type=Integer.parseInt(param.get("optType").toString());
		param.remove("optType");
		int count=0;
		if(type==0){
			count=voteService.getVoteInfoCounts(param);
			result.put("count", count);
		}
		List<Map<String,Object>> infos=null;
		if(type!=0||count>0)
			infos=new VoteAction().getVoteInfos(voteService, param, type);
		if(infos!=null){
			result.put("picpath", ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("vote"));
			result.put("infos", infos);
		}
		result.put("userpath", ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user"));
		return result;
	}
	
	/**
	 * 获取投票报表
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getVoteStatistic", method = RequestMethod.POST,produces="application/json")
	public JSONObject getVoteStatistic(HttpServletRequest request,HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		if(user==null){
			result.put("error", "用户已失效,请重新登陆");
			return result;
		}
		List<Map<String,Object>> list=null;
		Map<String, Object> param = RequestParamToMap.convert(request);
		param.put("userId", user.getUserid());
		param.put("companyId", user.getCompanyid());
		String[] voteIds=param.get("voteId").toString().split(",");
		param.put("voteIds", voteIds);
		int count=this.voteService.getVoteStatisticCounts(param);
		if(count>0)
			list=this.voteService.getVoteStatistic(param);
		result.put("counts", count);
		result.put("infos", list);
		return result;
	}
	
	/**
	 * 报表导出
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/exportVote")
	public ModelAndView exportVoteRecords(ModelMap model,
			HttpServletRequest request, HttpSession session) {
		
		JSONObject result=new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		if(user==null){
			result.put("error", "用户已失效,请重新登陆");
			return null;
		}
		Map<String, Object> param = RequestParamToMap.convert(request);
		param.put("userId", user.getUserid());
		param.put("companyId", user.getCompanyid());
		String[] voteIds=param.get("voteId").toString().split(",");
		param.put("voteIds", voteIds);
		List<Map<String,Object>> list=this.voteService.getVoteStatistic(param);
		
		List<String> title = new ArrayList<String>();
		title.add("投票发起时间");
		title.add("投票名称");
		title.add("选项名称");
		title.add("名次");
		title.add("得票/分");
		title.add("选项已投的人");
		title.add("入选否");
		title.add("应投票人数");
		title.add("已投票人数");
		title.add("未投票的人");
		List<List> content = null;
		if(list!=null&&list.size()>0){
			List row = null;
			content = new ArrayList<List>();
			int len=list.size();
			Map<String,Object> statistic=null;
			for (int i = 0; i < len; i++) {
				statistic=list.get(i);
				row=new ArrayList();
				row.add(statistic.get("start_time").toString());
				row.add(statistic.get("title").toString());
				row.add(statistic.get("item_name").toString());
				row.add(statistic.get("seq").toString());
				row.add(statistic.get("score").toString().indexOf(".")==-1?Integer.parseInt(statistic.get("score").toString()):Float.parseFloat(statistic.get("score").toString()));
				row.add(statistic.get("finished").toString());
				row.add(statistic.get("is_chosen").toString());
				row.add(statistic.get("counts").toString());
				row.add(Integer.parseInt(statistic.get("counts").toString())-Integer.parseInt(statistic.get("unfinished_counts").toString()));
				row.add(statistic.get("unfinished").toString());
				content.add(row);
			}
		}
		model.put("title", title);
		model.put("content", content);
		return new ModelAndView(new ExcelView("投票报表","投票报表"), model);
	}
	
	/**
	 * 投票
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/castVote", method = RequestMethod.POST,produces="application/json")
	public JSONObject cast(HttpServletRequest request, HttpSession session){
		JSONObject result=new JSONObject();
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		if(user==null){
			result.put("error", "用户已失效,请重新登陆");
			return result;
		}
		Map<String, Object> param = RequestParamToMap.convert(request);
		param.put("userId", user.getUserid());
		JSONArray arr=JSONArray.parseArray(param.get("id_score").toString());
		List<Map> list=JSON.parseArray(param.get("id_score").toString(), Map.class);
		param.remove("id_score");
		param.put("counts", list.size());
		param.put("list", list);
		if(this.voteService.insertVoteRecord(param)==0)
			result.put("error", "投票失败");
		return result;
	}
	
	/**
	 * 投票操作权限控制
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateAuthor", method = RequestMethod.POST,produces="application/json")
	public JSONObject validateAuthor(HttpServletRequest request, HttpSession session){
		JSONObject result=new JSONObject();
		int success=0;
		IpavuserEntity user = (IpavuserEntity) session.getAttribute("curuser");
		if(user==null){
			result.put("error", "用户已失效,请重新登陆");
			return result;
		}
		Map<String, Object> param = RequestParamToMap.convert(request);
		int optType=Integer.parseInt(param.get("type").toString());
		param.remove("type");
		if(optType==1){//取回投票
			param.put("userId", user.getUserid());
			if(voteService.getModifyAuthor(param))
				success=1;
		}
		else if(optType==2){//投票
			param.put("voteId", param.get("id"));
			param.put("userId", user.getUserid());
			param.remove("id");
			List<Map<String,Object>> list=this.voteService.getAuthorList(param);
			if(list!=null&&list.size()==1){
				int author=Integer.parseInt(list.get(0).get("author").toString());
				if(author==1||author==3)
					success=1;
			}
		}else if(optType==3){//查看结果
			param.put("voteId", param.get("id"));
			param.put("userId", user.getUserid());
			param.remove("id");
			List<Map<String,Object>> list=this.voteService.getAuthorList(param);
			if(list!=null&&list.size()==1){
				if(list.get(0).get("initiator").toString().equals(user.getUserid()))
					success=1;
				else if(list.get(0).get("result_type").toString().equals("0"))
					success=0;
				else if(list.get(0).get("result_type").toString().equals("1")&&list.get(0).get("status").toString().equals("3"))
					success=1;
				else if(list.get(0).get("result_type").toString().equals("2"))
				success=1;
			}
		}else if(optType==4){//查看报表
			List<Map<String,Object>> list=null;
			String ids=param.get("id").toString();
			if(ids.indexOf(",")==-1){
				list=this.voteService.getVoteInfoList(param);
				if(list!=null&&list.size()==1){
					if(list.get(0).get("initiator").toString().equals(user.getUserid()))
						success=1;
				}else if(success==0){
					param.put("voteId", param.get("id"));
					param.put("userId", user.getUserid());
					param.remove("id");
					list=this.voteService.getAuthorList(param);
					if(list!=null&&list.size()==1){
						int author=Integer.parseInt(list.get(0).get("author").toString());
						if(author==2||author==3)
							success=1;
					}
				}
			}else{
				String[] idArr=ids.split(",");
				List<String> successList=new ArrayList<String>();
				//List<String> faildList=new ArrayList<String>();
				String faildInfo="";
				int idx=1;
			
				for(String str:idArr){
					success=0;
					param.put("id", str);
					list=this.voteService.getVoteInfoList(param);
					if(list!=null&&list.size()==1){
						if(list.get(0).get("initiator").toString().equals(user.getUserid()))
							success=1;
					}else if(success==0){
						param.put("voteId", param.get("id"));
						param.put("userId", user.getUserid());
						param.remove("id");
						list=this.voteService.getAuthorList(param);
						if(list!=null&&list.size()==1){
							int author=Integer.parseInt(list.get(0).get("author").toString());
							if(author==2||author==3)
								success=1;
						}
					}
					if(success==1)
						successList.add(str);
					else
						faildInfo+=idx+",";
					idx++;
				}
				success=faildInfo.equals("")?1:0;
				result.put("successList", successList);
				faildInfo=faildInfo.equals("")?faildInfo:(faildInfo.substring(0, faildInfo.length()-1));
				result.put("faildInfo", faildInfo);
			}
		}
		result.put("result", success);
		return result;
	}
}

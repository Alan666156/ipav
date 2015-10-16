package com.ipav.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.entity.IpavmenuEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavmenuService;
import com.ipav.system.service.IpavroleService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.ImageUtil;
import com.ipav.system.util.RequestParamToMap;
import com.ipav.system.util.ftp.FTPUtil;

/**
 * creater Jerry
 * All  right  reserved.
 * Created on 2014年11月12日 下午12:17:17	
 * 上海天道启科电子有限公司
 */
@Controller
public class IpavmenuController {
	@Autowired
	private IpavmenuService menuService;
	@Autowired
	private IpavroleService roleService;
	/****
	 * 获取菜单列表信息
	 * @param levl
	 * @param parentid
	 * @param status
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/menulist")
	public String getMenuList(
			@RequestParam(value="levl",defaultValue="0",required=true)String levl,
			String parentid,String status,ModelMap map){
		Map parm =new HashMap();
		parm.put("levl", levl);
		String path ="";
		if(ContentUtil.SYS_MENU_LEVL.equals(levl)){
			path = "system/menu/sysmenulist";
		}else{
			path = "system/menu/menulist";
			parm.put("parentid", parentid);
			if(StringUtils.isNotEmpty(status)){
				parm.put("valflg", status);
				map.put("status", status);
			}
			List list= menuService.getMenuList(parm);
			map.put("list", list);
		} 
//		map.put("filepath", ContentUtil.PICPATH+File.separator);
//		map.put("filepath", ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("system"));
		return path;
	}
	/****
	 * ajaxs方式查询菜单列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryMenus")
	@ResponseBody
	public JSONObject queryMenus(HttpServletRequest request){
		JSONObject resultbody=new JSONObject();
		Map<String,Object> param=RequestParamToMap.convert(request);
//		Map parm =new HashMap();
//		parm.put("levl", "0");
//		parm.put("valflg", status);
		resultbody.put("size", menuService.getMenuListSize(param));
//		parm.put("beginRow",beginRow==null?0:beginRow);
//		parm.put("pageSize",pageSize==null?10:pageSize);
		List<IpavmenuEntity> menulist=menuService.getMenuList(param);
		if(menulist!=null&&menulist.size()!=0){
			for(IpavmenuEntity menu : menulist){
				menu.setImgsrc(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("system")+menu.getImgsrc());
			}
		}
		resultbody.put("list",menulist);
		return resultbody;
	}
	/****
	 * 跳转到菜单编辑页面
	 * @param levl
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/menuview")
	public String gotoEditView(@RequestParam(value="levl",defaultValue="0",required=true)String levl,ModelMap map){
		map.put("levl", levl);
		String path ="";
		if(ContentUtil.SYS_MENU_LEVL.equals(levl)){
			path = "system/menu/addsysmenu";
		}else{
			Map parm =new HashMap();
			List list= menuService.getMenuList(parm);
			map.put("menus", list);
			path = "system/menu/addsonmenu";
		} 
		return path;
	}
	/***
	 * 新增菜单
	 * @param entity
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/menuadd")
	public String addMenu(IpavmenuEntity entity,ModelMap model){
		menuService.addMenu(entity);
		model.put("levl", entity.getLevl());
		return "forward:/menulist" ;
	}
	
//	@RequestMapping(value="/menuadd")
//	public String addMenu(HttpServletRequest request,IpavmenuEntity entity,ModelMap model) throws IOException{
//		long timestamp=Calendar.getInstance().getTimeInMillis();
//		File tmp=new File(ContentUtil.IMAGEPATHS.get("system"),timestamp+".png");
//		MultipartHttpServletRequest fileReq=(MultipartHttpServletRequest)request;
//		MultipartFile file=fileReq.getFile("pic");
//		entity.setImgsrc(ImageUtil.saveImage(file, ContentUtil.IMAGEPATHS.get("system")));
//		long size=0;
//		if(file!=null)
//			size=file.getSize();
//		if(size>0){
//			try {
//				FileUtils.copyInputStreamToFile(file.getInputStream(), tmp);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			entity.setImgsrc(timestamp+".png");
//		}
//		menuService.addMenu(entity);
//		int result=menuService.addMenu(entity);
//		if(result<=0){
//			if(tmp.exists())
//				tmp.delete();
//		}
//		model.put("levl", entity.getLevl());
//		return "forward:/menulist" ;
//	}
	/***
	 * 新增菜单
	 * @param pic
	 * @param menu
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/addMenu")
	public String insertMenu(@RequestParam(value = "pic", required = false) MultipartFile pic,IpavmenuEntity menu) throws IOException{
		if(menu!=null){
			menu.setImgsrc(ImageUtil.saveImage(pic, ContentUtil.IMAGEPATHS.get("system")));
			menuService.addMenu(menu);
		}
		return "forward:/menulist" ;
	}
	
	/***
	 * 跳转到菜单修改页面
	 * @param menuid
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/modifyview")
	public String gotoupdateMenu(String menuid,ModelMap map){
		IpavmenuEntity menu = menuService.getMenuById(menuid);
		map.put("menu", menu);
		String path ="";
		if(ContentUtil.SYS_MENU_LEVL.equals(menu.getLevl()+"")){
			path = "system/menu/updatesysmenu";
		}else{
			Map parm =new HashMap(); 
			parm.put("menuid", menu.getMenuid());
			List list= menuService.getMenuExcistList(parm);
			map.put("menus", list);
			path = "system/menu/updatesonmenu";
		} 
		
		return path;
	}
	/***
	 * 修改菜单
	 * @param menu
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/updatemenu")
	public String updateMenu(IpavmenuEntity menu,ModelMap map){
		menuService.updateMenu(menu);
		map.put("levl", menu.getLevl());
		return "forward:/menulist" ;
	}
	
	/***
	 * ajax方式 弹出菜单修改信息
	 * @param menuid
	 * @return
	 */
	@RequestMapping(value="/gotoUpdateMenu")
	@ResponseBody
	public String gotoUpdateMenu(String menuid){
		JSONObject obj=new JSONObject();
		IpavmenuEntity menu=menuService.getMenuById(menuid);
		if(menu!=null)	menu.setImgsrc(ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("system")+menu.getImgsrc());
		obj.put("menu",  menu);
		return JSON.toJSONString(obj);
	}
	/***
	 * 修改菜单（新）
	 * @param pic
	 * @param menu
	 * @param oldPath
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/editMenu")
	public String editMenu(@RequestParam(value = "pic", required = false) MultipartFile pic,IpavmenuEntity menu,String oldPath) throws IOException{
		if(menu!=null){
			String imgpath=ImageUtil.saveImage(pic, ContentUtil.IMAGEPATHS.get("system"));
			menu.setImgsrc("".equals(imgpath)?oldPath:imgpath);
			menuService.updateMenu(menu);
		}
		return "forward:/menulist" ;
	}
	
	/***
	 * 0:更新应用菜单禁用状态;1:更新应用菜单启用状态;2:删除应用菜单;
	 * @param menuid
	 * @param valflg
	 * @param levl
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/updatevalflg")
	public String updateMenuValflg(String menuid,String valflg,String levl,ModelMap map){
		Map parm = new HashMap();
		parm.put("menuid", menuid);
		parm.put("valflg", valflg);
		menuService.updateMenuValflg(parm);
		map.put("levl", levl);
		return "forward:/menulist" ;
	}
	/**
	 * @author GaoYang 
	 * @param actiontype 0:更新应用菜单禁用状态;1:更新应用菜单启用状态;2:删除应用菜单;
	 * @param ids
	 * @param valflgs
	 * @return
	 */
	@RequestMapping(value="/updateMenuState")
	@ResponseBody
	public String updateMenuValflg(String actiontype,
			@RequestParam(value = "ids[]", required = false) String[] ids){
		JSONObject rsobj=new JSONObject();
	    String messageStr="success";
	    if (actiontype!=null&&ids!=null) {
			for(int i=0;i<ids.length;i++){
				Map<String,Object> parm = new HashMap<String,Object>();
				if("2".equals(actiontype)){
					menuService.delMenu(ids[i]);
				}else{
					parm.put("menuid", ids[i]);
					parm.put("valflg", actiontype);
					menuService.updateMenuValflg(parm);
				}
			}
		}else{
			   messageStr="error";
		}
	    rsobj.put("message",messageStr);
	    return JSON.toJSONString(rsobj);
	}
	/***
	 * 删除菜单
	 * @param menuid
	 * @param levl
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/delMenu")
	public String delMenu(String menuid,String levl,ModelMap map){
		menuService.delMenu(menuid);
		map.put("levl", levl);
		return "forward:/menulist" ;
	}
	/***
	 * 获取菜单树
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getMenutree")
	@ResponseBody
	public JSONArray getMenuTrees(HttpSession session){
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map map = new HashMap();
		map.put("username", user.getUserid());
		List menulist= menuService.getMenuTrees(map);
		JSONArray arr=(JSONArray)JSONArray.toJSON(menulist);
		System.out.println(arr.toJSONString());
		return arr;
	}	
	/***
	 * 检测菜单可以状态
	 * @param menuid
	 * @return
	 */
	@RequestMapping(value="/checkvalflg")
	@ResponseBody
	public boolean checkValflg(String menuid){
		int count=menuService.checkValflg(menuid);
		if(count>0){
			return false;
		}else{
			return true;
		}
	}
	/***
	 * 判断是否有系统管理员角色
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/havsysroleflg")
	@ResponseBody
	public boolean havSysRoleFlg(HttpSession session){
		IpavuserEntity user= (IpavuserEntity)session.getAttribute("curuser");
		Map map = new HashMap();
		map.put("userId", user.getUserid());
		map.put("menuId", "109");
		List<Map<String,Object>> list =roleService.getOptRole(map);
		if(list==null||list.size()==0){
			return false;
		}else{
			return true;
		}
	}
}

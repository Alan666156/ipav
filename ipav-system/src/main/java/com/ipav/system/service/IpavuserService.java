package com.ipav.system.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.ipav.system.dao.CompanyMapper;
import com.ipav.system.dao.ImageMapper;
import com.ipav.system.dao.LabourCompanyMapper;
import com.ipav.system.dao.OrgMapper;
import com.ipav.system.dao.RoleMapper;
import com.ipav.system.dao.UserMapper;
import com.ipav.system.entity.IpavcompanyEntity;
import com.ipav.system.entity.IpavimageEntity;
import com.ipav.system.entity.IpavorgEntity;
import com.ipav.system.entity.IpavroleEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;
import com.ipav.system.util.ImageUtil;
import com.ipav.system.util.MessageUtil;
import com.ipav.system.util.PwdUtil;
import com.ipav.system.util.ftp.FTPUtil;

/**
 * creater Jerry All right reserved. Created on 2014年11月15日 下午4:24:03
 * 上海天道启科电子有限公司
 */
@Service
@Transactional
public class IpavuserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private MessageUtil messageUtil;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private LabourCompanyMapper LabourCompanyMapper;
	@Autowired
	private ImageMapper imageMapper;
	@Autowired
	private IpavdutyService dutyService;
	/**
	 * 查询用户列表
	 * @param map
	 * @return
	 */
	public List getUserList(Map map ){
		return userMapper.getUserList(map);
	}
	/***
	 * 查询用户数量
	 * @param map
	 * @return
	 */
	public int getUserListCount(Map map){
		return userMapper.getUserListCount(map);
	} 
	
	/**
	 * 查询用户列表
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryUserList(Map<String,Object> param){
		return this.userMapper.queryUserList(param);
	}
	
	/***
	 * 查询用户数量
	 * @param map
	 * @return
	 */
	public int queryUserCount(Map<String,Object> param){
		return this.userMapper.queryUserCount(param);
	}
	
	/**
	 * 获取可用编号
	 * @param map
	 * @return
	 */
	public String getMaxUserNo(Map map){
		String userno= userMapper.getMaxUserNo(map);
		int maxno= Integer.parseInt(userno)+1;
		String rtno = FormatUtil.formatNumber(maxno, null);
		return rtno;
	}
	
	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param session
	 */
	public boolean loginUser(IpavuserEntity user, HttpSession session) {
		Map map = new HashMap();
		map.put("userid", user.getUserid());
		map.put("password", PwdUtil.MD5(user.getPassword().toUpperCase()));
		map.put("regtype", user.getRegtype());
		map.put("mobile", user.getMobile());
		map.put("email", user.getEmail());
		map.put("valflg", ContentUtil.DEFUAL_VALDLF_YES);
		IpavuserEntity temp = userMapper.getUserByUniKey(map);
		if (temp != null) {
			session.setAttribute("curuser", temp);
			return true;
		} else {
			return false;
		}
	}
	/***
	 * 查询用户by  ID
	 * 
	 * @param user
	 * @return
	 */
	public IpavuserEntity queryUserId(String userid) {
		Map map = new HashMap();
		map.put("userid",userid);
		IpavuserEntity temp = userMapper.getUserByUniKey(map);
		return temp;
	}
	/***
	 * 查询用户
	 * 
	 * @param user
	 * @return
	 */
	public IpavuserEntity queryUser(IpavuserEntity user) {
		Map map = new HashMap();
		map.put("userid", user.getUserid());
		map.put("regtype", user.getRegtype());
		map.put("mobile", user.getMobile());
		map.put("email", user.getEmail());
		map.put("valflg", ContentUtil.DEFUAL_VALDLF_YES);
		IpavuserEntity temp = userMapper.getUserByUniKey(map);
		return temp;
	}
	
	public IpavuserEntity userLogin(IpavuserEntity user){
		Map map = new HashMap();
		map.put("userid", user.getUserid());
		map.put("password", user.getPassword());
		map.put("regtype", user.getRegtype());
		map.put("mobile", user.getMobile());
		map.put("email", user.getEmail());
		map.put("valflg", ContentUtil.DEFUAL_VALDLF_YES);
		IpavuserEntity temp = userMapper.getUserByUniKey(map);
		return temp;
	}

	/**
	 * 更新用户密码
	 * 
	 * @param user
	 */
	public void updateUserPwd(IpavuserEntity user) {
		String pwd = PwdUtil.MD5(user.getPassword().toUpperCase());
		user.setPassword(pwd);
		userMapper.updateUserPwd(user);
	}
	
	/**
	 * 更新用户密码
	 * 
	 * @param user
	 */
	public int updatePwd(Map<String,Object> map) {
		map.put("newPwd", map.get("newPwd").toString());
		if(map.containsKey("oldPwd"))
			map.put("oldPwd", map.get("oldPwd").toString());
		return userMapper.updatePwd(map);
	}
	
	/**
	 * 校验是否存在用户
	 * 
	 * @return
	 */
	public boolean checkExistUser(IpavuserEntity user) {
		Map map = new HashMap();
		map.put("regtype", user.getRegtype());
		map.put("userid", user.getUserid());
		map.put("mobile", user.getMobile());
		map.put("email", user.getEmail());
		map.put("valflg", ContentUtil.DEFUAL_VALDLF_YES);
		IpavuserEntity temp = userMapper.getUserByUniKey(map);
		if (temp != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断用户是否存在
	 * @param user
	 * @return
	 */
	public boolean isExist(IpavuserEntity user){
		int count=0;
		if (ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())) {
			Map map = new HashMap();
			map.put("mobile", user.getMobile());
			map.put("regtype", user.getRegtype());
			count = userMapper.getUserListCount(map);
		} else  {
			Map map = new HashMap();
			map.put("email", user.getEmail());
			map.put("regtype", user.getRegtype());
			count = userMapper.getUserListCount(map);
		}
		return count>0?true:false;
	}
	
	/**
	 * 邀请时候---注册
	 * @param user
	 * @throws Exception
	 */
	public void regist(Map map) throws Exception {
		IpavuserEntity user=(IpavuserEntity)map.get("user");
		String pwd = PwdUtil.createPwd(); 
		user=PublicRegist(user,pwd); 

		if (ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())) {
			//messageUtil.sendInvitationOkMessage((String)map.get("iname"), user, pwd);
			// messageUtil.sendReistOkMessage(user, pwd);
		}
		if (ContentUtil.REGIST_TYPE_EMIAL.equals(user.getRegtype())) {
			//messageUtil.sendRegistOkMail(user, pwd);
			//messageUtil.sendInvitationOkMail((String)map.get("iname"),user, pwd);
		} 
	}
	
	
	/**
	 * 注册
	 * @param user
	 * @throws Exception
	 */
	public void regist(IpavuserEntity user) throws Exception {		 
		String pwd = PwdUtil.createPwd();
		PublicRegist(user,pwd);
		if (ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())) {
			 messageUtil.sendReistOkMessage(user, pwd);
		}
		if (ContentUtil.REGIST_TYPE_EMIAL.equals(user.getRegtype())) {
			messageUtil.sendRegistOkMail(user, pwd);
		}
		 
	}
	
	private IpavuserEntity PublicRegist(IpavuserEntity user,String pwd){
		
		String tm = FormatUtil.formatDate(new Date(), null);

		IpavcompanyEntity company = new IpavcompanyEntity();
		company.setMobile(user.getMobile());
		company.setEmail(user.getEmail());
		company.setCompanyname(ContentUtil.DEFUAL_COMPANYNAME);
		company.setCreatedate(tm);
		company.setCreater(ContentUtil.SYSTEM_NAME);
		companyMapper.insertCompany(company);
		
		//系统自动添加公司默认LOGO
		IpavimageEntity image = new IpavimageEntity();
		image.setCompanyid(company.getCompanyid());
		image.setBustype("LOGO");
		image.setCreater("system");
		image.setSorcepath(ContentUtil.DEFUAL_LOGO_PIC);
		image.setSubpath(ContentUtil.DEFUAL_SUB_LOGO_PIC);
		image.setCreatdate(FormatUtil.formatDate(new Date(), null));
		imageMapper.addCompanyImage(image);
		
		//系统自动生成初始机构
		IpavorgEntity org = new IpavorgEntity();
		org.setCompanyid(company.getCompanyid());
		org.setOrgno(ContentUtil.DEFUAL_ORGNO);
		org.setOrgname(ContentUtil.DEFUAL_ORGNAME);
		org.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
		org.setCreatedate(tm);
		org.setCreater(ContentUtil.SYSTEM_NAME);
		org.setSeqno("1");
		
		orgMapper.addOrg(org);
		
		
		IpavorgEntity orgocmpany = new IpavorgEntity();
		orgocmpany.setCompanyid(company.getCompanyid());
		orgocmpany.setOrgno(ContentUtil.DEFUAL_ORGNOCOMPANY);
		orgocmpany.setParentno(ContentUtil.DEFUAL_ORGNO);
		orgocmpany.setOrgname(ContentUtil.DEFUAL_COMPAYORGNAME);
		orgocmpany.setOrgfullname(ContentUtil.DEFUAL_COMPAYORGNAME);
		orgocmpany.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
		orgocmpany.setCreatedate(tm);
		orgocmpany.setCreater(ContentUtil.SYSTEM_NAME);
		orgocmpany.setSeqno(ContentUtil.DEFUAL_ORGNOCOMPANY);
		orgMapper.addOrg(orgocmpany);
		
		//自动生成用户
		user.setUserid(company.getCompanyid()
				+ ContentUtil.DEFUAL_RGSNAME_SUFFIX);
		user.setCompanyid(company.getCompanyid());
		user.setUserno(ContentUtil.DEFUAL_USERNO);
		user.setUsername(ContentUtil.DEFAULT_USER_NAME);
		user.setOrgid(orgocmpany.orgid);
		user.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
		user.setPassword(PwdUtil.MD5(pwd));
		user.setCreatedate(tm);
		user.setCreater(ContentUtil.SYSTEM_NAME);
		
		//添加默认机构id
		List<Map<String,Object>> userDutyList=new ArrayList<Map<String,Object>>();
		Map<String,Object> tmp=new HashMap<String, Object>();	
		tmp.put("orgId", orgocmpany.orgid);
		tmp.put("dutyId", "-1");
		tmp.put("isLeader",0);
		tmp.put("type", 0);
		userDutyList.add(tmp);
		Map<String,Object> userDutyParam=new HashMap<String, Object>();		 
		userDutyParam.put("updateUser", user.getUserid());
		userDutyParam.put("status", 1);
		userDutyParam.put("userId", user.getUserid());
		userDutyParam.put("list", userDutyList);
		userMapper.addUserDutyInfos(userDutyParam);
		
		user.setSex(1+"");
		//添加默认头像
		user.setPicpath("man.png");
		userMapper.insertUser(user);
		
		
		//新增默认角色
		IpavroleEntity role = new IpavroleEntity();
		role.setCompanyid(company.getCompanyid());
		role.setRolename(ContentUtil.SYS_ROLENAME);
		role.setCreater(ContentUtil.SYSTEM_NAME);
		role.setCreatedate(tm);
		role.setSysroleflg(ContentUtil.SYS_ROLEFLG);
		role.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
		roleMapper.addRole(role);
		
		Map parm = new HashMap();
		parm.put("roleno", role.getRoleno()) ;
		parm.put("userid",user.getUserid()) ;
		//用户和角色关系
		userMapper.addUserRole(parm);
		
		//角色菜单关系
		parm.clear();
		parm.put("roleno", role.getRoleno()) ;
		
		//系统管理员有所有菜单权限
		List allmenus= userMapper.queryAllMenuList();
		
		int count =allmenus.size();//ContentUtil.INIT_ROLEMENU.length;
		for(int i=0;i<count;i++){
			parm.put("menuid",allmenus.get(i)) ;
			roleMapper.addRoleMenu(parm);
		}
		return user;
	}
	

	
	
	/**
	 * 注册用户
	 * 
	 * @param user
	 */
	@Transactional
	public void registerUser(IpavuserEntity user) throws Exception {
		boolean flag=isExist(user);
		if(flag)
			throw new Exception(ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())?"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！":"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
		 
		regist(user);
	}
	/**
	 * 添加用户
	 * @param user
	 * @throws Exception
	 */
	@Transactional
	public void addUser(IpavuserEntity user) throws Exception{
		 String olpwd= PwdUtil.createPwd();
			boolean flag=isExist(user);
			if(flag)
				throw new Exception(ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())?"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！":"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
			user.setUserid(user.getCompanyid()+user.getUserno());
			user.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
			user.setPassword(PwdUtil.MD5(olpwd));
			String tm = FormatUtil.formatDate(new Date(), null);
			user.setCreatedate(tm);
			user.setCreater(ContentUtil.SYSTEM_NAME);
			if(user.getPicpath().equals("")){
				//添加默认头像
				if(user.getSex().equals("0"))
					user.setPicpath("girl.png");
				else
			     	user.setPicpath("man.png");
			}
			userMapper.insertUser(user);
		if (ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())) {
			 messageUtil.sendReistOkMessage(user, olpwd);
		}
		if (ContentUtil.REGIST_TYPE_EMIAL.equals(user.getRegtype())) {
			messageUtil.sendRegistOkMail(user, olpwd);
		}
	}
	
	/**
	 * 邀请同事（同事圈）
	 * @param user
	 * @param olpwd
	 * @throws Exception
	 */
	public void addUser(Map map) throws Exception{
		 IpavuserEntity user=(IpavuserEntity) map.get("user");
		 String olpwd= PwdUtil.createPwd();
			boolean flag=isExist(user);
			if(flag)
				throw new Exception(ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())?"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！":"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
			Map<String, Object> tmpParam = new HashMap();
			tmpParam.put("companyid", user.getCompanyid());			 
			String userno= userMapper.getMaxUserNo(tmpParam);
			int maxno= Integer.parseInt(userno)+1;
			String rtno = FormatUtil.formatNumber(maxno, null);
			user.setUserno(rtno);
			user.setUserid(user.getCompanyid()+rtno);
			user.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
			user.setPassword(PwdUtil.MD5(olpwd));
			String tm = FormatUtil.formatDate(new Date(), null);
			user.setCreatedate(tm);
			user.setCreater(ContentUtil.SYSTEM_NAME);
			if(user.getPicpath().equals("")){
				//添加默认头像
				if(user.getSex().equals("0"))
					user.setPicpath("girl.png");
				else
			     	user.setPicpath("man.png");
			} 	
			IpavorgEntity org= orgMapper.queryOrgnoTo0Bycompanyid(user.getCompanyid()+"");
			user.setOrgid(org.getOrgid());
			user.setDuty(-1);
			userMapper.insertUser(user);	
			
			//添加默认机构--
			List<Map<String,Object>> userDutyList=new ArrayList<Map<String,Object>>();
			Map<String,Object> tmp=new HashMap<String, Object>();	
			tmp.put("orgId",org.getOrgid() );
			tmp.put("dutyId", "-1");
			tmp.put("isLeader",0);
			tmp.put("type", 0);
			userDutyList.add(tmp);
			Map<String,Object> userDutyParam=new HashMap<String, Object>();		 
			userDutyParam.put("updateUser", user.getUserid());
			userDutyParam.put("status", 1);
			userDutyParam.put("userId", user.getUserid());
			userDutyParam.put("list", userDutyList);
			userMapper.addUserDutyInfos(userDutyParam);
			//-----	
			 IpavcompanyEntity company =companyMapper.queryCompanyByid(user.getCompanyid()+""); 
			if (ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())) {
				messageUtil.sendInvitationOkMessage((String)map.get("iname"),user, olpwd,company!=null?company.getCompanyname():"");
			}
			if (ContentUtil.REGIST_TYPE_EMIAL.equals(user.getRegtype())) {
				messageUtil.sendInvitationOkMail((String)map.get("iname"),user, olpwd,company!=null?company.getCompanyname():"");
			} 
	} 
//	/**
//	 * 修改用户
//	 * @param user
//	 */
//	@Transactional
//	public void updateUser(IpavuserEntity user)throws Exception{
//		IpavuserEntity temp= queryUserId(user.getUserid());
//		if(temp.getRegtype().equals(user.getRegtype())){
//			if(ContentUtil.REGIST_TYPE_EMIAL.equals(temp.getRegtype())){
//				if(!temp.getEmail().equals(user.getEmail())){
//					boolean flag=isExist(user);
//					if(flag)
//						throw new Exception( "亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
//				}
//			}else if(ContentUtil.REGIST_TYPE_MOBILE.equals(temp.getRegtype())){
//				if(!temp.getMobile().equals(user.getMobile())){
//					boolean flag=isExist(user);
//					if(flag)
//						throw new Exception("亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！");
//				}
//			}
//			
//		}else{
//			boolean flag=isExist(user);
//			if(flag)
//				throw new Exception(ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())?"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！":"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
//		}
//		userMapper.updateUser(user);
//	}
	
	/**
	 * 修改用户
	 * @param user
	 */
	@Transactional
	public void updateUser(IpavuserEntity user,Map<String,Object> userDutyParam,MultipartFile file)throws Exception{
		IpavuserEntity temp= queryUserId(user.getUserid());
		if(temp.getRegtype().equals(user.getRegtype())){
			if(ContentUtil.REGIST_TYPE_EMIAL.equals(temp.getRegtype())){
				if(!temp.getEmail().equals(user.getEmail())){
					boolean flag=isExist(user);
					if(flag)
						throw new Exception( "亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
				}
			}else if(ContentUtil.REGIST_TYPE_MOBILE.equals(temp.getRegtype())){
				if(!temp.getMobile().equals(user.getMobile())){
					boolean flag=isExist(user);
					if(flag)
						throw new Exception("亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！");
				}
			}
			
		}else{
			boolean flag=isExist(user);
			if(flag)
				throw new Exception(ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())?"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！":"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
		}
		//修改图片时没选择
		String imgPath="";
		if(file!=null && file.getSize()!=0){
			 imgPath=ImageUtil.saveImage(file, ContentUtil.IMAGEPATHS.get("user"));
			user.setPicpath(imgPath);
		} 
		Map<String,Object> param=new HashMap<String, Object>();
		if(user.getPicpath().equals("")){
			//添加默认头像
			if(user.getSex().equals("0"))
				user.setPicpath("girl.png");
			else
		     	user.setPicpath("man.png");
		}
		int counts=userMapper.updateUser(user);
		if(counts>0){
			param.clear();
			param.put("userId",  user.getUserid());
			this.userMapper.deleteUserDutyInfos(param);
			userDutyParam.put("userId", user.getUserid());
			if(((List)userDutyParam.get("list")).size()>0)
			this.userMapper.addUserDutyInfos(userDutyParam);
		}else{
			if(!imgPath.equals("")){
			FTPUtil ftp = new FTPUtil();
			ftp.delete(imgPath, ContentUtil.IMAGEPATHS.get("user"));
		}
		}
	}
	
	/***
	 * 禁用启用用户
	 * @param userid
	 */
	@Transactional
	public void setuserVal(String valflg,String[] userids){
		Map temp = new HashMap();
		if(userids!=null&&userids.length>0){
			for (int i = 0; i < userids.length; i++) {
				temp.put("valflg", valflg);
				temp.put("userid", userids[i]);
				userMapper.setuserVal(temp);
				temp.clear();
			}
		}
		
	}
	/**
	 * 删除用户
	 * @param userid
	 */
	@Transactional
	public void delUser(String[] userids){
		if(userids!=null&&userids.length>0){
			for (int i = 0; i < userids.length; i++) {
				userMapper.delUser(userids[i]);
			}
		}
	}
	
	public List queryOrgUserTree(String companyid){
		return userMapper.queryOrgUserTree(companyid);
	}
	
	public List getUserListForExport(Map map){
		return userMapper.getUserListForExport(map);
	}
	
	/**
	 * 用户姓名自动补全
	 * @param param
	 * @return
	 */
	public List<String> autoCompleteUser(Map<String,Object> param){
		return this.userMapper.autoCompleteUser(param);
	}
	
	/**
	 * 获取组织结构树状结构
	 * @param companyId
	 * @return
	 */
	public List<JSONObject> getAllLevelInfos(long companyId){
		List<Map<String,Object>> list=this.userMapper.getAllLevelInfos(companyId);
		List<JSONObject> result=new ArrayList<JSONObject>();
		JSONObject map=null;
		Map<String, Object> tmp=null;
		if(list!=null)
			for(int i=0;i<list.size();i++){
				if(!list.get(i).containsKey("pno")){
					tmp=list.get(i);
					map=new JSONObject();
					map.put("id", tmp.get("id"));
					map.put("no", tmp.get("no"));
					map.put("name", tmp.get("name"));
					map.put("fullname", tmp.containsKey("fullname")?tmp.get("fullname"):"");
					map.put("type", 1);
					result.add(map);
					list.remove(i);
					break;
				}
			}	
		this.recurring(result.get(0),new ArrayList<JSONObject>(), list, map.get("no").toString(),map.get("id").toString());
		return result;
	}
	
	/**
	 * 递归
	 * @param param
	 * @param list
	 * @param parentNo
	 */
	
	private void recurring(JSONObject param,List<JSONObject> mapList,List<Map<String,Object>> list,String parentNo,String parentId){
		JSONObject map=null;
		Map<String,Object> tmp=null;
		Iterator<Map<String, Object>> iter=list.iterator();
		while(iter.hasNext()){
			tmp=iter.next();
			map=new JSONObject();
			if("1".equals(tmp.get("type").toString())&&parentNo.equals(tmp.get("pno").toString())){
				iter.remove();
				map.put("id", tmp.get("id"));
				map.put("no", tmp.get("no"));
				map.put("name", tmp.get("name"));
				map.put("fullname", tmp.containsKey("fullname")?tmp.get("fullname"):"");
				map.put("type", 1);
				mapList.add(map);
			}else if("0".equals(tmp.get("type").toString())&&parentId.equals(tmp.get("pno").toString())){
				iter.remove();
				map.put("id", tmp.get("id"));
				map.put("no", tmp.get("no"));
				map.put("name", tmp.get("name"));
				map.put("type", 0);
				mapList.add(map);
			}
		}
		for(int i=0;i<mapList.size();i++){
			if(mapList.get(i).get("type").toString().equals("1"))
				this.recurring(mapList.get(i), new ArrayList<JSONObject>(),list, mapList.get(i).get("no").toString(),mapList.get(i).get("id").toString());
		}	
		param.put("nodes", mapList);
	}
	
	@Transactional
	public int addUser(IpavuserEntity user,Map<String,Object> userDutyParam,MultipartFile file,String createId) throws Exception{
		if(isExist(user))
			throw new Exception(ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())?"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！":"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
		String imgPath="";
		if(file!=null&&file.getSize()>0){
			imgPath=ImageUtil.saveImage(file, ContentUtil.IMAGEPATHS.get("user"));
			user.setPicpath(imgPath);
		}
		String userId=user.getCompanyid()+user.getUserno();
		user.setUserid(userId);
		String olpwd= PwdUtil.createPwd();
		user.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
		user.setPassword(PwdUtil.MD5(olpwd));
		String tm = FormatUtil.formatDate(new Date(), null);
		user.setCreatedate(tm);
		user.setCreater(createId);
		if(("").equals(user.getPicpath())){
			//添加默认头像
			if(user.getSex().equals("0"))
				user.setPicpath("girl.png");
			else
		     	user.setPicpath("man.png");
		}
		int count=this.userMapper.insertUser(user);
		if(("").equals(user.getUserid())){
			if(file!=null&&file.getSize()>0){
				FTPUtil ftp = new FTPUtil();
				ftp.delete(imgPath, ContentUtil.IMAGEPATHS.get("user"));
			}
		}else{
			if(userDutyParam!=null){
				userDutyParam.put("userId", userId);
				if(userDutyParam.containsKey("list"))
					count=this.userMapper.addUserDutyInfos(userDutyParam);
			}
		}
		if(count==0)
			return count;
		if (ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())) {
			 messageUtil.sendReistOkByCompanyMessage(user, olpwd);
		}
		if (ContentUtil.REGIST_TYPE_EMIAL.equals(user.getRegtype())) {
			messageUtil.sendRegistOkByCompanyMail(user, olpwd);
		}
		return count;
	}
	
	@Transactional
	public int addUser(IpavuserEntity user,Map<String,Object> userDutyParam,MultipartFile file,String createId,List<Map<String,Object>> list) throws Exception{
		if(isExist(user))
			throw new Exception(ContentUtil.REGIST_TYPE_MOBILE.equals(user.getRegtype())?"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！":"亲爱的快捷管家用户，您好！您在快捷管家中用来注册的邮箱在系统中已注册生成过用户，请重新输入邮箱注册！");
		
		String imgPath="";
		if(file!=null&&file.getSize()>0){
			ImageUtil.saveImage(file, ContentUtil.IMAGEPATHS.get("user"));
			user.setPicpath(imgPath);
		}
		user.setPicpath(imgPath);
		String userId=user.getCompanyid()+user.getUserno();
		user.setUserid(userId);
		String olpwd= PwdUtil.createPwd();
		user.setValflg(ContentUtil.DEFUAL_VALDLF_YES);
		user.setPassword(PwdUtil.MD5(olpwd));
		String tm = FormatUtil.formatDate(new Date(), null);
		user.setCreatedate(tm);
		user.setCreater(createId);
		if(user.getPicpath().equals("")){
			//添加默认头像
			if(user.getSex().equals("0"))
				user.setPicpath("girl.png");
			else
		     	user.setPicpath("man.png");
		}
		int count=this.userMapper.insertUser(user);
		if(("").equals(user.getUserid())){
			if(file!=null&&file.getSize()>0){
				FTPUtil ftp = new FTPUtil();
				ftp.delete(imgPath, ContentUtil.IMAGEPATHS.get("user"));
			}
		}else{
			if(userDutyParam!=null){
				userDutyParam.put("userId", userId);
				if(userDutyParam.containsKey("list"))
					count=this.userMapper.addUserDutyInfos(userDutyParam);
			}
		}
		if(count>0){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("user", user);
			map.put("pwd", olpwd);
			list.add(map);
		}
		return count>0?1:0;
	}

	/**
	 * 根据组织id得到组织下的所有用户
	 * @param orgid
	 * @return
	 */
	public List<JSONObject> getUserListForOrgid(Integer orgid,String[] orgnoids,Long companyid){
		Map map =new HashMap<String,Object>();
		map.put("companyid",companyid);
		map.put("orgid",orgid);
		map.put("orgnoids",orgnoids);
		List<Map<String, Object>> list=this.userMapper.getUserListForOrgid(map);
		List<JSONObject> result=listToJSONObject(list);
		return result;
	}
	
	
	/**
	 * 根据用户名模糊查询 用户信息
	 * @param orgid
	 * @return
	 */
	public List<JSONObject> getUserForName(Map map){ 
		List<Map<String, Object>> list=this.userMapper.getUserForName(map);
		List<JSONObject> result=listToJSONObject(list);
	   return result;	
	}
	/**
	 * 将集合转换为查看同事信息
	 * @param list
	 * @return
	 */
	private List<JSONObject> listToJSONObject(List<Map<String, Object>>  list){
		List<JSONObject> result=new ArrayList<JSONObject>();
		JSONObject map=null;
		Map<String, Object> tmp=null;
		if(list!=null)
			for(int i=0;i<list.size();i++){
				tmp=list.get(i);
				map=new JSONObject();
				map.put("userid", tmp.get("userid"));
				map.put("username", tmp.get("username"));
				map.put("mobile", tmp.get("mobile").equals("")==false?tmp.get("mobile"):"暂无");
				map.put("email", tmp.get("email").equals("")==false?tmp.get("email"):"暂无");
				map.put("phone", tmp.get("phone").equals("")==false?tmp.get("phone"):"暂无");
				map.put("address", tmp.get("address").equals("")==false?tmp.get("address"):"暂无");
				//map.put("orgname", tmp.get("orgname").equals("")==false?tmp.get("orgname"):"暂无");
				map.put("picpath", ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user")+tmp.get("picpath"));
				//List<String> dutys=new ArrayList<String>();
				List<JSONObject> org_dutys=new ArrayList<JSONObject>();
			for(int j=i; j<list.size();j++){
				if(list.get(j).get("userid").equals(list.get(i).get("userid"))){
					//if(list.get(j).get("duty_name")!=null){
					JSONObject org_duty=new JSONObject();
					org_duty.put("dutyname",list.get(j).get("duty_name")!=null?list.get(j).get("duty_name"):"");
					org_duty.put("orgname",list.get(j).get("orgname")!=null?list.get(j).get("orgname"):"");
					org_duty.put("type",list.get(j).get("type")!=null?list.get(j).get("type"):1);
					org_dutys.add(org_duty);
					//}
					if(i!=j){
					list.remove(j);
					j--;
					}
				}
			}
			//map.put("dutys", dutys);
			map.put("org_dutys", org_dutys);
			result.add(map);
	}
		return  result;
	}
	/**
	 * GaoYang--查询属于同一机构(公司)的全体员工)
	 * @param parm
	 * @return
	 */
	public List queryUsers(Map parm){
		return userMapper.queryUsers(parm);
	}
	
	/**
	 * 员工信息导出
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryExportUserList(Map<String,Object> param){
		return this.userMapper.queryExportUserList(param);
	}
	
	/**
	 * 员工任职信息
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryUserPostInfo(Map<String,Object> param){
		return this.userMapper.queryUserPostInfo(param);
	}
	
	/**
	 * 获取已注册的手机及邮箱
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryExistRegistedUser(Map<String,Object> param){
		return this.userMapper.queryExistRegistedUser(param);
	}
	
	/**
	 * 获取个人中心信息
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> queryUserInfoOfCenter(Map<String,Object> param){
		return this.userMapper.queryUserInfoOfCenter(param);
	}
	/**
	 * 更新用户信息
	 * @param param
	 * @param file
	 * @return
	 */
	public int updateUserInfo(Map<String,Object> param,MultipartFile file){
		Map<String,Object> tmpParam=new HashMap<String, Object>();
		tmpParam.put("userid", param.get("userId"));
		IpavuserEntity user=this.userMapper.getUserByUniKey(tmpParam);
		long timestamp=Calendar.getInstance().getTimeInMillis();
		long size=0;
		if(file!=null)
			size=file.getSize();
		param.put("picpath", "");
		if(!param.containsKey("sex"))
			param.put("sex", user.getSex());
		FTPUtil ftpUtil=new FTPUtil();
		String type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String filename="";
		if(size>0){
			try {
				ftpUtil.upload(ftpUtil.resizeImg(Integer.parseInt(param.get("outWidth").toString()), Integer.parseInt(param.get("outHeight").toString()), file.getInputStream(),type),ContentUtil.IMAGEPATHS.get("user"), timestamp+"."+type);
				if(param.get("startX").toString().trim().equals(""))
					filename=timestamp+"."+type;
				else{
					filename=new ImageUtil().cutFTPImage(Integer.parseInt(param.get("startX").toString()), 
							Integer.parseInt(param.get("startY").toString()), 
							Integer.parseInt(param.get("width").toString()), 
							Integer.parseInt(param.get("height").toString()), type,
							ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user"),ContentUtil.IMAGEPATHS.get("user"),timestamp+"."+type);
					ftpUtil.delete(timestamp+"."+type,ContentUtil.IMAGEPATHS.get("user"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			param.put("picpath", filename);
		}else if(!param.get("startX").toString().trim().equals("")){
			if(!user.getPicpath().equals("")){
				filename=new ImageUtil().cutFTPImage(Integer.parseInt(param.get("startX").toString()), 
						Integer.parseInt(param.get("startY").toString()), 
						Integer.parseInt(param.get("width").toString()), 
						Integer.parseInt(param.get("height").toString()), type,
						ContentUtil.IMAGE_ROOT+ContentUtil.IMAGEPATHS.get("user"),ContentUtil.IMAGEPATHS.get("user"),user.getPicpath());
				ftpUtil.delete(timestamp+"."+type,ContentUtil.IMAGEPATHS.get("user"));
				param.put("picpath", filename);
			}
		}else
			param.remove("picpath");
		return this.userMapper.updateUserInfos(param)>0?1:0;
	}
	
	/**
	 * 获取openfire群组信息
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> getRoomsInfosOfOF(Map<String,Object> param){
		return this.userMapper.getRoomsInfosOfOF(param);
	}
	
	/**
	 * 判断邮箱或手机号是否已注册
	 * @param param
	 * @return
	 */
	public boolean hasRegisted(Map<String,Object> param){
		List<Map<String,Object>> list=this.userMapper.hasRegisted(param);
		boolean flag=false;
		if(list!=null&&list.size()>0)
			flag=true;
		return flag;
	}
 
	/**
	 * 更改用户状态（是否是第一次登陆）
	 * @param map
	 */
	public void updateUserState(Map map) {
		userMapper.updateUserState(map);
	}
 
	
	/**
	 * 获取组织机构
	 * @param user
	 * @return
	 */
	public IpavorgEntity getOrg(IpavuserEntity user){
		 return orgMapper.queryOrgnoTo0Bycompanyid(user.getCompanyid()+"");
	}
 
}

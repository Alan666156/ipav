package com.ipav.infoshare.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ipav.system.dao.NoticeMapper;
import com.ipav.system.dao.OrgMapper;
import com.ipav.system.dao.UserMapper;
import com.ipav.system.entity.IpavcommentEntity;
import com.ipav.system.entity.IpavnoticeEntity;
import com.ipav.system.entity.IpavnoticeSendedEntity;
import com.ipav.system.entity.IpavnoticeTypeEntity;
import com.ipav.system.entity.IpavorgEntity;
import com.ipav.system.entity.IpavreplyEntity;
import com.ipav.system.entity.IpavuserEntity;
import com.ipav.system.service.IpavuserService;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.FormatUtil;
import com.ipav.system.util.ImageUtil;
import com.ipav.system.util.ftp.FTPUtil;

@Service
public class IpavnoticeService {
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private OrgMapper orgMapper;
	
	
	/**********************************公告接收人****************************************/
	/**
	 * 添加公告接收人
	 * @param notice
	 */
	public void addNoticeSended(IpavnoticeEntity notice){
		Map<String,Object> parm = new HashMap<String, Object>();
		boolean valflg=true;
		if (notice.getSendeds() != null) {
			for (IpavnoticeSendedEntity sended : notice.getSendeds()) {
				if(sended.getUserid().equals(notice.getUserid()))	valflg=false;
				parm.put("userid", sended.getUserid());
				parm.put("noticeid",notice.getId());				
				parm.put("isread",0);				
				noticeMapper.insertNoticeSended(parm);				
			}
		}
		if(valflg) insertDefaultSended(notice);
	}
	
	/**
	 * 添加默认公告接收人
	 * @param notice
	 */
	private void insertDefaultSended(IpavnoticeEntity notice){
		Map<String,Object> parm=new HashMap<String,Object>();
		parm.put("userid", notice.getUserid());
		parm.put("noticeid",notice.getId());
		parm.put("isread",0);
		noticeMapper.insertNoticeSended(parm);
	}
	
	/**
	 * 获取公告的接收人集合
	 * @param parm
	 * @return
	 */
			
	public List<IpavnoticeSendedEntity> getNoticeSended(Map parm) {
		return noticeMapper.queryNoticeSended(parm);
	}
	
	/**
	 * 获取公告接收人数据长度	 
	 */
	public int getSendedCnt(Map parm){
		Integer readeint=noticeMapper.querySendedCount(parm);
		return readeint==null?0:readeint;
	}
	
	/**
	 * 更新公告接收人
	 * @param notice
	 */
	private void modifyNoticeSended(IpavnoticeEntity notice){
		Map parm = new HashMap();
		parm.put("noticeid", notice.getId());
		parm.put("isdelete", 1);
		//删除原来的数据
		noticeMapper.updateNoticeSended(parm);
		boolean valflg=true;
		if (notice.getSendeds() != null) {
			for (IpavnoticeSendedEntity sended : notice.getSendeds()) {
				if(sended.getUserid().equals(notice.getUserid())) valflg=false;
				parm.clear();
				parm.put("noticeid", notice.getId());
				parm.put("userid", sended.getUserid());
				int sendedcnt=getSendedCnt(parm);
				parm.put("isread", 0);
				if(sendedcnt==0){
					 noticeMapper.insertNoticeSended(parm);
				}else{
					parm.put("isdelete", 0);
					noticeMapper.updateNoticeSended(parm);
				}		
			}
		}	
		if(valflg) modifyDefaultNotice(notice);
	}
	
	/**
	 * 更新默认公告接收人 
	 * @param notice
	 */
	private void modifyDefaultNotice(IpavnoticeEntity notice) {
		Map parm = new HashMap();
		parm.put("noticeid", notice.getId());
		parm.put("userid", notice.getUserid());
		parm.put("isdelete", 0);
		noticeMapper.updateNoticeSended(parm);
	}

	/**
	 * 更新公告接收人浏览状态(未读-->已读)
	 * @param parm
	 */
	public void modifySendedState(Map<String, Object> parm) {
		noticeMapper.updateNoticeSended(parm);
	}
	

	/************************************公告******************************************/
	
	/**
	 * 获取公告编号(公告编号规则：“公司简称”+“公告类型”+【年份】+第**号)
	 * @return
	 */
	public Map<String,String> getNoticeNo(Map<String,Object> parm){
		return noticeMapper.selectNoticeNo(parm);
	}
	
	/**
	 * 根据获取公告在某一公告类型下的最大编号
	 * @param parm
	 * @return
	 */
	public String queryMaxIndex(Map<String,Object> parm) {
		return noticeMapper.queryNoticeMaxIndex(parm);
	}
	
	/***
	 * 添加公告
	 * 
	 * @param notice
	 * @param filedata
	 * @throws Exception
	 */
	public void addNotice(IpavnoticeEntity notice, MultipartFile filedata)
			throws Exception {
		saveFile(notice,filedata);
		if(notice.getIstrue()==1)
			notice.setCreatedate(FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		noticeMapper.insertNotice(notice);
		addNoticeSended(notice);
//		releaseNotice(notice.getRemindtype());
		
	}
	
	/**
	 * 根据提醒方式发布新建公告消息(暂停使用)
	 * @param remindtype : 发布方式
	 */
	private void releaseNotice(String remindStr) {
		if(StringUtils.isNotEmpty(remindStr)){
			String[] strarr=remindStr.split(";");
			for(String s : strarr){
				if(s.equals("2")){//邮件提醒
//					sendEmailOr();
				}
				if(s.equals("3")){//短信提醒
					
				}
			}
		}
		
	}
	
	/**
	 * 根据用户id获取用户
	 * @param id
	 * @return
	 */
	public IpavuserEntity getUserById(String id){
		Map map = new HashMap();
		map.put("userid",id);
		return userMapper.getUserByUniKey(map);
	}
	
	/**
	 *获取公告信息中用户的名称 
	 * @param notice
	 */
	public void modifyName(IpavnoticeEntity notice){
		if(notice!=null){
			//获取发布公告的用户姓名		
			notice.setUsername(getUserById(notice.getUserid())==null?"":getUserById(notice.getUserid()).getUsername());
			
			//获取公告签发人的姓名
			notice.setIssuedname(getUserById(notice.getIssuedid())==null?"":getUserById(notice.getIssuedid()).getUsername());
	
			//获取公告发文部门名称
			IpavorgEntity org =orgMapper.queryOrgById(notice.getOrgid());
			notice.setOrgname(org==null?"":org.getOrgname());
			if(notice.getSendeds()!=null){
				//获取公共接收人中用户的名称
				for(IpavnoticeSendedEntity ns:notice.getSendeds())
					ns.setUsername(getUserById(ns.getUserid())==null?"":getUserById(ns.getUserid()).getUsername());
			}
		}
	}
	
	/**
	 * 查询获取公告列表集合
	 * @param parm
	 * @return
	 */
	public List<IpavnoticeEntity> getNoticeList(Map<String,Object> parm) {
		List<IpavnoticeEntity> noticelist=noticeMapper.queryNoticeList(parm);
		for(IpavnoticeEntity n:noticelist) modifyName(n);
		return noticelist;
	}
	
	/**
	 * 查询获取公告列表集合长度
	 * @param parm
	 * @return
	 */
	public int getListCnt(Map<String,Object> parm) {
		Integer cnt=noticeMapper.queryListCnt(parm);
		return cnt==null?0:cnt;
	}
	
	/***
	 * 根据ID获取公告
	 * 
	 * @param noticeid
	 * @return
	 */
	public IpavnoticeEntity getNoticeByParm(Map<String,Object> parm) {
		return noticeMapper.queryNoticeByParm(parm);
	}
	
	/***
	 * 更新公告信息
	 * 
	 * @param notice
	 * @param filedata
	 * @throws Exception
	 */
	public void modifyNotice(IpavnoticeEntity notice, MultipartFile filedata)
			throws Exception {
		//添加新的附件
		saveFile(notice,filedata);
		//更新公告接收人
		modifyNoticeSended(notice);
		if(notice.getIstrue()==1)
			notice.setCreatedate(FormatUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		noticeMapper.updateNotice(notice);
	}

	/**
	 * 更新公告状态
	 * @param parm
	 */
	public void updateNoticeState(Map<String,Object> parm){
		noticeMapper.updateNoticeState(parm);
	}
	
	/************************************公告类型*********************************/
	
	/**
	 * 查询公告类型名称使用次数
	 * @param typename
	 * @return
	 */
	public int queryNameCnt(Map<String,Object> parm) {
		Integer tynamecnt=noticeMapper.queryTypeNameCnt(parm);
		return tynamecnt==null?0:tynamecnt;
	}
		
	/***
	 * 添加公告类型
	 * 
	 * @param noticetype
	 */
	public void addNoticeType(IpavnoticeTypeEntity noticetype) {
		Integer typeindex = noticeMapper.queryMaxIndex();
		if(typeindex==null){
			noticetype.setTypeindex(1001);
		}else{
			noticetype.setTypeindex(typeindex + 1);
		}
		noticetype.setCreatedate(FormatUtil.formatDate(new Date(),
				"yyyy-MM-dd"));
		noticeMapper.insertNoticeType(noticetype);
	}
	
	/**
	 * 获取所有公告类型集合
	 * @param istrue
	 * @return
	 */
	public List<IpavnoticeTypeEntity> getNoticeTypeList(Map<String,Object> parm) {
		List<IpavnoticeTypeEntity> typelist=noticeMapper.queryNoticeTypes(parm);
		for(IpavnoticeTypeEntity nt:typelist) {
			IpavuserEntity user = getUserById(nt.getUserid());
			nt.setUsername(user==null?"":user.getUsername());
		}
		return typelist;
	}
	
	/**
	 * 获取公告类型数量最大值
	 * @param istrue
	 * @return
	 */
	public int getNoticeTypeCnt(Map<String,Object> parm) {
		Integer rscnt=noticeMapper.queryTypeCnt(parm);
		return rscnt==null?0:rscnt;
	}
	
	/***
	 * 更新公告类型信息
	 * 
	 * @param noticetype
	 */
	public void modifyNoticeType(IpavnoticeTypeEntity noticetype) {
		noticeMapper.updateNoticeType(noticetype);
	}
	
	/**
	 * 修改默认公告类型
	 */
	public void updateDefaultType(Map<String,Object> parm) {
		noticeMapper.updateDefaultType(parm);
	}

	/**
	 * 更新公告类型状态
	 * @param parm
	 */
	public void modifyNoticeTypeState(Map<String,Object> parm) {
		noticeMapper.updateNoticetypeState(parm);
	}

	/**
	 * 更新公告类型排列顺序
	 * @param parm
	 */
	public void changeNoticeTypeIndex(Map<String,Object> parm) {
		noticeMapper.updateNoticetypeState(parm);
	}

	/**
	 * 保存上传附件
	 * @param notice
	 * @param filedata
	 * @throws Exception
	 */
	public void saveFile(IpavnoticeEntity notice, MultipartFile filedata) throws Exception {
		if (filedata != null && !filedata.isEmpty()) {
			String oldfilename = filedata.getOriginalFilename();
			String newfilename=System.currentTimeMillis()+"";
			FTPUtil ftp = new FTPUtil();
			// 获取文件的扩展名
			String extensionName = oldfilename.substring(oldfilename.lastIndexOf(".")+1, oldfilename.length());
			// 新的文件路径名 = 获取时间戳+"."文件扩展名
			String filepath = newfilename+ "." + extensionName;
			ftp.upload(filedata.getInputStream(),  ContentUtil.IMAGEPATHS.get("files"), filepath);
			notice.setOldfilename(oldfilename);
			notice.setNewfilename(newfilename);
			notice.setFilepath(filepath);
		}
	}
	
	/**
	 * 删除原本文件
	 * @param notice
	 * @throws IOException 
	 */
	public void movefile(IpavnoticeEntity notice) throws IOException {
		  String oldFilePath=noticeMapper.queryFilePath(notice.getId());
		  FTPUtil ftp = new FTPUtil();
		  ftp.delete(oldFilePath,ContentUtil.IMAGEPATHS.get("files"));
	      notice.setOldfilename("");
		  notice.setNewfilename("");
		  notice.setFilepath("");
	}

	
}

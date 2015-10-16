package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

import com.ipav.system.entity.IpavnoticeEntity;
import com.ipav.system.entity.IpavnoticeSendedEntity;
import com.ipav.system.entity.IpavnoticeTypeEntity;

public interface NoticeMapper {

	public Map<String, String> selectNoticeNo(Map parm);
	
	public void insertNotice(IpavnoticeEntity notice);
	
	public void insertNoticeSended(Map parm);

	public Integer queryMaxIndex();

	public Integer queryTypeNameCnt(Map parm);

	public void insertNoticeType(IpavnoticeTypeEntity noticetype);

	public List<IpavnoticeTypeEntity> queryNoticeTypes(Map parm);

	public List<IpavnoticeEntity> queryNoticeList(Map map);

	public Integer queryListCnt(Map map);
	
	public List<IpavnoticeSendedEntity> queryNoticeSended(Map parm);

	public IpavnoticeEntity queryNoticeByParm(Map<String, Object> parm);

	public void updateNotice(IpavnoticeEntity notice);

	public void updateNoticeState(Map map);

	public void updateNoticeType(IpavnoticeTypeEntity noticetype);

	public void updateNoticetypeState(Map parm);
	
	public void updateDefaultType(Map parm);

	public String queryFilePath(Long nid);

	public Integer queryTypeCnt(Map parm);

	public void updateNoticeSended(Map parm);

	public Integer querySendedCount(Map parm);

	public String queryNoticeMaxIndex(Map parm);



}

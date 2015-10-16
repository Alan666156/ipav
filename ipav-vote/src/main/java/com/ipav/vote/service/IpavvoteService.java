package com.ipav.vote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ipav.system.dao.VoteAuthorMapper;
import com.ipav.system.dao.VoteItemMapper;
import com.ipav.system.dao.VoteMapper;
import com.ipav.system.dao.VoteRecordMapper;
import com.ipav.system.util.ContentUtil;
import com.ipav.system.util.ftp.FTPUtil;

@Service
public class IpavvoteService {

	@Autowired
	private VoteMapper voteMapper;

	@Autowired
	private VoteItemMapper voteItemMapper;

	@Autowired
	private VoteAuthorMapper voteAuthorMapper;

	@Autowired
	private VoteRecordMapper voteRecordMapper;

	/**
	 * 获取权限投票相关
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getAuthorList(Map<String, Object> param) {
		return this.voteAuthorMapper.getVoteAuthorOfVote(param);
	}

	/**
	 * 发起投票
	 * 
	 * @param voteInfo
	 * @param voteItems
	 * @return
	 */
	@Transactional
	public int insertVote(Map<String, Object> voteInfo,
			List<Map<String, Object>> voteItems,
			List<Map<String, Object>> userAuthorList, int oldId,
			Set<String> oldPicSet) {
		int count = this.voteMapper.insertVote(voteInfo);
		if (count == 0)
			return -1;
		
		int id = Integer.parseInt(voteInfo.get("id").toString());
		Map<String, Object> itemParam = new HashMap<String, Object>();
		itemParam.put("updateUser", voteInfo.get("initiator"));
		itemParam.put("list", voteItems);
		itemParam.put("voteId", id);
		this.voteItemMapper.insertBatch(itemParam);
		Map<String, Object> authorParam = new HashMap<String, Object>();
		authorParam.put("updateUser", voteInfo.get("initiator"));
		authorParam.put("list", userAuthorList);
		authorParam.put("voteId", id);
		this.voteAuthorMapper.insertBatch(authorParam);
		if (oldId > 0) {
			List<Map<String, Object>> oldItems = this.voteItemMapper
					.getVoteItemsByVoteId(oldId);
			int counts = this.voteMapper.deleteVoteAndOtherInfos(oldId);
			if (counts == 0)
				return -1;
			else {
				if (oldItems != null){
					FTPUtil util=new FTPUtil();
					for (Map<String, Object> map : oldItems) {
						if (map.containsKey("pic")
								&& !map.get("pic").toString().trim().equals("")
								&& !oldPicSet.contains(map.get("pic")
										.toString())) {
							util.delete(map.get("pic").toString(),ContentUtil.IMAGEPATHS.get("vote"));
						}
					}
				}
			}
		}
		return id;
	}

	/**
	 * 更新投票活动
	 * 
	 * @param param
	 * @return
	 */
	public int updateVote(Map<String, Object> param) {
		return this.voteMapper.updateVoteInfo(param);
	}

	/**
	 * 删除投票活动
	 * 
	 * @param param
	 * @return
	 */
	@Transactional
	public int updateVoteDel(Map<String, Object> param) {
		param.put("status", 0);
		int count = this.voteMapper.updateVoteDel(param);
		return count;
	}

	/**
	 * 添加选项
	 * 
	 * @param voteItem
	 * @param voteItems
	 */
	public void insertVoteItem(Map<String, Object> voteItem,
			List<Map<String, Object>> voteItems, String userId) {
		if (voteItem != null)
			this.voteItemMapper.insertVoteItem(voteItem);
		else {
			Map<String, Object> itemParam = new HashMap<String, Object>();
			itemParam.put("updateUser", userId);
			itemParam.put("item", voteItems);
			this.voteItemMapper.insertBatch(itemParam);
		}
	}

	/**
	 * 耿秀投票选项
	 * 
	 * @param param
	 * @return
	 */
	public int updateVoteItem(Map<String, Object> param) {
		return this.voteItemMapper.updateVoteItem(param);
	}

	/**
	 * 获取投票活动列表
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getVoteInfoList(Map<String, Object> param) {
		return this.voteMapper.getVoteInfoList(param);
	}

	/**
	 * 获取投票活动数量
	 * 
	 * @param param
	 * @return
	 */
	public int getVoteInfoCounts(Map<String, Object> param) {
		int count = 0;
		Map<String, Object> map = this.voteMapper.getVoteInfoCounts(param);
		if (map != null)
			count = Integer.parseInt(map.get("counts").toString());
		return count;
	}

	/**
	 * 判断是否有修改权限
	 * 
	 * @param param
	 * @return
	 */
	public boolean getModifyAuthor(Map<String, Object> param) {
		boolean result = false;
		List<Map<String, Object>> list = this.voteMapper.getModifyAuthor(param);
		if (list != null && list.size() == 1)
			result = true;
		return result;
	}

	/**
	 * 获取投票活动详情
	 * 
	 * @param voteId
	 * @param type
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getVoteDetails(int voteId, int type,
			String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> voteParam = new HashMap<String, Object>();
		voteParam.put("id", voteId);
		List<Map<String, Object>> votes = this.voteMapper
				.getVoteInfoList(voteParam);
		if (votes == null || votes.size() != 1)
			return result;
		result.put("id", votes.get(0).get("id"));
		result.put("title", votes.get(0).get("title"));
		result.put("remark", votes.get(0).containsKey("remark") ? votes.get(0)
				.get("remark") : "");
		result.put("type", votes.get(0).get("type"));
		result.put("endTime", votes.get(0).get("end_time"));
		result.put("startTime", votes.get(0).get("start_time"));
		result.put("updateTime", votes.get(0).get("update_time"));
		result.put("isAnonymout", votes.get(0).get("is_anonymous"));
		result.put("resultType", votes.get(0).get("result_type"));
		result.put("initiator", votes.get(0).get("initiator"));
		result.put("username", votes.get(0).get("username"));
		result.put("pic", votes.get(0).get("picpath"));
		result.put("minNum", votes.get(0).get("min_num"));
		result.put("criteria", votes.get(0).get("criteria"));
		result.put("status", votes.get(0).get("status"));
		result.put("ext", votes.get(0).get("ext"));
		if (type == 1) {
			Map<String, Object> authorParam = new HashMap<String, Object>();
			authorParam.put("voteId", result.get("id"));
			List<Map<String, Object>> authorList = this.voteAuthorMapper
					.getVoteAuthorOfVote(authorParam);
			StringBuffer joinPeople = new StringBuffer();
			StringBuffer joinPeopleName = new StringBuffer();
			StringBuffer viewPeople = new StringBuffer();
			StringBuffer viewPeopleName = new StringBuffer();
			if (authorList != null)
				for (Map<String, Object> map : authorList) {
					switch (Integer.parseInt(map.get("author").toString())) {
					case 1: {
						joinPeople.append(map.get("user_id").toString())
								.append(";");
						joinPeopleName.append(map.get("username").toString())
								.append(";");
					}
						break;
					case 2: {
						viewPeople.append(map.get("user_id").toString())
								.append(";");
						viewPeopleName.append(map.get("username").toString())
								.append(";");
					}
						break;
					case 3: {
						joinPeople.append(map.get("user_id").toString())
								.append(";");
						joinPeopleName.append(map.get("username").toString())
								.append(";");
						viewPeople.append(map.get("user_id").toString())
								.append(";");
						viewPeopleName.append(map.get("username").toString())
								.append(";");
					}
						break;
					}
				}
			if (joinPeople.length() > 0) {
				joinPeople.setLength(joinPeople.length() - 1);
				joinPeopleName.setLength(joinPeopleName.length() - 1);
			}
			if (viewPeople.length() > 0) {
				viewPeople.setLength(viewPeople.length() - 1);
				viewPeopleName.setLength(viewPeopleName.length() - 1);
			}
			result.put("joinPeople", joinPeople.toString());
			result.put("joinPeopleName", joinPeopleName.toString());
			result.put("viewPeople", viewPeople.toString());
			result.put("viewPeopleName", viewPeopleName.toString());
			List<Map<String, Object>> itemList = null;
			if (votes.get(0).get("type").toString().trim().equals("3"))
				itemList = this.voteItemMapper.getVoteColleagueItemsByVoteId(voteId);
			else
				itemList = this.voteItemMapper.getVoteItemsByVoteId(voteId);
			if (itemList != null && itemList.size() > 0) {
				JSONArray arr = new JSONArray();
				JSONObject item = null;
				if (votes.get(0).get("type").toString().trim().equals("3")) {
					for (Map<String, Object> map : itemList) {
						item = new JSONObject();
						item.put("id", map.get("id"));
						item.put("orgName", map.get("orgname"));
						item.put("duty", map.get("duty"));
						item.put("itemName", map.get("item_name"));
						item.put("userid", map.get("userid"));
						item.put("pic",
								map.containsKey("pic") ? map.get("pic") : "");
						arr.add(item);
					}
				} else {
					for (Map<String, Object> map : itemList) {
						item = new JSONObject();
						item.put("id", map.get("id"));
						item.put("itemName", map.get("item_name"));
						item.put("pic",
								map.containsKey("pic") ? map.get("pic") : "");
						arr.add(item);
					}
				}
				result.put("itemList", arr);
				Map<String, Object> castedParam = new HashMap<String, Object>();
				castedParam.put("voteId", votes.get(0).get("id"));
				castedParam.put("userId", userId);
				List<Map<String, Object>> castedInfos = this.voteRecordMapper
						.getCastedDetail(castedParam);
				if (castedInfos != null && castedInfos.size() > 0)
					result.put("castedInfos", castedInfos);
			}
		} else if (type == 2) {
			
//			if (votes.get(0).get("type").toString().trim().equals("0")) {
//				itemList=this.voteItemMapper.getVoteItemResultByVoteId(voteId);
//			}else 
//				
//				
//			if (votes.get(0).get("type").toString().trim().equals("0")) {
//				for (Map<String, Object> map : itemList) {
//					item = new JSONObject();
//					item.put("id", map.get("id"));
//					item.put("orgName", map.get("orgname"));
//					item.put("duty", map.get("duty"));
//					item.put("itemName", map.get("item_name"));
//					item.put("pic",
//							map.containsKey("pic") ? (ContentUtil.PICPATH
//									+ File.separator + map.get("pic")) : "");
//					item.put("voteCount", map.get("counts"));
//					arr.add(item);
//				}
//			}else 
			
			Map<String, Object> joinCounts = this.voteAuthorMapper
					.getJoinCounts(voteId);
			if (joinCounts != null && joinCounts.containsKey("counts"))
				result.put("counts", joinCounts.get("counts"));
			List<Map<String, Object>> itemResultList = null;
			if (votes.get(0).get("type").toString().trim().equals("3"))
				itemResultList = this.voteItemMapper
						.getVoteColleagueItemsResultByVoteId(voteId);
			else
				itemResultList = this.voteItemMapper
						.getVoteItemResultByVoteId(voteId);
			if (itemResultList != null)
				result.put("itemList", JSON.toJSON(itemResultList));
		}
		return result;
	}

	/**
	 * 获取投票报表总数
	 * 
	 * @param param
	 * @return
	 */
	public int getVoteStatisticCounts(Map<String, Object> param) {
		int count = 0;
		List<Map<String, Object>> list = this.voteMapper
				.getVoteStatisticCounts(param);
		if (list != null && list.size() == 1
				&& list.get(0).containsKey("counts"))
			count = Integer.parseInt(list.get(0).get("counts").toString());
		return count;
	}

	/**
	 * 获取投票报表
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getVoteStatistic(Map<String, Object> param) {
		return this.voteMapper.getVoteStatistic(param);
	}

	/**
	 * 投票
	 * 
	 * @param param
	 * @return
	 */
	@Transactional
	public int insertVoteRecord(Map<String, Object> param) {
//		this.voteRecordMapper.updateVoteRecordDel(param);
		this.voteRecordMapper.deleteVoteRecord(param);
		return this.voteRecordMapper.insertVoteRecord(param);
	}

	/**
	 * 判断是否已投票
	 * 
	 * @param param
	 * @return
	 */
	public boolean voteHasCasted(int voteId) {
		List<Map<String, Object>> list = this.voteRecordMapper
				.voteHasCasted(voteId);
		if (list != null && list.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 投票定时任务
	 * @return
	 */
	@Transactional
	public int voteStatusTask(){
		return this.voteMapper.voteStatusTask();
	}
	/**
	 * 投票定时任务
	 * @return
	 */
	public Map queryVote(Map map){
		return this.voteMapper.queryVote(map);
	}
}

package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

public interface VoteMapper {

	public int insertVote(Map<String,Object> param);
	
	public int updateVoteDel(Map<String,Object> param);
	
	public int updateVoteStatus(Map<String,Object> param);
	
	public int updateVoteInfo(Map<String,Object> param);
	
	public int deleteVoteAndOtherInfos(int id);
	
	public List<Map<String,Object>> getVoteInfoList(Map<String,Object> param);
	
	public Map<String,Object> getVoteInfoCounts(Map<String,Object> param);
	
	public List<Map<String,Object>> getVoteStatisticCounts(Map<String,Object> param);
	
	public List<Map<String,Object>> getVoteStatistic(Map<String,Object> param);
	
	public List<Map<String,Object>> getEarliestVotes();
	
	public List<Map<String,Object>> getLatestVotes();
	
	public List<Map<String,Object>> getModifyAuthor(Map<String,Object> param);
	
	public int voteStatusTask();
	
	public List<Map<String,Object>> getUnCompletedVote(Map<String,Object> param);
	
	public Map<String,Object> queryVote(Map<String,Object> param);
}

package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

public interface VoteItemMapper {

	public int insertVoteItem(Map<String,Object> param);
	
	public void insertBatch(Map<String,Object> param);
	
	public int updateVoteItem(Map<String,Object> param);
	
	public List<Map<String,Object>> getVoteItemsByVoteId(int voteId);
	
//	public List<Map<String,Object>> getVoteItemsResultByVoteId(int voteId);
	
	public List<Map<String,Object>> getVoteColleagueItemsByVoteId(int voteId);
	
	public List<Map<String,Object>> getVoteItemResultByVoteId(int voteId);
	
	public List<Map<String,Object>> getVoteColleagueItemsResultByVoteId(int voteId);
}

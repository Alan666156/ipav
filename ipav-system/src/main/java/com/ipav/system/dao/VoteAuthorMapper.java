package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

public interface VoteAuthorMapper {

	public long insertVoteAuthor(Map<String,Object> param);
	
	public void insertBatch(Map<String,Object> param);
	
	public int updateVoteAuthorDel(Map<String,Object> param);
	
	public int updateVoteAuthorDelByVote(Map<String,Object> param);
	
	public List<Map<String,Object>> getVoteAuthorOfVote(Map<String,Object> param);
	
	public Map<String,Object> getJoinCounts(int voteId);
}

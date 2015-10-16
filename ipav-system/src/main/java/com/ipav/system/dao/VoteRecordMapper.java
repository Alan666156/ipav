package com.ipav.system.dao;

import java.util.List;
import java.util.Map;

public interface VoteRecordMapper {

	public int insertVoteRecord(Map<String,Object> param);
	
	public List<Map<String,Object>> voteHasCasted(int voteId);
	
	public int updateVoteRecordDel(Map<String,Object> param);
	
	public int deleteVoteRecord(Map<String,Object> param);
	
	public List<Map<String,Object>> getCastedDetail(Map<String,Object> param);
}

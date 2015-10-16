package com.ipav.vote.util;

public class ContentUtil {
	
//	public static final String PICPATH="E:\\pic";

	public static final int VOTE_STATUS_CANCEL=0;//投票活动状态-投票取消
	
	public static final int VOTE_STATUS_TOBESTART=1;//投票活动状态-投票即将开始
	
	public static final int VOTE_STATUS_STARTING=2;//投票活动状态-投票进行中
	
	public static final int VOTE_STATUS_END=3;//投票活动状态-投票结束
	
	public static final int VOTE_ITEM=0;//投票活动类型-选项
	
	public static final int VOTE_SCORE=1;//投票活动类型-评分
	
	public static final int VOTE_RANK=2;//投票活动类型-排名
	
	public static final int VOTE_COLLEAGER=3;//投票活动类型-同事中选择
	
	public static final int VOTE_MINE=1;//投票个人关系-我发起的投票
	
	public static final int VOTE_MINE_FINISHED=2;//投票个人关系-我完成的投票
	
	public static final int VOTE_MINE_UNFINISHED=3;//投票个人关系-我未完成的投票
	
	public static final int VOTE_RESULT_NONE=0;//投票结果插卡类型-不允许投票人查看
	
	public static final int VOTE_RESULT_END=1;//投票结果插卡类型-投票截止后投票人可查看
	
	public static final int VOTE_RESULT_ANYTIME=2;//投票结果插卡类型-投票人随时可查看
	
	public static final int STATISTIC_TYPE_CHOSEN=1;//投票报表选项状态-入选
	
	public static final int STATISTIC_TYPE_UNCHOSEN=0;//投票报表选项状态-未入选
}

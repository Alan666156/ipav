package com.ipav.vote.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipav.vote.service.IpavvoteService;

@Controller(value="vote")
@RequestMapping(value="ipav/vote")
public class BaseController {
	@Autowired
	IpavvoteService ipavvoteService;
	private final String VOTE_PUBLISH="vote/originatevote";
	
	private final String VOTE_CENTER="vote/votecenter";
	
	private final String ITEM_INFO="vote/iteminfo";
	
	private final String SCORE_INFO="vote/scoreinfo";
	
	private final String RANK_INFO="vote/rankinfo";
	
	private final String COLLEAGUE_INFO="vote/colleagueinfo";
	
	private final String STATISTIC="vote/statistic";
	
	private final String CAST_ITEM="vote/castitem";
	
	private final String CAST_SCORE="vote/castscore";
	
	private final String CAST_RANK="vote/castrank";
	
	private final String CAST_COLLEAGUE="vote/castcolleague";
	
	private final String SHOWVOTE_PUBLISH="vote/showvote";
    /**
     * 跳转到投票或取回投票页面
     * @param req
     * @param model
     * @return
     */
	@RequestMapping(value="/publishVote")
	public String publishVoteForward(HttpServletRequest req,ModelMap model){
		if(req.getParameter("id")!=null)
			model.put("id", req.getParameter("id").toString());
		else
			model.put("id", "");
		return VOTE_PUBLISH;
	}
	/***
	 * 跳转到显示投票页面
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/showVote")
	public String showVoteForward(HttpServletRequest req,ModelMap model){
		if(req.getParameter("id")!=null)
			model.put("id", req.getParameter("id").toString());
		else
			model.put("id", "");
		return SHOWVOTE_PUBLISH;
	}
	
	@RequestMapping(value="/voteCenter")
	public String voteCenterForward(HttpServletRequest req,ModelMap model){
		if(req.getParameter("vid")!=null){
			model.put("vid", req.getParameter("vid"));	
			Map sql=new HashMap<String,Object>();
			sql.put("id", req.getParameter("vid"));
		    Map map=ipavvoteService.queryVote(sql);
			model.put("vtype",map.get("type"));			
			//model.put("type",1);
		}
		return VOTE_CENTER;
	}
	
	@RequestMapping(value="/itemInfo")
	public String itemInfoForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("id").toString());
		return ITEM_INFO;
	}
	
	@RequestMapping(value="/scoreInfo")
	public String scoreInfoForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("id").toString());
		return SCORE_INFO;
	}
	
	@RequestMapping(value="/rankInfo")
	public String rankInfoForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("id").toString());
		return RANK_INFO;
	}
	
	@RequestMapping(value="/colleagueInfo")
	public String colleagueInfoForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("id").toString());
		return COLLEAGUE_INFO;
	}
	
	@RequestMapping(value="/statistic")
	public String statisticForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("ids").toString());
		return STATISTIC;
	}
	
	@RequestMapping(value="/castItem")
	public String castItemInfoForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("id").toString());
		return CAST_ITEM;
	}
	
	@RequestMapping(value="/castScore")
	public String castScoreInfoForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("id").toString());
		return CAST_SCORE;
	}
	
	@RequestMapping(value="/castRank")
	public String castInfoForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("id").toString());
		return CAST_RANK;
	}
	
	@RequestMapping(value="/castColleague")
	public String castColleagueInfoForward(HttpServletRequest req,ModelMap model){
		model.put("id", req.getParameter("id").toString());
		return CAST_COLLEAGUE;
	}
}

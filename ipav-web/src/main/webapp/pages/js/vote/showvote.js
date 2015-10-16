  
$(document).ready(function(){
	if($("#voteId").val().trim()!=""){
		$("#voteId").prev().find("a").html("查看投票");
		$.ajax({
			url:"getVoteInfo",
			type:"POST",
			data:"id="+$("#voteId").val()+"&optType=1",
			dataType:"json",
			success:function(data){
				if(data!=undefined&&data.infos!=undefined&&data.infos.length==1){
					$("#voteName").html(data.infos[0].title);
					/***初始化投票人员/***/
					if(data.infos[0].joinPeopleName!=undefined)
					$("#showusers1").html(data.infos[0].joinPeopleName);					
					
					
					if(data.infos[0].viewPeopleName!=undefined)
					$("#showusers2").html(data.infos[0].viewPeopleName);
					var htm="";
				 
					 
					/***初始化投票人员/***/
					if(data.infos[0].remark!="")
						$("#guize").html(data.infos[0].remark);
					var endTime=data.infos[0].endTime.split(" ");
					endTime[0]=endTime[0].replace("-","年").replace("-","月").replace("年0","年").replace("月0","月")+"日";
					$("#endTimetext").html("投票截止："+endTime[0]+"&nbsp;&nbsp;&nbsp;"+endTime[1]+"时");
				  
					if(data.infos[0].startTime==data.infos[0].updateTime)
						$("#dstime1").hide();
					else{
						$("#dstime1").show();
					}
					var startTime=data.infos[0].startTime.split(" ");
					startTime[0]=startTime[0].replace("-","年").replace("-","月").replace("年0","年").replace("月0","月")+"日"
					$("#startTime").html("&nbsp;&nbsp;&nbsp;投票开始："+startTime[0]+"&nbsp;&nbsp;&nbsp;"+startTime[1]+"时");
					$("#dsTimeBox1").css("display","block"); 
					var s="";  
					if(data.infos[0].resultType==0){
						s="不允许投票人查看";
					}else if(data.infos[0].resultType==0){
						s="投票截止后投票人可查看";
					}else{
						s="投票人随时可查看";
					}
					$("#ptselectlook").html(s);
					if(data.infos[0].isAnonymout==1)
						$('#niming').show();
					if(data.infos[0].type==0)
						initItems(data.infos[0].itemList,data.infos[0].minNum,data.picpath);
					else if(data.infos[0].type==1)
						 initScore(data.infos[0].itemList,data.infos[0].minNum,data.infos[0].criteria,$.parseJSON(data.infos[0].ext),data.picpath);
					else if(data.infos[0].type==2)
						initRank(data.infos[0].itemList,$.parseJSON(data.infos[0].ext),data.picpath);
					else if(data.infos[0].type==3)
						initColleague(data.infos[0].itemList,data.infos[0].minNum);
					hideOtherVote(data.infos[0].type);
				}
			}
		})
	}
})

function hideOtherVote(type){
	for(var i=0;i<4;i++){
		if(i==parseInt(type)){
			$("#rcxs_title_"+i).click();
			$("#rcxs_title_"+i).css("display","block");
		}else
			$("#rcxs_title_"+i).css("display","none");
	}
	
}

/**
 * 修改初始化选项
 * @param data
 */
function initItems(data,minNum,picpath){
	var len=data.length-2;
	for(var i=0;i<len;i++)
		$("#rcxs_main_0").find(".addxux3").click();
	$(".tpmsg").find("span").html(minNum);
	$(".xsp").html(minNum+"项");
	$.each(data,function(idx,item){
		if(item.pic!="")
			$("#rcxs_main_0").find("table").append('<tr><td><p><label class="xlb3">选项'+(idx+1)+'：'+item.itemName+'</lable></p><div class="addimg"><img src="'+(picpath+item.pic)+'"></div></td></tr>');
		 else
	        $("#rcxs_main_0").find("table").append('<tr><td><p><label class="xlb3">选项'+(idx+1)+'：'+item.itemName+'</lable></p></td></tr>');
	})
}

/**
 * 修改初始化评分
 * @param data
 */
function initScore(data,minNum,criteria,ext,picpath){
	$("#pingfenhight").html(ext.maxScore);
	$("#pingfendi").html(ext.minScore);
	 
	$("#pinfenNumType").html(ext.scoreType==0?"整数型":"小数型");
	$.each(data,function(idx,item){
		if(item.pic!=""){
			$("#rcxs_main_1").find("table").append('<tr><td><p><label class="xlb3">评分对象'+(idx+1)+'：'+item.itemName+'</lable></p><div class="addimg"><img src="'+(picpath+item.pic)+'"></div></td></tr>');
		}else{
			$("#rcxs_main_1").find("table").append('<tr><td><p><label class="xlb3">评分对象'+(idx+1)+'：'+item.itemName+'</lable></p></td></tr>');
		}
	})
	//$(".tpmsg").find("span").html(minNum);
    $("#pingfenbixuan").html(minNum);	
	$("#pingfenmax").html(criteria);
}

/**
 * 修改初始化排名
 * @param data
 * @param ext
 */
function initRank(data,ext,picpath){
	var extLen=ext.length-2; 
	$.each(ext,function(idx,scoreinfo){
	 $($("#rcxs_main_2").find("table")[0]).append('<tr><td><p><label class="xlb3">第'+(idx+1)+'名得分：</label><span class="xsp">'+scoreinfo.score+'分</span><span class="xsp" style="margin-left:30px;">该名次必选项：</span><em class="tpmsg2">投票结果取总分最高第<span style="color:red">'+scoreinfo.count+'</span>名</em></p></td></tr>');
	}); 
	$.each(data,function(idx,item){
		if(item.pic!=""){
			$($("#rcxs_main_2").find("table")[1]).append('<tr><td><p><label class="xlb3">选项'+(idx+1)+'：'+item.itemName+'</lable></p><div class="addimg"><img src="'+(picpath+item.pic)+'"></div></td></tr>');
		}else{
			$($("#rcxs_main_2").find("table")[1]).append('<tr><td><p><label class="xlb3">选项'+(idx+1)+'：'+item.itemName+'</lable></p></td></tr>');
		}
	})
}

function initColleague(data,minNum){	 
		var htm="";
		var htmlll="";
		var htm1="";
		$("#bixuantongs").html("");
		$.each(data,function(i,value){
			htm+='<input type="hidden" value="'+value.userid+'"/>';
			htm1+='<input type="hidden" id="_username'+i+'" value="'+value.itemName+'"/>';
			htmlll+=value.itemName+";";
		});
		 
		$("#bixuantongs").html(minNum+"名");
		$(".tpmsg").find("span").html(minNum);
		 
		$("#hiduserid3").html(htm);						 
		$("#hidusername3").html(htm1);
		$("#showusers3").val(htmlll);
 
}
 
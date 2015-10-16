function updateVote(){
	if(!checkboxSelect(1))
		return; 
	$.ajax({
		url:"validateAuthor",
		type:"POST",
		data:"id="+$("#tabcolor").find('input[type="checkbox"]:checked').val()+"&type=1",
		dataType:"json",
		success:function(data){
			if(data!=undefined&&data.result!=undefined&&data.result==1){
				self.location='publishVote?id='+$("#tabcolor").find('input[type="checkbox"]:checked').val();
			}else {
				/*$("#warnning").html("您只能取回自己发起的且没被投票的活动");
				showjg12();*/
				msgBox("投票中心","您只能取回自己发起的且没被投票的活动");
			}
		}
	})
}

function deleteVoteAfter(){
	if(!checkboxSelect(0))
		return;
	msgDialogTohome({"title":"提示信息","centent":"确认删除勾选中我发起的投票?","okfunction":deleteVote});
}

/**
 * 删除投票活动
 */
function deleteVote(){
	/*if(!checkboxSelect(0))
		return;*/
	var param='';
	$.each($("#tabcolor").find('input[type="checkbox"]:checked'),function(idx,input){
		param+='&ids='+$(input).val();
	})
	param=param.substr(1);
	$.ajax({
		url:"deleteVote",
		type:"POST",
		data:param,
		dataType:"json",
		success:function(data){
			if(data!=undefined&&data.error!=undefined){
				$("#warnning").html("您只能删除自己发起的且没被投票的活动");
				showjg12();
			}else	
				search(1);
		}
	})
}

function selectAll(param){
	if($(param).attr("checked")=="checked")
		$("#tabcolor").find('input[type="checkbox"]').attr("checked","checked");
	else
		$("#tabcolor").find('input[type="checkbox"]').removeAttr("checked");
}

/**
 * 列表选中
 * @param type 0-多选,1-单选
 */
function checkboxSelect(type){
	if($("#tabcolor").find('input[type="checkbox"]:checked').length==0){
		$("#warnning").html("请选中投票");
		showjg12();
		return false;
	}
	if(type==1){
		if($("#tabcolor").find('input[type="checkbox"]:checked').length>1){
			$("#warnning").html("该操作只能选中一条投票");
			showjg12();
			return false;
		}
	}
	return true;
}

function pageClick(){
	search($(".pageol").find("li").find("a.mpages").find("span").html())
}

/**
 * 查询
 * @returns
 */
function search(currpage){
	var param='optType=0';
	if($("#minDate").val()!='')
		param+='&minDate='+convertDate($("#minDate").val());
	if($("#maxDate").val()!='')
		param+='&maxDate='+convertDate($("#maxDate").val());
	if($("#status").val()!="-1")
		param+='&status='+$("#status").val();
	else
		param+='&status=-1';
	if($("#myVoteStatus").val()!="-1")
		param+='&myVoteStatus='+$("#myVoteStatus").val();
	if($("#voteTitle").val().trim()!='')
		param+="&title="+$("#voteTitle").val();
	param+="&page="+(parseInt($("#pageol").find("a.mpages").find("span").html())-1)*10+"&pageSize=10";
	$("#tabcolor").find("tr").remove();
	$.ajax({
		url:"getVoteInfo",
		type:"POST",
		data:param,
		dataType:"json",
		success:function(data){
			if(data.count!=undefined){
				initPages(currpage,data.count,10,pageClick);
				if(data.count>0&&data.infos!=undefined){
					$.each(data.infos,function(idx,vote){
						var h='<tr><td width="80"><input type="checkbox" value="'+vote.id+'">&nbsp;'+(idx+1)+'</td><td width="290"><a href="#" onclick="showVote('+vote.id+')">'+vote.title+'</a></td><td width="150">'+(vote.end_time.replace('-','年').replace('-','月').replace(' ','日 ')+'时')+'</td><td width="100">'+vote.username+'</td><td width="150">'+(vote.start_time.replace('-','年').replace('-','月').replace(' ','日 ')+'时')+'</td><td width="200">';
						if(vote.status==2){
							h+='<a href="#1" onclick="castVote('+vote.id+','+vote.type+','+vote.has_casted+')">'+(vote.has_casted==0?"我要投票":"修改投票")+'</a>';
						}else if(vote.status==1){
							h+='<span href="#1"  style=" color:#3e4d4d;">投票未开始</span>';
						}else if(vote.status==3){
							h+='<span href="#1" style=" color:#3e4d4d;">投票截止</span>';
						}
						    h+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1" onclick="showResult('+vote.id+','+vote.type+')">投票结果</a></td></tr>'
						$("#tabcolor").append(h);
					})
				}
			}
		}
	})
}
/**
 * 显示投票信息
 */
function showVote(id){
	self.location="showVote?id="+id
}



/**
 * 查看投票结果
 * @param id
 * @param type
 */
function showResult(id,type){
	$.ajax({
		url:"validateAuthor",
		type:"POST",
		data:"id="+id+"&type=3",
		dataType:"json",
		success:function(data){
			if(data!=undefined&&data.result!=undefined&&data.result==1){
				if(type==0)
					self.location="itemInfo?id="+id;
				else if(type==1)
					self.location="scoreInfo?id="+id;
				else if(type==2)
					self.location="rankInfo?id="+id;
				else if(type==3)
					self.location="colleagueInfo?id="+id;
			}else {
				$("#warnning").html("您暂无查看权限");
				showjg12();
			}
		}
	})
}

/**
 * 投票
 * @param id
 * @param type
 */
function castVote(id,type,has_casted){
	$.ajax({
		url:"validateAuthor",
		type:"POST",
		data:"id="+id+"&type=2",
		dataType:"json",
		success:function(data){
			if(data!=undefined&&data.result!=undefined&&data.result==1){
				if(type==0)
					self.location="castItem?id="+id+"&casted="+has_casted;
				else if(type==1)
					self.location="castScore?id="+id+"&casted="+has_casted;
				else if(type==2)
					self.location="castRank?id="+id+"&casted="+has_casted;
				else if(type==3)
					self.location="castColleague?id="+id+"&casted="+has_casted;
			}else {
				/*$("#warnning").html("你没有该投票活动的投票权限");
				showjg12();*/
				msgBox("投票中心","你没有该投票活动的投票权限");
			}
		}
	})
	
}

/**
 * 页面初始化
 */
var topDiv;
$(document).ready(function(){
	if($.trim($("#voteidBymsg").val())==""){
	topDiv=$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont");
	search(1);
	}else{
	castVote($("#voteidBymsg").val(),$("#votetypeBymsg").val(),0)
	}
})

function gotoStatistic(ids){
	self.location="statistic?ids="+ids;
}

function showStatistic(){
	if(checkboxSelect(0)){
		var ids=''
		$.each($("#tabcolor").find('input[type="checkbox"]:checked'),function(idx,idinput){
			ids=ids.concat($(idinput).val()).concat(",");
		});
		ids=ids.substr(0,ids.length-1);
		$.ajax({
			url:"validateAuthor",
			type:"POST",
			data:"id="+ids+"&type=4",
			dataType:"json",
			success:function(data){
				
				if(data!=undefined&&data.result!=undefined&&data.result==1){
					self.location="statistic?ids="+ids
				}else {
					if(data.successList==undefined||data.successList.length==0){
						$("#warnning").html("您暂无查看权限");
						showjg12();
					}else{
						ids='';
						$.each(data.successList,function(idx,id){
							ids=ids.concat(id).concat(",");
						})
						ids=ids.substr(0,ids.length-1);
						if(data.faildInfo!=''){
							msgDialogTohome({"title":"提示信息",
								"centent":"您选择的第"+data.faildInfo+"条没有查看权限",
								"okfunction":gotoStatistic,"param":[ids]});
						}
					}
					
				}
			}
		})
	}
}

function publishVote(){
	self.location="publishVote"
}

function showjg12(){
	topDiv.show();
	$(window.top.document.body).addClass("html-body-overflow");
	$("#simScrollCont12").show();
	$("#simScrollContBox12").show();	
}
function hidejg12(){
	topDiv.hide();
	$(window.top.document.body).removeClass("html-body-overflow");
	$("#simScrollContBox12").hide();
	$("#simScrollCont12").hide();
}

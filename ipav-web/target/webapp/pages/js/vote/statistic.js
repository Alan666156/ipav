$(document).ready(function(){
	search(1);
})

/**
 * 查询
 */
function search(currpage){
	var param="&page="+(parseInt($("#pageol").find("a.mpages").find("span").html())-1)*10+"&pageSize=10";
	if($("#minDate").val()!='')
		param+='&minDate='+convertDate($("#minDate").val());
	if($("#maxDate").val()!='')
		param+='&maxDate='+convertDate($("#maxDate").val());
//	param+='&status='+$("#status").val();
	if($("#myVoteStatus").val()!="-1")
		param+='&myVoteStatus='+$("#myVoteStatus").val();
	if($("#voteTitle").val().trim()!='')
		param+="&title="+$("#voteTitle").val();
	if($("#statisticType").val()!="-1")
		param+='&statisticType='+$("#statisticType").val();
	param+="&voteId="+$("#voteId").val();
	$("#tabcolor").find("tr").remove();
	
	$.ajax({
		url:"getVoteStatistic",
		type:"POST",
		data:param,
		dataType:"json",
		success:function(data){
			if(data!=undefined){
				if(data.infos!=undefined){
					initPages(currpage,data.counts,10,pageClick);
					var finished='';
					var unfinished='';
					var finishedsize=0;

					$.each(data.infos,function(idx,statistic){
						if(statistic.finished!=''){
						$.each(statistic.finished.split(","),function(personIdx,person){
							finishedsize++;
						});
						}
					});
						$.each(data.infos,function(idx,statistic){
						finished='';
						unfinished='';
						if(statistic.finished!=''){
							$.each(statistic.finished.split(","),function(personIdx,person){
								finished+='<li>'+person+'</li>';
							})
						}
						if(statistic.unfinished!=''){
							$.each(statistic.unfinished.split(","),function(personIdx,person){
								unfinished+='<li>'+person+'</li>';
							})
						}
						$("#tabcolor").append('<tr align="center"><td width="45">'+(idx+1)+'</td><td width="135">'+statistic.start_time+'</td><td width="130">'+statistic.title+'</td>'+
				                '<td width="130">'+statistic.item_name+'</td><td width="40">'+statistic.seq+'</td><td width="60">'+statistic.score+'</td>'+
				                '<td width="100"><div style="width:80px; height:22px;line-height:22px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+statistic.finished+'</div>'+
				                '<div class="tpreny dn"><div class="tprenylist"><ul>'+finished+'</ul></div></div></td><td width="50">'+statistic.is_chosen+'</td>'+
				                '<td width="75">'+statistic.counts+'</td><td width="75">'+(statistic.counts-statistic.unfinished_counts)+'</td><td width="100"><div style="width:80px; height:22px;line-height:22px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+statistic.unfinished+'</div><div class="tpreny dn"><div  class="tprenylist"><ul>'+unfinished+'</ul></div></td></tr>');
					});
					
					$.each($("#tabcolor").find("tr"),function(idx,tr){
						initVotePeople($($(tr).find("td")[6]));
						initVotePeople($($(tr).find("td")[10]));
						/*initVotePeople($($(tr).find("div.tpreny")[0]).prev());
						initVotePeople($($(tr).find("div.tpreny")[1]).prev());
						initVotePeople($($(tr).find("div.tpreny")[0]));
						initVotePeople($($(tr).find("div.tpreny")[1]));*/
					});
				}
			}
		}
	})
}

function pageClick(){
	search($(".pageol").find("li").find("a.mpages").find("span").html())
}


/*var rowNum;
var cellNum;*/

/**
 * 绑定人员详细信息一入一出事件
 * @param param
 */
function initVotePeople(param){
	$(param).hover(
			function () {
				$($(param).find("div")[1]).css("height","210px");
				$($(param).find("div")[1]).css("overflow","scroll");
				$($(param).find("div")[1]).css("overflowX","hidden");
				$($(param).find("div")[1]).css("display","block");
			},
			function(){
				$($(param).find("div")[1]).css("display","none");
			}
		);
	/*$(param).hover(
		function () {
			if($(param).attr("class")==undefined){
				$(param).next().find("div.tprenylist").css("height","210px");
				$(param).next().find("div.tprenylist").css("overflow","scroll");
				$(param).next().find("div.tprenylist").css("overflowX","hidden");
				$(param).next().css("display","block");
			}
			rowNum=$(param).parent().parent().find("td:first-child").text();
			cellNum=$(param).parent().index();
		},
		function(){
			rowNum=undefined;
			cellNum=undefined;
			if($(param).attr("class")!=undefined){
				$(param).css("display","none");
			}else {
				setTimeout(function(){
					if(rowNum==undefined||parseInt(rowNum)!=parseInt($(param).parent().parent().find("td:first-child").text()))
						if(cellNum==undefined||parseInt(cellNum)!=parseInt($(param).parent().index()))
							$(param).next().css("display","none");
				},1000);
			}
		}
	);*/
}

/**
 * 导出报表
 */
function exportExcel(){
        var form = $("<form>");
        form.attr('style', 'display:none');
        form.attr('target', '');
        form.attr('method', 'post');
        form.attr('action', 'exportVote');
        if($("#minDate").val()!=''){
    		 var input1 = $('<input>');
    	     input1.attr('type', 'hidden');
    	     input1.attr('name', 'minDate');
    	     input1.attr('value', convertDate($("#minDate").val()));
    	     form.append(input1);
    	}
        if($("#minDate").val()!=''){
	   		var input2 = $('<input>');
	   	    input2.attr('type', 'hidden');
	   	    input2.attr('name', 'maxDate');
	   	    input2.attr('value', convertDate($("#maxDate").val()));
	   	    form.append(input2);
	   	}
        var input3 = $('<input>');
   	    input3.attr('type', 'hidden');
   	    input3.attr('name', 'status');
   	    input3.attr('value', $("#status").val());
   	    form.append(input3);
   	    var input4 = $('<input>');
 	    input4.attr('type', 'hidden');
 	    input4.attr('name', 'voteId');
 	    input4.attr('value', $("#voteId").val());
 	    form.append(input4);
        if($("#myVoteStatus").val()!="-1"){
        	var input4 = $('<input>');
	   	    input4.attr('type', 'hidden');
	   	    input4.attr('name', 'myVoteStatus');
	   	    input4.attr('value', $("#myVoteStatus").val());
	   	    form.append(input4);
        }
    	if($("#voteTitle").val().trim()!=''){
        	var input5 = $('<input>');
	   	    input5.attr('type', 'hidden');
	   	    input5.attr('name', 'title');
	   	    input5.attr('value', $("#voteTitle").val());
	   	    form.append(input5);    		
    	}
    	if($("#statisticType").val()!="-1"){
    		var input6 = $('<input>');
	   	    input6.attr('type', 'hidden');
	   	    input6.attr('name', 'statisticType');
	   	    input6.attr('value', $("#statisticType").val());
	   	    form.append(input6);    
    	}
        $('body').append(form);
       
        form.submit();
        form.remove();
}
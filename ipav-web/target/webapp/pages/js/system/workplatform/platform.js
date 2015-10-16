function pageClick(){
	search($(".pageol").find("li").find("a.mpages").find("span").html())
}

function search(currpage){
	var param="&page="+(parseInt($("#pageol").find("a.mpages").find("span").html())-1)*10+"&pageSize=10";
	$("#pttable").find("tr").not(":first").remove();
	$.ajax({
		url:"queryPlatformList",
		type:"POST",
		data:param,
		success:function(data){
			initPages(currpage,data.count,10,pageClick);
			if(data.list!=undefined){
				$.each(data.list,function(idx,item){
					$("#pttable").append('<tr><td align="center"><input type="checkbox" value="'+item.id+'">&nbsp;&nbsp;'+(idx+1)+'</td>'
					        +'<td>'+item.platname+'</td><td>'+item.remark+'</td></tr>');
				});
			}
		}
	});
}

/**
 * 选择校验
 * @param type:0-多选,1-单选
 */
function checkSelected(type){
	if($("#pttable").find('input[type="checkbox"]:checked').length==0){
		//$("#simaltBox3").find("p").html("请选择需要操作的项");
		//showtsBox3();
		msgBox("提示信息","请选择需要操作的项");
		return false;
	}else if(type==1&&$("#pttable").find('input[type="checkbox"]:checked').length>1){
		/*$("#simaltBox3").find("p").html("只能选择一项进行修改操作");
		showtsBox3();*/
		msgBox("提示信息","只能选择一项进行修改操作");
		return false;
	}
	return true;
}

/**
 * 新增平台
 */
function addPlatform(){
	var name=$("#simaltBoxt5").find('input[type="text"]').val();
	if($.trim(name)==''){
		$("#platformInputMsg5").html("平台名不能为空");
		$("#platformInputMsg5").show();
	}else{
		$.ajax({
			url:"platNameIsExist",
			type:"POST",
			data:{"platname":name,"id":$("#simaltBoxt5").find('input[type="hidden"]').val()},
			success:function(data){
		    if(data>0){
		    	$("#platformInputMsg5").html("平台名已经存在,请更换");
				$("#platformInputMsg5").show();
		    }else{
		    	
		    	var param='platName='+$("#simaltBoxt5").find('input[type="text"]').val();
		    	param+='&remark='+($("#simaltBoxt5").find("textarea").val().trim()!=''?$("#simaltBoxt5").find("textarea").val():'');
		    	$.ajax({
		    		url:"addPlatform",
		    		type:"POST",
		    		data:param,
		    		success:function(data){
		    			if(data.result==1){
		    				hidetsBoxt5();
		    				search(1);
		    			}else{
		    				/*$("#warnning").html("平台添加失败");
		    				showtsBox3();*/
		    				msgBox("提示信息","平台添加失败");
		    				
		    			}
		    		}
		    	});
		    	
		    }
	  }
		});
	}	
	
	
}

/**
 * 获取平台详情
 */
function getDetail(){
	if(!checkSelected(1))
		return;
	$.ajax({
		url:"queryPlatformList",
		type:"POST",
		data:"id="+$("#pttable").find('input[type="checkbox"]:checked').val(),
		success:function(data){
			if(data.list!=undefined){
				showtsBoxt6();
				$("#simaltBoxt6").find('input[type="hidden"]').val(data.list[0].id);
				$("#simaltBoxt6").find('input[type="text"]').val(data.list[0].platname);
				$("#simaltBoxt6").find("textarea").val(data.list[0].remark);
			}else{
				$("#warnning").html("获取信息失败");
				showtsBox3();
			}
		}
	});
}

/**
 * 平台修改
 */
function updatePlatform(){
	var name=$("#simaltBoxt6").find('input[type="text"]').val();
	if($.trim(name)==''){
		$("#platformInputMsg6").html("平台名不能为空");
		$("#platformInputMsg6").show();
	}else{
		$.ajax({
			url:"platNameIsExist",
			type:"POST",
			data:{"platname":name,"id":$("#simaltBoxt6").find('input[type="hidden"]').val()},
			success:function(data){
		    if(data>0){
		    	$("#platformInputMsg6").html("平台名已经存在,请更换");
				$("#platformInputMsg6").show();
		    }else{
		    	
		    	var param='id='+$("#simaltBoxt6").find('input[type="hidden"]').val()+'&platName='+$("#simaltBoxt6").find('input[type="text"]').val();
		    	param+='&remark='+($("#simaltBoxt6").find("textarea").val().trim()!=''?$("#simaltBoxt6").find("textarea").val():'');
		    	$.ajax({
		    		url:"addPlatform",
		    		type:"POST",
		    		data:param,
		    		success:function(data){
		    			if(data.result==1){
		    				hidetsBoxt6();
		    				search(1);
		    			}else{
		    				$("#warnning").html("平台修改失败");
		    				showtsBox3();
		    			}
		    		}
		    	});
		    	
		    }
	  }
		});
	}
	
	
}

function delPlatformAfter(){
	if(!checkSelected(0))
		return;
	msgDialog({"title":"提示信息","centent":"确认删除?","okfunction":deleteInfos});
}

/**
 * 删除平台
 */
function deleteInfos(){
	 
	var param='';
	$.each($("#pttable").find('input[type="checkbox"]:checked'),function(idx,id){
		param+='&ids='+$(id).val();
	});
	param=param.substr(1);
	$.ajax({
		url:"deletePlatform",
		type:"POST",
		data:param,
		success:function(data){
			if(data.result==1){
				search(1);
			}else{
				$("#warnning").html("平台删除失败");
				showtsBox3();
			}
		}
	});
}

$(document).ready(function(){
	pageClick();
});

function showtsBox3(){
	$("#simalpha3").show();
	$("#simaltBox3").show();	
}
function  hidetsBox3(){
	$("#simalpha3").hide();
	$("#simaltBox3").hide();
}
function  showtsBoxt5(){
	$("#platformInputMsg5").hide();
	$("#simaltBoxt5").find('input[type="text"]').val("");
	$("#simaltBoxt5").find("textarea").val("");
	$("#simalphat5").show();
	$("#simaltBoxt5").show();	
}
function  hidetsBoxt5(){
	$("#simalphat5").hide();
	$("#simaltBoxt5").hide();
}
function showtsBoxt6(){
	$("#simaltBoxt6").find('input[type="hidden"]').val("");
	$("#simaltBoxt6").find('input[type="text"]').val("");
	$("#simaltBoxt6").find("textarea").val("");
	$("#simalphat6").show();
	$("#simaltBoxt6").show();	
}
function  hidetsBoxt6(){
	$("#simalphat6").hide();
	$("#simaltBoxt6").hide();
	$("#platformInputMsg6").hide();
}
function  showtsBoxt7(){
	$("#simalphat7").show();
	$("#simaltBoxt7").show();	
}
function  hidetsBoxt7(){
	$("#simalphat7").hide();
	$("#simaltBoxt7").hide();
}
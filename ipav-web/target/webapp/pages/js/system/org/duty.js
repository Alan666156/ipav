/**
 * 职务查询
 * @param param
 */
function search(currpage){
	var param="page="+(parseInt($("#pageol").find("a.mpages").find("span").html())-1)*10+"&pageSize=10";
	param+='&status='+$('input[type="radio"]:checked').val();
	$("#pttable").find("tr").not(":first").remove();
	$.ajax({
		url:"queryDutyList",
		type:"post",
		data:param,
		success:function(data){
			if(data.counts!=undefined)
				initPages(currpage,data.counts,10,searchClick);
			if(data.list!=undefined)
				parseList(data.list);
		}
	});
}

/**
 * 查询按钮
 */
function searchClick(){
	search($(".pageol").find("li").find("a.mpages").find("span").html())
	//search(("#pageol").find("a.mpages").find("span").html());
}

/**
 * YYYY-MM-DD转年月日
 * @param param
 * @returns {String}
 */
function convertDate(param){
	param=param.split("-");
	var dateStr=param[0]+'年';
	var month=param[1].charAt(0)=="0"?param[1].substr(1):param[1];
	var date=param[2];
	var date=param[2].charAt(0)=="0"?param[2].substr(1):param[2];
	dateStr+=month+'月'+date+'日';
	return dateStr;
}

/**
 * 职务列表解析
 * @param dutylist
 */
function parseList(dutylist){
	$.each(dutylist,function(idx,duty){
		$("#pttable").append('<tr><td align="center"><input type="checkbox" value="'+duty.id+'">&nbsp;&nbsp;'+(idx+1)+'</td>'
				+'<td>'+duty.duty_name+'</td><td>'+duty.remark+'</td><td>'+(duty.status=="1"?"启用":"禁用")+'</td><td>'+duty.username+'</td><td>'+convertDate(duty.update_time)+'</td></tr>');
	});
	checkEventBind();
}

/**
 * 复选框事件
 */
function checkEventBind(){
	$("#pttable").find('input[type="checkbox"]').unbind("click");
	$.each($("#pttable").find('input[type="checkbox"]'),function(idx,input){
		if(idx==0)
			$(input).bind("click",function(){
				if($(this).attr("checked")=="checked")
					$("#pttable").find("td").find('input[type="checkbox"]').attr("checked","checked");
				else
					$("#pttable").find("td").find('input[type="checkbox"]').removeAttr("checked");
			});
		else{
			$(input).bind("click",function(){
				if($(this).attr("checked")=="checked")
					if($("#pttable").find("td").find('input[type="checkbox"]').not("checked").length==0)
						$("#pttable").find("input:first-child").attr("checked","checked");
				else
					$("#pttable").find("input:first").removeAttr("checked");
			});
		}
	});
}

/**
 * 职务添加
 */
function addDuty(){
	if($("#simaltBoxt5").find('input[type="text"]').val().trim()=='')
		$("#simaltBoxt5").find('input[type="text"]').next().html("职务名称不能为空");
	else{
		var param='dutyname='+$("#simaltBoxt5").find('input[type="text"]').val()+"&remark="+$("#simaltBoxt5").find('textarea').val();
		if($("#dutyId").val()!="")
			param+="&id="+$("#dutyId").val();
		$.ajax({
			url:"insertDuty",
			type:"POST",
			data:param,
			success:function(data){
				if(data.result!=1){
					$("#simaltBox3").find("p").html(data.err);
					showtsBox3();
				}else {
					searchClick();
					hidetsBoxt5();
				}
			}
		});
	}
}

/**
 * 获取职务信息
 */
function getDutyInfo(){
	if(!checkSelect(1))
		return;
	showtsBoxt5();
	$.ajax({
		url:"queryDutyList",
		type:"POST",
		data:"id="+$("#pttable").find("td").find('input[type="checkbox"]:checked').val(),
		success:function(data){
			if(data.list!=undefined){
				showtsBoxt5();
				$("#simaltBoxt5").find('input[type="text"]').val(data.list[0].duty_name);
				$("#simaltBoxt5").find('textarea').val(data.list[0].remark);
				$("#dutyId").val($("#pttable").find("td").find('input[type="checkbox"]:checked').val());
			}else{
				$("#simaltBox3").find("p").html("该职务信息不存在");
				showtsBox3();
				searchClick();
			}
		}
	})
}

/**
 * 复选框校验
 * @param type:1-单选,0-复选
 */
function checkSelect(type){
	if($("#pttable").find("td").find('input[type="checkbox"]:checked').length==0){
		$("#simaltBox3").find("p").html("至少选择一项职务");
		showtsBox3();
		return false;
	}
	if(type==1&&$('input[type="checkbox"]:checked').length>1){
		$("#simaltBox3").find("p").html("只能选择一项职务进行修改");
		showtsBox3();
		return false;
	}
	return true;
}

/**
 * 状态删除
 */
function delStatus(status){
 if(!checkSelect(0))	return;
 if(status==-1){	
	 msgDialog({"title":"提示信息","centent":"确认删除?","okfunction":updateStatus,"param":"-1"});
 }
}

/**
 * 状态修改
 * @param status
 */
function updateStatus(status){
	if(!checkSelect(0))
		return;
	var param="status="+status;
	$.each($('input[type="checkbox"]:checked'),function(idx,input){
		param+="&ids="+$(input).val();
	});
	$.ajax({
		url:"updateDutyStatus",
		type:"POST",
		data:param,
		success:function(data){
			if(data.result>0){
				searchClick();
				hidetsBoxt5();
			}else {
				$("#simaltBox3").find("p").html((status==0?"禁用":(status==1?"启用":"删除"))+"职务失败");
				showtsBox3();
			}
		}
	});
}

/**
 * 模板下载
 */
function exportTemplet(){
	window.open("exportTemplet");
}

/**
 * 职务信息导出
 */
function exportDutyList(){
	window.open('exportDutyList?status='+$('input[type="radio"]:checked').val());
}

function importDutyList(){
	if($("#excelFile").val().lastIndexOf(".xls")<0&&$("#excelFile").val().lastIndexOf(".xlsx")<0){
		$("#simaltBox3").find("p").html("请导入正确的excel文件");
		showtsBox3();
		return;
	}
	$.ajaxFileUpload({
        url: 'importDutyInfos', 
        type: 'post',
        secureuri: false, //一般设置为false
        fileElementId: 'excelFile', // 上传文件的id、name属性名
        dataType: 'json', //返回值类型
        data:{'fileName':$("#excelFile").val()},
        success: function(data, status){  
        	if(data.err!=undefined){
        		$("#simaltBox3").find("p").html(data.err);
				showtsBox3();
        	}else{ 
        		if(data.result>0)
	        		searchClick();
        		hidetsBoxt7();
        		if(data.exitsNames!=undefined&&data.exitsNames.length>0){
	        		clearImportErrLists();
					$.each(data.exitsNames,function(idx,obj){
						$("#copymag").find("table").append('<tr><td width="100" align="center">'+(idx+1)+'</td><td width="703" style="text-align:left; text-indent:2em;">职务名称: '+obj.duty_name+' 已存在</td></tr>');
					});
					showjg13();
        		}
        	}
        },
        error: function(data, status, e){ 
        	if(data.err!=null){
        		$("#simaltBox3").find("p").html(data.err);
				showtsBox3();
        	}else{ 
        		if(data.result>0)
	        		searchClick();
        		hidetsBoxt7();
        		if(data.exitsNames!=undefined&&data.exitsNames.length>0){
	        		clearImportErrLists();
					$.each(data.exitsNames,function(idx,obj){
						$("#copymag").find("table").append('<tr><td width="100" align="center">'+(idx+1)+'</td><td width="703" style="text-align:left; text-indent:2em;">职务名称: '+obj.duty_name+' 已存在</td></tr>');
					});
					showjg13();
        		}
        	}
        }
    });
}

/**
 * 清楚错误列表内容
 */
function clearImportErrLists(){
	$.each($("#copymag").find("table").find("tr"),function(idx,tr){
		if(idx>0)
			$(tr).remove();
	})
}

$(document).ready(function(){
	$('input[type="radio"]').bind("click",function(){
		searchClick();
	})
	search(1);
})

function showtsBox3(){
	$("#simalpha3").show();
	$("#simaltBox3").show();	
}
function hidetsBox3(){
	$("#simalpha3").hide();
	$("#simaltBox3").hide();
}

function showtsBoxt5(){
	$("#simaltBox3").find("p").html("");
	$("#dutyId").val("");
	$("#simaltBoxt5").find('input[type="text"]').val("");
	$("#simaltBoxt5").find('textarea').val("");
	$("#simalphat5").show();
	$("#simaltBoxt5").show();	
}
function hidetsBoxt5(){
	$("#simalphat5").hide();
	$("#simaltBoxt5").hide();
}

function showtsBoxt7(){
	$("#simaltBoxt7").find('input[type="text"]').val("");
	$("#simaltBoxt7").find('input[type="file"]').val("");
	$("#simalphat7").show();
	$("#simaltBoxt7").show();	
}
function hidetsBoxt7(){
	$("#simalphat7").hide();
	$("#simaltBoxt7").hide();
}

function showjg13(){
	$("#simScrollCont13").show();
	$("#simScrollContBox13").show();	
}
function hidejg13(){
	$("#simScrollContBox13").hide();
	$("#simScrollCont13").hide();
}
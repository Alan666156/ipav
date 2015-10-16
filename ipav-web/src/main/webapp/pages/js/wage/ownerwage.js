 
/**
 * 页面初始化
 */
var topDiv;
topDiv=$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont");
$(document).ready(function() {
	topDiv=$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont");
	var today=new Date();
	var today=new Date();
	var month=today.getMonth()+1;
	$("#minMonth").val(today.getFullYear()+'年'+month+'月');
	$("#maxMonth").val(today.getFullYear()+'年'+month+'月');
	month=month>9?month:('0'+month);
	if($("#idInput").val()==undefined|| $("#idInput").val()==''){
	search('type=1&page=0&pageSize=10&minMonth='+(today.getFullYear()+''+month)+'&maxMonth='+(today.getFullYear()+''+month),1);
    }else{
    search('type=1&page=0&pageSize=10&id='+$("#idInput").val(),1);
    getWageRecordInfo($("#idInput").val())
    }
	//$("#myWage",window.parent.document).attr("height",$("body").height());
});

/**
 * 查询按钮事件
 */
function searchClick(){
	var param='type=1';
	if($("#minMonth").val()!=""){
		if($("#minMonth").val().length==8)
			param+="&minMonth="+$("#minMonth").val().replace("年","").replace("月","");
		else
			param+="&minMonth="+$("#minMonth").val().replace("年","0").replace("月","");
	}
	if($("#maxMonth").val()!=""){
		if($("#maxMonth").val().length==8)
			param+="&maxMonth="+$("#maxMonth").val().replace("年","").replace("月","");
		else
			param+="&maxMonth="+$("#maxMonth").val().replace("年","0").replace("月","");
	}
	param+="&page="+(parseInt($("#pageol").find("a.mpages").find("span").html())-1)*10+"&pageSize=10";
	$("#wagelist").find("tr").remove();
	search(param,$(".pageol").find("li").find("a.mpages").find("span").html());
}

/**
 * 获取列表数据
 * 
 * @param param
 */
function search(param,currpage) {
	$.ajax({
		type : 'POST',
		url : 'getWages',
		data : param,
		success : function(data) {
			initPages(currpage,data.counts,10,searchClick);
			parser(data);
		}
	})
}

/**
 * 列表数据填充
 * 
 * @param param
 */
function parser(param) {
	if (param.error == undefined) {
		if (param.info != undefined) {
			$("#wagelist").find("tr").remove();
			$($("#wagePage").find("label")[0]).html(param.counts)
			var wageMonth='';
			var wageTime='';
			$
					.each(
							param.info,
							function(idx, element) {
								wageMonth=''+element.wage_month;
								if(wageMonth.charAt(4)=='0')
									wageMonth=wageMonth.substr(0,4)+'年'+wageMonth.substr(5)+'月';
								else
									wageMonth=wageMonth.substr(0,4)+'年'+wageMonth.substr(4)+'月';
								wageTime=''+element.wage_time;
								wageTime=wageTime.substr(0,4)+"年"+wageTime.substr(5,2)+"月"+wageTime.substr(8)+"日";
								wageTime=wageTime.replace("年0","年").replace("月0","月");
								$("#wagelist")
										.append(
												'<tr title="点击查看明细" onClick="getWageRecordInfo('+element.id+')"><td width="100"><input type="hidden" value="'+element.id+'">&nbsp;&nbsp;'+(idx+1)+'</td><td width="235">'
														+ wageMonth
														+ '</td><td width="235">'
														+ wageTime
														+ '</td><td width="200" align="left">'
														+ element.should_pay
														+ '</td><td width="200" align="left">'
														+ element.net_income
														+ '</td></tr>');
							});
		}
	} else {
		$("#warnning").html(param.error);
		showjg12();
	}
}

/**
 * 工资列表点击查看详情
 * @param id
 * @param type 1-修改
 */
function getWageRecordInfo(id){
	$("#myWageInfo").find("tr").remove();
	$.ajax({
		type : 'POST',
		url : 'getWages',
		data : "type=1&id="+id,
		success : function(data) {
			
			if(data.error!=undefined){
				$("#warnning").html(data.error);
				showjg12();
			}else{
				var wageMonth=''+data.info[0].wage_month;
				if(wageMonth.charAt(4)=='0')
					wageMonth=wageMonth.substr(0,4)+'年'+wageMonth.substr(5)+'月';
				else
					wageMonth=wageMonth.substr(0,4)+'年'+wageMonth.substr(4)+'月';
				$("#simScrollContBox7").find("div .tctitle").find("label").html("工资期间:"+wageMonth);
				$("#myWageInfo").append('<tr><td width="100">1</td><td width="235">应付工资</td><td width="235" align="left">'+data.info[0].should_pay+'</td><td width="200">'+(data.info[0].should_pay_user.username==undefined?"":data.info[0].should_pay_user.username)+'</td></tr>');
				$("#myWageInfo").append('<tr><td width="100">2</td><td width="235">实付工资</td><td width="235" align="left">'+data.info[0].net_income+'</td><td width="200">'+(data.info[0].net_income_user.username==undefined?"":data.info[0].net_income_user.username)+'</td></tr>');
				
				if(data.info[0].ext_wage!=undefined){
					$.each(data.info[0].ext_wage,function(idx,element){
						$("#myWageInfo").append('<tr><td width="100">'+(idx+3)+'</td><td width="235">'+element.title+'</td><td width="235" align="left">'+element.value+'</td><td width="200">'+element.username+'</td></tr>');
					})
				}
				showjg4();
			}
		}
	})
}

function showjg4(){
	topDiv.show();
	$("#simScrollCont7").show();
	$("#simScrollContBox7").show();	
}
function hidejg4(){
	topDiv.hide();
	$("#simScrollContBox7").hide();
	$("#simScrollCont7").hide();
}

function showjg12(){
	topDiv.show();
	$("#simScrollCont12").show();
	$("#simScrollContBox12").show();	
}
function hidejg12(){
	topDiv.hide();
	$("#simScrollContBox12").hide();
	$("#simScrollCont12").hide();
}
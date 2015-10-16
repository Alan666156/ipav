/**
 * 自定义map
 * @returns {___anonymous61_64}
 */
function getMap() {
	var map_ = new Object();  
	map_.put = function(key, value) {  
		map_[key+'_'] = value;  
	};  
	map_.get = function(key) {  
		return map_[key+'_'];  
	};  
	map_.remove = function(key) {  
		delete map_[key+'_'];  
	};  
	map_.keyset = function() {  
		var ret = "";  
		for(var p in map_) {  
			if(typeof p == 'string' && p.substring(p.length-1) == "_") {  
				ret += ",";  
				ret += p.substring(0,p.length-1);  
			}  
		}  
		if(ret == "") {  
			return ret.split(",");  
		} else {  
			return ret.substring(1).split(",");  
		}  
	};  
	return map_;  
}
/*******************组织机构********************/
var orgMap=getMap();
var setting = {
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	view: {
		dblClickExpand: false
	},
	data: {
		simpleData: {
			enable: true,
			idkey:"id",
			pIdKey:"Pid",
			nameKey:"name",
			rootPId:""
		}
	},
	callback: {
		onClick: onClick,
		onCheck: onCheck
	}
};

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "";
	id  ="";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		id +=nodes[i].id +",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (id.length > 0 ) id = id.substring(0,id.length-1);
	$("#orgselect").attr("value",v);
	$("#orgno").attr("value",id);
}

function showMenu() {
	var orgObj = $("#orgselect");
	var orgOffset = $("#orgselect").offset();
	$("#menuContent").css({left:orgOffset.left + "px", top:orgOffset.top + orgObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "orgselect" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}

/**
 * 组织架构内容初始化
 */
function initOrg(){
	$.ajax({
		type : 'POST',
		url : 'getDepartments',
		success : function(data) {
			if(data.error==undefined){
				$.fn.zTree.init($("#treeDemo"), setting, eval(data.info));
				/*$.each(data.info,function(idx,org){
					orgMap.put(org.id,org.orgid);
				});*/
			}
		}
	})
}
/***********************************************/
/**
 * 页面初始化
 */
var topDiv;
$(document).ready(function() {
	topDiv=$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont");
	initLevelInfos()
	var today=new Date();
	var month=today.getMonth()+1;
	$("#wageMonthCon").val(today.getFullYear()+'年'+month+'月');
	month=month>9?month:('0'+month);
	search("type=0&page=0&pageSize=10&wageMonthCon="+(today.getFullYear()+''+month),1);
	initOrg();
});

var levelInfos;

/**
 * 初始化层级信息
 */
function initLevelInfos(){
	if(levelInfos!=undefined)
		return;
	$.ajax({
		type:"POST",
		url:"initLevelInfos",
		success:function(data){
			if(data.error==undefined){
				levelInfos=data.info;
			}
		}
	})
}

/**
 * 显示层级树
 */
function showLevels(){
	$("#resolve_u").css("display","block");
	if(levelInfos==undefined)
		initLevelInfos();
	$("#resolve_u").find("ul").remove();
	var tree=$('<ul id="tree" name="treeArea" onblur="closeTree()"></ul>');
	recurring(tree,levelInfos);
	$("#resolve_u").append(tree);
	$.each($(tree).find("li"),function(idx,li){
		if(idx==0){
			$($(li).find("span")[0]).attr("class","company");
			$($(li).find("a")[0]).attr("class","current");
		}
	})
	$("#resolve_u").hover(
		function () {
			  
		},
		function(){
			closeTree();
		});
}

/**
 * 关闭树
 */
function closeTree(){
	$("#resolve_u").find("ul").remove();
	$("#resolve_u").css("display","none");
}

function addNewWageExt(){
	addWageExt();
	addExtWage();
}

/**
 * 递归层级结构
 * @param tree
 * @param obj
 * @returns
 */
function recurring(tree,obj){
	$.each(obj,function(idx,node){
		var li=$('<li name="treeArea"></li>');
		var input=$("<input>");
		input.attr('type', 'hidden');
		input.attr('value', node.id);
		li.append(input);
//		li.append('<a href="#1"><label>'+node.name+'</label>')
		if(node.type==1){
			li.append('<p name="treeArea" class="treezk" onclick="changeTree(this)"></p><a href="#1" name="treeArea" onclick="treeSelect(this)"><span name="treeArea" class="department" onclick="treeSelect(this)"></span><label name="treeArea">'+node.name+'</label></a>');
//			li.attr("onclick","focusInput()");
		}else {
			li.append('<a name="treeArea" href="#1" onclick="treeSelect(this)"><span name="treeArea" class="usersp" onclick="treeSelect(this)"></span><label name="treeArea">'+node.name+'</label></a>');
		}
		if(node.type==1&&node.nodes.length>0){
			var nodeTree=$('<ul name="treeArea"></ul>');
//			$(nodeTree).css("display","none");
			nodeTree=recurring(nodeTree,node.nodes);
			li.append(nodeTree);
		}
		tree.append(li);
	})
	return tree;
}

/**
 * 选中释疑人
 * @param param
 */
function treeSelect(param){
	$("#resolve_u").find("a").removeAttr("class");
	if(param.nodeName=="A"){
		if($(param).find("span").attr("class")=="usersp"){
			$("#simScrollContBox6").find("td").find(".ptmore").val($(param).find("label").html());
			$("#resolve_person").val($(param).prev().val())
			closeTree();
		}
		$(param).attr("class","current");
	}else{
		if($(param).attr("class")=="usersp"){
			$("#simScrollContBox6").find("td").find(".ptmore").val($(param).parent().find("label").html());
			$("#resolve_person").val($(param).parent().prev().val())
			closeTree();
		}
		$(param).parent().attr("class","current");
	}
}

/**
 * 打开或收缩树
 * @param param
 */
function changeTree(param){
	if($(param).attr("class")=='treezd'){
		$(param).attr("class","treezk");
		$($(param).parent().find("ul")[0]).css("display","block");
	}else{
		$(param).attr("class","treezd");
		$($(param).parent().find("ul")[0]).css("display","none");
	}
}


/************************************************机构展示******************************************/

function showOrgLevels(){
	$("#orgTree").css("display","block");
	if(levelInfos==undefined)
		initLevelInfos();
	$("#orgTree").find("ul").remove();
	var tree=$('<ul id="tree" name="treeArea" onblur="closeTree()"></ul>');
	recurringOrg(tree,levelInfos);
	$("#orgTree").append(tree);
	$.each($(tree).find("li"),function(idx,li){
		if(idx==0){
			$($(li).find("span")[0]).attr("class","company");
			$($(li).find("a")[0]).attr("class","current");
		}
	})
	$("#orgTree").hover(
		function () {
			  
		},
		function(){
			closeOrgTree();
		});
}

/**
 * 关闭树
 */
function closeOrgTree(){
	$("#orgTree").find("ul").remove();
	$("#orgTree").css("display","none");
}

/**
 * 递归层级结构
 * @param tree
 * @param obj
 * @returns
 */
function recurringOrg(tree,obj){
	$.each(obj,function(idx,node){
		var li=$('<li name="treeArea"></li>');
		var input=$("<input>");
		input.attr('type', 'hidden');
		input.attr('value', node.no);
		li.append(input);
		if(node.type==1){
			li.append('<p name="treeArea" class="treezk" onclick="changeOrgTree(this)"></p><a href="#1" name="treeArea" onclick="treeOrgSelect(this)"><span name="treeArea" class="department" onclick="treeOrgSelect(this)"></span><label name="treeArea">'+node.name+'</label></a>');
		}
		if(node.type==1&&node.nodes.length>0){
			var nodeTree=$('<ul name="treeArea"></ul>');
			nodeTree=recurringOrg(nodeTree,node.nodes);
			li.append(nodeTree);
		}
		tree.append(li);
	})
	return tree;
}

/**
 * 选中释疑人
 * @param param
 */
function treeOrgSelect(param){
	$("#orgTree").find("a").removeAttr("class");
	if(param.nodeName=="A"){
		$("#orgno").prev().prev().val($(param).find("label").html())
		$("#orgno").val($(param).prev().prev().val());
		closeOrgTree();
		$(param).attr("class","current");
	}else{
		$("#orgno").prev().prev().val($(param).find("label").html())
		$("#orgno").val($(param).parent().prev().prev().val());
		closeOrgTree();
		$(param).parent().attr("class","current");
	}
}

/**
 * 打开或收缩树
 * @param param
 */
function changeOrgTree(param){
	if($(param).attr("class")=='treezd'){
		$(param).attr("class","treezk");
		$($(param).parent().find("ul")[0]).css("display","block");
	}else{
		$(param).attr("class","treezd");
		$($(param).parent().find("ul")[0]).css("display","none");
	}
}

/************************************************************************************************/


var preInput='';

/**
 * 用户姓名自动补全
 */
function autoCompleteUser(){
	if(preInput.trim()!=$("#username").val().trim())
		setTimeout("delayMethod()", 1000);
}

/**
 * 延时执行方法
 */
function delayMethod(){
	if(preInput.trim()!=$("#username").val().trim()){
		preInput=$("#username").val().trim();
		getUserNames();
	}		
}

/**
 * 获取姓名列表
 */
function getUserNames(){
	$($("#prompt").find("ul")[0]).find("li").remove();
	$.ajax({
		type : 'POST',
		url : 'autoCompleteUser',
		data : 'userName='+preInput,
		success : function(data) {
			if(data.error==undefined){
				if(data.info!=undefined&&data.info.length>0){
					$("#prompt").find("ul").find("li").remove();
					$.each(data.info,function(idx,name){
						$($("#prompt").find("ul")[0]).append('<li><a href="#1" onclick="nameSelect(this);">'+name+'</a></li>');
					})
				}
			}
		}
	})
}

/**
 * 名字回填到员工名字框中
 */
function nameSelect(param){
	$("#username").val($(param).html());
	$("#prompt").find("ul").find("li").remove();
}

/******************************************工资页面*******************************************************/

/**
 * 工资导出
 */
function exportRecords(){
	if($("#wagelist").find("tr").length<=1){
		$("#warnning").html("请选择工资记录");
		showjg12();
		return;
	}
        var form = $("<form>");
        form.attr('style', 'display:none');
        form.attr('target', '');
        form.attr('method', 'post');
        form.attr('action', 'exportWR');
        var param='';
        if($("#wageMonthCon").val()!=""){
    		if($("#wageMonthCon").val().length==8)
    			param=$("#wageMonthCon").val().replace("年","").replace("月","");
    		else
    			param=$("#wageMonthCon").val().replace("年","0").replace("月","");
    		 var input1 = $('<input>');
    	     input1.attr('type', 'hidden');
    	     input1.attr('name', 'wageMonthCon');
    	     input1.attr('value', param);
    	     form.append(input1);
    	}
        
        if($("#orgselect").val().trim()!=''){
//    		if(orgMap==undefined)
//    			initOrg();
//    		param=orgMap.get($("#orgno").val());
    		var input2 = $('<input>');
    		input2.attr('type', 'hidden');
    		input2.attr('name', 'orgno');
    		input2.attr('value', $("#orgno").val());
    		form.append(input2);
    	}
    	if($("#username").val().trim()!=''){
    		param=$("#username").val();
    		var input3 = $('<input>');
    		input3.attr('type', 'hidden');
    		input3.attr('name', 'userName');
    		input3.attr('value', param);
    		form.append(input3);
    	}
        $('body').append(form);
        form.submit();
        form.remove();
}

/**
 * 查询按钮事件
 */
function searchClick(){
	var param='type=0';
	if($("#wageMonthCon").val()!=""){
		if($("#wageMonthCon").val().length==8)
			param+="&wageMonthCon="+$("#wageMonthCon").val().replace("年","").replace("月","");
		else
			param+="&wageMonthCon="+$("#wageMonthCon").val().replace("年","0").replace("月","");
	}
	if($('#orgselect').val().trim()!=''){
		param+="&orgno="+$("#orgno").val();
	}
	if($("#username").val().trim()!=''){
		param+="&userName="+$("#username").val();
	}
	param+="&page="+(parseInt($("#pageol").find("a.mpages").find("span").html())-1)*10+"&pageSize=10";
	var currpage=$("#pageol").find("a.mpages").find("span").html();
	search(param,currpage);
}

/**
 * 获取列表数据
 * 
 * @param param
 */
function search(param,currpage) {
//	clear();
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
 * 清空列表
 */
function clear() {
	$($("#wagePage").find("label")[0]).html(0);
	$($($("#wagelist").find("tr")[0]).find("label")[0]).html(0);
	$($($("#wagelist").find("tr")[0]).find("label")[1]).html(0);
	$.each($("#wagelist").find("tr"), function(idx, tr) {
		if (idx > 0)
			$(tr).remove();
	});
	$.each($("#wageTitles").find("th"),function(idx,th){
		if (idx > 6)
			$(th).remove();
	});
	$.each($($("#wagelist").find("tr")[0]).find("td"),function(idx,td){
		if (idx > 6)
			$(td).remove();
	})
	
}

/**
 * 分页选择
 */
function pageSelect() {
	$($(this).find("span")[0]).html();
}

/**
 * 全部工资选择或取消
 * @param param
 */
function allWageSelect(param) {
	if ($(param).attr("checked") == "checked")
		$("#wagelist").find("input").attr("checked", "true");
	else
		$("#wagelist").find("input").removeAttr("checked");
}

/**
 * 列表数据填充
 * 
 * @param param
 */
function parser(param) {
	if (param.error == undefined) {
		clear();
		if(param.titleArr!=undefined){
			$.each(param.titleArr,function(idx,title){
				$("#wageTitles").append('<th width="115" style="text-align: left" align="left">'+title+'</th>');
			})
		}
		if (param.info != undefined) {
			
			$($("#wagePage").find("label")[0]).html(param.counts)
			$($($("#wagelist").find("tr")[0]).find("label")[0]).html(
					param.should_pay_sum);
			$($($("#wagelist").find("tr")[0]).find("label")[1]).html(
					param.net_income_sum);
			
			if(param.extSumArr!=undefined){
				$.each(param.extSumArr,function(idx,extSum){
					$($("#wagelist").find("tr")[0]).append('<td width="115" align="left"><label class="red">'+extSum+'</label></td>');
				})
			}
			var ext_info='';
			var wageMonth='';
			$
					.each(
							param.info,
							function(idx, element) {
								wageMonth=''+element.wage_month;
								if(wageMonth.charAt(4)=='0')
									wageMonth=wageMonth.substr(0,4)+'年'+wageMonth.substr(5)+'月';
								else
									wageMonth=wageMonth.substr(0,4)+'年'+wageMonth.substr(4)+'月';
								
								var tr=$('<tr title="点击查看明细"><td align="center"><input type="checkbox" name="recordsCheck" value="'+element.id+'">&nbsp;&nbsp;'+(idx+1)+'</td><td onClick="getWageRecordInfo('+element.id+',0)">'
										+ wageMonth
										+ '</td><td onClick="getWageRecordInfo('+element.id+',0)">'
										+ element.orgname
										+ '</td><td onClick="getWageRecordInfo('+element.id+',0)">'
										+ element.userno
										+ '</td><td onClick="getWageRecordInfo('+element.id+',0)">'
										+ element.username
										+ '</td><td align="left" onClick="getWageRecordInfo('+element.id+',0)">'
										+ element.should_pay
										+ '</td><td align="left" onClick="getWageRecordInfo('+element.id+',0)">'
										+ element.net_income
										+ '</td></tr>')
								
								if(element.ext_wage!=undefined&&element.ext_wage!=''){
									$.each(element.ext_wage,function(extIdx,extWage){
										$(tr).append('<td align="left" onClick="getWageRecordInfo('+element.id+',0)" title="点击查看详细">'+extWage.value+'</td>');
										
									});
								}
								$("#wagelist").append(tr);
							});
		}
	} else {
		$("#warnning").html(param.error);
		showjg12();
	}
}

/**
 * 导出模板
 */
function exportExcel(){
	window.open("exportWT")
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

/**
 * 导入工资
 */
function importExcel(){
	if($("#wageMonth").val()=="")
		$("#wageMonth").parent().append('<p class="errormsg">请选择导入工资所属月份</p>');
	else if($("#wageTime").val()=="")
		$("#wageTime").parent().append('<p class="errormsg">请选择工资发放日期</p>');
	else if($("#xlsFile").val()=="")
		$("#xlsFile").parent().append('<p class="errormsg">请选择工资excel</p>');
	else{
		$.ajaxFileUpload({
            url: 'importWages', 
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: 'wageFile', // 上传文件的id、name属性名
            dataType: 'json', //返回值类型
            data:{'wageMonth':$("#wageMonth").val(),'wageTime':$("#wageTime").val()},
            success: function(data, status){  
            	if(data!=undefined){
            		if(data.error!=undefined){
            			if(data.errlist==undefined){
		            		$("#warnning").html(data.error);
		            		showjg12();
            			}else{
            				clearImportErrLists();
            				$.each(data.errlist,function(idx,err){
            					$("#copymag").find("table").append('<tr><td width="100" align="center">'+(idx+1)+'</td><td width="703" style="text-align:left; text-indent:2em;">'+err+'</td></tr>');
            				});
            				showjg13();
            			}
            		}else{
            			$("#wageMonthCon").val($("#wageMonth").val());
            			searchClick();
            		}
            	}
            },
            error: function(data, status, e){ 
            	if(data!=undefined){
            		if(data.error!=undefined){
            			if(data.errlist==undefined){
		            		$("#warnning").html(data.error);
		            		showjg12();
            			}else{
            				clearImportErrLists();
            				$.each(data.errlist,function(idx,err){
            					$("#copymag").find("table").append('<tr><td width="100" align="center">'+(idx+1)+'</td><td width="703" style="text-align:left; text-indent:2em;">'+err+'</td></tr>');
            				});
            				showjg13();
            			}
            		}
            	}
            }
        });
		searchClick();
		hidejg2();
	}
}

function fileSelect(param){
	$("#xlsFile").val($(param).val());
	if($("#xlsFile").val().trim()!="")
		$("#xlsFile").parent().find("p").remove();
}

/**
 * 清除导入工资警告
 * @param param
 */
function clearImportWarnning(param){
	$(param).siblings().remove();
}

/**
 * 工资复选框选中验证
 * @param type 1-单选
 * @returns {Boolean}
 */
function checkRecordsSelect(type){
	var flag=true;
	if($('input[name="recordsCheck"]:checked').length==0){
		$("#warnning").html("请选择工资记录");
		flag=false;
	}else if($('input[name="recordsCheck"]:checked').length>1&&type==1){
		$("#warnning").html("只能选择一条工资记录进行操作");
		flag=false;
	}
	if(!flag)
		showjg12();
	return flag;
}

/**
 * 删除选中的工资自定义项
 */
function deleteWageRecords(){
	if(checkRecordsSelect(0)){
		
		var param='optType=0';
		$.each($('input[name="recordsCheck"]:checked'),function(idx,element){
			param+='&id='+$(element).val();
		})
		$.ajax({
			type : 'POST',
			url : 'updateWI',
			data : param,
			success : function(data) {
				if(data.error!=undefined){
					$("#warnning").html(data.error);
					showjg12();
				}else
					searchClick();
			}
		})
	}
}

/**
 * 查看按钮获取工资详情
 * @param type 1-修改
 */
function getWageRecordInfoByChecked(type){
	if(checkRecordsSelect(1))
		getWageRecordInfo($($('input[name="recordsCheck"]:checked')[0]).val(),type);
	else{
		$("#warnning").html("只能选择一项进行该操作");
		showjg12();
	}
}

/**
 * 清除工资自定义项
 */
function clearWageInfoExt(){
	$.each($("#yourTableID").find('tr'),function(idx,element){
		if(idx>2)
			$(element).remove();
	});
}

/**
 * 修改工资
 */
function updateWageRecords(){
	
	if(checkRecordsSelect(1)){
		
		var param='optType=1';
		param+='&id='+$("#recordId").val();
		param+='&shouldPay='+$($($("#yourTableID").find('tr')[2]).find('input')[0]).val();
		param+='&netIncome='+$($($("#yourTableID").find('tr')[2]).find('input')[1]).val();
		var json='[';
		$.each($("#yourTableID").find("td").find("input[name='extWageJson']"),function(idx,extJSON){
			//json+='{"id":'+$(extJSON).parent().prev().find("input").val()+'"title":"'+$(extJSON).parent().prev().text().replace("：","")+'","value":'+$(extJSON).val()+'},';
			json+='{"title":'+$(extJSON).parent().prev().find("input").val()+',"value":'+$(extJSON).val()+'},';
		})
		if(json.charAt(json.length-1)==",")
			json=json.substr(0,json.length-1);
		json+=']';
		if(json.length>2)
			param+="&extWage="+json;
		$.ajax({
			type : 'POST',
			url : 'updateWI',
			data : param,
			success : function(data) {
				if(data.error!=undefined){
					$("#warnning").html(data.error);
					showjg12();
				}else{
					hidejg8();
					searchClick();
				}
			}
		})
	}
}

/**
 * 工资列表点击查看详情
 * @param id
 * @param type 1-修改
 */
function getWageRecordInfo(id,type){
	clearWageInfoExt();
	$.ajax({
		type : 'POST',
		url : 'getWages',
		data : "type=0&id="+id,
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
				$("#simScrollContBox8").find("div .tctitle").find("label").html("工资期间:"+wageMonth);
				$("#recordId").val(data.info[0].id);
				$($($("#yourTableID").find('tr')[0]).find('td')[1]).html(wageMonth);
				$($($("#yourTableID").find('tr')[0]).find('td')[3]).html(data.info[0].orgname);
				$($($("#yourTableID").find('tr')[1]).find('td')[1]).html(data.info[0].userno);
				$($($("#yourTableID").find('tr')[1]).find('td')[3]).html(data.info[0].username);
				if(type==1){
					$($($("#yourTableID").find('tr')[2]).find('input')[0]).removeAttr("readonly");
					$($($("#yourTableID").find('tr')[2]).find('input')[1]).removeAttr("readonly");
					$($($("#yourTableID").find('tr')[2]).find('input')[0]).val(data.info[0].should_pay);
					$($($("#yourTableID").find('tr')[2]).find('input')[1]).val(data.info[0].net_income);
					if(data.info[0].ext_wage!=undefined&&data.info[0].ext_wage!=''){
						var row=parseInt((data.info[0].ext_wage.length+1)/2);
						var is_odd=data.info[0].ext_wage.length%2;
						for(var i=0;i<=row;){
							if((i==row-1)&&(is_odd==1)){
								$("#yourTableID").append('<tr><td align="right"><input type="hidden" value="'+data.info[0].ext_wage[i].id+'"/>'+data.info[0].ext_wage[i].title+'：</td><td colspan="3"><input type="text" name="extWageJson" style="border:0px; height:30px; width:100%; text-indent:10px;" value="'+data.info[0].ext_wage[i].value+'" /></tr>');
							}else{
								$("#yourTableID").append('<tr><td align="right"><input type="hidden" value="'+data.info[0].ext_wage[i].id+'"/>'+data.info[0].ext_wage[i].title+'：</td><td><input type="text" name="extWageJson" style="border:0px; height:30px; width:100%; text-indent:10px;" value="'+data.info[0].ext_wage[i].value+'" /></td><td align="right"><input type="hidden" value="'+data.info[0].ext_wage[i+1].id+'"/>'+data.info[0].ext_wage[i+1].title+'：</td><td><input type="text" name="extWageJson" style="border:0px; height:30px; width:100%; text-indent:10px;" value="'+data.info[0].ext_wage[i+1].value+'" /></td></tr>');
							}
							i=i+2;
						}
					}
					$("#yourTableID").append('<tr height="50"><td colspan="4" align="center"><input type="button" class="pttcBut85" value="确认" onClick="updateWageRecords();"><input type="button" class="pttcButh85 ml30" value="取消" onClick="hidejg8()"></td></tr>');
				}else{
					$($($("#yourTableID").find('tr')[2]).find('input')[0]).val(data.info[0].should_pay);
					$($($("#yourTableID").find('tr')[2]).find('input')[1]).val(data.info[0].net_income);
					if(data.info[0].ext_wage!=undefined&&data.info[0].ext_wage!=''){
						var row=data.info[0].ext_wage.length/2;
						var is_odd=data.info[0].ext_wage.length%2;
						for(var i=0;i<row;i++){
							if((i==row-1)&&(is_odd==1)){
								$("#yourTableID").append('<tr><td align="right">'+data.info[0].ext_wage[i].title+'：</td><td colspan="3">'+data.info[0].ext_wage[i].value+'</td></tr>');
							}else{
								$("#yourTableID").append('<tr><td align="right">'+data.info[0].ext_wage[i].title+'：</td><td>'+data.info[0].ext_wage[i].value+'</td><td align="right">'+data.info[0].ext_wage[i+1].title+'：</td><td>'+data.info[0].ext_wage[i+1].value+'</td></tr>');
							}
						}
					}
				}
				showjg8();
			}
		}
	})
}
function sendWageInfoBefor(){
msgDialogTohome({"title":"提示信息","centent":"是否按输入的查询条件查询出工资单并发送?","okfunction":sendWageInfo});
}
/**
 * 工资单发送
 */
function sendWageInfo(){
	var param="optType=5";
	if($("#wageMonthCon").val()!=""){
		if($("#wageMonthCon").val().length==8)
			param+="&wageMonthCon="+$("#wageMonthCon").val().replace("年","").replace("月","");
		else
			param+="&wageMonthCon="+$("#wageMonthCon").val().replace("年","0").replace("月","");
	}
	if($('#orgselect').val().trim()!=''){
		param+="&orgno="+$("#orgno").val();
	}
	if($("#username").val().trim()!=''){
		param+="&userName="+$("#username").val();
	}
	$.ajax({
		type : 'POST',
		url : 'updateWI',
		data:param,
		success : function(data) {
			if(data.error!=undefined){
				$("#warnning").html(data.error);
				showjg12();
			}else{
				$("#warnning").html("发送成功!");
				var param='type=0';
				if($("#wageMonthCon").val()!=""){
					if($("#wageMonthCon").val().length==8)
						param+="&wageMonthCon="+$("#wageMonthCon").val().replace("年","").replace("月","");
					else
						param+="&wageMonthCon="+$("#wageMonthCon").val().replace("年","0").replace("月","");
				}
				if($('#orgselect').val().trim()!=''){
					param+="&orgno="+$("#orgno").val();
				}
				if($("#username").val().trim()!=''){
					param+="&userName="+$("#username").val();
				}
				param+="&page=0&pageSize=10";
				var currpage=$("#pageol").find("a.mpages").find("span").html();
				search(param,1);
				showjg12();
			}
		}
	})
}

/*****************************************************工资单项目设置******************************************************/

/**
 * 生成工资自定义项
 */
function addWageExt(){
	if($($("#simScrollContBox6").find("td").find(".pttcText")[0]).attr("readonly")!='readonly'&&($($("#simScrollContBox6").find("td").find(".pttcText")[0]).val().trim()=='应发工资'||$($("#simScrollContBox6").find("td").find(".pttcText")[0]).val().trim()=='实发工资')){
		$($("#simScrollContBox6").find("td").find(".pttcText")[0]).parent().append('<p class="errormsg">请填写新增项名称</p>');
	}else if($($("#simScrollContBox6").find("td").find(".pttcText")[0]).val().trim()==""){
		$($("#simScrollContBox6").find("td").find(".pttcText")[0]).parent().append('<p class="errormsg">请填写新增项名称</p>');
	}else if($("#resolve_person").val()==""){
		$("#simScrollContBox6").find("td").find(".ptmore").parent().append('<p class="errormsg">请选择工资释疑人</p>');
	}else {
		var param='optType=5&itemName='+$($("#simScrollContBox6").find("td").find(".pttcText")[0]).val().trim()+
				  '&isSend='+$("#simScrollContBox6 input[name='qysh']:checked").val()+
				  //'&status='+$("#simScrollContBox6 input[name='qysh']:checked").val()+
				  '&resolveUser='+$("#resolve_person").val()+
				  '&itemType=' +$("#simScrollContBox6 input[name='qysh2']:checked").val();
		if($("#simScrollContBox6").find("textarea").val().trim()!="")
			param+='&remark='+$("#simScrollContBox6").find("textarea").val();
		if($($("#simScrollContBox6").find("td").find(".pttcText")[0]).siblings().val()!='')
			param+='&id='+$($("#simScrollContBox6").find("td").find(".pttcText")[0]).siblings().val();
//		console.log(param)
//		console.log($("#simScrollContBox6 input[name='qysh']:checked").val()+"\t"+$("#simScrollContBox6 input[name='qysh2']:checked").val())
		postWageExt(param);
		hidejg3();
		getWageExtList($('input[name="extStatus"]:checked'));
	}
}

/**
 * 清除工资自定义项警告信息
 * @param param
 */
function clearExtWageWarnning(param){
	$(param).parent().find("p").remove();
}


/**
 * 工资自定义项操作提交
 * @param param
 */
function postWageExt(param){
	$.ajax({
		type : 'POST',
		url : 'updateEWI',
		data : param,
		success : function(data) {
			if(data.error!=undefined){
				$("#warnning").html(data.error);
				showjg12();
			}
		}
	})
}

/**
 * 获取工资自定义项
 */
function getWageExt(){
	$("#simScrollContBox6").find("td").find(".errormsg").remove();
	$("#simScrollContBox6").find("input[name='qysh']:eq(0)").attr("checked",'checked');
	$("#simScrollContBox6").find("input[name='qysh2']:eq(0)").attr("checked",'checked');
	$("#simScrollContBox6").find("textarea").val("");
	$($("#simScrollContBox6").find("td").find(".pttcText")[0]).removeAttr("readonly");
	if(checkExtWageSelect(1)){
		if($('input[name="wageExtCheck"]:checked').val()==''){
			$('input[name="wageExtCheck"]:checked').next().html()==1?'应发工资':'实发工资';
			$($("#simScrollContBox6").find("td").find(".pttcText")[0]).attr("readonly",'readonly');
			$($("#simScrollContBox6").find("td").find(".pttcText")[0]).val($('input[name="wageExtCheck"]:checked').next().html()==1?'应发工资':'实发工资');
			$($("#simScrollContBox6").find("td").find(".pttcText")[0]).next().val('');
			$("#simScrollContBox6").find("td").find(".ptmore").val('');
			$("#simScrollContBox6").find("td").find(".ptmore").siblings().val('');
			$("input[name='qysh']:eq(0)").attr("checked",'checked');
			$("input[name='qysh']:eq(1)").attr("disabled",'disabled');
			$("input[name='qysh2']:eq(0)").attr("disabled",'disabled');
			$("input[name='qysh2']:eq(1)").attr("checked",'checked');
			$("#simScrollContBox6").find("td").find(".pttexta").attr("readonly",'readonly');
			$($("#simScrollContBox6").find("td").find(".pttcText")[0]).siblings().val('');
			showjg3();
		}else{
			var templet=($('input[name="wageExtCheck"]:checked').next().html()=="&nbsp;&nbsp;1"||$('input[name="wageExtCheck"]:checked').next().html()=="&nbsp;&nbsp;2")?$('input[name="wageExtCheck"]:checked').next().html():"";
			var param='optType=0&id='+$('input[name="wageExtCheck"]:checked').val()+(templet==''?'':("&templetType="+templet.replace("&nbsp;&nbsp;","")));
			$.ajax({
				type : 'POST',
				url : 'getEWI',
				data : param,
				success : function(data) {
					if(data.error==undefined){
						if(data.info!=undefined){
							$($("#simScrollContBox6").find("td").find(".pttcText")[0]).val(data.info[0].item_name);
							$($("#simScrollContBox6").find("td").find(".pttcText")[0]).next().val(data.info[0].id);
							$("#simScrollContBox6").find("td").find(".ptmore").val(data.info[0].username);
							$("#simScrollContBox6").find("td").find(".ptmore").siblings().val(data.info[0].resolve_user);
							if(data.info[0].item_name=='应发工资'||data.info[0].item_name=='实发工资'){
								$("input[name='qysh']:eq(1)").attr("disabled",'disabled');
								$("input[name='qysh2']:eq(0)").attr("disabled",'disabled');
								$("input[name='qysh2']:eq(1)").attr("checked",'checked');
								$($("#simScrollContBox6").find("td").find(".pttcText")[0]).attr("readonly",'readonly');
								$("#simScrollContBox6").find("td").find(".pttexta").attr("readonly",'readonly');
							}else{
								$("input[name='qysh']:eq(1)").removeAttr("disabled");
								$("input[name='qysh2']:eq(0)").removeAttr("disabled");
								$("#simScrollContBox6").find("td").find(".pttexta").removeAttr("readonly");
								if(data.info[0].is_send==0)
									$("input[name='qysh']:eq(1)").attr("checked",'checked');
								else
									$("input[name='qysh']:eq(0)").attr("checked",'checked');
								if(data.info[0].item_type==0)
									$("input[name='qysh2']:eq(0)").attr("checked",'checked');
								else
									$("input[name='qysh2']:eq(1)").attr("checked",'checked');
								if(data.info[0].remark!=undefined)
									$("#simScrollContBox6").find("td").find(".pttexta").val(data.info[0].remark);
							}
							$($("#simScrollContBox6").find("td").find(".pttcText")[0]).siblings().val(data.info[0].id);
							showjg3();
						}
					}else{
						$("#warnning").html(data.error);
						showjg12();
					}
				}
			})
		}
	}
}

/**
 * 清楚工资自定义项列表
 */
function clearWageExtList(){
	$.each($("#wageExtList").find("tr"), function(idx, tr) {
		$(tr).remove();
	});
}

/**
 * 全部工资自定义项选择或取消
 * @param param
 */
function allWageExtSelect(param) {
	if ($(param).attr("checked") == "checked")
		$("#wageExtList").find("input").attr("checked", "true");
	else
		$("#wageExtList").find("input").removeAttr("checked");
}

/**
 * 清空工资自定义项
 */
function clearWageExtList(){
	$("#wageExtList").find("tr").remove();
}

/**
 * 获取工资自定义项
 */
function getWageExtList(param){
	clearWageExtList();
	$.ajax({
		type : 'POST',
		url : 'getEWI',
		data : 'status='+$(param).val(),
		success : function(data) {
			if(data.error==undefined){
				$.each(data.info,function(idx,element){
					$("#wageExtList").append('<tr><td width="115" align="center"><input type="checkbox" name="wageExtCheck" value="'+element.id+'"><label>&nbsp;&nbsp;'+(idx+1)+'</label></td><td width="150">'+element.item_name+'</td><td width="115">'+(element.item_type==0?'文本':'小数')+'</td><td width="115">'+(element.is_send==1?'是':'否')+'</td><td width="115">'+element.username+'</td><td width="115">'+(element.remark==undefined?'':element.remark)+'</td><td width="80">'+(element.status==1?"启用":"禁用")+'</td></tr>');
				})
			}else{
				$("#warnning").html(data.error);
				showjg12();
			}
		}
	})
}

/**
 * 自定义工资复选框选中验证
 * @param type 1-单选
 * @returns {Boolean}
 */
function checkExtWageSelect(type){
	var flag=true;
	
	if($('input[name="wageExtCheck"]:checked').length==0){
		$("#warnning").html("请选择自定义工资项");
		flag=false;
	}else if($('input[name="wageExtCheck"]:checked').length>1&&type==1){
		$("#warnning").html("只能选择一条自定义工资进行操作");
		flag=false;
	}
	if(!flag)
		showjg12();
	return flag;
}

/**
 * 
 * @param selectType 1-单选
 * @param optType 0-删除,1-启用,2-禁用,3-上移,4-下移
 */
function updateWageExt(selectType,optType){
	var param='';
	var flag=false;
	
	if($('input[name="extStatus"]:checked').val()!=0){
		if($('input[name="wageExtCheck"]:eq(0)').is(':checked')||$('input[name="wageExtCheck"]:eq(1)').is(':checked')){
			$("#warnning").html("应发工资和实发工资不能进行该操作");
			showjg12();
			return;
		}
	}
	if(selectType==1&&checkExtWageSelect(1)){//上移下移
		flag=true;
		if(optType==3){
			if($('input[name="extStatus"]:checked').val()==0){
				if($('input[name="wageExtCheck"]:checked').next().html()=="&nbsp;&nbsp;1"){
					$("#warnning").html("该项已经是第一位");
					showjg12();
					return;
				}
			}else {
				if($('input[name="wageExtCheck"]:checked').next().html()=="&nbsp;&nbsp;3"){
					$("#warnning").html("该项已经是第一位");
					showjg12();
					return;
				}
			}
			param="optType=3&id="+$('input[name="wageExtCheck"]:checked').val()+"&otherId="+$('input[name="wageExtCheck"]:checked').parents('tr').prev().find('input').val();
		}else{
			if($('input[name="wageExtCheck"]:checked').parents("tr").next().length==0){
				$("#warnning").html("该项已经是最后一位");
				showjg12();
				return;
			}
			param="optType=4&id="+$('input[name="wageExtCheck"]:checked').val()+"&otherId="+$('input[name="wageExtCheck"]:checked').parents('tr').next().find('input').val();
		}
	}else if(selectType==0&&checkExtWageSelect(0)){//删除,启用,禁用
		if(checkExtWageSelect(0)){
			flag=true;
			param+='optType='+optType
			$.each($('input[name="wageExtCheck"]:checked'),function(idx,element){
				param+='&id='+$(element).val()
			})
		}
	}
	if(flag){
		$.ajax({
			type : 'POST',
			url : 'updateEWI',
			data : param,
			success : function(data) {
				if(data.error!=undefined){
					$("#warnning").html(data.error);
					showjg12();
				}else {
					if(optType==0)
						msgDialog({"title":"提示信息","centent":"确认删除该工资表项?","okfunction":removeExtWageShow});
					 
					else if(optType==3||optType==4)
						convert(optType);
					else{
						if($('input[name="extStatus"]:checked').val()==-1)
							updateExtWageShow(optType==1?"启用":"禁用");
						else 
							removeExtWageShow();
					}
					initExtWageSequence();
				}
			}
		})
	}
}

function addExtWage() {
	$($("#simScrollContBox6").find("td").find(".pttcText")[0]).val('');
	$($("#simScrollContBox6").find("td").find(".pttcText")[0]).removeAttr("readonly");
	$($("#simScrollContBox6").find("td").find(".pttcText")[0]).next().val('');
	$("#simScrollContBox6").find("td").find(".ptmore").val('');
	$("#simScrollContBox6").find("td").find(".ptmore").siblings().val('');
	$("#simScrollContBox6").find("td").find(".pttexta").val('');
	$("#simScrollContBox6").find("input[name='qysh']").removeAttr("disabled");
	$("#simScrollContBox6").find("input[name='qysh2']").removeAttr("disabled");
	$("#simScrollContBox6").find("input[name='qysh']:eq(0)").attr("checked",'checked');
	$("#simScrollContBox6").find("input[name='qysh2']:eq(0)").attr("checked",'checked');
	showjg3();
}

/**
 * 换位
 * @param optType 3-上移,4-下移
 */
function convert(optType){
	if(optType==3)
		$('input[name="wageExtCheck"]:checked').parent("td").parent("tr").prev().before($('input[name="wageExtCheck"]:checked').parent("td").parent("tr"));
	else 
		$('input[name="wageExtCheck"]:checked').parent("td").parent("tr").next().after($('input[name="wageExtCheck"]:checked').parent("td").parent("tr"));
}

/**
 * 移除工资自定义项显示
 */
function removeExtWageShow(){
	$.each($('input[name="wageExtCheck"]:checked'),function(idx,element){
		$(element).parent("td").parent("tr").remove();
	})
}
/**
 * 更新工资自定义项状态显示
 * @param showinfo
 */
function updateExtWageShow(showinfo){
	
	$.each($('input[name="wageExtCheck"]:checked'),function(idx,element){
		$($(element).parent("td").siblings()[5]).html(showinfo);
	})
}

/**
 * 重新排序
 */
function initExtWageSequence(){
	$.each($("#wageExtList").find("tr"),function(idx,tr){
		$($(tr).find("td")[0]).find("label").html("&nbsp;&nbsp;"+(idx+1));
	})
}

/*****************************各浮动框*****************************************/
function showjg2() {
	$("#wageImport").find("td").find(".errormsg").remove();
	$("#wageImport").find("td").find('input[type="text"]').val("");
	$("#wageImport").find("td").find('input[type="file"]').val("");
	topDiv.show();
	$("#simScrollCont5").show();
	$("#simScrollContBox5").show();
}
function hidejg2() {
	topDiv.hide();
	$("#simScrollContBox5").hide();
	$("#simScrollCont5").hide();
}

function showjg3() {
	topDiv.show();
	$("#simScrollCont6").show();
	$("#simScrollContBox6").show();
}
function hidejg3() {
	topDiv.hide();
	$("#simScrollContBox6").hide();
	$("#simScrollCont6").hide();
}
function showjg8() {
	topDiv.show();
	$("#simScrollCont8").show();
	$("#simScrollContBox8").show();
}
function hidejg8() {
	topDiv.hide();
	$("#simScrollContBox8").hide();
	$("#simScrollCont8").hide();
}
function showjg12(){
	topDiv.show();
	$(window.top.document.body).addClass("html-body-overflow");
	$("#simScrollCont12").show();
	$("#simScrollContBox12").show();	
}
function hidejg12(){
	topDiv.hide();
	$("#simScrollContBox12").hide();
	$("#simScrollCont12").hide();
}

function showjg13(){
	topDiv.show();
	$("#simScrollCont13").show();
	$("#simScrollContBox13").show();	
}
function hidejg13(){
	topDiv.hide();
	$("#simScrollContBox13").hide();
	$("#simScrollCont13").hide();
}

function changeTab(id,curnum,tnum){
	for (var i = 0; i< tnum;i++ ){
		if(curnum == i){
			$("#"+id+"_title_"+curnum).toggleClass("current");
			$("#"+id+"_main_"+curnum).css("display",'block');
			$(".addgg").removeClass("addggcls");
			$(".addgg span").removeClass("lfad2");
			if(curnum==0){//发送工资单
				$("#wageSearch").trigger("click");
			}else if(curnum==1){//工资自定义项设置
				$($("input[name='extStatus']")[0]).trigger("click");
			}
		}else{
			$("#"+id+"_title_"+i).removeClass("current");
			$("#"+id+"_main_"+i).css("display",'none');
		}
	}
}

/** 统一树插件显示选择机构 **/
function showorgDiv(){
	topDiv.show();
	new openCommonView({title:'机构选择',setting:{callback: {onClick:onClickOrg}},show_user:false,closeEvent:function(){topDiv.hide();}});	
}
function onClickOrg(event, treeId, treeNode){
	$("#orgselect").val("");
	$("#orgno").val("");
	if(treeNode.level>0){
		$("#orgselect").val(treeNode.name);
		$("#orgno").val(treeNode.id);
	}
	$("#close_img").click();
}
/** 统一树插件显示选择用户(员工) **/
function showEmployeeDiv(){
	topDiv.show();
	new openCommonView({title:'员工选择',setting:{callback: {onClick:onClickEmployee}},show_user:true,closeEvent:function(){topDiv.hide();}});
}

function onClickEmployee(event, treeId, treeNode){
	$("#username").val("");
	if(treeNode.isParent==false) $("#username").val(treeNode.name);
	$("#close_img").click();
}

/** 统一树插件显示选择用户(工资释疑人) **/
function showuserDiv(){
	$(topDiv).css("opacity",0.7);
	new openCommonView({title:'工资释疑人选择',setting:{callback: {onClick:onClickUser}},show_user:true,closeEvent:function(){$(topDiv).css("opacity",0.4);}});	
}
function onClickUser(event, treeId, treeNode){
	if(treeNode.isParent==false){
		$("#resolve_person_txt").val(treeNode.name);
		$("#resolve_person").val(treeNode.id);		
	}
	$("#close_img").click();
}
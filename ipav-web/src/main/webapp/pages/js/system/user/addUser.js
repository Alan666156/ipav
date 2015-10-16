/**
 * 图片预览控件
 * @param $
 */
(function($) {
	jQuery.fn.extend({
				uploadPreview : function(opts) {
					opts = jQuery.extend({
						width : 0,
						height : 0,
						imgPreview : null,
						imgType : [ "gif", "jpeg", "jpg", "bmp", "png" ],
						callback : function() {
							return false;
						}
					}, opts || {});

					var _self = this;
					var _this = $(this);
					var imgPreview = $(opts.imgPreview);
					// 设置样式
					autoScaling = function() {
						imgPreview.css({
							"margin-left" : 0,
							"margin-top" : 10,
							"width" : 95,
							"height" : 120,
							"border":"1px solid #CCC",
							"background-size":"100% auto"
						});
//						imgPreview.show();
					}
					// file按钮出发事件
					_this.change(function() {
					if (this.value) {
						if (!RegExp(
								"\.(" + opts.imgType.join("|")
										+ ")$", "i").test(
								this.value.toLowerCase())) {
							alert("图片类型必须是"
									+ opts.imgType.join("，")
									+ "中的一种");
							this.value = "";
							return false;
						}
						
						if ($.browser.msie) {// 判断ie
							var path = $(this).val();
							if (/"\w\W"/.test(path)) {
								path = path.slice(1, -1);
							}
							imgPreview.css("background-image","url("+path+")");
//							imgPreview.attr("src", path);
							imgPreview.css({
								"margin-left" : 0,
								"margin-top" : 10,
								"width" : 95,
								"height" : 120,
								"border":"1px solid #CCC",
								"background-size":"100% auto"
							});
							setTimeout("autoScaling()", 100);
						} else {
							if ($.browser.version < 7) {
//								imgPreview.attr('src', this.files
//										.item(0).getAsDataURL());
								imgPreview.css("background-image","url("+this.files
										.item(0).getAsDataURL()+")");
					}
else {
	oFReader = new FileReader(),
	rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
	oFReader.onload = function(oFREvent) {
//			imgPreview.attr('src', oFREvent.target.result);
			imgPreview.css("background-image","url("+oFREvent.target.result+")");
		};
		var oFile = this.files[0];
		oFReader.readAsDataURL(oFile);
	}
	imgPreview.css({
		"margin-left" : 0,
		"margin-top" : 10,
		"width" : 95,
		"height" : 120,
		"border":"1px solid #CCC",
		"background-size":"100% auto"
	});
	setTimeout("autoScaling()", 100);
	}
	}
	opts.callback();
		});
		}
	});
})(jQuery);
/*******************************************************************************/

/***************************组织架构树状结构**************************************/
//var levelInfos;

/**
 * 初始化层级信息
 */
//function initLevelInfos(){
//	if(levelInfos!=undefined)
//		return;
//	$.ajax({
//		type:"POST",
//		url:"initOrgLevels",
//		success:function(data){
//			if(data.error==undefined){
//				levelInfos=data.orglevels;
//			}
//		}
//	})
//}

/**
 * 显示层级树
 * type:0-部门,1-兼职部门
 * index:兼职部门序号
 */
//function showLevels(type,index){
//	$("#orgType").val("");
//	$("#orgIdx").val("");
//	$("#orgType").val(type);
//	if(type==1)
//		$("#orgIdx").val(index);
//	if(levelInfos==undefined)
//		initLevelInfos();
//	$("#treeDemo").find("ul").remove();
//	var tree=$('<ul class="ztree" style="margin-top:0;"></ul>');
//	recurring(tree,levelInfos);
//	$("#treeDemo").append(tree);
//	$.each($(tree).find("li"),function(idx,li){
//		if(idx==0){
//			$($(li).find("span")[0]).attr("class","company");
//		}
//	})
//}

/**
 * 递归层级结构
 * @param tree
 * @param obj
 * @returns
 */
//function recurring(tree,obj){
//	$.each(obj,function(idx,node){
//		var li=$('<li name="treeArea"></li>');
//		var input=$("<input>");
//		input.attr('type', 'hidden');
//		input.attr('value', node.id);
//		li.append(input);
//		if($("#orgType").val()==0)
//			li.append('<p name="treeArea" class="'+(node.nodes.length==0?'treezk':'treezd')+'"'+(node.nodes.length==0?'':' onclick="changeTree(this)"')+'></p><a href="#1" name="treeArea" onclick="treeSelect(this)"'+getTreeItemClass(node.id)+'><span name="treeArea" class="department" onclick="treeSelect(this)"></span><label name="treeArea">'+node.name+'</label></a>');
//		else{
//			li.append('<p name="treeArea" class="'+(node.nodes.length==0?'treezk':'treezd')+'"'+(node.nodes.length==0?'':' onclick="changeTree(this)"')+'></p><a href="#1" name="treeArea" onclick="treeSelect(this)"'+getPartTimeOrgClass(node.id)+'><span name="treeArea" class="department" onclick="treeSelect(this)"></span><label name="treeArea">'+node.name+'</label></a>');
//		}
//		if($("#orgType").val()==0)
//			li.append('<p name="treeArea" class="treezk"'+(node.nodes.length==0?'':' onclick="changeTree(this)"')+'></p><a href="#1" name="treeArea" onclick="treeSelect(this)"'+getTreeItemClass(node.id)+'><span name="treeArea" class="department" onclick="treeSelect(this)"></span><label name="treeArea">'+node.name+'</label></a>');
//		else{
//			li.append('<p name="treeArea" class="treezk"'+(node.nodes.length==0?'':' onclick="changeTree(this)"')+'></p><a href="#1" name="treeArea" onclick="treeSelect(this)"'+getPartTimeOrgClass(node.id)+'><span name="treeArea" class="department" onclick="treeSelect(this)"></span><label name="treeArea">'+node.name+'</label></a>');
//		}
//		if(node.nodes.length>0){
//			var nodeTree=$('<ul name="treeArea" class="treeArea"></ul>');
////			$(nodeTree).css("display","none");
//			nodeTree=recurring(nodeTree,node.nodes);
//			li.append(nodeTree);
//		}
//		tree.append(li);
//	})
//	return tree;
//}

/**
 * 组织架构选项样式-兼职部门选择
 * @param id
 * @returns {String}
 */
function getPartTimeOrgClass(id){
	var result='';
	if($("#orgid").val()==id){
		result=' class="graySelected"';
	}else {
		$.each($("#simaltBox4").find("table").find("tr"),function(idx,tr){
			if(id==$($(tr).find('input[type="hidden"]')[0]).val()){
				if((idx+1)==$("#orgIdx").val())
					result=' class="treeSelected"';
				else {
					result=' class="graySelected"';
				}
				return false;
			}
		});
		if(result==''){
			var orgs=$("#partTimeOrg").val();
			if(orgs=="")
				result="";
			else{
				$.each($("#simaltBox4").find("table").find("tr"),function(idx,tr){
					if(id==orgs[idx]){
						result=' class="treeSelected"';
					}else{
						$.each(orgs,function(orgIdx,orgId){
							if(orgId==id){
								result=' class="graySelected"';
								return false;
							}
						});
					}
				});
			}
		}
	}
	return result;
}

/**
 * 组织架构选项样式
 * @param id
 * @returns {String}
 */
//function getTreeItemClass(id){
//	var result="";
//	if($("#orgType").val()==0){
//		if($("#orgid").val()==id){
//			result=' class="treeSelected"';
//		}else {
//			var orgs=$("#partTimeOrg").val();
//			if(orgs=="")
//				result="";
//			else{
//				var orgIds=orgs.split(",");
//				$.each(orgIds,function(idx,partTimeId){
//					if(id==partTimeId){
//						result=' class="graySelected"';
//						return false;
//					}
//				})
//			}
//		}
//	}
//	else{
//		if($("#orgid").val()==id)
//			result=' class="graySelected"';
//		else{
//			if($("#partTimeOrg").val()!=''){
//				var orgArr=$("#partTimeOrg").val().split(",");
//				$.each(orgArr,function(idx,orgid){
//					if(orgid==id){
//						if((idx+1)==$("#orgIdx").val())
//							result=' class="graySelected"';
//						else
//							result=' class="treeSelected"';
//						return false;
//					}
//				})
//			}
//			
//		}
//	}
//	return result;
//}

/**
 * 选中组织结构
 * @param param
 */
//function treeSelect(param){
//	if((param.nodeName=="A"&&$(param).attr("class")=="graySelected")||(param.nodeName=="SPAN"&&$(param).parent().attr("class")=="graySelected")){
//		$("#orgerr").html("您已在该部门任职");
//	}else if((param.nodeName=="A"&&$(param).attr("class")=="treeSelected")||(param.nodeName=="SPAN"&&$(param).parent().attr("class")=="treeSelected"))
//		$("#treeDemo").find("a").not(".graySelected").removeAttr("class");
//	else{
//		$("#treeDemo").find("a").not(".graySelected").removeAttr("class");
//		if(param.nodeName=="A"){
//			$(param).attr("class","treeSelected");
//		}else{
//			$(param).parent().attr("class","treeSelected");
//		}
//	}
//}

/**
 * 打开或收缩树
 * @param param
 */
//function changeTree(param){
//	if($(param).attr("class")=='treezd'){
//		$(param).attr("class","treezk");
//		$($(param).parent().find("ul")[0]).css("display","block");
//	}else{
//		$(param).attr("class","treezd");
//		$($(param).parent().find("ul")[0]).css("display","none");
//	}
//}

/**
 * 关闭树
 */
/*function closeTree(){
	$("#treeDemo").find("ul").remove();
	$("#treeDemo").css("display","none");
}*/
/*************************************************************************/

/***************************职务*******************************************/
var dutyInfos;
/**
 * 初始化职务信息
 */
function initDuty(){
	var obj;
	$.ajax({
		url:"queryDutyList",
		type:"POST",
		data:"status=1",
		success:function(data){
			if(data.list!=undefined){
				dutyInfos=new Array();
				$.each(data.list,function(idx,dutyInfo){
					obj=$.parseJSON('{"id":"'+dutyInfo.id+'","name":"'+dutyInfo.duty_name+'"}');
					dutyInfos.push(obj);
				});
			}
		}
	});
}
/*************************************************************************/

function initPicBind(){
	$("#picfile").uploadPreview({
		width : 90,
		height : 60,
		imgPreview : $("#userpic"),
		imgType : [ "png", "jpg" ],
		callback : function() {
//			$(file).parent().next().next().css("display","block");
//			$(file).prev().html("重新上传...");
//			$(file).attr("title","重新上传...");
			return false;
		}
	}); 
}

function selectOrg(){
	if($("#orgType").val()==0){
		$("#orgid").val("");
		$("#orgid").prev().val("");
	
		if($("#treeDemo").find("a.treeSelected").length==1){
			$("#orgid").val($("#treeDemo").find("a.treeSelected").parent().find('input[type="hidden"]').val());
			$("#orgid").prev().val($("#treeDemo").find("a.treeSelected").find("label").html());
			dutySelectToOrgMsg();
		}else{
			dutySelectToOrgMsg();
		}
	}else{
		if($("#treeDemo").find("a.treeSelected").length==1){
			$($($("#simaltBox4").find("tr")[parseInt($("#orgIdx").val())-1]).find('input[type="hidden"]')[0]).val($("#treeDemo").find("a.treeSelected").parent().find('input[type="hidden"]').val());
			$($($("#simaltBox4").find("tr")[parseInt($("#orgIdx").val())-1]).find('input[type="hidden"]')[0]).prev().prev().val($("#treeDemo").find("a.treeSelected").find("label").html());
			$($("#simaltBox4").find("tr")[parseInt($("#orgIdx").val())-1]).find("select").removeAttr("disabled");
			//dutySelectToOrgMsg();
		}else{
			$($($("#simaltBox4").find("tr")[parseInt($("#orgIdx").val())-1]).find('input[type="hidden"]')[0]).val('');
			$($($("#simaltBox4").find("tr")[parseInt($("#orgIdx").val())-1]).find('input[type="hidden"]')[0]).prev().prev().val('');
			$($("#simaltBox4").find("tr")[parseInt($("#orgIdx").val())-1]).find("select").attr("disabled","true");
			$("#orgid").val("");
			//dutySelectToOrgMsg();
			 
		}
	}
	hidetsBox2();
}

/**
 * 兼职信息
 */
function selectPartTimeInfo(){
	if(checkDutysMsg()){
		var partTimeOrgs='';
		var partTimeOrgNames='';
		var partTimeDuty='';
		var partTimeLevel='';
		var showPartTimeInfo='';
	    var flag=false;//<p><span>技术部</span>—<span>产品经理</span>—<span>(是)</span></p>
		$.each($("#simaltBox4").find("table").find("tr"),function(idx,tr){
			if($($(tr).find('input[type=hidden]')[0]).val()!=''){
				partTimeOrgs+=$($(tr).find('input[type=hidden]')[0]).val()+',';
				partTimeOrgNames+=$($(tr).find('input[type=text]')[0]).val()+',';
				showPartTimeInfo+='<p><span>'+$($(tr).find('input[type=hidden]')[0]).prev().prev().val()+'</span>';
			}else{
				$(tr).find("i.xterrmsg2").html("兼职部门不能为空");
				$(tr).find("i.xterrmsg2").css("display","block");
				flag=true;
			}
			if($($(tr).find('input[type=hidden]')[1]).val()==""){
				if($($(tr).find('input[type=text]')[1]).val()!=""){
					$(tr).find("i.xterrmsg3").html("兼职职务只能在指定的下拉列表中选择");
				}else{
				$(tr).find("i.xterrmsg3").html("兼职职务不能为空");
				}
				$(tr).find("i.xterrmsg3").css("display","block");
				flag=true;
			}else{
				partTimeDuty+=$($(tr).find('input[type=hidden]')[1]).val()+',';
				showPartTimeInfo+='—<span>'+$($(tr).find('input[type=hidden]')[1]).prev().val()+'</span>';
			}
			partTimeLevel+=$(tr).find('input[type="radio"]:checked').val()+',';
			showPartTimeInfo+='—<span>'+($(tr).find('input[type="radio"]:checked').val()==1?'(是)':'(否)')+'</span></p>';
		});
		if(flag)
			return;
		if(partTimeOrgs!=''){
			partTimeOrgs=partTimeOrgs.substr(0,partTimeOrgs.length-1);
			partTimeOrgNames=partTimeOrgNames.substr(0,partTimeOrgNames.length-1);
			partTimeDuty=partTimeDuty.substr(0,partTimeDuty.length-1);
			partTimeLevel=partTimeLevel.substr(0,partTimeLevel.length-1);
		}
		$("#partTimeOrg").val(partTimeOrgs);
		$("#partTimeOrgName").val(partTimeOrgNames);
		$("#partTimeDuty").val(partTimeDuty);
		$("#partTimeCock").val(partTimeLevel);
		$("#partTimeInfo").html(showPartTimeInfo);
		hidetsBox4();
	}else{
		return;
	}
}

/**
 * 递归展示选中的机构名
 * @param selectedId
 * @param obj
 * @returns {String}
 */
//var result='';
//function recurringName(selectedId,obj){
//	$.each(obj,function(idx,node){
//		if(selectedId==node.id){
//			result=node.name;
//			//return result;
//			 
//		}else {
//			if(node.nodes.length>0)
//			recurringName(selectedId,node.nodes);
//		}
//	})
//	return result;
//}

/**
 * 初始化兼职部门选择
 */
function initPartTimeOrg(){
	$("#simaltBox4").find("table").html("");
	if($("#partTimeOrg").val()==""){
		$("#simaltBox4").find("table").append('<tr><td><p><label class="xlb3">兼职部门1：</label><input type="text" onclick="showDep(1)" id="duty_orgno1" readonly class="pt_text160 gdnr2" ><i class="xterrmsg2"></i><input type="hidden" id="duty_orgid1"/>'
				+'<label class="xlb3">兼职职务1：</label> <input type="text" value="" autocomplete="off"  	class="pt_text160" name="dutyname" id="dutyname1" onclick="dutySelectFuc(1);" onkeyup="dutyKeyDown(1);" >	<input type="hidden" name="duty1" id="duty1" value="" />  <div class="dutySelect1" id="dutySelect1">  <ul> </ul></div><i class="xterrmsg3"></i>'
				+'<label class="zgzwradio">是否部门最高职务：</label><span class="xzradio"><input type="radio" value="1" name="yes1">是</span><span class="xzradio"><input type="radio" value="0" checked name="yes1">否</span>'
				+'</p></td></tr>');
		if(dutyInfos==undefined)
			initDuty();
		if(dutyInfos.length>0){
			var options='<option value="0"></option>';
			$.each(dutyInfos,function(idx,duty){
				options=options.concat('<option value="'+duty.id+'">'+duty.name+'</option>');
			});
			$.each($("#simaltBox4").find("table").find("tr"),function(idx,tr){
				$(tr).find("select").empty();
				$(tr).find("select").append(options);
				$($(tr).find("input")[0]).unbind("click");
				$($(tr).find("input")[0]).bind("click",function(){
					showDep(idx+1);
				});
				$(tr).find('input[type="radio"]').attr("name","yes"+idx+1);
			});
		}
	}else{
		var orgArr=$("#partTimeOrg").val().split(",");
		var orgNameArr=$("#partTimeOrgName").val().split(",");
		var dutyArr=$("#partTimeDuty").val().split(",");
		var cockArr=$("#partTimeCock").val().split(",");
		for(var i=0;i<orgArr.length;i++){
			if(i==0)
				$("#simaltBox4").find("table").append('<tr><td><p><label class="xlb3">兼职部门'+(i+1)+'：</label><input type="text" id="duty_orgno'+(i+1)+'"  readonly class="pt_text160 gdnr2" ><i class="xterrmsg2"></i><input type="hidden" id="duty_orgid'+(i+1)+'" />'
						+'<label class="xlb3">兼职职务'+(i+1)+'：</label> <input type="text" value="" autocomplete="off"  	class="pt_text160" name="dutyname" id="dutyname'+(i+1)+'" onclick="dutySelectFuc('+(i+1)+');" onkeyup="dutyKeyDown('+(i+1)+');">	<input type="hidden" id="duty'+(i+1)+'" name="duty" value="" />  <div class="dutySelect1" id="dutySelect'+(i+1)+'">  <ul> </ul></div><i class="xterrmsg3"></i>'
						+'<label class="zgzwradio">是否部门最高职务：</label><span class="xzradio"><input type="radio" value="1" name="yes'+(i+1)+'">是</span><span class="xzradio"><input type="radio" value="0" checked name="yes'+(i+1)+'">否</span>'
						+'</p></td></tr>');
			else 
				$("#simaltBox4").find("table").append('<tr><td><p><label class="xlb3">兼职部门'+(i+1)+'：</label><input type="text" id="duty_orgno'+(i+1)+'" readonly class="pt_text160 gdnr2" ><i class="xterrmsg2"></i><input type="hidden" id="duty_orgid'+(i+1)+'" />'
						+'<label class="xlb3">兼职职务'+(i+1)+'：</label> <input type="text" value="" autocomplete="off"  	class="pt_text160" name="dutyname" id="dutyname'+(i+1)+'" onclick="dutySelectFuc('+(i+1)+');" onkeyup="dutyKeyDown('+(i+1)+');">	<input type="hidden" id="duty'+(i+1)+'" name="duty" value="" />  <div class="dutySelect1" id="dutySelect'+(i+1)+'">  <ul> </ul></div><i class="xterrmsg3"></i>'
						+'<label class="zgzwradio">是否部门最高职务：</label><span class="xzradio"><input type="radio" value="1" name="yes'+(i+1)+'">是</span><span class="xzradio"><input type="radio" value="0" checked name="yes'+(i+1)+'">否</span>'
						+'<span style="display:block; float:left;"><a href="#1" class="delete" onclick="deleteOrg(this)" title="删除">删除</a></span></p></td></tr>');
		}
		$.each($("#simaltBox4").find("table").find("tr"),function(idx,tr){
			$($(tr).find('input[type="text"]')[0]).val(orgNameArr[idx]);
			$($(tr).find('input[type="hidden"]')[0]).val(orgArr[idx]);
			$($(tr).find('input[type="text"]')[1]).val(recurringDutyName(dutyArr[idx]));
			$($(tr).find('input[type="hidden"]')[1]).val(dutyArr[idx]);
		});
		if(dutyInfos==undefined)
			initDuty();
		if(dutyInfos.length>0){
			var options='<option value="0"></option>';
			$.each(dutyInfos,function(idx,duty){
				options=options.concat('<option value="'+duty.id+'">'+duty.name+'</option>');
			});
			$.each($("#simaltBox4").find("table").find("tr"),function(idx,tr){
				$(tr).find("select").empty();
				$(tr).find("select").append(options);
				$(tr).find("select").val(dutyArr[idx]);
				$($(tr).find("input")[0]).unbind("click");
				$($(tr).find("input")[0]).bind("click",function(){
					showDep(idx+1);
				});
				$(tr).find('input[type="radio"]').attr("name","yes"+idx+1);
				if(cockArr[idx]==1)
					$($(tr).find('input[type="radio"]')[0]).attr("checked","checked");
			});
		}
	}
}

/**
 * 删除指定兼职部门项
 * @param param
 */
function deleteOrg(param){
	$(param).parents("tr").remove();
	$.each($("#simaltBox4").find("table").find("tr"),function(idx,tr){
		$($(tr).find("label")[0]).html('兼职部门'+(idx+1)+'：');
		$($(tr).find("label")[1]).html('兼职职务'+(idx+1)+'：');
		$($(tr).find("input")[0]).unbind("click");
		$($(tr).find("input")[0]).bind("click",function(){
			showDep(idx+1);
//			showtsBox2(1,idx+1);
		});
		$(tr).find('input[type="radio"]').attr("name","yes"+idx+1);
	});
}

/**
 * 添加兼职部门选项
 */
function addOrgInfo(){
	var length=$("#simaltBox4").find("table").find("tr").length+1;
	$("#simaltBox4").find("table").append('<tr><td><p><label class="xlb3">兼职部门'+length+'：</label><input type="text" readonly id="duty_orgno'+length+'" class="pt_text160 gdnr2" onclick="showDep('+length+')"><i class="xterrmsg2"></i><input type="hidden" id="duty_orgid'+(length)+'" />'
			+'<label class="xlb3">兼职职务'+length+'：</label> <input type="text" value="" autocomplete="off"  	class="pt_text160" name="dutyname" id="dutyname'+length+'"  onclick="dutySelectFuc('+length+');" onkeyup="dutyKeyDown('+length+');">	<input type="hidden" id="duty'+length+'" name="duty" value="" />  <div class="dutySelect1" id="dutySelect'+length+'">  <ul> </ul></div><i class="xterrmsg3"></i>'
			+'<label class="zgzwradio">是否部门最高职务：</label><span class="xzradio"><input type="radio" value="1" name="yes'+length+'">是</span><span class="xzradio"><input type="radio" value="0" checked name="yes'+length+'">否</span>'
			+'<span style="display:block; float:left;"><a href="#1" class="delete" onclick="deleteOrg(this)" title="删除">删除</a></span></p></td></tr>');
	if(dutyInfos==undefined)
		initDuty();
	if(dutyInfos.length>0){
		var options='<option value="0"></option>';
		$.each(dutyInfos,function(idx,duty){
			options=options.concat('<option value="'+duty.id+'">'+duty.name+'</option>');
		});
		$("#simaltBox4").find("table").find("tr:last").find("select").empty();
		$("#simaltBox4").find("table").find("tr:last").find("select").append(options);
	}
}

/**
 * 性别选择
 * @param param
 */
function showSexPic(param){
	if($("#picfile").val()==''){
		if($(param).val()==1)
			$("#userpic").attr("class","sctx");
		else
			$("#userpic").attr("class","sctx2");
	}
}

$(document).ready(function(){
	if($("#userid").length==0)
		$.ajax({
			url:"initSelectors",
			type:"POST",
			success:function(data){
				var options='';
				if(data.platformlist!=undefined){
					$.each(data.platformlist,function(idx,platform){
						options+='<option value="'+platform.id+'">'+platform.platname+'</option>';
					});
					$("#platform").append(options);
				}
				options='';
				if(data.labourList!=undefined){
					$.each(data.labourList,function(idx,labour){
						options+='<option value="'+labour.id+'">'+labour.belong_name+'</option>';
					});
					$("#labourBelong").append(options);
				}
			}
		})
});

//function showtsBox2(type,index){
//	showLevels(type,index);
//	$("#simalpha2").show();
//	$("#simaltBox2").show();
//}
//function hidetsBox2(){
//	$("#simalpha2").hide();
//	$("#simaltBox2").hide();
//	$("#DirectorBox").hide();
//}

function showtsBox4(){
	initPartTimeOrg();
	$("#simalpha4").show();
	$("#simaltBox4").show();	
}
function hidetsBox4(){
	$("#simalpha4").hide();
	$("#simaltBox4").hide();
}
function checkDutyAndOrgid(){
/*	var orgid=$("#orgid").val();
	var duty=$("#duty0").val();
	if((isNotNull(orgid)==false && isNotNull(duty)==false)||
      (isNotNull(orgid)==true && isNotNull(duty)==true)){
		return true;
	 }else{
		return false;
	}*/
}

//判断元素是否为空
function isNotNull(obj){
	if(obj==null||$.trim(obj)==''||obj==undefined){
		return false;
	}else{
		return true;
	}
}

function dutySelectToOrgMsg(){
	$("#orgidMsg").html("");
	$("#dutyMsg").html("");
	var f=true;
	if(isNotNull($("#orgid").val())==true){
		var flag=true;
		$.each($("#partTimeOrg").val().split(","),function(i,v){
			if($("#orgid").val()==v){
				$("#orgidMsg").html("您已在该部门任职");
				$("#orgidMsg").show();
				//$("#orgidMsg").prev().hide();
				flag=false;
				f=false;
			}
		});
		if(flag){
			$("#orgidMsg").hide();
			//$("#orgidMsg").prev().show();
		}else{
			$("#orgno").val("");
			$("#orgid").val("");
		}
	}
		
	if(isNotNull($("#duty0").val())==false){    
		if($("#duty0").prev().val()!=""){
			$("#dutyMsg").html("职务只能从指定的下拉列表中选择");
			$("#dutyMsg").show();
			f=false;
	 
			///$("#dutyMsg").prev().hide();
		}else{
			$("#dutyMsg").hide();
			//$("#dutyMsg").prev().show();
			}
	}
	return f;
}
var dutySelectData;
$(document).ready(function(){
	$.ajax({
		type : 'POST',
		url : 'queryDutyList',			 
		success : function(data) {
			dutySelectData=data;
		}
});
});
function dutySelectFuc(i){	    
		$($("#dutySelect"+i).find("ul")[0]).find("li").remove();
		if(dutySelectData.error==undefined){
			if(dutySelectData.list!=undefined&&dutySelectData.list.length>0){
				var name=$("#dutyname"+i).val();				 
				$("#dutySelect"+i).find("ul").find("li").remove();
				$.each(dutySelectData.list,function(idx,value){
					if((name!='' && value.duty_name.indexOf(name)!=-1 )|| name=="")
					$($("#dutySelect"+i).find("ul")[0]).append("<li id="+value.id+"_"+value.duty_name+"_"+i+" onclick='dutySelectAfter(this);'><a href='#1' >"+value.duty_name+"</a></li>");
				})
			}
		}
		$("#dutySelect"+i).show();
		dutySelectss();
}
function dutyKeyDown(i){
	$("#duty"+i).val("");
	dutySelectFuc(i);
}
function dutySelectAfter(para){
	var arry=$(para).attr("id").split("_");
	$("#dutyname"+arry[2]).val(arry[1]);
	$("#duty"+arry[2]).val(arry[0]);
	hideDutySelect();
	if(arry[2]==0)dutySelectToOrgMsg();
}
function hideDutySelect(){
	//$("#dutySelect").hide();
	$("div[id^='dutySelect']").hide();
}
function dutySelectss(){
 
	var dutySelectss=$("div[id^='dutySelect']");
	$.each(dutySelectss,function(idx,value){
		$($(value).parent()).hover(function(){		 
			$(value).hide();
		});
	})
	
 
}

function recurringDutyName(id){
	var name ="";
	$.each(dutySelectData.list,function(idx,value){
		if(value.id==id) {name= value.duty_name;}
	})
	return name;
}

function checkDutysMsg(){
	var flag=true;
	var orgs=new Array();
	var dutys=new Array();
	$.each($("#simaltBox4").find("table").find("tr"),function(idx,tr){
		if($($(tr).find('input[type=hidden]')[0]).val()!=''){
			$(tr).find("i.xterrmsg2").css("display","none");
			orgs.push($($(tr).find('input[type=hidden]')[0]).val());
		}else{
			flag=false;
			$(tr).find("i.xterrmsg2").html("兼职部门不能为空");
			$(tr).find("i.xterrmsg2").css("display","block");
		}
		if($($(tr).find('input[type=hidden]')[1]).val()==""){
			if($($(tr).find('input[type=text]')[1]).val()!=""){
				flag=false;
				$(tr).find("i.xterrmsg3").html("兼职职务只能在指定的下拉列表中选择");
			}else{
				flag=false;
				$(tr).find("i.xterrmsg3").html("兼职职务不能为空");
			}
			$(tr).find("i.xterrmsg3").css("display","block");
		}else{
			dutys.push($($(tr).find('input[type=hidden]')[1]).val());
			$(tr).find("i.xterrmsg3").css("display","none");
			 
		}		 
	});
	if(flag && orgs.length==dutys.length){
		for(var i=0;i<orgs.length;i++){
		  for(var j=i+1;j<orgs.length;j++){
				if(orgs[i]==orgs[j] && dutys[i]==dutys[j]){
					flag=false;
					$("#checkall").css("display","block");
					return flag;
				}
			}
		}
	} 
	$("#checkall").css("display","none");
	return flag;
	
}



function showdirector(){
	new openCommonView({
		title:'直接主管选择',
		setting:{
			callback: {
				onClick:onClickDirector
			}
		},
		show_user:true
	});	
}

function onClickDirector(event, treeId, treeNode){
	if(treeNode.isParent==false){
		$("#directorValue").val(treeNode.id);
		$("#directorName").val(treeNode.name);
	}
	$("#close_img").click();
}

function showDep(index){
	var _title='部门选择';
	var _setting={callback: {onClick:onClickDep1}};
	if(index){
		$("#hid_index").val(index);
		_title='兼职部门选择';
		 _setting={callback: {onClick:onClickDep2}};
	}
	new openCommonView({
		title:_title,
		setting:_setting,
		show_user:false
	});	
}
function onClickDep1(event, treeId, treeNode){
	if(treeNode.level>0){
		$("#orgno").val(treeNode.name);
		$("#orgid").val(treeNode.tid);
		dutySelectToOrgMsg();
	}
	$("#close_img").click();
}
function onClickDep2(event, treeId, treeNode){
	$("#duty_orgno"+$("#hid_index").val()).next().html("");
	if(treeNode.level>0){
		if($("#orgid").val()!=treeNode.tid){
			$("#duty_orgno"+$("#hid_index").val()).val(treeNode.name);
			$("#duty_orgid"+$("#hid_index").val()).val(treeNode.tid);
		}else{
			$("#duty_orgno"+$("#hid_index").val()).next().html("您已在该部门任职");
			$("#duty_orgno"+$("#hid_index").val()).next().css("display","block");
		}
	}
	$("hid_index").val("");
	$("#close_img").click();
}
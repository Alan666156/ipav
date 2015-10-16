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
							"margin-top" : 0,
							"width" : opts.width,
							"height" : opts.height
						});
						imgPreview.show();
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
							imgPreview.attr("src", path);
							imgPreview.css({
								"margin-left" : 0,
								"margin-top" : 0,
								"width" : opts.width,
								"height" : opts.height
							});
							setTimeout("autoScaling()", 100);
						} else {
							if ($.browser.version < 7) {
								imgPreview.attr('src', this.files
										.item(0).getAsDataURL());
					}
else {
	oFReader = new FileReader(),
	rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
	oFReader.onload = function(oFREvent) {
			imgPreview.attr('src', oFREvent.target.result);
		};
		var oFile = this.files[0];
		oFReader.readAsDataURL(oFile);
	}
	imgPreview.css({
			"margin-left" : 0,
			"margin-top" : 0,
			"width" : opts.width,
			"height" : opts.height
		});
	setTimeout("autoScaling()", 100);
	}
	}
	opts.callback();
		});
		}
	});
})(jQuery);

var topDiv;
$(document).ready(function(){
	topDiv=$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont");
	if($("#voteId").val().trim()!=""){
		$("#voteId").prev().find("a").html("修改投票");
		$.ajax({
			url:"getVoteInfo",
			type:"POST",
			data:"id="+$("#voteId").val()+"&optType=1",
			dataType:"json",
			success:function(data){
				if(data!=undefined&&data.infos!=undefined&&data.infos.length==1){
					$("#voteName").val(data.infos[0].title);
					/***初始化投票人员/***/
					if(data.infos[0].joinPeopleName!=undefined)
					$("#showusers1").val(data.infos[0].joinPeopleName);
					var ht="";
					$.each(data.infos[0].joinPeople.split(";"),function(i,value){
						ht+='<input type="hidden" value="'+value+'"/>';
					});
					$("#hiduserid1").html(ht);
					var ht1="";
					$.each(data.infos[0].joinPeopleName.split(";"),function(i,value){
						ht1+='<input type="hidden" id="_username'+i+'" value="'+value+'"/>';
					});
					$("#hidusername1").html(ht1);
					
					
					
					if(data.infos[0].viewPeopleName!=undefined)
					$("#showusers2").val(data.infos[0].viewPeopleName);
					var htm="";
					$.each(data.infos[0].viewPeople.split(";"),function(i,value){
						htm+='<input type="hidden" value="'+value+'"/>';
					});
					$("#hiduserid2").html(htm);
					var htm1="";
					$.each(data.infos[0].viewPeopleName.split(";"),function(i,value){
						htm1+='<input type="hidden" id="_username'+i+'" value="'+value+'"/>';
					});
					$("#hidusername2").html(htm1);					
					   
					/***初始化投票人员/***/
					if(data.infos[0].remark!="")
						$("#voteName").parent().parent().next().find("textarea").val(data.infos[0].remark);
					var endTime=data.infos[0].endTime.split(" ");
					endTime[0]=endTime[0].replace("-","年").replace("-","月").replace("年0","年").replace("月0","月")+"日";
					$("#endTime").val(endTime[0]);
					$("#endTime").next().val(endTime[1]);
					$("#vote_person").prev().prev().val(data.infos[0].joinPeople);
					$("#vote_person").prev().prev().prev().val(data.infos[0].joinPeopleName);
					$("#view_person").prev().prev().val(data.infos[0].viewPeople);
					$("#view_person").prev().prev().prev().val(data.infos[0].viewPeopleName);
					if(data.infos[0].startTime==data.infos[0].updateTime)
						$("#startTime").parent().parent().prev().find('input[type="checkbox"]').removeAttr("checked");
					else{
						$("#startTime").parent().parent().prev().find('input[type="checkbox"]').attr("checked","checked");
						var startTime=data.infos[0].startTime.split(" ");
						startTime[0]=startTime[0].replace("-","年").replace("-","月").replace("年0","年").replace("月0","月")+"日"
						$("#startTime").val(startTime[0]);
						$("#startTime").next().val(startTime[1]);
						$("#dsTimeBox1").css("display","block");
					}
					$("#dsTimeBox1").next().find("select").val(data.infos[0].resultType);
					if(data.infos[0].isAnonymout==1)
						$('#che1').attr("checked","checked");
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
	initSelectCount($($("#rcxs_main_0").find("select")[0]))
	$("#rcxs_main_0").find("select").val(minNum);
	$.each(data,function(idx,item){
		$($("#rcxs_main_0").find("table").find("tr")[idx]).find('input[type="text"]').val(item.itemName);
		if(item.pic!=""){
			$($("#rcxs_main_0").find("table").find("tr")[idx]).find('img').attr("src",picpath+item.pic);
			$($("#rcxs_main_0").find("table").find("tr")[idx]).find('div').css("display","block");
		}
	})
}

/**
 * 修改初始化评分
 * @param data
 */
function initScore(data,minNum,criteria,ext,picpath){
	$($($("#rcxs_main_1").find("div")[0]).find('input[type="text"]')[0]).val(ext.maxScore);
	$($($("#rcxs_main_1").find("div")[0]).find('input[type="text"]')[1]).val(ext.minScore);
	$($("#rcxs_main_1").find("div")[0]).find("select").val(ext.scoreType);
	var len=data.length-2;
	for(var i=0;i<len;i++)
		$("#rcxs_main_1").find(".addxux3").click();
	$.each(data,function(idx,item){
		$($("#rcxs_main_1").find("table").find("tr")[idx]).find('input[type="text"]').val(item.itemName);
		if(item.pic!=""){
			$($("#rcxs_main_1").find("table").find("tr")[idx]).find('img').attr("src",picpath+item.pic);
			$($("#rcxs_main_1").find("table").find("tr")[idx]).find('div').css("display","block");
		}
	})
	initSelectCount($($("#rcxs_main_1").find("select")[1]))
	initSelectCount($($("#rcxs_main_1").find("select")[2]))
	$($("#rcxs_main_1").find("select")[1]).val(minNum);
	$($("#rcxs_main_1").find("select")[2]).val(criteria)
}

/**
 * 修改初始化排名
 * @param data
 * @param ext
 */
function initRank(data,ext,picpath){
	var extLen=ext.length-2;
	for(var i=0;i<extLen;i++)
		$($("#rcxs_main_2").find(".addxux3")[0]).click();
	$.each(ext,function(idx,scoreinfo){
		$($($($("#rcxs_main_2").find("table")[0]).find("tr")[idx]).find('input[type="text"]')[0]).val(scoreinfo.score);
		$($($($("#rcxs_main_2").find("table")[0]).find("tr")[idx]).find('input[type="text"]')[1]).val(scoreinfo.count);
	});
	var len=data.length-2;
	for(var i=0;i<len;i++)
		$($("#rcxs_main_2").find(".addxux3")[1]).click();
	$.each(data,function(idx,item){
		$($($("#rcxs_main_2").find("table")[1]).find("tr")[idx]).find('input[type="text"]').val(item.itemName);
		if(item.pic!=""){
			$($($("#rcxs_main_2").find("table")[1]).find("tr")[idx]).find('img').attr("src",picpath+item.pic);
			$($($("#rcxs_main_2").find("table")[1]).find("tr")[idx]).find('div').css("display","block");
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
			if((i+1)==minNum)	
				$("#bixuantongs").append('<option selected = "selected" value="'+(i+1)+'">'+(i+1)+'</option>');
			else
			    $("#bixuantongs").append('<option value="'+(i+1)+'">'+(i+1)+'</option>');
		});
		initSelectCount($("#bixuantongs"))
		$("#bixuantongs").val(minNum)
		$("#bixuantongs").next().next().find("span").html(minNum)
		$("#hiduserid3").html(htm);						 
		$("#hidusername3").html(htm1);
		$("#showusers3").val(htmlll);
				     
	/*$.each($("#join_person").find('input[type="checkbox"]'),function(idx,checkboxInput){
		$.each(data,function(personIdx,person){
			if($(checkboxInput).val().trim()==person.itemName)
				$(checkboxInput).attr("checked","checked");
		})
		
	})*/
}

/**
 * 验证投票活动名称
 * @returns {Boolean}
 */
function validateVoteTitle(){
	if($("#title").val().trim()==''){
		$("#errormsg2").html('请填写投票名称');
		return false;
	}
	return true;
}

/**
 * 删除图片
 * @param param
 */
function deletePic(param){
	$(param).prev().attr("src","");
	$(param).parent().prev().prev().find('input[type="file"]').attr("title","上传图片...")
	$($(param).parent().prev().prev().find('a')[0]).html("上传图片...");
	$(param).parent().prev().prev().find('input[type="file"]').val("");
	$(param).parent().css("display","none");
}

/**
 * 添加选项内容
 * @param param
 * @param type
 */
function addItem(param,type){
	onloalrame();
	var len=$(param).prev().find("tr").length+1;
	var labelinfo="";
	var placeholder=""
	var classinfo="";
	switch(type){
		case 1:{
			labelinfo="选项";
			placeholder="请输入投票选项";
			classinfo="addimg3";
		}
		break;
		case 2:{
			labelinfo="评分对象";
			placeholder="请输入评分对象";
			classinfo="addimg3";
		}
		break;
		case 3:{
			labelinfo="选项";
			placeholder="请输入排名选项";
			classinfo="addimg";
		}
		break;
	}
	$(param).prev().append('<tr><td><p><label class="xlb3">'+labelinfo+len+'：</label><input type="text" placeholder="'+placeholder+'" class="xuxtext" maxlength="50" /><a href="#1">上传图片...</a><input title="上传图片..." name="pic" type="file" class="filebut" /><span style="display:block"><a href="#1" class="delete" onclick="deleteItem(this,'+type+')" title="删除">删除</a></span></p><i class="errmsg2" style="margin-top:-14px;display:none">请输入选项名</i><div class="'+classinfo+'" style="display:none"><img src=""><span class="closes" onclick="deletePic(this)" title="删除图片"></span></div></td></tr>');

	var file=$(param).prev().find('tr:last').find('input[type="file"]');
	$(file).uploadPreview({
			width : 90,
			height : 60,
			imgPreview : $(file).parent().next().next().find("img"),
			imgType : [ "png", "jpg" ],
			callback : function() {
				$(file).parent().next().next().css("display","block");
				$(file).prev().html("重新上传...");
				$(file).attr("title","重新上传...");
				onloalrame();
				return false;
			}
		}); 
}

/**
 * 删除选项
 * @param param
 */
function deleteItem(param,type){
	var table=$(param).parent().parent().parent().parent().parent();
	$(param).parent().parent().parent().parent().remove();
	refreshSeq(table,type);
}

/**
 * 页面选项更新
 * @param param
 * @param type
 */
function refreshSeq(param,type){
	var itemName="";
	switch(type){
		case 1:
			itemName="选项";
		break;
		case 2:
			itemName="评分对象";
		break;
		case 3:
			itemName="选项";
		break;
	}
	$($(param).parent().next().next().find("li")[0]).find("select").find("option").remove();
	$.each($(param).find("tr"),function(idx,tr){
		$(tr).find("label").text(itemName+""+(idx+1)+"：");
		$('<option value="'+(idx+1)+'">'+(idx+1)+'</option>').appendTo($($(param).parent().next().next().find("li")[0]).find("select"));
	})
}

/**
 * 添加排名分数项
 */
function addRankScore(){
	var rowNum=$($("#rcxs_main_2").find("table")[0]).find("tr").length+1;
	$($("#rcxs_main_2").find("table")[0]).append('<tr><td><p><label class="xlb3">第'+rowNum+'名得分：</label><input type="text" class="pt_text80" onchange="inputFloatRank(this);"><span class="xsp">分</span>'
            +'<span class="xsp" style="margin-left:30px;">该名次必选项：</span><input type="text" class="pt_text80" name="quota" onchange="inputIntRank(this);">'
            +'<em class="tpmsg2" style="display:none">投票结果取总分最高第<span>3-4</span>名</em><a href="#1" class="delete" onclick="deleteRankScore(this)" title="删除">删除</a>'
            +'</p><i class="errmsg6">请输入整数型</i><i class="errmsg8">请输入整数型2</i></td></tr>');
}

/**
 * 删除排名分数项
 * @param param
 */
function deleteRankScore(param){
	var table=$(param).parent().parent().parent().parent();
	$(param).parent().parent().parent().remove();
	refreshRankScoreList(table);
	var counts=0;
	$.each($($("#rcxs_main_2").find('table')[0]).find('tr'),function(idx,tr){
		if(/^\d+$/.test($($(tr).find('input')[1]).val().trim())){
			if($($(tr).find('input')[1]).val().trim()=="1"){
				$(tr).find("span:last").html(counts+1);
				$(tr).find("em").css("display","block");
				counts+=1;
			}else{
				$(tr).find("span:last").html((counts+1)+'-'+(counts+parseInt($($(tr).find('input')[1]).val().trim())));
				$(tr).find("em").css("display","block");
				counts+=parseInt($($(tr).find('input')[1]).val().trim());
			}
		}
	})
}

/**
 * 刷新排名分数项
 * @param param
 */
function refreshRankScoreList(param){
	$.each($(param).find("tr"),function(idx,tr){
		$(tr).find("label").html("第"+(idx+1)+"名等分：");
	})
}

/**
 * 校验浮点型
 * @param param
 */
function inputFloat(param){
	if(!/^\d+|(\d+\.\d*[1-9])$/.test($(param).val())){
		$(param).next().html('只能输大于等于零入整数或小数')
		$(param).next().css("display","block");
	}else 
		$(param).next().css("display","none");
}

/**
 * 校验整型
 * @param param
 */
function inputInt(param){
	if(!/^\d+$/.test($(param).val())){
		$(param).next().html('只能输大于等于零入整数')
		$(param).next().css("display","block");
	}else 
		$(param).next().css("display","none");
}

/**
 * 校验整型
 * @param param
 */
function inputIntScore(param){
	if(!/^\d+$/.test($(param).val())){
		$(param).next().html('只能输大于或等于零的整数')
		$(param).next().css("display","inline");
		return false;
	}else 
		$(param).next().css("display","none");
	return true;
}

/**
 * 校验浮点型
 * @param param
 */
function inputFloatScore(param){
	if(!/^\d+|(\d+\.\d*[1-9])$/.test($(param).val())){
		$(param).next().html('只能输大于零的实数')
		$(param).next().css("display","inline");
		return false;
	}else 
		$(param).next().css("display","none");
	return true;
}

/**
 * 校验整型
 * @param param
 */
function inputIntRank(param){
	if(!/^\d+$/.test($(param).val())){
		$(param).parent().next().next().html('只能输大于等于零入整数')
		$(param).parent().next().next().css("display","block");
		$(param).next().css("display","none");
	}else 
		$(param).parent().next().next().css("display","none");
	var counts=0;
	$.each($($("#rcxs_main_2").find('table')[0]).find('tr'),function(idx,tr){
		if(/^\d+$/.test($($(tr).find('input')[1]).val().trim())){
			if($($(tr).find('input')[1]).val().trim()=="1"){
				$(tr).find("span:last").html(counts+1);
				$(tr).find("em").css("display","block");
				counts+=1;
			}else{
				$(tr).find("span:last").html((counts+1)+'-'+(counts+parseInt($($(tr).find('input')[1]).val().trim())));
				$(tr).find("em").css("display","block");
				counts+=parseInt($($(tr).find('input')[1]).val().trim());
			}
		}
	})
}

/**
 * 校验浮点型
 * @param param
 */
function inputFloatRank(param){
	if(!/^\d+|(\d+\.\d*[1-9])$/.test($(param).val())){
		$(param).parent().next().html('能输大于或等于零的整数或小数')
		$(param).parent().next().css("display","block");
	}else 
		$(param).parent().next().css("display","none");
}

/**
 * 校验选项或评分对象不能为空
 * @param param
 * @param type
 */
function validateItems(param,type){
	var desc='';
	switch(type){
		case 1:desc="选项";
			break;
		case 2:desc="评分对象";
			break;
	}
	var flag=true;
	$.each($(param).find('tr'),function(idx,tr){
		if($($(tr).find('input')[0]).val().trim()==""){
			$(tr).find('i').html(desc+"不能为空");
			$(tr).find('i').css("display","block");
			if(flag)
				flag=false;
		}else 
			$(tr).find('i').css("display","none");
	})
	return flag;
}

/**
 * 校验投票名称不能为空
 * @param param
 */
function checkVoteNameNull(param){
	if($(param).val().trim()==""){
		$(param).next().html("投票名称不能为空");
		$(param).next().css("display","block");
	}if($(param).val().length>20){
		$(param).next().html("投票名称过长(20字以内)");
		$(param).next().css("display","block");
	}else 
		$(param).next().css("display","none");
}

/**
 * 校验选项或评分对象不能为空
 * @param param
 * @param type
 */
function checkNull(param,type){
	var desc='';
	switch(type){
		case 1:desc="选项";
			break;
		case 2:desc="评分对象";
			break;
	}
	if($(param).val().trim()==""){
		$(param).parent().next().html(desc+'不能为空');
		$(param).parent().next().css("display","block");
		return false;
	}else 
		$(param).parent().next().css("display","none");
	return true;
}

/**
 * 发起投票校验
 */
function check(){
	if($("#voteId").val()!=''){
		var type=$("#voteType").find('a[style="display: block;"]').text();
		if(type=='选项')
			checkItems();
		else if(type=='评分')
			checkScore();
		else if(type=='排名')
			checkRanking();
		else
			checkColleague()
	}else{
		if($('div.tplxBox').find('li').find("a.current").html()=='选项')
			checkItems();
		else if($('div.tplxBox').find('li').find("a.current").html()=='评分')
			checkScore();
		else if($('div.tplxBox').find('li').find("a.current").html()=='排名')
			checkRanking();
		else
			checkColleague()
	}
}

/**
 * 更改最高分最低分类型时校验
 * @param param
 */
function changeScoreType(param){
	if($(param).find("option:selected").val()=="0"){
		$(param).parent().prev().find('input').attr("onblur","inputIntScore(this)");
		$(param).parent().prev().prev().find('input').attr("onblur","inputIntScore(this)");
		inputIntScore($(param).parent().prev().find('input'));
		inputIntScore($(param).parent().prev().prev().find('input'));
	}else{
		$(param).parent().prev().find('input').attr("onblur","inputFloatScore(this)");
		$(param).parent().prev().prev().find('input').attr("onblur","inputFloatScore(this)");
		inputFloatScore($(param).parent().prev().find('input'));
		inputFloatScore($(param).parent().prev().prev().find('input'));
	}
}

/**
 * 排名校验
 */
function checkItems(){
	var len=$($("#rcxs_main_0").find("table")[0]).find("tr").length;
	var flag=true;
	if(len<parseInt($("#rcxs_main_0").find("select").val())){
		$("#rcxs_main_0").find("select").next().next().next().html("必选项不能大于选项数");
		$("#rcxs_main_0").find("select").next().next().next().css("display","block");
		flag=false;
	}else 
		$("#rcxs_main_0").find("select").next().next().next().css("display","none");
	if(!checkPublicForm())
		flag=false;
	if(!validateItems($($("#rcxs_main_0").find("table")[0]),1))
		flag=false;
	if(!flag){
		msgBoxTohome("警告","输入项有误,请检查输入项");
		return;
	}
	getItemsParam();
}

/**
 * 必选项修改事件
 * @param param
 */
function changeSelectItem(param){
	$(param).next().next().find("span").html($(param).val())
	//$(param).find("option")[$("#param").val()].prop("selected", 'selected');;
 
}

/**
 * 评分校验
 */
function checkScore(){
	var flag=false;
	if($("#rcxs_main_1").find("div:first-child").find(".se24").val().trim()=="0"){
		if(!flag)
			flag=inputIntScore($($("#rcxs_main_1").find("div:first-child").find("input")[0]))?false:true;
		else
			inputIntScore($($("#rcxs_main_1").find("div:first-child").find("input")[0]));
		
		if(!flag)
			flag=inputIntScore($($("#rcxs_main_1").find("div:first-child").find("input")[1]))?false:true;
		else
			inputIntScore($($("#rcxs_main_1").find("div:first-child").find("input")[1]));
	}else{
		if(!flag)
			flag=inputFloatScore($($("#rcxs_main_1").find("div:first-child").find("input")[0]))?false:true;
		else
			inputFloatScore($($("#rcxs_main_1").find("div:first-child").find("input")[0]));
		
		if(!flag)
			flag=inputFloatScore($($("#rcxs_main_1").find("div:first-child").find("input")[1]))?false:true;
		else
			inputFloatScore($($("#rcxs_main_1").find("div:first-child").find("input")[1]));
	}
	
	var len=$("#rcxs_main_1").find("table").find("tr").length;
	
	if(len<parseInt($("#rcxs_main_1").find("select.ptselect").val())){
		$("#rcxs_main_1").find("select.ptselect").next().next().html("必选项不能大于选项数");
		$("#rcxs_main_1").find("select.ptselect").next().next().css("display","block");
		flag=true;
	}else 
		$("#rcxs_main_1").find("select.ptselect").next().next().css("display","none");
	
	if(len<parseInt($("#rcxs_main_1").find("select.ptselect2").val())){
		$("#rcxs_main_1").find("select.ptselect2").next().html("评选结果人数不能多余评选对象数");
		$("#rcxs_main_1").find("select.ptselect2").next().css("display","block");
		flag=true;
	}else 
		$("#rcxs_main_1").find("select.ptselect2").next().css("display","none");
	
	if(!checkPublicForm())
		flag=true;
	if(!validateItems($($("#rcxs_main_1").find("table")[0]),2))
		flag=true;
	if(flag){
		msgBoxTohome("警告","输入项有误,请检查输入项");
		return;
	}
	getScoreParam();
}

/**
 * 排名校验
 */
function checkRanking(){
	
	var sum=0;
	var reg=/^\d+$/;
	var regFloat=/^\d+|(\d+\.\d*[1-9])$/;
	var flag=false;
	var tmpFlag=false;
	$.each($($("#rcxs_main_2").find("table")[0]).find("tr").find('input[name="quota"]'),function(idx,input){
		tmpFlag=false;
		if(!reg.test($(input).val())){
			$(input).parent().next().next().html('数量只能输入大于零的整数');
			$(input).parent().next().next().css("display","block");
			tmpFlag=true;
		}else {
			$(input).parent().next().next().css("display","none");
		}
		if(!regFloat.test($(input).prev().prev().prev().val())){
			$(input).parent().next().html('分数只能输入大于零的实数');
			$(input).parent().next().css("display","block");
			tmpFlag=true;
		}else {
			$(input).parent().next().css("display","none");
		}
		if(tmpFlag){
			flag=true;
			return true;
		}
		sum+=parseInt($(input).val());
	});
	if(!checkPublicForm())
		flag=true;
	if(!validateItems($($("#rcxs_main_2").find("table")[1]),1))
		flag=true;
	if(flag){
		msgBoxTohome("警告","输入项有误,请检查输入项");
		return;
	}
	var itemCounts=$($("#rcxs_main_2").find("table")[1]).find("tr").length;
	if(itemCounts<sum){
		msgBoxTohome("警告","各名次名额总数不能大于选项数");
		return;
	}
	getRankParam();
}

/**
 * 在同事中选择校验
 */
function checkColleague(){
	var flag=false;
	if($("#join_person").prev().prev().prev().val().trim()==""){
		$("#join_person").next().html("请选择参选同事");
		$("#join_person").next().css("display","block");
		flag=true;
	}else
		$("#join_person").next().css("display","none");
	
	var len=$("#hiduserid3").find("input").length;
	if(len<parseInt($("#bixuantongs").val())){
		$("#bixuantongs").parent().find("i").html("必选同事数量必须小于参选人数");
		$("#bixuantongs").parent().find("i").css("display","block");
		flag=true;
	}else 
		$("#bixuantongs").parent().find("i").css("display","none");
	if(!checkPublicForm())
		flag=true;
	if(flag){
		msgBoxTohome("警告","输入项有误,请检查输入项");
		return;
	}
	getColleagueParam();
}

/**
 * 获取公共部分参数
 */
function getPublicParam(){
	var param='';
	param+="title="+$("#voteName").val();
	if($("#voteId").val().trim()!='')
		param+="&id="+$("#voteId").val();
	if($("#voteName").parent().parent().next().find("textarea").val().trim()!="")
		param+="&remark="+$("#voteName").parent().parent().next().find("textarea").val();
	var endTime=$("#endTime").val().substr(0,$("#endTime").val().length-1);
	if(/^\d[4]年\d[2]月\d[1]$/.test(endTime))
		endTime=endTime.replace("年","-").replace("月","-0");
	else if(/^\d[4]年\d[1]月\d[2]$/.test(endTime))
		endTime=endTime.replace("年","-0").replace("月","-");
	else if(/^\d[4]年\d[1]月\d[1]$/.test(endTime)) 
		endTime=endTime.replace("年","-0").replace("月","-0");
	else 
		endTime=endTime.replace("年","-").replace("月","-");
	endTime=endTime+" "+$("#endTime").next().val()+":00:00";
	param+="&endTime="+endTime;
	var joinPeople="";
	$.each($("#hiduserid1").find("input"),function(i,value){
		joinPeople+=$(value).val()+";";
	});
	param+="&joinPeople="+joinPeople.substring(0,joinPeople.length-1);;
//	if($("#view_person").prev().prev().val().trim()!='')
	var viewPeople="";
	$.each($("#hiduserid2").find("input"),function(i,value){
		viewPeople+=$(value).val()+";";
	});
	param+="&viewPeople="+viewPeople.substring(0,viewPeople.length-1);
	 
	if($("#dstime1").attr("checked")=="checked"){
		var startTime=$("#startTime").val().substr(0,$("#startTime").val().length-1);
		if(/^\d[4]年\d[2]月\d[1]$/.test(startTime))
			startTime=startTime.replace("年","-").replace("月","-0");
		else if(/^\d[4]年\d[1]月\d[2]$/.test(startTime))
			startTime=startTime.replace("年","-0").replace("月","-");
		else if(/^\d[4]年\d[1]月\d[1]$/.test(startTime)) 
			startTime=startTime.replace("年","-0").replace("月","-0");
		else 
			startTime=startTime.replace("年","-").replace("月","-");
		startTime=startTime+" "+$("#startTime").next().val()+":00:00";
		param+="&startTime="+startTime;
	}
	param+="&resultType="+$("#startTime").parent().parent().next().find("select").val();
	if($("#che1").attr("checked")=="checked")
		param+="&isAnonymout="+1;
	return param;
}

/**
 * 获取选项参数
 */
function getItemsParam(){
	var param=getPublicParam();
	param+="&type="+0;
	param+="&minNum="+$("#rcxs_main_0").find("select").val();
	param+="&criteria="+$("#rcxs_main_0").find("select").val();
	var picarr='';
	var oldPicArr='';
	$.each($("#rcxs_main_0").find('input[type="text"]'),function(idx,input){
		param+="&itemName="+$(input).val();
		if($(input).next().next().val()=='')
			picarr=picarr.concat('0');
		else
			picarr=picarr.concat('1');
		if($("#voteId").val().trim()!=''){
			if($(input).parent().next().next().find("img").attr("src")=='')
				oldPicArr=oldPicArr.concat('0;');
			else
				oldPicArr=oldPicArr.concat($(input).parent().next().next().find("img").attr("src").substr($(input).parent().next().next().find("img").attr("src").lastIndexOf("/")+1)).concat(';');
		}
	})
	param+="&picarr="+picarr;
	if(oldPicArr!='')
		param+="&oldPicArr="+oldPicArr;
	
	submitVote(param,$("#rcxs_main_0").find('input[type="file"]'));
}

/**
 * 获取评分参数
 */
function getScoreParam(){
	var param=getPublicParam();
	param+="&type="+1;
	param+="&minNum="+$($("#rcxs_main_1").find("select")[1]).val();
	param+="&criteria="+$($("#rcxs_main_1").find("select")[2]).val();
	var picarr='';
	var oldPicArr='';
	$.each($("#rcxs_main_1").find("table").find('input[type="text"]'),function(idx,input){
		param+="&itemName="+$(input).val();
//		if($(input).parent().next().next().find("img").attr("src")=='')
//			picarr=picarr.concat('0');
//		else
//			picarr=picarr.concat('1');
		if($(input).next().next().val()=='')
			picarr=picarr.concat('0');
		else
			picarr=picarr.concat('1');
		if($("#voteId").val().trim()!=''){
			if($(input).parent().next().next().find("img").attr("src")=='')
				oldPicArr=oldPicArr.concat('0;');
			else
				oldPicArr=oldPicArr.concat($(input).parent().next().next().find("img").attr("src").substr($(input).parent().next().next().find("img").attr("src").lastIndexOf("/")+1)).concat(';');
		}
	});
	param+="&picarr="+picarr
	param+="&oldPicArr="+oldPicArr;
	param+='&ext={"maxScore":'+$($("#rcxs_main_1").find("div:first-child").find('input[type="text"]')[0]).val()+',"minScore":'+$($("#rcxs_main_1").find("div:first-child").find('input[type="text"]')[1]).val()+',"scoreType":'+$("#rcxs_main_1").find("div:first-child").find("select").val()+'}';
	submitVote(param,$("#rcxs_main_1").find('input[type="file"]'));
}

/**
 * 获取排名参数
 */
function getRankParam(){
	var param=getPublicParam();
	param+="&type="+2;
	var picarr='';
	var oldPicArr='';
	$.each($($("#rcxs_main_2").find("table")[1]).find('input[type="text"]'),function(idx,input){
		param+="&itemName="+$(input).val();
//		if($(input).parent().next().next().find("img").attr("src")=='')
//			picarr=picarr.concat('0');
//		else
//			picarr=picarr.concat('1');
		if($(input).next().next().val()=='')
			picarr=picarr.concat('0');
		else
			picarr=picarr.concat('1');
		if($("#voteId").val().trim()!=''){
			if($(input).parent().next().next().find("img").attr("src")=='')
				oldPicArr=oldPicArr.concat('0;');
			else
				oldPicArr=oldPicArr.concat($(input).parent().next().next().find("img").attr("src").substr($(input).parent().next().next().find("img").attr("src").lastIndexOf("/")+1)).concat(';');
		}
	});
	param+="&picarr="+picarr;
	param+="&oldPicArr="+oldPicArr;
	var minNum=0;
	var tmp='['
	$.each($($("#rcxs_main_2").find("table")[0]).find("tr"),function(idx,tr){
		tmp+='{"score":'+$($(tr).find("input")[0]).val()+',"count":'+$($(tr).find("input")[1]).val()+'},';
		minNum+=parseInt($($(tr).find("input")[1]).val());
	});
	param+="&minNum="+minNum;
	param+="&criteria="+$($("#rcxs_main_2").find("table")[0]).find("tr").length
	tmp=tmp.substr(0,tmp.length-1)+']';
	param+="&ext="+tmp;
	
	submitVote(param,$("#rcxs_main_2").find('input[type="file"]'));
}

/**
 * 获取同事中选择的参数
 */
function getColleagueParam(){
	var param=getPublicParam();
	param+="&type="+3;
	param+="&minNum="+$("#rcxs_main_3").find("select").val();
	$.each($("#hiduserid3").find("input"),function(idx,id){
		param+="&itemName="+$(id).val();
	});
	submitVote(param,undefined);
}

/**
 * 发起或修改投票提交
 * @param param
 * @param fileArr
 */
function submitVote(param,fileArr){
	$.ajaxFilesUpload({
        url: 'publishVote', 
        type: 'post',
        secureuri: false, //一般设置为false
        files: fileArr, // 上传文件的id、name属性名
        dataType: 'json', //返回值类型
        data:param,
        success: function(data, status){ 
        	if(data!=undefined){
        		if(data.error!=undefined){
        			
        		}else
        			self.location='voteCenter';
        	}
        },
        error: function(data, status, e){ 
        	if(data!=undefined){
        		if(data.error!=undefined){
        			
        		}
        	}
        }
    });
}

/**
 * 初始化在同事中选择必选项
 * @param param
 */
function initColleagueSelect(param){
	var ids=$("#hiduserid3").find("input")
	 
	if(ids.length>$(param.options).length){ 
		for(var i= $(param.options).length+1;i<=ids.length;i++){
			$(param).append('<option value="'+i+'">'+i+'</option>');
		} 
		
	} 
}

/**
 * 初始化必选项及评选结果下拉框
 * @param param
 */
function initSelectCount(param){
	var len=$(param).parent().parent().parent().prev().prev().find("tr").length;
	if(len>$(param).find("option").length){
		for(var i=$(param).find("option").length+1;i<=len;i++){
			$(param).append('<option value="'+i+'">'+i+'</option>');
		}
	}
}

/**
 * 公共部分校验
 */
function checkPublicForm(){
	if($("#voteName").val().trim()==""){
		$("#voteName").next().html("投票名称不能为空");
		$("#voteName").next().css("display","block");
		return false;
	}else if($("#voteName").val().length>20){
		$("#voteName").next().html("投票名称过长(20字以内)");
		$("#voteName").next().css("display","block");
		return false;
	}else 
		$("#voteName").next().css("display","none");
	if(!checkRemark($("#voteName").parents("tr").next().find("textarea")))
		return false;
	var now=new Date();
	var nowHour=parseInt(now.getHours());
	now=parseInt(now.getFullYear()+''+(now.getMonth()<9?("0"+(now.getMonth()+1)):(now.getMonth()+1))+''+(now.getDate()<9?("0"+now.getDate()):now.getDate()));
	var endTime=$("#endTime").val().substr(0,$("#endTime").val().length-1);
	if(/^\d{4}年\d{2}月\d{1}$/.test(endTime))
		endTime=parseInt(endTime.replace("年","").replace("月","0"));
	else if(/^\d{4}年\d{1}月\d{2}$/.test(endTime))
		endTime=parseInt(endTime.replace("年","0").replace("月",""));
	else if(/^\d{4}年\d{1}月\d{1}$/.test(endTime)) 
		endTime=parseInt(endTime.replace("年","0").replace("月","0"));
	else 
		endTime=parseInt(endTime.replace("年","").replace("月",""));
	console.log(endTime+"\t"+now)
	if(endTime<now){
		$("#endTime").next().next().html("投票截止时间必须晚于当前时间");
		$("#endTime").next().next().css("display","block");
		return false;
	}else if(endTime==now){
		if(parseInt($("#endTime").next().val())<=nowHour){
			$("#endTime").next().next().html("投票截止时间必须晚于当前时间");
			$("#endTime").next().next().css("display","block");
			return false;
		}
	}else 
		$("#endTime").next().next().css("display","none");
	
	if($("#dstime1").attr("checked")=='checked'){
		var startTime=$("#startTime").val().substr(0,$("#startTime").val().length-1);
		if(/^\d{4}年\d{2}月\d{1}$/.test(startTime))
			startTime=parseInt(startTime.replace("年","").replace("月","0"));
		else if(/^\d{4}年\d{1}月\d{2}$/.test(startTime))
			startTime=parseInt(startTime.replace("年","0").replace("月",""));
		else if(/^\d{4}年\d{1}月\d{1}$/.test(startTime)) 
			startTime=parseInt(startTime.replace("年","0").replace("月","0"));
		else
			startTime=parseInt(startTime.replace("年","").replace("月",""));
		if(startTime<now){
			$("#startTime").next().next().html("投票开始时间必须晚于当前时间");
			$("#startTime").next().next().css("display","block");
			return false;
		}else if(startTime==now){
			if(parseInt($("#startTime").next().val())<=nowHour){
				$("#startTime").next().next().html("投票开始时间必须晚于当前时间");
				$("#startTime").next().next().css("display","block");
				return false;
			}
		}else if(startTime>endTime){
			console.log(startTime+"\t"+endTime)
			$("#startTime").next().next().html("投票开始时间必须早于投票截止时间");
			$("#startTime").next().next().css("display","block");
			return false;
		}else if(startTime==endTime){
			console.log(startTime+"\t"+endTime+"\t"+parseInt($("#startTime").next().val())+"\t"+parseInt($("#endTime").next().val()))
			if(parseInt($("#startTime").next().val())>=parseInt($("#endTime").next().val())){
				$("#startTime").next().next().html("投票开始时间必须早于投票截止时间");
				$("#startTime").next().next().css("display","block");
				return false;
			}
		}else 
			$("#startTime").next().next().css("display","none");
	}
	if($("#showusers1").val().trim()==''){
		$("#showusers1").parent().find(".errmsg5").html("投票人员不能为空");
		$("#showusers1").parent().find(".errmsg5").css("display","block");
		return false;
	}else 
        $("#showusers1").parent().find(".errmsg5").css("display","none");
	return true;
}

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
function showLevels(type){
	var obj;
	if(type==1)
		obj=$("#vote_person");
	else if(type==2)
		obj=$("#view_person");
	else 
		obj=$("#join_person");
	
	$(obj).css("display","block");
	if(levelInfos==undefined)
		initLevelInfos();
	$(obj).find("ul").remove();
	var tree=$('<ul name="treeArea" onblur="closeTree()"></ul>');
	recurring(tree,levelInfos,type);
	$(obj).append(tree);
	$.each($(tree).find("li"),function(idx,li){
		if(idx==0){
			$($(li).find("span")[0]).attr("class","company");
			$($(li).find("a")[0]).attr("class","current");
		}
	})
	initSelected(obj);
}

/**
 * 初始哈已选择的项
 * @param param
 */
function initSelected(param){
	if($(param).prev().prev().val()=='')
		return;
	$.each($(param).find('input[type="checkbox"]'),function(idx,input){
		$.each($(param).prev().prev().val().split(";"),function(idxSelect,id){
			if(id==$(input).val()){
				$(input).attr("checked","checked");
				return false;
			}
		})
	})
	OnSelectRecurring($(param).find("ul:first-child"));
}

/**
 * 关闭树
 */
function closeTree(param){
	$(param).prev().prev().val('');
	$(param).prev().prev().prev().val('');
	var ids='';
	var names='';
	var idsTmp='';
	$.each($(param).find('input[type="checkbox"]:checked'),function(idx,input){
		if($(input).prev().length==0){
			ids=ids.concat($(input).val()).concat(";");
			names=names.concat($(input).next().find('label').html()).concat(";");
		}else{
			idsTmp=idsTmp.concat($(input).val()).concat(";");
		}
	});
	
	if(ids!='')
		ids=ids.substr(0,ids.length-1);
	$(param).prev().prev().val(ids);
	$(param).prev().prev().prev().val(names)
	$(param).find("ul").remove();
	$(param).css("display","none");
}

/**
 * 递归层级结构
 * @param tree
 * @param obj
 * @returns
 */
function recurring(tree,obj,type){
	$.each(obj,function(idx,node){
		var li=$('<li name="treeArea"></li>');
		if(node.type==1){
			li.append('<p name="treeArea" class="treezd" onclick="changeTree(this)"></p><input type="checkbox" value="'+node.id+'" onclick="selectIt(this,'+node.type+')"><a href="#1" name="treeArea" onclick="treeSelect(this,'+type+')"><span name="treeArea" class="department" onclick="treeSelect(this,'+type+')"></span><label name="treeArea">'+node.name+'</label></a>');
			li.attr("onclick","focusInput()");
		}else {
			li.append('<input type="checkbox" value="'+node.id+'" onclick="selectIt(this,'+node.type+')"><a name="treeArea" href="#1" onclick="treeSelect(this,'+type+')"><span name="treeArea" class="usersp" onclick="treeSelect(this,'+type+')"></span><label name="treeArea">'+node.name+'</label></a>');
		}
		if(node.type==1&&node.nodes.length>0){
			var nodeTree=$('<ul name="treeArea"></ul>');
			$(nodeTree).css("display","none");
			nodeTree=recurring(nodeTree,node.nodes);
			li.append(nodeTree);
		}
		tree.append(li);
	})
	return tree;
}

/**
 * 组织架构选中
 * @param param
 * @param type
 */
function selectIt(param,type){
	if(type==1){
		if($(param).attr('checked')=="checked")
			$(param).parent().find('input[type="checkbox"]').attr("checked","checked");
		else 
			$(param).parent().find('input[type="checkbox"]').removeAttr("checked");
	}
	OnSelectRecurring($(param).parents("div").find("ul:first-child"));
}

/**
 * 递归组织架构选中
 * @param param
 */
function OnSelectRecurring(param){
	$.each($(param).find("li"),function(idx,li){
		if($(li).find("ul").length>0)
			OnSelectRecurring($($(li).find("ul")[0]));
		else{
			if($(li).parent().find('input[type="checkbox"]:checked').length<$(li).parent().find('input[type="checkbox"]').length)
				$(li).parent().prev().prev().removeAttr("checked");
			else if($(li).parent().find('input[type="checkbox"]').not("input:checked").length==0)
				$(li).parent().prev().prev().attr("checked","checked");
		}
	})
}

/**
 * 选中人员
 * @param param
 */
function treeSelect(param,type){
	var obj;
	if(type==1)
		obj=$("#vote_person");
	else if(type==2)
		obj=$("#view_person");
	else 
		obj=$("#join_person");
	$(obj).find("a").removeAttr("class");
	if(param.nodeName=="A"){
		$(param).attr("class","current");
	}else{
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

$(document).ready(function(){
	$("#vote_person").hover(
			function () {
				  
			},
			function(){
				closeTree($("#vote_person"));
				Checkvote_person();
			});
	$("#view_person").hover(
			function () {
				  
			},
			function(){
				closeTree($("#view_person"));
			});
	$("#join_person").hover(
			function () {
				  
			},
			function(){
				closeTree($("#join_person"));
			});
	var today=new Date();
	today.setHours(0);
	today.setMinutes(0);
	today.setSeconds(0);
	today.setMilliseconds(0);
	var hour=parseInt(today.getHours());
	$($("#publicDiv").find("select.se24")[0]).attr("value",hour+1);
	$($("#publicDiv").find("select.se24")[1]).attr("value",hour+2);
	var todayStr=today.getFullYear()+'年'+(today.getMonth()+1)+'月'+today.getDate()+'日';
	$("#startTime").val(todayStr);
	today.setTime(today.getTime()+604800000);
	var anotherDayStr=today.getFullYear()+'年'+(today.getMonth()+1)+'月'+today.getDate()+'日';
	$("#endTime").val(anotherDayStr);
	$.each($('input[type="file"]'),function(idx,file){
		$(file).uploadPreview({
 			width : 90,
 			height : 60,
 			imgPreview : $(file).parent().next().next().find("img"),
 			imgType : [ "png", "jpg" ],
 			callback : function() {
 				$(file).parent().next().next().css("display","block");
 				$(file).prev().html("重新上传...");
 				$(file).attr("title","重新上传...");
 				onloalrame();
 				return false;
 			}
 		}); 
	})
	initLevelInfos();
})

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


function Checkvote_person(){
//	if($("#vote_person").prev().prev().val().trim()!=''){
	if($("#showusers1").val().trim()==''){
		$("#showusers1").parent().find(".errmsg5").html("投票人员不能为空");
//		$("#vote_person").next().css("display","none");		
//	}else{
//		$("#vote_person").next().html("投票人员不能为空");
//		$("#vote_person").next().css("display","block");
	}
}

function checkRemark(param){
	if($.trim($(param).val())==''){
		$(param).next().html("投票规则不能为空")
		return false;
	}else{
		$(param).next().html("");
		if($(param).val().length>1000){
			$(param).next().html("投票规则不能超过1000字")
			return false;
		}
	}
	return true;
}

/** 统一树插件显示选择用户(投票人员,可查看投票报表人员,参选同事) **/
function showusersDiv(_type){
	var _title;
	if(_type==1)
		_title="投票人员选择";
	else if(_type==2)
		_title="可查看投票报表人员选择";
	else 
		_title="参选同事选择";
	$("#hid_action_type").val(_type);
	topDiv.show();
	new openCommonView({
		title:_title,
		setting:{
			check: {
				enable: true,
				chkStyle: "checkbox"
			}			
		},
		show_user:true,
		isMultiple:true,
		userids:'hiduserid'+_type,
		usernames:'hidusername'+_type,
		closeEvent:onClickUsers
	});	
}
function onClickUsers(event, treeId, treeNode){
	var _actionid=$("#hid_action_type").val();
	var uservalues="";
	$.each($("#hiduserid"+_actionid).find("input"),function(i,v){
		uservalues+=$("#hidusername"+_actionid).find("#_username"+i).val()+";";
	});
	$("#showusers"+_actionid).attr("title",uservalues);
	$("#showusers"+_actionid).val(uservalues);
	topDiv.hide();
}
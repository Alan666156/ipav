/***
 * 消息弹出层
 * @param data
 * title:标题
 * centent:内容
 * buttonName:button名称
 */
function msgBox(title,centent,buttonName){drowMsgBox(title,centent,buttonName);}


/***
 * 首页消息弹出层
 * @param data
 * title:标题
 * centent:内容
 * buttonName:button名称
 */
function msgBoxTohome(title,centent,buttonName){drowMsgBox(title,centent,buttonName);}

function drowMsgBox(title,centent,buttonName){
	var html="<div id='msgBox_simScrollCont' class='simScrollCont' ></div><div  id='msgBox'>"; 
	html+="<div class='simScrollContBox4' id='msgBox_simScrollContBox4'>";
	html+="<div class='simScrollContBoxs4' >";
	html+="<div class='tctitle'>"+title+"<a href='javascript:;' onClick='MsgBoxColse()' title='关闭'></a></div>";
	html+="<div class='ptx'></div>";
	html+="<div class='alertBox'>";
	html+="<p class='alertmsg' align='center'>"+centent+"</p>";
	html+="<p align='center'>";
	html+="<input type='button' style='margin-top:25px;' ";
	if(buttonName){html+=" value='"+buttonName+"'";}else{html+=" value='确定'";}
	html+="  class='no  mr20'  onClick='MsgBoxColse();'>";
	html+="</p></div></div> </div></div>";
	$(document.body).append(html);
	var left=($(window).width()-$("#msgBox_simScrollContBox4").width())/2;
	var top=$(window.parent.document).scrollTop()+(window.screen.availHeight-400-$("#msgBox_simScrollContBox4").height())/2;
	$("#msgBox_simScrollContBox4").css("position","absolute");	 
	$("#msgBox_simScrollContBox4").css("left",left); 
	$("#msgBox_simScrollContBox4").css("top",top); 
	$("#msgBox_simScrollContBox4").css("display","block");
	$("#msgBox_simScrollCont").css("display","block"); 
	$("#msgBox_simScrollCont").css("width",$(document).width()); 
	$("#msgBox_simScrollCont").css("height",$(document).height()); 	 
	var fatherScrollConts =$(window.top.document.body).find("iframe").contents().find("div#fatherScrollCont");
	if(fatherScrollConts.length==0)fatherScrollConts=$("#fatherScrollCont");
	if(fatherScrollConts.length!=0){$(fatherScrollConts).show()}
	//$(window.top.document.body).addClass("html-body-overflow");
	var fatherptlfbg =$(window.top.document.body).find("div#ptlfbg2");
	if(fatherptlfbg.length!=0){		$(fatherptlfbg).show();}
	//$(document.body).addClass("html-body-overflow");
}
/**
 * 消息对话框
 * @param data
 * title:标题
 * centent:内容
 * okfunction:点击是后触发的方法
 * nofunction:点击是后触发的方法
 * param:方法的参数
 */
function msgDialog(data){
var html="<div id='msgBox_simScrollCont' class='simScrollCont' ></div><div  id='msgBox'><div class='simScrollContBox4' id='msgBox_simScrollContBox4'>";
	html+="<div class='simScrollContBoxs4' ><div class='tctitle'>"+data.title+"<a href='javascript:;' onClick='MsgBoxColse()' title='关闭'></a></div>";
	html+="<div class='ptx'></div><div class='alertBox'><p class='alertmsg' align='center'>"+data.centent+"</p>";
	html+="<div class='alertbut'><input type='button' value='否' class='no fr mr20 mt40'  id='msgNoInput'>";
	html+="<input type='button' value='是' class='yes fr mr20 mt40' id='msgOkInput'></div></div> </div> </div> ";	 
	$(document.body).append(html);
	var left=($(window).width()-$("#msgBox_simScrollContBox4").width())/2;
	var top=$(window.parent.document).scrollTop()+(window.screen.availHeight-400-$("#msgBox_simScrollContBox4").height())/2;
	$("#msgBox_simScrollContBox4").css("position","absolute");
	$("#msgBox_simScrollContBox4").css("left",left); 
	$("#msgBox_simScrollContBox4").css("top",top); 	
	$("#msgBox_simScrollContBox4").css("display","block");	
	var fatherScrollConts;
	fatherScrollConts =$(window.top.document.body).find("iframe").contents().find("div#fatherScrollCont");
	if(fatherScrollConts.length==0){fatherScrollConts=$("#fatherScrollCont")};
	if(fatherScrollConts.length!=0){$(fatherScrollConts).show()}
	if(fatherScrollConts.length!=2){$("#msgBox_simScrollCont").css("display","block");}	
	var fatherptlfbg =$(window.top.document.body).find("div#ptlfbg2");
	if(fatherptlfbg.length!=0){$(fatherptlfbg).show();}
	$("#msgBox_simScrollCont").css("width",$(document).width()); 
	$("#msgBox_simScrollCont").css("height",$(document).height()); 
	$(window.top.document.body).addClass("html-body-overflow");
	$("#msgOkInput").on("click",function(){
		MsgBoxColse()
		if(data.okfunction){
			if(data.param)
				data.okfunction(data.param[0],data.param[1],data.param[2],data.param[3],data.param[4],data.param[5]);
			else
				data.okfunction();
			}	});
	$("#msgNoInput").on("click",function(){
		MsgBoxColse();
		if(data.nofunction){
			if(data.param)
				data.nofunction(data.param[0],data.param[1],data.param[2],data.param[3],data.param[4],data.param[5]);
			else
				data.nofunction();
		}	});
}

/***
 * 首页消息对话框
 * @param data
 * title:标题
 * centent:内容
 * okfunction:点击是后触发的方法
 * nofunction:点击是后触发的方法
 * param:方法的参数
 */
function msgDialogTohome(data){msgDialog(data)}

/**
 * 关闭消息框
 */
function MsgBoxColse(){
	$("#msgBox").remove();
	$("#msgBox_simScrollCont").remove();  
	$(document.body).removeClass("html-body-overflow");
	var fatherptlfbg;
	fatherptlfbg =$(window.top.document.body).find("div#ptlfbg2");
	if(fatherptlfbg.length!=0){
		fatherptlfbg.hide();
	}
	var fatherScrollConts;
	fatherScrollConts =$(window.top.document.body).find("iframe").contents().find("div#fatherScrollCont");
	if(fatherScrollConts.length==0)fatherScrollConts=$("#fatherScrollCont");
	$(fatherScrollConts).hide();
	$(window.top.document.body).removeClass("html-body-overflow");	
}

/**
 * 加载中提示
 */

function loadingMsg(title,center){
	var html="<div id='msgBox_simScrollCont' class='simScrollCont' ></div><div  id='msgBox'><div class='simScrollContBox4' id='msgBox_simScrollContBox4' style='width:350px'>";
	html+="<div class='simScrollContBoxs4' style='width:350px'><div class='tctitle'>"+title+"</div><div class='ptx'></div>";
	html+="<div class='alertBox'><p class='alertmsg' align='center'><img src='/pages/images/platform/xubox_loading2.gif'/></p><br/>";
	html+="<p class='alertmsg' align='center'>"+center+"</p></div></div> </div></div>";
	$(document.body).append(html);
	var left=($(window).width()-$("#msgBox_simScrollContBox4").width())/2;	
	var top=$(window.parent.document).scrollTop()+(window.screen.availHeight-400-$("#msgBox_simScrollContBox4").height())/2;
	$("#msgBox_simScrollContBox4").css("position","absolute");
	$("#msgBox_simScrollContBox4").css("left",left); 
	$("#msgBox_simScrollContBox4").css("top",top); 
	$("#msgBox_simScrollContBox4").css("display","block");
	$("#msgBox_simScrollCont").css("display","block"); 
	$("#msgBox_simScrollCont").css("width",$(document).width()); 
	$("#msgBox_simScrollCont").css("height",$(document).height()); 	 
	var fatherScrollConts =$(window.top.document.body).find("iframe").contents().find("div#fatherScrollCont");
	if(fatherScrollConts.length==0)fatherScrollConts=$("#fatherScrollCont");
	if(fatherScrollConts.length!=0){$(fatherScrollConts).show()}
	$(window.top.document.body).addClass("html-body-overflow");
	var fatherptlfbg =$(window.top.document.body).find("div#ptlfbg2");
	if(fatherptlfbg.length!=0){		$(fatherptlfbg).show();}
	$(document.body).addClass("html-body-overflow");
}

/**
 * @author GaoYang
 * @data   2015年03月21日
 * @work   打开选择机构组织人员的公用视图
 */
var openCommonView=function(parameter){
	
	/*
	* this(当前对象)
	*/
	var _self=this;
	
	/*
	* 全局参数 
	*/
	var Global_Parm=null;
	var _self_div_ID="_simalpha_ID";
	var _self_divBox_ID="_simaltBox_ID";
	
	/*
	* 判断为null或者空值 
	*/
	_self.IsNull = function(value) {
	    if (value == undefined || value == null || value == "") {
	        return true;
	    }
	    return false;
	};
	
	/*
	* 插件默认数据
	*/
	_self.DefaultData={
			title:"请选择",//默认标题
			setting://默认树插件显示参数
			{
				//默认数据类型
				data:{
					simpleData: {
						enable: true,
						idkey:"id",
						pIdKey:"Pid",
						nameKey:"name"
					}
				},
				//默认数据视图
				view:{
					nameIsHTML: true
				},
				//默认按钮样式
				check: {
					enable: false
				},
				//默认回调函数
				callback:{
					onCheck:function(){}
				}
		 },
		 show_user:false,//true表示在树插件中显示用户,默认false不显示
		 isMultiple:false,//true表示是否允许多选,即checkbox模式,默认false表示单选模式
		 isAllChk:false,//true表示初始化时,是否全部选中,该属性只有当isMultiple为true时才有效,默认false则反之
		 userids:'hiduserids',//保存用户选择的对象的id集合
		 usernames:'hidusernames',//保存用户选择的对象的name集合
		 closeEvent:function(){//关闭视图后触发的函数
			 $("#"+_self_div_ID).remove();
			 $("#"+_self_divBox_ID).remove();
		 }			
	};
	
	/*
	*  装配插件参数
	*/
	_self.readData=function(){
		var _parm=null;
		if(_self.IsNull(parameter)){
			_parm=_self.DefaultData;
		}else{
			_parm={
					title:_self.IsNull(parameter.title) ? _self.DefaultData.title:parameter.title,
					setting:{
						data:_self.IsNull(parameter.setting.data) ? _self.DefaultData.setting.data:parameter.setting.data,
						view:_self.IsNull(parameter.setting.view) ? _self.DefaultData.setting.view:parameter.setting.view,
						check:_self.IsNull(parameter.setting.check) ? _self.DefaultData.setting.check:parameter.setting.check,
						callback:_self.IsNull(parameter.setting.callback) ? _self.DefaultData.setting.callback:parameter.setting.callback,
					},
					show_user:_self.IsNull(parameter.show_user) ? _self.DefaultData.show_user:parameter.show_user,
					isMultiple:_self.IsNull(parameter.isMultiple) ? _self.DefaultData.isMultiple:parameter.isMultiple,
					isAllChk:_self.IsNull(parameter.isAllChk) ? _self.DefaultData.isAllChk:parameter.isAllChk,
					userids:_self.IsNull(parameter.userids) ? _self.DefaultData.userids:parameter.userids,
					usernames:_self.IsNull(parameter.usernames) ? _self.DefaultData.usernames:parameter.usernames,
					closeEvent:_self.IsNull(parameter.closeEvent) ? _self.DefaultData.closeEvent:function(){
						_self.DefaultData.closeEvent();
						parameter.closeEvent();
					}
			}
		}
		return _parm;
	};	

	/*
	* 展示视图 
	*/
	_self.begin=function(){
		Global_Parm=_self.readData();
		if(_self.IsNull(Global_Parm)) return;
		$.post("/ipav/notice/showOrgTree",function(data_info) { 
			if(!Global_Parm.show_user){
				data_info=$.grep(data_info, function(v,i){
					 return v.isParent=="true";
		        });
				$.each(data_info,function(i,value){
					if(value.id=="0"){
						value["icon"]="/pages/images/platform/jigou.png";								 
					}else if(value.Pid=="0"){
						value["icon"]="/pages/images/platform/1_close.png";	
					}else{
						value["icon"]="/pages/images/platform/1_open.png";
					}
				});
			}
			//初始化树形插件界面
			var grayDivHtml='<div id="'+_self_div_ID+'" style="position: absolute;background: none repeat scroll 0% 0% #000;top: 0px;left: 0px;opacity: 0.4;"></div>';
			if(Global_Parm.isMultiple){
				Global_Parm.setting.callback={onCheck:_self.selectUsers};
				$(document.body).append(grayDivHtml+_self.createMultipleView());
				_self.setMultipleCss();
			}else{
				$(document.body).append(grayDivHtml+_self.createSingleView());
				_self.setSingleCss();
			}
			$("#close_img").bind("click",Global_Parm.closeEvent);
			$.fn.zTree.init($("#treeID"),Global_Parm.setting,data_info); 
			var treeObj = $.fn.zTree.getZTreeObj("treeID");
			treeObj.expandAll(true);
			if(Global_Parm.isMultiple){
				//初始化默认人员信息		
				$.each($("#"+Global_Parm.userids).find("input"),function(){
					var userid=$(this).val();
					var node = treeObj.getNodeByParam("id",userid, null);
					if(!_self.IsNull(node))	treeObj.checkNode(node,true,true,true);
				});
			}
		});
	};
	
	/*
	 * 遮罩层css样式
	 */
	_self.setPubCss=function(){
		var w1=$(window).width();//页面body宽度
		var w2=$(document.body).width();//屏幕可见宽度
		var h1=$(window).height();
		var h2=$(document.body).height();
		$("#"+_self_div_ID).css("width",w1<w2?w2:w1); 
		$("#"+_self_div_ID).css("height",h1<h2?h2:h1);//document.body.clientHeight	
		var left=(w1-$("#_simaltBox_ID").outerWidth()) / 2;
		var top=(h1-$("#_simaltBox_ID").outerHeight()) / 2;
		$("#_simaltBox_ID").css("left",left>0?left:400); 
		$("#_simaltBox_ID").css("top",top>0?top:100); 
		$("#d2").css({
			'color': '#FFF',
			'width': '100%',
			'height': '40px',
			'font-size': '15px',
			'text-indent': '15px',
			'line-height': '40px',
			'background': 'none repeat scroll 0% 0% #48A8CF'
		});
		$("#close_img").css({
			'float': 'right',
			'width': '19px',
			'height': '19px',
			'display': 'block',
			'margin-top': '10px',
			'margin-right': '10px',
			'background':' url(/pages/images/systems/close1.jpg) repeat scroll 0% 0% transparent'
		});
	};
	
	/*
	* 单选Div的html
	*/
	_self.createSingleView=function(){
		var div_html='<div id="'+_self_divBox_ID+'" style="display: block;position: absolute;width: 274px;height: 410px;border: 5px solid #FFF;background: none repeat scroll 0% 0% #FFF;text-align: left;width: 274px;height: 410px;">'+
		'<div id="sd1"><div id="d2"><span>'+Global_Parm.title+'</span><a id="close_img" href="javascript:;" title="关闭"></a></div>'+
		'<div style="width: 100%;height: 10px;"></div><div id="slistbox"><div id="slistbox2"><ul id="treeID" class="ztree"></ul></div></div></div></div>';
		return div_html;
	};
	
	/*
	* 单选css样式
	*/
	_self.setSingleCss=function(){
		_self.setPubCss();		
		$("#sd1").css({
			'width': '272px',
			'height': '408px',
			'overflow': 'hidden',
			'borde': '1px solid #48A8CF',
			'background':'none repeat scroll 0% 0% #FFF'
		});
		$("#slistbox").css({
			'width': '252px',
			'height': '330px',
			'overflow':'hidden',
			'margin': '0px auto',
			'border': '1px solid #D9D9D9'
		});
		$("#slistbox2").css({
			'height': '310px',
			'overflow': 'auto',
			'margin-top': '9px',
			'margin-left': '9px'
		});
		

	};
	
	/*
	* 多选Div的html
	*/
	_self.createMultipleView=function(){
		var div_html='<div id="'+_self_divBox_ID+'" style="display: block;position: absolute;border: 5px solid #FFF;background: none repeat scroll 0% 0% #FFF;text-align: left;width: 716px;height: 412px;">'+
		'<div id="md1"><div id="d2"><span>'+Global_Parm.title+'</span><a href="javascript:;" id="close_img" title="关闭"></a></div><div style="width: 100%;height: 10px;"></div>'+
		'<div id="mlistbox"><div id="mlistbox2"><ul id="treeID" class="ztree"></ul></div></div><div id="muserlistBox"><div id="muserlist"><ul id="muserinfo" style="margin-top: 10px;list-style-type: none;"></ul></div></div>'+
		'<div id="mbutBox"><input type="button" id="yesbut" value="确定"><input type="button" id="cancelbut" value="取消"></div></div></div>';
		return div_html;
	};
	
	/*
	* 多选css样式
	*/
	_self.setMultipleCss=function(){
		_self.setPubCss();
		$("#md1").css({
			'width': '714px',
			'height': '410px',
			'overflow': 'hidden',
			'border': '1px solid #48A8CF',
			'background': 'none repeat scroll 0% 0% #FFF'
		});
		$("#mlistbox").css({
			'float': 'left',
			'width': '207px',
			'height': '296px',
			'margin-left': '9px',
			'border': '1px solid #D9D9D9'
		});
		$("#mlistbox2").css({
			'height': '237px',
			'overflow': 'auto',
			'margin-top': '9px',
			'margin-left': '9px'
		});
		$("#muserlistBox").css({
			'float': 'right',
			'width': '476px',
			'height': '296px',
			'overflow': 'hidden',
			'margin-right': '9px',
			'border': '1px solid #D9D9D9',
		});
		$("#muserlist").css({
			'width': '476px',
			'height': '296px',
			'overflow-y':' auto',
			'overflow-x': 'hidden'
		});
		$("#mbutBox").css({
			'width': '100%',
			'height': '45px',
			'text-align': 'center',
			'margin': '0px auto'
		});
		$("#yesbut").css({
			'color': '#FFF',
			'width': '65px',
			'height': '30px',
			'cursor': 'pointer',
			'border': '0px none',
			'margin-top': '15px',
			'background': 'url(/pages/images/platform/yes.png) repeat scroll 0% 0% transparent'
		});
		$("#cancelbut").css({
			'color': '#3E4D4D',
			'width': '65px',
			'height': '30px',
			'cursor': 'pointer',
			'border': '0px none',
			'margin-top': '15px',
			'margin-left': '30px',
			'background': 'url(/pages/images/platform/no.png) repeat scroll 0% 0% transparent'
		});
		$("#yesbut").bind("click",_self.backData);
		$("#cancelbut").bind("click",Global_Parm.closeEvent);
	};
	
	/*
	* 多选模式中选中对象样式 
	*/
	_self.setUserDivCss=function(){
		$("#muserinfo li").css({
			'float': 'left',
			'width': '80px',
			'height': '27px',
			'margin-left': '10px',
			'margin-bottom': '10px',
			'background': 'url(/pages/images/platform/altts.png) repeat scroll 0% 0% transparent'
		});
		$("#muserinfo li div").css({
			'float': 'left',
			'width': '63px',
			'height': '27px',
			'color': '#08384A',
			'overflow': 'hidden',
			'text-indent': '6px',
			'line-height': '27px',
			'white-space': 'nowrap',
			'text-overflow': 'ellipsis'
		});
		$("#muserinfo li a").css({
			'float': 'left',
			'width': '15px',
			'height': '27px',
			'display': 'block'
		});
		//绑定删除选中人员div事件
		$(".removeUser").bind("click",function(){
			  $(this).parent().remove();
			  var treeObj = $.fn.zTree.getZTreeObj("treeID");
			  var node = treeObj.getNodeByParam("id",$(this).prev().find("input").val(), null);
			  treeObj.checkNode(node, false, true,false);
		});
	};
	
	/*
	* 多选模式下树插件的默认回调函数
	*/
	_self.selectUsers=function(event, treeId, treeNode){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var nodes =  treeObj.getCheckedNodes(true);
		$("#muserinfo").html("");
		$(nodes).each(function(i,v){
			if(v.isParent==false)
				_self.checkSendsUser(v);
		});
	};
	
	/*
	* 多选模式选中后触发 
	*/
	_self.checkSendsUser=function(chkuser){
		if(_self.IsNull(chkuser)){
			return;
		}else{
			var curuser = _self.searchUser(chkuser.id);
			//选中状态
			if(chkuser.checked){
				//该用户不存在添加
				if(_self.IsNull(curuser)){
					$("#muserinfo").append("<li><div title='"+chkuser.name+"'>"+chkuser.name+"<input class='_hiduserids' value='"+chkuser.id+"' type='hidden'></div><a href='javascript:;' title='删除' class='removeUser'></a></li>");
					_self.setUserDivCss();
				}
			}
			//未选中状态
			if(!chkuser.checked){
				//该用户存在删除
				if(!_self.IsNull(curuser)){
					$(curuser).parent().parent().remove();
				}
			}
		}
	};
	
	/*
	* 排查选中对象是否存在
	*/
	_self.searchUser=function(uid){
		var rs=null;
		if(!_self.IsNull(uid)){
			$.each($("._hiduserids"),function(i,v){
				if(!_self.IsNull(v.value)&&uid==v.value)
					rs=v;				
			});
		}
		return rs;
	};
	
	/*
	*	多选模式回填数据 
	*/
	_self.backData=function(){
		//清空原来的数据
		$("#"+Global_Parm.userids).html("");
		$("#"+Global_Parm.usernames).html("");
		var show_values="";
		$.each($("#muserinfo li"),function(i,v){
			var hidid=$(v).find("input").val();
			var hidname=$(v).find("div").text();
			show_values+=hidname+";";
			_self.addHiddenValue(i,hidid,hidname);
		});
		Global_Parm.closeEvent();
	}
	
	/*
	* 多选模式回填数据到隐藏域
	*/
	_self.addHiddenValue=function(index,hidid,hidname){
		var idinput=$("#"+Global_Parm.userids).find("input[value="+hidid+"]");
		if($(idinput).length>0){
			return;
		}else{
			var maxIndex=$("#"+Global_Parm.userids+" input").length;
			$("#"+Global_Parm.userids).append("<input  type='hidden' value='"+hidid+"'/>");
			$("#"+Global_Parm.usernames).append("<input id='_username"+index+"' type='hidden'  value='"+hidname+"'/>");
		}
	};
	/*;
	*	启动插件 
	*/
	_self.begin();
};




/**
 * 图片放大
 */
/***说说图片放大预览START****/
var img_num=0;
var imgIndex=0; //图片标识
function imglook(imgs,ids)
{ 
	var html="";
	html+="<div id='fatherMask' class='uphotobg'></div><div class='playimgBox' style='height:580px;'>";
	html+="	<A href='#1' class='closeim' id='sayimage_colse'></A><div id='play' style='height:532px;'>";
	html+="<ul class='img_ul'>";
	for(var i=0;i<imgs.length;i++){
		if(i==ids)
		html+="<li style='display:block;'><a class='img_a img_abox'><img src='"+imgs[i].src+"'></a></li>";
		else
	    html+="<li  style='display:none;'><a class='img_a img_abox'><img src='"+imgs[i].src+"'></a></li> ";
	}
	html+="</ul>";
	html+="<a href='javascript:void(0)' class='prev_a change_a' title='上一张'><span></span></a>";
	html+="<a href='javascript:void(0)' class='next_a change_a' title='下一张'><span style='display:block;'></span></a>";
	html+="</div>";
	html+="<div class='img_hd'>";
	html+="<ul class='clearfix'>";
	for(var i=0;i<imgs.length;i++){
		html+="<li><a class='img_a'><img src='"+imgs[i].src+"' onload='imgs_load(this)'></a></li>";     
	}
	html+="</ul>";
	html+="<a class='bottom_a prev_a' style='opacity:0.7;'></a>";
	html+="<a class='bottom_a next_a' style='opacity:0.7;'></a>";
	html+="</div>";
	html+="</div>";
	$(document.body).append(html); 
	var top=$(window.parent.document).scrollTop()+(window.screen.availHeight-250-$(".playimgBox").height())/2;
	var left=($(window).width()-$(".playimgBox").width())/2;
	$(".playimgBox").css("left",left); 
	$(".playimgBox").css("top",top); 
   	$(".playimgBox").css("position","absolute");
   	imgIndex=ids;
	img_num=$(".img_ul").children("li").length; //图片个数
	//$(".img_ul li").hide(); //初始化图片	
	play();
	
	$(".playimgBox").show();  
	var playbgheises =$(window.top.frames.document.body).find("iframe").contents().find("div#fatherMask");
	if(playbgheises.length==0)playbgheises=$("#fatherMask");
	$(playbgheises).show();	
	$("#fatherMask").css("width",$(document).width()+50); 
	$("#fatherMask").css("height",$(document).height()); 	 
	fatherptlfbg =$(window.top.document.body).find("div#ptlfbg")
	if(fatherptlfbg) $(fatherptlfbg).show();
	$(window.top.document.body).addClass("html-body-overflow");
	//sagImgMaskShow();
	var playbgheises =$(window.top.frames.document.body).find("iframe").contents().find("div#fatherMask");
	$(playbgheises).click(function(){ 
         ImgMaskHide();  
         
     });
	 $('#sayimage_colse').click(function(){ 
         ImgMaskHide(); 
     });
 
	$(".img_hd ul").css("width",($(".img_hd ul li").outerWidth(true))*img_num); //设置ul的长度

	$(".bottom_a").css("opacity",0.7);	//初始化底部a透明度
	//$("#play").css("height",$("#play .img_ul").height());
	if (!window.XMLHttpRequest) {//对ie6设置a的位置
		$(".change_a").css("height",$(".change_a").parent().height());
	}
	
	$(".change_a").focus(function(){
		this.blur();
	});
	$(".bottom_a").hover(function(){//底部a经过事件
		$(this).css("opacity",1);	
	},function(){
		$(this).css("opacity",0.7);	 
	});

	$(".change_a").hover(function(){//箭头显示事件
		$(this).children("span").show();
	},function(){
		$(this).children("span").hide();
	});
	
	$(".img_hd ul li").click(function(){
		imgIndex=$(this).index();
		play();
	});

	$(".prev_a").click(function(){
		//i+=img_num;
		imgIndex--;
		//i=i%img_num;
		imgIndex=(imgIndex<0?img_num-1:imgIndex);
		play();
	}); 
	$(".next_a").click(function(){
		imgIndex++;
		//i=i%img_num;
		imgIndex=(imgIndex>(img_num-1)?0:imgIndex);
		play();
	}); 
} 

function play(){//动画移动	
	
	var img=new Image(); //图片预加载
	img.onload=function(){
		img_load(img,$(".img_ul").children("li").eq(imgIndex).find("img"))
	};
	img.src=$(".img_ul").children("li").eq(imgIndex).find("img").attr("src");
	//$(".img_ul").children("li").eq(i).find("img").(img_load($(".img_ul").children("li").eq(i).find("img")));
	$(".img_hd ul").children("li").eq(imgIndex).addClass("on").siblings().removeClass("on");
	if(img_num>7){//大于7个的时候进行移动
		if(imgIndex<img_num-3){ //前3个
			$(".img_hd ul").animate({"marginLeft":(-($(".img_hd ul li").outerWidth()+4)*(imgIndex-3<0?0:(imgIndex-3)))});
		}else if(imgIndex>=img_num-3){//后3个
			$(".img_hd ul").animate({"marginLeft":(-($(".img_hd ul li").outerWidth()+4)*(img_num-7))});
		}
	}
	if(!window.XMLHttpRequest){//对ie6设置a的位置
		$(".change_a").css("height",$(".change_a").parent().height());
	}
	
}

function img_load(img_id,now_imgid){//大图片加载设置 （img_id 新建的img,now_imgid当前图片）
	if(img_id.width/img_id.height>1){
		if(img_id.width >=$("#play").width())
		$(now_imgid).width($("#play").width());
	}else{
		if(img_id.height>=500) $(now_imgid).height(500);
	}
	$(".img_ul").children("li").eq(imgIndex).show().siblings("li").hide(); //大小确定后进行显示
}

function imgs_load(img_id){//小图片加载设置
	if(img_id.width >=$(".img_hd ul li").width()){img_id.width = 80};
	//if(img_id.height>=$(".img_hd ul li").height()) {img_id.height=$(".img_hd ul li").height();}
}

/**
 * 失效信息提示
 */
function FailureMsg(rtnData){
	if( Object.prototype.toString.call(rtnData).toLowerCase() == "[object xmldocument]")	return true;
	else return false;
}

 

/***说说查看图片遮罩隐藏****/
function ImgMaskHide(){
	 $('.playimgBox').remove();
	 $('#fatherMask').remove(); 
	 var fatherptlfbg =$(window.top.document.body).find("div#ptlfbg")
	 if(fatherptlfbg) $(fatherptlfbg).hide();
	 var playbgheises =$(window.top.frames.document.body).find("iframe").contents().find("div#fatherMask"); 
	 $(playbgheises).hide();	
	$(window.top.document.body).removeClass("html-body-overflow");
}
 

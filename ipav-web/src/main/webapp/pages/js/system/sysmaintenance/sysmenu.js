/*
 * 修改信息提示框高度
 */
$(window).resize(function() {	
	$(".simScrollContBox4").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox4").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox4").outerHeight()) / 2
    });	
	$(".simaltBoxt8").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBoxt8").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBoxt8").outerHeight()) / 2
    });
});
/*
 * 页面初始化
 */
$(function(){
	//初始化遮罩层高度
	$(".simScrollCont").css("height",document.documentElement.clientHeight);
	//触发窗口改变重置高度事件
	$(window).resize();
	//数据初始化
	fnc.init();
});
/*
 * 常量C
 */
var constant={
	PAGESIZE:10,
	DELETEMENU:2,
	ENABLEMENU:1,
	DISABLEMENU:0,
	ADDMENU:0,
	EDITMENU:1,
	ADDMENU_URL:"addMenu",
	GOTO_EDIT_MENU_URL:"gotoUpdateMenu",
	EDITMENU_URL:"editMenu",
	VALIDATAFILES:{"menuname":true,	"imgsrc":true,"pathstr":true}
};
/*
 *消息M
 */
var message={
	ADDMENU_MESSAGE:"新增应用菜单",
	EDITMENU_MESSAGE:"修改应用菜单",
	NULL_MESSAGE:"请选择一项操作对象!",
	NO_EDIT_MESSAGE:"停用的应用菜单不能进行修改操作!",
	EDIT_ONE_MESSAGE:"您每次只能对一项执行修改操作!",
	DEL_MESSAGE:"您确定执行删除操作吗?",
	ENABLE_MESSAGE:"您确定执行启用操作吗?",
	DISABLE_MESSAGE:"您确定执行禁用操作吗?",
	NO_ENABLE_MESSAGE:"您选择的对象已经处于启用状态!",
	NO_DISABLE_MESSAGE:"您选择的对象已经处于禁用状态!",
	ERROR_MESSAGE:"不好意思,操作失败!",
	VALIDATION_ERROR_MESSAGE:"带(*)为必填项,不可以为空!"
};
/*
 * 视图V
 */
var view={
	showPublicDiv:function(){
		$("#simScrollCont").show();
	},
	hidePublicDiv:function(){
		$("#simScrollCont").hide();
	},
	showAddOrEditDiv:function(title){
		view.showPublicDiv();
		$("#simaltBoxt8").show();
	},
	hideAddOrEditDiv:function(){		
		view.hidePublicDiv();
		$("#simaltBoxt8").hide();
	},
	showInfoDiv:function(infoHtml,action_type){
		view.showPublicDiv();
		$("#hidaction").val(action_type);
		$("#contentstr").html(infoHtml);
		$("#simScrollContBox1").show();
	},
	hideInfoDiv:function(){
		$("#hidaction").val("");
		$("#contentstr").html("");
		view.hidePublicDiv();
		$("#simScrollContBox1").hide();
	},
	test:function(){
		alert("welcome to your!!");
	}
};
/*
 * 函数F
 */
var fnc={
	init:function(){
		$("input[name='qysh']").bind("click",fnc.initialize);
		fnc.picUpload();
		fnc.initialize();
	},
	initialize:function(){
		$("#pageol").find("a.mpages").find("span").html("1");
		fnc.queryMenus();
	},
	queryMenus:function(){
		var startRow=parseInt($("#pageol").find("a.mpages").find("span").html());
		startRow=isNaN(startRow)?1:startRow;
		var data = {
				"levl":"0",
				"valflg" :$("input[name='qysh']:checked").val(),
				"beginRow":(startRow-1)*constant.PAGESIZE,
				"pageSize":constant.PAGESIZE
		};
		fnc.sendAjax("/queryMenus",data);
	},
	sendAjax:function(url,data){
		$.post(url, data, function(arr) {
			var count=0;
			if(isNotNull(arr)){
				count=arr.size;
				fnc.createMenuHtml(arr.list);
			}
			// 每次请求结束后将页面上的全选复原
			$("#firstChk").attr("checked", false);
			//初始化分页
			initPages($(".pageol").find("li").find("a.mpages").find("span").html(),count, constant.PAGESIZE, fnc.queryMenus);
		});
	},
	sendAjax2:function(url,data){
		$.post(url, data, function(arr) {
			if(isNotNull(arr)){
				if(arr.message=='success'){					
					fnc.init();
				}else if(arr.message=='error'){
					view.showInfoDiv(message.ERROR_MESSAGE);
				}
			}
		});
	},
	createMenuHtml:function(list){
		var menuHTML="";
		$.each(list,function(i,v){
			var index=i+1;
			menuHTML+="<tr><td><input type='checkbox' name='chk' value='"+index+"'>"+index+"<input type='hidden' id='menuid"+index+"' value='"+v.menuid+"'/></td>";
			menuHTML+="<td>"+v.menuname+"</td><td>"+v.pathstr+"</td>" ;
			menuHTML+="<td align='center'><div class='tbicon'><img id='imgsrc"+index+"' src='"+v.imgsrc+"'></div></td>";
			if(v.valflg==1){
				menuHTML+="<td>启用</td>";
			}else if(v.valflg==0){
				menuHTML+="<td>禁用</td>";
			}
			menuHTML+="</tr>";
		});	
		$("#menubody").html(menuHTML);
	},
	//全选与反选
	selectChk : function() {
		if ($("#firstChk").prop("checked")) {
			$("#menubody :checkbox").attr("checked", true);
		} else {
			$("#menubody :checkbox").attr("checked", false);
		}
	},
	//获取所有选中的checkbox值集合
	checkboxList : function() {
		var chkvalue = new Array();
		$("input[name='chk']:checked").each(function() {
			chkvalue.push($(this).val());
		});
		return chkvalue;
	},
	//图片上传预览
	picUpload:function(){
		$("#imgUpload").uploadPreview({
			Img: "imgPr",
			Width : 200,
			Height : 120,
			ImgType : ["gif", "jpeg", "jpg", "bmp", "png"]
		});
	},	
	//显示图片真实名称
	showFileName:function(){
		$("#MenuFrom").find("input[name='imgsrc']").val($("#imgUpload")[0].files[0].name);
	},
	clearData:function(){
		var menuFrom=$("#MenuFrom");
		menuFrom.attr("action","");
		menuFrom.find("#title_span").html("");
		menuFrom.find("#hidmenuid").val("");
		menuFrom.find("input[name='menuname']").val("");
		menuFrom.find("input[name='imgsrc']").val("");
		menuFrom.find("#imgPr").removeAttr("src");
		menuFrom.find("input[name='pathstr']").val("");
		$("#hidlevl").val("");
		$("#hidvalflg").val("");
		$("#hidparentid").val("");
		$("#hidrootid").val("");
		$("#hidoldPath").val("");
		//清空所有错误信息
		$("#MenuFrom").find(".errormsg2").html("");
//		var file = $("#imgUpload")   
//		file.after(file.clone().val(""));     
//		file.remove();
	},
	checkFiles:function(){
		var rsflg=true;
		//清空所有错误信息
		$("#MenuFrom").find(".errormsg2").html("");
		var fields=constant.VALIDATAFILES;
		//获取被校验字段的id
		for(var name in fields){
			var name_field=$("#MenuFrom").find("input[name='"+name+"']");
			if(isNotNull($(name_field).val())){
				fields[name]=true;
			}else{
				$(name_field).parent().find("p[class='errormsg2']").html(message.VALIDATION_ERROR_MESSAGE);
				fields[name]=false;
			}
		}
		//检验
		for(var k in fields){
			if(fields[k]==false){
				rsflg=false;
				break;
			}
		}
		return rsflg;
	}
};
/*
 * 控制C
 */
var control={
	actionDiv:function(action_type){
		var infoHtml="";
		var chkleng = $("input[name='chk']:checked").length;
		if(chkleng==0){
			infoHtml=message.NULL_MESSAGE;
			action_type="";
		}else{
			var status=$("input[name='qysh']:checked").val();
			switch (action_type) {
				case constant.DELETEMENU:
					infoHtml=message.DEL_MESSAGE;
					break;
				case constant.ENABLEMENU:
					if(status!=''&&status==1){
						infoHtml=message.NO_ENABLE_MESSAGE;
						action_type="";
					}else{
						infoHtml=message.ENABLE_MESSAGE;
					}					
					break;
				case constant.DISABLEMENU:
					if(status!=''&&status==0){
						infoHtml=message.NO_DISABLE_MESSAGE;
						action_type="";
					}else{	
						infoHtml=message.DISABLE_MESSAGE;
					}
					break;
			}
		}
		view.showInfoDiv(infoHtml,action_type);
	},
	closeDiv:function(tt){
		if(tt==0){
			view.hideInfoDiv();
		}else{
			var hid_action=$("#hidaction").val();
			view.hideInfoDiv();
			if(isNotNull(hid_action)){
				var data=new Object();
				var idlist = new Array();
				$.each(fnc.checkboxList(),function(i,v){
					idlist.push($("#menuid"+v).val());
				});
				data.actiontype=hid_action;
				data.ids=idlist;
				fnc.sendAjax2("/updateMenuState", data);
			}
		}
	},
	openAddOrEdit:function(action_type){
		fnc.clearData();
		var titleHtml="";
		var action_url="";
		var flg=-1;
		if(action_type==constant.ADDMENU){
			titleHtml=message.ADDMENU_MESSAGE;
			action_url=constant.ADDMENU_URL;
			flg=0;
		}else if(action_type==constant.EDITMENU){
			var chkleng = $("input[name='chk']:checked").length;
			if(chkleng==1){
				var status=$("input[name='qysh']:checked").val();
				if(status==0&&status!=''){
					view.showInfoDiv(message.NO_EDIT_MESSAGE);
				}else{
					titleHtml=message.EDITMENU_MESSAGE;
					action_url=constant.EDITMENU_URL;
					flg=1;
				}
			}else{
				var infoHtml="";
				if(chkleng==0){
					infoHtml=message.NULL_MESSAGE;
				}else if(chkleng>1){
					infoHtml=message.EDIT_ONE_MESSAGE;
				}
				view.showInfoDiv(infoHtml);
			}
		}
		if(flg>-1){
			$("#MenuFrom").attr("action",action_url);
			$("#title_span").html(titleHtml);
			if(flg==0){
				$("#hidlevl").val("0");
				$("#hidvalflg").val("1");
				$("#hidparentid").val("0");
				view.showAddOrEditDiv();
			}else if(flg==1){
				var index=$("input[name='chk']:checked").val();
				$.post(constant.GOTO_EDIT_MENU_URL,{menuid:$("#menuid"+index).val()},function(data){
					if(isNotNull(data)){
						var m=data.menu;
						var showPicPath=m.imgsrc;
						if(isNotNull(m.imgsrc))
							showPicPath=showPicPath.substring(showPicPath.lastIndexOf("/")+1,showPicPath.length);
						var menuFrom=$("#MenuFrom");
						menuFrom.find("#hidmenuid").val(m.menuid);
						menuFrom.find("input[name='menuname']").val(m.menuname);
						menuFrom.find("input[name='imgsrc']").val(showPicPath);
						menuFrom.find("#imgPr").attr("src",m.imgsrc);
						menuFrom.find("input[name='pathstr']").val(m.pathstr);
						$("#hidlevl").val(m.levl);
						$("#hidvalflg").val(m.valflg);
						$("#hidparentid").val(m.parentid);
						$("#hidrootid").val(m.rootid);
						$("#hidoldPath").val(showPicPath);
					}
					view.showAddOrEditDiv();
				});
			}
		}
	},
	closeAddOrEdit:function(tt){
		if(tt==1&&fnc.checkFiles()){
			$("#MenuFrom").submit();			
		}else if(tt==0){
			view.hideAddOrEditDiv();
		}
		
	}
};

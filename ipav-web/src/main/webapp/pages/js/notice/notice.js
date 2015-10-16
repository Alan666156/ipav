/*
 * 公告对象
 */
var notice = {
	appAllOrgInfo:null,
	topDiv:null,
	default_commcnt:5,//默认评论数
	default_pagesize:10,//默认分页数
	clearPageParm:function(pageid){
		//重置当前页为第一页
		$("#"+pageid).find("#pageol").find("a.mpages").find("span").html("1");
		//清空文本框用户输入的数据
		$("#"+pageid).find("input[class='gopage']").val("");
	},
	initPageParm:function(pageid,pagesize){
		var startRow=parseInt($("#"+pageid).find("#pageol").find("a.mpages").find("span").html());
		startRow=isNaN(startRow)?1:startRow;
		var page={
			"beginRow":(startRow-1)*pagesize,
			"pageSize":pagesize
		}
		return page;
	},
	Initialize:function(){
		notice.clearPageParm("pageInfo_0");
		notice.fnc.initAllNotice();
	},
	//还原被转义的HTML字符
	escape2Html:function(str){
		var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
		return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
	},
	confirmationView:function(i){
		var model=$("#actionmodel").val();
		if(isNotNull(model)){
			var info="";
			if(model=="addNotice"){
				info=notice.msg.ExitNewInfo;
			}else if(model=="updateNotice"){
				info=notice.msg.ExitEditInfo;
			}
			$("#hid_action_type").val(i);
			notice.view.showExitDiv(info);
		}else{
			notice.showView(i);		
		}
		
	},
	showView:function(i){
		$("#AddOrEditNotice").hide();
		if(i==0){
			notice.Initialize();
		}else if(i==1){
			noticetype.Initialize();
		}
		tab('notice',i,2);
	},
	setting:{
			//勾选框类型(checkbox 或 radio）
			check: {
				enable: true,
				chkStyle: "checkbox",
				radioType: "level"
			},
			//数据模板
		
			data: {
			/*
				key:{
					children: "nodes",
					name:"name",
					title: "fullname"
				}
			*/
				simpleData: {
					enable: true,
					idkey:"id",
					pIdKey:"Pid",
					nameKey:"name"
				}
			
			},
			view:{
				nameIsHTML: true
			}			
		}
};
/**
 * 公告常量数据
 *
 */
notice.constant={
	Zero:0,
	One:1,
	Hours:'H',
	Minute:'M',
	MaxHours:24,
	MaxMinute:60,
	DefaultNoticeNo:"",
	AddNoticeURL:"gotoAddNotice",
	EditNoticeURL:"gotoUpdateNotice",
	InitTypeSelect:"<option selected='selected'>请选择公告类型</option>",
	InitStateSelect:"<option selected='selected'>请选择公告状态</option>",
	QueryList:{"0":'我未发布的公告',"1":'我发布的公告',	"2":'我收到的公告',"":'全部公告'},
	Chkfields:{"gg_type":true,"gg_sended":true,"gg_issued":true,"gg_title":true,"gg_no":true,"gg_content":true},
	Filechks:{"filetypes":[".jpg",".png",".rar",".txt",".zip",".doc",".ppt",".xls",".pdf",".docx",".xlsx"],
			  "filemaxsize":1024*1024*20}//单位bytes,大小20M
};
/**
 * 公告数据信息层
 */
notice.msg={
	AddNoticeTitle:"新建公告",
	EditNoticeTitle:"修改公告",
	ErrorInfo:"带红色星号(*)为必填项,必填项不可以为空!",
	StartTimeNull:"请选择开始时间!",
	EndTimeNull:"请选择结束时间!",
	TimeError:"开始时间不能大于结束时间!",
	IssuedTitle:"选择签发人",
	DepTitle:"选择发文部门",
	ExitNewInfo:"您确定退出新建公告吗?",
	ExitEditInfo:"您确定退出修改公告吗?",
	ReadesNull:"不好意思,暂时还未有用户浏览!",
	DelNotice:"您确定执行删除操作吗?",
	ReleaseNotice:"您确定执行发布操作吗?",
	FileNotExists:"您选择的文件不存在!",
	FileSizeNull:"您选择的文件为空文件!",
	FileOutSize:"您选择的文件大小超过上限值(20M)",
	FileTypeError:"目前仅支持如下类型文件[.jpg,.png,.rar,.txt,.zip,.doc,.ppt,.xls,.pdf,.docx,.xlsx]"
};
/*
 * 公告视图层
 */
notice.view = {
	show_pub:function(){		
		$("#_ptlfbg",window.parent.document).show();
		notice.topDiv.show();
	},
	hide_pub:function(){
		$("#_ptlfbg",window.parent.document).hide();
		notice.topDiv.hide();
	},
	//取消新建或者修改公告
	cancel:function(){
		$("#notice_title_0").click();
	},	
	//显示上传文件信息界面
	showfileinfo:function(){
		var rsint=checkFile($("#file_id")[0].files[0],notice.constant.Filechks);
		var infohtml='';
		if(rsint==0){
			infohtml=notice.msg.FileNotExists;
		}else if(rsint==1){
			infohtml=notice.msg.FileTypeError;
		}else if(rsint==2){
			infohtml=notice.msg.FileSizeNull;
		}else if(rsint==3){
			infohtml=notice.msg.FileOutSize;
		}
		if(isNotNull(infohtml)){
			notice.view.showggDiv(infohtml);
		}else{
			var fileInput=$("#file_id")[0];
			if(isNotNull(fileInput)){
				var byteSize  = fileInput.files[0].size;
				var fileSize=Math.ceil(byteSize / 1024);
				var fileinfo=fileInput.files[0].name+'('+fileSize+'KB)';
				$("#fileinfo").html(fileinfo);
			}
			$("#filediv").show();
		}
	},
	//清除上传文件信息
	clearfileinfo:function(){
		$("#hidIsUpdateFile").val("1");
		if(isNotNull($("#hidfilename").val()))  $("#hidfilename").val("");
		$("#fileinfo").html("");
		$("#filediv").hide();
		//重写file上传
		$("#file_id").remove();
		$("#loadfilediv").append('<input title="仅支持上传单个附件(附件大小不能超过20M)" type="file" name="filedata" id="file_id" class="filebut" onchange="notice.view.showfileinfo()">');
		
	},
	//显示添加接收人界面
	showSendsDiv:function(){
		notice.view.show_pub();
		$("#simalpha1").show();
		$("#simaltBox1").show();
	},
	//隐藏添加接收人界面
	hideSendsDiv:function(){
		//清空组织机构树的数据
		$("#treeDemo1").html("");
		//清空已选人员的显示内容
		$("#userinfos").html("");
		notice.view.hide_pub();
		$("#simalpha1").hide();
		$("#simaltBox1").hide();
	},
	//显示预览界面
	showPreview:function(){
		$("#yltt").html($("#gg_title").val());
		$("#ylbh").html($("#gg_no").val());
		$("#ylissued").html($("#gg_issued").val());
		$("#ylinfo").html(notice.escape2Html(KindEditor.instances[notice.constant.Zero].html()));
		$("#ylinfo").css("display",'block');
		notice.view.show_pub();
		$("#simScrollCont15").show();
		$("#simScrollContBox15").show();	
	},
	//隐藏预览界面
	hidePreview:function(){
		notice.view.hide_pub();
		$("#simScrollContBox15").hide();
		$("#simScrollCont15").hide();
	},
	// 隐藏全部公告与公告类型设置模块
	showAddOrEditDiv : function() {
		$(".addgg").addClass("addggcls");
		$(".addgg span").addClass("lfad2")
		$(".ptleftmu li a").removeClass("current");
		$("#notice_main_0").hide();
		$("#notice_main_1").hide();
		$("#AddOrEditNotice").show();
	},
	//开启或关闭选项定时发布
	dsTime:function(){
		 if($("#dstime1").prop("checked"))
		 {
			 //填充默认日期
			 var d=new Date();
			 var defaultData=d.getFullYear()+"年"+(notice.fnc.pad((d.getMonth()+1+""), 2))+"月"+d.getDate()+"日";
			 $("#seldate").val(defaultData);
			 //填充默认小时和分钟
			 var hoursHtml='';
			 var hoursArr=notice.fnc.createTimeStr(notice.constant.Hours);
			 $(hoursArr).each(function(i,v){
				 hoursHtml+='<option>'+v+'</option>';
			 });
			 $("#hours").html(hoursHtml);
			 var minuteHtml='';
			 var minuteArr=notice.fnc.createTimeStr('M');
			 $(minuteArr).each(function(i,v){
				 minuteHtml+='<option>'+v+'</option>';
			 });
			 $("#minute").html(minuteHtml);
			 $("#dsTimeBox1").css('display','block');	
		 }else
		 {
			 $("#seldate").val("");
			 $("#hours").val("");
			 $("#minute").val("");
			 $("#dsTimeBox1").css('display','none');	
		 }
	},
	//显示退出新建确认提示信息
	showExitDiv:function(info){
		$("#contenthtml").html(info);		
		notice.view.show_pub();
		$("#simScrollCont11").show();
		$("#simScrollContBox11").show();
	},
	//隐藏退出新建确认提示信息
	hideExitDiv:function(flg){
		$("#_ptlfbg",window.parent.document).hide();
		notice.view.hide_pub();
		$("#simScrollCont11").hide();
		$("#simScrollContBox11").hide();
		if(flg==1){
			$("#actionmodel").val("");
			notice.showView($("#hid_action_type").val());
		}
	},
	//展开公告详情
	showggxqinfo:function (tt,nid){
		var gg_istrue=$("#gg_istrue"+nid).val();
		if(isNotNull(gg_istrue)){
			if(gg_istrue==1){
				$.post("modifyReadeState",{noticeid:nid},function(data){
					if(isNotNull(data)){
//						console.log(data);
						if(data.msg=="success"){
							$(".reade_span"+nid).html(data.readecnt);
							$("#gg_title"+tt).removeAttr("style");
						}
					}
				});
			}
		}		
		$("#viewmodel"+nid).val(1);
		$("#gginfo"+tt).hide();
		$("#ggxqinfo"+tt).show();
	},
	//收起公告详情
	hidggxqinfo:function(tt,nid){
		$("#viewmodel"+nid).val(0);
		$("#gginfo"+tt).show();
		$("#ggxqinfo"+tt).hide();
	},
	//显示公告操作提示信息
	showggaction:function(tt,ggid){
		$("#actiontt").val(tt);
		$("#noticeid").val(ggid);
		var info;
		if(tt==0){//待发布公告进行发布操作
			info=notice.msg.ReleaseNotice;
		}else if(tt==1){//进行删除公告操作
			info=notice.msg.DelNotice;
		}
		notice.view.showggDiv(info);
	},
	//显示公告提示信息
	showggDiv:function(info){
		$("#contentstr").html(info);
		notice.view.show_pub();
		$("#simScrollCont13").show();
		$("#simScrollContBox13").show();
	},
	//隐藏公告提示信息
	hidggDiv:function(){
		$("#actiontt").val("");
		$("#noticeid").val("");
		$("#contentstr").html("");
		notice.view.hide_pub();
		$("#simScrollContBox13").hide();
		$("#simScrollCont13").hide();
	},
	//展开公告已赞人数
	showZans:function(nid){
		if(isNotNull(nid)){
			var out_div=$(nid);
			$(".praiseBox").hide();
			out_div.show();
			out_div.hover(function(){out_div.show();},function(){out_div.hide();});
		}
	},
	//展开公告已读人数
	showReades:function(){		
		notice.view.show_pub();
		$("#simScrollCont16").show();
		$("#simScrollContBox16").show();
	},
	//隐藏公告已读人数
	hideReades:function(){
		notice.view.hide_pub();
		$("#simScrollContBox16").hide();
		$("#simScrollCont16").hide();
	}	
};
/*
 * 公告服务层
 */
notice.fnc = {
	//新建或修改公告
	gotoNewOrEditNotice:function(nid){
		var url=notice.constant.AddNoticeURL;
		var data=null;
		var toptitlestr=notice.msg.AddNoticeTitle;
		//清空页面上所有信息
		this.clearNotice();
		//修改操作
		if(isNotNull(nid)){
			toptitlestr=notice.msg.EditNoticeTitle;
			url=notice.constant.EditNoticeURL;
			data={
				"noticeid":nid	
			};
		}else{//新建操作
			//提前初始化默认公告类型
			this.initTypelist("queryTypes",'gg_type');
		}
		//标题信息
		$("#toptitle").html(toptitlestr);
		$.post(url,data,function(arr) {
			if(arr){
				$("#actionmodel").val(arr.actionmodel);
				if(url==notice.constant.AddNoticeURL){
					notice.fnc.initNewNotice(arr);
				}else if(url==notice.constant.EditNoticeURL){
					notice.fnc.initEditNotice(arr);
				}
			}
			notice.view.showAddOrEditDiv();
		});
	},
	//清除新建或修改公告页面内容
	clearNotice:function(){
		$("#editgglxid").val("");
		$("#editggindex").val("");
		$("#gg_type").html("");
		$("#gg_sended").attr("title","");
		$("#gg_sended").val("");
		$("#hidsendsids").html("");
		$("#hidsendsnames").html("");
		$("#gg_issued").val("");
		$("#gg_department").val("");
		$("#gg_title").val("");
		$("#gg_no").val("");
		$("#gg_content").val("");
		//清空富文本编辑器对象
		if(KindEditor.instances.length!=0)
			KindEditor.instances[notice.constant.Zero].html("");
		$("#fileinfo").html("");
		$("#filediv").hide();
		$("#dstime3").attr("checked",false);
		$("#dstime2").attr("checked",false);
//		$("#dstime1").attr("checked",false);
		notice.view.dsTime();
//		$("input[class='remindtype']:checked").each(function(){
//			$(this).attr("checked",false);
//		});
		//恢复保存按钮
		$(":input[class='pttcBut85 ml5']").show(); 
		//清空隐藏域信息
		$("#hidgguserid").val("");
		$("#hidggissuedid").val();
		$("#hidggorgid").val();
		$("#hidggid").val("");
		$("#hidIstime").val("");
		$("#hidIstrue").val("");
		$("#hidremindtype").val("");
		$("#hidnostr").val("");
		$("#hidnoindex").val("");
		$("#hidIsUpdateFile").val("");
		//清空错误信息
		$("#AddOrEditNotice .errortab").html("");
	},
	//初始化新建和修改公告的公共信息
	initAddOrEdit:function(parm){
//		console.log(parm);
		//记录操作类型
		$("#actionmodel").val(parm.actionmodel);		
		//初始化默认接收人
		var sendsvalue ='';
		$(parm.sendeds).each(function(){
			sendsvalue+=this.username+';';
			notice.fnc.addSendsHidden(this.userid,this.username);
		});
		$("#gg_sended").attr("title",sendsvalue);
		$("#gg_sended").val(sendsvalue);
		//初始化默认签发人
		$("#hidggissuedid").val(parm.issuedid);
		$("#gg_issued").val(parm.issuedname);
		//初始化默认发布部门
		$("#hidggorgid").val(parm.orgid);
		$("#gg_department").val(parm.orgname);
		//初始化默认编号
		$("#companyname").val(parm.companyname);
//		$("#typename").val(parm.typename);
		$("#hidnostr").val(parm.nostr);
		$("#hidnoindex").val(parm.noindex);
//		var noticeno=parm.companyname+"{0}"+parm.nostr.replace("{0}",parm.noindex);
		var noticeno=parm.companyname+" {1}"+parm.nostr;
		notice.constant.DefaultNoticeNo=noticeno;
		$("#gg_no").val(noticeno.replace("{0}",parm.noindex).replace('{1}',isNotNull(parm.typename)?parm.typename:$("#gg_type").find("option:selected").text()));	
	},
	//添加公告默认信息初始化
	initNewNotice:function(data){
//		console.log(data);
		//初始化发表公告用户信息
		$("#hidgguserid").val($("#hiduserid").val());
//		$("#hidggusername").val($("#hidusername").val());
		this.initAddOrEdit(data);
	},
	//修改公告默认信息初始化
	initEditNotice:function(data){
//		console.log(data);
		var gg=data.notice;
		if(isNotNull(gg)){			
			var parm={
				"actionmodel":data.actionmodel,
				"sendeds":gg.sendeds,
				"issuedid":gg.issuedid,
				"issuedname":gg.issuedname,
				"orgid":gg.orgid,
				"orgname":gg.orgname,
				"companyname":data.companyname,	
				"typename":data.typename,	
				"nostr":data.nostr,
				"noindex":data.noindex		
			};
			this.initAddOrEdit(parm);
			//初始化公告id
			$("#hidggid").val(gg.id);
			//初始化公告用户信息
			$("#hidgguserid").val(gg.userid);
//			$("#hidggusername").val(gg.username);
			//初始化公告标题
			$("#gg_title").val(gg.title);
			//初始化公告内容
			KindEditor.instances[notice.constant.Zero].html(gg.content);
			$("#gg_content").val(gg.content);
//			//隐藏上传字段
//			$("#loadfilediv").hide();
			//显示公告附件文件信息
			if(isNotNull(gg.oldfilename)){
				$("#fileinfo").html("<a href='/ipav/notice/getFile?path="+gg.newfilename+"'>"+gg.oldfilename+"</a>");
				$("#filediv").show();
				$("#hidIsUpdateFile").val("0");
			}
//			$("#editfilediv").show();
			//初始化其他设置选项
			if(isNotNull(gg.iscomment)) $("#dstime3").attr("checked",true);
			if(isNotNull(gg.istop)) $("#dstime2").attr("checked",true);
			var istime=gg.istime;
			//初始化定时发布
			if(isNotNull(istime)){
				this.initDstimeHtml(istime);
			}
			//初始化提醒方式
			var remindtype = gg.remindtype;
			if(isNotNull(remindtype)){
				var rtarr=remindtype.split(";");
				$("input[class='remindtype']").each(function(i,v){
					if(rtarr[i]==$(v).val()){
						$(v).attr("checked",true);
					}
				});
			}
			//初始化保存按钮
//			if(gg.istrue==1){
//				$(":input[class='pttcBut85 ml5']").hide(); 
//			}
			//初始化修改公告中的默认公告类型
			$("#editgglxid").val(gg.typeid);
			$("#editggindex").val(data.noindex);
			this.initTypelist("queryTypes",'gg_type');
		}
		
	},
	//初始化修改公告定时发布信息
	initDstimeHtml:function(istime){
		 istime=istime.replace("-","年").replace("-","月").replace(" ","日");
//		 console.log(istime);
//		 $("#dstime1").attr("checked",true);
		 notice.view.dsTime();
		 var datahtml=istime.substr(0,11);
		 $("#seldate").val(istime.substr(0,11));
		 var m1=parseInt(istime.indexOf(":"));
		 var hourshtml=istime.substring(11,m1);
		 $("#hours").find("option").each(function(i,v){
			if($(v).text()==hourshtml){
				$(v).attr("selected",true);
			}
		 });
		 var m2=parseInt(istime.lastIndexOf(":"));
		 var minutehtml=istime.substring(m1+1,m2);
//		 console.log(minutehtml);
		 $("#minute").find("option").each(function(i,v){
			if($(v).text()==minutehtml){
				$(v).attr("selected",true);
			}
		 });
		 $("#dsTimeBox1").css('display','block');
	},
	//初始化公告类型下拉列表	
	initTypelist:function(url,objdivid){
		var ntypelist;
		if(objdivid!='gg_type'){
			ntypelist+=notice.constant.InitTypeSelect;
		}
		var editgglxid=$("#editgglxid").val();		
		$.post(url,{istrue:notice.constant.Zero},function(arr){
			if(isNotNull(arr)){
				$(arr.typelist).each(function(i,v){
						if(objdivid=='gg_type'){
							if(isNotNull(editgglxid)){
								if(v.id==editgglxid){
									ntypelist+="<option selected='selected' value='"+v.id+"'>"+v.typename+"</option>";
								}else{
									ntypelist+="<option value='"+v.id+"'>"+v.typename+"</option>";
								}
							}else{
								if(v.isdefault==1){
									ntypelist+="<option selected='selected' value='"+v.id+"'>"+v.typename+"</option>";
								}else{
									ntypelist+="<option value='"+v.id+"'>"+v.typename+"</option>";
								}
							}
					    }else{
					    	ntypelist+="<option value='"+v.id+"'>"+v.typename+"</option>";
					    }
				});	
				$("#"+objdivid).html(ntypelist);
			}
		});
	},
	//查询并显示选择接受人界面
	showSends:function(){
		notice.view.show_pub();
		new openCommonView({
			title:'公告接收人员选择',
			setting:{
				check: {
					enable: true,
					chkStyle: "checkbox"
				}		
			},
			show_user:true,
			isMultiple:true,
			isAllChk:true,
			userids:'hidsendsids',
			usernames:'hidsendsnames',
			closeEvent:notice.fnc.getSendUsers
		});	
		//初始化已选人员信息		
//		var infohtml="";
//		$.each($("#hidsendsnames input"),function(){
//			var userid=$("#hidsendsids").find("input#sendsid"+$(this).attr('id')).val();
//			infohtml+="<li><div title='"+$(this).val()+"'>"+$(this).val()+"<input type='hidden' class='hiduserids' value='"+userid+"'/></div><a href='javascript:;' title='删除' class='removeUser'></a></li>";
//		});
//		$("#muserinfo").html(infohtml);
//		//绑定删除选中人员div事件
//		$(".removeUser").bind("click",function(){
//			  $(this).parent().remove();
//		});
		//显示选择接收人树形结构配置
//		$.post("showOrgTree",function(data) {
//			var setting1=notice.setting;
//			setting1.check.enable=true;
//			setting1["callback"]={onCheck :function(event, treeId, treeNode){notice.fnc.selectUsers(event, treeId, treeNode)}};
//			$.fn.zTree.init($("#treeDemo1"),setting1,data); 
//			var treeObj = $.fn.zTree.getZTreeObj("treeDemo1");
//			treeObj.checkAllNodes(true);
			//初始化已选人员信息		
//			var infohtml="";
//			$.each($("#hidsendsnames input"),function(){
//				var userid=$("#hidsendsids").find("input#sendsid"+$(this).attr('id')).val();
//				infohtml+="<li><div title='"+$(this).val()+"'>"+$(this).val()+"<input type='hidden' class='hiduserids' value='"+userid+"'/></div><a href='javascript:;' title='删除' class='removeUser'></a></li>";
//			});
//			$("#userinfos").html(infohtml);
//			//绑定删除选中人员div事件
//			$(".removeUser").bind("click",function(){
//				  $(this).parent().remove();
//			});
//			notice.view.showSendsDiv();
//		});
	},
	//用户选择的接收人后回调函数
	getSendUsers:function(){		
		var sendsvalues="";
		$.each($("#hidsendsids input"),function(i,v){
			$(v).attr("name","sendeds["+i+"].userid");
			sendsvalues+=$("#_username"+i).val()+";";
		});
		$("#gg_sended").attr("title",sendsvalues);
		$("#gg_sended").val(sendsvalues);
		notice.view.hide_pub();
	},
	//获取用户选择的接受人员
//	selectUsers:function(event, treeId, treeNode){
//		var treeObj = $.fn.zTree.getZTreeObj(treeId);
//		var nodes =  treeObj.getCheckedNodes(true);
//		$("#userinfos").html("");
//		$(nodes).each(function(i,v){
//			if(v.isParent==false)
//				notice.fnc.checkSendsUser(v);
//		});
//	},
//	checkSendsUser:function(chkuser){
//		if(isNotNull(chkuser)){
//			var curuser = notice.fnc.searchUser(chkuser.id);
//			//选中状态
//			if(chkuser.checked){
//				//该用户不存在添加
//				if(!isNotNull(curuser)){
//					$("#userinfos").append("<li><div title='"+chkuser.name+"'>"+chkuser.name+"<input class='hiduserids' value='"+chkuser.id+"' type='hidden'></div><a href='javascript:;' title='删除' class='removeUser'></a></li>");
//					//绑定删除选中人员div事件
//					$(".removeUser").bind("click",function(){
//						  $(this).parent().remove();
//					});
//					
//				}
//			}
//			//未选中状态
//			if(!chkuser.checked){
//				//该用户存在删除
//				if(isNotNull(curuser)){
//					$(curuser).parent().parent().remove();
//				}
//			}
//		}
//	},
//	//在已经存在的接收人员中查找人员
//	searchUser:function(uid){
//		var rs=null;
//		if(isNotNull(uid)){
//			$.each($(".hiduserids"),function(i,v){
//				if(isNotNull(v.value)&&uid==v.value){
//					rs=v;
//				}
//			});
//		}
//		return rs;
//	},
//	//回填用户选择的接收人
//	createSends:function(){
//		//清空原来的数据
//		$("#gg_sended").attr("title","");
//		$("#gg_sended").val("");
//		$("#hidsendsids").html("");
//		$("#hidsendsnames").html("");
//		var sendsvalues="";
//		$.each($("#userinfos li"),function(){
//			var hidid=$(this).find("input").val();
//			var hidname=$(this).find("div").text();
//			sendsvalues+=hidname+";";
//			notice.fnc.addSendsHidden(hidid, hidname);
//		});
//		$("#gg_sended").attr("title",sendsvalues);
//		$("#gg_sended").val(sendsvalues);
//		notice.view.hideSendsDiv();
//	},
//	//添加接收人隐藏域
	addSendsHidden:function(hidid,hidname){
		var idinput=$("#hidsendsids").find("input[value="+hidid+"]");
		if($(idinput).length>0){
			return;
		}else{
			var maxIndex=$("#hidsendsids input").length;
			$("#hidsendsids").append("<input id='sendsid"+maxIndex+"' type='hidden' name='sendeds["+maxIndex+"].userid' value='"+hidid+"'/>");
			$("#hidsendsnames").append("<input id='_username"+maxIndex+"' type='hidden'  value='"+hidname+"'/>");
		}
	},
	//显示组织机构,同时选择签发人或者发文部门
	showOrglist:function(tt){
		var titleinfo=notice.msg.IssuedTitle;
		var _showUser=true;
		$("#hid_action").val(tt);
		if(tt==1){
			//选择发文部门,不用显示用户信息
			_showUser=false;
			titleinfo=notice.msg.DepTitle;
		}
		notice.view.show_pub();
		new openCommonView({
			title:titleinfo,
			setting:{callback: {onClick:notice.fnc.getSelect}},
			show_user:_showUser,
			closeEvent:notice.view.hide_pub
		});		
	},
	//获取用户选择的签发人或者发文部门onClick
	getSelect:function(event, treeId, treeNode){
		//0:签发人或者;1:发文部门
		if(isNotNull(treeNode)){
			var selectID="";
			var selectName="";
			var action_id="hidggissuedid";
			var action_name="gg_issued";
			if($("#hid_action").val()==0){
				if(treeNode.isParent==false){
					selectID=treeNode.id;
					selectName=treeNode.name;
				}
			}else if($("#hid_action").val()==1){
				if(treeNode.level>0){
					action_id="hidggorgid";
					action_name="gg_department";
					selectID=treeNode.tid;
					selectName=treeNode.name;
				}
			}
			if(isNotNull(selectID)) $("#"+action_id).val(selectID);	
			if(isNotNull(selectName)) $("#"+action_name).val(selectName);		
		}
		$("#close_img").click();
	},
	//根据公告类型自动修改编号
	autoModifyNo:function(){
		var _notice_id=$("#gg_type").find("option:selected").val();
		var edit_gglxid=$("#editgglxid").val();
		var noticeno=notice.constant.DefaultNoticeNo;
		var typename=$("#gg_type").find("option:selected").text();
		if(!isNotNull(edit_gglxid)||edit_gglxid!=_notice_id){
			$.post("getMaxNoticeIndex",{type_id:_notice_id},function(data){
				if(isNotNull(data)){
					var noticeno=notice.constant.DefaultNoticeNo;
					if(isNotNull(noticeno)){
						$("#hidnoindex").val(data.maxIndex);
						$("#gg_no").val(noticeno.replace("{0}",data.maxIndex).replace('{1}',typename));
					}
				}
			});
		}else{
			$("#hidnoindex").val($("#editggindex").val());
			$("#gg_no").val(noticeno.replace("{0}",$("#editggindex").val()).replace('{1}',typename));
		}
	},
	//新建和修改公告字段校验
	checkNotice:function(){
		var rsflg=true;
		//清空所有错误信息
		$("#AddOrEditNotice .errortab").html("");
		var chks=notice.constant.Chkfields;
		//获取被校验字段的id
		for(var id in chks){
			if(!isNotNull($("#"+id).val())){
				$("#"+id).parent().find("p[class='errortab']").html(notice.msg.ErrorInfo);
				chks[id]=false;
			}else{
				chks[id]=true;
			}
		}
		//检验
		for(var k in chks){
			if(chks[k]==false){
				rsflg=false;
				break;
			}
		}
		return rsflg;
	},
	//保存或者发布公告
	saveOrRelease:function(ttpye){
		if(notice.fnc.checkNotice()){
			//公告类容
			$("#gg_content").val(notice.escape2Html(KindEditor.instances[notice.constant.Zero].html()));
			//获取定时发布时间
			if($("#dstime1").attr("checked")){	
				//选择的日期
				var seldate=$("#seldate").val();
				seldate=this.formatdateStr(seldate);
				var istime=seldate+$("#hours").val()+":"+$("#minute").val()+":00";
				$("#hidIstime").val(istime);
			}
			var temp="";//提醒方式字符串
			$("input[class='remindtype']:checked").each(function(i,v){
				temp=temp+$(v).val()+";";
			});
			$("#hidremindtype").val(temp);
			$("#hidIstrue").val(ttpye);
			$("#noticeFrom").attr("action",$("#actionmodel").val());
			$("#noticeFrom").submit();
		}
	},
	//初始化全部公告
	initAllNotice:function(_type){
		notice.fnc.clearAllNotice();
		//重新初始化查询条件中下拉列表的数据
		notice.fnc.initTypelist("queryTypes",'querygglx');
		var queryHtml=notice.constant.InitStateSelect;
		var querylist=notice.constant.QueryList;
		for(var i in querylist){
			queryHtml+="<option  value='"+i+"'>"+querylist[i]+"</option>";
		}
		$("#queryggflag").html(queryHtml);
		var data=new Object();
		var url="queryNotices";
		if(_type==1){//根据传递过来的id查询一条
			url="queryNoticeById";
			data.noticeid=$("#hidnotice_id").val();	
			$("#pageInfo_0").hide();
			notice.fnc.sendAjax1(url, data);
		}else{//查询全部
			$("#hidnotice_id").val("");
			data.beginRow=notice.initPageParm("pageInfo_0",notice.default_pagesize).beginRow;
			data.pageSize=notice.initPageParm("pageInfo_0",notice.default_pagesize).pageSize;
			notice.fnc.sendAjax1(url, data,notice.fnc.initAllNotice);
		}
	},
	//清空全部公告数据
	clearAllNotice:function(){		
//		$("#gg_istrue").val("");		
		$("#startTime").val("");
		$("#endTime").val("");	
		$("#queryinfo").val("");
		$("#time_error").html("");
		$("#querygglx").html("");
		$("#queryggflag").html("");
	},
	//根据条件查询公告
	initQueryNotice:function(){
		notice.clearPageParm("pageInfo_0");
		notice.fnc.queryNotice();
	},
	queryNotice:function(){
		var url="searchNotices";
		var dateBegin=$("#startTime").val();
		var dateEnd=$("#endTime").val();
		if(notice.fnc.chkDate(dateBegin,dateEnd)){
			var data=new Object();
			data.dateBegin=notice.fnc.formatdateStr(dateBegin);
			data.dateEnd=notice.fnc.formatdateStr(dateEnd);
			var gglxint=parseInt($("#querygglx").val());
			if(!isNaN(gglxint)){			
				data.noticetype=gglxint;
			}
			var flgint=parseInt($("#queryggflag").val());
			if(!isNaN(flgint)){
				data.flag=flgint;
			}
			data.contentstr=$("#queryinfo").val();
			data.beginRow=notice.initPageParm("pageInfo_0",notice.default_pagesize).beginRow;
			data.pageSize=notice.initPageParm("pageInfo_0",notice.default_pagesize).pageSize;
			notice.fnc.sendAjax1(url, data,notice.fnc.queryNotice);
		}
	},	
	//校验公告查询日期
	chkDate:function(date_str1,date_str2){
		var err_info="";
		$("#time_error").html("");
		var flg1=isNotNull(date_str1);
		var flg2=isNotNull(date_str2);
		var flg3=false;
		var rsflg=true;
		if(flg1){
			if(flg2){
				flg3=true;
			}else{
				rsflg=false;
				err_info=notice.msg.EndTimeNull;
			}
		}
		if(flg2){
			if(flg1){
				flg3=true;
			}else{
				rsflg=false;
				err_info=notice.msg.StartTimeNull;
			}
		}
		//开始比较时间大小
		if(flg3){
			var date1=new Date(notice.fnc.formatdateStr(date_str1));
			var date2=new Date(notice.fnc.formatdateStr(date_str2));
//			console.log(date1>date2);
			if(date1>date2){
				err_info=notice.msg.TimeError;
				rsflg=false;
			}
		}
		$("#time_error").html(err_info);
		return rsflg;
	},
	//待发布公告进行发布或删除公告操作
	relOrdelNotice:function(){
		var nid=$("#noticeid").val();
		if(isNotNull(nid)){
			var url="updateNoticeState";
			var data=new Object();
			data.noticeid=nid;
			var aciontt=$("#actiontt").val();
			if(isNotNull(aciontt)){
				data.actiontype=aciontt;
			}
			notice.fnc.sendAjax1(url,data);
		}
		notice.view.hidggDiv();
	},
	//公告Ajax发送请求
	sendAjax1 : function(url, data,_callback) {
		$.post(url, data, function(arr) {
			notice.fnc.createAllNoticeHtml(arr.list);
			if(isNotNull($("#hidnotice_id").val())){
				$("div[class='ggonetit ggdiv']").find("a").click();
				//默认展开事件触发完成后销毁
				$("#hidnotice_id").val("");
			}
			if(isNotNull(_callback)){	//初始化分页
				$("#pageInfo_0").show();
				initPages($("#notice_main_0 .pageol").find("li").find("a.mpages").find("span").html(),arr.listcnt, notice.default_pagesize, _callback,"pageInfo_0");
			}
		});
	},
	//生成所有公告列表html
	createAllNoticeHtml:function(arrlist){
		var gglistHtml="";
		if(arrlist!=null){
			$(arrlist).each(function(i,v){
				var onegg="";
				var n=v.notice;
				//默认已读
				var title_str="<p onClick='notice.view.showggxqinfo("+i+","+n.id+")'>"+n.title+"</p>";
				//未读标题显示加粗
				if(v.isReade==0) title_str="<p id='gg_title"+i+"' onClick='notice.view.showggxqinfo("+i+","+n.id+")' style='font-weight:bolder'>"+n.title+"</p>";				
				//公告简要info
				onegg+="<div class='ggone' id='gginfo"+i+"'><div class='ggfg'></div>";
				onegg+="<div class='ggonetit ggdiv'>"+title_str+"<a href='javascript:;' onClick='notice.view.showggxqinfo("+i+","+n.id+")'>展开</a>";
				if(isNotNull(n.createdate)) onegg+="<span>发布时间：<label>"+n.createdate+"</label></span>";
				onegg+="</div><div class='ggdiv'> <div class='cgg'>"+n.contentText+"</div></div><div class='ggfg'></div>";
				onegg+="<div class='ggdiv'>"+notice.fnc.createPublicStr(i,v,0)+"</div>";
				onegg+="<div class='oneplBox dn' id='gg_commdiv"+n.id+"'></div><div id='gg_moreComms"+n.id+"'></div><div class='ggfg'></div></div>";
				//公告详情info				
				onegg+="<div class='tabBox listBox dn' id='ggxqinfo"+i+"'><div class='ggonexq'><div class='ggfg'></div><div class='ggonetitxq ggdiv'><a href='javascript:;' onClick='notice.view.hidggxqinfo("+i+","+n.id+")'>收起</a></div>";
				onegg+="<div class='ggdiv'><div class='ggtitlexq'><p class='yltitle2'>"+n.title+"</p><p class='yltimsg'><span>"+v.noticeno+"</span><span>签发人：<label>"+n.issuedname+"</label></span><span>发文部门：<label>"+n.orgname+"</label></span><span>发布时间：<label>"+n.createdate+"</label></span></p></div>";
				onegg+="<div class='ggtitlexq2' style='word-wrap: break-word;'><div>"+n.content+"</div></div></div>";
				onegg+="<div class='ggfg'></div> <div class='ggdiv' style=' border-top:1px solid #d9d9d9; background:#F8F8F8'>"+notice.fnc.createPublicStr(i,v,1)+"</div></div>";
				onegg+="<div class='oneplBox dn' id='ggxq_commdiv"+n.id+"'></div><div id='ggxq_moreComms"+n.id+"'></div><div class='ggfg'></div></div>";
				//加入隐藏域,保存公告当前显示状态(展开或收起),默认收起0
				onegg+="<input type='hidden' id='viewmodel"+n.id+"' value='0'/><input type='hidden' id='gg_istrue"+n.id+"' value='"+n.istrue+"'/>";//$("#gg_istrue").val("");
				gglistHtml+=onegg;
			});
		}
		$("#gglist").html(gglistHtml);		
	},
	//生成公告操作部分html
	createPublicStr:function(i,v,tt){
		var n=v.notice;
		var readecnt=v.readecnt;
		var commcount=v.commcnt;
		var likecount=v.likecnt;
		var curuserid=$("#hiduserid").val();
		var rshtml="";
		rshtml+="<span class='cjrinfo'>创建人：<label>"+n.username+"</label></span>";
		if(isNotNull(n.newfilename)) rshtml+="<span class='cjrinfo'>附件：<a href='/ipav/notice/getFile?path="+n.newfilename+"'>"+n.oldfilename+"</a></span>";
		rshtml+="<div class='ggzplun'>";
		var str1="<a href='javascript:;' class='ggdelete' onClick='notice.view.showggaction(1,"+n.id+")'>删除</a>";
		var str1_1="<a href='javascript:;' class='ggupdate' onClick='notice.fnc.gotoNewOrEditNotice("+n.id+")'>修改</a>";
		var str2="<a href='javascript:;' class='gga mr10' onClick='notice.fnc.queryReades("+n.id+",1)'>已读(<span class='reade_span"+n.id+"'>"+readecnt+"</span>)</a>";
		//判断此公告是否允许评论
		if(n.iscomment==1){
			str2+="<a href='javascript:;' class='gga' onClick='notice.fnc.initComms("+n.id+")'>评论(<span class='comm_span"+n.id+"'>"+commcount+"</span>)</a>";
			str2+="<a href='javascript:;'class='zans'";
			if(likecount>0)	str2+="onmouseover='notice.fnc.initLikes("+n.id+")'"; 
			if(tt==0) str2+=" id='gg_zan"+n.id+"' ";
			else str2+=" id='ggxq_zan"+n.id+"' ";
			//判断当前用户是否对此条公告点过赞
			//未赞
			if(v.isPraise==0){
				str2+=" onclick='notice.fnc.addCommOrLike(0,"+n.id+")'>("+likecount+")</a>";
			}//已赞
			else{
				str2+=" >("+likecount+")</a>";
			}
			//查看点赞详情div
			if(tt==0)	str2+="<div class='praiseBox' id='gg_zandiv"+n.id+"'>";
			else str2+="<div class='praiseBox' id='ggxq_zandiv"+n.id+"'>";
			
			str2+="<div class='praiseul'></div><div class='praisePageBox'></div></div>";
		}
		var str3="<a href='javascript:;' class='ggas' style='color:#0198d1;' onClick='notice.view.showggaction(0,"+n.id+")'>发布</a>";
		var str5="<a href='javascript:;'>已发布</a>";
		if(curuserid==n.userid){
			if(n.istrue==0){
				rshtml+=str1;
				if($("#hidModify").val()==1) rshtml+=str1_1;
				rshtml+=str3;
			}else{
				rshtml+=str2;
				rshtml+=str1;
				rshtml+=str5;
			}
		}else{
				rshtml+=str2;
				rshtml+=str5;
		}
		rshtml+="</div>";
		return rshtml;
	},
	//获取公告评论显示状态:展开或收起
	getggviewstate:function(nid,div){
		var viewstr="#gg_";
		if($("#viewmodel"+nid).val()=="1") viewstr="#ggxq_";
		return viewstr+div+nid;	
	},
	//查询已赞用户
	initLikes:function(nid){		
		var zanid=notice.fnc.getggviewstate(nid,"zan");
		var zandivid=notice.fnc.getggviewstate(nid,"zandiv");
		var h=($(window).height()-60)-$(zanid).parent().position().top;
		if(h<219)	$(zandivid).removeClass().addClass("praiseBox2");
		else	$(zandivid).removeClass().addClass("praiseBox");
		notice.fnc.queryLikes(nid,1);
		$(zanid).bind("mouseout",function(){$(zandivid).hide();});
	},
	//查询点赞人员
	queryLikes:function(nid,curPage){
		var data = new Object();
		data.noticeid=nid;
		data.curPage=curPage;			
		data.pageSize=notice.default_pagesize;
		$.post("queryNoticeLikes",data,function(datainfo) {
				if(isNotNull(datainfo))					
					notice.fnc.createZanHtml(nid,datainfo);			
			});
	},
	//生成点赞用户html
	createZanHtml:function(nid,data){
		var zanid=notice.fnc.getggviewstate(nid,"zan");
		$(zanid).html("("+data.likecount+")");
		var zan_html="<ul>";
		$.each(data.likelist,function(){
			zan_html+="<li><a href='javascript:;'><img src='"+this.userimg+"' title='"+this.username+"'></a></li>";
		});
		zan_html+="</ul>";
		var zandivid=notice.fnc.getggviewstate(nid,"zandiv");
		$(zandivid).find("div[class='praiseul']").html(zan_html);
		var _curPage=data.curPage;
		var maxPage=Math.ceil(data.likecount/notice.default_pagesize);
		var page_html="";
		if(maxPage>1){
			if(_curPage==1){
				page_html='<ul><li><span style="margin-left: 10px;line-height: 30px;">上一页</span></li><li><a href="#1" onclick="notice.fnc.queryLikes('+nid+','+(_curPage+1)+')">下一页</a></li></ul>';
			}else if(_curPage==maxPage){
				page_html='<ul><li><a href="#1" onclick="notice.fnc.queryLikes('+nid+','+(_curPage-1)+')">上一页</a></li><li><span style="margin-left: 10px;line-height: 30px;">下一页</span></li></ul>';
			}else{
				page_html='<ul><li><a href="#1" onclick="notice.fnc.queryLikes('+nid+','+(_curPage-1)+')">上一页</a></li><li><a href="#1" onclick="notice.fnc.queryLikes('+nid+','+(_curPage+1)+')">下一页</a></li></ul>';
			}
		}else{
			page_html='<ul><li><span style="margin-left: 10px;line-height: 30px;">上一页</span></li><li><span style="margin-left: 10px;line-height: 30px;">下一页</span></li></ul>';
		}
		$(zandivid).find("div[class='praisePageBox']").html(page_html);
		notice.view.showZans(zandivid);
	},
	//查询公告已读用户
	queryReades:function(nid,_curPage){
		var data=new Object();
		data.noticeid=nid;
		data.curPage=_curPage;			
		data.pageSize=notice.default_pagesize;
		$.post("queryNoticeReades",data,function(data_info) {
			if(isNotNull(data_info))
				notice.fnc.createReadesHtml(nid,data_info);				
			notice.view.showReades();
		});
	},
	//生成已读人员html
	createReadesHtml:function(nid,data){
		var readehtml="";
		if(data.readelist.length!=0){
			readehtml+="<ul>";
			$(data.readelist).each(function(){
				readehtml+="<li><a href='javascript:;' title='"+this+"'>"+this+"</a></li>";
			});
			readehtml+="</ul>";
		}else{
			readehtml=notice.msg.ReadesNull;
		}
		$("#readeslist").html(readehtml);
		var _curPage=data.curPage;
		var maxPage=Math.ceil(data.readecnt/notice.default_pagesize);
		var page_html="";
		if(maxPage>1){
			if(_curPage==1){
				page_html='<ul><li><span style="margin-left: 10px;line-height: 30px;">上一页</span></li><li><a href="#1" onclick="notice.fnc.queryReades('+nid+','+(_curPage+1)+')">下一页</a></li></ul>';
			}else if(_curPage==maxPage){
				page_html='<ul><li><a href="#1" onclick="notice.fnc.queryReades('+nid+','+(_curPage-1)+')">上一页</a></li><li><span style="margin-left: 10px;line-height: 30px;">下一页</span></li></ul>';
			}else{
				page_html='<ul><li><a href="#1" onclick="notice.fnc.queryReades('+nid+','+(_curPage-1)+')">上一页</a></li><li><a href="#1" onclick="notice.fnc.queryReades('+nid+','+(_curPage+1)+')">下一页</a></li></ul>';
			}
		}else{
			page_html='<ul><li><span style="margin-left: 10px;line-height: 30px;">上一页</span></li><li><span style="margin-left: 10px;line-height: 30px;">下一页</span></li></ul>';
		}
		$("#readePage").html(page_html);
	},
	initComms:function(nid){
		var commdivid=notice.fnc.getggviewstate(nid,"commdiv");
		if($(commdivid).css("display")=="none"){
			notice.fnc.queryComms(nid,0);
		}else{
			//隐藏公告评论
			$(commdivid).html("");
			$(commdivid).hide();
			var moreCommid=notice.fnc.getggviewstate(nid,"moreComms");
			$(moreCommid).html("");	
			$(moreCommid).hide("");	
		}
	},
	//查询公告的所有评论
	queryComms:function(nid,_isAll){
		var commdivid=notice.fnc.getggviewstate(nid,"commdiv");		
		$.post("queryNoticeComments", {noticeid:nid,isAll:_isAll}, function(arr) {
			if(isNotNull(arr)){
				$(".comm_span"+nid).html(arr.commcount);
				$(commdivid).html(notice.fnc.createCommHtml(nid,arr));
				$(commdivid).show();
				var moreCommid=notice.fnc.getggviewstate(nid,"moreComms");
				if(_isAll==1)
					$(moreCommid).html("");					
				else if(_isAll==0&&arr.commcount>notice.default_commcnt)
					$(moreCommid).html("<div class='loadpl' style='margin-bottom:10px;' align='center'><a href='javascript:;' onclick='notice.fnc.queryComms("+nid+",1)'><img src='/pages/images/platform/renew_gray.png'>加载更多评论...</a></div>");
				$(moreCommid).show();
			}
		});
		$("#mainBox").css("height","auto");
//		console.log("+++:"+$("#mainBox").outerHeight(true));
	},
	//生成公告评论内容html
	createCommHtml:function(nid,data){
		var commhtml="";
		commhtml+="<div class='onepl ppk' style='margin-top:-8px;'><textarea id='comm_content"+nid+"' class='ppktext' placeholder='请输入评论内容...'></textarea><div class='ppk2'><input type='button' class='plbut' value='评论' onClick='notice.fnc.addCommOrLike(1,"+nid+")'></div></div>";
		$.each(data.commlist,function(){
			commhtml+="<div class='twoplBox ppk2 bdx'><div class='pluser2'><img src='"+this.commuserimg+"'></div>";
			commhtml+="<div class='pluserx'><div class='pluserxtit plx'><a href='javascript:;' id='"+this.commuserid+"'>"+this.commusername+"</a><span>评论时间：<label>"+this.commdate+"</label></span></div>";
			commhtml+="<div class='pluserxtit'><p class='ggnr2'>"+this.commcontent+"</p></div></div></div>";
		});
		return commhtml;
		
	},
	//添加评论或点赞
	addCommOrLike:function(tt,nid){
		var url="addLikeOrComment";
		var data = new Object();
		data.actionid=nid;
		data.commuserid=$("#hiduserid").val();//当前操作用户id
		data.commtype=tt;//操作类型,0:点赞,1:代表评论
		if(tt==1){//评论
			data.commcontent=$("#comm_content"+nid).val();
			if(!isNotNull($.trim(data.commcontent))) return false;
		}
		$.post(url,{commentStr:JSON.stringify(data)},function(data) {
			if(isNotNull(data)){
				if(data.msg=='success'){
					if(tt==0){
						var zanid=notice.fnc.getggviewstate(nid,"zan");
						$(zanid).removeAttr("onclick");
						$(zanid).bind("mouseover",function(){notice.fnc.initLikes(nid);});
						notice.fnc.initLikes(nid);
					}else if(tt==1){
						notice.fnc.queryComms(nid,1);
					}
				}
			}
		});
	},
	//拼接时间字符串
	createTimeStr:function(timeType){
		var maxValue=notice.constant.Zero;
		if(timeType==notice.constant.Hours){
			maxValue=notice.constant.MaxHours;
		}else if(timeType==notice.constant.Minute){
			maxValue=notice.constant.MaxMinute;
		}
		var timeStr=new Array();
		for(var i=0;i<maxValue;i++){		
			timeStr.push(this.pad(i, 2));			
		}
		return timeStr;
	},
	//自动补充0
	pad:function(str,len){
		var strLen=str.toString().length;
		while(strLen<len){
			str='0'+str;
			strLen++;
		}
		return str;
	},
	//格式化日期字符串,形如2015年1月1日转换为2015-01-01形式
	formatdateStr:function(datestr){
		if(/^\d{4}.\d{1}.\d{1}.$/.test(datestr)){
			datestr=datestr.replace("年","-0").replace("月","-0");
		}else if(/^\d{4}.\d{2}.\d{1}.$/.test(datestr)){
			datestr=datestr.replace("年","-").replace("月","-0");
		}else if(/^\d{4}.\d{1}.\d{2}.$/.test(datestr)){
			datestr=datestr.replace("年","-0").replace("月","-");
		}else if(/^\d{4}.\d{2}.\d{2}.$/.test(datestr)){
			datestr=datestr.replace("年","-").replace("月","-");
		}
		datestr=datestr.replace("日","");
		return datestr;
	}	
	
};
/*
 * 公告类型对象
 */
var noticetype = {
	Initialize : function() {		
		$("input[name='qysh']").bind("click", function(){noticetype.fnc.initlist();});
		notice.clearPageParm("pageInfo_1");
		noticetype.fnc.initlist();
	}
};
/*
 * 公告类型提示消息
 */
noticetype.msg = {
	NotNull:"*不能为空",
	Unavailable:"*该类型名称已经被使用!",
	EditOne:"您每次只能对一项执行修改操作!",
	MoveUpFail:"当前行为首行,不能执行上移操作",
	MoveDownFail:"当前行为末行,不能执行下移操作",
	Enable : "您确定执行启用操作吗?",
	Disable : "您确定执行禁用操作吗?",
	Delete : "您确定执行删除操作吗?",
	SetDefault : "您确定执行设置默认类型操作吗?",
	OnlyOne : "您只能设置一项为默认类型!",
	Emply : "您尚未选择一项操作对象!",
	NoEnable:"您选择的公告类型已经处于启用状态!",
	NoEdit:"停用的公告类型不能进行修改操作!",
	NoDelete:"停用的公告类型不能进行删除操作!",
	NoSetDefault : "停用的公告类型不能进行设置默认类型操作!",
	NoDisable : "停用的公告类型不能进行禁用操作!",
	ErrorMsg:"不好意思,操作失败!"
};
/*
 * 公告类型标识符
 */
noticetype.constant = {
	Zero:0,	
	Enable : 0,
	Disable : 1,
	SetDefault : 2,
	Delete : 3,
	MoveUp:0,
	MoveDown:1
};
/*
 * 公告类型视图层
 */
noticetype.view = {
    //显示新建公告类型视图
	showNewDiv : function() {
		notice.view.show_pub();
		$("#simScrollCont6").show();
		$("#simScrollContBox6").show();
	},
	//隐藏新建公告类型视图
	hidNewDiv : function() {
		$("#typename").val("");
		$("#description").val("");
		$("#errinfo").html("");
		notice.view.hide_pub();
		$("#simScrollContBox6").hide();
		$("#simScrollCont6").hide();
	},
	//显示修改公告类型视图
	showEditDiv : function() {
		var chkleng = $("input[name='chk']:checked").length;
		if (chkleng == noticetype.constant.Zero) {
			this.showgglxDiv(noticetype.msg.Emply);
		}else if(chkleng==1){
			var istrue = $("input[name='qysh']:checked").val();
			if (istrue == 1) {
				this.showgglxDiv(noticetype.msg.NoEdit);
			}else{
				var index=$("input[name='chk']:checked").val();
				$("#editid").val($("#typeid"+index).val());
				$("#hideditname").val($("#typename"+index).html());
				$("#editname").val($("#typename"+index).html());
				$("#editdescription").val($("#description"+index).html());
				notice.view.show_pub();
				$("#simScrollCont7").show();
				$("#simScrollContBox7").show();
			}
		}else{
			this.showgglxDiv(noticetype.msg.EditOne);
		}
	},
	//隐藏修改公告类型视图
	hidEditDiv : function() {
		$("#editid").val("");
		$("#hideditname").val("");
		$("#editname").val("");
		$("#eidterrinfo").html("");
		$("#editdescription").val("");
		noticetype.fnc.clearAllChk();
		notice.view.hide_pub();
		$("#simScrollCont7").hide();
		$("#simScrollContBox7").hide();
	},
	//显示公告类型操作视图
	showDiv : function(key) {
		var chkleng = $("input[name='chk']:checked").length;
		var info = "";
		if (chkleng == noticetype.constant.Zero) {
			info = noticetype.msg.Emply;
			key="";
		} else {
			var istrue = $("input[name='qysh']:checked").val();
			switch (key) {
			case noticetype.constant.Enable:
				if(isNotNull(istrue)&&istrue==0){
					info = noticetype.msg.NoEnable;
					key="";
				}else{
					info = noticetype.msg.Enable;
				}
				break;
			case noticetype.constant.Disable:
				if (istrue == 1) {
					info = noticetype.msg.NoDisable;	
					key="";
				} else {
					info = noticetype.msg.Disable;
				}
				break;
			case noticetype.constant.SetDefault:
				if (istrue == 1) {
					info = noticetype.msg.NoSetDefault;		
					key="";
				} else {
					if (chkleng == 1) {
						info = noticetype.msg.SetDefault;
					} else {
						info = noticetype.msg.OnlyOne;
						key="";
					}
				}
				break;
			case noticetype.constant.Delete:
				if (istrue == 1) {
					info = noticetype.msg.NoDelete;			
					key="";
				}else{
					info = noticetype.msg.Delete;
				}
				break;
			}
		}
		$("#actiontype").val(key);
		this.showgglxDiv(info);
	},
	//上移操作视图
	moveUp:function(){
		var chklist = noticetype.fnc.checkboxList();
		if (chklist.length==noticetype.constant.Zero) {
			this.showgglxDiv(noticetype.msg.Emply);
		}else{		
			for ( i = 0; i<chklist.length ; i++ ) { 
				var index=chklist[i];
				if(index==1){
					noticetype.view.showgglxDiv(noticetype.msg.MoveUpFail);
					break;
				}else{
					//当前行
					var curRow=$("#tr"+index);
					var curIdValue=curRow.find("#typeid"+index).val();
					//上一行
					var prevRow=curRow.prev();
					var prevIndex=prevRow.find("input[type=checkbox]").val();
					var prevIdValue=prevRow.find("#typeid"+prevIndex).val();
					
					//改变状态与交换id值
					curRow.find("input[type=checkbox]").attr("checked",false);
					curRow.find("#typeid"+index).val(prevIdValue);
					prevRow.find("input[type=checkbox]").attr("checked",true);
					prevRow.find("#typeid"+prevIndex).val(curIdValue);
				}
			}
			noticetype.fnc.changeIndex();					
		}
	},
	//下移操作视图
	moveDown:function(){
		var chklist = noticetype.fnc.checkboxList();
		if (chklist.length==noticetype.constant.Zero) {
			noticetype.view.showgglxDiv(noticetype.msg.Emply);
		}else{
			var lastRow=$("#typelist tr").length;
			for ( i = chklist.length - 1; i >= 0 ; i-- ) { 
				var index=chklist[i];
				if(index==lastRow){
					noticetype.view.showgglxDiv(noticetype.msg.MoveDownFail);
					break;
				}else{
					//当前行
					var curRow=$("#tr"+index);
					var curIdValue=curRow.find("#typeid"+index).val();
					//下一行
					var nextRow=curRow.next();
					var nextIndex=nextRow.find("input[type=checkbox]").val();
					var nextIdValue=nextRow.find("#typeid"+nextIndex).val();
					//改变状态与交换id值
					curRow.find("input[type=checkbox]").attr("checked",false);
					curRow.find("#typeid"+index).val(nextIdValue);
					nextRow.find("input[type=checkbox]").attr("checked",true);
					nextRow.find("#typeid"+nextIndex).val(curIdValue);
				}
			}
			noticetype.fnc.changeIndex();					
		}
	},
	//显示公告类型提示信息
	showgglxDiv : function(info) {
		$("#contentstr2").html(info);
		notice.view.show_pub();
		$("#simScrollCont12").show();
		$("#simScrollContBox12").show();
	},
	//隐藏公告类型提示信息
	hidgglxDiv : function() {
		noticetype.fnc.clearAllChk();
		$("#contentstr2").html("");
		$("#actiontype").val("");
		notice.view.hide_pub();
		$("#simScrollContBox12").hide();
		$("#simScrollCont12").hide();
	}

};
/*
 * 公告类型服务层
 */
noticetype.fnc = {
	//初始化数据
	initlist:function(){
		//恢复到第一页数据
		$("#pageol").find("a.mpages").find("span").html("1");
		this.getNoticeType(null);
	},
    //查询所有公告类型
	getNoticeType : function(chklist) {
		var url = "showTypes";
		var data = {
			"istrue" : $("input[name='qysh']:checked").val(),
			"beginRow":notice.initPageParm("pageInfo_1",notice.default_pagesize).beginRow,
			"pageSize":notice.initPageParm("pageInfo_1",notice.default_pagesize).pageSize
		};
		noticetype.fnc.sendAjax2(url, data,chklist);
	},
	//校验公告类型名
	chkTypeName:function(chkname){
		$.post("checkTypeName", {typename:chkname}, function(arr) {
			if(isNotNull(arr)){
				if(arr.success=='1'){
					return false;
				}else{
					return true;
				}
			}
		});
	},
	//添加公告类型
	addType : function() {
		var url="addNoticeType";
		var typename= $("#typename").val();
		if(isNotNull(typename)){
			var data =new Object();
			data.typename=typename;
			data.description=$("#description").val();
			data.userid=$("#hiduserid").val();
			$.post(url, data, function(arr) {
				if(isNotNull(arr)){
					if(arr.message=='success'){
						noticetype.view.hidNewDiv();
						noticetype.fnc.initlist();
					}else if(arr.message=='error'){
						$("#errinfo").html(noticetype.msg.Unavailable);
					}
				}
			});
		}else{
			$("#errinfo").html(noticetype.msg.NotNull);
		}
	},
	//修改公告类型顺序
	changeIndex:function(){
		var url="changeNoticeTypeIndex";
		var data=new Object();
		var idlist = new Array();
		var indexlist = new Array();
		$("#typelist tr").each(function(k,v){
			idlist.push($("#typeid"+(k+1)).val());
			indexlist.push($("#typeindex"+(k+1)).val());
		});
		data.ids=idlist;
		data.indexs=indexlist;
		noticetype.fnc.sendAjax3(url, data, noticetype.fnc.checkboxList());
	},
	//修改公告类型
	modifyType:function(){
		var typename= $("#editname").val();
		if(isNotNull(typename)){
			var url = "modifyNoticeType";
			var data=new Object();
			data.id=$("#editid").val();
			if(typename==$("#hideditname").val()){
				data.ischk=0;
			}else{
				data.ischk=1;
			}
			data.typename=typename;
			data.description=$("#editdescription").val();
			$.post(url, data, function(arr) {
				if(isNotNull(arr)){
					if(arr.message=='success'){					
						noticetype.view.hidEditDiv();
						noticetype.fnc.initlist();
					}else if(arr.message=='error'){
						$("#eidterrinfo").html(noticetype.msg.Unavailable);
					}
				}
			});
		}else{
			$("#eidterrinfo").html(noticetype.msg.NotNull);
		}
	},
	//修改公告类型状态
	modifyState : function() {
		var actiontype = $("#actiontype").val();
		if (isNotNull(actiontype)) {
			var chklist = this.checkboxList();
			if (chklist.length > noticetype.constant.Zero) {
				var url = "modifyNoticeTypeState";
				var data = new Object();
				var keylist = new Array();
				var valuelist = new Array();
				var key = parseInt(actiontype);
				$.each(chklist, function() {
					keylist.push($("#typeid" + this).val());
					if (actiontype == "0") {
						valuelist.push(noticetype.constant.Enable);
					} else {
						valuelist.push(noticetype.constant.Disable);
					}
				});
				data.actiontype = actiontype;
				data.ids = keylist;
				data.values = valuelist;
				noticetype.fnc.sendAjax3(url, data,null);
			}
		}
		noticetype.view.hidgglxDiv();
		
	},
	//获取所有选中的checkbox值集合
	checkboxList : function() {
		var chkvalue = new Array();
		$("input[name='chk']:checked").each(function() {
			chkvalue.push($(this).val());
		});
		return chkvalue;
	},
	//清空所选checkbox
	clearAllChk:function(){
		$("#firstChk").attr("checked", false);
		$("input[name='chk']:checked").each(function() {
			$(this).attr("checked", false);
		});
	},
	//全选与反选
	selectChk : function() {
		if ($("#firstChk").prop("checked")) {
			$("#typelist :checkbox").attr("checked", true);
		} else {
			$("#typelist :checkbox").attr("checked", false);
		}
	},
	//公告类型发送Ajax请求
	sendAjax2 : function(url, data,chklist) {
		$.post(url, data, function(arr) {
			noticetype.fnc.createTypeHtml(arr.typelist);
			// 每次请求结束后将页面上的全选复原
			if ($("#firstChk").prop("checked")) {
				$("#firstChk").attr("checked", false);
			}
			if(isNotNull(chklist)){
				//恢复上次被选中复选框
				$.each(chklist,function(){
					$("#tr"+this).find("input[type=checkbox]").attr("checked",true);
				});
			}
			//初始化分页
			initPages($("#notice_main_1 .pageol").find("li").find("a.mpages").find("span").html(),arr.resultcnt, notice.default_pagesize, noticetype.fnc.getNoticeType,"pageInfo_1");
		});
	},
	//公告类型操作发送请求
	sendAjax3:function(url,data,chkbox){
		$.post(url, data, function(arr) {
			if(isNotNull(arr)){
				if(arr.message=='success'){					
					noticetype.fnc.getNoticeType(chkbox);
				}else if(arr.message=='error'){
					noticetype.view.showgglxDiv(noticetype.msg.ErrorMsg);
				}
			}
		});
	},
	//创建生成公告类型html
	createTypeHtml : function(arr) {
		var divhtml = "";
		$.each(arr, function(i, d) {
			var index = i + 1;
			divhtml += "<tr id='tr"+index+"'>";
			divhtml += "<td style='text-align:left;padding-left:30px;'><input type='checkbox' name='chk' value='" + index+ "'/><span>" + index + "</span></td>";
			divhtml += "<td><span id='typename" + index + "' class='gglxname'>" + d.typename	+ "</span></td>";
			divhtml += "<td><p id='description" + index + "' class='ms' title='"+d.description+"'>"+ d.description + "</p></td>";
			var defaulthtml="否";
			if (d.isdefault == 1) {
				defaulthtml="是";
			}
			divhtml += "<td>"+defaulthtml+"</td>";
			divhtml += "<td><span id='username" + index + "'>" + d.username	+ "</span><input type='hidden' value='" + d.userid+ "' id='userid" + index + "'/></td>";
			divhtml += "<td><span id='createdate" + index + "'>" + d.createdate	+ "</span></td>";
			var statehtml="启用";
			if(d.istrue==1){
				statehtml="禁用";
			}
			divhtml+="<td>"+statehtml+"<input type='hidden' value='" + d.istrue+ "' id='typestate'/></td>";
			divhtml += "<input type='hidden' value='" + d.userid+ "' id='userid" + index + "'/><input type='hidden' value='" + d.id+ "' id='typeid" + index + "'/><input type='hidden' value='" + d.typeindex+ "' id='typeindex" + index + "'/>";
			divhtml += "</tr>";
		});
		$("#typelist").html(divhtml);
	}
};

function onloalrame(){	
	var inhight =window.parent.document.documentElement.clientHeight;	
	var heightmain = inhight-114;
	var leftheight = inhight-126;
	document.getElementById("pt-center").style.minHeight=leftheight+"px";
	$("#_ptlfbg",window.parent.document).css("height",heightmain);
	$(".ggmainBox").height(heightmain);
	$(".ggmainBox").css('overflow-Y','scroll');
	$(".ggmainBox").css('overflow-X','auto');
	$("#leftBox").height(leftheight);
	var besmain =  $(".pt_center").height();
	document.getElementById("notice_main_0").style.minHeight=leftheight+"px";
	if($("#notice_main_1").height()<heightmain) $("#notice_main_1").height(leftheight);

	if(besmain<leftheight)
	{
    document.getElementById("leftBox").style.height=leftheight+"px";
	}
	else if(besmain>leftheight)
	{
	document.getElementById("leftBox").style.height=(besmain-12)+"px";
	}

}
window.onload=onloalrame;

$(function() {

	$("body").mouseover(function(){
		onloalrame();
		initHeight();

	})

});

$(window).resize(function() {
	onloalrame();
	$(".simaltBox").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBox").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBox").outerHeight()) / 2
    });
	$(".simaltBox2").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBox2").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBox2").outerHeight()) / 2
    });
    $(".simScrollContBox").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox").outerHeight()) / 2
    });
	$(".simScrollContBoxs").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBoxs").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBoxs").outerHeight()) / 2
    });
	$(".simScrollContBox3").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox3").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox3").outerHeight()) / 2
    });
	$(".simScrollContBox4").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox4").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox4").outerHeight()) / 2
    });
	$(".simScrollContBox5").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox5").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox5").outerHeight()) / 2
    });
	$(".simScrollContBox7").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox7").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox7").outerHeight()) / 2
    });
	$(".simScrollContBox8").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox8").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox8").outerHeight()) / 2
    });
	$(".simScrollContBox9").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox9").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox9").outerHeight()) / 2
    });

});
function initHeight(){
	var t = $('.pttitle').offset().top;
	var mh = $('body').height();
	var fh = $('.pttitle').height();
	$(".ggmainBox").scroll(function(e){
		console.log($(".ggmainBox").scrollTop());	
		var s = $(".ggmainBox").scrollTop();	
		if(s > t - 0){
			$('.pttitle').css('position','fixed');
			$('.pttitle').css('top',12+'px');
			
			$('.lefme').css('position','fixed');
			$('.lefme').css('top',12+'px');	
			if(s + fh < mh){
			}			
		}else{
			$('.pttitle').css('position','absolute');
			$('.lefme').css('position','absolute');
		}
	})
}
$(function() {
	initHeight();
//	$(".ggmainBox").bind("mourse",initHeight);
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			resizeType : 0,
			allowPreviewEmoticons : false,
			allowImageUpload : true,
			items : [
			         'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			         'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			         'insertunorderedlist', '|'],
			         afterBlur : function(){ this.sync(); }  //Kindeditor下获取文本框信息
		});
	});
	notice.topDiv=$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont");
	$(window).resize();
	if(isNotNull($("#hidnotice_id").val())){
		notice.fnc.initAllNotice(1);
	}else{
		if($("#hidAll").val()==1){
			$("#notice_title_0").click();
		}else if($("#hidType").val()==1){
			$("#notice_title_1").click();
		}else if($("#hidNew").val()==1){
			notice.view.showAddOrEditDiv();
			notice.fnc.gotoNewOrEditNotice();
		}
	}
});
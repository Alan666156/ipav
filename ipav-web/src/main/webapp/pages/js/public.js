// JavaScript Document
function set_menu(e) {
	var menu_num=document.getElementById("topmenu");
	var len_num=menu_num.getElementsByTagName("a");
	for(var i=0; i<len_num.length;i++){
		 len_num[i].className=" ";
	}
	 len_num[e].className="current";
	}
	

function set_menu2(e) {
	var menu_num=document.getElementById("leftmenu");
	var len_num=menu_num.getElementsByTagName("a");
	for(var i=0; i<len_num.length;i++){
		 len_num[i].className=" ";
	}
	 len_num[e].className="current";

	}
	
function set_menu3(e) {
	var menu_num=document.getElementById("leftmenu");
	var len_num=menu_num.getElementsByTagName("a");
	for(var i=0; i<len_num.length;i++){
		 len_num[i].className=" ";
	}
	 len_num[e].className="current";

	}
	
function set_menu4(e) {
	var menu_num=document.getElementById("pageol");
	var len_num=menu_num.getElementsByTagName("a");
	for(var i=0; i<len_num.length;i++){
		 len_num[i].className="";
	}
	 len_num[e].className="mpages";

	}
	
function drdcinfo(){
	document.getElementById("drdc").style.display="block";
	}
function ycdrdc(){
	document.getElementById("drdc").style.display="none";
	}
function drdcinfo2(){
	document.getElementById("drdc").style.display="none";
	}
	
function senfe(o,a,b,c,d){
var t=document.getElementById(o).getElementsByTagName("tr");
for(var i=0;i<t.length;i++){
t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
t[i].onclick=function(){
if(this.x!="1"){
this.x="1";
this.style.backgroundColor=d;
}else{
this.x="0";
this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
}
}
t[i].onmouseover=function(){
if(this.x!="1")this.style.backgroundColor=c;
}
t[i].onmouseout=function(){
if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
}
}
}
function tab(id,curnum,tnum){
		for (var i = 0; i< tnum;i++ ){
			if(curnum == i){
//				document.getElementById(id+"_title_"+curnum).className = "current";
				$("#"+id+"_title_"+curnum).toggleClass("current");
//				document.getElementById(id+"_main_"+curnum).style.display = "block";
				$("#"+id+"_main_"+curnum).css("display",'block');
				$(".addgg").removeClass("addggcls");
				$(".addgg span").removeClass("lfad2");
//				if(id=='notice'){
//					$("#AddOrEditNotice").hide();
//					if(curnum==0){//全部公告
//						notice.Initialize();
//					}else if(curnum==1){//类型设置
//						noticetype.Initialize();
//					}
//				}
//				else 
				/*if(id=='rsgl'){						
					if(curnum==0){//发送工资单
						$("#wageSearch").trigger("click");
					}else if(curnum==1){//工资自定义项设置
						$($("input[name='extStatus']")[0]).trigger("click");
					}
				}*/
			}else{
//				document.getElementById(id+"_title_"+i).className = "";
				$("#"+id+"_title_"+i).removeClass("current");
//				document.getElementById(id+"_main_"+i).style.display = "none";
				$("#"+id+"_main_"+i).css("display",'none');
			}
		}
}
		
function setleftMen(e,c) {
	var menu_num=document.getElementById(c);
	var len_num=menu_num.getElementsByTagName("a");
	for(var i=0; i<len_num.length;i++){
		 len_num[i].className=" ";
	}
	 len_num[e].className="current";
	}
function setleftMen2(e,c) {
	var menu_num=document.getElementById(c);
	var len_num=menu_num.getElementsByTagName("a");
	for(var i=0; i<len_num.length;i++){
		 len_num[i].className=" ";
	}
	 len_num[e].className="current";
	}

 function tabzw(id,curnum,tnum){
			for (var i = 0; i< tnum;i++ ){
				if(curnum == i){
					document.getElementById(id+"_main_"+curnum).style.display = "block";
					document.getElementById(id+"_mainz_"+curnum).style.display = "none";
				}else{
					document.getElementById(id+"_main_"+i).style.display = "none";
				}
			}
		}
 function tabzwa(id,curnum,tnum){
			for (var i = 0; i< tnum;i++ ){
				if(curnum == i){
					document.getElementById(id+"_mainz_"+curnum).style.display = "block";
					document.getElementById(id+"_main_"+curnum).style.display = "none";

				}else{
					document.getElementById(id+"_main_"+i).style.display = "none";
				}
			}
		}
//function windowinfo(){
   // var a = document.documentElement.clientHeight;
	//var c = a-200;
	//document.getElementById("tabBox").style.height=c+"px";
	//document.getElementById("tabBox").style.overflow="scroll";
	//document.getElementById("tabBox").style.overflowX="hidden";
//	}

//平台首页左边导航
	/**
	 * 轮询查询应用消息
	 */	
	var timer;
	showTime();
	function showTime()	{ 
		alwaySearchMsg();
		timer=setTimeout("showTime()", 60000); 
	}		
	/**
	 * 未读信息轮循
	 */	
	var UnReadyMsg;
	UnReadyMsgFunction();
	function UnReadyMsgFunction(){ 
		queryUnreadyMsg();
		UnReadyMsg=setTimeout("UnReadyMsgFunction()", 60000); 
	}
	 
        function ptmenu(id,curnum,tnum){
        	sayitleFunction(1,3)
        	if(id=='ptmu'&&curnum!=-1)
        		$("#ptmu_main_4").css("display","none");
        	if(id=='ptmu' && curnum==0){
        		//showTime();
        		//alwaySearchMsg();
        	}else{
        		//clearTimeout(timer);
        	}
			for (var i = 0; i< tnum;i++ ){
				if(curnum == i){
					document.getElementById(id+"_title_"+curnum).className = "current";
					document.getElementById(id+"_main_"+curnum).style.display = "block";
					//当前选中的菜单图标变色
					if(curnum==0)
					{ 
						document.getElementById("ptm1-1").style.background="url(/pages//images/platform/menu.png) 0px 0px";
					}else 
					if(curnum==1)
					{ 
						//清除发表说说
						 SayUploadSuccess();
						$(".qudtinfo").html("全部动态");
						allSays(1);
						document.getElementById("ptm2-1").style.background="url(/pages//images/platform/menu.png) 0px -104px";
					} else
					if(curnum==2)
					{ 
						document.getElementById("ptm3-1").style.background="url(/pages//images/platform/menu.png) 0px -156px";
					} else
					if(curnum==3)
					{ 
						recommend.clear();
						document.getElementById("ptm4-1").style.background="url(/pages/images/platform/menu.png) 0px -208px";
					}
					//点点
					if(curnum==0)
					{ 
						document.getElementById("my1").style.display="none";
					}else 
					if(curnum==1)
					{ 
						document.getElementById("my2").style.display="none";
					} else
					if(curnum==2)
					{ 
						document.getElementById("my3").style.display="none";
					}
					
				}else{
					document.getElementById(id+"_title_"+i).className = "";
					document.getElementById(id+"_main_"+i).style.display = "none";
					
					if(i==0)
					{ 
						document.getElementById("ptm1-1").style.background="url(/pages/images/platform/menu.png) 0px -26px";
					}else 
					if(i==1)
					{ 
						document.getElementById("ptm2-1").style.background="url(/pages/images/platform/menu.png) 0px -130px";
					} else
					if(i==2)
					{ 
						document.getElementById("ptm3-1").style.background="url(/pages/images/platform/menu.png) 0px -182px";
					} else
					if(i==3)
					{ 
						document.getElementById("ptm4-1").style.background="url(/pages/images/platform/menu.png) 0px -234px";
					} 
				}
			}
		}

        function tabmsg(id,curnum,tnum){
        	if(id=='tsq'&& curnum==1){
        		Mytsclick();
        	}else if(id=='tsq' && curnum==2){
        		invitationReset()
            }
        	 
			for (var i = 0; i< tnum;i++ ){
				if(curnum == i){
					document.getElementById(id+"_title_"+curnum).className = "current";
					document.getElementById(id+"_main_"+curnum).style.display = "block";
					
					if(i==1)
					{
						//document.getElementById("xtmsg").style.display="none";
						//$(".filter").show();
						}
					if(i==0||i==1)
					{ 
						document.getElementById("msglist").style.display="none";
						document.getElementById("gzList").style.display="none";
						}
					if(i==0||i==2)
					{ 
						$(".filter").hide();
						}
				}else{
					document.getElementById(id+"_title_"+i).className = "";
					document.getElementById(id+"_main_"+i).style.display = "none";
				}
			}
		}
		
		function gzinfo(){
				document.getElementById("msglist").style.display="block";
				document.getElementById("gzList").style.display="block";
				document.getElementById("msg_main_0").style.display="none";
				$("#msg_title_0").removeClass();   
			
			}
		function removegzinfo(){
				document.getElementById("msglist").style.display="none";
				document.getElementById("gzList").style.display="none";
				$("#msg_title_0").addClass("current");
				$("#msg_main_0").css('display','block');
			}
			

	function tabpt(id,curnum,tnum){
		   if(id=='msg' && curnum==0){
			   showTime();
			   alwaySearchMsg();
		   }
			for (var i = 0; i< tnum;i++ ){
				if(curnum == i){
					document.getElementById(id+"_title_"+curnum).className = "current";
					document.getElementById(id+"_main_"+curnum).style.display = "block";
					if(i==0||i==1)
					{ 
						document.getElementById("msglist").style.display="none";
						document.getElementById("gzList").style.display="none";
						}
					
				}else{
					document.getElementById(id+"_title_"+i).className = "";
					document.getElementById(id+"_main_"+i).style.display = "none";
				}
			}
		}
	
	 /**
	  * 回退
	  */
	function goback(){
		window.history.back();
	}

	/**********************分页*************************/
	function pagesEventBind(pageClick,_pageid){
		var pageid=isNotNull(_pageid)?_pageid:"pageInfo";
		$("#"+pageid).find(".pageol").find("li").unbind("click");
		$("#"+pageid).find(".pageol").find("li").bind("click",function(){
			if($(this).find("p").length>0)
				return;
			$("#"+pageid).find(".pageol").find("li").find("a").removeClass("mpages");
			if($(this).find("a").length==1){	
				if(parseInt($(this).find("a").find("span").html())==1){
					if(parseInt($(this).parent().find("li:last-child").find("a").find("span").html())>10){
						var pagesHtml='';
						for(var i=2;i<10;i++)
							pagesHtml=pagesHtml.concat('<li><a href="#1"><div><span>'+i+'</span></div></a></li>');
						pagesHtml=pagesHtml.concat('<li><p class="slh">...<p></li><li><a href="#1"><div><span>'+$("#"+pageid).find(".pageol").find("li:last-child").find("span").html()+'</span></div></a></li>');
						$(this).parent().find("li:first-child").nextAll().remove();
						$("#"+pageid).find(".pageol").append(pagesHtml);
						pagesEventBind(pageClick,pageid);
					}
				}else if(parseInt($(this).find("a").find("span").html())==parseInt($(this).parent().find("li:last-child").find("a").find("span").html())){
					var pagesHtml='<li><a href="#1"><div><span>1</span></div></a></li><li><p class="slh">...<p></li>';
					if(parseInt($(this).parent().find("li:last-child").find("a").find("span").html())>10){
						for(var i=parseInt($(this).parent().find("li:last-child").find("a").find("span").html())-8;i<parseInt($(this).parent().find("li:last-child").find("a").find("span").html());i++)
							pagesHtml=pagesHtml.concat('<li><a href="#1"><div><span>'+i+'</span></div></a></li>');
						$(this).parent().find("li:last-child").prevAll().remove();
						$("#"+pageid).find(".pageol").prepend(pagesHtml);
						pagesEventBind(pageClick,pageid);
					}
				}
				$(this).find("a").attr("class","mpages");
				pageClick();
			}
		})
	}

	function initPages(currpage,count,pageSize,pageClick,_pageid){
		var pageid=isNotNull(_pageid)?_pageid:"pageInfo";
		$("#"+pageid).find("label").html(count);
		var pages=Math.ceil(count/pageSize);
		pages=pages>0?pages:1;
		if(currpage==1)
			var pagesHtml='<li><a href="#1" class="mpages"><div><span>1</span></div></a></li>';
		else
			var pagesHtml='<li><a href="#1"><div><span>1</span></div></a></li>';
		if(pages<=10){
			for(var i=2;i<=pages;i++)
				pagesHtml=pagesHtml.concat('<li><a href="#1"'+(currpage==i?'  class="mpages"':'')+'><div><span>'+i+'</span></div></a></li>');
		}else{
			for(var i=2;i<10;i++)
				pagesHtml=pagesHtml.concat('<li><a href="#1"'+(currpage==i?'  class="mpages"':'')+'><div><span>'+i+'</span></div></a></li>');
			pagesHtml=pagesHtml.concat('<li><p class="slh">...<p></li><li><a href="#1"'+(currpage==pages?'  class="mpages"':'')+'><div class="pageysn"><span>'+pages+'</span></div></a></li>');
		}
		$("#"+pageid).find(".pageol").find("li").remove();
		$("#"+pageid).find(".pageol").append(pagesHtml);
		pagesEventBind(pageClick,pageid);
		$("#"+pageid).find(".pageol").prev().unbind("click");
		$("#"+pageid).find(".pageol").next().unbind("click");
		
		$("#"+pageid).find(".pageol").prev().bind("click",function(){
//			if($(".pageol").find("li").find("a.mpages").length==0)
//				alert("已经是第一页");
//			else{
				
				if($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html()==1)
					msgBox("消息提示","已经是第一页");
				else if($("#"+pageid).find(".pageol").find("li").find("a.mpages").parent().prev().find("p").length==0)
					$("#"+pageid).find(".pageol").find("li").find("a.mpages").parent().prev().click();
				else{
					var pagesHtml='';
					var start=parseInt($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html())-8;
					start=parseInt(start)<1?1:start;
					var maxPage=parseInt($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html());
					maxPage=parseInt(maxPage)>9?maxPage:9;
					if(parseInt($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html())>11)
						pagesHtml='<li><a href="#1"><div><span>1</span></div></a></li><li><p class="slh">...<p></li>';	
					for(var i=start;i<=maxPage;i++)
						pagesHtml=pagesHtml.concat('<li><a href="#1"'+(i==(parseInt($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html())-1)?' class="mpages"':'')+'><div><span>'+i+'</span></div></a></li>');
					pagesHtml=pagesHtml.concat('<li><p class="slh">...<p></li><li><a href="#1"><div><span>'+$("#"+pageid).find(".pageol").find("li:last-child").find("span").html()+'</span></div></a></li>');
					$("#"+pageid).find(".pageol").find("li").remove();
					$("#"+pageid).find(".pageol").append(pagesHtml);
					pagesEventBind(pageClick,pageid);
				}
//			}
		});
		$("#"+pageid).find(".pageol").next().bind("click",function(){
				if($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html()==$("#"+pageid).find(".pageol").find("li:last").find("a.mpages").find("span").html())
					 
					msgBox("消息提示","已经是最后一页");
				else if($("#"+pageid).find(".pageol").find("li").find("a.mpages").parent().next().find("p").length==0)
					$($("#"+pageid).find(".pageol").find("li").find("a.mpages")).parent().next().click();
				else{
					var pagesHtml='<li><a href="#1"><div><span>1</span></div></a></li><li><p class="slh">...<p></li>';
					var maxPage=parseInt($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html())+9;
					maxPage=parseInt(maxPage)>=parseInt($("#"+pageid).find(".pageol").find("li:last-child").find("span").html())?parseInt($("#"+pageid).find(".pageol").find("li:last-child").find("span").html()):maxPage;
					maxPage+=1;
					var start=parseInt($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html())+1;
					start=start>parseInt($("#"+pageid).find(".pageol").find("li:last-child").find("span").html())-8?(parseInt($("#"+pageid).find(".pageol").find("li:last-child").find("span").html())-8):start;
					for(var i=start;i<maxPage;i++)
						pagesHtml=pagesHtml.concat('<li><a href="#1"'+(i==(parseInt($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html())+1)?'	class="mpages"':'')+'><div><span>'+i+'</span></div></a></li>');
					if(parseInt($("#"+pageid).find(".pageol").find("li").find("a.mpages").find("span").html())<parseInt($("#"+pageid).find(".pageol").find("li:last-child").find("span").html())-9)
						pagesHtml=pagesHtml.concat('<li><p class="slh">...<p></li><li><a href="#1"><div><span>'+$("#"+pageid).find(".pageol").find("li:last-child").find("span").html()+'</span></div></a></li>');
					$("#"+pageid).find(".pageol").find("li").remove();
					$("#"+pageid).find(".pageol").append(pagesHtml);
					pagesEventBind(pageClick,pageid);
				}
//			}
		})
		$("#"+pageid).find("a:last").unbind("click");
		$("#"+pageid).find("a:last").bind("click",function(){
			goPage(this,pageid);
		});
	}

	function goPage(param,_pageid){
		var pageid=isNotNull(_pageid)?_pageid:"pageInfo";
		var page=$(param).prev().val();
		if(!/^[1-9]\d*$/.test(page)){
			 
			msgBox("消息提示","请输入大于0的整数!");
			
		}else{
			var maxPage=parseInt($("#"+pageid).find(".pageol").find("li:last-child").find("span").html());
			if(page<1||page>maxPage){ 
				msgBox("消息提示","请输入1~"+maxPage+"之间的整数!");
			}else{
				var is_show=0;
				$.each($("#"+pageid).find(".pageol").find("li").find("span"),function(idx,span){
					if(parseInt($(span).html())==page){
						$(span).parent().parent().parent().click();
						is_show=1;
						return false;
					}
				})
				if(is_show==1)
					return;
				var pagesHtml='';
				if(page<10){
					for(var i=1;i<10;i++)
						pagesHtml=pagesHtml.concat('<li><a href="#1"><div><span>'+i+'</span></div></a></li>');
					pagesHtml=pagesHtml.concat('<li><p class="slh">...<p></li><li><a href="#1"><div><span>'+maxPage+'</span></div></a></li>');
				}else if(page>maxPage-9){
					pagesHtml=pagesHtml.concat('<li><a href="#1"><div><span>1</span></div></a></li><li><p class="slh">...<p></li>');
					for(var i=maxPage-8;i<=maxPage;i++)
						pagesHtml=pagesHtml.concat('<li><a href="#1"><div><span>'+i+'</span></div></a></li>');
				}else{
					pagesHtml=pagesHtml.concat('<li><a href="#1"><div><span>1</span></div></a></li><li><p class="slh">...<p></li>');
					for(var i=page;i<page+9;i++)
						pagesHtml=pagesHtml.concat('<li><a href="#1"><div><span>'+i+'</span></div></a></li>');
					if(maxPage==page+9)
						pagesHtml=pagesHtml.concat('<li><a href="#1"><div><span>'+maxPage+'</span></div></a></li>');
					else
						pagesHtml=pagesHtml.concat('<li><p class="slh">...<p></li><li><a href="#1"><div><span>'+maxPage+'</span></div></a></li>');
				}
				$("#"+pageid).find(".pageol").find("li").remove();
				$("#"+pageid).find(".pageol").append(pagesHtml);
				pagesEventBind(pageClick,pageid);
				$.each($("#"+pageid).find(".pageol").find("li").find("span"),function(idx,span){
					if(parseInt($(span).html())==page){
						$(span).parent().parent().parent().click();
						return false;
					}
				})
		  }
		}
	}
	/*********************************************/
	
	/**********************校验上传文件类型和大小(GaoYang)*************************/
	//判断元素是否为空
	function isNotNull(obj){
		if(obj==null||obj==''||obj==undefined){
			return false;
		}else{
			return true;
		}
	};
	/**
	 * file:被检验的文件
	 * chks:检验的条件:包括的类型filetypes和大小filemaxsize
	 * chks参数形式如:chks={"filetypes":[".jpg",".png",".rar",".txt",".zip",".doc",".ppt",".xls",".pdf",".docx",".xlsx"],"filemaxsize":2048}
	 * 函数返回值:0代表文件不存在;1代表文件类型不正确;2代表文件大小为0,即为空文件;3代表文件大小超出范围;
	 */
	function checkFile(file,chks){
		var rsvalues={
				FileNotExists:0,
				TypeError:1,
				SizeNull:2,
				OutSize:3
		};
		if(!isNotNull(file)){
			return rsvalues.FileNotExists;
		}else{
			if(isNotNull(chks)){
				var filesize = 0; 
				var filetypes =chks.filetypes; 
				var filename = file.name; 
				var filemaxsize = chks.filemaxsize;
				//文件类型校验
				if(isNotNull(filename)){
					var isflag=false;
					//获取文件扩展名
					var extensionName = filename.substring(filename.lastIndexOf("."),filename.length).toLowerCase();
					if(isNotNull(filetypes) && filetypes.length>0){ 
						for(var t in filetypes){
							if(filetypes[t]==extensionName){
								isflag=true;
								break;
							}
						}
					}
					if(!isflag){
						return rsvalues.TypeError;
					}
				}
				//文件大小校验
				var filesize  = file.size;
				if(filesize<=0){
					return rsvalues.SizeNull;
				}else if(filesize>filemaxsize){
					return rsvalues.OutSize;
				}
			}
		}
	}
	
	
	/**
	 * 向浏览器追加trim方法
	 */
	if(!String.prototype.trim){ 
	String.prototype.trim = function () {
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
	}
	}
	
	
	
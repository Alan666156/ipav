<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ipav"   uri="ipav" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<%-- <%@include file="/pages/system/common/head.jsp" %> --%>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/zTreeStyle.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript" src="/pages/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/pages/js/common.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/pages/js/tipswindown.js"></script>
<script type="text/javascript" src="/pages/js/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="/pages/js/zeroclipboard-2.1.6/dist/ZeroClipboard.min.js"></script>
<SCRIPT type="text/javascript">
 
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
			$("#citySel").attr("value",v);
			$("#orgid").attr("value",id);
		}

		function showMenu() {
			var cityObj = $("#citySel");
			var cityOffset = $("#citySel").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		
		$().ready(function(){
			var code = "companyid=" + '<c:out value="${curuser.companyid}"/>'; 
			$.ajax({ type:"POST", url:"/showOrgTrees", 
				data:code, success:function(arr){
					$.fn.zTree.init($("#treeDemo"), setting, eval(arr));
				} });
			
			$(window).resize();
			
		} );
		//禁用 启用
		function setUserFlg(val){ 
			 
			var cout =0;
			$('input[name="userids"]:checked').each(function(){
				cout =cout+1;
			});
			if(cout == 0){
				/* $("#tbBoxgdt2").find("p").html("请选择用户！");
				showjg12(); */
				msgBox("提示信息","请选择用户！");
				return false ;
			}
			$("#userfrom").attr("action","/setuserVal");
			
			$("#myvalflg").attr("value",val);
			
			$("#userfrom").submit();
			
		/* 	$.post("/setuserVal",{"userid":id,"myvalflg":val},function(data){
				search(1);
			}); */
		}
		
		function delUsers(){
			var cout =0;
			$('input[name="userids"]:checked').each(function(){
				cout =cout+1;
			});
			if(cout == 0){
				/* $("#tbBoxgdt2").find("p").html("请选择用户！");
				showjg12(); */
				msgBox("提示信息","请选择用户！");
				return false ;
			}
			
			
			$(".alertbut").show();
			/* $("#tbBoxgdt2").find("p").html("是否确认删除所选用户！");
			showjg12(); */
			msgDialog({"title":"提示信息","centent":"是否确认删除所选用户！","okfunction":delUsersOK});
		 
		}
		function delUsersOK(){
			$("#userfrom").attr("action","/delUsers");
			$("#userfrom").submit();
		}
		//修改用户
		function updateUser(){
			var cout = 0;
			var userid ="";
			$('input[name="userids"]:checked').each(function(){
				cout = cout+1;
				userid = $(this).attr("value");
			});
			if(cout == 0){
				/* $("#tbBoxgdt2").find("p").html("请选择用户！");
				showjg12(); */
				msgBox("提示信息","请选择用户！");
				return  false ;
			}
			if(cout > 1){
			/* 	$("#tbBoxgdt2").find("p").html("不可同时修改多个用户,请选择一个用户！");
				showjg12(); */
				msgBox("提示信息","不可同时修改多个用户,请选择一个用户！");
				return false ;
			}
			if(cout == 1){
				getUpdateUserInfo(userid);
			}
			
		}
		
		function checkAll(){
			if( $("#allids").attr("checked")=="checked"){
				$('input[name="userids"]').each(function(){
					$(this).attr("checked",'true');
				});
			}else{
				$('input[name="userids"]').each(function(){
					$(this).removeAttr("checked");
				});
			}
		}
		/**
		 * 导出模板
		 */
		function exportExcel(oper){
			if(oper == "user"){
				var orgid = $("#orgid").val();
				var param="oper="+oper;
				if($("#orgid").val()!='')
					param+='&orgid='+$("#orgid").val();
				if($("#username").val()!='')
					param+='&userName='+$("#username").val();
				if($("#valflg").val()!='-1')
					param+='&valflg='+$("#valflg").val();
				window.open("/userExport?"+param);
				//window.open("/userExport?orgid="+orgid+"&oper="+oper);
			}else{
				window.open("/userExport");
			}
		}
		
		function  showdiv(){
			$("#simScrollCont1").show();
			$("#simScrollContBox1").show();	
		}
		function  hidediv(){
			$("#simScrollContBox1").hide();
			$("#simScrollCont1").hide();	

		}
		
		function hidediv2(){
			$("#simScrollContBox2").hide();
			$("#simScrollCont2").hide();
		}
		function  showdiv2(){
			$("#simScrollCont2").show();
			$("#simScrollContBox2").show();	
		}
		
		$(window).resize(function(){
			$(".simScrollContBox4").css({
		        position: "absolute",
		        left: ($(window).width() - $(".simScrollContBox4").outerWidth()) / 2,
		        top: ($(window).height() - $(".simScrollContBox4").outerHeight()) / 2
		    });
			
			$(".simScrollContBox").css({position:"absolute",
				left:($(window).width()-$(".simScrollContBox").outerWidth()-200)/2,
				top:($(window).height()-$(".simScrollContBox").outerHeight()-100)/2});
			$(".simaltBoxt9").css({position:"absolute",
				left:($(window).width()-$(".simaltBoxt9").outerWidth()-200)/2,
				top:($(window).height()-$(".simaltBoxt9").outerHeight()-100)/2});
			var client = new ZeroClipboard($("#copyButton"));
			client.on("copy",function(e){
				 var content="";
					$.each($("#excelWarnings").find('tr'),function(idxTr,tr){
						$.each($(tr).find('th'),function(idxTd,td){
							if(idxTd==1)
									content+="          ";
							content+=$(td).text()
						})
						
						$.each($(tr).find('td'),function(idxTd,td){
							if(idxTd==1)
								content+="              ".substr((idxTr+"").length);
							content+=$(td).text()
						}) 
						content+="\r\n";
					})
				e.clipboardData.setData("text/plain",content);
			});
		});

	 function onloasimBox(){
		var winhight = document.documentElement.clientHeight;
	    document.getElementById("simScrollCont1").style.height=winhight+"px";
		}
		
		/**
		 * 导入员工
		 */
		function importExcel(){
			$("#excelWarnings").find("tr").not(":first").remove();
				$.ajaxFileUpload({
		            url: 'userImport', 
		            type: 'post',
		            secureuri: false, //一般设置为false
		            fileElementId: 'userInfos', // 上传文件的id、name属性名
		            dataType: 'json', //返回值类型
		            data:null,
		            success: function(data, status){  
		            	if(data.validateInfo.err!=undefined){
		            		/* $("#warnning").html(data.err);
		            		showjg12(); */
		            		msgBox("提示信息",data.err);
		            	}else{ 
		            		if(data.validateInfo.warningList!=undefined&&data.validateInfo.warningList.length>0){
			            		$.each(data.validateInfo.warningList,function(idx,warninginfo){
			            			$("#excelWarnings").append('<tr>'
			            					+'<td>'+warninginfo[0]+'</td>'
			            					+'<td>'+warninginfo[1]+'</td>'
			            					+'<td>'+warninginfo[2]+'</td>'
			            					+'<td>'+warninginfo[3]+'</td>'
			            					/* +'<td>'+warninginfo[4]+'</td>'
			            					+'<td>'+warninginfo[5]+'</td>'
			            					+'<td>'+warninginfo[6]+'</td>'
			            					+'<td>'+warninginfo[7]+'</td>'
			            					+'<td>'+warninginfo[8]+'</td>'
			            					+'<td>'+warninginfo[9]+'</td>'
			            					+'<td>'+warninginfo[10]+'</td>'
			            					+'<td>'+warninginfo[11]+'</td>' */
			            					+'</tr>');
			            		});
			            	}else if(data.validateInfo.existsList!=undefined&&data.validateInfo.existsList.length>0){
			            		$.each(data.validateInfo.existsList,function(idx,existsinfo){
			            			$("#excelWarnings").append('<tr>'
			            					+'<td>'+existsinfo[0]+'</td>'
			            					+'<td>'+existsinfo[1]+'</td>'
			            					+'<td>'+existsinfo[2]+'</td>'
			            					+'<td>'+existsinfo[3]+'</td>'
			            					/* +'<td>'+existsinfo[4]+'</td>'
			            					+'<td>'+existsinfo[5]+'</td>'
			            					+'<td>'+existsinfo[6]+'</td>'
			            					+'<td>'+existsinfo[7]+'</td>'
			            					+'<td>'+existsinfo[8]+'</td>'
			            					+'<td>'+existsinfo[9]+'</td>'
			            					+'<td>'+existsinfo[10]+'</td>'
			            					+'<td>'+existsinfo[11]+'</td>' */
			            					+'</tr>');
			            		});
			            	}
		            		pageClick();
		            		if($("#excelWarnings").find("tr").not(":first").length>0)
		            			 showtsBoxt9();
		            		else{
		            			hidediv();
		            		}
		            	}
		            },
		            error: function(data, status, e){ 
		            	if(data.err!=undefined){
		            		/* $("#warnning").html(data.err);
		            		showjg12(); */
		            		msgBox("提示信息",data.err);
		            	}else{ 
		            		if(data.warningList!=undefined&&data.warningList.length>0){
			            		$.each(data.warningList,function(idx,warninginfo){
			            			$("#excelWarnings").append('<tr>'
			            					+'<td>'+warninginfo[0]+'</td>'
			            					+'<td>'+warninginfo[1]+'</td>'
			            					+'<td>'+warninginfo[2]+'</td>'
			            					+'<td>'+warninginfo[3]+'</td>'
			            					/* +'<td>'+warninginfo[4]+'</td>'
			            					+'<td>'+warninginfo[5]+'</td>'
			            					+'<td>'+warninginfo[6]+'</td>'
			            					+'<td>'+warninginfo[7]+'</td>'
			            					+'<td>'+warninginfo[8]+'</td>'
			            					+'<td>'+warninginfo[9]+'</td>'
			            					+'<td>'+warninginfo[10]+'</td>'
			            					+'<td>'+warninginfo[11]+'</td>' */
			            					+'</tr>');
			            		});
			            	}else if(data.existsList!=undefined&&data.existsList.length>0){
			            		$.each(data.existsList,function(idx,existsinfo){
			            			$("#excelWarnings").append('<tr>'
			            					+'<td>'+existsinfo[0]+'</td>'
			            					+'<td>'+existsinfo[1]+'</td>'
			            					+'<td>'+existsinfo[2]+'</td>'
			            					+'<td>'+existsinfo[3]+'</td>'
			            					/* +'<td>'+existsinfo[4]+'</td>'
			            					+'<td>'+existsinfo[5]+'</td>'
			            					+'<td>'+existsinfo[6]+'</td>'
			            					+'<td>'+existsinfo[7]+'</td>'
			            					+'<td>'+existsinfo[8]+'</td>'
			            					+'<td>'+existsinfo[9]+'</td>'
			            					+'<td>'+existsinfo[10]+'</td>'
			            					+'<td>'+existsinfo[11]+'</td>' */
			            					+'</tr>');
			            		});
			            	}
		            		pageClick();
		            		if($("#excelWarnings").find("tr").not(":first").length>0)
		            			 showtsBoxt9();
		            		else{
		            			hidediv();
		            		}
		            	}
		            }
		        });
		}
		
		function pageClick(){
			search($(".pageol").find("li").find("a.mpages").find("span").html())
		}
		
		function search(currpage){
			var param="&page="+(parseInt($("#pageol").find("a.mpages").find("span").html())-1)*10+"&pageSize=10";
			if($("#orgid").val()!=""&&$("#orgid").val()!="0")
				param+='&orgid='+$("#orgid").val();
			if($("#username").val()!="")
				param+='&username='+$("#username").val();
			if($("#valflg").val()!='-1')
				param+='&valflg='+$("#valflg").val();
			$("#senfe").find("tr").not(":first").remove();
			$.ajax({
				url:"userinfos",
				type:"POST",
				data:param,
				success:function(data){
					if(data.counts!=undefined){
						initPages(currpage,data.counts,10,pageClick);
						$.each(data.userlist,function(idx,userinfo){
							$("#senfe").append('<tr><td align="left"><input type="checkbox" name="userids" value="'+userinfo.userid+'">&nbsp;&nbsp;'+(idx+1)+'</td>'
							+'<td>'+userinfo.userno+'</td>'
							+'<td><a href="#1" style="text-decoration: none;" onclick="showUserInfo(\''+userinfo.userid+'\')">'+userinfo.username+'</a></td>'
							+'<td>'+(userinfo.sex==0?'女':(userinfo.sex==1?'男':''))+'</td>'
							+'<td>'+(userinfo.orgname==undefined?"":userinfo.orgname)+'</td>'
							+'<td>'+(userinfo.regtype=="M"?userinfo.mobile:userinfo.email)+'</td>'
							+'<td>'+userinfo.email+'</td>'
							+'<td>'+userinfo.mobile+'</td>'
							+'<td>'+(userinfo.valflg==1?'启用':'禁用')+'</td>'
							+'<td>'+(userinfo.directorName==undefined?"":userinfo.directorName)+'</td>'
							+'</tr>')
						});
						
					}
				} 
			});
		}
		
		function getUpdateUserInfo(id){
			location.href='/gotomodifyuser?oper=update&userid='+id;
		}
		
		function handleInOut(param){
			$(param).hover(
				function () {
					$(this).addClass("kedbutm");
				},
				function () {
					$(this).addClass("kedbut");
				}
			)
		}
		
		function showUserInfo(id){
			location.href="/gotomodifyuser?oper=sel&userid="+id;
		}
		
		function showtsBoxt9(){
			$("#simalpha9").show();
			$("#simaltBox9").show();	
		}
		function hidetsBoxt9(){
			$("#simalpha9").hide();
			$("#simaltBox9").hide();
		}
		
		function showjg12(){
			$("#simScrollCont12").show();
			$("#simScrollContBox12").show();	
		}
		function hidejg12(){
			$("#simScrollContBox12").hide();
			$("#simScrollCont12").hide();
			$(".alertbut").hide();
		}
		
		$(document).ready(function(){
			search(1);
		})
		
		/** 统一树插件显示选择机构 **/
		function showOrgDiv(){
			new openCommonView({title:'机构选择',setting:{callback: {onClick:onClickOrg}},show_user:false});	
		}
		function onClickOrg(event, treeId, treeNode){
			$("#citySel").val("");
			$("#orgid").val("");
			if(treeNode.level>0){
				$("#citySel").val(treeNode.name);
				$("#orgid").val(treeNode.id);
			}
			$("#close_img").click();
		}
</SCRIPT>
</head>

<body onload="onloasimBox()">

<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>

<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">员工维护</a></li>
    </ol>
</div>
<form action="/userlist" name="userfrom" method="post" id="userfrom">
<input type="hidden" name="companyid" value="">
<input type="hidden" name="userid" id="userid" >
<input type="hidden" name="myvalflg" id="myvalflg" >

<div class="tabBox serchBox">
<ol>
	<li>机构：
<!-- 		<input type="text" class="sertext gdnr" id="citySel" readonly="readonly" name="showchoice" onclick="showMenu();"> -->
		<input type="text" class="sertext gdnr" id="citySel" readonly="readonly" name="showchoice" onclick="showOrgDiv();">
		<input type="hidden" name="orgid" id="orgid">
	</li>
    <li>姓名：<input type="text" class="sertext" id="username" name="username"></li>
    <li>启用/停用：
    <select class="serchse" id="valflg" name="valflg">
    	<option value="1">启用</option>
    	<option value="0">禁用</option>
    	<option value="-1">全部</option>
    </select>
	<li><input type="button" style="margin-left:20px;" value="查询" onclick="search(1)" class="serch92" ></li>
</ol>
</div>
<div class="toolBox" style="margin-top:5px; width:97.5%; margin-left:15px;">
	<a href="#1" class="cur8" onClick="location.href='/gotoadduser?companyid=<c:out value ='${curuser.companyid }'/>'">新增员工</a>
	<a href="#1" class="cur3" onClick="updateUser()">修改</a>
	<a href="#1" class="cur4" onClick="delUsers()">删除</a>
	
	<a href="#1" class="cur9" onClick="setUserFlg(1)">启用</a>
	<a href="#1" class="cur10" onClick="setUserFlg(0)">禁用</a>
	
	<a href="#1" class="curs22" onclick="showdiv();">员工导入</a>
	<a href="#1" class="curs22" onclick="exportExcel('user');">员工导出</a>
	<a href="#1" class="cur7" onclick="exportExcel('');">模板下载</a>
</div>

<div class="tabBox tablbnr mt" style=" margin-top:10px;">
	<table class="tabinfo" id="senfe" width="100%" cellpadding="0" cellspacing="0">
      <tr class="tabtitle">
        <td width="60" align="left"><input type="checkbox" id="allids"  onclick="checkAll();"> 序号</td>
        <td>员工编号</td>
        <td>姓名</td>
        <td>性别</td>
        <td>机构名称</td>
        <td>用户注册信息</td>
        <td>Email</td>
        <td>手机</td>
        <td>用工状态</td>
        <td>直接主管</td>
<!--         <td width="210">操作</td> -->
      </tr>
     </table>
	<%-- <table class="tabinfo" id="senfe" width="100%" cellpadding="0" cellspacing="0">
  
      <c:if test="${!empty userlist}">
	     <c:forEach items="${userlist }" var="user" varStatus="step"> 	
	      <tr>
	      	<td align="left">
	      		<c:if test="${user.userid ne curuser.userid }"><input type="checkbox" name="userids" value="${user.userid}"></c:if>
	      		&nbsp;&nbsp;<c:out value="${step.index+1 }"/>
	      	</td>
	        <td><c:out value="${user.userno }"/></td>
	        <td><c:out value="${user.username }"/></td>
	        <td><ipav:convert type="sex" value="${user.sex}"/>
	        </td>
	        <td title="${user.orgfullname}"><c:out value="${user.orgname }"/></td>
	        <td><ipav:convert type="regtype" value="${user.regtype}"/></td>
	        <td><c:out value="${user.email }"/></td>
	        <td><c:out value="${user.mobile }"/></td>
	        <td><ipav:convert type="valflg" value="${user.valflg}"/></td>
	        <td>
	        	<c:choose>
	        		<c:when test="${user.valflg eq '1'}">
	        			<c:if test="${user.userid ne curuser.userid }">
						<input type="button" value="禁用" class="kedbut" onmouseover="this.className='kedbutm'" 
						onmouseout="this.className='kedbut'" onclick="setUserFlg('<c:out value="${user.userid }"/>','0')">&nbsp;
						<input type="button" value="修改" id="up<c:out value="${step.index}"/>" class="kedbut" onmouseover="this.className='kedbutm'" 
	           			 onmouseout="this.className='kedbut'" onclick="location.href='/gotomodifyuser?oper=update&userid=<c:out value ='${user.userid }'/>'">&nbsp;
	           			 </c:if>
					</c:when>
	        		<c:when test="${user.valflg eq '0'}">
	        			<c:if test="${user.userid ne curuser.userid }">
						<input type="button" value="启用" class="kedbut" onmouseover="this.className='kedbutm'" 
						onmouseout="this.className='kedbut'"  onclick="setUserFlg('<c:out value="${user.userid }"/>','1')">&nbsp;
						<input type="button" value="修改" class="bkedbut">&nbsp;
						</c:if>
					</c:when>
	        		<c:otherwise></c:otherwise>
	        	</c:choose>
	            
	            <input type="button" value="查看" class="kedbut" onmouseover="this.className='kedbutm'" 
	            onmouseout="this.className='kedbut'" onclick="location.href='/gotomodifyuser?oper=sel&userid=<c:out value ='${user.userid }'/>'">
	        </td>
	      </tr>
	      </c:forEach>
      </c:if>
    </table> --%>
</div>

<div class="butpageBox tabBox ht80" >
    
    <div id="pageInfo">
                <div class="rightpagebox fr">
	                <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
	                <a href="#1"><div class="pageys"><span>上一页</span></div></a>
	                <ol id="pageol" class="pageol">
	                    <li><a href="#1" class="mpages" onClick="set_menu4(0)"><div><span>1</span></div></a></li>
	                    <li><a href="#1" onClick="set_menu4(1)"><div><span>2</span></div></a></li>
	                    <li><a href="#1" onClick="set_menu4(2)"><div><span>3</span></div></a></li>
	                    <li><p class="slh">...<p></li>
	                    <li><a href="#1"><div class="pageysn" onClick="set_menu4(3)"><span>10</span></div></a></li>
	                </ol>
	                <a href="#1"><div class="pageys"><span>下一页</span></div></a>
	                <input type="text" class="gopage">
	                <a href="#1"><div class="pageys"><span>GO</span></div></a>
                </div>
     </div>
    
    
</div>

<div id="simScrollCont2" class="simScrollCont" style="display: none">
</div>
	<div class="simScrollContBox" id="simScrollContBox2" style="display: none">
    	<div class="simScrollContBox2">
        	<div class="tctitle">员工数据导出<a href="#1" onClick="hidediv2()" title="关闭"></a></div>
            <table width="100%" class="qytable mt40" border="0">
              <tr height="60">
                <td width="110" align="right"><label>选择机构：</label></td>
                <td width="280">
                	<select class="sytext3">
                        <option>天道启科</option>
                        <option></option>
                    </select>
                </td>
              </tr>
             
              <tr height="100">
                <td>&nbsp;</td>
                <td>
                <input style="margin-right:30px;"  onClick="hidediv2()" type="submit" value="导出" class="butl100" 
                onmouseover="this.className='butl100m'" onmouseout="this.className='butl100'">
       			<input type="submit" value="取消" onClick="hidediv2()" class="buth100" onmouseover="this.className='buth100m'" 
       			onmouseout="this.className='buth100'">

                </td>
              </tr>
            </table>

        </div>
 </div>

<div id="simScrollCont1" class="simScrollCont" style="display: none;">
</div> 
<div class="simScrollContBox" id="simScrollContBox1" style="display: none;">
    	<div class="simScrollContBox2">
        	<div class="tctitle">员工数据导入<a href="#1" onClick="hidediv()" title="关闭"></a></div>
            <table width="100%" class="qytable mt30" border="0">
              <tr height="60">
                <td width="110" align="right"><label>选择文件：</label></td>
                <td width="280"><input type="text" class="sytext3" id="textfilename" ></td>
                <td>
                <input type="button" value="浏览..." class="buth100 ">
                <input type="file" id="userInfos" name="userInfoFile" style=" width:100px; height:40px; float: left; position: absolute; margin-left: -100px;filter: alpha(opacity : 0); opacity: 0; " onchange="document.getElementById('textfilename').value=this.value"></td>
              </tr>
              <tr height="20">
                <td></td>
                <td><p class="lsmsg" style=" margin-left:0px;">备注：导入后系统会为员工生成用户账号及密码发送至员工手机或邮箱中</p></td>
                <td></td>
              </tr>
              <tr height="100">
                <td>&nbsp;</td>
                <td colspan="2">
                       	<input style="margin-right:30px;"  onClick="importExcel()" type="button" value="导入" class="butl100" onmouseover="this.className='butl100m'" 
                       	onmouseout="this.className='butl100'">
       					 <input type="button" value="取消" onClick="hidediv()" class="buth100" onmouseover="this.className='buth100m'" 
       					 onmouseout="this.className='buth100'">
                </td>
              </tr>
            </table>

        </div>
</div>

<!--导入错误提示 begin-->
<div id="simalpha9" class="simalpha">
</div>
<div class="simaltBoxt9" id="simaltBox9">
	<div class="simaltBoxst9">
		<div class="tctitle" style="width: 800px;">错误提示<a href="#1" onClick="hidetsBoxt9()" title="关闭"></a></div>
 		
		<div class="userdreroor">
        		<table id="excelWarnings" class="tab2015" width="150%" cellpadding="0" cellspacing="0">
                  <tr id="tab2015tr">
                    <td>员工姓名</td>
                    <td>用户注册信息</td>
                   <!--  <td>部门</td>
                    <td>职务</td>
                    <td>是否部门最高职务</td>
                    <td>兼职部门及职务</td> -->
                    <td>手机号码</td>
                    <td>电子邮箱</td>
                    <!-- <td>所属工作平台</td>
                    <td>工作电话</td>
                    <td>办公地址</td>
                    <td>劳动合同签订单位</td> -->
                  </tr>
				</table>
        </div>
        
		
		<div class="jgtcbutBox" align="center">
        	<input type="button" class="butl120m mt15" value="复制错误信息" id="copyButton" />
        	<input type="button" class="yes ml30" value="确定" onClick="hidetsBoxt9()">
            <input type="button" class="no mt15 ml30" value="取消" onClick="hidetsBoxt9()">
        </div>

	</div>
</div>
<!--导入错误提示 end-->

<div id="simScrollCont12" class="simScrollCont">
</div>

<div class="simScrollContBox4" id="simScrollContBox12">
   	<div class="simScrollContBoxs4">
       <div class="tctitle">提示信息<a href="#1" onClick="hidejg12()" title="关闭"></a></div>
       
     	<div class="ptx"></div>
       <div id="tbBoxgdt2" class="alertBox" align="center">
       	<p id="warnning" class="alertmsg">XXX，dafadsfadf你即将修改XXXX的个人信息，请慎重修改，不然你就联系不到他了！</p>
       </div>
       <div class="alertbut"  style="display: none;">
           <input type="button" value="否" class="no fr mr20"  onClick="hidejg12()">
       	<input type="button" value="是" class="yes fr mr20" onClick="hidejg12();delUsersOK();">
       </div>

     </div>
</div>
</body>
</html>

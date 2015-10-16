<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css"/>
<title>系统管理</title>
<style type="text/css">
.simScrollContaaa{z-index:1000000; position:absolute;width:100%;height:100%;background:#000;top:0;left:0;filter:alpha(Opacity=50);-moz-opacity:0;opacity:0.4;}
</style>
<script type="text/javascript">
		var trees ;
		$().ready(function(){
			var roleno ='<c:out value="${role.roleno}" />';
			var code = "roleno="+roleno; 
			$.ajax({ type:"POST", url:"/getMenutreeByRole", 
				data:code, success:function(arr){
					trees = eval(arr);
					showRoles();
				} });
			
		} );
		var row_count = 0; 
		function showRoles(){
			var tab = $("#menulist");
			var ftr = $("<tr></tr>");
			var ftd = $("<td></td>");
			ftd.attr("width","100");
			ftd.attr("valign","top");
			ftd.append('<h3 class="lsmsg" style="margin-left:0px;">角色权限</h3>');
			ftr.append(ftd);
			tab.append(ftr);
			row_count++;
			$.each(trees,function(n,value){
				if(value.id != 0){
					if(value.Pid ==0){
						var row = $("<tr></tr>");
						row.attr("valign","top");
						var td = $("<td></td>");
						var conte ="<p>"+value.name+"</p>";
						td.append(conte); 
						row.append(td);
						if(value.open == "true"){
							var td3 = $("<td></td>");
							row.append(td3);
							earchtd(value.id,row,td3);
						}
						tab.append(row);
						row_count++; 
					}
				}
				
			});
		}
		function earchtd(pid,row,td){
			$.each(trees,function(i,obj){
				if(obj.Pid == pid){
					var tbs = $("<table></table>");
					var rr =$("<tr></tr>");
					rr.attr("valign","top");
					var dd =$("<td></td>");
					var temp = "<p>"+obj.name+"</p>";
					dd.append(temp);
					rr.append(dd);
					tbs.append(rr);
					td.append(tbs);
					if(obj.open == "true"){
						 var tdd =$("<td></td>");
						 earchtd(obj.id,rr,tdd);
					}
					
				}
			});
			row.append(td);
		}
		
		function delRole(roleno){
			msgDialog({"title":"提示信息","centent":"确认删除?","okfunction":delRoleAfter,"param":[roleno]});
		}
		function delRoleAfter(roleno){
			 
			var code = "roleno="+roleno; 
			$.ajax({ type:"POST", url:"/delRole", 
				data:code, success:function(obj){
					parent.parent.topFrame.flushView();
			} });
		}
		
		 
		
		function onloadjgwh(){
			var winhight = document.documentElement.clientHeight;
			var jghet = winhight-90;
			$(".jslistBoxs").height(winhight-200)
			}
		window.onload=onloadjgwh;
		
		var pageSize=2;
		function initUsers(){
			tab('rcxs',1,2);
			$("#pageInfo").find("input[class='gopage']").val("");
			$("#pageol").find("a.mpages").find("span").html("1");
			queryUsers();
		}
		function queryUsers(){
			var startRow=parseInt($("#pageol").find("a.mpages").find("span").html());
			startRow=isNaN(startRow)?1:startRow;
			var data = {
					"roleno":"<c:out value='${role.roleno}' />",
					"page":(startRow-1)*pageSize,
					"pageSize":pageSize
			};
			$.post("/queryRoleUsers", data, function(arr) {
				var count=0;
				if(isNotNull(arr)){
					createUsersHtml(arr.list,arr.size);
					count=arr.size;
				}
				//初始化分页
				initPages($(".pageol").find("li").find("a.mpages").find("span").html(),count, pageSize, queryUsers);
			});
		}
		function createUsersHtml(list,count){
			var userHTML="";
			$.each(list,function(i,v){
				userHTML+="<tr><td>"+(i+1)+"</td><td>"+v.username+"</td><td>"+v.rolename+"</td><td>"+v.orgname +"</td></tr>";
			});
			$("#userbody").html(userHTML);
		}
		
		$(document).ready(function(){
			if($("#flag").val()!='')
				$("#leftFrame",window.parent.document).attr("src","/system/left?op=ROLE");
		});
</script>
</head>
<body style="position: inherit;">
<input type="hidden" id="flag" value="${flag}"/>
<p class=" ml20 mt15"><b>角色名称：</b><span><c:out value="${role.rolename }"/></span></p>
<p class=" ml20 mt5"><b>角色描述：</b><span><c:out value="${role.mark }"/></span></p>
<div class="toolBox" style="margin-top:5px; width:98%; margin-left:15px;">
	<a href="#1" class="cur8" onClick="location.href='/gotoaddRole?roleno=<c:out value='${role.roleno }'/>'">新增角色</a>
	<c:if test="${role.sysroleflg ne 'Y'}">
		<a href="#1" class="cur3" onClick="location.href='/gotoupdateRole?companyid=<c:out value ='${curuser.companyid }'/>&roleno=<c:out value ='${role.roleno }'/>'">修改</a>
		<a href="#1" class="cur4" onClick="delRole('<c:out value ='${role.roleno }'/>')">删除</a>
	</c:if>
</div>
<div class="centmenudiv mt15">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="tab('rcxs',0,2)" class="current" id="rcxs_title_0" title="角色维护">角色维护</a></li>
        <li><a href="#1" onClick="initUsers()" id="rcxs_title_1" title="查看人员">查看人员</a></li>
    </ol>
</div>

<div class="jslistBox" id="rcxs_main_0" >
		<div class="jslistlBox jslistBoxs">
			<table class="qxtab mt20 ml20" border="0" id="menulist" cellpadding="0" cellspacing="0">
		    </table>
		</div> 	
</div>

<div class="jslistBox dn" id="rcxs_main_1">
	<table class="tabinfo" id="senfe" width="100%" cellpadding="0" cellspacing="0">
      <tr class="tabtitle">
        <td width="50">序号</td>
        <td>姓名</td>
        <td>角色名称</td>
        <td>所属机构</td>
      </tr>
      <tbody id="userbody"></tbody>
<%--       <c:if test="${!empty userlist}"> --%>
<%--       		<c:forEach items="${userlist }" var="user" varStatus="item"> --%>
<!--       		 <tr> -->
<%-- 		        <td><c:out value="${item.index+1 }"/></td> --%>
<%-- 		        <td><c:out value="${user.username }"/></td> --%>
<%-- 		        <td><c:out value="${user.rolename }"/></td> --%>
<%-- 		        <td><ipav:orgfullname orgid="${user.orgid }"/></td> --%>
<!-- 		      </tr> -->
<%--       		</c:forEach> --%>
<%--       </c:if> --%>
    </table>
    
<div class="ptpageBox" id="pageInfo" style="width:97.5%; margin-left:15px;">	
    <div class="rightpagebox fr">
	  <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
          <a href="javascript:;"><div class="pageys"><span>上一页</span></div></a>
          <ol id="pageol" class="pageol">
              <li><a href="javascript:;" class="mpages" onClick="set_menu4(0)"><div><span>1</span></div></a></li>
              <li><a href="javascript:;" onClick="set_menu4(1)"><div><span>2</span></div></a></li>
              <li><a href="javascript:;" onClick="set_menu4(2)"><div><span>3</span></div></a></li>
              <li><p class="slh">...<p></li>
              <li><a href="javascript:;"><div class="pageysn" onClick="set_menu4(3)"><span>10</span></div></a></li>
          </ol>
          <a href="javascript:;"><div class="pageys"><span>下一页</span></div></a>
          <input type="text" class="gopage">
          <a href="javascript:;"><div class="pageys"><span>GO</span></div></a>
    </div>
</div>
 

</body>
</html>

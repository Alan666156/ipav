<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<title>系统管理</title>
	<SCRIPT type="text/javascript">
		
		var setting = {
			view: {
				dblClickExpand: dblClickExpand
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
			callback:{
				beforeClick: function(treeId, treeNode) {//点击前的函数
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.expandNode(treeNode);
					if (treeNode.isParent) {
						return true;
					} else {
						return true;
					}
				},
				onClick:function(e,treeId,treeNode){
					selectMenus(treeNode.id);
				}
			}
		};

		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}
		
		function selectMenus(paretid){
			$("#parentid").attr("value",paretid);
			menuform.submit();
		}
		
		//var zNodes ;
		$().ready(function(){
			$("input[name='qysh']").bind("click",subForm)
			var code =""; 
			$.ajax({ type:"POST", url:"/getMenutree", 
				data:code, success:function(arr){
					$.fn.zTree.init($("#treeDemo"), setting, eval(arr));
				} });
			
		} );
		
		function updateMenu(){
			
			var menuid= $('input[name="op"]:checked').val();
			
			if(menuid == undefined){
				//alert("请选择要修改的菜单！");
				msgBox("提示信息","请选择要修改的菜单！");
				return false ;
			}
			location.href="/modifyview?menuid="+menuid;
		}
		
		
		function checkValflg(flg){
			var menuid= $('input[name="op"]:checked').val();
			if(menuid == undefined){
				//alert("请选择菜单！");
				msgBox("提示信息","请选择菜单！");
				return false ;
			}
			if(1== flg){
				location.href='/updatevalflg?menuid='+menuid+'&valflg=1&levl=1';
			}else{
				var parm = "menuid="+menuid;
				$.ajax({type:"POST",url:"/checkvalflg",data:parm,
					success:function(data){
						if(data){
							location.href='/updatevalflg?menuid='+menuid+'&valflg=0&levl=1';
						}else{
							//alert("菜单存在启用状态的子菜单，不可禁用！");
							msgBox("提示信息","菜单存在启用状态的子菜单，不可禁用！");
						}
					}
				});
			}
		}
		
		function delMenuAfter(){
			var menuid= $('input[name="op"]:checked').val();
			if(menuid == undefined){
				//alert("请选择要删除的菜单！");
				msgBox("提示信息","请选择要删除的菜单！");
				return false ;
			}
			msgDialog({"title":"提示信息","centent":"确认删除?","okfunction":delMenu});
		}
		
		function delMenu(){
			var menuid= $('input[name="op"]:checked').val();
				var parm = "menuid="+menuid;
				$.ajax({type:"POST",url:"/checkvalflg",data:parm,
					success:function(data){
						if(data){
							location.href='/delMenu?menuid='+menuid+'&levl=1';
						}else{
							//alert("菜单存在启用状态的子菜单，不可删除！");
							msgBox("提示信息","菜单存在启用状态的子菜单，不可删除！");
						}
					}
				});
			}
		
		
		
		function subForm(){
			$("#hidvalflg").val($("input[name='qysh']:checked").val());
			menuform.submit();
		}
	</SCRIPT>
	<style type="text/css">
.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
	</style>
</head>

<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">菜单维护</a></li>
    </ol>
</div>
<div class="tabBox " style=" margin-top:5px;">
      <p class="rtradio" style="float:left; width:100%; margin-left:0px; line-height:30px; ">
      	<input type="radio" name="qysh" value=""  <c:if test="${empty status }">checked="checked"</c:if> /><label>全部</label>
      	<input type="radio" name="qysh" value="1" <c:if test="${status eq '1'}">checked="checked"</c:if> /><label>启用</label>
      	<input type="radio" name="qysh" value="0" <c:if test="${status eq '0'}">checked="checked"</c:if> /><label>停用</label>
      </p>
</div>
<div class="toolBox" style="margin-top:5px; width:98%; margin-left:15px;">
	<a href="#1" class="cur8" onClick="location.href='/menuview?levl=1'">添加菜单</a>
	<a href="#1" class="cur3" onClick="updateMenu()">修改</a>
	<a href="#1" class="cur4" onClick="delMenu()">删除</a>
	<a href="#1" class="cur9" onClick="checkValflg(1)">启用</a>
	<a href="#1" class="cur10" onClick="checkValflg(0)">禁用</a>
</div>
<form action="/menulist" method="post" name="menuform">
<input type="hidden" name="parentid" id="parentid">
<input type="hidden" name="levl" value="1">
<input type="hidden" name="status" id="hidvalflg">
<div class="tabBox">
	<table width="100%">
	<tr>
    <td width="215" valign="top">
    	<div class="zzjgBox">
                <ul id="treeDemo" class="ztree" style="border:0px; background:#fff; width:190px; margin-top:0px; height:490px;"></ul>
        </div>
    </td>
    <td valign="top">
    	<div class="zzjgtabBox">
        <div class="tabBoxyz">
     <table class="tabinfo" id="senfe" width="100%" cellpadding="0" cellspacing="0">
      <tr class="tabtitle">
        <td width="50">选择</td>
        <td>菜单名称</td>
        <td>父级菜单名称</td>
        <td>链接</td>
        <td>状态</td>
      </tr>
      <c:if test="${!empty list}">
	     <c:forEach items="${list}" var="menu">
	      <tr>
	        <td><input type="radio"  name="op" value="<c:out value="${menu.menuid }"/>"></td>
	        <td><c:out value="${menu.menuname }"/></td>
	        <td><ipav:menuname menuid="${menu.parentid }"/></td>
	        <td><c:out value="${menu.pathstr }"/></td>
	        <td><ipav:convert type="valflg" value="${menu.valflg}"/></td>
	      </tr>
	      </c:forEach>
      </c:if>
    </table>
      </div>
     </div>
	</td>
	</tr>
	</table>
</div>
</form>
</body>
</html>

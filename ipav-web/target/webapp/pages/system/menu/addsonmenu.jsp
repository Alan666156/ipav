<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<%@include file="/pages/system/common/head.jsp" %>
<script type="text/javascript">
	$().ready(function(){
		$("#menufrom").validate(); 
	} );
</script>
</head>

<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">新建模块菜单</a></li>
    </ol>
</div>
<form action="/menuadd"  method="post" name="menufrom" id="menufrom">
<input type="hidden" name="levl" value="${levl}">
<input type="hidden" name="valflg" value="1">
<table class="qytable mt30"  border="0" cellpadding="0" cellspacing="0">
  <tr height="60">
    <td width="170"><label class="xin">父级菜单名称：</label></td>
    <td colspan="2">
    <select class="tjsele {required:true}" name="parentid">
    	<option value=""></option>
    	<c:if test="${!empty menus}">
    		<c:forEach items="${menus}" var="item">
	        	<option value="${item.menuid }"><c:out value="${item.menuname}"/></option>
	        </c:forEach>
        </c:if>
    </select></td>
  </tr>
  <tr height="60">
    <td><label class="xin">菜单名称：</label></td>
    <td><input type="text" class="sytext2 {required:true,maxlength:20}" name="menuname"></td>
  </tr>
   <tr height="60">
    <td><label class="xin">链接地址：</label></td>
    <td><input type="text" class="sytext2 {required:true,maxlength:100}"  name="pathstr"></td>
  </tr>
  <tr height="100">
  	<td></td>
  	<td>
       	<input style="margin-right:40px;" type="submit" value="确认" class="butl100" onmouseover="this.className='butl100m'" 
       	onmouseout="this.className='butl100'" >
    <input type="button" value="取消" class="buth100" onmouseover="this.className='buth100m'" 
    onmouseout="this.className='buth100'" onclick="location.href='/menulist?levl=1'">
    </td>
  </tr>
</table>

</form>
</body>
</html>

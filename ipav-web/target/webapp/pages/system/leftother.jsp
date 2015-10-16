<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>

<script type="text/javascript">
$().ready(function(){
	if($("#0")!=undefined){
		$("#0").click();
	}
});
 
</script>
</head>

<body class="leftbody">
<div class="leftmenudiv">
	<ol class="leftmenu" id="leftmenu" style="overflow:auto;">
		<c:if test="${!empty roles}">
    			<c:forEach items="${roles}" var="item" varStatus="num">
    				<li><a href="/mianRole?roleno=<c:out value="${item.roleno }"/>" target="mainFrame" 
    				<c:if test="${num.index eq 0}">class="current"</c:if>
    				onclick="set_menu2(<c:out value="${num.index }"/>);">
    				<span id="<c:out value="${num.index }"/>"><c:out value="${item.rolename }"/></span></a></li>
    			</c:forEach>
    	</c:if>    	 
    </ol>
</div>
</body>
</html>

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
	<ol class="leftmenu" id="leftmenu">
		<c:if test="${!empty leftmenu}">
    			<c:forEach items="${leftmenu}" var="item" varStatus="num">
    				<li><a href="<c:out value="${item.pathstr }"/>" target="mainFrame" 
    				<c:if test="${num.index eq 0}">class="current"</c:if>
    				onclick="set_menu2(<c:out value="${num.index }"/>);">
    				<span id="<c:out value="${num.index }"/>"><c:out value="${item.menuname }"/></span></a></li>
    			</c:forEach>
    	</c:if>    	 
    </ol>
</div>
</body>
</html>
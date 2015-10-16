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
	
	function flushView(){
		if($(".current")!=undefined){
			$(".current").find("span").click();
		}
	}
	
	function pageClose(){
		window.parent.close();
	}
</script>
</head>
<body>
<div class="top">
	<div class="top_onediv">
    	<div class="logo ml20"></div>
        <div class="rtmenudiv">
            <ol class="rtmenu">
                <li><a href="#1" class="bz1">帮助中心</a></li>
                <li><a href="#1" class="bz2" onclick="pageClose()">关闭</a></li>
            </ol>
        </div>
    </div>
    
    <div class="topmenudiv">
    	<ol class="topmenu" id="topmenu">
    		<c:if test="${!empty topmenu}">
    			<c:forEach items="${topmenu}" var="item" varStatus="num">
    				<li><a href="<c:out value="${item.pathstr }"/>&menuid=<c:out value="${item.menuid }"/>" target="leftFrame"  <c:if test="${num.index eq 0}">class="current"</c:if> 
    				onclick="set_menu(<c:out value="${num.index }"/>);">
    				<span id="<c:out value="${num.index }"/>"><c:out value="${item.menuname }"/></span>
    				 </a></li>
    			</c:forEach>
    		</c:if>
        </ol>
    </div>

</div>
</body>
</html>
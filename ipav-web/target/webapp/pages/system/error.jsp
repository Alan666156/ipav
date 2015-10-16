<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>系统出错</title>

<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/myMsg/myMsg.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/mask.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>

<script type="text/javascript">
	
</script>


</head>
<body >
	<div align="center">
	 	<table>
	 		<tr>
	 			<c:if test="${!empty excpMessage}">
		 		    <tr>
			 			<td colspan="4" align="center">
<%-- 				 			 <font color="red"><c:out value="${excpMessage}"/></font> --%>
				 			 <script type="text/javascript">
				 			 		var msg = '${excpMessage}';
				 			 		msgBoxTohome('提示信息',msg);
				 			 </script>
			 			</td>
		 			</tr>
	 			</c:if>
	 		</tr>	
	 	</table>
	 </div>
</body>
</html>

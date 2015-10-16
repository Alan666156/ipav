<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>

</head>
<body>
	<div align="center">
	 <form action="http://kegoo.hss100.cn/Interface/Send.aspx" name="sendfrom" accept-charset="GBK"
	  method="post" onsubmit="document.charset='GBK';" >
		<input type="hidden" name="CorpID" value="CS007">
		<input type="hidden" name="Pwd" value="123456">
		<input type="hidden" name="Mobile" value="13917480816">
		<input type="hidden" name="Content" value="您已经成功注册快捷管家，请去以下地址激活：http://localhost:8090/ipav【天道启科电子有限公司】">
	 </form>
	 </div>
	 <script type="text/javascript">
		function sendMsg(){
			sendfrom.submit();
		}
		sendMsg();
	 </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
</head>
<body>
<input type="hidden" id="warning" value="${warninginfo}" />
<script type="text/javascript">
top.window.location.href="http://localhost:8080/warningInfo?warninginfo="+$("#warning").val();
/* var form=$('<form action="/warningInfo"></form>');
var input=$('<input type="hidden" name="warninginfo" value="'+$("#warning").val()+'" />');
$(form).append(input); */

</script>
</body>
</html>
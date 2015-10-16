<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误页面</title>
<style type="text/css">
body{
	font-family:"微软雅黑","Microsoft Yahei";
	}
.container_404{
	position:absolute;
	top:50%;
	left:50%;
	width:593px;
	height:374px;
	margin-left:-296px;
	margin-top:-187px;
	background:url(/pages/images/error.png) no-repeat center center;}

.text_404{
	width:300px;
	margin:0 auto;
	padding-left:90px;
	margin-top:30px;
	position:relative;
	top:140px;
	left:20px;
	}
</style>
</head>

<body>
<div class="container_404">
<!-- <img class="pic_404" src="404.png"> -->
<div class="text_404">
${warninginfo}<br><a href="${url}">确定</a>
</div>


</div>
</body>
</html>
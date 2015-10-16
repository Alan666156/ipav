<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/loginhead.jsp" %>
<title>恭喜您，注册成功！</title>
</head>
<%-- <jsp:include page="/pages/system/common/sendMessage.jsp"> --%>
<%-- 	<jsp:param value="${user.mobile }" name="mobile"/> --%>
<%-- </jsp:include> --%>
<body class="bobybg">

<form action="/ipav" name="regokfrom" method="post" >
<div class="top2">
	<div class="topdiv2">
    	<div class="topdivleft2"></div>
        <div class="topdivright2">
        	<p class="fwtel2">服务电话<span>800-820-7816</span></p>
            <ul class="topul2">
            	<li><a href="#1">产品介绍</a></li>
                <li>|</li>
                <li><a href="#1">帮助中心</a></li>
                <li>|</li>
                <li><a href="#1">设为收藏</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="fgdiv"></div>

<div class="regmain het450" align="center">
    	<table class="regtab mt40" width="400" border="0" cellpadding="0" cellspacing="0">
          <tr height="60">
            <td align="center" colspan="2"><p class="zccg">恭喜您，注册成功！</p></td>
          </tr>
          <tr height="80">
          	<td colspan="2">
            	<div class="zcmsg">管理员账号及密码已发送到手机（邮箱)中！</div>
            </td>
          </tr>
          <tr height="40">
          	<td align="right"><label>企业号：</label></td>
            <td align="left"><span class="usermsg"><c:out value="${user.companyid }"></c:out></span></td>
          </tr>
          <tr height="30">
          	<td align="right"><label>管理员账号：</label></td>
            <td align="left"><span class="usermsg"><c:out value="${user.userid }"></c:out></span><span class="hse2">(默认登录账号)</span></td>
			<input type="hidden" name="userno" value="<c:out value='${user.userid }'/>"/>
          </tr>
          <tr height="30">
          	<td align="right"></td>
            <td align="left">
            	<span class="usermsg2">
            	<c:choose>
            		<c:when test="${user.regtype eq 'M' }"><c:out value="${user.mobile }"></c:out></c:when>
            		<c:when test="${user.regtype eq 'E' }"><c:out value="${user.email }"></c:out></c:when>
            		<c:otherwise></c:otherwise>
            	</c:choose>
            	<c:out value=""></c:out></span>
            	<span class="hse2">(也可作为登录账号使用)</span></td>
          </tr>
          <tr height="150">
            <td align="center" colspan="2">
            <input type="submit" value="进入登录页面" class="userlogin" onmouseover="this.className='userlogin1'" 
            onmouseout="this.className='userlogin'"></td>
          </tr>
        </table>
</div>

<div class="floor2">
	<div class="floordiv2">
    	<p align="center" class="pdt25">上海天道启科电子有限公司&nbsp;&nbsp;&nbsp;©版权所有沪&nbsp;&nbsp;&nbsp;ICP备10000764号</p>
        <p align="center">上海市肇嘉浜路333号亚太企业大楼1204室</p>
    </div>
</div>
</form>
</body>
</html>

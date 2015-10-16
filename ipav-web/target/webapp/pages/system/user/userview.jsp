<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<title>系统管理</title>
</head>

<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">员工基本信息</a></li>
    </ol>
</div>
<form action="/" method="post" name="userform">
<div class="tabBox serchBox">
	<table class="tabmsg" align="center" width="100%" border="1" cellpadding="0" cellspacing="0">
    	<tr >
        	<td width="180" align="right">员工编号：</td>
            <td width="33%"><c:out value="${user.userno }"/></td>
            <td width="180" align="right">员工姓名：</td>
            <td width="33%"><c:out value="${user.username }"/></td>
            <td width="100" rowspan="7" valign="top">
            <img src="${user.picpath}" width="80" height="100" class="mt10"></td>
        </tr>
        <tr>
       	    <td width="180" align="right">性别：</td>
            <td><ipav:convert type="sex" value="${user.sex}"/></td>
            <td align="right">部门：</td>
            <td><c:out value="${orgName }"/></td>
        </tr>
        <tr>
        	<td align="right">职务：</td>
            <td><c:out value="${dutyName }"/></td>
            <td align="right">注册用户信息：</td>
            <td><ipav:convert type="regtype" value="${user.regtype}"/></td>
        </tr>
        <tr>
            <td align="right">手机号码：</td>
            <td><c:out value="${user.mobile }"/></td>
            <td align="right">电子邮箱：</td>
            <td><c:out value="${user.email }"/></td>
        </tr>
        <tr>
            <td align="right">是否是部门最高职位：</td>
            <td>${isLeader==1?'是':'否'}</td>
            <td align="right">兼职部门：</td>
            <td><c:out value="${partTimeDep }"/></td>
        </tr>
        <tr>
            <td align="right">兼职职务：</td>
            <td><c:out value="${partTimeJob }"/></td>
            <td align="right">所属工作平台：</td>
           <td>${platform}</td>
        </tr>
        <tr>
        	<td align="right">办公地址：</td>
            <td><c:out value="${user.address }"/></td>
            <td align="right">工作电话：</td>
            <td><c:out value="${user.phone }"/></td>
        </tr>
        <tr>
            <td align="right">直接主管：</td>
    		<td >${directorName}</td>
        
            <td align="right">劳动合同签订单位：</td>
    		<td >${labour}</td>
        </tr>
    </table>
</div>

<div class="tabBox">
			<c:if test="${user.valflg eq '1'}">
				<c:if test="${user.userid ne curuser.userid }">
		       	<input style="margin-right:30px;" type="button" value="修改员工信息" class="butl100" onmouseover="this.className='butl100m'" 
		       	onmouseout="this.className='butl100'" onclick="location.href='/gotomodifyuser?oper=update&userid=<c:out value ='${user.userid }'/>'">
	       		</c:if>
	       	</c:if>
	        <input type="button" value="返回" class="buth100" onmouseover="this.className='buth100m'" 
	        onmouseout="this.className='buth100'" onclick="location.href='/userlist?companyid=<c:out value ='${user.companyid }'/>'">

</div>
</form>
</body>
</html>

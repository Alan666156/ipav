<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	Cookie[] cookies = request.getCookies();
	String registno = "";
	String password = "";
	int count = 0;
	if(cookies!=null){
		for(Cookie c : cookies){
			if(c.getName().equals("registno")){
				if(count == 0){
					registno = c.getValue();
					count = count +1;
				}
			}
			if(c.getName().equals(registno+"password")){
				password = c.getValue();
			}
		}
	}

%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/loginhead.jsp" %>
<script type="text/javascript" src="/pages/js/publicjs/collect.js"></script>
<title>欢迎登录快捷管家</title>

<script type="text/javascript">
	$(function() {
		$('input').placeholder();
	});
	function gotoRegister(){
		loginfrom.action="/gotoRegister";
		loginfrom.submit();
// 		window.open("/gotoRegister");
	}
	function login(){
		var str = $("#registno").val();
		if(checkEmail(str)){
			loginfrom.regtype.value ="E";
			loginfrom.email.value =str;
		}else if(checkMobile(str)){
			loginfrom.regtype.value ="M";
			loginfrom.mobile.value =str;
		}else{
			loginfrom.userid.value = str;
		}
		if($("#check1").attr('checked')){
			loginfrom.saveflg.value ="save";
		}
		loginfrom.submit();
	}
	function showMsg(){
		$("#msg1").hide();
	}
	
	function gotoLogin(evt){
		evt = (evt) ? evt : ((window.event) ? window.event : "")
       keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
       if (keyCode == 13) {
    	   login();
       }
		 
	}
	
	document.onkeydown = gotoLogin;
	
</script>

</head>

<body>
	<form name="loginfrom" action="/ipavlogin" method="post">
		<input type="hidden" name="mobile" />	
		<input type="hidden" name="email" />
		<input type="hidden" name="userid" />	
		<input type="hidden" name="regtype"/>
		<input type="hidden" name="saveflg"/>
		<div class="top">
			<div class="topdiv">
				<div class="topdivleft mt20"></div>
				<div class="topdivright">
					<p class="fwtel">
						服务电话<span>800-820-7816</span>
					</p>
					<ul class="topul">
						<li><a href="javascript:;">产品介绍</a></li>
						<li>|</li>
						<li><a href="javascript:;">帮助中心</a></li>
						<li>|</li>
						<li><a href="javascript:;" rel="sidebar" onclick="shoucang()">设为收藏</a></li>
					</ul>
				</div>
			</div>
		</div>

		<div class="banner bgs1">
			<div class="logindiv">
				<div class="logindivleft">
					<a class="iphones" title="iPhone版下载" href="javascript:;"></a><a
						title="Android版下载" class="ml20" href="javascript:;"></a>
				</div>
				<div class="logindivright" id="logindivright">
					<table class="logintab" width="260" border="0" cellpadding="0"
						cellspacing="0">
						<tr height="50">
							<td colspan="2"><input type="text" class="usertext"  name="registno" id="registno" placeholder="请输入用户账号" 
							<c:if test="${not empty userno }">value="${userno }"</c:if>
							<c:if test="${not empty cookie['registno']}">value="${cookie['registno'].value}"</c:if>  
							onchange="showMsg();" autofocus ></td>
						</tr>
<!-- 						<tr height="14"> -->
<!-- 							<td colspan="2"><label class="error1">用户名输入有误！</label></td> -->
<!-- 						</tr> -->
						<tr height="50">
							<td colspan="2"><input type="password" value="<%=password %>" class="userpasd" name="password" onchange="showMsg();"
								placeholder="请输入账号密码"></td>
						</tr>
						<tr height="14">
							<td colspan="2" id="msg1" align="center" <c:if test="${loginfalse ne 'Y'}">style="display: none;" </c:if>
							<c:if test="${loginfalse eq 'Y'}">style="display: block;" </c:if>>
							<label class="error1">用户名或密码输入有误！</label></td>
						</tr>
						<tr height="30">
							<td><input type="checkbox" class="check" <%if(!registno.equals("") && !password.equals("")){ %>checked="checked"<%} %> id="check1"><label
								class="hse" for="check1">记住密码</label></td>
							<td align="right"><a href="/findPassword"
								class="wjmima">忘记密码？</a></td>
						</tr>
						<tr height="60">
							<td colspan="2"><input type="button" value="登 录" onclick="login();"
								class="userlogin" onmouseover="this.className='userlogin1'"
								onmouseout="this.className='userlogin'"></td>
						</tr>
					</table>
					<p class="userreg">
						还没有快捷管家账号？<a href="javascript:;" onclick="gotoRegister();">立即注册</a>
					</p>
				</div>
			</div>
		</div>

		<div class="floor">
			<div class="floordiv">
				<p align="center" class="pdt25">上海天道启科电子有限公司&nbsp;&nbsp;&nbsp;©版权所有沪&nbsp;&nbsp;&nbsp;ICP备10000764号</p>
				<p align="center">上海市肇嘉浜路333号亚太企业大楼1204室</p>
			</div>
		</div>
	</form>
</body>
</html>

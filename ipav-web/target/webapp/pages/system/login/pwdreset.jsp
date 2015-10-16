<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/loginhead.jsp" %>
<title>密码重置</title>
<script type="text/javascript">
	function pwdreset(){
		var pwd= $("#pwd1").val();
		if(!checkPwd(pwd)){
			$("#msgshow").show();
			return false;
		}
		var pwd2= $("#pwd2").val();
		if(pwd != pwd2){
			$("#msgshow").hide();
			$("#msgshow2").show();
			return false;
		}
		repwdfrom.password.value =pwd;
		repwdfrom.submit();
	}

</script>

</head>

<body class="bobybg">
<form action="/pwdReset" method="post" name="repwdfrom">
<input type="hidden" name="userid" value="${userid }" />
<input type="hidden" name="password" value="${password }" />
<%-- <input type="hidden" name="session_id" value="${session_id}"> --%>
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
	
	<div class="regmain het450">
	    	<table class="regtab" align="center" width="980" border="0" cellpadding="0" cellspacing="0">
	          <tr height="150">
	            <td width="300"></td>
	            <td align="center" width="253" ><i>密码重置</i></td>
	            <td width="410"></td>
	          </tr>
	          <tr height="75">
	            <td align="right"><label>新密码：</label></td>
	            <td><input type="password" class="regtext" placeholder="请输入密码(6至16位数字或英文字符)" name="pwd1" id="pwd1"></td>
	            <td >
	             <div id="msgshow" style="display: none;">
	            <span class="msg ml10">6至16位数字或英文字符</span></div></td>
	          </tr>
	          <tr height="60">
	            <td align="right"><label>重复新密码：</label></td>
	            <td><input class="regtext" type="password" placeholder="请再次输入新密码" name="pwd2" id="pwd2"></td>
	            <td>
	            	<div id="msgshow2" style="display: none;">
            			<span class="xin ml10">密码输入不一致！</span></div>
	            </td>
	          </tr>
	         
	          <tr height="150">
	            <td></td>
	            <td><input type="button" value="确 定" class="userlogin" onmouseover="this.className='userlogin1'" 
	            onmouseout="this.className='userlogin'" onclick="pwdreset();"></td>
	            <td></td>
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
<script type="text/javascript">
$(function(){
			$('input').placeholder();
		});

</script>
</body>
</html>

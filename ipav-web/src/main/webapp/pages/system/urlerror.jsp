<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/loginhead.jsp" %>
<script type="text/javascript">
	function gotoIndex(){
		urlForm.submit();
	}
	function gotoFindPwd(){
		urlForm.action="/findPassword";
		urlForm.submit();
	}
	
</script>

</head>
<body class="bobybg">
<form action="/ipav" name="urlForm" method="post">
<div class="top2">
	<div class="topdiv2">
    	<div class="topdivleft2"></div>
        <div class="topdivright2">
        	<p class="fwtel2">服务电话<span>800-820-7816</span></p>
            <ul class="topul2">
            	<li><a href="javascript:;">首页</a></li>
                <li>|</li>
                <li><a href="javascript:;">登录</a></li>
                <li>|</li>
                <li><a href="javascript:;">注册</a></li>
                <li>|</li>
                <li><a href="javascript:;">产品介绍</a></li>
                <li>|</li>
                <li><a href="javascript:;">帮助中心</a></li>
                <li>|</li>
                <li><a href="javascript:;">设为收藏</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="fgdiv"></div>

<div class="regmain het450" align="center">
    	<table class="regtab" style="margin-top:160px;" width="300" border="0" cellpadding="0" cellspacing="0">
          <tr height="40">
            <td align="left"><p class="errortis">链接已过期，请重新发送邮件！</p></td>
          </tr>
          <tr height="25">
          	<td align="left"><label class="ml30">链接有效期为24小时</label></td>
          </tr>
          <tr height="60">
            <td>
            	<input type="button" class="urlbut mr30 ml30" value="进入首页" onclick="gotoIndex()">
                <input type="button" class="urlbut" value="找回密码" onclick="gotoFindPwd()">
            </td>
          </tr>
        </table>

</div>

<div class="floor2">
	<div class="floordiv2">
    	<p align="center">上海天道启科电子有限公司&nbsp;&nbsp;&nbsp;©版权所有沪&nbsp;&nbsp;&nbsp;ICP备10000764号</p>
        <p align="center">上海市肇嘉浜路333号亚太企业大楼1204室</p>
    </div>
</div>
</form>
</body>
</html>

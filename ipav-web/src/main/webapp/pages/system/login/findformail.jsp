<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/loginhead.jsp" %>
<style type="text/css">
.no{ width:65px; height:30px; color:#3e4d4d; border:0px; background:url(/pages/images/platform/no.png); cursor:pointer; }
</style>
<title>验证信息</title>
</head>
<body class="bobybg">
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
    	<table class="regtab" width="980" border="0" cellpadding="0" cellspacing="0">
          <tr height="150" align="center">
            <td></td>
            <td width="138"><i>验证信息</i></td>
            <td width="415"></td>
          </tr>
          <tr height="120">
            <td align="center" colspan="3"><p class="czmima">密码重置链接已发送到你的邮箱：<span class="hide_email"><c:out value="${user.email }"/></span>，请去邮箱点击链接重置密码。</p></td>
          </tr>
        
          <tr height="150">
            <td></td>
            <td><input type="submit" value="去邮箱" id="btn_actemail" class="userlogins" onmouseover="this.className='userlogins1'" 
            onmouseout="this.className='userlogins'"></td>
            <td><a href="#1" class="toopen"></a></td>
          </tr>
        </table>
        

</div>

<div class="floor2">
	<div class="floordiv2">
    	<p align="center" class="pdt25">上海天道启科电子有限公司&nbsp;&nbsp;&nbsp;©版权所有沪&nbsp;&nbsp;&nbsp;ICP备10000764号</p>
        <p align="center">上海市肇嘉浜路333号亚太企业大楼1204室</p>
    </div>
</div>
<script type="text/javascript">
 //跳转到指定的邮箱登录页面
            $("#btn_actemail").click(function () {
                var uurl = $(".hide_email").text();
                uurl = gotoEmail(uurl);
                if (uurl != "") {
                    $(".toopen").attr("href", "http://"+uurl);
                    $(".toopen")[0].click();
                } else {
                   // alert("抱歉!未找到对应的邮箱登录地址，请自己登录邮箱查看邮件！");
                	msgBox("提示信息", "抱歉!未找到对应的邮箱登录地址，请自己登录邮箱查看邮件！");
                }
            });

            //功能：根据用户输入的Email跳转到相应的电子邮箱首页
            function gotoEmail($mail) {
                $t = $mail.split('@')[1];
                $t = $t.toLowerCase();
                if ($t == '163.com') {
                    return 'mail.163.com';
                } else if ($t == 'vip.163.com') {
                    return 'vip.163.com';
                } else if ($t == '126.com') {
                    return 'mail.126.com';
                } else if ($t == 'qq.com' || $t == 'vip.qq.com' || $t == 'foxmail.com') {
                    return 'mail.qq.com';
                } else if ($t == 'gmail.com') {
                    return 'mail.google.com';
                } else if ($t == 'sohu.com') {
                    return 'mail.sohu.com';
                } else if ($t == 'tom.com') {
                    return 'mail.tom.com';
                } else if ($t == 'vip.sina.com') {
                    return 'vip.sina.com';
                } else if ($t == 'sina.com.cn' || $t == 'sina.com') {
                    return 'mail.sina.com.cn';
                } else if ($t == 'tom.com') {
                    return 'mail.tom.com';
                } else if ($t == 'yahoo.com.cn' || $t == 'yahoo.cn') {
                    return 'mail.cn.yahoo.com';
                } else if ($t == 'tom.com') {
                    return 'mail.tom.com';
                } else if ($t == 'yeah.net') {
                    return 'www.yeah.net';
                } else if ($t == '21cn.com') {
                    return 'mail.21cn.com';
                } else if ($t == 'hotmail.com') {
                    return 'www.hotmail.com';
                } else if ($t == 'sogou.com') {
                    return 'mail.sogou.com';
                } else if ($t == '188.com') {
                    return 'www.188.com';
                } else if ($t == '139.com') {
                    return 'mail.10086.cn';
                } else if ($t == '189.cn') {
                    return 'webmail15.189.cn/webmail';
                } else if ($t == 'wo.com.cn') {
                    return 'mail.wo.com.cn/smsmail';
                } else if ($t == '139.com') {
                    return 'mail.10086.cn';
                } else {
                    return '';
                }
            };

</script>
</body>
</html>

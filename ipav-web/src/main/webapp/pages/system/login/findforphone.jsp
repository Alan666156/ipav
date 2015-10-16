<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/loginhead.jsp" %>
<title>欢迎登录快捷管家</title>
<script type="text/javascript">
	var x =120;
	function countNum(){
		x= x-1;
		if(x>0){
			$("#timecount").html("还剩"+x+"秒")	
			setTimeout('countNum()',1000);
		}else{
			$("#timecount").html("重新获取")
			return false ;
		}
		
	}
	
	function getNewFhoneCode(){
		var userid = $("#userid").attr("value"); 
		var mobile = $("#mobile").attr("value"); 
		code = "userid=" + userid+"&mobile="+mobile; 
		$.ajax({ type:"POST", url:"/createPhonecdoe", 
			data:code, success:function(){
				countNum();
			} }); 
		
	}
	
 
</script>

</head>

<body class="bobybg">
<form action="/checkPhonecode" name="phoneform" method="post" >
<input type="hidden" name="userid" id="userid" value="${user.userid }">
<input type="hidden" name="mobile" id="mobile" value="${user.mobile }">
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
    	<table align="center" class="regtab" width="450" border="0" cellpadding="0" cellspacing="0">
          <tr height="150">
            <td align="center" colspan="2"><i>验证信息</i></td>
          </tr>
          <tr height="40">
            <td align="center" colspan="3"><p class="czmima">已发送验证码到手机号：<span><c:out value="${user.mobile }"/></span>，验证码<span>两分钟</span>内有效。</p></td>
          </tr>
         <tr height="90">
            <td align="right"><label>请输入收到的短信验证码：</label></td>
            <td><input class="duanxin fl" type="text" name="phonecode">
            <span href="#" class="seconds"  id="timecount" ></span>
            </td>
          </tr>
          <tr height="120">
            <td></td>
            <td><input type="submit" value="验 证" class="userlogins" onmouseover="this.className='userlogins1'" 
            onmouseout="this.className='userlogins'"></td>

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
	countNum();
</script>
</form>
</body>
</html>

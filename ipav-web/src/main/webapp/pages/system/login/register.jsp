<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/loginhead.jsp" %>
<title>欢迎登录快捷管家</title>
<script type="text/javascript" src="/pages/js/publicjs/collect.js"></script>
<script type="text/javascript">
	$(function() {
		$('input').placeholder();
		if($("#hid_message").val()=="exist"){
			$("#simScrollCont").show();
			$("#simScrollCont").css("width",$(window).width()); 
			$("#simScrollCont").css("height",$(window).height()); 
			$("#simScrollContBox").show();
		}
	});
	function register(){
		var str = $("#registno").val();
		if(checkEmail(str)||checkMobile(str)){
			if(checkEmail(str)){
				registerfrom.regtype.value ="E";
				registerfrom.email.value =str;
			}
			if(checkMobile(str)){
				registerfrom.regtype.value ="M";
				registerfrom.mobile.value =str;
			}
			if(!$("#check1").attr('checked')){
				//alert("未同意服务协议！");
				msgBox("提示信息", "未同意服务协议！");
				return false ;
			}
			isRightCode();
			
		} else{
			 $("#msgshow").show();
			 return false ;
		}
	}
	
	function changeImg(){ 
		var imgSrc = $("#imgObj"); 
		var src = imgSrc.attr("src"); 
		imgSrc.attr("src",chgUrl(src)); 
		checkImg();
	} 
	//时间戳 //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳 
	function chgUrl(url){ 
		var timestamp = (new Date()).valueOf(); 
		url = url.substring(0,17); 
		if((url.indexOf("&")>=0)){ 
			url = url + "&timestamp=" + timestamp; 
		}else{ 
			url = url + "?timestamp=" + timestamp; 
		} 
		return url; 
	}  
	function isRightCode(){ 
		var code = $("#veryCode").attr("value"); 
		code = "veryCode=" + code; 
		$.ajax({ type:"POST", url:"/checkCode", 
			data:code, success:callback }); 
	}  
	function callback(data){ 
		if(!data){
			$("#msgshow2").show();
			changeImg();
			return false;
		}else{
			registerfrom.submit();
		}
	} 
	function closeMsg(){
		$("#simScrollCont").hide();
		$("#simScrollContBox").hide();
	}
	
	function gotoLogin(){
		registerfrom.action="/ipav";
		registerfrom.submit();
	}
	
	function checkImg(){
		var code = $("#veryCode").attr("value"); 
		code = "veryCode=" + code; 
		$.ajax({ type:"POST", url:"/checkCode", 
			data:code, success:function(da){
				if(!da){
				$("#veryCode").removeClass("yes");
				if(!$("#veryCode").hasClass("error")){
				$("#veryCode").addClass("error");
				}
				}else{
				$("#veryCode").removeClass("error");
				if(!$("#veryCode").hasClass("yes")){
				$("#veryCode").addClass("yes");
				$("#msgshow2").hide();
					}
				}
			}		
		}); 
	}
	//根据窗口大小调整弹出框的位置
	$(window).resize(function() {
		$(".simScrollContBox4").css({
	        position: "absolute",
	        left: ($(window).width() - $(".simScrollContBox4").outerWidth()) / 2,
	        top: ($(window).height() - $(".simScrollContBox4").outerHeight()) / 2
	    });
	})
	$(function() {
	    $(window).resize();
	});
</script>
<style type="text/css">
.no{ width:65px; height:30px; color:#3e4d4d; border:0px; background:url(/pages/images/platform/no.png); cursor:pointer; }
</style>
</head>

<body class="bobybg">
<input type="hidden" id="hid_message" value="${message}"/>
<form id="regis_from" name="registerfrom" action="/registerUser" method="post">
		<input type="hidden" name="mobile" />	
		<input type="hidden" name="email" />
		<input type="hidden" name="regtype"/>
<div class="top2">
	<div class="topdiv2">
    	<div class="topdivleft2"></div>
        <div class="topdivright2">
        	<p class="fwtel2">服务电话<span>800-820-7816</span></p>
            <ul class="topul2">
               <li><a href="javascript:;">首页</a></li>
                <li>|</li>
                <li><a href="javascript:;" onclick="gotoLogin();">登录</a></li> 
                 <li>|</li>
            	<li><a href="javascript:;">产品介绍</a></li>
                <li>|</li>
                <li><a href="javascript:;">帮助中心</a></li>
                <li>|</li>
                <li><a href="javascript:;" onclick="shoucang();" rel="sidebar">设为收藏</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="fgdiv"></div>

<div class="regmain">
	<div class="regmainleft">
    	<table class="regtab" width="565" border="0" cellpadding="0" cellspacing="0">
          <tr height="120" align="center">
            <td colspan="3"><i>欢迎注册</i></td>
          </tr>
          <tr height="75">
            <td align="right"><label>管理员账号：</label></td>
            <td width="138"><input type="text" class="regtext" name="registno" id="registno" placeholder="邮箱或手机号，将作为账号使用"></td>
            <td>
            <div id="msgshow" style="display: none;">
            <span class="xin ml10">手机号或邮箱格式不对！</span></div>
            </td>
          </tr>
          <tr height="60">
            <td align="right"><label>效验码：</label></td>
            <td><input class="regtext2" id="veryCode" name="veryCode" type="text" onblur="checkImg()"/> 
            <img id="imgObj" alt="" src="/createCode" class="fr"/></td>
            <td><span class="ml10">看不清？<a href="javascript:;" class="als" onclick="changeImg()">换一张</a></span>
            </td>
          </tr>
          <tr height="20">
            <td></td>
          	<td>
          	 <div id="msgshow2" style="display: none;">
          	<span class="xin">验证码输入有误！</span>
          	</div>
          	</td>
            <td></td>
          </tr>
          <tr height="80">
            <td></td>
            <td>
            <input type="checkbox" class="check" id="check1"><label class="hse" style=" font-size:13px;" for="check1">我已阅读并同意<a href="javascript:;">《快捷管家服务协议》</a></label>
           </td>
            <td></td>
          </tr>
          <tr height="90">
            <td></td>
            <td><input type="button" value="立即注册" class="userlogin" onmouseover="this.className='userlogin1'" 
            onmouseout="this.className='userlogin'" onclick="register();"></td>
            <td></td>
          </tr>
        </table>

    </div>
    
    <div class="regmainright">
    	<img src="/pages/images/login/regimg.jpg">
        <div class="meregs"><span>我已经注册，现在就</span><a href="javascript:;" onclick="gotoLogin();" class="xlogin">登录</a></div>
        <div class="iphonehref">
        	<a title="iPhone版下载" href="javascript:;"></a><a title="Android版下载" class="ml20" href="javascript:;"></a>
        </div>
    </div>
</div>

<div class="floor2">
	<div class="floordiv2">
    	<p align="center" class="pdt25">上海天道启科电子有限公司&nbsp;&nbsp;&nbsp;©版权所有沪&nbsp;&nbsp;&nbsp;ICP备10000764号</p>
        <p align="center">上海市肇嘉浜路333号亚太企业大楼1204室</p>
    </div>
</div>
	</form>
	<!-- 提示信息 -->
<div id="simScrollCont" class="simScrollCont"></div>
<div class="simScrollContBox4" id="simScrollContBox">
    	<div class="simScrollContBoxs4">
        <div class="tctitle">提示信息<a href="javascript:;" onClick="closeMsg()" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        	<p class="alertmsg" id="msg_info">亲爱的快捷管家用户，您好！您在快捷管家中用来注册的手机号在系统中已注册生成过用户，请重新输入手机号注册！ </p>
        </div>
        <div class="alertbut">
        	<input type="button" value="确定" class="butyes fr mr20" onClick="closeMsg()" />
        </div>
        </div>
</div> 
</body>
</html>

<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script language="javascript">
	$(function() {
		var winhight = document.documentElement.clientHeight;
		var mainboxs = winhight-114;
		document.getElementById("mainframe").style.height=mainboxs+"px";
		document.getElementById("ptlfbg").style.height=mainboxs+"px";
		document.getElementById("ptlfbg2").style.height=mainboxs+"px";
	});
	//窗口最大化最小化事件
	$(window).resize(function(){
		var winhight = document.documentElement.clientHeight;
		var mainboxs = winhight-114;
		document.getElementById("mainframe").style.height=mainboxs+"px";
		document.getElementById("ptlfbg").style.height=mainboxs+"px";
		document.getElementById("ptlfbg2").style.height=mainboxs+"px";
	});
	$(window).load(function(){
		if($("#state").val()=="Y"){
			 userState.showWidow();
			}
	});
	var userState={
			showWidow:function(){
			     var html='<div id="msgBox_simScrollCont" class="simScrollCont" ></div><div  id="msgBox"><div class="passwordBox" id="mima" >'+
		           '<div class="passwordBox2" ><div class="tctitle">为保证您的账户安全，首次登陆请重置密码！<a href="javascript:;" onClick="userState.closeWindow()" title="关闭"></a></div>'+
		           '<div id="UpdatePwdCenter"><table width="100%" style="margin-left:40px;" border="0" class="mt10" cellpadding="0" cellspacing="0">'+
		           ' <tr height="50"> <td align="right" width="80"><label>原密码：</label></td>'+
			       ' <td><input type="password" class="grzxnBoxtext" placeholder="请输入当前账号密码"  id="oldpwd" onBlur="userState.oldpwdBlur()"><span class="pzsmsg">原密码不能为空</span></td>'+
			       ' </tr><tr height="50">  <td align="right" width="80"><label>新密码：</label></td>'+
			       ' <td><input type="password" class="grzxnBoxtext" placeholder="请输入6-16位数字或英文密码" id="newpwd" onBlur="userState.newpwdBlur()"><span class="pzsmsg">6-16位字符密码，只可输入英文或数字！</span></td>'+
			       '</tr> <tr height="50"><td align="right" width="80"><label>重复新密码：</label></td>'+
			       ' <td><input type="password" class="grzxnBoxtext" placeholder="请重新输入新密码" id="newpwd2" onBlur="userState.newpwd2Blur()"><span class="pzsmsg">6-16位字符密码，只可输入英文或数字！</span></td>'+
			       ' </tr><tr height="60"><td></td><td >'+
			       ' <input type="button" class="pttcBut85"  value="确认" onclick="userState.updatepwd();">'+
			       ' <input type="button" class="pttcButh85 ml30" value="取消"  	onClick="userState.closeWindow()">'+
			       ' </td></tr></table></div><div> </div>';	 
					$(document.body).append(html);
					var left=($(document).width()-$("#mima").width())/2;
					var top= ($(document).height()-$("#mima").height())/2;
					$("#mima").css("position","absolute");
					$("#mima").css("left",left); 
					$("#mima").css("top",top); 	
					$("#mima").css("display","block");	
					$("#msgBox_simScrollCont").css("display","block");				
					},
			closeWindow:function(){
				$("#mima").remove();
				$("#msgBox_simScrollCont").remove();
			},
			updatepwd:function(){
		       if(userState.oldpwdBlur()==true
				  &userState.newpwdBlur()==true
				  &userState.newpwd2Blur()==true){
		    	   var param="oldPwd="+$("#oldpwd").val()+"&newPwd="+$("#newpwd").val();			 
					$.ajax({
						url:"updatePwd",
						type:"POST",
						data:param,
						success:function(data){
							if(data!=undefined){
								if(data.success!=undefined&&data.success==1){
									$("#UpdatePwdCenter").html('<p class="pzscenter">恭喜你密码修改成功</p>');
									 $("#mima").fadeOut(3000,function(){ 
										 userState.closeWindow();								 
									 });  	
								}else{
									$("#oldpwd").next().html("原密码错误");
									$("#oldpwd").next().css("display","block"); 
								}
							}else{
								$("#newpwd").next().html("原密码错误");
								$("#newpwd").next().css("display","block"); 
							}
						}
					})
				  }
			} ,
			oldpwdBlur:function(){
				if($.trim($("#oldpwd").val())==""){
					$("#oldpwd").next().html("原密码不能为空");
					$("#oldpwd").next().css("display","block");
					return false;
				  }else{
					$("#oldpwd").next().hide(); 
					return true
				  }
			},
			newpwdBlur:function(){
				if($.trim($("#newpwd").val())==""){
					$("#newpwd").next().html("6-16位字符密码，只可输入英文或数字！");
					$("#newpwd").next().css("display","block");
					return false;
				  }if($("#newpwd").val().length>16 || $("#newpwd").val().length<6){
					  $("#newpwd").next().html("请输入6-16位数字或英文密码");
					  $("#newpwd").next().css("display","block");
				  }else{
					  $("#newpwd").next().hide(); 
					return true
				  }
			},
			newpwd2Blur:function(){
				if($.trim($("#newpwd2").val())==""){			
					$("#newpwd2").next().html("6-16位字符密码，只可输入英文或数字！");
					$("#newpwd2").next().css("display","block");
					return false;
				  }if($("#newpwd").val()!=$("#newpwd2").val()){
					  $("#newpwd2").next().html("两次密码不一致");  
					  $("#newpwd2").next().css("display","block");  
				  }
				else{
					$("#newpwd2").next().hide(); 
					return true
				  }
			},
			}
</script>

</head>

<body class="pt-body" style=" margin:0px; padding:0px;"> 
<input type="hidden" id="companyId" value="${topCompanyId}" />
<input type="hidden" id="companyName" value="${topCompanyName}" />
<input type="hidden" id="logo" value="${topCompanyPic}" />
<input type="hidden" id="state" value="${state}" />
<div id="ptlfbg" class="ptlfbg" style="margin-top: 114px;"></div>
<div id="ptlfbg2" class="ptlfbg2" style="margin-top: 114px;"></div>
<iframe height="114px" id="topframe" name="topframe" width="100%"  scrolling="no" frameborder="0" allowtransparency="true" src="/pages/top.jsp"></iframe>
<iframe id="mainframe" height="100%" width="100%"  scrolling="no" frameborder="0" allowtransparency="true" src="/ipavMain" style="bz-index: 1;margin-left: 8px;"></iframe>
<div id="_ptlfbg" style="display:none;position: absolute;width: 8px;background: none repeat scroll 0% 0% #000;top: 0px;left: 0px;opacity: 0.4;margin-top: 114px;"></div>
</body>
</html>

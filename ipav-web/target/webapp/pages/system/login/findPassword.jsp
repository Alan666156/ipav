<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/loginhead.jsp" %>
<title>密码重置</title>
<script type="text/javascript" src="/pages/js/publicjs/collect.js"></script>
<script type="text/javascript">
	function changeImg(){ 
		var imgSrc = $("#imgObj"); 
		var src = imgSrc.attr("src"); 
		imgSrc.attr("src",chgUrl(src)); 
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
		if(code!=""&&code!=null){
			code = "veryCode=" + code; 
			$.ajax({ type:"POST", url:"/checkCode", 
				data:code, success:callback }); 
		}else{
			showErr("msgshow2", "请输入验证码");
		}		
	} 
	function callback(data){ 
		if(!data){
			showErr("msgshow2", "验证码输入有误！");
			changeImg();
			return false;
		}else{
			respasswordfrom.submit();
		}
	} 
	function checkUserNo(){
		var rs_data=new Object();
		var str = $("#registno").val();
		if(str!=null&&str!=undefined&&str!=""){
			if(checkEmail(str)){
				respasswordfrom.regtype.value ="E";
				respasswordfrom.email.value =str;
				rs_data.regtype="E";
				rs_data.email=str;
				return rs_data;
			}else if(checkMobile(str)){
				respasswordfrom.regtype.value ="M";
				respasswordfrom.mobile.value =str;
				rs_data.regtype="M";
				rs_data.mobile=str;
				return rs_data;
			}else{
				respasswordfrom.userid.value =str;
				rs_data.userid=str;
				return rs_data;		
			}
		}else{
			return null;
		}
	}
	function checkExsitUser(parm){
		hideErr();
		var parm = checkUserNo();
		if(parm==null){
			showErr("msgshow","请输入账号");
		}else{
			$.ajax({
				type:"POST", 
				url:"/checkhavUser",
				data:parm, 
				success: function(str){
					if(!str){	
// 						changeImg();
						showErr("msgshow","用户不存在！");
						return false;
					}
					isRightCode();				
				}
			}); 
		}
	}
	function showErr(id,err_info){
		$("#"+id+" span").html(err_info);
		$("#"+id).show();
	}
	function hideErr(){
		$("#msgshow span").html("");
		$("#msgshow2 span").html("");
		$("#msgshow").hide();
		$("#msgshow2").hide();
	}
	function gotoLogin(){
		window.location="/ipav";
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
</script>
</head>
<body class="bobybg">

<form action="/passwordValdt" name="respasswordfrom" method="post" >
		<input type="hidden" name="userid" />	
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
            	<li><a href="#1">产品介绍</a></li>
                <li>|</li>
                <li><a href="#1">帮助中心</a></li>
                <li>|</li>
                <li><a href="#1" onclick="shoucang();" rel="sidebar">设为收藏</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="fgdiv"></div>

<div class="regmain het450">
    	<table class="regtab" align="center" width="980" border="0" cellpadding="0" cellspacing="0">
          <tr height="150">
            <td width="300"></td>
            <td align="center" width="253" ><i>找回密码</i></td>
            <td width="410"></td>
          </tr>
          <tr height="75">
            <td align="right"><label>用户账号：</label></td>
            <td><input type="text" id="registno" name="registno" class="regtext" placeholder="请输入账号/手机号/邮箱"></td>
            
            <td><div id="msgshow" style="display: none;"><span class="xin ml10"></span></div></td>
          </tr>
           <tr height="60"> 
             <td align="right"><label>效验码：</label></td>
            <td><input class="regtext2" id="veryCode" name="veryCode" type="text" onblur="checkImg()"/> 
            <img id="imgObj" alt="" src="/createCode" class="fr"/></td>
            <td><span class="ml10">看不清？<a href="#1" class="als" onclick="changeImg()">换一张</a></span>
            </td>
          </tr>
          <tr height="20">
            <td></td>
          	<td>
          	 <div id="msgshow2" style="display: none;">
          	<span class="xin"></span>
          	</div>
          	</td>
            <td></td>
          </tr>
          <tr height="150">
            <td></td>
            <td><input type="button" value="立即验证" class="userlogin" onmouseover="this.className='userlogin1'" 
            onmouseout="this.className='userlogin'" onclick="checkExsitUser();"></td>
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
</body>
</html>

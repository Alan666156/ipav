<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<title>系统管理</title>
<script type="text/javascript">
	function cusDo(flg){
		$("#passflg").attr("value",flg);
		$("#companyfrom").submit();
	}
	//控制输入字数
	function checkLen(obj) {  

		var maxChars = 100;//最多字符数  

		if (obj.value.length > maxChars)  obj.value = obj.value.substring(0,maxChars);  

		var curr = maxChars - obj.value.length;  

		$("#count").html(curr.toString());
	}
	function showInfoDiv(){		
		if($("#infomation").css("display")=='none'){
			$("#infomation").css("display","block");
		}else{
			var info=$(".textar2").val();
			if(info==''){
				$(".textar2").val("");
				$("#infomation").css("display","none");
			}else{
				cusDo('0');
			}
		}
	}
</script>
</head>

<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">企业用户审核</a></li>
    </ol>
</div>
<form action="/sysattecompany" method="post" name="companyfrom" id="companyfrom">
<input type="hidden" name="companyid" value="${company.companyid }">
<input type="hidden" name="passflg" id="passflg">
<table style="margin-left:88px; margin-top:30px;" border="0" cellpadding="0" cellspacing="0">
  <tr height="340"><!--update-->
    <td width="430" colspan="2">
    	<div class="qyrzs"><img style="width:402px; height:279px" src="${picPath }"></div>
        <div class="qyrzk"></div>
    </td>
    <td valign="top">
    	<table class="zltab mt10">
        	<tr>
            	<td width="100" align="right">企业号：</td>
                <td><c:out value="${company.companyid}"/></td>
            </tr>
            <tr>
            	<td align="right">企业名称：</td>
                <td><c:out value="${company.companyname}"/></td>
            </tr>
            <tr>
            	<td align="right">企业联系人：</td>
                <td><c:out value="${company.contacts}"/></td>
            </tr>
            <tr>
            	<td align="right">联系电话：</td>
                <td><c:out value="${company.mobile}"/></td>
            </tr>
            <tr>
            	<td align="right">企业地址：</td>
                <td><c:out value="${company.address}"/></td>
            </tr>
        </table>
    </td>
  </tr>
  <tr id="infomation" style="display: none;">
    <td width="100" align="left" valign="top">退回修改意见</td>
    <td colspan="2">
    	<textarea class="textar2" name="reason" onkeyup="checkLen(this)"></textarea>
    	<div>您还可以输入 <span id="count" style="color: red;">100</span> 个文字</div>
    </td>
  </tr>
  <tr height="90">
    <td colspan="3">
    	<input type="submit" value="认证" class="butl100 mr30" onmouseover="this.className='butl100m mr30'" 
    	onmouseout="this.className='butl100 mr30'" onclick="cusDo('1')">
        <input type="button" value="退回修改" class="butl100 mr30" onmouseover="this.className='butl100m mr30'" 
        onmouseout="this.className='butl100 mr30'" onclick="showInfoDiv()">
        <input type="submit" value="取消" class="buth100" onmouseover="this.className='buth100m'" onmouseout="this.className='buth100'"
        onclick="location.href='/companylist'">
    </td>
  </tr>
</table>
</form>
</body>
</html>

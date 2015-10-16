<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<script type="text/javascript" src="/pages/js/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="/pages/js/zeroclipboard-2.1.6/dist/ZeroClipboard.min.js"></script>
<title>系统管理</title>
<script type="text/javascript">
	function gotoAdduserrole(){
		//location.href="/gotouseraddrole?companyid="+companyid;
		var chk_value =[];//定义一个数组    
        $('input[name="op"]:checked').each(function(){
        	chk_value.push($(this).val());
        });
		$("#userids").attr("value",chk_value);
		roleuserfrom.action ="/gotouseraddrole";
		roleuserfrom.submit();
	}
	function cusDo(v){
		$("#isatte").attr("value",v);
		search(1); 
	}
	
	function pageClick(){
		search($(".pageol").find("li").find("a.mpages").find("span").html())
	}
	function search(currpage){
		var param = "&page="+(parseInt($("#pageol").find("a.mpages").find("span").html())-1)*10+"&pageSize=10";
		param += "&isatte="+$("#isatte").val();
		$("#senfe").find("tr").not(":first").remove();
		//alert(param);
		$.ajax({url:"roleuserinfos",
			type:"POST",
			data:param,
			success:function(data){
				var c= data.counts;
				if(data.counts!=undefined){
					initPages(currpage, data.counts, 10, pageClick);
					$.each(data.userlist,function(idx,userinfo){
						$("#senfe").append('<tr><td>'
		 						+'<input type="checkbox" name="op" value="'+userinfo.userid+'">'+(idx+1)+'</td>'
		 						+'<td>'+userinfo.userno+'</td>'
		 						+'<td>'+(userinfo.username==undefined?"":userinfo.username)+'</td>'
		 						+'<td>'+(userinfo.rolename==undefined?"":userinfo.rolename)+'</td>'
		 						+'<td>'+(userinfo.orgname==undefined?"":userinfo.orgname)+'</td>'
		 						+'</tr>')
					});
				}
				
			}
		});
		
	}
	
	$(document).ready(function(){
		search(1);
	})
</script>

</head>
<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">员工授权</a></li>
    </ol>
    
</div>
<form action="" method="post" name="roleuserfrom">
	<input type="hidden" name="companyid" id="companyid" value="<c:out value ="${curuser.companyid }"/>">
	<input type="hidden" name="userids" id="userids">
	<input type="hidden" name="isatte" id="isatte">
</form>
<div class="tabBox " style=" margin-top:5px;">
      <p class="rtradio" style="float:left; width:100%; margin-left:0px; line-height:30px; ">
      <input type="radio" name="qysh" <c:if test="${empty isatte}">checked</c:if> onclick="cusDo('')"><label>全部</label>
      <input type="radio" name="qysh" <c:if test="${isatte eq '1'}">checked</c:if>  onclick="cusDo(1)"><label>已授权</label>
      <input type="radio" name="qysh" <c:if test="${isatte eq '0'}">checked</c:if>  onclick="cusDo(0)"><label>未授权</label></p>
</div>
<div class="toolBox" style="margin-top:5px; width:98%; margin-left:15px;">
	<a href="#" class="cur23" onClick="gotoAdduserrole()">员工授权</a>
</div>
<div class="tabBox tablbnr mt" style=" margin-top:10px;">
	<table class="tabinfo" id="senfe" width="100%" cellpadding="0" cellspacing="0">
      <tr class="tabtitle">
        <td>选择</td>
        <td>员工编号</td>
        <td>姓名</td>
        <td>角色名称</td>
        <td>所属机构</td>
      </tr>
    </table>
</div>

<div class="butpageBox tabBox ht80" >
    <div id="pageInfo">
                <div class="rightpagebox fr">
	                <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
	                <a href="#1"><div class="pageys"><span>上一页</span></div></a>
	                <ol id="pageol" class="pageol">
	                    <li><a href="#1" class="mpages" onClick="set_menu4(0)"><div><span>1</span></div></a></li>
	                    <li><a href="#1" onClick="set_menu4(1)"><div><span>2</span></div></a></li>
	                    <li><a href="#1" onClick="set_menu4(2)"><div><span>3</span></div></a></li>
	                    <li><p class="slh">...<p></li>
	                    <li><a href="#1"><div class="pageysn" onClick="set_menu4(3)"><span>10</span></div></a></li>
	                </ol>
	                <a href="#1"><div class="pageys"><span>下一页</span></div></a>
	                <input type="text" class="gopage">
	                <a href="#1"><div class="pageys"><span>GO</span></div></a>
                </div>
     </div>
</div>
</body>
</html>

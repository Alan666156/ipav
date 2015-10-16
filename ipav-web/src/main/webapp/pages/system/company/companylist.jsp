<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<title>系统管理</title>
<script type="text/javascript">
	$(window).resize(function() {
		$(".simScrollContBox7").css({
	        position: "absolute",
	        left: ($(window).width() - $(".simScrollContBox7").outerWidth()) / 2,
	        top: ($(window).height() - $(".simScrollContBox7").outerHeight()) / 2
	    });
	});
	function cusDo(stus){
		$("#attstuts").attr("value",stus);
		$("#companyfrom").submit();
		
	}
	var pageSize=10;
	$(function(){
		$(window).resize();
		//初始化遮罩层高度
		$("#simScrollCont").css("height",$("#mainFrame",window.parent.document).height());
		$("input[name='qysh']").bind("click", initInfo);
		initInfo();
	});
	function initInfo(){
		$("#pageol").find("a.mpages").find("span").html("1");
		queryCompanys();
	}
	function queryCompanys(){
		var startRow=parseInt($("#pageol").find("a.mpages").find("span").html());
		startRow=isNaN(startRow)?1:startRow;
		var data = {
				"attstuts" :$("input[name='qysh']:checked").val(),
				"beginRow":(startRow-1)*pageSize,
				"pageSize":pageSize
		};
		sendAjax("/queryCompanys",data);
	}
	function sendAjax(url,data){
		$.post(url, data, function(arr) {
			var count=0;
			if(isNotNull(arr)){
				createCompanyHtml(arr.list,arr.size);
				count=arr.size;
			}
			//初始化分页
			initPages($(".pageol").find("li").find("a.mpages").find("span").html(),count, pageSize, queryCompanys);
		});
	};
	function createCompanyHtml(list,count){
		var companyHTML="";
		$.each(list,function(i,v){
			companyHTML+="<tr><td>"+(i+1)+"</td><td>"+v.companyname+"</td><td>"+v.contacts+"</td><td>"+v.mobile+"</td><td>"+v.attetime+"</td>";
			if(v.attstuts==2){
				companyHTML+="<td>待认证</td><td><input type='button' value='审核' class='kedbut' onmouseover='overout(0,this)' onmouseout='overout(1,this)' onclick='sendReq(1,"+v.companyid+")' />";
			}else if(v.attstuts==1){
				companyHTML+="<td>已认证</td><td><input type='button' value='取消认证' class='qxbut' onclick='sendReq(0,"+v.companyid+")' />";
			} 
			companyHTML+="</tr>";
		});
		$("#companybody").html(companyHTML);
	}
	function overout(t,obj){
		if(t==0){
			$(obj).removeClass("kedbut");
			$(obj).addClass("kedbutm");
		}else if(t==1){
			$(obj).removeClass("kedbutm");
			$(obj).addClass("kedbut");
		}
	}
	function hiddDiv(){
		$("#errinfo").html("");
		$("#hid_company_id").val("");
		$("#description").val("");
		$("#simScrollCont").hide();
		$("#simScrollContBox").hide();
	}
	function sub_data(){
		$("#errinfo").html("");
		var info=$("#description").val();
		if(info==''){
			$("#errinfo").html("请输入取消认证原因");
			return;
		}else{
			atteFrom.submit();
		}
	}
	function sendReq(t,cid){
		var actionurl;
		if(t==1){//审核认证
			location.href="/companyattveiw?companyid="+cid;
		}else if(t==0){//取消认证
			$("#hid_company_id").val(cid);
			$("#simScrollCont").show();
			$("#simScrollContBox").show();
		}
	}
function sendReqAfter(cid){
	location.href="/restcompanyatte?companyid="+cid;
}
</script>

</head>
<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">企业用户审核</a></li>
    </ol>
</div>

<div class="tabBox " style=" margin-top:5px;">
      <p class="rtradio" style="float:left; width:100%; margin-left:0px; line-height:30px; ">
	      <input type="radio" name="qysh" value="" checked/><label>全部</label>
    	  <input type="radio" name="qysh" value="1"/> <label>已认证</label>
    	  <input type="radio" name="qysh" value="2"/><label>待认证</label>
      </p>
</div>
<form action="/companylist" method="post" name="companyfrom" id="companyfrom">
<input type="hidden" name="attstuts" id="attstuts">
<div class="tabBox tablbnr mt" style=" margin-top:10px;">
	<table class="tab2015" width="100%" cellpadding="0" cellspacing="0">
      <tr id="tab2015tr">
        <td width="50">序号</td>
        <td>企业名称</td>
        <td>企业联系人</td>
        <td>联系人电话</td>
        <td>认证时间</td>
        <td width="130">状态</td>
        <td width="100">操作</td>
      </tr>
      <tbody id="companybody"></tbody>
    </table>
</div>
</form>
<div class="ptpageBox" id="pageInfo" style="width:97.5%; margin-left:15px;">	
    <div class="rightpagebox fr">
	  <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
          <a href="javascript:;"><div class="pageys"><span>上一页</span></div></a>
          <ol id="pageol" class="pageol">
              <li><a href="javascript:;" class="mpages" onClick="set_menu4(0)"><div><span>1</span></div></a></li>
              <li><a href="javascript:;" onClick="set_menu4(1)"><div><span>2</span></div></a></li>
              <li><a href="javascript:;" onClick="set_menu4(2)"><div><span>3</span></div></a></li>
              <li><p class="slh">...<p></li>
              <li><a href="javascript:;"><div class="pageysn" onClick="set_menu4(3)"><span>10</span></div></a></li>
          </ol>
          <a href="javascript:;"><div class="pageys"><span>下一页</span></div></a>
          <input type="text" class="gopage">
          <a href="javascript:;"><div class="pageys"><span>GO</span></div></a>
    </div>
</div>
<!-- 取消认证消息提示框 -->
<div id="simScrollCont" class="simScrollCont"></div>
<div class="simScrollContBox7" id="simScrollContBox">
  <div class="simScrollContBoxs7">
  <form action="/sysattecompany" name="atteFrom" method="POST" >	
  	<div class="tctitle">取消企业认证<a href="javascript:;" onClick="hiddDiv()" title="关闭"></a></div>
      <table width="100%" align="center" class="qytable mt20" border="0">
          <tr height="120">
            <td><label>取消原因：</label></td>
            <td><textarea class="pttexta" id="description" name="reason" style="width:300px;"></textarea>
            	<p class="errormsg2"><font color="red"><span id="errinfo" ></span></font></p>
            </td>
          </tr>
         <tr height="60">
         	<td></td>
            <td colspan="3">
              <input type="button" class="pttcBut85" value="确认" onClick="sub_data()">
              <input type="button" class="pttcButh85 ml30" value="取消" onClick="hiddDiv()">
              <input type="hidden" id="hid_company_id" name="companyid" />
              <input type="hidden"  name="passflg"  value="0"/>
             </td>
         </tr>
       </table>
   </div>
</div>
</body>
</html>

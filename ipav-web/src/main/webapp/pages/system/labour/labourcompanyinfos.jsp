<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">劳动合同单位</a></li>
    </ol>
</div>

<div class="toolBox" style="margin-top:15px; width:97.5%; margin-left:15px;">
	<a href="#1" class="cur8" onClick="showtsBoxt5()">新增单位</a>
	<a href="#1" class="cur3" onClick="getDetail()">修改</a>
	<a href="#1" class="cur4" onClick="deleteInfoAfter();">删除</a>
</div>
<div class="ptx"></div>
<div class="xttabBox">
	<table class="tab2015" id="pttable" width="100%" cellpadding="0" cellspacing="0">
      <tr id="tab2015tr">
        <td width="80">序号</td>
        <td>单位名称</td>
        <td width="35%">描述</td>
      </tr>
      <tr>
        <td align="center"><input type="checkbox">&nbsp;&nbsp;1</td>
        <td>上海天道启科电子有限公司</td>
        <td>上海办事处</td>
      </tr>
      <tr>
        <td align="center"><input type="checkbox">&nbsp;&nbsp;2</td>
        <td>北京天道启科电子有限公司</td>
        <td>北京办事处</td>
      </tr>
      <tr>
        <td align="center"><input type="checkbox">&nbsp;&nbsp;3</td>
        <td>深圳天道启科电子有限公司</td>
        <td>深圳办事处</td>
      </tr>
</table>
</div>
<div class="ptpageBox" style="width:970px;" id="pageInfo">
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

<!--新增职务  begin-->
<div id="simalphat5" class="simalpha">
</div>
<div class="simaltBoxt5" id="simaltBoxt5">
	<div class="simaltBoxst5">
		<div class="tctitle">新增劳动合同单位<a href="#1" onClick="hidetsBoxt5()" title="关闭"></a></div>
		<table class="ggtab  ml5 mt5"  width="100%" cellpadding="0" cellspacing="0">
			<tr height="65">
				<td align="right" width="85"><label class="xin">单位名称：</label></td>
                <td><input type="text" class="xtzwwhtext" /><p id="hetongNameMsg5" class="errormsg2"></p></td>
			</tr>
			<tr height="65">
				<td align="right"><label>单位描述：</label></td>
                <td><textarea class="xtzwwhtexta"></textarea></td>
			</tr>
		</table>
		<div class="jgtcbutBox" align="center">
        	<input type="button" class="yes mt15" value="确认" onClick="addLabourCompany()">
            <input type="button" class="no mt15 ml30" value="取消" onClick="hidetsBoxt5()">
        </div>
	</div>
</div>
  
<!--新增职务 end-->

<!--修改职务  begin-->
<div id="simalphat6" class="simalpha">
</div>
<div class="simaltBoxt5" id="simaltBoxt6">
	<div class="simaltBoxst5">
		<div class="tctitle">修改劳动合同单位<a href="#1" onClick="hidetsBoxt6()" title="关闭"></a></div>
		<input type="hidden" value="" />
		<table class="ggtab  ml5 mt5"  width="100%" cellpadding="0" cellspacing="0">
			<tr height="65">
				<td align="right" width="85"><label class="xin">单位名称：</label></td>
                <td><input type="text" class="xtzwwhtext" /><p id="hetongNameMsg" class="errormsg2"></p></td>
			</tr>
			<tr height="65">
				<td align="right"><label>描述：</label></td>
                <td><textarea class="xtzwwhtexta"></textarea></td>
			</tr>
		</table>
		<div class="jgtcbutBox" align="center">
        	<input type="button" class="yes mt15" value="确认" onClick="updatePlatform()">
            <input type="button" class="no mt15 ml30" value="取消" onClick="hidetsBoxt6()">
        </div>
	</div>
</div>
  
<!--修改职务 end-->

<!--弹出提示 begin-->
<div id="simalpha3" class="simalpha">
</div>
	<div class="simaltBox3" id="simaltBox3">
    	<div class="simaltBoxs3">
        <div class="tctitle">提示信息<a href="#1" onClick="hidetsBox3()" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        	<p class="alertmsg" id="warnning">XXX，你即将修改XXXX的个人信息，请慎重修改，不然你就联系不到他了！</p>
        </div>
        <div class="alertbut">
            <input type="button" value="否" class="no fr mr20"  onClick="hidetsBox3()">
        	<input type="button" value="是" class="yes fr mr20" onClick="hidetsBox3()">
        </div>

        </div>
    </div>
  
<!--弹出提示 end-->
</body>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript" src="/pages/js/system/labour/labour.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript">
function onloalrame(){
	var winhight = document.documentElement.clientHeight;
	$(".simalpha").height(winhight);
	}
	window.onload=onloalrame;
$(function(){
	$('input').placeholder();
		
});

$(document).ready(function(){  
//鼠标移到tr上变色  	
	$("#pttable tr").mouseover(function(){  
		$(this).css("background","#fafafa");  
		}); 
		$("#pttable tr").mouseout(function(){  
		$(this).css("background","");  
	}); 
});
//根据窗口大小调整弹出框的位置
$(window).resize(function() {
	$(".simaltBox3").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBox3").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBox3").outerHeight()) / 2
    });
	
	$(".simaltBoxt5").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBoxt5").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBoxt5").outerHeight()) / 2
    });
	
	$(".simaltBoxt6").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBoxt6").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBoxt6").outerHeight()) / 2
    });
})

$(function() {
    $(window).resize();
});

</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统管理</title>
</head>
<body class="ptbg" onLoad="onloalrame()"> 
<div class="pt_center rsglBox"> 
<c:if test="${!empty wid}"><input type="hidden" id="idInput" value="<c:out value ='${wid}'/>"/></c:if>
    <!---center begin-->
    
    <div class="pt_maincenBox mt12" id="mainBox">
    
        <div class="pttitle" style="width:970px;">
            <ol class="centmenu" id="centmenu">
                <li><a href="#1" class="current">我的工资</a></li>
            </ol>
        </div>
        
        <div class="tabBox serchBox" style="margin-left:15px; margin-top:15px;">
        <ol>
            <li>工资期间：
            <input id="minMonth" class="pt_text pttime" name="minMonth" readonly="readonly"  value=""
							type="text" onClick="WdatePicker({dateFmt:'yyyy年M月',maxDate:'#F{$dp.$D(\'maxMonth\')}'})">
			至				
            <input id="maxMonth" class="pt_text pttime" name="maxMonth" readonly="readonly"  value=""
							type="text" onClick="WdatePicker({dateFmt:'yyyy年M月',minDate:'#F{$dp.$D(\'minDate\')}',maxDate:'%y-%M'})">
			</li>
            <li class="ml20"><input type="button" value="查询" onclick="searchClick()" class="ptserch"></li>
        </ol>
        </div>
		
        <div class="ptx"></div>
      	<table class="pttable"  width="970" border="0" cellspacing="0" cellpadding="0">
            <tr class="ptitles">
                <th width="100" style="text-align:center">序号</th>
                <th width="235" style="text-align:center">工资所属期间</th>
                <th width="235" style="text-align:center">工资发放日期</th>
                <th width="200" style="text-align:left">应发工资</th>
                <th width="200" style="text-align:left">实发工资</th>
            </tr>
        </table>
        <div id="tbBoxgdt">
            <table id="wagelist" class="pttable"  width="970" border="0" cellspacing="0" cellpadding="0">
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
    </div>
    <!-- center end--->




<!--工资详情-->
<div id="simScrollCont7" class="simScrollCont">
</div>
	<div class="simScrollContBox3" id="simScrollContBox7">
    	<div class="simScrollContBoxs3">
        <div class="tctitle"><label>工资期间：2014年10月</label><a href="#1" onClick="hidejg4()" title="关闭"></a></div>
        
 
      <div class="ptx"></div>
      	<table class="pttable"  width="670" border="0" cellspacing="0" cellpadding="0">
            <tr class="ptitles">
                <th width="100" style="text-align:center">序号</th>
                <th width="235" style="text-align:center">工资项目</th>
                <th width="235" style="text-align:center">金额</th>
                <th width="200" style="text-align:left">释疑人</th>
            </tr>
        </table>
        <div id="tbBoxgdt2">
            <table id="myWageInfo" class="pttable"  width="670" border="0" cellspacing="0" cellpadding="0">
        	</table>
        </div>

        </div>
    </div>

<!-- 错误提示框 -->
<div id="simScrollCont12" class="simScrollCont">
</div>
	<div class="simScrollContBox4" id="simScrollContBox12">
    	<div class="simScrollContBoxs4">
        <div class="tctitle">提示信息<a href="#1" onClick="hidejg12()" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        	<p id="warnning" class="alertmsg">XXX，dafadsfadf你即将修改XXXX的个人信息，请慎重修改，不然你就联系不到他了！</p>
        </div>
        <div class="alertbut">
            <input type="button" value="否" class="no fr mr20"  onClick="hidejg12()">
        	<input type="button" value="是" class="yes fr mr20" onClick="hidejg12()">
        </div>

        </div>
    </div>
</body>

<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/pages/js/wage/ownerwage.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>

<script language="javascript">
	function onloalrame(){
		var winhight = window.parent.document.documentElement.clientHeight-114;
		var mainboxs = winhight-12;
		var tabBoxa = winhight-157;
		document.getElementById("tbBoxgdt").style.height=mainboxs+"px";
		document.getElementById("tbBoxgdt").style.minHeight=tabBoxa+"px";
		document.getElementById("tbBoxgdt").style.maxHeight=tabBoxa+1+"px";
		document.getElementById("tbBoxgdt").style.overflowY="auto";
		document.getElementById("tbBoxgdt").style.width=985+"px";
		document.getElementById("tbBoxgdt").style.overflowX="hidden";
		
		document.getElementById("tbBoxgdt2").style.minHeight=290+"px";
		document.getElementById("tbBoxgdt2").style.maxHeight=290+1+"px";
		document.getElementById("tbBoxgdt2").style.overflowY="auto";
		document.getElementById("tbBoxgdt2").style.width=685+"px";
		document.getElementById("tbBoxgdt2").style.overflowX="hidden";
		$("#140",window.parent.document).height($("body").height());
		}
		window.onload=onloalrame;
		
		
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

	$(".simScrollContBox3").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox3").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox3").outerHeight()) / 2
    });
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
</html>
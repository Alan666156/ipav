<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript">
$(function(){
			$('input').placeholder();
		});

</script>
<script type="text/javascript">
function getCurDate()
{
 var d = new Date();
 var week;
 switch (d.getDay()){
 case 1: week="星期一"; break;
 case 2: week="星期二"; break;
 case 3: week="星期三"; break;
 case 4: week="星期四"; break;
 case 5: week="星期五"; break;
 case 6: week="星期六"; break;
 default: week="星期天";
 }
 var years = d.getYear()-100;
 var month = add_zero(d.getMonth()+1);
 var days = add_zero(d.getDate());
 var hours = add_zero(d.getHours());
 var minutes = add_zero(d.getMinutes());
 var seconds=add_zero(d.getSeconds());
 var ndate = years+"年"+month+"月"+days+"日  " +hours+ ":"+minutes+":"+seconds+"&nbsp;&nbsp;&nbsp;&nbsp;"+week;
 divT.innerHTML= ndate;
}

function add_zero(temp)
{
 if(temp<10) return "0"+temp;
 else return temp;
}

setInterval("getCurDate()",100);

//按enter键触发搜索框
function CheckEnterKey(evt) 
{ 
if(evt.keyCode == 13) 
{	 
 window.location.href=document.getElementById("sercha").href;
 alert("d");
} 
} 

$(document).ready(function(){
	$("#logo").attr("src",$('#logo', window.parent.document).val());
	$("#companyName").html($('#companyName', window.parent.document).val());
})

function quit(){
	$.ajax({
		url:"/quit",
		type:"POST",
		success:function(data){
			console.log(data)
			if(data.result!=undefined)
				window.parent.location.href=data.result;
		}
	})
}
function refreshLogo(){
	var companyid=$('#companyId', window.parent.document).val();
	$.post("/getCompanyLOGO",{"companyid":companyid},function(data){
		if(data!=null)
			$("#logo").attr("src",data.logo);
	});
}
</script>
</head>

<body class="pt-body"> 

<div class="pt-top">
	<div class="pt-topcen">
    	<div class="pt-qylogo" onclick="refreshLogo()">
        	<img id="logo" src="/pages/images/platform/qylogo.png">
            <p id="companyName">天道启科</p>
        </div>
        <div class="pt-top-rt">
    		<div class="pt-rt-logo"></div>
            <a href="#1" class="pt-close" onclick="quit()">安全退出</a>
            <p id="divT" class="pt-time mr20"></p>
    	</div>
    </div>
</div>

<div class="pt-tfg"></div>

<div class="pt-tmenu">
	<div class="toolmenu">
    	<ol>
        	<li><div id="mainframe" class="ptworks">工作平台</div></li>
            <!-- <li><div>我的工资</div><a href="#1" class="pt-muclose" title="关闭"></a></li> -->
        </ol>
    </div>
	<div class="pt-serach">
		<input type="text" class="pt-serachwb" placeholder="请输入查询内容">
       	<a href="#1" class="sercha"></a>
    </div>
</div>

<div id="simScrollCont12" class="simScrollCont"></div>
<!-- 说说图片遮罩层 -->
<div id='fatherMask' class='uphotobg'></div>
<!-- 普通遮罩层-->
<div id='fatherScrollCont' class='simScrollCont' ></div>
</body>
</html>

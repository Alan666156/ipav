<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="ptbg" onLoad="onloalrame()"> 
<input type="hidden" id="voteId" value="${id}">

<div class="pt_center rsglBox">

    <!---center begin-->
    <div class="pt_maincenBox mt12" id="mainBox">
    
        <div class="pttitle" style="width:970px;">
            <ol class="centmenu" id="centmenu">
            	<li><a href="#1" onclick="goback()">投票中心</a></li>
                <li><a href="#1" class="current">投票结果</a></li>
            </ol>
        </div>
        
        <div class="faqiBox">
        	<div class="fqwt">
            	<div class="user-tx"><img src="/pages/images/platform/userimg/us1.png"></div>
                <p class="user-msg2"><a href="#1">张小明</a><span>发起</span></p>
                <i class="dqtime" id="dqtime"></i>
            </div>
            <p class="xuxinfo">各位同事投票猜猜明天天气怎么样?</p>
            <p class="tptsmsg mt5">投票规则：选项类型的投票，每人最少获得两次投票机会。</p>
            <p class="tptsmsg mb8">截止日期：2014年12月2日  5点00分</p>
        </div>
		        
        <div class="xuxiangBox wt970s" style="margin-top:15px;" id="xuxiangBox">
        	
            <div class="xuxinfoxs xztoup">
            	<p class="xuxinfo2">明日气象台于9时发布“霾黄色预警信号”。</p>
            	<div class="tpjguo">
                	<a href="#1" class="awit" title="查看大图"><div class="xuimgs"><img src="/pages/images/platform/xitp1.png"><p href="#1" class="ckimgs"></p></div></a>
                	<div class="tpjdBox">
                    	<div class="tpjdBox2 jdys1"><div class="jdys1-2"></div></div><span>30票<label>(70%)</label></span>
                    </div>
                </div>
            </div>
            
        </div> 
        
        <div class="wt970s" style="margin-bottom:30px; margin-top:15px;">
        	<input type="button" class="pttcBut85 ml20" value="返回" onclick="goback()">
            <input type="button" class="pttcBut85 ml30" value="刷新" onclick="initPage()">
        </div>
            
    </div>
    <!-- center end--->
</div>
</body>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript">
	/* function onloalrame(){
		var winhight = window.parent.document.documentElement.clientHeight;
		var mainboxs = winhight-12;
		var tabbox6 = winhight-314;
		if(mainboxs<150)
			document.getElementById("mainBox").style.height="auto";
		else if(mainboxs>230)
			document.getElementById("mainBox").style.height=mainboxs+"px";
		$("#142",window.parent.document).height($("body").height());
	} */
	 
	function onloalrame(){
		var winhight = window.parent.document.documentElement.clientHeight;
		var mainboxs = winhight-12;
		var tabbox6 = winhight-314;
		var tpnum =  $(".rsglBox").height();
		if(tpnum>mainboxs)
			{
			$(".mainBox").height(tpnum);
			$("#142",window.parent.document).height(tpnum);
			}
		else if(tpnum<mainboxs)
			{
			$(".mainBox").height(mainboxs);
			$("#142",window.parent.document).height(mainboxs);
			}
	}
	window.onload=onloalrame;
	
	//获取当前时间
	function CurentTime(){ 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日
        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var clock = year + "年";
        if(month < 10)
            clock += "0";
        clock += month + "月";
        if(day < 10)
            clock += "0";
        clock += day + "日 ";
        if(hh < 10)
            clock += "0";
        clock += hh + "点";
        if (mm < 10) clock += '0'; 
        clock += mm+"分"; 
        dqtime.innerHTML= clock;
    } 
	setInterval("CurentTime()",1000);
	
	function initPage(){
		$("#xuxiangBox").html('');
		$.ajax({
			url:"getVoteInfo",
			type:"POST",
			data:"optType=2&id="+$("#voteId").val(),
			dataType:"json",
			success:function(data){
				$("div.user-tx").find("img").attr("src",data.infos[0].pic==""?"":(data.userpath+data.infos[0].pic));
				$("p.user-msg2").find("a").html(data.infos[0].username);
				$("p.xuxinfo").html(data.infos[0].title);
				$("p.mt5").html(data.infos[0].remark);
				$("p.mb8").html("截止日期："+data.infos[0].endTime.replace("-0","年").replace("-0","月").replace("-","年").replace("-","月").replace(" ","日")+"点00分");
				console.log(data.infos[0].itemList)
				$.each(data.infos[0].itemList,function(idx,item){
					initItems(item,data.picpath,data.infos[0].counts);
				})
				onloalrame();
				$(".ckimgs").on("click",function(para){		 
					imglook([$(this).parent("div").find("img")[0]],0);
				});
			}
		});
		
	}
	
	$(document).ready(function(){
		initPage();
	})
		
	function initItems(item,picpath,counts){
		$("#xuxiangBox").append('<div class="xuxinfoxs'+(item.is_chosen=='是'?' xztoup':'')+'"><p class="xuxinfo2">'+item.item_name+'</p><div class="tpjguo">'+
				(item.pic==''?'':'<a href="#1" class="awit" title="查看大图"><div class="xuimgs"><img src="'+(item.pic==''?'':(picpath+item.pic))+'"><p href="#1" class="ckimgs"></p></div></a>')+
				'<div class="tpjdBox"><div class="tpjdBox2 jdys3"><div class="jdys3-2" style="width:'+(item.score*100/counts)+'%"></div></div><span>'+item.score+'票<label>('+(parseInt(item.score)*100/parseInt(counts))+'%)</label></span></div></div></div>');
	}
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="ptbg" > 
<input type="hidden" id="voteId" value="com.ipav:ipav-web:war:1.0.0-SNAPSHOT">
<div class="pt_center rsglBox">

    <!---center begin-->
    <div class="pt_maincenBox mt12" id="mainBox">
    
        <div class="pttitle" style="width:970px;">
            <ol class="centmenu" id="centmenu">
            	<li><a href="#1" onclick="goback()">投票中心</a></li>
                <li><a href="#1" class="current">我要投票</a></li>
            </ol>
        </div>
        
        <div class="faqiBox">
        	<div class="fqwt">
            	<div class="user-tx"><img src="/pages/images/platform/userimg/us1.png"></div>
                <p class="user-msg2"><a href="#1">张小明</a><span>发起</span></p>
                <i class="dqtime" id="dqtime"></i>
            </div>
            <p class="xuxinfo">下面是我南京出差所拍的美景大家评评分！?</p>
            <p class="tptsmsg mt5">投票规则：请输入1至10分以内的整数型评分,必评	，最多可评选两张。</p>
            <p class="tptsmsg mb8">截止日期：2014年12月2日  5点00分</p>
        </div>
		
        <div class="tpxinx wt970s">
        	<p class="jcp">需评分对象<i></i>个，评选结果取平均分最高前<i></i>名</p><span class="fl ml20"><input type="checkbox" id="ycimg" onClick="hideimg()"><label for="ycimg">隐藏图片</label></span>
        </div>
        
        <div class="xuxiangBox wt970s" id="xuxiangBox">
        	
            <div class="xuxinfoxspf" >
                <a href="#1"><div class="pfimg"><img src="/pages/images/platform/xitp1.png"><p href="#1" class="ckimg"></p></div></a>
                <p class="pfname">南京夫子庙风光</p>
                <p class="pfinfo">评分<input type="text" class="pftext"></p>
            </div>
        </div> 
        
        <div class="wt970s" style="height:40px;">
        	<p class="ml20">匿名投票</p>
        </div>
        
        <div class="wt970s" style="margin-bottom:30px;">
        	<input type="button" class="pttcBut85 ml20" value="确认" onclick="cast()">
            <input type="button" class="pttcBut85 ml30" value="查看结果" onclick="showResult()">
            <input type="button" class="pttcButh85 ml30" value="取消" onclick="goback()">
        </div>
            
    </div>
    <!-- center end--->
    <div id="simScrollCont12" class="simScrollCont"></div>
	<div class="simScrollContBox4" id="simScrollContBox12">
    	<div class="simScrollContBoxs4" >
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
</div>
</body>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/vote/castVote.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript">
	function showResult(){
		

		$.ajax({
			url:"validateAuthor",
			type:"POST",
			data:"id="+$("#voteId").val()+"&type=3",
			dataType:"json",
			success:function(data){
				if(data!=undefined&&data.result!=undefined&&data.result==1){
					self.location="scoreInfo?id="+$("#voteId").val();
				}else {
					//$("#warnning").html("您暂无查看权限");
					//showjg12();
					msgBoxTohome("警告","您暂无查看权限");
				}
			}
		})
	}

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
	setInterval("CurentTime()",100);
	
	//隐藏图片
	function hideimg(){
		var dscheck = document.getElementById("ycimg").checked;
		var imgs =  document.getElementById("xuxiangBox");
		var imglist = imgs.getElementsByTagName("a");
		 if(dscheck == true){
			for(var i=0; i<imglist.length;i++)		
				imglist[i].className="dn";
		 }else{
			for(var i=0; i<imglist.length;i++)	
				imglist[i].className="bk";
		 }
	}
	
	var jsonObj;

	$(document).ready(function(){
		$("#xuxiangBox").html('');
		$.ajax({
			url:"getVoteInfo",
			type:"POST",
			data:"optType=1&id="+$("#voteId").val(),
			dataType:"json",
			success:function(data){
				$("div.user-tx").find("img").attr("src",data.infos[0].pic==""?"":(data.userpath+data.infos[0].pic));
				$("p.user-msg2").find("a").html(data.infos[0].username);
				$("p.xuxinfo").html(data.infos[0].title);
				$("p.mt5").html(data.infos[0].remark);
				$("p.mb8").html("截止日期："+data.infos[0].endTime.replace("-0","年").replace("-0","月").replace("-","年").replace("-","月").replace(" ","日")+"点00分");
				$("#voteType").html(data.infos[0].isAnonymout=='0'?'':'匿名投票');
				$(".jcp").find("i").html(data.infos[0].minNum);
				jsonObj=$.parseJSON(data.infos[0].ext);
				$.each(data.infos[0].itemList,function(idx,item){
					initScoreItems(item,jsonObj,data.infos[0].minNum,data.picpath);
				});
				if(data.infos[0].castedInfos!=undefined)
					$.each(data.infos[0].castedInfos,function(idx,castedInfo){
						$.each($("#xuxiangBox").find('input[type="hidden"]'),function(inputIdx,input){
							if($(input).val()==castedInfo.item_id){
								$(input).parent().find('input[type="text"]').val(castedInfo.score);
								return false;
							}
						})
					})
				//initClick();
					$(".ckimg").on("click",function(para){		 
						imglook([$(this).parent("div").find("img")[0]],0);
					});
				onloalrame();
			}
		});
	})
	
	/* function inputCheckInt(param,max,min,type){
		var intreg=/^[1-9]+\d*$/;
		var floatreg=/^([1-9]+\d*)|(\d+\.\d*[1-9])$/
		if(type==0){
			if(!intreg.test($(param).val()))
				return false;
		}else{
			if(!floatreg.test($(param).val()))
				return false;
		}
		if($(param).val()>max||$(param).val()<min){
			msgBoxTohome("警告","只能输入"+min+"~"+max+"之间的数值");
			return false;
		}
		return true;
	} */
	
	function cast(){
		var param='voteId='+$("#voteId").val();
		var count=0;
		var id_score='[';
		var flag=false;
		$.each($("div.xuxinfoxspf"),function(idx,div){
			if($(div).find('input[type="text"]').val().trim()!=''){
				if(!checkScore(jsonObj.maxScore,jsonObj.minScore,jsonObj.scoreType,$($(".jcp").find("i")[0]).html(),$(div).find('input[type="text"]'))){
					msgBoxTohome("警告","评分只能输入"+jsonObj.minScore+"~"+jsonObj.maxScore+"的数值");
					flag=true;
					return;
				}
				++count;
				id_score+='{"id":'+$(div).find('input[type="hidden"]').val()+',"score":'+$(div).find('input[type="text"]').val().trim()+'},'
			}
		});
		if(flag)
			return;
		if(count>parseInt($($(".jcp").find("i")[0]).html())){
			//$("#warnning").html("只能选择"+$($(".jcp").find("i")[0]).html()+"项进行投票");
			//showjg12();
			msgBoxTohome("警告","只能选择"+$($(".jcp").find("i")[0]).html()+"项进行投票");
			return false;
		}
		
		id_score=id_score.substr(0,id_score.length-1)+']';
		param+='&id_score='+id_score;
		castVote(param);
	}
	function initScoreItems(item,jsonObj,minNum,picpath){
		$("#xuxiangBox").append('<div class="xuxinfoxspf"><input type="hidden" value="'+item.id+'"/><a href="#1"><div class="pfimg"'+(item.pic==''?' style="display:none"':'')+'><img src="'+(item.pic==''?'':(picpath+item.pic))+'"><p href="#1" class="ckimg"></p></div></a>'+
				'<p class="pfname">'+item.itemName+'</p><p class="pfinfo">评分<input type="text" class="pftext" onblur="checkScore('+jsonObj.maxScore+','+jsonObj.minScore+','+jsonObj.scoreType+','+minNum+',this)"></p><i class="errmsg16"></i></div>')
	}
	
	function checkScore(maxScore,minScore,scoreType,minNum,obj){
		$(obj).parent().next().html('');
		var count=0;
		$.each($('input.pftext'),function(idx,input){
			if($(input).val().trim()!='')
				++count;
		});
		if(count>parseInt(minNum)){
			//$("#warnning").html("只能选择"+$($(".jcp").find("i")[0]).html()+"项进行投票");
			//showjg12();
			msgBoxTohome("警告","只能选择"+$($(".jcp").find("i")[0]).html()+"项进行投票");
			return false;
		}
		if($.trim($(obj).val())=='')
			return true;
		if(scoreType=='0'){
			if(!/^[1-9]+\d*$/.test($(obj).val())){
				$(obj).parent().next().html("评分只能输入整数");
				return false;
			}else{
				if(parseInt($(obj).val())>parseInt(maxScore)){
					$(obj).parent().next().html("评分只能输入不大于"+maxScore+"的整数");
					return false;
				}else if(parseInt($(obj).val())<parseInt(minScore)){
					$(obj).parent().next().html("评分只能输入不小于"+maxScore+"的整数");
					return false;
				}
			}
		}else{
			if(!/^(\d+\.\d{2}|[1-9]+\d*|0)$/.test($(obj).val())){
				$(obj).parent().next().html("评分只能输入精度最大为2位的实数");
				return false;
			}else{
				if(parseFloat($(obj).val())>parseFloat(maxScore)){
					$(obj).parent().next().html("评分只能输入不大于"+maxScore+"的实数");
					return false;
				}else if(parseFloat($(obj).val())<parseFloat(minScore)){
					$(obj).parent().next().html("评分只能输入不小于"+maxScore+"的实数");
					return false;
				}
			}
		}
		return true;
	}
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="ptbg" onLoad="onloalrame()"> 
<input type="hidden" value="com.ipav:ipav-web:war:1.0.0-SNAPSHOT" id="voteId" />
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
            <p class="xuxinfo">各位同事投票猜猜明天天气怎么样?</p>
            <p class="tptsmsg mt5">投票规则：选项类型的投票，每人最少获得两次投票机会。</p>
            <p class="tptsmsg mb8">截止日期：2014年12月2日  5点00分</p>
        </div>
		
        <div class="tpxinx wt970s">
        	<p class="jcp">请点击投票，必选<i></i>项</p>
        </div>
        <div class="tpxinx wt970s">
            
            <div class="xzhouTs xzh">
                 <div class="ts-xzinfo">
                    <div class="ts-xzinfoimg"><img src="/pages/images/platform/pax.png"></div>
                    <div class="ts-xzinfomsg">
                        <p>张拉拉</p>
                        <p>东一区销售员</p>
    
                    </div>
                 </div>
            </div>
            
            <div class="xzhouTs xzh">
                 <div class="ts-xzinfo">
                    <div class="ts-xzinfoimg"><img src="/pages/images/platform/pax.png"></div>
                    <div class="ts-xzinfomsg">
                        <p>张拉拉</p>
                        <p>东一区销售员</p>
    
                    </div>
                 </div>
            </div>
            
        </div>
        
        <div class="toolBox" style="width:968px;">
        	<span class="paixspan">排序：</span>
        	<a href="#1" class="cur11">部门</a>
            <a href="#1" class="cur12">拼音</a>
        </div>
        
        <div class="tsinfoBox">
	    	
            <div class="tsinfo tsclass">
                <div class="tsinfo2">
                    <div class="ts-xzinfoimg"><img src="/pages/images/platform/pax.png"></div>
                    <div class="tsinfomsg">
                        <p>张拉拉</p>
                        <p>东一区销售员</p>
                    </div>
                    <input type="button" value="取消选择" class="tsxzbutls">
	    		</div>
	    	</div>
            
            <div class="tsinfo tsclass">
                <div class="tsinfo2">
                    <div class="ts-xzinfoimg"><img src="/pages/images/platform/pax.png"></div>
                    <div class="tsinfomsg">
                        <p>张拉拉</p>
                        <p>东一区销售员</p>
                    </div>
                    <input type="button" value="取消选择" class="tsxzbutls">
	    		</div>
	    	</div>
            
            <div class="tsinfo">
                <div class="tsinfo2">
                    <div class="ts-xzinfoimg"><img src="/pages/images/platform/pax.png"></div>
                    <div class="tsinfomsg">
                        <p>张拉拉</p>
                        <p>东一区销售员</p>
                    </div>
                    <input type="button" value="投她一票" class="tsxzbutls2">
	    		</div>
	    	</div>
            
            <div class="tsinfo">
                <div class="tsinfo2">
                    <div class="ts-xzinfoimg"><img src="/pages/images/platform/pax.png"></div>
                    <div class="tsinfomsg">
                        <p>张拉拉</p>
                        <p>东一区销售员</p>
                    </div>
                    <input type="button" value="投他一票" class="tsxzbutls2">
	    		</div>
	    	</div>
            
            
            
        </div> 
        
        <div class="wt970s" style="height:40px; margin-top:15px;">
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
	        	<p id="warning" class="alertmsg">XXX，dafadsfadf你即将修改XXXX的个人信息，请慎重修改，不然你就联系不到他了！</p>
	        </div>
	        <div class="alertbut">
	           <!--  <input type="button" value="否" class="no fr mr20"  onClick="hidejg12()"> -->
	        	<input type="button" value="确定" class="yes fr mr20" onClick="hidejg12()">
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
					self.location="colleagueInfo?id="+$("#voteId").val();
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
	//选中效果
	$(function(){
	$(".xuxinfos").click(function(){
				$(this).addClass("xuxinfosxz");
		})	
	})
	
	$(document).ready(
			function() {
				$("div.tsinfoBox").html('');
				$($("div.tpxinx")[1]).html('');
				$
						.ajax({
							url : "getVoteInfo",
							type : "POST",
							data : "optType=1&id=" + $("#voteId").val(),
							dataType : "json",
							success : function(data) {
								$("div.user-tx").find("img").attr("src",
										data.infos[0].pic==""?"":(data.userpath+data.infos[0].pic));
								$("p.user-msg2").find("a").html(
										data.infos[0].username);
								$("p.xuxinfo").html(data.infos[0].title);
								$("p.mt5").html(data.infos[0].remark);
								$("p.mb8").html(
										"截止日期："
												+ data.infos[0].endTime
														.replace("-0", "年")
														.replace("-0", "月")
														.replace("-", "年")
														.replace("-", "月")
														.replace(" ", "日")
												+ "点00分");
								$("#voteType").html(
										data.infos[0].isAnonymout == '0' ? ''
												: '匿名投票');
								$(".jcp").find("i").html(data.infos[0].minNum);
								//data.infos[0].itemList=initSeq(data.infos[0].itemList);
								$.each(data.infos[0].itemList,function(idx,item){
									initColleague(data.userpath,item);
								})
								if(data.infos[0].castedInfos!=undefined)
									$.each(data.infos[0].castedInfos,function(idx,castedInfo){
										$.each($("div.tsinfoBox").find('input[type="hidden"]'),function(inputIdx,input){
											if($(input).val()==castedInfo.item_id){
												$(input).parent().find('input[type="button"]').click();
												return false;
											}
										})
									})
							}
						});
				onloalrame();
			});
	/**
	*初始化排序
	*/
	function initSeq(itemList){
		$.each(itemList,function(idx,item){
			$.each(itemList,function(subIdx,subItem){
				//if(idx>=subIdx)
				//	return false;
				if(item.orgName>subItem.orgName){
					itemList[idx]=subItem;
					itemList[subIdx]=item;
				}else if(item.orgName==subItem.orgName&&item.itemName>subItem.itemName){
					itemList[idx]=subItem;
					itemList[subIdx]=item;
				}
			})
		});
		return itemList;
	}
	
	function initColleague(picpath,item){
		$("div.tsinfoBox").append('<div class="tsinfo"><input type="hidden" value="'+item.id+'"><div class="tsinfo2">'+
				'<div class="ts-xzinfoimg"><img src="'+picpath+item.pic+'"></div>'+
				'<div class="tsinfomsg"><p>'+item.itemName+'</p><div class="tszwbm" style="height:30px;" title="'+item.orgName+(item.duty==undefined||item.duty==''?'':item.duty)+'">'+item.orgName+(item.duty==undefined||item.duty==''?'':item.duty)+'</div></div>'+
				'<input type="button" value="投TA一票" onclick="selectIt(this)" class="tsxzbutls2"></div></div>');
	}
	
	function selectIt(obj){
		onloalrame();
		if($(obj).attr("class")=="tsxzbutls"){
			$(obj).parent().parent().attr("class","tsinfo");
			$(obj).val("投TA一票");
			$(obj).attr("class","tsxzbutls2");
			$.each($($("div.tpxinx")[1]).find('input[type="hidden"]'),function(idx,input){
				if($(input).val()==$(obj).parent().parent().find('input[type="hidden"]').val())
					$(input).parent().remove();
			})
		}else{
			if($($("div.tpxinx")[1]).find('input[type="hidden"]').length==parseInt($(".jcp").find("i").html())){
				//$("#warning").html("只能选择"+$(".jcp").find("i").html()+"项进行投票");
				//showjg12();
				msgBoxTohome("警告","只能选择"+$(".jcp").find("i").html()+"项进行投票");
				return;
			}
			$(obj).parent().parent().attr("class","tsinfo tsclass");
			$(obj).val("取消投票");
			$(obj).attr("class","tsxzbutls");
			$($("div.tpxinx")[1]).append('<div class="xzhouTs xzh2"><input type="hidden" value="'+$(obj).parent().parent().find('input[type="hidden"]').val()+'"><div class="ts-xzinfo"><div class="ts-xzinfoimg"><img src="'+$(obj).prev().prev().find('img').attr('src')+'"></div>'+
					'<div class="ts-xzinfomsg">'+$(obj).prev().html()+'</div></div></div>');
		}
	}
	
	function cast(){
		if($($("div.tpxinx")[1]).find('input[type="hidden"]').length==0){
			//$("#warnning").html("请选择要投票的项");
			//showjg12();
			msgBoxTohome("警告","请选择要投票的项");
			return;
		}
		if($($("div.tpxinx")[1]).find('input[type="hidden"]').length!=parseInt($(".jcp").find("i").html())){	 
			msgBoxTohome("警告","请选择"+$(".jcp").find("i").html()+"项投票");
			return;
		}
		
		var param='voteId='+$("#voteId").val();
		var id_score='[';
		$.each($($("div.tpxinx")[1]).find('input[type="hidden"]'),function(idx,input){
			id_score+='{"id":'+$(input).val()+',"score":1},'
		});
		id_score=id_score.substr(0,id_score.length-1)+']';
		param+='&id_score='+id_score;
		castVote(param);
	}
</script>
</html>
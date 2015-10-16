<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="ptbg" onLoad="onloalrame()">
	<input type="hidden" id="voteId" value="com.ipav:ipav-web:war:1.0.0-SNAPSHOT">
	<div class="pt_center rsglBox">

		<!---center begin-->
		<div class="pt_maincenBox mt12" id="mainBox">

			<div class="pttitle" style="width: 970px;">
				<ol class="centmenu" id="centmenu">
					<li><a href="#1" onclick="goback()">投票中心</a></li>
					<li><a href="#1" class="current">我要投票</a></li>
				</ol>
			</div>

			<div class="faqiBox">
				<div class="fqwt">
					<div class="user-tx">
						<img src="../../images/platform/userimg/us1.png">
					</div>
					<p class="user-msg2">
						<a href="#1">张小明</a><span>发起</span>
					</p>
					<i class="dqtime" id="dqtime"></i>
				</div>
				<p class="xuxinfo">各位同事投票猜猜明天天气怎么样?</p>
				<p class="tptsmsg mt5">投票规则：选项类型的投票，每人最少获得两次投票机会。</p>
				<p class="tptsmsg mb8">截止日期：2014年12月2日 5点00分</p>
			</div>

			<div class="xuxiangBox wt970s" id="xuxiangBox">
				<div class="ptx"></div>
				<table class="pttable" style="margin-left: 0px;" width="985"
					cellspacing="0" cellpadding="0">
					<tr class="ptitles">
						<th width="100" style="text-align: center">名次</th>
						<th width="180" style="text-align: center">得分</th>
						<th width="180" style="text-align: center">该名次必选人数</th>
						<th width="500"
							style="text-align: center; color: #fff; background: #2d9ac7;">请在下面排名投票</th>
					</tr>
				</table>
				<div id="tbBoxgdt6">
					<table class="pttable" style="margin-left: 0px;" width="985"
						cellspacing="0" cellpadding="0">
						<!-- <tr align="center">
							<td width="100">第1名</td>
							<td width="200">5分</td>
							<td width="180">1</td>
							<td width="500"><div class="pmtextBox"
									contentEditable="true" title="请选择第一名" id="pmtextBox2">
									<p class="qxuanz wtps">
										请选择第三名
										选择后清除标签
									</p>
									<p class="wtps">
										显示内容标签
									</p>
									<div class="pmtiocn" onClick="showjg2()"
										contentEditable="false"></div>
								</div></td>
						</tr>
						<tr align="center">
							<td>第2名</td>
							<td>4分</td>
							<td>1</td>
							<td><div class="pmtextBox" contentEditable="true"
									title="请选择第二名">
									<p class="qxuanz wtps">
										请选择第三名
										选择后清除标签
									</p>
									<p class="wtps">
										显示内容标签
									</p>
									<div class="pmtiocn" onClick="showjg2()"
										contentEditable="false"></div>
								</div></td>
						</tr>
						<tr align="center">
							<td>第3名</td>
							<td>3分</td>
							<td>3</td>
							<td><div class="pmtextBox" contentEditable="true"
									title="请选择第三名" id="pmtextBox2">
									<p class="qxuanz wtps">
										请选择第三名
										选择后清除标签
									</p>
									<p class="wtps">
										显示内容标签
									</p>
									<div class="pmtiocn" id="pmtiocn2" onClick="showjg2()"
										contentEditable="false"></div>
								</div></td>
						</tr> -->
					</table>
				</div>
			</div>

			<div class="wt970s" style="height: 40px; margin-top: 15px;">
				<p class="ml20">匿名投票</p>
			</div>

			<div class="wt970s" style="margin-bottom: 30px;">
				<input type="button" class="pttcBut85 ml20" value="确认" onclick="cast()"> <input
					type="button" class="pttcBut85 ml30" value="查看结果" onclick="showResult()"> <input
					type="button" class="pttcButh85 ml30" value="取消" onclick="goback()">
			</div>

		</div>
		<!-- center end--->
	</div>


	<!--simTestContent1 begin-->
	<div id="simScrollCont" class="simScrollCont"></div>
	<div class="simScrollContBox6" id="simScrollContBox">
		<div class="simScrollContBoxs6">
			<div class="tctitle" style="font-size: 18px;">
				第一名<span style="font-size: 12px;">(请点击选中其中一项)</span><a href="#1"
					onClick="hidejg()" title="关闭"></a>
			</div>
			<input type="hidden" id="count" value="" />
			<div class="ptx"></div>
			<div class="pmnrBox" id="items">
				<div class="pmfoxuinfo" title="点击投票">
					<p class="xuxinfo2">《牵手观音舞蹈》</p>
					<a href="#1" class="art10"><div class="pmimgs">
							<img src="../../images/platform/xitp1.png">
							<p href="#1" class="pmimgsdt"></p>
						</div></a>
				</div>
			</div>
			<div class="alertbut mt30" align="center">
				<input type="button" class="pttcBut85" value="确认" id="casequerne"> 
				<input type="button" class="pttcButh85 ml30" value="取消" onClick="hidejg()">
			</div>

		</div>
	</div>
	
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

	<!--simTestContent1 end-->

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
					self.location="rankInfo?id="+$("#voteId").val();
				}else {
					//$("#warnning").html("您暂无查看权限");
					//showjg12();
					msgBoxTohome("警告","您暂无查看权限");
				}
			}
		})
	}

	/* function onloalrame() {
		var winhight = window.parent.document.documentElement.clientHeight;
		var mainboxs = winhight - 12;
		var tabbox6 = winhight - 314;
		if (mainboxs < 150) {
			document.getElementById("mainBox").style.height = "auto";
		} else if (mainboxs > 230) {
			document.getElementById("mainBox").style.height = mainboxs + "px";
		}
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
	window.onload = onloalrame;
	
	//获取当前时间
	function CurentTime() {
		var now = new Date();
		var year = now.getFullYear(); //年
		var month = now.getMonth() + 1; //月
		var day = now.getDate(); //日
		var hh = now.getHours(); //时
		var mm = now.getMinutes(); //分
		var clock = year + "年";
		if (month < 10)
			clock += "0";
		clock += month + "月";
		if (day < 10)
			clock += "0";
		clock += day + "日 ";
		if (hh < 10)
			clock += "0";
		clock += hh + "点";
		if (mm < 10)
			clock += '0';
		clock += mm + "分";
		dqtime.innerHTML = clock;
	}
	setInterval("CurentTime()", 100);

	function hideimg() {
		var dscheck = document.getElementById("ycimg").checked;
		var imgs = document.getElementById("xuxiangBox");
		var imglist = imgs.getElementsByTagName("a");
		if (dscheck == true) {
			for (var i = 0; i < imglist.length; i++) {
				imglist[i].className = "dn";
			}
		} else {
			for (var i = 0; i < imglist.length; i++) {
				imglist[i].className = "bk";
			}
		}

	}

	$(window).resize(
			function() {
				$(".simScrollContBox6").css(
						{
							position : "absolute",
							left : ($(window).width() - $(".simScrollContBox6")
									.outerWidth()) / 2,
							top : ($(window).height() - $(".simScrollContBox6")
									.outerHeight()) / 2
						});
			})

	$(function() {
		$(window).resize();
	});

	$(document).ready(
			function() {
				$("#tbBoxgdt6").find("table").find('tr').remove();
				$("#items").html('');
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

								$.each($.parseJSON(data.infos[0].ext),
										function(idx, item) {
											initRank(item, idx + 1);
										});

								$.each(data.infos[0].itemList, function(idx,
										item) {
									initItems(data.picpath,item);
								});
								  if(data.infos[0].castedInfos!=undefined){
									
								if(data.infos[0].castedInfos!=undefined){
									var selected;
									$.each($("#tbBoxgdt6").find("table").find("tr"),function(trIdx,tr){
										var selected='';
										$.each(data.infos[0].castedInfos,function(idx,castedInfo){
											if(castedInfo.score==$($(tr).find("td")[1]).find("span").html())
												selected=selected.concat('<input type="hidden" value="'+castedInfo.item_id+'"><p>'+castedInfo.item_name+';</p>');
										}); 
										if(selected.indexOf(";")>=0)
											selected=selected.substr(0,selected.length-5)+'</p>';
										 selected+='<div contenteditable="false" onclick="showItems('+(trIdx+1)+',this)" class="pmtiocn"></div>';
										$(tr).find("div.pmtextBox").html(selected);
									})
								}   
								//$(obj).parent().find("p").remove();
								//$(obj).parent().prepend(selected);
								}
								/* $(obj).parent().find("p").remove();
								$(obj).parent().prepend(selected); */
								initClick();
							}
						});
				onloalrame();
			});

	/**
	 *初始化评分
	 */
	function initRank(item, idx) {
		$("#tbBoxgdt6")
				.find("table")
				.append(
						'<tr align="center"><td width="100">第'
								+ (idx)
								+ '名</td><td width="200"><span>'
								+ item.score
								+ '</span>分</td>'
								+ ' <td width="180">'
								+ item.count
								+ '</td><td width="500">'
								+ '<div class="pmtextBox" title="请选择第'+idx+'名" id="pmtextBox2">'
								+ '<p class="qxuanz wtps">请选择第'
								+ idx
								+ '名</p><div class="pmtiocn" onClick="showItems('+item.count+',this)" contentEditable="false"></div>'
								+ '</div></td>')
	}

	
	function showItems(counts,obj){
	 
		$("#simScrollCont").css("height",$(document).height()); 
		$($(".alertbut").find('input[type="button"]')[0]).unbind('click');
		$($(".alertbut").find('input[type="button"]')[0]).bind('click',function(){
			confirm(obj);
		})
		$("#items").find("div").not(".pmimgs").attr("class", "pmfoxuinfo");
		$.each($(obj).parent().parent().parent().siblings().find('input[type="hidden"]'),function(idx,input){
			$.each($("#items").find("div").not(".pmimgs"),function(idx,div){
				if($(input).val()==$(div).find('input[type="hidden"]').val()){
					$(div).attr("class", "pmfoxuinfohidden");
					return false;					
				}
			})
		});
		
		$.each($(obj).parent().find('input[type="hidden"]'),function(idx,input){
			$.each($("#items").find("div").not(".pmimgs"),function(idx,div){
				if($(input).val()==$(div).find('input[type="hidden"]').val()){
					$(div).attr("class", "pmfoxuinfo2");
					return false;					
				}
			})
		});
		
		$("#count").val(counts);
		$("div.tctitle").text($(obj).parent().parent().prev().prev().prev().html());
		$("div.tctitle").find('span').text('(请点击选中其中'+counts+'项)');
		$("div.tctitle").html($(obj).parent().parent().prev().prev().prev().html()+'<span style="font-size: 12px;">(请点击选中其中'+counts+'项)</span><a href="#1" onClick="hidejg()" title="关闭"></a>');
		showjg();
		$(".pmimgsdt").on("click",function(){		 
			imglook([$(this).parent("div").find("img")[0]],0);
			 $(this).parent().parent().parent("div").click();
		});
	}
	
	function confirm(obj){
		if($("div.pmfoxuinfo2").length!=parseInt($("#count").val())){
			$(obj).parent().find("p").remove();
			$(obj).parent().append('<p class="qxuanz wtps">请选择'+$(obj).parent().parent().parent().find('td:first-child').html()+'</p>')
			$("#casequerne").parent().find("p").remove();
			$("#casequerne").parent().append('<p class="qxuanz wtps">请选择'+$(obj).parent().parent().parent().find('td:first-child').html().substr(1)+'</p>')
			//hidejg12();
			return;
		}
		var selected='';
		$.each($("div.pmfoxuinfo2"),function(idx,div){
			//selected=selected.concat('<input type="hidden" value="'+$(div).find('input[type="hidden"]').val()+'"><p class="wtps">'+$(div).find('input[type="hidden"]').next().text()+'</p>;');
			selected=selected.concat('<input type="hidden" value="'+$(div).find('input[type="hidden"]').val()+'"><p>'+$(div).find('input[type="hidden"]').next().text()+(idx==$("div.pmfoxuinfo2").length-1?'':';')+'</p>');
		});
	    $("#casequerne").parent().find("p").remove();
	    if(selected.indexOf(";")>=0)
			selected=selected.substr(0,selected.length-5)+'</p>';
		selected+=$(obj).prop('outerHTML');
		//$(obj).parent().find("p").remove();
		$(obj).parent().html(selected);
		//$(obj).parent().prepend(selected);
		hidejg();
	}
	
	/**
	*选中效果
	*/
	function initClick() {
		$("#items")
				.find("div")
				.not(".pmimgs")
				.click(
						function() {
							if ($(this).attr("class") == 'pmfoxuinfo') {
								if ($("div.pmfoxuinfo2").length >= parseInt($(this).parent().prev().prev().val())) {
									msgBoxTohome("警告","只能选择"+$("#count").val()+"项进行投票");
								} else
									$(this).attr("class", "pmfoxuinfo2");
							} else
								$(this).attr("class", "pmfoxuinfo");
						});
	}

	/**
	 *初始化评分选项
	 */
	function initItems(picpath,item) {
		$("#items")
				.append(
						//'<div class="pmfoxuinfo" title="点击投票" onClick="xztpinfo()">'+
						'<div class="pmfoxuinfo" title="点击投票">'+
						'<input type="hidden" value="'+item.id+'" />'
								+ '<p class="xuxinfo2">'
								+ item.itemName
								+ '</p>'
								+ '<a href="#1" class="art10"><div class="pmimgs"'+(item.pic==''?' style="display:none"':'')+'>'
								+ '<img src="'+picpath+item.pic+'"><p href="#1" class="pmimgsdt"></p></div></a></div>');
	}
	
	function showjg() {
		topDiv.show();
		$("#simScrollCont").show();
		$("#simScrollContBox").show();
	}
	function hidejg() {
		topDiv.hide();
		$("#simScrollCont").hide();
		$("#simScrollContBox").hide();
	}

	function cast() {
		$("#tbBoxgdt6").find("tr").find('input[type="hidden"]').length 
		if($("#tbBoxgdt6").find("tr").find('input[type="hidden"]').length == 0) {
			//$("#warnning").html("请选择要投票的项");
			//showjg12();
			msgBoxTohome("警告","请选择要投票的项");
		} 
		var param = 'voteId=' + $("#voteId").val();
		var id_score = '[';
		$.each($("#tbBoxgdt6").find("tr"),function(idx,tr){
			$.each($(tr).find('input[type="hidden"]'),function(inputIdx,input){
				id_score += '{"id":' + $(input).val() + ',"score":'+$(tr).find("span").html()+'},'
			})
		});
		id_score = id_score.substr(0, id_score.length - 1) + ']';
		param += '&id_score=' + id_score;
		castVote(param);
	}
</script>
</html>
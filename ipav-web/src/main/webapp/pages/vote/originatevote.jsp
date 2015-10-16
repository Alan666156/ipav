<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统管理</title>
</head>
<body class="ptbg" onLoad="onloalrame()">
<input type="hidden" id="hid_action_type"/>
<div class="pt_center rsglBox">

    <!---center begin-->
    <div class="pt_maincenBox mt12" id="mainBox">
    
        <div class="pttitle" style="width:970px;">
            <ol class="centmenu" id="centmenu">
                <li><a href="#1" class="current">发起新投票</a></li>
            </ol>
        </div>
        <input type="hidden" id="voteId" value="${id}">
        <div class="tabBox serchBox" style="margin-left:15px; margin-top:15px;">
        	<table width="100%" class="mr15" cellpadding="0" cellspacing="0">
            	<tr height="73">
                	<td width="70"><label>投票名称：</label></td>
                    <td><input type="text" class="pt_text500" id="voteName" placeholder="请输入投票名称" onblur="checkVoteNameNull(this)" maxlength="50"><p class="errormsg2"></p></td>
                </tr>
                <tr height="73" valign="top">
                	<td>投票规则：</td>
                    <td><textarea class="texta500" placeholder="请输入投票规则" onblur="checkRemark(this)"></textarea><p class="errormsg2"></p></td>
                </tr>
                <tr valign="top">
                	<td><label class="mt10 fl">投票类型：</label></td>
                    <td>
                    	<div class="tplxBox" id="voteType">
                        	<ul>
                            	<li><a href="#1" onclick="tab('rcxs',0,4)" id="rcxs_title_0" class="current" title="选项" >选项</a></li>
                                <li><a href="#1" onclick="tab('rcxs',1,4)" id="rcxs_title_1" title="评分" >评分</a></li>
                                <li><a href="#1" onclick="tab('rcxs',2,4)" id="rcxs_title_2" title="排名" >排名</a></li>
                                <li><a href="#1" onclick="tab('rcxs',3,4)" id="rcxs_title_3" title="在同事中选择" >在同事中选择</a></li>
                            </ul>
                        </div>
						
                        <!---选项  begin--->
                        <div class="tpinfoBox" id="rcxs_main_0">
                         	<table class="xuxtab mt10" width="100%">
                            	<tr>
                                	<td>
                                    	<p>
                                        <label class="xlb3">选项1：</label><input type="text" placeholder="请输入投票选项" class="xuxtext" maxlength="50" onblur="checkNull(this,1)">
                                        <a href="#1">上传图片...</a><input title="上传图片..." type="file" class="filebut">
                                        </p>
                                        <i class="errmsg2"></i>
                                        <div class="addimg" style="display:none"><img src=""><span class="closes" title="删除图片" onclick="deletePic(this)"></span></div>
                                    </td>
                            	</tr>
                                <tr>
                                    <td>
                                    	<p>
                                        <label class="xlb3">选项2：</label><input type="text" placeholder="请输入投票选项" class="xuxtext" maxlength="50" onblur="checkNull(this,1)">
                                        <a href="#1">上传图片...</a><input title="上传图片..." type="file" class="filebut">
                                        </p>
                                        <i class="errmsg2"></i>
                                        <div class="addimg" style="display:none"><img src=""><span class="closes" title="删除图片" onclick="deletePic(this)"></span></div>
                                    </td>
                            	</tr>
                            </table>
                            <a href="#1" class="addxux3" style="width:75px;" onclick="addItem(this,1)">增加选项</a>
                            <div class="tabBox serchBox" style="margin-left:3px; margin-top:15px;">
	                            <ol>
	                                <li>
	                                	<label class="xlb3">必选：</label>
	                                    <select class="ptselect" style="float:left;" onclick="initSelectCount(this)" onchange="changeSelectItem(this)" >
	                                    	<option>1</option>
	                                    </select>
	                                    <span class="xsp">项</span>
	                                    <p class="tpmsg">投票结果取得票最高前<span>1</span>名</p><i class="errmsg3"></i>
	                                </li>
	                            </ol>
                            </div>
                            
                        </div>
                        <!---选项  end--->
                        
                        <!---评分  begin--->
                        <div class="tpinfoBox dn" id="rcxs_main_1">
                        	<div class="tabBox serchBox" style="margin-left:3px; margin-top:20px;">
	                            <ol>
	                                <li>
	                                	<label class="xlb3">最高值：</label>
	                                    <input type="text" class="pt_text80" onblur="inputIntScore(this)"><i class="errmsg erml"></i>
	                                </li>
	                                <li class="ml30">最低值：<input type="text" class="pt_text80" onblur="inputIntScore(this)"><i class="errmsg erml"></i></li>
	                                <li class="ml30">数据类型：
	                                 	<select class="se24" onchange="changeScoreType(this)">
	                                    	<option value="0">整数型</option>
	                                        <option value="1">小数型</option>
	                                    </select>
	                                </li>
	                            </ol>
                            </div>
                        	<table class="xuxtab mt10" width="100%">
                            	<tr>
                                	<td>
                                    	<p>
                                        <label class="xlb3">评分对象1：</label><input type="text" placeholder="请输入评分对象" maxlength="50" class="xuxtext" onblur="checkNull(this,2)">
                                        <a href="#1">上传图片...</a><input title="上传图片..." type="file" class="filebut">
                                        </p>
                                        <i class="errmsg2"></i>
                                        <div class="addimg3" style="display:none"><img src=""><span class="closes" title="删除图片" onclick="deletePic(this)"></span></div>
                                    </td>
                            	</tr>
                                <tr>
                                    <td>
                                    	<p>
                                        <label class="xlb3">评分对象2：</label><input type="text" placeholder="请输入评分对象" maxlength="50" class="xuxtext" onblur="checkNull(this,2)">
                                        <a href="#1">上传图片...</a><input title="上传图片..." type="file" class="filebut">
                                        </p>
                                        <i class="errmsg2"></i>
                                        <div class="addimg2" style="display:none"><img src=""><span class="closes" title="删除图片" onclick="deletePic(this)"></span></div>
                                    </td>
                            	</tr>
                            </table>
                            <a href="#1" class="addxux3" style="width:100px;" onclick="addItem(this,2)">增加评分对象</a>
                            <div class="tabBox serchBox" style="margin-left:3px; margin-top:15px;">
	                            <ol>
	                                <li>
	                                	<label class="xlb3">必选对象：</label>
	                                    <select class="ptselect" style="float:left;" onclick="initSelectCount(this)">
	                                    	<option>1</option>
	                                    </select>
	                                    <span class="xsp">个</span>
	                                    <i class="errmsg3"></i>
	                                </li>
	                                <li class="ml30">评选结果取平均分最高前：
	                                 	<select class="ptselect2" onclick="initSelectCount(this)">
	                                    	<option>1</option>
	                                    </select>名
	                                    <i class="errmsg13"></i></li>
	                                </li>
	                            </ol>
                            </div>

                        </div>
                        <!---评分  end--->
                        
                        <!---排名 begin--->
                        <div class="tpinfoBox dn" id="rcxs_main_2">
                        	<table class="xuxtab mt10" width="100%">
                            	<tr>
                                	<td>
                                    	<p>
	                                        <label class="xlb3">第1名得分：</label>
	                                        <input type="text" class="pt_text80" onblur="inputFloatRank(this);">
	                                        <span class="xsp">分</span>
	                                        <span class="xsp" style="margin-left:30px;">该名次必选项：</span>
	                                        <input type="text" class="pt_text80" name="quota" onblur="inputIntRank(this);">
	                                        <em class="tpmsg2" style="display:none">投票结果取总分最高第<span style="color:red">1</span>名</em>
                                        </p>
                                        <i class="errmsg6"></i>
                                        <i class="errmsg8">2</i>
                                    </td>
                            	</tr>
                                <tr>
                                	<td>
                                    	<p>
                                        <label class="xlb3">第2名得分：</label>
                                        <input type="text" class="pt_text80" onblur="inputFloatRank(this);">
                                        <span class="xsp">分</span>
                                        <span class="xsp" style="margin-left:30px;">该名次必选项：</span>
                                        <input type="text" class="pt_text80" name="quota" onblur="inputIntRank(this);">
                                        <em class="tpmsg2" style="display:none">投票结果取总分最高第<span>2</span>名</em>
                                        </p>
                                        <i class="errmsg6"></i>
                                        <i class="errmsg8">2</i>
                                    </td>
                            	</tr>
                            </table>
                            <a href="#1" class="addxux3" style="width:125px;" onclick="addRankScore()">增加名次得分设置</a>
                        	<table class="xuxtab mt10" width="100%">
                            	<tr>
                                	<td>
                                    	<p>
                                        <label class="xlb3">选项1：</label><input type="text" placeholder="请输入排名选项" class="xuxtext" onblur="checkNull(this,1)">
                                        <a href="#1">上传图片...</a><input title="上传图片..." type="file" class="filebut">
                                        </p>
                                        <i class="errmsg2"></i>
                                        <div class="addimg3" style="display:none"><img src=""><span class="closes" title="删除图片" onblur="deletePic(this)"></span></div>
                                    </td>
                            	</tr>
                                <tr>
                                    <td>
                                    	<p>
                                        <label class="xlb3">选项2：</label><input type="text" placeholder="请输入排名选项" class="xuxtext" onblur="checkNull(this,1)">
                                        <a href="#1">上传图片...</a><input title="上传图片..." type="file" class="filebut">
                                        </p>
                                        <i class="errmsg2"></i>
                                        <div class="addimg3" style="display:none"><img src=""><span class="closes" title="删除图片" onclick="deletePic(this)"></span></div>
                                    </td>
                            	</tr>
                            </table>
                            <a href="#1" class="addxux3" style="width:75px;" onclick="addItem(this,3)">增加选项</a>
                        </div>
                        <!--排名 end--->
                        
                        <!--在同事中选择 begin--->
                        <div class="tpinfoBox dn" id="rcxs_main_3">
                        	<div class="tabBox" style="margin-left:3px; margin-top:25px;">
	                            <ol>
	                                <li>
	                                	<label class="xlb3">参选同事：</label>
<!-- 	                                    <input type="text" readonly class="pt_text503 gdnr2" onclick="showLevels(3)"> -->
	                                    <input type="text" readonly class="pt_text503 gdnr2" onclick="showusersDiv(3)" id="showusers3">
	                                     <div id="hiduserid3"></div><div id="hidusername3"></div>
	                                    <div class="dn treeBox bbs" id="join_person" style="margin-left: 88px;  display:none;z-index:999">
						                	<ul>
						                    </ul>
						                </div>
	                                    <i class="errmsg10">2</i>
	                                </li>
	                            </ol>
                            </div>
                        	<div class="tabBox serchBox" style="margin-left:3px; margin-top:15px;">
	                            <ol>
	                                <li>
	                                	<label class="xlb3">必选同事：</label>
	                                    <select  class="ptselect" id="bixuantongs" style="float:left;" onclick="initColleagueSelect(this)" onchange="changeSelectItem(this)">
	                                    	 
	                                    </select>
	                                    <span class="xsp">名</span>
	                                    <p class="tpmsg">投票结果取得票最高前<span>1</span>名</p>
	                                    <i class="errmsg3">2</i>
	                                </li>
	                            </ol>
                            </div>
                        </div>
                        <!---在同事中选择 end-->
                    </td>
                </tr>
            </table>
        </div>
        <!---公共-->
        <div style=" margin-left:85px; padding-top:5px;" id="publicDiv">
        	<div class="tabBox serchBox" style="margin-left:3px; margin-top:25px;">
                            <ol>
                                 <li><label class="xlb3">投票截止：</label>
                                 <input id="endTime" class="pt_text145 pttime" name="endTime" readonly="readonly"  value=""
							type="text" onClick="WdatePicker({dateFmt:'yyyy年M月d日',minDate:'%y-%M-%d'})">
                                 	<select class="se24">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
                                    </select>
                                 时<i class="errmsg14"></i></li>
                            </ol>
                            </div>
                            <div class="tabBox" style="margin-left:3px; margin-top:25px;">
                            <ol>
                                <li class="fl">
                                	<label class="xlb3">投票人员：</label>
<!--                                     <input type="text" readonly="readonly" value="" class="pt_text160 gdnr2" onclick="showLevels(1)"> -->
                                    <input type="text" readonly="readonly" value="" class="pt_text160 gdnr2" onclick="showusersDiv(1)" id="showusers1">
                                    <div id="hiduserid1"></div><div id="hidusername1"></div>
<!-- 					                <div class="dn treeBox" id="vote_person" style="margin-left: 88px;  display:none;z-index:1;"> -->
<!-- 					                	<ul> -->
<!-- 					                    </ul> -->
<!-- 					                </div> -->
                                    <i class="errmsg5" style="margin-left:90px;"></i>
                                </li>
                                <li class="ml30 fl">指定可查看投票报表人员：
<!-- 	                                <input type="text" readonly="readonly" class="pt_text160 gdnr2" onclick="showLevels(2)"> -->
	                                <input type="text" readonly="readonly" class="pt_text160 gdnr2" onclick="showusersDiv(2)" id="showusers2">
	                               <div id="hiduserid2"></div><div id="hidusername2"></div>
<!-- 						            <div class="dn treeBox" id="view_person" style="margin-left: 163px; display:none;"> -->
<!-- 						                <ul> -->
<!-- 						                </ul> -->
<!-- 						            </div> -->
	                                <i class="errmsg5"></i>
                                </li>
                            </ol>
                            </div>
                            <div class="tabBox serchBox" style="margin-left:3px; margin-top:23px;">
	                            <ol>
	                                <li>
	                                	<p class="nmtpbox" onClick="dsTime()"><input type="checkbox" id="dstime1"><label for="dstime1">定时开始投票</label></p>
	                                </li>
	                                <li id="dsTimeBox1" class="ml10 dn">
	                                	<!--弹出定时时间 begin-->
	                                        <p style=" float:left;">
		                                        <input id="startTime" class="pt_text145 pttime" name="startTime" readonly="readonly"  value=""
								type="text" onClick="WdatePicker({dateFmt:'yyyy年M月d日',minDate:'%y-%M-%d'})">
		                                        <select class="se24">
													<option value="1">1</option>
													<option value="2">2</option>
													<option value="3">3</option>
													<option value="4">4</option>
													<option value="5">5</option>
													<option value="6">6</option>
													<option value="7">7</option>
													<option value="8">8</option>
													<option value="9">9</option>
													<option value="10">10</option>
													<option value="11">11</option>
													<option value="12">12</option>
													<option value="13">13</option>
													<option value="14">14</option>
													<option value="15">15</option>
													<option value="16">16</option>
													<option value="17">17</option>
													<option value="18">18</option>
													<option value="19">19</option>
													<option value="20">20</option>
													<option value="21">21</option>
													<option value="22">22</option>
													<option value="23">23</option>
													<option value="24">24</option>
		                                        </select>时<i class="errmsg15"></i>
	                                        </p>
	                                    <!--弹出定时时间 end-->
	                                </li>
	                                 <li class="ml30">是否可查看投票结果：
	                                 	<select class="ptselect2" style="width:180px;">
	                                    	<option value="0">不允许投票人查看</option>
	                                        <option value="1">投票截止后投票人可查看</option>
	                                        <option value="2">投票人随时可查看</option>
	                                    </select>
	                                 </li>
	                            </ol>
                            </div>
                            <div class="tabBox serchBox" style="margin-left:3px; margin-top:5px;">
	                            <ol>
	                                <li>
	                                	<p class="nmtpbox"><input type="checkbox" id="che1"><label for="che1">匿名投票</label></p>
	                                </li>
	                            </ol>
                            </div>                            
                            <div class="tabBox serchBox mb30" style="margin-left:3px; margin-top:20px;">
                            	<input type="button" class="pttcBut85 ml75" value="发起投票" onclick="check()">
  		          			    <input type="button" class="pttcButh85 ml30" value="取消" onclick="goback()">
                            </div>

        </div>
        <!--公共-->

    </div>
    <!-- center end--->
</div>

<!-- 错误提示框 -->
<div id="simScrollCont12" class="simScrollCont">
</div>
	<div class="simScrollContBox4" id="simScrollContBox12"  >
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
<link rel="stylesheet" type="text/css" href="/pages/css/zTreeStyle.css" >
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/pages/js/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="/pages/js/vote/originateVote.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript">
$(function(){
	$('input').placeholder();
});
</script>

<script language="javascript">
	function onloalrame(){
		var winhight = window.parent.document.documentElement.clientHeight;
		var mainboxs = winhight-12;
		var tabbox6 = winhight-314;
		if(mainboxs<750)
			document.getElementById("mainBox").style.height="auto";
		else if(mainboxs>751)
			document.getElementById("mainBox").style.height=mainboxs+"px";
		$("#142",window.parent.document).height($("body").height());
	}
	window.onload=onloalrame;
	$("body").click(function(){onloalrame();});
	//开启关闭选项定时投票
	function dsTime(){
		 var dscheck = document.getElementById("dstime1").checked;
		 if(dscheck == true)
		 {
		 document.getElementById("dsTimeBox1").style.display="block";
		 }else
		 {
			 document.getElementById("dsTimeBox1").style.display="none";
			 }
		}
	//开启关闭评分定时投票
	function dsTime2(){
		 var dscheck = document.getElementById("dstime2").checked;
		 if(dscheck == true)
		 {
		 document.getElementById("dsTimeBox2").style.display="block";
		 }else
		 {
			 document.getElementById("dsTimeBox2").style.display="none";
			 }
		}
	//开启关闭排名定时投票
	function dsTime3(){
		 var dscheck = document.getElementById("dstime3").checked;
		 if(dscheck == true)
		 {
		 document.getElementById("dsTimeBox3").style.display="block";
		 }else
		 {
			 document.getElementById("dsTimeBox3").style.display="none";
			 }
		}
	//开启关闭在同事中定时投票
	function dsTime4(){
		 var dscheck = document.getElementById("dstime4").checked;
		 if(dscheck == true)
		 {
		 document.getElementById("dsTimeBox4").style.display="block";
		 }else
		 {
			 document.getElementById("dsTimeBox4").style.display="none";
			 }
		}
	
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
</script>
</html>
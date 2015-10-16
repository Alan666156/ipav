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
                    <td><span id="voteName" ></span></td>
                </tr>
                <tr height="73" valign="top">
                	<td>投票规则：</td>
                    <td><span id="guize"></span></td>
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
                            	 
                            </table>
                            <div class="tabBox serchBox" style="margin-left:3px; margin-top:15px;">
	                            <ol>
	                                <li>
	                                	<label class="xlb3">必选：</label>
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
	                                	<label class="xlb3">最高值：</label><label id="pingfenhight"></label>
	                                </li>
	                                <li class="ml30">最低值：<label id="pingfendi"></label></li>
	                                <li class="ml30">数据类型：
	                                  <label id="pinfenNumType"></label> 
	                                </li>
	                            </ol>
                            </div>
                        	<table class="xuxtab mt10" width="100%">
                            
                            </table>
                            <div class="tabBox serchBox" style="margin-left:3px; margin-top:15px;">
	                            <ol>
	                                <li>必选对象：
	                                	<label   id="pingfenbixuan"></label>个
	                                </li>
	                                <li class="ml30">评选结果取平均分最高前：
	                                	<label   id="pingfenmax"></label>名
	                                </li>
	                            </ol>
                            </div>

                        </div>
                        <!---评分  end--->
                        
                        <!---排名 begin--->
                        <div class="tpinfoBox dn" id="rcxs_main_2">
                        	<table class="xuxtab mt10" width="100%">                            	 
                            </table>                           
                        	<table class="xuxtab mt10" width="100%">                             
                            </table> 
                        </div>
                        <!--排名 end--->
                        
                        <!--在同事中选择 begin--->
                        <div class="tpinfoBox dn" id="rcxs_main_3">
                        	<div class="tabBox" style=" margin-left:-10px;margin-top:25px;">
	                            <ol>
	                                <li>
	                                	<label class="xlb3">参选同事：</label>
	                                    <textarea readonly="readonly"  class="texta500"  id="showusers3"></textarea>
	                                     <div id="hiduserid3"></div><div id="hidusername3"></div>
	                                    <div class="dn treeBox bbs" id="join_person" style="margin-left: 88px;  display:none;z-index:999">
						                	<ul>
						                    </ul>
						                </div> 
	                                </li>
	                            </ol>
                            </div>
                        	<div class="tabBox serchBox" style="margin-left:-10px; margin-top:15px;">
	                            <ol>
	                                <li>
	                                	<label class="xlb3">必选同事：</label>
	                                	<label id="bixuantongs" class="xlb3"></label>	 
	                                    <p class="tpmsg">投票结果取得票最高前<span>1</span>名</p>
	                                 
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
                                 <li><label id="endTimetext">投票截止：</label>
                               
                            </ol>
                            </div>
                            <div class="tabBox" style="margin-left:3px; margin-top:25px;">
                            <ol>
                                <li class="fl">
                                	<label style="width: 165px;display: block;">投票人员：</label>
                                    <textarea id="showusers1" class="texta500" style="margin-left: 70px" readonly="readonly"></textarea> 
                                </li>
                                <li  class="fl"><label style="width: 165px;display: block;">指定可查看投票报表人员：</label>
	                                <textarea id="showusers2"  class="texta500" style="margin-left: 70px"  readonly="readonly"></textarea>
                                </li>
                            </ol>
                            </div>
                            <div class="tabBox serchBox" style="margin-left:3px; margin-top:23px;">
	                            <ol>
	                                <li>
	                                	<p ><lable id="dstime1">定时开始投票</lable><label  id="startTime"></label></p>
	                                </li>
	                                
	                                 <li class="ml30">是否可查看投票结果：<label id="ptselectlook"></label>
	                                 </li>
	                            </ol>
                            </div>
                            <div class="tabBox serchBox" style="margin-left:3px; margin-top:5px;">
	                            <ol>
	                                <li>
	                                	 <label id="niming" class="dn">匿名投票</label></p>
	                                </li>
	                            </ol>
                            </div>                            
                            <div class="tabBox serchBox mb30" style="margin-left:253px; margin-top:20px;">
                            	<!-- <input type="button" class="pttcBut85 ml75" value="发起投票" onclick="check()"> -->
  		          			    <input type="button" class="pttcButh85 ml30" value="返回" onclick="goback()">
                            </div>

        </div>
        <!--公共-->

    </div>
    <!-- center end--->
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
<script type="text/javascript" src="/pages/js/vote/showvote.js"></script>
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
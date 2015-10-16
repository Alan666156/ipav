<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="ptbg" onLoad="onloalrame()"> 
<div class="pt_center rsglBox">
     <input type="hidden" id="voteidBymsg" value="${vid}" />
     <input type="hidden" id="votetypeBymsg" value="${vtype}" />
    <!---center begin-->
    <div class="pt_maincenBox mt12" id="mainBox">
    
        <div class="pttitle" style="width:970px;">
            <ol class="centmenu" id="centmenu">
                <li><a href="#1" class="current">投票中心</a></li>
            </ol>
        </div>
        
        <div class="tabBox serchBox" style="margin-left:15px; margin-top:15px;">
        <ol>
            <li>投票发起时间：
            	<input id="minDate" class="pt_text145 pttime" name="minDate" readonly="readonly"  value=""
							type="text" onClick="WdatePicker({dateFmt:'yyyy年M月d日'})" readonly="readonly">
            	至
            	<input id="maxDate" class="pt_text145 pttime" name="maxDate" readonly="readonly"  value=""
							type="text" onClick="WdatePicker({dateFmt:'yyyy年M月d日'})" readonly="readonly">
            </li>
        </ol>
        </div>
        
        <div class="tabBox serchBox" style="margin-left:15px; margin-top:15px;">
        <ol>
            <li>
            	<select class="ptselect" id="status">
            		<option value="2">正在进行的投票</option>
            		<option value="-1">全部投票</option>
            		<option value="1">未开始的投票</option>
            		<option value="3">已关闭的投票</option>
            	</select></li>
            <li class="ml30">
            	<select class="ptselect" id="myVoteStatus">
            		<option value="3">我未完成的投票</option>
            		<option value="-1">全部投票</option>
            		<option value="2">我已完成的投票</option>
            		<option value="1">我发起的投票</option>
            	</select>
            </li>
            <li class="ml30"><input type="text" class="pt_text200" id="voteTitle" placeholder="请输入投票名称关键字"></li>

            <li class="ml20"><input type="button" value="查询" class="ptserch" onclick="search(1)"></li>
        </ol>
        </div>
		
        <div class="toolBox" style="width:968px;">
        	<a href="#1" class="cur15">刷新</a>
            <a href="#1" class="cur16" onclick="publishVote()">发起新投票</a>
            <a href="#1" class="cur17" onclick="showStatistic()">查看投票报表</a>
            <a href="#1" class="cur18" onclick="updateVote()">取回我发起的投票</a>
            <a href="#1" class="cur19" onclick="deleteVoteAfter()">删除我发起的投票</a>
        </div>

        <div class="ptx"></div>
      	<table class="pttable"  width="970" border="0" cellspacing="0" cellpadding="0">
            <tr class="ptitles">
                <th width="80" style="text-align:center"><input type="checkbox" onclick="selectAll(this)">&nbsp;序号</th>
                <th width="290" style="text-align:center">投票名称</th>
                <th width="150" style="text-align:center">投票截止时间</th>
                <th width="100" style="text-align:center">投票发起人</th>
                <th width="150" style="text-align:center">投票发起时间</th>
                <th width="200" style="text-align:center">操作</th>
            </tr>
        </table>
        <div id="tbBoxgdt6">
            <table id="tabcolor" class="pttable"  width="970" border="0" cellspacing="0" cellpadding="0" id="voteList">
            <tr>
                <td width="80"><input type="checkbox">&nbsp;1</td>
                <td width="290">最佳新人选项投票</td>
                <td width="150">2015年1月12日 9时</td>
                <td width="100">张小明</td>
                <td width="150">2015年1月12日 9时</td>
                <td width="200"><a href="#1">我要投票</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1">投票结果</a></td>
            </tr>
        </table>
        </div>
      		  <!---page--->
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
             <!---page--->
         </div>  
        <!----工资单项目设置  end--->
        
    </div>
    <!-- center end--->
    
    <div id="simScrollCont12" class="simScrollCont">a</div>
	<div class="simScrollContBox4" id="simScrollContBox12">
    	<div class="simScrollContBoxs4" >
	        <div class="tctitle">提示信息<a href="#1" onClick="hidejg12()" title="关闭"></a></div>
	     	<div class="ptx"></div>
	        <div id="tbBoxgdt2" class="alertBox">
	        	<p id="warnning" class="alertmsg">XXX，dafadsfadf你即将修改XXXX的个人信息，请慎重修改，不然你就联系不到他了！</p>
	        </div>
	        <div class="alertbut">
	          <!--   <input type="button" value="否" class="no fr mr20"  onClick="hidejg12()"> -->
	        	<input type="button" value="确定" class="yes fr mr20" onClick="hidejg12()">
	        </div>
    	</div>
    </div>
</body>


<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/pages/js/tools.js"></script>
<script type="text/javascript" src="/pages/js/vote/voteCenter.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript">
$(function(){
			$('input').placeholder();
			$(window).resize(function() {
				$(".simScrollContBox4").css({
			        position: "absolute",
			        left: ($(window).width() - $(".simScrollContBox4").outerWidth()) / 2,
			        top: ($(window).height() - $(".simScrollContBox4").outerHeight()) / 2
			    });

			})
		});
$(function() {
    $(window).resize();	
});
</script>
<script language="javascript">
	function onloalrame(){
		var winhight = window.parent.document.documentElement.clientHeight;
		var mainboxs = winhight-12;
		var tabbox6 = winhight-314;
		document.getElementById("mainBox").style.height=mainboxs+"px";	

		document.getElementById("tbBoxgdt6").style.minHeight=tabbox6+"px";
		document.getElementById("tbBoxgdt6").style.maxHeight=tabbox6+1+"px";
		document.getElementById("tbBoxgdt6").style.overflowY="auto";
		document.getElementById("tbBoxgdt6").style.width=985+"px";
		document.getElementById("tbBoxgdt6").style.overflowX="hidden";
		$("#142",window.parent.document).height($("body").height());
	}
	window.onload=onloalrame;
	

</script>
<script type="text/javascript">
   //鼠标移到表格中变颜色
   $(function(){
       $("#tabcolor tr").hover(function(){$(this).addClass("tr_hover")},function(){ $(this).removeClass("tr_hover")});
   })
</script>
</html>
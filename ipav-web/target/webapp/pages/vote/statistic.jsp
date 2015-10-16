<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body class="ptbg" onLoad="onloalrame()"> 
<div class="pt_center rsglBox">
<input type="hidden" id="voteId" value="com.ipav:ipav-web:war:1.0.0-SNAPSHOT" />
    <!---center begin-->
    <div class="pt_maincenBox mt12" id="mainBox">
    
        <div class="pttitle" style="width:970px;">
            <ol class="centmenu" id="centmenu">
            	<li><a href="#1" onclick="goback()">投票中心</a></li>
                <li><a href="#1" class="current">投票报表</a></li>
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
            		<option value="-1">全部投票</option>
            		<option value="2">正在进行的投票</option>
            		<option value="3">已关闭的投票</option>
            	</select>
            </li>
            <li class="ml20">
            	<select class="ptselect" id="myVoteStatus">
            		<option value="-1">全部投票</option>
            		<option value="3">我未完成的投票</option>
            		<option value="2">我已完成的投票</option>
            		<option value="1">我发起的投票</option>
            	</select>
            </li>
            <li class="ml20">
            	<select class="ptselect" id="statisticType">
            		<option value="-1">全部</option>
            		<option value="1">仅显示入选项</option>
            	</select>
            </li>
            <li class="ml20"><input type="text" class="pt_text200" id="voteTitle" style="margin-left:22px;" placeholder="请输入投票名称关键字"></li>
            <li class="ml20"><input type="button" value="查询" class="ptserch" onclick="search(1)"></li>
        </ol>
        </div>
		
        <div class="toolBox" style="width:968px;">
        	<a href="#1" class="cur15" onclick="">刷新</a>
            <a href="#1" class="cur20" onclick="exportExcel()">导出投票报表</a>
        </div>

        <div class="ptx"></div>
      	<table id="yourTableID" class="pttable"  width="970" cellspacing="0" cellpadding="0">
            <tr class="ptitles">
                <th width="40" style="text-align:center">序号</th>
                <th width="135" style="text-align:center">投票发起时间</th>
                <th width="130" style="text-align:center">投票名称</th>
                <th width="130" style="text-align:center">选项名称</th>
                <th width="40" style="text-align:center">名次</th>
                <th width="60" style="text-align:center">得票/分</th>
                <th width="100" style="text-align:center">选项已投的人 </th>
                <th width="50" style="text-align:center">入选否</th>
                <th width="75" style="text-align:center">应投票人数</th>
                <th width="75" style="text-align:center">已投票人数</th>
                <th width="100" style="text-align:center">未投票人</th>
            </tr>
        </table>
        <div id="tbBoxgdt6">
            <table id="tabcolor" class="pttable" width="970" cellspacing="0" cellpadding="0">
            <tr align="center">
                <td width="45">1</td>
                <td width="135">14年12月11日 12:55</td>
                <td width="145">最佳员工</td>
                <td width="145">李三</td>
                <td width="40">1</td>
                <td width="60">30</td>
                <td width="130"><div onMouseMove="onm()" style="text-overflow:ellipsis;overflow: hidden;width:100px">张三,李四</div>
                <div class="tpreny dn" id="tpreny">
                	<div  class="tprenylist" id="tprenylist">
                    	<ul>
                        	<li>张三</li><li>三毛</li><li>丽斯安</li><li>马西索</li><li>东方不败</li><li>悟空空</li>
                            <li>张三</li><li>三毛</li><li>丽斯安</li><li>马西索</li><li>东方不败</li><li>悟空空</li>
                            <li>张三</li><li>三毛</li><li>丽斯安</li><li>马西索</li><li>东方不败</li><li>悟空空</li>
                        </ul>
                    </div>
                </div>
                </td>
                <td width="50">入选</td>
                <td width="110">120</td>
                <td width="100">王五、小五...</td>
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
      		  
     	     <!-- <div class="ptpageBox" style="width:970px;" id="pageInfo">
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
     	     </div> -->
             <!---page--->
         </div>  
        <!----工资单项目设置  end--->
        
    </div>
    <!-- center end--->
</body>

<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/pages/js/tools.js"></script>
<script type="text/javascript" src="/pages/js/vote/statistic.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>
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
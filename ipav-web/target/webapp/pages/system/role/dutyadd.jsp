<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Cache-Control" content="no-cache">
<%-- <%@include file="/pages/system/common/head.jsp" %> --%>
<title>系统管理</title>
<!-- <script type="text/javascript">
$(function(){
			$('input').placeholder();
			$("#dutyfrom").validate({
		        submitHandler: cusDo(op,dutyid)
		    }); 
		});
function cusDo(op,dutyid,valobj){
	if("insert"==op){
		addDuty();
	}
	if("delall"==op){
		delAll();
	}
	if("delone"==op){
		delOne(dutyid);
	}
	if("valyes"==op){
		setVal(dutyid,"1");
	}
	if("valno"==op){
		setVal(dutyid,"0");
	}
	if("select"==op){
		dutyList(valobj);
	}
	if("update"==op){
		updatDuty(dutyid,$(valobj).val());
	}
}

function updatDuty(dutyid,dname){
	if(""!=dname.trim()){
		$("#dutyname").attr("value",dname);
		$("#dutyid").attr("value",dutyid);
		$("#dutyfrom").attr("action","/dutyupdate");
		$("#dutyfrom").submit();
	}
}

function dutyList(v){
	$("#queryvalflg").attr("value",v);
	$("#dutyfrom").attr("action","/dutylist");
	$("#dutyfrom").submit();
}

function addDuty(){
	if($("#dutyfrom").valid()){
		var dname= $("#dutyname1").val();
		$("#dutyname").attr("value",dname);
		$("#dutyfrom").attr("action","/dutyadd");
		$("#dutyfrom").submit();
	}
}
//启用//停用
function setVal(dutyid,flg){
	$("#dutyid").attr("value",dutyid);
	$("#valflg").attr("value",flg);
	$("#dutyfrom").attr("action","/dutyval");
	$("#dutyfrom").submit();
}
 

function delOne(dutyid){
	$("#dutyid").attr("value",dutyid);
	$("#dutyfrom").attr("action","/dutydel");
	$("#dutyfrom").submit();
}

function delAll(){
	var cout =0;
	$('input[name="dutyids"]:checked').each(function(){
		cout =cout+1;
	});
	if(cout<1){
		alert("请选择职位！");
		return false;
	}
	$("#dutyfrom").attr("action","/dutydelall");
	$("#dutyfrom").submit();
}

function removeOp(obj){
	$(obj).removeAttr("readonly");
}

</script> -->
</head>

<body>
<%-- <div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">职务维护</a></li>
    </ol>
</div>
<form action="/dutylist" method="post" name="dutyfrom" id="dutyfrom">
<input type="hidden" name="dutyname" id="dutyname">
<input type="hidden" name="companyid" value="${companyid}">
<input type="hidden" name="dutyid" id="dutyid">
<input type="hidden" name="valflg" id="valflg">
<input type="hidden" name="queryvalflg" id="queryvalflg">
<div class="tabBox serchBox">

      <p class="rtradio" style="float:left; width:100%; margin-left:0px; line-height:30px; ">
      <input type="radio" name="qysh" <c:if test="${empty queryvalflg}">checked</c:if>   onclick="cusDo('select','','')"><label>全部</label>
      <input type="radio" name="qysh" <c:if test="${queryvalflg eq '1'}">checked</c:if>  onclick="cusDo('select','','1')"><label>启用</label>
      <input type="radio" name="qysh" <c:if test="${queryvalflg eq '0'}">checked</c:if>  onclick="cusDo('select','','0')"><label>停用</label></p>
<div class="tabBox mb10 fl" style="margin-left:0px;">
<ol>
	<li><input type="text" class="sertext {required:true,maxlength:20}" id="dutyname1" placeholder="请输入职务名称"></li>
	<li><input type="button" style="margin-left:20px;" value="添加职务" class="serch92" 
		onmouseover="this.className='serch92m'" onmouseout="this.className='serch92'" onclick="cusDo('insert','','');">
    	<input type="button" style="margin-left:20px;" value="批量删除" class="serch92h" 
    	onmouseover="this.className='serch92hm'" onmouseout="this.className='serch92h'" onclick="cusDo('delall','','');" >
    </li>
</ol>
<ul class="drdcul" id="drdcul">
         <li><p class="tcdrdc">职务导入导出</p>
         	<ul>
            	<li>
                <div class="drdc" id="drdc">
	                <a href="#1">职务模板下载</a>
	                <a href="#1" onclick="OpenDialog();">职务数据导入</a>
	                <a href="#1" onclick="OpenDialog2();">职务数据导出</a>
                </div>
                </li>
            </ul>
         </li>
        </ul>
</div>
	<ul class="zwwhul" id="zwwhul">
    	<c:if test="${!empty dutylist }">
    		<c:forEach items="${dutylist}" var="item" varStatus="num">
		    	<c:if test="${item.valflg eq '1'}">
			    	<li>
			            <div class="zwwh"  id="rcxs_mainz_<c:out value="${num.index}"/>">
			            <h5 style="float:left; width:20px; clear:left;"><c:out value="${num.index+1}"/>.</h5>
			            <label for="<c:out value="${item.dutyid}"/>">
			            <input class="ml10" type="checkbox" name="dutyids" id="<c:out value="${item.dutyid}"/>" value="<c:out value="${item.dutyid}"/>">
			            <input type="text" class="zwwhtext" readonly value="<c:out value="${item.dutyname}"/>" onclick="removeOp(this);" onchange="cusDo('update','<c:out value="${item.dutyid}"/>',this);">
			            </label>
			            <span><a href="#1" onclick="cusDo('valno','<c:out value="${item.dutyid}"/>','');">停用</a>
			            <a href="#1" onClick="tabzw('rcxs',<c:out value="${num.index}"/>,<c:out value="${num.index+1}"/>);" id="rcxs_title_<c:out value="${num.index}"/>" >修改</a>
			            <a href="#1" onclick="cusDo('delone','<c:out value="${item.dutyid}"/>','');">删除</a></span>
			            </div>
			            <div class="zwwh dn" id="rcxs_main_<c:out value="${num.index}"/>">
			            	<label for="<c:out value="${item.dutyid}"/>">
			            	<input type="text" class="zwwhtext" value="<c:out value="${item.dutyname}"/>"></label>
			                <span><input type="button" value="保存" onClick="tabzwa('rcxs',<c:out value="${num.index}"/>,<c:out value="${num.index+1}"/>);" class="kedbutm"></span>
			            </div>
			        </li>
		        </c:if>
		        <c:if test="${item.valflg ne '1'}">
		          	<li>
			            <div class="zwwh2"><h5 style="float:left; width:20px; clear:left;"><c:out value="${num.index+1}"/>.</h5>
			            <label><input type="text" class="zwwhtext" style="color:#c3c3c3" readonly value="<c:out value="${item.dutyname}"/>"></label>
			            <span><a href="#1" onclick="cusDo('valyes','<c:out value="${item.dutyid}"/>','');" class="current">启用</a><a href="#1" >修改</a><a href="#1">删除</a></span>
			            </div>
			        </li>
		        </c:if>
	        </c:forEach>
        </c:if>
    </ul>
</div>
</form> --%>




<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">职务维护</a></li>
    </ol>
</div>

<div class="tabBox " style=" margin-top:5px;">
      <p class="rtradio" style="float:left; width:100%; margin-left:0px; line-height:30px; ">
      	<input type="radio" name="qysh" value="-1" checked ><label>全部</label>
      	<input type="radio" name="qysh" value="1" checked><label>启用</label>
      	<input type="radio" name="qysh" value="0"><label>停用</label>
      </p>
</div>

<div class="toolBox" style="margin-top:5px; width:97.5%; margin-left:15px;">
	<a href="#1" class="cur8" onClick="showtsBoxt5()">新增职务</a>
	<a href="#1" class="cur3" onClick="getDutyInfo()">修改</a>
	<a href="#1" class="cur4" onClick="delStatus(-1)">删除</a>
	<a href="#1" class="cur9" onClick="updateStatus(1)">启用</a>
	<a href="#1" class="cur10" onClick="updateStatus(0)">禁用</a>
	<a href="#1" class="curs22" onclick="showtsBoxt7()">职务导入</a>
	<a href="#1" class="curs22" onclick="exportDutyList()">职务导出</a>
	<a href="#1" class="cur7" onclick="exportTemplet()">模板下载</a>
</div>
<div class="ptx"></div>

<div class="xttabBox">
<table id="pttable" class="xpttable" width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="ptitles">
        <th width="80" style="text-align:center" width="60" align="center"><input type="checkbox"> 序号</th>
        <th width="150" style="text-align:center" width="100">职务名称</th>
        <th width="250" style="text-align:center">描述</th>
        <th width="90" style="text-align:center">状态</th>
        <th width="90" style="text-align:center">创建人</th>
        <th width="130" style="text-align:center" align="left">创建时间</th>
      </tr>
</table>
</div>

<!-- <div class="ptpageBox" style="width:97.5%; margin-left:15px;">
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

<div class="ptpageBox" style="width:97.5%; margin-left:15px;" id="pageInfo">
                <div class="rightpagebox fr">
	                <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
	                <a href="#1"><div class="pageys"><span>上一页</span></div></a>
	                <ol id="pageol" class="pageol">
	                    <li><a href="#1" class="mpages"><div><span>1</span></div></a></li>
	                    <li><a href="#1"><div><span>2</span></div></a></li>
	                    <li><a href="#1"><div><span>3</span></div></a></li>
	                    <li><p class="slh">...<p></li>
	                    <li><a href="#1"><div class="pageysn"><span>10</span></div></a></li>
	                </ol>
	                <a href="#1"><div class="pageys"><span>下一页</span></div></a>
	                <input type="text" class="gopage">
	                <a href="#1"><div class="pageys"><span>GO</span></div></a>
                </div>
     	     </div>

<!--新增职务  begin-->
<div id="simalphat5" class="simalpha">
</div>
<div class="simaltBoxt5" id="simaltBoxt5">
	<div class="simaltBoxst5">
		<div class="tctitle">职务维护<a href="#1" onClick="hidetsBoxt5()" title="关闭"></a></div>
		<input type="hidden" id="dutyId" value="" />
		<table class="ggtab  ml5 mt5"  width="100%" cellpadding="0" cellspacing="0">
			<tr height="65">
				<td align="right" width="85"><label class="xin">职务名称：</label></td>
                <td><input type="text" class="xtzwwhtext"><p class="errormsg2"></p></td>
			</tr>
			<tr height="65">
				<td align="right"><label>职务描述：</label></td>
                <td><textarea class="xtzwwhtexta"></textarea></td>
			</tr>
		</table>
		<div class="jgtcbutBox" align="center">
        	<input type="button" class="yes mt15" value="确认" onClick="addDuty()">
            <input type="button" class="no mt15 ml30" value="取消" onClick="hidetsBoxt5()">
        </div>
	</div>
</div>
  
<!--新增职务 end-->

<!--修改职务  begin-->
<!-- <div id="simalphat6" class="simalpha">
</div>
<div class="simaltBoxt5" id="simaltBoxt6">
	<div class="simaltBoxst5">
		<div class="tctitle">修改职务<a href="#1" onClick="hidetsBoxt6()" title="关闭"></a></div>
		<table class="ggtab  ml5 mt5"  width="100%" cellpadding="0" cellspacing="0">
			<tr height="65">
				<td align="right" width="85"><label class="xin">职务名称：</label></td>
                <td><input type="text" class="xtzwwhtext" value="java工程师"><p class="errormsg2">提示错误信息</p></td>
			</tr>
			<tr height="65">
				<td align="right"><label>职务描述：</label></td>
                <td><textarea class="xtzwwhtexta"></textarea></td>
			</tr>
		</table>
		<div class="jgtcbutBox" align="center">
        	<input type="button" class="yes mt15" value="确认" onClick="hidetsBoxt6()">
            <input type="button" class="no mt15 ml30" value="取消" onClick="hidetsBoxt6()">
        </div>
	</div>
</div> -->
  
<!--修改职务 end-->

<!--职务导入  begin-->
<div id="simalphat7" class="simalpha">
</div>
<div class="simaltBoxt6" id="simaltBoxt7">
	<div class="simaltBoxst6">
		<div class="tctitle">职务导入<a href="#1" onClick="hidetsBoxt7()" title="关闭"></a></div>
		<table class="ggtab  ml5 mt20"  width="100%" cellpadding="0" cellspacing="0">
			<tr height="45">
				<td align="right" width="85"><label>选择文件：</label></td>
                <td width="200"><input type="text" readonly="readonly" id="textfield" class="xtzwwhtext" ></td>
                <td><input type="button" class="no ml10 filepsz" value="浏览..."><input type="file" id="excelFile" name="dutyFile" class="xtfilebut" onchange="document.getElementById('textfield').value=this.value">
                </td>

			</tr>
		</table>
		<div class="jgtcbutBox" align="center">
        	<input type="button" class="yes mt15" value="导入" onClick="importDutyList()">
            <input type="button" class="no mt15 ml30" value="取消" onClick="hidetsBoxt7()">
        </div>
	</div>
</div>
  
<!--职务导入 end-->

<!--弹出提示 begin-->
<div id="simalpha3" class="simalpha">
</div>
	<div class="simaltBox3" id="simaltBox3">
    	<div class="simaltBoxs3">
        <div class="tctitle">提示信息<a href="#1" onClick="hidetsBox3()" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        	<p class="alertmsg">XXX，你即将修改XXXX的个人信息，请慎重修改，不然你就联系不到他了！</p>
        </div>
        <div class="alertbut">
            <input type="button" value="否" class="no fr mr20"  onClick="hidetsBox3()">
        	<input type="button" value="是" class="yes fr mr20" onClick="hidetsBox3()">
        </div>

        </div>
    </div>
  
<!--弹出提示 end-->


<div id="simScrollCont13" class="simScrollCont">
</div>
<div class="simScrollContBox5" id="simScrollContBox13">
		<div class="simScrollContBoxs5">
        	<div class="tctitle">导入信息提示<a href="#1" onClick="hidejg13()" title="关闭"></a></div>
            	<div class="drBoxtab" id="copymag" >
                      <table class="pttable"  style="float:left; margin-left:0px;" width="803" border="0" cellspacing="0" cellpadding="0">
                          <tr class="ptitles">
                            <th width="100" style="text-align:center" width="60" align="center">序号</th>
                            <th width="703" style="text-align:left; text-indent:2em;" width="100">错误信息</th>
                          </tr>
                      </table>
                </div>
                <div class="mt20" style="width:100%;" align="center" >
                <!-- <input type="button" class="pttcBut85" value="全选" id="copyButton" data-clipboard-target="copymag" onclick="copytoclip()"> -->
                <input type="button" class="pttcBut85" value="全选复制" id="copyButton">
                <input type="button" class="pttcButh85 ml30" value="取消" onClick="hidejg13()">
                </div>
        </div>
</div>
</body>

<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript" src="/pages/js/system/org/duty.js"></script>
<script type="text/javascript" src="/pages/js/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="/pages/js/zeroclipboard-2.1.6/dist/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript">
var client = new ZeroClipboard($("#copyButton"));
client.on("copy",function(e){
	 var content="";
		$.each($("#copymag").find('tr'),function(idxTr,tr){
			
			$.each($(tr).find('th'),function(idxTd,td){
				if(idxTd==1)
						content+="          ";
				content+=$(td).text()
			})
			
			$.each($(tr).find('td'),function(idxTd,td){
				if(idxTd==1)
					content+="              ".substr((idxTr+"").length);
				content+=$(td).text()
			}) 
			content+="\r\n";
		})
	e.clipboardData.setData("text/plain",content);
});
function onloalrame(){
	var winhight = document.documentElement.clientHeight;
	$(".simalpha").height(winhight);
	}
	window.onload=onloalrame;
$(function(){
	$('input').placeholder();
		
});

/* function  showtsBoxt6(){
	$("#simalphat6").show();
	$("#simaltBoxt6").show();	
}
function  hidetsBoxt6(){
	$("#simalphat6").hide();
	$("#simaltBoxt6").hide();
} */

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
	$(".simaltBox3").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBox3").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBox3").outerHeight()) / 2
    });
	
	$(".simaltBoxt5").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBoxt5").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBoxt5").outerHeight()) / 2
    });
	$(".simScrollContBox5").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox5").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox5").outerHeight()) / 2
    });
	$(".simaltBoxt6").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBoxt6").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBoxt6").outerHeight()) / 2
    });
	
	$(".simaltBoxt7").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBoxt7").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBoxt7").outerHeight()) / 2
    });
})

$(function() {
    $(window).resize();
});

</script>
</html>

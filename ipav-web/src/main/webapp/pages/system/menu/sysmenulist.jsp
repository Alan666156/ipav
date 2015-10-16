<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<%@include file="/pages/system/common/head.jsp" %>
<script type="text/javascript" src="/pages/js/uploadPreview.min.js"></script>
<script type="text/javascript" src="/pages/js/system/sysmaintenance/sysmenu.js"></script>
</head>
<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">应用菜单</a></li>
    </ol>
</div>
<div class="tabBox " style=" margin-top:5px;">
      <p class="rtradio" style="float:left; width:100%; margin-left:0px; line-height:30px; ">
      	<input type="radio" name="qysh" value="" checked="checked" ><label>全部</label>
      	<input type="radio" name="qysh" value="1"><label>启用</label>
      	<input type="radio" name="qysh" value="0"><label>停用</label>
      </p>
</div>
<div class="toolBox" style="margin-top:5px; width:98%; margin-left:15px;">
	<a href="#1" class="cur8" onClick="control.openAddOrEdit(0)">新增菜单</a>
	<a href="#1" class="cur3" onClick="control.openAddOrEdit(1)">修改</a>
	<a href="#1" class="cur4" onClick="control.actionDiv(2)">删除</a>
	<a href="#1" class="cur9" onClick="control.actionDiv(1)">启用</a>
	<a href="#1" class="cur10" onClick="control.actionDiv(0)">禁用</a>
</div>
<div class="ptx"></div>
<div class="tabBox2">
	<table class="tab2015" id="senfe" width="100%" cellpadding="0" cellspacing="0">
      <tr id="tab2015tr">
        <td width="80"><input type="checkbox" id="firstChk" onclick="fnc.selectChk()">序号</td>
        <td>应用名称</td>
        <td>链接地址</td>
        <td>图标</td>
        <td>状态</td>
      </tr>
      <tbody id="menubody"></tbody> 
    </table>
</div>
<div class="ptpageBox" id="pageInfo" style="width:97.5%; margin-left:15px;">	
    <div class="rightpagebox fr">
	  <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
          <a href="javascript:;"><div class="pageys"><span>上一页</span></div></a>
          <ol id="pageol" class="pageol">
              <li><a href="javascript:;" class="mpages" onClick="set_menu4(0)"><div><span>1</span></div></a></li>
              <li><a href="javascript:;" onClick="set_menu4(1)"><div><span>2</span></div></a></li>
              <li><a href="javascript:;" onClick="set_menu4(2)"><div><span>3</span></div></a></li>
              <li><p class="slh">...<p></li>
              <li><a href="javascript:;"><div class="pageysn" onClick="set_menu4(3)"><span>10</span></div></a></li>
          </ol>
          <a href="javascript:;"><div class="pageys"><span>下一页</span></div></a>
          <input type="text" class="gopage">
          <a href="javascript:;"><div class="pageys"><span>GO</span></div></a>
    </div>
</div>

<!-- 公共遮罩层 -->
<div id="simScrollCont" class="simScrollCont"></div>
<!-- 提示信息框 begin-->
<div class="simScrollContBox4" id="simScrollContBox1">
	    <div class="simScrollContBoxs4">
	      <div class="tctitle">提示信息<a href="javascript:;" onClick="control.closeDiv(0)" title="关闭"></a></div>
	      <div class="ptx"></div>
          <div id="tbBoxgdt2" class="alertBox">
        	<p class="alertmsg" id="contentstr"></p>
        	<input type="hidden" id="hidaction" />
          </div>
          <div class="alertbut">
        	<input type="button" value="确认" class="yes fr mr20" onClick="control.closeDiv(1)">
          </div>
	   </div>
</div> 
<!-- 提示信息框 end-->

<!--新增或者修改应用菜单  begin-->
<div class="simaltBoxt8" id="simaltBoxt8">
  <form action="" id="MenuFrom" method="POST" enctype="multipart/form-data">				
	<div class="simaltBoxst8">
		<div class="tctitle"><span id="title_span"></span><a href="#1" onClick="control.closeAddOrEdit(0)" title="关闭"></a></div>
		<table class="ggtab  ml5 mt10"  width="460px" cellpadding="0" cellspacing="0">
			<tr height="55">
				<td align="right" width="85"><label class="xin">应用名称：</label></td>
                <td width="230"><input type="text" class="xtqyyytext" name="menuname"><p class="errormsg2"></p></td>
                <td></td>
			</tr>
            <tr height="55">
				<td align="right"><label class="xin">应用图标：</label></td>
                <td><input type="text" class="xtqyyytextsc" name="imgsrc" disabled="disabled" >
                	<input type="button" class="no" style="margin-left:13px;" value="上传">
                    <input type="file" class="fileqyyy" id="imgUpload" name="pic" onchange="fnc.showFileName()">
                    <p class="errormsg2"></p>
                </td>
                <td width="90" rowspan="2"><div class="xtyytb bdbg" align="center"><img id="imgPr" width="200" height="120" /></div></td>
			</tr>
			<tr height="55">
				<td align="right"><label class="xin">链接地址：</label></td>
                <td><input type="text" class="xtqyyytext"  name="pathstr" ><p class="errormsg2"></p></td>
			</tr>
		</table>
		<div class="jgtcbutBox" align="center">
        	<input type="button" class="yes mt15" value="确认" onClick="control.closeAddOrEdit(1)">
            <input type="button" class="no mt15 ml30" value="取消" onClick="control.closeAddOrEdit(0)">
	        <input type="hidden" id="hidmenuid"  name="menuid"/>
	        <input type="hidden" id="hidlevl"  name="levl"/>
	        <input type="hidden" id="hidvalflg"  name="valflg"/>
	        <input type="hidden" id="hidparentid"  name="parentid"/>
	        <input type="hidden" id="hidrootid"  name="rootid"/>
	        <input type="hidden" id="hidoldPath"  name="oldPath"/>
        </div>
	</div>
 </form>
</div>
<!--新增或者修改应用菜单 end-->
</body>
</html>

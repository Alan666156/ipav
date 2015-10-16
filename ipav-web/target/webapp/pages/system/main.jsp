<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>

<title>欢迎登录快捷管家</title>
<style type="text/css">
html,body,div,ul,li,ol,p,form,input,span,a,img,h1,h2,h3,h4,del,dl,dd,dt,textarea,label,fieldset{margin:0px;padding:0px;}
body{font-family:"微软雅黑","宋体";  font-size:13px; height:100%;  color:#3e4d4d; }
.simScrollCont{display:none;position:absolute;width:100%;height:100%;background:#000;top:0;left:0;filter:alpha(Opacity=80);-moz-opacity:0;opacity:0.4;}
.simScrollContBox{position:fixed;_position:absolute;border:5px solid #E9F3FD;background:#FFF;text-align:left; width:542px; height:290px; display:none;}
.simScrollContBox2{ width:540px; height:288px; background:#fff; border:1px solid #48a8cf;}
.tctitle{ line-height:40px; color:#fff; text-indent:15px; width:540px; height:40px; background:#48a8cf; font-size:15px;}
.tctitle a{ float:right; width:19px; height:19px; background:url(../images/systems/close1.jpg); display:block; margin-top:10px; margin-right:10px;}
.tctitle a:hover{ background:url(../images/systems/close2.jpg);} 
.four{ margin-top:-4px;}
.foure{ margin-bottom:-4px;}
</style>
<script language="javascript">
	function onloalrame(){
		var winhights = document.documentElement.clientHeight;
		var leftboxs = winhights-89;
		var mainboxs = winhights-89;
	    document.getElementById("leftFrame").style.height=leftboxs+"px";
		
		document.getElementById("mainFrame").style.height=mainboxs+"px";
		}
	$(document).ready(function(){
		$("#mainframe").load(function () { 
	        var mainheight = $(this).contents().find("body").height(); 
	        $(this).height(mainheight); 
	    }); 
	})

</script>

</head>

<!-- <frameset rows="119,*,55" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="/system/top?menuid=109" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="198,*" frameborder="no" border="0" framespacing="0" id="fr">
    <frame src="/system/left" name="leftFrame" scrolling="no" id="leftFrame" title="leftFrame" />
    <frame src="/system/index" name="mainFrame" id="mainFrame" title="mainFrame" />
  </frameset>
  <frame src="/system/floor" name="bottomFrame" scrolling="No" noresize="noresize" id="bottomFrame" title="bottomFrame" />	
</frameset>
<noframes>
</noframes> -->
<body  onLoad="onloalrame()" style=" margin:0px; padding:0px;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="2">
    	<iframe width="100%" height="85px" src="/system/top?menuid=109" name="topFrame" noresize="noresize" id="topFrame"  scrolling="no" frameborder="0" allowtransparency="true"></iframe>
    </td>
  </tr>
  <tr>
    <td rowspan="2" width="180" valign="top" style="background:url(../images/systems/leftbg.jpg) repeat-y;">
    	<iframe width="180px;" height="100%" src="/system/left" frameborder="0" allowtransparency="true" name="leftFrame" id="leftFrame"  title="leftFrame"></iframe>
    </td>
    <td>
    	<iframe width="100%" height="100%" src="/system/index" frameborder="0" allowtransparency="true"  name="mainFrame" id="mainFrame" title="mainFrame" ></iframe>
    </td>
  </tr>
</table>
</body>
</html>


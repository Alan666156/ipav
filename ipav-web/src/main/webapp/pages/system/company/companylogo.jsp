<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<title>系统管理</title>
<script type="text/javascript" src="/pages/js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min.js"></script>
<script type="text/javascript">
	function initCut(){
		$("#x").val("0");
		$("#y").val("0");
		$("#width").val("410");
		$("#height").val("213");
		$("#txtWidth").html("135");
	    $("#txtHeight").html("45");
		$("#filefield").uploadPreview({
			width : 410,
			height : 213,
			imgPreview : "#img1",
			imgCutview:"#newimg",
			imgType : [ "png", "jpg" ],
			callback : function() {
				return false;
			}
		});
		$('#img1').imgAreaSelect({ 
			selectionColor: 'blue', 
			maxWidth:410,
			minWidth:0,
			minHeight:0,
			maxHeight:213, 
			selectionOpacity: 0.2,
			aspectRatio: '3:1',//设定选取区域的显示比率，如：”4:3“
			instance:true,//若设为true，imgAreaSelect()函数会返回一个对选择区域图像的一个引用，以便能够进一步使用API。（详见８、API方法）
			autoHide:true,//如果设为true，那么在选择完后区域会消失。 
			onSelectChange: processImg 
		}); 
	}
	function clearImage(){
		$("#img1").removeAttr("src");
		$("#newimg").removeAttr("src");
		//清空file文件
		var file = $("#filefield");
		file.after(file.clone().val(""));
		file.remove();
		//初始化函数绑定和数据
		initCut();
	}
// 	function showCutImage(){
// 		$('#img1').imgAreaSelect({ 
// 			selectionColor: 'blue', 
// 			maxWidth:410,
// 			minWidth:0,
// 			minHeight:0,
// 			maxHeight:213, 
// 			selectionOpacity: 0.2,
// 			aspectRatio: '3:1',//设定选取区域的显示比率，如：”4:3“
// 			instance:true,//若设为true，imgAreaSelect()函数会返回一个对选择区域图像的一个引用，以便能够进一步使用API。（详见８、API方法）
// 			autoHide:true,//如果设为true，那么在选择完后区域会消失。 
// 			onSelectChange: processImg 
// 		}); 
// 	}
	function validata(){
		if(isNotNull($("#img1").attr("src"))&&isNotNull($("#newimg").attr("src"))){
			logoForm.submit();
		}else{
			showMsg("请选择图片作为LOGO!");
			return;
		}
	}
    function processImg(img, selection){
    	//为了保证在预览框内有剪切后的图片存在的情况下,保证重新剪切的效果
    	if(isNotNull($("#hidsource").val())){
    		$("#newimg").attr("src",$("#img1").attr("src"));
    		$("#hidsource").val("");
    	}    
		var scaleX = 135 / (selection.width || 1);
	    var scaleY = 45 / (selection.height || 1);
	    $("#x").val(Math.round(selection.x1));
	    $("#y").val(Math.round(selection.y1));
	    var select_width=Math.round(selection.width);
	    var select_height=Math.round(selection.height);
	    $("#width").val(select_width);
	    $("#height").val(select_height);
	    $("#txtWidth").html(select_width);
	    $("#txtHeight").html(select_height);
	    $('#newimg').css({
	        width: Math.round(scaleX * 410) + 'px',
	        height: Math.round(scaleY * 213) + 'px',
	        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
	        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
	    });
	}
    function showMsg(msg){
    	$("#msg_info").html(msg);
		$("#simScrollCont").show();
		$("#simScrollContBox").show();
    }
	function closeMsg(){
    	$("#simScrollCont").hide();
    	$("#simScrollContBox").hide();
    }
 	 //根据窗口大小调整弹出框的位置
    $(window).resize(function() {
    	$(".simScrollContBox4").css({
            position: "absolute",
            left: ($(window).width() - $(".simScrollContBox4").outerWidth()) / 2,
            top: ($(window).height() - $(".simScrollContBox4").outerHeight()) / 2
        });
    })
    	
	$(function() {
		$(window).resize();
		//初始化遮罩层高度
		$("#simScrollCont").css("height",$("#mainFrame",window.parent.document).height());
		var message=$("#hid_save_message").val();
		if(isNotNull(message)){
			var info="保存企业LOGO失败!";
    		if(message=="success") 	info="保存企业LOGO成功!请点击公司LOGO刷新数据!";
			showMsg(info);
		}
		initCut();
		
	});  
  </script>
  <style type="text/css">
.file-box{ position:relative;width:340px}
.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
</style>
</head>
<body>
<input type="hidden" id="hid_save_message" value="${message }"/>
<form action="/saveLogo" method="post" enctype="multipart/form-data" name="logoForm">
<input type="hidden" name="x" id="x" value="">
<input type="hidden" name="y" id="y" value="">
<input type="hidden" name="width" id="width" value="">
<input type="hidden" name="height" id="height" value="">
<input type="hidden" name="companyid"  value="${curuser.companyid}">
<input type="hidden" id="hidsource"  value="${sourceimage}">
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">企业LOGO</a></li>
    </ol>
</div>
<table style="margin-left:88px; margin-top:10px;" border="0" cellpadding="0" cellspacing="0">
  <tr height="100">
    <td valign="middle" colspan="2">
    	<p style=" vertical-align:middle;  line-height:40px;">
    	<input type="button"  value="选择企业LOGO"    class="buth120 fl"  >
    	<input type="file" name="filefield" id="filefield" value="选择企业LOGO" style=" height:40px; width:120px; float:left;position:absolute;
			margin-left:-120px; filter:alpha(opacity:0);opacity: 0;">&nbsp;&nbsp;上传企业LOGO，支持jpg、png格式的图片</p> 
    </td>
  </tr>
  <tr>
    <td width="450">
    	<div class="qylogo" align="center" id="logoview">
    		<img id="img1" style="width:410px;" <c:if test="${sourceimage ne ''}">src="<c:out value='${sourceimage }'/>"</c:if>  />
    	</div>
    </td>
    <td valign="top">
    	<p>LOGO预览</p>
    	<div class="qylogo2" id="preview" style="width:135px; height:45px;overflow:hidden; border:1px solid gray;">
    		<img id="newimg" style="width:135px; height:45px;"  <c:if test="${subimage ne ''}"> src="<c:out value='${subimage }'/>"</c:if>  />
    	</div> 
		<label>宽  <span id="txtWidth"></span>* 高 <span id="txtHeight"></span></label>
		 
    </td>
  </tr>
  <tr height="140">
    <td>
    	<input type="button" value="保存企业LOGO" class="butl120 bcqy1" onmouseover="this.className='butl120m bcqy1'" 
    	onmouseout="this.className='butl120 bcqy1'" onclick="validata()">
        <input type="button" value="清空选择" class="buth100" onmouseover="this.className='buth100m'" 
        onmouseout="this.className='buth100'" onclick="clearImage()">
    </td>
    <td></td>
  </tr>
</table>
</form>
<!-- 提示信息 -->
<div id="simScrollCont" class="simScrollCont"></div>
<div class="simScrollContBox4" id="simScrollContBox">
    	<div class="simScrollContBoxs4">
        <div class="tctitle">提示信息<a href="javascript:;" onClick="closeMsg()" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        	<p class="alertmsg" id="msg_info"></p>
        </div>
        <div class="alertbut">
        	<input type="button" value="确定" class="yes fr mr20" onClick="closeMsg()" />
        </div>
        </div>
</div> 
</body>
</html>
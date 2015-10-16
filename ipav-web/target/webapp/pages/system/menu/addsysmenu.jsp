<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<%@include file="/pages/system/common/head.jsp" %>
<script type="text/javascript">
	/**
	 * 图片预览控件
	 * @param $
	 */
	(function($) {
		jQuery.fn.extend({
					uploadPreview : function(opts) {
						opts = jQuery.extend({
							width : 0,
							height : 0,
							imgPreview : null,
							imgType : [ "gif", "jpeg", "jpg", "bmp", "png" ],
							callback : function() {
								return false;
							}
						}, opts || {});

						var _self = this;
						var _this = $(this);
						var imgPreview = $(opts.imgPreview);
						// 设置样式
						autoScaling = function() {
							imgPreview.css({
								"margin-left" : 0,
								"margin-top" : 26,
								"width" : opts.width,
								"height" : opts.height
							});
							imgPreview.show();
						}
						// file按钮出发事件
						_this.change(function() {
						if (this.value) {
							if (!RegExp(
									"\.(" + opts.imgType.join("|")
											+ ")$", "i").test(
									this.value.toLowerCase())) {
								/* alert("图片类型必须是"
										+ opts.imgType.join("，")
										+ "中的一种"); */
								msgBox("提示信息","图片类型不正确");
								this.value = "";
								return false;
							}
							
							if ($.browser.msie) {// 判断ie
								var path = $(this).val();
								if (/"\w\W"/.test(path)) {
									path = path.slice(1, -1);
								}
								imgPreview.attr("src", path);
								imgPreview.css({
									"margin-left" : 0,
									"margin-top" : 26,
									"width" : opts.width,
									"height" : opts.height
								});
								setTimeout("autoScaling()", 100);
							} else {
								if ($.browser.version < 7) {
									imgPreview.attr('src', this.files
											.item(0).getAsDataURL());
						}
	else {
		oFReader = new FileReader(),
		rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
		oFReader.onload = function(oFREvent) {
				imgPreview.attr('src', oFREvent.target.result);
			};
			var oFile = this.files[0];
			oFReader.readAsDataURL(oFile);
		}
		imgPreview.css({
				"margin-left" : 0,
				"margin-top" : 26,
				"width" : opts.width,
				"height" : opts.height
			});
		setTimeout("autoScaling()", 100);
		}
		}
		opts.callback();
			});
			}
		});
	})(jQuery);
	
	$(document).ready(function(){
		$("#pic").uploadPreview({
			width : 48,
			height : 48,
			imgPreview : $("#imgview"),
			imgType : [ "png", "jpg" ],
			callback : function() {
				return false;
			}
		}); 
	})
	
	$().ready(function(){
		$("#menufrom").validate(); 
	} );
	
	function getShowName(param){
		$('#xtqyyytextsc').val($(param).val());
	}
</script>
</head>

<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">新建应用菜单</a></li>
    </ol>
</div>
<form action="/menuadd" method="post" name="menufrom" id="menufrom" enctype="multipart/form-data">
<input type="hidden" name="levl" value="${levl}">
<input type="hidden" name="valflg" value="1">
<input type="hidden" name="parentid" value="0">
<table class="qytable mt30"  border="0" cellpadding="0" cellspacing="0">
  <tr height="60">
    <td width="170"><label class="xin">应用名称：</label></td>
    <td colspan="2"><input type="text" class="sytext2 {required:true,maxlength:20}" name="menuname"></td>
  </tr>
  <tr height="60">
    <td><label class="xin">应用图标：</label></td>
    <td>
	    <input type="text" class="sytext1 {required:true,maxlength:100}" readonly="readonly" name="imgsrc" id="xtqyyytextsc">
	    <!-- <p>
	    	<input type="file" style="margin-left:15px;" value="上传" class="buth100" onmouseover="this.className='buth100m'" 
	    	onmouseout="this.className='buth100'">
	    </p> -->
        <input type="button" class="no" style="margin-left:13px;" value="上传">
        <input type="file" id="pic" name="pic" class="fileqyyy"  onchange="getShowName(this)">
    </td>
    <td width="140px;" rowspan="2" valign="middle" align="center">
    	<div class="icondiv bdbg">
    		<img id="imgview" src="/images/systems/Icon/icon1.png">
    	</div>
    </td>
  </tr>
   <tr height="60">
    <td><label class="xin">链接地址：</label></td>
    <td><input type="text" class="sytext2 {required:true,maxlength:100}"  name="pathstr"></td>
  </tr>
  <tr height="100">
  	<td></td>
  	<td colspan="2">
       	<input style="margin-right:40px;" type="submit" value="确认" class="butl100" onmouseover="this.className='butl100m'" 
       	onmouseout="this.className='butl100'" >
    	<input type="button" value="取消" class="buth100" onmouseover="this.className='buth100m'" 
    	onmouseout="this.className='buth100'" onclick="location.href='/menulist?levl=0'">
    </td>
  </tr>
</table>
</form>

</body>
</html>

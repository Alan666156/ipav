<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp"%>
<title>系统管理</title>
<script type="text/javascript">
	function initData(){
		$("#filefield").uploadPreview({
			width : 402,
			height : 279,
			imgPreview : "#img1",
			imgType : [ "png", "jpg" ],
			callback : function() {
				return false;
			}
		});
	}
	function clearImage(){
		$("#img1").removeAttr("src");
		//清空file文件
		var file = $("#filefield");
		file.after(file.clone().val(""));
		file.remove();
		//初始化函数绑定和数据
		initData();
	}
	
	function validata(){
		if(isNotNull($("#filefield").val())){
			attrForm.submit();
		}else{
			showMsg("请您选择企业认证的新图片!");
			return;
		}
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
    //对给定的元素绑定onclick事件
    function bind_click(o,f,e){
		switch (o) {
			case 0://选择照片按钮
				if(!f) 	$("#filefield").remove();
				break;
			case 1://认证按钮
				if(f)	$("#sub_btn").bind("click",e);
				else	$("#sub_btn").unbind("click");
				break;
			case 2://清除按钮
				if(f)	$("#clear_btn").bind("click",e);
				else	$("#clear_btn").unbind("click");
				break;
		}
	}
	$(function() {
		$(window).resize();
		//初始化遮罩层高度
		$("#simScrollCont").css("height",$("#mainFrame",window.parent.document).height());
		var message=$("#hid_save_message").val();
		if(isNotNull(message)){
			var info="保存企业认证图片成功,请等待审核结果!";
			if(message=="error") info="保存企业认证图片失败,请重新上传!";
			showMsg(info);
		}
		var back_info='<c:out value="${backInfo }"></c:out>';
		if(back_info!='') $("#back_infomation").show();
		else $("#back_infomation").hide();
		var state=$("#hid_att_state").val();
		var flags=[false,false,false];
		var evts=[null,null,null];
		var sub_cls,sub_val;
		if(state==0){//未认证或认证失败
			sub_val="提交认证";
			flags=[true,true,true];
			evts=[null,validata,clearImage];
			sub_cls="butl100 bcqy1";
		}else if(state==1){//已认证
			sub_val="已认证";
			sub_cls="butl1002 bcqy1";
		}else{//待审核
			sub_val="待审核";
			sub_cls="butl100m bcqy1";
		}
		$("#sub_btn").val(sub_val);
		$("#sub_btn").removeClass().addClass(sub_cls);
		for(var i=0;i<3;i++)	bind_click(i,flags[i],evts[i]);
		initData();
	});
</script>
<style type="text/css">
.file-box {
	position: relative;
	width: 340px
}

.btn {
	background-color: #FFF;
	border: 1px solid #CDCDCD;
	height: 24px;
	width: 70px;
}
</style>
</head>
<body>
<input type="hidden" id="hid_att_state" value="${company.attstuts }"/>
<input type="hidden" id="hid_save_message" value="${message }"/>
	<form action="/saveAtte" method="post" enctype="multipart/form-data" name="attrForm">
		<input type="hidden" name="companyid" value="<c:out value ='${company.companyid }'/>">
		<div class="centmenudiv">
			<ol class="centmenu" id="centmenu">
				<li><a href="#1" onClick="set_menu3(0)" class="current">企业认证</a></li>
			</ol>
		</div>
		<table style="margin-left: 88px; margin-top: 10px;" border="0"
			cellpadding="0" cellspacing="0">
			<tr height="100">
				<td valign="middle" colspan="3">
					<p style="vertical-align: middle; line-height: 40px;">
						<input type="button" value="选择照片" class="buth120 fl">
						<input type="file" name="filefield" id="filefield"	value="选择企业LOGO"
							style="height: 40px; width: 120px; float: left; position: absolute; margin-left: -120px; filter: alpha(opacity : 0); opacity: 0;">&nbsp;&nbsp;请您完成企业信息填写后上传企业营业执照，支持jpg、png格式的图片
					</p>
				</td>
			</tr>
			<tr height="340">
				<td width="430" colspan="2">
						<div class="qyrzs"><img id="img1" style="width:402px; height:279px;" <c:if test="${imagepath ne ''}">src="${imagepath }"</c:if> /></div>
        				<c:if test="${company.attstuts eq '1' }">
        				<div class="qyrzk2"></div>
        				</c:if>
				</td>
				 <td width="320" valign="top">
				    <div class="rzshuom">
				    	<p class="p1">认证说明：</p>
				        <p class="wzdq">1、请您上传加盖企业公章（写清“仅供快捷管家认证使用”）的企业营业执照副本电子版(扫描件或数码相机拍摄均可)；</p>
				        <p class="wzdq">2、我们将在1个工作日内进行审核，此文件仅企业认证使用。我们承诺，您的资料不会用于其他商业用途！</p>
				        <p class="p2">认证规则:</p>
				        <p class="pls">1、营业执照在经营期限内</p>
						<p class="pls">2、营业执照上的企业名称应与注册企业名称一致</p>
				    </div>
				 </td>
			</tr>
			<tr id="back_infomation">
		       <td align="right" valign="top" width="80"><label>认证信息：</label></td>
		       <td><textarea class="textar" style="width:320px;font-size: 13px;" readonly="readonly"><c:out value="${backInfo }"></c:out></textarea></td>
		       <td></td>
 			</tr>
			<tr height="90">
				<td colspan="3"><input type="button" id="sub_btn" ><input type="button" value="清空选择" id="clear_btn" class="buth100" ></td>
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
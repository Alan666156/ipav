<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<script type="text/javascript" src="pages/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
$(document).ready(function() {	
	t = $('.centmenudiv').offset().top;
	mh = $('body').height();
	fh = $('.centmenudiv').height();
	$(window).scroll(function(e){
		s = $(document).scrollTop();	
		if(s > t - 0){
			$('.centmenudiv').css('position','fixed');
			$('.centmenudiv').css('top',0+'px');	
			if(s + fh > mh){
				$('.centmenudiv').css('top',mh-s-fh+'px');	

			}				
		}else{
			$('.centmenudiv').css('position','');
		}
	})
});

</script>
<script type="text/javascript">
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
	$("#compnayform").validate(); 
	var	regexpMoble=/^(17[0]|13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/;
	var	regexpPhone=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/ ;
	$("#submitOver").click(function(){
		var flag=true;
		if(!regexpPhone.test($("#companyPhone").val())){
			flag=false;
			if($("#companyPhone").parent().parent().children().length==2)
			$("#companyPhone").parent().parent().append("<td style='color:red;'>请输入正确的电话号码</td>");
		}
		if(!regexpMoble.test($("#companyMoble").val())){
			flag=false;
			if($("#companyMoble").parent().parent().children().length==2)
			$("#companyMoble").parent().parent().append("<td style='color:red;'>请输入正确的手机号码</td>");
		}
		if($("#companyFax").val()!=""&& !regexpPhone.test($("#companyFax").val())){
			flag=false;
			if($("#companyFax").parent().parent().children().length==2)
			$("#companyFax").parent().parent().append("<td style='color:red;'>请输入正确的传真号码</td>");
		}
		return flag;
	});
	
	$("#companyFax").live("blur",function(){
		if(regexpPhone.test($("#companyFax").val()) || $("#companyFax").val()==""){
			if($("#companyFax").parent().parent().children().length==3)
			$("#companyFax").parent().parent().children()[2].remove();
		}
	});
	$("#companyMoble").live("blur",function(){
		if(regexpMoble.test($("#companyMoble").val()) ){
			if($("#companyMoble").parent().parent().children().length==3)
			$("#companyMoble").parent().parent().children()[2].remove();
		}
	});
	$("#companyPhone").live("blur",function(){
		if(regexpPhone.test($("#companyPhone").val())){
			if($("#companyPhone").parent().parent().children().length==3)
			$("#companyPhone").parent().parent().children()[2].remove();
		}
	});
	var add_class="butl100";
	var mouseover_class="butl100m";
	if($("#hid_action").val()=="modify"){
		add_class="buth100";
		mouseover_class="buth100m";
		var message=$("#hid_save_message").val();
		if(isNotNull(message)){
			var info="保存企业信息失败!";
			if(message=="success")	info="保存企业信息成功!";
			$("#msg_info").html(info);
			$("#simScrollCont").show();
			$("#simScrollContBox").show();
		}
	}
	changeClass(add_class, mouseover_class);
	
	$("#compnayform :text").bind("change",function(){
		changeClass("butl100", "butl100m");
	});
	$("input[name='bullerdate']").bind("blur",function(){
		changeClass("butl100", "butl100m");
	});

	$("#compnayform  select").change(function(){
		changeClass("butl100", "butl100m");
	});
	
	$("#compnayform :radio").click(function(){
		changeClass("butl100", "butl100m");
	});
	
	 $("#pro_id").change(function(){
		 $("#city_id").html("");
		 var pid = $(this).val();
		 if(isNaN(pid)||pid==''){
			 $("#city_id").html("<option value=''>请选择城市</option>");
			 return;
		 }
		 $.post("/getCitys",{province_id:pid},function(data){
			 var city_html="";
			 $(data).each(function(i,v){
				 city_html+="<option value='"+v.id+"'>"+v.name+"</option>";
			 });
			 $("#city_id").html(city_html);
		 });
	 });
	
});
function changeClass(c1,c2){
	$("#submitOver").removeClass().addClass(c1);  
	$("#submitOver").bind("mouseover",function(){
		$("#submitOver").removeClass().addClass(c2);  
	});
	$("#submitOver").bind("mouseout",function(){
		$("#submitOver").removeClass().addClass(c1);  
	});
}
function closeMsg(){
	$("#simScrollCont").hide();
	$("#simScrollContBox").hide();
}
</script>
</head>
<body>
<input type="hidden" id="hid_save_message" value="${message }"/>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">企业信息</a></li>
    </ol>
</div>
<form action="/updateCompany" method="post" name="compnayform" id="compnayform">
<input name="companyid" type="hidden" value="${company.companyid }">
<input id="hid_action" type="hidden" value="${action}">
<table class="qytable" width=" " border="0" cellpadding="0" cellspacing="0">
  <tr height="60">
    <td width="170"><label>企业号：</label></td>
    <td><span><c:out value="${company.companyid }"/></span></td>
  </tr>
  <tr height="60">
    <td><label class="xin">企业名称：</label></td>
    <td valign="middle"><input type="text" class="sytext {required:true,maxlength:50}"  name="companyname" value="${company.companyname }"></td>
  </tr>
  <tr height="60">
    <td><label class="xin">企业简称：</label></td>
    <td><input type="text" class="sytext {required:true,maxlength:20}" name="abbreviation" value="${company.abbreviation }"></td>
  </tr>
  <tr height="60" valign="middle">
    <td><label class="xin">联系人姓名：</label></td>
    <td valign="middle">
    	<input type="text" class="sytext1 {required:true,maxlength:20}" name="contacts" value="${company.contacts }">
        <p class="redop">
        <input type="radio" name="sex" class="ml10 ven" value="1" <c:if test="${company.sex eq '1'}">checked="checked"</c:if>><span class="ven">&nbsp;男</span>
        <input type="radio" name="sex" class="ml10 ven" value="0" <c:if test="${company.sex eq '0'}">checked="checked"</c:if>><span class="ven">&nbsp;女</span>
        </p>
   </td>
  </tr>
  <tr height="60">
    <td><label class="xin">联系人职位：</label></td>
    <td><select class="selects" name="duty">
    		<option value="公司决策人"  <c:if test="${company.duty eq '公司决策人'}">selected="selected"</c:if>>公司决策人</option>
    		<option value="总监"  <c:if test="${company.duty eq '总监'}">selected="selected"</c:if>>总监</option>
    		<option value="经理"  <c:if test="${company.duty eq '经理'}">selected="selected"</c:if>>经理</option>
    		<option value="IT管理人员"  <c:if test="${company.duty eq 'IT管理人员'}">selected="selected"</c:if>>IT管理人员</option>
    		<option value="其他管理人员"  <c:if test="${company.duty eq '其他管理人员'}">selected="selected"</c:if>>其他管理人员</option> 
    	
    	
    </select></td>
  </tr>
   <tr height="60">
    <td><label class="xin">公司电话：</label></td>
    <td><input type="text" id='companyPhone' class="sytext" name="telephone" value="${company.telephone }"></td>
  </tr>
    <tr height="60">
    <td><label class="xin" >手机号：</label></td>
    <td><input type="text" id='companyMoble' class="sytext" name="mobile" value="${company.mobile}"></td>
  </tr>
   <tr height="60">
    <td><label class="xin">邮箱号：</label></td>
    <td><input type="text" class="sytext {required:true,email:true,maxlength:40}" name="email" value="${company.email }"></td>
  </tr>
  <tr height="60">
    <td><label>传真：</label></td>
    <td><input type="text" class="sytext " id='companyFax' name="fax" value="${company.fax }"></td>
  </tr>
  <tr height="60">
    <td><label >企业类型：</label></td>
    <td><select class="selects" name="companytype">
    	<%-- <ipav:option parmtype="companytype" curvalue="${company.companytype}"/> --%>
      <c:if test="${!empty Parames}">
    		<c:forEach items="${Parames}" var="item">
    			<option value="${item.ipavcode}" <c:if test="${company.companytype eq item.ipavcode}">selected</c:if>><c:out value="${item.ipavname}"/></option>
    		</c:forEach>
    	</c:if> 
    </select></td>
  </tr> 
   <tr height="60">
    <td><label >企业成立时间：</label></td>
    <td>
		<input class="sytext time" name="bullerdate" readonly="readonly"  value="${company.bullerdate }"
		type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})">
    </td>
  </tr>
  <tr height="60">
    <td><label>所在地区：</label></td>
    <td>
    <select class="selects2 mr15" name="province" id="pro_id">
    	<option value="">请选择省份</option>
    	<c:if test="${!empty provinces}">
    		<c:forEach items="${provinces}" var="item">
    			<c:choose>
    				<c:when test="${company.province eq item.id}"><option value="${item.id }" selected="selected">${item.name }</option></c:when>
    				<c:otherwise><option value="${item.id }">${item.name }</option></c:otherwise>
    			</c:choose>
	        </c:forEach>
    	</c:if>
    </select>
    <select class="selects2" name="city" id="city_id">
    	<option value="">请选择市</option>    	
	 	<c:if test="${!empty citys}">
    		<c:forEach items="${citys}" var="item">
    			<c:choose>
    				<c:when test="${company.city eq item.id}"><option value="${item.id }" selected="selected">${item.name }</option></c:when>
    				<c:otherwise><option value="${item.id }">${item.name }</option></c:otherwise>
    			</c:choose>
	        </c:forEach>
    	</c:if>
    </select>
    </td>
  </tr>
     <tr height="60">
    <td><label >详细地址：</label></td>
    <td><input type="text" class="sytext {maxlength:100}"  name="address" value="${company.address}"></td>
  </tr>
  <tr height="60">
    <td><label >邮政编码：</label></td>
    <td><input type="text" class="sytext {digits:true,maxlength:6,messages:{digits:'只能输入数字'}}" name="postcode"  value="${company.postcode}"></td>
  </tr>
   <tr height="60">
    <td><label >网址：</label></td>
    <td><input type="text" class="sytext {maxlength:50}" name="webaddress"  value="${company.webaddress}"></td>
  </tr>
  <tr height="100">
  	<td></td>
  	<td>
       	<input style="margin-right:40px;" id="submitOver" type="submit"  value="确认">
        <input type="submit" value="重置" class="buth100" onmouseover="this.className='buth100m'" onmouseout="this.className='buth100'">

    </td>
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

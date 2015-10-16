<%@page language="java" contentType="text/html; charset=utf-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ipav"   uri="ipav" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/zTreeStyle.css" />
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript" src="/pages/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/pages/js/common.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/pages/js/tipswindown.js"></script>
<script type="text/javascript" src="/pages/js/system/user/addUser.js"></script>
<title>系统管理</title>

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
	function saveUser(op){
		if(dutySelectToOrgMsg()==false){
			//dutySelectToOrgMsg();
			return 
		}
		if($("#userform").valid()){
			userform.oper.value =op;
			if(!checkval()){
				return false;
			}
			$("#userform").submit();
		}
	}
	function checkval(){
		var str = $("#registno").val();
		if(checkEmail(str)||checkMobile(str)){
			if(checkEmail(str)){
				userform.regtype.value ="E";
				userform.email.value =str;
				$('input[name=email]').attr("readonly","readonly")//将input元素设置为readonly
				$('input[name=mobile]').removeAttr("readonly");//去除input元素的readonly属性
			}
			if(checkMobile(str)){
				userform.regtype.value ="M";
				userform.mobile.value =str;
				$('input[name=mobile]').attr("readonly","readonly")//将input元素设置为readonly
				$('input[name=email]').removeAttr("readonly");//去除input元素的readonly属性
			}
			 return true ;
		} else{
		 	//alert("手机号或邮箱格式不对！");
		 	msgBox("提示信息", "手机号或邮箱格式不对！");
			 return false ;
		}
	}
	/* $().ready(function(){
		$("#userform").validate({
			        submitHandler: saveUser(op)
			    }); 
	}); */
	
	
	function toolBox(){
		document.getElementById("toolBox").style.display="block";
	}
    function toolBoxhide(){
		document.getElementById("toolBox").style.display="none";
	}
    
    $(window).resize(function() {    
    	$(".simaltBox4").css({
            position: "absolute",
            left: ($(document.body).width() - $(".simaltBox4").outerWidth()) / 2,
            top: ($(document.body).height() - $(".simaltBox4").outerHeight()) / 2
        });
    })

    $(function() {
    	initDuty();
    	initPicBind();
        $(window).resize();
        $("#userform").validate(); 
    });
    

</script>


</head>

<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">添加员工</a></li>
    </ol>
</div>
<form action="/addUser" name="userform" method="post" id="userform" enctype="multipart/form-data">
<input type="hidden" name="companyid" value="<c:out value="${curuser.companyid}" />">
<input type="hidden" name="regtype"/>
<input type="hidden" name="oper">
<input type="hidden" id="partTimeOrg" name="partTimeOrg"/>
<input type="hidden" id="partTimeOrgName" />
<input type="hidden" id="partTimeDuty" name="partTimeDuty"/>
<input type="hidden" id="partTimeCock" name="partTimeCock"/>

<table class="qytable" border="0" cellpadding="0" cellspacing="0">
  <tr height="50">
    <td width="170"><label>员工编号：</label></td>
    <td colspan="2"><input type="text" class="sytext hswn" name="userno" 
    	readonly="readonly"  value="<c:out value="${userno}"/>">
    	
   	</td>
   	<td rowspan="14" valign="top"><div id="userpic" class="sctx"></div><!--选择女就默认女头像的背景-<div class="sctx2"></div>-->
	<p><input type="button" class="sctxsc" value="上传照片"><input id="picfile" type="file" name="picfile" class="filegrnv"></p></td>
  </tr>
  <tr height="50">
    <td><label class="xin">员工姓名：</label></td>
    <td><input type="text" class="sytext {required:true,maxlength:20}" name="username" ></td>
     <td width="200"><p class="redop ml5">
        	<input type="radio" name="sex" class="ml10 ven" value="1" checked onclick="showSexPic(this)"><span class="ven">&nbsp;男</span>
        	<input type="radio" name="sex" class="ml10 ven" value="0" onclick="showSexPic(this)"><span class="ven">&nbsp;女</span>
        </p>
    </td>
  </tr>
  <tr height="50">
    <td><label class="xin">用户注册信息：</label></td>
    <td><input type="text" class="sytext {required:true,maxlength:40}"  placeholder="手机号或邮箱号" 
    name="registno" id="registno" onchange="checkval();"></td>
    <td><span class="zcmsg">作为用户登录账号</span></td>
  </tr>
  <tr height="50">
    <td><label>部门：</label></td>
    <td  onclick="showDep(null)"><input type="text" value="" readonly="readonly" 
    	class="sytext gdnr2" name="orgno"  id="orgno"><input type="hidden" name="orgid" id="orgid" value="" id="orgid"/>
    	
    	</td>
    	<td valign="middle"><!-- <span class="zcmsg fl">选择部门后职务为必填</span> -->
    	    <label class="errormsg dn" id="orgidMsg" style="padding-left:20px;"></label>
    	</td>
  </tr>
    <tr height="50">
    <td><label>职务：</label></td>
    <td id="dutyisTr">
   
    <input type="text" value="" autocomplete="off" 
    	class="sytext" name="dutyname" id="dutyname0"  onclick="dutySelectFuc(0);"  onkeyup="dutyKeyDown(0);" onblur="dutySelectToOrgMsg();">
    	<input type="hidden" name="duty" id="duty0" value="" />
     
	    <div class="dutySelect" id="dutySelect0">
           <ul>
           </ul>
		</div>	
 
    </td>
	  <td valign="middle"><!-- <span class="zcmsg fl" >选择职务后部门为必填</span> -->
	     <label class="errormsg dn" id="dutyMsg" style="padding-left:20px;">请选择职务(选择职务只能从指定的下拉列表中选择)</label>
	  </td>	
  </tr>
    <tr height="50">
    <td><label>是否部门最高职务：</label></td>
    <td colspan="2">
    	<select class="selects" name="chefflg">
    		<option value="0">否</option>
    		<option value="1">是</option>
    	</select>
    	<%-- <select class="selects" name="chefflg">
    		<ipav:option parmtype="default"/>
    	</select> --%>
    </td>
  </tr>
   <tr height="50">
    <td><label>直接主管：</label></td>
    <td colspan="2"><input type="text"  id="directorName"   readonly="readonly" onClick="showdirector()" class="sytext gdnr2">
  				    <input type="hidden" name="director"  id="directorValue"  class="sytext gdnr2">
    </td>
  </tr>
  <tr height="50">
    <td><label>兼职部门及职务：</label></td>
    <td colspan="2">
    	<div id="partTimeInfo" class="jzbmjzw" onClick="showtsBox4()">
        </div>
    </td>
  </tr>
  <%-- <tr height="50">
    <td><label>兼职部门：</label></td>
    <td colspan="2" onclick="showtsBox4()"><input type="text" value="" readonly
    	class="sytext gdnr2" id="exorgno" name="exorgno" >
    </td>
  </tr>
   <tr height="50">
    <td><label>兼职职务：</label></td>
    <td colspan="2">
	    <input type="text" value="" readonly
    		class="sytext gdnr2" name="exduty" onClick="toolBox()" >
	    <div class="toolBoxs dn" id="toolBox">
	    	<div class="toolBox2">
	        	<ul>
	        		<c:if test="${!empty dutys}">
	    				<c:forEach items="${dutys}" var="item">
	            		<li><a href="#1" ><input type="checkbox" id="dy${item.id }" value="${item.id }">
	            			<label for="dy${item.id }"><c:out value="${item.duty_name}"/></label></a></li>
	            	    </c:forEach>
	            	</c:if>
	            </ul>
	            <div class="toolbut">
	            	<input type="button" class="toolbt1 ml15" onClick="toolBoxhide()" value="确定">
	            	<input type="button" class="toolbt2 ml20" onClick="toolBoxhide()" value="取消">
	            </div>
	        </div>        	
  	   </div>
    </td>
  </tr> --%>
   <tr height="50">
    <td><label>手机号码：</label></td>
    <td colspan="2"><input type="text" class="sytext {maxlength:16}" readonly="readonly" name="mobile"></td>
  </tr>
  <tr height="50">
    <td><label>电子邮箱：</label></td>
    <td colspan="2"><input type="text" class="sytext {maxlength:40}" readonly="readonly"  name="email"></td>
  </tr>
  <tr height="50">
    <td><label>所属工作平台：</label></td>
    <td colspan="2"><select class="selects" id="platform" name="platform"></select></td>
  </tr>
  <tr height="50">
    <td><label>工作电话：</label></td>
    <td colspan="2"><input type="text" class="sytext {maxlength:16}" name="phone"></td>
  </tr>
  <tr height="50">
    <td><label>办公地址：</label></td>
    <td colspan="2"><input type="text" class="sytext {maxlength:100}" name="address" ></td>
  </tr>
  <tr height="50">
    <td><label>劳动合同签订单位：</label></td>
    <td colspan="2"><select class="selects" id="labourBelong" name="labourBelong"></select></td>
  </tr>
  <tr height="80">
  	<td></td>
  	<td colspan="2">
       	<input style="margin-right:30px;" type="button" value="确认" class="butl100" onmouseover="this.className='butl100m'" 
       	onmouseout="this.className='butl100'" onclick="saveUser();">
        <input style="margin-right:30px;" type="button" value="确认并继续添加" class="butl120" onmouseover="this.className='butl120m'" 
        onmouseout="this.className='butl120'" onclick="saveUser('again');">
        <input type="button" value="取消" class="buth100" onmouseover="this.className='buth100m'" onmouseout="this.className='buth100'" 
        onclick="location.href='/userlist?companyid=<c:out value ='${curuser.companyid }'/>'">
    </td>
  </tr>
</table>
</form>


<!--兼职职务与部门  begin-->
<div id="simalpha4" class="simalpha">
</div>
<div class="simaltBox4" id="simaltBox4">
	<div class="simaltBoxs4">
		<div class="tctitle">兼职职务与部门<a href="#1" onClick="hidetsBox4()" title="关闭"></a></div>
      	<div class="ptx"></div>
      	<input id="orgType" type="hidden" />
      	<input id="orgIdx" type="hidden" />
		<table  class="xuxtab ml5" width="100%"></table>
		<a href="#1" class="addjzzw" onclick="addOrgInfo()"><span style="margin-left:15px;">增加兼职部门及职务</span></a>
		<p    id='checkall' style="display:none;font-size:12px;color:#F00;margin-left:300px;">兼职部门与兼职职务重复</p>
		<div class="jgtcbutBox" align="center">
        	<input type="button" class="yes mt15" value="确认" onClick="selectPartTimeInfo()">
            <input type="button" class="no mt15 ml30" value="取消" onClick="hidetsBox4()">
            <input type="hidden" id="hid_index"/>
        </div>
	</div>
</div>
  
<!--兼职职务与部门 end-->


</body>
</html>

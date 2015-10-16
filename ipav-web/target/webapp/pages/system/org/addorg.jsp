<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ipav"   uri="ipav" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%-- <%@include file="/pages/system/common/head.jsp" %> --%>
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
<script type="text/javascript">
/**机构负责人START**/

function showLevelsMan(){
	new openCommonView({
		title:'机构负责人选择',
		setting:{
			callback: {
				onClick:onClickTreeMan
			}
		},
		show_user:true
	});	
}

function onClickTreeMan(event, treeId, treeNode){
	if(treeNode.isParent==false){
		$("#orgchefValue").val(treeNode.id);
		$("#orgchefName").val(treeNode.name);
	}
	$("#close_img").click();
}

/**机构负责人 END**/
function onloalrame(){
	var winhight = document.documentElement.clientHeight;
	$(".simalpha").height(winhight);
	$(".simScrollCont").height(winhight);
}
	window.onload=onloalrame;

	var trees ;
	$().ready(function(){
		
		var porgno = '<c:out value="${porgno}"/>'; 
		var porgfullname = '<c:out value="${porgfullname}"/>'; 
		var porgname = '<c:out value="${porgname}"/>'; 
		
		var code = "companyid=" + '<c:out value="${curuser.companyid}"/>'; 
		$.ajax({ type:"POST", url:"/getOrgTree", 
			data:code, success:function(arr){
				trees = arr;
				var co =11;
				if(arr.length<1){
					orgform.orgno.value=co;
					orgform.seqno.value = co;
				}else{
					$.each(trees,function(n,value){
						if("0" == porgno){
							if(porgno == value.parentno){
								if(parseInt(co)<=parseInt(value.orgno)){
									co= parseInt(value.orgno) + parseInt(1) ;
								}
							}
						}else{
							if(porgno == value.orgno){
								if(parseInt(value.num)<1){
									var nm= formatNum(1);
									co=value.orgno+""+nm;
								}else{
									co= parseInt(value.num)+parseInt(1);
								}
							}
							
						}
					});
					if("0"!=porgno){
						orgform.orgfullname.value=porgfullname+"_";
						 $("#parentno").attr("value",porgname);
						 
						 $("#parentno").next().attr("value",porgno);
					}
					
					orgform.orgno.value=co;
					orgform.seqno.value = co;
				}
			} }); 
	} );
	
	function saveOrg(op){
		orgform.isagin.value="";
		if($("#orgform").valid()){
			orgform.isagin.value=op;
			checkSameOrgName();
		}
	}
	//检查是否同级机构是否有同名机构
	function checkSameOrgName(){
		var orgname = $("#orgname").val();
		var orgno  = $("#parentno").next().val();
		var companyid = orgform.companyid.value;
		var parm = "orgno="+orgno+"&orgname="+orgname+"&companyid="+companyid+"&oldorgno=''";
		$.ajax({type:"POST",url:"/existSameName",data:parm,
			success:function(data){
				if(data.res =="Y"){
					$("#orgform").submit();		 
				}else{
					msgBox("温馨提示","该父级机构下已存在同名机构！");
				}
			}	
		});
	}
	
	
	function changCode(){
		var curno = $("#parentno").next().val();
		
		if("0" != curno){
			$.each(trees,function(n,value){
				if(curno == value.orgno){
					if(parseInt(value.num)<1){
						var co= formatNum(1);
						orgform.orgno.value=value.orgno+""+co;
						orgform.seqno.value =value.orgno+""+co;
					}else{
						var co= parseInt(value.num)+parseInt(1);
						orgform.orgno.value=co;
						orgform.seqno.value = co;
					}
				}
			});
		}else{
			var pno ="0";
			var co =11;
			$.each(trees,function(n,value){
				if(pno == value.parentno){
					if(parseInt(co)<=parseInt(value.orgno)){
						co= parseInt(value.orgno) + parseInt(1) ;
					}
				}
			});
			orgform.orgno.value =co;
			orgform.seqno.value = co;
			orgform.parentno.value = pno;
		}
	}
	
	function formatNum(n){
		var rs =n;
		if(n<10){
			rs="0"+""+n;
		}
		return rs;
		
	}
	
	function changName(){
		var curname = $("#orgname").val();
		var curno = $("#parentno").next().val();
		if(curname.length>0 && curno.length>0){
		orgform.orgfullname.value=curno+"_"+curname;
		}
	
		if("0"!= curno){
			$.each(trees,function(n,value){
				if(curno == value.orgno){
					orgform.orgfullname.value=value.orgfullname+"_"+curname;
				}
			});
		}else{
			orgform.orgfullname.value=curname;
		}
	}
	
	function showtsBox2(){
		new openCommonView({
			title:'机构选择',
			setting:{
				callback: {
					onClick:selectOrg
				}
			},
			show_user:false
		});	
	}
	function selectOrg(event, treeId, treeNode){
		$("#parentno").val(treeNode.name);
		$("input[name='parentno']").val(treeNode.id);
		$("#close_img").click();
		changName();
		changCode();
	};


	/*************************************************************************/
	
	$(document).ready(function(){		
	   $("#orgform").validate(); 
	})
</script>
</head>

<body >
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">添加机构</a></li>
    </ol>
</div>
<form action="/addorg" name="orgform" method="post" id="orgform">
<input type="hidden" name="companyid" value="<c:out value='${curuser.companyid}'/>" />
<input type="hidden" name="seqno">
<input type="hidden" name="isagin">
<input type="hidden" name="porgid" value="<c:out value='${porgid}'/>" />

<table class="qytable mt30" border="0" cellpadding="0" cellspacing="0">
  <tr height="60">
    <td width="170"><label>父级机构：</label></td>
    <td>
    <input type="text" id="parentno" class="sytext2 gdnr2" readonly="readonly" value="组织机构"  onclick="showtsBox2()"/>
    <input type="hidden" readonly="readonly" name="parentno" value="0"/>
    </td>
    <td><span class="lsmsg">备注：新建一级机构，父级机构选择组织机构，集团内各子公司请分别添加为一级机构，不要添加为集团的二级机构。</span></td>
  </tr>
  <tr height="60">
    <td><label class="xin">机构名称：</label></td>
    <td><input type="text" class="sytext2 {required:true,maxlength:50}" name="orgname" id="orgname" 
    onfocus="changName();"   onchange="changName();"></td>
    <td></td>
  </tr>
  
  <tr height="60">
    <td><label>机构负责人：</label></td>
    <td colspan="2"><input type="text" class="sytext2 hswn"  id="orgchefName" readonly="true"  onclick="showLevelsMan();">
                    <input type="hidden" class="sytext2 hswn"  name="orgchef" id="orgchefValue">
     </td>
  </tr>
  
  <tr height="60">
    <td><label>机构编号：</label></td>
    <td colspan="2"><input type="text" class="sytext2 hswn" name="orgno" readonly="true"></td>
  </tr>
  <tr height="60">
    <td><label>机构全称：</label></td>
    <td colspan="2"><input type="text" class="sytext2 hswn" readonly="true" name="orgfullname" ></td>
  </tr>
  <tr height="95">
    <td><label>机构描述：</label></td>
    <td colspan="2"><textarea class="textar {maxlength:100}" name="remark"></textarea></td>
  </tr>
  <tr height="100">
  	<td></td>
  	<td colspan="2">
       	<input   type="button" style="margin-right:20px;" name="submitType" value="确认" onclick="saveOrg('')"  class="butl100" onmouseover="this.className='butl100m'" onmouseout="this.className='butl100'">
       	<input style="margin-right:20px;" type="button" name="submitType" value="确认并继续添加" onclick="saveOrg('agin')" class="butl120" onmouseover="this.className='butl120m'" onmouseout="this.className='butl120'">
        <input type="button" value="取消" class="buth100" onmouseover="this.className='buth100m'" onmouseout="this.className='buth100'" onclick="location.href='/queryorglist?companyid=<c:out value ='${curuser.companyid }'/>'">
    </td>
  </tr>
</table>
</form> 

<div class="btm"></div>
</body>
</html>

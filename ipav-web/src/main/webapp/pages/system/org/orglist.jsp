<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
	<SCRIPT type="text/javascript">
		
		var setting = {
			view: {
				dblClickExpand: dblClickExpand,
				nameIsHTML: true
			},
			data: {
				simpleData: {
					enable: true,
					idkey:"id",
					pIdKey:"Pid",
					nameKey:"name",
					rootPId:""
				},
			key: {
				title: "title"
			}
			},
			callback:{
				beforeClick: function(treeId, treeNode) {//点击前的函数
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.expandNode(treeNode);
					if (treeNode.isParent) {
						return true;
					} else {
						return true;
					}
				},
				onClick:function(e,treeId,treeNode){
					selectOrgs(treeNode.id);
				}
			}
		};

		function dblClickExpand(treeId, treeNode) {
			return treeNode.level > 0;
		}
		
		function selectOrgs(orgno){
			$("#orgno").attr("value",orgno);
			orgform.submit();
		}
		
		var zNodes ;
		$().ready(function(){
			var code = "companyid=" + '<c:out value="${curuser.companyid}"/>'; 
			var orgno = '<c:out value="${orgno}"/>'; 
 			$.ajax({ type:"POST", url:"/showOrgTrees", 
				data:code, success:function(arr){
					$.each(arr,function(i,value) {
							value.title=value.name;
							if(orgno!=undefined && orgno!=''){
							if(value.id==orgno){
								value.name="<span style='background:#ffebbf;border:1px #ffc773 solid；padding:2px 5px;'>"+value.name+"</span>";
							}}
						    
							if(value.id=="0"){
								value["icon"]="/pages/images/platform/jigou.png";								 
							}else if(value.Pid=="0"){
								value["icon"]="/pages/images/platform/1_close.png";	
							}else{
								value["icon"]="/pages/images/platform/1_open.png";
							}
						});
					
					zNodes = arr;
					$.fn.zTree.init($("#treeDemo"), setting, eval(arr));
				} });
			
		} );
		
		function updateOrg(companyid){
				
			var orgid= $('input[name="op"]:checked').val();
			if(orgid == undefined){
				 msgBox("温馨提示","请选择机构！");
				//alert("请选择机构！");
				return false ;
			}
			var faNo=$("tr")[2].children[0].children[2].value;
			location.href="/gotoaddorg?companyid="+companyid+"&oper=update&orgid="+orgid+"&faNo="+faNo;
		}
	   function delOrg(){
		   var orgid= $('input[name="op"]:checked').val();
			if(orgid == undefined){
			   msgBox("温馨提示","请选择机构！");
				//alert("请选择机构！");
				return false ;
			}
		       msgDialog({"title":"确认消息","centent":"确认删除?","okfunction":delOrgAfter})
	   }
	   
	   function  addOrg(companyid){
		   var orgid= $('input[name="op"]:checked').val();
			if(orgid == undefined){
				orgid= "";
			}
		    location.href="/gotoaddorg?companyid="+companyid+"&oper=add&orgid="+orgid;
		   
	   }
		
		function delOrgAfter(){
			  var orgid= $('input[name="op"]:checked').val();
				var parm = "orgid="+orgid;
				var faNo=$("tr")[2].children[0].children[2].value;
				$.ajax({type:"POST",url:"/checkOrgvalflg",data:parm,
					success:function(data){
						if(data){
							location.href="/delorg?orgid="+orgid+"&faNo="+faNo;
						}else{
							//alert("机构存在启用状态的子机构，不可删除！");
							 msgBox("温馨提示","机构存在启用状态的子机构，不可删除！");
						}
					}
				});
			}
		 
		//上移
		function upSeqno(obj,selfno,pno,sefseqno){
			var utr = $(obj).parent().parent().prev("tr");
			var partno= $(utr).find('input[name="temparentno"]').val();//父级机构号
			if(pno == partno){
				var othseqno= $(utr).find('input[name="tempseqno"]').val();
				var otherogno= $(utr).find('input[name="temorgno"]').val();
				changSqeno(selfno,otherogno,sefseqno,othseqno);
			}else{
				 var trs = $(obj).parent().parent().prevAll("tr");
				 var inx  =-1;
				 $.each(trs,function(i,value){
					var tempno=  $(value).find('input[name="temparentno"]').val();
					if(pno == tempno){
						inx = i;
						var othseqno= $(value).find('input[name="tempseqno"]').val();
						var otherogno= $(value).find('input[name="temorgno"]').val();
						changSqeno(selfno,otherogno,sefseqno,othseqno);
						return false ;
					}
				 });
				 if(inx<0){
					 ///alert("不存在同级机构不能上移！");
					 msgBox("温馨提示","不存在同级机构不能上移！");
						return false ;
				 }
				
			}
		}
		//下移
		function downSeqno(obj,selfno,pno,sefseqno){
			var utr = $(obj).parent().parent().next("tr");
			var partno= $(utr).find('input[name="temparentno"]').val();
			if(pno == partno){
				var otherogno= $(utr).find('input[name="temorgno"]').val();
				var othseqno= $(utr).find('input[name="tempseqno"]').val();
				changSqeno(selfno,otherogno,sefseqno,othseqno);
			}else{
				var trs = $(obj).parent().parent().nextAll("tr");
				 var inx  =-1;
				$.each(trs,function(i,value){
						var tempno=  $(value).find('input[name="temparentno"]').val();
						if(pno == tempno){
							inx = i;
							var othseqno= $(value).find('input[name="tempseqno"]').val();
							var otherogno= $(value).find('input[name="temorgno"]').val();
							changSqeno(selfno,otherogno,sefseqno,othseqno);
							return false ;
						}
					 });
				if(inx<0){
					//alert("不存在同级机构不能下移！");
					msgBox("温馨提示","不存在同级机构不能下移！");
					return false ;
				}
			}
		}
		function changSqeno(orgno,otherorgno,seqno,otherseqno){
			var faNo=$("tr")[2].children[0].children[2].value;
			var companyid=$("#companyid").val();
			var parm = "orgno="+orgno+"&otherno="+otherorgno+"&companyid="+companyid+"&seqno="+seqno+"&othersqno="+otherseqno;
			$.ajax({type:"POST",url:"/changeSeqno",data:parm,
				success:function(data){
					$("#orgno").attr("value",faNo);
					orgform.submit();			 
				}
			});
		}
		/**
		 * 确认操作
		 */
		function tsqyesFunction(arr,type){
			//type[1:删除机构对话框]
			$("#tsqMsg").remove(); 
			if(type==1){
				delOrgAfter();
			}
		}
	
	</SCRIPT>
	<style type="text/css">
.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
	</style>
</head>

<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">机构维护</a></li>
    </ol>
</div>
<form action="/queryorglist" method="post" name="orgform">
<input name="companyid" id="companyid" type="hidden" value="${curuser.companyid}">
<input type="hidden" name="orgno" id="orgno" >
<div class="toolBox" style="margin-top:14px; width:97.9%; margin-left:15px;">
	<a href="#1" class="cur8" onClick="addOrg('<c:out value ='${curuser.companyid }'/>');">新增机构</a>
	<a href="#1" class="cur3" onClick="updateOrg('<c:out value ='${curuser.companyid }'/>');">修改</a>
	<a href="#1" class="cur4" onClick="delOrg()">删除</a>
</div>
<div class="tabBox" style=" margin-top:13px;">
	<table width="100%">
	<tr>
    <td width="215" valign="top">
    	<div class="zzjgBox">
                <ul id="treeDemo" class="ztree" style="border:0px; background:#fff; width:190px; margin-top:0px; height:490px;"></ul>
        </div>
    </td>
    <td  valign="top">
    	<div class="zzjgtabBox jghet">
        <div class="tabBoxyz">
     <table class="tab2015" id="senfe" width="100%" cellpadding="0" cellspacing="0">
      <tr class="tabtitle">
        <td width="50">选择</td>
        <td>机构名称</td>
        <td >机构全称</td>
        <td>机构负责人</td>
        <td>机构代码</td>
        <td width="210">操作</td>
      </tr>
      <c:if test="${!empty list}">
	     <c:forEach items="${list}" var="org" varStatus="item">
	      <tr>
	      	
	        <td><input type="radio"  <c:if test="${orgno==org.orgno }">checked="checked"</c:if> name="op" value="<c:out value="${org.orgid }"/>">
	        	<input type="hidden" name="temorgno" value="<c:out value="${org.orgno }"/>">
	        	<input type="hidden" name="temparentno" value="<c:out value="${org.parentno }"/>">
	        	<input type="hidden" name="tempseqno" value="<c:out value="${org.seqno }"/>">
	        </td>
	        <td><c:out value="${org.orgname }"/></td>
	        <td><c:out value="${org.orgfullname }"/></td>
	        <td><c:out value="${org.orgchefName }"/></td>
	        <td><c:out value="${org.orgno }"/></td>
	        <td>
	        	<c:choose>
	        		<c:when test="${item.index eq 0 }">
		        		<input type="button" value="上移" class="upbutNo">&nbsp;
		        	</c:when>
		        	<c:otherwise>
		        		<input type="button" value="上移" class="upbut" onclick="upSeqno(this,'<c:out value="${org.orgno }"/>','<c:out value="${org.parentno }"/>','<c:out value="${org.seqno }"/>');">&nbsp;
		        	</c:otherwise>
	        	</c:choose>
	        	
	        	<c:if test="${!item.last}">
            		<input type="button" value="下移" class="downbut" onclick="downSeqno(this,'<c:out value="${org.orgno }"/>','<c:out value="${org.parentno }"/>','<c:out value="${org.seqno }"/>')">&nbsp;
            	</c:if>
            	<c:if test="${item.last}">
            		<input type="button" value="下移" class="upbutNo" >&nbsp;
            	</c:if>
            </td>
	      </tr>
	      </c:forEach>
      </c:if>
    </table>
      </div>
<!--           <div class="tabBox"> -->
<%--           <input type="button" style="float:left;" value="添加机构" onclick="addOrg('<c:out value ='${curuser.companyid }'/>');"   --%>
<!--           class="butl100" onmouseover="this.className='butl100m'" onmouseout="this.className='butl100'"> -->
<%--           <input type="button" style="float:left; margin-left:30px;" value="修改机构" onclick="updateOrg('<c:out value ='${curuser.companyid }'/>');"   --%>
<!--           class="butl100" onmouseover="this.className='butl100m'" onmouseout="this.className='butl100'"> -->
<!--           <input type="button" style="float:left;margin-left:30px;" value="删除机构" onclick="delOrg();"   -->
<!--           class="buth100" onmouseover="this.className='buth100m'" onmouseout="this.className='buth100'"> -->
<!--           </div> -->
     </div>
	</td>
	</tr>
	</table>
</div>
</form>
</body>
</html>

<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<title>系统管理</title>
<script type="text/javascript">
		var trees ;
		
		$().ready(function(){
			var code = ""; 
			 $.ajax({ type:"POST", url:"/getMenutree", 
				data:code, success:function(arr){
					trees = eval(arr);
					showRoles();
				} }); 
			/* $("#rolefrom").validate({
			        submitHandler: cusDo("add")
			    }); */
		});
		
		function cusDo(op){
			if("add"== op){
				if($("#rolefrom").valid()){
					$("#rolefrom").attr("action","/addRole");
					$("#rolefrom").submit();
				}
			}
			if("cancle"==op){
				var rno= $("#fromroleno").val();
				$("#rolefrom").attr("action","/mianRole?roleno="+rno);
				$("#rolefrom").submit();
			}
		}
		var row_count = 0; 
		
		function showRoles(){
			var tab = $("#menulist");
			var ftr = $("<tr></tr>");
			var ftd = $("<td></td>");
			ftd.attr("width","100");
			ftd.attr("valign","top");
			ftd.append('<h3 class="lsmsg" style="margin-left:0px;">角色权限</h3>');
			ftr.append(ftd);
			tab.append(ftr);
			row_count++;
			$.each(trees,function(n,value){
				if(value.id != 0){
					if(value.Pid ==0){
						var row = $("<tr></tr>");
						row.attr("valign","top");
						var td = $("<td></td>");
						
						var chid= '<p class="qxinfop">';
						chid =chid+'<input type="checkbox" style="filter:alpha(opacity:0);opacity:0;" id="'+value.id+'" name="menuid" value="'+value.id+'">';
						chid =chid+'<input type="checkbox" id="show'+value.id+'" onclick="chainCheck('+value.id+','+value.Pid+')">'; 
						chid =chid+'<label>'+value.name+'</label></p>';
						td.append(chid);
						row.append(td);
						if(value.open == "true"){
							var td3 = $("<td></td>");
							row.append(td3);
							earchtd(value.id,row,td3);
						}
						tab.append(row);
						row_count++; 
					}
				}
				
			});
		}
		function earchtd(pid,row,td){
			$.each(trees,function(i,obj){
				if(obj.Pid == pid){
					var tbs = $("<table></table>");
					var rr =$("<tr></tr>");
					rr.attr("valign","top");
					var dd =$("<td></td>");
					var chid = '<p class="qxinfop">';
					chid =chid+'<input type="checkbox" style="filter:alpha(opacity:0);opacity:0;" id="'+obj.id+'"   name="menuid" value="'+obj.id+'">';
					
					chid =chid+'<input type="checkbox" id="show'+obj.id+'" onclick="chainCheck('+obj.id+','+obj.Pid+')">';  
					
					chid =chid+'<label>'+obj.name+'</label></p>';
					dd.append(chid);
					rr.append(dd);
					tbs.append(rr);
					td.append(tbs);
					if(obj.open == "true"){
						 var tdd =$("<td></td>");
						 rr.append(tdd);
						 earchtd(obj.id,rr,tdd);
					}
					
				}
			});
			//row.append(td);
		}
		
		function chainCheck(idval,pidval){
			
			if($("#show"+idval).is(":checked")){
				$("#"+idval).attr("checked","true");
				checkOpt(pidval);
				checkAllChild(idval);
				checkAllShowChild(idval);
				checkShowOpt(pidval);
			}else{
				uncheckOpt(idval);
				uncheckPOpt(pidval);
				uncheckShwoOpt(pidval);
			}
		}
		function checkOpt(pidval){
			$.each(trees,function(i,obj){
				if(obj.id == pidval&& obj.id!=0){
					$("#"+obj.id).attr("checked","true");
					checkOpt(obj.Pid);
				}
			});
		}
		function checkAllChild(pidval){
			$.each(trees,function(i,obj){
				if(obj.Pid == pidval&& obj.id!=0){
					$("#"+obj.id).attr("checked","true");
					checkAllChild(obj.id);
				}
			});
			
		}
		function checkAllShowChild(pidval){
			$.each(trees,function(i,obj){
				if(obj.Pid == pidval&& obj.id!=0){
					$("#show"+obj.id).attr("checked","true");
					checkAllShowChild(obj.id);
				}
			});
			
			
		}
		function checkShowOpt(pidval){
			var flg =0 ;
			var temp ;
			$.each(trees,function(i,obj){
				if(obj.id == pidval){
					temp = obj;
				}
				if(obj.Pid == pidval){
					if($("#show"+obj.id).is(":checked")){
					}else{
						flg++;
					}
				}
			});
			if(flg==0){
				$("#show"+temp.id).attr("checked","true");
				checkShowOpt(temp.Pid);
			} 
		}
		
		
		function uncheckOpt(idval){
			$.each(trees,function(i,obj){
				if(obj.Pid == idval){
					$("#"+obj.id).removeAttr("checked");
					$("#show"+obj.id).removeAttr("checked");
					uncheckOpt(obj.id);
				}
			});
		}
		function uncheckPOpt(pidval){
			var flg =true ;
			var temp ;
			$.each(trees,function(i,obj){
				if(obj.id == pidval){
					temp = obj;
				}
				if(obj.Pid == pidval){
					if($("#"+obj.id).is(":checked")){
						flg =false;
					}
				}
			});
			if(flg){
				$("#"+temp.id).removeAttr("checked");
				uncheckPOpt(temp.Pid);
			}
			
		}
		
		function uncheckShwoOpt(pidval){
			var flg =0 ;
			var temp ;
			$.each(trees,function(i,obj){
				if(obj.id == pidval){
					temp = obj;
				}
				if(obj.Pid == pidval){
					if($("#"+obj.id).is(":checked")){
						flg =flg+1;
					}
				}
			});
			if(flg>0){
				$("#show"+temp.id).removeAttr("checked");
				uncheckShwoOpt(temp.Pid);
			} 
		}
		
		$(document).ready(function(){
			if($("#flag").val()!='')
				$("#leftFrame",window.parent.document).attr("src","/system/left?op=ROLE");
		});
</script>

 
</head>
<body>
<div class="centmenudiv mt15">
	<input type="hidden" id="flag" value="${flag}"/>
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" class="current">新建角色</a></li>
    </ol>
</div>
<form action="/addRole" method="post" name="rolefrom" id="rolefrom">
<input type="hidden" name="companyid" value="<c:out value="${curuser.companyid}" />">
<input type="hidden" name="fromroleno" id="fromroleno" value="<c:out value="${fromroleno}" />">

<div class="tabBox">
<table class="qytable" border="0" cellpadding="0" cellspacing="0">
  <tr height="60">
    <td><label class="xin">角色名称：</label></td>
    <td><!-- <input type="text" class="sytext2 {required:true,maxlength:20}" name="rolename"> -->
    <input type="text" class="sytext2" name="rolename">
    </td>
    <td></td>
  </tr>
   <tr height="95">
    <td><label>角色描述：</label></td>
    <td colspan="2"><textarea class="textar {maxlength:100}" name="mark"></textarea></td>
  </tr>
</table>
</div>
 
 
<div>
	<table class="qxtab mt20 ml20" border="0" id="menulist" cellpadding="0" cellspacing="0">
		
    </table>
</div> 

<div>
   	<input type="button" style="float:left;" value="确认" class="butl100" onmouseover="this.className='butl100m'" 
   	onmouseout="this.className='butl100'" onclick="cusDo('add');" >
    <input type="button" style="float:left; margin-left:30px;" value="取消" class="buth100" 
    onmouseover="this.className='buth100m'" onmouseout="this.className='buth100'" onClick="cusDo('cancle');">
</div>
 </form>   
</body>
</html>

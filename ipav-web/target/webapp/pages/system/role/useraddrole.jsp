<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/pages/system/common/head.jsp" %>
<title>系统管理</title>
<script type="text/javascript">
		var setting = {
			view: {
				selectedMulti: false
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
					idkey:"id",
					pIdKey:"Pid",
					nameKey:"name",
					rootPId:""
				}
			},
			callback: {
				onCheck: onCheck
			}
		};

		function onCheck(e, treeId, treeNode) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodes =  treeObj.getCheckedNodes(true);
			$("#usersdiv").html("");
			$(nodes).each(function(i,v){
				if(v.isParent==false)
					insertUser(v);
			});
		}		

		function insertUser(tem){
			if(isNotNull(tem)){
				var flag=chkUserExist(tem.id);
				if(tem.checked&&!flag){
					console.log("insert::"+tem.id);
					$("#usersdiv").append("<div id='hiduserid"+tem.id+"'><input type='hidden' name='userid' value='"+tem.id+"'><a href='#1'>"+tem.name+"</a></div>");
				}else if(!tem.checked&&flag){
					console.log("remove::"+tem.id);
					$("#usersdiv").find("#"+tem.id).remove();	
				}
			}
		}
		//返回值:存在,ture;不存在:false
		function chkUserExist(uid){
			var flag=false;
			$("#usersdiv input[type='hidden']").each(function(i,v){
				if(isNotNull(v.value)&&uid==v.value)
					flag=true;				
			});
			return flag;
		}
		
		
		$().ready(function(){
			var code = "companyid=" + '<c:out value="${curuser.companyid}"/>'; 
			var userids = '<c:out value="${userids}"/>';
			$.ajax({ type:"POST", url:"/getOrgUserTree", 
				data:code, success:function(arr){
					$.fn.zTree.init($("#treeDemo"), setting, arr);
					initCheckItem(userids);
				} });
			
		} );
		
		function initCheckItem(userids){
			var ids=userids.split(",");
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			
			var nods = treeObj.transformToArray(treeObj.getNodes());
			 
			for (var i = 0; i < ids.length; i++) {
				 for (var j = 0; j < nods.length; j++) {
					 if(nods[j].id == ids[i]){
						treeObj.checkNode(nods[j], true, true,true);
					 }
					
				}
			}
		}
		
		function saveUserole(){
			var users= $("input[name=userid]").val();
			if(users == undefined){
				//alert("请选择用户！");
				msgBox("提示信息","请选择用户！");
				return false;
			}
			var cout =0;
			$('input[name="roleid"]:checked').each(function(){
				cout =cout+1;
			});
			if(cout<1){
				//alert("请选择角色！");
				msgBox("提示信息","请选择角色！");				
				return false;
			}
			roleuserfrom.submit();
		}
</script>
<style type="text/css">
.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
</style>
</head>

<body>
<div class="centmenudiv">
	<ol class="centmenu" id="centmenu">
    	<li><a href="#1" onClick="set_menu3(0)" class="current">员工授权</a></li>
    </ol>
</div>
<form action="/useraddrole" method="post" name="roleuserfrom">
<input type="hidden" name="companyid" value="${companyid }">
<div class="tabBox">
	<table width="100%" border="0">
      <tr height="30">
        <td width="220"><h3 class="lsmsg" style="margin-left:0px;">选择员工</h3></td>
        <td width="210"><h3 class="lsmsg" style="margin-left:0px;">选中员工</h3></td>
        <td><h3 class="lsmsg" style="margin-left:0px;">选择角色</h3></td>
      </tr>
      <tr>
        <td>
        <div class="zzjgBox">
           <ul id="treeDemo" class="ztree" style="border:0px; background:#fff; width:190px; margin-top:0px; height:490px;"></ul>
        </div>
        </td>
        <td>
        <div class="zzjgBox yglist" style="height:500px;overflow-y:auto; " align="center" id="usersdiv">
        </div>
        </td>
        <td valign="top">
        	<div class="jeinfoBox">
            	<ul>
            		<c:if test="${!empty roles}">
            			<c:forEach items="${roles}" var="item">
                			<li><input type="checkbox" name="roleid" value="${item.roleno }"
                			<c:if test="${item.havflg eq 'Y'}">checked</c:if> >
                				<label><c:out value="${item.rolename }"/></label>
                			</li>
               			</c:forEach>
                    </c:if>
                </ul>
            </div>
            <div class="tabBox" style="margin-left:0px;">
            <p class="lsmsg" style="margin-left:0px;">备注：选中员工为2人及以上时，员工原角色不显示，选择角色进行授权后，覆盖选中员工的所有权限。</p><br/>
            <input style="margin-right:30px;" type="button" value="授权" class="butl100" onmouseover="this.className='butl100m'" 
            onmouseout="this.className='butl100'" onclick="saveUserole();">
        <input type="button" value="取消" class="buth100" onmouseover="this.className='buth100m'" 
        onmouseout="this.className='buth100'" onclick="location.href='/roleuserlist?companyid=<c:out value ='${companyid }'/>'">
            </div>

        </td>
      </tr>
    </table>

</div>
</form>
</body>
</html>

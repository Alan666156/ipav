<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/zTreeStyle.css" />
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/pages/js/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="/pages/js/wage/wagelist.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>
<script type="text/javascript" src="/pages/js/zeroclipboard-2.1.6/dist/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>

</head>

<body class="ptbg">

	<div class="pt_center rsglBox">
		<!--top menu begin-->
		<div class="rsmenuBox">
			<ol class="rsmenu" id="topmenu">
				<!-- <li><a href="#1" onclick="tab('rcxs',0,2)" id="rcxs_title_0">人员管理</a></li> -->
				<li><a href="#1" onclick="tab('rcxs',1,2)" class="current"
					id="rcxs_title_1">工资单</a></li>
			</ol>
		</div>
		<!--top menu end--->

		<!--top menu begin-->
		<div class="pt_leftmenuBox" id="leftBox">

			<div class="dn" id="rcxs_main_0">
				<ul class="ptleftmu">
					<!---人事管理三级菜单-->
				</ul>
			</div>

			<div id="rcxs_main_1">
				<ul class="ptleftmu">
					<li><a href="#1" onclick="changeTab('rsgl',0,2)" id="rsgl_title_0"
						class="current">发送工资单</a></li>
					<li><a href="#1" onclick="changeTab('rsgl',1,2)" id="rsgl_title_1">工资单项目设置</a></li>
				</ul>
			</div>

		</div>
		<!--top menu end--->

		<!---center begin-->
		<div class="pt_centerBox" id="mainBox">

			<!---我的工资单---->
			<div class="gzdBox" id="rsgl_main_0">

				<div class="pttitle">
					<ol class="centmenu" id="centmenu">
						<li><a href="#1" class="current">发送工资单</a></li>
					</ol>
				</div>

				<div class="tabBox serchBox"
					style="margin-left: 15px; margin-top: 15px;">
					<ol>
						<li>工资月份：
							<input id="wageMonthCon" class="pt_text pttime" name="wageMonthCon" readonly="readonly"  value=""
							type="text" onClick="WdatePicker({dateFmt:'yyyy年M月',maxDate:'%y-%M'})">
							</li>
						<!-- <li class="ml10">机构：<input type="text" class="pt_text ptmore"
							id="orgselect" value="" onClick="showMenu();" readonly="readonly">
							<div id="menuContent" class="menuContent" style="position: absolute;">
								<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
							</div>
							<input type="hidden" name="orgno" id="orgno"></li> -->
<!-- 						<li class="ml10">机构：<input type="text" class="pt_text ptmore" -->
<!-- 							id="orgselect" value="" onClick="showOrgLevels()" readonly="readonly"> -->
							<li class="ml10">机构：<input type="text" class="pt_text ptmore"
							id="orgselect" value="" onClick="showorgDiv()" readonly="readonly">
							<div class="dn treeBox" style=" margin-left: 42px;" id="orgTree"></div>
							<input type="hidden" name="orgno" id="orgno"></li>
						<li class="ml10">员工：
<!-- 						<input type="text" class="pt_text"	id="username" name="username" onkeyup="autoCompleteUser()" value=""> -->
						<input type="text" class="pt_text"	id="username" name="username" onClick="showEmployeeDiv()" value="">
							<div class="prompt" id="prompt">
			                	<ul>
			                    </ul>
			                </div>	
						</li>
						<li class="ml20"><input type="button" id="wageSearch" value="查询" class="ptserch" onclick="searchClick()"></li>
					</ol>
				</div>

				<div class="toolBox">
					<a href="#1" class="cur3" onclick="getWageRecordInfoByChecked(1)">修改</a>
					<a href="#1" class="cur4" onclick="deleteWageRecords()">删除</a>
					<a href="#1" class="cur5" onclick="showjg2()">工资单导入</a>
					<a href="#1" class="cur6" onclick="exportRecords();">工资单导出</a>
					<a href="#1" class="cur7" onClick="exportExcel();">模板下载</a>
					<a href="#1" class="cur1" onclick="sendWageInfoBefor()">发送工资单</a>
				</div>

				<div class="ptx"></div>
				<div id="tbBoxgdt3" class="ml15 mr15">
				<table class="pttable mr15" width="120%" border="0" cellspacing="0"
					cellpadding="0">
					<tr class="ptitles" id="wageTitles">
						<th width="115" style="text-align: center" width="60" align="center"><input type="checkbox" onclick="allWageSelect(this)"> 序号</th>
						<th width="115" style="text-align: center" width="100">工资期间</th>
						<th width="115" style="text-align: center">员工部门</th>
						<th width="115" style="text-align: center">员工编号</th>
						<th width="115" style="text-align: center">员工姓名</th>
						<th width="115" style="text-align: left" align="left">应发工资</th>
						<th width="115" style="text-align: left" align="left">实发工资</th>
					</tr>
				</table>
				<!-- <div id="tbBoxgdt3"> -->
					<table id="wagelist" class="pttable mr15" width="120%" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td width="115" align="center">合计</td>
							<td width="115"></td>
							<td width="115"></td>
							<td width="115"></td>
							<td width="115"></td>
							<td width="115" align="left"><label class="red"></label></td>
							<td width="115" align="left"><label class="red"></label></td>
						</tr>
					</table>
				</div>

				<div class="ptpageBox" id="pageInfo">
                <div class="rightpagebox fr">
	                <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
	                <a href="#1"><div class="pageys"><span>上一页</span></div></a>
	                <ol id="pageol" class="pageol">
	                    <li><a href="#1" class="mpages" onClick="set_menu4(0)"><div><span>1</span></div></a></li>
	                    <li><a href="#1" onClick="set_menu4(1)"><div><span>2</span></div></a></li>
	                    <li><a href="#1" onClick="set_menu4(2)"><div><span>3</span></div></a></li>
	                    <li><p class="slh">...<p></li>
	                    <li><a href="#1"><div class="pageysn" onClick="set_menu4(3)"><span>10</span></div></a></li>
	                </ol>
	                <a href="#1"><div class="pageys"><span>下一页</span></div></a>
	                <input type="text" class="gopage">
	                <a href="#1"><div class="pageys"><span>GO</span></div></a>
                </div>
     	     </div>
         </div>    
    	<!---我的工资单-end--->
        
        
        <!--工资单项目设置---->
        <div class="gzdBox dn" id="rsgl_main_1">	
        
	        <div class="pttitle">
	            <ol class="centmenu" id="centmenu">
	                <li><a href="#1" class="current">工资单项目设置</a></li>
	            </ol>
	        </div>
        
        	<div class="tabBox serchBox" style="margin-left:15px; margin-top:5px;">
      			<p class="rtradio"
						style="float:left; width:100%; margin-left:0px; margin-top:0px; line-height:30px;">
						<input type="radio" name="extStatus" value="-1" checked onclick="getWageExtList(this)"><label>全部</label><input
							type="radio" value="1" name="extStatus" onclick="getWageExtList(this)"><label>启用</label><input
							type="radio" value="0" name="extStatus" onclick="getWageExtList(this)"><label>禁用</label>
      			</p>
        	</div>
        
	        <div class="toolBox" style="margin-top:5px;">
	        	<a href="#1" class="cur8" onClick="addExtWage()">新增项目</a>
	            <a href="#1" class="cur3" onclick="getWageExt()">修改</a>
	            <a href="#1" class="cur4" onclick="updateWageExt(0,0)">删除</a>
	            <a href="#1" class="cur9" onclick="updateWageExt(0,1)">启用</a>
	            <a href="#1" class="cur10" onclick="updateWageExt(0,2)">禁用</a>
	            <a href="#1" class="cur11" onclick="updateWageExt(1,3)">上移</a>
	            <a href="#1" class="cur12" onclick="updateWageExt(1,4)">下移</a>
	        </div>
      
      		<div class="ptx"></div>
		    <table class="pttable" width="803" border="0" cellspacing="0"	cellpadding="0">
			      <tr class="ptitles">
			        <th width="115" style="text-align:center" width="60"
										align="center"><input type="checkbox" onclick="allWageExtSelect(this)"> 序号</th>
			        <th width="150" style="text-align:center" width="100">工资表项目名称</th>
			        <th width="115" style="text-align:center">项目类型</th>
			        <th width="115" style="text-align:center">是否发送</th>
			        <th width="115" style="text-align:center">工资释疑人</th>
			        <th width="115" style="text-align:center" align="left">项目备注</th>
			        <th width="80" style="text-align:center" align="left">状态</th>
			      </tr>
		    </table>
      		<div id="tbBoxgdt4">
      		<table id="wageExtList" class="pttable" width="803" border="0" cellspacing="0" cellpadding="0">
			      <!-- <tr>
			        <td width="115" align="center"><input type="checkbox" name="wageExtCheck" onclick="allWageExtSelect(this)">&nbsp;&nbsp;1</td>
			        <td width="150">应发工资</td>
			        <td width="115">小数型</td>
			        <td width="115">是</td>
			        <td width="115">小贤</td>
			        <td width="115"></td>
			        <td width="80">启用</td>
			      </tr> -->
      		</table>
      		</div>
        </div> 
        <!----工资单项目设置  end--->
    </div>
    <!-- center end--->

<!--simTestContent1 begin-->
<div id="simScrollCont5" class="simScrollCont">
</div>
	<div class="simScrollContBox" id="simScrollContBox5">
    	<div class="simScrollContBox2">
        	<div class="tctitle">工资单导入<a href="#1"
						onClick="hidejg2()" title="关闭"></a>
				</div>
			<form id="wageImport" action="" method="POST" enctype="multipart/form-data">
	            <table width="100%" class="qytable mt20" border="0">
	              <tr height="50">
	                <td><label>导入工资所属期间：</label></td>
	                <td width="195"><input id="wageMonth" class="pttcText pttime" name="wageMonth" readonly="readonly"  value=""
								type="text" onClick="WdatePicker({dateFmt:'yyyy年M月'});" onfocus="clearImportWarnning(this);">
					</td>
	                <td>&nbsp;</td>
	              </tr>
	              <tr height="50">
	                <td><label>导入工资发放日期：</label></td>
	                <td><input id="wageTime" class="pttcText pttime" name="wageTime" readonly="readonly"  value=""
								type="text" onClick="WdatePicker({dateFmt:'yyyy年M月d日'})" onfocus="clearImportWarnning(this);">
					</td>
	                <td>&nbsp;</td>
	              </tr>
	              <tr height="50">
	                <td><label>选择文件：</label></td>
	                <td><input id="xlsFile" type="text" class="pttcText" readonly="readonly">
	                </td>
	                <td>
	                <!-- <input type="button" class="pttcButh85 ml10 filepsz" value="浏览..."><input type="file" class="filebut" onchange="document.getElementById('xlsFile').value=this.value" > -->
	                <input type="button" class="pttcButh85 ml10 filepsz" value="浏览..."><input type="file" class="filebut" id="wageFile" name="wageFile" onchange="fileSelect(this)" >
	                </td>
	              </tr>
	              <tr height="50">
	                <td></td>
	                <td colspan="2"><input type="button"
								class="pttcBut85" value="导入" onClick="importExcel();"><input
								type="button" class="pttcButh85 ml30" value="取消"
								onClick="hidejg2()"></td>
	              </tr>
	            </table>
			</form>
        </div>
    </div>
<!--simTestContent1 end-->


<!--simTestContent1 begin-->
<div id="simScrollCont6" class="simScrollCont">
</div>
<div class="simScrollContBoxs" id="simScrollContBox6">
    	<div class="simScrollContBoxs2">
        	<div class="tctitle">新增工资单项目<a href="#1"
						onClick="hidejg3()" title="关闭"></a>
				</div>
            <table width="100%" border="0">
              <tr height="50">
                <td width="130"><label>工资表项目名称：</label></td>
                <td width="195">
                	<input type="text" class="pttcText" onfocus="clearExtWageWarnning(this);">
                	<input type="hidden" readonly="readonly" />
                </td>
                <td width="90"><label>是否发送：</label></td>
                <td width="195" align="left">
                	<p class="ptredio">
						<input type="radio" value="1" name="qysh" checked id="rado1"><label for="rado1">是</label>
						<input type="radio" value="0" name="qysh" id="rado2"><label for="rado2">否</label>
					</p>
				</td>
              </tr>
              <tr height="50">
              	<td><label>工资释疑人：</label></td>
                <td width="195">
<!--                 	<input type="text" value="" class="pttcText ptmore" readonly="readonly" onclick="showLevels();" > -->
                	<input type="text" value="" class="pttcText ptmore" id="resolve_person_txt" readonly="readonly" onclick="showuserDiv();" >
			        <div class="dn treeBox" id="resolve_u">
		            	<!-- <ul>
		                	<li><p class="treezk"></p><a href="#1" class="current"><span></span><label for="che1">集团</label></a>
		                    	<ul>
		                        	<li>
		                            <p class="treezk"></p><a href="#1"><span></span><label for="che2">技术部</label></a>					                            	<ul>
		                            		<li>
		                            <a href="#1"><span></span><label for="che3">张三</label></a>
		                            		</li>
		                            	</ul>
		                            </li>
		                        </ul>
		                    </li>
		                    <li><p class="treezd"></p><span></span><label for="che4">集团</label></a></li>
		                    <li><p class="treezd"></p><a href="#1"><span></span><label for="che5">集团</label></a></li>
		                </ul> -->
		            </div>
                	<input id="resolve_person" type="hidden" value="" style="display:none">
                </td>
                <td width="90"><label>项目类型：</label></td>
                <td width="195" align="left">
                	<p class="ptredio">
						<input type="radio" value="0" name="qysh2" checked id="rado3"><label for="rado3">文本</label>
						<input type="radio" value="2" name="qysh2" id="rado4"><label for="rado4">小数型</label>
					</p>
				</td>
              </tr>
              <tr height="70">
                <td><label>项目备注：</label></td>
                <td colspan="3"><textarea class="pttexta"></textarea></td>
              </tr>
              <tr height="60">
                <td></td>
                <td colspan="3">
                <input type="button" class="pttcBut85" value="确认"
							onClick="addWageExt()">
                <input type="button" class="pttcBut135 ml30" onclick="addNewWageExt()"
							value="确认后继续新增">
                <input type="button" class="pttcButh85 ml30" value="取消"
							onClick="hidejg3()">
						</td>
              </tr>
            </table>

        </div>
    </div>
<!--simTestContent1 end-->





<!--simTestContent1 begin-->
<div id="simScrollCont8" class="simScrollCont">
</div>
	<div class="simScrollContBox3" id="simScrollContBox8">
    	<div class="simScrollContBoxs3">
        <div class="tctitle"><label>工资期间：2014年10月</label><a href="#1"
						onClick="hidejg8()" title="关闭"></a>
				</div>
        
 		<input type="hidden" value="" id="recordId" />
      <div class="ptx"></div>
        <div id="tbBoxgdt2">
         <table id="yourTableID" class="tabmsg" align="center"
						width="670" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="100" align="right">工资期间：</td>
                <td>2014年9月</td>
                <td width="100" align="right">员工部门：</td>
                <td>技术部</td>
            </tr>
            <tr>
                <td align="right">员工编号：</td>
                <td>001</td>
                <td align="right">员工姓名：</td>
                <td>张小明</td>
            </tr>
            <tr>
                <td align="right">应发工资：</td>
                <td>
                	<input type="text" style="border:0px; height:30px; width:100%; text-indent:10px;" value="" readonly="readonly" />
                </td>
                <td align="right">实发工资：</td>
                <td>
                	<input type="text" style="border:0px; height:30px; width:100%; text-indent:10px;" value="" readonly="readonly" />
                </td>
            </tr>
        </table>
        </div>

        </div>
    </div>
<!--simTestContent1 end-->
<!--simTestContent1 begin-->
<div id="simScrollCont12" class="simScrollCont">
</div>
	<div class="simScrollContBox4" id="simScrollContBox12">
    	<div class="simScrollContBoxs4">
        <div class="tctitle">提示信息<a href="#1" onClick="hidejg12()" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        	<p id="warnning" class="alertmsg">XXX，dafadsfadf你即将修改XXXX的个人信息，请慎重修改，不然你就联系不到他了！</p>
        </div>
        <div class="alertbut">
           <!--  <input type="button" value="否" class="no fr mr20"  onClick="hidejg12()"> -->
        	<input type="button" value="确定" class="yes fr mr20" onClick="hidejg12()">
        </div>

        </div>
    </div>
<!--simTestContent1 end-->

<!--导入信息提示 begin-->
<div id="simScrollCont13" class="simScrollCont">
</div>
<div class="simScrollContBox5" id="simScrollContBox13">
		<div class="simScrollContBoxs5">
        	<div class="tctitle">导入信息提示<a href="#1" onClick="hidejg13()" title="关闭"></a></div>
            	<div class="drBoxtab" id="copymag" >
                      <table class="pttable"  style="float:left; margin-left:0px;" width="803" border="0" cellspacing="0" cellpadding="0">
                          <tr class="ptitles">
                            <th width="100" style="text-align:center" width="60" align="center">序号</th>
                            <th width="703" style="text-align:left; text-indent:2em;" width="100">错误信息</th>
                          </tr>
                      </table>
                </div>
                <div class="mt20" style="width:100%;" align="center" >
                <!-- <input type="button" class="pttcBut85" value="全选" id="copyButton" data-clipboard-target="copymag" onclick="copytoclip()"> -->
                <input type="button" class="pttcBut85" value="全选复制" id="copyButton">
                <input type="button" class="pttcButh85 ml30" value="取消" onClick="hidejg13()">
                </div>
        </div>
</div>
</div>
</div>
</body>
<script language="javascript">
	var client = new ZeroClipboard($("#copyButton"));
	client.on("copy",function(e){
		 var content="";
			$.each($("#copymag").find('tr'),function(idxTr,tr){
				
				$.each($(tr).find('th'),function(idxTd,td){
					if(idxTd==1)
							content+="          ";
					content+=$(td).text()
				})
				
				$.each($(tr).find('td'),function(idxTd,td){
					if(idxTd==1)
						content+="              ".substr((idxTr+"").length);
					content+=$(td).text()
				}) 
				content+="\r\n";
			})
		e.clipboardData.setData("text/plain",content);
	});
	function onloalrame() {
		var winhight = window.parent.document.documentElement.clientHeight-114;
		var leftboxs = winhight - 45;
		var mainboxs = winhight - 45;
		var tabbox3 = winhight - 302;
		var tabbox4 = winhight - 283;
		document.getElementById("leftBox").style.height = leftboxs + "px";
		document.getElementById("mainBox").style.height = mainboxs + "px";

		document.getElementById("tbBoxgdt3").style.height = tabbox3 + "px";
		document.getElementById("tbBoxgdt3").style.minHeight = tabbox3 + "px";
		document.getElementById("tbBoxgdt3").style.maxHeight = tabbox3 + 1
				+ "px";
		document.getElementById("tbBoxgdt3").style.overflowY = "auto";
		document.getElementById("tbBoxgdt3").style.width = 803 + "px";
		document.getElementById("tbBoxgdt3").style.overflowX = "scroll";

		document.getElementById("tbBoxgdt4").style.height = tabbox3 + "px";
		document.getElementById("tbBoxgdt4").style.minHeight = tabbox3 + "px";
		document.getElementById("tbBoxgdt4").style.maxHeight = tabbox3 + 1
				+ "px";
		document.getElementById("tbBoxgdt4").style.overflowY = "auto";
		document.getElementById("tbBoxgdt4").style.width = 818 + "px";
		document.getElementById("tbBoxgdt4").style.overflowX = "hidden";

		document.getElementById("tbBoxgdt2").style.minHeight = 325 + "px";
		document.getElementById("tbBoxgdt2").style.maxHeight = 325 + 1 + "px";
		document.getElementById("tbBoxgdt2").style.overflowY = "auto";
		document.getElementById("tbBoxgdt2").style.width = 685 + "px";
		document.getElementById("tbBoxgdt2").style.overflowX = "hidden";
		$("#141",window.parent.document).height($("body").height());
	}
	window.onload = onloalrame;

	$(window).resize(
			function() {
				$(".simScrollContBox").css(
						{
							position : "absolute",
							left : ($(window).width() - $(".simScrollContBox")
									.outerWidth()) / 2,
							top : ($(window).height() - $(".simScrollContBox")
									.outerHeight()) / 2
						});
				$(".simScrollContBoxs").css(
						{
							position : "absolute",
							left : ($(window).width() - $(".simScrollContBoxs")
									.outerWidth()) / 2,
							top : ($(window).height() - $(".simScrollContBoxs")
									.outerHeight()) / 2
						});
				$(".simScrollContBox3").css(
						{
							position : "absolute",
							left : ($(window).width() - $(".simScrollContBox3")
									.outerWidth()) / 2,
							top : ($(window).height() - $(".simScrollContBox3")
									.outerHeight()) / 2
						});
				
				$(".simScrollContBox4").css({
			        position: "absolute",
			        left: ($(window).width() - $(".simScrollContBox4").outerWidth()) / 2,
			        top: ($(window).height() - $(".simScrollContBox4").outerHeight()) / 2
			    });
				$(".simScrollContBox5").css({
			        position: "absolute",
			        left: ($(window).width() - $(".simScrollContBox5").outerWidth()) / 2,
			        top: ($(window).height() - $(".simScrollContBox5").outerHeight()) / 2
			    });
			})

	$(function() {
		$(window).resize();
	});
</script>
</html>
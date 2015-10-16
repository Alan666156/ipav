<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/zTreeStyle.css" >
<link rel="stylesheet" href="/pages/js/kindeditor-4.1.7/themes/default/default.css" />
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/My97DatePicker/WdatePicker.js"></script>
<script charset="utf-8" src="/pages/js/kindeditor-4.1.7/kindeditor.js"></script>
<script charset="utf-8" src="/pages/js/kindeditor-4.1.7/lang/zh_CN.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="/pages/js/notice/notice.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>

</head>

<body class="ptbg">
<input type="hidden" id="hiduserid" value="<c:out value ='${curuser.userid }'/>" />
<input type="hidden" id="hidusername" value="<c:out value ='${curuser.username }'/>"/>
<c:if test="${!empty notice_id}"><input type="hidden" id="hidnotice_id" value="<c:out value ='${notice_id}'/>"/></c:if>
<div class="ggmainBox"  style=" overflow:scroll; overflow-Y:auto;overflow-X:hidden;">
<div class="pt_center rsglBox" id="pt-center">
	<div class="pt-tfg2" id="titgd4" style="position: fixed; background: none repeat scroll 0% 0% rgb(168, 214, 226); height: 12px;"></div>
	<div class="mutop"></div>
	<!--top menu begin-->
    <div class="pt_leftmenuBox" id="leftBox">    
    	<div class="dn" id="rcxs_main_0">
	    	<ul class="ptleftmu">
	        <!---人事管理三级菜单-->
	        </ul>
        </div>
    	<div class="lefme" style="position:absolute; top:12px;">
    		<c:forEach items="${menulist}" var="item">
    			<c:if test="${item eq 144}">
    				<input type="hidden" id="hidNew" value="1"/>
    				<a href="javascript:;" class="addgg" onClick="notice.fnc.gotoNewOrEditNotice()"><span class="addggbut lfad1">新建公告</span></a>
    			</c:if>
    		</c:forEach>
    		<ul class="ptleftmu">
        	<c:forEach items="${menulist}" var="item">
        		<c:if test="${item eq 145}">
        			<input type="hidden" id="hidAll" value="1"/>
	        		<li><a href="javascript:;"  onclick="notice.confirmationView(0)" id="notice_title_0">全部公告</a></li>
	        	</c:if>
	        	<c:if test="${item eq 146}">
	        		<input type="hidden" id="hidModify" value="1"/>
	        	</c:if>
    			<c:if test="${item eq 147}">
    				 <input type="hidden" id="hidType" value="1"/>
           			 <li><a href="javascript:;"  onclick="notice.confirmationView(1)" id="notice_title_1">类型设置</a></li>
            	</c:if>
    		</c:forEach>
        </ul>
        </div>
    </div>
    <!--top menu end--->
	
    <!---center begin-->
    <div class="pt_centerBox" id="mainBox">
    
    
    	<!---新建或修改公告---->
        <div class="gzdBox dn" id="AddOrEditNotice">	
        	<input type="hidden" id="actionmodel"/>        	
        	<input type="hidden" id="companyname"/>
            <div class="pttitle"  style="position:absolute; top:12px;">
                <ol class="centmenu" id="centmenu">
                    <li><a href="javascript:;" class="current" id='toptitle'></a></li>
                </ol>
            </div>
            
            <div class="addggBox" style=" margin-top: 40px;">
				<form action="addNotice" id="noticeFrom" method="POST" enctype="multipart/form-data">				
            	<table width="750px" class="ggtab mr15 ml20 mt15" cellpadding="0" cellspacing="0">
                <tr height="60">
                	<td width="90"><label class="xin">类型：</label></td>
                    <td>
               	    	<select class="ptselect" style="float: none;" id="gg_type" name='typeid' onchange="notice.fnc.autoModifyNo()"></select>
               	    	<input type="hidden" id="editgglxid"/>
               	    	<input type="hidden" id="editggindex"/>
	                    <p class="errortabd"></p>
                    <td><label class="xin">接收人：</label></td>
                    <td>
               	    	<input type="text"  class="pt_text160 gdnr2" id="gg_sended" onclick="notice.fnc.showSends()" readonly="readonly"/>
               	    	<div id="hidsendsids"></div><div id="hidsendsnames"></div>
	                    <p class="errortab"></p>
                </tr>
                <tr height="60">
                	<td><label class="xin">签发人：</label></td>
                    <td width="254">
               	    	<input type="text"  class="pt_text160 gdnr2"  id="gg_issued" readonly="readonly" onclick="notice.fnc.showOrglist(0)">
               	    	<div class="dn treeBox" id="issued_org"></div><p class="errortab"></p>
                    </td>
                    <td width="90"><label>发文部门：</label></td>
                    <td>
               	    	<input type="text"  class="pt_text160 gdnr2"  id="gg_department" readonly="readonly" onclick="notice.fnc.showOrglist(1)">
               	    	<div class="dn treeBox" id="dep_org"></div><p class="errortab"></p>
                    </td>
                </tr>
            	<tr height="60">
                	<td><label class="xin">标题：</label></td>
                    <td colspan="3"><input type="text" class="pt_text500" name="title" id="gg_title" placeholder="请输入标题" /><p class="errortab"></p></td>
                </tr>
                <tr height="60">
                	<td><label class="xin">公告编号：</label></td>
                    <td colspan="3"><input type="text" class="pt_text500" name="noticeno" id="gg_no" disabled="disabled" ><p class="errortab"></p></td>
                </tr>
                <tr height="270">
                	<td valign="top"><label class="xin" style="margin-top:15px;">内容：</label></td>
                	<td colspan="4">
					<textarea name="content" id="gg_content" style="width:600px;height:240px;visibility:hidden; margin-left:5px; padding-left:5px;"></textarea>
					<p class="errortab"></p></td>
                </tr>
                <tr height="35">
                	<td></td>
                	<td colspan="4">
                		 <div style='display:inline;height:35px;' id='loadfilediv'><p style="width:80px; margin-left:4px;line-height:29px;position: relative;" class="fl"><a href="javascript:;" style=" color:#0198d1;" >上传单个附件</a><input title="仅支持上传单个附件(附件大小不能超过20M)" type="file" name="filedata" id="file_id" class="filebut" onchange="notice.view.showfileinfo()"></p></div>
                    	 <div style='display:none;height:35px;' id='filediv'><span id='fileinfo' class="fl" style=" line-height:29px; margin-left:10px;"></span><a href="javascript:;" class="delete ml10" style="color:#0198d1" title="删除" onclick="notice.view.clearfileinfo()"></a></div>
                    </td>
                </tr>
                <tr>
                	<td width="90"><label>其他设置：</label></td>
                    <td colspan="3">
                    	<ol class="addol">
                        	<li>
                                <p class="ggtpbox"><input type="checkbox" name="iscomment" value="1" id="dstime3"><label for="dstime3">允许评论</label></p>
                        	</li>
                            <li>
                                <p class="ggtpbox"><input type="checkbox" name="istop" value="1" id="dstime2" disabled="disabled"><label for="dstime2">置顶</label></p>
                        	</li>
                        	<li>
                                <p class="ggtpbox" onClick="notice.view.dsTime()"><input type="checkbox" id="dstime1" disabled="disabled"><label for="dstime1">定时发布</label></p>
                        	</li>
                        	<li id="dsTimeBox1" class="ml10 dn">
                                <!--弹出定时时间 begin-->
                                	<p style=" float:left;">
                                	<input type="text" class="pt_text145 pttime" id="seldate"  onclick="WdatePicker({dateFmt:'yyyy年MM月d日',minDate:'%y-%MM-%d',readOnly:true})">
                                	<select class="se24" id="hours">
                                	</select>:
                                	<select class="se24" id="minute">
                                	</select>
                                	</p><i class="errmsg15"></i>
                                <!--弹出定时时间 end-->
                        	</li>
                    	</ol>
                    </td>
                </tr>
                
                <tr>
                	<td width="90"><label>提醒方式：</label></td>
                    <td colspan="3">
                    		<ol class="addol">
                                <li>
                                    <p class="ggtpbox"><input type="checkbox" class="remindtype" value="1" id="dstime4" checked="checked" disabled="disabled"><label for="dstime4">应用消息</label></p>
                                </li>
                                <li>
                                    <p class="ggtpbox"><input type="checkbox" class="remindtype" value="2" id="dstime5"  disabled="disabled"><label for="dstime5">邮件</label></p>
                                </li>
                                <li>
                                    <p class="ggtpbox"><input type="checkbox" class="remindtype" value="3" id="dstime6"  disabled="disabled"><label for="dstime6">短信</label></p>
                                </li>
                            </ol>
                    </td>
                </tr>
                <tr height="70">
                	<td></td>
                	<td colspan="3">
               	    	<input type="button" class="pttcBut85 ml5"   value="保存" onclick="notice.fnc.saveOrRelease(0)" />
                        <input type="button" class="pttcBut85 ml30"  value="发布" onclick="notice.fnc.saveOrRelease(1)"/>
                        <input type="button" class="pttcBut85 ml30"  value="预览" onClick="notice.view.showPreview()"/>
               	    	<input type="button" class="pttcButh85 ml30" value="取消" onClick="notice.view.cancel()" />
                    </td>
                </tr>
                <input type="hidden" id="hidgguserid" name="userid" />
				<input type="hidden" id="hidggissuedid" name="issuedid" />
				<input type="hidden" id="hidggorgid" name="orgid" />
                <input type="hidden" id="hidggid" name="id"/>
                <input type="hidden" id="hidIstime" name="istime"/>
                <input type="hidden" id="hidIstrue" name="istrue" />
           		<input type="hidden" id="hidremindtype" name="remindtype"/>
        	    <input type="hidden" id="hidnostr" name="nostr"/>
	        	<input type="hidden" id="hidnoindex" name="noindex"/>
	        	<input type="hidden" id="hidIsUpdateFile" name="updateFile"/>
                </table>
                </form>
            </div>
    
         </div>   
    	<!---新建公告-end--->
    	
    	<!--公告预览 begin-->
		<div id="simScrollCont15" class="simScrollCont">
		</div>
			<div class="simScrollContBox8" id="simScrollContBox15">
		    	<div class="simScrollContBoxs8">
		        	<div class="tctitle">公告预览<a href="javascript:;" onClick="notice.view.hidePreview()" title="关闭"></a></div>
		            <div class="ggtitle">
		            	<p class="yltitle"><label id="yltt"></label></p>
		                <p class="yltimsg"><span><label id="ylbh"></label></span><span>签发人：<label id="ylissued"></label></span></p>
		            </div>
		            <div class="gginfo" id="ylinfo">
		            </div>
		            <div class="ggtitle" style="border:0px; margin-top:23px;" align="center">
		            	<input type="button" class="pttcBut85" value="发布" onClick="notice.fnc.saveOrRelease(1)">
		                <input type="button" class="pttcButh85 ml30" value="取消" onClick="notice.view.hidePreview()">
		            </div> 
		           
		        </div>
		    </div>
		<!--公告预览 end-->
		
    	<!--选择接收人 begin-->
		<div id="simalpha1" class="simalpha">
		</div>
		<div id="simaltBox1" class="simaltBox">
			<div class="simaltBoxs">
				<div class="tctitle">选择公告接收人员<a href="javascript:;" onClick="notice.view.hideSendsDiv()" title="关闭"></a></div>
				<div class="ten"></div>
				<!-- 显示组织机构 -->
		      	<div class="jglistBox">
	            	<div class="treeBoxs">
	                    <ul id="treeDemo1" class="ztree" style="margin-top:0;"></ul>
	                </div>
		      	</div>
		      	
		        <!--显示已选人员-->
		        <div class="jguserlistBox">
		        	<div class="jguserlist">
		            	<ul class="jguserulinfo" id="userinfos">
		                </ul>
		            </div>
		        </div>
		        
				<div class="jgtcbutBox" align="center">
		        	<input type="button" class="yes mt15" value="确定" onClick="notice.fnc.createSends()">
		            <input type="button" class="no mt15 ml30" value="取消" onClick="notice.view.hideSendsDiv()">
		        </div>
		        
			</div>
		</div>
		<!--选择接收人  end-->
		
	    <!--选择签发人或者发文部门begin-->
<!-- 	    <div id="simalpha2" class="simalpha"> -->
<!-- 		</div> -->
<!-- 		<div class="simaltBox2" id="simaltBox2"> -->
<!-- 			<div class="simaltBoxs2"> -->
<!-- 				<div class="tctitle"><span id="orgDiv_title"></span></span><a href="#1" onClick="notice.view.hideOrgDiv()" title="关闭"></a></div> -->
		 		
<!-- 				<div class="ten"></div> -->
<!-- 				显示组织机构 -->
<!-- 		        <div class="jglistxzBox"> -->
<!-- 		        	<div class="jglistxz"> -->
<!-- 		            	<ul id="treeDemo2" class="ztree" style="margin-top:0;"></ul> -->
<!-- 		            </div> -->
<!-- 		        </div>				 -->
<!-- 			</div> -->
<!-- 		</div> -->
	    <!--选择签发人或者发文部门end-->
	    <!-- 退出新建公告begin -->
	    <div id="simScrollCont11" class="simScrollCont"></div>
	    <div class="simScrollContBox4" id="simScrollContBox11">
	    	<div class="simScrollContBoxs4">
	        <div class="tctitle">提示信息<a href="javascript:;" onClick="notice.view.hideExitDiv(0)" title="关闭"></a></div>
	        <div class="ptx"></div>
	        <div id="tbBoxgdt2" class="alertBox">
	        	<p class="alertmsg" id="contenthtml"></p>
	        </div>
	        <div class="alertbut">
	            <input type="button" value="否" class="no fr mr20"  onClick="notice.view.hideExitDiv(0)">
	        	<input type="button" value="是" class="yes fr mr20" onClick="notice.view.hideExitDiv(1)">
	        	<input type="hidden" id="hid_action_type"/>
	        </div>
	     </div>
	    </div>
	    <input type="hidden" id="hid_action"/>
	    <!-- 退出新建公告end -->
    	<!---全部公告---->
        <div class="gzdBox" id="notice_main_0" >	
<!--             <input type="hidden" id="viewmodel"/> -->
            <input type="hidden" id="gg_istrue"/>
            <div class="pttitle"  style="position:absolute; top:12px;">
                <ol class="centmenu" id="centmenu">
                    <li><a href="javascript:;" class="current">全部公告</a></li>
                </ol>
            </div>
            
            <!-- 查询条件  -->
            <div class="tabBox serchBox" style="margin-left:15px; margin-top:15px; margin-top: 55px;">
			<ol>
				<li>
					公告发布时间：<input type="text" class="pt_text145 pttime" id="startTime" onclick="WdatePicker({dateFmt:'yyyy年M月d日',readOnly:true})" />至
					<input type="text" class="pt_text145 pttime" id="endTime" onclick="WdatePicker({dateFmt:'yyyy年M月d日',readOnly:true})">
					<p id="time_error" style="display: inline;font-size: 12px;margin-left: 4px;color:red;"></p>
				</li>
			</ol>
			</div>
            <div class="tabBox serchBox" style="margin-left:12px; margin-top:15px;">
                <ol>
                    <li><select class="ptselect" id="querygglx"></select></li>
                    <li class="ml20"><select class="ptselect" id="queryggflag"></select></li>
                    <li class="ml20"><input type="text" class="pt_text200" id="queryinfo" style="margin-left:22px;" placeholder="请输入查询的内容"></li>
                    <li class="ml20"><input type="button" value="查询" class="ptserch" onclick="notice.fnc.initQueryNotice()"></li>
                </ol>
            </div>         
            <div class="tabBox listBox" id="gglist"></div>
            <div style="height: 60px;"></div>
          <!---page begin--->
          <div class="ptpageBox"  id="pageInfo_0" style="height: 55px;background: none repeat scroll 0% 0% #FFF;position: fixed;bottom: 0px;margin-left:20px;width: 810px;">
              <div class="rightpagebox fr">
               <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
               <a href="javascript:;"><div class="pageys"><span>上一页</span></div></a>
               <ol id="pageol" class="pageol">
                   <li><a href="javascript:;" class="mpages" onClick="set_menu4(0)"><div><span>1</span></div></a></li>
                   <li><a href="javascript:;" onClick="set_menu4(1)"><div><span>2</span></div></a></li>
                   <li><a href="javascript:;" onClick="set_menu4(2)"><div><span>3</span></div></a></li>
                   <li><p class="slh">...<p></li>
                   <li><a href="javascript:;"><div class="pageysn" onClick="set_menu4(3)"><span>10</span></div></a></li>
               </ol>
               <a href="javascript:;"><div class="pageys"><span>下一页</span></div></a>
               <input type="text" class="gopage">
               <a href="javascript:;"><div class="pageys"><span>GO</span></div></a>
              </div>
   	       </div>
   	      <!---page end---> 
         </div>   
    	<!---全部公告-end--->
    	
        <!--发布或删除公告提示信息div begin-->
	    <div id="simScrollCont13" class="simScrollCont"></div>
		  <div class="simScrollContBox4" id="simScrollContBox13">
	    	<div class="simScrollContBoxs4">
	        <div class="tctitle">提示信息<a href="javascript:;" onClick="notice.view.hidggDiv()" title="关闭"></a></div>
	        <div class="ptx"></div>
	        <div id="tbBoxgdt2" class="alertBox">
	        	<p class="alertmsg" id="contentstr"></p>
	        	<input type="hidden" id="noticeid" />
	        	<input type="hidden" id="actiontt" />
	        </div>
	        <div class="alertbut">
	            <input type="button" value="否" class="no fr mr20"  onClick="notice.view.hidggDiv()">
	        	<input type="button" value="是" class="yes fr mr20" onClick="notice.fnc.relOrdelNotice()">
	        </div>
	     </div>
	    </div> 
		<!--发布或删除公告提示信息div end-->

		<!--已读名单 begin-->
		<div id="simScrollCont16" class="simScrollCont">
		</div>
			<div class="simScrollContBox9" id="simScrollContBox16">
		    	<div class="simScrollContBoxs9">
		        <div class="tctitle">已读名单<a href="javascript:;" onClick="notice.view.hideReades()" title="关闭"></a></div>
		        
		        <div class="ptx"></div>
		        <div class="alertnameBox" align="center" id="readeslist"></div>
		       <div class='praisePageBox' id="readePage" style="width:375px;margin-left:30px;"></div>
		        <div class="alertbut">
		            <input type="button" value="确定" class="yes fr mr20"  onClick="notice.view.hideReades()">
		        </div>
			</div>
		</div>
		<!--已读名单 end-->
        <!--公告类型设置---->
        <div class="gzdBox dn" id="notice_main_1">	
        
          <div class="pttitle"  style="position:absolute; top:12px;">
                <ol class="centmenu" id="centmenu">
                      <li><a href="javascript:;" class="current">类型设置</a></li>
              </ol>
            </div>
        
            <div class="tabBox serchBox" style="margin-left:15px; margin-top:5px; margin-top: 40px;">
          		<p class="rtradio" style="float:left; width:100%; margin-left:0px; margin-top:0px; line-height:30px;">
                <input type="radio" name="qysh" value="" id="che1"><label for="che1">全部</label>
                <input type="radio" name="qysh" value="0" checked id="che2"><label for="che2">启用</label>
                <input type="radio" name="qysh" value="1" id="che3"><label for="che3">停用</label>
                </p>
            </div>
        
            <div class="toolBox" style="margin-top:5px;">
                <a href="javascript:;" class="cur15" onclick="noticetype.fnc.initlist()">刷新</a>
                <a href="javascript:;" class="cur8" onClick="noticetype.view.showNewDiv()">新增类型</a>
                <a href="javascript:;" class="cur3" onClick="noticetype.view.showEditDiv()">修改</a>
                <a href="javascript:;" class="cur4" onClick="noticetype.view.showDiv(3)">删除</a>
                <a href="javascript:;" class="cur11" onclick="noticetype.view.moveUp()">上移</a>
                <a href="javascript:;" class="cur12" onclick="noticetype.view.moveDown()">下移</a>
                <a href="javascript:;" class="cur9" onClick="noticetype.view.showDiv(0)">启用</a>
                <a href="javascript:;" class="cur10" onClick="noticetype.view.showDiv(1)">禁用</a>
				<a href="javascript:;" class="cur21" onClick="noticetype.view.showDiv(2)">设置默认类型</a>
            </div>
      
      <div class="ptx"></div>
      <div id="tbBoxgdt4">
      <table class="pttable"  width="803" border="0" cellspacing="0" cellpadding="0">
      <thead>
      <tr class="ptitles">
        <th width="80" style="text-align:center" width="60" align="center">
        	<input type="checkbox" id="firstChk" onclick="noticetype.fnc.selectChk()"> 序号</th>
        <th width="150" style="text-align:center" width="100">类型</th>
        <th width="250" style="text-align:center">描述</th>
        <th width="90" style="text-align:center">设置默认</th>
        <th width="90" style="text-align:center">创建人</th>
        <th width="130" style="text-align:center" align="left">创建时间</th>
        <th width="60" style="text-align:center">状态</th>
      </tr>
      </thead>
      <tbody id="typelist"></tbody>     
      </table>
      </div>
   		<!---page begin--->
          <div class="ptpageBox"  id="pageInfo_1" style="height: 55px;background: none repeat scroll 0% 0% #FFF;position: fixed;bottom: 0px;margin-left:20px;width: 810px;">
              <div class="rightpagebox fr">
               <span class="fl mr15 hs lh27">共有<label>1</label>条记录</span>
               <a href="javascript:;"><div class="pageys"><span>上一页</span></div></a>
               <ol id="pageol" class="pageol">
                   <li><a href="javascript:;" class="mpages" onClick="set_menu4(0)"><div><span>1</span></div></a></li>
                   <li><a href="javascript:;" onClick="set_menu4(1)"><div><span>2</span></div></a></li>
                   <li><a href="javascript:;" onClick="set_menu4(2)"><div><span>3</span></div></a></li>
                   <li><p class="slh">...<p></li>
                   <li><a href="javascript:;"><div class="pageysn" onClick="set_menu4(3)"><span>10</span></div></a></li>
               </ol>
               <a href="javascript:;"><div class="pageys"><span>下一页</span></div></a>
               <input type="text" class="gopage">
               <a href="javascript:;"><div class="pageys"><span>GO</span></div></a>
              </div>
   	       </div>
   	     <!---page end---> 
        </div>   
        <!----公告类型设置  end--->  
    </div>    
    <!-- center end--->
<!--添加类型 begin-->
<div id="simScrollCont6" class="simScrollCont">
</div>
	<div class="simScrollContBox7" id="simScrollContBox6">
    	<div class="simScrollContBoxs7">
        	<div class="tctitle">新增类型<a href="javascript:;" onClick="noticetype.view.hidNewDiv()" title="关闭"></a></div>
            <table width="100%" align="center" class="qytable mt20" border="0">
              <tr height="50">
                <td width="110"><label class="xin">类型名称：</label></td>
                <td><input type="text" id="typename" class="pttcText"><font color="red"><span id="errinfo" ></span></font></td>
              </tr>
              <tr height="70">
                <td><label>描述：</label></td>
                <td><textarea class="pttexta" id="description" style=" width:300px;"></textarea></td>
              </tr>
              <tr height="60">
                <td></td>
                <td colspan="3">
                <input type="button" class="pttcBut85" value="确认" onClick="noticetype.fnc.addType()">
                <input type="button" class="pttcButh85 ml30" value="取消" onClick="noticetype.view.hidNewDiv()"></td>
              </tr>
            </table>
        </div>
    </div>
<!--添加类型 end-->

<!--修改类型 begin-->
<div id="simScrollCont7" class="simScrollCont">
</div>
	<div class="simScrollContBox7" id="simScrollContBox7">
    	<div class="simScrollContBoxs7">
        	<div class="tctitle">修改类型<a href="javascript:;" onClick="noticetype.view.hidEditDiv()" title="关闭"></a></div>
            <table width="100%" class="qytable mt20" border="0">
              <tr height="50">
                <td width="110"><label class="xin">类型名称：</label></td>
                <td><input type="text" id="editname" class="pttcText"><font color="red"><span id="eidterrinfo" ></span></font></td>
              </tr>
              <tr height="70">
                <td><label>描述：</label></td>
                <td><textarea class="pttexta" id="editdescription" style=" width:300px;"></textarea></td>
              </tr>
              <tr height="60">
                <td></td>
                <td colspan="3">
                <input type="hidden" id="editid"/>
                <input type="hidden" id="hideditname"/>
                <input type="button" class="pttcBut85" value="确认" onClick="noticetype.fnc.modifyType()">
                <input type="button" class="pttcButh85 ml30" value="取消" onClick="noticetype.view.hidEditDiv()"></td>
              </tr>
            </table>

        </div>
    </div>
<!--修改类型 end-->

<!--公告类型提示信息div begin-->
<div id="simScrollCont12" class="simScrollCont"></div>
	<div class="simScrollContBox4" id="simScrollContBox12">
    	<div class="simScrollContBoxs4">
        <div class="tctitle">提示信息<a href="javascript:;" onClick="noticetype.view.hidgglxDiv()" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        	<p class="alertmsg" id="contentstr2"></p>
        	<input type="hidden" id="actiontype" />
        </div>
        <div class="alertbut">
            <input type="button" value="否" class="no fr mr20"  onClick="noticetype.view.hidgglxDiv()">
        	<input type="button" value="是" class="yes fr mr20" onClick="noticetype.fnc.modifyState()">
        </div>

        </div>
    </div> 
<!--公告类型提示信息div end-->
</div>
</div>
</body>
</html>

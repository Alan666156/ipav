<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>个人主页</title>
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/uploadPreview.min.js"></script>
<style type="text/css">
	#saylistdiv{
		width:100%;
		height:400px;
		margin: 0px auto;
		border: 1px solid red;		
		overflow:auto;
	}
	  a{
	  	text-decoration:none;
	  	font-size: 16px;
	  }
	  textarea{
	  	width: 300px;
	  	height: 75px;
	  	margin: 0px;
	  	display: inline;
	  	border: 1px solid red;
	  }
	#saylistdiv ul{	list-style: none;}
</style>

</head>
<body>
	<div style="border: 1px solid; width: 70%;height:100%; margin: 0px auto;">
		<form action="/addSay" id="sayfrom" method="post" enctype="multipart/form-data">
			<input type="hidden" id="isPersonal"  value=""/>
			<input type="hidden" id="hiduserid" name="sayuserid" value="<c:out value='${curuser.userid }'/>"/>
			<input type="hidden" id="hidusername" name="sayusername" value="<c:out value='${curuser.username }'/>"/>
			<p align="center">	欢迎<font color="red" size="5"><c:out value="${curuser.username}"/></font>进入快捷管家同事圈	</p>
			<p><a href="javascript:;" onclick="personalDynamic()">个人中心</a>&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="workerDynamic()">同事动态</a></p>
			<hr/>
			<div style="border:1px solid black;" >
				<div style="float:left;width:300px;"><textarea rows="10" cols="5" name="saycontent" style="width:250px;height:50px;"></textarea></div>
				<div style="width:850px;padding-top: 30px;">可见范围:
						<select id="permissionItem" name="permission">
							<option value="0">所有可见人</option>
							<option value="1">仅同事可见</option>
							<option value="2">仅指定人可见</option>
							<option value="3">仅自己可见</option>
						</select>
						<input type="file" name="photo" id="imgUpload"   accept="image/*" style="width:66px;height:25px;" />
						<input type="submit" value="发表" />
				</div>
			</div>		
		</form>
		<div id="saylistdiv"></div>
	</div>
	<script type="text/javascript">
		$(function(){
// 			personalDynamic();		
			workerDynamic();
		});
		$("#imgbtn").click(function(){
			$("#imgUpload").click();
		});
		$("#imgUpload").change(function(){
			if($("#imgdiv").length>0){
				$("#imgdiv").remove();
			}
			var imgdivhtml="<div style='width:200px;height:120px;' id='imgdiv'><img id='ImgPr' width='200' height='120' style='display: inherit;margin:10px;'/></div>";
			$("#sayfrom").append(imgdivhtml);
		});	
		$("#imgUpload").uploadPreview({
				Img: "ImgPr",
				Width : 200,
				Height : 120,
				ImgType : ["gif", "jpeg", "jpg", "bmp", "png"]
		});
		function personalDynamic(){
			$("#isPersonal").val("1");
			var url = "/userSayList";
			var data = {userid:'<c:out value="${curuser.userid}"/>'};
			sendAjaxRequest(url, data);
		}
		function workerDynamic(){
			$("#isPersonal").val("");
			var url = "/saylist";
			var data = {userid:'<c:out value="${curuser.userid}"/>'};
			sendAjaxRequest(url, data);
		}
		function insertCommentDiv(activetype,index){
			var activeDiv=$("#activeDiv");
			if(activeDiv.length>0){
				activeDiv.parent().remove();
			}
			var divStr="<li><div id='activeDiv' tabindex='-1' style='width:400px;'>"+
			"<textarea id='contentStr' style='display:inline;width:300px;height:50px;margin:0px;resize:none;' ></textarea>"+
			"<div tabindex='-1' id='linkDiv' style='display:inline;width:50px;height:50px;'><a href='javascript:;' id='activeLink'>发表</a></div></div></li>";			 
			if(activetype=="say"){//comment is to say
				$("#say"+index).find("ul:eq(0)").append(divStr);
				$("#activeLink").unbind().bind('click',{parm:index},function(e){commentToSay(e.data.parm,2,null);});
			}else if(activetype=="comment"){//reply is to comment
				$("#comment"+index).find("ul:eq(0)").append(divStr);
				$("#activeLink").unbind().bind('click',{parm:index},function(e){replyToComment(e.data.parm);});		
			}else if(activetype="reply"){//reply is to reply
				$("#reply"+index).find("ul:eq(0)").append(divStr);
				$("#activeLink").unbind().bind('click',{parm:index},function(e){replyToReply(e.data.parm);});	
			}
			$("#contentStr").focus();
			$("#activeDiv").blur(function(){
				$("#activeDiv").parent().remove();
			});
		}
		function commentToSay(sayno,isLike,commid){
			if(isLike==2&&!isContentNull()) return;	
			var url="/addComment";
			var dataStr={
				"sayid":$("#hidsayid"+sayno).val(),
				"commuserid":$("#hiduserid").val(),
				"commusername":$("#hidusername").val(),				
				"commcontent":$("#contentStr").val(),
				"isdelete":1
			};
			if(isLike==1){// 点赞
				dataStr["commtype"]=1;
				var isdelete=$("#hidislike"+commid).val();				
				if((!isdelete)||isdelete==null||isdelete==undefined){//添加点赞
					dataStr["isdelete"]=1;
				}else{
					if(isdelete==1){
						dataStr["isdelete"]=0;
					}else{
						dataStr["isdelete"]=1;
					}
				}
				if($("#likediv a").length=0){
					$("#likediv").remove();
				} 
			}else if(isLike==2){//评论
				dataStr["commtype"]=2;
			}
			var data={
					"commentStr":JSON.stringify(dataStr),
					"isPersonal":$("#isPersonal").val()
				};
			sendAjaxRequest(url, data);
		}
		function replyToComment(commentno){
			if(!isContentNull()) return;	
			var url="/addReply";
			var dataStr={
				"commentid":$("#hidcommentid"+commentno).val(),
				"replyuserid":$("#hiduserid").val(),
				"replyusername":$("#hidusername").val(),
				"bereplyid":$("#hidbecommentid"+commentno).val(),
				"bereplyname":$("#hidbecommentname"+commentno).val(),
				"replycontent":$("#contentStr").val()
			};
			var data={
					"replyStr":JSON.stringify(dataStr),
					"isPersonal":$("#isPersonal").val()
				};
			sendAjaxRequest(url, data);
		}
		function replyToReply(replyno){
			if(!isContentNull()) return;	
			var url="/addReply";
			var dataStr={
				"commentid":$("#hidreplyid"+replyno).val(),
				"replyuserid":$("#hiduserid").val(),
				"replyusername":$("#hidusername").val(),
				"bereplyid":$("#hidbereplyid"+replyno).val(),
				"bereplyname":$("#hidbereplyname"+replyno).val(),
				"replycontent":$("#contentStr").val()
			};
			var data={
					"replyStr":JSON.stringify(dataStr),
					"isPersonal":$("#isPersonal").val()					
				};
			sendAjaxRequest(url, data);
		}
		function isContentNull(){
			var connStr=$.trim($("#contentStr").val());
			if(connStr==null||connStr==""){
				$("#activeDiv").parent().remove();
				return false;
			}
			return true;
		}
		function sendAjaxRequest(url,data){
			var flag=$("#isPersonal").val();
			$.post(url,data,function(arr){
			  var htmlinfo="";
			  if(flag=="1"){//个人动态
				  $.each(arr,function() {					  
					  htmlinfo+="<div>";
					  htmlinfo+="<p><a href='"+this.userid+"'>"+this.username+"</a>";
					  var t=this.actiontype;
					  if(t==0){//发表说说动态
						  htmlinfo+="发表";
					  }else if(t==1){//赞说说动态
						  htmlinfo+="赞了";
					  }else if(t==2){//评论说说动态
						  htmlinfo+="评论";
					  }else if(t==3){//回复说说动态
						  htmlinfo+="回复";
					  }
					  htmlinfo+="</p>";
					  htmlinfo+="<p>操作时间:"+this.actiondate+"</p>";
					  htmlinfo+=createSayDivHtml(this.say);
					  htmlinfo+="</div>";
				  });
			  }else{//同事动态
				  $.each(arr,function() {
					  htmlinfo+=createSayDivHtml(this);
				  });
			  }
	          $("#saylistdiv").html(htmlinfo);  
			});
		}
		function createSayDivHtml(say){
			if(say!=null){			
			var commdata=commentOrLike(say.comments);
			var comms=commdata.comment;
			var likes=commdata.like;
			var info = ""; 
			sayno=say.sayid;
			info+="<div id='say"+sayno+"'><ul>";
			info+="<input type='hidden' id='hidsayid"+sayno+"' value='"+say.sayid+"'/>";
 			info+="<input type='hidden' id='hidsayuserid"+sayno+"' value='"+say.sayuserid+"'/>";
 			info+="<input type='hidden' id='hidsayusername"+sayno+"' value='"+say.sayusername+"' />";
 			info+="<input type='hidden' id='hidcanlike"+sayno+"' value='true'/>";
			info+="<li><a href='"+say.sayuserid+"'>"+say.sayusername+"</a>  说:"+say.saycontent;
			if(say.images!=null){
				  info+="<div>";
				  $.each(say.images,function(){
					  	info+="<div style='width:200px;height:120px;display:inline;' ><img src='/getImage?imagename="+this+"' width='200' height='120' /></div>";
				  });
				  info+="</div>";
			}
			info+="</li><li>发表时间:"+say.saydate+"</li>";
			info+="<li><a href='javascript:;'  onclick=insertCommentDiv('"+"say"+"',"+sayno+")>评论";
			if(comms!=null){if(comms.length>0){info+="("+comms.length+")";	}}
			info+="</a>&nbsp;&nbsp;&nbsp;&nbsp;"+likeLinkeHtml(likes,sayno);
			if(comms!=null){
				if(comms.length>0){
					info+="<li>";
						$.each(comms,function(){
							commno=this.commentid;
							info+="<div id='comment"+commno+"'><ul>";
							info+="<input type='hidden' id='hidcommentid"+commno+"' value='"+this.commentid+"'/>";
			 				info+="<input type='hidden' id='hidbecommentid"+commno+"' value='"+this.commuserid+"'/>";
			 				info+="<input type='hidden' id='hidbecommentname"+commno+"' value='"+this.commusername+"' />";
			 				info+="<li><a href='"+this.commuserid+"'>"+this.commusername+"</a>  说:"+this.commcontent+"</li>";
							info+="<li>评论时间:"+this.commdate+"&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:;'  onclick=insertCommentDiv('"+"comment"+"',"+commno+")>回复";
							if(this.replys!=null){if(this.replys.length>0){info+="("+this.replys.length+")";}}info+="</a>&nbsp;&nbsp;</li>";
							if(this.replys!=null){
								info+="<li>";
									$.each(this.replys,function(){
											replyno=this.replyid;
											info+="<div id='reply"+replyno+"'><ul>";
											info+="<input type='hidden' id='hidreplyid"+replyno+"' value='"+this.commentid+"'/>";
								 			info+="<input type='hidden' id='hidbereplyid"+replyno+"' value='"+this.replyuserid+"'/>";
								 			info+="<input type='hidden' id='hidbereplyname"+replyno+"' value='"+this.replyusername+"' />";
								 			info+="<li><a href='"+this.replyuserid+"'>"+this.replyusername+"</a>对<a href='"+this.bereplyid+"'>"+this.bereplyname+"</a>回复:"+this.replycontent+"</li>";
											info+="<li>评论时间:"+this.replydate+"&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:;'  onclick=insertCommentDiv('"+"reply"+"',"+replyno+")>回复</a>&nbsp;&nbsp;</li>";
											info+="</ul></div>"
										});
									info+="</li>";
								}
							info+="</ul></div>";
						});
					info+="</li>";
					}
				}			
			info+="</ul><hr/></div>";
			}
			return info;
		}
		function commentOrLike(comments){
			var commArr = new Array();
			var likeArr = new Array();
			if(comments!=null){
				$.each(comments,function(){
					if(this.commtype==1){//赞
						likeArr.push(this);
					}else if(this.commtype==2){//评论
						commArr.push(this);
					}
				});
			var rs = {
				"comment":commArr,
				"like":likeArr
			};
			}
			return rs;
		}
		function likeLinkeHtml(likes,sayno){
			var infohtml="<a href='javascript:;' onclick=commentToSay('"+sayno+"',1,'null')><span>赞</span></a>";
			if(likes.length>0){
				var	info="<li><div id='likediv' style='font-size:12px;border:1px solid red;color:red;'>";
				$.each(likes,function(){
					info+="<input type='hidden' id='hidislike"+this.commuserid+"' value='"+this.isdelete+"'/>";
					if(this.commuserid==$("#hiduserid").val()){
						if(this.isdelete==1){
							infohtml="<a href='javascript:;' onclick=commentToSay('"+sayno+"',1,'"+this.commuserid+"')><span>取消赞</span></a>";
							info+="<a href='"+this.commuserid+"'><span>"+this.commusername+"</span></a>&nbsp;|";
						}
					}else{info+="<a href='"+this.commuserid+"'><span>"+this.commusername+"</span></a>&nbsp;|";}
				});
				info+="觉得很赞哟!<div></li>";
				infohtml+=info;
			}
			infohtml+="</li>";
			return infohtml;
		}
</script>
</body>
</html>

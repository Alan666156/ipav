/***动态显示START****/
var  pageSize=10;
//var remail=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
//var rphone=/^(((13[0-9]{1})|159|153|170)+\d{8})$/;

/**
 * 全部动态
 */ 
function allSays(pageNo) {	
	if(pageNo==1){$("#myall").hide();}
	 var  url = "/saylist";
	 var data={
		 pageNo:pageNo,
		 pageSize:pageSize,
	 }; 
	 $.post(url,data,function(rtnData){ 
		 if(pageNo==1){$("#myall").hide();}
		 if(FailureMsg(rtnData)==true)return;
		 var htm="";
		 if(pageNo!=1){
			 htm+=$("#tsqdt_hua").html();
		 }
		 htm+=drowSay(rtnData)
		 if(rtnData.length ==pageSize){
			 $("#tsqloading").html("<div class='loading' style='margin-bottom:15px;'><a href='#1' onClick='allSays("+(parseInt(pageNo)+1)+","+pageSize+")'>查看更多...</a></div>");
	     }else{
	    	 $("#tsqloading").html("<div class='loading' style='margin-bottom:15px;'><a href='#1'>没有更多数据了...</a></div>");
	    		
	     }
		 $("#tsqdt_hua").html(htm);
		 replyiconBing();
	 });
} 
/**
 * 我的动态
 */ 
function mySays(pageNo) {	
	 var  url = "/userSayList";
	 var data={
		 pageNo:pageNo,
		 pageSize:pageSize,
	 };
	 $.post(url,data,function(rtnData){ 
		 if(FailureMsg(rtnData)==true)return;
		 var htm="";
		 if(pageNo!=1){
			 htm+=$("#tsqdt_hua").html();
		 }
		 htm+=drowMySay(rtnData)
		 if(rtnData.length > 0){
			 $("#tsqloading").html("<div class='loading' style='margin-bottom:15px;'><a href='#1' onClick='mySays("+(parseInt(pageNo)+1)+","+pageSize+")'>查看更多...</a></div>");
	     }else{
	    	 $("#tsqloading").html("<div class='loading' style='margin-bottom:15px;'><a href='#1'>没有更多数据了...</a></div>");
	     }
		  
		 $("#tsqdt_hua").html(htm);
		 myReplyiconBing();
	 });
} 

/**
 * 全部动态
 */ 
function myReleaseSays(pageNo) {	
	var  url = "/myReleaseSays";
	var data={
			pageNo:pageNo,
			pageSize:pageSize,
	}; 
	$.post(url,data,function(rtnData){ 
		if(FailureMsg(rtnData)==true)return;
		var htm="";
		if(pageNo!=1){
			htm+=$("#tsqdt_hua").html();
		}
		htm+=drowSay(rtnData)
		if(rtnData.length ==pageSize){
			$("#tsqloading").html("<div class='loading' style='margin-bottom:15px;'><a href='#1' onClick='myReleaseSays("+(parseInt(pageNo)+1)+","+pageSize+")'>查看更多...</a></div>");
		}else{
			$("#tsqloading").html("<div class='loading' style='margin-bottom:15px;'><a href='#1'>没有更多数据了...</a></div>");
			
		}
		$("#tsqdt_hua").html(htm);
		
	});
} 
/**
 *刷新动态
 */
function tsqDTRefresh() {
	 $("#tsqdt_hua").html("");
	 $("#tsqloading").html("");
	for(var j=1;j<=3;j++){
		 if($("#sayitle_"+j).hasClass("current")){
			    if(j==1){allSays(1);}
				else if(j==2){mySays(1);}
				else if(j==3){myReleaseSays(1);} 
		 }
	}
 
}

/**
 * 描绘全部动态
 */
function drowSay(data){
	 var html = ""; 	 
	   $.each(data,function(i,value) {
		   html+=" <div class='yymsgList ptcs' id='tsqyymsgList_"+value.say.sayid+"'>"
		   html+="<div class='yyimgBox'><img src="+value.say.sayuserimg+"></div>";
		   html+="<div class='yymsgBox'>";
		   html+="<p class='yyname'><span>"+StrEscape(value.say.sayusername)+"</span><i class='yynamei'>"+value.say.saydate+"</i></p>";
		   html+="<p style='word-wrap: break-word;'>"+StrEscape(value.say.saycontent)+"</p>"; 
		   if(value.say !=null && value.say.images.length!=0){	 
		   html+="<div><table><tr>";
		   $.each(value.say.images,function(i,value)  {
			   if(i%3==0 & i != 0){			   
				html+="</tr><tr>";
			   }
			   html+="<td><img onClick='tsqSayImgOnclick(this,"+i+")' class='tsqsay_image' src="+value+"></td>";		  
		   });
		   html+="</tr></table>" ;
		   html+="</div>";
		   }
		   if(isNotNull(value.fmap)){
			   html+="<br/><br/><p>附件：<a title='点击下载' href='/getImage?imagename="+value.fmap.path+"' ><span>"+value.fmap.name+"</span></a></p>" ;
		   }
		  
		   html+="</div>";
		   html+="<div class='zplun'><a href='#1' class='gga' onClick='getComment(this,"+value.say.sayid+")'>评论(<span>"+value.CommentSize+"</span>)</a>";
		   html+="<a href='#1' class='zans' onClick=praiseOrCancel(this,"+value.say.sayid+") >";
		   if(value.isPrais==0){
			   html+="<span>赞</span>";
		   }else{
			   html+="<span>取消赞</span>";
		   }
		   html+= "(<span>"+value.praisSize+"</span>)</a>";
		   if(value.isMyTopic == "Y"){
			   html+="<a href='#1' class='ggdelete' onClick='delSay("+value.say.sayid+")'><span>删除</span></a>"; 
		   }
	       html+="</div><div class='zanBox'>"+zanBogChange(value.userPraises,value.say.sayid) +"</div>";
	       if(value.commentMap.size!=0){
	    	   html+="<div id='tsqpinglun_"+value.say.sayid+"'>"+drowComment(value.commentMap, 0)+"</div>";
	    	 
	       }
           html+="</div>";  
	       		
		});
	  
	   return html;
	
}

/**
 * 描绘赞图标
 */
function zanBogChange(data,sid){
	var html="<div class='zanicon'><img src='/pages/images/platform/zanicon.png'></div>";  
    if(data!=null){
	$.each(data,function(i,value)  {
	 if(i<21){
 	   html+="<div class='zan4 fl'>";  
 	   html+="<img src='"+value.userImageUrl+"' width='28' height='28' title='"+value.username+"'>";
 	   html+="</div>";  
 	  /* if(i==data.length-1){
 		html+=' <div class="zangd" onClick="getPraiseOnClick(this,'+sid+',1)"><div></div></div>';
 		  // html+= drowZanlib(data);
 	   }*/
	 }else{
		 html+=' <div class="zangd" onClick="getPraiseOnClick(this,'+sid+',1)"><div></div></div>';
		 //html+= drowZanlib(data);
	   return false;
	 }
    });
    }  
    return html;
}

/**
 * 描绘赞列表
 */
function drowZanlib(data,sid,pageNo){
	var html="";
	 html +='<div class="dzanbox" id="dzanbox1">';
	 html +='<div class="praiseul"><ul>';
	 $.each(data,function(i,value)  {
		 html +='<li><a href="#1"><img src="'+value.userImageUrl+'"  width="28" height="28" title="'+value.username+'"></a></li>';
	 });	
	 html+='</ul></div>'; 
	 html+='<div class="praisePageBox"><ul><li><a href="#1" >上一页</a></li>  <li>';
	 if(data.length>=32){
	 html+='<a href="#1" onClick="getPraise(1,'+sid+','+(pageNo+1)+')">下一页</a>';
	 }else{
	 html+='<a href="#1">下一页</a>';
	 }
	 html+='</li></ul></div></div>';
  	return 	html;
}
function drowZanPage(sid,pageNo,data){
	var html ='<div class="praiseul"><ul>';
	 $.each(data,function(i,value)  {
		 html +='<li><a href="#1"><img src="'+value.userImageUrl+'"  width="28" height="28" title="'+value.username+'"></a></li>';
	 });	
	 html +='</ul></div>';
	 html+='<div class="praisePageBox"><ul><li>';
	 if(pageNo>1){
		 html+='<a href="#1" onClick="getPraise(1,'+sid+','+(pageNo-1)+')">上一页</a>';
	 }else{
		 html+='<a href="#1">上一页</a>';
	 }
		 
	 html +=' </li>  <li>';
	 if(data.length>=32){
		 html+='<a href="#1" onClick="getPraise(1,'+sid+','+(pageNo+1)+')">下一页</a>';
		 }else{
		 html+='<a href="#1">下一页</a>';
		 }
	 html+='</li></ul></div></div>';
	 return html;
}


/**
 * 描绘我的动态 
 */
function drowMySay(data){
	 var html = ""; 	 
	   $.each(data,function(i,value) {
		   html+=" <div class='yymsgList ptcs' id='tsqyymsgList_"+value.say.sayid+"' >"
		   html+="<div class='yyimgBox'><img src='"+value.img+"'></div>";
		   html+="<div class='yymsgBox'>";
		   html+="<p class='yyname'><span>"+value.uname+"</span><span>"+value.type+"您</span><i class='yynamei'>"+value.say.saydate+"</i></p></div>";
		   html+="<div class='yymsgBox'>";
		   html+="<p  class='yyname'><span style='margin-right:10px'><img src="+value.say.sayuserimg+" width='25' height='22'></span><span>"+StrEscape(value.say.sayusername)+":发表说说</span></p>";
		   html+="<br/><p style='word-wrap: break-word;'>"+StrEscape(value.say.saycontent)+"</p><br/>"; 
		   if(value.say !=null && value.say.images.length!=0){	 
		   html+="<div><table><tr>";
		   $.each(value.say.images,function(i,value)  {
			   if(i%3==0 & i != 0){			   
				html+="</tr><tr>";
			   }
			   html+="<td><img  class='tsqsay_image' onClick='tsqSayImgOnclick(this,"+i+")'  src="+value+"></td>";		  
		   });
		   html+="</tr></table>" ;
		   html+="</div>";
		   }
		   if(isNotNull(value.fmap)){
			   html+="<br/><br/><p>附件：<a title='点击下载' href='/getImage?imagename="+value.fmap.path+"' ><span>"+value.fmap.name+"</span></a></p>" ;
		   }
		  
		   html+="</div>";
		   html+="<div class='zplun'><a href='#1' class='gga' >评论(<span>"+value.CommentSize+"</span>)</a>";
		   html+="<a href='#1' class='zans' onClick=praiseOrCancel(this,"+value.say.sayid+") >";
		   if(value.isPrais==0){
			   html+="<span>赞</span>";
		   }else{
			   html+="<span>取消赞</span>";
		   }
		   html+= "(<span>"+value.praisSize+"</span>)</a>";
		   if(value.isMyTopic == "Y"){
			   html+="<a href='#1' class='ggdelete' onClick='delSay("+value.say.sayid+")'><span>删除</span></a></div>"; 
		   }
	       html+="<div class='zanBox'>"+zanBogChange(value.userPraises,value.say.sayid) +"</div></div>"; 
	    
	       if(value.type != '赞了'){
	    	   html+="<div class='plb1'></div>";
		   html+=drowMyComment(value.say.comments,1,value.surplusSize,value.uname,value.uid);
	       }
	       html+="</div></div>";   
		});
	   return html;
}

/**
 * 描绘评论
 */

function drowComment(data,tip){
	var html=""; 
	html+="<div class='plb1'></div>";
	if(tip==0){
	if(data.surplusSize !=undefined && data.surplusSize!=null){
	if(data.surplusSize>0){
		html+="<div class='plb2'><a class='tsqAllSurplus' onClick='tsqAllSurplusPut(this,-1);' href='#'>展开剩余<span>"+data.surplusSize+"</span>条评论↓</a></div>";
	}
	else{
		html+="<div class='plb2'><a class='tsqAllSurplus' onClick='tsqAllSurplusPut(this);' href='#'>收起评论↑</a></div>";
	}
	}
    }
	
	html+="<div class='plb2' id='tsqsay_"+data.sid+"'>";
 if(data.commList!=null && data.commList.length>0){
 $.each(data.commList,function(i,value){
	html+="<div class='pllist checkbut' id='tsqComment_"+value.commentid+"'>";
	html+="<div class='pllistbox'><p class='fl'><img class='tsqsayimg' src='"+value.commuserimg+"'/><a href='#1' title='评论'>"+StrEscape(value.commusername)+"：</a>"+StrEscape(value.commcontent)+"</p><span class='replyicon' id='tsqreply_"+value.commuserid+"_"+value.commentid+"'></span></div>";
  if(value.replys!=null&&value.replys.length>0){
	$.each(value.replys,function(i,value){
	html+="<div class='pllistbox'><p class='fl' style='margin-left:32px;'><img  class='tsqsayimg'	 src='"+value.replyuserimg+"'/><a href='#1' title='回复'>"+StrEscape(value.replyusername)+"</a>回复<a href='#1'>@"+StrEscape(value.bereplyname)+"：</a>"+StrEscape(value.replycontent)+"</p><span class='replyicon' id='tsqreply_"+value.replyuserid+"_"+value.commentid+"'></span></div>	";
	});
	}
	html+="</div>"}); }
 
	html+=" <div class='plltext'><input type='text' class='pltext' placeholder='请输入评论内容...'></div>";
	html+=" <div class='checkbut1'>";
	html+=" <input type='button' value='评论' class='comment fr mt10' onClick='tsqaddComment(this)'> ";
	html+=" </div>";
 
	html+="  </div>"; 	
	return html;
}

/**
 * 描绘我的动态显示评论
 */
function drowMyComment(data,tip,surplusSize,uname,uid){
	var html="";
	$.each(data,function(i,value){
		html+="<div class='plb2' >";
		if(surplusSize != undefined && surplusSize != null){
			if(surplusSize>0){
				html+="<div><a class='tsqAllSurplus' onClick='queryReapyByCid(this,"+value.commentid+",-1);' href='#'>展开剩余<span>"+surplusSize+"</span>条回复↓</a></div>";
			}else{
				html+="<div ><a class='tsqAllSurplus'onClick='queryReapyByCid(this,"+value.commentid+");' href='#'>收起回复↑</a></div>";
			}
		}
		html+="<div class='pllist checkbut' id='tsqcomment_"+value.commentid+"'>";
		html+="<div class='pllistbox'><p class='fl'><img  class='tsqsayimg'	 src='"+value.commuserimg+"'/><a href='#1' title='评论'>"+StrEscape(value.commusername)+"：</a>"+StrEscape(value.commcontent)+"</p><span class='replyicon' id='tsqreply_"+value.commuserid+"_"+value.commentid+"'></span></div>";
	  if(value.replys!=null&&value.replys.length>0){
		$.each(value.replys,function(i,value){
		html+="<div class='pllistbox'><p class='fl' style='margin-left:32px;'><img class='tsqsayimg' src='"+value.replyuserimg+"'/><a href='#1' title='回复'>"+StrEscape(value.replyusername)+"</a>回复<a href='#1'>@"+StrEscape(value.bereplyname)+"：</a>"+StrEscape(value.replycontent)+"</p><span class='replyicon' id='tsqreply_"+value.replyuserid+"_"+value.commentid+"'></span></div>	";
		});
	}
	html+="</div>"; 
	if(tip!=null && tip!=0){
	if(uname!=undefined && uid!=undefined){
		html+="<div class='plltext'><input type='text' class='pltext' placeholder='回复"+uname+":'></div>";
		html+=" <div class='checkbut1'>";
		html+=" <input type='button' value='回复' class='comment fr mt10' id='tsqreply_"+uid+"_"+value.commentid+"' onClick='tsqaddReply(this)'> ";
	}else{
	html+="<div class='plltext'><input type='text' class='pltext' placeholder='回复"+StrEscape(value.commusername)+":'></div>";
	html+=" <div class='checkbut1'>";
	html+=" <input type='button' value='回复' class='comment fr mt10' id='tsqreply_"+value.commuserid+"_"+value.commentid+"' onClick='tsqaddReply(this)'> ";
	}
	html+=" </div> </div>";
	} 
	html+="</div>";
	});
	return html;
}
/***动态显示END****/

/***动态操作START****/
/**
 * 删除说说
 */ 
function delSay(sid){
/*	var arr =[sid];
	tsqMsgDialog("确认提示","确认删除?",arr,1);*/
	msgDialogTohome({"title":"提示信息","centent":"确认删除?","okfunction":delSayAfter,"param":[sid]});
}	
function delSayAfter(sid){
	var data={sid:sid};
	$.post("/delSay",data,function(rtnData){ 	
   if(FailureMsg(rtnData)==true)return;
   if(rtnData=="success"){
	$("#tsqyymsgList_"+sid).fadeOut("slow",function(){
	$("#tsqyymsgList_"+sid).remove();
		});
   }else if(rtnData=="error"){
	  // alert("未知错误,删除失败!");
	   msgBoxTohome("温馨提示","未知错误,删除失败!");
   }else{
	   msgBoxTohome("温馨提示",rtnData);
	  // alert(rtnData)
   }
	});
 }

/**
 * 点赞/取消赞
 */
function praiseOrCancel(param,sid){	 
 if(param.firstChild.textContent=="取消赞"){
		isdelete=1;
 }else{
		isdelete=0;		
 }
 var data={	actiontype:1,actionid:sid,commtype:0,isdelete:isdelete};
 var dataStr={"commentStr" : JSON.stringify(data)}
 $.post("/praiseOrCancel",dataStr,function(rtnData){ 
	 if(FailureMsg(rtnData)==true)return;
	if(isdelete==0){
		param.firstChild.textContent="取消赞"; 
    } else{
    	param.firstChild.textContent="赞";	 			   
   }   
	 $(param).parent().parent().find(".zanBox").html(zanBogChange(rtnData.userList,sid));
	 $(param).children()[1].textContent=rtnData.size;	
	
});
}
/**
 * 得到评论前
 */
function getComment(param,sid){
	if($("#tsqpinglun_"+sid).length!=0 && $("#tsqpinglun_"+sid+"   input[type=\'text\']").val()!="" ){		 
		msgDialogTohome({"title":"提示信息","centent":"您有未完成的评论,确认放弃?","okfunction":getCommentAfter,"param":[param,sid]});
	} else
	 {
	 getCommentAfter(param,sid)
	 }
 
}

/**
 * 得到评论
 */
function getCommentAfter(param,sid){
  //存在则移除
 if($(param).parent().parent().children("#tsqpinglun_"+sid).length!=0){
	 $("#tsqpinglun_"+sid).fadeOut("slow",function(){ 
		 $("#tsqpinglun_"+sid).remove();
	 }); 
	 return;
 }
 var my = document.getElementById("tsqpinglun_"+sid);
 if (my != null)
          my.parentNode.removeChild(my);
 var data={"sid":sid};	  
 $.post("/queryCommentBySid",data,function(rtnData){
	 if(FailureMsg(rtnData)==true)return;
	 param.children[0].textContent=rtnData.size;	
	 var html  ="<div id='tsqpinglun_"+sid+"'>";
	 html+=drowComment(rtnData,0)+"</div>";
     $(param).parent().parent().append(html);
     replyiconBing();
 });
 }
/**
 * 全部动态点击评论/回复图标时候
 */
 function  replyiconBing() {
  $(".replyicon").click(function(param){
	 var textmsg=$(param.target).parent().children("p").children("a")[0].textContent.split("：")[0]+"：";
	 var inputText=$(param.target).parent().parent().parent().parent().parent().find("div.plltext").children("input");
	 var inputButton=$(param.target).parent().parent().parent().parent().parent().find("div.checkbut1").children("input");
	 inputButton.attr("id",$(param.target).attr('id'));
	 inputButton.val("回复");
	 inputText.attr("placeholder","回复"+textmsg);
	 inputText.focus();
	 inputText.blur(function(){
	 if($(this).val()==""){
		 inputText.attr("placeholder","请输入评论内容");
		 inputButton.val("评论");
	 }
	 });
	 }); 
	 
}
 /**
  * 展开/收起评论
  * @param patam
  */
 
 function tsqAllSurplusPut(patam,pageSize){
			 var sid=$(patam).parent().parent().children("div[id^=tsqsay]").attr('id').split("_")[1];
			 var data={"sid":sid,"pageSize":pageSize};	  
			 $.post("/queryCommentBySid",data,function(rtnData){
				 if(FailureMsg(rtnData)==true)return;
				var html =drowComment(rtnData,0)
				 $("#tsqpinglun_"+sid).html(html);
				 replyiconBing();
			 });
		 }
 /**
  * 我的动态点击评论/回复图标时候
  */
  function  myReplyiconBing() {
   $(".replyicon").click(function(param){
 	 var textmsg=$(param.target).parent().children("p").children("a")[0].textContent.split("：")[0]+"：";
 	 var inputText=$(param.target).parent().parent().parent().parent().find("div.plltext").children("input");
 	 var inputButton=$(param.target).parent().parent().parent().parent().find("div.checkbut1").children("input");
 	 inputButton.attr("id",$(param.target).attr('id')); 
 	 inputText.attr("placeholder","回复"+textmsg);
  });
   
 }
  
/**
 * 全部动态下 添加评论/回复
 */
function tsqaddComment(param){ 
	  var plb2s=$(param).parent().parent().parent().children("div.plb2");
	  var sid;
	  if(plb2s.length==1){
	  sid=$(param).parent().parent().parent().children("div.plb2")[0].id.split('_')[1];
	  }else{
      sid=$(param).parent().parent().parent().children("div.plb2")[1].id.split('_')[1];
	  }
	  if(param.value=='评论'){
		  var inpu=$(param).parent().parent().find("div input[type='text']");
		if($.trim(inpu.val())==""){
			//alert("请输入评论内容!");
		 msgBoxTohome("温馨提示","请输入评论内容!");
			return;
		}	
		if(inpu.val().length >100){
			//alert("请输入评论内容!");
		 msgBoxTohome("温馨提示","您输入的评论内容过长(100字以内)!");
			return;
		}	
		var  url= "/addSayComment";
		 var dataStr = {
			 actiontype:1, actionid:sid, 
			 commtype:1,commcontent:inpu.val(),
			 isdelete:0
		 };
		 var pageSize=undefined;
         var aa= $(param).parent().parent().parent().children("div.plb2").children('a')
         if(aa.length!=0 && !aa.html().indexOf("收起"))
        	 {
        	 pageSize=-1;
        	 }
		 data={"commentStr":JSON.stringify(dataStr	),'pageSize':pageSize}
		 $.post(url,data,function(rtnData){ 
			 if(FailureMsg(rtnData)==true)return;
			 inpu.val("");
			 $(param).parent().parent().parent().parent().find("div .gga span")[0].textContent=rtnData.size;
			// $(param).parent().parent().parent().parent().parent().parent().find("div .gga span")[0].textContent=rtnData.size;	
		     $(param).parent().parent().parent().html(drowComment(rtnData,0));
		     replyiconBing();
		    });	
	  }else if(param.value=='回复'){
		  //添加回复
	  var inpu=$(param).parent().parent().children("div.plltext").children('input');
 		if($.trim(inpu.val())==""){
			//alert("请输入回复内容!");
 			msgBoxTohome("温馨提示","请输入回复内容!");
			return;
			}
 		if(inpu.val().length >100){
			//alert("请输入评论内容!");
		 msgBoxTohome("温馨提示","您输入的回复内容过长(100字以内)!");
			return;
		}	
		 var arr= $(param).attr('id').split('_');	 
			var  url= "/addSayReply";
			 var dataStr = {
				 "commentid" :arr[2],
				 "bereplyid":arr[1],
				 "replycontent" :inpu.val()
			 };
			 data={"replyStr":JSON.stringify(dataStr),"sid":sid}
			 $.post(url,data,function(rtnData){
				 if(FailureMsg(rtnData)==true)return;
				 inpu.val("");
				 inpu.attr("placeholder","请输入评论内容");
				 param.value='评论';
				 $("#tsqComment_"+rtnData.comment[0].commentid).html(drowMyComment(rtnData.comment,0,rtnData.surplusSize));
				 replyiconBing() 
			 });	 
}
}
/**
 * 添加回复
 */
function tsqaddReply(param){
var inpu=$(param).parent().parent().children("div.plltext").children('input');
	if($.trim(inpu.val())==""){
	//alert("请输入回复内容!");
		msgBoxTohome("温馨提示","请输入回复内容!");
	return;
	}	
	var pageSize=undefined;
	var a =$(param).parent().parent().children("div").children("a");
	if(a.length!=0 && !a.html().indexOf('收起')){
		 pageSize=-1;
	}
 
 var arr= $(param).attr('id').split('_');	 
	var  url= "/addSayReply";
    var dataStr = {
		 "commentid" :arr[2],
		 "bereplyid":arr[1],
		 "replycontent" :inpu.val()
		 
	 };
	 data={"replyStr":JSON.stringify(dataStr),"pageSize":pageSize}
	 $.post(url,data,function(rtnData){ 
		 if(FailureMsg(rtnData)==true)return;
		 $(param).parent().parent().html(drowMyComment(rtnData.comment,1,rtnData.surplusSize));
		 myReplyiconBing();
	 });	 
}
	
/**
 * 查询回复
 */
function queryReapyByCid(param,cid,pageSize){
	 data={"pageSize":pageSize,"cid":cid}
	 $.post("/queryReapyByCid",data,function(rtnData){ 
		 if(FailureMsg(rtnData)==true)return;
		 $(param).parent().parent().html(drowMyComment(rtnData.comment,1,rtnData.surplusSize));
		 myReplyiconBing();
	 });	 
}
 
/***动态操作END****/
/***发布说说START***/
/**
 * 点击发表说说按钮
 */
function sayRelease(){
	if($.trim($("#sayContent").val())==""){
		//alert("请输入说说内容!");
		msgBoxTohome("温馨提示","请输入说说内容!");
		return ;
	}
	if($("#sayContent").val().length>1000){
		msgBoxTohome("温馨提示","您输入的说说内容过长(字数在1000以内)!");
		return ;
	}
	var filedata  = [];// 创建数组	 
	$.each($("input[id^=sayInputImages_]"),function(idx,file){
		if(file.value!=null && file.value!=""){
			filedata.push(file.id);
		}
	}); 
	var fileSize=0
	if($("#sayfile_id").val!=''){
		filedata.push("sayfile_id");
		fileSize=1;
	}
	var permission=0;
	var  sayusers=new Array();
	if($(".kejians").html()=="所有人可见"){
		permission=1;
	}else if($(".kejians").html()=='指定的同事'){
		permission=2;
		var tsqli= $("li[id^=tsqli_]");
		 for(var j=0;j<tsqli.length;j++){
	 		var id= $(tsqli[j]).attr('id').split("_")[1];
	 		sayusers.push(id); 
	      }
		if(sayusers.length==0){
			//alert("您还未选择指定同事!");
			msgBoxTohome("温馨提示","您还未选择指定同事!");
			return;
		}
	}else{
		permission=3;
	}
	var parm= {   
    	"saycontent":$("#sayContent").val(),// 说说内容 
    	"fileSize":fileSize,//附件个数
    	"permission":permission ,"sayusers":sayusers
    }
	  
	if(filedata.length!=0){
    $.ajaxFileUpload({
        url: "/addSayTwo",
        secureuri: false,
        fileElementId: filedata,
        dataType: 'json',  
        data:parm,
        type: 'POST',
        success: function(data, status){  
        	SayUploadSuccess();
        },
        error: function(data, status, e){ 
        	SayUploadSuccess();
        }
    });
   }else{   
		 $.post("/addSay",parm,function(rtnData){ 
			 if(FailureMsg(rtnData)==true)return;
			 SayUploadSuccess();	 
	 });	
   }
	
}
/**
 * 提交后
 */
function SayUploadSuccess(){
	$.each($("input[id^=sayInputImages_]"),function(idx,file){
		//if(file.value!=null && file.value!=""){
			$(file).remove();
		//}
   });
	$.each($("div[id^=sayImages_]"),function(idx,file){
		//if($(file).children("img").attr("src")!=null && $(file).children("img").attr("src")!=""){
			$(file).remove();
		//}
   });
	$("#sayContent").val("");
	//if($("#tsq_main_0").css("display")=="block" && $("#tsq_dynamic_title").html()=="我的动态"){
		allSays(1);
	//}
	 $(".tslistul").html("");
	 $("#appoint_user").html("");
	 $("#tslistBox").hide();
	 $("#sayFileDiv").hide(); 
	 $(".shuoright").append("<input id='sayInputImages_0' class='sfileimg' type='file' onchange='sayImageChange(this);' name='filedata' >");
     refresh();
}
/**
 * 选择图片后
 */
function sayImageChange(param){
	$('#tsqBoxdd').show(); 
	var id=$(param).attr("id").split('_')[1];
	var imgType=[ ".gif", ".jpeg", ".jpg", ".bmp", ".png",".GIF", ".JPEG", ".JPG", ".BMP" ,".PNG",".PCX",".pcx",".TIFF",".tiff",".PSD",".psd",".CDR",".cdr",".TGA",".tga",".EXIF",".exif",".FPX",".fpx",".ai",".AI",".UFO",".ufo",".HDRI",".HDRI",".pcd",".PCD"];
	
	/*if (!RegExp("\.(" + imgType.join("|")+ ")$", "i").test(
			$(param).val().toLowerCase())) {
		alert("图片类型必须是"+ imgType.join("，")+ "中的一种");
		$(param).val("");
		return
	};*/
	var chks={"filetypes":imgType,"filemaxsize":1024*1024*10};
		var checkMsg=checkFile($(param)[0].files[0],chks);
	if(checkMsg==0){//alert("文件不存在,请重选!");
	msgBoxTohome("温馨提示","文件不存在,请重选!");
	return;}
	else if(checkMsg==1){//alert("文件类型不正确,请重选!");
	msgBoxTohome("温馨提示","文件类型不正确,请重选！");
	return;}
	else if(checkMsg==2){//alert("文件为空,请重选!");
	msgBoxTohome("温馨提示","文件为空,请重选!");
	return;}
	else if(checkMsg==3){//alert("文件大小超出范围,请重选!");
	msgBoxTohome("温馨提示","文件大小超出范围(大小在10MB以内),请重选!");
	return;}
	$("#sayImages").append("<div class='shuoimgs'   id='sayImages_"+id+"'><img src='' class='simg'><a href='#1' class='imgdeletes' onClick='deleteSayimg(this)' title='删除图片'></a></div>");
	$(param).hide(); 
	$("#sayImages_"+id).css("display","inline"); 
	$("#sayImages_"+id).parent().css("display","block"); 
	$(param).parent().append("<input id='sayInputImages_"+(parseInt(id)+1)+"' class='sfileimg' type='file' onchange='sayImageChange(this);' name='filedata' >");
	refresh(); 
}


/**
 *检查是否有输入动态的操作false:有 true:没有
 */
function checkSay(){
	$.each($("input[id^=sayInputImages_]"),function(idx,file){
		if(file.value!=null && file.value!=""){
		return false;
		}
   });
	$.each($("div[id^=sayImages_]"),function(idx,file){
		if($(file).children("img").attr("src")!=null && $(file).children("img").attr("src")!=""){
		return false;
		}
   });
 if($("#sayContent").val()!=""){
	   return false;
 }
 
 return true;
}

/**
 * 初始化刷新一次
 */
$(document).ready(function(){	
	$.each($("input[id^=sayInputImages_]"),function(idx,file){
		if(file.value!=null && file.value!=""){
			file.value="";
		}
   });  
	 $("#sayContent").val(""); 
	 refresh();
});
/**
 * 刷新图片预览
 */
function refresh(){
$.each($("input[id^=sayInputImages_]"),function(idx,file){
	var id = file.id.split("_")[1]; 
	$(file).SayuploadPreview({
		width : 132,
		height :90,
		imgPreview : $("#sayImages_"+id).children('img'),
		imgType :[ ".gif", ".jpeg", ".jpg", ".bmp", ".png",".GIF", ".JPEG", ".JPG", ".BMP" ,".PNG",".PCX",".pcx",".TIFF",".tiff",".PSD",".psd",".CDR",".cdr",".TGA",".tga",".EXIF",".exif",".FPX",".fpx",".ai",".AI",".UFO",".ufo",".HDRI",".HDRI",".pcd",".PCD"],
		callback : function() {}
	}); 
	});		
 }
/**
 * 在预览上面删除图片
 */
function deleteSayimg(param){
	var id=$(param).parent().attr('id').split('_')[1];
	$("#sayInputImages_"+id).remove();
	$("#sayImages_"+id).remove();
}
 
/***发布说说END***/
 



/***我的同事控件START***/
/**
 * 查看我的同事后
 */
function searchColleagueMsgAfter(data){
	var html=""
	if(data.length==0){
		html+="<li class='tsxxinfo'>暂无同事</li>" ;
	}else{
	 $.each(data,function(i,value)  {
			html+="<li class='tsxxinfo'>" ;
			html+="<div class='tsmsgBoxs' onMouseMove='gettsxxinfo("+i+")'><div class='tsmboximg'><img src='"+value.picpath+"'></div><div class='tsminfo'>"+value.username+"</div></div>";
			html+="<div class='tstacBox dn' id='tsxxmain"+i+"'>";
			html+="<div class='ztopBox'></div>";
			html+="<div class='mediumBox'>";
			html+="<div class='tstaimms'>";
			html+="<div class='tstaimg'><img src='"+value.picpath+"'></div>";
			html+="<div class='tstamsgs'><p class='mt5'>姓名：<span>"+value.username+"</span></p> ";
			if(value.org_dutys.length!=0){
				$.each(value.org_dutys,function(i,value)  {
					if(value.type==0){
					html+="<p>部门：<span>"+value.orgname+"<span><p>职位：<span>"+value.dutyname+"</span></p>";
					}else{
					html+="<p>兼职部门"+(i)+"：<span>"+value.orgname+"<span><p>兼职职位"+(i)+"：<span>"+value.dutyname+"</span></p>";	
					}
				});
			}else{
				html+="<p>部门：<span>暂无<span><p>职位：<span>暂无</span></p>";
			}
			 
			html+="</div></div>";
			html+="<p class='tstap'>电话："+value.phone+"</span></p>"; 
			html+="<p class='tstap'>邮箱："+value.email+"</span></p>";
			html+="<div class='zbottomBox'></div></div>";
			html+="</li>";
		 });
	}
	   $("#mytsq_control").html(html);
		$(".tsxxinfo").hover(function(){
		$(this).find(".tstacBox").hide();
	})	
}

/**
 * 点击查看同事
 */
function zTreeOnClick(event, treeId, treeNode) {
	var orgnoids=new Array();
	if(isNotNull(treeNode.children)==false)
		{
		orgnoids.push(treeNode.orgid);		
		}else{
	for(var i =0 ;i<treeNode.children.length;i++){
		 
		orgnoids.push(treeNode.children[i].orgid);
		if(isNotNull(treeNode.children[i].children)==true)addorgnoidsStr(treeNode.children[i].children,orgnoids)
	}
  }
	data={"orgid":treeNode.orgid,"orgnoids":orgnoids}
	 $.post("/querygetUserListForOrgid",data,function(rtnData){ 
		 
		 if(FailureMsg(rtnData)==true)return;
		 searchColleagueMsgAfter(rtnData);
	 });	
};
/**
 * 描绘同事控件
 */
function Mytsclick() {
	$("#mytsq_control").html("");
	var setting = {  
			view: {				 
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
			callback: {onClick: zTreeOnClick}};  
	$.post("/getAllLevelInfos",{},function(rtnData){ 
		 if(FailureMsg(rtnData)==true)return;
		var data=rtnData;
		$.each(data,function(i,value) {
			value.title=value.name;	 
			if(value.id=="0"){
				value["icon"]="/pages/images/platform/jigou.png";								 
			}else if(value.Pid=="0"){
				value["icon"]="/pages/images/platform/1_close.png";	
			}else{
				value["icon"]="/pages/images/platform/1_open.png";
			}
		});
	$.fn.zTree.init($("#tsqtreeDemo"), setting,data); 
	 }); 
}
    
/**
 * 点击指定同事可见时
 */
function kejianforControl() {
	 $(".tslistul").html("");
	 $("#appoint_user").html("");
	var setting = {  
			view: {				 
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
			callback: {onClick:kejianTreeOnClick}}; 
	$.post("/getAllLevelInfos",{},function(rtnData){ 
		 if(FailureMsg(rtnData)==true)return;
		var data=rtnData;
		$.each(data,function(i,value) {
			value.title=value.name;	 
			if(value.id=="0"){
				value["icon"]="/pages/images/platform/jigou.png";								 
			}else if(value.Pid=="0"){
				value["icon"]="/pages/images/platform/1_close.png";	
			}else{
				value["icon"]="/pages/images/platform/1_open.png";
			}
		});
	$.fn.zTree.init($("#tsqKeJianTree"), setting,data); 
	 $("#tsqKeJianTree").parent().show();
	 }); 
}

function addorgnoidsStr(arr,orgnoids){
	for(var i =0 ;i<arr.length;i++){
		if(orgnoids.length==0)
		orgnoids.push(arr[i].orgid);
		else
		orgnoids.push(arr[i].orgid);
		if(isNotNull(arr[i].children)==true)addorgnoidsStr(arr[i].children,orgnoids)
	}
}

/**
 * 可见里面点击组织菜单
 */
function kejianTreeOnClick(event, treeId, treeNode){
	var orgnoids=new Array();
	if(isNotNull(treeNode.children)==false){
		orgnoids.push(treeNode.orgid);
	}else{
	for(var i =0 ;i<treeNode.children.length;i++){		 
		orgnoids.push(treeNode.children[i].orgid);
		if(isNotNull(treeNode.children[i].children))addorgnoidsStr(treeNode.children[i].children,orgnoids)
	}
	}
	data={"orgid":treeNode.orgid,"orgnoids":orgnoids}
	 $.post("/querygetUserListForOrgid",data,function(rtnData){ 
		 if(FailureMsg(rtnData)==true)return;
		 getFriendAfter(rtnData);
  });
 
 }
 

/**
 *获得好友可见列表后 
 */
function getFriendAfter(data){
	var html="";
	if(data.length==0){
		html+="<li class='tsxxinfo'>暂无同事</li>" ;
	}else{
	 $.each(data,function(i,value)  {
		html+="<li id='tsmsgli_"+value.userid+"'><div class='tsmsgBox tsmbg' ><div class='tsmboximg'><img src="+value.picpath+"></div><div class='tsminfo'>"+value.username+"</div></div></li>"; 
    });
	}
    $("#appoint_user").html(html);
   $(".tsmsgBox").click(function(){
		var sa = $(this).attr('class').split(" ")[1];
		if(sa=="tsmbg")
		{
		$(this).removeClass('tsmbg');
		}else if(sa==null){
		$(this).addClass('tsmbg');
		}
})  
}

/**
 *发表说说时添加指定好友
 */
function addFrendForSay(){
	var html="";
	var showline= $("li[id^=tsmsgli_]");
	for(var i=0;i<showline.length;i++){
		var id=$(showline[i]).attr('id').split("_")[1];
		var sa = $(showline[i]).children('div').attr('class').split(" ")[1];
	    if(sa!=null && sa =='tsmbg'){
	    	var tsqli= $("li[id^=tsqli_]");
	    if(tsqli.length!=0){
	    	var flag=true;
	      for(var j=0;j<tsqli.length;j++){
	 		  if(id==$(tsqli[j]).attr('id').split("_")[1]){
	 			  flag=false;
	 			  break;
	 		  }
	      }
	      if(flag==true)
	 		  html+="<li id='tsqli_"+id+"'><div>"+$(showline[i]).children('div').children('div')[1].textContent+"</div><a href='#1' class='delname' title='删除'></a></li>"; 
	      }else{  
	    	  html+="<li id='tsqli_"+id+"'><div>"+$(showline[i]).children('div').children('div')[1].textContent+"</div><a href='#1' class='delname' title='删除'></a></li>"; 
	      }
	 	 }
      }
	 $(".tslistul").html($(".tslistul").html()+html);
		$(".delname").click(function(){
			  $(this).parent().remove();
	})
}
/**
 *发表说说是清除全部指定好友 
 */
function cancelFrendForSay(){
	 $(".tslistul").html("");
}
/**
 *在查看中搜索好友的时候
 */
function searchUserForName(){
	if($.trim($("#searchMytsinput").val())==""){
		msgBoxTohome("温馨提示","请输入要查询的同事名!");
		//alert("请输入要查询的同事名！");
		return;
	}
     data={"username":$("#searchMytsinput").val()}
	 $.post("/searchUserForName",data,function(rtnData){ 
		 if(FailureMsg(rtnData)==true)return;
		 searchColleagueMsgAfter(rtnData);	
 });
}
/**
 *在好友可见中搜索好友的时候
 */
function searchKeJianUserForName(){
	if($.trim($("#searchtsKejianinput").val())==""){
		//alert("请输入要查询的同事名！");
		msgBoxTohome("温馨提示","请输入要查询的同事名!");
		return;
	}
	data={"username":$("#searchtsKejianinput").val()}
	$.post("/searchUserForName",data,function(rtnData){ 
		 if(FailureMsg(rtnData)==true)return;
		 getFriendAfter(rtnData);
	});
}


/***我的同事控件END***/
 

/***邀请同事操作START***/
function invitationTs(){ 
  if(invitationInputAddress_onblur() == false){
	  //alert("请填写正确的手机号码和邮箱！");
	  msgBoxTohome("温馨提示","请填写正确的手机号码和邮箱!");
	  return;
  }
   
  if(invitationInputName_onblur() == false){
	  //alert("请填写用户名！");
	  msgBoxTohome("温馨提示","请填写用户名!");
	  return;
  } 
  data={"name":$.trim($("#invitationInputName").val()),
		"mailOrphone":$.trim($("#invitationInputAddress").val()) 
	   }
  loadingMsg("消息提示","正在处理请稍后.....");
	$.post("/invitationColleague",data,function(rtnData){ 
		  MsgBoxColse();
		 if(FailureMsg(rtnData)==true)return;
		var msg="";
		var title="";
		if(rtnData.msg=="success"){
			title="邀请成功";
	       msg="恭喜您邀请成功,邀请信息已发送至对方邮箱或者手机短信中!";
	       invitationReset();
		}else if(rtnData.msg=="error"){
		  title="邀请失败";
		  msg="很遗憾,邀请失败!";
		}else{
		  title="邀请提示";
		  msg=rtnData.msg;
		}
		
		if(!isNotNull(rtnData.msg)) msg=rtnData;
		//$("#tsq_main_2").append("<p align='center' id='invitationMsg' style='color:red;font-size:25px;'>"+msg+"</p>")
	    //$("#invitationMsg").fadeIn(10);
		//$("#invitationMsg").fadeOut(5000,function(){
		//	$("#invitationMsg").remove();
		//});
		msgBoxTohome(title,msg);
	});
  
}

/**
 * 离开时邮箱验证
 */
function invitationInputAddress_onblur(){
	var address =$("#invitationInputAddress").val();
	if($.trim(address)==""){
		$("#invitationInputAddressMsg").html('手机号或者邮箱不能为空');
		$("#invitationInputAddressMsg").css("display","block")
		return false;
	
	}else if(!checkEmail(address)&&
			 !checkMobile(address)){ 
		$("#invitationInputAddressMsg").html('手机号或者邮箱有误！');
		$("#invitationInputAddressMsg").css("display","block");
		return false;
	}else{
		$("#invitationInputAddressMsg").html('');
		$("#invitationInputAddressMsg").css("display","none");
	}
}
/**
 * 离开时姓名验证
 */
function invitationInputName_onblur(){
	if($.trim($("#invitationInputName").val())==""){
		$("#invitationInputNameMsg").html('姓名不能为空！');
		$("#invitationInputNameMsg").css("display","block");
		return false;
	}else{
		$("#invitationInputNameMsg").html('');
		$("#invitationInputNameMsg").css("display","none");
		return true;
	}
}

/**
 * 邀请信息清除
 */
function invitationReset(){
	$("#invitationInputAddress").val('');
	$("#invitationInputName").val('');
	$("#invitationInputAddressMsg").html('');
	$("#invitationInputNameMsg").html('');
}
/***邀请同事操作END***/
/*var fatherScrollConts;
var fatherptlfbg;*/
 
 
 
 
 
function tsqSayImgOnclick(param,ids)
{
	var imgs=$(param).parent().parent().parent().find("img");
	imglook(imgs,ids);
} 



/***说说图片放大预览END****/

//处理转义字符
function  StrEscape(str){
	str=str.replace("<", "&lt;");
	str=str.replace(">", "&#62;"); 
	return str;
}
function getPraiseOnClick(pata,sid,pageNo){
	if($(pata).children("#dzanbox1").length==0 && $(pata).parent().find("#dzanbox1").length==0){
		 $('#dzanbox1').remove();
		 getPraise(pata,sid,pageNo);
	}
}
/**赞列表**/
function getPraise(pata,sid,pageNo){		 
	 var data={
	   "actionid": sid,
		"pageNo":pageNo,
		"pageSize":32,
	 }; 
	 $.post("/SearchPraiseUserMsg",data,function(rtnData){  
	 if($("#dzanbox1").length!=0){
		 $("#dzanbox1").html(drowZanPage(sid,pageNo,rtnData)); 
	 }else{
        $(pata).html(drowZanlib(rtnData,sid,pageNo));
        $("#dzanbox1").show();
     $(pata).hover(function(){
    	 $('#dzanbox1').show();
     },function(){
    	 $('#dzanbox1').remove();
     });
 	 
	 }
	 });	 
	}
 function sayFileChange(){
	 //var filell=$("#sayfile_id").value();
	 var  size=$("#sayfile_id")[0].files[0].size;
	 if(!size)size=$("#sayfile_id").context.fileSize;
	 var  name=$("#sayfile_id")[0].files[0].name;
	 if(!name)
		 name=$("#sayfile_id").val().split("\\")[$("#sayfile_id").val().split("\\").length-1];
	 if(size>1024*1024*40){
		 msgBoxTohome("温馨提示","文件大小超出范围(大小在40MB以内),请重选!");
		 return 
	 }
	 if(size==0){
		 msgBoxTohome("温馨提示","文件为空,请重选!");
		 return 
	 }
	// var  size=$("#sayfile_id")[0].files[0].size;
	// $("#sayFileDiv").html(name);
	  $("#sayfileinfo").html("上传文件："+name+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;("+(Math.round(size/1024))+"KB)");
	  $("#sayFileDiv").show();
	  
 }
function clearSayfile(){
	$("#sayFileDiv").hide();
	$("#sayfileinfo").html(""); 
	$("#sayfile_id").val("");
}

/***推荐分享 start****/  
var recommend ={
     checkRecommendem:function(){
    	 var em =$("#tuijianem").val();
    		if($.trim(em)==""){
    			$("#tuijian_emMsg").html('手机号或者邮箱不能为空');
    			$("#tuijian_emMsg").css("display","block")
    			$("#tuijian_em").css("display","none")
    			return false;
    		
    		}else if(!checkEmail(em)&&
    				 !checkMobile(em)) { 
    			$("#tuijian_emMsg").html('手机号或者邮箱有误');
    			$("#tuijian_emMsg").css("display","block");
    			$("#tuijian_em").css("display","none")
    			return false;
    		}else{
    			$("#tuijian_emMsg").html('');
    			$("#tuijian_emMsg").css("display","none");
    			$("#tuijian_em").css("display","block")
    			return true;
    		}
	}, checkRecommendLiu:function(){
   	   var ly =$("#tuijianly").val();
		if($.trim(ly)==""){
			$("#tuijian_ly_msg").html('随便说一句吧(20字以内)');
			$("#tuijian_ly_msg").css("display","block")
			return false;		
		} if(ly.length>20){
			$("#tuijian_ly_msg").html('您的留言字数太多啦(20字内)');
			$("#tuijian_ly_msg").css("display","block")
			return false;		
		}else{
			$("#tuijian_ly_msg").html('');
			$("#tuijian_ly_msg").css("display","none"); 
			return true;
         }
	},
	RecommendSubmit:function(){
		if(recommend.checkRecommendLiu() & 
		  recommend.checkRecommendem()){
			    loadingMsg("推荐分享","正在处理，请稍后....");
			  data={"ly":$.trim($("#tuijianly").val()),	"mailOrphone":$.trim($("#tuijianem").val())}
					$.post("/recommendSharing",data,function(rtnData){  
						MsgBoxColse();
					   if(FailureMsg(rtnData)==true)return;						 					 
						msgBoxTohome("推荐分享",rtnData.msg);
						recommend.clear();
					});
		}
	},
	clear:function(){
		$("#tuijianly").val("");
		$("#tuijianem").val(""); 
		$("#tuijian_ly_msg").css("display","none"); 
		$("#tuijian_emMsg").css("display","none");
		$("#tuijian_em").css("display","block")
	},
	
}
/***推荐分享 end  ****/  
 /**
 * 失效信息提示
 */
function FailureMsg(rtnData){
	if( Object.prototype.toString.call(rtnData).toLowerCase() == "[object xmldocument]")	return true;
	else return false;
}

/**
 * 查询未读应用消息
 */

function alwaySearchMsg() {
	$.ajax({
		url:"/alwaySearchMsg",
		type:"POST",
		success:function(data){
			 if(FailureMsg(data)==true)return;
			if(data!=undefined && data!="" && data.length!=undefined){
		        $("#msg_main_0").html(drowMsgList(data));
			}else{
				$("#msg_main_0").html("<p  align='center' style='padding-top:30px;'>暂无消息</p>");	
			}
			var showt0=false;///公告			 
			var showt1=false;///工资
			var showt2=false;///投票
			$.each(data,function(i,value){
				if(value.type==0){showt0=true;$("#system_menus").find("div#143").find("i").html(value.size);
				}else if(value.type==1){showt1=true;$("#system_menus").find("div#140").find("i").html(value.size);		
				}else if(value.type==2){showt2=true;$("#system_menus").find("div#142").find("i").html(value.size);		
				}				
			});
			if(showt0){$("#system_menus").find("div#143").find("i").show();
			}else{$("#system_menus").find("div#143").find("i").hide();}		
			if(showt1){	$("#system_menus").find("div#140").find("i").show();
		    }else{$("#system_menus").find("div#140").find("i").hide();}
			if(showt2){	$("#system_menus").find("div#142").find("i").show();
		    }else{$("#system_menus").find("div#142").find("i").hide();}
		}
	})
	 
	
}
var type=['公告管理','我的工资','投票','','','']
/**
 * 描绘消息提示列表
 */
function drowMsgList(data){ 
	var html="";
	$.each(data,function(i,value){
		html+="<div class='yymsgList ptcs' onClick='lookMsgClick(this,"+value.size+","+value.id+","+value.type+",0)'>";
		html+="<div class='yyimgBox'><a href='#1'><img src='"+value.picpath+"'></a></div>";
		html+="<div class='yymsgBox'>";   
		html+="<p class='yyname2'><span class='fz15'><a href='#1'>"+value.title+"</a></span>" ;
		if(value.size>1){
		html+="<span class='msg2'>"+value.size+"</span>";
		}	
		html+="<i>"+value.createdate+"</i></p>";
		html+="<p class='plifo'>"+value.contentText+"</p></div></div>"
	});
	return html;
}

/**
 * 描绘消息查看列表
 */
function drowLookMsgList(data){ 
	var html="";
	if(data.length==0){
		html=+"<div class='yymsgList ptcs'>暂无消息</div>";
	}else{
	$.each(data,function(i,value){
		html+="<div class='yymsgList ptcs' onClick='lookMsgs(this,"+value.id+","+value.type+")'>";
		html+="<div class='yyimgBox'><a href='#1'><img src='"+value.picpath+"'></a></div>";
		html+="<div class='yymsgBox'>";   
		html+="<p class='yyname'><span class='fz15'><a href='#1'>"+value.title+"</a></span><i>"+value.createdate+"</i></p>";
		html+="<p class='plifo'>"+value.contentText+"</p></div></div>"
	});
    }
	return html;
}
/**
 * 查询看消息
 */
function lookMsgClick(param,size,id,type,tip){
	 
	if(size>1){
		$.post("/searchMsgs",{"type":type},function(rtnData){ 
			 if( FailureMsg(rtnData)==true)return  ;
			try{rtnData=JSON.parse(rtnData);}catch(e){}
			 if(rtnData.warninginfo!=undefined){
				 $("#gzList").html("<p>"+rtnData.warninginfo+"</p>");
				 gzinfo();
				 return;
			 }
			if(rtnData!=undefined && rtnData!=""){
	        $("#gzList").html(drowLookMsgList(rtnData));
			}else{
			$("#gzList").html("暂无消息");
			}
			if(tip==0)	{gzinfo();}
		});
		
	}else if(size = 1){
		lookMsgs(param,id,type);
		
	}
}
/**
 * 查看消息详情
 */
function lookMsgs(param,id,type){
     $(param).remove();
	if(type==0){	  
	  Iframeload("?nid="+id,143);
	}else if(type==1){ 
	  Iframeload("?wid="+id,140);
	}else if(type==2){ 
	  Iframeload("?vid="+id,142);
	 }
}

function Iframeload(str,menuid){
	var url="/queryMenus";
	var data = {
			"levl":"0",	
			"valflg" :"1"			 
		};
	$.post(url,data, function(arr) {
		if(isNotNull(arr)){
			createMenuHtml(1,arr.list);
			if(isNotNull(menuid)){
				var menu=null;
				$.each(arr.list,function(i,v){
					if(v.menuid==menuid) menu=v;
				});
				if(menu!=null){
					validateAuthor(menu.menuid,menu.menuname,menu.pathstr+str);
				}else{
					//alert("该应用已不存在!");
					msgBoxTohome("提示信息","该应用已不存在!");
				}
			}
		}
	});
}
	
//首页红点 
function queryUnreadyMsg(){
	var url="/queryUnreadyMsg";	 
	$.post(url,{}, function(data) {	
	 if(FailureMsg(data)==true)return;
	 var flag=true;
	 $.each(data,function(i,value){
		 if(value.type==1 ){
			 //我的消息
			 if($("#ptmu_main_0").css('display')!='block' && value.state==1){
				 $("#my1").show();
			 }else{
				 $("#my1").hide();
			 }
		 } 
		 
		 if(value.type==2){
			 if(value.state==1 ){
				 if($("#ptmu_main_1").css('display')!='block'){
					 $("#my2").show();
					 flag=false;
					 
				 }else{
					 $("#my2").hide();
				 }
				 $("#myall").css("display","block");
			 }else {
				 $("#myall").css("display","none");
				 if(flag==true){
					 $("#my2").hide();
				 }
			 }
			 
		 } 
		 if(value.type==3){
			  //与我相关
			 if(value.state==1 ){
				 if($("#ptmu_main_1").css('display')!='block'){
					 $("#my2").show();
					 flag=false;
					 
				 }else{
					 $("#my2").hide();
				 }
				 $("#myme").css("display","block");
			 }else {
				 $("#myme").css("display","none");
				 if(flag==true){
					 $("#my2").hide();
				 }
			 }
			
		 } 
		 
	 });	 
	});
}
	


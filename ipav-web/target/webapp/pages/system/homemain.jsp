<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Content-Disposition" content="attachment;">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="/pages/css/xt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/pt_style.css" />
<link rel="stylesheet" type="text/css" href="/pages/css/zTreeStyle.css" >
<script type="text/javascript" src="/pages/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/pages/js/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
<script type="text/javascript" src="/pages/js/colleague/colleague.js"></script>
<script type="text/javascript" src="/pages/js/myMsg/myMsg.js"></script>
<script type="text/javascript" src="/pages/js/preview.js"></script>
<link rel="stylesheet" type="text/css" href="/pages/css/public_style.css" />
<script type="text/javascript" src="/pages/js/public.js"></script>
<script type="text/javascript" src="/pages/js/jquery.placeholder.js"></script>
<script type="text/javascript" src="/pages/js/jquery.validate.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="/pages/js/jquery.ztree.excheck-3.5.js"></script>
<!-- <script type="text/javascript" src="/pages/js/jquery.Jcrop.js"></script> -->
<script type="text/javascript" src="/pages/js/jquery.imgareaselect-0.9.10/scripts/jquery.imgareaselect.min.js"></script>
<script type="text/javascript" src="/pages/js/sessionManager.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/mask.js"></script>
<script type="text/javascript" src="/pages/js/publicjs/msgBox.js"></script>
<script type="text/javascript" src="/pages/js/common.js"></script>
<script type="text/javascript">
$(function(){
			$('input').placeholder();
		});
</script>

<script type="text/javascript">
var menuid_to_iframeid={
		"myWage":"",
		"wageManager":"",
		"notice":"",
		"wageManager":""
};
$(function(){
	//初始化系统应用展开
	$("#app_system").click();
});
	//div显示与隐藏切换
	function showView(obj){
		$(obj).toggleClass("currentDt").siblings(".subNav");//.removeClass("currentDt");变换其它为收起的箭头方向
		// 修改数字控制速度， slideUp(500)控制卷起速度
		$(obj).next(".toolinfo").slideToggle(500);//.siblings(".toolinfo").slideUp(500);追加后的效果就是点击当前影藏其它展开的项
	}
	function showMenus(obj,tt){
		showView(obj);
		if($(obj).attr("class")=='subNav')
			queryMenus(tt,null);		
	}
	function queryMenus(tt,request_id){
		var url="/queryMenus";
		var data = {
				"levl":"0",	
				"valflg" :"1"
				//"menutype":tt 包括系统应用,个人应用,企业应用,查询条件
			};
		getMenus(tt,url,data,request_id);
	}
	function getMenus(tt,url,data,request_id){		
		$.post(url,data, function(arr) {
			if(isNotNull(arr)){
				createMenuHtml(tt,arr.list);
				if(isNotNull(request_id)){
					var menu=null;
					$.each(arr.list,function(i,v){
						if(v.menuid==request_id) menu=v;
					});
					if(menu!=null){
						validateAuthor(menu.menuid,menu.menuname,menu.pathstr);
					}else{
						msgBoxTohome("该应用已不存在!");
					}
				}
			}
		});
	}
	function createMenuHtml(tt,list){
		var infodiv_id="";
		if(tt==1){
			infodiv_id="#system_menus";
		}else if(tt==2){
			infodiv_id="#company_menus";
		}else if(tt==3){
			infodiv_id="#user_menus";
		}
		var infohtml="";
		$.each(list,function(i,v){
			if(v.menuid==109){
				infohtml+="<div class='tiocbox' id='"+v.menuid+"'><a href='javascript:;' onClick='tanchuang()'><div><img src='"+v.imgsrc+"' style='width:48px;height:48px;'></div><p>"+v.menuname+"</p></a></div>";
			}else{
				infohtml+="<div class='tiocbox' id='"+v.menuid+"'><i class='msg4 dn'>4</i><a href='javascript:;' onClick='queryMenus(1,"+v.menuid+")'><div><img src='"+v.imgsrc+"' style='width:48px;height:48px;'></div><p>"+v.menuname+"</p></a></div>";			 
			}
		});
		$(infodiv_id).html(infohtml);
	}
	
</script>

<script type="text/javascript">

(function($) {
	jQuery.fn.extend({
				uploadPreview : function(opts) {
					opts = jQuery.extend({
						width : 0,
						height : 0,
						imgPreview : null,
						imgType : [ "gif", "jpeg", "jpg", "bmp", "png" ],
						callback : function() {
							return false;
						}
					}, opts || {});

					var _self = this;
					var _this = $(this);
					var imgPreview = $(opts.imgPreview);
					var showWidth;
					var showHeight;
					// 设置样式
					autoScaling = function() {
						imgPreview.css({
							"margin-left" : 0,
							"margin-top" : 0,
							"width" : opts.width,
							"height" : opts.height,
							"background-size": "100% auto"
						});
						imgPreview.show();
					}
					// file按钮出发事件
					_this.change(function() {
					opts.width=$(imgPreview).parent().width();
					opts.height=$(imgPreview).parent().height();
					if (this.value) {
						if (!RegExp(
								"\.(" + opts.imgType.join("|")
										+ ")$", "i").test(
								this.value.toLowerCase())) {
							msgBoxTohome("图片类型必须是"
									+ opts.imgType.join("，")
									+ "中的一种");
							this.value = "";
							return false;
						}
						
						if ($.browser.msie) {// 判断ie
							try{
								var file=$(this)[0].files[0];
							    if (window.createObjectURL != undefined) {
							    	path = window.createObjectURL(file);
							    } else if (window.URL != undefined) {
							    	path = window.URL.createObjectURL(file);
							    } else if (window.webkitURL != undefined) {
							    	path = window.webkitURL.createObjectURL(file);
							    }
							    $("<img>").attr("src",path).load(function(){
									var showDivRatio=parseFloat(opts.width/opts.height);
									var imgRatio=parseFloat(this.width/this.height);
									if(showDivRatio>=imgRatio){
										if(opts.height>=this.height){
											opts.width=parseInt(this.width);
											opts.height=parseInt(this.height);
										}else{
											opts.width=parseInt(this.width*(opts.height/this.height));
											opts.height=parseInt(this.height);
										}
									}else{
										if(opts.width>=this.width){
											opts.width=parseInt(this.width);
											opts.height=parseInt(this.height);
										}else{
											opts.width=parseInt(this.width);
											opts.height=parseInt(this.height*(opts.width/this.width));
										}
									}
									autoScaling();
									initImgPosition(opts.width,opts.height);
								});
							    imgPreview.css("background","url("+path+") center no-repeat");
							    $("#previewPic").attr("src",path);
							}catch(e){
								$(this)[0].select();
								window.parent.document.body.focus();
								path=document.selection.createRange().text;
								var imgJsObj=document.getElementById($(imgPreview).attr("id"));
								imgJsObj.style.filter ="progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale')"; 
								imgJsObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").apply();
								$("<img>").attr("src",path).load(function(){
									var showDivRatio=parseFloat(opts.width/opts.height);
									var imgRatio=parseFloat(this.width/this.height);
									if(showDivRatio>=imgRatio){
										if(opts.height>=this.height){
											opts.width=this.width;
											opts.height=this.height;
										}else{
											opts.width=parseInt(this.width*(opts.height/this.height));
										}
									}else{
										if(opts.width>=this.width){
											opts.width=this.width;
											opts.height=this.height;
										}else{
											opts.height=parseInt(this.height*(opts.width/this.width));
										}
									}
									autoScaling();
									initImgPosition(opts.width,opts.height);
								});
								$(imgJsObj).css("background","url("+path+") center no-repeat");
								$("#previewPic").attr("src",path);
							}
						} else {
							if ($.browser.version < 7) {
								$("<img>").attr("src",this.files
										.item(0).getAsDataURL()).load(function(){
									var showDivRatio=parseFloat(opts.width/opts.height);
									var imgRatio=parseFloat(this.width/this.height);
									if(showDivRatio>=imgRatio){
										if(opts.height>=this.height){
											opts.width=this.width;
											opts.height=this.height;
										}else{
											opts.width=parseInt(this.width*(opts.height/this.height));
										}
									}else{
										if(opts.width>=this.width){
											opts.width=this.width;
											opts.height=this.height;
										}else{
											opts.height=parseInt(this.height*(opts.width/this.width));
										}
									}
									autoScaling();
									initImgPosition(opts.width,opts.height);
								});
								imgPreview.css("background","url("+this.files
										.item(0).getAsDataURL()+") center no-repeat");
								$("#previewPic").attr("src",this.files
										.item(0).getAsDataURL());
					}
else {
	oFReader = new FileReader(),
	rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
	oFReader.onload = function(oFREvent) {
		$("<img>").attr("src",oFREvent.target.result).load(function(){
			var showDivRatio=parseFloat(opts.width/opts.height);
			var imgRatio=parseFloat(this.width/this.height);
			if(showDivRatio>=imgRatio){
				if(opts.height>=this.height){
					opts.width=this.width;
					opts.height=this.height;
				}else{
					opts.width=parseInt(this.width*(opts.height/this.height));
				}
			}else{
				if(opts.width>=this.width){
					opts.width=this.width;
					opts.height=this.height;
				}else{
					opts.height=parseInt(this.height*(opts.width/this.width));
				}
			}
			autoScaling();
			initImgPosition(opts.width,opts.height);
		});
			imgPreview.css("background","url("+oFREvent.target.result+") center no-repeat");
			$("#previewPic").attr("src",oFREvent.target.result);
		};
		var oFile = this.files[0];
		oFReader.readAsDataURL(oFile);
	}
	}
	}
	opts.callback();
		});
		}
	});
})(jQuery);

function initImgPosition(width,height){
	$("#usermsgimage").css({
        position: "absolute",
        left: ($("#usermsgimage").parent().width() - $("#usermsgimage").outerWidth()) / 2,
        top: ($("#usermsgimage").parent().height() - $("#usermsgimage").outerHeight()) / 2
    });
	/* $('#usermsgimage').imgAreaSelect({ 
		selectionColor: 'blue', 
		maxWidth:width,
		minWidth:0,
		minHeight:0,
		maxHeight:height, 
		selectionOpacity: 0.2,
		aspectRatio: '1:1',
		instance:true,
		autoHide:true,
		onSelectChange: processImg 
	});	 */	
}
</script>

<script type="text/javascript">
function tanchuang(){
	
	$.ajax({type:"POST",url:"/havsysroleflg",data:null,
		success:function(data){
			if(data){
				window.open("/ipavsystem");
			}else{
// 				$("#simaltBox3").find("p").html("您没有该操作权限,请联系管理员!");
// 				showtsBox3();
				msgBoxTohome("提示信息","您没有该操作权限,请联系管理员!","");
			}
		}
	});
}

function tishi(){
	msgBoxTohome("您好,如需使用此模块请联系管理员");
}

function showjg12(info){
	$("#msg_info").html(info);
	$("#simScrollCont12").show();
	$("#simScrollContBox12").show();	
}
function  hidejg12(){
	$("#msg_info").html("");
	$("#simScrollContBox12").hide();
	$("#simScrollCont12").hide();
}

$(window).resize(function() {
	$(".simScrollContBox4").css({
        position: "absolute",
        left: ($(window).width() - $(".simScrollContBox4").outerWidth()) / 2,
        top: ($(window).height() - $(".simScrollContBox4").outerHeight()) / 2
    });
	
	$(".passwordBox").css({
        position: "absolute",
        left: ($(window).width() - $(".passwordBox").outerWidth()) / 2,
        top: ($(window).height() - $(".passwordBox").outerHeight()) / 2
    });
	
	$(".simaltBox3").css({
        position: "absolute",
        left: ($(window).width() - $(".simaltBox3").outerWidth()) / 2,
        top: ($(window).height() - $(".simaltBox3").outerHeight()) / 2
    });
})

$(function() {
    $(window).resize();	
});

function showtsBox3(){
/* 	$(window.top.document.children).contents().find("div#ptlfbg2").show();
	$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont").show();
	$(window.top.document.body).addClass("html-body-overflow"); */
	homeOrdinaryShow();
	//$("#simalpha3").show();
	$("#simaltBox3").css("z-index",1);	
	$("#simaltBox3").show();	
}

function validateAuthor(menuid,menuname,path){
// 	var path='';
// 	if(type==1){
// 		path="/ipav/wage/myWage";
// 	}else if(type==2){
// 		path="/ipav/wage/wageManager";
// 	}else if(type==4){
// 		path="/ipav/notice/noticeManager";
// 	}else if(type==5){
// 		path="/ipav/vote/voteCenter";
// 	}
	$.ajax({
		url:path,
		type:"POST",
		success:function(data){
			if(data.warningtype==undefined)
				openService(menuid,menuname,path);
			else{
				$("#simalpha3").find("a").unbind("click");
				$("#simalpha3").find("input[type=button]").unbind("click");
				if(data.warningtype==0)
					sessionTimeOut();
				else if(data.warningtype==1)
					authorFilter();
				/* $("#simaltBox3").find("p").html(data.warninginfo);
				showtsBox3(); */
				msgBoxTohome("提示信息", data.warninginfo);
			}
		}
	});
}

function sessionTimeOut(){
	$("#simalpha3").find("a").bind("click",function(){
		window.parent.location="http://localhost:8080/ipav";
	});
	$("#simalpha3").find("input[type=button]").bind("click",function(){
		window.parent.location="http://localhost:8080/ipav";
	});
}

function authorFilter(){
	$("#simaltBox3").find("a").bind("click",function(){
		$("#simalpha3").hide();
		$("#simaltBox3").hide();	
		/* $(window.top.document.children).contents().find("div#ptlfbg2").hide();
		$(window.top.document.body).removeClass("html-body-overflow");
		$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont").hide(); */
		homeOrdinaryHide()
	});
	$("#simaltBox3").find("input[type=button]").bind("click",function(){
		$("#simalpha3").hide();
		$("#simaltBox3").hide();	
	/* 	$(window.top.document.children).contents().find("div#ptlfbg2").hide();
		$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont").hide();
		$(window.top.document.body).removeClass("html-body-overflow"); */
		homeOrdinaryHide();
	});
}

/* $.ajaxSetup({
	contentType:'application/x-www-form-urlencoded;charset=utf-8',
	complete:function(XMLHttpRequest,textStatus){
		var warning=XMLHttpRequest.getResponseHeader("errmsg");
		if(warning!=undefined)
			window.parent.location="/warningInfo?warninginfo="+warning;
	}
}); */

function openService(menuid,menuname,menupath){
// 	var id;
// 	var name;
// 	var src_str_4="/ipav/notice/noticeManager"
// 	if(isNotNull(nid)) src_str_4+="?nid="+nid;
// 	if(type==1){
// 		id="myWage";
// 		name="我的工资";
// 		iframe=$('<iframe id="myWage" height="auto" width="100%"  scrolling="no" frameborder="0" allowtransparency="true" src="/ipav/wage/myWage" style="margin-bottom:-4px;"></iframe>');
// 	}else if(type==2){
// 		id="wageManager";
// 		name="人事管理";
// 		iframe=$('<iframe id="wageManager" height="auto" width="100%"  scrolling="no" frameborder="0" allowtransparency="true" src="/ipav/wage/wageManager" style="margin-bottom:-4px;"></iframe>');
// 	}else if(type==4){
//  	id="notice";
// 		name="公告管理";
// 		iframe=$('<iframe id="notice" height="auto" width="100%"  scrolling="no" frameborder="0" allowtransparency="true" src="'+src_str_4+'" style="margin-bottom:-4px;"></iframe>');
// 	}else if(type==5){
// 		id="voteCenter";
// 		name="投票中心";
// 		iframe=$('<iframe id="voteCenter" height="auto" width="100%"  scrolling="no" frameborder="0" allowtransparency="true" src="/ipav/vote/voteCenter" style="margin-bottom:-4px;"></iframe>');
// 	}
	
	if($("#"+menuid,window.parent.document).length==0){
		var iframe=$('<iframe id="'+menuid+'" height="auto" width="100%"  scrolling="no" frameborder="0" allowtransparency="true" src="'+menupath+'"></iframe>');
		$("body",window.parent.document).append(iframe);
		var li=$('<li><div class="works" id="'+menuid+'">'+menuname+'</div><a href="#1" class="pt-muclose" title="关闭"></a></li>');
		$($("iframe",window.parent.document)[0].contentWindow.document).find("div.toolmenu").find("ol").append(li);
		$($("iframe",window.parent.document)[0].contentWindow.document).find("div.toolmenu").find("li").unbind("click");
		$($("iframe",window.parent.document)[0].contentWindow.document).find("div.toolmenu").find("li").bind("click",function(){
			var curr_id=$(this).find("div").attr("id");
			
			if(curr_id=="mainframe"){
				$(this).siblings().find("div").removeClass("works");
				//$(this).find("div").attr("class","ptworks");
				$(this).find("div").addClass("ptworks2");
			}else{
				$(this).siblings().find("div").removeClass("works");
				$(this).parent().find("div#mainframe").attr("class","ptworks");
				$(this).find("div").addClass("works");
			}
			$.each($("iframe",window.parent.document),function(idx,frame){
				if(idx>0&&$(frame).attr("id")!=curr_id){
					$(frame).css("display","none");
				}else{ 
					$(frame).css("display","block");
				}
			});
		})
		
		
		$($("iframe",window.parent.document)[0].contentWindow.document).find("div.toolmenu").find("li").find("a").bind("click",function(){
			var preli=$(this).parent().prev();
			var id=$(this).prev().attr("id");
			$("#"+id,window.parent.document).remove();
			$(this).parent().remove();
			$(preli).click();
		})
		
		
		$(li).click();
	}else{
		$($("iframe",window.parent.document)[0].contentWindow.document).find("div.toolmenu").find("div#"+menuid).parent().click();
		$("#"+menuid,window.parent.document).attr("src",menupath);
	}
	if(menuid==143){
		$("#143",window.parent.document).css("height",$(window.parent.document).height()-114);
		$("#143",window.parent.document).css("marginLeft",8);
	}
		
}

function sayitleFunction(i,len){
	if(i==1){allSays(1);$("#myall").hide();}
	else if(i==2){mySays(1);$("#myme").hide();}
	else if(i==3){myReleaseSays(1);}
	for(var j=1;j<=len;j++){
		if(j==i){
		 $("#sayitle_"+j).addClass("current");
		}else{
		 $("#sayitle_"+j).removeClass("current");			
		}
	}
	/* //$(".qudtinfo").html(ke);
	//$("#kejianfws").hide(); */
}
$(function(){
	$(".mefilter").click(function(){
				var sp = $(this).text();
				$("#kjsq_title_1").html(sp);
				$("#filterBox").hide();
		})	

	$("#hidefilt").hover(function(){
				$("#filterBox").hide();
		})	

	$(".shuotool").hover(function(){
				$("#kejianfw").hide();
		})
	$(".tsqmainBox").hover(function(){
				$("#kejianfws").hide();
		})
	$(".tsxxinfo").hover(function(){
				$(this).find(".tstacBox").hide();
		})	
		
	$(".kejiannr").click(function(){
				var ke = $(this).text();
				$(".kejians").html(ke);
				if(ke=="指定的同事")
				{
				$("#tslistBox").show();
				kejianforControl();
					}else{
						$("#tslistBox").hide();
						}
				$("#kejianfw").hide();
	})	
		
	$(".delname").click(function(){
				  $(this).parent().remove();
		})


	//选中同事
	$(".tsmsgBox").click(function(){
				var sa = $(this).attr('class').split(" ")[1];
				if(sa=="tsmbg")
				{
				$(this).removeClass('tsmbg');
				}else if(sa==null){
				$(this).addClass('tsmbg');
				}
		})

	$(".shuoright").click(function(){
			$(".shuoimgBox").show();
		})
	//说说模块获得焦点的时候
	$('.shuotext').focus(function() { 
	$('#tsqBoxdd').show(); 
	}); 

	})

	//全部动态评论显示隐藏
	function getRevie(e){
		var sa = $("#revie-main"+e).css("display");
		   if(sa ==="none")
		   {	
			   	$("#revie-main"+e).show();
		   }
			if(sa ==="block")
		   {
		   		$("#revie-main"+e).hide();
		   }
		}

	//我的动态评论内容显示隐藏
	function getRevies(e){
		var sa = $("#revies-main"+e).css("display");
		   if(sa ==="none")
		   {	
			   	$("#revies-main"+e).show();
		   }
			if(sa ==="block")
		   {
		   		$("#revies-main"+e).hide();
		   }
		}
	//我的动态评论内容显示隐藏
	function gettongshiq(e){
		var sa = $("#tsqs-main"+e).css("display");
		   if(sa ==="none")
		   {	
			   	$("#tsqs-main"+e).show();
		   }
			if(sa ==="block")
		   {
		   		$("#tsqs-main"+e).hide();
		   }
		}
	function getfilter(){
		$("#filterBox").show();
		}
	function getkejianfw(){
			$("#kejianfw").show();
		}
	function getkejianfws(){
			$("#kejianfws").show();
		}
	function gettslistinfo(){
		$("#tslistBox").show();
		}
	function hidetslistinfo(){
		$("#tslistBox").hide();
		$(".kejians").html("所有人可见");
		}
	function getfind(){
			$("#kejianfws").show();
		}

	function gettsxxinfo(e){
		document.getElementById("tsxxmain"+e).style.display = "block";
		}
	//删除图片	
	function deleteshuoimg(){
		$(".shuoimgs").remove();
		$(".shuoimgBox").hide();
	}
	
	function processImg(img, selection){
		var scaleX = 70 / (selection.width || 1);
	    var scaleY = 70 / (selection.height || 1);
	    $("#startX").val(selection.x1);
	    $("#startY").val(selection.y1);
	    $("#width").val(selection.width);
	    $("#height").val(selection.height);
	    /* $('#previewPic').css({
	        width: 70 + 'px',
	        height: 70 + 'px',
	        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
	        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
	    }); */
	    $('#previewPic').css({
	        width: Math.round(scaleX * $("#usermsgimage").width()) + 'px',
	        height: Math.round(scaleY * $("#usermsgimage").height()) + 'px',
	        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
	        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
	    });
	}
	
	$(document).ready(function(){
		$("#headpic").uploadPreview({
			imgPreview : $("#usermsgimage"),
			imgType : [ "png", "jpg" ],
			callback : function() {
				return false;
			}
		});
		
		$('#usermsgimage').imgAreaSelect({ 
			selectionColor: 'blue', 
			maxWidth:215,
			minWidth:0,
			minHeight:0,
			maxHeight:145, 
			selectionOpacity: 0.2,
			aspectRatio: '1:1',
			instance:true,
			autoHide:true,
			onSelectChange: processImg 
		}); 
		$.ajax({
			url:"queryUserInfo",
			type:"POST",
			success:function(data){
				if(data.account!=undefined){
					$("#leftUserInfo").find("label").html(data.userName);
					if(data.sex==0)
						$("#leftUserInfo").find("div").attr("class","girl");
					else
						$("#leftUserInfo").find("div").attr("class","man");
					if(data.picpath!=undefined&&data.picpath!=''){
						$("#leftUserInfo").find("div").css("background",'url('+data.picpath+')');
						$("#leftUserInfo").find("div").css("background-size","70px 70px");
					}
					$($("#otherInfos").find("label")[0]).html("");
					$($("#otherInfos").find("label")[1]).html("");
					$($("#otherInfos").find("label")[2]).html("");
					$($("#otherInfos").find("label")[3]).html("");
					$($("#otherInfos").find("label")[4]).html("");
					$($("#otherInfos").find("label")[5]).html("");
					
					$($("#otherInfos").find("label")[0]).html(data.account);
					$($("#otherInfos").find("label")[1]).html(data.org);
					$($("#otherInfos").find("label")[2]).html(data.duty);
					$($("#otherInfos").find("label")[3]).html(data.is_leader);
					$($("#otherInfos").find("label")[4]).html(data.partTimeDesc==""?"无":data.partTimeDesc);
					$($("#otherInfos").find("label")[5]).html(data.platname);
				}
			}
		});
	})
	
	function updateEmailReg(param){
		$(param).parent().prev().find('input').attr('readonly','readonly');
		$(param).html('取消设置');
		$(param).attr("onclick","resetClick(this)");
		//$("#mobile").find('td:last-child').html('<a href="#1" onclick="createValidateTR(this)">设置为账号</a>')
	}
	
	var mobile;
	var email;
	
	function initUserInfo(){
		cancelPicUpdate();
		if($("#mobile").next().attr("id")==undefined)
 			$("#mobile").next().remove();
 		if($("#email").next().attr("id")==undefined)
 			$("#email").next().remove();
		$.ajax({
			url:"queryUserInfo",
			type:"POST",
			success:function(data){
				if(data.account!=undefined){
					$("#username").find("img").attr("src",'/pages/images/platform/userimg.png');
					$("#headpic").val('');
					$("#previewPic").attr("src","/pages/images/platform/headpic.png");
					$("#sex").find('input[name="rad1"]').removeAttr("checked");
					$("#phone").find("input").val('');
					$("#address").find("textarea").val('');
					$("#otherinfo").find("label").html('');
					
					$("#leftUserInfo").find("label").html(data.userName);
					$("#username").find("p").html(data.userName);
					$("#sex").find('input[type="radio"][value="'+data.sex+'"]').attr("checked","checked");
					$("#previewPic").removeAttr("style");
					if(data.sex==0)
						$("#leftUserInfo").find("div").attr("class","girl");
					else
						$("#leftUserInfo").find("div").attr("class","man");
					if(data.picpath!=undefined&&data.picpath!=''){
						$("#username").find("img").attr("src",data.picpath);
						$("#previewPic").attr("src",data.picpath);
						$("#leftUserInfo").find("div").css("background",'url('+data.picpath+')');
						$("#leftUserInfo").find("div").css("background-size","70px 70px");
						initUserPic(data.picpath,$("#usermsgimage"));
					}
					$("#regtype").val(data.regtype);
					if(data.regtype=='M'){
						$("#mobile").find("label").html("手机(账号)：")
						$("#email").find("label").html('邮箱：');
						$("#mobile").find("td:last-child").html('<a href="#1" onclick="setInputWritable(this)">修改账号</a>');
						$("#email").find('input[type="text"]').removeAttr("readonly");
					}else{
						$("#email").find("label").html('邮箱(账号)：');
						$("#mobile").find("label").html("手机：")
						$("#email").find("td:last-child").html('<a href="#1" onclick="setInputWritable(this)">修改账号</a>');
						$("#mobile").find('input[type="text"]').removeAttr("readonly");
					}
					if(data.mobile!=undefined){
						$("#mobile").find("input").val(data.mobile);
						mobile=data.mobile;
					}
					if(data.email!=undefined){
						$("#email").find("input").val(data.email);
						email=data.email;
					}
					if(data.phone!=undefined){
						if(data.phone.indexOf("-")>0){
							var arr=data.phone.split("-");
							$($("#phone").find("input")[0]).val(arr[0]);
							$($("#phone").find("input")[1]).val(arr[1]);
						}else
							$($("#phone").find("input")[1]).val(data.phone);
					}
					if(data.address!=undefined)
						$("#address").find("textarea").val(data.address);
					$($("#otherinfo").find("label")[0]).html(data.account);
					if(data.org!=undefined)
						$($("#otherinfo").find("label")[1]).html(data.org);
					if(data.duty!=undefined)
						$($("#otherinfo").find("label")[2]).html(data.duty);
					if(data.is_leader!=undefined)
						$($("#otherinfo").find("label")[3]).html(data.is_leader);
					if(data.partTimeDesc!=undefined&&data.partTimeDesc!='')
						$($("#otherinfo").find("label")[4]).html(data.partTimeDesc);
					else
						$($("#otherinfo").find("label")[4]).html('无');
					if(data.platname!=undefined)
						$($("#otherinfo").find("label")[5]).html(data.platname);
				}
			}
		});
	}
	
	function changeReg(param){
		if($(param).parents("tr").attr("id")=="mobile"){
			$("#regtype").val("M");
			$(param).remove();
			$("#email").find("td:last-child").html('<a href="#1" onclick="changeReg(this)">绑定</a>');
		}else{
			$("#regtype").val("E");
			$(param).remove();
			console.log($("#mobile").find("td"));
			$("#mobile").find("td:last-child").html('<a href="#1" onclick="changeReg(this)">绑定</a>');
		}
	}
	
	function setInputWritable(param){
		$(param).parent().prev().find('input').removeAttr("readonly");
		//if($(param).parents("tr").attr("id")=="mobile")
			$(param).parent().html('<a href="#1" onclick="createValidateTR(this)">获取验证码</a>')
		/* else{
			$(param).html('取消设置');
			$(param).attr("onclick","resetClick(this)");
		} */
	}
	
	function initUserPic(picpath,div){
		var outWidth=div.parent().width();
		var outHeight=div.parent().height();
		$("<img>").attr("src",picpath).load(function(){
			var showDivRatio=parseFloat(outWidth/outHeight);
			var imgRatio=parseFloat(this.width/this.height);
			if(showDivRatio>=imgRatio){
				if(outHeight>=this.height){
					outWidth=this.width;
					outHeight=this.height;
				}else{
					outWidth=parseInt(this.width*(outHeight/this.height));
				}
			}else{
				if(outWidth>=this.width){
					outWidth=this.width;
					outHeight=this.height;
				}else{
					outHeight=parseInt(this.height*(outWidth/this.width));
				}
			}
			div.css({
				"margin-left" : 0,
				"margin-top" : 0,
				"width" : outWidth,
				"height" : outHeight,
				"background-size": "100% auto"
			});
			initImgPosition(outWidth,outHeight);
			div.css({"background":"url("+picpath+") center no-repeat","width":outWidth,"height":outHeight,"background-size":"100% auto"});
			$("#headpic").uploadPreview({
				imgPreview : $("#usermsgimage"),
				imgType : [ "png", "jpg" ],
				callback : function() {
					return false;
				}
			});
		});
	}
	
	function createValidateTR(param){
		$(param).removeAttr("onclick");
		var sendParam='receive='+$(param).parent().prev().find('input[type="text"]').val();
		if($(param).parents("tr").attr("id")=="mobile"){
			if(!/^(13[0-9]{9}|14[5|7]\d{8}|15[0-3|5-9]\d{8}|17[0|6-8]\d{8}|18\d{9})$/.test($(param).parent().prev().find('input[type="text"]').val())){
				return;
			}
			sendParam+='&type=1';
		}else{
			if(!/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test($(param).parent().prev().find('input[type="text"]').val())){
				return;
			}
			sendParam+='&type=2';
		}
		
		$.ajax({
			url:"getValidateCode",
			type:"POST",
			data:sendParam,
			success:function(data){
				if(data.err==undefined){
					$(param).parents("tr").after('<tr height="45"><td align="right" width="80"></td><td><input type="text" class="usermsgtext" placeholder="请输入收到的验证码"></td>'
			                +'<td colspan="3"><input id="validateCode" type="button" class="btnyanzheng" value=""></td></tr>');
					$(param).html("取消设置");
					$(param).attr("onclick","resetClick(this)");
					countDown(60,1000,initValidateBtn);
				}else{
					$(param).attr("onclick","createValidateTR(this)");
					msgBoxTohome("获取验证码",data.err);
				}
			}
		})
	}
	
	function getCenterValidateCode(param){
		var sendParam='receive='+$(param).parent().prev().find('input[type="text"]').val();
		if($(param).parents("tr").attr("id")=="mobile"){
			if(!/^(13[0-9]{9})|(15[89][0-9]{8})$/.test($(param).parent().prev().find('input[type="text"]').val())){
				return;
			}
			sendParam+='&type=1';
		}else{
			if(!/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test($(param).parent().prev().find('input[type="text"]').val())){
				return;
			}
			sendParam+='&type=2';
		}
		
		$.ajax({
			url:"getValidateCode",
			type:"POST",
			data:sendParam,
			success:function(data){
				if(data.err==undefined){
					countDown(60,1000,initValidateBtn)
				}else{
					msgBoxTohome("获取验证码",data.err);
				}
			}
		})
	}
	
	function initValidateBtn(){
		$("#validateCode").unbind("click");
		$("#validateCode").bind("click",function(){
			getCenterValidateCode($(param).parents("tr").prev().find("a"));
		});
		$("#validateCode").val("获取验证码");
	}
	
	function countDown(max,interval,fn){
		var timeout=setInterval(function(){
			if(max>0){
				$("#validateCode").val(max);
				--max;
			}else{
				if(fn!=undefined)
					fn();
				clearTimeout(timeout);
			}
		},1000)
	}
	
	function resetClick(param){
		if(mobile==undefined)
			mobile='';
		if(email==undefined)
			email='';
		$(param).parent().prev().find('input').attr('readonly','readonly');
		$(param).parents("tr").next().remove();
		$(param).parents("tr").find('input[type="text"]').val(email);
		$(param).parent().html('<a href="#1" onclick="setInputWritable(this)">修改账号</a>');
		$(param).parent().prev().find("input").attr("readonly","readonly");
	}
	
	function getValidateCode(param){
		var sendParam='receive='+$(param).parents("tr").prev().find('input[type="text"]').val();
		if($(param).parents("tr").prev().attr("id")=="mobile"){
			if(!/^(13[0-9]{9})|(15[89][0-9]{8})$/.test($(param).parents("tr").prev().find('input[type="text"]').val())){
				return;
			}
			sendParam+='&type=1';
		}else{
			if(!/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test($(param).parents("tr").prev().find('input[type="text"]').val())){
				return;
			}
			sendParam+='&type=0';
		}
		$.ajax({
			url:"getValidateCode",
			type:"POST",
			data:sendParam,
			success:function(data){
				if(data.err!=undefined){
					msgBoxTohome("提示信息",data.err);
				}
			}
		})
	}
	
	function showUserInfo(){
		initUserInfo();
		$("#username").find("img").removeAttr("style");
		$("#username").find("img").css({"width":"70px","height":"70px","overflow":"hidden"});
		$("#ptmu_main_4").css("display","block");
		ptmenu('ptmu',-1,4);
	}
	
	function cancelPicUpdate(){
		$("#headpic").val('');
		$("#userfileimgBox").hide();
		$("#usermsgsBox").show();
	}
	
	function showHeadImg(){
		$("#userfileimgBox").show();
		$("#usermsgsBox").hide();
	}
	
	function showBasicInfo(){
		$("#username").find("img").css("width",$('#previewPic').css("width"));
		$("#username").find("img").css("height",$('#previewPic').css("height"));
		/* $("#username").find("img").css("width",70);
		$("#username").find("img").css("height",70); */
		$("#username").find("img").css("marginLeft",$('#previewPic').css("marginLeft"));
		$("#username").find("img").css("marginTop",$('#previewPic').css("marginTop"));
		$("#username").find("img").css("overflow","hidden");
		$("#username").find("img").attr("src",$("#previewPic").attr("src"));
		$("#userfileimgBox").hide();
		$("#usermsgsBox").show();
	}
	
	function updateUserInfo(){
		if($.trim($("#mobile").find('input[type="text"]').val())!=''){
			if(!/^(((13[0-9]{1})|159|153|170)+\d{8})$/.test($("#mobile").find('input[type="text"]').val())){
				msgBoxTohome("警告","手机号码格式不正确");
				return;
			} 
		}
		if($.trim($("#email").find('input[type="text"]').val())!=''){
			if(!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test($("#email").find('input[type="text"]').val())){
				msgBoxTohome("警告","邮箱格式不正确");
				return;
			}
		}
		if($("#validateCode").val()!=undefined&&$.trim($("#validateCode").parents("tr").find('input[type="text"]').val())==""){
			msgBoxTohome("警告","请输入验证码");
			return;
		}
		var param='"userId":"'+$($("#otherinfo").find("label")[0]).html()
			+'","mobile":"'+$("#mobile").find("input").val()
			+'","email":"'+$("#email").find("input").val()
			+'","phone":"'+($($("#phone").find("input")[0]).val()==''?$($("#phone").find("input")[1]).val():($($("#phone").find("input")[0]).val()+'-'+$($("#phone").find("input")[1]).val()))
			+'","address":"'+$("#address").find("textarea").val()
			+'","startX":"'+$("#startX").val()
			+'","startY":"'+$("#startY").val()
			+'","width":"'+$("#width").val()
			+'","height":"'+$("#height").val()
			+'","outWidth":"'+$("#usermsgimage").width()
			+'","outHeight":"'+$("#usermsgimage").height()+'"';
		param+=',"regType":"'+$("#regtype").val()+'"';
		//if($("#validateCode")!=undefined)
		//	param+=',"validateCode":"'+$.trim($("#validateCode").parents("tr").find('input[type="text"]').val())+'"';
		//if($("#mobile").find('td:last').text()=='设置为账号')
		//	param+=',"regType":"E"';
		//else
		//	param+=',"regType":"M"';
		if($("#sex").find('input[type="radio"]:checked').length==1)
			param+=',"sex":"'+$("#sex").find('input[type="radio"]:checked').val()+'"';
		else{
			//$("#msg_info").html("请选择性别");
			//showjg12("请选择性别");	
			
			msgBoxTohome("提示信息","请选择性别");
			return;
		}
		if($("#mobile").next().attr("id")==undefined)
			param+=',"mobileCode":"'+$("#mobile").next().find("input").val()+'"';	
		if($("#email").next().attr("id")==undefined)
			param+=',"emailCode":"'+$("#email").next().find("input").val()+'"';	
		param=$.parseJSON('{'+param+'}');
		$.ajaxFileUpload({
	    	url: 'updateUserInfos', 
	    	type: 'post',
	    	secureuri: false, //一般设置为false
	    	fileElementId: 'headpic', // 上传文件的id、name属性名
	    	dataType: 'json', //返回值类型
	     	data:param,
	     	success: function(data){ 
	     		if(data){
	     			//showjg12(data.msginfo);	
	     			msgBoxTohome("提示信息",data.msginfo);
		     		initUserInfo();
		     		//ptmenu('ptmu',0,4);
	     		}
	     	},
	     	error: function(data, status, e){ 
	     		if(data){
	     			//showjg12(data.msginfo);	
	     			msgBoxTohome("提示信息",data.msginfo);
		     		initUserInfo();
	     		}	
	    	}
	    });
	}

	function onloadjgwh(){
		var inhight =window.parent.document.documentElement.clientHeight;
		var heightmain = inhight-114;
		document.getElementById("pt-center").style.minHeight=heightmain+"px";
		$(".mainscc").height(heightmain);
		}
	window.onload=onloadjgwh;
	
	$(window).resize(function(){
		var inhight =window.parent.document.documentElement.clientHeight;
		var heightmain = inhight-114;
		document.getElementById("pt-center").style.minHeight=heightmain+"px";
		$(".mainscc").height(heightmain);
	});
	
	function tabs(id,curnum,tnum){
		if(curnum==2)
			$("#rcxs_main_2").find('input[type="password"]').val("");
		for (var i = 0; i< tnum;i++ ){
			if(curnum == i){
				document.getElementById(id+"_title_"+curnum).className = "current";
				document.getElementById(id+"_main_"+curnum).style.display = "block";
			}else{
				document.getElementById(id+"_title_"+i).className = "";
				document.getElementById(id+"_main_"+i).style.display = "none";
			}
		}
	}
	
	function clearpwd(){
		$("#rcxs_main_2").find('input[type="password"]').val("");
		ptmenu('ptmu',0,4);
	}
	
	function updatepwd(){
		var password=$($("#rcxs_main_2").find('input[type="password"]')[1]).val();
		var newpassword=$($("#rcxs_main_2").find('input[type="password"]')[2]).val();
		if($($("#rcxs_main_2").find('input[type="password"]')[0]).val()==""){
			msgBoxTohome("警告","原密码不能为空");
			return
		}
		if(password!=newpassword){
			//两次密码不一致
			msgBoxTohome("警告","两次密码不一致")
			return;
		}else{
			/* if(/\w/.test(password)==false){
				msgBoxTohome("警告","只可输入英文和数字！")
				return;
			} */
			if(password.length<6 || password.length>16){
				msgBoxTohome("警告","请输入6-16位新密码！")
				return;
			}
			var param="oldPwd="+$($("#rcxs_main_2").find('input[type="password"]')[0]).val()+"&newPwd="+$($("#rcxs_main_2").find('input[type="password"]')[1]).val();
			$("#rcxs_main_2").find('input[type="password"]').val("");
			$.ajax({
				url:"updatePwd",
				type:"POST",
				data:param,
				success:function(data){
					if(data!=undefined){
						if(data.success!=undefined&&data.success==1){
							//修改成功
							msgBoxTohome("修改密码","修改成功");
							ptmenu('ptmu',0,4);
						}else{
							//修改失败
							msgBoxTohome("修改密码","修改失败,原密码错误");
						}
					}else{
						//修改失败
						msgBoxTohome("修改密码","修改失败,原密码错误");
					}
				}
			})
		}
	}
	
	function picChange(){
		/* $("#headpic").uploadPreview({
			imgPreview : $("#usermsgimage"),
			imgType : [ "png", "jpg" ],
			callback : function() {
				return false;
			}
		});
		
		setTimeout(function(){
			$('#usermsgimage').imgAreaSelect({ 
				selectionColor: 'blue', 
				maxWidth:$('#usermsgimage').width(),
				minWidth:0,
				minHeight:0,
				maxHeight:$('#usermsgimage').height(), 
				selectionOpacity: 0.2,
				aspectRatio: '1:1',
				instance:true,
				autoHide:true,
				onSelectChange: processImg 
			});
		},500); */
		
		 $('#previewPic').removeAttr("style");
		 $("#startX").val("");
		 $("#startY").val("");
		 $("#width").val("");
		 $("#height").val("");
	}
</script>
</head>

<body class="pt-body">
<div class="mainscc" >
<a name="uptops"></a>
<div class="worksBox">
	<div class="pt-tfg2" id="titgd4" style="z-index:30000em; position:absolute; background: #a8d6e2;height: 15px;"></div>
    <div class="pt-tfg2" style="z-index:30000em; background: #a8d6e2;height: 15px;" ></div>
    <!---左边菜单 begin--->
    <div class="pt-left" id="fixed" style="position:absolute;">
    	<!---头像 begin--->
    	<div class="pt-left-user" align="center" id="leftUserInfo">
        	<div class="man"></div><!---默认男性头像-->
            <!--默认女性头像--<div class="girl"></div>--->
            <p align="center"><a href="#1" class="user-msg" onclick="showUserInfo()"><label>张小明</label><img src="/pages/images/platform/upda.png"></a></p>
        </div>
        <!---头像 end--->
        
        <!---左边菜单 begin--->
        <div class="pt-menu">
        	<ul>
            	<li><a href="#uptops" onClick="ptmenu('ptmu',0,4)" id="ptmu_title_0" class="current"><span class="ptm1-1" id="ptm1-1"></span><i class="dd dn" id="my1"></i>我的消息</a></li>
                <li><a href="#uptops" onClick="ptmenu('ptmu',1,4)" id="ptmu_title_1"><span class="ptm3-2" id="ptm2-1"></span><i class="dd dn " id="my2"></i>同事圈</a></li>
                <li><a href="#uptops" onClick="ptmenu('ptmu',2,4)" id="ptmu_title_2"><span class="ptm4-2" id="ptm3-1"></span><i class="dd dn" id="my3"></i>快捷社区</a></li>
                <li><a href="#uptops" onClick="ptmenu('ptmu',3,4)" id="ptmu_title_3"><span class="ptm5-2" id="ptm4-1"></span>推荐分享</a></li>
            </ul>
        </div>
        <!---左边菜单 end--->

    </div>
    <!---左边菜单 end--->
    
    <!---中间内容模块 beign--->
    <div class="pt-center" id="pt-center" style="margin-left: 179px;">
    	
        <!---我的消息 begin--->
    	<div class="mymsg ptc" id="ptmu_main_0">
        	<div class="mymsgtBox mymsgTitle"  style="position: absolute;z-index: 999px;" id="titgd2">
            	<ul>
                	<li><a href="#1" onClick="tabpt('msg',0,2)" id="msg_title_0" class="current">应用消息</a></li>
                    <!-- <li><a href="#1" onClick="tabpt('msg',1,2)" id="msg_title_1">系统消息</a><span class="msg" id="xtmsg">12</span></li>-->
                    <li class="dn" id="msglist"><a href="#1" class="current">消息列表</a><i class="ptclose" onClick="removegzinfo()"></i></li>
                </ul>
            </div>
            <div style="height: 1px; width: 100%; margin-top: 35px;"></div>
            <!---应用消息begin--->
            <div class="yyBox ptc" id="msg_main_0">
            	 
                
              

            </div>
            <!---应用消息 end--->
            
            
            <!---系统消息begin--->
            <div class="yyBox ptc dn"  id="msg_main_1">
                
                <div class="yymsgList ptcs">
                	<div class="yyimgBox"><a href="#1"><img src="/pages/images/platform/tool/y3.png"></a></div>
                    <div class="yymsgBox">
                    	<p class="yyname"><span class="fz15"><a href="#1">系统管理</a></span><span class="msg2">3</span><i>三小时前</i></p>
                        <p class="plifo">系统管理应用已更新...功能强大，去看吧!!!</p>
                    </div>
                </div>
                
                <div class="yymsgList ptcs">
                	<div class="yyimgBox"><a href="#1"><img src="/pages/images/platform/tool/y3.png"></a></div>
                    <div class="yymsgBox">
                    	<p class="yyname"><span class="fz15"><a href="#1">系统管理</a></span><span class="msg2">3</span><i>三小时前</i></p>
                        <p class="plifo">系统管理应用已更新...功能强大，去看吧!!!</p>
                    </div>
                </div>

            </div>
            <!---系统消息 end--->
            
            <!---我的工资 begin--->
            <div class="yyBox ptc dn"  id="gzList">

            </div>
            <!---我的工资 end--->
            
        </div>
        <!---我的消息 end--->
        
        <!---同事圈 begin--->
    	<div class="mymsg ptc dn" id="ptmu_main_1">   
        	<div class="mymsgtBox mymsgTitle" style="position: absolute;z-index: 999px;" id="titgd">
            	<ul>
                	<li><a href="#1" onClick="tabmsg('tsq',0,3)" id="tsq_title_0" class="current">同事圈</a></li>
                    <li><a href="#1" onClick="tabmsg('tsq',1,3)" id="tsq_title_1">我的同事</a></li>
                    <li><a href="#1" onClick="tabmsg('tsq',2,3)" id="tsq_title_2">邀请同事</a></li>
                </ul>
        	</div>
            <div style="height: 1px; width: 100%; margin-top: 35px;"></div>
            <!--同事圈内容 begin-->
            <div class="kjsqdtBox" id="tsq_main_0">
            	
                <!--说说模块 begin-->
            	<div class="tsqBox">
                	
                    <div class="shuoBox">
                    	<div class="shuoleft"><textarea class="shuotext" placeholder="随便说说！" id="sayContent"></textarea></div>
                        <div class="shuoright"><input type="file" name="filedata" class="sfileimg" id="sayInputImages_0" onchange="sayImageChange(this);"></div>
                    </div>
                    
                    <!--指定范围模块 begin-->
                    <div class="tsqBox dn" id="tsqBoxdd">
                    	
                        <div class="shuotool"> 
                        <div style='display:inline;height:35px;' id='loadfilediv'><p style="width:100px; margin-left:4px;line-height:29px;" class="fl"><a href="javascript:;" style=" color:#0198d1;" >上传单个附件...</a><input title="仅支持上传单个附件(附件大小不能超过40M)" type="file" name="filedata" id="sayfile_id" class="filebut" onchange="sayFileChange();"></p></div>
                    	 
                           <!--<a href="#1" class="ait" onClick="gettslistinfo()"></a>  -->
                            <input type="button" class="fabiao" onClick="sayRelease()" value="发表">
                            <p class="kejian">可见范围：<span class="kejians">所有人可见</span><i class="kei" onClick="getkejianfw()"></i>
                                <div class="kejianfw dn" id="kejianfw">
                                    <span class="kejiannr">所有人可见</span>
                                    <span class="kejiannr">指定的同事</span>
                                    <span class="kejiannr">仅自己可见</span>
                                </div>			  
                            </p>
                        </div>
                        
                        <!--上传图片显示区域 begin-->
                        <div class="shuoimgBox dn" id="sayImages">
                        	 
                        </div>
                        <!--上传图片显示区域 end-->
                          <!--上传文件显示区域 begin-->
                       <div style='display:none;height:35px;' class="shuoimgBox1 dn"  id='sayFileDiv'>
		                       <span id='sayfileinfo' class="fl" style=" line-height:29px;"></span>
		                       <a href="javascript:;" class="delete ml10" style="color:#0198d1" title="删除" onclick="clearSayfile()"></a>
                       </div>
                        <!--上传文件显示区域 end-->
                         <!--同事列表 begin-->
                        <div class="zhidinBox tsqBox dn" id="tslistBox">
                        	<div class="tongsBox">
                            	<ul class="tslistul"></ul>
                        	</div>
                            <div class="tongsbutBox">
                            	<p>您可以在下面选择添加最多1000位好友</p>
                            	 <input type="button" value="清空" class="fabiao" onClick="cancelFrendForSay();">
                                <input type="button" value="添加" class="fabiao" onClick="addFrendForSay()">
                            </div>
                            <div class="tongsinfoBox">
                            	<div class="tsleft">
                                	<div class="serachts"><input type="text" class="tssertext" placeholder="搜索同事" id="searchtsKejianinput"><a href="#1" class="serabut" onclick="searchKeJianUserForName();"></a></div>
                                    <div class="zzjgtsBoxs" style="width: 135px;height: 240px;overflow: auto;"><ul id="tsqKeJianTree" class="ztree"></ul></div>
                                </div>
                                <div class="closetsbox"><a href="#1" onClick="hidetslistinfo()"  class="closexxts"></a></div>
                                <div class="tsright">
                                	<ul id="appoint_user">                                   	
                                   </ul>
                                </div>
                            </div>
                        </div>
                        <!--同事列表 end-->
                        
                    </div>
                    <!--指定范围模块 end-->
                    
                </div>
                <!--说说模块 end-->
                
                <!--同事圈列表内容 begin-->
                <div class="tsqmainBox">
                          <!--   <p class="wddtbox" ><span id="tsq_dynamic_title" class="qudtinfo">全部动态</span><i class="keis"  onMouseover="getkejianfws()"></i>
                                <div class="medtBox dn" id="kejianfws">
                                    <span class="qudtme"> <i class="dd dn" id="myall"></i>全部动态</span>
                                    <span class="qudtme"> <i class="dd dn" id="myme"></i>与我相关</span>
                                    <span class="qudtme">我的说说</span>
                                </div>			  
                            </p> -->
                  <div class="sayitle">
            	<ul>
                	<li><i class="dd2" id="myall" ></i><a href="#1" onClick="sayitleFunction(1,3)" id="sayitle_1"  class="current">全部动态</a> </li>
                    <li><i class="dd2"  id="myme"></i><a href="#1" onClick="sayitleFunction(2,3)" id="sayitle_2" >与我相关</a></li>
                    <li><a href="#1" onClick="sayitleFunction(3,3)" id="sayitle_3">我的说说</a></li>
                </ul>
        	</div>
                            <a class="renew" onclick="tsqDTRefresh()"></a>
                </div>
                
                <div class="tsqnrinfoBox tsqBox" id="tsqdt_hua"></div>
                <div id="tsqloading"></div>
                <!--同事圈列表内容 end-->
                
            </div> 
            <!--同事圈内容 end-->  
            
            <!--我的同事 begin-->
            <div class="kjsqdtBox dn" id="tsq_main_1">
            
            <div class="mytslistBox">
				<div class="tslefts">
					<div class="serachts"><input type="text" id='searchMytsinput' class="tssertext" placeholder="搜索同事"><a href="#1" class="serabut" onclick="searchUserForName();"></a></div>
					<div class="zzjgtsBoxs"><ul id="tsqtreeDemo" class="ztree" style="margin-top:0;"></ul></div>
				</div>

				<div class="tsrights">
					<ul id="mytsq_control"></ul>
				</div>
            </div>
            
            </div> 
            <!--我的同事 end--> 
            
            <!--邀请同事 begin-->
            <div class="kjsqdtBox dn" id="tsq_main_2">
            	<table width="100%" class="ggtab ml30 fl mt10" cellpadding="0" cellspacing="0">
                	<tr height="40">
                    	<td><p class="lsmsg" style=" margin-left:3px;">您可以邀请您的同事加入贵公司快捷管家信息互动平台</p></td>
                    </tr>
                    <tr height="50">
                        <td>
                        <input type="text" class="tjfxtext" placeholder="您要邀请的同事手机号或邮箱号" id="invitationInputAddress" onblur="invitationInputAddress_onblur()"><p style="color:red;font-size: 12px;"  id="invitationInputAddressMsg"></p>
						</td>
                    </tr>
                    <tr height="50">
                        <td><input type="text" class="tjfxtext" placeholder="您要邀请的同事姓名" id="invitationInputName" onblur="invitationInputName_onblur()"><p style="color:red;font-size: 12px;" id="invitationInputNameMsg"></p></td>
                    </tr>
                    <tr height="80">
                        <td>
                        <input type="button" class="pttcBut85 ml5"  value="发送" onclick="invitationTs()">
               	    	<input type="reset" class="pttcButh85 ml30" value="重置" onclick="invitationReset()">
                        </td>
                    </tr>
                </table>
            </div>
            <!--邀请同事 begin-->
            
        </div>
        <!---同事圈 end--->
        
        
        <!---快捷社区 begin--->
    	<div class="mymsg ptc dn" id="ptmu_main_2">   
             <div class="mymsgtBox mymsgTitle"  style="position: absolute;z-index: 999px;" id="titgd3">
            	<ul>
                	<li><a href="#1" onClick="tabmsg('kjsq',0,3)" id="kjsq_title_0" class="current">全部动态</a></li>
                    <li id="hidefilt">
                    	<a href="#1" onClick="tabmsg('kjsq',1,3)" id="kjsq_title_1">我的帖子</a><i class="filter dn" onMouseMove="getfilter()"></i>
                        <div class="filterBox dn" id="filterBox">
                        	<span class="mefilter">我发起的</span>
                            <span class="mefilter">我评论的</span>
                            <span class="mefilter">我的帖子</span>
                        </div>
                    </li>
                    <li><a href="#1" onClick="tabmsg('kjsq',2,3)" id="kjsq_title_2">反馈需求</a></li>
                </ul>
             </div>
            <div style="height: 1px; width: 100%; margin-top: 35px;"></div>
            <!--全部动态 begin-->
            <div class="kjsqdtBox" id="kjsq_main_0">
            	<!-- 
                <div class="yymsgList ptcs">
                	<div class="yyimgBox"><img src="/pages/images/platform/pax.png"></div>
                    <div class="yymsgBox">
                    	<p class="yyname"><span>李晓贤</span><i>一小时前</i></p>
                        <p>快过年了，回家后记得找个路口，点燃一只炮仗后大喊“不要杀我！”这样是不是有过节的气氛[哈哈]<a href="#1">@张小明</a>人生如戏，全靠演技啊！（图文来自网络）</p>
                    </div>
                    <div class="zplun"><a href="#1" class="gga" onClick="getRevie(0)">评论(<span>12</span>)</a><a href="#1" class="zans">(<span>12</span>)</a></div>
                    <div class="plunBox ptc1 dn" id="revie-main0">
                    	<div class="plb1"></div>
                        <div class="plb2">
                        	<div class="pltx"><img src="/pages/images/platform/12.jpg"></div>
                            <div class="plltext"><input type="text" class="pltext" placeholder="请输入评论内容..."></div>
                            <div class="checkbut">
                            	<p class="plzf"><input type="checkbox" id="check1"><label for="check1">评论后转发</label></p>
                                <input type="button" value="评论" class="comment fr mt10" onClick="getRevie(0)"> 
                            </div>
                            <div class="pllist checkbut">
                            	<p><a href="#1" title="回复">张小明：</a>不如我们过年回家的时候也那样玩下[阴险][阴险]</p>
                                <p><a href="#1" title="回复">李小贤</a>回复<a href="#1">@张小明：</a>人生如戏，全靠演技啊！</p>
                            </div>
                        </div>
                        <div class="plb3"></div>
                    </div>
                </div>
                
                <div class="yymsgList ptcs">
                	<div class="yyimgBox"><img src="/pages/images/platform/pax.png"></div>
                    <div class="yymsgBox">
                    	<p class="yyname"><span>李晓贤</span><i>一小时前</i></p>
                        <p>快过年了，回家后记得找个路口，点燃一只炮仗后大喊“不要杀我！”这样是不是有过节的气氛[哈哈]<a href="#1">@张小明</a>人生如戏，全靠演技啊！（图文来自网络）</p>
                    </div>
                    <div class="zplun"><a href="#1" class="gga" onClick="getRevie(1)">评论(<span>12</span>)</a><a href="#1" class="zans">(<span>12</span>)</a></div>
                    <div class="plunBox ptc1 dn" id="revie-main1">
                    	<div class="plb1"></div>
                        <div class="plb2">
                        	<div class="pltx"><img src="/pages/images/platform/12.jpg"></div>
                            <div class="plltext"><input type="text" class="pltext" placeholder="请输入评论内容..."></div>
                            <div class="checkbut">
                            	<p class="plzf"><input type="checkbox" id="check2"><label for="check2">评论后转发</label></p>
                                <input type="button" value="评论" class="comment fr mt10" onClick="getRevie(1)"> 
                            </div>
                            <div class="pllist checkbut">
                            	<p><a href="#1" title="回复">张小明：</a>不如我们过年回家的时候也那样玩下[阴险][阴险]</p>
                                <p><a href="#1" title="回复">李小贤</a>回复<a href="#1">@张小明：</a>人生如戏，全靠演技啊！</p>
                            </div>
                        </div>
                        <div class="plb3"></div>
                    </div>
                </div>
                
                
                <div class="loading"><a href="#1">查看更多...</a></div>
                --> 
                <p align="center" class="yyname" style="padding-top:50px;">网站完善中,敬请期待!</p>
            </div>
            <!--全部动态 end-->
            
            <!--我的帖子 begin-->
            <div class="kjsqmeBox dn" id="kjsq_main_1">
            <!-- 
            	<div class="yymsgList ptcs">
                	<div class="yyimgBox"><img src="/pages/images/platform/us1.png"></div>
                    <div class="yymsgBox">
                    	<p class="yyname"><span>李晓贤</span><i>一小时前</i></p>
                        <p>快过年了，回家后记得找个路口，点燃一只炮仗后大喊“不要杀我！”这样是不是有过节的气氛[哈哈]<a href="#1">@张小明</a>人生如戏，全靠演技啊！（图文来自网络）</p>
                    </div>
                    <div class="zplun"><a href="#1" class="gga" onClick="getRevies(0)">评论(<span>12</span>)</a><a href="#1" class="zans">(<span>12</span>)</a></div>
                    <div class="plunBox ptc1 dn" id="revies-main0">
                    	<div class="plb1"></div>
                        <div class="plb2">
                            <div class="pllist checkbut">
                            	<p><a href="#1" title="回复">张小明：</a>不如我们过年回家的时候也那样玩下[阴险][阴险]</p>
                                <p><a href="#1" title="回复">李小贤</a>回复<a href="#1">@张小明：</a>人生如戏，全靠演技啊！</p>
                            </div>
                        </div>
                        <div class="plb3"></div>
                    </div>
                </div>
                
                <div class="yymsgList ptcs">
                	<div class="yyimgBox"><img src="/pages/images/platform/us1.png"></div>
                    <div class="yymsgBox">
                    	<p class="yyname"><span>李晓贤</span><i>一小时前</i></p>
                        <p>快过年了，回家后记得找个路口，点燃一只炮仗后大喊“不要杀我！”这样是不是有过节的气氛[哈哈]<a href="#1">@张小明</a>人生如戏，全靠演技啊！（图文来自网络）</p>
                    </div>
                    <div class="zplun"><a href="#1" class="gga" onClick="getRevies(1)">评论(<span>12</span>)</a><a href="#1" class="zans">(<span>12</span>)</a></div>
                    <div class="plunBox ptc1 dn" id="revies-main1">
                    	<div class="plb1"></div>
                        <div class="plb2">
                            <div class="pllist checkbut">
                            	<p><a href="#1" title="回复">张小明：</a>不如我们过年回家的时候也那样玩下[阴险][阴险]</p>
                                <p><a href="#1" title="回复">李小贤</a>回复<a href="#1">@张小明：</a>人生如戏，全靠演技啊！</p>
                            </div>
                        </div>
                        <div class="plb3"></div>
                    </div>
                </div>
                
                
                <div class="loading"><a href="#1">查看更多...</a></div>
            -->
            <p align="center" class="yyname" style="padding-top:50px;">网站完善中,敬请期待!</p>
            </div>
            <!--我的帖子 end-->
            
            <!--反馈需求 begin-->
            <div class="kjsqxqBox dn" id="kjsq_main_2">
            <!-- 
            	<div class="tplxBox" style=" margin:0px auto; margin-top:15px; width:510px;">
            		<ul>
            			<li><a href="#1" onclick="tab('fkxq',0,3)" id="fkxq_title_0" class="current" title="新的需求" >新的需求</a></li>
            			<li><a href="#1" onclick="tab('fkxq',1,3)" id="fkxq_title_1" title="改善意见" >改善意见</a></li>
            			<li><a href="#1" onclick="tab('fkxq',2,3)" id="fkxq_title_2" title="其他反馈" >其他反馈</a></li>
            		</ul>
            	</div>
                <div class="fkxqBox" id="fkxq_main_0">
                <table width="100%" class="ggtab mr15" cellpadding="0" cellspacing="0">
                    <tr height="73">
                        <td width="80"><label>需求类型：</label></td>
                        <td>
                        <select class="xqselect">
							<option>人事管理</option>
							<option>我的工资</option>
							<option>系统管理</option>
                            <option>公告管理</option>
						</select><i class="errmsg15">提示错误信息</i>
						</td>
                    </tr>
                    <tr valign="top">
                        <td><label>需求描述：</label></td>
                        <td><textarea class="xqtexta" placeholder="亲爱的用户，您在管理中如有企业内或企业间信息互动的需求，让我们来帮助您实现！"></textarea><p class="errormsg2">请输入整数型</p></td>
                    </tr>
                </table>
                </div>
                <div class="fkxqBox dn" id="fkxq_main_1">
                <table width="100%" class="ggtab mr15" cellpadding="0" cellspacing="0">
                    <tr height="73">
                        <td width="80"><label>应用名称：</label></td>
                        <td>
                        <select class="xqselect">
							<option>人事管理</option>
							<option>我的工资</option>
							<option>系统管理</option>
                            <option>公告管理</option>
						</select><i class="errmsg15">提示错误信息</i>
						</td>
                    </tr>
                    <tr width="80" valign="top">
                        <td><label>改善意见：</label></td>
                        <td><textarea class="xqtexta" placeholder="亲爱的用户，您对我们已发布的应用有好的改善意见，敬请提出！"></textarea><p class="errormsg2">请输入整数型</p></td>
                    </tr>
                </table>
                </div>
                <div class="fkxqBox dn" id="fkxq_main_2">
                <table width="100%" class="ggtab mr15"  style=" margin-top:23px;" cellpadding="0" cellspacing="0">
                    <tr valign="top">
                        <td width="80"><label>其他反馈：</label></td>
                        <td><textarea class="xqtexta" placeholder="亲爱的用户，您还有其他什么想和大家分享的，欢迎畅所欲言！"></textarea><p class="errormsg2">请输入整数型</p></td>
                    </tr>
                </table>
                </div>
                <div class="fkxqBox mt20">
                <table width="100%" class="ggtab mr15" cellpadding="0" cellspacing="0">
                    <tr height="73">
                        <td width="80" valign="top"><label style="margin-top:5px;">上传图片：</label></td>
                        <td>
                        <p><input type="text" placeholder="请输入投票选项" class="xqsctext2">
						<a style="color:#0198d1;" href="#1">重新上传...</a><input title="重新上传..." type="file" class="filebut"></p>
                        <div class="xqaddimg" ><img src="/pages/images/platform/12.jpg"><span class="closes" title="删除图片"></span></div>
						</td>
                    </tr>
                    <tr valign="top">
                        <td><label>联系方式：</label></td>
                        <td><input type="text" class="xqsctext" placeholder="您的QQ、Email或手机号"><p class="errormsg2">请输入整数型</p></td>
                    </tr>
                    <tr height="60">
                    	<td></td>
                        <td>
                        	<p class="ggtpbox fl"><input type="checkbox" style="margin-top:10px; float:left;" id="dstime3"><label for="dstime3">发布到快捷社区</label></p>
						</td>
                    </tr>
                    <tr>
                    	<td></td>
                        <td>
                        <input type="button" class="pttcBut85 ml5"  value="确认">
               	    	<input type="button" class="pttcButh85 ml30" value="取消">
                        </td>
                    </tr>
                </table>
                </div>
                -->
                <p align="center" class="yyname" style="padding-top:50px;">网站完善中,敬请期待!</p>
            </div>
            <!--反馈需求 end-->
        </div>
        <!---快捷社区 end--->
        
        
        <!---推荐分享 begin--->
    	<div class="mymsg ptc dn" id="ptmu_main_3">  
    	 
        	<div class="mymsgtBox mymsgTitle" style="position: absolute; top: 12px">
            	<ul>
                	<li><a href="#1" onClick="tabmsg('tjfx',0,1)" id="tjfx_title_0" class="current">推荐分享</a></li>
                </ul>
            </div>

            <div class="kjsqdtBox" id="tjfx_main_0" style="padding-top:50px;position: relative;">
            	<table width="100%" class="ggtab ml30 fl mt10" cellpadding="0" cellspacing="0">
                	<tr height="40">
                    	<td><p class="lsmsg" style=" margin-left:3px;">如果您觉得“快捷管家”信息互动平台对您朋友可能有帮助，请推荐：</p></td>
                    </tr>
                    <tr height="60">
                        <td valign="top">
                        <input type="text" id="tuijianem" class="tjfxtext" placeholder="您要推荐的朋友手机号或邮箱号" onblur="recommend.checkRecommendem()"><p class="errormsg2" style="color:#808080;" id="tuijian_em">本邀请由快捷管家后台发送短信或邮件</p><p class="errormsg2 dn" id="tuijian_emMsg"></p>
						</td>
                    </tr>
                    <tr height="80" valign="top">
                        <td><textarea class="tjfxtexta" id="tuijianly" placeholder="给朋友留言..." onblur="recommend.checkRecommendLiu()"></textarea><p class="errormsg2 dn" id="tuijian_ly_msg"></p></td>
                    </tr>
                    <tr height="50">
                        <td>
                        <input type="button" class="pttcBut85 ml5"  value="发送" onclick="recommend.RecommendSubmit()">
               	    	<input type="button" class="pttcButh85 ml30" value="重置" onclick="recommend.clear()">
                        </td>
                    </tr>
                </table>
            </div>

<!-- <p align="center" class="yyname" style="padding-top:50px;">网站完善中,敬请期待!</p> -->
        </div>
        <!---推荐分享 end--->
        
        <!---个人中心 begin--->
    	<div class="mymsg ptc df" id="ptmu_main_4" style="display:none">   
        	<div class="mymsgtBox mymsgTitle" style="position: absolute;z-index: 999px;">
            	<ul>
                	<li><a href="#1" class="current">个人中心</a></li>
                </ul>
            </div>
			<div style="height: 1px; width: 100%; margin-top: 35px;"></div>
			<div class="tplxBox" style=" width:510px; margin:0px auto; margin-top:15px;">
            	<ul>
            		<li><a href="#1" onclick="tabs('rcxs',0,3)" id="rcxs_title_0" class="current" title="基本信息" >基本信息</a></li>
            		<li><a href="#1" onclick="tabs('rcxs',1,3)" id="rcxs_title_1" title="其他信息" >其他信息</a></li>
            		<li><a href="#1" onclick="tabs('rcxs',2,3)" id="rcxs_title_2" title="修改密码" >修改密码</a></li>
            	</ul>
            </div>
            
            <div class="grzxnBox" id="rcxs_main_0">
				<div class="usermsgBoxone" id="usermsgsBox">
                    		<input type="hidden" id="regtype" value="" />
                            <table width="100%" class="mt10" cellpadding="0" cellspacing="0">
                                <tr height="30" id="username">
                                    <td align="right" width="80" class="name"><label>姓名：</label></td>
                                    <td width="265"><p>张小明</p></td>
                                    <td></td>
                                    <td></td>
                                    <td align="right" rowspan="3"><div class="userimg"><a href="#1" class="userer" onclick="showHeadImg()"></a><img src="../images/platform/userimg.png"></div></td>
                                </tr>
                                <tr height="26" id="sex">
                                    <td align="right" width="80"><label>性别：</label></td>
                                    <td><p class="usersex"><span><input type="radio" value="1" name="rad1" />男</span><span><input type="radio" value="0" name="rad1" />女</span></p></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr height="45" id="mobile">
                                    <td align="right" width="80"><label>手机：</label></td>
                                    <td><input type="text" class="usermsgtext" readonly="readonly"></td>
                                    <td></td>
                                </tr>
                                <!-- <tr height="45">
                                    <td align="right" width="80"></td>
                                    <td><input type="text" class="usermsgtext" placeholder="请输入收到的短信验证码"></td>
                                     <td colspan="3">
                                    <input type="button" class="btnyanzheng" value="验证">
                                    </td>
                                </tr> -->
                                <tr height="45" id="email">
                                    <td align="right" width="80"><label>邮箱：</label></td>
                                    <td><input type="text" class="usermsgtext" readonly="readonly"></td>
                                    <td></td>
                                </tr>
                                <!-- <tr height="45">
                                    <td align="right" width="80"></td>
                                    <td><input type="text" class="usermsgtext" placeholder="请输入收到的短信验证码"></td>
                                     <td colspan="3">
                                    <input type="button" class="btnyanzheng" value="验证">
                                    </td>
                                </tr> -->
                                <tr height="45" id="phone">
                                    <td align="right" width="80"><label>工作电话：</label></td>
                                    <td><input type="text" class="usermsgtext" style="width:60px;">-<input type="text" class="usermsgtext" style="width:187px;"></td>
                                    <td colspan="3"></td>
                                </tr>
                                <tr height="45" id="address">
                                    <td align="right" width="80"><label>办公地址：</label></td>
                                    <td><textarea class="usermsgtexta"></textarea></td>
                                    <td colspan="3"></td>
                                </tr>
                                <tr height="70">
                                    <td></td>
                                    <td colspan="4">
                                        <input type="button" class="pttcBut85"  value="确认" onclick="updateUserInfo()" />
                                        <input type="button" class="pttcButh85 ml30" value="取消" onclick="ptmenu('ptmu',0,4);">
                                    </td>
                                </tr>
                            </table>
                    </div>
                    
					<div class="headsetBoxone dn" id="userfileimgBox">
                         <div class="topBox" style="margin-top:15px;">
                             <div><input type="button" class="btnselect" value="选择照片"><input type="file" id="headpic" name="headpic" class="fileuserimg" onchange="picChange()"></div>
                         </div>
                         <div class="centerBox">
                            <div class="usermsgimage">
                            	<div id="usermsgimage"></div>
                            </div>
                            <input type="hidden" id="startX" value="" />
                            <input type="hidden" id="startY" value="" />
                            <input type="hidden" id="width" value="" />
                            <input type="hidden" id="height" value="" />
                            <div class="rightBox">
                                <div>头像预览</div>
                                <div class="userheadimg"><img id="previewPic" src="../images/platform/headpic.png"></div>
                            </div>
                         </div>
                     
                         <div class="bottomBox">
                             <div>
                                  <input type="button" class="pttcBut85 butuserimg" onclick="showBasicInfo()" value="确认">
                                  <input type="button" class="pttcButh85 ml30 butuserimg" onclick="cancelPicUpdate()" value="取消">
                             </div>
                         </div>
                    </div>
			</div>
            <div class="grzxnBox dn" id="rcxs_main_1">
            <table width="100%" class="ml15" id="otherInfos">
                        	<tr height="28">
                            	<td><p>用户账户：<label>01356ipav</label></p></td>
                            </tr>
                            <tr height="28">
                            	<td><p>部门：<label>技术部</label></p></td>
                            </tr>
                           	<tr height="28">
                            	<td><p>职务：<label>java开发</label></p></td>
                            </tr>
                            <tr height="28">
                            	<td><p>是否是部门最高职务：<label>否</label></p></td>
                            </tr>
                             <tr height="28">
                            	<td><p>兼职部门及职务：<label>无</label></p></td>
                            </tr>
                             <tr height="28">
                            	<td><p>所属工作平台：<label>上海</label></p></td>
                            </tr>
                        </table>
            </div>
            <div class="grzxnBox dn" id="rcxs_main_2">
            	<table width="100%" class="mt10" cellpadding="0" cellspacing="0">
                                <tr height="45">
                                    <td align="right" width="80"><label>原密码：</label></td>
                                    <td><input type="password" class="grzxnBoxtext" placeholder="请输入当前账号密码"></td>
                                    <td></td>
                                </tr>
                                <tr height="45">
                                    <td align="right" width="80"><label>新密码：</label></td>
                                    <td><input type="password" class="grzxnBoxtext" placeholder="请输入6-16位数字或英文密码"></td>
                                    <td><span class="pasdi xin" style="font-size:11px;">6-16位字符密码，只可输入英文或数字！</span></td>
                                </tr>
                                <tr height="45">
                                    <td align="right" width="80"><label>重复新密码：</label></td>
                  	                  <td><input type="password" class="grzxnBoxtext" placeholder="请重新输入新密码"></td>
                                    <td></td>
                                </tr>
                                <tr height="70">
                                    <td></td>
                                    <td>
                                        <input type="button" class="pttcBut85"  value="确认" onclick="updatepwd();">
                                        <input type="button" class="pttcButh85 ml30" value="取消" onclick="clearpwd();">
                                    </td>
                                </tr>
                            </table>
            </div>

        </div>
        <!---个人中心 end--->  
        
	</div>
    <!---中间内容模块 end--->
    
    
    <!---右边应用中心 begin--->
    <div class="toolrtBox" style="margin-left: 730px;position:absolute;" id="fixed2">
    	<div class="toolrtTitle mymsgTitle">
            	<ul>
                	<li><a href="#1" style="text-align:left; text-indent:12px;">应用中心</a></li>
                </ul>
        </div>
        <div class="toollistBox too">
                <div  id="app_system"  class="subNav currentDt"onclick="showMenus(this,1)">系统应用</div>  
		        	<div  id="system_menus" class="toolinfo " style="display:none;"></div>              
                <div  class="subNav currentDt" onclick="showMenus(this,2)">企业应用</div>
                	<div  id="company_menus"  class="toolinfo" style="display:none;"></div>
				<div  class="subNav currentDt" onclick="showMenus(this,3)">个人应用</div>
                	<div  id="user_menus"  class="toolinfo" style="display:none;"></div>
        </div>
    </div>
    <!---右边应用中心 end--->
    <a href="#uptops" class="uptop" title="回顶部"></a>
</div>
</div>




<!---版权 begin--->
<!-- <div class="foot" align="center">
	<div class="footbox"> 
	<p align="center">
    	<ol class="footol">
        	<li><a href="#1">关于快捷管家</a></li>
            <li><span>|</span></li>
            <li><a href="#1">服务协议</a></li>
            <li><span>|</span></li>
            <li><a href="#1">客户端下载</a></li>
            <li><span>|</span></li>
            <li><a href="#1">意见反馈</a></li>
            <li><span>|</span></li>
            <li><a href="#1">帮助中心</a></li>
            <li><span>|</span></li>
            <li><a href="#1">联系我们</a></li>
        </ol>
    </p>
    <p>上海天道启科电子有限公司   ©版权所有   沪ICP备10000764号</p>
    </div>
</div> -->
<!---版权 end--->


<!--simTestContent1 begin-->
<div id="simScrollCont12" class="simScrollCont">
</div>
	<div class="simScrollContBox4" id="simScrollContBox12">
    	<div class="simScrollContBoxs4">
        <div class="tctitle">提示信息<a href="#1" onClick="hidejg12()" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        <p class="alertmsg" id="msg_info"></p>
<!--         	<p class="alertmsg">您好，如需使用此模块请联系管理员！</p> -->
        </div>
        <div class="alertbut">
        	<input type="button" value="确定" class="yes fr mr20" onClick="hidejg12()">
        </div>

        </div>
    </div>
  
<!--simTestContent1 end-->

<!--弹出提示 begin-->
<div id="simalpha3" class="simalpha">
</div>
	<div class="simaltBox3" id="simaltBox3">
    	<div class="simaltBoxs3">
        <div class="tctitle">提示信息<a href="#1" title="关闭"></a></div>
        
      <div class="ptx"></div>
        <div id="tbBoxgdt2" class="alertBox">
        	<p class="alertmsg">XXX，你即将修改XXXX的个人信息，请慎重修改，不然你就联系不到他了！</p>
        </div>
        <div class="alertbut">
            <input type="button" value="否" class="no fr mr20">
        	<input type="button" value="是" class="yes fr mr20">
        </div>

        </div>
    </div> 
<!--弹出提示 end-->
<!-- 说说图片遮罩层 -->
<!-- <div id='fatherMask' class='uphotobg'></div> -->
<!-- 普通遮罩层-->
<!-- <div id='fatherScrollCont' class='simScrollCont' ></div> -->

<script type="text/javascript">

$(document).ready(function() {	
	
	t = $('#fixed').offset().top;
	mh = $('.main').height();
	fh = $('#fixed').height();
	$(".mainscc").scroll(function(){
		s = $(".mainscc").scrollTop();	
		if(s > t - 0){
			$('#fixed').css('position','fixed');
			$('#fixed').css('top',12+'px');	
			if(s + fh < mh){
			}				
		}else{
			$('#fixed').css('position','absolute');
		}
	})
	UnReadyMsgFunction();
});


$(document).ready(function() {	
	t = $('#fixed2').offset().top;
	mh = $('.main').height();
	fh = $('#fixed2').height();
	$(".mainscc").scroll(function(){
		s = $(".mainscc").scrollTop();	
		if(s > t - 0){
			$('#fixed2').css('position','fixed');
			$('#fixed2').css('top',12+'px');	
			if(s + fh > mh){
				
			}				
		}else{
			$('#fixed2').css('position','absolute');
		}
	})
});
$(document).ready(function() {	
	t = $('#titgd').offset().top;
	mh = $('.main').height();
	fh = $('#titgd').height();
	$(".mainscc").scroll(function(){
		s = $(".mainscc").scrollTop();	
		if(s > t - 0){
			$('#titgd').css('position','fixed');
			$('#titgd').css('top',12+'px');	
			if(s + fh > mh){
				
			}				
		}else{
			$('#titgd').css('position','absolute');
		}
	})
});
$(document).ready(function() {	
	t = $('#titgd2').offset().top;
	mh = $('.main').height();
	fh = $('#titgd2').height();
	$(".mainscc").scroll(function(){
		s = $(".mainscc").scrollTop();	
		if(s > t - 0){
			$('#titgd2').css('position','fixed');
			$('#titgd2').css('top',12+'px');	
			if(s + fh > mh){
				
			}				
		}else{
			$('#titgd2').css('position','absolute');
		}
	})
});
$(document).ready(function() {	
	t = $('#titgd3').offset().top;
	mh = $('.main').height();
	fh = $('#titgd3').height();
	$(".mainscc").scroll(function(){
		s = $(".mainscc").scrollTop();	
		if(s > t - 0){
			$('#titgd3').css('position','fixed');
			$('#titgd3').css('top',12+'px');	
			if(s + fh > mh){
				
			}				
		}else{
			$('#titgd3').css('position','absolute');
		}
	})
});
$(document).ready(function() {	
	t = $('#titgd4').offset().top;
	mh = $('.main').height();
	fh = $('#titgd4').height();
	$(".mainscc").scroll(function(){
		s = $(".mainscc").scrollTop();	
		if(s > t - 0){
			$('#titgd4').css('position','fixed');

			if(s + fh > mh){
				
			}				
		}else{
			$('#titgd4').css('position','absolute');
		}
	}) 
	if($($(window.top.document.body).find("#state")).val()=='Y'){
	  
	}

});
</script>
</body>
</html>

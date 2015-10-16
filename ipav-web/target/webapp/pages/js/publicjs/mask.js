/***首页普通遮罩显示***/
function homeOrdinaryShow(){
	var fatherptlfbg;
	fatherptlfbg =$(window.top.document.body).find("div#ptlfbg2");
	if(fatherptlfbg.length!=0){
		$(fatherptlfbg).show();
	}
	var fatherScrollConts;
	fatherScrollConts =$(window.top.document.body).find("iframe").contents().find("div#fatherScrollCont");
	if(fatherScrollConts.length==0)fatherScrollConts=$("#fatherScrollCont");
	$(fatherScrollConts).show();
	$(window.top.document.body).addClass("html-body-overflow");
}

/***首页普通遮罩隐藏***/
function homeOrdinaryHide(){
	var fatherptlfbg;
	fatherptlfbg =$(window.top.document.body).find("div#ptlfbg2");
	if(fatherptlfbg.length!=0){
		fatherptlfbg.hide();
	}
	var fatherScrollConts;
	fatherScrollConts =$(window.top.document.body).find("iframe").contents().find("div#fatherScrollCont");
	if(fatherScrollConts.length==0)fatherScrollConts=$("#fatherScrollCont");
	$(fatherScrollConts).hide();
	$(window.top.document.body).removeClass("html-body-overflow");
}


/***说说查看图片遮罩显示****/
function sagImgMaskShow(){
	var playbgheises =$(window.top.frames.document.body).find("iframe").contents().find("div#fatherMask");
	if(playbgheises.length==0)playbgheises=$("#fatherMask");
	$(playbgheises).show();	
	fatherptlfbg =$(window.top.document.body).find("div#ptlfbg")
	if(fatherptlfbg) $(fatherptlfbg).show();
	$(window.top.document.body).addClass("html-body-overflow");
}

/***说说查看图片遮罩隐藏****/
function sagImgMaskHide(){
	var playbgheises =$(window.top.frames.document.body).find("iframe").contents().find("div#fatherMask");
	if(playbgheises.length==0)playbgheises=$("#fatherMask");
	$(playbgheises).hide();	
	fatherptlfbg =$(window.top.document.body).find("div#ptlfbg")
	if(fatherptlfbg) $(fatherptlfbg).hide();
	$(window.top.document.body).removeClass("html-body-overflow");
}
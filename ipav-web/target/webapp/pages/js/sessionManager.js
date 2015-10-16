$.ajaxSetup({
	contentType:"application/x-www-form-urlencoded;charset=utf8",
	complete:function(XMLHttpRequest,textStatus){
		var warning=XMLHttpRequest.getResponseHeader("errmsg");
		if(warning==1)
			window.parent.location="/warningInfo?warninginfo="+warning;
	}
});
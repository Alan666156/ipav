function castVote(param){
	$.ajax({
		url:"castVote",
		type:"POST",
		data:param,
		dataType:"json",
		success:function(data){
			console.log(data);
			if(data!=undefined){
				if(data.error==undefined){
					self.location='voteCenter';
				}else{
					$("#warnning").html(data.error);
					showjg12();
				}
			}
		}
	});
}
var topDiv;
$(function(){
	topDiv=$(window.top.frames.document.children).find("iframe").contents().find("div#fatherScrollCont");
});
function showjg12(){
	topDiv.show();
	$(window.top.document.body).addClass("html-body-overflow");
	$("#simScrollCont12").show();
	$("#simScrollContBox12").show();	
}

function hidejg12(){
	topDiv.hide();
	$(window.top.document.body).removeClass("html-body-overflow");
	$("#simScrollContBox12").hide();
	$("#simScrollCont12").hide();
}
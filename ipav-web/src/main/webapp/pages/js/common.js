/***
 * 验证邮箱格式是否正确
 * @param str
 * @returns {Boolean}
 */
function checkEmail(str){
	var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
	if(re.test(str)){
		return true;
	}else{
		return false ;
	}
}
function checkMobile(str){
	 var  re = /^(13[0-9]{9}|14[5|7]\d{8}|15[0-3|5-9]\d{8}|17[0|6-8]\d{8}|18\d{9})$/;
	 if (re.test(str)) {
    	 return true;
     } else {
         return false ;
     }
}

function checkPwd(str){
//	var re = /^([a-zA-Z]{2}\d{4})$/;
	var re = /^[a-zA-Z|\d]{6,16}$/;//Gaoyang
	 if (re.test(str)) {
    	 return true;
     } else {
         return false ;
     }
}

jQuery.extend(jQuery.validator.messages, {
	required: "必输字段",
	remote: "请修正该字段",
	email: "请输入正确格式的电子邮件",
	url: "请输入合法的网址",
	date: "请输入合法的日期",
	dateISO: "请输入合法的日期 (ISO).",
	number: "请输入合法的数字",
	digits: "只能输入整数",
	creditcard: "请输入合法的信用卡号",
	equalTo: "请再次输入相同的值",
	accept: "请输入拥有合法后缀名的字符串",
	maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
	minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
	rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
	range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
	max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
	min: jQuery.validator.format("请输入一个最小为 {0} 的值")
	}); 

(function($) {
	jQuery.fn.extend({
				uploadPreview : function(opts) {
					opts = jQuery.extend({
						width : 0,
						height : 0,
						imgPreview : null,
						imgCutview : null,
						imgType : [ "gif", "jpeg", "jpg", "bmp", "png" ],
						callback : function() {
							return false;
						}
					}, opts || {});

					var _self = this;
					var _this = $(this);
					var imgPreview = $(opts.imgPreview);
					var imgCutview = $(opts.imgCutview);
					// 设置样式
					autoScaling = function() {
						imgPreview.css({
							"margin-left" : 0,
							"margin-top" : 0,
							"width" : opts.width,
							"height" : opts.height
						});
						imgPreview.show();
					}
					// file按钮出发事件
					_this.change(function() {
					if (this.value) {
						if (!RegExp(
								"\.(" + opts.imgType.join("|")
										+ ")$", "i").test(
								this.value.toLowerCase())) {
							alert("图片类型必须是"
									+ opts.imgType.join("，")
									+ "中的一种");
							this.value = "";
							return false;
						}
						if ($.browser.msie) {// 判断ie
							var path = $(this).val();
							if (/"\w\W"/.test(path)) {
								path = path.slice(1, -1);
							}
							imgPreview.attr("src", path);
							imgCutview.attr("src", path);
							imgPreview.css({
								"margin-left" : 0,
								"margin-top" : 0,
								"width" : opts.width,
								"height" : opts.height
							});
							setTimeout("autoScaling()", 100);
						} else {
							if ($.browser.version < 7) {
								imgPreview.attr('src', this.files
										.item(0).getAsDataURL());
								imgCutview.attr('src', this.files
										.item(0).getAsDataURL());
					}
else {
	oFReader = new FileReader(),
	rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
	oFReader.onload = function(oFREvent) {
			console.log(oFREvent.target.result);
			imgPreview.attr('src', oFREvent.target.result);
			imgCutview.attr('src', oFREvent.target.result);
		};
		var oFile = this.files[0];
		oFReader.readAsDataURL(oFile);
	}
	imgPreview.css({
			"margin-left" : 0,
			"margin-top" : 0,
			"width" : opts.width,
			"height" : opts.height
		});
	setTimeout("autoScaling()", 100);
	}
	}
	opts.callback();
		});
		}
	});
})(jQuery);

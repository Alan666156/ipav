/*
 * 设为收藏js
 * 
 */
function shoucang(){
	var ctrl = (navigator.userAgent.toLowerCase()).indexOf('mac') != -1 ? 'Command/Cmd' : 'CTRL'; 
	console.log(window.sidebar)
	if (document.all) { 
		window.external.addFavorite(window.location, window.name);
	} else if (window.sidebar) { 
		window.sidebar.addPanel(window.name, window.location, "");
	} else {
		alert('添加失败\n您可以尝试通过快捷键' + ctrl + ' + D 加入到收藏夹~'); 
	}
}
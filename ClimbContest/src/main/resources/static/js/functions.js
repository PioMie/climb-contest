function toggle(className) {
	var cells = document.getElementsByClassName(className);
	for (var i = 0; i < cells.length; i++) {
		cells[i].style.display = cells[i].style.display == "none" ? "table-cell"
				: "none";
	}
}

function nextPage(page) {
	switch (page) {
		case 'scoreCard': return 'PM';
		case 'PM': return 'TM';
		case 'TM': return 'LM';
		case 'LM': return 'WM';
		case 'WM': return 'JM'; 
		case 'JM': return 'TK'; 
		case 'TK': return 'LK';
		case 'LK': return 'WK';
		case 'WK': return 'JK';
		case 'JK': return 'PM';
	}
}

function go() {
	window.location.href += '?go=aye';
}


setTimeout(function(){
	if(window.location.search) {
		var prefix = window.location.href.substring(0, location.href.lastIndexOf('scoreCard') + 9);
		var sufix = window.location.href.substring(window.location.href.lastIndexOf('/') + 1, window.location.href.lastIndexOf('?'));
		window.location.replace(prefix + '/' + nextPage(sufix) + '?go=aye');
	}
},10000);
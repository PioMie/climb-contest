function toggle(className) {
	var cells = document.getElementsByClassName(className);
	for (var i = 0; i < cells.length; i++) {
		cells[i].style.display = cells[i].style.display == "none" ? "table-cell"
				: "none";
	}
}
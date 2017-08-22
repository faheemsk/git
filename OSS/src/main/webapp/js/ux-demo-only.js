// The scripts contained in this document are for mockup purposes only.
// These should nevever be used in a production environment.

function ux_selectTab( str_tabLabel ) {
	// 1. Remove currently selected.
	$(".ux-pnav li.ux-pnav-selected").removeClass("ux-pnav-selected");
	// 2. Add that class to the tab whose content matches str_tabLabel.
	var nothingPatt = /\bNothing\b/i;
	if( str_tabLabel == null || 
			str_tabLabel == "" || 
			nothingPatt.test(str_tabLabel)
		) {
		// alert("We don't make any menu look selected.");
		// Do nothing
	}
	else if($(".ux-pnav>li>a:contains('" + str_tabLabel + "')").length > 0) {
	  // alert( "It's in the visible set." );
		$(".ux-pnav>li>a:contains('" + str_tabLabel + "')").parent().addClass("ux-pnav-selected");
	}
	else {
		// alert("It's either under the More menu or it's not present, so we'll make the 2nd to last tab look like it.");
		var secondLast = $("ul.ux-pnav>li").length - 2;
		var secondLastMenuElement = $("ul.ux-pnav>li").get(secondLast);
		$(secondLastMenuElement).addClass("ux-pnav-selected");
		$("a", secondLastMenuElement).text( str_tabLabel );
	}
}

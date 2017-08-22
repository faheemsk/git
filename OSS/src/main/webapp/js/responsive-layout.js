/*

	Responsive Layout JavaScript
	Do not modify this file.
	Version 0.11

*/

function ResponsiveLayout(phoneLayout, tabletLayout, desktopLayout) {
	
	var start = new Date().getTime();

	// Store default tabs, vertical navigation, and form layouts so we can revert back on window resize
	var oldTabs = $(".ux-hnav").clone();
	oldTabs.removeClass("ux-hnav");
	var oldVertNav = $(".ux-vnav").clone();
	oldVertNav.removeClass(".ux-vnav");
	$(document).data('hnav', oldTabs);
	$(document).data('vnav', oldVertNav);
	var multiCols = $(".ux-tabl-form-multicol").clone();
	$(document).data('multicols', multiCols);
	
	
	function gbl_rlSupported() { return window.matchMedia; }; // Using matchMedia support as a proxy for media query support.
	
	function FormatMulticolForms() {
		$(".ux-tabl-form-multicol").each( function() {
			// We need to figure out whether the form fits as is.
			if (  $(this).outerWidth(true)  >  $(this).parent().innerWidth()  ) {
				// It doesn't fit. Find out which state we're in. Try stacking the labels on top of their elements.
				// First see if the labels are already stacking.
				// If they are stacked then there will be labels that are peers of inputs, selects, buttons, or fieldsets
				// TODO: rewrite this to be more robust
				var stacked = ( ($(this).find("label ~ select").length>0) || 
								($(this).find("label ~ input").length>0) ||
								($(this).find("label ~ button").length>0) ||
								($(this).find("label ~ fieldset").length>0)
								) ? true : false;
				// If they're not stacked then stack them.
				if (!stacked) {
					ConvertMulticolFormToStacked($(this));
				}
				// At this point the labels are stacked. See if it fits now.
				if (  $(this).outerWidth(true)  >  $(this).parents().innerWidth()  ) {
					// It doesn't fit. Try reducing the number of columns until you get to 1 or it does fit.
					var numColumns = $(this).find("tr").first().find("td").length;
					while ((  $(this).outerWidth(true)  >  $(this).parents(".ux-panl-content").innerWidth()  ) &&
						   ( numColumns > 1 )) {
						// Keep going until it fits or you cut it down to 1 column.
						var tds = $(this).find("td");
						--numColumns;
						$(this).find("tr").remove();
						// ...and build new ones.
						for (var i=0; i<tds.length; i++) {
							if (i%numColumns == 0) {
								// This would be the first cell in a new row, so create the new row first.
								if ($.trim($(tds[i]).text()) != "") {
									// Only do this if the td has some content other than whitespace
									var newRow = $(document.createElement("tr"));
									$(tds[i]).appendTo(newRow);
									$(newRow).appendTo(this);
								}								
							} else {
								// This is not the first cell in a new row, so add it to the last row in the table.
								$(tds[i]).appendTo($(this).find("tr").last());
							}
						}
					}
				}
			}
		});
	}
	
	function ConvertMulticolFormToStacked(t) {
		// Converts the usual multicolumn form layout tables (labels in the same row as their inputs/selects/etc but in different cells).
		// t is a jQuery form table object of class ux-tabl-form-multicol
		// Assumption: even columns (starting with the first) are label columns, odd columns are form field columns.
		$(t).find("tr").each( function () {
			var colsInRow = $(this).find("td");
			for (var i=0; i<colsInRow.length; i+=2) {
				// 
				var originalLabelCellContents = $(colsInRow[i]).html();
				var adjacentCellContents = $(colsInRow[i+1]).html();
				$(colsInRow[i]).html( originalLabelCellContents + adjacentCellContents );
				$(colsInRow[i+1]).remove();
			}								
		});
		// Give the multi-col form table the stacked class
		$(t).addClass("ux-tabl-form-vertical-labels");
	}
	
	function ConvertVerticalSecondaryNavToHorizontal() {
		//Check if secondary navigation is already enabled and page has vertical navigation to convert
		if (($(".ux-hnav.ux-hnav-has-horizontal-secondary").length == 0)&&($(".sec-hor-nav").length == 0)&&($(".ux-vnav").length == 1)){
			// Label the items that have tertiary nav
			$(".ux-vnav > li").each( function () {
				if ($(this).children("ul").length > 0) {
					$(this).addClass("ux-hnav-has-vertical-tertiary");	
				}
			});
			// Move this ul over to the horizontal navigation ul
			// First test to see if there's a selected tab
			if ($(".ux-hnav-selected").length > 0) {
				// There is a selected tab. Add the secondary nav to that tab.
				$(".ux-vnav").insertAfter($(".ux-hnav-selected a").first());
				// Remove its ux-vnav class
				$(".ux-vnav").removeClass("ux-vnav");
				// Replace the ux-vnav-selected class with ux-hnav-selected
				$(".ux-vnav-selected").addClass("ux-hnav-selected").removeClass("ux-vnav-selected");
				// Add .ux-hnav-has-horizontal-secondary to the primary nav
				$(".ux-hnav").addClass("ux-hnav-has-horizontal-secondary");
			} else {
				// There is no selected tab. Convert the secondary nav to horizontal secondary nav.
				$(".ux-vnav").removeClass().addClass("ux-hnav-secondary"); // Be sure to remove any width class first
				$(".ux-vnav-selected").addClass("ux-hnav-selected").removeClass("ux-vnav-selected");
			}
		}
	}
	
	function RestoreNavigation() {
		// When you switch from one layout to the other without reloading the page we want to first restore the navigation to its original state.
		$(".ux-hnav").remove();
		var restoredTabs = $(document).data('hnav').clone();
		restoredTabs.addClass("ux-hnav");
		restoredTabs.insertAfter($("#ux-hdr"));
		$(".ux-vnav").remove();
		$(".ux-hnav-secondary").remove();
		var restoredVerticalNav = $(document).data('vnav').clone();
		restoredVerticalNav.addClass("ux-vnav");
		restoredVerticalNav.insertAfter($(".ux-hnav"));
	}
	
	function RestoreMultiColForms() {
		// When you switch from one layout to the other without reloading the page we want to first restore the forms to their original state.
		var oldforms = $(document).data('multicols').clone();
		var newforms = $(".ux-tabl-form-multicol");
		var i;
		for (i=0; i<newforms.length; i++) {
			$(newforms[i]).replaceWith($(oldforms[i]));
		}
	}

	function AdjustTabs() {
	
		var runningWidth = parseInt($(".ux-hnav").css("paddingLeft")) * 2; // Running width of the tabs, initialized to the left and right total padding	
		var runningWidth2 = parseInt($(".ux-hnav").css("paddingLeft")) * 2;
		var moreTab = false;
		var selectedWillFit = true; // Whether or not the selected tab would fit on screen, or if it would otherwise go in the More menu (which we don't want)
		var containerWidth = $("#ux-wrapper").width();
		var widthToEndOfSelectedTab = runningWidth;
		
		$(".ux-hnav > li").each( function(){
				var thisLiWidth = $(this).outerWidth(true);
				if ((runningWidth + thisLiWidth) >= containerWidth) {
					// This tab will NOT fit. Switch to the More tab style.
					// Add the more tab to the start of the tabs.
					var ul;
					if (!moreTab) { // Only add one More tab to the DOM
						// Create the more tab by starting with a clone of the last tab.
						var c = $(document.createElement("li"));
						a = $(document.createElement("a"));
						a.attr( "href", "#");
						a.text("More").appendTo(c);
						ul = document.createElement("ul"); // Create the secondary navigation ul
						ul = $(ul); // Cast to jQuery object
						ul.appendTo(c); // Add the secondary navigation ul to the more tab
						c.addClass("ux-hnav-more-tab");
						c.appendTo($(".ux-hnav"));
						moreTab = true;
					}
				}
				runningWidth += thisLiWidth;
				// If this is the selected tab...
				if ( $(this).hasClass("ux-hnav-selected") ) {
					// ...then keep track of its width
					widthToEndOfSelectedTab = runningWidth;
				}
		});
		
		if (moreTab) {
			widthToEndOfSelectedTab += parseInt($(".ux-hnav-more-tab").outerWidth(true)); // Add the width of the more tab
		}
	
		$(".ux-hnav-more-tab").prependTo($(".ux-hnav")); // Note, we had to append and then prepend because the margin is different for the first tab than the others.
		selectedWillFit = (widthToEndOfSelectedTab < containerWidth) ? true : false;
					
		/* At this point we've added the More tab to the start of the tab set. We still have to do a few things:
			1. If the selected tab is one of those that would go in the More menu then we need to move it up in the tab order so it remains a tab (doesn't get added to the More menu).
			2. We need to retest the running width.
			3. We need to add the tabs that don't fit to the More menu.
			4. Move the More tab to the end. 
			5. Move the selected tab to the left of the More tab. */
	
		// Determine whether or not the selected tab would be in the More menu.
		if (!selectedWillFit) {
			// If you haven't found the selected one yet then there either isn't one or it should be (temporarily) moved to the front of the line.
			$(".ux-hnav > li.ux-hnav-selected").prependTo($(".ux-hnav"));
		}
		
		// Retest the running width now that we know the More and selected tabs are visible.
		$(".ux-hnav > li").each( function(){
				var thisLiWidth2 = $(this).outerWidth(true);
				if ((runningWidth2 + thisLiWidth2) < containerWidth) {
					// This tab will fit.
					runningWidth2 += thisLiWidth2;
				} else {
					// This tab will NOT fit. Add it to the More menu.
					$(this).appendTo($(".ux-hnav-more-tab > ul"));
				}
		});
		
		// Move the More and selected tabs to the proper spots.
		if (!selectedWillFit) {
			$(".ux-hnav > li.ux-hnav-selected").appendTo($(".ux-hnav"));	
		}
		if (moreTab) {
			$(".ux-hnav-more-tab").appendTo($(".ux-hnav"));
		}
			
		if ($(".ux-hnav-has-horizontal-secondary").length > 0) {
			AdjustSecondaryHorizontalNav();
		}
		
	} // End AdjustTabs
	
	
	function AdjustSecondaryHorizontalNav() {
		var runningWidth = parseInt($(".ux-hnav").css("paddingLeft")) * 2; // Running width of the tabs, initialized to the left and right total padding	
		var runningWidth2 = parseInt($(".ux-hnav").css("paddingLeft")) * 2;
		var moreTab = false;
		var selectedWillFit = true; // Whether or not the selected tab would fit on screen, or if it would otherwise go in the More menu (which we don't want)
		var containerWidth = $("#ux-wrapper").width();
		var widthToEndOfSelectedTab = runningWidth;
		var tabsThatFit = 0;
		$(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul > li").each( function(){
				var thisLiWidth = $(this).outerWidth(true);
				if ((runningWidth + thisLiWidth) >= containerWidth) {
					// This tab will NOT fit. Switch to the More tab style.
					// Add the more tab to the start of the tabs.
					var ul;
					if (!moreTab) { // Only add one More tab to the DOM
						// Create the more tab by starting with a clone of the last tab.
						var c = $(document.createElement("li"));
						a = $(document.createElement("a"));
						a.attr( "href", "#");
						a.text("More").appendTo(c);
						ul = document.createElement("ul"); // Create the secondary navigation ul
						ul = $(ul); // Cast to jQuery object
						ul.appendTo(c); // Add the secondary navigation ul to the more tab
						c.addClass("ux-hnav-has-vertical-tertiary");
						c.prependTo($(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul"));
						moreTab = true;
					}
				} else {
					++tabsThatFit;	
				}
				runningWidth += thisLiWidth;
				// If this is the selected tab...
				if ( $(this).hasClass("ux-hnav-selected") ) {
					// ...then keep track of its width
					widthToEndOfSelectedTab = runningWidth;
				}
		});
		
		if (moreTab) {
			widthToEndOfSelectedTab += parseInt($(".ux-hnav-selected .ux-hnav-has-vertical-tertiary").outerWidth(true)); // Add the width of the more tab
		}
	
		$(".ux-hnav-selected .ux-hnav-selected .ux-hnav-has-vertical-tertiary").prependTo($(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul")); // Note, we had to append and then prepend because the margin is different for the first tab than the others.
		selectedWillFit = (widthToEndOfSelectedTab < containerWidth) ? true : false;
		
		/* At this point we've added the More tab to the start of the tab set. We still have to do a few things:
			1. If the selected tab is one of those that would go in the More menu then we need to move it up in the tab order so it remains a tab (doesn't get added to the More menu).
			2. We need to retest the running width.
			3. We need to add the tabs that don't fit to the More menu.
			4. Move the More tab to the end. 
			5. Move the selected tab to the left of the More tab. */
	
		// Determine whether or not the selected tab would be in the More menu.
		if (!selectedWillFit) {
			// If you haven't found the selected one yet then there either isn't one or it should be (temporarily) moved to the front of the line.
			$(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul > li.ux-hnav-selected").prependTo($(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul"));
		}
		
		// Retest the running width now that we know the More and selected tabs are visible.
		$(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul > li").each( function(){
				var thisLiWidth2 = $(this).outerWidth(true);
				if ((runningWidth2 + thisLiWidth2) < containerWidth) {
					// This tab will fit.
					runningWidth2 += thisLiWidth2;
				} else {
					// This tab will NOT fit. Add it to the More menu, assuming it is not the More menu itself.
					if ($(this).html().indexOf('More') == -1) {
						$(this).appendTo($(".ux-hnav-has-vertical-tertiary:contains('More') > ul"));
					}
				}
		});
		
		// Move the More and selected tabs to the proper spots.
		if (!selectedWillFit) {
			$(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul > li.ux-hnav-selected").appendTo($(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul"));	
		}
		
		if (moreTab) {
			$(".ux-hnav-selected .ux-hnav-has-vertical-tertiary:contains('More')").appendTo($(".ux-hnav-has-horizontal-secondary > li.ux-hnav-selected > ul"));
		}
		
	} // End AdjustSecondaryHorizontalNav
	

	if (gbl_rlSupported()) {
	
		// Cutoffs for responsive layout
		var gbl_phoneTablet = 0;
		var gbl_tabletDesktop = 1200;
	
		function SwitchLayout() {
		
			var w = window.outerWidth; // w = layout width of device
			
			// Reset collapsible panel state
			$(".ux-widg").removeClass("ux-widg-collapsed");
			
			// Reset all expand/collapse event-handlers
			$(".ux-widg").off("click", ".ux-widg-heading");
		
			if (w >= gbl_tabletDesktop) { 
				desktopLayout();
			} else if (w >= gbl_phoneTablet) {
				ConvertVerticalSecondaryNavToHorizontal();
				tabletLayout();
			} else {
				ConvertVerticalSecondaryNavToHorizontal();
				phoneLayout();
			}
			
			AdjustTabs();
			FormatMulticolForms();
		}
		
		var gbl_old_w = window.outerWidth;
		SwitchLayout();
		
		$(window).resize(function() {
			var w = window.outerWidth;	
			if (w != gbl_old_w) {
				RestoreNavigation();
				RestoreMultiColForms();
				SwitchLayout();
				gbl_old_w = w;
			}		
		});
			
	}
}

function ExpandCollapse(e) {
	var hdg = e.target;
	$(hdg).parents(".ux-widg").first().toggleClass("ux-widg-collapsed");
}
/*

	User Experience JavaScript
	Do not modify this file.
	Version 0.6

*/

if (!uxAlreadyLoaded) {
	
	var ux = function () {
		
		var hi_res_suffix = "_ratio2"; // If a standard image is named logo.gif, then the Retina image would be named logo_ratio2.gif
		var hi_res_ext = ".png";
		var hi_res_class = ".ux-img-has-higher-res";
		var hi_res_gif = "ux-img-high-res-gif";
		
		var rtc_class = ".ux-tabl-resizeable-columns";
		
		var touchInterface = 'ontouchstart' in window || navigator.msMaxTouchPoints;
		
		return {
			updateImageResolution: function() { 
				// Change the img src of all images with the class ux-img-has-higher-res to the high res version on retina displays
				if (window.devicePixelRatio && window.devicePixelRatio >= 2) {
					$(hi_res_class).each(
						function(){
							
							var img = $(this);
							var old_width;
							$("<img/>")
								.attr("src", $(img).attr("src"))
								.load(function() {
									
									old_width = this.width;
									var image_src = $(img).attr("src");
									var filename_and_path = image_src.slice( 0, image_src.lastIndexOf(".") );
									image_src = filename_and_path + hi_res_suffix;
									if ($(img).hasClass(hi_res_gif)) {
										image_src += ".gif";
									} else {
										image_src += hi_res_ext;
									}
									$(img).attr("src", image_src);
									$(img).css("max-width", old_width);
									
								});
							
						}
					);
				}
			},
			
			
			oneClickOnly: function() {
				// Makes it so designated buttons can only submit a form once. Subsequent clicks return false.
				$(".ux-js-one-click-only").click(
					function (e) {
						if (!$(e.target).hasClass("ux-js-one-click-only-clicked")) {
							// If this one-click button hasn't already been clicked...
							// ...add the clicked class to it and execute as normal
							$(e.target).addClass("ux-js-one-click-only-clicked");
							return true;
						} else {
							// This button has already been clicked so just return false.
							$(e.target).stopImmediatePropagation();
							return false;
						}
					}
				);
			},
			
			
			cuxDropdowns: function() {
				// Converts standard HTML selects to fancy CUX style ones
				$("select").each(
					function ( index, ogSelect ) {
						
						$(this).addClass( "ux-mirage-dd-original" );
						
						var mirageId = "ux-mirage-dd-" + index;
						var mirageIdQuery = "#" + mirageId;
						var mirageSelectionId = "ux-mirage-dd-" + index + "-selection";
						var mirageSelectionIdQuery = "#" + mirageSelectionId;
						
						// Container
						$("<div>").attr("id",mirageId).addClass("ux-mirage-dd").insertAfter(this);
						$(mirageIdQuery).addClass( $(this).attr("class") );
						$(mirageIdQuery).removeClass( "ux-mirage-dd-original" );
						
						// Selection span
						$("<span>").attr("id",mirageSelectionId).appendTo( $(mirageIdQuery) );
						$(mirageSelectionIdQuery).addClass("ux-mirage-dd-selection").html( $(ogSelect).children("option").eq(0).html() );
						
						// Dropdown list
						$("<ul>").appendTo( $(mirageIdQuery) );
						$(this).children("option").each( function(i,e) {
							var newLI = $("<li>").html( $(ogSelect).children("option").eq(i).html() )
							var q = mirageIdQuery + " ul";
							$(q).append( newLI );
						});

						// Set up event handlers
							/*	
							- Add ux-mirage-dd-active to selection span on click/press
							- Add ux-mirage-dd-li-hover, ux-mirage-dd-li-active to lis on hover and click/press, respectively
							*/
						var bubbleCheck = false;
				
						if (!touchInterface) {
							// Desktop
							$(mirageIdQuery).on("click", function(event) {
								$(this).toggleClass("ux-mirage-dd-active");
								bubbleCheck = true;
							});
								
							$(document).on("click", function(event) {
								if (!bubbleCheck) {
									$(mirageIdQuery).removeClass("ux-mirage-dd-active");
								}
								bubbleCheck = false;
							});
							
							var q = mirageIdQuery + " ul li";
							$(q).on("mouseover", function(event) {
								$(this).addClass("ux-mirage-dd-li-hover");
							});
							$(q).on("mouseout", function(event) {
								$(this).removeClass("ux-mirage-dd-li-hover");
							});
							$(q).on("click", function(event) {
								$(mirageIdQuery).find("*").removeClass("ux-mirage-dd-li-active");
								$(this).addClass("ux-mirage-dd-li-active");
								$(mirageSelectionIdQuery).html($(this).html());
								for (var i=0; i<$(this).parent("ul").children("li").length; i++) {
									if ( $(this).parent("ul").children("li").eq(i).html() == $(this).html() ) {
										$(ogSelect).find("option").attr("selected", false);
										$(ogSelect).find("option").eq(i).attr("selected","selected");
									}
								}
							});
						}
					}
				);
			},
			
			
			resizeableTableColumns: function() {
				// Enables resizeable table columns. Works on tables with the designated CSS class.
				
				$(rtc_class).each( function(index, element) {
					
					// For each table with the resizeable table columns
					// Add the resize handle to each th or td in the top row except the last one.
					
					var tds = $(this).find("tr").first().find("th","td");
					var td_widths = [];
					
					$(tds).each( function(i,e) {
						
						td_widths[i] = $(e).innerWidth() / $(element).innerWidth() * 100;
						$(this).css("width", function() { return td_widths[i] + "%"; });
						
						// Make sure it's not the last one.
						if ( i < tds.length-1 ) {
							
							var handle = $('<span class="ux-tabl-resizeable-column-handle"></span>');
							// Use negative margins to put the handle over the border between thsi cell and the one to the right of it.
							
							$(handle).css("height", function() {
								return $(e).innerHeight();
							});
							$(handle).css("margin-top", function() {
								return "-" + $(e).css("padding-top");
							});
							$(handle).css("margin-bottom", function() {
								var total_padding = $(e).innerHeight() - $(e).height();
								return "-" + total_padding + "px";	
							});
							$(handle).css("margin-right", function() {
								var right_offset = $(this).width()/2 + parseInt($(e).css("padding-right").replace("px",""), 10);
								return "-" + right_offset + "px";
							});
							
							$(this).append(handle);
						}
					});
					
					var mouse_x = 0;
					var resizing = false;
					var original_width = 0;
					var td_resizing;
					$(".ux-tabl-resizeable-column-handle").mousedown( function(e) {
						// Handle event handler
						mouse_x = e.pageX;
						resizing = true;
						original_width = $(this).parent().innerWidth();
						td_resizing = $(this).parent();
						$("body").toggleClass("ux-tabl-resizeable-columns-cursor");
						return false;
					});
					$("body").mousemove( function(e) {
						// Handle event handler
						if (resizing) {
							return false;
						}
					});
					$("body").mouseup( function(e) {
						// Handle event handler
						if (resizing) {
							$(td_resizing).css("width", function() {
								var new_width = original_width + e.pageX - mouse_x;
								new_width = new_width/$(element).innerWidth() * 100;
								return new_width + "%";
							});
							$("body").toggleClass("ux-tabl-resizeable-columns-cursor");
							resizing = false;
							
							// Update the widths of the other table cells.
							$(tds).each( function( i, this_td ) {
								if ($(this_td).html() != $(td_resizing).html()) {
									td_widths[i] = $(this_td).innerWidth() / $(element).innerWidth() * 100;
									$(this).css("width", function() { return td_widths[i] + "%"; });
								}
							});
							
							return false;
						}
					});
					
				});
				
				// Find the first row of table cells or table headers
				
				// Add resize handles
				
							
			},
			
			
			initializeHeader: function() {
				// Add event handlers to header
				
				$(".ux-head").each( function(index, element) {
					
					if (touchInterface) {
						
						$(".ux-pnav li a").on("touchstart", function(event) {
							$(this).parents("li").siblings("li").removeClass("ux-pnav-hover");
							$(this).parents("li").siblings("li").removeClass("ux-pnav-active");
							$(this).parents("li").addClass("ux-pnav-hover");
							$(this).parents("li").addClass("ux-pnav-active");
							if ( $(this).parent("li").hasClass("ux-pnav-has-submenu") ) {
								event.preventDefault();
							}
						});
						
						$(".ux-unav li a").on("touchstart", function(event) {
							$(this).parents("li").siblings("li").removeClass("ux-unav-hover");
							$(this).parents("li").siblings("li").removeClass("ux-unav-active");
							$(this).parents("li").addClass("ux-unav-hover");
							$(this).parents("li").addClass("ux-unav-active");
							if ( $(this).parent("li").hasClass("ux-unav-has-submenu") ) {
								event.preventDefault();
							}
						});
						
						$(".ux-unav li span").on("touchstart", function(event) {
							$(this).parents("li").siblings("li").removeClass("ux-unav-hover");
							$(this).parents("li").addClass("ux-unav-hover");
							if ( $(this).parent("li").hasClass("ux-unav-has-submenu") ) {
								event.preventDefault();
							}
						});
						
						$(document).on("touchstart", function(event) {
							if ( !$(event.target).is(".ux-pnav li a") ) {
								$(".ux-pnav-active").removeClass("ux-pnav-active");
								$(".ux-pnav-hover").removeClass("ux-pnav-hover");
							}
							if ( !$(event.target).is(".ux-unav li a") && !$(event.target).is(".ux-unav li span") ) {
								$(".ux-unav-active").removeClass("ux-unav-active");
								$(".ux-unav-hover").removeClass("ux-unav-hover");
							}
						});
						
						/* Notifications Drop-Down */
						$(".ux-ntfy tr").on("touchstart", function(event) {
							if ( $(event.target).parents("tr").find("td").length > 0  &&  $(event.target).parents("tr").find("a").length > 0 ) {
								window.location = $(event.target).parents("tr").find("a").first().attr("href");
							}
						});
												
					} else {
						
						$(".ux-unav li").on("mouseover", function(event) {
							$(this).toggleClass("ux-unav-hover");
						});
						
						$(".ux-unav li").on("mouseout", function(event) {
							$(this).toggleClass("ux-unav-hover");
						});
						
						$(".ux-unav li a").on("click", function(event) {
							$(this).parents("li").siblings("li").removeClass("ux-unav-active");
							$(this).parent("li").addClass("ux-unav-active");
							if ( $(this).parent("li").hasClass("ux-unav-has-submenu") ) {
								event.preventDefault();
							}
						});
						
						$(".ux-unav li span").on("click", function(event) {
							$(this).parents("li").siblings("li").removeClass("ux-unav-active");
							$(this).parent("li").addClass("ux-unav-active");
							event.stopPropagation();
						});
												
						$(document).click( function(event) {
							if ( !$(event.target).is(".ux-unav li a")) {
								$(".ux-unav-active").removeClass("ux-unav-active");
							}
						});
						
						$(".ux-pnav li").on("mouseover", function(event) {
							$(this).toggleClass("ux-pnav-hover");
						});
						
						$(".ux-pnav li").on("mouseout", function(event) {
							$(this).toggleClass("ux-pnav-hover");
						});
						
						$(".ux-pnav li a").on("click", function(event) {
							$(this).parents("li").siblings("li").removeClass("ux-pnav-active");
							$(this).parent("li").addClass("ux-pnav-active");
							if ( $(this).parent("li").hasClass("ux-pnav-has-submenu") ) {
								event.preventDefault();
							}
						});
												
						$(document).click( function(event) {
							if ( !$(event.target).is(".ux-pnav li a") ) {
								$(".ux-pnav-active").removeClass("ux-pnav-active");
							}
						});
						
						/* Notifications Drop-Down */
						$(".ux-ntfy tr").on("click", function(event) {
							if ( $(event.target).parents("tr").find("td").length > 0  &&  $(event.target).parents("tr").find("a").length > 0 ) {
								window.location = $(event.target).parents("tr").find("a").first().attr("href");
							}
						});
						
					}
										
				});
				
			},
			
			
			initializeSecondaryHorizontalNav: function() {
				// Add event handlers to secondary horizontal navigation
				
				$(".ux-snav").each( function(index, element) {
					
					// Make sure there is a ux-snav-has-submenu class on the correct elements.
					$(".ux-snav li").each( function(idx, el) {
						if ( $(el).children("ul").length > 0 ) {
							$(el).addClass("ux-snav-has-submenu");	
						}
					});
					
					// Make any drop-down menus at least as wide as the parent tab
					$(".ux-snav > li").each( function(idx, el) {
						var adjustment = $(el).hasClass("ux-snav-selected") ? 2 : 0;
						var parentWidth = $(el).innerWidth()-adjustment + "px";
						var childWidth = $(el).children("ul").children("li").children("a").css("min-width", parentWidth);
					});
					
					if (touchInterface) {
						
						$(".ux-snav li a").on("touchstart", function(event) {
							if ( !$(this).parents("li").hasClass("ux-snav-disabled") ) {
								$(this).parents("li").siblings("li").removeClass("ux-snav-hover");
								$(this).parents("li").siblings("li").removeClass("ux-snav-active");
								$(this).parents("li").addClass("ux-snav-hover");
								$(this).parents("li").addClass("ux-snav-active");
								if ( $(this).parent("li").hasClass("ux-snav-has-submenu") ) {
									event.preventDefault();
								}
							} else {
								event.preventDefault();
							}
						});
						
						$(document).on("touchstart", function(event) {
							if ( !$(event.target).is(".ux-snav li a") ) {
								$(".ux-snav-hover").removeClass("ux-snav-hover");
								$(".ux-snav-active").removeClass("ux-snav-active");
							}
						});
												
					} else {
						
						$(".ux-snav li").on("mouseover", function(event) {
							$(this).toggleClass("ux-snav-hover");
						});
						
						$(".ux-snav li").on("mouseout", function(event) {
							$(this).toggleClass("ux-snav-hover");
						});
						
						$(".ux-snav li a").on("click", function(event) {
							if ( !$(this).parents("li").hasClass("ux-snav-disabled") ) {
								$(this).parents("li").siblings("li").removeClass("ux-snav-active");
								$(this).parent("li").addClass("ux-snav-active");
								if ( $(this).parent("li").hasClass("ux-snav-has-submenu") ) {
									event.preventDefault();
								}
							} else {
								event.preventDefault();
							}
						});
												
						$(document).click( function(event) {
							if ( !$(event.target).is(".ux-snav li a") ) {
								$(".ux-snav-active").removeClass("ux-snav-active");
							}
						});
						
					}
										
				});
				
			},
			
			
			initializeVerticalNavigation: function() {
				// Add event handlers to vertical navigation
				
				$(".ux-vnav").each( function(index, element) {
					
					// Add the has-selected class if necessary
					$(this).find(".ux-vnav-selected").parents("li").addClass("ux-vnav-has-selected");
					
					// Add submenu classes if they don't already exist
					$(this).children("li").each( function(lii, li) {
						var $li = $(li);
						if ( $li.find("ul").length > 0 ) {
							// This list item has a submenu
							if ( $li.hasClass("ux-vnav-submenu-open") || $li.hasClass("ux-vnav-submenu-closed") ) {
								// This list item already has a submenu state defined, so leave it alone	
							} else {
								// This list item needs a submenu state defined
								if ( $li.find(".ux-vnav-selected").length > 0 ) {
									// li contains the selected item, so make it open by default
									$li.addClass("ux-vnav-submenu-open");	
								} else {
									$li.addClass("ux-vnav-submenu-closed");	
								}
							}
						}
					});
					
					if (touchInterface) {
						
						var timeoutId;
						
						$(".ux-vnav li > a").on("touchstart", function(event) {
							if ( !$(this).parents("li").hasClass("ux-vnav-disabled") ) {
								$(element).find("li").removeClass("ux-vnav-active");
								$(this).parent("li").addClass("ux-vnav-active");
								
								// Check to see if this has a submenu
								if ( $(this).parent("li").hasClass("ux-vnav-submenu-closed") ) {
									$(this).parents(".ux-vnav").find(".ux-vnav-submenu-open")
										.removeClass("ux-vnav-submenu-open")
										.addClass("ux-vnav-submenu-closed");
									$(this).parent("li").removeClass("ux-vnav-submenu-closed").addClass("ux-vnav-submenu-open");
									event.preventDefault();
								} else if ( $(this).parent("li").hasClass("ux-vnav-submenu-open") ) {
									$(this).parent("li").removeClass("ux-vnav-submenu-open").addClass("ux-vnav-submenu-closed");
									event.preventDefault();
								}
								
								// If this is not a leaf node (an active link) then remove the active state after half a second
								if ( $(this).parent("li").hasClass("ux-vnav-submenu-closed") ||
									 $(this).parent("li").hasClass("ux-vnav-submenu-open") ) {
									var $parentLI = $(this).parent("li");
									function fadeOut() { $parentLI.removeClass("ux-vnav-active") };
									timeoutId = window.setTimeout(
											fadeOut,
											500
										);
								}
								
							} else {
								event.preventDefault();
							}
						});
												
						$(document).touchstart( function(event) {
							if ( !$(event.target).is(".ux-vnav li > a") ) {
								$(".ux-vnav-active").removeClass("ux-vnav-active");
							}
						});
												
					} else {
						
						$(".ux-vnav li a").on("mouseover", function(event) {
							if ( !$(this).parents("li").hasClass("ux-vnav-disabled") ) {
								$(this).parent("li").toggleClass("ux-vnav-hover");
							}
						});
						
						$(".ux-vnav li a").on("mouseout", function(event) {
							var $li = $(this).parent("li");
							$li.removeClass("ux-vnav-hover");
							if ($li.hasClass("ux-vnav-submenu-closed") ||
								$li.hasClass("ux-vnav-submenu-open")) {
								$li.removeClass("ux-vnav-active");		
							}
						});
						
						$(".ux-vnav li > a").on("click", function(event) {
							if ( !$(this).parents("li").hasClass("ux-vnav-disabled") ) {
								$(element).find("li").removeClass("ux-vnav-active");
								$(this).parent("li").addClass("ux-vnav-active");
								
								// Check to see if this has a submenu
								if ( $(this).parent("li").hasClass("ux-vnav-submenu-closed") ) {
									$(this).parents(".ux-vnav").find(".ux-vnav-submenu-open")
										.removeClass("ux-vnav-submenu-open")
										.addClass("ux-vnav-submenu-closed");
									$(this).parent("li").removeClass("ux-vnav-submenu-closed").addClass("ux-vnav-submenu-open");
									event.preventDefault();
								} else if ( $(this).parent("li").hasClass("ux-vnav-submenu-open") ) {
									$(this).parent("li").removeClass("ux-vnav-submenu-open").addClass("ux-vnav-submenu-closed");
									event.preventDefault();
								}
								
							} else {
								event.preventDefault();
							}
						});
												
						$(document).click( function(event) {
							if ( !$(event.target).is(".ux-vnav li > a") ) {
								$(".ux-vnav-active").removeClass("ux-vnav-active");
							}
						});
						
					}
										
				});
				
			},
			
			
			initializeTabbedPanels: function() {
				// Add event handlers to tabbed panels
				
				$(".ux-tpnl").each( function(index, element) {
					
					// Set the content pane initially to the selected tab's content.
					$(this).children("div.ux-tpnl-content").html(
						$(this).find("li.ux-tpnl-selected > div").clone(true, true).contents()
					);
						
					// Make sure the content panel's minimum width is always at least as wide as the tabs.
					var tabWidth = 0;
					var $lis = $(this).children("ul").children("li");
					for (var i=0; i<$lis.length; i++) {
						tabWidth += $lis.eq(i).innerWidth();	
					}
					$(this).children("div.ux-tpnl-content").css("min-width", tabWidth);
					
					if (touchInterface) {
						
						var timeoutId;
						
						$(".ux-tpnl > ul > li > a").on("touchstart", function(event) {
							if ( !$(this).parents("li").hasClass("ux-tpnl-disabled") ) {
								// First move the existing selected content back to its tab.
								$(this).parents("li").siblings("li.ux-tpnl-selected").children("div").html(
									$(this).parents(".ux-tpnl").children("div.ux-tpnl-content").clone(true, true).contents()
								);
								$(this).parents("li").siblings("li").removeClass("ux-tpnl-selected");
								$(this).parents("li").siblings("li").removeClass("ux-tpnl-active");
								$(this).parents("li").addClass("ux-tpnl-selected");
								$(this).parents("li").addClass("ux-tpnl-active");
								var $parentLI = $(this).parent("li");
								function fadeOut() { $parentLI.removeClass("ux-tpnl-active") };
								timeoutId = window.setTimeout(
										fadeOut,
										500
									);
								$(this).parents(".ux-tpnl").children("div.ux-tpnl-content").html(
									$(this).siblings("div").clone(true, true).contents() );
							} 
							event.preventDefault();
						});
						
						$(document).on("touchstart", function(event) {
							if ( !$(event.target).is(".ux-tpnl > ul > li > a") ) {
								$(".ux-tpnl-active").removeClass("ux-tpnl-active");
							}
						});
												
					} else {
						
						$(".ux-tpnl > ul > li").on("mouseover", function(event) {
							$(this).toggleClass("ux-tpnl-hover");
						});
						
						$(".ux-tpnl > ul > li").on("mouseout", function(event) {
							$(this).toggleClass("ux-tpnl-hover");
							$(this).removeClass("ux-tpnl-active");
						});
						
						$(".ux-tpnl > ul > li > a").on("click", function(event) {
							if ( !$(this).parents("li").hasClass("ux-tpnl-disabled") ) {
								// First move the existing selected content back to its tab.
								$(this).parents("li").siblings("li.ux-tpnl-selected").children("div").html(
									$(this).parents(".ux-tpnl").children("div.ux-tpnl-content").clone(true, true).contents()
								);
								$(this).parents("li").siblings("li").removeClass("ux-tpnl-selected");
								$(this).parents("li").siblings("li").removeClass("ux-tpnl-active");
								$(this).parent("li").addClass("ux-tpnl-selected");
								$(this).parent("li").addClass("ux-tpnl-active");
								$(this).parents(".ux-tpnl").children("div.ux-tpnl-content").html(
									$(this).siblings("div").clone(true, true).contents() );
							} 
							event.preventDefault();
						});
												
						$(document).click( function(event) {
							if ( !$(event.target).is(".ux-tpnl > ul > li > a") ) {
								$(".ux-tpnl-active").removeClass("ux-tpnl-active");
							}
						});
						
					}
										
				});
				
			},
			
			//Tabbed function 
			initializeTabsPanels: function() {
				// Add event handlers to tabbed panels
				
				$(".ux-tabs").each( function(index, element) {
					
					// Set the content pane initially to the selected tab's content.
					$(this).children("div.ux-tabs-content").html(
						$(this).find("li.ux-tabs-selected > div").clone(true, true).contents()
					);
						
					// Make sure the content panel's minimum width is always at least as wide as the tabs.
					var tabWidth = 0;
					var $lis = $(this).children("ul").children("li");
					for (var i=0; i<$lis.length; i++) {
						tabWidth += $lis.eq(i).innerWidth();	
					}
					$(this).children("div.ux-tabs-content").css("min-width", tabWidth);
					
					if (touchInterface) {
						
						var timeoutId;
						
						$(".ux-tabs > ul > li > a").on("touchstart", function(event) {
							if ( !$(this).parents("li").hasClass("ux-tabs-disabled") ) {
								// First move the existing selected content back to its tab.
								$(this).parents("li").siblings("li.ux-tabs-selected").children("div").html(
									$(this).parents(".ux-tabs").children("div.ux-tabs-content").clone(true, true).contents()
								);
								$(this).parents("li").siblings("li").removeClass("ux-tabs-selected");
								$(this).parents("li").siblings("li").removeClass("ux-tabs-active");
								$(this).parents("li").addClass("ux-tabs-selected");
								$(this).parents("li").addClass("ux-tabs-active");
								var $parentLI = $(this).parent("li");
								function fadeOut() { $parentLI.removeClass("ux-tabs-active") };
								timeoutId = window.setTimeout(
										fadeOut,
										500
									);
								$(this).parents(".ux-tabs").children("div.ux-tabs-content").html(
									$(this).siblings("div").clone(true, true).contents() );
							} 
							event.preventDefault();
						});
						
						$(document).on("touchstart", function(event) {
							if ( !$(event.target).is(".ux-tabs > ul > li > a") ) {
								$(".ux-tabs-active").removeClass("ux-tabs-active");
							}
						});
												
					} else {
						
						$(".ux-tabs > ul > li").on("mouseover", function(event) {
							$(this).toggleClass("ux-tabs-hover");
						});
						
						$(".ux-tabs > ul > li").on("mouseout", function(event) {
							$(this).toggleClass("ux-tabs-hover");
							$(this).removeClass("ux-tabs-active");
						});
						
						$(".ux-tabs > ul > li > a").on("click", function(event) {
							if ( !$(this).parents("li").hasClass("ux-tabs-disabled") ) {
								// First move the existing selected content back to its tab.
								$(this).parents("li").siblings("li.ux-tabs-selected").children("div").html(
									$(this).parents(".ux-tabs").children("div.ux-tabs-content").clone(true, true).contents()
								);
								$(this).parents("li").siblings("li").removeClass("ux-tabs-selected");
								$(this).parents("li").siblings("li").removeClass("ux-tabs-active");
								$(this).parent("li").addClass("ux-tabs-selected");
								$(this).parent("li").addClass("ux-tabs-active");
								$(this).parents(".ux-tabs").children("div.ux-tabs-content").html(
									$(this).siblings("div").clone(true, true).contents() );
							} 
							event.preventDefault();
						});
												
						$(document).click( function(event) {
							if ( !$(event.target).is(".ux-tabs > ul > li > a") ) {
								$(".ux-tabs-active").removeClass("ux-tabs-active");
							}
						});
						
					}
										
				});
				
			},
			
			initializeMobileHeader: function() {
				// Add event handlers to mobile header
				
				$("#ux-mhdr").each( function(index, element) {
					
					$("#ux-mhdr > li").on("touchstart", function(event) {
						$(this).siblings().find(".ux-mhdr-show").removeClass("ux-mhdr-show");
						$(this).siblings(".ux-mhdr-show").removeClass("ux-mhdr-show");
						$(this).toggleClass("ux-mhdr-show");
						event.stopImmediatePropagation();
					});
					$("#ux-mhdr > li > ul > li").on("touchstart", function(event) {
						$(this).siblings().find(".ux-mhdr-show").removeClass("ux-mhdr-show");
						$(this).siblings(".ux-mhdr-show").removeClass("ux-mhdr-show");
						$(this).toggleClass("ux-mhdr-show");
						event.stopImmediatePropagation();
					});
					$(document).on("touchstart", function(event) {
						$(".ux-mhdr-show").removeClass("ux-mhdr-show");
					});
					
				});
				
			},
			
			
			initializeHoverPanels: function() {
				// Add event handlers to hover panels
				
				$(".ux-hovr").each( function(index, element) {
					
					// Desktops use click!				
					$(this).on("mouseover", function(event) {
						$(".ux-hovr-show").removeClass("ux-hovr-show");	
						$(this).toggleClass("ux-hovr-show");
						event.stopImmediatePropagation();
					});
					$(this).on("mouseout", function(event) {
						$(".ux-hovr-show").removeClass("ux-hovr-show");
					});
					
					// Tablets use touch!				
					$(this).on("touchstart", function(event) {
						$(".ux-hovr-show").removeClass("ux-hovr-show");	
						$(this).toggleClass("ux-hovr-show");
						//event.stopImmediatePropagation();
					});
					$(document).on("touchstart", function(event) {
						$(".ux-hovr-show").removeClass("ux-hovr-show");
					});
					
				});
				
			}
		};
		
	};
	
	function ux_selectTab(tabName) {
		return;	
	}
	
		
	$(document).ready(function(){
		
		var uxo = ux(); // UX object
		uxo.updateImageResolution();
		uxo.oneClickOnly();
		uxo.resizeableTableColumns();
		//uxo.initializeMobileHeader();
		uxo.initializeHeader();
		uxo.initializeSecondaryHorizontalNav();
		uxo.initializeHoverPanels();
		//uxo.cuxDropdowns();
		uxo.initializeTabbedPanels();
		uxo.initializeTabsPanels();
		uxo.initializeVerticalNavigation();
		
		
		//alert(uxo.isLoaded);
		
	});
	
	var uxAlreadyLoaded = true;
	
}

/*--------ADD ROW js-------*/
	function addRow(tableID)  {
    var table  = document.getElementById(tableID);
    var rowCount  = table.rows.length;
    var row  = table.insertRow(rowCount);
    var colCount  = table.rows[1].cells.length;
    for(var i=0; i<colCount; i++)  {
        var newcell	 = row.insertCell(i);
        newcell.innerHTML  = table.rows[1].cells[i].innerHTML;
        //alert(newcell.childNodes);
        				switch(newcell.childNodes[0].type)  {
            case "text":newcell.childNodes[0].value  = "";
			
            break;
            case "checkbox":newcell.childNodes[0].checked  = false;
            break;
            case "select-one":newcell.childNodes[0].selectedIndex  = 0;
            break;
        }
    }
}
function deleteRow(tableID)  {
    try  {
        var table  = document.getElementById(tableID);
        var rowCount  = table.rows.length;
        for(var i=0; i<rowCount; i++)  {
            var row  = table.rows[i];
            var chkbox  = row.cells[0].childNodes[0];
            if(null  != chkbox  && true  == chkbox.checked)  {
                if(rowCount  <= 1)  {
                    alert("Cannot delete all the rows.");
                    break;
                }
                table.deleteRow(i);
                rowCount--;
                i--;
            }
        }
    }
    
			catch(e) {
				alert(e);
			}
			
			
		}
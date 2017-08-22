// JavaScript Document
$(document).ready(function () {

    (function resizableTableColumns() {
        // Enables resizeable table columns. Works on tables with the designated CSS class.

        var rtc_class = ".ui-datatable-resizable-patched";
        var tablesInitialized = false;
		
		// First remove PrimeFaces event handlers and classes
        $(".ui-datatable-resizable table").addClass("ui-datatable-resizable-patched");
        $(".ui-datatable-resizable").removeClass("ui-datatable-resizable");
        $(".ui-datatable-resizable-patched td, .ui-datatable-resizable-patched th").removeAttr("style");
        $(".ui-resizable-column").removeClass("ui-resizable-column");
        $(".ui-column-resizer").remove();
		
		$("table").click(function () {
            // Move the sort arrow after the name and restyle it.
            $(".ui-sortable-column-icon").each(function (i, e) {
				$(e).css("position", "relative");
                $(e).css("margin-left", "4px");
                $(e).css("margin-right", "0px");
                $(e).css("margin-top", "0px");
                $(e).appendTo($(e).siblings("span").first());
            });
        });

		$("body").click(function () {
            $(rtc_class).each(function (index, element) {

                // We won't be able to get the correct widths if the tables are hidden.
                if ($(element).is(":visible") && !$(element).hasClass("ux-col-resize-initialized")) {

                    $(element).toggleClass("ux-col-resize-initialized");

                    // Remove hard-coded styles
                    var all_tds = $(element).find("th, td");
                    $(all_tds).each(function (i, e) {
                        $(this).removeAttr("style");
                    });

                    // For each table with the resizeable table columns
                    // Add the resize handle to each th or td in the top row except the last one.

					//$(".ui-datatable-resizable-patched").find("tr").first().remove();
					
					
					var tds = $(element).find('tr[role|="row"]').first().find("th, td");
                    $(tds).last().css("width", "99%");
                    var td_widths = [];

                    $(tds).each(function (i, e) {

                        // Set column width and add resize handle

                        td_widths[i] = $(e).innerWidth() / $(element).innerWidth() * 100;
                        $(this).css("width", function () {
                            return td_widths[i] + "%";
                        });

                        // Make sure it's not the last one.
                        if (i < tds.length - 1) {

                            var handle = $('<span class="ux-tabl-resizeable-column-handle"></span>');
                            // Use negative margins to put the handle over the border between thsi cell and the one to the right of it.

                            var pf_div = $(e).find(".ui-dt-c");
                            $(handle).css("height", function () {
                                return $(pf_div).innerHeight();
                            });
                            $(handle).css("margin-top", function () {
                                return "-" + $(pf_div).css("padding-top");
                            });
                            $(handle).css("margin-bottom", function () {
                                var total_padding = $(pf_div).innerHeight() - $(pf_div).height();
                                return "-" + total_padding + "px";
                            });
                            $(handle).css("margin-right", function () {
                                var right_offset = $(this).width() / 2 + parseInt($(pf_div).css("padding-right").replace("px", ""), 10);
                                return "-" + right_offset + "px";
                            });

                            $(this).find(".ui-dt-c").append(handle);
                        }
                    
	
						var mouse_x = 0;
						var resizing = false;
						var original_width = 0;
						var td_resizing;
						$(this).find(".ux-tabl-resizeable-column-handle").mousedown(function (e) {
							// Handle event handler
							mouse_x = e.pageX;
							resizing = true;
							original_width = $(this).parents("td,th").innerWidth();
							td_resizing = $(this).parents("td,th");
							$("body").toggleClass("ux-tabl-resizeable-columns-cursor");
							e.stopPropagation();
							return false;
						});
						$("body").mousemove(function (e) {
							// Handle event handler
							if (resizing) {
								e.stopPropagation();
								return false;
							}
						});
						$("body").mouseup(function (e) {
							// Handle event handler
							if (resizing) {
								$(td_resizing).css("width", function () {
									var new_width = original_width + e.pageX - mouse_x;
									new_width = new_width / $(element).innerWidth() * 100;
									return new_width + "%";
								});
								
								$(td_resizing).css("width", function () {
									return (($(td_resizing).innerWidth() / $(element).innerWidth() * 100) + "%");
								});
								$("body").toggleClass("ux-tabl-resizeable-columns-cursor");
								resizing = false;
	
								// Update the widths of the other table cells.
								$(tds).each(function (this_td_i, this_td) {
									if ( this_td_i>i ) {
										$(this).removeAttr("style");
									}
								});
								
								// Remove hard-coded widths on tbody tds
								var tbody_tds = $(element).find("tbody td");
								$(tbody_tds).each(function (i, e) {
									$(this).removeAttr("style");
								});
	
								e.stopPropagation();
	
								return false;
							}
						});
					});

                }

            });
		});
		
		$("body").click();

    })();

});
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    //Left menu activation
    $("#rpm").addClass("ux-vnav-selected");
    //Left menu activation

    $("#accordion").accordion({
        collapsible: true,
        active: false
    });

    // Use specific table id to add Class
    $('#dataTable tr:odd').addClass('ux-tabl-alt-row');

    $('#dataTable').dataTable({
        ordering: true,
        paging: false,
        info: false,
        "sScrollY": "250",
        "sScrollX": true,
        "scrollCollapse": true,
        "aoColumns": [
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": true},
            {"bSortable": false}
        ]
    }).columnFilter({
        sPlaceHolder: "head:after",
        aoColumns: [
            {type: "text"},
            {type: "text"},
            {type: "select"},
            {type: "select"},
            {type: "select"},
            {type: "select"},
            null
        ]
    });
    /*
     * For hiding global search from data table
     */
    $("#dataTable_filter").css({"display": "none"});
    $("#removeSortingForCheckbox").removeClass("sorting_asc");

    var dateType = $("#dataTable").dataTable();

    /* Add a select menu for each TH element in the table footer */
    $("thead td").each(function(i) {
        if (i === 2) {
            this.innerHTML = fnCreateSelect(dateType.fnGetColumnData(i));
            $('select', this).change(function() {
                dateType.fnFilter($(this).val(), i);
            });
        }
    });
    dateType.fnSortNeutral();
    dateType.fnDraw();
});

$.fn.SearchFilterClear = function(obj, tableID) {
    var dateType = $('#dataTable').dataTable();

    dateType.fnSortNeutral();

    var tabObj = $(tableID).dataTable().fnFilterClear("");
    var tds = $(obj).parent().parent().parent().children("td");

    $(tds).each(function() {
        var inputFeild = $(this).children().children("input").attr("type");
        if (typeof inputFeild !== "undefined") {
            var textObj = $(this).children().children("input");
            $(textObj).val("");
            var tableSettings = dateType.fnSettings();
            dateType.fnDraw();
            $(textObj).trigger('blur');
        }
        else {
            inputFeild = $(this).children().children("select");
            if (typeof inputFeild !== "undefined") {
                $(inputFeild).val("");
            }
        }
    });

    //Start: Recreating the dropdown for SERVICE dropdown
    $("thead td").each(function(i) {
        if (i === 2) {
            this.innerHTML = fnCreateSelect(dateType.fnGetColumnData(i));
            $('select', this).change(function() {
                dateType.fnFilter($(this).val(), i);
            });
        }
    });
    dateType.fnDraw();
    //End: Recreating the dropdown for SERVICE dropdown
};

//Start: funnction to reset sorting in datatable
jQuery.fn.dataTableExt.oApi.fnSortNeutral = function(oSettings)
{
    /* Remove any current sorting */
    oSettings.aaSorting = [];

    /* Sort display arrays so we get them in numerical order */
    oSettings.aiDisplay.sort(function(x, y) {
        return x - y;
    });
    oSettings.aiDisplayMaster.sort(function(x, y) {
        return x - y;
    });

    /* Redraw */
    oSettings.oApi._fnReDraw(oSettings);
};
//End: funnction to reset sorting in datatable

//Function to get data for select dropdown in datatable
(function($) {
    /*
     * Function: fnGetColumnData
     * Purpose:  Return an array of table values from a particular column.
     * Returns:  array string: 1d data array
     * Inputs:   object:oSettings - dataTable settings object. This is always the last argument past to the function
     *           int:iColumn - the id of the column to extract the data from
     *           bool:bUnique - optional - if set to false duplicated values are not filtered out
     *           bool:bFiltered - optional - if set to false all the table data is used (not only the filtered)
     *           bool:bIgnoreEmpty - optional - if set to false empty values are not filtered from the result array
     * Author:   Benedikt Forchhammer <b.forchhammer /AT\ mind2.de>
     */
    $.fn.dataTableExt.oApi.fnGetColumnData = function(oSettings, iColumn, bUnique, bFiltered, bIgnoreEmpty) {
        // check that we have a column id
        if (typeof iColumn === "undefined")
            return new Array();

        // by default we only wany unique data
        if (typeof bUnique === "undefined")
            bUnique = true;

        // by default we do want to only look at filtered data
        if (typeof bFiltered === "undefined")
            bFiltered = true;

        // by default we do not wany to include empty values
        if (typeof bIgnoreEmpty === "undefined")
            bIgnoreEmpty = true;

        // list of rows which we're going to loop through
        var aiRows;

        // use only filtered rows
        if (bFiltered === true)
            aiRows = oSettings.aiDisplay;
        // use all rows
        else
            aiRows = oSettings.aiDisplayMaster; // all row numbers

        // set up data array   
        var asResultData = new Array();

        for (var i = 0, c = aiRows.length; i < c; i++) {
            iRow = aiRows[i];
            var aData = this.fnGetData(iRow);
            var sValue = aData[iColumn];

            // ignore empty values?
            if (bIgnoreEmpty === true && sValue.length === 0)
                continue;

            // ignore unique values?
            else if (bUnique === true && jQuery.inArray(sValue, asResultData) > -1)
                continue;

            // else push the value onto the result data array
            else
                asResultData.push(sValue);
        }

        return asResultData;
    };
}(jQuery));

//Preparing select dropdown for SERVICEs in datatable
function fnCreateSelect(aData)
{
    var r = '<select><option value="">Select</option>', i, iLen = aData.length;
    for (i = 0; i < iLen; i++)
    {
        r += '<option value="' + aData[i] + '">' + aData[i] + '</option>';
    }
    return r + '</select>';
}

var statusCode = "";
function changeStatusValue(count) {
    var status = $("#statusCode" + count + " :selected").text();
    if (status === 'Validated') {
        status = 'V';
    } else if (status === 'Closed') {
        status = 'C';
    }
    statusCode = statusCode + status + "@" + $("#eii" + count).val() + ",";
}

function updateFindingStatus() {
    $(".statusValue0").val(statusCode);
    document.updateFindingStatusForm.action = "updatefindingstatus.htm";
    document.updateFindingStatusForm.submit();
}
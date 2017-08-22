/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    var checkboxResult = [];
    var selectedCheckboxes = [];
    var headCheckbox = document.getElementById('headCheckbox');
    $('.checkboxGroup').each(function() {
        if (this.checked) {
            var id = $(this).attr('id');
            selectedCheckboxes.push(id);
        }
    });
    $('.checkboxGroup').each(function() {
        var id = $(this).attr('id');
        checkboxResult.push(id);
    });
    if (selectedCheckboxes.length === checkboxResult.length) {
        headCheckbox.checked = true;
    }
});

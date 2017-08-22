/*COPYRIGHT ALLISON STONE 2013*/


//<!-- MODAL TREE STRUCTURE -->

    $("li.parent-list ul").hide(); //hide the child lists
    $("li.parent-list i").click(function () {
        $(this).toggleClass('icon-caret-up'); // toggle the font-awesome icon class on click
        $(this).next("ul").toggle(); // toggle the visibility of the child list on click
    });

//<!--MODAL MULTISELECT FUNCTIONALITY -->

// check-uncheck all
    $(document).on('change', 'input[id="all"]', function () { 
        $('.canine').prop("checked", this.checked);
    });

// parent/child check-uncheck all
    $(function () {
        $('.parent').on('click', function () {
            $(this).closest('ul li').find(':checkbox').prop('checked', this.checked);
        });
    });

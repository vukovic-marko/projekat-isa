

$(document).ready(function() {

    $("#tabs" ).
    tabs({
        active:0,
        collapsible:true
    });
    $("#tabs").tabs("enable",0);

    $("div[id^='alert']").hide();

    $("#buttonRefresh").click(function(event) {

        console.log('button clicked');

        $("#tabs").tabs("disable");

        //
        var loadedTabs = 0;
        $("#tabs").on("tabsload", function(event) {

            loadedTabs++;
            if(loadedTabs == 4) {

                $(this).tabs("enable");
                $(this).off("tabsload");
            }
        });

        for (var i = 0; i < 4; i++) {

            $("#tabs").tabs("load", i);
        }
    });

    $("#tabs").on("tabsbeforeload", function(event, ui) {

        //selektovani tab
        var indeks = $(this).tabs("option", "active");

        ui.jqXHR.done(function(data) {
            console.log("successful <" + indeks + "> tab loading, data: " + data);
        });

        ui.jqXHR.fail(function(data) {

            console.log("Failed loading data!");
        });
    });


    //ProfileInfo panel
    $("#username").prop("disabled", true);
    $("#userInfoFieldset").prop("disabled", true);

    $(document).on("click", "#edit", function(event) {

        $("#cityFieldset").prop("disabled", false);
        $("#userInfoFieldset").prop("disabled", false);
        $("#btnEdit").hide();
        $("#btnsEditing").show();
    });

    $(document).on("click", "#cancel", function(event) {

        $("#cityFieldset").prop("disabled", true);
        $("#userInfoFieldset").prop("disabled", true);
        $("#btnsEditing").hide();
        $("#btnEdit").show();
    });


});
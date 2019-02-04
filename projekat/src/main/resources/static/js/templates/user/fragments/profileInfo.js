$(document).ready(function () {

    $("#tabs").tabs({
        active: 0,
        collapsible: true
    });

    $("div[id^='alert']").hide();

    $("#tabs").on("tabsbeforeload", function (event, ui) {

        //selektovani tab
        var indeks = $(this).tabs("option", "active");
        var myjqxhr = ui.jqXHR;

        ui.jqXHR.done(function (data) {
            console.log("successful <" + indeks + "> tab loading, data: " + data);

            if (indeks == 0) {
                $("#username").prop("disabled", true);
                $("#userInfoFieldset").prop("disabled", true);
            }
        });

        ui.jqXHR.fail(function (data) {

            console.log("Failed loading data!");
        });
    });

    var validator = null;
    $("#tabs").on("tabsload", function (event, ui) {

        //selektovani tab
        var indeks = $(this).tabs("option", "active");

        if (indeks == 0 && validator == null) {

            validator = $('#profileInfoForm').validate({
                rules: {
                    username: {
                        required: true,
                        remote: "user/checkusername/"
                    },
                    email: {
                        required: true,
                        email: true,
                        remote: "user/checkemail/"
                    },
                    firstName: {
                        required: true
                    },
                    lastName: {
                        required: true
                    },
                    phone: {
                        required: true,
                        phoneCheck: true
                    },
                    city: {
                        required: true
                    }

                },
                messages: {
                    username: {
                        required: "Ime nije uneto",
                        remote: "Korisničko ime je zauzeto"
                    },

                    password: "Lozinka nije uneta",

                    password2: {
                        required: "Unesite ponovo lozinku",
                        equalTo: "Lozinke se ne poklapaju"
                    },
                    firstName: {
                        required: "Ime nije uneto",
                        letters: "Dozvoljena su samo slova srpske latinice i razmaci"
                    },
                    lastName: {
                        required: "Prezime nije uneto",
                        letters: "Dozvoljena su samo slova srpske latinice i razmaci"
                    },
                    email: {
                        required: "Email adresa nije uneta",
                        email: "Neispravan format adrese",
                        remote: "Već postoji nalog koji je registrovan na ovu email adresu"
                    },
                    phone: {
                        required: "Telefon nije unet",
                        phoneCheck: "Neispravan format (123/456-78-99)"
                    },
                    city: {
                        required: "Grad nije unet"
                    }
                }
            });
        }
    });
    // Refresh taba
    $(document).on("click", "#buttonRefresh", function (event) {

        console.log('button clicked');

        $("#tabs").tabs("disable");

        //
        var loadedTabs = 0;
        $("#tabs").on("tabsload", function (event) {

            loadedTabs++;
            if (loadedTabs == 4) {

                $(this).tabs("enable");
                $(this).off("tabsload");
            }
        });

        for (var i = 0; i < 4; i++) {

            $("#tabs").tabs("load", i);
        }
    });

    // Profile info dugmad
    $(document).on("click", "#edit", function (event) {

        $("#cityFieldset").prop("disabled", false);
        $("#userInfoFieldset").prop("disabled", false);
        $("#btnEdit").hide();
        $("#btnsEditing").show();
    });

    $(document).on("click", "#cancel", function (event) {

        $("#reset").trigger("click");

        $("#username").prop("disabled", true);
        $("#userInfoFieldset").prop("disabled", true);
        $("#btnsEditing").hide();
        $("#btnEdit").show();
    });

    $(document).on("click", "#reset", function (event) {

        $("#profileInfoForm").trigger("reset");

        validator.resetForm();
    });

    $(document).on("click", "#submit", function (event) {

        if ($("#profileInfoForm").valid()) {

            var serialized = $("#profileInfoForm").serialize();

            $.ajax({
                url: "/api/registeredusers/" + $("#username").val() + "/edit",
                data: serialized,
                method: 'PATCH'
            }).done(function (event, data, jqxhr) {

                console.log("Uspjesno je poslan patch");

                $("#cancel").trigger("click");

                $("#tabs").tabs("load", 0);

            })
        } else {

            console.log("Nije poslan");
        }
    });
});


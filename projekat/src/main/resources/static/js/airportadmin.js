
// Token
function getToken() {
    return localStorage.getItem('jwtToken');
}
function refreshToken() {
    $.ajax({
        url: '/user/refresh',
        type: 'post',
        success: function (data) {
            localStorage.setItem('jwtToken', data.accessToken);
        }
    });
}
$(document).ajaxSend(function (event, jqxhr, settings) {
    var token = getToken();
    if (settings.url.includes('https'))
        return;
    if (token != null)
        jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);

});

//
$(document).ready(function () {

    //
    var indexOfActiveTab = 0;

    initialSetup();

    hashChange();

    //
    setupTabs(indexOfActiveTab);

});

function hashChange() {

    $(window).on('hashchange', function () {
        if (location.hash.includes("profile")) {

            indexOfActiveTab = 0;
        }
        else if(location.hash.includes("airlinecompanies")) {

            indexOfActiveTab = 1;
        }
        else if(location.hash.includes("flights")) {

            indexOfActiveTab = 2;
        }
    });

    $(window).trigger('hashchange');
}

function initialSetup() {

    setInterval(refreshToken, 60000);

    $("#logout").click(function(event) {

        localStorage.setItem('jwtToken', null);
        window.location.href = '/';
    });
}

function setupTabs(indexOfActiveTab) {

    $("#tabs").tabs({
        active: indexOfActiveTab,
        collapsible: true,
        show: 400
    });

    // podesavanje hash
    $("#tabs").on("tabsbeforeload", function (event, ui) {

        //selektovani tab
        var indeks = $(this).tabs("option", "active");

        if (indeks == 0) {

            location.hash = 'profile';
        } else if (indeks == 1) {

            location.hash = 'airlinecompanies';
        }  else if (indeks == 2) {

            location.hash = 'flights';
        }
    });

    // kod za svaki od ucitanih tabova
    $("#tabs").on("tabsload", function (event, ui) {

        var indeks = $(this).tabs("option", "active");

        if (indeks == 0) {

            profileInfoSetup();
        }
        else if (indeks == 1) {

            airlineCompaniesSetup();
        }
        else if (indeks == 2) {

            flightsSetup();
        }
    });

    $("#tabs").tabs("load", indexOfActiveTab).show("fade", {}, 400, function() {});
}

function profileInfoSetup() {

    // Provjera telefona
    $.validator.methods.phoneCheck = function(value, element) {
        return this.optional(element)
            || /([0-9]{3,3}\/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2})$/
                .test(value);
    };

    $("#username").prop("disabled", true);
    $("#userInfoFieldset").prop("disabled", true);

    validatorProfileInfo = $('#profileInfoForm').validate({
        rules: {
            username: {
                required: true,
                remote: "/registereduser/profile/validity/checkusername/"
            },
            email: {
                required: true,
                email: true,
                remote: "/registereduser/profile/validity/checkemail/"
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
                remote: "Korisničko ime se ne može mjenjati"
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
                remote: "Email se ne može mjenjati"
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

        validatorProfileInfo.resetForm();
    });

    $(document).on("click", "#submit", function (event) {

        if ($("#profileInfoForm").valid()) {

            var myform = $('#profileInfoForm');

            // Find disabled inputs, and remove the "disabled" attribute
            var disabled = myform.find(':input:disabled').removeAttr('disabled');

            // serialize the form
            var serialized = myform.serialize();

            // re-disabled the set of inputs that you previously enabled
            disabled.attr('disabled','disabled');


            $.ajax({
                url: "/api/registeredusers/edit",
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
}

function flightsSetup() {

    // Polja za datume
    $("#startDate").datepicker();
    $("#endDate").datepicker();

    // GET SOURCES FOR AUTOCOMPLETE

    // Polja za destinacije
    $("#startPointCity").autocomplete({
        source: availableTags
    });
    $("#startPointState").autocomplete({});

    $("#endPointCity").autocomplete({});
    $("#endPointState").autocomplete({});

    // Submit search flight form button
    $("#submitFlightForm").on('click', function (event) {

        event.preventDefault();
        
        //searchFlightAdditionalFields
        
        // AJAX POST SA UCITAVANJEM REZULTATA
    });

    //
    $("#showNewFlightFormBtn").on('click', function (event) {

        $("#result")
            .empty()
            .load("/airportadmins/flights/new", function($context) {

                console.log("new flight form loaded!!!");
            });
    });
    
    $("#flightsSearchBtn").on('click', function (event) {

        var $context = $(this)[0];


        $("#result")
            .load("/airportadmins/flights/search", function($context) {

                console.log("search form loaded!!!");
            });
    });
}

function airlineCompaniesSetup() {

}





















/**
 * ovde su funckije za:
 * uzimanje tokena
 * preomenu sifre prilikom prvog logovanja
 * dodavanje dugmica u nav bar
 * prikaz profila admina
 * prikaz kompanije i njenih filijala
 * i neke pomocne xD
 */

function getToken() {
    return localStorage.getItem('jwtToken');
}
function refreshToken(){
	$.ajax({
		   url: '/user/refresh',
           type: 'post',
           success: function (data) {
        	   localStorage.setItem('jwtToken',data.accessToken);
        	   }
	});
}

$(document).ajaxSend(function (event, jqxhr, settings) {
    var token = getToken();
    if (token != null)
        jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
    
});

$(document).ready(
	
    function () {
      setInterval(refreshToken, 60000); //svaki min
        $.validator.methods.phoneCheck = function (value, element) {
            return this.optional(element)
                || /([0-9]{3,3}\/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2})$/
                    .test(value);
        }
        $.ajax({
            url: 'user/checkactivated',
            type: 'get'
        }).then(function (data) {
            if (data == true) {
                addRCAButtons();
            } else {
                changePassword();
            }
        });

    });

function changePassword() {
    $("#changePasswordModal").modal({
        show: true,
        backdrop: 'static',
        keyboard: false
    });

    $('#changePasswordForm')
        .validate(
            {
                rules: {

                    changepassword: "required",
                    changepassword2: {
                        required: true,
                        equalTo: "#changepassword"
                    }

                },
                messages: {
                    changepassword: "Lozinka nije uneta",

                    changepassword2: {
                        required: "Unesite ponovo lozinku",
                        equalTo: "Lozinke se ne poklapaju"
                    }
                }
            });

    $('#changePasswordButton').click(

        function () {
            if ($('#changePasswordForm').valid()) {
                var d = {};
                d.username = "";
                d.password = $('#changepassword').val();
                d.firstName = "";
                d.lastName = "";
                d.phone = "";
                d.email = "";
                d.city = "";
                role = "";

                $.ajax({
                    url: '/user/changePassword',
                    type: 'post',
                    contentType: 'application/json',
                    data: JSON.stringify(d),

                    success: function (data) {
                        if (data == true) {
                            $("#changePasswordModal").modal('hide');
                            addRCAButtons();
                        } else {
                            $('#regbtn-error').show().html(
                                'Promena lozinke nije uspela').fadeOut(
                                    5000);

                        }
                    }
                });

            }
        });
}

$(window).resize(adjust_body_offset);
adjust_body_offset();
function addRCAButtons() {

    $('.logged-out-navbar .navbar-nav').append(
        '<li class="nav-item">' + ' <button id="logout"'
        + '  class="btn btn-primary">Odjavi se</button> </li>');
    $('.logged-out-navbar .navbar-nav').append(
        '<li class="nav-item">' + ' <button id="myProfRcaAdmin"'
        + '  class="btn btn-primary">Moj profil</button> </li>');

    $('.logged-out-navbar .navbar-nav').append(
        '<li class="nav-item">' + ' <button id="myCompany"'
        + '  class="btn btn-primary">Kompanija</button> </li>');
    $('.logged-out-navbar .navbar-nav').append(
            '<li class="nav-item">' + ' <button id="myCars"'
            + '  class="btn btn-primary">Automobili</button> </li>');

    $('#myCars').click(function () {
        location.hash = 'mycars';
    });
    $('#myProfRcaAdmin').click(function () {
        location.hash = 'myprofile';
    });
    $('#logout').click(function () {
        localStorage.setItem('jwtToken', null);
        window.location.href = '/index.html';
    });
    $('#myCompany').click(function () {
        location.hash = 'mycompany';
    });

    $(window).on('hashchange', function () {
        // alert('Changed');
        if (location.hash === '#myprofile') {
            myProfileRCAAdmin();
        } else if (location.hash === '#mycompany') {
            myCompanyRACAdmin();
        }else if (location.hash === '#mycars') {
            myCarsRACAdmin();
            }
    });

}

function myProfileRCAAdmin() {
    $
        .ajax({
            url: 'racadmin/i/',
            type: 'get',
            success: function (user) {
                $('#items').empty();

                html = '<div class="container">';
                html += '<a data-toggle="collapse" href="#prof"><h2>Podaci o korisniku</h2></a>';
                html += '<div class="row collapse bakground" id="prof"></div>';
                html += '</div>';
                $('#items').append(html);
                $('#prof').load("rent/parts.html #prof2", function () {
                    $("#regusername").val(user.username);
                    $("#regemail").val(user.email);
                    $("#regfirstName").val(user.firstName);
                    $("#reglastName").val(user.lastName);
                    $("#regphone").val(user.phone);
                    $("#regcity").val(user.city);

                    $('#editForm')
                        .validate(
                            {
                                rules: {

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

                                    firstName: {
                                        required: "Ime nije uneto",
                                    },
                                    lastName: {
                                        required: "Prezime nije uneto",
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
                    $('#editBtn').click(

                        function () {
                            if ($('#editForm').valid()) {
                                var d = {};
                                d.username = $('#regusername').val();
                                d.password = $('#regpassword').val();
                                d.firstName = $('#regfirstName').val();
                                d.lastName = $('#reglastName').val();
                                d.phone = $('#regphone').val();
                                d.email = $('#regemail').val();
                                d.city = $('#regcity').val();

                                $.ajax({
                                    url: '/racadmin/edit',
                                    type: 'put',
                                    contentType: 'application/json',
                                    data: JSON.stringify(d),

                                    success: function (data) {
                                        if (data == true) {

                                            $('#btn-error').show().html(
                                                'Uspesno!').fadeOut(
                                                    5000);

                                            // window.location.replace("");
                                        } else {
                                            $('#btn-error').show().html(
                                                'Doslo je do greske!')
                                                .fadeOut(5000);

                                        }
                                    }

                                });

                            }
                        });


                });

                //$('#editForm').append(html);




            }

        });

}

function myCompanyRACAdmin() {
    $
        .ajax({
            url: 'racadmin/company/',
            type: 'get',
            success: function (company) {
                $('#items').empty();

                let html = '<div class="container">';
                html = '<a data-toggle="collapse" href="#prof"><h2>Detalji</h2></a>';
                html += '<div class="row collapse row-eq-height bakground" id="prof"></div>';
                html += '</div>';
                $('#items').append(html);
                html = '<div class="container">';
                html = '<a data-toggle="collapse" href="#branches"><h2>Filijale</h2></a>';
                html += '<div class="row collapse row-eq-height bakground" id="branches"></div>';
                html += '</div>';
                $('#items').append(html);

                $("#branches").load("rent/parts.html #branches > table", function () {
                    $("#branches").prepend('<button class="btn btn-primary" id="newBranch">Nova filijala</button>');
                    $('#newBranch').click(function () {
                        let c = {};
                        $.ajax({
                            url: 'racadmin/company/',
                            type: 'get',
                            async: false,
                            success: function (company) {
                                c = company;
                            }

                        });
                        if (c == "") {
                            alert('Ne možete dodati filijale nepostojećoj kompaniji!');
                            return;

                        }
                        showModal('add');
                    });




                    if (company != "")
                        for (let index = 0; index < company.branchOffices.length; ++index) {
                            let value = company.branchOffices[index];
                            let html = createBoTR(value);
                            $('#btable tbody').append(html);
                        }

                    deleteListeners();


                });

                //------------
                $("#prof").load("rent/parts.html #prof > div", function () {
                    if (company != "") {
                        $("#name").val(company.name);
                        $("#description").val(company.description);
                        $("#address").val(company.address);
                        $("#city").val(company.location.city);
                        $("#country").val(company.location.country);

                    }
                    //------------------
                    editFormValidation();
                    //---------------------------				
                    $('#editBtn').click(

                        function () {
                            if ($('#editForm').valid()) {
                                var d = {};
                                d.name = $('#name').val();
                                d.description = $('#description').val();
                                d.location = {};
                                d.location.country = $('#country').val();
                                d.address = $('#address').val();
                                d.location.city = $('#city').val();

                                $.ajax({
                                    url: '/racadmin/editcompany',
                                    type: 'put',
                                    contentType: 'application/json',
                                    data: JSON.stringify(d),

                                    success: function (data) {
                                        if (data == true) {

                                            $('#btn-error').show().html(
                                                'Uspesno!').fadeOut(
                                                    5000);
                                        } else {
                                            $('#btn-error').show().html(
                                                'Doslo je do greske!')
                                                .fadeOut(5000);
                                        }
                                    }

                                });

                            }
                        });
                    //------------------------------------------	

                });


            }

        });

}

function createBoTR(value) {
    html = '<tr class="br-row" id="' + value.id + '">';
    html += '<td class="adr">' + value.address + '</td>';
    html += '<td class="city">' + value.location.city + '</td>';
    html += '<td class="country">' + value.location.country + '</td>';
    html += '<td><button class="btn rembtn btn-danger close">×</button></td>';
    html += '</tr>';
    return html;
}

function deleteListeners() {
    $('.br-row').unbind('click');
    $('.br-row').click(function () {
    	$this=this;
    	let cb=function(){
    		$('#modaladdress').val($($this).find('.adr').text());
            $('#modalcity').val($($this).find('.city').text());
            $('#modalcountry').val($($this).find('.country').text());
    	}
        showModal('edit',$(this).attr('id'),cb);

        
    });


    $('.rembtn').unbind('click');
    $('.rembtn').click(function (event) {
    	event.stopPropagation();
        let row = $(this).parent().parent();
        $.ajax({

            url: 'racadmin/branch/' + $(this).parent().parent().attr('id'),
            type: 'delete',
            success: function (data) {
                if (data == true)
                    row.remove();
            }
        });

    });
}
//prikazuje modal za dodavanje/izmenu filijale
function showModal(flag,id,cb) {
    $('body').append('<div id="modalcont"></div><button id="hbtn" hidden data-toggle="modal" href="#modalBO"></button>');
    $('#modalcont').load("rent/parts.html #modalBO", function () {
        $('#newboForm')
            .validate(
                {
                    rules: {

                        modaladdress: {
                            required: true
                        },
                        modalcity: {
                            required: true
                        },
                        modalcountry: {
                            required: true
                        }

                    },
                    messages: {
                        modaladdress: {
                            required: "Adresa nije uneta",
                        },

                        modalcountry: {
                            required: "Drzava nije uneta"
                        },

                        modalcity: {
                            required: "Grad nije unet"
                        }
                    }
                });
        $('#modalBO').on('hidden.bs.modal', function (e) {
            $('#modalcont').remove();
            $('#hbtn').remove();
        });
      
        $('#modalBO').on('shown.bs.modal', function (e) {
        	  if(cb!=undefined)
              	cb();
        });
        if(flag=='edit'){
        	$('#modal-title').html('Izmeni filijalu');
        	$('#addbo').html('Izmeni');
        	
        }
        
        $('#hbtn').trigger("click");
        $('#addbo').click(function () {

            if ($('#newboForm').valid()) {
                var d = {};

                d.location = {};
                d.location.country = $('#modalcountry').val();
                d.address = $('#modaladdress').val();
                d.location.city = $('#modalcity').val();
                if (flag == 'add') {
                    $.ajax({
                        url: '/racadmin/branch',
                        type: 'post',
                        contentType: 'application/json',
                        data: JSON.stringify(d),

                        success: function (data) {
                            if (data != null) {

                                $('#btn-error2').show().html(
                                    'Uspesno!').fadeOut(
                                        5000);
                                d.id = data;
                                let html = createBoTR(d);
                                $('#btable tbody').append(html);
                                deleteListeners();
                            } else {
                                $('#btn-error2').show().html(
                                    'Doslo je do greske!')
                                    .fadeOut(5000);
                            }
                        }

                    });
                }
                else {
                	d.id=id;
                    $.ajax({
                        url: '/racadmin/branch',
                        type: 'put',
                        contentType: 'application/json',
                        data: JSON.stringify(d),

                        success: function (data) {
                            if (data) {

                                $('#btn-error2').show().html(
                                    'Uspesno!').fadeOut(
                                        5000);
                               $('#'+id).replaceWith(createBoTR(d));
                                deleteListeners();
                            } else {
                                $('#btn-error2').show().html(
                                    'Doslo je do greske!')
                                    .fadeOut(5000);
                            }
                        }

                    });
                }
            }

        });


    });
    

}
//validacija forme za izmenu /dodavanje
function editFormValidation() {
    $('#editForm')
        .validate(
            {
                rules: {

                    name: {
                        required: true
                    },
                    address: {
                        required: true
                    },
                    city: {
                        required: true
                    },
                    country: {
                        required: true
                    }

                },
                messages: {

                    name: {
                        required: "Ime nije uneto",
                    },
                    address: {
                        required: "Adresa nije uneta",
                    },

                    country: {
                        required: "Drzava nije uneta"
                    },

                    city: {
                        required: "Grad nije unet"
                    }
                }
            });

}
//pomera page dole, da se vidi navbar
function adjust_body_offset() {
    $('#page').css('padding-top', $('.navbar').outerHeight(true) + 'px');
}
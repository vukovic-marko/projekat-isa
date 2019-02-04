
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

$(document).ajaxSend(function(event, jqxhr, settings) {
	var token = getToken();
	if (token != null)
		jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
});

function addSysAdminButtons() {
	$('.system-admin-navbar .navbar-nav').append(
			'<li class="nav-item">' + ' <button id="logout"'
					+ '  class="btn btn-primary">Odjavi se</button> </li>');
	
	$('#logout').click(function() {
		localStorage.setItem('jwtToken', null);
		window.location.href = '/index.html';
	});
	
	$('.system-admin-navbar .navbar-nav')
	.append(
			'<li class="nav-item">'
					+ ' <button id="refModalShow" data-toggle="modal" href="#registerAdminModal"'
					+ '  class="btn btn-primary">Registruj administratora</button> </li>');
	
	$('#registerAdminForm')
	.validate(
			{
				rules : {
					username : {
						required : true,
						remote : "user/checkusername/"
					},
					password : "required",
					email : {
						required : true,
						email : true,
						remote : "user/checkemail/"
					},
					firstName : {
						required : true
					},
					lastName : {
						required : true
					},
					password2 : {
						required : true,
						equalTo : "#regpassword"
					},
					phone : {
						required : true,
						phoneCheck : true
					},
					city : {
						required : true
					}

				},
				messages : {
					username : {
						required : "Ime nije uneto",
						remote : "Korisničko ime je zaueto"
					},

					password : "Lozinka nije uneta",

					password2 : {
						required : "Unesite ponovo lozinku",
						equalTo : "Lozinke se ne poklapaju"
					},
					firstName : {
						required : "Ime nije uneto",
						letters : "Dozvoljena su samo slova srpske latinice i razmaci"
					},
					lastName : {
						required : "Prezime nije uneto",
						letters : "Dozvoljena su samo slova srpske latinice i razmaci"
					},
					email : {
						required : "Email adresa nije uneta",
						email : "Neispravan format adrese",
						remote : "Već postoji nalog koji je registrovan na ovu email adresu"
					},
					phone : {
						required : "Telefon nije unet",
						phoneCheck : "Neispravan format (123/456-78-99)"
					},

					city : {
						required : "Grad nije unet"
					}
				}
			});
	
	$('#registerButton').click(

			function() {
				if ($('#registerAdminForm').valid()) {
					var d = {};
					d.username = $('#regusername').val();
					d.password = $('#regpassword').val();
					d.firstName = $('#regfirstName').val();
					d.lastName = $('#reglastName').val();
					d.phone = $('#regphone').val();
					d.email = $('#regemail').val();
					d.city = $('#regcity').val();
					role = $('#regrole').val();
					
					console.log(d);

					$.ajax({
						url : '/sysadmin/register/' + role,
						type : 'post',
						contentType : 'application/json',
						data : JSON.stringify(d),

						success : function(data) {
							if (data == true) {
								$("#registerAdminModal").modal('hide');

								$("#successModal").modal('show');

								// window.location.replace("");
							} else {
								$('#regbtn-error').show().html(
										'Registracija nije uspela').fadeOut(
										5000);

							}
						}
					});

				}
			});
}

$(document).ready(function() {
	setInterval(refreshToken, 60000); //svaki min
	
	$.validator.methods.phoneCheck = function(value, element) {
		return this.optional(element)
				|| /([0-9]{3,3}\/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2})$/
						.test(value);
	}
	
	$.ajax({
		url: 'user/checkactivated',
		type: 'get',
		statusCode: {
			401: function() {
				alert('Potrebno je da se ponovo ulogujete');
				localStorage.setItem('jwtToken', null);
				window.location.href = '/index.html';
			}
		}
	}).then(function(data) {
		if (data == true) {
			addSysAdminButtons();
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
				rules : {
					
					changepassword : "required",
					changepassword2 : {
						required : true,
						equalTo : "#changepassword"
					}

				},
				messages : {
					changepassword : "Lozinka nije uneta",

					changepassword2 : {
						required : "Unesite ponovo lozinku",
						equalTo : "Lozinke se ne poklapaju"
					}
				}
			});
	
	$('#changePasswordButton').click(

			function() {
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
						url : '/user/changePassword',
						type : 'post',
						contentType : 'application/json',
						data : JSON.stringify(d),

						success : function(data) {
							if (data == true) {
								$("#changePasswordModal").modal('hide');
								addSysAdminButtons();
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

function adjust_body_offset() {
	$('#page').css('padding-top', $('.navbar').outerHeight(true) + 'px');
}
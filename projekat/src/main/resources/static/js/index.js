function getToken() {
	return localStorage.getItem('jwtToken');
}
$(document).ajaxSend(function(event, jqxhr, settings) {
	var token = getToken();
	if (token != null)
		jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
});

$(document).on("click", ".table-system-admin input[type=\"radio\"]", function(e) {
	var username =e.target.attributes["meta-username"].value;
	var kind = e.target.attributes["meta-kind"].value;
	var obj = username;
	
	$.ajax({
		url: 'user/editRole/' + kind,
		type: 'post',
		contentType : 'application/json',
		data : JSON.stringify(obj),
		success: function(e) {
			alert("Uspesno promenjena uloga.");
		},
		statusCode : {
			400: function(data) {
				alert("Nije moguce promeniti svoj tip.");
				setView();
			},
			401: function(data) {
				alert("Samo administratori sistema mogu menjati uloge korisnika.");
				setView();
			}
		}
		
	});
	//e.preventDefault();
});


$(document).ready(

		function() {
			
			$.validator.methods.phoneCheck = function(value, element) {
				return this.optional(element)
						|| /([0-9]{3,3}\/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2})$/
								.test(value);
			}

			fillButtons();
		});

$(window).resize(adjust_body_offset);
adjust_body_offset();

function fillButtons() {	
	
	$('.logged-out-navbar .navbar-nav').append(
			'<li class="nav-item">'
					+ ' <button data-toggle="modal" href="#loginModal"'
					+ '  class="btn btn-primary">Prijavi se</button> </li>');
	$('#loginForm').validate({
		rules : {
			username : "required",
			password : "required"
		},
		messages : {
			username : "Ime nije uneto",

			password : "Lozinka nije uneta"

		}
	});
	$('#registerForm')
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

	$('#login').click(function() {

		if ($('#loginForm').valid()) {
			var d = {};
			d.username = $('#loginModal #username').val();
			d.password = $('#loginModal #password').val();
			$.ajax({
				url : 'user/login',
				type : 'post',
				contentType : 'application/json',
				data : JSON.stringify(d),
				success : function(data) {
					localStorage.setItem('jwtToken',data.accessToken);
					window.location.replace("/");

				},
				statusCode : {
					401 : function(data) {
						var string = data.responseJSON.message;
						var flag = string.localeCompare("Bad credentials");
						if (flag == 0)
							string = "Neispravno korisničko ime i/ili lozinka";
						else
							string = "Korisnički nalog nije aktiviran"
						$('#btn-error').show().html(string).fadeOut(5000);
					}
				}
			});
		}
	});
	$('.logged-out-navbar .navbar-nav')
			.append(
					'<li class="nav-item">'
							+ ' <button id="refModalShow" data-toggle="modal" href="#registerModal"'
							+ '  class="btn btn-primary">Registruj se</button> </li>');
	$('#registerButton').click(

			function() {
				if ($('#registerForm').valid()) {
					var d = {};
					d.username = $('#regusername').val();
					d.password = $('#regpassword').val();
					d.firstName = $('#regfirstName').val();
					d.lastName = $('#reglastName').val();
					d.phone = $('#regphone').val();
					d.email = $('#regemail').val();
					d.city = $('#regcity').val();

					$.ajax({
						url : '/user/register',
						type : 'post',
						contentType : 'application/json',
						data : JSON.stringify(d),

						success : function(data) {
							if (data == true) {
								$("#registerModal").modal('hide');

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

function adjust_body_offset() {
	$('#page').css('padding-top', $('.navbar').outerHeight(true) + 'px');
}
function getToken() {
	return localStorage.getItem('jwtToken');
}
$(document).ajaxSend(function(event, jqxhr, settings) {
	var token = getToken();
	if (token != null)
		jqxhr.setRequestHeader('Authorization', 'Bearer ' + token);
});

$(document).ready(

		function() {
			$.validator.methods.phoneCheck = function(value, element) {
				return this.optional(element)
						|| /([0-9]{3,3}\/[0-9]{3,3}-[0-9]{2,2}-[0-9]{2,2})$/
								.test(value);
			}

			addRCAButtons();
		});

$(window).resize(adjust_body_offset);
adjust_body_offset();
function addRCAButtons() {

	$('.logged-out-navbar .navbar-nav').append(
			'<li class="nav-item">' + ' <button id="logout"'
					+ '  class="btn btn-primary">Odjavi se</button> </li>');
	$('.logged-out-navbar .navbar-nav').append(
			'<li class="nav-item">' + ' <button id="myProfRcaAdmin"'
					+ '  class="btn btn-primary">Moj profil</button> </li>');

	$('#myProfRcaAdmin').click(function() {
		location.hash = 'myprofile';
	});
	$('#logout').click(function() {
		localStorage.setItem('jwtToken', null);
		window.location.href = '/index.html';
	});

	$(window).on('hashchange', function() {
		// alert('Changed');
		if (location.hash === '#myprofile') {
			myProfileRCAAdmin();
		}
	});

}

function myProfileRCAAdmin() {
	$
			.ajax({
				url : 'racadmin/i/',
				type : 'get',
				success : function(user) {
					$('#items').empty();

					html = '<div class="container">';
					html += '<a data-toggle="collapse" href="#prof"><h2>Podaci o korisniku</h2></a>';
					html += '<div class="row collapse bakground" id="prof"></div>';
					html += '</div>';
					$('#items').append(html);
					$('#prof')
							.append(
									'<form style="margin: auto; width: -webkit-fill-available;" id="editForm"></form>');
					html = "<div class=\"form-group\">\r\n"
							+ "							<label for=\"regemail\">Email:</label> <input type=\"email\"\r\n"
							+ "								disabled class=\"form-control\" id=\"regemail\" name=\"email\">\r\n"
							+ "\r\n"
							+ "						</div>\r\n"
							+ "						<div class=\"form-group\">\r\n"
							+ "							<label for=\"regusername\">Korisničko ime:</label> <input\r\n"
							+ "								disabled type=\"text\" class=\"form-control\" name=\"username\"\r\n"
							+ "								id=\"regusername\">\r\n"
							+ "						</div>\r\n"
							+

							"\r\n"
							+ "						<div class=\"form-group\">\r\n"
							+ "							<label for=\"regfirstNname\">Ime:</label> <input type=\"text\"\r\n"
							+ "								class=\"form-control\" name=\"firstName\" id=\"regfirstName\">\r\n"
							+ "						</div>\r\n"
							+ "						<div class=\"form-group\">\r\n"
							+ "							<label for=\"reglastName\">Prezime:</label> <input type=\"text\"\r\n"
							+ "								class=\"form-control\" name=\"lastName\" id=\"reglastName\">\r\n"
							+ "						</div>\r\n"
							+ "						<div class=\"form-group\">\r\n"
							+ "							<label for=\"regphone\">Telefon:</label> <input type=\"text\"\r\n"
							+ "								class=\"form-control\" name=\"phone\" id=\"regphone\">\r\n"
							+ "						</div>\r\n"
							+ "						<div class=\"form-group\">\r\n"
							+ "							<label for=\"regcity\">Grad:</label> <input type=\"text\"\r\n"
							+ "								class=\"form-control\" name=\"city\" id=\"regcity\">\r\n"
							+ "						</div>"
							+ "<button type=\"button\" id=\"editBtn\" class=\"btn btn-primary\">Izmeni</button>"
							+ "<label style=\"display: none\" id=\"btn-error\" class=\"error\"\r\n"
							+ "							for=\"editBtn\"></label>";
					$('#editForm').append(html);
					$("#regusername").val(user.username);
					$("#regemail").val(user.email);
					$("#regfirstName").val(user.firstName);
					$("#reglastName").val(user.lastName);
					$("#regphone").val(user.phone);
					$("#regcity").val(user.city);
					$('#editForm')
							.validate(
									{
										rules : {

											firstName : {
												required : true
											},
											lastName : {
												required : true
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

											firstName : {
												required : "Ime nije uneto",
											},
											lastName : {
												required : "Prezime nije uneto",
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

					$('#editBtn').click(

							function() {
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
										url : '/racadmin/edit',
										type : 'put',
										contentType : 'application/json',
										data : JSON.stringify(d),

										success : function(data) {
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

				}

			});

}

function adjust_body_offset() {
	$('#page').css('padding-top', $('.navbar').outerHeight(true) + 'px');
}